package org.compiere.wf;

import org.compiere.model.I_AD_Note;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_Note
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Note extends PO implements I_AD_Note, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_Note(Properties ctx, int AD_Note_ID, String trxName) {
    super(ctx, AD_Note_ID, trxName);
    /** if (AD_Note_ID == 0) { setAD_Message_ID (0); setAD_Note_ID (0); } */
  }

  /** Load Constructor */
  public X_AD_Note(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_AD_Note[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Broadcast Message.
   *
   * @return Broadcast Message
   */
  public int getAD_BroadcastMessage_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_BroadcastMessage_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Message.
   *
   * @param AD_Message_ID System Message
   */
  public void setAD_Message_ID(int AD_Message_ID) {
    if (AD_Message_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_Message_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_Message_ID, Integer.valueOf(AD_Message_ID));
  }

  /**
   * Get Message.
   *
   * @return System Message
   */
  public int getAD_Message_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Message_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Notice.
   *
   * @return System Notice
   */
  public int getAD_Note_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Note_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Table.
   *
   * @param AD_Table_ID Database Table information
   */
  public void setAD_Table_ID(int AD_Table_ID) {
    if (AD_Table_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_Table_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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
   * Get Workflow Activity.
   *
   * @return Workflow Activity
   */
  public int getAD_WF_Activity_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_Activity_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
  }

  /**
   * Get Processed.
   *
   * @return The document has been processed
   */
  public boolean isProcessed() {
    Object oo = get_Value(COLUMNNAME_Processed);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
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
   * Set Record ID.
   *
   * @param Record_ID Direct internal record ID
   */
  public void setRecord_ID(int Record_ID) {
    if (Record_ID < 0) set_ValueNoCheck(COLUMNNAME_Record_ID, null);
    else set_ValueNoCheck(COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
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
   * Set Text Message.
   *
   * @param TextMsg Text Message
   */
  public void setTextMsg(String TextMsg) {
    set_Value(COLUMNNAME_TextMsg, TextMsg);
  }

    @Override
  public int getTableId() {
    return I_AD_Note.Table_ID;
  }
}
