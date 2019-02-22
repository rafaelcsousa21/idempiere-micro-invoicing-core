package org.compiere.bank;

import org.compiere.model.I_C_BankAccount;
import org.compiere.orm.MSysConfig;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Bank Account Model
 *
 * @author Jorg Janke
 * @version $Id: MBankAccount.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MBankAccount extends X_C_BankAccount {
    /**
     *
     */
    private static final long serialVersionUID = -110709935374907275L;
    /**
     * Cache
     */
    private static CCache<Integer, MBankAccount> s_cache =
            new CCache<Integer, MBankAccount>(I_C_BankAccount.Table_Name, 5);

    /**
     * Bank Account Model
     *
     * @param ctx              context
     * @param C_BankAccount_ID bank account
     * @param trxName          transaction
     */
    public MBankAccount(Properties ctx, int C_BankAccount_ID) {
        super(ctx, C_BankAccount_ID);
        if (C_BankAccount_ID == 0) {
            setIsDefault(false);
            setBankAccountType(X_C_BankAccount.BANKACCOUNTTYPE_Checking);
            setCurrentBalance(Env.ZERO);
            //	setC_Currency_ID (0);
            setCreditLimit(Env.ZERO);
            //	setC_BankAccount_ID (0);
        }
    } //	MBankAccount

    /**
     * Bank Account Model
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MBankAccount(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MBankAccount

    /**
     * Get BankAccount from Cache
     *
     * @param ctx              context
     * @param C_BankAccount_ID id
     * @return MBankAccount
     */
    public static MBankAccount get(Properties ctx, int C_BankAccount_ID) {
        Integer key = new Integer(C_BankAccount_ID);
        MBankAccount retValue = (MBankAccount) s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MBankAccount(ctx, C_BankAccount_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * String representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb =
                new StringBuilder("MBankAccount[")
                        .append(getId())
                        .append("-")
                        .append(getAccountNo())
                        .append("]");
        return sb.toString();
    } //	toString

    /**
     * Get Bank
     *
     * @return bank parent
     */
    public MBank getBank() {
        return MBank.get(getCtx(), getC_Bank_ID());
    } //	getBank

    /**
     * Get Bank Name and Account No
     *
     * @return Bank/Account
     */
    public String getName() {
        StringBuilder msgreturn =
                new StringBuilder().append(getBank().getName()).append(" ").append(getAccountNo());
        return msgreturn.toString();
    } //	getName

    /**
     * Before Save
     *
     * @param newRecord new record
     * @return success
     */
    protected boolean beforeSave(boolean newRecord) {

        if (MSysConfig.getBooleanValue(
                MSysConfig.IBAN_VALIDATION, true, Env.getClientId(Env.getCtx()))) {
            if (!Util.isEmpty(getIBAN())) {
                setIBAN(IBAN.normalizeIBAN(getIBAN()));
                if (!IBAN.isValid(getIBAN())) {
                    log.saveError("Error", "IBAN is invalid");
                    return false;
                }
            }
        }

        return true;
    } // beforeSave

    /**
     * After Save
     *
     * @param newRecord new record
     * @param success   success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (newRecord && success)
            return insert_Accounting("C_BankAccount_Acct", "C_AcctSchema_Default", null);
        return success;
    } //	afterSave
} //	MBankAccount
