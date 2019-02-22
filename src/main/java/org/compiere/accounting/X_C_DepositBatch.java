package org.compiere.accounting;

import org.compiere.model.I_C_DepositBatch;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_DepositBatch
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_DepositBatch extends PO implements I_C_DepositBatch, I_Persistent {

    /**
     * Drafted = DR
     */
    public static final String DOCSTATUS_Drafted = "DR";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_DepositBatch(Properties ctx, int C_DepositBatch_ID) {
        super(ctx, C_DepositBatch_ID);
        /**
         * if (C_DepositBatch_ID == 0) { setC_BankAccount_ID (0); setC_DepositBatch_ID (0);
         * setC_DocType_ID (0); setDateDeposit (new Timestamp( System.currentTimeMillis() )); // @#Date@
         * setDateDoc (new Timestamp( System.currentTimeMillis() )); // @#Date@ setDepositAmt
         * (Env.ZERO); setDocStatus (null); // DR setDocumentNo (null); setProcessed (false); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_DepositBatch(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_C_DepositBatch[").append(getId()).append("]");
        return sb.toString();
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
     * Get Deposit Batch.
     *
     * @return Deposit Batch
     */
    public int getC_DepositBatch_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_DepositBatch_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Deposit Batch.
     *
     * @param C_DepositBatch_ID Deposit Batch
     */
    public void setC_DepositBatch_ID(int C_DepositBatch_ID) {
        if (C_DepositBatch_ID < 1) set_ValueNoCheck(COLUMNNAME_C_DepositBatch_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_DepositBatch_ID, Integer.valueOf(C_DepositBatch_ID));
    }

    /**
     * Get Document Type.
     *
     * @return Document type or rules
     */
    public int getC_DocType_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_DocType_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Document Type.
     *
     * @param C_DocType_ID Document type or rules
     */
    public void setC_DocType_ID(int C_DocType_ID) {
        if (C_DocType_ID < 0) set_Value(COLUMNNAME_C_DocType_ID, null);
        else set_Value(COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
    }

    /**
     * Get Deposit Date.
     *
     * @return Deposit Date
     */
    public Timestamp getDateDeposit() {
        return (Timestamp) get_Value(COLUMNNAME_DateDeposit);
    }

    /**
     * Set Deposit Date.
     *
     * @param DateDeposit Deposit Date
     */
    public void setDateDeposit(Timestamp DateDeposit) {
        set_Value(COLUMNNAME_DateDeposit, DateDeposit);
    }

    /**
     * Get Document Date.
     *
     * @return Date of the Document
     */
    public Timestamp getDateDoc() {
        return (Timestamp) get_Value(COLUMNNAME_DateDoc);
    }

    /**
     * Set Document Date.
     *
     * @param DateDoc Date of the Document
     */
    public void setDateDoc(Timestamp DateDoc) {
        set_Value(COLUMNNAME_DateDoc, DateDoc);
    }

    /**
     * Get Deposit Amount.
     *
     * @return Deposit Amount
     */
    public BigDecimal getDepositAmt() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_DepositAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Deposit Amount.
     *
     * @param DepositAmt Deposit Amount
     */
    public void setDepositAmt(BigDecimal DepositAmt) {
        set_Value(COLUMNNAME_DepositAmt, DepositAmt);
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
     * Set Document Status.
     *
     * @param DocStatus The current status of the document
     */
    public void setDocStatus(String DocStatus) {

        set_Value(COLUMNNAME_DocStatus, DocStatus);
    }

    /**
     * Get Document No.
     *
     * @return Document sequence number of the document
     */
    public String getDocumentNo() {
        return (String) get_Value(COLUMNNAME_DocumentNo);
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
     * Set Process Now.
     *
     * @param Processing Process Now
     */
    public void setProcessing(boolean Processing) {
        set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
    }

    @Override
    public int getTableId() {
        return I_C_DepositBatch.Table_ID;
    }
}
