package org.compiere.accounting

import org.compiere.model.I_C_Period
import org.compiere.orm.TimeUtil
import org.idempiere.common.util.CCache
import software.hsharp.core.util.DB
import software.hsharp.core.util.Environment
import software.hsharp.core.util.queryOf
import java.sql.Timestamp

/**
 * Cache
 */
val periodCache = CCache<Int, MPeriod>(I_C_Period.Table_Name, 10)

/**
 * Find a period by calendar
 */
fun findByCalendar(DateAcct: Timestamp, calendarId: Int): MPeriod? {
    val AD_Client_ID = Environment.current.clientId
    // 	Search in Cache first
    val it = periodCache.values.iterator()
    while (it.hasNext()) {
        val period = it.next() as MPeriod
        if (period.calendarId == calendarId &&
            period.isStandardPeriod &&
            period.isInPeriod(DateAcct) &&
            period.clientId == AD_Client_ID
        )
        // globalqss - CarlosRuiz - Fix [ 1820810 ] Wrong Period Assigned to
        // Fact_Acct
            return period
    }

    val sql = ("SELECT * " +
            "FROM C_Period " +
            "WHERE C_Year_ID IN " +
            "(SELECT C_Year_ID FROM C_Year WHERE C_Calendar_ID= ?)" +
            " AND ? BETWEEN TRUNC(StartDate) AND TRUNC(EndDate)" +
            " AND IsActive=? AND PeriodType=?")

    val query =
        queryOf(sql, listOf(calendarId, TimeUtil.getDay(DateAcct), "Y", "S")).map { row -> MPeriod(row) }.asList

    val items = DB.current.run(query)

    var retValue: MPeriod? = null

    for (period in items) {
        val key = period.getPeriodId()
        periodCache[key] = period
        if (period.isStandardPeriod()) retValue = period
    }

    return retValue
}

/**
 * Find first Year Period of DateAcct based on Client Calendar
 *
 * @param ctx context
 * @param DateAcct date
 * @param AD_Org_ID TODO
 * @return active first Period
 */
fun getFirstPeriodInYear(DateAcct: Timestamp, AD_Org_ID: Int): MPeriod? {
    val C_Calendar_ID = MPeriod.get(DateAcct, AD_Org_ID).calendarId

    val sql = ("SELECT * " +
            "FROM C_Period " +
            "WHERE C_Year_ID IN " +
            "(SELECT p.C_Year_ID " +
            "FROM C_Year y" +
            " INNER JOIN C_Period p ON (y.C_Year_ID=p.C_Year_ID) " +
            "WHERE y.C_Calendar_ID=?" +
            "     AND ? BETWEEN StartDate AND EndDate)" +
            " AND IsActive=? AND PeriodType=? " +
            "ORDER BY StartDate")

    val query = queryOf(sql, listOf(C_Calendar_ID, DateAcct, "Y", "S")).map { row -> MPeriod(row) }.asSingle
    return DB.current.run(query)
} // 	getFirstInYear

/**
 * Get Period Control
 *
 * @param requery requery
 * @return period controls
 */
fun getPeriodControls(C_Period_ID: Int): Array<MPeriodControl> {
    val sql = "SELECT * FROM C_PeriodControl " + "WHERE C_Period_ID=?"

    val query = queryOf(sql, listOf(C_Period_ID)).map { row -> MPeriodControl(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getPeriodControls