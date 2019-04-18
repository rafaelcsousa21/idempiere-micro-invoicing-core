package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.accounting.MProduct;
import org.compiere.accounting.MStorageOnHand;
import org.compiere.accounting.MWarehouse;
import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_InOutLine;
import org.compiere.order.MInOut;
import org.compiere.orm.Query;
import org.compiere.product.MAttributeSetInstance;
import org.compiere.product.MUOM;
import org.compiere.production.MLocator;
import org.compiere.util.MsgKt;
import org.idempiere.common.exceptions.FillMandatoryException;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;

import java.math.BigDecimal;
import java.util.List;

import static software.hsharp.core.util.DBKt.getSQLValueEx;

public class MInOutLine extends org.compiere.order.MInOutLine implements IPODoc {

    /**
     * Product
     */
    private MProduct m_product = null;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param M_InOutLine_ID id
     */
    public MInOutLine(int M_InOutLine_ID) {
        super(M_InOutLine_ID);
    }

    /**
     * Parent Constructor
     *
     * @param inout parent
     */
    public MInOutLine(MInOut inout) {
        super(inout);
    }

    /**
     * Load Constructor
     */
    public MInOutLine(Row row) {
        super(row);
    } //	MInOutLine

    /**
     * Get Ship lines Of Order Line
     *
     * @param C_OrderLine_ID line
     * @param where          optional addition where clause
     * @return array of receipt lines
     */
    public static MInOutLine[] getOfOrderLine(
            int C_OrderLine_ID, String where) {
        String whereClause = "C_OrderLine_ID=?" + (!Util.isEmpty(where, true) ? " AND " + where : "");
        List<org.compiere.order.MInOutLine> list =
                new Query(I_M_InOutLine.Table_Name, whereClause)
                        .setParameters(C_OrderLine_ID)
                        .list();
        return list.toArray(new MInOutLine[0]);
    } //	getOfOrderLine

    /**
     * Set Invoice Line. Does not set Quantity!
     *
     * @param iLine        invoice line
     * @param M_Locator_ID locator
     * @param Qty          qty only fo find suitable locator
     */
    public void setInvoiceLine(MInvoiceLine iLine, int M_Locator_ID, BigDecimal Qty) {
        setOrderLineId(iLine.getOrderLineId());
        setLine(iLine.getLine());
        setUOMId(iLine.getUOMId());
        int M_Product_ID = iLine.getProductId();
        if (M_Product_ID == 0) {
            setValueNoCheck("M_Product_ID", null);
            setValueNoCheck("M_Locator_ID", null);
            setValueNoCheck("M_AttributeSetInstance_ID", null);
        } else {
            setProductId(M_Product_ID);
            setAttributeSetInstanceId(iLine.getAttributeSetInstanceId());
            if (M_Locator_ID == 0) setLocatorId(Qty); // 	requires warehouse, product, asi
            else setLocatorId(M_Locator_ID);
        }
        setChargeId(iLine.getChargeId());
        setDescription(iLine.getDescription());
        setIsDescription(iLine.isDescription());
        //
        setProjectId(iLine.getProjectId());
        setProjectPhaseId(iLine.getProjectPhaseId());
        setProjectTaskId(iLine.getProjectTaskId());
        setBusinessActivityId(iLine.getBusinessActivityId());
        setCampaignId(iLine.getCampaignId());
        setTransactionOrganizationId(iLine.getTransactionOrganizationId());
        setUser1Id(iLine.getUser1Id());
        setUser2Id(iLine.getUser2Id());
    } //	setInvoiceLine

    /**
     * Set (default) Locator based on qty.
     *
     * @param Qty quantity Assumes Warehouse is set
     */
    @Override
    public void setLocatorId(BigDecimal Qty) {
        //	Locator established
        if (getLocatorId() != 0) return;
        //	No Product
        if (getProductId() == 0) {
            setValueNoCheck(I_M_InOutLine.COLUMNNAME_M_Locator_ID, null);
            return;
        }

        //	Get existing Location
        int M_Locator_ID =
                MStorageOnHand.getLocatorId(
                        getWarehouseId(),
                        getProductId(),
                        getAttributeSetInstanceId(),
                        Qty,
                        null);
        //	Get default Location
        if (M_Locator_ID == 0) {
            MWarehouse wh = MWarehouse.get(getWarehouseId());
            M_Locator_ID = wh.getDefaultLocator().getLocatorId();
        }
        setLocatorId(M_Locator_ID);
    } //	setLocatorId

