package org.idempiere.process

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Material Allocations for Line
 *
 * @param ctx context
 * @param M_MovementLine_ID line
 * @return allocations
 */
fun getMaterialAllocationsForLine(M_MovementLine_ID: Int): Array<MMovementLineMA> {
    val sql = "SELECT * FROM M_MovementLineMA WHERE M_MovementLine_ID=?"
    val query = queryOf(sql, listOf(M_MovementLine_ID)).map { row -> MMovementLineMA(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	get