package org.idempiere.process

import org.compiere.accounting.MOrder
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

internal fun getOrdersToBatchProcess(sql: String, p_C_DocTypeTarget_ID: Int, p_DocStatus: String?): Array<MOrder> {
    val query = queryOf(sql, listOf(p_C_DocTypeTarget_ID, p_DocStatus)).map { row -> MOrder(row) }.asList
    return DB.current.run(query).toTypedArray()
}