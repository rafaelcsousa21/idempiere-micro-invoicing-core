package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.orm.TimeUtil;
import org.idempiere.common.util.CCache;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;

public class MInvoiceSchedule extends X_C_InvoiceSchedule {
    /**
     *
     */
    private static final long serialVersionUID = -1750020695983938895L;
    /**
     * Cache
     */
    private static CCache<Integer, MInvoiceSchedule> s_cache =
            new CCache<Integer, MInvoiceSchedule>(Table_Name, 5);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx                  context
     * @param C_InvoiceSchedule_ID id
     */
    public MInvoiceSchedule(Properties ctx, int C_InvoiceSchedule_ID) {
        super(ctx, C_InvoiceSchedule_ID);
    } //	MInvoiceSchedule

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MInvoiceSchedule(Properties ctx, Row row) {
        super(ctx, row);
    } //	MInvoiceSchedule

    /**
     * Get MInvoiceSchedule from Cache
     *
     * @param ctx                  context
     * @param C_InvoiceSchedule_ID id
     * @return MInvoiceSchedule
     */
    public static MInvoiceSchedule get(Properties ctx, int C_InvoiceSchedule_ID) {
        Integer key = C_InvoiceSchedule_ID;
        MInvoiceSchedule retValue = (MInvoiceSchedule) s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MInvoiceSchedule(ctx, C_InvoiceSchedule_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Can I send Invoice
     *
     * @param xDate    date
     * @param orderAmt order amount
     * @return true if I can send Invoice
     */
    @Deprecated
    // Deprecation note: consider using just canInvoice(Timestamp)
    // validating the order amount doesn't make sense as the total must be calculated based on
    // shipments
    public boolean canInvoice(Timestamp xDate, BigDecimal orderAmt) {
        //	Amount
        if (isAmount() && getAmt() != null && orderAmt != null && orderAmt.compareTo(getAmt()) >= 0)
            return true;

        return canInvoice(xDate);
    } //	canInvoice

    /**
     * Can I send Invoice
     *
     * @param xDate date
     * @return true if I can send Invoice
     */
    public boolean canInvoice(Timestamp xDate) {
        //	Daily
        if (INVOICEFREQUENCY_Daily.equals(getInvoiceFrequency())) return true;

        //	Remove time
        xDate = TimeUtil.getDay(xDate);
        Calendar today = TimeUtil.getToday();

        //	Weekly
        if (INVOICEFREQUENCY_Weekly.equals(getInvoiceFrequency())) {
            Calendar cutoff = TimeUtil.getToday();
            cutoff.set(Calendar.DAY_OF_WEEK, getCalendarDay(getInvoiceWeekDayCutoff()));
            if (cutoff.after(today)) cutoff.add(Calendar.DAY_OF_YEAR, -7);
            Timestamp cutoffDate = new Timestamp(cutoff.getTimeInMillis());
            if (log.isLoggable(Level.FINE))
                log.fine(
                        "canInvoice - Date="
                                + xDate
                                + " > Cutoff="
                                + cutoffDate
                                + " - "
                                + xDate.after(cutoffDate));
            if (xDate.after(cutoffDate)) return false;
            //
            Calendar invoice = TimeUtil.getToday();
            invoice.set(Calendar.DAY_OF_WEEK, getCalendarDay(getInvoiceWeekDay()));
            if (invoice.after(today)) invoice.add(Calendar.DAY_OF_YEAR, -7);
            Timestamp invoiceDate = new Timestamp(invoice.getTimeInMillis());
            if (log.isLoggable(Level.FINE))
                log.fine(
                        "canInvoice - Date="
                                + xDate
                                + " > Invoice="
                                + invoiceDate
                                + " - "
                                + xDate.after(invoiceDate));
            if (xDate.after(invoiceDate)) return false;
            return true;
        }

        //	Monthly
        if (INVOICEFREQUENCY_Monthly.equals(getInvoiceFrequency())
                || INVOICEFREQUENCY_TwiceMonthly.equals(getInvoiceFrequency())) {
            if (getInvoiceDayCutoff() > 0) {
                Calendar cutoff = TimeUtil.getToday();
                cutoff.set(Calendar.DAY_OF_MONTH, getInvoiceDayCutoff());
                if (cutoff.after(today)) cutoff.add(Calendar.MONTH, -1);
                Timestamp cutoffDate = new Timestamp(cutoff.getTimeInMillis());
                if (log.isLoggable(Level.FINE))
                    log.fine(
                            "canInvoice - Date="
                                    + xDate
                                    + " > Cutoff="
                                    + cutoffDate
                                    + " - "
                                    + xDate.after(cutoffDate));
                if (xDate.after(cutoffDate)) return false;
            }
            Calendar invoice = TimeUtil.getToday();
            invoice.set(Calendar.DAY_OF_MONTH, getInvoiceDay());
            if (invoice.after(today)) invoice.add(Calendar.MONTH, -1);
            Timestamp invoiceDate = new Timestamp(invoice.getTimeInMillis());
            if (log.isLoggable(Level.FINE))
                log.fine(
                        "canInvoice - Date="
                                + xDate
                                + " > Invoice="
                                + invoiceDate
                                + " - "
                                + xDate.after(invoiceDate));
            if (xDate.after(invoiceDate)) return false;
            return true;
        }

        //	Bi-Monthly (+15)
        if (INVOICEFREQUENCY_TwiceMonthly.equals(getInvoiceFrequency())) {
            if (getInvoiceDayCutoff() > 0) {
                Calendar cutoff = TimeUtil.getToday();
                cutoff.set(Calendar.DAY_OF_MONTH, getInvoiceDayCutoff() + 15);
                if (cutoff.after(today)) cutoff.add(Calendar.MONTH, -1);
                Timestamp cutoffDate = new Timestamp(cutoff.getTimeInMillis());
                if (xDate.after(cutoffDate)) return false;
            }
            Calendar invoice = TimeUtil.getToday();
            invoice.set(Calendar.DAY_OF_MONTH, getInvoiceDay() + 15);
            if (invoice.after(today)) invoice.add(Calendar.MONTH, -1);
            Timestamp invoiceDate = new Timestamp(invoice.getTimeInMillis());
            if (xDate.after(invoiceDate)) return false;
            return true;
        }
        return false;
    } //	canInvoice

    /**
     * Convert to Calendar day
     *
     * @param day Invoice Week Day
     * @return day
     */
    private int getCalendarDay(String day) {
        if (INVOICEWEEKDAY_Friday.equals(day)) return Calendar.FRIDAY;
        if (INVOICEWEEKDAY_Saturday.equals(day)) return Calendar.SATURDAY;
        if (INVOICEWEEKDAY_Sunday.equals(day)) return Calendar.SUNDAY;
        if (INVOICEWEEKDAY_Monday.equals(day)) return Calendar.MONDAY;
        if (INVOICEWEEKDAY_Tuesday.equals(day)) return Calendar.TUESDAY;
        if (INVOICEWEEKDAY_Wednesday.equals(day)) return Calendar.WEDNESDAY;
        //	if (INVOICEWEEKDAY_Thursday.equals(day))
        return Calendar.THURSDAY;
    } //	getCalendarDay
} //	MInvoiceSchedule
