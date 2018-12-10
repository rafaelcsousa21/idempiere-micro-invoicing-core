package org.compiere.conversionrate;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_C_ConversionType;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

/**
 * Generated Model for C_ConversionType
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ConversionType extends BasePONameValue
    implements I_C_ConversionType, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_ConversionType(Properties ctx, int C_ConversionType_ID, String trxName) {
    super(ctx, C_ConversionType_ID, trxName);
  }

  /** Load Constructor */
  public X_C_ConversionType(Properties ctx, ResultSet rs, String trxName) {
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

  /** Load Meta Data */
  protected POInfo initPO(Properties ctx) {
    POInfo poi = POInfo.getPOInfo(ctx, Table_ID, get_TrxName());
    return poi;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_ConversionType[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Currency Type.
   *
   * @param C_ConversionType_ID Currency Conversion Rate Type
   */
  public void setC_ConversionType_ID(int C_ConversionType_ID) {
    if (C_ConversionType_ID < 1) set_ValueNoCheck(COLUMNNAME_C_ConversionType_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
  }

  /**
   * Get Currency Type.
   *
   * @return Currency Conversion Rate Type
   */
  public int getC_ConversionType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ConversionType_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_ConversionType_UU.
   *
   * @param C_ConversionType_UU C_ConversionType_UU
   */
  public void setC_ConversionType_UU(String C_ConversionType_UU) {
    set_Value(COLUMNNAME_C_ConversionType_UU, C_ConversionType_UU);
  }

  /**
   * Get C_ConversionType_UU.
   *
   * @return C_ConversionType_UU
   */
  public String getC_ConversionType_UU() {
    return (String) get_Value(COLUMNNAME_C_ConversionType_UU);
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
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }
}
