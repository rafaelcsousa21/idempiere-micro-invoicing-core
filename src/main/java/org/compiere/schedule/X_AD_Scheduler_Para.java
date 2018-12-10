package org.compiere.schedule;

import org.compiere.model.I_AD_Scheduler_Para;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_Scheduler_Para
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Scheduler_Para extends PO implements I_AD_Scheduler_Para, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_Scheduler_Para(Properties ctx, int AD_Scheduler_Para_ID, String trxName) {
    super(ctx, AD_Scheduler_Para_ID, trxName);
    /** if (AD_Scheduler_Para_ID == 0) { setAD_Process_Para_ID (0); setAD_Scheduler_ID (0); } */
  }

  /** Load Constructor */
  public X_AD_Scheduler_Para(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_AD_Scheduler_Para[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_AD_Process_Para getAD_Process_Para() throws RuntimeException {
    return (org.compiere.model.I_AD_Process_Para)
        MTable.get(getCtx(), org.compiere.model.I_AD_Process_Para.Table_Name)
            .getPO(getAD_Process_Para_ID(), get_TrxName());
  }

  /**
   * Set Process Parameter.
   *
   * @param AD_Process_Para_ID Process Parameter
   */
  public void setAD_Process_Para_ID(int AD_Process_Para_ID) {
    if (AD_Process_Para_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_Process_Para_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_Process_Para_ID, Integer.valueOf(AD_Process_Para_ID));
  }

  /**
   * Get Process Parameter.
   *
   * @return Process Parameter
   */
  public int getAD_Process_Para_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Process_Para_ID);
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
   * Set AD_Scheduler_Para_UU.
   *
   * @param AD_Scheduler_Para_UU AD_Scheduler_Para_UU
   */
  public void setAD_Scheduler_Para_UU(String AD_Scheduler_Para_UU) {
    set_Value(COLUMNNAME_AD_Scheduler_Para_UU, AD_Scheduler_Para_UU);
  }

  /**
   * Get AD_Scheduler_Para_UU.
   *
   * @return AD_Scheduler_Para_UU
   */
  public String getAD_Scheduler_Para_UU() {
    return (String) get_Value(COLUMNNAME_AD_Scheduler_Para_UU);
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
   * Set Default Parameter.
   *
   * @param ParameterDefault Default value of the parameter
   */
  public void setParameterDefault(String ParameterDefault) {
    set_Value(COLUMNNAME_ParameterDefault, ParameterDefault);
  }

  /**
   * Get Default Parameter.
   *
   * @return Default value of the parameter
   */
  public String getParameterDefault() {
    return (String) get_Value(COLUMNNAME_ParameterDefault);
  }

  /**
   * Set Default To Parameter.
   *
   * @param ParameterToDefault Default value of the to parameter
   */
  public void setParameterToDefault(String ParameterToDefault) {
    set_Value(COLUMNNAME_ParameterToDefault, ParameterToDefault);
  }

  /**
   * Get Default To Parameter.
   *
   * @return Default value of the to parameter
   */
  public String getParameterToDefault() {
    return (String) get_Value(COLUMNNAME_ParameterToDefault);
  }

  @Override
  public int getTableId() {
    return I_AD_Scheduler_Para.Table_ID;
  }
}
