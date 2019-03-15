package org.compiere.invoicing

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get Material Allocations for Line
 *
 * @param ctx context
 * @param M_InventoryLine_ID line
 * @return allocations
 */
fun getMaterialAllocationsForLine(ctx: Properties, M_InventoryLine_ID: Int): Array<MInventoryLineMA> {
    val sql = "SELECT * FROM M_InventoryLineMA WHERE M_InventoryLine_ID=?"
    val query = queryOf(sql, listOf(M_InventoryLine_ID)).map { row -> MInventoryLineMA(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	get