package org.compiere.wf;

import org.compiere.model.I_AD_WF_ActivityApprover;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_WF_ActivityApprover
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_ActivityApprover extends PO implements I_AD_WF_ActivityApprover, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_WF_ActivityApprover(Properties ctx, int AD_WF_ActivityApprover_ID, String trxName) {
    super(ctx, AD_WF_ActivityApprover_ID, trxName);
    /**
     * if (AD_WF_ActivityApprover_ID == 0) { setAD_User_ID (0); setAD_WF_ActivityApprover_ID (0);
     * setAD_WF_Activity_ID (0); }
     */
  }

  /** Load Constructor */
  public X_AD_WF_ActivityApprover(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_AD_WF_ActivityApprover[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_AD_User getAD_User() throws RuntimeException {
    return (org.compiere.model.I_AD_User)
        MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
            .getPO(getAD_User_ID(), null);
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
   * Set Workflow Activity Approver.
   *
   * @param AD_WF_ActivityApprover_ID Workflow Activity Approver
   */
  public void setAD_WF_ActivityApprover_ID(int AD_WF_ActivityApprover_ID) {
    if (AD_WF_ActivityApprover_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_WF_ActivityApprover_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_AD_WF_ActivityApprover_ID, Integer.valueOf(AD_WF_ActivityApprover_ID));
  }

  /**
   * Get Workflow Activity Approver.
   *
   * @return Workflow Activity Approver
   */
  public int getAD_WF_ActivityApprover_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_ActivityApprover_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set AD_WF_ActivityApprover_UU.
   *
   * @param AD_WF_ActivityApprover_UU AD_WF_ActivityApprover_UU
   */
  public void setAD_WF_ActivityApprover_UU(String AD_WF_ActivityApprover_UU) {
    set_Value(COLUMNNAME_AD_WF_ActivityApprover_UU, AD_WF_ActivityApprover_UU);
  }

  /**
   * Get AD_WF_ActivityApprover_UU.
   *
   * @return AD_WF_ActivityApprover_UU
   */
  public String getAD_WF_ActivityApprover_UU() {
    return (String) get_Value(COLUMNNAME_AD_WF_ActivityApprover_UU);
  }

  public org.compiere.model.I_AD_WF_Activity getAD_WF_Activity() throws RuntimeException {
    return (org.compiere.model.I_AD_WF_Activity)
        MTable.get(getCtx(), org.compiere.model.I_AD_WF_Activity.Table_Name)
            .getPO(getAD_WF_Activity_ID(), null);
  }

  /**
   * Set Workflow Activity.
   *
   * @param AD_WF_Activity_ID Workflow Activity
   */
  public void setAD_WF_Activity_ID(int AD_WF_Activity_ID) {
    if (AD_WF_Activity_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_WF_Activity_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_WF_Activity_ID, Integer.valueOf(AD_WF_Activity_ID));
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

  @Override
  public int getTableId() {
    return I_AD_WF_ActivityApprover.Table_ID;
  }
}
