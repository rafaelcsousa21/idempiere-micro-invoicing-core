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
  /** */
  private static final long serialVersionUID = -8878804332001384969L;

    /** Logger */
  @SuppressWarnings("unused")
  private static CLogger s_log = CLogger.getCLogger(MDDOrderLine.class);

  /**
   * ************************************************************************ Default Constructor
   *
   * @param ctx context
   * @param C_OrderLine_ID order line to load
   * @param trxName trx name
   */
  public MDDOrderLine(Properties ctx, int C_OrderLine_ID, String trxName) {
    super(ctx, C_OrderLine_ID, trxName);
    if (C_OrderLine_ID == 0) {
      //	setC_Order_ID (0);
      //	setLine (0);
      //	setM_Warehouse_ID (0);	// @M_Warehouse_ID@
      //	setC_BPartner_ID(0);
      //	setC_BPartner_Location_ID (0);	// @C_BPartner_Location_ID@
      //	setC_Currency_ID (0);	// @C_Currency_ID@
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

  /**
   * Parent Constructor. ol.setM_Product_ID(wbl.getM_Product_ID());
   * ol.setQtyOrdered(wbl.getQuantity()); ol.setPrice(); ol.setPriceActual(wbl.getPrice());
   * ol.setTax(); ol.saveEx();
   *
   * @param order parent order
   */
  public MDDOrderLine(MDDOrder order) {
    this(order.getCtx(), 0, null);
    if (order.getId() == 0) throw new IllegalArgumentException("Header not saved");
    setDD_Order_ID(order.getDD_Order_ID()); // 	parent
    setOrder(order);
  } //	MDDOrderLine

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set record
   * @param trxName transaction
   */
  public MDDOrderLine(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MDDOrderLine

  private int m_M_PriceList_ID = 0;
  //
  private boolean m_IsSOTrx = true;

  /** Cached Currency Precision */
  // private Integer			m_precision = null;
  /** Product */
  private I_M_Product m_product = null;
  /** Parent */
  private MDDOrder m_parent = null;

  /**
   * Set Defaults from Order. Does not set Parent !!
   *
   * @param order order
   */
  public void setOrder(MDDOrder order) {
    setClientOrg(order);
    /*setC_BPartner_ID(order.getC_BPartner_ID());
    setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());*/
    // setM_Warehouse_ID(order.getM_Warehouse_ID());
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
    if (m_parent == null) m_parent = new MDDOrder(getCtx(), getDD_Order_ID(), null);
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
    set_Value("M_AttributeSetInstance_ID", new Integer(0));
    else super.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
  } //	setM_AttributeSetInstance_ID

  /**
   * Set Warehouse
   *
   * @param M_Warehouse_ID warehouse
   */
  /*public void setM_Warehouse_ID (int M_Warehouse_ID)
  {
  	if (getM_Warehouse_ID() > 0
  		&& getM_Warehouse_ID() != M_Warehouse_ID
  		&& !canChangeWarehouse())
  		log.severe("Ignored - Already Delivered/Invoiced/Reserved");
  	else
  		super.setM_Warehouse_ID (M_Warehouse_ID);
  }	//	setM_Warehouse_ID
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
  public int getC_Project_ID() {
    int ii = super.getC_Project_ID();
    if (ii == 0) ii = getParent().getC_Project_ID();
    return ii;
  } //	getC_Project_ID

  /**
   * Get C_Activity_ID
   *
   * @return Activity
   */
  public int getC_Activity_ID() {
    int ii = super.getC_Activity_ID();
    if (ii == 0) ii = getParent().getC_Activity_ID();
    return ii;
  } //	getC_Activity_ID

  /**
   * Get C_Campaign_ID
   *
   * @return Campaign
   */
  public int getC_Campaign_ID() {
    int ii = super.getC_Campaign_ID();
    if (ii == 0) ii = getParent().getC_Campaign_ID();
    return ii;
  } //	getC_Campaign_ID

  /**
   * Get User2_ID
   *
   * @return User2
   */
  public int getUser1_ID() {
    int ii = super.getUser1_ID();
    if (ii == 0) ii = getParent().getUser1_ID();
    return ii;
  } //	getUser1_ID

  /**
   * Get User2_ID
   *
   * @return User2
   */
  public int getUser2_ID() {
    int ii = super.getUser2_ID();
    if (ii == 0) ii = getParent().getUser2_ID();
    return ii;
  } //	getUser2_ID

  /**
   * Get AD_OrgTrx_ID
   *
   * @return trx org
   */
  public int getAD_OrgTrx_ID() {
    int ii = super.getAD_OrgTrx_ID();
    if (ii == 0) ii = getParent().getAD_OrgTrx_ID();
    return ii;
  } //	getAD_OrgTrx_ID

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
  public void setC_Charge_ID(int C_Charge_ID) {
    super.setC_Charge_ID(C_Charge_ID);
    if (C_Charge_ID > 0) set_ValueNoCheck("C_UOM_ID", null);
  } //	setC_Charge_ID

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
    /*if (getC_BPartner_ID() == 0 || getC_BPartner_Location_ID() == 0
    || getM_Warehouse_ID() == 0)
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
    if (getC_Charge_ID() != 0 && getM_Product_ID() != 0) setM_Product_ID(0);
    //	No Product
    if (getM_Product_ID() == 0) setM_AttributeSetInstance_ID(0);
    //	Product

    //	UOM
    if (getC_UOM_ID() == 0 && (getM_Product_ID() != 0 || getC_Charge_ID() != 0)) {
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
                  locator_from.getM_Warehouse_ID(),
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
      int ii = getSQLValue(null, sql, getDD_Order_ID());
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
   * @param success success
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
