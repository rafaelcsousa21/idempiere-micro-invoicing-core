package org.compiere.schedule;

import org.compiere.model.AdempiereProcessor;
import org.compiere.model.AdempiereProcessor2;
import org.compiere.model.I_AD_SchedulerRecipient;
import org.compiere.model.I_AD_Scheduler_Para;
import org.compiere.orm.MColumn;
import org.compiere.orm.MTable;
import org.compiere.orm.MUserRoles;
import org.compiere.orm.Query;
import org.compiere.util.DisplayType;
import org.compiere.util.Msg;
import org.idempiere.icommon.model.IPO;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

import static software.hsharp.core.util.DBKt.executeUpdateEx;

/**
 * Scheduler Model
 *
 * @author Jorg Janke
 * @version $Id: MScheduler.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * <p>Contributors: Carlos Ruiz - globalqss - FR [3135351] - Enable Scheduler for buttons
 */
public class MScheduler extends X_AD_Scheduler implements AdempiereProcessor, AdempiereProcessor2 {
    /**
     *
     */
    private static final long serialVersionUID = 5106574386025319255L;
    /**
     * Process Parameter
     */
    private MSchedulerPara[] m_parameter = null;
    /**
     * Process Recipients
     */
    private MSchedulerRecipient[] m_recipients = null;

    /**
     * Standard Constructor
     *
     * @param ctx             context
     * @param AD_Scheduler_ID id
     * @param trxName         transaction
     */
    public MScheduler(Properties ctx, int AD_Scheduler_ID) {
        super(ctx, AD_Scheduler_ID);
        if (AD_Scheduler_ID == 0) {
            setKeepLogDays(7);
        }
    } //	MScheduler

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MScheduler(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MScheduler

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
     * Delete old Request Log
     *
     * @return number of records
     */
    public int deleteLog() {
        if (getKeepLogDays() < 1) return 0;
        String sql =
                "DELETE AD_SchedulerLog "
                        + "WHERE AD_Scheduler_ID="
                        + getSchedulerId()
                        + " AND (Created+"
                        + getKeepLogDays()
                        + ") < SysDate";
        int no = executeUpdateEx(sql);
        return no;
    } //	deleteLog

    /**
     * Get Parameters
     *
     * @param reload reload
     * @return parameter
     */
    public MSchedulerPara[] getParameters(boolean reload) {
        if (!reload && m_parameter != null) return m_parameter;
        //
        final String whereClause = MSchedulerPara.COLUMNNAME_AD_Scheduler_ID + "=?";
        List<MSchedulerPara> list =
                new Query(getCtx(), I_AD_Scheduler_Para.Table_Name, whereClause)
                        .setParameters(getSchedulerId())
                        .setOnlyActiveRecords(true)
                        .list();
        m_parameter = new MSchedulerPara[list.size()];
        list.toArray(m_parameter);
        return m_parameter;
    } //	getParameter

    /**
     * Get Recipients
     *
     * @param reload reload
     * @return Recipients
     */
    public MSchedulerRecipient[] getRecipients(boolean reload) {
        if (!reload && m_recipients != null) return m_recipients;
        //
        final String whereClause = MSchedulerRecipient.COLUMNNAME_AD_Scheduler_ID + "=?";
        List<MSchedulerRecipient> list =
                new Query(getCtx(), I_AD_SchedulerRecipient.Table_Name, whereClause)
                        .setParameters(getSchedulerId())
                        .setOnlyActiveRecords(true)
                        .list();
        m_recipients = new MSchedulerRecipient[list.size()];
        list.toArray(m_recipients);
        return m_recipients;
    } //	getRecipients

    /**
     * Get Recipient AD_User_IDs
     *
     * @return array of user IDs
     */
    public Integer[] getRecipientAD_User_IDs() {
        TreeSet<Integer> list = new TreeSet<Integer>();
        MSchedulerRecipient[] recipients = getRecipients(false);
        for (int i = 0; i < recipients.length; i++) {
            MSchedulerRecipient recipient = recipients[i];
            if (!recipient.isActive()) continue;
            if (recipient.getUserId() != 0) {
                list.add(recipient.getUserId());
            }
            if (recipient.getRoleId() != 0) {
                MUserRoles[] urs = MUserRoles.getOfRole(getCtx(), recipient.getRoleId());
                for (int j = 0; j < urs.length; j++) {
                    MUserRoles ur = urs[j];
                    if (!ur.isActive()) continue;
                    if (!list.contains(ur.getUserId())) list.add(ur.getUserId());
                }
            }
        }
        //
        return list.toArray(new Integer[list.size()]);
    } //	getRecipientAD_User_IDs

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    @Override
    protected boolean beforeSave(boolean newRecord) {

        // FR [3135351] - Enable Scheduler for buttons
        if (getDBTableId() > 0) {
            // Validate the table has any button referencing the process
            int colid =
                    new Query(
                            getCtx(),
                            MColumn.Table_Name,
                            "AD_Table_ID=? AND AD_Reference_ID=? AND AD_Process_ID=?"
                    )
                            .setOnlyActiveRecords(true)
                            .setParameters(getDBTableId(), DisplayType.Button, getProcessId())
                            .firstId();
            if (colid <= 0) {
                log.saveError("Error", Msg.getMsg(getCtx(), "TableMustHaveProcessButton"));
                return false;
            }
        } else {
            setRecordId(-1);
        }

        if (getRecordId() != 0) {
            // Validate AD_Table_ID must be set
            if (getDBTableId() <= 0) {
                log.saveError("Error", Msg.getMsg(getCtx(), "MustFillTable"));
                return false;
            }
            // Validate the record must exists on the same client of the scheduler
            MTable table = MTable.get(getCtx(), getDBTableId());
            IPO po = (IPO) table.getPO(getRecordId());
            if (po == null || po.getId() <= 0 || po.getClientId() != getClientId()) {
                log.saveError("Error", Msg.getMsg(getCtx(), "NoRecordID"));
                return false;
            }
        }

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

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MScheduler[");
        sb.append(getId()).append("-").append(getName()).append("]");
        return sb.toString();
    } //	toString

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

} //	MScheduler
