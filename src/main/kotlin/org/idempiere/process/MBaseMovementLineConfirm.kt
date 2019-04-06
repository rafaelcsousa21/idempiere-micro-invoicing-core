package org.idempiere.process

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Lines
 *
 * @param requery requery
 * @return array of lines
 */
fun getMovementLineConfirmLines(movementConfirmId: Int): Array<MMovementLineConfirm> {
    val sql = "SELECT * FROM M_MovementLineConfirm WHERE M_MovementConfirm_ID=?"

    val query = queryOf(sql, listOf(movementConfirmId)).map { row -> MMovementLineConfirm(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getLines