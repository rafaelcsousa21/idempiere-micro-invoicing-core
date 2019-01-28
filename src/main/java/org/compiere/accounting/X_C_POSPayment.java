package org.compiere.accounting;

import org.compiere.model.I_C_POSPayment;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_POSPayment
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_POSPayment extends PO implements I_C_POSPayment, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_POSPayment(Properties ctx, int C_POSPayment_ID, String trxName) {
    super(ctx, C_POSPayment_ID, trxName);
    /**
     * if (C_POSPayment_ID == 0) { setC_Order_ID (0); setC_POSPayment_ID (0); setC_POSTenderType_ID
     * (0); setIsPostDated (false); // N setPayAmt (Env.ZERO); setProcessed (false); }
     */
  }

  /** Load Constructor */
  public X_C_POSPayment(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_POSPayment[").append(getId()).append("]");
    return sb.toString();
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
   * Get Account Name.
   *
   * @return Name on Credit Card or Account holder
   */
  public String getA_Name() {
    return (String) get_Value(COLUMNNAME_A_Name);
  }

    /**
   * Get Check No.
   *
   * @return Check Number
   */
  public String getCheckNo() {
    return (String) get_Value(COLUMNNAME_CheckNo);
  }

  /** CheckStatus AD_Reference_ID=200007 */
  public static final int CHECKSTATUS_AD_Reference_ID = 200007;
  /** Charged = C */
  public static final String CHECKSTATUS_Charged = "C";
  /** Delayed = D */
  public static final String CHECKSTATUS_Delayed = "D";
  /** Replaced = P */
  public static final String CHECKSTATUS_Replaced = "P";
  /** Received = R */
  public static final String CHECKSTATUS_Received = "R";
  /** Returned = T */
  public static final String CHECKSTATUS_Returned = "T";

    /**
   * Get Order.
   *
   * @return Order
   */
  public int getC_Order_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Order_ID);
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
   * Get POS Tender Type.
   *
   * @return POS Tender Type
   */
  public int getC_POSTenderType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_POSTenderType_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Number.
   *
   * @return Credit Card Number
   */
  public String getCreditCardNumber() {
    return (String) get_Value(COLUMNNAME_CreditCardNumber);
  }

  /** CreditCardType AD_Reference_ID=149 */
  public static final int CREDITCARDTYPE_AD_Reference_ID = 149;
  /** Amex = A */
  public static final String CREDITCARDTYPE_Amex = "A";
  /** MasterCard = M */
  public static final String CREDITCARDTYPE_MasterCard = "M";
  /** Visa = V */
  public static final String CREDITCARDTYPE_Visa = "V";
  /** ATM = C */
  public static final String CREDITCARDTYPE_ATM = "C";
  /** Diners = D */
  public static final String CREDITCARDTYPE_Diners = "D";
  /** Discover = N */
  public static final String CREDITCARDTYPE_Discover = "N";
  /** Purchase Card = P */
  public static final String CREDITCARDTYPE_PurchaseCard = "P";

    /**
   * Get Credit Card.
   *
   * @return Credit Card (Visa, MC, AmEx)
   */
  public String getCreditCardType() {
    return (String) get_Value(COLUMNNAME_CreditCardType);
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
   * Get Post Dated.
   *
   * @return Post Dated
   */
  public boolean isPostDated() {
    Object oo = get_Value(COLUMNNAME_IsPostDated);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Micr.
   *
   * @return Combination of routing no, account and check no
   */
  public String getMicr() {
    return (String) get_Value(COLUMNNAME_Micr);
  }

    /**
   * Get Payment amount.
   *
   * @return Amount being paid
   */
  public BigDecimal getPayAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_PayAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
  }

    /**
   * Get Routing No.
   *
   * @return Bank Routing Number
   */
  public String getRoutingNo() {
    return (String) get_Value(COLUMNNAME_RoutingNo);
  }

    /**
   * Get Swift code.
   *
   * @return Swift Code or BIC
   */
  public String getSwiftCode() {
    return (String) get_Value(COLUMNNAME_SwiftCode);
  }

  /** TenderType AD_Reference_ID=214 */
  public static final int TENDERTYPE_AD_Reference_ID = 214;
  /** Credit Card = C */
  public static final String TENDERTYPE_CreditCard = "C";
  /** Check = K */
  public static final String TENDERTYPE_Check = "K";
  /** Direct Deposit = A */
  public static final String TENDERTYPE_DirectDeposit = "A";
  /** Direct Debit = D */
  public static final String TENDERTYPE_DirectDebit = "D";
  /** Account = T */
  public static final String TENDERTYPE_Account = "T";
  /** Cash = X */
  public static final String TENDERTYPE_Cash = "X";

    /**
   * Get Tender type.
   *
   * @return Method of Payment
   */
  public String getTenderType() {
    return (String) get_Value(COLUMNNAME_TenderType);
  }

    /**
   * Get Voice authorization code.
   *
   * @return Voice Authorization Code from credit card company
   */
  public String getVoiceAuthCode() {
    return (String) get_Value(COLUMNNAME_VoiceAuthCode);
  }
}
