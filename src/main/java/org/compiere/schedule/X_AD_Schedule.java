package org.compiere.schedule;

import kotliquery.Row;
import org.compiere.model.I_AD_Schedule;
import org.compiere.orm.BasePOName;

import java.util.Properties;

/**
 * Generated Model for AD_Schedule
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Schedule extends BasePOName implements I_AD_Schedule {

    /**
     * Minute = M
     */
    public static final String FREQUENCYTYPE_Minute = "M";
    /**
     * Hour = H
     */
    public static final String FREQUENCYTYPE_Hour = "H";
    /**
     * Day = D
     */
    public static final String FREQUENCYTYPE_Day = "D";
    /**
     * Frequency = F
     */
    public static final String SCHEDULETYPE_Frequency = "F";
    /**
     * Cron Scheduling Pattern = C
     */
    public static final String SCHEDULETYPE_CronSchedulingPattern = "C";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_Schedule(Properties ctx, int AD_Schedule_ID) {
        super(ctx, AD_Schedule_ID);
    }

    /**
     * Load Constructor
     */
    public X_AD_Schedule(Properties ctx, Row row) {
        super(ctx, row);
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
     * Get Cron Scheduling Pattern.
     *
     * @return Cron pattern to define when the process should be invoked.
     */
    public String getCronPattern() {
        return (String) getValue(I_AD_Schedule.COLUMNNAME_CronPattern);
    }

    /**
     * Set Cron Scheduling Pattern.
     *
     * @param CronPattern Cron pattern to define when the process should be invoked.
     */
    public void setCronPattern(String CronPattern) {
        setValue(I_AD_Schedule.COLUMNNAME_CronPattern, CronPattern);
    }

    /**
     * Get Frequency.
     *
     * @return Frequency of events
     */
    public int getFrequency() {
        Integer ii = (Integer) getValue(I_AD_Schedule.COLUMNNAME_Frequency);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Frequency.
     *
     * @param Frequency Frequency of events
     */
    public void setFrequency(int Frequency) {
        setValue(I_AD_Schedule.COLUMNNAME_Frequency, Integer.valueOf(Frequency));
    }

    /**
     * Get Frequency Type.
     *
     * @return Frequency of event
     */
    public String getFrequencyType() {
        return (String) getValue(I_AD_Schedule.COLUMNNAME_FrequencyType);
    }

    /**
     * Set Frequency Type.
     *
     * @param FrequencyType Frequency of event
     */
    public void setFrequencyType(String FrequencyType) {

        setValue(I_AD_Schedule.COLUMNNAME_FrequencyType, FrequencyType);
    }

    /**
     * Get Ignore Processing Time.
     *
     * @return Do not include processing time for the DateNextRun calculation
     */
    public boolean isIgnoreProcessingTime() {
        Object oo = getValue(I_AD_Schedule.COLUMNNAME_IsIgnoreProcessingTime);
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
        return (String) getValue(I_AD_Schedule.COLUMNNAME_RunOnlyOnIP);
    }

    /**
     * Get Schedule Type.
     *
     * @return Type of schedule
     */
    public String getScheduleType() {
        return (String) getValue(I_AD_Schedule.COLUMNNAME_ScheduleType);
    }

    @Override
    public int getTableId() {
        return I_AD_Schedule.Table_ID;
    }
}
