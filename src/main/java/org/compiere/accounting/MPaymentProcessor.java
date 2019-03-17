package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_BankAccount;
import org.idempiere.common.util.Env;

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
     */
    public MPaymentProcessor(Properties ctx, int C_PaymentProcessor_ID) {
        super(ctx, C_PaymentProcessor_ID);
        if (C_PaymentProcessor_ID == 0) {
            //	setBankAccountId (0);		//	Parent
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
     * @param ctx context
     */
    public MPaymentProcessor(Properties ctx, Row row) {
        super(ctx, row);
    } //	MPaymentProcessor

    /**
     * @deprecated Use C_BankAccount.C_PaymentProcessor_ID
     */
    @Override
    public I_C_BankAccount getBankAccount() throws RuntimeException {
        return super.getBankAccount();
    }

    /**
     * @deprecated Use C_BankAccount.C_PaymentProcessor_ID
     */
    @Override
    public int getBankAccountId() {
        return super.getBankAccountId();
    }

    /**
     * @deprecated Use C_BankAccount.C_PaymentProcessor_ID
     */
    @Override
    public void setBankAccountId(int C_BankAccount_ID) {
        super.setBankAccountId(C_BankAccount_ID);
    }
} //	MPaymentProcessor
