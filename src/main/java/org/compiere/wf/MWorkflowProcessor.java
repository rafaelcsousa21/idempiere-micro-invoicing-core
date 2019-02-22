package org.compiere.wf;

import org.compiere.model.AdempiereProcessor;
import org.compiere.model.AdempiereProcessor2;
import org.compiere.model.AdempiereProcessorLog;
import org.compiere.orm.Query;
import org.compiere.schedule.MSchedule;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;


/**
 * Workflow Processor Model
 *
 * @author Jorg Janke
 * @version $Id: MWorkflowProcessor.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWorkflowProcessor extends X_AD_WorkflowProcessor
        implements AdempiereProcessor, AdempiereProcessor2 {
    /**
     *
     */
    private static final long serialVersionUID = 6110376502075157361L;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx                     context
     * @param AD_WorkflowProcessor_ID id
     * @param trxName                 transaction
     */
    public MWorkflowProcessor(Properties ctx, int AD_WorkflowProcessor_ID) {
        super(ctx, AD_WorkflowProcessor_ID);
    } //	MWorkflowProcessor

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MWorkflowProcessor(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MWorkflowProcessor

    /**
     * Get Server ID
     *
     * @return id
     */
    public String getServerID() {
        return "WorkflowProcessor" + getId();
    } //	getServerID

    /**
     * Get Date Next Run
     *
     * @param requery requery
     * @return date next run
     */
    public Timestamp getDateNextRun(boolean requery) {
        if (requery) load((HashMap) null);
        return getDateNextRun();
    } //	getDateNextRun

    /**
     * Get Logs
     *
     * @return logs
     */
    public AdempiereProcessorLog[] getLogs() {
        List<MWorkflowProcessorLog> list =
                new Query(
                        getCtx(),
                        MWorkflowProcessorLog.Table_Name,
                        "AD_WorkflowProcessor_ID=?"
                )
                        .setParameters(new Object[]{getAD_WorkflowProcessor_ID()})
                        .setOrderBy("Created DESC")
                        .list();
        MWorkflowProcessorLog[] retValue = new MWorkflowProcessorLog[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getLogs

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    @Override
    protected boolean beforeSave(boolean newRecord) {
        if (newRecord || is_ValueChanged("AD_Schedule_ID")) {
            long nextWork =
                    MSchedule.getNextRunMS(
                            System.currentTimeMillis(),
                            getScheduleType(),
                            getFrequencyType(),
                            getFrequency(),
                            getCronPattern());
            if (nextWork > 0) setDateNextRun(new Timestamp(nextWork));
        }

        return true;
    } //	beforeSave

    @Override
    public String getFrequencyType() {
        return MSchedule.get(getCtx(), getAD_Schedule_ID()).getFrequencyType();
    }

    @Override
    public int getFrequency() {
        return MSchedule.get(getCtx(), getAD_Schedule_ID()).getFrequency();
    }

    @Override
    public boolean isIgnoreProcessingTime() {
        return MSchedule.get(getCtx(), getAD_Schedule_ID()).isIgnoreProcessingTime();
    }

    @Override
    public String getScheduleType() {
        return MSchedule.get(getCtx(), getAD_Schedule_ID()).getScheduleType();
    }

    @Override
    public String getCronPattern() {
        return MSchedule.get(getCtx(), getAD_Schedule_ID()).getCronPattern();
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
     * Get Date last run.
     *
     * @return Date the process was last run.
     */
    public Timestamp getDateLastRun() {
        return (Timestamp) get_Value(COLUMNNAME_DateLastRun);
    }

    /**
     * Set Date last run.
     *
     * @param DateLastRun Date the process was last run.
     */
    public void setDateLastRun(Timestamp DateLastRun) {
        set_Value(COLUMNNAME_DateLastRun, DateLastRun);
    }

} //	MWorkflowProcessor
