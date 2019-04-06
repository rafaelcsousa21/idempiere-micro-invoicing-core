package org.compiere.accounting;

import kotliquery.Row;

/**
 * Payment Batch Model
 *
 * @author Jorg Janke
 * @version $Id: MPaymentBatch.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MPaymentBatch extends X_C_PaymentBatch {
    /**
     *
     */
    private static final long serialVersionUID = 779975501904633495L;

    /**
     * Standard Constructor
     *
     * @param ctx               context
     * @param C_PaymentBatch_ID id
     * @param trxName           transaction
     */
    public MPaymentBatch(int C_PaymentBatch_ID) {
        super(C_PaymentBatch_ID);
        if (C_PaymentBatch_ID == 0) {
            //	setName (null);
            setProcessed(false);
            setProcessing(false);
        }
    } //	MPaymentBatch

    /**
     * Load Constructor
     */
    public MPaymentBatch(Row row) {
        super(row);
    } //	MPaymentBatch

    /**
     * New Constructor
     *
     * @param Name name
     */
    public MPaymentBatch(String Name) {
        this(0);
        setName(Name);
    } //	MPaymentBatch

    /**
     * Parent Constructor
     *
     * @param ps Pay Selection
     */
    public MPaymentBatch(MPaySelection ps) {
        this(0);
        setClientOrg(ps);
        setName(ps.getName());
    } //	MPaymentBatch
} //	MPaymentBatch
