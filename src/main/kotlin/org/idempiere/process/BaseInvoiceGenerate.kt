package org.idempiere.process

import org.compiere.accounting.MOrder
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

internal fun getOrdersForInvoiceGeneration(
    ctx: Properties,
    sql: String,
    AD_PInstance_ID: Int,
    p_Selection: Boolean,
    p_AD_Org_ID: Int,
    p_C_BPartner_ID: Int,
    p_C_Order_ID: Int
): Array<MOrder> {

    val parameters = listOf(
        if (p_Selection) {
            AD_PInstance_ID
        } else {
            (if (p_AD_Org_ID != 0) listOf(p_AD_Org_ID) else emptyList()) +
            (if (p_C_BPartner_ID != 0) listOf(p_C_BPartner_ID) else emptyList()) +
             (if (p_C_Order_ID != 0) listOf(p_C_Order_ID) else emptyList())
        }
    )

    val query =
        queryOf(sql.toString(), parameters).map { row -> MOrder(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
}