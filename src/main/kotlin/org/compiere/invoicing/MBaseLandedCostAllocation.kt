package org.compiere.invoicing

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Cost Allocations for invoice Line
 *
 * @param ctx context
 * @param C_InvoiceLine_ID invoice line
 * @return landed cost alloc
 */
fun getCostAllocationsforInvoiceLine(
    C_InvoiceLine_ID: Int
): Array<MLandedCostAllocation> {
    val sql = "SELECT * FROM C_LandedCostAllocation WHERE C_InvoiceLine_ID=?"

    val query = queryOf(sql, listOf(C_InvoiceLine_ID)).map { row -> MLandedCostAllocation(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getOfInvliceLine