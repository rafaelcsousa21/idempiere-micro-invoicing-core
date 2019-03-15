package org.idempiere.process

import org.compiere.production.MRequest
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

internal fun getRequestsToBeInvoiced(
    ctx: Properties,
    p_R_RequestType_ID: Int,
    p_R_Group_ID: Int,
    p_R_Category_ID: Int,
    p_C_BPartner_ID: Int
): Array<MRequest> {
    val sql = StringBuilder("SELECT * FROM R_Request r")
        .append(" INNER JOIN R_Status s ON (r.R_Status_ID=s.R_Status_ID) ")
        .append("WHERE s.IsClosed='Y'")
        .append(" AND r.R_RequestType_ID=?")
    // globalqss -- avoid double invoicing
    // + " AND EXISTS (SELECT 1 FROM R_RequestUpdate ru " +
    // 		"WHERE ru.R_Request_ID=r.R_Request_ID AND NVL(C_InvoiceLine_ID,0)=0";
    if (p_R_Group_ID != 0) sql.append(" AND r.R_Group_ID=?")
    if (p_R_Category_ID != 0) sql.append(" AND r.R_Category_ID=?")
    if (p_C_BPartner_ID != 0) sql.append(" AND r.C_BPartner_ID=?")
    sql.append(" AND r.IsInvoiced='Y' ").append("ORDER BY C_BPartner_ID")

    val parameters =
            listOf(p_R_RequestType_ID) +
                    (if (p_R_Group_ID != 0) listOf(p_R_Group_ID) else emptyList()) +
                    (if (p_R_Category_ID != 0) listOf(p_R_Category_ID) else emptyList()) +
                    (if (p_C_BPartner_ID != 0) listOf(p_C_BPartner_ID) else emptyList())
    val query =
        queryOf(sql.toString(), parameters).map { row -> MRequest(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
}