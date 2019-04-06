package org.idempiere.process

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Replenish Records
 *
 * @return replenish
 */
fun getReplenishRecords(where: String?, AD_PInstance_ID: Int): Array<X_T_Replenish> {
    val sql = StringBuilder("SELECT * FROM T_Replenish ")
    sql.append("WHERE AD_PInstance_ID=? AND C_BPartner_ID > 0 ")
    if (where != null && where.length > 0) sql.append(" AND ").append(where)
    sql.append(" ORDER BY M_Warehouse_ID, M_WarehouseSource_ID, C_BPartner_ID")

    val query = queryOf(sql.toString(), listOf(AD_PInstance_ID)).map { row -> X_T_Replenish(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getReplenish

/**
 * Get Replenish Records
 *
 * @return replenish
 */
fun getReplenishDO(where: String?, AD_PInstance_ID: Int): Array<X_T_Replenish> {
    val sql = StringBuilder("SELECT * FROM T_Replenish ")
    sql.append("WHERE AD_PInstance_ID=? ")
    if (where != null && where.length > 0) sql.append(" AND ").append(where)
    sql.append(" ORDER BY M_Warehouse_ID, M_WarehouseSource_ID, C_BPartner_ID")

    val query = queryOf(sql.toString(), listOf(AD_PInstance_ID)).map { row -> X_T_Replenish(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getReplenish