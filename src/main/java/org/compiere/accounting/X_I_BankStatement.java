package org.compiere.accounting;

import org.compiere.model.I_I_BankStatement;
import org.compiere.orm.BasePOName;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

public class X_I_BankStatement extends BasePOName implements I_I_BankStatement, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_I_BankStatement(Properties ctx, int I_BankStatement_ID) {
        super(ctx, I_BankStatement_ID);
        /** if (I_BankStatement_ID == 0) { setI_BankStatement_ID (0); setI_IsImported (false); } */
    }

    /**
     * Load Constructor
     */
    public X_I_BankStatement(Properties ctx, ResultSet rs) {
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

    @Override
    public int getTableId() {
        return Table_ID;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer("X_I_BankStatement[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Bank Account No.
     *
     * @return Bank Account Number
     */
    public String getBankAccountNo() {
        return (String) get_Value(COLUMNNAME_BankAccountNo);
    }

    /**
     * Set Bank Account No.
     *
     * @param BankAccountNo Bank Account Number
     */
    public void setBankAccountNo(String BankAccountNo) {
        set_Value(COLUMNNAME_BankAccountNo, BankAccountNo);
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
     * Set Bank Statement.
     *
     * @param C_BankStatement_ID Bank Statement of account
     */
    public void setC_BankStatement_ID(int C_BankStatement_ID) {
        if (C_BankStatement_ID < 1) set_Value(COLUMNNAME_C_BankStatement_ID, null);
        else set_Value(COLUMNNAME_C_BankStatement_ID, Integer.valueOf(C_BankStatement_ID));
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
     * Set Bank statement line.
     *
     * @param C_BankStatementLine_ID Line on a statement from this Bank
     */
    public void setC_BankStatementLine_ID(int C_BankStatementLine_ID) {
        if (C_BankStatementLine_ID < 1) set_Value(COLUMNNAME_C_BankStatementLine_ID, null);
        else set_Value(COLUMNNAME_C_BankStatementLine_ID, Integer.valueOf(C_BankStatementLine_ID));
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
     * Set Charge amount.
     *
     * @param ChargeAmt Charge Amount
     */
    public void setChargeAmt(BigDecimal ChargeAmt) {
        set_Value(COLUMNNAME_ChargeAmt, ChargeAmt);
    }

    /**
     * Set Charge Name.
     *
     * @param ChargeName Name of the Charge
     */
    public void setChargeName(String ChargeName) {
        set_Value(COLUMNNAME_ChargeName, ChargeName);
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
     * Get Account Date.
     *
     * @return Accounting Date
     */
    public Timestamp getDateAcct() {
        return (Timestamp) get_Value(COLUMNNAME_DateAcct);
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
     * Get EFT Amount.
     *
     * @return Electronic Funds Transfer Amount
     */
    public BigDecimal getEftAmt() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_EftAmt);
        if (bd == null) return Env.ZERO;
        return bd;
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
     * Get EFT Check No.
     *
     * @return Electronic Funds Transfer Check No
     */
    public String getEftCheckNo() {
        return (String) get_Value(COLUMNNAME_EftCheckNo);
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
     * Get EFT Currency.
     *
     * @return Electronic Funds Transfer Currency
     */
    public String getEftCurrency() {
        return (String) get_Value(COLUMNNAME_EftCurrency);
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
     * Get EFT Memo.
     *
     * @return Electronic Funds Transfer Memo
     */
    public String getEftMemo() {
        return (String) get_Value(COLUMNNAME_EftMemo);
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
     * Get EFT Payee.
     *
     * @return Electronic Funds Transfer Payee information
     */
    public String getEftPayee() {
        return (String) get_Value(COLUMNNAME_EftPayee);
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
     * Get EFT Payee Account.
     *
     * @return Electronic Funds Transfer Payee Account Information
     */
    public String getEftPayeeAccount() {
        return (String) get_Value(COLUMNNAME_EftPayeeAccount);
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
     * Get EFT Reference.
     *
     * @return Electronic Funds Transfer Reference
     */
    public String getEftReference() {
        return (String) get_Value(COLUMNNAME_EftReference);
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
     * Get EFT Statement Date.
     *
     * @return Electronic Funds Transfer Statement Date
     */
    public Timestamp getEftStatementDate() {
        return (Timestamp) get_Value(COLUMNNAME_EftStatementDate);
    }

    /**
     * Set EFT Statement Date.
     *
     * @param EftStatementDate Electronic Funds Transfer Statement Date
     */
    public void setEftStatementDate(Timestamp EftStatementDate) {
        set_Value(COLUMNNAME_EftStatementDate, EftStatementDate);
    }

    /**
     * Get EFT Statement Line Date.
     *
     * @return Electronic Funds Transfer Statement Line Date
     */
    public Timestamp getEftStatementLineDate() {
        return (Timestamp) get_Value(COLUMNNAME_EftStatementLineDate);
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
     * Get EFT Statement Reference.
     *
     * @return Electronic Funds Transfer Statement Reference
     */
    public String getEftStatementReference() {
        return (String) get_Value(COLUMNNAME_EftStatementReference);
    }

    /**
     * Set EFT Statement Reference.
     *
     * @param EftStatementReference Electronic Funds Transfer Statement Reference
     */
    public void setEftStatementReference(String EftStatementReference) {
        set_Value(COLUMNNAME_EftStatementReference, EftStatementReference);
    }

    /**
     * Get EFT Trx ID.
     *
     * @return Electronic Funds Transfer Transaction ID
     */
    public String getEftTrxID() {
        return (String) get_Value(COLUMNNAME_EftTrxID);
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
     * Get EFT Trx Type.
     *
     * @return Electronic Funds Transfer Transaction Type
     */
    public String getEftTrxType() {
        return (String) get_Value(COLUMNNAME_EftTrxType);
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
     * Get EFT Effective Date.
     *
     * @return Electronic Funds Transfer Valuta (effective) Date
     */
    public Timestamp getEftValutaDate() {
        return (Timestamp) get_Value(COLUMNNAME_EftValutaDate);
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
     * Set Imported.
     *
     * @param I_IsImported Has this import been processed
     */
    public void setI_IsImported(boolean I_IsImported) {
        set_Value(COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
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
     * Set Interest Amount.
     *
     * @param InterestAmt Interest Amount
     */
    public void setInterestAmt(BigDecimal InterestAmt) {
        set_Value(COLUMNNAME_InterestAmt, InterestAmt);
    }

    /**
     * Set ISO Currency Code.
     *
     * @param ISO_Code Three letter ISO 4217 Code of the Currency
     */
    public void setISO_Code(String ISO_Code) {
        set_Value(COLUMNNAME_ISO_Code, ISO_Code);
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
     * Get Line Description.
     *
     * @return Description of the Line
     */
    public String getLineDescription() {
        return (String) get_Value(COLUMNNAME_LineDescription);
    }

    /**
     * Get Memo.
     *
     * @return Memo Text
     */
    public String getMemo() {
        return (String) get_Value(COLUMNNAME_Memo);
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
     * Set Processed.
     *
     * @param Processed The document has been processed
     */
    public void setProcessed(boolean Processed) {
        set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
    }

    /**
     * Get Reference No.
     *
     * @return Your customer or vendor number at the Business Partner's site
     */
    public String getReferenceNo() {
        return (String) get_Value(COLUMNNAME_ReferenceNo);
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
     * Get Statement date.
     *
     * @return Date of the statement
     */
    public Timestamp getStatementDate() {
        return (Timestamp) get_Value(COLUMNNAME_StatementDate);
    }

    /**
     * Set Statement date.
     *
     * @param StatementDate Date of the statement
     */
    public void setStatementDate(Timestamp StatementDate) {
        set_Value(COLUMNNAME_StatementDate, StatementDate);
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
     * Set Statement Line Date.
     *
     * @param StatementLineDate Date of the Statement Line
     */
    public void setStatementLineDate(Timestamp StatementLineDate) {
        set_Value(COLUMNNAME_StatementLineDate, StatementLineDate);
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
     * Set Statement amount.
     *
     * @param StmtAmt Statement Amount
     */
    public void setStmtAmt(BigDecimal StmtAmt) {
        set_Value(COLUMNNAME_StmtAmt, StmtAmt);
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
     * Set Transaction Amount.
     *
     * @param TrxAmt Amount of a transaction
     */
    public void setTrxAmt(BigDecimal TrxAmt) {
        set_Value(COLUMNNAME_TrxAmt, TrxAmt);
    }

    /**
     * Get Effective date.
     *
     * @return Date when money is available
     */
    public Timestamp getValutaDate() {
        return (Timestamp) get_Value(COLUMNNAME_ValutaDate);
    }

    /**
     * Set Effective date.
     *
     * @param ValutaDate Date when money is available
     */
    public void setValutaDate(Timestamp ValutaDate) {
        set_Value(COLUMNNAME_ValutaDate, ValutaDate);
    }
}
