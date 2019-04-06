package org.idempiere.process;

import kotliquery.Row;
import org.compiere.accounting.MClient;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.AdempiereProcessor2;
import org.compiere.schedule.MSchedule;
import org.compiere.util.Msg;

import java.sql.Timestamp;
import java.util.HashMap;


public class MRequestProcessor extends X_R_RequestProcessor
        implements AdempiereProcessor, AdempiereProcessor2 {
    /**
     *
     */
    private static final long serialVersionUID = 8231854734466233461L;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx                   context
     * @param R_RequestProcessor_ID id
     */
    public MRequestProcessor(int R_RequestProcessor_ID) {
        super(R_RequestProcessor_ID);
        if (R_RequestProcessor_ID == 0) {
            //	setName (null);
            // setFrequencyType (FREQUENCYTYPE_Day);
            // setFrequency (0);
            setKeepLogDays(7);
            setOverdueAlertDays(0);
            setOverdueAssignDays(0);
            setRemindDays(0);
            //	setSupervisorId (0);
        }
    } //	MRequestProcessor

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MRequestProcessor(Row row) {
        super(row);
    } //	MRequestProcessor

    /**
     * Parent Constructor
     *
     * @param parent        parent
     * @param Supervisor_ID Supervisor
     */
    public MRequestProcessor(MClient parent, int Supervisor_ID) {
        this(0);
        setClientOrg(parent);
        setName(parent.getName() + " - " + Msg.translate("R_RequestProcessor_ID"));
        setSupervisorId(Supervisor_ID);
    } //	MRequestProcessor

    /**
     * Get the date Next run
     *
     * @param requery requery database
     * @return date next run
     */
    public Timestamp getDateNextRun(boolean requery) {
        if (requery) loadFromMap(null);
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
        if (newRecord || isValueChanged("AD_Schedule_ID")) {
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
        return MSchedule.get(getScheduleId()).getFrequencyType();
    }

    @Override
    public int getFrequency() {
        return MSchedule.get(getScheduleId()).getFrequency();
    }

    @Override
    public boolean isIgnoreProcessingTime() {
        return MSchedule.get(getScheduleId()).isIgnoreProcessingTime();
    }

    @Override
    public String getScheduleType() {
        return MSchedule.get(getScheduleId()).getScheduleType();
    }

    @Override
    public String getCronPattern() {
        return MSchedule.get(getScheduleId()).getCronPattern();
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
        setValue(COLUMNNAME_DateLastRun, DateLastRun);
    }
} //	MRequestProcessor
