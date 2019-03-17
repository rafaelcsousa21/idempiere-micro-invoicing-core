package org.compiere.invoicing

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get Payment Schedule of the invoice
 *
 * @param ctx context
 * @param C_Invoice_ID invoice id (direct)
 * @param C_InvoicePaySchedule_ID id (indirect)
 * @return array of schedule
 */
fun getInvoicePaySchedule(
    ctx: Properties,
    C_Invoice_ID: Int,
    C_InvoicePaySchedule_ID: Int
): Array<MInvoicePaySchedule> {
    val sql = StringBuilder("SELECT * FROM C_InvoicePaySchedule ips WHERE IsActive='Y' ")
    if (C_Invoice_ID != 0)
        sql.append("AND C_Invoice_ID=? ")
    else
        sql.append("AND EXISTS (SELECT * FROM C_InvoicePaySchedule x")
            .append(" WHERE x.C_InvoicePaySchedule_ID=? AND ips.C_Invoice_ID=x.C_Invoice_ID) ")
    sql.append("ORDER BY DueDate")
    //

    val parameters = listOf(
        if (C_Invoice_ID != 0)
            C_Invoice_ID
        else
            C_InvoicePaySchedule_ID
    )

    val query =
        queryOf(sql.toString(), parameters).map { row -> MInvoicePaySchedule(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getSchedule