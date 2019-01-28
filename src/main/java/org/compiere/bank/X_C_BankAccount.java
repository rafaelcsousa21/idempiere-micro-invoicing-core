package org.compiere.bank;

import org.compiere.model.I_C_BankAccount;
import org.compiere.orm.BasePONameValue;
import org.compiere.orm.MTable;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_BankAccount
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_BankAccount extends BasePONameValue implements I_C_BankAccount, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_BankAccount(Properties ctx, int C_BankAccount_ID, String trxName) {
    super(ctx, C_BankAccount_ID, trxName);
  }

  /** Load Constructor */
  public X_C_BankAccount(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_BankAccount[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Account No.
   *
   * @param AccountNo Account Number
   */
  public void setAccountNo(String AccountNo) {
    set_Value(COLUMNNAME_AccountNo, AccountNo);
  }

  /**
   * Get Account No.
   *
   * @return Account Number
   */
  public String getAccountNo() {
    return (String) get_Value(COLUMNNAME_AccountNo);
  }

  /** BankAccountType AD_Reference_ID=216 */
  public static final int BANKACCOUNTTYPE_AD_Reference_ID = 216;
  /** Checking = C */
  public static final String BANKACCOUNTTYPE_Checking = "C";
  /** Savings = S */
  public static final String BANKACCOUNTTYPE_Savings = "S";
  /** Cash = B */
  public static final String BANKACCOUNTTYPE_Cash = "B";
  /** Card = D */
  public static final String BANKACCOUNTTYPE_Card = "D";
  /**
   * Set Bank Account Type.
   *
   * @param BankAccountType Bank Account Type
   */
  public void setBankAccountType(String BankAccountType) {

    set_Value(COLUMNNAME_BankAccountType, BankAccountType);
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

    public org.compiere.model.I_C_Bank getC_Bank() throws RuntimeException {
    return (org.compiere.model.I_C_Bank)
        MTable.get(getCtx(), org.compiere.model.I_C_Bank.Table_Name)
            .getPO(getC_Bank_ID(), null);
  }

  /**
   * Set Bank.
   *
   * @param C_Bank_ID Bank
   */
  public void setC_Bank_ID(int C_Bank_ID) {
    if (C_Bank_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Bank_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Bank_ID, Integer.valueOf(C_Bank_ID));
  }

  /**
   * Get Bank.
   *
   * @return Bank
   */
  public int getC_Bank_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Bank_ID);
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
   * Set Credit limit.
   *
   * @param CreditLimit Amount of Credit allowed
   */
  public void setCreditLimit(BigDecimal CreditLimit) {
    set_Value(COLUMNNAME_CreditLimit, CreditLimit);
  }

    /**
   * Set Current balance.
   *
   * @param CurrentBalance Current Balance
   */
  public void setCurrentBalance(BigDecimal CurrentBalance) {
    set_Value(COLUMNNAME_CurrentBalance, CurrentBalance);
  }

  /**
   * Get Current balance.
   *
   * @return Current Balance
   */
  public BigDecimal getCurrentBalance() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_CurrentBalance);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Set IBAN.
   *
   * @param IBAN International Bank Account Number
   */
  public void setIBAN(String IBAN) {
    set_Value(COLUMNNAME_IBAN, IBAN);
  }

  /**
   * Get IBAN.
   *
   * @return International Bank Account Number
   */
  public String getIBAN() {
    return (String) get_Value(COLUMNNAME_IBAN);
  }

  /**
   * Set Default.
   *
   * @param IsDefault Default value
   */
  public void setIsDefault(boolean IsDefault) {
    set_Value(COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
  }

    @Override
  public int getTableId() {
    return I_C_BankAccount.Table_ID;
  }
}
