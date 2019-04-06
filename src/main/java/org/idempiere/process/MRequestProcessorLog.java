package org.idempiere.process;

import kotliquery.Row;
import org.compiere.model.AdempiereProcessorLog;

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
    public MRequestProcessorLog(int R_RequestProcessorLog_ID) {
        super(R_RequestProcessorLog_ID);
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
    public MRequestProcessorLog(Row row) {
        super(row);
    } //	MRequestProcessorLog

    /**
     * Parent Constructor
     *
     * @param parent  parent
     * @param summary summary
     */
    public MRequestProcessorLog(MRequestProcessor parent, String summary) {
        this(0);
        setClientOrg(parent);
        setRequestProcessorId(parent.getRequestProcessorId());
        setSummary(summary);
    } //	MRequestProcessorLog
} //	MRequestProcessorLog
