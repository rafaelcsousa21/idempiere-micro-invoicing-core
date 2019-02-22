package org.compiere.wf;

import org.compiere.model.I_AD_WorkflowProcessorLog;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_WorkflowProcessorLog
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WorkflowProcessorLog extends PO
        implements I_AD_WorkflowProcessorLog, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_WorkflowProcessorLog(Properties ctx, int AD_WorkflowProcessorLog_ID) {
        super(ctx, AD_WorkflowProcessorLog_ID);
        /**
         * if (AD_WorkflowProcessorLog_ID == 0) { setAD_WorkflowProcessor_ID (0);
         * setAD_WorkflowProcessorLog_ID (0); setIsError (false); }
         */
    }

    /**
     * Load Constructor
     */
    public X_AD_WorkflowProcessorLog(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 6 - System - Client
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    @Override
    public int getTableId() {
        return Table_ID;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_WorkflowProcessorLog[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Workflow Processor.
     *
     * @return Workflow Processor Server
     */
    public int getAD_WorkflowProcessor_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_WorkflowProcessor_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Workflow Processor.
     *
     * @param AD_WorkflowProcessor_ID Workflow Processor Server
     */
    public void setAD_WorkflowProcessor_ID(int AD_WorkflowProcessor_ID) {
        if (AD_WorkflowProcessor_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_WorkflowProcessor_ID, null);
        else
            set_ValueNoCheck(
                    COLUMNNAME_AD_WorkflowProcessor_ID, Integer.valueOf(AD_WorkflowProcessor_ID));
    }

    /**
     * Set Error.
     *
     * @param IsError An Error occurred in the execution
     */
    public void setIsError(boolean IsError) {
        set_Value(COLUMNNAME_IsError, Boolean.valueOf(IsError));
    }

    /**
     * Set Summary.
     *
     * @param Summary Textual summary of this request
     */
    public void setSummary(String Summary) {
        set_Value(COLUMNNAME_Summary, Summary);
    }

}
