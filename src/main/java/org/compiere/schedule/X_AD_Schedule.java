package org.compiere.schedule;

import org.compiere.model.I_AD_Schedule;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_Schedule
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Schedule extends BasePOName implements I_AD_Schedule, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_Schedule(Properties ctx, int AD_Schedule_ID) {
    super(ctx, AD_Schedule_ID);
  }

  /** Load Constructor */
  public X_AD_Schedule(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  }

  /**
   * AccessLevel
   *
   * @return 4 - System
   */
  protected int getAccessLevel() {
    return I_AD_Schedule.accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_AD_Schedule[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Set Cron Scheduling Pattern.
   *
   * @param CronPattern Cron pattern to define when the process should be invoked.
   */
  public void setCronPattern(String CronPattern) {
    set_Value(I_AD_Schedule.COLUMNNAME_CronPattern, CronPattern);
  }

  /**
   * Get Cron Scheduling Pattern.
   *
   * @return Cron pattern to define when the process should be invoked.
   */
  public String getCronPattern() {
    return (String) get_Value(I_AD_Schedule.COLUMNNAME_CronPattern);
  }

    /**
   * Set Frequency.
   *
   * @param Frequency Frequency of events
   */
  public void setFrequency(int Frequency) {
    set_Value(I_AD_Schedule.COLUMNNAME_Frequency, Integer.valueOf(Frequency));
  }

  /**
   * Get Frequency.
   *
   * @return Frequency of events
   */
  public int getFrequency() {
    Integer ii = (Integer) get_Value(I_AD_Schedule.COLUMNNAME_Frequency);
    if (ii == null) return 0;
    return ii;
  }

    /** Minute = M */
  public static final String FREQUENCYTYPE_Minute = "M";
  /** Hour = H */
  public static final String FREQUENCYTYPE_Hour = "H";
  /** Day = D */
  public static final String FREQUENCYTYPE_Day = "D";
  /**
   * Set Frequency Type.
   *
   * @param FrequencyType Frequency of event
   */
  public void setFrequencyType(String FrequencyType) {

    set_Value(I_AD_Schedule.COLUMNNAME_FrequencyType, FrequencyType);
  }

  /**
   * Get Frequency Type.
   *
   * @return Frequency of event
   */
  public String getFrequencyType() {
    return (String) get_Value(I_AD_Schedule.COLUMNNAME_FrequencyType);
  }

    /**
   * Get Ignore Processing Time.
   *
   * @return Do not include processing time for the DateNextRun calculation
   */
  public boolean isIgnoreProcessingTime() {
    Object oo = get_Value(I_AD_Schedule.COLUMNNAME_IsIgnoreProcessingTime);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Run only on IP.
   *
   * @return Run only on IP
   */
  public String getRunOnlyOnIP() {
    return (String) get_Value(I_AD_Schedule.COLUMNNAME_RunOnlyOnIP);
  }

    /** Frequency = F */
  public static final String SCHEDULETYPE_Frequency = "F";
    /** Cron Scheduling Pattern = C */
  public static final String SCHEDULETYPE_CronSchedulingPattern = "C";

    /**
   * Get Schedule Type.
   *
   * @return Type of schedule
   */
  public String getScheduleType() {
    return (String) get_Value(I_AD_Schedule.COLUMNNAME_ScheduleType);
  }

    @Override
  public int getTableId() {
    return I_AD_Schedule.Table_ID;
  }
}
