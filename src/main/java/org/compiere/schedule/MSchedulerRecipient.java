package org.compiere.schedule;

import kotliquery.Row;

/**
 * Scheduler Recipient Model
 *
 * @author Jorg Janke
 * @version $Id: MSchedulerRecipient.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MSchedulerRecipient extends X_AD_SchedulerRecipient {
    /**
     *
     */
    private static final long serialVersionUID = -6521993049769786393L;

    /**
     * Standard Constructor
     *
     * @param ctx                      context
     * @param AD_SchedulerRecipient_ID id
     */
    public MSchedulerRecipient(int AD_SchedulerRecipient_ID) {
        super(AD_SchedulerRecipient_ID);
    } //	MSchedulerRecipient

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MSchedulerRecipient(Row row) {
        super(row);
    } //	MSchedulerRecipient
} //	MSchedulerRecipient
