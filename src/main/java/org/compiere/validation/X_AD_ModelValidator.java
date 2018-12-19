package org.compiere.validation;

import kotliquery.Row;
import org.compiere.model.I_AD_ModelValidator;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_ModelValidator
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_ModelValidator extends BasePOName implements I_AD_ModelValidator, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_ModelValidator(Properties ctx, int AD_ModelValidator_ID, String trxName) {
    super(ctx, AD_ModelValidator_ID, trxName);
  }

  /** Load Constructor */
  public X_AD_ModelValidator(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }
  public X_AD_ModelValidator(Properties ctx, Row row) {
    super(ctx, row);
  }

  /**
   * AccessLevel
   *
   * @return 4 - System
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }


  @Override
  public int getTableId() {
    return Table_ID;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_AD_ModelValidator[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Model Validator.
   *
   * @param AD_ModelValidator_ID Model Validator
   */
  public void setAD_ModelValidator_ID(int AD_ModelValidator_ID) {
    if (AD_ModelValidator_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_ModelValidator_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_ModelValidator_ID, Integer.valueOf(AD_ModelValidator_ID));
  }

  /**
   * Get Model Validator.
   *
   * @return Model Validator
   */
  public int getAD_ModelValidator_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_ModelValidator_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set AD_ModelValidator_UU.
   *
   * @param AD_ModelValidator_UU AD_ModelValidator_UU
   */
  public void setAD_ModelValidator_UU(String AD_ModelValidator_UU) {
    set_Value(COLUMNNAME_AD_ModelValidator_UU, AD_ModelValidator_UU);
  }

  /**
   * Get AD_ModelValidator_UU.
   *
   * @return AD_ModelValidator_UU
   */
  public String getAD_ModelValidator_UU() {
    return (String) get_Value(COLUMNNAME_AD_ModelValidator_UU);
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

    set_ValueNoCheck(COLUMNNAME_EntityType, EntityType);
  }

  /**
   * Get Entity Type.
   *
   * @return Dictionary Entity Type; Determines ownership and synchronization
   */
  public String getEntityType() {
    return (String) get_Value(COLUMNNAME_EntityType);
  }

  /**
   * Set Comment/Help.
   *
   * @param Help Comment or Hint
   */
  public void setHelp(String Help) {
    set_Value(COLUMNNAME_Help, Help);
  }

  /**
   * Get Comment/Help.
   *
   * @return Comment or Hint
   */
  public String getHelp() {
    return (String) get_Value(COLUMNNAME_Help);
  }

  /**
   * Set Model Validation Class.
   *
   * @param ModelValidationClass Model Validation Class
   */
  public void setModelValidationClass(String ModelValidationClass) {
    set_Value(COLUMNNAME_ModelValidationClass, ModelValidationClass);
  }

  /**
   * Get Model Validation Class.
   *
   * @return Model Validation Class
   */
  public String getModelValidationClass() {
    return (String) get_Value(COLUMNNAME_ModelValidationClass);
  }

  /**
   * Set Sequence.
   *
   * @param SeqNo Method of ordering records; lowest number comes first
   */
  public void setSeqNo(int SeqNo) {
    set_Value(COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
  }

  /**
   * Get Sequence.
   *
   * @return Method of ordering records; lowest number comes first
   */
  public int getSeqNo() {
    Integer ii = (Integer) get_Value(COLUMNNAME_SeqNo);
    if (ii == null) return 0;
    return ii;
  }
}
