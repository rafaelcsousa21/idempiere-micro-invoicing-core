package org.compiere.accounting

import org.compiere.model.AccountingSchema
import org.compiere.model.I_M_CostElement
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get/Create Cost Queue Record. CostingLevel is not validated
 *
 * @param product product
 * @param M_AttributeSetInstance_ID real asi
 * @param `as`                        accounting schema
 * @param AD_Org_ID real org
 * @param M_CostElement_ID element
 * @param trxName transaction
 * @return cost queue or null
 */
fun getCreateCostQueueRecord(
    product: MProduct,
    M_AttributeSetInstance_ID: Int,
    `as`: MAcctSchema,
    AD_Org_ID: Int,
    M_CostElement_ID: Int
): MCostQueue {
    val sql = ("SELECT * FROM M_CostQueue " +
            "WHERE AD_Client_ID=? AND AD_Org_ID=?" +
            " AND M_Product_ID=?" +
            " AND M_AttributeSetInstance_ID=?" +
            " AND M_CostType_ID=? AND C_AcctSchema_ID=?" +
            " AND M_CostElement_ID=?")

    val parameters = listOf(
        product.clientId,
        AD_Org_ID,
        product.productId,
        M_AttributeSetInstance_ID,
        `as`.costTypeId,
        `as`.accountingSchemaId,
        M_CostElement_ID
    )

    val query = queryOf(sql, parameters).map { row -> MCostQueue(row) }.asSingle
    return DB.current.run(query) ?: MCostQueue(
        product, M_AttributeSetInstance_ID, `as`, AD_Org_ID, M_CostElement_ID
    )
} // 	get

/**
 * Get Cost Queue Records in Lifo/Fifo order
 *
 * @param product product
 * @param M_ASI_ID costing level ASI
 * @param as accounting schema
 * @param Org_ID costing level org
 * @param ce Cost Element
 * @return cost queue or null
 */
fun getCostQueueRecordsInLifoFifoOrder(
    product: MProduct,
    M_ASI_ID: Int,
    `as`: AccountingSchema,
    Org_ID: Int,
    ce: I_M_CostElement
): Array<MCostQueue> {
    val sql = StringBuilder("SELECT * FROM M_CostQueue ")
        .append("WHERE AD_Client_ID=? AND AD_Org_ID=?")
        .append(" AND M_Product_ID=?")
        .append(" AND M_CostType_ID=? AND C_AcctSchema_ID=?")
        .append(" AND M_CostElement_ID=?")
    if (M_ASI_ID != 0) sql.append(" AND M_AttributeSetInstance_ID=?")
    sql.append(" AND CurrentQty<>0 ").append("ORDER BY M_AttributeSetInstance_ID ")
    if (!ce.isFifo) sql.append("DESC")

    val parameters = listOf(
        product.clientId,
        Org_ID,
        product.productId,
        `as`.costTypeId,
        `as`.accountingSchemaId,
        ce.costElementId
    ) + (if (M_ASI_ID != 0) listOf(M_ASI_ID) else emptyList())

    val query = queryOf(sql.toString(), parameters).map { row -> MCostQueue(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getQueue