package org.compiere.accounting;

import java.math.BigDecimal;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.getSQLValueEx;


public class MCharge extends org.compiere.order.MCharge {

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx         context
     * @param C_Charge_ID id
     * @param trxName     transaction
     */
    public MCharge(Properties ctx, int C_Charge_ID) {
        super(ctx, C_Charge_ID);
    }

    /**
     * Get Charge Account
     *
     * @param C_Charge_ID charge
     * @param as          account schema
     * @param amount      amount NOT USED
     * @return Charge Account or null
     * @deprecated use getAccount(Charge, as) instead
     */
    public static MAccount getAccount(int C_Charge_ID, MAcctSchema as, BigDecimal amount) {
        return getAccount(C_Charge_ID, as);
    } //  getAccount

    /**
     * Get Charge Account
     *
     * @param C_Charge_ID charge
     * @param as          account schema
     * @return Charge Account or null
     */
    public static MAccount getAccount(int C_Charge_ID, MAcctSchema as) {
        if (C_Charge_ID == 0 || as == null) return null;

        String sql =
                "SELECT Ch_Expense_Acct FROM C_Charge_Acct WHERE C_Charge_ID=? AND C_AcctSchema_ID=?";
        int Account_ID = getSQLValueEx(sql, C_Charge_ID, as.getId());
        //	No account
        if (Account_ID <= 0) {
            s_log.severe("NO account for C_Charge_ID=" + C_Charge_ID);
            return null;
        }

        //	Return Account
        MAccount acct = MAccount.get(as.getCtx(), Account_ID);
        return acct;
    } //  getAccount
}
