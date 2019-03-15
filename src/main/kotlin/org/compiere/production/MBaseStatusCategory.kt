package org.compiere.production

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get Default Status Category for Client
 *
 * @param ctx context
 * @return status category or null
 */
fun getDefaultStatusCategoryForClient(ctx: Properties, AD_Client_ID: Int): MStatusCategory? {
    val sql = ("SELECT * FROM R_StatusCategory " +
            "WHERE clientId in (0,?) AND IsDefault='Y' " +
            "ORDER BY clientId DESC")

    val query = queryOf(sql, listOf(AD_Client_ID)).map { row -> MStatusCategory(ctx, row) }.asSingle
    return DB.current.run(query)
} // 	getDefault

/**
 * Get all Status
 *
 * @param reload reload
 * @return Status array
 */
fun getAllStatus(ctx: Properties, R_StatusCategory_ID: Int): Array<MStatus> {
    val sql = "SELECT * FROM R_Status " + "WHERE R_StatusCategory_ID=? " + "ORDER BY SeqNo"

    val query = queryOf(sql, listOf(R_StatusCategory_ID)).map { row -> MStatus(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getStatus