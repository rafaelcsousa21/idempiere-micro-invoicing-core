package org.idempiere.process;

import org.compiere.accounting.MProduct;
import org.compiere.accounting.MStorageOnHand;
import org.compiere.model.I_M_Product;
import org.compiere.product.MAttributeSet;
import org.compiere.product.MUOM;
import org.compiere.production.MLocator;
import org.compiere.util.Msg;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.getSQLValue;

public class MDDOrderLine extends X_DD_OrderLine {
    /**
     *
     */
    private static final long serialVersionUID = -8878804332001384969L;

    /**
     * Logger
     */
    @SuppressWarnings("unused")
    private static CLogger s_log = CLogger.getCLogger(MDDOrderLine.class);
    private int m_M_PriceList_ID = 0;
    //
    private boolean m_IsSOTrx = true;
    /**
     * Product
     */
    private I_M_Product m_product = null;
    /**
     * Parent
     */
    private MDDOrder m_parent = null;

    /**
     * ************************************************************************ Default Constructor
     *
     * @param ctx            context
     * @param C_OrderLine_ID order line to load
     * @param trxName        trx name
     */
    public MDDOrderLine(Properties ctx, int C_OrderLine_ID) {
        super(ctx, C_OrderLine_ID);
        if (C_OrderLine_ID == 0) {
            //	setOrderId (0);
            //	setLine (0);
            //	setWarehouseId (0);	// @M_Warehouse_ID@
            //	setBusinessPartnerId(0);
            //	setBusinessPartnerLocationId (0);	// @C_BPartner_Location_ID@
            //	setCurrencyId (0);	// @C_Currency_ID@
            //	setDateOrdered (new Timestamp(System.currentTimeMillis()));	// @DateOrdered@
            //
            //	setC_Tax_ID (0);
            //	setC_UOM_ID (0);
            //
            setFreightAmt(Env.ZERO);
            setLineNetAmt(Env.ZERO);
            //
            setM_AttributeSetInstance_ID(0);
            //
            setQtyEntered(Env.ZERO);
            setQtyInTransit(Env.ZERO);
            setConfirmedQty(Env.ZERO);
            setTargetQty(Env.ZERO);
            setPickedQty(Env.ZERO);
            setQtyOrdered(Env.ZERO); // 1
            setQtyDelivered(Env.ZERO);
            setQtyReserved(Env.ZERO);
            //
            setIsDescription(false); // N
            setProcessed(false);
            setLine(0);
        }
    } //	MDDOrderLine

    /** Cached Currency Precision */
    // private Integer			m_precision = null;

