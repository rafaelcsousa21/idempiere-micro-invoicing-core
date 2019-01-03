package org.idempiere.process;

import org.compiere.model.I_M_BOM;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
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
   * Set BOM.
   *
   * @param M_BOM_ID Bill of Material
   */
  public void setM_BOM_ID(int M_BOM_ID) {
    if (M_BOM_ID < 1) set_ValueNoCheck(COLUMNNAME_M_BOM_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_BOM_ID, Integer.valueOf(M_BOM_ID));
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
   * Set M_BOM_UU.
   *
   * @param M_BOM_UU M_BOM_UU
   */
  public void setM_BOM_UU(String M_BOM_UU) {
    set_Value(COLUMNNAME_M_BOM_UU, M_BOM_UU);
  }

  /**
   * Get M_BOM_UU.
   *
   * @return M_BOM_UU
   */
  public String getM_BOM_UU() {
    return (String) get_Value(COLUMNNAME_M_BOM_UU);
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
    if (M_Product_ID < 1) set_ValueNoCheck(COLUMNNAME_M_Product_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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
   * Set Process Now.
   *
   * @param Processing Process Now
   */
  public void setProcessing(boolean Processing) {
    set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
  }

  /**
   * Get Process Now.
   *
   * @return Process Now
   */
  public boolean isProcessing() {
    Object oo = get_Value(COLUMNNAME_Processing);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }
}
