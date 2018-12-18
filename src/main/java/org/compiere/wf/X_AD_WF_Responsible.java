package org.compiere.wf;

import org.compiere.model.HasName;
import org.compiere.model.I_AD_WF_Responsible;
import org.compiere.orm.BasePOUser;
import org.compiere.orm.MTable;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_WF_Responsible
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_Responsible extends BasePOUser implements I_AD_WF_Responsible, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_WF_Responsible(Properties ctx, int AD_WF_Responsible_ID, String trxName) {
    super(ctx, AD_WF_Responsible_ID, trxName);
  }

  /** Load Constructor */
  public X_AD_WF_Responsible(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 6 - System - Client
   */
  protected int getAccessLevel() {
    return I_AD_WF_Responsible.accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_AD_WF_Responsible[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException {
    return (org.compiere.model.I_AD_Role)
        MTable.get(getCtx(), org.compiere.model.I_AD_Role.Table_Name)
            .getPO(getAD_Role_ID(), null);
  }

  /**
   * Set Role.
   *
   * @param AD_Role_ID Responsibility Role
   */
  public void setAD_Role_ID(int AD_Role_ID) {
    if (AD_Role_ID < 0) set_Value(I_AD_WF_Responsible.COLUMNNAME_AD_Role_ID, null);
    else set_Value(I_AD_WF_Responsible.COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
  }

  /**
   * Get Role.
   *
   * @return Responsibility Role
   */
  public int getAD_Role_ID() {
    Integer ii = (Integer) get_Value(I_AD_WF_Responsible.COLUMNNAME_AD_Role_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Workflow Responsible.
   *
   * @param AD_WF_Responsible_ID Responsible for Workflow Execution
   */
  public void setAD_WF_Responsible_ID(int AD_WF_Responsible_ID) {
    if (AD_WF_Responsible_ID < 1)
      set_ValueNoCheck(I_AD_WF_Responsible.COLUMNNAME_AD_WF_Responsible_ID, null);
    else
      set_ValueNoCheck(
          I_AD_WF_Responsible.COLUMNNAME_AD_WF_Responsible_ID,
          Integer.valueOf(AD_WF_Responsible_ID));
  }

  /**
   * Get Workflow Responsible.
   *
   * @return Responsible for Workflow Execution
   */
  public int getAD_WF_Responsible_ID() {
    Integer ii = (Integer) get_Value(I_AD_WF_Responsible.COLUMNNAME_AD_WF_Responsible_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set AD_WF_Responsible_UU.
   *
   * @param AD_WF_Responsible_UU AD_WF_Responsible_UU
   */
  public void setAD_WF_Responsible_UU(String AD_WF_Responsible_UU) {
    set_Value(I_AD_WF_Responsible.COLUMNNAME_AD_WF_Responsible_UU, AD_WF_Responsible_UU);
  }

  /**
   * Get AD_WF_Responsible_UU.
   *
   * @return AD_WF_Responsible_UU
   */
  public String getAD_WF_Responsible_UU() {
    return (String) get_Value(I_AD_WF_Responsible.COLUMNNAME_AD_WF_Responsible_UU);
  }

  /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(I_AD_WF_Responsible.COLUMNNAME_Description, Description);
  }

  /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(I_AD_WF_Responsible.COLUMNNAME_Description);
  }

  /** EntityType AD_Reference_ID=389 */
  public static final int ENTITYTYPE_AD_Reference_ID = 389;
  /**
   * Set Entity Type.
   *
   * @param EntityType Dictionary Entity Type; Determines ownership and synchronization
   */
  public void setEntityType(String EntityType) {

    set_Value(I_AD_WF_Responsible.COLUMNNAME_EntityType, EntityType);
  }

  /**
   * Get Entity Type.
   *
   * @return Dictionary Entity Type; Determines ownership and synchronization
   */
  public String getEntityType() {
    return (String) get_Value(I_AD_WF_Responsible.COLUMNNAME_EntityType);
  }

  /**
   * Set Name.
   *
   * @param Name Alphanumeric identifier of the entity
   */
  public void setName(String Name) {
    set_Value(HasName.Companion.getCOLUMNNAME_Name(), Name);
  }

  /**
   * Get Name.
   *
   * @return Alphanumeric identifier of the entity
   */
  public String getName() {
    return (String) get_Value(HasName.Companion.getCOLUMNNAME_Name());
  }

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), getName());
  }

  /** ResponsibleType AD_Reference_ID=304 */
  public static final int RESPONSIBLETYPE_AD_Reference_ID = 304;
  /** Organization = O */
  public static final String RESPONSIBLETYPE_Organization = "O";
  /** Human = H */
  public static final String RESPONSIBLETYPE_Human = "H";
  /** Role = R */
  public static final String RESPONSIBLETYPE_Role = "R";
  /** System Resource = S */
  public static final String RESPONSIBLETYPE_SystemResource = "S";
  /** Manual = M */
  public static final String RESPONSIBLETYPE_Manual = "M";
  /**
   * Set Responsible Type.
   *
   * @param ResponsibleType Type of the Responsibility for a workflow
   */
  public void setResponsibleType(String ResponsibleType) {

    set_Value(I_AD_WF_Responsible.COLUMNNAME_ResponsibleType, ResponsibleType);
  }

  /**
   * Get Responsible Type.
   *
   * @return Type of the Responsibility for a workflow
   */
  public String getResponsibleType() {
    return (String) get_Value(I_AD_WF_Responsible.COLUMNNAME_ResponsibleType);
  }

  @Override
  public int getTableId() {
    return I_AD_WF_Responsible.Table_ID;
  }
}