    /**
     * Parent Constructor. ol.setM_Product_ID(wbl.getM_Product_ID());
     * ol.setQtyOrdered(wbl.getQuantity()); ol.setPrice(); ol.setPriceActual(wbl.getPrice());
     * ol.setTax(); ol.saveEx();
     *
     * @param order parent order
     */
    public MDDOrderLine(MDDOrder order) {
        this(order.getCtx(), 0);
        if (order.getId() == 0) throw new IllegalArgumentException("Header not saved");
        setDD_Order_ID(order.getDD_Order_ID()); // 	parent
        setOrder(order);
    } //	MDDOrderLine

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set record
     * @param trxName transaction
     */
    public MDDOrderLine(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MDDOrderLine

    /**
     * Set Defaults from Order. Does not set Parent !!
     *
     * @param order order
     */
    public void setOrder(MDDOrder order) {
        setClientOrg(order);
    /*setBusinessPartnerId(order.getBusinessPartnerId());
    setBusinessPartnerLocationId(order.getBusinessPartnerLocationId());*/
        // setWarehouseId(order.getWarehouseId());
        setDateOrdered(order.getDateOrdered());
        setDatePromised(order.getDatePromised());
        //
        setHeaderInfo(order); // 	sets m_order
        //	Don't set Activity, etc as they are overwrites
    } //	setOrder

    /**
     * Set Header Info
     *
     * @param order order
     */
    public void setHeaderInfo(MDDOrder order) {
        m_parent = order;
        m_IsSOTrx = order.isSOTrx();
    } //	setHeaderInfo

    /**
     * Get Parent
     *
     * @return parent
     */
    public MDDOrder getParent() {
        if (m_parent == null) m_parent = new MDDOrder(getCtx(), getDD_Order_ID());
        return m_parent;
    } //	getParent

    /**
     * Get Product
     *
     * @return product or null
     */
    public I_M_Product getProduct() {
        if (m_product == null && getM_Product_ID() != 0)
            m_product = MProduct.get(getCtx(), getM_Product_ID());
        return m_product;
    } //	getProduct

    /**
     * Set M_AttributeSetInstance_ID
     *
     * @param M_AttributeSetInstance_ID id
     */
    public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID == 0) // 	 0 is valid ID
            setValue("M_AttributeSetInstance_ID", new Integer(0));
        else super.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
    } //	setM_AttributeSetInstance_ID

    /**
     * Set Warehouse
     *
     * @param M_Warehouse_ID warehouse
     */
  /*public void setWarehouseId (int M_Warehouse_ID)
  {
  	if (getWarehouseId() > 0
  		&& getWarehouseId() != M_Warehouse_ID
  		&& !canChangeWarehouse())
  		log.severe("Ignored - Already Delivered/Invoiced/Reserved");
  	else
  		super.setWarehouseId (M_Warehouse_ID);
  }	//	setWarehouseId
  */

    /**
     * Can Change Warehouse
     *
     * @return true if warehouse can be changed
     */
    public boolean canChangeWarehouse() {
        if (getQtyDelivered().signum() != 0) {
            log.saveError("Error", Msg.translate(getCtx(), "QtyDelivered") + "=" + getQtyDelivered());
            return false;
        }

        if (getQtyReserved().signum() != 0) {
            log.saveError("Error", Msg.translate(getCtx(), "QtyReserved") + "=" + getQtyReserved());
            return false;
        }
        //	We can change
        return true;
    } //	canChangeWarehouse

    /**
     * Get C_Project_ID
     *
     * @return project
     */
    public int getProjectId() {
        int ii = super.getProjectId();
        if (ii == 0) ii = getParent().getProjectId();
        return ii;
    } //	getProjectId

    /**
     * Get C_Activity_ID
     *
     * @return Activity
     */
    public int getBusinessActivityId() {
        int ii = super.getBusinessActivityId();
        if (ii == 0) ii = getParent().getBusinessActivityId();
        return ii;
    } //	getBusinessActivityId

    /**
     * Get C_Campaign_ID
     *
     * @return Campaign
     */
    public int getCampaignId() {
        int ii = super.getCampaignId();
        if (ii == 0) ii = getParent().getCampaignId();
        return ii;
    } //	getCampaignId

    /**
     * Get User2_ID
     *
     * @return User2
     */
    public int getUser1Id() {
        int ii = super.getUser1Id();
        if (ii == 0) ii = getParent().getUser1Id();
        return ii;
    } //	getUser1Id

    /**
     * Get User2_ID
     *
     * @return User2
     */
    public int getUser2Id() {
        int ii = super.getUser2Id();
        if (ii == 0) ii = getParent().getUser2Id();
        return ii;
    } //	getUser2Id

    /**
     * Get AD_OrgTrx_ID
     *
     * @return trx org
     */
    public int getTransactionOrganizationId() {
        int ii = super.getTransactionOrganizationId();
        if (ii == 0) ii = getParent().getTransactionOrganizationId();
        return ii;
    } //	getTransactionOrganizationId

    /**
     * ************************************************************************ String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuffer sb =
                new StringBuffer("MDDOrderLine[")
                        .append(getId())
                        .append(",Line=")
                        .append(getLine())
                        .append(",Ordered=")
                        .append(getQtyOrdered())
                        .append(",Delivered=")
                        .append(getQtyDelivered())
                        .append(",Reserved=")
                        .append(getQtyReserved())
                        .append("]");
        return sb.toString();
    } //	toString

    /**
     * Add to Description
     *
     * @param description text
     */
    public void addDescription(String description) {
        String desc = getDescription();
        if (desc == null) setDescription(description);
        else setDescription(desc + " | " + description);
    } //	addDescription

    /**
     * Set C_Charge_ID
     *
     * @param C_Charge_ID charge
     */
    public void setChargeId(int C_Charge_ID) {
        super.setChargeId(C_Charge_ID);
        if (C_Charge_ID > 0) setValueNoCheck("C_UOM_ID", null);
    } //	setChargeId

    /**
     * Set Qty Entered/Ordered. Use this Method if the Line UOM is the Product UOM
     *
     * @param Qty QtyOrdered/Entered
     */
    public void setQty(BigDecimal Qty) {
        super.setQtyEntered(Qty);
        super.setQtyOrdered(getQtyEntered());
    } //	setQty

    /**
     * Set Qty Entered - enforce entered UOM
     *
     * @param QtyEntered
     */
    public void setQtyEntered(BigDecimal QtyEntered) {
        if (QtyEntered != null && getC_UOM_ID() != 0) {
            int precision = MUOM.getPrecision(getCtx(), getC_UOM_ID());
            QtyEntered = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_UP);
        }
        super.setQtyEntered(QtyEntered);
    } //	setQtyEntered

    /**
     * Set Qty Ordered - enforce Product UOM
     *
     * @param QtyOrdered
     */
    public void setQtyOrdered(BigDecimal QtyOrdered) {
        I_M_Product product = getProduct();
        if (QtyOrdered != null && product != null) {
            int precision = product.getUOMPrecision();
            QtyOrdered = QtyOrdered.setScale(precision, BigDecimal.ROUND_HALF_UP);
        }
        super.setQtyOrdered(QtyOrdered);
    } //	setQtyOrdered

    /**
     * ************************************************************************ Before Save
     *
     * @param newRecord
     * @return true if it can be sabed
     */
    protected boolean beforeSave(boolean newRecord) {
        if (newRecord && getParent().isComplete()) {
            log.saveError("ParentComplete", Msg.translate(getCtx(), "DD_OrderLine"));
            return false;
        }
        //	Get Defaults from Parent
    /*if (getBusinessPartnerId() == 0 || getBusinessPartnerLocationId() == 0
    || getWarehouseId() == 0)
    setOrder (getParent());*/
        if (m_M_PriceList_ID == 0) setHeaderInfo(getParent());

        //	R/O Check - Product/Warehouse Change
        if (!newRecord
                && (is_ValueChanged("M_Product_ID")
                || is_ValueChanged("M_Locator_ID")
                || is_ValueChanged("M_LocatorTo_ID"))) {
            if (!canChangeWarehouse()) return false;
        } //	Product Changed

        //	Charge
        if (getChargeId() != 0 && getM_Product_ID() != 0) setM_Product_ID(0);
        //	No Product
        if (getM_Product_ID() == 0) setM_AttributeSetInstance_ID(0);
        //	Product

        //	UOM
        if (getC_UOM_ID() == 0 && (getM_Product_ID() != 0 || getChargeId() != 0)) {
            int C_UOM_ID = MUOM.getDefault_UOM_ID(getCtx());
            if (C_UOM_ID > 0) setC_UOM_ID(C_UOM_ID);
        }
        //	Qty Precision
        if (newRecord || is_ValueChanged("QtyEntered")) setQtyEntered(getQtyEntered());
        if (newRecord || is_ValueChanged("QtyOrdered")) setQtyOrdered(getQtyOrdered());

        //	Qty on instance ASI for SO
        if (m_IsSOTrx
                && getMAttributeSetInstance_ID() != 0
                && (newRecord
                || is_ValueChanged("M_Product_ID")
                || is_ValueChanged("M_AttributeSetInstance_ID")
                || is_ValueChanged("M_Warehouse_ID"))) {
            I_M_Product product = getProduct();
            if (product.isStocked()) {
                int M_AttributeSet_ID = product.getMAttributeSet_ID();
                boolean isInstance = M_AttributeSet_ID != 0;
                if (isInstance) {
                    MAttributeSet mas = MAttributeSet.get(getCtx(), M_AttributeSet_ID);
                    isInstance = mas.isInstanceAttribute();
                }
                //	Max
                if (isInstance) {
                    MLocator locator_from = MLocator.get(getCtx(), getM_Locator_ID());
                    MStorageOnHand[] storages =
                            MStorageOnHand.getWarehouse(
                                    getCtx(),
                                    locator_from.getWarehouseId(),
                                    getM_Product_ID(),
                                    getMAttributeSetInstance_ID(),
                                    null,
                                    true,
                                    false,
                                    0,
                                    null);
                    BigDecimal qty = Env.ZERO;
                    for (int i = 0; i < storages.length; i++) {
                        if (storages[i].getMAttributeSetInstance_ID() == getMAttributeSetInstance_ID())
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

        //	Get Line No
        if (getLine() == 0) {
            String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM C_OrderLine WHERE C_Order_ID=?";
            int ii = getSQLValue(sql, getDD_Order_ID());
            setLine(ii);
        }

        return true;
    } //	beforeSave

    /**
     * Before Delete
     *
     * @return true if it can be deleted
     */
    protected boolean beforeDelete() {
        //	R/O Check - Something delivered. etc.
        if (Env.ZERO.compareTo(getQtyDelivered()) != 0) {
            log.saveError(
                    "DeleteError", Msg.translate(getCtx(), "QtyDelivered") + "=" + getQtyDelivered());
            return false;
        }
        if (Env.ZERO.compareTo(getQtyReserved()) != 0) {
            //	For PO should be On Order
            log.saveError("DeleteError", Msg.translate(getCtx(), "QtyReserved") + "=" + getQtyReserved());
            return false;
        }
        return true;
    } //	beforeDelete

    /**
     * After Save
     *
     * @param newRecord new
     * @param success   success
     * @return saved
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;

        return true;
    } //	afterSave

    /**
     * After Delete
     *
     * @param success success
     * @return deleted
     */
    protected boolean afterDelete(boolean success) {
        if (!success) return success;

        return true;
    } //	afterDelete

} //	MDDOrderLine
