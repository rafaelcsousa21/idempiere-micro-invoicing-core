package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.orm.PO;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_Product_Category_Acct
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_Product_Category_Acct extends PO
        implements I_M_Product_Category_Acct {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_Product_Category_Acct(Properties ctx, int M_Product_Category_Acct_ID) {
        super(ctx, M_Product_Category_Acct_ID);
        /**
         * if (M_Product_Category_Acct_ID == 0) { setAccountingSchemaId (0); setM_Product_Category_ID (0);
         * setP_Asset_Acct (0); setP_COGS_Acct (0); setP_CostAdjustment_Acct (0); setP_Expense_Acct (0);
         * setP_InventoryClearing_Acct (0); setP_InvoicePriceVariance_Acct (0);
         * setP_PurchasePriceVariance_Acct (0); setP_RateVariance_Acct (0); setP_Revenue_Acct (0);
         * setP_TradeDiscountGrant_Acct (0); setP_TradeDiscountRec_Acct (0); }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_Product_Category_Acct(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    public X_M_Product_Category_Acct(Properties ctx, Row row) {
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
        StringBuffer sb = new StringBuffer("X_M_Product_Category_Acct[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Accounting Schema.
     *
     * @return Rules for accounting
     */
    public int getC_AcctSchema_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_AcctSchema_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Costing Level.
     *
     * @return The lowest level to accumulate Costing Information
     */
    public String getCostingLevel() {
        return (String) getValue(COLUMNNAME_CostingLevel);
    }

    /**
     * Get Costing Method.
     *
     * @return Indicates how Costs will be calculated
     */
    public String getCostingMethod() {
        return (String) getValue(COLUMNNAME_CostingMethod);
    }

    /**
     * Get Product Category.
     *
     * @return Category of a Product
     */
    public int getM_Product_Category_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_Product_Category_ID);
        if (ii == null) return 0;
        return ii;
    }

    @Override
    public int getTableId() {
        return I_M_Product_Category_Acct.Table_ID;
    }
}
