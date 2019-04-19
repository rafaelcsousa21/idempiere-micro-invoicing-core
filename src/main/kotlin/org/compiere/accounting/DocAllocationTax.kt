package org.compiere.accounting

import org.compiere.model.AccountingSchema

/**
 * Callback to Tax Correction
 */
interface DocAllocationTax {
    val lineCount: Int

    /**
     * Add Invoice Fact Line
     */
    fun addInvoiceFact(mFactAcct: MFactAcct)

    /**
     * Create Accounting Entries
     */
    fun createEntries(mAcctSchema: AccountingSchema, fact: Fact, line: DocLine): Boolean
}
