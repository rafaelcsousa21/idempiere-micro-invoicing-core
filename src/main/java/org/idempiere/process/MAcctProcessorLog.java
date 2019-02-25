package org.idempiere.process;

import org.compiere.model.AdempiereProcessorLog;

import java.sql.ResultSet;
import java.util.Properties;

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
     * @param trxName               transaction
     */
    public MAcctProcessorLog(Properties ctx, int C_AcctProcessorLog_ID) {
        super(ctx, C_AcctProcessorLog_ID);
    } //	MAcctProcessorLog

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MAcctProcessorLog(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MAcctProcessorLog

    /**
     * Parent Constructor
     *
     * @param parent  parent
     * @param summary summary
     */
    public MAcctProcessorLog(MAcctProcessor parent, String summary) {
        this(parent.getCtx(), 0);
        setClientOrg(parent);
        setAccountingProcessorId(parent.getAccountingProcessorId());
        setSummary(summary);
    } //	MAcctProcessorLog
} //	MAcctProcessorLog
