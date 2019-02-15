package org.idempiere.process;

import org.compiere.accounting.MClient;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.AdempiereProcessor2;
import org.compiere.model.AdempiereProcessorLog;
import org.compiere.schedule.MSchedule;
import org.compiere.util.Msg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.*;


public class MRequestProcessor extends X_R_RequestProcessor
    implements AdempiereProcessor, AdempiereProcessor2 {
  /** */
  private static final long serialVersionUID = 8231854734466233461L;

    /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param R_RequestProcessor_ID id
   */
  public MRequestProcessor(Properties ctx, int R_RequestProcessor_ID) {
    super(ctx, R_RequestProcessor_ID);
    if (R_RequestProcessor_ID == 0) {
      //	setName (null);
      // setFrequencyType (FREQUENCYTYPE_Day);
      // setFrequency (0);
      setKeepLogDays(7);
      setOverdueAlertDays(0);
      setOverdueAssignDays(0);
      setRemindDays(0);
      //	setSupervisor_ID (0);
    }
  } //	MRequestProcessor

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   */
  public MRequestProcessor(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MRequestProcessor

  /**
   * Parent Constructor
   *
   * @param parent parent
   * @param Supervisor_ID Supervisor
   */
  public MRequestProcessor(MClient parent, int Supervisor_ID) {
    this(parent.getCtx(), 0);
    setClientOrg(parent);
    setName(parent.getName() + " - " + Msg.translate(getCtx(), "R_RequestProcessor_ID"));
    setSupervisor_ID(Supervisor_ID);
  } //	MRequestProcessor

    /**
   * Get Logs
   *
   * @return Array of Logs
   */
  public AdempiereProcessorLog[] getLogs() {
    ArrayList<MRequestProcessorLog> list = new ArrayList<MRequestProcessorLog>();
    String sql =
        "SELECT * "
            + "FROM R_RequestProcessorLog "
            + "WHERE R_RequestProcessor_ID=? "
            + "ORDER BY Created DESC";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = prepareStatement(sql);
      pstmt.setInt(1, getR_RequestProcessor_ID());
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(new MRequestProcessorLog(getCtx(), rs));
    } catch (Exception e) {
      log.log(Level.SEVERE, sql, e);
    } finally {

      rs = null;
      pstmt = null;
    }
    MRequestProcessorLog[] retValue = new MRequestProcessorLog[list.size()];
    list.toArray(retValue);
    return retValue;
  } //	getLogs

    /**
   * Get the date Next run
   *
   * @param requery requery database
   * @return date next run
   */
  public Timestamp getDateNextRun(boolean requery) {
    if (requery) load((HashMap)null);
    return getDateNextRun();
  } //	getDateNextRun

  /**
   * Get Unique ID
   *
   * @return Unique ID
   */
  public String getServerID() {
    return "RequestProcessor" + getId();
  } //	getServerID

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
   * Set Date last run.
   *
   * @param DateLastRun Date the process was last run.
   */
  public void setDateLastRun(Timestamp DateLastRun) {
    set_Value(COLUMNNAME_DateLastRun, DateLastRun);
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
} //	MRequestProcessor
