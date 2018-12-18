package org.compiere.accounting;

import org.compiere.model.I_C_Period;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_Period
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Period extends BasePOName implements I_C_Period, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_Period(Properties ctx, int C_Period_ID, String trxName) {
    super(ctx, C_Period_ID, trxName);
    /**
     * if (C_Period_ID == 0) { setC_Period_ID (0); setC_Year_ID (0); setName (null); setPeriodNo
     * (0); setPeriodType (null); // S setStartDate (new Timestamp( System.currentTimeMillis() )); }
     */
  }

  /** Load Constructor */
  public X_C_Period(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_Period[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Period.
   *
   * @param C_Period_ID Period of the Calendar
   */
  public void setC_Period_ID(int C_Period_ID) {
    if (C_Period_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Period_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
  }

  /**
   * Get Period.
   *
   * @return Period of the Calendar
   */
  public int getC_Period_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Period_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_Period_UU.
   *
   * @param C_Period_UU C_Period_UU
   */
  public void setC_Period_UU(String C_Period_UU) {
    set_Value(COLUMNNAME_C_Period_UU, C_Period_UU);
  }

  /**
   * Get C_Period_UU.
   *
   * @return C_Period_UU
   */
  public String getC_Period_UU() {
    return (String) get_Value(COLUMNNAME_C_Period_UU);
  }

  public org.compiere.model.I_C_Year getC_Year() throws RuntimeException {
    return (org.compiere.model.I_C_Year)
        MTable.get(getCtx(), org.compiere.model.I_C_Year.Table_Name)
            .getPO(getC_Year_ID(), null);
  }

  /**
   * Set Year.
   *
   * @param C_Year_ID Calendar Year
   */
  public void setC_Year_ID(int C_Year_ID) {
    if (C_Year_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Year_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
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
   * Set End Date.
   *
   * @param EndDate Last effective date (inclusive)
   */
  public void setEndDate(Timestamp EndDate) {
    set_Value(COLUMNNAME_EndDate, EndDate);
  }

  /**
   * Get End Date.
   *
   * @return Last effective date (inclusive)
   */
  public Timestamp getEndDate() {
    return (Timestamp) get_Value(COLUMNNAME_EndDate);
  }

  /**
   * Set Period No.
   *
   * @param PeriodNo Unique Period Number
   */
  public void setPeriodNo(int PeriodNo) {
    set_Value(COLUMNNAME_PeriodNo, Integer.valueOf(PeriodNo));
  }

  /**
   * Get Period No.
   *
   * @return Unique Period Number
   */
  public int getPeriodNo() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PeriodNo);
    if (ii == null) return 0;
    return ii;
  }

  /** PeriodType AD_Reference_ID=115 */
  public static final int PERIODTYPE_AD_Reference_ID = 115;
  /** Standard Calendar Period = S */
  public static final String PERIODTYPE_StandardCalendarPeriod = "S";
  /** Adjustment Period = A */
  public static final String PERIODTYPE_AdjustmentPeriod = "A";
  /**
   * Set Period Type.
   *
   * @param PeriodType Period Type
   */
  public void setPeriodType(String PeriodType) {

    set_ValueNoCheck(COLUMNNAME_PeriodType, PeriodType);
  }

  /**
   * Get Period Type.
   *
   * @return Period Type
   */
  public String getPeriodType() {
    return (String) get_Value(COLUMNNAME_PeriodType);
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

  /**
   * Set Start Date.
   *
   * @param StartDate First effective day (inclusive)
   */
  public void setStartDate(Timestamp StartDate) {
    set_Value(COLUMNNAME_StartDate, StartDate);
  }

  /**
   * Get Start Date.
   *
   * @return First effective day (inclusive)
   */
  public Timestamp getStartDate() {
    return (Timestamp) get_Value(COLUMNNAME_StartDate);
  }

  @Override
  public int getTableId() {
    return I_C_Period.Table_ID;
  }
}
