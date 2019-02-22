package org.compiere.schedule;

import org.compiere.model.I_AD_Scheduler;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for AD_Scheduler
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Scheduler extends BasePOName implements I_AD_Scheduler, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_Scheduler(Properties ctx, int AD_Scheduler_ID) {
        super(ctx, AD_Scheduler_ID);
    }

    /**
     * Load Constructor
     */
    public X_AD_Scheduler(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_AD_Scheduler[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Process.
     *
     * @return Process or Report
     */
    public int getAD_Process_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_Process_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Schedule.
     *
     * @return Schedule
     */
    public int getAD_Schedule_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_Schedule_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Scheduler.
     *
     * @return Schedule Processes
     */
    public int getAD_Scheduler_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_Scheduler_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Table.
     *
     * @return Database Table information
     */
    public int getAD_Table_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_Table_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Date next run.
     *
     * @return Date the process will run next
     */
    public Timestamp getDateNextRun() {
        return (Timestamp) get_Value(COLUMNNAME_DateNextRun);
    }

    /**
     * Set Date next run.
     *
     * @param DateNextRun Date the process will run next
     */
    public void setDateNextRun(Timestamp DateNextRun) {
        set_Value(COLUMNNAME_DateNextRun, DateNextRun);
    }

    /**
     * Get Description.
     *
     * @return Optional short description of the record
     */
    public String getDescription() {
        return (String) get_Value(COLUMNNAME_Description);
    }

    /**
     * Get Days to keep Log.
     *
     * @return Number of days to keep the log entries
     */
    public int getKeepLogDays() {
        Integer ii = (Integer) get_Value(COLUMNNAME_KeepLogDays);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Days to keep Log.
     *
     * @param KeepLogDays Number of days to keep the log entries
     */
    public void setKeepLogDays(int KeepLogDays) {
        set_Value(COLUMNNAME_KeepLogDays, Integer.valueOf(KeepLogDays));
    }

    /**
     * Get Record ID.
     *
     * @return Direct internal record ID
     */
    public int getRecord_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_Record_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Record ID.
     *
     * @param Record_ID Direct internal record ID
     */
    public void setRecord_ID(int Record_ID) {
        if (Record_ID < 0) set_Value(COLUMNNAME_Record_ID, null);
        else set_Value(COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
    }

    /**
     * Get Mail Template.
     *
     * @return Text templates for mailings
     */
    public int getR_MailText_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_R_MailText_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Supervisor.
     *
     * @return Supervisor for this user/organization - used for escalation and approval
     */
    public int getSupervisor_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_Supervisor_ID);
        if (ii == null) return 0;
        return ii;
    }

    @Override
    public int getTableId() {
        return I_AD_Scheduler.Table_ID;
    }
}
