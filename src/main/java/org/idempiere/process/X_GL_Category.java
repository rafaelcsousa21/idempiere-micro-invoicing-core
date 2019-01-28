package org.idempiere.process;

import org.compiere.model.I_GL_Category;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

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

  @Override
  public int getTableId() {
    return Table_ID;
  }


  public String toString() {
    StringBuffer sb = new StringBuffer("X_GL_Category[").append(getId()).append("]");
    return sb.toString();
  }

    /** Manual = M */
  public static final String CATEGORYTYPE_Manual = "M";
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
