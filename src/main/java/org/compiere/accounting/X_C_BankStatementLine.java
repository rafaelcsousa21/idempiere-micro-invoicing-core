package org.compiere.accounting;

import org.compiere.model.I_C_BankStatementLine;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_BankStatementLine
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_BankStatementLine extends PO implements I_C_BankStatementLine, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_BankStatementLine(Properties ctx, int C_BankStatementLine_ID, String trxName) {
    super(ctx, C_BankStatementLine_ID, trxName);
    /**
     * if (C_BankStatementLine_ID == 0) { setC_BankStatement_ID (0); setC_BankStatementLine_ID (0);
     * setC_Currency_ID (0); // @SQL=SELECT C_Currency_ID FROM C_BankAccount WHERE
     * C_BankAccount_ID=@C_BankAccount_ID@ setChargeAmt (Env.ZERO); setDateAcct (new Timestamp(
     * System.currentTimeMillis() )); // @DateAcct@ setInterestAmt (Env.ZERO); setIsManual (true);
     * // Y setIsReversal (false); setLine (0); // @SQL=SELECT COALESCE(MAX(Line),0)+10 FROM
     * C_BankStatementLine WHERE C_BankStatement_ID=@C_BankStatement_ID@ setProcessed (false);
     * setStatementLineDate (new Timestamp( System.currentTimeMillis() )); // @StatementLineDate@
     * setStmtAmt (Env.ZERO); setTrxAmt (Env.ZERO); setValutaDate (new Timestamp(
     * System.currentTimeMillis() )); // @StatementDate@ }
     */
  }

  /** Load Constructor */
  public X_C_BankStatementLine(Properties ctx, ResultSet rs, String trxName) {
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

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_BankStatementLine[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Set Bank Statement.
   *
   * @param C_BankStatement_ID Bank Statement of account
   */
  public void setC_BankStatement_ID(int C_BankStatement_ID) {
    if (C_BankStatement_ID < 1) set_ValueNoCheck(COLUMNNAME_C_BankStatement_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_BankStatement_ID, Integer.valueOf(C_BankStatement_ID));
  }

  /**
   * Get Bank Statement.
   *
   * @return Bank Statement of account
   */
  public int getC_BankStatement_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BankStatement_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Bank statement line.
   *
   * @return Line on a statement from this Bank
   */
  public int getC_BankStatementLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BankStatementLine_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Business Partner .
   *
   * @param C_BPartner_ID Identifies a Business Partner
   */
  public void setC_BPartner_ID(int C_BPartner_ID) {
    if (C_BPartner_ID < 1) set_Value(COLUMNNAME_C_BPartner_ID, null);
    else set_Value(COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
   * Set Charge.
   *
   * @param C_Charge_ID Additional document charges
   */
  public void setC_Charge_ID(int C_Charge_ID) {
    if (C_Charge_ID < 1) set_Value(COLUMNNAME_C_Charge_ID, null);
    else set_Value(COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
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

    /**
   * Set Currency.
   *
   * @param C_Currency_ID The Currency for this record
   */
  public void setC_Currency_ID(int C_Currency_ID) {
    if (C_Currency_ID < 1) set_Value(COLUMNNAME_C_Currency_ID, null);
    else set_Value(COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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
   * Set Charge amount.
   *
   * @param ChargeAmt Charge Amount
   */
  public void setChargeAmt(BigDecimal ChargeAmt) {
    set_Value(COLUMNNAME_ChargeAmt, ChargeAmt);
  }

  /**
   * Get Charge amount.
   *
   * @return Charge Amount
   */
  public BigDecimal getChargeAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_ChargeAmt);
    if (bd == null) return Env.ZERO;
    return bd;
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
   * Set Payment.
   *
   * @param C_Payment_ID Payment identifier
   */
  public void setC_Payment_ID(int C_Payment_ID) {
    if (C_Payment_ID < 1) set_Value(COLUMNNAME_C_Payment_ID, null);
    else set_Value(COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
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
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
  }

  /**
   * Set EFT Amount.
   *
   * @param EftAmt Electronic Funds Transfer Amount
   */
  public void setEftAmt(BigDecimal EftAmt) {
    set_Value(COLUMNNAME_EftAmt, EftAmt);
  }

    /**
   * Set EFT Check No.
   *
   * @param EftCheckNo Electronic Funds Transfer Check No
   */
  public void setEftCheckNo(String EftCheckNo) {
    set_Value(COLUMNNAME_EftCheckNo, EftCheckNo);
  }

    /**
   * Set EFT Currency.
   *
   * @param EftCurrency Electronic Funds Transfer Currency
   */
  public void setEftCurrency(String EftCurrency) {
    set_Value(COLUMNNAME_EftCurrency, EftCurrency);
  }

    /**
   * Set EFT Memo.
   *
   * @param EftMemo Electronic Funds Transfer Memo
   */
  public void setEftMemo(String EftMemo) {
    set_Value(COLUMNNAME_EftMemo, EftMemo);
  }

    /**
   * Set EFT Payee.
   *
   * @param EftPayee Electronic Funds Transfer Payee information
   */
  public void setEftPayee(String EftPayee) {
    set_Value(COLUMNNAME_EftPayee, EftPayee);
  }

    /**
   * Set EFT Payee Account.
   *
   * @param EftPayeeAccount Electronic Funds Transfer Payee Account Information
   */
  public void setEftPayeeAccount(String EftPayeeAccount) {
    set_Value(COLUMNNAME_EftPayeeAccount, EftPayeeAccount);
  }

    /**
   * Set EFT Reference.
   *
   * @param EftReference Electronic Funds Transfer Reference
   */
  public void setEftReference(String EftReference) {
    set_Value(COLUMNNAME_EftReference, EftReference);
  }

    /**
   * Set EFT Statement Line Date.
   *
   * @param EftStatementLineDate Electronic Funds Transfer Statement Line Date
   */
  public void setEftStatementLineDate(Timestamp EftStatementLineDate) {
    set_Value(COLUMNNAME_EftStatementLineDate, EftStatementLineDate);
  }

    /**
   * Set EFT Trx ID.
   *
   * @param EftTrxID Electronic Funds Transfer Transaction ID
   */
  public void setEftTrxID(String EftTrxID) {
    set_Value(COLUMNNAME_EftTrxID, EftTrxID);
  }

    /**
   * Set EFT Trx Type.
   *
   * @param EftTrxType Electronic Funds Transfer Transaction Type
   */
  public void setEftTrxType(String EftTrxType) {
    set_Value(COLUMNNAME_EftTrxType, EftTrxType);
  }

    /**
   * Set EFT Effective Date.
   *
   * @param EftValutaDate Electronic Funds Transfer Valuta (effective) Date
   */
  public void setEftValutaDate(Timestamp EftValutaDate) {
    set_Value(COLUMNNAME_EftValutaDate, EftValutaDate);
  }

    /**
   * Set Interest Amount.
   *
   * @param InterestAmt Interest Amount
   */
  public void setInterestAmt(BigDecimal InterestAmt) {
    set_Value(COLUMNNAME_InterestAmt, InterestAmt);
  }

  /**
   * Get Interest Amount.
   *
   * @return Interest Amount
   */
  public BigDecimal getInterestAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_InterestAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Set Reversal.
   *
   * @param IsReversal This is a reversing transaction
   */
  public void setIsReversal(boolean IsReversal) {
    set_Value(COLUMNNAME_IsReversal, Boolean.valueOf(IsReversal));
  }

  /**
   * Get Reversal.
   *
   * @return This is a reversing transaction
   */
  public boolean isReversal() {
    Object oo = get_Value(COLUMNNAME_IsReversal);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Line No.
   *
   * @param Line Unique line for this document
   */
  public void setLine(int Line) {
    set_Value(COLUMNNAME_Line, Integer.valueOf(Line));
  }

  /**
   * Get Line No.
   *
   * @return Unique line for this document
   */
  public int getLine() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Line);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Memo.
   *
   * @param Memo Memo Text
   */
  public void setMemo(String Memo) {
    set_Value(COLUMNNAME_Memo, Memo);
  }

    /**
   * Set Reference No.
   *
   * @param ReferenceNo Your customer or vendor number at the Business Partner's site
   */
  public void setReferenceNo(String ReferenceNo) {
    set_Value(COLUMNNAME_ReferenceNo, ReferenceNo);
  }

    /**
   * Set Statement Line Date.
   *
   * @param StatementLineDate Date of the Statement Line
   */
  public void setStatementLineDate(Timestamp StatementLineDate) {
    set_Value(COLUMNNAME_StatementLineDate, StatementLineDate);
  }

  /**
   * Get Statement Line Date.
   *
   * @return Date of the Statement Line
   */
  public Timestamp getStatementLineDate() {
    return (Timestamp) get_Value(COLUMNNAME_StatementLineDate);
  }

  /**
   * Set Statement amount.
   *
   * @param StmtAmt Statement Amount
   */
  public void setStmtAmt(BigDecimal StmtAmt) {
    set_Value(COLUMNNAME_StmtAmt, StmtAmt);
  }

  /**
   * Get Statement amount.
   *
   * @return Statement Amount
   */
  public BigDecimal getStmtAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_StmtAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Transaction Amount.
   *
   * @param TrxAmt Amount of a transaction
   */
  public void setTrxAmt(BigDecimal TrxAmt) {
    set_Value(COLUMNNAME_TrxAmt, TrxAmt);
  }

  /**
   * Get Transaction Amount.
   *
   * @return Amount of a transaction
   */
  public BigDecimal getTrxAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_TrxAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Effective date.
   *
   * @param ValutaDate Date when money is available
   */
  public void setValutaDate(Timestamp ValutaDate) {
    set_Value(COLUMNNAME_ValutaDate, ValutaDate);
  }

  /**
   * Get Effective date.
   *
   * @return Date when money is available
   */
  public Timestamp getValutaDate() {
    return (Timestamp) get_Value(COLUMNNAME_ValutaDate);
  }

  @Override
  public int getTableId() {
    return I_C_BankStatementLine.Table_ID;
  }
}
