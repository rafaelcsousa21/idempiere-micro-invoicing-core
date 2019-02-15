package org.compiere.production;

import org.compiere.model.I_PA_Goal;
import org.compiere.orm.BasePOName;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for PA_Goal
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_PA_Goal extends BasePOName implements I_PA_Goal, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_PA_Goal(Properties ctx, int PA_Goal_ID) {
    super(ctx, PA_Goal_ID);
  }

  /** Load Constructor */
  public X_PA_Goal(Properties ctx, ResultSet rs) {
    super(ctx, rs);
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
    StringBuffer sb = new StringBuffer("X_PA_Goal[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Set Role.
   *
   * @param AD_Role_ID Responsibility Role
   */
  public void setAD_Role_ID(int AD_Role_ID) {
    if (AD_Role_ID < 0) set_Value(COLUMNNAME_AD_Role_ID, null);
    else set_Value(COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
  }

  /**
   * Get Role.
   *
   * @return Responsibility Role
   */
  public int getAD_Role_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Role_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get User/Contact.
   *
   * @return User within the system - Internal or Business Partner Contact
   */
  public int getAD_User_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_User_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Date last run.
   *
   * @param DateLastRun Date the process was last run.
   */
  public void setDateLastRun(Timestamp DateLastRun) {
    set_ValueNoCheck(COLUMNNAME_DateLastRun, DateLastRun);
  }

  /**
   * Get Date last run.
   *
   * @return Date the process was last run.
   */
  public Timestamp getDateLastRun() {
    return (Timestamp) get_Value(COLUMNNAME_DateLastRun);
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
   * Set Performance Goal.
   *
   * @param GoalPerformance Target achievement from 0..1
   */
  public void setGoalPerformance(BigDecimal GoalPerformance) {
    set_ValueNoCheck(COLUMNNAME_GoalPerformance, GoalPerformance);
  }

  /**
   * Get Performance Goal.
   *
   * @return Target achievement from 0..1
   */
  public BigDecimal getGoalPerformance() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_GoalPerformance);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Summary Level.
   *
   * @param IsSummary This is a summary entity
   */
  public void setIsSummary(boolean IsSummary) {
    set_Value(COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
  }

  /**
   * Get Summary Level.
   *
   * @return This is a summary entity
   */
  public boolean isSummary() {
    Object oo = get_Value(COLUMNNAME_IsSummary);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Measure Actual.
   *
   * @param MeasureActual Actual value that has been measured.
   */
  public void setMeasureActual(BigDecimal MeasureActual) {
    set_ValueNoCheck(COLUMNNAME_MeasureActual, MeasureActual);
  }

  /**
   * Get Measure Actual.
   *
   * @return Actual value that has been measured.
   */
  public BigDecimal getMeasureActual() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_MeasureActual);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /** MeasureDisplay AD_Reference_ID=367 */
  public static final int MEASUREDISPLAY_AD_Reference_ID = 367;
  /** Year = 1 */
  public static final String MEASUREDISPLAY_Year = "1";
  /** Quarter = 3 */
  public static final String MEASUREDISPLAY_Quarter = "3";
  /** Month = 5 */
  public static final String MEASUREDISPLAY_Month = "5";
  /** Total = 0 */
  public static final String MEASUREDISPLAY_Total = "0";
  /** Week = 7 */
  public static final String MEASUREDISPLAY_Week = "7";
  /** Day = 8 */
  public static final String MEASUREDISPLAY_Day = "8";

    /**
   * Get Measure Display.
   *
   * @return Measure Scope initially displayed
   */
  public String getMeasureDisplay() {
    return (String) get_Value(COLUMNNAME_MeasureDisplay);
  }

    /** Year = 1 */
  public static final String MEASURESCOPE_Year = "1";
  /** Quarter = 3 */
  public static final String MEASURESCOPE_Quarter = "3";
  /** Month = 5 */
  public static final String MEASURESCOPE_Month = "5";
  /** Total = 0 */
  public static final String MEASURESCOPE_Total = "0";
  /** Week = 7 */
  public static final String MEASURESCOPE_Week = "7";
  /** Day = 8 */
  public static final String MEASURESCOPE_Day = "8";
  /**
   * Set Measure Scope.
   *
   * @param MeasureScope Performance Measure Scope
   */
  public void setMeasureScope(String MeasureScope) {

    set_Value(COLUMNNAME_MeasureScope, MeasureScope);
  }

  /**
   * Get Measure Scope.
   *
   * @return Performance Measure Scope
   */
  public String getMeasureScope() {
    return (String) get_Value(COLUMNNAME_MeasureScope);
  }

  /**
   * Set Measure Target.
   *
   * @param MeasureTarget Target value for measure
   */
  public void setMeasureTarget(BigDecimal MeasureTarget) {
    set_Value(COLUMNNAME_MeasureTarget, MeasureTarget);
  }

  /**
   * Get Measure Target.
   *
   * @return Target value for measure
   */
  public BigDecimal getMeasureTarget() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_MeasureTarget);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Get Color Schema.
   *
   * @return Performance Color Schema
   */
  public int getPA_ColorSchema_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PA_ColorSchema_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Goal.
   *
   * @return Performance Goal
   */
  public int getPA_Goal_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PA_Goal_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Parent Goal.
   *
   * @return Parent Goal
   */
  public int getPA_GoalParent_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PA_GoalParent_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Measure.
   *
   * @param PA_Measure_ID Concrete Performance Measurement
   */
  public void setPA_Measure_ID(int PA_Measure_ID) {
    if (PA_Measure_ID < 1) set_Value(COLUMNNAME_PA_Measure_ID, null);
    else set_Value(COLUMNNAME_PA_Measure_ID, Integer.valueOf(PA_Measure_ID));
  }

  /**
   * Get Measure.
   *
   * @return Concrete Performance Measurement
   */
  public int getPA_Measure_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PA_Measure_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Relative Weight.
   *
   * @param RelativeWeight Relative weight of this step (0 = ignored)
   */
  public void setRelativeWeight(BigDecimal RelativeWeight) {
    set_Value(COLUMNNAME_RelativeWeight, RelativeWeight);
  }

    /**
   * Set Sequence.
   *
   * @param SeqNo Method of ordering records; lowest number comes first
   */
  public void setSeqNo(int SeqNo) {
    set_Value(COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
  }

    @Override
  public int getTableId() {
    return I_PA_Goal.Table_ID;
  }
}
