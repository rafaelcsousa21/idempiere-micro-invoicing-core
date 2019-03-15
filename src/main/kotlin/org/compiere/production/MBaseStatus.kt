package org.compiere.production

import org.idempiere.common.util.Env
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get Default Request Status
 *
 * @param ctx context
 * @param R_RequestType_ID request type
 * @return Request Type
 */
fun getDefaultRequestStatus(ctx: Properties, R_RequestType_ID: Int): MStatus? {
    // 	Get New
    val sql = ("SELECT * FROM R_Status s " +
            "WHERE EXISTS (SELECT * FROM R_RequestType rt " +
            "WHERE rt.R_StatusCategory_ID=s.R_StatusCategory_ID" +
            " AND rt.R_RequestType_ID=?)" +
            " AND IsDefault='Y' " +
            "ORDER BY SeqNo")
    val query = queryOf(sql, listOf(R_RequestType_ID)).map { row -> MStatus(ctx, row) }.asSingle
    return DB.current.run(query)
} // 	getDefault

/**
 * Get Closed Status
 *
 * @param ctx context
 * @return Request Type
 */
fun getClosedStatuses(ctx: Properties): Array<MStatus> {
    val AD_Client_ID = Env.getClientId(ctx)
    val sql = ("SELECT * FROM R_Status " +
            "WHERE AD_Client_ID=? AND IsActive='Y' AND IsClosed='Y' " +
            "ORDER BY Value")

    val query = queryOf(sql, listOf(AD_Client_ID)).map { row -> MStatus(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	get