package org.compiere.wf;

import org.compiere.model.I_AD_WF_EventAudit;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_WF_EventAudit
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_EventAudit extends PO implements I_AD_WF_EventAudit, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_WF_EventAudit(Properties ctx, int AD_WF_EventAudit_ID, String trxName) {
    super(ctx, AD_WF_EventAudit_ID, trxName);
    /**
     * if (AD_WF_EventAudit_ID == 0) { setAD_Table_ID (0); setAD_WF_EventAudit_ID (0);
     * setAD_WF_Node_ID (0); setAD_WF_Process_ID (0); setAD_WF_Responsible_ID (0); setElapsedTimeMS
     * (Env.ZERO); setEventType (null); setRecord_ID (0); setWFState (null); }
     */
  }

  /** Load Constructor */
  public X_AD_WF_EventAudit(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 7 - System - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_AD_WF_EventAudit[").append(getId()).append("]");
    return sb.toString();
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
   * Get Workflow Event Audit.
   *
   * @return Workflow Process Activity Event Audit Information
   */
  public int getAD_WF_EventAudit_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_EventAudit_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Node.
   *
   * @param AD_WF_Node_ID Workflow Node (activity), step or process
   */
  public void setAD_WF_Node_ID(int AD_WF_Node_ID) {
    if (AD_WF_Node_ID < 1) set_Value(COLUMNNAME_AD_WF_Node_ID, null);
    else set_Value(COLUMNNAME_AD_WF_Node_ID, Integer.valueOf(AD_WF_Node_ID));
  }

  /**
   * Get Node.
   *
   * @return Workflow Node (activity), step or process
   */
  public int getAD_WF_Node_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_Node_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Workflow Process.
   *
   * @param AD_WF_Process_ID Actual Workflow Process Instance
   */
  public void setAD_WF_Process_ID(int AD_WF_Process_ID) {
    if (AD_WF_Process_ID < 1) set_Value(COLUMNNAME_AD_WF_Process_ID, null);
    else set_Value(COLUMNNAME_AD_WF_Process_ID, Integer.valueOf(AD_WF_Process_ID));
  }

  /**
   * Get Workflow Process.
   *
   * @return Actual Workflow Process Instance
   */
  public int getAD_WF_Process_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_Process_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Workflow Responsible.
   *
   * @param AD_WF_Responsible_ID Responsible for Workflow Execution
   */
  public void setAD_WF_Responsible_ID(int AD_WF_Responsible_ID) {
    if (AD_WF_Responsible_ID < 1) set_Value(COLUMNNAME_AD_WF_Responsible_ID, null);
    else set_Value(COLUMNNAME_AD_WF_Responsible_ID, Integer.valueOf(AD_WF_Responsible_ID));
  }

  /**
   * Get Workflow Responsible.
   *
   * @return Responsible for Workflow Execution
   */
  public int getAD_WF_Responsible_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_Responsible_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Attribute Name.
   *
   * @param AttributeName Name of the Attribute
   */
  public void setAttributeName(String AttributeName) {
    set_Value(COLUMNNAME_AttributeName, AttributeName);
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
   * Set Elapsed Time ms.
   *
   * @param ElapsedTimeMS Elapsed Time in milli seconds
   */
  public void setElapsedTimeMS(BigDecimal ElapsedTimeMS) {
    set_Value(COLUMNNAME_ElapsedTimeMS, ElapsedTimeMS);
  }

    /** Process Created = PC */
  public static final String EVENTTYPE_ProcessCreated = "PC";
  /** State Changed = SC */
  public static final String EVENTTYPE_StateChanged = "SC";
  /** Process Completed = PX */
  public static final String EVENTTYPE_ProcessCompleted = "PX";
  /**
   * Set Event Type.
   *
   * @param EventType Type of Event
   */
  public void setEventType(String EventType) {

    set_Value(COLUMNNAME_EventType, EventType);
  }

    /**
   * Set New Value.
   *
   * @param NewValue New field value
   */
  public void setNewValue(String NewValue) {
    set_Value(COLUMNNAME_NewValue, NewValue);
  }

    /**
   * Set Old Value.
   *
   * @param OldValue The old file data
   */
  public void setOldValue(String OldValue) {
    set_Value(COLUMNNAME_OldValue, OldValue);
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

    /**
   * Set Workflow State.
   *
   * @param WFState State of the execution of the workflow
   */
  public void setWFState(String WFState) {

    set_Value(COLUMNNAME_WFState, WFState);
  }

    @Override
  public int getTableId() {
    return I_AD_WF_EventAudit.Table_ID;
  }
}
