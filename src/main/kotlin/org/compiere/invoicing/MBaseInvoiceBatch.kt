package org.compiere.invoicing

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Lines
 *
 * @param reload reload data
 * @return array of lines
 */
fun getLines(C_InvoiceBatch_ID: Int): Array<MInvoiceBatchLine> {
    val sql = "SELECT * FROM C_InvoiceBatchLine WHERE C_InvoiceBatch_ID=? ORDER BY Line"

    val query = queryOf(sql, listOf(C_InvoiceBatch_ID)).map { row -> MInvoiceBatchLine(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getLines