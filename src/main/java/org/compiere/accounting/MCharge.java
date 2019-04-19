package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.AccountingSchema;

import static software.hsharp.core.util.DBKt.getSQLValueEx;


public class MCharge extends org.compiere.order.MCharge {

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx         context
     * @param C_Charge_ID id
     */
    public MCharge(int C_Charge_ID) {
        super(C_Charge_ID);
    }

    public MCharge(Row row) {
        super(row);
    }

    /**
     * Get Charge Account
     *
     * @param C_Charge_ID charge
     * @param as          account schema
     * @return Charge Account or null
     */
    public static MAccount getAccount(int C_Charge_ID, AccountingSchema as) {
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
        return MAccount.get(Account_ID);
    } //  getAccount
}
