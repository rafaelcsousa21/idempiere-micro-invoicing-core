package org.idempiere.process;

import org.compiere.model.I_C_City;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

public class X_C_City extends BasePOName implements I_C_City, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_City(Properties ctx, int C_City_ID, String trxName) {
    super(ctx, C_City_ID, trxName);
    /** if (C_City_ID == 0) { setC_City_ID (0); setName (null); } */
  }

  /** Load Constructor */
  public X_C_City(Properties ctx, ResultSet rs, String trxName) {
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

  @Override
  public int getTableId() {
    return Table_ID;
  }


  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_City[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Area Code.
   *
   * @param AreaCode Phone Area Code
   */
  public void setAreaCode(String AreaCode) {
    set_Value(COLUMNNAME_AreaCode, AreaCode);
  }

  /**
   * Get Area Code.
   *
   * @return Phone Area Code
   */
  public String getAreaCode() {
    return (String) get_Value(COLUMNNAME_AreaCode);
  }

  /**
   * Set City.
   *
   * @param C_City_ID City
   */
  public void setC_City_ID(int C_City_ID) {
    if (C_City_ID < 1) set_ValueNoCheck(COLUMNNAME_C_City_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_City_ID, Integer.valueOf(C_City_ID));
  }

  /**
   * Get City.
   *
   * @return City
   */
  public int getC_City_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_City_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_City_UU.
   *
   * @param C_City_UU C_City_UU
   */
  public void setC_City_UU(String C_City_UU) {
    set_Value(COLUMNNAME_C_City_UU, C_City_UU);
  }

  /**
   * Get C_City_UU.
   *
   * @return C_City_UU
   */
  public String getC_City_UU() {
    return (String) get_Value(COLUMNNAME_C_City_UU);
  }

  public org.compiere.model.I_C_Country getC_Country() throws RuntimeException {
    return (org.compiere.model.I_C_Country)
        MTable.get(getCtx(), org.compiere.model.I_C_Country.Table_Name)
            .getPO(getC_Country_ID(), get_TrxName());
  }

  /**
   * Set Country.
   *
   * @param C_Country_ID Country
   */
  public void setC_Country_ID(int C_Country_ID) {
    if (C_Country_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Country_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
  }

  /**
   * Get Country.
   *
   * @return Country
   */
  public int getC_Country_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Country_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Coordinates.
   *
   * @param Coordinates Location coordinate
   */
  public void setCoordinates(String Coordinates) {
    set_Value(COLUMNNAME_Coordinates, Coordinates);
  }

  /**
   * Get Coordinates.
   *
   * @return Location coordinate
   */
  public String getCoordinates() {
    return (String) get_Value(COLUMNNAME_Coordinates);
  }

  public org.compiere.model.I_C_Region getC_Region() throws RuntimeException {
    return (org.compiere.model.I_C_Region)
        MTable.get(getCtx(), org.compiere.model.I_C_Region.Table_Name)
            .getPO(getC_Region_ID(), get_TrxName());
  }

  /**
   * Set Region.
   *
   * @param C_Region_ID Identifies a geographical Region
   */
  public void setC_Region_ID(int C_Region_ID) {
    if (C_Region_ID < 1) set_Value(COLUMNNAME_C_Region_ID, null);
    else set_Value(COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
  }

  /**
   * Get Region.
   *
   * @return Identifies a geographical Region
   */
  public int getC_Region_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Region_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Locode.
   *
   * @param Locode Location code - UN/LOCODE
   */
  public void setLocode(String Locode) {
    set_Value(COLUMNNAME_Locode, Locode);
  }

  /**
   * Get Locode.
   *
   * @return Location code - UN/LOCODE
   */
  public String getLocode() {
    return (String) get_Value(COLUMNNAME_Locode);
  }

  /**
   * Set ZIP.
   *
   * @param Postal Postal code
   */
  public void setPostal(String Postal) {
    set_Value(COLUMNNAME_Postal, Postal);
  }

  /**
   * Get ZIP.
   *
   * @return Postal code
   */
  public String getPostal() {
    return (String) get_Value(COLUMNNAME_Postal);
  }
}
