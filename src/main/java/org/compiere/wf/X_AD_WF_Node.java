package org.compiere.wf;

import kotliquery.Row;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.orm.BasePONameValue;
import org.compiere.orm.MTable;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Generated Model for AD_WF_Node
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_Node extends BasePONameValue implements I_AD_WF_Node {

    /**
     * Wait (Sleep) = Z
     */
    public static final String ACTION_WaitSleep = "Z";
    /**
     * User Choice = C
     */
    public static final String ACTION_UserChoice = "C";
    /**
     * Sub Workflow = F
     */
    public static final String ACTION_SubWorkflow = "F";
    /**
     * Set Variable = V
     */
    public static final String ACTION_SetVariable = "V";
    /**
     * User Window = W
     */
    public static final String ACTION_UserWindow = "W";
    /**
     * User Form = X
     */
    public static final String ACTION_UserForm = "X";
    /**
     * Apps Task = T
     */
    public static final String ACTION_AppsTask = "T";
    /**
     * Apps Report = R
     */
    public static final String ACTION_AppsReport = "R";
    /**
     * Apps Process = P
     */
    public static final String ACTION_AppsProcess = "P";
    /**
     * Document Action = D
     */
    public static final String ACTION_DocumentAction = "D";
    /**
     * EMail = M
     */
    public static final String ACTION_EMail = "M";
    /**
     * User Info = I
     */
    public static final String ACTION_UserInfo = "I";
    /**
     * Minute = M
     */
    public static final String DYNPRIORITYUNIT_Minute = "M";
    /**
     * Hour = H
     */
    public static final String DYNPRIORITYUNIT_Hour = "H";
    /**
     * Day = D
     */
    public static final String DYNPRIORITYUNIT_Day = "D";
    /**
     * Document Owner = D
     */
    public static final String EMAILRECIPIENT_DocumentOwner = "D";
    /**
     * Document Business Partner = B
     */
    public static final String EMAILRECIPIENT_DocumentBusinessPartner = "B";
    /**
     * WF Responsible = R
     */
    public static final String EMAILRECIPIENT_WFResponsible = "R";
    /**
     * AND = A
     */
    public static final String JOINELEMENT_AND = "A";
    /**
     * XOR = X
     */
    public static final String JOINELEMENT_XOR = "X";
    /**
     * AND = A
     */
    public static final String SPLITELEMENT_AND = "A";
    /**
     * XOR = X
     */
    public static final String SPLITELEMENT_XOR = "X";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_WF_Node(Properties ctx, int AD_WF_Node_ID) {
        super(ctx, AD_WF_Node_ID);
        /**
         * if (AD_WF_Node_ID == 0) { setAction (null); // Z setAD_WF_Node_ID (0); setAD_Workflow_ID (0);
         * setCost (Env.ZERO); setDuration (0); setEntityType (null); // @SQL=select
         * get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual setIsCentrallyMaintained (true); // Y
         * setJoinElement (null); // X setLimit (0); setName (null); setSplitElement (null); // X
         * setValue (null); setWaitingTime (0); setXPosition (0); setYPosition (0); }
         */
    }

    /**
     * Load Constructor
     */
    public X_AD_WF_Node(Properties ctx, Row row) {
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

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_WF_Node[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Action.
     *
     * @return Indicates the Action to be performed
     */
    public String getAction() {
        return (String) getValue(COLUMNNAME_Action);
    }

    /**
     * Set Action.
     *
     * @param Action Indicates the Action to be performed
     */
    public void setAction(String Action) {

        setValue(COLUMNNAME_Action, Action);
    }

    /**
     * Get Column.
     *
     * @return Column in the table
     */
    public int getColumnId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_Column_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Special Form.
     *
     * @return Special Form
     */
    public int getFormId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_Form_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Info Window.
     *
     * @return Info and search/select Window
     */
    public int getInfoWindowId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_InfoWindow_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Process.
     *
     * @return Process or Report
     */
    public int getProcessId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_Process_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get OS Task.
     *
     * @return Operation System Task
     */
    public int getTaskId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_Task_ID);
        if (ii == null) return 0;
        return ii;
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
     * Get Workflow Responsible.
     *
     * @return Responsible for Workflow Execution
     */
    public int getAD_WF_Responsible_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_WF_Responsible_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Window.
     *
     * @return Data entry or display window
     */
    public int getWindowId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_Window_ID);
        if (ii == null) return 0;
        return ii;
    }

    public org.compiere.model.I_AD_Workflow getWorkflow() throws RuntimeException {
        return (org.compiere.model.I_AD_Workflow)
                MTable.get(getCtx(), org.compiere.model.I_AD_Workflow.Table_Name)
                        .getPO(getWorkflowId());
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
        if (AD_Workflow_ID < 1) setValueNoCheck(COLUMNNAME_AD_Workflow_ID, null);
        else setValueNoCheck(COLUMNNAME_AD_Workflow_ID, AD_Workflow_ID);
    }

    /**
     * Get Attribute Name.
     *
     * @return Name of the Attribute
     */
    public String getAttributeName() {
        return (String) getValue(COLUMNNAME_AttributeName);
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
     * Get Attribute Value.
     *
     * @return Value of the Attribute
     */
    public String getAttributeValue() {
        return (String) getValue(COLUMNNAME_AttributeValue);
    }

    /**
     * Set Cost.
     *
     * @param Cost Cost information
     */
    public void setCost(BigDecimal Cost) {
        setValue(COLUMNNAME_Cost, Cost);
    }

    /**
     * Get Description.
     *
     * @return Optional short description of the record
     */
    public String getDescription() {
        return (String) getValue(COLUMNNAME_Description);
    }

    /**
     * Get Document Action.
     *
     * @return The targeted status of the document
     */
    public String getDocAction() {
        return (String) getValue(COLUMNNAME_DocAction);
    }

    /**
     * Set Duration.
     *
     * @param Duration Normal Duration in Duration Unit
     */
    public void setDuration(int Duration) {
        setValue(COLUMNNAME_Duration, Integer.valueOf(Duration));
    }

    /**
     * Get EMail Address.
     *
     * @return Electronic Mail Address
     */
    public String getEMail() {
        return (String) getValue(COLUMNNAME_EMail);
    }

    /**
     * Get EMail Recipient.
     *
     * @return Recipient of the EMail
     */
    public String getEMailRecipient() {
        return (String) getValue(COLUMNNAME_EMailRecipient);
    }

    /**
     * Set Entity Type.
     *
     * @param EntityType Dictionary Entity Type; Determines ownership and synchronization
     */
    public void setEntityType(String EntityType) {

        setValue(COLUMNNAME_EntityType, EntityType);
    }

    /**
     * Get Comment/Help.
     *
     * @return Comment or Hint
     */
    public String getHelp() {
        return (String) getValue(COLUMNNAME_Help);
    }

    /**
     * Set Centrally maintained.
     *
     * @param IsCentrallyMaintained Information maintained in System Element table
     */
    public void setIsCentrallyMaintained(boolean IsCentrallyMaintained) {
        setValue(COLUMNNAME_IsCentrallyMaintained, Boolean.valueOf(IsCentrallyMaintained));
    }

    /**
     * Get Join Element.
     *
     * @return Semantics for multiple incoming Transitions
     */
    public String getJoinElement() {
        return (String) getValue(COLUMNNAME_JoinElement);
    }

    /**
     * Set Join Element.
     *
     * @param JoinElement Semantics for multiple incoming Transitions
     */
    public void setJoinElement(String JoinElement) {

        setValue(COLUMNNAME_JoinElement, JoinElement);
    }

    /**
     * Get Duration Limit.
     *
     * @return Maximum Duration in Duration Unit
     */
    public int getLimit() {
        Integer ii = (Integer) getValue(COLUMNNAME_Limit);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Duration Limit.
     *
     * @param Limit Maximum Duration in Duration Unit
     */
    public void setLimit(int Limit) {
        setValue(COLUMNNAME_Limit, Integer.valueOf(Limit));
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
     * Get Mail Template.
     *
     * @return Text templates for mailings
     */
    public int getR_MailText_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_R_MailText_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Split Element.
     *
     * @return Semantics for multiple outgoing Transitions
     */
    public String getSplitElement() {
        return (String) getValue(COLUMNNAME_SplitElement);
    }

    /**
     * Set Split Element.
     *
     * @param SplitElement Semantics for multiple outgoing Transitions
     */
    public void setSplitElement(String SplitElement) {

        setValue(COLUMNNAME_SplitElement, SplitElement);
    }

    /**
     * Set Waiting Time.
     *
     * @param WaitingTime Workflow Simulation Waiting time
     */
    public void setWaitingTime(int WaitingTime) {
        setValue(COLUMNNAME_WaitingTime, Integer.valueOf(WaitingTime));
    }

    /**
     * Get Wait Time.
     *
     * @return Time in minutes to wait (sleep)
     */
    public int getWaitTime() {
        Integer ii = (Integer) getValue(COLUMNNAME_WaitTime);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set X Position.
     *
     * @param XPosition Absolute X (horizontal) position in 1/72 of an inch
     */
    public void setXPosition(int XPosition) {
        setValue(COLUMNNAME_XPosition, Integer.valueOf(XPosition));
    }

    /**
     * Set Y Position.
     *
     * @param YPosition Absolute Y (vertical) position in 1/72 of an inch
     */
    public void setYPosition(int YPosition) {
        setValue(COLUMNNAME_YPosition, Integer.valueOf(YPosition));
    }

    @Override
    public int getTableId() {
        return I_AD_WF_Node.Table_ID;
    }
}
