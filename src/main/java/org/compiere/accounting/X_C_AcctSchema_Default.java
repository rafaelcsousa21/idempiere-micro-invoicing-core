package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.orm.PO;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_AcctSchema_Default
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_AcctSchema_Default extends PO implements I_C_AcctSchema_Default {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_AcctSchema_Default(Properties ctx, int C_AcctSchema_Default_ID) {
        super(ctx, C_AcctSchema_Default_ID);
        /**
         * if (C_AcctSchema_Default_ID == 0) { setB_Asset_Acct (0); setB_InterestExp_Acct (0);
         * setB_InterestRev_Acct (0); setB_InTransit_Acct (0); setB_PaymentSelect_Acct (0);
         * setB_UnallocatedCash_Acct (0); setAccountingSchemaId (0); setCh_Expense_Acct (0);
         * setC_Prepayment_Acct (0); setC_Receivable_Acct (0); setNotInvoicedReceipts_Acct (0);
         * setP_Asset_Acct (0); setPayDiscount_Exp_Acct (0); setPayDiscount_Rev_Acct (0); setP_COGS_Acct
         * (0); setP_CostAdjustment_Acct (0); setP_Expense_Acct (0); setP_InventoryClearing_Acct (0);
         * setP_InvoicePriceVariance_Acct (0); setPJ_Asset_Acct (0); setPJ_WIP_Acct (0);
         * setP_PurchasePriceVariance_Acct (0); setP_RateVariance_Acct (0); setP_Revenue_Acct (0);
         * setP_TradeDiscountGrant_Acct (0); setP_TradeDiscountRec_Acct (0); setRealizedGain_Acct (0);
         * setRealizedLoss_Acct (0); setT_Credit_Acct (0); setT_Due_Acct (0); setT_Expense_Acct (0);
         * setUnrealizedGain_Acct (0); setUnrealizedLoss_Acct (0); setV_Liability_Acct (0);
         * setV_Prepayment_Acct (0); setW_Differences_Acct (0); setWriteOff_Acct (0); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_AcctSchema_Default(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    public X_C_AcctSchema_Default(Properties ctx, Row row) {
        super(ctx, row);
    } //	MAcctSchemaDefault


    /**
     * AccessLevel
     *
     * @return 2 - Client
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_C_AcctSchema_Default[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Bank Asset.
     *
     * @return Bank Asset Account
     */
    public int getBankAssetAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_B_Asset_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Bank Interest Expense.
     *
     * @return Bank Interest Expense Account
     */
    public int getBankInterestExpenseAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_B_InterestExp_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Bank Interest Revenue.
     *
     * @return Bank Interest Revenue Account
     */
    public int getBankInterestRevenueAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_B_InterestRev_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Bank In Transit.
     *
     * @return Bank In Transit Account
     */
    public int getBankInTransitAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_B_InTransit_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Payment Selection.
     *
     * @return AP Payment Selection Clearing Account
     */
    public int getAPPaymentSelectionClearingAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_B_PaymentSelect_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Unallocated Cash.
     *
     * @return Unallocated Cash Clearing Account
     */
    public int getUnallocatedCashClearingAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_B_UnallocatedCash_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Accounting Schema.
     *
     * @return Rules for accounting
     */
    public int getAccountingSchemaId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_AcctSchema_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Accounting Schema.
     *
     * @param C_AcctSchema_ID Rules for accounting
     */
    public void setAccountingSchemaId(int C_AcctSchema_ID) {
        if (C_AcctSchema_ID < 1) set_ValueNoCheck(COLUMNNAME_C_AcctSchema_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
    }

    /**
     * Get Cash Book Asset.
     *
     * @return Cash Book Asset Account
     */
    public int getCashBookAssetAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_CB_Asset_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Cash Transfer.
     *
     * @return Cash Transfer Clearing Account
     */
    public int getCashTransferClearingAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_CB_CashTransfer_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Cash Book Differences.
     *
     * @return Cash Book Differences Account
     */
    public int getCashBookDifferencesAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_CB_Differences_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Cash Book Expense.
     *
     * @return Cash Book Expense Account
     */
    public int getCashBookExpenseAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_CB_Expense_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Cash Book Receipt.
     *
     * @return Cash Book Receipts Account
     */
    public int getCashBookReceiptsAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_CB_Receipt_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Charge Account.
     *
     * @return Charge Account
     */
    public int getChargeAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_Ch_Expense_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Customer Prepayment.
     *
     * @return Account for customer prepayments
     */
    public int getCustomerPrepaymentAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Prepayment_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Customer Receivables.
     *
     * @return Account for Customer Receivables
     */
    public int getCustomerReceivableAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Receivable_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Receivable Services.
     *
     * @return Customer Accounts Receivables Services Account
     */
    public int getCustomerReceivableServicesAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Receivable_Services_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Not-invoiced Receipts.
     *
     * @return Account for not-invoiced Material Receipts
     */
    public int getNotInvoicedReceiptsAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_NotInvoicedReceipts_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Product Asset.
     *
     * @return Account for Product Asset (Inventory)
     */
    public int getProductAssetAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_Asset_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Average Cost Variance.
     *
     * @return Average Cost Variance
     */
    public int getAverageCostVarianceAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_AverageCostVariance_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Payment Discount Expense.
     *
     * @return Payment Discount Expense Account
     */
    public int getPaymentDiscountExpenseAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_PayDiscount_Exp_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Payment Discount Revenue.
     *
     * @return Payment Discount Revenue Account
     */
    public int getPaymentDiscountRevenueAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_PayDiscount_Rev_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Product COGS.
     *
     * @return Account for Cost of Goods Sold
     */
    public int getProductCOGSAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_COGS_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Cost Adjustment.
     *
     * @return Product Cost Adjustment Account
     */
    public int getProductCostAdjustmentAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_CostAdjustment_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Product Expense.
     *
     * @return Account for Product Expense
     */
    public int getProductExpenseAccount() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_Expense_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Inventory Clearing.
     *
     * @return Product Inventory Clearing Account
     */
    public int getP_InventoryClearing_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_InventoryClearing_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Invoice Price Variance.
     *
     * @return Difference between Costs and Invoice Price (IPV)
     */
    public int getP_InvoicePriceVariance_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_InvoicePriceVariance_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Project Asset.
     *
     * @return Project Asset Account
     */
    public int getPJ_Asset_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_PJ_Asset_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Landed Cost Clearing.
     *
     * @return Product Landed Cost Clearing Account
     */
    public int getP_LandedCostClearing_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_LandedCostClearing_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Purchase Price Variance.
     *
     * @return Difference between Standard Cost and Purchase Price (PPV)
     */
    public int getP_PurchasePriceVariance_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_PurchasePriceVariance_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Rate Variance.
     *
     * @return The Rate Variance account is the account used Manufacturing Order
     */
    public int getP_RateVariance_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_RateVariance_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Product Revenue.
     *
     * @return Account for Product Revenue (Sales Account)
     */
    public int getP_Revenue_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_Revenue_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Trade Discount Granted.
     *
     * @return Trade Discount Granted Account
     */
    public int getP_TradeDiscountGrant_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_TradeDiscountGrant_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Trade Discount Received.
     *
     * @return Trade Discount Receivable Account
     */
    public int getP_TradeDiscountRec_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_P_TradeDiscountRec_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Realized Gain Acct.
     *
     * @return Realized Gain Account
     */
    public int getRealizedGain_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_RealizedGain_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Realized Loss Acct.
     *
     * @return Realized Loss Account
     */
    public int getRealizedLoss_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_RealizedLoss_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Tax Credit.
     *
     * @return Account for Tax you can reclaim
     */
    public int getT_Credit_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_T_Credit_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Tax Due.
     *
     * @return Account for Tax you have to pay
     */
    public int getT_Due_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_T_Due_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Tax Expense.
     *
     * @return Account for paid tax you cannot reclaim
     */
    public int getT_Expense_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_T_Expense_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Unearned Revenue.
     *
     * @return Account for unearned revenue
     */
    public int getUnEarnedRevenue_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_UnEarnedRevenue_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Unrealized Gain Acct.
     *
     * @return Unrealized Gain Account for currency revaluation
     */
    public int getUnrealizedGain_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_UnrealizedGain_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Unrealized Loss Acct.
     *
     * @return Unrealized Loss Account for currency revaluation
     */
    public int getUnrealizedLoss_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_UnrealizedLoss_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Vendor Liability.
     *
     * @return Account for Vendor Liability
     */
    public int getV_Liability_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_V_Liability_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Vendor Service Liability.
     *
     * @return Account for Vendor Service Liability
     */
    public int getV_Liability_Services_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_V_Liability_Services_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Vendor Prepayment.
     *
     * @return Account for Vendor Prepayments
     */
    public int getV_Prepayment_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_V_Prepayment_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Warehouse Differences.
     *
     * @return Warehouse Differences Account
     */
    public int getW_Differences_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_W_Differences_Acct);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Write-off.
     *
     * @return Account for Receivables write-off
     */
    public int getWriteOff_Acct() {
        Integer ii = (Integer) getValue(COLUMNNAME_WriteOff_Acct);
        if (ii == null) return 0;
        return ii;
    }

    @Override
    public int getTableId() {
        return I_C_AcctSchema_Default.Table_ID;
    }
}
