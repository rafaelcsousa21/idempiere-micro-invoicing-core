package org.compiere.production;

import org.compiere.model.I_R_StatusCategory;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for R_StatusCategory
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_StatusCategory extends BasePOName implements I_R_StatusCategory, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_StatusCategory(Properties ctx, int R_StatusCategory_ID, String trxName) {
    super(ctx, R_StatusCategory_ID, trxName);
  }

  /** Load Constructor */
  public X_R_StatusCategory(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_R_StatusCategory[").append(getId()).append("]");
    return sb.toString();
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
   * Set Status Category.
   *
   * @param R_StatusCategory_ID Request Status Category
   */
  public void setR_StatusCategory_ID(int R_StatusCategory_ID) {
    if (R_StatusCategory_ID < 1) set_ValueNoCheck(COLUMNNAME_R_StatusCategory_ID, null);
    else set_ValueNoCheck(COLUMNNAME_R_StatusCategory_ID, Integer.valueOf(R_StatusCategory_ID));
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
   * Set R_StatusCategory_UU.
   *
   * @param R_StatusCategory_UU R_StatusCategory_UU
   */
  public void setR_StatusCategory_UU(String R_StatusCategory_UU) {
    set_Value(COLUMNNAME_R_StatusCategory_UU, R_StatusCategory_UU);
  }

  /**
   * Get R_StatusCategory_UU.
   *
   * @return R_StatusCategory_UU
   */
  public String getR_StatusCategory_UU() {
    return (String) get_Value(COLUMNNAME_R_StatusCategory_UU);
  }

  @Override
  public int getTableId() {
    return I_R_StatusCategory.Table_ID;
  }
}
