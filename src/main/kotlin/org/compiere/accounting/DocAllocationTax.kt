package org.compiere.accounting

import org.compiere.model.I_C_AcctSchema

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
    fun createEntries(mAcctSchema: I_C_AcctSchema, fact: Fact, line: DocLine): Boolean
}
