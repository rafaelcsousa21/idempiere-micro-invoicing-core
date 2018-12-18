package org.compiere.schedule;

import org.compiere.model.I_AD_SchedulerLog;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_SchedulerLog
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_SchedulerLog extends PO implements I_AD_SchedulerLog, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_SchedulerLog(Properties ctx, int AD_SchedulerLog_ID, String trxName) {
    super(ctx, AD_SchedulerLog_ID, trxName);
    /**
     * if (AD_SchedulerLog_ID == 0) { setAD_Scheduler_ID (0); setAD_SchedulerLog_ID (0); setIsError
     * (false); }
     */
  }

  /** Load Constructor */
  public X_AD_SchedulerLog(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_AD_SchedulerLog[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_AD_Scheduler getAD_Scheduler() throws RuntimeException {
    return (org.compiere.model.I_AD_Scheduler)
        MTable.get(getCtx(), org.compiere.model.I_AD_Scheduler.Table_Name)
            .getPO(getAD_Scheduler_ID(), null);
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
   * Set Scheduler Log.
   *
   * @param AD_SchedulerLog_ID Result of the execution of the Scheduler
   */
  public void setAD_SchedulerLog_ID(int AD_SchedulerLog_ID) {
    if (AD_SchedulerLog_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_SchedulerLog_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_SchedulerLog_ID, Integer.valueOf(AD_SchedulerLog_ID));
  }

  /**
   * Get Scheduler Log.
   *
   * @return Result of the execution of the Scheduler
   */
  public int getAD_SchedulerLog_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_SchedulerLog_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set AD_SchedulerLog_UU.
   *
   * @param AD_SchedulerLog_UU AD_SchedulerLog_UU
   */
  public void setAD_SchedulerLog_UU(String AD_SchedulerLog_UU) {
    set_Value(COLUMNNAME_AD_SchedulerLog_UU, AD_SchedulerLog_UU);
  }

  /**
   * Get AD_SchedulerLog_UU.
   *
   * @return AD_SchedulerLog_UU
   */
  public String getAD_SchedulerLog_UU() {
    return (String) get_Value(COLUMNNAME_AD_SchedulerLog_UU);
  }

  /**
   * Set Binary Data.
   *
   * @param BinaryData Binary Data
   */
  public void setBinaryData(byte[] BinaryData) {
    set_Value(COLUMNNAME_BinaryData, BinaryData);
  }

  /**
   * Get Binary Data.
   *
   * @return Binary Data
   */
  public byte[] getBinaryData() {
    return (byte[]) get_Value(COLUMNNAME_BinaryData);
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
   * Set Error.
   *
   * @param IsError An Error occurred in the execution
   */
  public void setIsError(boolean IsError) {
    set_Value(COLUMNNAME_IsError, Boolean.valueOf(IsError));
  }

  /**
   * Get Error.
   *
   * @return An Error occurred in the execution
   */
  public boolean isError() {
    Object oo = get_Value(COLUMNNAME_IsError);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Reference.
   *
   * @param Reference Reference for this record
   */
  public void setReference(String Reference) {
    set_Value(COLUMNNAME_Reference, Reference);
  }

  /**
   * Get Reference.
   *
   * @return Reference for this record
   */
  public String getReference() {
    return (String) get_Value(COLUMNNAME_Reference);
  }

  /**
   * Set Summary.
   *
   * @param Summary Textual summary of this request
   */
  public void setSummary(String Summary) {
    set_Value(COLUMNNAME_Summary, Summary);
  }

  /**
   * Get Summary.
   *
   * @return Textual summary of this request
   */
  public String getSummary() {
    return (String) get_Value(COLUMNNAME_Summary);
  }

  /**
   * Set Text Message.
   *
   * @param TextMsg Text Message
   */
  public void setTextMsg(String TextMsg) {
    set_Value(COLUMNNAME_TextMsg, TextMsg);
  }

  /**
   * Get Text Message.
   *
   * @return Text Message
   */
  public String getTextMsg() {
    return (String) get_Value(COLUMNNAME_TextMsg);
  }

  @Override
  public int getTableId() {
    return I_AD_SchedulerLog.Table_ID;
  }
}
