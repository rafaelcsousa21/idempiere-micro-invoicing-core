package org.compiere.wf;

import org.compiere.model.I_AD_Workflow;
import org.compiere.orm.BasePONameValue;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for AD_Workflow
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Workflow extends BasePONameValue implements I_AD_Workflow {

    /**
     * Organization = 1
     */
    public static final String ACCESSLEVEL_Organization = "1";
    /**
     * Year = Y
     */
    public static final String DURATIONUNIT_Year = "Y";
    /**
     * Month = M
     */
    public static final String DURATIONUNIT_Month = "M";
    /**
     * Day = D
     */
    public static final String DURATIONUNIT_Day = "D";
    /**
     * hour = h
     */
    public static final String DURATIONUNIT_Hour = "h";
    /**
     * minute = m
     */
    public static final String DURATIONUNIT_Minute = "m";
    /**
     * second = s
     */
    public static final String DURATIONUNIT_Second = "s";
    /**
     * Under Revision = U
     */
    public static final String PUBLISHSTATUS_UnderRevision = "U";
    /**
     * Document Value = V
     */
    public static final String WORKFLOWTYPE_DocumentValue = "V";
    /**
     * Manufacturing = M
     */
    public static final String WORKFLOWTYPE_Manufacturing = "M";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_Workflow(Properties ctx, int AD_Workflow_ID) {
        super(ctx, AD_Workflow_ID);
    }

    /**
     * Load Constructor
     */
    public X_AD_Workflow(Properties ctx, ResultSet rs) {
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

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_Workflow[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Set Data Access Level.
     *
     * @param AccessLevel Access Level required
     */
    public void setWFAccessLevel(String AccessLevel) {

        setValue(COLUMNNAME_AccessLevel, AccessLevel);
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
    public int getWorkflowResponsibleId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_WF_Responsible_ID);
        if (ii == null) return 0;
        return ii;
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
     * Set Author.
     *
     * @param Author Author/Creator of the Entity
     */
    public void setAuthor(String Author) {
        setValue(COLUMNNAME_Author, Author);
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
     * Get Document Value Logic.
     *
     * @return Logic to determine Workflow Start - If true, a workflow process is started for the
     * document
     */
    public String getDocValueLogic() {
        return (String) getValue(COLUMNNAME_DocValueLogic);
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
     * Get Duration Unit.
     *
     * @return Unit of Duration
     */
    public String getDurationUnit() {
        return (String) getValue(COLUMNNAME_DurationUnit);
    }

    /**
     * Set Duration Unit.
     *
     * @param DurationUnit Unit of Duration
     */
    public void setDurationUnit(String DurationUnit) {

        setValue(COLUMNNAME_DurationUnit, DurationUnit);
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
     * Set Beta Functionality.
     *
     * @param IsBetaFunctionality This functionality is considered Beta
     */
    public void setIsBetaFunctionality(boolean IsBetaFunctionality) {
        setValue(COLUMNNAME_IsBetaFunctionality, Boolean.valueOf(IsBetaFunctionality));
    }

    /**
     * Set Default.
     *
     * @param IsDefault Default value
     */
    public void setIsDefault(boolean IsDefault) {
        setValue(COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
    }

    /**
     * Set Valid.
     *
     * @param IsValid Element is valid
     */
    public void setIsValid(boolean IsValid) {
        setValue(COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
    }

    /**
     * Get Valid.
     *
     * @return Element is valid
     */
    public boolean isValid() {
        Object oo = getValue(COLUMNNAME_IsValid);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
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
     * Set Publication Status.
     *
     * @param PublishStatus Status of Publication
     */
    public void setPublishStatus(String PublishStatus) {

        setValue(COLUMNNAME_PublishStatus, PublishStatus);
    }

    /**
     * Get Valid from.
     *
     * @return Valid from including this date (first day)
     */
    public Timestamp getValidFrom() {
        return (Timestamp) getValue(COLUMNNAME_ValidFrom);
    }

    /**
     * Get Valid to.
     *
     * @return Valid to including this date (last day)
     */
    public Timestamp getValidTo() {
        return (Timestamp) getValue(COLUMNNAME_ValidTo);
    }

    /**
     * Set Version.
     *
     * @param Version Version of the table definition
     */
    public void setVersion(int Version) {
        setValue(COLUMNNAME_Version, Integer.valueOf(Version));
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
     * Get Workflow Type.
     *
     * @return Type of Workflow
     */
    public String getWorkflowType() {
        return (String) getValue(COLUMNNAME_WorkflowType);
    }

    /**
     * Set Working Time.
     *
     * @param WorkingTime Workflow Simulation Execution Time
     */
    public void setWorkingTime(int WorkingTime) {
        setValue(COLUMNNAME_WorkingTime, Integer.valueOf(WorkingTime));
    }

    @Override
    public int getTableId() {
        return I_AD_Workflow.Table_ID;
    }
}
