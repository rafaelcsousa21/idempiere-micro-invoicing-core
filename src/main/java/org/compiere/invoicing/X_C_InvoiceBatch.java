package org.compiere.invoicing;

import org.compiere.model.I_C_InvoiceBatch;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_InvoiceBatch
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_InvoiceBatch extends PO implements I_C_InvoiceBatch, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_InvoiceBatch(Properties ctx, int C_InvoiceBatch_ID) {
        super(ctx, C_InvoiceBatch_ID);
        /**
         * if (C_InvoiceBatch_ID == 0) { setC_Currency_ID (0); // @$C_Currency_ID@ setC_InvoiceBatch_ID
         * (0); setControlAmt (Env.ZERO); // 0 setDateDoc (new Timestamp( System.currentTimeMillis() ));
         * // @#Date@ setDocumentAmt (Env.ZERO); setDocumentNo (null); setIsSOTrx (false); // N
         * setProcessed (false); setSalesRep_ID (0); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_InvoiceBatch(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_C_InvoiceBatch[").append(getId()).append("]");
        return sb.toString();
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
     * Get Invoice Batch.
     *
     * @return Expense Invoice Batch Header
     */
    public int getC_InvoiceBatch_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_InvoiceBatch_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Control Amount.
     *
     * @return If not zero, the Debit amount of the document must be equal this amount
     */
    public BigDecimal getControlAmt() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_ControlAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Control Amount.
     *
     * @param ControlAmt If not zero, the Debit amount of the document must be equal this amount
     */
    public void setControlAmt(BigDecimal ControlAmt) {
        set_Value(COLUMNNAME_ControlAmt, ControlAmt);
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
     * Get Description.
     *
     * @return Optional short description of the record
     */
    public String getDescription() {
        return (String) get_Value(COLUMNNAME_Description);
    }

    /**
     * Get Document Amt.
     *
     * @return Document Amount
     */
    public BigDecimal getDocumentAmt() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_DocumentAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Document Amt.
     *
     * @param DocumentAmt Document Amount
     */
    public void setDocumentAmt(BigDecimal DocumentAmt) {
        set_ValueNoCheck(COLUMNNAME_DocumentAmt, DocumentAmt);
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
     * Set Sales Transaction.
     *
     * @param IsSOTrx This is a Sales Transaction
     */
    public void setIsSOTrx(boolean IsSOTrx) {
        set_Value(COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
    }

    /**
     * Get Sales Transaction.
     *
     * @return This is a Sales Transaction
     */
    public boolean isSOTrx() {
        Object oo = get_Value(COLUMNNAME_IsSOTrx);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
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
     * Get Sales Representative.
     *
     * @return Sales Representative or Company Agent
     */
    public int getSalesRep_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_SalesRep_ID);
        if (ii == null) return 0;
        return ii;
    }

    @Override
    public int getTableId() {
        return I_C_InvoiceBatch.Table_ID;
    }
}
