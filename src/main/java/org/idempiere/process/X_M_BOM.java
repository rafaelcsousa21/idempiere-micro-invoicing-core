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
  public X_M_BOM(Properties ctx, int M_BOM_ID, String trxName) {
    super(ctx, M_BOM_ID, trxName);
    /**
     * if (M_BOM_ID == 0) { setBOMType (null); // A setBOMUse (null); // A setM_BOM_ID (0);
     * setM_Product_ID (0); setName (null); }
     */
  }

  /** Load Constructor */
  public X_M_BOM(Properties ctx, ResultSet rs, String trxName) {
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

  @Override
  public int getTableId() {
    return Table_ID;
  }


  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_BOM[").append(getId()).append("]");
    return sb.toString();
  }

  /** BOMType AD_Reference_ID=347 */
  public static final int BOMTYPE_AD_Reference_ID = 347;
  /** Current Active = A */
  public static final String BOMTYPE_CurrentActive = "A";
  /** Make-To-Order = O */
  public static final String BOMTYPE_Make_To_Order = "O";
  /** Previous = P */
  public static final String BOMTYPE_Previous = "P";
  /** Previous, Spare = S */
  public static final String BOMTYPE_PreviousSpare = "S";
  /** Future = F */
  public static final String BOMTYPE_Future = "F";
  /** Maintenance = M */
  public static final String BOMTYPE_Maintenance = "M";
  /** Repair = R */
  public static final String BOMTYPE_Repair = "R";
  /** Product Configure = C */
  public static final String BOMTYPE_ProductConfigure = "C";
  /** Make-To-Kit = K */
  public static final String BOMTYPE_Make_To_Kit = "K";
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

  /** BOMUse AD_Reference_ID=348 */
  public static final int BOMUSE_AD_Reference_ID = 348;
  /** Master = A */
  public static final String BOMUSE_Master = "A";
  /** Engineering = E */
  public static final String BOMUSE_Engineering = "E";
  /** Manufacturing = M */
  public static final String BOMUSE_Manufacturing = "M";
  /** Planning = P */
  public static final String BOMUSE_Planning = "P";
  /** Quality = Q */
  public static final String BOMUSE_Quality = "Q";
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
