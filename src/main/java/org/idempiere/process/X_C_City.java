package org.idempiere.process;

import org.compiere.model.I_C_City;
import org.compiere.orm.BasePOName;
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

}
