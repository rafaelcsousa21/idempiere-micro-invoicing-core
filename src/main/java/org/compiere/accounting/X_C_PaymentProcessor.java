package org.compiere.accounting;

import org.compiere.model.I_C_PaymentProcessor;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.common.util.Env;
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

  public org.compiere.model.I_AD_Sequence getAD_Sequence() throws RuntimeException {
    return (org.compiere.model.I_AD_Sequence)
        MTable.get(getCtx(), org.compiere.model.I_AD_Sequence.Table_Name)
            .getPO(getAD_Sequence_ID(), get_TrxName());
  }

  /**
   * Set Sequence.
   *
   * @param AD_Sequence_ID Document Sequence
   */
  public void setAD_Sequence_ID(int AD_Sequence_ID) {
    if (AD_Sequence_ID < 1) set_Value(COLUMNNAME_AD_Sequence_ID, null);
    else set_Value(COLUMNNAME_AD_Sequence_ID, Integer.valueOf(AD_Sequence_ID));
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
            .getPO(getC_BankAccount_ID(), get_TrxName());
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

  public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException {
    return (org.compiere.model.I_C_Currency)
        MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
            .getPO(getC_Currency_ID(), get_TrxName());
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
   * Set Commission %.
   *
   * @param Commission Commission stated as a percentage
   */
  public void setCommission(BigDecimal Commission) {
    set_Value(COLUMNNAME_Commission, Commission);
  }

  /**
   * Get Commission %.
   *
   * @return Commission stated as a percentage
   */
  public BigDecimal getCommission() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Commission);
    if (bd == null) return Env.ZERO;
    return bd;
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
   * Get Cost per transaction.
   *
   * @return Fixed cost per transaction
   */
  public BigDecimal getCostPerTrx() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_CostPerTrx);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Payment Processor.
   *
   * @param C_PaymentProcessor_ID Payment processor for electronic payments
   */
  public void setC_PaymentProcessor_ID(int C_PaymentProcessor_ID) {
    if (C_PaymentProcessor_ID < 1) set_ValueNoCheck(COLUMNNAME_C_PaymentProcessor_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_PaymentProcessor_ID, Integer.valueOf(C_PaymentProcessor_ID));
  }

  /**
   * Get Payment Processor.
   *
   * @return Payment processor for electronic payments
   */
  public int getC_PaymentProcessor_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_PaymentProcessor_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_PaymentProcessor_UU.
   *
   * @param C_PaymentProcessor_UU C_PaymentProcessor_UU
   */
  public void setC_PaymentProcessor_UU(String C_PaymentProcessor_UU) {
    set_Value(COLUMNNAME_C_PaymentProcessor_UU, C_PaymentProcessor_UU);
  }

  /**
   * Get C_PaymentProcessor_UU.
   *
   * @return C_PaymentProcessor_UU
   */
  public String getC_PaymentProcessor_UU() {
    return (String) get_Value(COLUMNNAME_C_PaymentProcessor_UU);
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
   * Set Host Address.
   *
   * @param HostAddress Host Address URL or DNS
   */
  public void setHostAddress(String HostAddress) {
    set_Value(COLUMNNAME_HostAddress, HostAddress);
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
   * Set Host port.
   *
   * @param HostPort Host Communication Port
   */
  public void setHostPort(int HostPort) {
    set_Value(COLUMNNAME_HostPort, Integer.valueOf(HostPort));
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
   * Set Minimum Amt.
   *
   * @param MinimumAmt Minimum Amount in Document Currency
   */
  public void setMinimumAmt(BigDecimal MinimumAmt) {
    set_Value(COLUMNNAME_MinimumAmt, MinimumAmt);
  }

  /**
   * Get Minimum Amt.
   *
   * @return Minimum Amount in Document Currency
   */
  public BigDecimal getMinimumAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_MinimumAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Payment Processor Class.
   *
   * @param PayProcessorClass Payment Processor Java Class
   */
  public void setPayProcessorClass(String PayProcessorClass) {
    set_Value(COLUMNNAME_PayProcessorClass, PayProcessorClass);
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
   * Set Proxy address.
   *
   * @param ProxyAddress Address of your proxy server
   */
  public void setProxyAddress(String ProxyAddress) {
    set_Value(COLUMNNAME_ProxyAddress, ProxyAddress);
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
   * Set Proxy logon.
   *
   * @param ProxyLogon Logon of your proxy server
   */
  public void setProxyLogon(String ProxyLogon) {
    set_Value(COLUMNNAME_ProxyLogon, ProxyLogon);
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
   * Set Proxy password.
   *
   * @param ProxyPassword Password of your proxy server
   */
  public void setProxyPassword(String ProxyPassword) {
    set_Value(COLUMNNAME_ProxyPassword, ProxyPassword);
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
   * Set Proxy port.
   *
   * @param ProxyPort Port of your proxy server
   */
  public void setProxyPort(int ProxyPort) {
    set_Value(COLUMNNAME_ProxyPort, Integer.valueOf(ProxyPort));
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

  /**
   * Get Require CreditCard Verification Code.
   *
   * @return Require 3/4 digit Credit Verification Code
   */
  public boolean isRequireVV() {
    Object oo = get_Value(COLUMNNAME_RequireVV);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
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
  /**
   * Set Transaction Type.
   *
   * @param TrxType Type of credit card transaction
   */
  public void setTrxType(String TrxType) {

    set_Value(COLUMNNAME_TrxType, TrxType);
  }

  /**
   * Get Transaction Type.
   *
   * @return Type of credit card transaction
   */
  public String getTrxType() {
    return (String) get_Value(COLUMNNAME_TrxType);
  }

  @Override
  public int getTableId() {
    return I_C_PaymentProcessor.Table_ID;
  }
}
