package org.compiere.production;

import org.compiere.model.I_R_Category;
import org.compiere.orm.BasePOName;
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
   * Get Product.
   *
   * @return Product, Service, Item
   */
  public int getM_Product_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Product_ID);
    if (ii == null) return 0;
    return ii;
  }

    @Override
  public int getTableId() {
    return I_R_Category.Table_ID;
  }
}
