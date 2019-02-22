package org.idempiere.process;

import org.compiere.model.I_M_Product_Acct;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

public class X_M_Product_Acct extends PO implements I_M_Product_Acct, I_Persistent {

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
         * if (M_Product_Acct_ID == 0) { setC_AcctSchema_ID (0); setM_Product_ID (0); setP_Asset_Acct
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
     * Get Accounting Schema.
     *
     * @return Rules for accounting
     */
    public int getC_AcctSchema_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_AcctSchema_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Product.
     *
     * @return Product, Service, Item
     */
    public int getM_Product_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_Product_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Product Asset.
     *
     * @return Account for Product Asset (Inventory)
     */
    public int getP_Asset_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_Asset_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Average Cost Variance.
     *
     * @return Average Cost Variance
     */
    public int getP_AverageCostVariance_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_AverageCostVariance_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Product COGS.
     *
     * @return Account for Cost of Goods Sold
     */
    public int getP_COGS_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_COGS_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Cost Adjustment.
     *
     * @return Product Cost Adjustment Account
     */
    public int getP_CostAdjustment_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_CostAdjustment_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Product Expense.
     *
     * @return Account for Product Expense
     */
    public int getP_Expense_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_Expense_Acct);
        if (ii == null) return 0;
        return ii;
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
     * Get Inventory Clearing.
     *
     * @return Product Inventory Clearing Account
     */
    public int getP_InventoryClearing_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_InventoryClearing_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Invoice Price Variance.
     *
     * @return Difference between Costs and Invoice Price (IPV)
     */
    public int getP_InvoicePriceVariance_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_InvoicePriceVariance_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Landed Cost Clearing.
     *
     * @return Product Landed Cost Clearing Account
     */
    public int getP_LandedCostClearing_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_LandedCostClearing_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Purchase Price Variance.
     *
     * @return Difference between Standard Cost and Purchase Price (PPV)
     */
    public int getP_PurchasePriceVariance_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_PurchasePriceVariance_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Rate Variance.
     *
     * @return The Rate Variance account is the account used Manufacturing Order
     */
    public int getP_RateVariance_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_RateVariance_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Product Revenue.
     *
     * @return Account for Product Revenue (Sales Account)
     */
    public int getP_Revenue_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_Revenue_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Product Revenue.
     *
     * @param P_Revenue_Acct Account for Product Revenue (Sales Account)
     */
    public void setP_Revenue_Acct(int P_Revenue_Acct) {
        set_Value(COLUMNNAME_P_Revenue_Acct, Integer.valueOf(P_Revenue_Acct));
    }

    /**
     * Get Trade Discount Granted.
     *
     * @return Trade Discount Granted Account
     */
    public int getP_TradeDiscountGrant_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_TradeDiscountGrant_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Trade Discount Received.
     *
     * @return Trade Discount Receivable Account
     */
    public int getP_TradeDiscountRec_Acct() {
        Integer ii = (Integer) get_Value(COLUMNNAME_P_TradeDiscountRec_Acct);
        if (ii == null) return 0;
        return ii;
    }
}
