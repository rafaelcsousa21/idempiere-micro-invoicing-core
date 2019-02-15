package org.idempiere.process;

import org.compiere.model.I_M_BOM;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

public class X_M_BOM extends BasePOName implements I_M_BOM, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_BOM(Properties ctx, int M_BOM_ID) {
    super(ctx, M_BOM_ID);
    /**
     * if (M_BOM_ID == 0) { setBOMType (null); // A setBOMUse (null); // A setM_BOM_ID (0);
     * setM_Product_ID (0); setName (null); }
     */
  }

  /** Load Constructor */
  public X_M_BOM(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  }

  /**
   * AccessLevel
   *
   * @return 3 - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  @Override
  public int getTableId() {
    return Table_ID;
  }


  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_BOM[").append(getId()).append("]");
    return sb.toString();
  }

    /** Current Active = A */
  public static final String BOMTYPE_CurrentActive = "A";
  /** Make-To-Order = O */
  public static final String BOMTYPE_Make_To_Order = "O";

    /**
   * Set BOM Type.
   *
   * @param BOMType Type of BOM
   */
  public void setBOMType(String BOMType) {

    set_Value(COLUMNNAME_BOMType, BOMType);
  }

  /**
   * Get BOM Type.
   *
   * @return Type of BOM
   */
  public String getBOMType() {
    return (String) get_Value(COLUMNNAME_BOMType);
  }

    /** Master = A */
  public static final String BOMUSE_Master = "A";

    /**
   * Set BOM Use.
   *
   * @param BOMUse The use of the Bill of Material
   */
  public void setBOMUse(String BOMUse) {

    set_Value(COLUMNNAME_BOMUse, BOMUse);
  }

  /**
   * Get BOM Use.
   *
   * @return The use of the Bill of Material
   */
  public String getBOMUse() {
    return (String) get_Value(COLUMNNAME_BOMUse);
  }

    /**
   * Get BOM.
   *
   * @return Bill of Material
   */
  public int getM_BOM_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_BOM_ID);
    if (ii == null) return 0;
    return ii;
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

}
