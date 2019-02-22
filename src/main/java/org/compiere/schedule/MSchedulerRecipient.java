package org.compiere.schedule;

import java.sql.ResultSet;
import java.util.Properties;

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
     * @param trxName                  transaction
     */
    public MSchedulerRecipient(Properties ctx, int AD_SchedulerRecipient_ID) {
        super(ctx, AD_SchedulerRecipient_ID);
    } //	MSchedulerRecipient

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MSchedulerRecipient(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MSchedulerRecipient
} //	MSchedulerRecipient
