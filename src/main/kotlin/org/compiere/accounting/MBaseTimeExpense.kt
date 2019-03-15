package org.compiere.accounting

import kotliquery.Row
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get Lines
 *
 * @param requery true requeries
 * @return array of lines
 */
fun getBaseExpenseLines(ctx: Properties, timeExpenseId: Int, currencyId: Int): Array<MTimeExpenseLine> {
    val sql = "SELECT * FROM S_TimeExpenseLine WHERE S_TimeExpense_ID=? ORDER BY Line"

    fun load(row: Row): MTimeExpenseLine {
        val te = MTimeExpenseLine(ctx, row)
        te.c_Currency_Report_ID = currencyId
        return te
    }

    val query = queryOf(sql, listOf(timeExpenseId)).map { load(it) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getLines
