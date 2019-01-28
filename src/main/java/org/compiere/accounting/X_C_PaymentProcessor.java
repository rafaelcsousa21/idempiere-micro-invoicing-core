package org.compiere.accounting;

import org.compiere.model.I_C_PaymentProcessor;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_PaymentProcessor
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_PaymentProcessor extends BasePOName implements I_C_PaymentProcessor, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_PaymentProcessor(Properties ctx, int C_PaymentProcessor_ID, String trxName) {
    super(ctx, C_PaymentProcessor_ID, trxName);
  }

  /** Load Constructor */
  public X_C_PaymentProcessor(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 6 - System - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_PaymentProcessor[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Accept AMEX.
   *
   * @param AcceptAMEX Accept American Express Card
   */
  public void setAcceptAMEX(boolean AcceptAMEX) {
    set_Value(COLUMNNAME_AcceptAMEX, Boolean.valueOf(AcceptAMEX));
  }

  /**
   * Get Accept AMEX.
   *
   * @return Accept American Express Card
   */
  public boolean isAcceptAMEX() {
    Object oo = get_Value(COLUMNNAME_AcceptAMEX);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Accept ATM.
   *
   * @param AcceptATM Accept Bank ATM Card
   */
  public void setAcceptATM(boolean AcceptATM) {
    set_Value(COLUMNNAME_AcceptATM, Boolean.valueOf(AcceptATM));
  }

  /**
   * Get Accept ATM.
   *
   * @return Accept Bank ATM Card
   */
  public boolean isAcceptATM() {
    Object oo = get_Value(COLUMNNAME_AcceptATM);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Accept Electronic Check.
   *
   * @param AcceptCheck Accept ECheck (Electronic Checks)
   */
  public void setAcceptCheck(boolean AcceptCheck) {
    set_Value(COLUMNNAME_AcceptCheck, Boolean.valueOf(AcceptCheck));
  }

  /**
   * Get Accept Electronic Check.
   *
   * @return Accept ECheck (Electronic Checks)
   */
  public boolean isAcceptCheck() {
    Object oo = get_Value(COLUMNNAME_AcceptCheck);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Accept Corporate.
   *
   * @param AcceptCorporate Accept Corporate Purchase Cards
   */
  public void setAcceptCorporate(boolean AcceptCorporate) {
    set_Value(COLUMNNAME_AcceptCorporate, Boolean.valueOf(AcceptCorporate));
  }

  /**
   * Get Accept Corporate.
   *
   * @return Accept Corporate Purchase Cards
   */
  public boolean isAcceptCorporate() {
    Object oo = get_Value(COLUMNNAME_AcceptCorporate);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Accept Diners.
   *
   * @param AcceptDiners Accept Diner's Club
   */
  public void setAcceptDiners(boolean AcceptDiners) {
    set_Value(COLUMNNAME_AcceptDiners, Boolean.valueOf(AcceptDiners));
  }

  /**
   * Get Accept Diners.
   *
   * @return Accept Diner's Club
   */
  public boolean isAcceptDiners() {
    Object oo = get_Value(COLUMNNAME_AcceptDiners);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Accept Direct Debit.
   *
   * @param AcceptDirectDebit Accept Direct Debits (vendor initiated)
   */
  public void setAcceptDirectDebit(boolean AcceptDirectDebit) {
    set_Value(COLUMNNAME_AcceptDirectDebit, Boolean.valueOf(AcceptDirectDebit));
  }

  /**
   * Get Accept Direct Debit.
   *
   * @return Accept Direct Debits (vendor initiated)
   */
  public boolean isAcceptDirectDebit() {
    Object oo = get_Value(COLUMNNAME_AcceptDirectDebit);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Accept Direct Deposit.
   *
   * @param AcceptDirectDeposit Accept Direct Deposit (payee initiated)
   */
  public void setAcceptDirectDeposit(boolean AcceptDirectDeposit) {
    set_Value(COLUMNNAME_AcceptDirectDeposit, Boolean.valueOf(AcceptDirectDeposit));
  }

  /**
   * Get Accept Direct Deposit.
   *
   * @return Accept Direct Deposit (payee initiated)
   */
  public boolean isAcceptDirectDeposit() {
    Object oo = get_Value(COLUMNNAME_AcceptDirectDeposit);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Accept Discover.
   *
   * @param AcceptDiscover Accept Discover Card
   */
  public void setAcceptDiscover(boolean AcceptDiscover) {
    set_Value(COLUMNNAME_AcceptDiscover, Boolean.valueOf(AcceptDiscover));
  }

  /**
   * Get Accept Discover.
   *
   * @return Accept Discover Card
   */
  public boolean isAcceptDiscover() {
    Object oo = get_Value(COLUMNNAME_AcceptDiscover);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Accept MasterCard.
   *
   * @param AcceptMC Accept Master Card
   */
  public void setAcceptMC(boolean AcceptMC) {
    set_Value(COLUMNNAME_AcceptMC, Boolean.valueOf(AcceptMC));
  }

  /**
   * Get Accept MasterCard.
   *
   * @return Accept Master Card
   */
  public boolean isAcceptMC() {
    Object oo = get_Value(COLUMNNAME_AcceptMC);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Accept Visa.
   *
   * @param AcceptVisa Accept Visa Cards
   */
  public void setAcceptVisa(boolean AcceptVisa) {
    set_Value(COLUMNNAME_AcceptVisa, Boolean.valueOf(AcceptVisa));
  }

  /**
   * Get Accept Visa.
   *
   * @return Accept Visa Cards
   */
  public boolean isAcceptVisa() {
    Object oo = get_Value(COLUMNNAME_AcceptVisa);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Sequence.
   *
   * @return Document Sequence
   */
  public int getAD_Sequence_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Sequence_ID);
    if (ii == null) return 0;
    return ii;
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
    if (C_BankAccount_ID < 1) set_ValueNoCheck(COLUMNNAME_C_BankAccount_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
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
   * Set Commission %.
   *
   * @param Commission Commission stated as a percentage
   */
  public void setCommission(BigDecimal Commission) {
    set_Value(COLUMNNAME_Commission, Commission);
  }

    /**
   * Set Cost per transaction.
   *
   * @param CostPerTrx Fixed cost per transaction
   */
  public void setCostPerTrx(BigDecimal CostPerTrx) {
    set_Value(COLUMNNAME_CostPerTrx, CostPerTrx);
  }

    /**
   * Get Host Address.
   *
   * @return Host Address URL or DNS
   */
  public String getHostAddress() {
    return (String) get_Value(COLUMNNAME_HostAddress);
  }

    /**
   * Get Host port.
   *
   * @return Host Communication Port
   */
  public int getHostPort() {
    Integer ii = (Integer) get_Value(COLUMNNAME_HostPort);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Payment Processor Class.
   *
   * @return Payment Processor Java Class
   */
  public String getPayProcessorClass() {
    return (String) get_Value(COLUMNNAME_PayProcessorClass);
  }

    /**
   * Get Proxy address.
   *
   * @return Address of your proxy server
   */
  public String getProxyAddress() {
    return (String) get_Value(COLUMNNAME_ProxyAddress);
  }

    /**
   * Get Proxy logon.
   *
   * @return Logon of your proxy server
   */
  public String getProxyLogon() {
    return (String) get_Value(COLUMNNAME_ProxyLogon);
  }

    /**
   * Get Proxy password.
   *
   * @return Password of your proxy server
   */
  public String getProxyPassword() {
    return (String) get_Value(COLUMNNAME_ProxyPassword);
  }

    /**
   * Get Proxy port.
   *
   * @return Port of your proxy server
   */
  public int getProxyPort() {
    Integer ii = (Integer) get_Value(COLUMNNAME_ProxyPort);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Require CreditCard Verification Code.
   *
   * @param RequireVV Require 3/4 digit Credit Verification Code
   */
  public void setRequireVV(boolean RequireVV) {
    set_Value(COLUMNNAME_RequireVV, Boolean.valueOf(RequireVV));
  }

    /** TrxType AD_Reference_ID=215 */
  public static final int TRXTYPE_AD_Reference_ID = 215;
  /** Sales = S */
  public static final String TRXTYPE_Sales = "S";
  /** Delayed Capture = D */
  public static final String TRXTYPE_DelayedCapture = "D";
  /** Credit (Payment) = C */
  public static final String TRXTYPE_CreditPayment = "C";
  /** Voice Authorization = F */
  public static final String TRXTYPE_VoiceAuthorization = "F";
  /** Authorization = A */
  public static final String TRXTYPE_Authorization = "A";
  /** Void = V */
  public static final String TRXTYPE_Void = "V";

    @Override
  public int getTableId() {
    return I_C_PaymentProcessor.Table_ID;
  }
}
