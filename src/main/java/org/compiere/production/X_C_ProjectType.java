package org.compiere.production;

import org.compiere.model.I_C_ProjectType;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_ProjectType
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ProjectType extends BasePOName implements I_C_ProjectType, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_ProjectType(Properties ctx, int C_ProjectType_ID, String trxName) {
    super(ctx, C_ProjectType_ID, trxName);
    /**
     * if (C_ProjectType_ID == 0) { setC_ProjectType_ID (0); setName (null); setProjectCategory
     * (null); // N }
     */
  }

  /** Load Constructor */
  public X_C_ProjectType(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 3 - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_ProjectType[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Project Type.
   *
   * @param C_ProjectType_ID Type of the project
   */
  public void setC_ProjectType_ID(int C_ProjectType_ID) {
    if (C_ProjectType_ID < 1) set_ValueNoCheck(COLUMNNAME_C_ProjectType_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_ProjectType_ID, Integer.valueOf(C_ProjectType_ID));
  }

  /**
   * Get Project Type.
   *
   * @return Type of the project
   */
  public int getC_ProjectType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ProjectType_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_ProjectType_UU.
   *
   * @param C_ProjectType_UU C_ProjectType_UU
   */
  public void setC_ProjectType_UU(String C_ProjectType_UU) {
    set_Value(COLUMNNAME_C_ProjectType_UU, C_ProjectType_UU);
  }

  /**
   * Get C_ProjectType_UU.
   *
   * @return C_ProjectType_UU
   */
  public String getC_ProjectType_UU() {
    return (String) get_Value(COLUMNNAME_C_ProjectType_UU);
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

  /** ProjectCategory AD_Reference_ID=288 */
  public static final int PROJECTCATEGORY_AD_Reference_ID = 288;
  /** General = N */
  public static final String PROJECTCATEGORY_General = "N";
  /** Asset Project = A */
  public static final String PROJECTCATEGORY_AssetProject = "A";
  /** Work Order (Job) = W */
  public static final String PROJECTCATEGORY_WorkOrderJob = "W";
  /** Service (Charge) Project = S */
  public static final String PROJECTCATEGORY_ServiceChargeProject = "S";
  /**
   * Set Project Category.
   *
   * @param ProjectCategory Project Category
   */
  public void setProjectCategory(String ProjectCategory) {

    set_ValueNoCheck(COLUMNNAME_ProjectCategory, ProjectCategory);
  }

  /**
   * Get Project Category.
   *
   * @return Project Category
   */
  public String getProjectCategory() {
    return (String) get_Value(COLUMNNAME_ProjectCategory);
  }

  @Override
  public int getTableId() {
    return I_C_ProjectType.Table_ID;
  }
}
