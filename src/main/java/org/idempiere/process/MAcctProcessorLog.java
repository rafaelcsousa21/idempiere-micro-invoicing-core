package org.idempiere.process;

import kotliquery.Row;
import org.compiere.model.AdempiereProcessorLog;

public class MAcctProcessorLog extends X_C_AcctProcessorLog implements AdempiereProcessorLog {

    /**
     *
     */
    private static final long serialVersionUID = 3668544104375224987L;

    /**
     * Standard Constructor
     *
     * @param ctx                   context
     * @param C_AcctProcessorLog_ID id
     */
    public MAcctProcessorLog(int C_AcctProcessorLog_ID) {
        super(C_AcctProcessorLog_ID);
    } //	MAcctProcessorLog

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MAcctProcessorLog(Row row) {
        super(row);
    } //	MAcctProcessorLog

    /**
     * Parent Constructor
     *
     * @param parent  parent
     * @param summary summary
     */
    public MAcctProcessorLog(MAcctProcessor parent, String summary) {
        this(0);
        setClientOrg(parent);
        setAccountingProcessorId(parent.getAccountingProcessorId());
        setSummary(summary);
    } //	MAcctProcessorLog
} //	MAcctProcessorLog
