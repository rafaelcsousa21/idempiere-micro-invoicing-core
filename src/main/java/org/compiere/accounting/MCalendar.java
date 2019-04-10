package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_Calendar;
import org.compiere.orm.MClient;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.CCache;

import java.util.Locale;

/**
 * Calendar Model
 *
 * @author Jorg Janke
 * @version $Id: MCalendar.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MCalendar extends X_C_Calendar {
    /**
     *
     */
    private static final long serialVersionUID = 7721451326626542420L;
    /**
     * Cache
     */
    private static CCache<Integer, MCalendar> s_cache =
            new CCache<>(I_C_Calendar.Table_Name, 20);

    /**
     * *********************************************************************** Standard Constructor
     *
     * @param ctx           context
     * @param C_Calendar_ID id
     */
    public MCalendar(int C_Calendar_ID) {
        super(C_Calendar_ID);
    } //	MCalendar

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MCalendar(Row row) {
        super(row);
    } //	MCalendar

    /**
     * Parent Constructor
     *
     * @param client parent
     */
    public MCalendar(MClient client) {
        super(0);
        setClientOrg(client);
        String msgset = client.getName() +
                " " +
                MsgKt.translate("C_Calendar_ID");
        setName(msgset);
    } //	MCalendar

    /**
     * Get MCalendar from Cache
     *
     * @param ctx           context
     * @param C_Calendar_ID id
     * @return MCalendar
     */
    public static MCalendar get(int C_Calendar_ID) {
        Integer key = C_Calendar_ID;
        MCalendar retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MCalendar(C_Calendar_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Create (current) Calendar Year
     *
     * @param locale locale
     * @return The Year
     */
    public MYear createYear(Locale locale) {
        if (getId() == 0) return null;
        MYear year = new MYear(this);
        year.saveEx();
        year.createStdPeriods(locale);
        //
        return year;
    } //	createYear
} //	MCalendar
