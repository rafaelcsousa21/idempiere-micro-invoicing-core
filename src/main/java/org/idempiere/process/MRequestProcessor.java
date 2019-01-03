package org.idempiere.process;

import org.compiere.accounting.MClient;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.AdempiereProcessor2;
import org.compiere.model.AdempiereProcessorLog;
import org.compiere.schedule.MSchedule;
import org.compiere.util.Msg;
import org.idempiere.common.util.CLogger;

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
   * Get Active Request Processors
   *
   * @param ctx context
   * @return array of Request
   */
  public static MRequestProcessor[] getActive(Properties ctx) {
    ArrayList<MRequestProcessor> list = new ArrayList<MRequestProcessor>();
    String sql = "SELECT * FROM R_RequestProcessor WHERE IsActive='Y'";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = prepareStatement(sql, null);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(new MRequestProcessor(ctx, rs, null));
    } catch (Exception e) {
      s_log.log(Level.SEVERE, sql, e);
    } finally {
      close(rs, pstmt);
      rs = null;
      pstmt = null;
    }
    MRequestProcessor[] retValue = new MRequestProcessor[list.size()];
    list.toArray(retValue);
    return retValue;
  } //	getActive

  /** Static Logger */
  private static CLogger s_log = CLogger.getCLogger(MRequestProcessor.class);

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param R_RequestProcessor_ID id
   */
  public MRequestProcessor(Properties ctx, int R_RequestProcessor_ID, String trxName) {
    super(ctx, R_RequestProcessor_ID, trxName);
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
  public MRequestProcessor(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MRequestProcessor

  /**
   * Parent Constructor
   *
   * @param parent parent
   * @param Supervisor_ID Supervisor
   */
  public MRequestProcessor(MClient parent, int Supervisor_ID) {
    this(parent.getCtx(), 0, null);
    setClientOrg(parent);
    setName(parent.getName() + " - " + Msg.translate(getCtx(), "R_RequestProcessor_ID"));
    setSupervisor_ID(Supervisor_ID);
  } //	MRequestProcessor

  /** The Lines */
  private MRequestProcessorRoute[] m_routes = null;

  /**
   * Get Routes
   *
   * @param reload reload data
   * @return array of routes
   */
  public MRequestProcessorRoute[] getRoutes(boolean reload) {
    if (m_routes != null && !reload) return m_routes;

    String sql =
        "SELECT * FROM R_RequestProcessor_Route WHERE R_RequestProcessor_ID=? ORDER BY SeqNo";
    ArrayList<MRequestProcessorRoute> list = new ArrayList<MRequestProcessorRoute>();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = prepareStatement(sql, null);
      pstmt.setInt(1, getR_RequestProcessor_ID());
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(new MRequestProcessorRoute(getCtx(), rs, null));
    } catch (Exception e) {
      log.log(Level.SEVERE, sql, e);
    } finally {
      close(rs, pstmt);
      rs = null;
      pstmt = null;
    }
    //
    m_routes = new MRequestProcessorRoute[list.size()];
    list.toArray(m_routes);
    return m_routes;
  } //	getRoutes

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
      pstmt = prepareStatement(sql, null);
      pstmt.setInt(1, getR_RequestProcessor_ID());
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(new MRequestProcessorLog(getCtx(), rs, null));
    } catch (Exception e) {
      log.log(Level.SEVERE, sql, e);
    } finally {
      close(rs, pstmt);
      rs = null;
      pstmt = null;
    }
    MRequestProcessorLog[] retValue = new MRequestProcessorLog[list.size()];
    list.toArray(retValue);
    return retValue;
  } //	getLogs

  /**
   * Delete old Request Log
   *
   * @return number of records
   */
  public int deleteLog() {
    if (getKeepLogDays() < 1) return 0;
    String sql =
        "DELETE R_RequestProcessorLog "
            + "WHERE R_RequestProcessor_ID="
            + getR_RequestProcessor_ID()
            + " AND (Created+"
            + getKeepLogDays()
            + ") < SysDate";
    int no = executeUpdate(sql, null);
    return no;
  } //	deleteLog

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
} //	MRequestProcessor
