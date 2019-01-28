package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_AcctSchema_GL;
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
  public X_C_AcctSchema_GL(Properties ctx, Row row) {
    super(ctx, row);
  } //	MAcctSchemaGL

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

    @Override
  public int getTableId() {
    return I_C_AcctSchema_GL.Table_ID;
  }
}
