package org.compiere.wf;

import org.compiere.model.I_AD_WorkflowProcessorLog;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_WorkflowProcessorLog
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WorkflowProcessorLog extends PO
    implements I_AD_WorkflowProcessorLog, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_WorkflowProcessorLog(Properties ctx, int AD_WorkflowProcessorLog_ID, String trxName) {
    super(ctx, AD_WorkflowProcessorLog_ID, trxName);
    /**
     * if (AD_WorkflowProcessorLog_ID == 0) { setAD_WorkflowProcessor_ID (0);
     * setAD_WorkflowProcessorLog_ID (0); setIsError (false); }
     */
  }

  /** Load Constructor */
  public X_AD_WorkflowProcessorLog(Properties ctx, ResultSet rs, String trxName) {
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

  @Override
  public int getTableId() {
    return Table_ID;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_AD_WorkflowProcessorLog[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_AD_WorkflowProcessor getAD_WorkflowProcessor()
      throws RuntimeException {
    return (org.compiere.model.I_AD_WorkflowProcessor)
        MTable.get(getCtx(), org.compiere.model.I_AD_WorkflowProcessor.Table_Name)
            .getPO(getAD_WorkflowProcessor_ID(), get_TrxName());
  }

  /**
   * Set Workflow Processor.
   *
   * @param AD_WorkflowProcessor_ID Workflow Processor Server
   */
  public void setAD_WorkflowProcessor_ID(int AD_WorkflowProcessor_ID) {
    if (AD_WorkflowProcessor_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_WorkflowProcessor_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_AD_WorkflowProcessor_ID, Integer.valueOf(AD_WorkflowProcessor_ID));
  }

  /**
   * Get Workflow Processor.
   *
   * @return Workflow Processor Server
   */
  public int getAD_WorkflowProcessor_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WorkflowProcessor_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Workflow Processorl Log.
   *
   * @param AD_WorkflowProcessorLog_ID Result of the execution of the Workflow Processor
   */
  public void setAD_WorkflowProcessorLog_ID(int AD_WorkflowProcessorLog_ID) {
    if (AD_WorkflowProcessorLog_ID < 1)
      set_ValueNoCheck(COLUMNNAME_AD_WorkflowProcessorLog_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_AD_WorkflowProcessorLog_ID, Integer.valueOf(AD_WorkflowProcessorLog_ID));
  }

  /**
   * Get Workflow Processorl Log.
   *
   * @return Result of the execution of the Workflow Processor
   */
  public int getAD_WorkflowProcessorLog_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WorkflowProcessorLog_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set AD_WorkflowProcessorLog_UU.
   *
   * @param AD_WorkflowProcessorLog_UU AD_WorkflowProcessorLog_UU
   */
  public void setAD_WorkflowProcessorLog_UU(String AD_WorkflowProcessorLog_UU) {
    set_Value(COLUMNNAME_AD_WorkflowProcessorLog_UU, AD_WorkflowProcessorLog_UU);
  }

  /**
   * Get AD_WorkflowProcessorLog_UU.
   *
   * @return AD_WorkflowProcessorLog_UU
   */
  public String getAD_WorkflowProcessorLog_UU() {
    return (String) get_Value(COLUMNNAME_AD_WorkflowProcessorLog_UU);
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
}
