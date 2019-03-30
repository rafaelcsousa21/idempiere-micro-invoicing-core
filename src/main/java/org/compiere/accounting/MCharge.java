package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_AcctSchema;

import java.util.Properties;

import static software.hsharp.core.util.DBKt.getSQLValueEx;


public class MCharge extends org.compiere.order.MCharge {

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx         context
     * @param C_Charge_ID id
     */
    public MCharge(Properties ctx, int C_Charge_ID) {
        super(ctx, C_Charge_ID);
    }
    public MCharge(Properties ctx, Row row) {
        super(ctx, row);
    }

    /**
     * Get Charge Account
     *
     * @param C_Charge_ID charge
     * @param as          account schema
     * @return Charge Account or null
     */
    public static MAccount getAccount(int C_Charge_ID, I_C_AcctSchema as) {
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
        return MAccount.get(as.getCtx(), Account_ID);
    } //  getAccount
}
