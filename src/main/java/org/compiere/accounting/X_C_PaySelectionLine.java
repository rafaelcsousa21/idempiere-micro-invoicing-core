package org.compiere.accounting;

import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_PaySelectionLine
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_PaySelectionLine extends PO implements I_C_PaySelectionLine, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_PaySelectionLine(Properties ctx, int C_PaySelectionLine_ID) {
        super(ctx, C_PaySelectionLine_ID);
        /**
         * if (C_PaySelectionLine_ID == 0) { setC_Invoice_ID (0); setC_PaySelection_ID (0);
         * setC_PaySelectionLine_ID (0); setDifferenceAmt (Env.ZERO); setDiscountAmt (Env.ZERO);
         * setIsManual (false); setIsSOTrx (false); setLine (0); // @SQL=SELECT NVL(MAX(Line),0)+10 AS
         * DefaultValue FROM C_PaySelectionLine WHERE C_PaySelection_ID=@C_PaySelection_ID@ setOpenAmt
         * (Env.ZERO); setPayAmt (Env.ZERO); setPaymentRule (null); // S setProcessed (false); // N
         * setWriteOffAmt (Env.ZERO); // 0 }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_PaySelectionLine(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_C_PaySelectionLine[").append(getId()).append("]");
        return sb.toString();
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
     * Get Pay Selection Check.
     *
     * @return Payment Selection Check
     */
    public int getC_PaySelectionCheck_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_PaySelectionCheck_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Pay Selection Check.
     *
     * @param C_PaySelectionCheck_ID Payment Selection Check
     */
    public void setC_PaySelectionCheck_ID(int C_PaySelectionCheck_ID) {
        if (C_PaySelectionCheck_ID < 1) set_Value(COLUMNNAME_C_PaySelectionCheck_ID, null);
        else set_Value(COLUMNNAME_C_PaySelectionCheck_ID, Integer.valueOf(C_PaySelectionCheck_ID));
    }

    /**
     * Get Payment Selection.
     *
     * @return Payment Selection
     */
    public int getC_PaySelection_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_PaySelection_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Payment Selection.
     *
     * @param C_PaySelection_ID Payment Selection
     */
    public void setC_PaySelection_ID(int C_PaySelection_ID) {
        if (C_PaySelection_ID < 1) set_ValueNoCheck(COLUMNNAME_C_PaySelection_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_PaySelection_ID, Integer.valueOf(C_PaySelection_ID));
    }

    /**
     * Get Payment Selection Line.
     *
     * @return Payment Selection Line
     */
    public int getC_PaySelectionLine_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_PaySelectionLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Difference.
     *
     * @return Difference Amount
     */
    public BigDecimal getDifferenceAmt() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_DifferenceAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Difference.
     *
     * @param DifferenceAmt Difference Amount
     */
    public void setDifferenceAmt(BigDecimal DifferenceAmt) {
        set_ValueNoCheck(COLUMNNAME_DifferenceAmt, DifferenceAmt);
    }

    /**
     * Get Discount Amount.
     *
     * @return Calculated amount of discount
     */
    public BigDecimal getDiscountAmt() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_DiscountAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Discount Amount.
     *
     * @param DiscountAmt Calculated amount of discount
     */
    public void setDiscountAmt(BigDecimal DiscountAmt) {
        set_ValueNoCheck(COLUMNNAME_DiscountAmt, DiscountAmt);
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
     * Set Line No.
     *
     * @param Line Unique line for this document
     */
    public void setLine(int Line) {
        set_Value(COLUMNNAME_Line, Integer.valueOf(Line));
    }

    /**
     * Get Open Amount.
     *
     * @return Open item amount
     */
    public BigDecimal getOpenAmt() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_OpenAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Open Amount.
     *
     * @param OpenAmt Open item amount
     */
    public void setOpenAmt(BigDecimal OpenAmt) {
        set_ValueNoCheck(COLUMNNAME_OpenAmt, OpenAmt);
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
     * Get Payment Rule.
     *
     * @return How you pay the invoice
     */
    public String getPaymentRule() {
        return (String) get_Value(COLUMNNAME_PaymentRule);
    }

    /**
     * Set Payment Rule.
     *
     * @param PaymentRule How you pay the invoice
     */
    public void setPaymentRule(String PaymentRule) {

        set_Value(COLUMNNAME_PaymentRule, PaymentRule);
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
     * Get Write-off Amount.
     *
     * @return Amount to write-off
     */
    public BigDecimal getWriteOffAmt() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_WriteOffAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Write-off Amount.
     *
     * @param WriteOffAmt Amount to write-off
     */
    public void setWriteOffAmt(BigDecimal WriteOffAmt) {
        set_ValueNoCheck(COLUMNNAME_WriteOffAmt, WriteOffAmt);
    }

    @Override
    public int getTableId() {
        return I_C_PaySelectionLine.Table_ID;
    }
}
