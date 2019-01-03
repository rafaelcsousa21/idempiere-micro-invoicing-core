package org.compiere.accounting;

import org.compiere.model.I_C_PaySelection;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_PaySelection
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_PaySelection extends BasePOName implements I_C_PaySelection, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_PaySelection(Properties ctx, int C_PaySelection_ID, String trxName) {
    super(ctx, C_PaySelection_ID, trxName);
    /**
     * if (C_PaySelection_ID == 0) { setC_BankAccount_ID (0); setC_PaySelection_ID (0);
     * setIsApproved (false); setName (null); // @#Date@ setPayDate (new Timestamp(
     * System.currentTimeMillis() )); // @#Date@ setProcessed (false); setProcessing (false);
     * setTotalAmt (Env.ZERO); }
     */
  }

  /** Load Constructor */
  public X_C_PaySelection(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_PaySelection[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException {
    return (org.compiere.model.I_C_BankAccount)
        MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
            .getPO(getC_BankAccount_ID(), null);
  }

  /**
   * Set Bank Account.
   *
   * @param C_BankAccount_ID Account at the Bank
   */
  public void setC_BankAccount_ID(int C_BankAccount_ID) {
    if (C_BankAccount_ID < 1) set_Value(COLUMNNAME_C_BankAccount_ID, null);
    else set_Value(COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
  }

  /**
   * Get Bank Account.
   *
   * @return Account at the Bank
   */
  public int getC_BankAccount_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BankAccount_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Payment Selection.
   *
   * @param C_PaySelection_ID Payment Selection
   */
  public void setC_PaySelection_ID(int C_PaySelection_ID) {
    if (C_PaySelection_ID < 1) set_ValueNoCheck(COLUMNNAME_C_PaySelection_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_PaySelection_ID, Integer.valueOf(C_PaySelection_ID));
  }

  /**
   * Get Payment Selection.
   *
   * @return Payment Selection
   */
  public int getC_PaySelection_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_PaySelection_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_PaySelection_UU.
   *
   * @param C_PaySelection_UU C_PaySelection_UU
   */
  public void setC_PaySelection_UU(String C_PaySelection_UU) {
    set_Value(COLUMNNAME_C_PaySelection_UU, C_PaySelection_UU);
  }

  /**
   * Get C_PaySelection_UU.
   *
   * @return C_PaySelection_UU
   */
  public String getC_PaySelection_UU() {
    return (String) get_Value(COLUMNNAME_C_PaySelection_UU);
  }

  /**
   * Set Create lines from.
   *
   * @param CreateFrom Process which will generate a new document lines based on an existing
   *     document
   */
  public void setCreateFrom(String CreateFrom) {
    set_Value(COLUMNNAME_CreateFrom, CreateFrom);
  }

  /**
   * Get Create lines from.
   *
   * @return Process which will generate a new document lines based on an existing document
   */
  public String getCreateFrom() {
    return (String) get_Value(COLUMNNAME_CreateFrom);
  }

  /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(COLUMNNAME_Description, Description);
  }

  /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
  }

  /**
   * Set Approved.
   *
   * @param IsApproved Indicates if this document requires approval
   */
  public void setIsApproved(boolean IsApproved) {
    set_Value(COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
  }

  /**
   * Get Approved.
   *
   * @return Indicates if this document requires approval
   */
  public boolean isApproved() {
    Object oo = get_Value(COLUMNNAME_IsApproved);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Payment date.
   *
   * @param PayDate Date Payment made
   */
  public void setPayDate(Timestamp PayDate) {
    set_Value(COLUMNNAME_PayDate, PayDate);
  }

  /**
   * Get Payment date.
   *
   * @return Date Payment made
   */
  public Timestamp getPayDate() {
    return (Timestamp) get_Value(COLUMNNAME_PayDate);
  }

  /**
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Processed);
  }

  /**
   * Get Processed.
   *
   * @return The document has been processed
   */
  public boolean isProcessed() {
    Object oo = get_Value(COLUMNNAME_Processed);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Process Now.
   *
   * @param Processing Process Now
   */
  public void setProcessing(boolean Processing) {
    set_Value(COLUMNNAME_Processing, Processing);
  }

  /**
   * Get Process Now.
   *
   * @return Process Now
   */
  public boolean isProcessing() {
    Object oo = get_Value(COLUMNNAME_Processing);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Total Amount.
   *
   * @param TotalAmt Total Amount
   */
  public void setTotalAmt(BigDecimal TotalAmt) {
    set_Value(COLUMNNAME_TotalAmt, TotalAmt);
  }

  /**
   * Get Total Amount.
   *
   * @return Total Amount
   */
  public BigDecimal getTotalAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_TotalAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  @Override
  public int getTableId() {
    return I_C_PaySelection.Table_ID;
  }
}
