package org.compiere.schedule;

import org.compiere.model.I_AD_Scheduler;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for AD_Scheduler
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Scheduler extends BasePOName implements I_AD_Scheduler, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_Scheduler(Properties ctx, int AD_Scheduler_ID, String trxName) {
    super(ctx, AD_Scheduler_ID, trxName);
  }

  /** Load Constructor */
  public X_AD_Scheduler(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
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
    StringBuffer sb = new StringBuffer("X_AD_Scheduler[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException {
    return (org.compiere.model.I_AD_Process)
        MTable.get(getCtx(), org.compiere.model.I_AD_Process.Table_Name)
            .getPO(getAD_Process_ID(), null);
  }

  /**
   * Set Process.
   *
   * @param AD_Process_ID Process or Report
   */
  public void setAD_Process_ID(int AD_Process_ID) {
    if (AD_Process_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_Process_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
  }

  /**
   * Get Process.
   *
   * @return Process or Report
   */
  public int getAD_Process_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Process_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_AD_Schedule getAD_Schedule() throws RuntimeException {
    return (org.compiere.model.I_AD_Schedule)
        MTable.get(getCtx(), org.compiere.model.I_AD_Schedule.Table_Name)
            .getPO(getAD_Schedule_ID(), null);
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
   * Set Scheduler.
   *
   * @param AD_Scheduler_ID Schedule Processes
   */
  public void setAD_Scheduler_ID(int AD_Scheduler_ID) {
    if (AD_Scheduler_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_Scheduler_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_Scheduler_ID, Integer.valueOf(AD_Scheduler_ID));
  }

  /**
   * Get Scheduler.
   *
   * @return Schedule Processes
   */
  public int getAD_Scheduler_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Scheduler_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set AD_Scheduler_UU.
   *
   * @param AD_Scheduler_UU AD_Scheduler_UU
   */
  public void setAD_Scheduler_UU(String AD_Scheduler_UU) {
    set_Value(COLUMNNAME_AD_Scheduler_UU, AD_Scheduler_UU);
  }

  /**
   * Get AD_Scheduler_UU.
   *
   * @return AD_Scheduler_UU
   */
  public String getAD_Scheduler_UU() {
    return (String) get_Value(COLUMNNAME_AD_Scheduler_UU);
  }

  public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException {
    return (org.compiere.model.I_AD_Table)
        MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
            .getPO(getAD_Table_ID(), null);
  }

  /**
   * Set Table.
   *
   * @param AD_Table_ID Database Table information
   */
  public void setAD_Table_ID(int AD_Table_ID) {
    if (AD_Table_ID < 1) set_Value(COLUMNNAME_AD_Table_ID, null);
    else set_Value(COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
  }

  /**
   * Get Table.
   *
   * @return Database Table information
   */
  public int getAD_Table_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Table_ID);
    if (ii == null) return 0;
    return ii;
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
   * Get Date last run.
   *
   * @return Date the process was last run.
   */
  public Timestamp getDateLastRun() {
    return (Timestamp) get_Value(COLUMNNAME_DateLastRun);
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
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(COLUMNNAME_Description, Description);
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
   * Set Process Now.
   *
   * @param Processing Process Now
   */
  public void setProcessing(boolean Processing) {
    set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
  }

  /**
   * Get Process Now.
   *
   * @return Process Now
   */
  public boolean isProcessing() {
    Object oo = get_Value(COLUMNNAME_Processing);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Record ID.
   *
   * @param Record_ID Direct internal record ID
   */
  public void setRecord_ID(int Record_ID) {
    if (Record_ID < 0) set_Value(COLUMNNAME_Record_ID, null);
    else set_Value(COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
  }

  /**
   * Get Record ID.
   *
   * @return Direct internal record ID
   */
  public int getRecord_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Record_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_R_MailText getR_MailText() throws RuntimeException {
    return (org.compiere.model.I_R_MailText)
        MTable.get(getCtx(), org.compiere.model.I_R_MailText.Table_Name)
            .getPO(getR_MailText_ID(), null);
  }

  /**
   * Set Mail Template.
   *
   * @param R_MailText_ID Text templates for mailings
   */
  public void setR_MailText_ID(int R_MailText_ID) {
    if (R_MailText_ID < 1) set_ValueNoCheck(COLUMNNAME_R_MailText_ID, null);
    else set_ValueNoCheck(COLUMNNAME_R_MailText_ID, Integer.valueOf(R_MailText_ID));
  }

  /**
   * Get Mail Template.
   *
   * @return Text templates for mailings
   */
  public int getR_MailText_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_MailText_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_AD_User getSupervisor() throws RuntimeException {
    return (org.compiere.model.I_AD_User)
        MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
            .getPO(getSupervisor_ID(), null);
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

  @Override
  public int getTableId() {
    return I_AD_Scheduler.Table_ID;
  }
}