    /**
     * ************************************************************************ Before Save
     *
     * @param newRecord new
     * @return save
     */
    @Override
    protected boolean beforeSave(boolean newRecord) {
        log.fine("");
        if (newRecord && getParent().isComplete()) {
            log.saveError("ParentComplete", MsgKt.translate("M_InOutLine"));
            return false;
        }
        // Locator is mandatory if no charge is defined - teo_sarca BF [ 2757978 ]
        if (getProduct() != null && MProduct.PRODUCTTYPE_Item.equals(getProduct().getProductType())) {
            if (getLocatorId() <= 0 && getChargeId() <= 0) {
                throw new FillMandatoryException(I_M_InOutLine.COLUMNNAME_M_Locator_ID);
            }
        }

        //	Get Line No
        if (getLine() == 0) {
            String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM M_InOutLine WHERE M_InOut_ID=?";
            int ii = getSQLValueEx(sql, getInOutId());
            setLine(ii);
        }
        //	UOM
        if (getUOMId() == 0) setUOMId(Env.getContextAsInt("#C_UOM_ID"));
        if (getUOMId() == 0) {
            int C_UOM_ID = MUOM.getDefault_UOMId();
            if (C_UOM_ID > 0) setUOMId(C_UOM_ID);
        }
        //	Qty Precision
        if (newRecord || isValueChanged("QtyEntered")) setQtyEntered(getQtyEntered());
        if (newRecord || isValueChanged("MovementQty")) setMovementQty(getMovementQty());

        //	Order/RMA Line
        if (getOrderLineId() == 0 && getRMALineId() == 0) {
            if (getParent().isSOTrx()) {
                log.saveError("FillMandatory", MsgKt.translate("C_Order_ID"));
                return false;
            }
        }

        // Validate Locator/Warehouse - teo_sarca, BF [ 2784194 ]
        if (getLocatorId() > 0) {
            MLocator locator = MLocator.get(getLocatorId());
            if (getWarehouseId() != locator.getWarehouseId()) {
                throw new WarehouseLocatorConflictException(
                        MWarehouse.get(getWarehouseId()), locator, getLine());
            }

            // IDEMPIERE-2668
            if (org.compiere.order.MInOut.MOVEMENTTYPE_CustomerShipment.equals(
                    getParent().getMovementType())) {
                if (locator.getLocatorTypeId() > 0) {
                    MLocatorType lt = MLocatorType.get(locator.getLocatorTypeId());
                    if (!lt.isAvailableForShipping()) {
                        log.saveError("Error", MsgKt.translate("LocatorNotAvailableForShipping"));
                        return false;
                    }
                }
            }
        }
        I_M_AttributeSet attributeset = getProduct().getMAttributeSet();
        boolean isAutoGenerateLot = false;
        if (attributeset != null) isAutoGenerateLot = attributeset.isAutoGenerateLot();
        if (getReversalLineId() == 0
                && !getParent().isSOTrx()
                && !getParent().getMovementType().equals(MInOut.MOVEMENTTYPE_VendorReturns)
                && isAutoGenerateLot
                && getAttributeSetInstanceId() == 0) {
            MAttributeSetInstance asi =
                    MAttributeSetInstance.generateLot(getProduct());
            setAttributeSetInstanceId(asi.getAttributeSetInstanceId());
        }
        /* Carlos Ruiz - globalqss
         * IDEMPIERE-178 Orders and Invoices must disallow amount lines without product/charge
         */
        if (getParent().getDocumentType().isChargeOrProductMandatory()) {
            if (getChargeId() == 0 && getProductId() == 0) {
                log.saveError("FillMandatory", MsgKt.translate("ChargeOrProductMandatory"));
                return false;
            }
        }

        return true;
    } //	beforeSave

    /**
     * Get Base value for Cost Distribution
     *
     * @param CostDistribution cost Distribution
     * @return base number
     */
    public BigDecimal getBase(String CostDistribution) {
        if (MLandedCost.LANDEDCOSTDISTRIBUTION_Costs.equals(CostDistribution)) {
            I_C_InvoiceLine m_il = MInvoiceLine.getOfInOutLine(this);
            if (m_il == null) {
                m_il = MInvoiceLine.getOfInOutLineFromMatchInv(this);
                if (m_il == null) {
                    log.severe("No Invoice Line for: " + this.toString());
                    return Env.ZERO;
                }
            }
            return this.getMovementQty().multiply(m_il.getPriceActual()); // Actual delivery
        } else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Line.equals(CostDistribution)) return Env.ONE;
        else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Quantity.equals(CostDistribution))
            return getMovementQty();
        else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Volume.equals(CostDistribution)) {
            MProduct product = getProduct();
            if (product == null) {
                log.severe("No Product");
                return Env.ZERO;
            }
            return getMovementQty().multiply(product.getVolume());
        } else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Weight.equals(CostDistribution)) {
            MProduct product = getProduct();
            if (product == null) {
                log.severe("No Product");
                return Env.ZERO;
            }
            return getMovementQty().multiply(product.getWeight());
        }
        //
        log.severe("Invalid Criteria: " + CostDistribution);
        return Env.ZERO;
    } //	getBase

    /**
     * Get Product
     *
     * @return product or null
     */
    public MProduct getProduct() {
        if (m_product == null && getProductId() != 0)
            m_product = MProduct.get(getProductId());
        return m_product;
    } //	getProduct

    /**
     * Set Product
     *
     * @param product product
     */
    public void setProduct(MProduct product) {
        m_product = product;
        if (m_product != null) {
            setProductId(m_product.getProductId());
            setUOMId(m_product.getUOMId());
        } else {
            setProductId(0);
            setUOMId(0);
        }
        setAttributeSetInstanceId(0);
    } //	setProduct

    public void setClientOrg(MInOut inout) {
        super.setClientOrg(inout);
    } //	setClientOrg

    @Override
    public void setDoc(IDoc doc) {
    }

    @Override
    public void setProcessedOn(String processed, boolean b, boolean b1) {
    }
}
