package org.compiere.invoicing;

import org.compiere.model.I_C_PaymentTransaction;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_PaymentTransaction
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_PaymentTransaction extends PO implements I_C_PaymentTransaction, I_Persistent {

    /**
     * Amex = A
     */
    public static final String CREDITCARDTYPE_Amex = "A";
    /**
     * MasterCard = M
     */
    public static final String CREDITCARDTYPE_MasterCard = "M";
    /**
     * Visa = V
     */
    public static final String CREDITCARDTYPE_Visa = "V";
    /**
     * ATM = C
     */
    public static final String CREDITCARDTYPE_ATM = "C";
    /**
     * Diners = D
     */
    public static final String CREDITCARDTYPE_Diners = "D";
    /**
     * Discover = N
     */
    public static final String CREDITCARDTYPE_Discover = "N";
    /**
     * Purchase Card = P
     */
    public static final String CREDITCARDTYPE_PurchaseCard = "P";
    /**
     * Unavailable = X
     */
    public static final String R_AVSZIP_Unavailable = "X";
    /**
     * Credit Card = C
     */
    public static final String TENDERTYPE_CreditCard = "C";
    /**
     * Check = K
     */
    public static final String TENDERTYPE_Check = "K";
    /**
     * Sales = S
     */
    public static final String TRXTYPE_Sales = "S";
    /**
     * Delayed Capture = D
     */
    public static final String TRXTYPE_DelayedCapture = "D";
    /**
     * Credit (Payment) = C
     */
    public static final String TRXTYPE_CreditPayment = "C";
    /**
     * Voice Authorization = F
     */
    public static final String TRXTYPE_VoiceAuthorization = "F";
    /**
     * Authorization = A
     */
    public static final String TRXTYPE_Authorization = "A";
    /**
     * Void = V
     */
    public static final String TRXTYPE_Void = "V";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_PaymentTransaction(Properties ctx, int C_PaymentTransaction_ID) {
        super(ctx, C_PaymentTransaction_ID);
        /**
         * if (C_PaymentTransaction_ID == 0) { setC_BPartner_ID (0); setC_Currency_ID (0);
         * setC_PaymentTransaction_ID (0); setDateTrx (new Timestamp( System.currentTimeMillis() ));
         * // @#Date@ setIsApproved (false); // N setIsDelayedCapture (false); setIsOnline (false);
         * setIsReceipt (false); setIsSelfService (false); setIsVoided (false); // N setPayAmt
         * (Env.ZERO); // 0 setProcessed (false); // N setTenderType (null); // K setTrxType (null); //
         * S }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_PaymentTransaction(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_C_PaymentTransaction[").append(getId()).append("]");
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
     * Set Account No.
     *
     * @param AccountNo Account Number
     */
    public void setAccountNo(String AccountNo) {
        set_Value(COLUMNNAME_AccountNo, AccountNo);
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
     * Set Account City.
     *
     * @param A_City City or the Credit Card or Account Holder
     */
    public void setA_City(String A_City) {
        set_Value(COLUMNNAME_A_City, A_City);
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
     * Set Account Country.
     *
     * @param A_Country Country
     */
    public void setA_Country(String A_Country) {
        set_Value(COLUMNNAME_A_Country, A_Country);
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
     * Set Account EMail.
     *
     * @param A_EMail Email Address
     */
    public void setA_EMail(String A_EMail) {
        set_Value(COLUMNNAME_A_EMail, A_EMail);
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
     * Set Driver License.
     *
     * @param A_Ident_DL Payment Identification - Driver License
     */
    public void setA_Ident_DL(String A_Ident_DL) {
        set_Value(COLUMNNAME_A_Ident_DL, A_Ident_DL);
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
     * Set Social Security No.
     *
     * @param A_Ident_SSN Payment Identification - Social Security No
     */
    public void setA_Ident_SSN(String A_Ident_SSN) {
        set_Value(COLUMNNAME_A_Ident_SSN, A_Ident_SSN);
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
     * Set Account Name.
     *
     * @param A_Name Name on Credit Card or Account holder
     */
    public void setA_Name(String A_Name) {
        set_Value(COLUMNNAME_A_Name, A_Name);
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
     * Set Account State.
     *
     * @param A_State State of the Credit Card or Account holder
     */
    public void setA_State(String A_State) {
        set_Value(COLUMNNAME_A_State, A_State);
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
     * Set Account Street.
     *
     * @param A_Street Street address of the Credit Card or Account holder
     */
    public void setA_Street(String A_Street) {
        set_Value(COLUMNNAME_A_Street, A_Street);
    }

    /**
     * Get Account Zip/Postal.
     *
     * @return Zip Code of the Credit Card or Account Holder
     */
    public String getA_Zip() {
        return (String) get_Value(COLUMNNAME_A_Zip);
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
     * Get Activity.
     *
     * @return Business Activity
     */
    public int getC_Activity_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Activity_ID);
        if (ii == null) return 0;
        return ii;
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
     * Set Bank Account.
     *
     * @param C_BankAccount_ID Account at the Bank
     */
    public void setC_BankAccount_ID(int C_BankAccount_ID) {
        if (C_BankAccount_ID < 1) set_Value(COLUMNNAME_C_BankAccount_ID, null);
        else set_Value(COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
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
     * Set Business Partner .
     *
     * @param C_BPartner_ID Identifies a Business Partner
     */
    public void setC_BPartner_ID(int C_BPartner_ID) {
        if (C_BPartner_ID < 1) set_Value(COLUMNNAME_C_BPartner_ID, null);
        else set_Value(COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
     * Set Partner Bank Account.
     *
     * @param C_BP_BankAccount_ID Bank Account of the Business Partner
     */
    public void setC_BP_BankAccount_ID(int C_BP_BankAccount_ID) {
        if (C_BP_BankAccount_ID < 1) set_Value(COLUMNNAME_C_BP_BankAccount_ID, null);
        else set_Value(COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
    }

    /**
     * Get Campaign.
     *
     * @return Marketing Campaign
     */
    public int getC_Campaign_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Campaign_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Cash Book.
     *
     * @return Cash Book for recording petty cash transactions
     */
    public int getC_CashBook_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_CashBook_ID);
        if (ii == null) return 0;
        return ii;
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
     * Get Currency Type.
     *
     * @return Currency Conversion Rate Type
     */
    public int getC_ConversionType_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_ConversionType_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Currency Type.
     *
     * @param C_ConversionType_ID Currency Conversion Rate Type
     */
    public void setC_ConversionType_ID(int C_ConversionType_ID) {
        if (C_ConversionType_ID < 1) set_Value(COLUMNNAME_C_ConversionType_ID, null);
        else set_Value(COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
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
     * Set Currency.
     *
     * @param C_Currency_ID The Currency for this record
     */
    public void setC_Currency_ID(int C_Currency_ID) {
        if (C_Currency_ID < 1) set_Value(COLUMNNAME_C_Currency_ID, null);
        else set_Value(COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
    }

    /**
     * Get Check No.
     *
     * @return Check Number
     */
    public String getCheckNo() {
        return (String) get_Value(COLUMNNAME_CheckNo);
    }

    /**
     * Set Check No.
     *
     * @param CheckNo Check Number
     */
    public void setCheckNo(String CheckNo) {
        set_Value(COLUMNNAME_CheckNo, CheckNo);
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
     * Set Invoice.
     *
     * @param C_Invoice_ID Invoice Identifier
     */
    public void setC_Invoice_ID(int C_Invoice_ID) {
        if (C_Invoice_ID < 1) set_Value(COLUMNNAME_C_Invoice_ID, null);
        else set_Value(COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
    }

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
     * Set Order.
     *
     * @param C_Order_ID Order
     */
    public void setC_Order_ID(int C_Order_ID) {
        if (C_Order_ID < 1) set_Value(COLUMNNAME_C_Order_ID, null);
        else set_Value(COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
    }

    /**
     * Get Payment Batch.
     *
     * @return Payment batch for EFT
     */
    public int getC_PaymentBatch_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_PaymentBatch_ID);
        if (ii == null) return 0;
        return ii;
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
     * Set Payment.
     *
     * @param C_Payment_ID Payment identifier
     */
    public void setC_Payment_ID(int C_Payment_ID) {
        if (C_Payment_ID < 1) set_Value(COLUMNNAME_C_Payment_ID, null);
        else set_Value(COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
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
     * Set Payment Processor.
     *
     * @param C_PaymentProcessor_ID Payment processor for electronic payments
     */
    public void setC_PaymentProcessor_ID(int C_PaymentProcessor_ID) {
        if (C_PaymentProcessor_ID < 1) set_Value(COLUMNNAME_C_PaymentProcessor_ID, null);
        else set_Value(COLUMNNAME_C_PaymentProcessor_ID, Integer.valueOf(C_PaymentProcessor_ID));
    }

    /**
     * Get Payment Transaction.
     *
     * @return Payment Transaction
     */
    public int getC_PaymentTransaction_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_PaymentTransaction_ID);
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
     * Set POS Tender Type.
     *
     * @param C_POSTenderType_ID POS Tender Type
     */
    public void setC_POSTenderType_ID(int C_POSTenderType_ID) {
        if (C_POSTenderType_ID < 1) set_Value(COLUMNNAME_C_POSTenderType_ID, null);
        else set_Value(COLUMNNAME_C_POSTenderType_ID, Integer.valueOf(C_POSTenderType_ID));
    }

    /**
     * Get Project.
     *
     * @return Financial Project
     */
    public int getC_Project_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Project_ID);
        if (ii == null) return 0;
        return ii;
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
     * Set Exp. Month.
     *
     * @param CreditCardExpMM Expiry Month
     */
    public void setCreditCardExpMM(int CreditCardExpMM) {
        set_Value(COLUMNNAME_CreditCardExpMM, Integer.valueOf(CreditCardExpMM));
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
     * Set Exp. Year.
     *
     * @param CreditCardExpYY Expiry Year
     */
    public void setCreditCardExpYY(int CreditCardExpYY) {
        set_Value(COLUMNNAME_CreditCardExpYY, Integer.valueOf(CreditCardExpYY));
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
     * Set Number.
     *
     * @param CreditCardNumber Credit Card Number
     */
    public void setCreditCardNumber(String CreditCardNumber) {
        set_Value(COLUMNNAME_CreditCardNumber, CreditCardNumber);
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
     * Set Credit Card.
     *
     * @param CreditCardType Credit Card (Visa, MC, AmEx)
     */
    public void setCreditCardType(String CreditCardType) {

        set_Value(COLUMNNAME_CreditCardType, CreditCardType);
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
     * Set Verification Code.
     *
     * @param CreditCardVV Credit Card Verification code on credit card
     */
    public void setCreditCardVV(String CreditCardVV) {
        set_Value(COLUMNNAME_CreditCardVV, CreditCardVV);
    }

    /**
     * Get Customer Address ID.
     *
     * @return Customer Address ID
     */
    public String getCustomerAddressID() {
        return (String) get_Value(COLUMNNAME_CustomerAddressID);
    }

    /**
     * Set Customer Address ID.
     *
     * @param CustomerAddressID Customer Address ID
     */
    public void setCustomerAddressID(String CustomerAddressID) {
        set_Value(COLUMNNAME_CustomerAddressID, CustomerAddressID);
    }

    /**
     * Get Customer Payment Profile ID.
     *
     * @return Customer Payment Profile ID
     */
    public String getCustomerPaymentProfileID() {
        return (String) get_Value(COLUMNNAME_CustomerPaymentProfileID);
    }

    /**
     * Set Customer Payment Profile ID.
     *
     * @param CustomerPaymentProfileID Customer Payment Profile ID
     */
    public void setCustomerPaymentProfileID(String CustomerPaymentProfileID) {
        set_Value(COLUMNNAME_CustomerPaymentProfileID, CustomerPaymentProfileID);
    }

    /**
     * Get Customer Profile ID.
     *
     * @return Customer Profile ID
     */
    public String getCustomerProfileID() {
        return (String) get_Value(COLUMNNAME_CustomerProfileID);
    }

    /**
     * Set Customer Profile ID.
     *
     * @param CustomerProfileID Customer Profile ID
     */
    public void setCustomerProfileID(String CustomerProfileID) {
        set_Value(COLUMNNAME_CustomerProfileID, CustomerProfileID);
    }

    /**
     * Get Transaction Date.
     *
     * @return Transaction Date
     */
    public Timestamp getDateTrx() {
        return (Timestamp) get_Value(COLUMNNAME_DateTrx);
    }

    /**
     * Set Transaction Date.
     *
     * @param DateTrx Transaction Date
     */
    public void setDateTrx(Timestamp DateTrx) {
        set_Value(COLUMNNAME_DateTrx, DateTrx);
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
     * Set Description.
     *
     * @param Description Optional short description of the record
     */
    public void setDescription(String Description) {
        set_Value(COLUMNNAME_Description, Description);
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
     * Set IBAN.
     *
     * @param IBAN International Bank Account Number
     */
    public void setIBAN(String IBAN) {
        set_Value(COLUMNNAME_IBAN, IBAN);
    }

    /**
     * Set Approved.
     *
     * @param IsApproved Indicates if this document requires approval
     */
    public void setIsApproved(boolean IsApproved) {
        set_ValueNoCheck(COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
    }

    /**
     * Get Approved.
     *
     * @return Indicates if this document requires approval
     */
    public boolean isApproved() {
        Object oo = get_Value(COLUMNNAME_IsApproved);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Delayed Capture.
     *
     * @param IsDelayedCapture Charge after Shipment
     */
    public void setIsDelayedCapture(boolean IsDelayedCapture) {
        set_Value(COLUMNNAME_IsDelayedCapture, Boolean.valueOf(IsDelayedCapture));
    }

    /**
     * Get Delayed Capture.
     *
     * @return Charge after Shipment
     */
    public boolean isDelayedCapture() {
        Object oo = get_Value(COLUMNNAME_IsDelayedCapture);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Online Access.
     *
     * @param IsOnline Can be accessed online
     */
    public void setIsOnline(boolean IsOnline) {
        set_Value(COLUMNNAME_IsOnline, Boolean.valueOf(IsOnline));
    }

    /**
     * Get Online Access.
     *
     * @return Can be accessed online
     */
    public boolean isOnline() {
        Object oo = get_Value(COLUMNNAME_IsOnline);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Receipt.
     *
     * @param IsReceipt This is a sales transaction (receipt)
     */
    public void setIsReceipt(boolean IsReceipt) {
        set_Value(COLUMNNAME_IsReceipt, Boolean.valueOf(IsReceipt));
    }

    /**
     * Get Receipt.
     *
     * @return This is a sales transaction (receipt)
     */
    public boolean isReceipt() {
        Object oo = get_Value(COLUMNNAME_IsReceipt);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Self-Service.
     *
     * @param IsSelfService This is a Self-Service entry or this entry can be changed via Self-Service
     */
    public void setIsSelfService(boolean IsSelfService) {
        set_Value(COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
    }

    /**
     * Get Self-Service.
     *
     * @return This is a Self-Service entry or this entry can be changed via Self-Service
     */
    public boolean isSelfService() {
        Object oo = get_Value(COLUMNNAME_IsSelfService);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Voided.
     *
     * @param IsVoided Voided
     */
    public void setIsVoided(boolean IsVoided) {
        set_Value(COLUMNNAME_IsVoided, Boolean.valueOf(IsVoided));
    }

    /**
     * Get Voided.
     *
     * @return Voided
     */
    public boolean isVoided() {
        Object oo = get_Value(COLUMNNAME_IsVoided);
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
     * Set Micr.
     *
     * @param Micr Combination of routing no, account and check no
     */
    public void setMicr(String Micr) {
        set_Value(COLUMNNAME_Micr, Micr);
    }

    /**
     * Get Original Transaction ID.
     *
     * @return Original Transaction ID
     */
    public String getOrig_TrxID() {
        return (String) get_Value(COLUMNNAME_Orig_TrxID);
    }

    /**
     * Set Original Transaction ID.
     *
     * @param Orig_TrxID Original Transaction ID
     */
    public void setOrig_TrxID(String Orig_TrxID) {
        set_Value(COLUMNNAME_Orig_TrxID, Orig_TrxID);
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
     * Set Payment amount.
     *
     * @param PayAmt Amount being paid
     */
    public void setPayAmt(BigDecimal PayAmt) {
        set_Value(COLUMNNAME_PayAmt, PayAmt);
    }

    /**
     * Get PO Number.
     *
     * @return Purchase Order Number
     */
    public String getPONum() {
        return (String) get_Value(COLUMNNAME_PONum);
    }

    /**
     * Set PO Number.
     *
     * @param PONum Purchase Order Number
     */
    public void setPONum(String PONum) {
        set_Value(COLUMNNAME_PONum, PONum);
    }

    /**
     * Get Processed.
     *
     * @return The document has been processed
     */
    public boolean isProcessed() {
        Object oo = get_Value(COLUMNNAME_Processed);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
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
     * Get Authorization Code.
     *
     * @return Authorization Code returned
     */
    public String getR_AuthCode() {
        return (String) get_Value(COLUMNNAME_R_AuthCode);
    }

    /**
     * Set Authorization Code.
     *
     * @param R_AuthCode Authorization Code returned
     */
    public void setR_AuthCode(String R_AuthCode) {
        set_ValueNoCheck(COLUMNNAME_R_AuthCode, R_AuthCode);
    }

    /**
     * Get Address verified.
     *
     * @return This address has been verified
     */
    public String getR_AvsAddr() {
        return (String) get_Value(COLUMNNAME_R_AvsAddr);
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
     * Get Zip verified.
     *
     * @return The Zip Code has been verified
     */
    public String getR_AvsZip() {
        return (String) get_Value(COLUMNNAME_R_AvsZip);
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
     * Get CVV Match.
     *
     * @return Credit Card Verification Code Match
     */
    public boolean isR_CVV2Match() {
        Object oo = get_Value(COLUMNNAME_R_CVV2Match);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set CVV Match.
     *
     * @param R_CVV2Match Credit Card Verification Code Match
     */
    public void setR_CVV2Match(boolean R_CVV2Match) {
        set_ValueNoCheck(COLUMNNAME_R_CVV2Match, Boolean.valueOf(R_CVV2Match));
    }

    /**
     * Get Referenced Payment Transaction.
     *
     * @return Referenced Payment Transaction
     */
    public int getRef_PaymentTransaction_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_Ref_PaymentTransaction_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Referenced Payment Transaction.
     *
     * @param Ref_PaymentTransaction_ID Referenced Payment Transaction
     */
    public void setRef_PaymentTransaction_ID(int Ref_PaymentTransaction_ID) {
        if (Ref_PaymentTransaction_ID < 1) set_Value(COLUMNNAME_Ref_PaymentTransaction_ID, null);
        else
            set_Value(COLUMNNAME_Ref_PaymentTransaction_ID, Integer.valueOf(Ref_PaymentTransaction_ID));
    }

    /**
     * Get Info.
     *
     * @return Response info
     */
    public String getR_Info() {
        return (String) get_Value(COLUMNNAME_R_Info);
    }

    /**
     * Set Info.
     *
     * @param R_Info Response info
     */
    public void setR_Info(String R_Info) {
        set_ValueNoCheck(COLUMNNAME_R_Info, R_Info);
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
     * Set Routing No.
     *
     * @param RoutingNo Bank Routing Number
     */
    public void setRoutingNo(String RoutingNo) {
        set_Value(COLUMNNAME_RoutingNo, RoutingNo);
    }

    /**
     * Get Reference.
     *
     * @return Payment reference
     */
    public String getR_PnRef() {
        return (String) get_Value(COLUMNNAME_R_PnRef);
    }

    /**
     * Set Reference.
     *
     * @param R_PnRef Payment reference
     */
    public void setR_PnRef(String R_PnRef) {
        set_ValueNoCheck(COLUMNNAME_R_PnRef, R_PnRef);
    }

    /**
     * Get Response Message.
     *
     * @return Response message
     */
    public String getR_RespMsg() {
        return (String) get_Value(COLUMNNAME_R_RespMsg);
    }

    /**
     * Set Response Message.
     *
     * @param R_RespMsg Response message
     */
    public void setR_RespMsg(String R_RespMsg) {
        set_ValueNoCheck(COLUMNNAME_R_RespMsg, R_RespMsg);
    }

    /**
     * Get Result.
     *
     * @return Result of transmission
     */
    public String getR_Result() {
        return (String) get_Value(COLUMNNAME_R_Result);
    }

    /**
     * Set Result.
     *
     * @param R_Result Result of transmission
     */
    public void setR_Result(String R_Result) {
        set_ValueNoCheck(COLUMNNAME_R_Result, R_Result);
    }

    /**
     * Get Void Message.
     *
     * @return Void Message
     */
    public String getR_VoidMsg() {
        return (String) get_Value(COLUMNNAME_R_VoidMsg);
    }

    /**
     * Set Void Message.
     *
     * @param R_VoidMsg Void Message
     */
    public void setR_VoidMsg(String R_VoidMsg) {
        set_Value(COLUMNNAME_R_VoidMsg, R_VoidMsg);
    }

    /**
     * Get Swift code.
     *
     * @return Swift Code or BIC
     */
    public String getSwiftCode() {
        return (String) get_Value(COLUMNNAME_SwiftCode);
    }

    /**
     * Set Swift code.
     *
     * @param SwiftCode Swift Code or BIC
     */
    public void setSwiftCode(String SwiftCode) {
        set_Value(COLUMNNAME_SwiftCode, SwiftCode);
    }

    /**
     * Get Tax Amount.
     *
     * @return Tax Amount for a document
     */
    public BigDecimal getTaxAmt() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_TaxAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Tax Amount.
     *
     * @param TaxAmt Tax Amount for a document
     */
    public void setTaxAmt(BigDecimal TaxAmt) {
        set_Value(COLUMNNAME_TaxAmt, TaxAmt);
    }

    /**
     * Get Tender type.
     *
     * @return Method of Payment
     */
    public String getTenderType() {
        return (String) get_Value(COLUMNNAME_TenderType);
    }

    /**
     * Set Tender type.
     *
     * @param TenderType Method of Payment
     */
    public void setTenderType(String TenderType) {

        set_Value(COLUMNNAME_TenderType, TenderType);
    }

    /**
     * Get Transaction Type.
     *
     * @return Type of credit card transaction
     */
    public String getTrxType() {
        return (String) get_Value(COLUMNNAME_TrxType);
    }

    /**
     * Set Transaction Type.
     *
     * @param TrxType Type of credit card transaction
     */
    public void setTrxType(String TrxType) {

        set_Value(COLUMNNAME_TrxType, TrxType);
    }

    /**
     * Get User Element List 1.
     *
     * @return User defined list element #1
     */
    public int getUser1_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_User1_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get User Element List 2.
     *
     * @return User defined list element #2
     */
    public int getUser2_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_User2_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Voice authorization code.
     *
     * @return Voice Authorization Code from credit card company
     */
    public String getVoiceAuthCode() {
        return (String) get_Value(COLUMNNAME_VoiceAuthCode);
    }

    /**
     * Set Voice authorization code.
     *
     * @param VoiceAuthCode Voice Authorization Code from credit card company
     */
    public void setVoiceAuthCode(String VoiceAuthCode) {
        set_Value(COLUMNNAME_VoiceAuthCode, VoiceAuthCode);
    }

    @Override
    public int getTableId() {
        return I_C_PaymentTransaction.Table_ID;
    }
}
