package org.compiere.production;

import org.compiere.model.I_PA_Measure;
import org.compiere.orm.BasePOName;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for PA_Measure
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_PA_Measure extends BasePOName implements I_PA_Measure, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_PA_Measure(Properties ctx, int PA_Measure_ID, String trxName) {
    super(ctx, PA_Measure_ID, trxName);
    /**
     * if (PA_Measure_ID == 0) { setMeasureDataType (null); // T setMeasureType (null); // M setName
     * (null); setPA_Measure_ID (0); }
     */
  }

  /** Load Constructor */
  public X_PA_Measure(Properties ctx, ResultSet rs, String trxName) {
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

  public String toString() {
    StringBuffer sb = new StringBuffer("X_PA_Measure[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Calculation Class.
   *
   * @return Java Class for calculation, implementing Interface Measure
   */
  public String getCalculationClass() {
    return (String) get_Value(COLUMNNAME_CalculationClass);
  }

    /**
   * Get Project Type.
   *
   * @return Type of the project
   */
  public int getC_ProjectType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ProjectType_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Manual Actual.
   *
   * @return Manually entered actual value
   */
  public BigDecimal getManualActual() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_ManualActual);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /** Qty/Amount in Time = T */
  public static final String MEASUREDATATYPE_QtyAmountInTime = "T";
  /** Status Qty/Amount = S */
  public static final String MEASUREDATATYPE_StatusQtyAmount = "S";

    /**
   * Get Measure Data Type.
   *
   * @return Type of data - Status or in Time
   */
  public String getMeasureDataType() {
    return (String) get_Value(COLUMNNAME_MeasureDataType);
  }

    /** Manual = M */
  public static final String MEASURETYPE_Manual = "M";
  /** Calculated = C */
  public static final String MEASURETYPE_Calculated = "C";
  /** Achievements = A */
  public static final String MEASURETYPE_Achievements = "A";
  /** User defined = U */
  public static final String MEASURETYPE_UserDefined = "U";
  /** Ratio = R */
  public static final String MEASURETYPE_Ratio = "R";
  /** Request = Q */
  public static final String MEASURETYPE_Request = "Q";
  /** Project = P */
  public static final String MEASURETYPE_Project = "P";

    /**
   * Get Measure Type.
   *
   * @return Determines how the actual performance is derived
   */
  public String getMeasureType() {
    return (String) get_Value(COLUMNNAME_MeasureType);
  }

    /**
   * Get Benchmark.
   *
   * @return Performance Benchmark
   */
  public int getPA_Benchmark_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PA_Benchmark_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Reporting Hierarchy.
   *
   * @return Optional Reporting Hierarchy - If not selected the default hierarchy trees are used.
   */
  public int getPA_Hierarchy_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PA_Hierarchy_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Measure Calculation.
   *
   * @return Calculation method for measuring performance
   */
  public int getPA_MeasureCalc_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PA_MeasureCalc_ID);
    if (ii == null) return 0;
    return ii;
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
   * Get Ratio.
   *
   * @return Performance Ratio
   */
  public int getPA_Ratio_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PA_Ratio_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Request Type.
   *
   * @return Type of request (e.g. Inquiry, Complaint, ..)
   */
  public int getR_RequestType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_RequestType_ID);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_PA_Measure.Table_ID;
  }
}
