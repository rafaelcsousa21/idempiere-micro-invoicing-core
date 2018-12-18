package org.compiere.accounting;

import org.compiere.model.I_C_AcctSchema_GL;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_AcctSchema_GL
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_AcctSchema_GL extends PO implements I_C_AcctSchema_GL, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_AcctSchema_GL(Properties ctx, int C_AcctSchema_GL_ID, String trxName) {
    super(ctx, C_AcctSchema_GL_ID, trxName);
    /**
     * if (C_AcctSchema_GL_ID == 0) { setC_AcctSchema_ID (0); setCommitmentOffset_Acct (0);
     * setCommitmentOffsetSales_Acct (0); setIntercompanyDueFrom_Acct (0); setIntercompanyDueTo_Acct
     * (0); setPPVOffset_Acct (0); setUseCurrencyBalancing (false); setUseSuspenseBalancing (false);
     * setUseSuspenseError (false); }
     */
  }

  /** Load Constructor */
  public X_C_AcctSchema_GL(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 2 - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_AcctSchema_GL[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set C_AcctSchema_GL_UU.
   *
   * @param C_AcctSchema_GL_UU C_AcctSchema_GL_UU
   */
  public void setC_AcctSchema_GL_UU(String C_AcctSchema_GL_UU) {
    set_Value(COLUMNNAME_C_AcctSchema_GL_UU, C_AcctSchema_GL_UU);
  }

  /**
   * Get C_AcctSchema_GL_UU.
   *
   * @return C_AcctSchema_GL_UU
   */
  public String getC_AcctSchema_GL_UU() {
    return (String) get_Value(COLUMNNAME_C_AcctSchema_GL_UU);
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

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), String.valueOf(getC_AcctSchema_ID()));
  }

  public I_C_ValidCombination getCommitmentOffset_A() throws RuntimeException {
    return (I_C_ValidCombination)
        MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
            .getPO(getCommitmentOffset_Acct(), null);
  }

  /**
   * Set Commitment Offset.
   *
   * @param CommitmentOffset_Acct Budgetary Commitment Offset Account
   */
  public void setCommitmentOffset_Acct(int CommitmentOffset_Acct) {
    set_Value(COLUMNNAME_CommitmentOffset_Acct, Integer.valueOf(CommitmentOffset_Acct));
  }

  /**
   * Get Commitment Offset.
   *
   * @return Budgetary Commitment Offset Account
   */
  public int getCommitmentOffset_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_CommitmentOffset_Acct);
    if (ii == null) return 0;
    return ii;
  }

  public I_C_ValidCombination getCommitmentOffsetSales_A() throws RuntimeException {
    return (I_C_ValidCombination)
        MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
            .getPO(getCommitmentOffsetSales_Acct(), null);
  }

  /**
   * Set Commitment Offset Sales.
   *
   * @param CommitmentOffsetSales_Acct Budgetary Commitment Offset Account for Sales
   */
  public void setCommitmentOffsetSales_Acct(int CommitmentOffsetSales_Acct) {
    set_Value(COLUMNNAME_CommitmentOffsetSales_Acct, Integer.valueOf(CommitmentOffsetSales_Acct));
  }

  /**
   * Get Commitment Offset Sales.
   *
   * @return Budgetary Commitment Offset Account for Sales
   */
  public int getCommitmentOffsetSales_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_CommitmentOffsetSales_Acct);
    if (ii == null) return 0;
    return ii;
  }

  public I_C_ValidCombination getCurrencyBalancing_A() throws RuntimeException {
    return (I_C_ValidCombination)
        MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
            .getPO(getCurrencyBalancing_Acct(), null);
  }

  /**
   * Set Currency Balancing Acct.
   *
   * @param CurrencyBalancing_Acct Account used when a currency is out of balance
   */
  public void setCurrencyBalancing_Acct(int CurrencyBalancing_Acct) {
    set_Value(COLUMNNAME_CurrencyBalancing_Acct, Integer.valueOf(CurrencyBalancing_Acct));
  }

  /**
   * Get Currency Balancing Acct.
   *
   * @return Account used when a currency is out of balance
   */
  public int getCurrencyBalancing_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_CurrencyBalancing_Acct);
    if (ii == null) return 0;
    return ii;
  }

  public I_C_ValidCombination getIntercompanyDueFrom_A() throws RuntimeException {
    return (I_C_ValidCombination)
        MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
            .getPO(getIntercompanyDueFrom_Acct(), null);
  }

  /**
   * Set Intercompany Due From Acct.
   *
   * @param IntercompanyDueFrom_Acct Intercompany Due From / Receivables Account
   */
  public void setIntercompanyDueFrom_Acct(int IntercompanyDueFrom_Acct) {
    set_Value(COLUMNNAME_IntercompanyDueFrom_Acct, Integer.valueOf(IntercompanyDueFrom_Acct));
  }

  /**
   * Get Intercompany Due From Acct.
   *
   * @return Intercompany Due From / Receivables Account
   */
  public int getIntercompanyDueFrom_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_IntercompanyDueFrom_Acct);
    if (ii == null) return 0;
    return ii;
  }

  public I_C_ValidCombination getIntercompanyDueTo_A() throws RuntimeException {
    return (I_C_ValidCombination)
        MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
            .getPO(getIntercompanyDueTo_Acct(), null);
  }

  /**
   * Set Intercompany Due To Acct.
   *
   * @param IntercompanyDueTo_Acct Intercompany Due To / Payable Account
   */
  public void setIntercompanyDueTo_Acct(int IntercompanyDueTo_Acct) {
    set_Value(COLUMNNAME_IntercompanyDueTo_Acct, Integer.valueOf(IntercompanyDueTo_Acct));
  }

  /**
   * Get Intercompany Due To Acct.
   *
   * @return Intercompany Due To / Payable Account
   */
  public int getIntercompanyDueTo_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_IntercompanyDueTo_Acct);
    if (ii == null) return 0;
    return ii;
  }

  public I_C_ValidCombination getPPVOffset_A() throws RuntimeException {
    return (I_C_ValidCombination)
        MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
            .getPO(getPPVOffset_Acct(), null);
  }

  /**
   * Set PPV Offset.
   *
   * @param PPVOffset_Acct Purchase Price Variance Offset Account
   */
  public void setPPVOffset_Acct(int PPVOffset_Acct) {
    set_Value(COLUMNNAME_PPVOffset_Acct, Integer.valueOf(PPVOffset_Acct));
  }

  /**
   * Get PPV Offset.
   *
   * @return Purchase Price Variance Offset Account
   */
  public int getPPVOffset_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PPVOffset_Acct);
    if (ii == null) return 0;
    return ii;
  }

  public I_C_ValidCombination getSuspenseBalancing_A() throws RuntimeException {
    return (I_C_ValidCombination)
        MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
            .getPO(getSuspenseBalancing_Acct(), null);
  }

  /**
   * Set Suspense Balancing Acct.
   *
   * @param SuspenseBalancing_Acct Suspense Balancing Acct
   */
  public void setSuspenseBalancing_Acct(int SuspenseBalancing_Acct) {
    set_Value(COLUMNNAME_SuspenseBalancing_Acct, Integer.valueOf(SuspenseBalancing_Acct));
  }

  /**
   * Get Suspense Balancing Acct.
   *
   * @return Suspense Balancing Acct
   */
  public int getSuspenseBalancing_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_SuspenseBalancing_Acct);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Use Currency Balancing.
   *
   * @param UseCurrencyBalancing Use Currency Balancing
   */
  public void setUseCurrencyBalancing(boolean UseCurrencyBalancing) {
    set_Value(COLUMNNAME_UseCurrencyBalancing, Boolean.valueOf(UseCurrencyBalancing));
  }

  /**
   * Get Use Currency Balancing.
   *
   * @return Use Currency Balancing
   */
  public boolean isUseCurrencyBalancing() {
    Object oo = get_Value(COLUMNNAME_UseCurrencyBalancing);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Use Suspense Balancing.
   *
   * @param UseSuspenseBalancing Use Suspense Balancing
   */
  public void setUseSuspenseBalancing(boolean UseSuspenseBalancing) {
    set_Value(COLUMNNAME_UseSuspenseBalancing, Boolean.valueOf(UseSuspenseBalancing));
  }

  /**
   * Get Use Suspense Balancing.
   *
   * @return Use Suspense Balancing
   */
  public boolean isUseSuspenseBalancing() {
    Object oo = get_Value(COLUMNNAME_UseSuspenseBalancing);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Use Suspense Error.
   *
   * @param UseSuspenseError Use Suspense Error
   */
  public void setUseSuspenseError(boolean UseSuspenseError) {
    set_Value(COLUMNNAME_UseSuspenseError, Boolean.valueOf(UseSuspenseError));
  }

  /**
   * Get Use Suspense Error.
   *
   * @return Use Suspense Error
   */
  public boolean isUseSuspenseError() {
    Object oo = get_Value(COLUMNNAME_UseSuspenseError);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  @Override
  public int getTableId() {
    return I_C_AcctSchema_GL.Table_ID;
  }
}
