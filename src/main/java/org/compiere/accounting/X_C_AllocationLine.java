package org.compiere.accounting;

import org.compiere.model.I_C_AllocationLine;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_AllocationLine
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_AllocationLine extends PO implements I_C_AllocationLine, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_AllocationLine(Properties ctx, int C_AllocationLine_ID, String trxName) {
    super(ctx, C_AllocationLine_ID, trxName);
    /**
     * if (C_AllocationLine_ID == 0) { setAmount (Env.ZERO); setC_AllocationHdr_ID (0);
     * setC_AllocationLine_ID (0); setDiscountAmt (Env.ZERO); setWriteOffAmt (Env.ZERO); }
     */
  }

  /** Load Constructor */
  public X_C_AllocationLine(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_AllocationLine[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Amount.
   *
   * @param Amount Amount in a defined currency
   */
  public void setAmount(BigDecimal Amount) {
    set_ValueNoCheck(COLUMNNAME_Amount, Amount);
  }

  /**
   * Get Amount.
   *
   * @return Amount in a defined currency
   */
  public BigDecimal getAmount() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Amount);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Set Allocation.
   *
   * @param C_AllocationHdr_ID Payment allocation
   */
  public void setC_AllocationHdr_ID(int C_AllocationHdr_ID) {
    if (C_AllocationHdr_ID < 1) set_ValueNoCheck(COLUMNNAME_C_AllocationHdr_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_AllocationHdr_ID, Integer.valueOf(C_AllocationHdr_ID));
  }

  /**
   * Get Allocation.
   *
   * @return Payment allocation
   */
  public int getC_AllocationHdr_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_AllocationHdr_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Allocation Line.
   *
   * @return Allocation Line
   */
  public int getC_AllocationLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_AllocationLine_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Business Partner .
   *
   * @param C_BPartner_ID Identifies a Business Partner
   */
  public void setC_BPartner_ID(int C_BPartner_ID) {
    if (C_BPartner_ID < 1) set_ValueNoCheck(COLUMNNAME_C_BPartner_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
  }

  /**
   * Get Business Partner .
   *
   * @return Identifies a Business Partner
   */
  public int getC_BPartner_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BPartner_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Cash Journal Line.
   *
   * @param C_CashLine_ID Cash Journal Line
   */
  public void setC_CashLine_ID(int C_CashLine_ID) {
    if (C_CashLine_ID < 1) set_ValueNoCheck(COLUMNNAME_C_CashLine_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_CashLine_ID, Integer.valueOf(C_CashLine_ID));
  }

  /**
   * Get Cash Journal Line.
   *
   * @return Cash Journal Line
   */
  public int getC_CashLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_CashLine_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Charge.
   *
   * @return Additional document charges
   */
  public int getC_Charge_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Charge_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException {
    return (org.compiere.model.I_C_Invoice)
        MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
            .getPO(getC_Invoice_ID(), null);
  }

  /**
   * Set Invoice.
   *
   * @param C_Invoice_ID Invoice Identifier
   */
  public void setC_Invoice_ID(int C_Invoice_ID) {
    if (C_Invoice_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Invoice_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
  }

  /**
   * Get Invoice.
   *
   * @return Invoice Identifier
   */
  public int getC_Invoice_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Invoice_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), String.valueOf(getC_Invoice_ID()));
  }

    /**
   * Set Order.
   *
   * @param C_Order_ID Order
   */
  public void setC_Order_ID(int C_Order_ID) {
    if (C_Order_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Order_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
  }

  /**
   * Get Order.
   *
   * @return Order
   */
  public int getC_Order_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Order_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException {
    return (org.compiere.model.I_C_Payment)
        MTable.get(getCtx(), org.compiere.model.I_C_Payment.Table_Name)
            .getPO(getC_Payment_ID(), null);
  }

  /**
   * Set Payment.
   *
   * @param C_Payment_ID Payment identifier
   */
  public void setC_Payment_ID(int C_Payment_ID) {
    if (C_Payment_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Payment_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
  }

  /**
   * Get Payment.
   *
   * @return Payment identifier
   */
  public int getC_Payment_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Payment_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Discount Amount.
   *
   * @param DiscountAmt Calculated amount of discount
   */
  public void setDiscountAmt(BigDecimal DiscountAmt) {
    set_ValueNoCheck(COLUMNNAME_DiscountAmt, DiscountAmt);
  }

  /**
   * Get Discount Amount.
   *
   * @return Calculated amount of discount
   */
  public BigDecimal getDiscountAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_DiscountAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Set Over/Under Payment.
   *
   * @param OverUnderAmt Over-Payment (unallocated) or Under-Payment (partial payment) Amount
   */
  public void setOverUnderAmt(BigDecimal OverUnderAmt) {
    set_Value(COLUMNNAME_OverUnderAmt, OverUnderAmt);
  }

  /**
   * Get Over/Under Payment.
   *
   * @return Over-Payment (unallocated) or Under-Payment (partial payment) Amount
   */
  public BigDecimal getOverUnderAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_OverUnderAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Write-off Amount.
   *
   * @param WriteOffAmt Amount to write-off
   */
  public void setWriteOffAmt(BigDecimal WriteOffAmt) {
    set_ValueNoCheck(COLUMNNAME_WriteOffAmt, WriteOffAmt);
  }

  /**
   * Get Write-off Amount.
   *
   * @return Amount to write-off
   */
  public BigDecimal getWriteOffAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_WriteOffAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  @Override
  public int getTableId() {
    return I_C_AllocationLine.Table_ID;
  }
}
