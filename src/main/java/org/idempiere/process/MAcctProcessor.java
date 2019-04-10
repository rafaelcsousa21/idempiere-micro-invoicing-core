package org.idempiere.process;

import kotliquery.Row;
import org.compiere.accounting.MClient;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.AdempiereProcessor2;
import org.compiere.schedule.MSchedule;
import org.compiere.util.MsgKt;

import java.sql.Timestamp;


public class MAcctProcessor extends X_C_AcctProcessor
        implements AdempiereProcessor, AdempiereProcessor2 {
    /**
     *
     */
    private static final long serialVersionUID = -4760475718973777369L;

    /**
     * Standard Construvtor
     *
     * @param ctx                context
     * @param C_AcctProcessor_ID id
     * @param trxName            transaction
     */
    public MAcctProcessor(int C_AcctProcessor_ID) {
        super(C_AcctProcessor_ID);
        if (C_AcctProcessor_ID == 0) {
            //	setName (null);
            //	setSupervisorId (0);
            //	setFrequencyType (FREQUENCYTYPE_Hour);
            //	setFrequency (1);
            setKeepLogDays(7); // 7
        }
    } //	MAcctProcessor

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MAcctProcessor(Row row) {
        super(row);
    } //	MAcctProcessor

    /**
     * Parent Constructor
     *
     * @param client        parent
     * @param Supervisor_ID admin
     */
    public MAcctProcessor(MClient client, int Supervisor_ID) {
        this(0);
        setClientOrg(client);
        StringBuilder msgset =
                new StringBuilder()
                        .append(client.getName())
                        .append(" - ")
                        .append(MsgKt.translate("C_AcctProcessor_ID"));
        setName(msgset.toString());
        setSupervisorId(Supervisor_ID);
    } //	MAcctProcessor

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

    /**
     * Get Date Next Run
     *
     * @param requery requery
     * @return date next run
     */
    public Timestamp getDateNextRun(boolean requery) {
        if (requery) loadFromMap(null);
        return getDateNextRun();
    } //	getDateNextRun

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
} //	MAcctProcessor
