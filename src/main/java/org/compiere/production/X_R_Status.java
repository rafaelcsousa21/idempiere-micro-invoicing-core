package org.compiere.production;

import org.compiere.model.I_R_Status;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for R_Status
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_Status extends BasePONameValue implements I_R_Status, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_Status(Properties ctx, int R_Status_ID) {
    super(ctx, R_Status_ID);
  }

  /** Load Constructor */
  public X_R_Status(Properties ctx, ResultSet rs) {
    super(ctx, rs);
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
    StringBuffer sb = new StringBuffer("X_R_Status[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Set Closed Status.
   *
   * @param IsClosed The status is closed
   */
  public void setIsClosed(boolean IsClosed) {
    set_Value(COLUMNNAME_IsClosed, Boolean.valueOf(IsClosed));
  }

  /**
   * Get Closed Status.
   *
   * @return The status is closed
   */
  public boolean isClosed() {
    Object oo = get_Value(COLUMNNAME_IsClosed);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Default.
   *
   * @param IsDefault Default value
   */
  public void setIsDefault(boolean IsDefault) {
    set_Value(COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
  }

  /**
   * Get Default.
   *
   * @return Default value
   */
  public boolean isDefault() {
    Object oo = get_Value(COLUMNNAME_IsDefault);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Final Close.
   *
   * @param IsFinalClose Entries with Final Close cannot be re-opened
   */
  public void setIsFinalClose(boolean IsFinalClose) {
    set_Value(COLUMNNAME_IsFinalClose, Boolean.valueOf(IsFinalClose));
  }

  /**
   * Get Final Close.
   *
   * @return Entries with Final Close cannot be re-opened
   */
  public boolean isFinalClose() {
    Object oo = get_Value(COLUMNNAME_IsFinalClose);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Open Status.
   *
   * @param IsOpen The status is closed
   */
  public void setIsOpen(boolean IsOpen) {
    set_Value(COLUMNNAME_IsOpen, Boolean.valueOf(IsOpen));
  }

  /**
   * Get Open Status.
   *
   * @return The status is closed
   */
  public boolean isOpen() {
    Object oo = get_Value(COLUMNNAME_IsOpen);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Web Can Update.
   *
   * @param IsWebCanUpdate Entry can be updated from the Web
   */
  public void setIsWebCanUpdate(boolean IsWebCanUpdate) {
    set_Value(COLUMNNAME_IsWebCanUpdate, Boolean.valueOf(IsWebCanUpdate));
  }

  /**
   * Get Web Can Update.
   *
   * @return Entry can be updated from the Web
   */
  public boolean isWebCanUpdate() {
    Object oo = get_Value(COLUMNNAME_IsWebCanUpdate);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Set Next Status.
   *
   * @param Next_Status_ID Move to next status automatically after timeout
   */
  public void setNext_Status_ID(int Next_Status_ID) {
    if (Next_Status_ID < 1) set_Value(COLUMNNAME_Next_Status_ID, null);
    else set_Value(COLUMNNAME_Next_Status_ID, Integer.valueOf(Next_Status_ID));
  }

  /**
   * Get Next Status.
   *
   * @return Move to next status automatically after timeout
   */
  public int getNext_Status_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Next_Status_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Status Category.
   *
   * @return Request Status Category
   */
  public int getR_StatusCategory_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_StatusCategory_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Status.
   *
   * @return Request Status
   */
  public int getR_Status_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Status_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Timeout in Days.
   *
   * @return Timeout in Days to change Status automatically
   */
  public int getTimeoutDays() {
    Integer ii = (Integer) get_Value(COLUMNNAME_TimeoutDays);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Update Status.
   *
   * @param Update_Status_ID Automatically change the status after entry from web
   */
  public void setUpdate_Status_ID(int Update_Status_ID) {
    if (Update_Status_ID < 1) set_Value(COLUMNNAME_Update_Status_ID, null);
    else set_Value(COLUMNNAME_Update_Status_ID, Integer.valueOf(Update_Status_ID));
  }

  /**
   * Get Update Status.
   *
   * @return Automatically change the status after entry from web
   */
  public int getUpdate_Status_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Update_Status_ID);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_R_Status.Table_ID;
  }
}
