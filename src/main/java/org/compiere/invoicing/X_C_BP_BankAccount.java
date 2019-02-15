package org.compiere.invoicing;

import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_BP_BankAccount
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_BP_BankAccount extends PO implements I_C_BP_BankAccount, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_BP_BankAccount(Properties ctx, int C_BP_BankAccount_ID) {
    super(ctx, C_BP_BankAccount_ID);
    /**
     * if (C_BP_BankAccount_ID == 0) { setA_Name (null); setC_BPartner_ID (0);
     * setC_BP_BankAccount_ID (0); setIsACH (false); }
     */
  }

  /** Load Constructor */
  public X_C_BP_BankAccount(Properties ctx, ResultSet rs) {
    super(ctx, rs);
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
    StringBuffer sb = new StringBuffer("X_C_BP_BankAccount[").append(getId()).append("]");
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

  /**
   * Set Account City.
   *
   * @param A_City City or the Credit Card or Account Holder
   */
  public void setA_City(String A_City) {
    set_Value(COLUMNNAME_A_City, A_City);
  }

  /**
   * Get Account City.
   *
   * @return City or the Credit Card or Account Holder
   */
  public String getA_City() {
    return (String) get_Value(COLUMNNAME_A_City);
  }

  /**
   * Set Account Country.
   *
   * @param A_Country Country
   */
  public void setA_Country(String A_Country) {
    set_Value(COLUMNNAME_A_Country, A_Country);
  }

  /**
   * Get Account Country.
   *
   * @return Country
   */
  public String getA_Country() {
    return (String) get_Value(COLUMNNAME_A_Country);
  }

    /**
   * Get User/Contact.
   *
   * @return User within the system - Internal or Business Partner Contact
   */
  public int getAD_User_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_User_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Account EMail.
   *
   * @param A_EMail Email Address
   */
  public void setA_EMail(String A_EMail) {
    set_Value(COLUMNNAME_A_EMail, A_EMail);
  }

  /**
   * Get Account EMail.
   *
   * @return Email Address
   */
  public String getA_EMail() {
    return (String) get_Value(COLUMNNAME_A_EMail);
  }

  /**
   * Set Driver License.
   *
   * @param A_Ident_DL Payment Identification - Driver License
   */
  public void setA_Ident_DL(String A_Ident_DL) {
    set_Value(COLUMNNAME_A_Ident_DL, A_Ident_DL);
  }

  /**
   * Get Driver License.
   *
   * @return Payment Identification - Driver License
   */
  public String getA_Ident_DL() {
    return (String) get_Value(COLUMNNAME_A_Ident_DL);
  }

  /**
   * Set Social Security No.
   *
   * @param A_Ident_SSN Payment Identification - Social Security No
   */
  public void setA_Ident_SSN(String A_Ident_SSN) {
    set_Value(COLUMNNAME_A_Ident_SSN, A_Ident_SSN);
  }

  /**
   * Get Social Security No.
   *
   * @return Payment Identification - Social Security No
   */
  public String getA_Ident_SSN() {
    return (String) get_Value(COLUMNNAME_A_Ident_SSN);
  }

  /**
   * Set Account Name.
   *
   * @param A_Name Name on Credit Card or Account holder
   */
  public void setA_Name(String A_Name) {
    set_Value(COLUMNNAME_A_Name, A_Name);
  }

  /**
   * Get Account Name.
   *
   * @return Name on Credit Card or Account holder
   */
  public String getA_Name() {
    return (String) get_Value(COLUMNNAME_A_Name);
  }

    /**
   * Set Account State.
   *
   * @param A_State State of the Credit Card or Account holder
   */
  public void setA_State(String A_State) {
    set_Value(COLUMNNAME_A_State, A_State);
  }

  /**
   * Get Account State.
   *
   * @return State of the Credit Card or Account holder
   */
  public String getA_State() {
    return (String) get_Value(COLUMNNAME_A_State);
  }

  /**
   * Set Account Street.
   *
   * @param A_Street Street address of the Credit Card or Account holder
   */
  public void setA_Street(String A_Street) {
    set_Value(COLUMNNAME_A_Street, A_Street);
  }

  /**
   * Get Account Street.
   *
   * @return Street address of the Credit Card or Account holder
   */
  public String getA_Street() {
    return (String) get_Value(COLUMNNAME_A_Street);
  }

  /**
   * Set Account Zip/Postal.
   *
   * @param A_Zip Zip Code of the Credit Card or Account Holder
   */
  public void setA_Zip(String A_Zip) {
    set_Value(COLUMNNAME_A_Zip, A_Zip);
  }

  /**
   * Get Account Zip/Postal.
   *
   * @return Zip Code of the Credit Card or Account Holder
   */
  public String getA_Zip() {
    return (String) get_Value(COLUMNNAME_A_Zip);
  }

    /** Both = B */
  public static final String BPBANKACCTUSE_Both = "B";
  /** Direct Debit = D */
  public static final String BPBANKACCTUSE_DirectDebit = "D";
  /** Direct Deposit = T */
  public static final String BPBANKACCTUSE_DirectDeposit = "T";
  /**
   * Set Account Usage.
   *
   * @param BPBankAcctUse Business Partner Bank Account usage
   */
  public void setBPBankAcctUse(String BPBankAcctUse) {

    set_Value(COLUMNNAME_BPBankAcctUse, BPBankAcctUse);
  }

  /**
   * Get Account Usage.
   *
   * @return Business Partner Bank Account usage
   */
  public String getBPBankAcctUse() {
    return (String) get_Value(COLUMNNAME_BPBankAcctUse);
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
   * Set Business Partner .
   *
   * @param C_BPartner_ID Identifies a Business Partner
   */
  public void setC_BPartner_ID(int C_BPartner_ID) {
    if (C_BPartner_ID < 1) set_ValueNoCheck(COLUMNNAME_C_BPartner_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
   * Get Partner Bank Account.
   *
   * @return Bank Account of the Business Partner
   */
  public int getC_BP_BankAccount_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BP_BankAccount_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set Exp. Month.
   *
   * @param CreditCardExpMM Expiry Month
   */
  public void setCreditCardExpMM(int CreditCardExpMM) {
    set_Value(COLUMNNAME_CreditCardExpMM, Integer.valueOf(CreditCardExpMM));
  }

  /**
   * Get Exp. Month.
   *
   * @return Expiry Month
   */
  public int getCreditCardExpMM() {
    Integer ii = (Integer) get_Value(COLUMNNAME_CreditCardExpMM);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Exp. Year.
   *
   * @param CreditCardExpYY Expiry Year
   */
  public void setCreditCardExpYY(int CreditCardExpYY) {
    set_Value(COLUMNNAME_CreditCardExpYY, Integer.valueOf(CreditCardExpYY));
  }

  /**
   * Get Exp. Year.
   *
   * @return Expiry Year
   */
  public int getCreditCardExpYY() {
    Integer ii = (Integer) get_Value(COLUMNNAME_CreditCardExpYY);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Number.
   *
   * @param CreditCardNumber Credit Card Number
   */
  public void setCreditCardNumber(String CreditCardNumber) {
    set_Value(COLUMNNAME_CreditCardNumber, CreditCardNumber);
  }

  /**
   * Get Number.
   *
   * @return Credit Card Number
   */
  public String getCreditCardNumber() {
    return (String) get_Value(COLUMNNAME_CreditCardNumber);
  }

    /**
   * Set Credit Card.
   *
   * @param CreditCardType Credit Card (Visa, MC, AmEx)
   */
  public void setCreditCardType(String CreditCardType) {

    set_Value(COLUMNNAME_CreditCardType, CreditCardType);
  }

  /**
   * Get Credit Card.
   *
   * @return Credit Card (Visa, MC, AmEx)
   */
  public String getCreditCardType() {
    return (String) get_Value(COLUMNNAME_CreditCardType);
  }

  /**
   * Set Verification Code.
   *
   * @param CreditCardVV Credit Card Verification code on credit card
   */
  public void setCreditCardVV(String CreditCardVV) {
    set_Value(COLUMNNAME_CreditCardVV, CreditCardVV);
  }

  /**
   * Get Verification Code.
   *
   * @return Credit Card Verification code on credit card
   */
  public String getCreditCardVV() {
    return (String) get_Value(COLUMNNAME_CreditCardVV);
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
   * Set ACH.
   *
   * @param IsACH Automatic Clearing House
   */
  public void setIsACH(boolean IsACH) {
    set_Value(COLUMNNAME_IsACH, Boolean.valueOf(IsACH));
  }

  /**
   * Get ACH.
   *
   * @return Automatic Clearing House
   */
  public boolean isACH() {
    Object oo = get_Value(COLUMNNAME_IsACH);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Set Address verified.
   *
   * @param R_AvsAddr This address has been verified
   */
  public void setR_AvsAddr(String R_AvsAddr) {

    set_ValueNoCheck(COLUMNNAME_R_AvsAddr, R_AvsAddr);
  }

    /**
   * Set Zip verified.
   *
   * @param R_AvsZip The Zip Code has been verified
   */
  public void setR_AvsZip(String R_AvsZip) {

    set_ValueNoCheck(COLUMNNAME_R_AvsZip, R_AvsZip);
  }

    /**
   * Set Routing No.
   *
   * @param RoutingNo Bank Routing Number
   */
  public void setRoutingNo(String RoutingNo) {
    set_Value(COLUMNNAME_RoutingNo, RoutingNo);
  }

  /**
   * Get Routing No.
   *
   * @return Bank Routing Number
   */
  public String getRoutingNo() {
    return (String) get_Value(COLUMNNAME_RoutingNo);
  }

  @Override
  public int getTableId() {
    return I_C_BP_BankAccount.Table_ID;
  }
}
