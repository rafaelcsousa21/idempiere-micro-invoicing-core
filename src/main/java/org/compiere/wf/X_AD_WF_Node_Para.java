package org.compiere.wf;

import org.compiere.model.I_AD_WF_Node_Para;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_WF_Node_Para
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_Node_Para extends PO implements I_AD_WF_Node_Para, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_WF_Node_Para(Properties ctx, int AD_WF_Node_Para_ID, String trxName) {
    super(ctx, AD_WF_Node_Para_ID, trxName);
    /**
     * if (AD_WF_Node_Para_ID == 0) { setAD_WF_Node_ID (0); // @1|AD_WF_Node_ID@
     * setAD_WF_Node_Para_ID (0); setEntityType (null); // @SQL=select
     * get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual }
     */
  }

  /** Load Constructor */
  public X_AD_WF_Node_Para(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_AD_WF_Node_Para[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_AD_Process_Para getAD_Process_Para() throws RuntimeException {
    return (org.compiere.model.I_AD_Process_Para)
        MTable.get(getCtx(), org.compiere.model.I_AD_Process_Para.Table_Name)
            .getPO(getAD_Process_Para_ID(), null);
  }

  /**
   * Set Process Parameter.
   *
   * @param AD_Process_Para_ID Process Parameter
   */
  public void setAD_Process_Para_ID(int AD_Process_Para_ID) {
    if (AD_Process_Para_ID < 1) set_Value(COLUMNNAME_AD_Process_Para_ID, null);
    else set_Value(COLUMNNAME_AD_Process_Para_ID, Integer.valueOf(AD_Process_Para_ID));
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

  public org.compiere.model.I_AD_WF_Node getAD_WF_Node() throws RuntimeException {
    return (org.compiere.model.I_AD_WF_Node)
        MTable.get(getCtx(), org.compiere.model.I_AD_WF_Node.Table_Name)
            .getPO(getAD_WF_Node_ID(), null);
  }

  /**
   * Set Node.
   *
   * @param AD_WF_Node_ID Workflow Node (activity), step or process
   */
  public void setAD_WF_Node_ID(int AD_WF_Node_ID) {
    if (AD_WF_Node_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_WF_Node_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_WF_Node_ID, Integer.valueOf(AD_WF_Node_ID));
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
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), String.valueOf(getAD_WF_Node_ID()));
  }

  /**
   * Set Workflow Node Parameter.
   *
   * @param AD_WF_Node_Para_ID Workflow Node Execution Parameter
   */
  public void setAD_WF_Node_Para_ID(int AD_WF_Node_Para_ID) {
    if (AD_WF_Node_Para_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_WF_Node_Para_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_WF_Node_Para_ID, Integer.valueOf(AD_WF_Node_Para_ID));
  }

  /**
   * Get Workflow Node Parameter.
   *
   * @return Workflow Node Execution Parameter
   */
  public int getAD_WF_Node_Para_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_Node_Para_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set AD_WF_Node_Para_UU.
   *
   * @param AD_WF_Node_Para_UU AD_WF_Node_Para_UU
   */
  public void setAD_WF_Node_Para_UU(String AD_WF_Node_Para_UU) {
    set_Value(COLUMNNAME_AD_WF_Node_Para_UU, AD_WF_Node_Para_UU);
  }

  /**
   * Get AD_WF_Node_Para_UU.
   *
   * @return AD_WF_Node_Para_UU
   */
  public String getAD_WF_Node_Para_UU() {
    return (String) get_Value(COLUMNNAME_AD_WF_Node_Para_UU);
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
   * Get Attribute Name.
   *
   * @return Name of the Attribute
   */
  public String getAttributeName() {
    return (String) get_Value(COLUMNNAME_AttributeName);
  }

  /**
   * Set Attribute Value.
   *
   * @param AttributeValue Value of the Attribute
   */
  public void setAttributeValue(String AttributeValue) {
    set_Value(COLUMNNAME_AttributeValue, AttributeValue);
  }

  /**
   * Get Attribute Value.
   *
   * @return Value of the Attribute
   */
  public String getAttributeValue() {
    return (String) get_Value(COLUMNNAME_AttributeValue);
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

  /** EntityType AD_Reference_ID=389 */
  public static final int ENTITYTYPE_AD_Reference_ID = 389;
  /**
   * Set Entity Type.
   *
   * @param EntityType Dictionary Entity Type; Determines ownership and synchronization
   */
  public void setEntityType(String EntityType) {

    set_Value(COLUMNNAME_EntityType, EntityType);
  }

  /**
   * Get Entity Type.
   *
   * @return Dictionary Entity Type; Determines ownership and synchronization
   */
  public String getEntityType() {
    return (String) get_Value(COLUMNNAME_EntityType);
  }

  @Override
  public int getTableId() {
    return I_AD_WF_Node_Para.Table_ID;
  }
}
