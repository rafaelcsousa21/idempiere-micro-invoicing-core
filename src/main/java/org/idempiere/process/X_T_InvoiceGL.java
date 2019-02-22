package org.idempiere.process;

import org.compiere.model.I_T_InvoiceGL;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

public class X_T_InvoiceGL extends PO implements I_T_InvoiceGL, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_T_InvoiceGL(Properties ctx, int T_InvoiceGL_ID) {
        super(ctx, T_InvoiceGL_ID);
        /**
         * if (T_InvoiceGL_ID == 0) { setAD_PInstance_ID (0); setAmtAcctBalance (Env.ZERO);
         * setAmtRevalCr (Env.ZERO); setAmtRevalCrDiff (Env.ZERO); setAmtRevalDr (Env.ZERO);
         * setAmtRevalDrDiff (Env.ZERO); setAmtSourceBalance (Env.ZERO); setC_ConversionTypeReval_ID
         * (0); setC_Invoice_ID (0); setDateReval (new Timestamp( System.currentTimeMillis() ));
         * setFact_Acct_ID (0); setGrandTotal (Env.ZERO); setIsAllCurrencies (false); setOpenAmt
         * (Env.ZERO); }
         */
    }

    /**
     * Load Constructor
     */
    public X_T_InvoiceGL(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_T_InvoiceGL[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Process Instance.
     *
     * @return Instance of the process
     */
    public int getAD_PInstance_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_PInstance_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Revaluated Difference Cr.
     *
     * @return Revaluated Cr Amount Difference
     */
    public BigDecimal getAmtRevalCrDiff() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_AmtRevalCrDiff);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Get Revaluated Difference Dr.
     *
     * @return Revaluated Dr Amount Difference
     */
    public BigDecimal getAmtRevalDrDiff() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_AmtRevalDrDiff);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Get Revaluation Conversion Type.
     *
     * @return Revaluation Currency Conversion Type
     */
    public int getC_ConversionTypeReval_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_ConversionTypeReval_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Revaluation Document Type.
     *
     * @return Document Type for Revaluation Journal
     */
    public int getC_DocTypeReval_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_DocTypeReval_ID);
        if (ii == null) return 0;
        return ii;
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
     * Get Accounting Fact.
     *
     * @return Accounting Fact
     */
    public int getFact_Acct_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_Fact_Acct_ID);
        if (ii == null) return 0;
        return ii;
    }

    @Override
    public int getTableId() {
        return Table_ID;
    }
}
