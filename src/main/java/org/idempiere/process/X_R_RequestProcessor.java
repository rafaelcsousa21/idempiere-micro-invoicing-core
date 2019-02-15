package org.idempiere.process;

import org.compiere.model.I_R_RequestProcessor;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

public class X_R_RequestProcessor extends BasePOName implements I_R_RequestProcessor, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_RequestProcessor(Properties ctx, int R_RequestProcessor_ID) {
    super(ctx, R_RequestProcessor_ID);
  }

  /** Load Constructor */
  public X_R_RequestProcessor(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  }

  /**
   * AccessLevel
   *
   * @return 2 - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  @Override
  public int getTableId() {
    return Table_ID;
  }


  public String toString() {
    StringBuffer sb = new StringBuffer("X_R_RequestProcessor[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Set Schedule.
   *
   * @param AD_Schedule_ID Schedule
   */
  public void setAD_Schedule_ID(int AD_Schedule_ID) {
    if (AD_Schedule_ID < 1) set_Value(COLUMNNAME_AD_Schedule_ID, null);
    else set_Value(COLUMNNAME_AD_Schedule_ID, Integer.valueOf(AD_Schedule_ID));
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
   * Set Date next run.
   *
   * @param DateNextRun Date the process will run next
   */
  public void setDateNextRun(Timestamp DateNextRun) {
    set_Value(COLUMNNAME_DateNextRun, DateNextRun);
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
   * Set Days to keep Log.
   *
   * @param KeepLogDays Number of days to keep the log entries
   */
  public void setKeepLogDays(int KeepLogDays) {
    set_Value(COLUMNNAME_KeepLogDays, Integer.valueOf(KeepLogDays));
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
   * Set Alert after Days Due.
   *
   * @param OverdueAlertDays Send email alert after number of days due (0=no alerts)
   */
  public void setOverdueAlertDays(int OverdueAlertDays) {
    set_Value(COLUMNNAME_OverdueAlertDays, Integer.valueOf(OverdueAlertDays));
  }

    /**
   * Set Escalate after Days Due.
   *
   * @param OverdueAssignDays Escalation to superior after number of due days (0 = no)
   */
  public void setOverdueAssignDays(int OverdueAssignDays) {
    set_Value(COLUMNNAME_OverdueAssignDays, Integer.valueOf(OverdueAssignDays));
  }

    /**
   * Set Reminder Days.
   *
   * @param RemindDays Days between sending Reminder Emails for a due or inactive Document
   */
  public void setRemindDays(int RemindDays) {
    set_Value(COLUMNNAME_RemindDays, Integer.valueOf(RemindDays));
  }

    /**
   * Get Request Processor.
   *
   * @return Processor for Requests
   */
  public int getR_RequestProcessor_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_RequestProcessor_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Request Type.
   *
   * @return Type of request (e.g. Inquiry, Complaint, ..)
   */
  public int getR_RequestType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_RequestType_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Supervisor.
   *
   * @param Supervisor_ID Supervisor for this user/organization - used for escalation and approval
   */
  public void setSupervisor_ID(int Supervisor_ID) {
    if (Supervisor_ID < 1) set_Value(COLUMNNAME_Supervisor_ID, null);
    else set_Value(COLUMNNAME_Supervisor_ID, Integer.valueOf(Supervisor_ID));
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
}
