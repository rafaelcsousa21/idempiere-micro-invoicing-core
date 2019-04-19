package org.compiere.schedule;

import kotliquery.Row;
import org.compiere.model.SchedulerLog;
import org.compiere.orm.PO;

/**
 * Generated Model for AD_SchedulerLog
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_SchedulerLog extends PO implements SchedulerLog {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_SchedulerLog(int AD_SchedulerLog_ID) {
        super(AD_SchedulerLog_ID);
    }

    /**
     * Load Constructor
     */
    public X_AD_SchedulerLog(Row row) {
        super(row);
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
        StringBuffer sb = new StringBuffer("X_AD_SchedulerLog[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Set Scheduler.
     *
     * @param AD_Scheduler_ID Schedule Processes
     */
    public void setSchedulerId(int AD_Scheduler_ID) {
        if (AD_Scheduler_ID < 1) setValueNoCheck(COLUMNNAME_AD_Scheduler_ID, null);
        else setValueNoCheck(COLUMNNAME_AD_Scheduler_ID, AD_Scheduler_ID);
    }

    /**
     * Set Error.
     *
     * @param IsError An Error occurred in the execution
     */
    public void setIsError(boolean IsError) {
        setValue(COLUMNNAME_IsError, IsError);
    }

    /**
     * Set Reference.
     *
     * @param Reference Reference for this record
     */
    public void setReference(String Reference) {
        setValue(COLUMNNAME_Reference, Reference);
    }

    /**
     * Set Summary.
     *
     * @param Summary Textual summary of this request
     */
    public void setSummary(String Summary) {
        setValue(COLUMNNAME_Summary, Summary);
    }

    @Override
    public int getTableId() {
        return SchedulerLog.Table_ID;
    }
}
