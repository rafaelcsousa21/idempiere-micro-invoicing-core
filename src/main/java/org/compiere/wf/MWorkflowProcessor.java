package org.compiere.wf;

import org.compiere.model.AdempiereProcessor;
import org.compiere.model.AdempiereProcessor2;
import org.compiere.schedule.MSchedule;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
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
        return MSchedule.get(getCtx(), getScheduleId()).getFrequencyType();
    }

    @Override
    public int getFrequency() {
        return MSchedule.get(getCtx(), getScheduleId()).getFrequency();
    }

    @Override
    public boolean isIgnoreProcessingTime() {
        return MSchedule.get(getCtx(), getScheduleId()).isIgnoreProcessingTime();
    }

    @Override
    public String getScheduleType() {
        return MSchedule.get(getCtx(), getScheduleId()).getScheduleType();
    }

    @Override
    public String getCronPattern() {
        return MSchedule.get(getCtx(), getScheduleId()).getCronPattern();
    }

    /**
     * Get Date last run.
     *
     * @return Date the process was last run.
     */
    public Timestamp getDateLastRun() {
        return (Timestamp) getValue(COLUMNNAME_DateLastRun);
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
