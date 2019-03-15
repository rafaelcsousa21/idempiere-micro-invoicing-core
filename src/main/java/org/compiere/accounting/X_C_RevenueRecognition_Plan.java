package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_RevenueRecognition_Plan;
import org.compiere.orm.PO;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Generated Model for C_RevenueRecognition_Plan
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_RevenueRecognition_Plan extends PO
        implements I_C_RevenueRecognition_Plan {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_RevenueRecognition_Plan(
            Properties ctx, int C_RevenueRecognition_Plan_ID) {
        super(ctx, C_RevenueRecognition_Plan_ID);
    }

    /**
     * Load Constructor
     */
    public X_C_RevenueRecognition_Plan(Properties ctx, Row row) {
        super(ctx, row);
    }

    /**
     * AccessLevel
     *
     * @return 1 - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    @Override
    public int getTableId() {
        return Table_ID;
    }

    public String toString() {
        return "X_C_RevenueRecognition_Plan[" + getId() + "]";
    }

    /**
     * Set Accounting Schema.
     *
     * @param C_AcctSchema_ID Rules for accounting
     */
    public void setC_AcctSchema_ID(int C_AcctSchema_ID) {
        if (C_AcctSchema_ID < 1) setValueNoCheck(COLUMNNAME_C_AcctSchema_ID, null);
        else setValueNoCheck(COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
    }

    /**
     * Set Currency.
     *
     * @param C_Currency_ID The Currency for this record
     */
    public void setCurrencyId(int C_Currency_ID) {
        if (C_Currency_ID < 1) setValueNoCheck(COLUMNNAME_C_Currency_ID, null);
        else setValueNoCheck(COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
    }

    /**
     * Set Invoice Line.
     *
     * @param C_InvoiceLine_ID Invoice Detail Line
     */
    public void setC_InvoiceLine_ID(int C_InvoiceLine_ID) {
        if (C_InvoiceLine_ID < 1) setValueNoCheck(COLUMNNAME_C_InvoiceLine_ID, null);
        else setValueNoCheck(COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
    }

    /**
     * Set Revenue Recognition.
     *
     * @param C_RevenueRecognition_ID Method for recording revenue
     */
    public void setC_RevenueRecognition_ID(int C_RevenueRecognition_ID) {
        if (C_RevenueRecognition_ID < 1) setValueNoCheck(COLUMNNAME_C_RevenueRecognition_ID, null);
        else
            setValueNoCheck(
                    COLUMNNAME_C_RevenueRecognition_ID, Integer.valueOf(C_RevenueRecognition_ID));
    }

    /**
     * Set Product Revenue.
     *
     * @param P_Revenue_Acct Account for Product Revenue (Sales Account)
     */
    public void setP_Revenue_Acct(int P_Revenue_Acct) {
        setValueNoCheck(COLUMNNAME_P_Revenue_Acct, Integer.valueOf(P_Revenue_Acct));
    }

    /**
     * Set Recognized Amount.
     *
     * @param RecognizedAmt Recognized Amount
     */
    public void setRecognizedAmt(BigDecimal RecognizedAmt) {
        setValueNoCheck(COLUMNNAME_RecognizedAmt, RecognizedAmt);
    }

    /**
     * Set Total Amount.
     *
     * @param TotalAmt Total Amount
     */
    public void setTotalAmt(BigDecimal TotalAmt) {
        setValueNoCheck(COLUMNNAME_TotalAmt, TotalAmt);
    }

    /**
     * Set Unearned Revenue.
     *
     * @param UnEarnedRevenue_Acct Account for unearned revenue
     */
    public void setUnEarnedRevenue_Acct(int UnEarnedRevenue_Acct) {
        setValueNoCheck(COLUMNNAME_UnEarnedRevenue_Acct, Integer.valueOf(UnEarnedRevenue_Acct));
    }
}
