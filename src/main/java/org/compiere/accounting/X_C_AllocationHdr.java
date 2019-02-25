package org.compiere.accounting;

import org.compiere.model.I_C_AllocationHdr;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_AllocationHdr
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_AllocationHdr extends PO implements I_C_AllocationHdr {

    /**
     * Complete = CO
     */
    public static final String DOCACTION_Complete = "CO";
    /**
     * Close = CL
     */
    public static final String DOCACTION_Close = "CL";
    /**
     * <None> = --
     */
    public static final String DOCACTION_None = "--";
    /**
     * Prepare = PR
     */
    public static final String DOCACTION_Prepare = "PR";
    /**
     * Drafted = DR
     */
    public static final String DOCSTATUS_Drafted = "DR";
    /**
     * Completed = CO
     */
    public static final String DOCSTATUS_Completed = "CO";
    /**
     * Approved = AP
     */
    public static final String DOCSTATUS_Approved = "AP";
    /**
     * Not Approved = NA
     */
    public static final String DOCSTATUS_NotApproved = "NA";
    /**
     * Voided = VO
     */
    public static final String DOCSTATUS_Voided = "VO";
    /**
     * Invalid = IN
     */
    public static final String DOCSTATUS_Invalid = "IN";
    /**
     * Reversed = RE
     */
    public static final String DOCSTATUS_Reversed = "RE";
    /**
     * Closed = CL
     */
    public static final String DOCSTATUS_Closed = "CL";
    /**
     * In Progress = IP
     */
    public static final String DOCSTATUS_InProgress = "IP";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_AllocationHdr(Properties ctx, int C_AllocationHdr_ID) {
        super(ctx, C_AllocationHdr_ID);
        /**
         * if (C_AllocationHdr_ID == 0) { setApprovalAmt (Env.ZERO); setC_AllocationHdr_ID (0);
         * setCurrencyId (0); setDateAcct (new Timestamp( System.currentTimeMillis() )); setDateTrx
         * (new Timestamp( System.currentTimeMillis() )); setDocAction (null); // CO setDocStatus
         * (null); // DR setDocumentNo (null); setIsApproved (false); setIsManual (false); setPosted
         * (false); setProcessed (false); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_AllocationHdr(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_C_AllocationHdr[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Approval Amount.
     *
     * @return Document Approval Amount
     */
    public BigDecimal getApprovalAmt() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_ApprovalAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Approval Amount.
     *
     * @param ApprovalAmt Document Approval Amount
     */
    public void setApprovalAmt(BigDecimal ApprovalAmt) {
        set_Value(COLUMNNAME_ApprovalAmt, ApprovalAmt);
    }

    /**
     * Get Allocation.
     *
     * @return Payment allocation
     */
    public int getC_AllocationHdr_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_AllocationHdr_ID);
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
     * Set Currency.
     *
     * @param C_Currency_ID The Currency for this record
     */
    public void setC_Currency_ID(int C_Currency_ID) {
        if (C_Currency_ID < 1) set_Value(COLUMNNAME_C_Currency_ID, null);
        else set_Value(COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
    }

    /**
     * Set Document Type.
     *
     * @param C_DocType_ID Document type or rules
     */
    public void setC_DocType_ID(int C_DocType_ID) {
        if (C_DocType_ID < 0) set_ValueNoCheck(COLUMNNAME_C_DocType_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
    }

    /**
     * Get Account Date.
     *
     * @return Accounting Date
     */
    public Timestamp getDateAcct() {
        return (Timestamp) getValue(COLUMNNAME_DateAcct);
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
     * Get Transaction Date.
     *
     * @return Transaction Date
     */
    public Timestamp getDateTrx() {
        return (Timestamp) getValue(COLUMNNAME_DateTrx);
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
        return (String) getValue(COLUMNNAME_Description);
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
     * Get Document Action.
     *
     * @return The targeted status of the document
     */
    public String getDocAction() {
        return (String) getValue(COLUMNNAME_DocAction);
    }

    /**
     * Set Document Action.
     *
     * @param DocAction The targeted status of the document
     */
    public void setDocAction(String DocAction) {

        set_Value(COLUMNNAME_DocAction, DocAction);
    }

    /**
     * Get Document Status.
     *
     * @return The current status of the document
     */
    public String getDocStatus() {
        return (String) getValue(COLUMNNAME_DocStatus);
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
        return (String) getValue(COLUMNNAME_DocumentNo);
    }

    /**
     * Set Document No.
     *
     * @param DocumentNo Document sequence number of the document
     */
    public void setDocumentNo(String DocumentNo) {
        set_Value(COLUMNNAME_DocumentNo, DocumentNo);
    }

    /**
     * Set Approved.
     *
     * @param IsApproved Indicates if this document requires approval
     */
    public void setIsApproved(boolean IsApproved) {
        set_Value(COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
    }

    /**
     * Get Approved.
     *
     * @return Indicates if this document requires approval
     */
    public boolean isApproved() {
        Object oo = getValue(COLUMNNAME_IsApproved);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Manual.
     *
     * @param IsManual This is a manual process
     */
    public void setIsManual(boolean IsManual) {
        set_Value(COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
    }

    /**
     * Get Posted.
     *
     * @return Posting status
     */
    public boolean isPosted() {
        Object oo = getValue(COLUMNNAME_Posted);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Posted.
     *
     * @param Posted Posting status
     */
    public void setPosted(boolean Posted) {
        set_Value(COLUMNNAME_Posted, Boolean.valueOf(Posted));
    }

    /**
     * Get Processed.
     *
     * @return The document has been processed
     */
    public boolean isProcessed() {
        Object oo = getValue(COLUMNNAME_Processed);
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
     * Set Process Now.
     *
     * @param Processing Process Now
     */
    public void setProcessing(boolean Processing) {
        set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
    }

    /**
     * Set Reversal ID.
     *
     * @param Reversal_ID ID of document reversal
     */
    public void setReversal_ID(int Reversal_ID) {
        if (Reversal_ID < 1) set_Value(COLUMNNAME_Reversal_ID, null);
        else set_Value(COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
    }

    @Override
    public int getTableId() {
        return I_C_AllocationHdr.Table_ID;
    }
}
