package org.compiere.accounting

import org.compiere.model.I_C_AcctSchema
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**************************************************************************
 * Create Tax Correction.
 * Requirement: Adjust the tax amount, if you did not receive the full amount of the invoice
 * (payment discount, write-off). Applies to many countries with VAT. Example: Invoice: Net $100 +
 * Tax1 $15 + Tax2 $5 = Total $120 Payment: $115 (i.e. $5 underpayment) Tax Adjustment = Tax1 =
 * 0.63 (15/120*5) Tax2 = 0.21 (5/120/5)
 *
 * @param `as` accounting schema
 * @param fact fact
 * @param line Allocation line
 * @param DiscountAccount discount acct
 * @param WriteOffAccoint write off acct
 * @return true if created
 */
fun createTaxCorrection(
    ctx: Properties,
    `as`: I_C_AcctSchema,
    fact: Fact,
    line: DocLine_Allocation,
    tax: DocAllocationTax
): Boolean {
    // 	Get Source Amounts with account
    val sql = ("SELECT * " +
            "FROM Fact_Acct " +
            "WHERE AD_Table_ID=318 AND Record_ID=?" + // 	Invoice

            " AND C_AcctSchema_ID=?" +
            " AND Line_ID IS NULL") // 	header lines like tax or total

    val query = queryOf(sql, listOf(line.invoiceId, `as`.accountingSchemaId)).map {
        tax.addInvoiceFact(
            MFactAcct(
                ctx,
                it
            )
        )
    }.asList
    DB.current.run(query)

    // 	Invoice Not posted
    if (tax.lineCount == 0) {
        return false
    }
    // 	size = 1 if no tax
    return if (tax.lineCount < 2) true else tax.createEntries(`as`, fact, line)
} // 	createTaxCorrection