package org.compiere.schedule;

import org.compiere.model.I_AD_SchedulerRecipient;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_SchedulerRecipient
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_SchedulerRecipient extends PO implements I_AD_SchedulerRecipient, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_SchedulerRecipient(Properties ctx, int AD_SchedulerRecipient_ID, String trxName) {
    super(ctx, AD_SchedulerRecipient_ID, trxName);
    /**
     * if (AD_SchedulerRecipient_ID == 0) { setAD_Scheduler_ID (0); setAD_SchedulerRecipient_ID (0);
     * }
     */
  }

  /** Load Constructor */
  public X_AD_SchedulerRecipient(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_AD_SchedulerRecipient[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException {
    return (org.compiere.model.I_AD_Role)
        MTable.get(getCtx(), org.compiere.model.I_AD_Role.Table_Name)
            .getPO(getAD_Role_ID(), get_TrxName());
  }

  /**
   * Set Role.
   *
   * @param AD_Role_ID Responsibility Role
   */
  public void setAD_Role_ID(int AD_Role_ID) {
    if (AD_Role_ID < 0) set_Value(COLUMNNAME_AD_Role_ID, null);
    else set_Value(COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
  }

  /**
   * Get Role.
   *
   * @return Responsibility Role
   */
  public int getAD_Role_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Role_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_AD_Scheduler getAD_Scheduler() throws RuntimeException {
    return (org.compiere.model.I_AD_Scheduler)
        MTable.get(getCtx(), org.compiere.model.I_AD_Scheduler.Table_Name)
            .getPO(getAD_Scheduler_ID(), get_TrxName());
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
   * Set Scheduler Recipient.
   *
   * @param AD_SchedulerRecipient_ID Recipient of the Scheduler Notification
   */
  public void setAD_SchedulerRecipient_ID(int AD_SchedulerRecipient_ID) {
    if (AD_SchedulerRecipient_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_SchedulerRecipient_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_AD_SchedulerRecipient_ID, Integer.valueOf(AD_SchedulerRecipient_ID));
  }

  /**
   * Get Scheduler Recipient.
   *
   * @return Recipient of the Scheduler Notification
   */
  public int getAD_SchedulerRecipient_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_SchedulerRecipient_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set AD_SchedulerRecipient_UU.
   *
   * @param AD_SchedulerRecipient_UU AD_SchedulerRecipient_UU
   */
  public void setAD_SchedulerRecipient_UU(String AD_SchedulerRecipient_UU) {
    set_Value(COLUMNNAME_AD_SchedulerRecipient_UU, AD_SchedulerRecipient_UU);
  }

  /**
   * Get AD_SchedulerRecipient_UU.
   *
   * @return AD_SchedulerRecipient_UU
   */
  public String getAD_SchedulerRecipient_UU() {
    return (String) get_Value(COLUMNNAME_AD_SchedulerRecipient_UU);
  }

  public org.compiere.model.I_AD_User getAD_User() throws RuntimeException {
    return (org.compiere.model.I_AD_User)
        MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
            .getPO(getAD_User_ID(), get_TrxName());
  }

  /**
   * Set User/Contact.
   *
   * @param AD_User_ID User within the system - Internal or Business Partner Contact
   */
  public void setAD_User_ID(int AD_User_ID) {
    if (AD_User_ID < 1) set_Value(COLUMNNAME_AD_User_ID, null);
    else set_Value(COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
  }

  /**
   * Get User/Contact.
   *
   * @return User within the system - Internal or Business Partner Contact
   */
  public int getAD_User_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_User_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), String.valueOf(getAD_User_ID()));
  }

  @Override
  public int getTableId() {
    return I_AD_SchedulerRecipient.Table_ID;
  }
}
