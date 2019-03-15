package org.compiere.wf;

import kotliquery.Row;
import org.compiere.model.I_AD_WF_EventAudit;
import org.compiere.orm.PO;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Generated Model for AD_WF_EventAudit
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_EventAudit extends PO implements I_AD_WF_EventAudit {

    /**
     * Process Created = PC
     */
    public static final String EVENTTYPE_ProcessCreated = "PC";
    /**
     * State Changed = SC
     */
    public static final String EVENTTYPE_StateChanged = "SC";
    /**
     * Process Completed = PX
     */
    public static final String EVENTTYPE_ProcessCompleted = "PX";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_WF_EventAudit(Properties ctx, int AD_WF_EventAudit_ID) {
        super(ctx, AD_WF_EventAudit_ID);
        /*
         * if (AD_WF_EventAudit_ID == 0) { setColumnTableId (0); setAD_WF_EventAudit_ID (0);
         * setAD_WF_Node_ID (0); setAD_WF_Process_ID (0); setAD_WF_Responsible_ID (0); setElapsedTimeMS
         * (Env.ZERO); setEventType (null); setRecordId (0); setWFState (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_AD_WF_EventAudit(Properties ctx, Row row) {
        super(ctx, row);
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
        return "X_AD_WF_EventAudit[" + getId() + "]";
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
     * Get Node.
     *
     * @return Workflow Node (activity), step or process
     */
    public int getWorkflowNodeId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_WF_Node_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Node.
     *
     * @param AD_WF_Node_ID Workflow Node (activity), step or process
     */
    public void setWorkflowNodeId(int AD_WF_Node_ID) {
        if (AD_WF_Node_ID < 1) setValue(COLUMNNAME_AD_WF_Node_ID, null);
        else setValue(COLUMNNAME_AD_WF_Node_ID, Integer.valueOf(AD_WF_Node_ID));
    }

    /**
     * Set Workflow Process.
     *
     * @param AD_WF_Process_ID Actual Workflow Process Instance
     */
    public void setWorkflowProcessId(int AD_WF_Process_ID) {
        if (AD_WF_Process_ID < 1) setValue(COLUMNNAME_AD_WF_Process_ID, null);
        else setValue(COLUMNNAME_AD_WF_Process_ID, Integer.valueOf(AD_WF_Process_ID));
    }

    /**
     * Set Workflow Responsible.
     *
     * @param AD_WF_Responsible_ID Responsible for Workflow Execution
     */
    public void setWorkflowResponsibleId(int AD_WF_Responsible_ID) {
        if (AD_WF_Responsible_ID < 1) setValue(COLUMNNAME_AD_WF_Responsible_ID, null);
        else setValue(COLUMNNAME_AD_WF_Responsible_ID, Integer.valueOf(AD_WF_Responsible_ID));
    }

    /**
     * Set Attribute Name.
     *
     * @param AttributeName Name of the Attribute
     */
    public void setAttributeName(String AttributeName) {
        setValue(COLUMNNAME_AttributeName, AttributeName);
    }

    /**
     * Set Elapsed Time ms.
     *
     * @param ElapsedTimeMS Elapsed Time in milli seconds
     */
    public void setElapsedTimeMS(BigDecimal ElapsedTimeMS) {
        setValue(COLUMNNAME_ElapsedTimeMS, ElapsedTimeMS);
    }

    /**
     * Set Event Type.
     *
     * @param EventType Type of Event
     */
    public void setEventType(String EventType) {

        setValue(COLUMNNAME_EventType, EventType);
    }

    /**
     * Set New Value.
     *
     * @param NewValue New field value
     */
    public void setNewValue(String NewValue) {
        setValue(COLUMNNAME_NewValue, NewValue);
    }

    /**
     * Set Old Value.
     *
     * @param OldValue The old file data
     */
    public void setOldValue(String OldValue) {
        setValue(COLUMNNAME_OldValue, OldValue);
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
     * Set Text Message.
     *
     * @param TextMsg Text Message
     */
    public void setTextMsg(String TextMsg) {
        setValue(COLUMNNAME_TextMsg, TextMsg);
    }

    /**
     * Set Workflow State.
     *
     * @param WFState State of the execution of the workflow
     */
    public void setWFState(String WFState) {

        setValue(COLUMNNAME_WFState, WFState);
    }

    @Override
    public int getTableId() {
        return I_AD_WF_EventAudit.Table_ID;
    }

    /**
     * Set Table.
     *
     * @param AD_Table_ID Database Table information
     */
    public void setTableId(int AD_Table_ID) {
        if (AD_Table_ID < 1) setValue(COLUMNNAME_AD_Table_ID, null);
        else setValue(COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
    }
}
