package org.compiere.invoicing

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Costs of Invoice Line
 *
 * @param il invoice line
 * @return array of landed cost lines
 */
fun getInvoiceLineLandedCosts(il: MInvoiceLine): Array<MLandedCost> {
    val sql = "SELECT * FROM C_LandedCost WHERE C_InvoiceLine_ID=?"
    val query = queryOf(sql, listOf(il.c_InvoiceLine_ID)).map { row -> MLandedCost(il.ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // getLandedCosts