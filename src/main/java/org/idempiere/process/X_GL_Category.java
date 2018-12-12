package org.idempiere.process;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_GL_Category;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

public class X_GL_Category extends BasePOName implements I_GL_Category, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_GL_Category(Properties ctx, int GL_Category_ID, String trxName) {
    super(ctx, GL_Category_ID, trxName);
  }

  /** Load Constructor */
  public X_GL_Category(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 2 - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  /** Load Meta Data */
  protected POInfo initPO(Properties ctx) {
    POInfo poi = POInfo.getPOInfo(ctx, Table_ID, get_TrxName());
    return poi;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_GL_Category[").append(getId()).append("]");
    return sb.toString();
  }

  /** CategoryType AD_Reference_ID=207 */
  public static final int CATEGORYTYPE_AD_Reference_ID = 207;
  /** Manual = M */
  public static final String CATEGORYTYPE_Manual = "M";
  /** Import = I */
  public static final String CATEGORYTYPE_Import = "I";
  /** Document = D */
  public static final String CATEGORYTYPE_Document = "D";
  /** System generated = S */
  public static final String CATEGORYTYPE_SystemGenerated = "S";
  /**
   * Set Category Type.
   *
   * @param CategoryType Source of the Journal with this category
   */
  public void setCategoryType(String CategoryType) {

    set_Value(COLUMNNAME_CategoryType, CategoryType);
  }

  /**
   * Get Category Type.
   *
   * @return Source of the Journal with this category
   */
  public String getCategoryType() {
    return (String) get_Value(COLUMNNAME_CategoryType);
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
   * Set GL Category.
   *
   * @param GL_Category_ID General Ledger Category
   */
  public void setGL_Category_ID(int GL_Category_ID) {
    if (GL_Category_ID < 1) set_ValueNoCheck(COLUMNNAME_GL_Category_ID, null);
    else set_ValueNoCheck(COLUMNNAME_GL_Category_ID, Integer.valueOf(GL_Category_ID));
  }

  /**
   * Get GL Category.
   *
   * @return General Ledger Category
   */
  public int getGL_Category_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_GL_Category_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set GL_Category_UU.
   *
   * @param GL_Category_UU GL_Category_UU
   */
  public void setGL_Category_UU(String GL_Category_UU) {
    set_Value(COLUMNNAME_GL_Category_UU, GL_Category_UU);
  }

  /**
   * Get GL_Category_UU.
   *
   * @return GL_Category_UU
   */
  public String getGL_Category_UU() {
    return (String) get_Value(COLUMNNAME_GL_Category_UU);
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
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }
}
