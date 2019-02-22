package org.compiere.accounting;

import org.compiere.model.I_C_BankAccount;
import org.idempiere.common.util.Env;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Payment Processor Model
 *
 * @author Jorg Janke
 * @version $Id: MPaymentProcessor.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MPaymentProcessor extends X_C_PaymentProcessor {
    /**
     *
     */
    private static final long serialVersionUID = 8514876566904723695L;

    /**
     * ************************************************************************ Payment Processor
     * Model
     *
     * @param ctx                   context
     * @param C_PaymentProcessor_ID payment processor
     * @param trxName               transaction
     */
    public MPaymentProcessor(Properties ctx, int C_PaymentProcessor_ID) {
        super(ctx, C_PaymentProcessor_ID);
        if (C_PaymentProcessor_ID == 0) {
            //	setC_BankAccount_ID (0);		//	Parent
            //	setUserID (null);
            //	setPassword (null);
            //	setHostAddress (null);
            //	setHostPort (0);
            setCommission(Env.ZERO);
            setAcceptVisa(false);
            setAcceptMC(false);
            setAcceptAMEX(false);
            setAcceptDiners(false);
            setCostPerTrx(Env.ZERO);
            setAcceptCheck(false);
            setRequireVV(false);
            setAcceptCorporate(false);
            setAcceptDiscover(false);
            setAcceptATM(false);
            setAcceptDirectDeposit(false);
            setAcceptDirectDebit(false);
            //	setName (null);
        }
    } //	MPaymentProcessor

    /**
     * Payment Processor Model
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MPaymentProcessor(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MPaymentProcessor

    /**
     * @deprecated Use C_BankAccount.C_PaymentProcessor_ID
     */
    @Override
    public I_C_BankAccount getC_BankAccount() throws RuntimeException {
        return super.getC_BankAccount();
    }

    /**
     * @deprecated Use C_BankAccount.C_PaymentProcessor_ID
     */
    @Override
    public int getC_BankAccount_ID() {
        return super.getC_BankAccount_ID();
    }

    /**
     * @deprecated Use C_BankAccount.C_PaymentProcessor_ID
     */
    @Override
    public void setC_BankAccount_ID(int C_BankAccount_ID) {
        super.setC_BankAccount_ID(C_BankAccount_ID);
    }
} //	MPaymentProcessor
