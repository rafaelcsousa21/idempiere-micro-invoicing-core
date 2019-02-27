package org.compiere.wf;

import org.compiere.model.I_AD_WF_Process;
import org.compiere.orm.PO;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_WF_Process
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_Process extends PO implements I_AD_WF_Process {

    /**
     * Not Started = ON
     */
    public static final String WFSTATE_NotStarted = "ON";
    /**
     * Running = OR
     */
    public static final String WFSTATE_Running = "OR";
    /**
     * Suspended = OS
     */
    public static final String WFSTATE_Suspended = "OS";
    /**
     * Terminated = CT
     */
    public static final String WFSTATE_Terminated = "CT";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_WF_Process(Properties ctx, int AD_WF_Process_ID) {
        super(ctx, AD_WF_Process_ID);
        /**
         * if (AD_WF_Process_ID == 0) { setColumnTableId (0); setAD_WF_Process_ID (0);
         * setAD_WF_Responsible_ID (0); setAD_Workflow_ID (0); setProcessed (false); setRecordId (0);
         * setWFState (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_AD_WF_Process(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 7 - System - Client - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_WF_Process[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Table.
     *
     * @return Database Table information
     */
    public int getDBTableId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_Table_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Table.
     *
     * @param AD_Table_ID Database Table information
     */
    public void setDBTableId(int AD_Table_ID) {
        if (AD_Table_ID < 1) setValue(COLUMNNAME_AD_Table_ID, null);
        else setValue(COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
    }

    /**
     * Get User/Contact.
     *
     * @return User within the system - Internal or Business Partner Contact
     */
    public int getUserId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_User_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User/Contact.
     *
     * @param AD_User_ID User within the system - Internal or Business Partner Contact
     */
    public void setUserId(int AD_User_ID) {
        if (AD_User_ID < 1) setValue(COLUMNNAME_AD_User_ID, null);
        else setValue(COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
    }

    /**
     * Get Workflow Process.
     *
     * @return Actual Workflow Process Instance
     */
    public int getWorkFlowProcessId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_WF_Process_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Workflow Responsible.
     *
     * @return Responsible for Workflow Execution
     */
    public int getWorkFlowResponsibleId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_WF_Responsible_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Workflow Responsible.
     *
     * @param AD_WF_Responsible_ID Responsible for Workflow Execution
     */
    public void setWorkFlowResponsibleId(int AD_WF_Responsible_ID) {
        if (AD_WF_Responsible_ID < 1) setValue(COLUMNNAME_AD_WF_Responsible_ID, null);
        else setValue(COLUMNNAME_AD_WF_Responsible_ID, Integer.valueOf(AD_WF_Responsible_ID));
    }

    /**
     * Get Workflow.
     *
     * @return Workflow or combination of tasks
     */
    public int getWorkflowId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_Workflow_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Workflow.
     *
     * @param AD_Workflow_ID Workflow or combination of tasks
     */
    public void setWorkflowId(int AD_Workflow_ID) {
        if (AD_Workflow_ID < 1) setValue(COLUMNNAME_AD_Workflow_ID, null);
        else setValue(COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
    }

    /**
     * Get Priority.
     *
     * @return Indicates if this request is of a high, medium or low priority.
     */
    public int getPriority() {
        Integer ii = (Integer) getValue(COLUMNNAME_Priority);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Priority.
     *
     * @param Priority Indicates if this request is of a high, medium or low priority.
     */
    public void setPriority(int Priority) {
        setValue(COLUMNNAME_Priority, Integer.valueOf(Priority));
    }

    /**
     * Set Processed.
     *
     * @param Processed The document has been processed
     */
    public void setProcessed(boolean Processed) {
        setValue(COLUMNNAME_Processed, Boolean.valueOf(Processed));
    }

    /**
     * Get Record ID.
     *
     * @return Direct internal record ID
     */
    public int getRecordId() {
        Integer ii = (Integer) getValue(COLUMNNAME_Record_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Record ID.
     *
     * @param Record_ID Direct internal record ID
     */
    public void setRecordId(int Record_ID) {
        if (Record_ID < 0) setValue(COLUMNNAME_Record_ID, null);
        else setValue(COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
    }

    /**
     * Get Text Message.
     *
     * @return Text Message
     */
    public String getTextMsg() {
        return (String) getValue(COLUMNNAME_TextMsg);
    }

    /**
     * Set Text Message.
     *
     * @param TextMsg Text Message
     */
    public void setTextMsg(String TextMsg) {
        setValue(COLUMNNAME_TextMsg, TextMsg);
    }

    /**
     * Get Workflow State.
     *
     * @return State of the execution of the workflow
     */
    public String getWorkflowState() {
        return (String) getValue(COLUMNNAME_WFState);
    }

    /**
     * Set Workflow State.
     *
     * @param WFState State of the execution of the workflow
     */
    public void setWorkflowState(String WFState) {

        setValue(COLUMNNAME_WFState, WFState);
    }

    @Override
    public int getTableId() {
        return I_AD_WF_Process.Table_ID;
    }
}
