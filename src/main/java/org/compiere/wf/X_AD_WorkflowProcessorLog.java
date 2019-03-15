package org.compiere.wf;

import kotliquery.Row;
import org.compiere.model.I_AD_WorkflowProcessorLog;
import org.compiere.orm.PO;

import java.util.Properties;

/**
 * Generated Model for AD_WorkflowProcessorLog
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WorkflowProcessorLog extends PO
        implements I_AD_WorkflowProcessorLog {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_WorkflowProcessorLog(Properties ctx, int AD_WorkflowProcessorLog_ID) {
        super(ctx, AD_WorkflowProcessorLog_ID);
    }

    /**
     * Load Constructor
     */
    public X_AD_WorkflowProcessorLog(Properties ctx, Row row) {
        super(ctx, row);
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
        return "X_AD_WorkflowProcessorLog[" + getId() + "]";
    }

    /**
     * Set Workflow Processor.
     *
     * @param AD_WorkflowProcessor_ID Workflow Processor Server
     */
    public void setWorkflowProcessorId(int AD_WorkflowProcessor_ID) {
        if (AD_WorkflowProcessor_ID < 1) setValueNoCheck(COLUMNNAME_AD_WorkflowProcessor_ID, null);
        else
            setValueNoCheck(
                    COLUMNNAME_AD_WorkflowProcessor_ID, Integer.valueOf(AD_WorkflowProcessor_ID));
    }

    /**
     * Set Error.
     *
     * @param IsError An Error occurred in the execution
     */
    public void setIsError(boolean IsError) {
        setValue(COLUMNNAME_IsError, Boolean.valueOf(IsError));
    }

    /**
     * Set Summary.
     *
     * @param Summary Textual summary of this request
     */
    public void setSummary(String Summary) {
        setValue(COLUMNNAME_Summary, Summary);
    }

}
