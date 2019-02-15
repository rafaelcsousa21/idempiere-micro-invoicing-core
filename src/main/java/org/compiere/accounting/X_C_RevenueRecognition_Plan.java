package org.compiere.accounting;

import org.compiere.model.I_C_RevenueRecognition_Plan;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_RevenueRecognition_Plan
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_RevenueRecognition_Plan extends PO
    implements I_C_RevenueRecognition_Plan, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_RevenueRecognition_Plan(
      Properties ctx, int C_RevenueRecognition_Plan_ID) {
    super(ctx, C_RevenueRecognition_Plan_ID);
    /**
     * if (C_RevenueRecognition_Plan_ID == 0) { setC_AcctSchema_ID (0); setC_Currency_ID (0);
     * setC_InvoiceLine_ID (0); setC_RevenueRecognition_ID (0); setC_RevenueRecognition_Plan_ID (0);
     * setP_Revenue_Acct (0); setRecognizedAmt (Env.ZERO); setTotalAmt (Env.ZERO);
     * setUnEarnedRevenue_Acct (0); }
     */
  }

  /** Load Constructor */
  public X_C_RevenueRecognition_Plan(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  }

  /**
   * AccessLevel
   *
   * @return 1 - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  @Override
  public int getTableId() {
    return Table_ID;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_RevenueRecognition_Plan[").append(getId()).append("]");
    return sb.toString();
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

    /**
   * Set Currency.
   *
   * @param C_Currency_ID The Currency for this record
   */
  public void setC_Currency_ID(int C_Currency_ID) {
    if (C_Currency_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Currency_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
  }

  /**
   * Get Currency.
   *
   * @return The Currency for this record
   */
  public int getC_Currency_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Currency_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Invoice Line.
   *
   * @param C_InvoiceLine_ID Invoice Detail Line
   */
  public void setC_InvoiceLine_ID(int C_InvoiceLine_ID) {
    if (C_InvoiceLine_ID < 1) set_ValueNoCheck(COLUMNNAME_C_InvoiceLine_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
  }

  /**
   * Get Invoice Line.
   *
   * @return Invoice Detail Line
   */
  public int getC_InvoiceLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_InvoiceLine_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Revenue Recognition.
   *
   * @param C_RevenueRecognition_ID Method for recording revenue
   */
  public void setC_RevenueRecognition_ID(int C_RevenueRecognition_ID) {
    if (C_RevenueRecognition_ID < 1) set_ValueNoCheck(COLUMNNAME_C_RevenueRecognition_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_C_RevenueRecognition_ID, Integer.valueOf(C_RevenueRecognition_ID));
  }

  /**
   * Get Revenue Recognition.
   *
   * @return Method for recording revenue
   */
  public int getC_RevenueRecognition_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_RevenueRecognition_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Product Revenue.
   *
   * @param P_Revenue_Acct Account for Product Revenue (Sales Account)
   */
  public void setP_Revenue_Acct(int P_Revenue_Acct) {
    set_ValueNoCheck(COLUMNNAME_P_Revenue_Acct, Integer.valueOf(P_Revenue_Acct));
  }

  /**
   * Get Product Revenue.
   *
   * @return Account for Product Revenue (Sales Account)
   */
  public int getP_Revenue_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_P_Revenue_Acct);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Recognized Amount.
   *
   * @param RecognizedAmt Recognized Amount
   */
  public void setRecognizedAmt(BigDecimal RecognizedAmt) {
    set_ValueNoCheck(COLUMNNAME_RecognizedAmt, RecognizedAmt);
  }

    /**
   * Set Total Amount.
   *
   * @param TotalAmt Total Amount
   */
  public void setTotalAmt(BigDecimal TotalAmt) {
    set_ValueNoCheck(COLUMNNAME_TotalAmt, TotalAmt);
  }

    /**
   * Set Unearned Revenue.
   *
   * @param UnEarnedRevenue_Acct Account for unearned revenue
   */
  public void setUnEarnedRevenue_Acct(int UnEarnedRevenue_Acct) {
    set_ValueNoCheck(COLUMNNAME_UnEarnedRevenue_Acct, Integer.valueOf(UnEarnedRevenue_Acct));
  }

  /**
   * Get Unearned Revenue.
   *
   * @return Account for unearned revenue
   */
  public int getUnEarnedRevenue_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_UnEarnedRevenue_Acct);
    if (ii == null) return 0;
    return ii;
  }
}
