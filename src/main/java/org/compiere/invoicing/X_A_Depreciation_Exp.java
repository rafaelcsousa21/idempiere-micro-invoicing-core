package org.compiere.invoicing;

import org.compiere.model.I_A_Depreciation_Exp;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for A_Depreciation_Exp
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Depreciation_Exp extends PO implements I_A_Depreciation_Exp, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_A_Depreciation_Exp(Properties ctx, int A_Depreciation_Exp_ID, String trxName) {
    super(ctx, A_Depreciation_Exp_ID, trxName);
  }

  /** Load Constructor */
  public X_A_Depreciation_Exp(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 3 - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  @Override
  public int getTableId() {
    return Table_ID;
  }

  public String toString() {
    return "X_A_Depreciation_Exp[" + getId() + "]";
  }

    /**
   * Set A_Account_Number_Acct.
   *
   * @param A_Account_Number_Acct A_Account_Number_Acct
   */
  public void setA_Account_Number_Acct(int A_Account_Number_Acct) {
    set_Value(COLUMNNAME_A_Account_Number_Acct, A_Account_Number_Acct);
  }

  /**
   * Get A_Account_Number_Acct.
   *
   * @return A_Account_Number_Acct
   */
  public int getA_Account_Number_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Account_Number_Acct);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Accumulated Depreciation.
   *
   * @param A_Accumulated_Depr Accumulated Depreciation
   */
  public void setA_Accumulated_Depr(BigDecimal A_Accumulated_Depr) {
    set_Value(COLUMNNAME_A_Accumulated_Depr, A_Accumulated_Depr);
  }

  /**
   * Get Accumulated Depreciation.
   *
   * @return Accumulated Depreciation
   */
  public BigDecimal getA_Accumulated_Depr() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_A_Accumulated_Depr);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Accumulated Depreciation (delta).
   *
   * @param A_Accumulated_Depr_Delta Accumulated Depreciation (delta)
   */
  public void setA_Accumulated_Depr_Delta(BigDecimal A_Accumulated_Depr_Delta) {
    set_Value(COLUMNNAME_A_Accumulated_Depr_Delta, A_Accumulated_Depr_Delta);
  }

    /**
   * Set Accumulated Depreciation (fiscal).
   *
   * @param A_Accumulated_Depr_F Accumulated Depreciation (fiscal)
   */
  public void setA_Accumulated_Depr_F(BigDecimal A_Accumulated_Depr_F) {
    set_Value(COLUMNNAME_A_Accumulated_Depr_F, A_Accumulated_Depr_F);
  }

  /**
   * Get Accumulated Depreciation (fiscal).
   *
   * @return Accumulated Depreciation (fiscal)
   */
  public BigDecimal getA_Accumulated_Depr_F() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_A_Accumulated_Depr_F);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Accumulated Depreciation - fiscal (delta).
   *
   * @param A_Accumulated_Depr_F_Delta Accumulated Depreciation - fiscal (delta)
   */
  public void setA_Accumulated_Depr_F_Delta(BigDecimal A_Accumulated_Depr_F_Delta) {
    set_Value(COLUMNNAME_A_Accumulated_Depr_F_Delta, A_Accumulated_Depr_F_Delta);
  }

    /**
   * Get Asset Addition.
   *
   * @return Asset Addition
   */
  public int getA_Asset_Addition_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_Addition_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Asset Cost.
   *
   * @param A_Asset_Cost Asset Cost
   */
  public void setA_Asset_Cost(BigDecimal A_Asset_Cost) {
    set_Value(COLUMNNAME_A_Asset_Cost, A_Asset_Cost);
  }

    /**
   * Get Asset Disposed.
   *
   * @return Asset Disposed
   */
  public int getA_Asset_Disposed_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_Disposed_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Asset.
   *
   * @param A_Asset_ID Asset used internally or by customers
   */
  public void setA_Asset_ID(int A_Asset_ID) {
    if (A_Asset_ID < 1) set_Value(COLUMNNAME_A_Asset_ID, null);
    else set_Value(COLUMNNAME_A_Asset_ID, A_Asset_ID);
  }

  /**
   * Get Asset.
   *
   * @return Asset used internally or by customers
   */
  public int getA_Asset_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Remaining Amt.
   *
   * @param A_Asset_Remaining Remaining Amt
   */
  public void setA_Asset_Remaining(BigDecimal A_Asset_Remaining) {
    set_Value(COLUMNNAME_A_Asset_Remaining, A_Asset_Remaining);
  }

    /**
   * Set Remaining Amt (fiscal).
   *
   * @param A_Asset_Remaining_F Remaining Amt (fiscal)
   */
  public void setA_Asset_Remaining_F(BigDecimal A_Asset_Remaining_F) {
    set_Value(COLUMNNAME_A_Asset_Remaining_F, A_Asset_Remaining_F);
  }

    /**
   * Get Depreciation Entry.
   *
   * @return Depreciation Entry
   */
  public int getA_Depreciation_Entry_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_Entry_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get A_Depreciation_Exp_ID.
   *
   * @return A_Depreciation_Exp_ID
   */
  public int getA_Depreciation_Exp_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_Exp_ID);
    if (ii == null) return 0;
    return ii;
  }

    /** Depreciation = DEP */
  public static final String A_ENTRY_TYPE_Depreciation = "DEP";

    /**
   * Set Entry Type.
   *
   * @param A_Entry_Type Entry Type
   */
  public void setA_Entry_Type(String A_Entry_Type) {

    set_Value(COLUMNNAME_A_Entry_Type, A_Entry_Type);
  }

  /**
   * Get Entry Type.
   *
   * @return Entry Type
   */
  public String getA_Entry_Type() {
    return (String) get_Value(COLUMNNAME_A_Entry_Type);
  }

  /**
   * Set Asset Period.
   *
   * @param A_Period Asset Period
   */
  public void setA_Period(int A_Period) {
    set_Value(COLUMNNAME_A_Period, A_Period);
  }

  /**
   * Get Asset Period.
   *
   * @return Asset Period
   */
  public int getA_Period() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Period);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Account (credit).
   *
   * @param CR_Account_ID Account used
   */
  public void setCR_Account_ID(int CR_Account_ID) {
    if (CR_Account_ID < 1) set_Value(COLUMNNAME_CR_Account_ID, null);
    else set_Value(COLUMNNAME_CR_Account_ID, CR_Account_ID);
  }

  /**
   * Get Account (credit).
   *
   * @return Account used
   */
  public int getCR_Account_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_CR_Account_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Account Date.
   *
   * @param DateAcct Accounting Date
   */
  public void setDateAcct(Timestamp DateAcct) {
    set_Value(COLUMNNAME_DateAcct, DateAcct);
  }

  /**
   * Get Account Date.
   *
   * @return Accounting Date
   */
  public Timestamp getDateAcct() {
    return (Timestamp) get_Value(COLUMNNAME_DateAcct);
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
   * Set Account (debit).
   *
   * @param DR_Account_ID Account used
   */
  public void setDR_Account_ID(int DR_Account_ID) {
    if (DR_Account_ID < 1) set_Value(COLUMNNAME_DR_Account_ID, null);
    else set_Value(COLUMNNAME_DR_Account_ID, DR_Account_ID);
  }

  /**
   * Get Account (debit).
   *
   * @return Account used
   */
  public int getDR_Account_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_DR_Account_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Expense.
   *
   * @param Expense Expense
   */
  public void setExpense(BigDecimal Expense) {
    set_Value(COLUMNNAME_Expense, Expense);
  }

  /**
   * Get Expense.
   *
   * @return Expense
   */
  public BigDecimal getExpense() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Expense);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Expense (fiscal).
   *
   * @param Expense_F Expense (fiscal)
   */
  public void setExpense_F(BigDecimal Expense_F) {
    set_Value(COLUMNNAME_Expense_F, Expense_F);
  }

  /**
   * Get Expense (fiscal).
   *
   * @return Expense (fiscal)
   */
  public BigDecimal getExpense_F() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Expense_F);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Comment/Help.
   *
   * @param Help Comment or Hint
   */
  public void setHelp(String Help) {
    set_Value(COLUMNNAME_Help, Help);
  }

    /**
   * Set Depreciate.
   *
   * @param IsDepreciated The asset will be depreciated
   */
  public void setIsDepreciated(boolean IsDepreciated) {
    set_Value(COLUMNNAME_IsDepreciated, IsDepreciated);
  }

    /**
   * Set PostingType.
   *
   * @param PostingType The type of posted amount for the transaction
   */
  public void setPostingType(String PostingType) {

    set_Value(COLUMNNAME_PostingType, PostingType);
  }

  /**
   * Get PostingType.
   *
   * @return The type of posted amount for the transaction
   */
  public String getPostingType() {
    return (String) get_Value(COLUMNNAME_PostingType);
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
   * Set Usable Life - Months.
   *
   * @param UseLifeMonths Months of the usable life of the asset
   */
  public void setUseLifeMonths(int UseLifeMonths) {
    set_Value(COLUMNNAME_UseLifeMonths, UseLifeMonths);
  }

    /**
   * Set Use Life - Months (fiscal).
   *
   * @param UseLifeMonths_F Use Life - Months (fiscal)
   */
  public void setUseLifeMonths_F(int UseLifeMonths_F) {
    set_Value(COLUMNNAME_UseLifeMonths_F, UseLifeMonths_F);
  }

}
