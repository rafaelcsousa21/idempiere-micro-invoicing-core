package org.compiere.accounting

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get Storage Info
 *
 * @param ctx context
 * @param M_Warehouse_ID warehouse
 * @param M_Product_ID product
 * @param M_AttributeSetInstance_ID instance
 * @param isSOTrx
 * @return existing or null
 */
fun getStorageInfo(
    ctx: Properties,
    M_Warehouse_ID: Int,
    M_Product_ID: Int,
    M_AttributeSetInstance_ID: Int,
    isSOTrx: Boolean
): MStorageReservation? {
    var sql = "SELECT * FROM M_StorageReservation " + "WHERE M_Warehouse_ID=? AND M_Product_ID=? AND IsSOTrx=? AND "
    if (M_AttributeSetInstance_ID == 0)
        sql += "(M_AttributeSetInstance_ID=? OR M_AttributeSetInstance_ID IS NULL)"
    else
        sql += "M_AttributeSetInstance_ID=?"

    val query =
        queryOf(sql, listOf(M_Warehouse_ID, M_Product_ID, if (isSOTrx) "Y" else "N", M_AttributeSetInstance_ID))
            .map { row -> MStorageReservation(ctx, row) }.asSingle
    return DB.current.run(query)
} // 	get