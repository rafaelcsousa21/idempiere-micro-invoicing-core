package org.compiere.accounting;

import org.compiere.model.I_C_PaymentAllocate;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_PaymentAllocate
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_PaymentAllocate extends PO implements I_C_PaymentAllocate, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_PaymentAllocate(Properties ctx, int C_PaymentAllocate_ID, String trxName) {
    super(ctx, C_PaymentAllocate_ID, trxName);
    /**
     * if (C_PaymentAllocate_ID == 0) { setAmount (Env.ZERO); setC_Invoice_ID (0);
     * setC_PaymentAllocate_ID (0); setC_Payment_ID (0); setDiscountAmt (Env.ZERO); setOverUnderAmt
     * (Env.ZERO); setWriteOffAmt (Env.ZERO); }
     */
  }

  /** Load Constructor */
  public X_C_PaymentAllocate(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_PaymentAllocate[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Amount.
   *
   * @param Amount Amount in a defined currency
   */
  public void setAmount(BigDecimal Amount) {
    set_Value(COLUMNNAME_Amount, Amount);
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
   * Set Allocation Line.
   *
   * @param C_AllocationLine_ID Allocation Line
   */
  public void setC_AllocationLine_ID(int C_AllocationLine_ID) {
    if (C_AllocationLine_ID < 1) set_Value(COLUMNNAME_C_AllocationLine_ID, null);
    else set_Value(COLUMNNAME_C_AllocationLine_ID, Integer.valueOf(C_AllocationLine_ID));
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
   * Set Invoice.
   *
   * @param C_Invoice_ID Invoice Identifier
   */
  public void setC_Invoice_ID(int C_Invoice_ID) {
    if (C_Invoice_ID < 1) set_Value(COLUMNNAME_C_Invoice_ID, null);
    else set_Value(COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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
    set_Value(COLUMNNAME_DiscountAmt, DiscountAmt);
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
   * Set Invoice Amt.
   *
   * @param InvoiceAmt Invoice Amt
   */
  public void setInvoiceAmt(BigDecimal InvoiceAmt) {
    set_Value(COLUMNNAME_InvoiceAmt, InvoiceAmt);
  }

  /**
   * Get Invoice Amt.
   *
   * @return Invoice Amt
   */
  public BigDecimal getInvoiceAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_InvoiceAmt);
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
    set_Value(COLUMNNAME_WriteOffAmt, WriteOffAmt);
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
    return I_C_PaymentAllocate.Table_ID;
  }
}
