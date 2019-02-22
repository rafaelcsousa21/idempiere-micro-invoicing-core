package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_InvoiceTax
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_InvoiceTax extends PO implements I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_InvoiceTax(Properties ctx, int C_InvoiceTax_ID) {
        super(ctx, C_InvoiceTax_ID);
        /**
         * if (C_InvoiceTax_ID == 0) { setC_Invoice_ID (0); setC_Tax_ID (0); setIsTaxIncluded (false);
         * setProcessed (false); setTaxAmt (Env.ZERO); setTaxBaseAmt (Env.ZERO); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_InvoiceTax(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    public X_C_InvoiceTax(Properties ctx, Row row) {
        super(ctx, row);
    } //	MInvoiceTax

    /**
     * AccessLevel
     *
     * @return 1 - Org
     */
    protected int getAccessLevel() {
        return I_C_InvoiceTax.accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_C_InvoiceTax[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Invoice.
     *
     * @return Invoice Identifier
     */
    public int getC_Invoice_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceTax.COLUMNNAME_C_Invoice_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Invoice.
     *
     * @param C_Invoice_ID Invoice Identifier
     */
    public void setC_Invoice_ID(int C_Invoice_ID) {
        if (C_Invoice_ID < 1) set_ValueNoCheck(I_C_InvoiceTax.COLUMNNAME_C_Invoice_ID, null);
        else set_ValueNoCheck(I_C_InvoiceTax.COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
    }

    /**
     * Get Tax.
     *
     * @return Tax identifier
     */
    public int getC_Tax_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceTax.COLUMNNAME_C_Tax_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Tax.
     *
     * @param C_Tax_ID Tax identifier
     */
    public void setC_Tax_ID(int C_Tax_ID) {
        if (C_Tax_ID < 1) set_ValueNoCheck(I_C_InvoiceTax.COLUMNNAME_C_Tax_ID, null);
        else set_ValueNoCheck(I_C_InvoiceTax.COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
    }

    /**
     * Set Price includes Tax.
     *
     * @param IsTaxIncluded Tax is included in the price
     */
    public void setIsTaxIncluded(boolean IsTaxIncluded) {
        set_Value(I_C_InvoiceTax.COLUMNNAME_IsTaxIncluded, Boolean.valueOf(IsTaxIncluded));
    }

    /**
     * Get Price includes Tax.
     *
     * @return Tax is included in the price
     */
    public boolean isTaxIncluded() {
        Object oo = get_Value(I_C_InvoiceTax.COLUMNNAME_IsTaxIncluded);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Tax Amount.
     *
     * @return Tax Amount for a document
     */
    public BigDecimal getTaxAmt() {
        BigDecimal bd = (BigDecimal) get_Value(I_C_InvoiceTax.COLUMNNAME_TaxAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Tax Amount.
     *
     * @param TaxAmt Tax Amount for a document
     */
    public void setTaxAmt(BigDecimal TaxAmt) {
        set_ValueNoCheck(I_C_InvoiceTax.COLUMNNAME_TaxAmt, TaxAmt);
    }

    /**
     * Get Tax base Amount.
     *
     * @return Base for calculating the tax amount
     */
    public BigDecimal getTaxBaseAmt() {
        BigDecimal bd = (BigDecimal) get_Value(I_C_InvoiceTax.COLUMNNAME_TaxBaseAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Tax base Amount.
     *
     * @param TaxBaseAmt Base for calculating the tax amount
     */
    public void setTaxBaseAmt(BigDecimal TaxBaseAmt) {
        set_ValueNoCheck(I_C_InvoiceTax.COLUMNNAME_TaxBaseAmt, TaxBaseAmt);
    }

    @Override
    public int getTableId() {
        return I_C_InvoiceTax.Table_ID;
    }
}
