package org.compiere.invoicing;

import org.compiere.model.I_C_InvoicePaySchedule;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_InvoicePaySchedule
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_InvoicePaySchedule extends PO implements I_C_InvoicePaySchedule, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_InvoicePaySchedule(Properties ctx, int C_InvoicePaySchedule_ID) {
        super(ctx, C_InvoicePaySchedule_ID);
        /**
         * if (C_InvoicePaySchedule_ID == 0) { setC_Invoice_ID (0); setC_InvoicePaySchedule_ID (0);
         * setDiscountAmt (Env.ZERO); setDiscountDate (new Timestamp( System.currentTimeMillis() ));
         * setDueAmt (Env.ZERO); setDueDate (new Timestamp( System.currentTimeMillis() )); setIsValid
         * (false); setProcessed (false); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_InvoicePaySchedule(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_C_InvoicePaySchedule[").append(getId()).append("]");
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
        if (C_Invoice_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Invoice_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
    }

    /**
     * Get Payment Schedule.
     *
     * @return Payment Schedule Template
     */
    public int getC_PaySchedule_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_PaySchedule_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Payment Schedule.
     *
     * @param C_PaySchedule_ID Payment Schedule Template
     */
    public void setC_PaySchedule_ID(int C_PaySchedule_ID) {
        if (C_PaySchedule_ID < 1) set_ValueNoCheck(COLUMNNAME_C_PaySchedule_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_PaySchedule_ID, Integer.valueOf(C_PaySchedule_ID));
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
        set_Value(COLUMNNAME_DiscountAmt, DiscountAmt);
    }

    /**
     * Get Discount Date.
     *
     * @return Last Date for payments with discount
     */
    public Timestamp getDiscountDate() {
        return (Timestamp) get_Value(COLUMNNAME_DiscountDate);
    }

    /**
     * Set Discount Date.
     *
     * @param DiscountDate Last Date for payments with discount
     */
    public void setDiscountDate(Timestamp DiscountDate) {
        set_Value(COLUMNNAME_DiscountDate, DiscountDate);
    }

    /**
     * Get Amount due.
     *
     * @return Amount of the payment due
     */
    public BigDecimal getDueAmt() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_DueAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Amount due.
     *
     * @param DueAmt Amount of the payment due
     */
    public void setDueAmt(BigDecimal DueAmt) {
        set_Value(COLUMNNAME_DueAmt, DueAmt);
    }

    /**
     * Get Due Date.
     *
     * @return Date when the payment is due
     */
    public Timestamp getDueDate() {
        return (Timestamp) get_Value(COLUMNNAME_DueDate);
    }

    /**
     * Set Due Date.
     *
     * @param DueDate Date when the payment is due
     */
    public void setDueDate(Timestamp DueDate) {
        set_Value(COLUMNNAME_DueDate, DueDate);
    }

    /**
     * Set Valid.
     *
     * @param IsValid Element is valid
     */
    public void setIsValid(boolean IsValid) {
        set_Value(COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
    }

    /**
     * Get Valid.
     *
     * @return Element is valid
     */
    public boolean isValid() {
        Object oo = get_Value(COLUMNNAME_IsValid);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
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
        return I_C_InvoicePaySchedule.Table_ID;
    }
}
