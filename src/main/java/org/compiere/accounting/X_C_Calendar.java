package org.compiere.accounting;

import org.compiere.model.I_C_Calendar;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_Calendar
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Calendar extends BasePOName implements I_C_Calendar, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_Calendar(Properties ctx, int C_Calendar_ID, String trxName) {
    super(ctx, C_Calendar_ID, trxName);
  }

  /** Load Constructor */
  public X_C_Calendar(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 2 - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_Calendar[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Calendar.
   *
   * @param C_Calendar_ID Accounting Calendar Name
   */
  public void setC_Calendar_ID(int C_Calendar_ID) {
    if (C_Calendar_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Calendar_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Calendar_ID, Integer.valueOf(C_Calendar_ID));
  }

  /**
   * Get Calendar.
   *
   * @return Accounting Calendar Name
   */
  public int getC_Calendar_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Calendar_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_Calendar_UU.
   *
   * @param C_Calendar_UU C_Calendar_UU
   */
  public void setC_Calendar_UU(String C_Calendar_UU) {
    set_Value(COLUMNNAME_C_Calendar_UU, C_Calendar_UU);
  }

  /**
   * Get C_Calendar_UU.
   *
   * @return C_Calendar_UU
   */
  public String getC_Calendar_UU() {
    return (String) get_Value(COLUMNNAME_C_Calendar_UU);
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

  @Override
  public int getTableId() {
    return I_C_Calendar.Table_ID;
  }
}
