package org.compiere.accounting;

import org.compiere.model.I_I_Payment;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

public class X_I_Payment extends PO implements I_I_Payment {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_I_Payment(Properties ctx, int I_Payment_ID) {
        super(ctx, I_Payment_ID);
        /** if (I_Payment_ID == 0) { setI_IsImported (false); setI_Payment_ID (0); } */
    }

    /**
     * Load Constructor
     */
    public X_I_Payment(Properties ctx, ResultSet rs) {
        super(ctx, rs);
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
        StringBuffer sb = new StringBuffer("X_I_Payment[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Account No.
     *
     * @return Account Number
     */
    public String getAccountNo() {
        return (String) getValue(COLUMNNAME_AccountNo);
    }

    /**
     * Get Account City.
     *
     * @return City or the Credit Card or Account Holder
     */
    public String getA_City() {
        return (String) getValue(COLUMNNAME_A_City);
    }

    /**
     * Get Account Country.
     *
     * @return Country
     */
    public String getA_Country() {
        return (String) getValue(COLUMNNAME_A_Country);
    }

    /**
     * Get Account EMail.
     *
     * @return Email Address
     */
    public String getA_EMail() {
        return (String) getValue(COLUMNNAME_A_EMail);
    }

    /**
     * Get Driver License.
     *
     * @return Payment Identification - Driver License
     */
    public String getA_Ident_DL() {
        return (String) getValue(COLUMNNAME_A_Ident_DL);
    }

    /**
     * Get Social Security No.
     *
     * @return Payment Identification - Social Security No
     */
    public String getA_Ident_SSN() {
        return (String) getValue(COLUMNNAME_A_Ident_SSN);
    }

    /**
     * Get Account Name.
     *
     * @return Name on Credit Card or Account holder
     */
    public String getA_Name() {
        return (String) getValue(COLUMNNAME_A_Name);
    }

    /**
     * Get Account State.
     *
     * @return State of the Credit Card or Account holder
     */
    public String getA_State() {
        return (String) getValue(COLUMNNAME_A_State);
    }

    /**
     * Get Account Street.
     *
     * @return Street address of the Credit Card or Account holder
     */
    public String getA_Street() {
        return (String) getValue(COLUMNNAME_A_Street);
    }

    /**
     * Get Account Zip/Postal.
     *
     * @return Zip Code of the Credit Card or Account Holder
     */
    public String getA_Zip() {
        return (String) getValue(COLUMNNAME_A_Zip);
    }

    /**
     * Get Bank Account.
     *
     * @return Account at the Bank
     */
    public int getC_BankAccount_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_BankAccount_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Business Partner .
     *
     * @return Identifies a Business Partner
     */
    public int getC_BPartner_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_BPartner_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Charge.
     *
     * @return Additional document charges
     */
    public int getC_Charge_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Charge_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Currency.
     *
     * @return The Currency for this record
     */
    public int getC_Currency_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Currency_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Document Type.
     *
     * @return Document type or rules
     */
    public int getC_DocType_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_DocType_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Charge amount.
     *
     * @return Charge Amount
     */
    public BigDecimal getChargeAmt() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_ChargeAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Get Check No.
     *
     * @return Check Number
     */
    public String getCheckNo() {
        return (String) getValue(COLUMNNAME_CheckNo);
    }

    /**
     * Get Invoice.
     *
     * @return Invoice Identifier
     */
    public int getC_Invoice_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Invoice_ID);
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
     * Get Exp. Month.
     *
     * @return Expiry Month
     */
    public int getCreditCardExpMM() {
        Integer ii = (Integer) getValue(COLUMNNAME_CreditCardExpMM);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Exp. Year.
     *
     * @return Expiry Year
     */
    public int getCreditCardExpYY() {
        Integer ii = (Integer) getValue(COLUMNNAME_CreditCardExpYY);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Number.
     *
     * @return Credit Card Number
     */
    public String getCreditCardNumber() {
        return (String) getValue(COLUMNNAME_CreditCardNumber);
    }

    /**
     * Get Credit Card.
     *
     * @return Credit Card (Visa, MC, AmEx)
     */
    public String getCreditCardType() {
        return (String) getValue(COLUMNNAME_CreditCardType);
    }

    /**
     * Get Verification Code.
     *
     * @return Credit Card Verification code on credit card
     */
    public String getCreditCardVV() {
        return (String) getValue(COLUMNNAME_CreditCardVV);
    }

    /**
     * Get Transaction Date.
     *
     * @return Transaction Date
     */
    public Timestamp getDateTrx() {
        return (Timestamp) getValue(COLUMNNAME_DateTrx);
    }

    /**
     * Get Discount Amount.
     *
     * @return Calculated amount of discount
     */
    public BigDecimal getDiscountAmt() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_DiscountAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Get Document No.
     *
     * @return Document sequence number of the document
     */
    public String getDocumentNo() {
        return (String) getValue(COLUMNNAME_DocumentNo);
    }

    /**
     * Get IBAN.
     *
     * @return International Bank Account Number
     */
    public String getIBAN() {
        return (String) getValue(COLUMNNAME_IBAN);
    }

    /**
     * Set Imported.
     *
     * @param I_IsImported Has this import been processed
     */
    public void setI_IsImported(boolean I_IsImported) {
        set_Value(COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
    }

    /**
     * Get Micr.
     *
     * @return Combination of routing no, account and check no
     */
    public String getMicr() {
        return (String) getValue(COLUMNNAME_Micr);
    }

    /**
     * Get Original Transaction ID.
     *
     * @return Original Transaction ID
     */
    public String getOrig_TrxID() {
        return (String) getValue(COLUMNNAME_Orig_TrxID);
    }

    /**
     * Get Payment amount.
     *
     * @return Amount being paid
     */
    public BigDecimal getPayAmt() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_PayAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Get PO Number.
     *
     * @return Purchase Order Number
     */
    public String getPONum() {
        return (String) getValue(COLUMNNAME_PONum);
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
        return (String) getValue(COLUMNNAME_R_AuthCode);
    }

    /**
     * Get Info.
     *
     * @return Response info
     */
    public String getR_Info() {
        return (String) getValue(COLUMNNAME_R_Info);
    }

    /**
     * Get Routing No.
     *
     * @return Bank Routing Number
     */
    public String getRoutingNo() {
        return (String) getValue(COLUMNNAME_RoutingNo);
    }

    /**
     * Get Reference.
     *
     * @return Payment reference
     */
    public String getR_PnRef() {
        return (String) getValue(COLUMNNAME_R_PnRef);
    }

    /**
     * Get Response Message.
     *
     * @return Response message
     */
    public String getR_RespMsg() {
        return (String) getValue(COLUMNNAME_R_RespMsg);
    }

    /**
     * Get Result.
     *
     * @return Result of transmission
     */
    public String getR_Result() {
        return (String) getValue(COLUMNNAME_R_Result);
    }

    /**
     * Get Swift code.
     *
     * @return Swift Code or BIC
     */
    public String getSwiftCode() {
        return (String) getValue(COLUMNNAME_SwiftCode);
    }

    /**
     * Get Swipe.
     *
     * @return Track 1 and 2 of the Credit Card
     */
    public String getSwipe() {
        return (String) getValue(COLUMNNAME_Swipe);
    }

    /**
     * Get Tax Amount.
     *
     * @return Tax Amount for a document
     */
    public BigDecimal getTaxAmt() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_TaxAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Get Tender type.
     *
     * @return Method of Payment
     */
    public String getTenderType() {
        return (String) getValue(COLUMNNAME_TenderType);
    }

    /**
     * Get Transaction Type.
     *
     * @return Type of credit card transaction
     */
    public String getTrxType() {
        return (String) getValue(COLUMNNAME_TrxType);
    }

    /**
     * Get Voice authorization code.
     *
     * @return Voice Authorization Code from credit card company
     */
    public String getVoiceAuthCode() {
        return (String) getValue(COLUMNNAME_VoiceAuthCode);
    }

    /**
     * Get Write-off Amount.
     *
     * @return Amount to write-off
     */
    public BigDecimal getWriteOffAmt() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_WriteOffAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    @Override
    public int getTableId() {
        return I_I_Payment.Table_ID;
    }
}
