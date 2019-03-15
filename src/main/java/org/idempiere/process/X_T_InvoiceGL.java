package org.idempiere.process;

import kotliquery.Row;
import org.compiere.model.I_T_InvoiceGL;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.Properties;

public class X_T_InvoiceGL extends PO implements I_T_InvoiceGL {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_T_InvoiceGL(Properties ctx, int T_InvoiceGL_ID) {
        super(ctx, T_InvoiceGL_ID);
    }

    /**
     * Load Constructor
     */
    public X_T_InvoiceGL(Properties ctx, Row row) {
        super(ctx, row);
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
        return "X_T_InvoiceGL[" + getId() + "]";
    }

    /**
     * Get Revaluated Difference Cr.
     *
     * @return Revaluated Cr Amount Difference
     */
    public BigDecimal getAmtRevalCrDiff() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_AmtRevalCrDiff);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Get Revaluated Difference Dr.
     *
     * @return Revaluated Dr Amount Difference
     */
    public BigDecimal getAmtRevalDrDiff() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_AmtRevalDrDiff);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Get Invoice.
     *
     * @return Invoice Identifier
     */
    public int getInvoiceId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Invoice_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Accounting Fact.
     *
     * @return Accounting Fact
     */
    public int getFact_Acct_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_Fact_Acct_ID);
        if (ii == null) return 0;
        return ii;
    }

    @Override
    public int getTableId() {
        return Table_ID;
    }
}
