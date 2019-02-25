package org.idempiere.process;

import org.compiere.model.I_M_Product_Acct;
import org.compiere.orm.PO;

import java.sql.ResultSet;
import java.util.Properties;

public class X_M_Product_Acct extends PO implements I_M_Product_Acct {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_Product_Acct(Properties ctx, int M_Product_Acct_ID) {
        super(ctx, M_Product_Acct_ID);
        /**
         * if (M_Product_Acct_ID == 0) { setAccountingSchemaId (0); setM_Product_ID (0); setP_Asset_Acct
         * (0); setP_COGS_Acct (0); setP_CostAdjustment_Acct (0); setP_Expense_Acct (0);
         * setP_InventoryClearing_Acct (0); setP_InvoicePriceVariance_Acct (0);
         * setP_PurchasePriceVariance_Acct (0); setP_RateVariance_Acct (0); setP_Revenue_Acct (0);
         * setP_TradeDiscountGrant_Acct (0); setP_TradeDiscountRec_Acct (0); }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_Product_Acct(Properties ctx, ResultSet rs) {
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

    @Override
    public int getTableId() {
        return Table_ID;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer("X_M_Product_Acct[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Set Product Expense.
     *
     * @param P_Expense_Acct Account for Product Expense
     */
    public void setP_Expense_Acct(int P_Expense_Acct) {
        set_Value(COLUMNNAME_P_Expense_Acct, Integer.valueOf(P_Expense_Acct));
    }

    /**
     * Set Product Revenue.
     *
     * @param P_Revenue_Acct Account for Product Revenue (Sales Account)
     */
    public void setP_Revenue_Acct(int P_Revenue_Acct) {
        set_Value(COLUMNNAME_P_Revenue_Acct, Integer.valueOf(P_Revenue_Acct));
    }

}
