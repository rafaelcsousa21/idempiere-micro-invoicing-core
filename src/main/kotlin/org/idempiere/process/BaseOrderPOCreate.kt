package org.idempiere.process

import org.compiere.accounting.MOrder
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.sql.Timestamp
import java.util.Properties

fun getOrdersForPO(
    ctx: Properties,
    sql: String,
    p_C_Order_ID: Int,
    p_C_BPartner_ID: Int,
    p_Vendor_ID: Int,
    p_DateOrdered_From: Timestamp?,
    p_DateOrdered_To: Timestamp?
): Array<MOrder> {
    val parameters =
        if (p_C_Order_ID != 0)
            listOf(p_C_Order_ID)
        else {
            (if (p_C_BPartner_ID != 0) listOf(p_C_BPartner_ID) else emptyList()) +
            (if (p_Vendor_ID != 0) listOf(p_Vendor_ID) else emptyList()) +
                    (if (p_DateOrdered_From != null && p_DateOrdered_To != null) {
                listOf(p_DateOrdered_From, p_DateOrdered_To)
            } else if (p_DateOrdered_From != null && p_DateOrdered_To == null)
                        listOf(p_DateOrdered_From)
            else if (p_DateOrdered_From == null && p_DateOrdered_To != null)
                        listOf(p_DateOrdered_To) else emptyList())
        }

    val query =
        queryOf(sql.toString(), parameters).map { row -> MOrder(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
}