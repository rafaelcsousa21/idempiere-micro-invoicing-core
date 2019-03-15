package org.compiere.invoicing

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get Lines
 *
 * @param reload reload data
 * @return array of lines
 */
fun getLines(ctx: Properties, C_InvoiceBatch_ID: Int): Array<MInvoiceBatchLine> {
    val sql = "SELECT * FROM C_InvoiceBatchLine WHERE C_InvoiceBatch_ID=? ORDER BY Line"

    val query = queryOf(sql, listOf(C_InvoiceBatch_ID)).map { row -> MInvoiceBatchLine(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getLines