package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_M_Cost;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_Cost
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_Cost extends PO implements I_M_Cost, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_Cost(Properties ctx, int M_Cost_ID, String trxName) {
    super(ctx, M_Cost_ID, trxName);
    /**
     * if (M_Cost_ID == 0) { setC_AcctSchema_ID (0); setCurrentCostPrice (Env.ZERO);
     * setCurrentCostPriceLL (Env.ZERO); setCurrentQty (Env.ZERO); setFutureCostPrice (Env.ZERO);
     * setM_AttributeSetInstance_ID (0); setM_CostElement_ID (0); setM_CostType_ID (0);
     * setM_Product_ID (0); }
     */
  }

  /** Load Constructor */
  public X_M_Cost(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }
  public X_M_Cost(Properties ctx, Row row) {
    super(ctx, row);
  } //	MCost


  /**
   * AccessLevel
   *
   * @return 3 - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_Cost[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException {
    return (org.compiere.model.I_C_AcctSchema)
        MTable.get(getCtx(), org.compiere.model.I_C_AcctSchema.Table_Name)
            .getPO(getC_AcctSchema_ID(), null);
  }

  /**
   * Set Accounting Schema.
   *
   * @param C_AcctSchema_ID Rules for accounting
   */
  public void setC_AcctSchema_ID(int C_AcctSchema_ID) {
    if (C_AcctSchema_ID < 1) set_ValueNoCheck(COLUMNNAME_C_AcctSchema_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
  }

  /**
   * Get Accounting Schema.
   *
   * @return Rules for accounting
   */
  public int getC_AcctSchema_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_AcctSchema_ID);
    if (ii == null) return 0;
    return ii;
  }

  /** CostingMethod AD_Reference_ID=122 */
  public static final int COSTINGMETHOD_AD_Reference_ID = 122;
  /** Standard Costing = S */
  public static final String COSTINGMETHOD_StandardCosting = "S";
  /** Average PO = A */
  public static final String COSTINGMETHOD_AveragePO = "A";
  /** Lifo = L */
  public static final String COSTINGMETHOD_Lifo = "L";
  /** Fifo = F */
  public static final String COSTINGMETHOD_Fifo = "F";
  /** Last PO Price = p */
  public static final String COSTINGMETHOD_LastPOPrice = "p";
  /** Average Invoice = I */
  public static final String COSTINGMETHOD_AverageInvoice = "I";
  /** Last Invoice = i */
  public static final String COSTINGMETHOD_LastInvoice = "i";
  /** User Defined = U */
  public static final String COSTINGMETHOD_UserDefined = "U";
  /** _ = x */
  public static final String COSTINGMETHOD__ = "x";

    /**
   * Set Accumulated Amt.
   *
   * @param CumulatedAmt Total Amount
   */
  public void setCumulatedAmt(BigDecimal CumulatedAmt) {
    set_ValueNoCheck(COLUMNNAME_CumulatedAmt, CumulatedAmt);
  }

  /**
   * Get Accumulated Amt.
   *
   * @return Total Amount
   */
  public BigDecimal getCumulatedAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_CumulatedAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Accumulated Qty.
   *
   * @param CumulatedQty Total Quantity
   */
  public void setCumulatedQty(BigDecimal CumulatedQty) {
    set_ValueNoCheck(COLUMNNAME_CumulatedQty, CumulatedQty);
  }

  /**
   * Get Accumulated Qty.
   *
   * @return Total Quantity
   */
  public BigDecimal getCumulatedQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_CumulatedQty);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Current Cost Price.
   *
   * @param CurrentCostPrice The currently used cost price
   */
  public void setCurrentCostPrice(BigDecimal CurrentCostPrice) {
    set_Value(COLUMNNAME_CurrentCostPrice, CurrentCostPrice);
  }

  /**
   * Get Current Cost Price.
   *
   * @return The currently used cost price
   */
  public BigDecimal getCurrentCostPrice() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_CurrentCostPrice);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Get Current Cost Price Lower Level.
   *
   * @return Current Price Lower Level Is the sum of the costs of the components of this product
   *     manufactured for this level.
   */
  public BigDecimal getCurrentCostPriceLL() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_CurrentCostPriceLL);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Current Quantity.
   *
   * @param CurrentQty Current Quantity
   */
  public void setCurrentQty(BigDecimal CurrentQty) {
    set_Value(COLUMNNAME_CurrentQty, CurrentQty);
  }

  /**
   * Get Current Quantity.
   *
   * @return Current Quantity
   */
  public BigDecimal getCurrentQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_CurrentQty);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Set Future Cost Price.
   *
   * @param FutureCostPrice Future Cost Price
   */
  public void setFutureCostPrice(BigDecimal FutureCostPrice) {
    set_Value(COLUMNNAME_FutureCostPrice, FutureCostPrice);
  }

  /**
   * Get Future Cost Price.
   *
   * @return Future Cost Price
   */
  public BigDecimal getFutureCostPrice() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_FutureCostPrice);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Set Attribute Set Instance.
   *
   * @param M_AttributeSetInstance_ID Product Attribute Set Instance
   */
  public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
    if (M_AttributeSetInstance_ID < 0) set_ValueNoCheck(COLUMNNAME_M_AttributeSetInstance_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
  }

  /**
   * Get Attribute Set Instance.
   *
   * @return Product Attribute Set Instance
   */
  public int getMAttributeSetInstance_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_M_CostElement getM_CostElement() throws RuntimeException {
    return (org.compiere.model.I_M_CostElement)
        MTable.get(getCtx(), org.compiere.model.I_M_CostElement.Table_Name)
            .getPO(getM_CostElement_ID(), null);
  }

  /**
   * Set Cost Element.
   *
   * @param M_CostElement_ID Product Cost Element
   */
  public void setM_CostElement_ID(int M_CostElement_ID) {
    if (M_CostElement_ID < 1) set_ValueNoCheck(COLUMNNAME_M_CostElement_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_CostElement_ID, Integer.valueOf(M_CostElement_ID));
  }

  /**
   * Get Cost Element.
   *
   * @return Product Cost Element
   */
  public int getM_CostElement_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_CostElement_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Cost Type.
   *
   * @param M_CostType_ID Type of Cost (e.g. Current, Plan, Future)
   */
  public void setM_CostType_ID(int M_CostType_ID) {
    if (M_CostType_ID < 1) set_ValueNoCheck(COLUMNNAME_M_CostType_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_CostType_ID, Integer.valueOf(M_CostType_ID));
  }

  /**
   * Get Cost Type.
   *
   * @return Type of Cost (e.g. Current, Plan, Future)
   */
  public int getM_CostType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_CostType_ID);
    if (ii == null) return 0;
    return ii;
  }

    public org.compiere.model.I_M_Product getM_Product() throws RuntimeException {
    return (org.compiere.model.I_M_Product)
        MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
            .getPO(getM_Product_ID(), null);
  }

  /**
   * Set Product.
   *
   * @param M_Product_ID Product, Service, Item
   */
  public void setM_Product_ID(int M_Product_ID) {
    if (M_Product_ID < 1) set_ValueNoCheck(COLUMNNAME_M_Product_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
  }

  /**
   * Get Product.
   *
   * @return Product, Service, Item
   */
  public int getM_Product_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Product_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Percent.
   *
   * @param Percent Percentage
   */
  public void setPercent(int Percent) {
    set_Value(COLUMNNAME_Percent, Integer.valueOf(Percent));
  }

  /**
   * Get Percent.
   *
   * @return Percentage
   */
  public int getPercent() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Percent);
    if (ii == null) return 0;
    return ii;
  }

    @Override
  public int getTableId() {
    return I_M_Cost.Table_ID;
  }
}
