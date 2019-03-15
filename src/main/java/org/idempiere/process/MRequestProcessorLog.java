package org.idempiere.process;

import kotliquery.Row;
import org.compiere.model.AdempiereProcessorLog;

import java.util.Properties;

public class MRequestProcessorLog extends X_R_RequestProcessorLog implements AdempiereProcessorLog {
    /**
     *
     */
    private static final long serialVersionUID = 3295903266591998482L;

    /**
     * Standard Constructor
     *
     * @param ctx                      context
     * @param R_RequestProcessorLog_ID id
     */
    public MRequestProcessorLog(Properties ctx, int R_RequestProcessorLog_ID) {
        super(ctx, R_RequestProcessorLog_ID);
        if (R_RequestProcessorLog_ID == 0) {
            setIsError(false);
        }
    } //	MRequestProcessorLog

    /**
     * Load Constructor
     *
     * @param ctx context
     * @param rs  result set
     */
    public MRequestProcessorLog(Properties ctx, Row row) {
        super(ctx, row);
    } //	MRequestProcessorLog

    /**
     * Parent Constructor
     *
     * @param parent  parent
     * @param summary summary
     */
    public MRequestProcessorLog(MRequestProcessor parent, String summary) {
        this(parent.getCtx(), 0);
        setClientOrg(parent);
        setR_RequestProcessor_ID(parent.getR_RequestProcessor_ID());
        setSummary(summary);
    } //	MRequestProcessorLog
} //	MRequestProcessorLog
