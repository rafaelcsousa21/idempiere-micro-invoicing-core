package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.invoicing.MLandedCost;
import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.orm.MRole;
import org.compiere.product.MAttributeSet;
import org.compiere.product.MUOM;
import org.compiere.product.ProductNotOnPriceListException;
import org.compiere.util.Msg;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;

import static software.hsharp.core.util.DBKt.getSQLValue;

public class MOrderLine extends org.compiere.order.MOrderLine implements IPODoc {

    /**
     * Product
     */
    protected MProduct m_product = null;

    public MOrderLine(MOrder order) {
        super(order);
    }

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set record
     * @param trxName transaction
     */
    public MOrderLine(Row row) {
        super(row);
    }

    /**
     * ************************************************************************ Default Constructor
     *
     * @param ctx            context
     * @param C_OrderLine_ID order line to load
     * @param trxName        trx name
     */
    public MOrderLine(int C_OrderLine_ID) {
        super(C_OrderLine_ID);
    }

    /**
     * Get Base value for Cost Distribution
     *
     * @param CostDistribution cost Distribution
     * @return base number
     */
    public BigDecimal getBase(String CostDistribution) {
        if (MLandedCost.LANDEDCOSTDISTRIBUTION_Costs.equals(CostDistribution)) {
            return this.getQtyOrdered().multiply(getPriceActual()); // Actual delivery
        } else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Line.equals(CostDistribution)) return Env.ONE;
        else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Quantity.equals(CostDistribution))
            return getQtyOrdered();
        else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Volume.equals(CostDistribution)) {
            org.compiere.product.MProduct product = getProduct();
            if (product == null) {
                log.severe("No Product");
                return Env.ZERO;
            }
            return getQtyOrdered().multiply(product.getVolume());
        } else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Weight.equals(CostDistribution)) {
            MProduct product = getProduct();
            if (product == null) {
                log.severe("No Product");
                return Env.ZERO;
            }
            return getQtyOrdered().multiply(product.getWeight());
        }
        //
        log.severe("Invalid Criteria: " + CostDistribution);
        return Env.ZERO;
    } //	getBase

    /**
     * ************************************************************************ Before Save
     *
     * @param newRecord
     * @return true if it can be saved
     */
    @Override
    protected boolean beforeSave(boolean newRecord) {
        if (newRecord && getParent().isComplete()) {
            log.saveError("ParentComplete", Msg.translate("C_OrderLine"));
            return false;
        }
        //	Get Defaults from Parent
        if (getBusinessPartnerId() == 0
                || getBusinessPartnerLocationId() == 0
                || getWarehouseId() == 0
                || getCurrencyId() == 0) setOrder(getParent());
        if (m_M_PriceList_ID == 0) setHeaderInfo(getParent());

        //	R/O Check - Product/Warehouse Change
        if (!newRecord && (isValueChanged("M_Product_ID") || isValueChanged("M_Warehouse_ID"))) {
            if (!canChangeWarehouse()) return false;
        } //	Product Changed

        //	Charge
        if (getChargeId() != 0 && getProductId() != 0) setProductId(0);
        //	No Product
        if (getProductId() == 0) setAttributeSetInstanceId(0);
            //	Product
        else //	Set/check Product Price
        {
            //	Set Price if Actual = 0
            if (m_productPrice == null
                    && Env.ZERO.compareTo(getPriceActual()) == 0
                    && Env.ZERO.compareTo(getPriceList()) == 0) setPrice();
            //	Check if on Price list
            if (m_productPrice == null) getProductPricing(m_M_PriceList_ID);
            // IDEMPIERE-1574 Sales Order Line lets Price under the Price Limit when updating
            //	Check PriceLimit
            boolean enforce = m_IsSOTrx && getParent().getPriceList().isEnforcePriceLimit();
            if (enforce && MRole.getDefault().isOverwritePriceLimit()) enforce = false;
            //	Check Price Limit?
            if (enforce
                    && !getPriceLimit().equals(Env.ZERO)
                    && getPriceActual().compareTo(getPriceLimit()) < 0) {
                log.saveError(
                        "UnderLimitPrice",
                        "PriceEntered=" + getPriceEntered() + ", PriceLimit=" + getPriceLimit());
                return false;
            }
            //
            if (!m_productPrice.isCalculated()) {
                throw new ProductNotOnPriceListException(m_productPrice, getLine());
            }
        }

        //	UOM
        if (getUOMId() == 0
                && (getProductId() != 0
                || getPriceEntered().compareTo(Env.ZERO) != 0
                || getChargeId() != 0)) {
            int C_UOM_ID = MUOM.getDefault_UOMId();
            if (C_UOM_ID > 0) setUOMId(C_UOM_ID);
        }
        //	Qty Precision
        if (newRecord || isValueChanged("QtyEntered")) setQtyEntered(getQtyEntered());
        if (newRecord || isValueChanged("QtyOrdered")) setQtyOrdered(getQtyOrdered());

        //	Qty on instance ASI for SO
        if (m_IsSOTrx
                && getAttributeSetInstanceId() != 0
                && (newRecord
                || isValueChanged("M_Product_ID")
                || isValueChanged("M_AttributeSetInstance_ID")
                || isValueChanged("M_Warehouse_ID"))) {
            MProduct product = getProduct();
            if (product.isStocked()) {
                int M_AttributeSet_ID = product.getAttributeSetId();
                boolean isInstance = M_AttributeSet_ID != 0;
                if (isInstance) {
                    MAttributeSet mas = MAttributeSet.get(M_AttributeSet_ID);
                    isInstance = mas.isInstanceAttribute();
                }
                //	Max
                if (isInstance) {
                    MStorageOnHand[] storages =
                            MStorageOnHand.getWarehouse(

                                    getWarehouseId(),
                                    getProductId(),
                                    getAttributeSetInstanceId(),
                                    null,
                                    true,
                                    false,
                                    0,
                                    null);
                    BigDecimal qty = Env.ZERO;
                    for (int i = 0; i < storages.length; i++) {
                        if (storages[i].getAttributeSetInstanceId() == getAttributeSetInstanceId())
                            qty = qty.add(storages[i].getQtyOnHand());
                    }

                    if (getQtyOrdered().compareTo(qty) > 0) {
                        log.warning("Qty - Stock=" + qty + ", Ordered=" + getQtyOrdered());
                        log.saveError("QtyInsufficient", "=" + qty);
                        return false;
                    }
                }
            } //	stocked
        } //	SO instance

        //	FreightAmt Not used
        if (Env.ZERO.compareTo(getFreightAmt()) != 0) setFreightAmt(Env.ZERO);

        //	Set Tax
        if (getTaxId() == 0) setTax();

        //	Get Line No
        if (getLine() == 0) {
            String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM C_OrderLine WHERE C_Order_ID=?";
            int ii = getSQLValue(sql, getOrderId());
            setLine(ii);
        }

        //	Calculations & Rounding
        setLineNetAmt(); //	extended Amount with or without tax
        setDiscount();

        /* Carlos Ruiz - globalqss
         * IDEMPIERE-178 Orders and Invoices must disallow amount lines without product/charge
         */
        if (getParent().getTargetDocumentType().isChargeOrProductMandatory()) {
            if (getChargeId() == 0
                    && getProductId() == 0
                    && (getPriceEntered().signum() != 0 || getQtyEntered().signum() != 0)) {
                log.saveError("FillMandatory", Msg.translate("ChargeOrProductMandatory"));
                return false;
            }
        }

        return true;
    } //	beforeSave

    /**
     * Before Delete
     *
     * @return true if it can be deleted
     */
    @Override
    protected boolean beforeDelete() {
        //	R/O Check - Something delivered. etc.
        if (Env.ZERO.compareTo(getQtyDelivered()) != 0) {
            log.saveError(
                    "DeleteError", Msg.translate("QtyDelivered") + "=" + getQtyDelivered());
            return false;
        }
        if (Env.ZERO.compareTo(getQtyInvoiced()) != 0) {
            log.saveError("DeleteError", Msg.translate("QtyInvoiced") + "=" + getQtyInvoiced());
            return false;
        }
        if (Env.ZERO.compareTo(getQtyReserved()) != 0) {
            //	For PO should be On Order
            log.saveError("DeleteError", Msg.translate("QtyReserved") + "=" + getQtyReserved());
            return false;
        }

        // UnLink All Requisitions
        MRequisitionLine.unlinkC_OrderLineId(getId());

        return true;
    } //	beforeDelete

    /**
     * After Delete
     *
     * @param success success
     * @return deleted
     */
    @Override
    protected boolean afterDelete(boolean success) {
        if (!success) return success;
        if (getResourceAssignmentId() != 0) {
            MResourceAssignment ra =
                    new MResourceAssignment(getResourceAssignmentId());
            ra.delete(true);
        }

        return updateHeaderTax();
    } //	afterDelete

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

    @Override
    public void setDoc(IDoc doc) {
    }

    @Override
    public void setProcessedOn(String processed, boolean b, boolean b1) {
    }
}
