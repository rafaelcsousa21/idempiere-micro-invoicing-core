package org.compiere.accounting

import org.compiere.model.I_C_AcctSchema

interface DocAllocationTax {
    val lineCount: Int

    fun addInvoiceFact(mFactAcct: MFactAcct)
    fun createEntries(mAcctSchema: I_C_AcctSchema, fact: Fact, line: DocLine): Boolean
}
