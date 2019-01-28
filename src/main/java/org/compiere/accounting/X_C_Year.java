package org.compiere.accounting;

import org.compiere.model.I_C_Year;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_Year
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Year extends PO implements I_C_Year, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_Year(Properties ctx, int C_Year_ID, String trxName) {
    super(ctx, C_Year_ID, trxName);
    /** if (C_Year_ID == 0) { setC_Calendar_ID (0); setC_Year_ID (0); setFiscalYear (null); } */
  }

  /** Load Constructor */
  public X_C_Year(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_Year[").append(getId()).append("]");
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
   * Get Year.
   *
   * @return Calendar Year
   */
  public int getC_Year_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Year_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Year.
   *
   * @param FiscalYear The Fiscal Year
   */
  public void setFiscalYear(String FiscalYear) {
    set_Value(COLUMNNAME_FiscalYear, FiscalYear);
  }

  /**
   * Get Year.
   *
   * @return The Fiscal Year
   */
  public String getFiscalYear() {
    return (String) get_Value(COLUMNNAME_FiscalYear);
  }

    /**
   * Set Process Now.
   *
   * @param Processing Process Now
   */
  public void setProcessing(boolean Processing) {
    set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
  }

    @Override
  public int getTableId() {
    return I_C_Year.Table_ID;
  }
}
