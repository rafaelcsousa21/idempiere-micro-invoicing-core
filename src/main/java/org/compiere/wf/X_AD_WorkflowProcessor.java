package org.compiere.wf;

import org.compiere.model.I_AD_WorkflowProcessor;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for AD_WorkflowProcessor
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WorkflowProcessor extends BasePOName
        implements I_AD_WorkflowProcessor, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_WorkflowProcessor(Properties ctx, int AD_WorkflowProcessor_ID) {
        super(ctx, AD_WorkflowProcessor_ID);
        /**
         * if (AD_WorkflowProcessor_ID == 0) { setAD_Schedule_ID (0); setAD_WorkflowProcessor_ID (0);
         * setKeepLogDays (0); // 7 setName (null); setSupervisor_ID (0); }
         */
    }

    /**
     * Load Constructor
     */
    public X_AD_WorkflowProcessor(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_AD_WorkflowProcessor[").append(getId()).append("]");
        return sb.toString();
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
        return I_AD_WorkflowProcessor.Table_ID;
    }
}
