package org.compiere.production;

import org.compiere.model.I_R_Group;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for R_Group
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_Group extends BasePOName implements I_R_Group, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_Group(Properties ctx, int R_Group_ID, String trxName) {
    super(ctx, R_Group_ID, trxName);
    /** if (R_Group_ID == 0) { setName (null); setR_Group_ID (0); } */
  }

  /** Load Constructor */
  public X_R_Group(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_R_Group[").append(getId()).append("]");
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

  public org.compiere.model.I_M_ChangeNotice getM_ChangeNotice() throws RuntimeException {
    return (org.compiere.model.I_M_ChangeNotice)
        MTable.get(getCtx(), org.compiere.model.I_M_ChangeNotice.Table_Name)
            .getPO(getM_ChangeNotice_ID(), null);
  }

  /**
   * Set Change Notice.
   *
   * @param M_ChangeNotice_ID Bill of Materials (Engineering) Change Notice (Version)
   */
  public void setM_ChangeNotice_ID(int M_ChangeNotice_ID) {
    if (M_ChangeNotice_ID < 1) set_Value(COLUMNNAME_M_ChangeNotice_ID, null);
    else set_Value(COLUMNNAME_M_ChangeNotice_ID, Integer.valueOf(M_ChangeNotice_ID));
  }

  /**
   * Get Change Notice.
   *
   * @return Bill of Materials (Engineering) Change Notice (Version)
   */
  public int getM_ChangeNotice_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_ChangeNotice_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM() throws RuntimeException {
    return (org.eevolution.model.I_PP_Product_BOM)
        MTable.get(getCtx(), org.eevolution.model.I_PP_Product_BOM.Table_Name)
            .getPO(getPP_Product_BOM_ID(), null);
  }

  /**
   * Set BOM & Formula.
   *
   * @param PP_Product_BOM_ID BOM & Formula
   */
  public void setPP_Product_BOM_ID(int PP_Product_BOM_ID) {
    if (PP_Product_BOM_ID < 1) set_Value(COLUMNNAME_PP_Product_BOM_ID, null);
    else set_Value(COLUMNNAME_PP_Product_BOM_ID, Integer.valueOf(PP_Product_BOM_ID));
  }

  /**
   * Get BOM & Formula.
   *
   * @return BOM & Formula
   */
  public int getPP_Product_BOM_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PP_Product_BOM_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Group.
   *
   * @param R_Group_ID Request Group
   */
  public void setR_Group_ID(int R_Group_ID) {
    if (R_Group_ID < 1) set_ValueNoCheck(COLUMNNAME_R_Group_ID, null);
    else set_ValueNoCheck(COLUMNNAME_R_Group_ID, Integer.valueOf(R_Group_ID));
  }

  /**
   * Get Group.
   *
   * @return Request Group
   */
  public int getR_Group_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Group_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set R_Group_UU.
   *
   * @param R_Group_UU R_Group_UU
   */
  public void setR_Group_UU(String R_Group_UU) {
    set_Value(COLUMNNAME_R_Group_UU, R_Group_UU);
  }

  /**
   * Get R_Group_UU.
   *
   * @return R_Group_UU
   */
  public String getR_Group_UU() {
    return (String) get_Value(COLUMNNAME_R_Group_UU);
  }

  @Override
  public int getTableId() {
    return I_R_Group.Table_ID;
  }
}
