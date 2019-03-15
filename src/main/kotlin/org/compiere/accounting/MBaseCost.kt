package org.compiere.accounting

import kotliquery.Row
import org.compiere.accounting.MCost.getCurrentCost
import org.idempiere.common.util.Env
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Create costing for client.
 *
 * @param client client
 */
fun createCostingForClient(client: MClient) {
    val ass = MAcctSchema.getClientAcctSchema(client.ctx, client.clientId)
    // 	For all Products
    val sql = ("SELECT * FROM M_Product p " +
            "WHERE AD_Client_ID=?" +
            " AND EXISTS (SELECT * FROM M_CostDetail cd " +
            "WHERE p.M_Product_ID=cd.M_Product_ID AND Processed='N')")

    fun load(row: Row): Int {
        val product = MProduct(client.ctx, row)
        for (i in ass.indices) {
            val cost = getCurrentCost(
                product,
                0,
                ass[i],
                0, null,
                Env.ONE,
                0,
                false
            ) // 	create non-zero costs
        }
        return 0
    }

    val query = queryOf(sql, listOf(client.clientId)).map { load(it) }.asList
    DB.current.run(query).min()
} // 	create