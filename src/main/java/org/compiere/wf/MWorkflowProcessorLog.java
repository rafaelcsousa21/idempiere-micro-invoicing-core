package org.compiere.wf;

import kotliquery.Row;
import org.compiere.model.AdempiereProcessorLog;

import java.util.Properties;

/**
 * Processor Log
 *
 * @author Jorg Janke
 * @version $Id: MWorkflowProcessorLog.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWorkflowProcessorLog extends X_AD_WorkflowProcessorLog
        implements AdempiereProcessorLog {
    /**
     *
     */
    private static final long serialVersionUID = 7646579803939292482L;

    /**
     * Standard Constructor
     *
     * @param ctx                        context
     * @param AD_WorkflowProcessorLog_ID id
     * @param trxName                    transaction
     */
    public MWorkflowProcessorLog(Properties ctx, int AD_WorkflowProcessorLog_ID) {
        super(ctx, AD_WorkflowProcessorLog_ID);
        if (AD_WorkflowProcessorLog_ID == 0) {
            setIsError(false);
        }
    } //	MWorkflowProcessorLog

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MWorkflowProcessorLog(Properties ctx, Row row) {
        super(ctx, row);
    } //	MWorkflowProcessorLog

    /**
     * Parent Constructor
     *
     * @param parent  parent
     * @param Summary Summary
     */
    public MWorkflowProcessorLog(MWorkflowProcessor parent, String Summary) {
        this(parent.getCtx(), 0);
        setClientOrg(parent);
        setWorkflowProcessorId(parent.getWorkflowProcessorId());
        setSummary(Summary);
    } //	MWorkflowProcessorLog
} //	MWorkflowProcessorLog
