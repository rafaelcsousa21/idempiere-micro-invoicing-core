package org.idempiere.process

import org.compiere.accounting.*
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get Products to Create New Standard Costs
 *
 * @param as accounting schema
 */
internal fun getProductsToCreateNewStandardCosts(
    ctx: Properties,
    `as`: MAcctSchema,
    p_M_Product_Category_ID: Int,
    M_CostElement_ID: Int
): Array<MProduct> {
    var sql = ("SELECT * FROM M_Product p " +
            "WHERE NOT EXISTS (SELECT * FROM M_Cost c WHERE c.M_Product_ID=p.M_Product_ID" +
            " AND c.M_CostType_ID=? AND c.C_AcctSchema_ID=? AND c.M_CostElement_ID=?" +
            " AND c.M_AttributeSetInstance_ID=0) " +
            "AND AD_Client_ID=?")
    if (p_M_Product_Category_ID != 0) sql += " AND M_Product_Category_ID=?"

    val query =
        queryOf(sql, listOf(`as`.costTypeId, `as`.accountingSchemaId, M_CostElement_ID, `as`.clientId))
        .map { row -> MProduct(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	createNew

/************************************************************************** Get Costs to Update Cost Records
 *
 * @return no updated
 */
internal fun getCostsToUpdate(
    ctx: Properties,
    sql: String,
    M_CostElement_ID: Int,
    p_M_Product_Category_ID: Int
): Array<MCost> {
        val parameters = listOf(M_CostElement_ID) +
            (if (p_M_Product_Category_ID != 0) listOf(p_M_Product_Category_ID) else emptyList())

    val query =
        queryOf(sql.toString(), parameters).map { row -> MCost(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	update