package org.idempiere.process;

import kotliquery.Row;
import org.compiere.accounting.MClient;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.AdempiereProcessor2;
import org.compiere.schedule.MSchedule;
import org.compiere.util.Msg;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Properties;


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
    public MAcctProcessor(Properties ctx, int C_AcctProcessor_ID) {
        super(ctx, C_AcctProcessor_ID);
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
    public MAcctProcessor(Properties ctx, Row row) {
        super(ctx, row);
    } //	MAcctProcessor

    /**
     * Parent Constructor
     *
     * @param client        parent
     * @param Supervisor_ID admin
     */
    public MAcctProcessor(MClient client, int Supervisor_ID) {
        this(client.getCtx(), 0);
        setClientOrg(client);
        StringBuilder msgset =
                new StringBuilder()
                        .append(client.getName())
                        .append(" - ")
                        .append(Msg.translate(getCtx(), "C_AcctProcessor_ID"));
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
        if (requery) load((HashMap) null);
        return getDateNextRun();
    } //	getDateNextRun

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
        setValue(COLUMNNAME_DateLastRun, DateLastRun);
    }
} //	MAcctProcessor
