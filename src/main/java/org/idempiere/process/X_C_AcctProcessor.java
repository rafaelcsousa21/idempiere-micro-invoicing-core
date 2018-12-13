package org.idempiere.process;

import org.compiere.model.I_C_AcctProcessor;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
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

  public org.compiere.model.I_AD_Schedule getAD_Schedule() throws RuntimeException {
    return (org.compiere.model.I_AD_Schedule)
        MTable.get(getCtx(), org.compiere.model.I_AD_Schedule.Table_Name)
            .getPO(getAD_Schedule_ID(), get_TrxName());
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

  public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException {
    return (org.compiere.model.I_AD_Table)
        MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
            .getPO(getAD_Table_ID(), get_TrxName());
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
   * Set Accounting Processor.
   *
   * @param C_AcctProcessor_ID Accounting Processor/Server Parameters
   */
  public void setC_AcctProcessor_ID(int C_AcctProcessor_ID) {
    if (C_AcctProcessor_ID < 1) set_ValueNoCheck(COLUMNNAME_C_AcctProcessor_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_AcctProcessor_ID, Integer.valueOf(C_AcctProcessor_ID));
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
   * Set C_AcctProcessor_UU.
   *
   * @param C_AcctProcessor_UU C_AcctProcessor_UU
   */
  public void setC_AcctProcessor_UU(String C_AcctProcessor_UU) {
    set_Value(COLUMNNAME_C_AcctProcessor_UU, C_AcctProcessor_UU);
  }

  /**
   * Get C_AcctProcessor_UU.
   *
   * @return C_AcctProcessor_UU
   */
  public String getC_AcctProcessor_UU() {
    return (String) get_Value(COLUMNNAME_C_AcctProcessor_UU);
  }

  public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException {
    return (org.compiere.model.I_C_AcctSchema)
        MTable.get(getCtx(), org.compiere.model.I_C_AcctSchema.Table_Name)
            .getPO(getC_AcctSchema_ID(), get_TrxName());
  }

  /**
   * Set Accounting Schema.
   *
   * @param C_AcctSchema_ID Rules for accounting
   */
  public void setC_AcctSchema_ID(int C_AcctSchema_ID) {
    if (C_AcctSchema_ID < 1) set_Value(COLUMNNAME_C_AcctSchema_ID, null);
    else set_Value(COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
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

  public org.compiere.model.I_AD_User getSupervisor() throws RuntimeException {
    return (org.compiere.model.I_AD_User)
        MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
            .getPO(getSupervisor_ID(), get_TrxName());
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
