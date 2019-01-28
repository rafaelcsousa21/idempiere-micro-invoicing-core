package org.compiere.wf;

import org.compiere.model.I_AD_WF_Node_Para;
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
   * Get Attribute Value.
   *
   * @return Value of the Attribute
   */
  public String getAttributeValue() {
    return (String) get_Value(COLUMNNAME_AttributeValue);
  }

    /** EntityType AD_Reference_ID=389 */
  public static final int ENTITYTYPE_AD_Reference_ID = 389;

    @Override
  public int getTableId() {
    return I_AD_WF_Node_Para.Table_ID;
  }
}
