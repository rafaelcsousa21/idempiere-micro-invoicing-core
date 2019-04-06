package org.compiere.production

import org.compiere.model.I_M_Warehouse
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * FR [ 1966333 ] Get oldest Default Locator of warehouse with locator
 *
 * @return locator or null
 */
fun getOldestDefaultLocatorOfWarehouseWithLocator(warehouse: I_M_Warehouse): MLocator? {
    val sql = ("SELECT * FROM M_Locator l " +
            "WHERE IsActive = 'Y' AND IsDefault='Y' AND l.M_Warehouse_ID=? " +
            "ORDER BY PriorityNo")
    val query = queryOf(sql, listOf(warehouse.warehouseId)).map { row -> MLocator(row) }.asSingle
    return DB.current.run(query)
} // 	getDefault