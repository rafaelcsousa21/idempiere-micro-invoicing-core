package org.compiere.production;

import org.compiere.model.I_R_Category;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for R_Category
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_Category extends BasePOName implements I_R_Category, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_Category(Properties ctx, int R_Category_ID, String trxName) {
    super(ctx, R_Category_ID, trxName);
    /** if (R_Category_ID == 0) { setName (null); setR_Category_ID (0); } */
  }

  /** Load Constructor */
  public X_R_Category(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_R_Category[").append(getId()).append("]");
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

  public org.compiere.model.I_M_Product getM_Product() throws RuntimeException {
    return (org.compiere.model.I_M_Product)
        MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
            .getPO(getM_Product_ID(), null);
  }

  /**
   * Set Product.
   *
   * @param M_Product_ID Product, Service, Item
   */
  public void setM_Product_ID(int M_Product_ID) {
    if (M_Product_ID < 1) set_Value(COLUMNNAME_M_Product_ID, null);
    else set_Value(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
  }

  /**
   * Get Product.
   *
   * @return Product, Service, Item
   */
  public int getM_Product_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Product_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Category.
   *
   * @param R_Category_ID Request Category
   */
  public void setR_Category_ID(int R_Category_ID) {
    if (R_Category_ID < 1) set_ValueNoCheck(COLUMNNAME_R_Category_ID, null);
    else set_ValueNoCheck(COLUMNNAME_R_Category_ID, Integer.valueOf(R_Category_ID));
  }

  /**
   * Get Category.
   *
   * @return Request Category
   */
  public int getR_Category_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Category_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set R_Category_UU.
   *
   * @param R_Category_UU R_Category_UU
   */
  public void setR_Category_UU(String R_Category_UU) {
    set_Value(COLUMNNAME_R_Category_UU, R_Category_UU);
  }

  /**
   * Get R_Category_UU.
   *
   * @return R_Category_UU
   */
  public String getR_Category_UU() {
    return (String) get_Value(COLUMNNAME_R_Category_UU);
  }

  @Override
  public int getTableId() {
    return I_R_Category.Table_ID;
  }
}
