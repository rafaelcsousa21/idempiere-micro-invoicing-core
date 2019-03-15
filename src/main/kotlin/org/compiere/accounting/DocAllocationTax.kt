package org.compiere.accounting

interface DocAllocationTax {
    val lineCount: Int

    fun addInvoiceFact(mFactAcct: MFactAcct)
    fun createEntries(mAcctSchema: MAcctSchema, fact: Fact, line: DocLine): Boolean
}
