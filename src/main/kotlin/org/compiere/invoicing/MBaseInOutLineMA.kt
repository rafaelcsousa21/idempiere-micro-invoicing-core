package org.compiere.invoicing

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Material Allocations from shipment which is not returned
 *
 * @param ctx context
 * @param inOutLineId line
 * @return allocations
 */
fun getMaterialAllocationsFromShipmentWhichIsNotReturned(inOutLineId: Int): Array<MInOutLineMA> {
    val sql =
        "SELECT * FROM M_InoutLineMA_Returned WHERE (returnedQty<>movementQty or returnedQty is null) and m_inoutline_id=? "
    val query = queryOf(sql, listOf(inOutLineId)).map { row -> MInOutLineMA(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getNonReturned