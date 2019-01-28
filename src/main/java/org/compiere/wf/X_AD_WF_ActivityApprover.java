package org.compiere.wf;

import org.compiere.model.I_AD_WF_ActivityApprover;
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

  @Override
  public int getTableId() {
    return I_AD_WF_ActivityApprover.Table_ID;
  }
}
