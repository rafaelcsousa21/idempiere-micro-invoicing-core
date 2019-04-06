package org.compiere.accounting

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Check for Payment
 *
 * @param ctx context
 * @param C_Payment_ID id
 * @return pay selection check for payment or null
 */
fun getCheckforPayment(C_Payment_ID: Int): MPaySelectionCheck? {
    val sql = "SELECT * FROM C_PaySelectionCheck WHERE C_Payment_ID=?"

    val query = queryOf(sql, listOf(C_Payment_ID)).map { row -> MPaySelectionCheck(row) }.asList
    val result = DB.current.run(query)
    return result.firstOrNull { it.isProcessed }
} // 	getOfPayment

/**
 * Get Payment Selection Lines of this check
 *
 * @param requery requery
 * @return array of payment selection lines
 */
fun getPaySelectionLines(paySelectionCheckId: Int): Array<MPaySelectionLine> {
    val sql = "SELECT * FROM C_PaySelectionLine WHERE C_PaySelectionCheck_ID=? ORDER BY Line"
    val query = queryOf(sql, listOf(paySelectionCheckId)).map { row -> MPaySelectionLine(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getPaySelectionLines