package org.compiere.wf;

import org.compiere.model.I_AD_Workflow_Access;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_Workflow_Access
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Workflow_Access extends PO implements I_AD_Workflow_Access, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_Workflow_Access(Properties ctx, int AD_Workflow_Access_ID, String trxName) {
    super(ctx, AD_Workflow_Access_ID, trxName);
    /**
     * if (AD_Workflow_Access_ID == 0) { setAD_Role_ID (0); setAD_Workflow_ID (0); setIsReadWrite
     * (false); }
     */
  }

  /** Load Constructor */
  public X_AD_Workflow_Access(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_AD_Workflow_Access[").append(getId()).append("]");
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
    if (AD_Role_ID < 0) set_ValueNoCheck(COLUMNNAME_AD_Role_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
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

  /**
   * Set AD_Workflow_Access_UU.
   *
   * @param AD_Workflow_Access_UU AD_Workflow_Access_UU
   */
  public void setAD_Workflow_Access_UU(String AD_Workflow_Access_UU) {
    set_Value(COLUMNNAME_AD_Workflow_Access_UU, AD_Workflow_Access_UU);
  }

  /**
   * Get AD_Workflow_Access_UU.
   *
   * @return AD_Workflow_Access_UU
   */
  public String getAD_Workflow_Access_UU() {
    return (String) get_Value(COLUMNNAME_AD_Workflow_Access_UU);
  }

  public org.compiere.model.I_AD_Workflow getAD_Workflow() throws RuntimeException {
    return (org.compiere.model.I_AD_Workflow)
        MTable.get(getCtx(), org.compiere.model.I_AD_Workflow.Table_Name)
            .getPO(getAD_Workflow_ID(), get_TrxName());
  }

  /**
   * Set Workflow.
   *
   * @param AD_Workflow_ID Workflow or combination of tasks
   */
  public void setAD_Workflow_ID(int AD_Workflow_ID) {
    if (AD_Workflow_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_Workflow_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
  }

  /**
   * Get Workflow.
   *
   * @return Workflow or combination of tasks
   */
  public int getAD_Workflow_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Workflow_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Read Write.
   *
   * @param IsReadWrite Field is read / write
   */
  public void setIsReadWrite(boolean IsReadWrite) {
    set_Value(COLUMNNAME_IsReadWrite, Boolean.valueOf(IsReadWrite));
  }

  /**
   * Get Read Write.
   *
   * @return Field is read / write
   */
  public boolean isReadWrite() {
    Object oo = get_Value(COLUMNNAME_IsReadWrite);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }
}
