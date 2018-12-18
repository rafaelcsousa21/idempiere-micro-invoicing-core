package org.compiere.accounting;

import org.compiere.model.I_C_OrderLandedCostAllocation;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_OrderLandedCostAllocation
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_OrderLandedCostAllocation extends PO
    implements I_C_OrderLandedCostAllocation, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_OrderLandedCostAllocation(
      Properties ctx, int C_OrderLandedCostAllocation_ID, String trxName) {
    super(ctx, C_OrderLandedCostAllocation_ID, trxName);
    /**
     * if (C_OrderLandedCostAllocation_ID == 0) { setAmt (Env.ZERO); setBase (Env.ZERO);
     * setC_OrderLandedCostAllocation_ID (0); setC_OrderLandedCost_ID (0); setC_OrderLine_ID (0);
     * setProcessed (false); // N setQty (Env.ZERO); }
     */
  }

  /** Load Constructor */
  public X_C_OrderLandedCostAllocation(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 1 - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb =
        new StringBuffer("X_C_OrderLandedCostAllocation[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Amount.
   *
   * @param Amt Amount
   */
  public void setAmt(BigDecimal Amt) {
    set_Value(COLUMNNAME_Amt, Amt);
  }

  /**
   * Get Amount.
   *
   * @return Amount
   */
  public BigDecimal getAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Amt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Base.
   *
   * @param Base Calculation Base
   */
  public void setBase(BigDecimal Base) {
    set_Value(COLUMNNAME_Base, Base);
  }

  /**
   * Get Base.
   *
   * @return Calculation Base
   */
  public BigDecimal getBase() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Base);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Estimated Landed Cost Allocation.
   *
   * @param C_OrderLandedCostAllocation_ID Estimated Landed Cost Allocation
   */
  public void setC_OrderLandedCostAllocation_ID(int C_OrderLandedCostAllocation_ID) {
    if (C_OrderLandedCostAllocation_ID < 1)
      set_ValueNoCheck(COLUMNNAME_C_OrderLandedCostAllocation_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_C_OrderLandedCostAllocation_ID,
          Integer.valueOf(C_OrderLandedCostAllocation_ID));
  }

  /**
   * Get Estimated Landed Cost Allocation.
   *
   * @return Estimated Landed Cost Allocation
   */
  public int getC_OrderLandedCostAllocation_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_OrderLandedCostAllocation_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_OrderLandedCostAllocation_UU.
   *
   * @param C_OrderLandedCostAllocation_UU C_OrderLandedCostAllocation_UU
   */
  public void setC_OrderLandedCostAllocation_UU(String C_OrderLandedCostAllocation_UU) {
    set_Value(COLUMNNAME_C_OrderLandedCostAllocation_UU, C_OrderLandedCostAllocation_UU);
  }

  /**
   * Get C_OrderLandedCostAllocation_UU.
   *
   * @return C_OrderLandedCostAllocation_UU
   */
  public String getC_OrderLandedCostAllocation_UU() {
    return (String) get_Value(COLUMNNAME_C_OrderLandedCostAllocation_UU);
  }

  public org.compiere.model.I_C_OrderLandedCost getC_OrderLandedCost() throws RuntimeException {
    return (org.compiere.model.I_C_OrderLandedCost)
        MTable.get(getCtx(), org.compiere.model.I_C_OrderLandedCost.Table_Name)
            .getPO(getC_OrderLandedCost_ID(), null);
  }

  /**
   * Set Estimated Landed Cost.
   *
   * @param C_OrderLandedCost_ID Estimated Landed Cost
   */
  public void setC_OrderLandedCost_ID(int C_OrderLandedCost_ID) {
    if (C_OrderLandedCost_ID < 1) set_ValueNoCheck(COLUMNNAME_C_OrderLandedCost_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_OrderLandedCost_ID, Integer.valueOf(C_OrderLandedCost_ID));
  }

  /**
   * Get Estimated Landed Cost.
   *
   * @return Estimated Landed Cost
   */
  public int getC_OrderLandedCost_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_OrderLandedCost_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException {
    return (org.compiere.model.I_C_OrderLine)
        MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
            .getPO(getC_OrderLine_ID(), null);
  }

  /**
   * Set Sales Order Line.
   *
   * @param C_OrderLine_ID Sales Order Line
   */
  public void setC_OrderLine_ID(int C_OrderLine_ID) {
    if (C_OrderLine_ID < 1) set_ValueNoCheck(COLUMNNAME_C_OrderLine_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
  }

  /**
   * Get Sales Order Line.
   *
   * @return Sales Order Line
   */
  public int getC_OrderLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_OrderLine_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
  }

  /**
   * Get Processed.
   *
   * @return The document has been processed
   */
  public boolean isProcessed() {
    Object oo = get_Value(COLUMNNAME_Processed);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Quantity.
   *
   * @param Qty Quantity
   */
  public void setQty(BigDecimal Qty) {
    set_Value(COLUMNNAME_Qty, Qty);
  }

  /**
   * Get Quantity.
   *
   * @return Quantity
   */
  public BigDecimal getQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Qty);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  @Override
  public int getTableId() {
    return I_C_OrderLandedCostAllocation.Table_ID;
  }
}
