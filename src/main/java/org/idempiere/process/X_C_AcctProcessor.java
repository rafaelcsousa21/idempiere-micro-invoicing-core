package org.idempiere.process;

import org.compiere.model.I_C_AcctProcessor;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

public class X_C_AcctProcessor extends BasePOName implements I_C_AcctProcessor, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_AcctProcessor(Properties ctx, int C_AcctProcessor_ID, String trxName) {
    super(ctx, C_AcctProcessor_ID, trxName);
  }

  /** Load Constructor */
  public X_C_AcctProcessor(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
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
    StringBuffer sb = new StringBuffer("X_C_AcctProcessor[").append(getId()).append("]");
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
   * Get Accounting Processor.
   *
   * @return Accounting Processor/Server Parameters
   */
  public int getC_AcctProcessor_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_AcctProcessor_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Accounting Schema.
   *
   * @return Rules for accounting
   */
  public int getC_AcctSchema_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_AcctSchema_ID);
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
