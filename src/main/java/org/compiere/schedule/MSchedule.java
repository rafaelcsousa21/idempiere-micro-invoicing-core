package org.compiere.schedule;

import it.sauronsoftware.cron4j.Predictor;
import it.sauronsoftware.cron4j.SchedulingPattern;
import kotliquery.Row;
import org.compiere.model.I_AD_Schedule;
import org.idempiere.common.util.CCache;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class MSchedule extends X_AD_Schedule {
    /**
     *
     */
    private static final long serialVersionUID = -3319184522988847237L;
    private static final String ipv4Pattern =
            "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";
    private static Pattern VALID_IPV4_PATTERN = null;
    private static Pattern VALID_IPV6_PATTERN = null;
    /**
     * Cache
     */
    private static CCache<Integer, MSchedule> s_cache =
            new CCache<Integer, MSchedule>(I_AD_Schedule.Table_Name, 10);

    public MSchedule(Properties ctx, int AD_Schedule_ID) {
        super(ctx, AD_Schedule_ID);
    }

    public MSchedule(Properties ctx, Row row) {
        super(ctx, row);
    }

    public static MSchedule get(Properties ctx, int AD_Schedule_ID) {
        Integer key = AD_Schedule_ID;
        MSchedule retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MSchedule(ctx, AD_Schedule_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    }

    /**
     * Get Next Run
     *
     * @param last in MS
     * @return next run in MS
     */
    public static long getNextRunMS(
            long last, String scheduleType, String frequencyType, int frequency, String cronPattern) {
        long now = System.currentTimeMillis();
        if (X_AD_Schedule.SCHEDULETYPE_Frequency.equals(scheduleType)) {
            // Calculate sleep interval based on frequency defined
            if (frequency < 1) frequency = 1;
            long typeSec = 600; // 	10 minutes
            if (frequencyType == null) typeSec = 300; // 	5 minutes
            else if (X_AD_Schedule.FREQUENCYTYPE_Minute.equals(frequencyType)) typeSec = 60;
            else if (X_AD_Schedule.FREQUENCYTYPE_Hour.equals(frequencyType)) typeSec = 3600;
            else if (X_AD_Schedule.FREQUENCYTYPE_Day.equals(frequencyType)) typeSec = 86400;
            long sleepInterval = typeSec * 1000 * frequency; // 	ms

            long next = last + sleepInterval;
            while (next < now) {
                next = next + sleepInterval;
            }
            return next;
        } else if (X_AD_Schedule.SCHEDULETYPE_CronSchedulingPattern.equals(scheduleType)) {
            if (cronPattern != null
                    && cronPattern.trim().length() > 0
                    && SchedulingPattern.validate(cronPattern)) {
                Predictor predictor = new Predictor(cronPattern, last);
                long next = predictor.nextMatchingTime();
                while (next < now) {
                    predictor = new Predictor(cronPattern, next);
                    next = predictor.nextMatchingTime();
                }
                return next;
            }
        } // not implemented MSchedule.SCHEDULETYPE_MonthDay, MSchedule.SCHEDULETYPE_WeekDay - can be
        // done with cron

        return 0;
    } //	getNextRunMS

    @Override
    protected boolean beforeSave(boolean newRecord) {
        //		Set Schedule Type & Frequencies
        if (X_AD_Schedule.SCHEDULETYPE_Frequency.equals(getScheduleType())) {
            if (getFrequencyType() == null) setFrequencyType(X_AD_Schedule.FREQUENCYTYPE_Day);
            if (getFrequency() < 1) setFrequency(1);
            setCronPattern(null);
        } else if (X_AD_Schedule.SCHEDULETYPE_CronSchedulingPattern.equals(getScheduleType())) {
            String pattern = getCronPattern();
            if (pattern != null && pattern.trim().length() > 0) {
                if (!SchedulingPattern.validate(pattern)) {
                    log.saveError("Error", "InvalidCronPattern");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Brought from Compiere Open Source Community version 3.3.0 Is it OK to Run process On IP of this
     * box
     *
     * @return
     */
    public boolean isOKtoRunOnIP() {
        String ipOnly = getRunOnlyOnIP();
        if ((ipOnly == null) || (ipOnly.length() == 0)) return true;

        StringTokenizer st = new StringTokenizer(ipOnly, ";");
        while (st.hasMoreElements()) {
            String ip = st.nextToken();
            if (checkIP(ip)) return true;
        }
        return false;
    } //	isOKtoRunOnIP

    /**
     * Brought from Compiere Open Source Community version 3.3.0 check whether this IP is allowed to
     * process
     *
     * @param ipOnly
     * @return true if IP is correct
     */
    private boolean checkIP(String ipOnly) {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                     enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && !inetAddress.isLinkLocalAddress()
                            && inetAddress.isSiteLocalAddress()) {
                        String retVal = inetAddress.getHostAddress();
                        if (chekIPFormat(ipOnly)) {
                            retVal = inetAddress.getHostAddress();
                        } else {
                            retVal = inetAddress.getHostName();
                        }
                        if (ipOnly.equals(retVal)) {
                            if (log.isLoggable(Level.INFO)) log.info("Allowed here - IP=" + retVal + " match");
                            return true;
                        } else {
                            if (log.isLoggable(Level.INFO))
                                log.info("Not Allowed here - IP=" + retVal + " does not match " + ipOnly);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "", e);
        }
        return false;
    } // checkIP

    public boolean chekIPFormat(String ipOnly) {
        boolean IsIp = false;
        try {
            VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);
            VALID_IPV6_PATTERN = Pattern.compile(ipv6Pattern, Pattern.CASE_INSENSITIVE);

            Matcher m1 = VALID_IPV4_PATTERN.matcher(ipOnly);
            if (m1.matches()) {
                IsIp = true;
            } else {
                Matcher m2 = VALID_IPV6_PATTERN.matcher(ipOnly);
                IsIp = m2.matches();
            }
        } catch (PatternSyntaxException e) {
            // TODO: handle exception
            if (log.isLoggable(Level.FINE)) log.fine("Error: " + e.getLocalizedMessage());
        }
        return IsIp;
    }
}
