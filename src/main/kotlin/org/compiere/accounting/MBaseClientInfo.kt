package org.compiere.accounting

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Client Info
 *
 * @param ctx context
 * @param AD_Client_ID id
 * @return Client Info
 */
fun getClientInfo(AD_Client_ID: Int): MClientInfo? {
    val sql = "SELECT * FROM AD_ClientInfo WHERE AD_Client_ID=?"
    val query = queryOf(sql, listOf(AD_Client_ID)).map { row -> MClientInfo(row) }.asSingle
    return DB.current.run(query)
} // 	get
