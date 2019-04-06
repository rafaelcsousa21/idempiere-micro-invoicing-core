package org.idempiere.process

import org.compiere.accounting.MTimeExpense
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.sql.Timestamp

/**
 *  get Expense Reports to Create AP Invoices from
 *  see [ExpenseAPInvoice]
 */
fun getExpensesToBeInvoices(
    sql: String,
    clientId: Int,
    m_C_BPartner_ID: Int,
    m_DateFrom: Timestamp?,
    m_DateTo: Timestamp?
): Array<MTimeExpense> {
    val parameters = listOf(clientId) +
        (if (m_C_BPartner_ID != 0) listOf(m_C_BPartner_ID) else emptyList()) +
        (if (m_DateFrom != null) listOf(m_DateFrom) else emptyList()) +
        (if (m_DateTo != null) listOf(m_DateTo) else emptyList())

    val query = queryOf(sql, parameters).map { row -> MTimeExpense(row) }.asList
    return DB.current.run(query).toTypedArray()
}