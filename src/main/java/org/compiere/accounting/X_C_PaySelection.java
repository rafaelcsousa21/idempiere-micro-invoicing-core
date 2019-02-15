package org.compiere.accounting;

import org.compiere.model.I_C_PaySelection;
import org.compiere.orm.BasePOName;
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
  public X_C_PaySelection(Properties ctx, int C_PaySelection_ID) {
    super(ctx, C_PaySelection_ID);
    /**
     * if (C_PaySelection_ID == 0) { setC_BankAccount_ID (0); setC_PaySelection_ID (0);
     * setIsApproved (false); setName (null); // @#Date@ setPayDate (new Timestamp(
     * System.currentTimeMillis() )); // @#Date@ setProcessed (false); setProcessing (false);
     * setTotalAmt (Env.ZERO); }
     */
  }

  /** Load Constructor */
  public X_C_PaySelection(Properties ctx, ResultSet rs) {
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

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_PaySelection[").append(getId()).append("]");
    return sb.toString();
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
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(COLUMNNAME_Description, Description);
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
   * Set Total Amount.
   *
   * @param TotalAmt Total Amount
   */
  public void setTotalAmt(BigDecimal TotalAmt) {
    set_Value(COLUMNNAME_TotalAmt, TotalAmt);
  }

    @Override
  public int getTableId() {
    return I_C_PaySelection.Table_ID;
  }
}
