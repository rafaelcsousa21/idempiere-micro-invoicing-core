package org.compiere.wf;

import org.compiere.model.I_AD_WF_Block;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_WF_Block
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_Block extends BasePOName implements I_AD_WF_Block, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_WF_Block(Properties ctx, int AD_WF_Block_ID, String trxName) {
    super(ctx, AD_WF_Block_ID, trxName);
  }

  /** Load Constructor */
  public X_AD_WF_Block(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_AD_WF_Block[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Workflow Block.
   *
   * @param AD_WF_Block_ID Workflow Transaction Execution Block
   */
  public void setAD_WF_Block_ID(int AD_WF_Block_ID) {
    if (AD_WF_Block_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_WF_Block_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_WF_Block_ID, Integer.valueOf(AD_WF_Block_ID));
  }

  /**
   * Get Workflow Block.
   *
   * @return Workflow Transaction Execution Block
   */
  public int getAD_WF_Block_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_Block_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set AD_WF_Block_UU.
   *
   * @param AD_WF_Block_UU AD_WF_Block_UU
   */
  public void setAD_WF_Block_UU(String AD_WF_Block_UU) {
    set_Value(COLUMNNAME_AD_WF_Block_UU, AD_WF_Block_UU);
  }

  /**
   * Get AD_WF_Block_UU.
   *
   * @return AD_WF_Block_UU
   */
  public String getAD_WF_Block_UU() {
    return (String) get_Value(COLUMNNAME_AD_WF_Block_UU);
  }

  public org.compiere.model.I_AD_Workflow getAD_Workflow() throws RuntimeException {
    return (org.compiere.model.I_AD_Workflow)
        MTable.get(getCtx(), org.compiere.model.I_AD_Workflow.Table_Name)
            .getPO(getAD_Workflow_ID(), null);
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

  @Override
  public int getTableId() {
    return I_AD_WF_Block.Table_ID;
  }
}
