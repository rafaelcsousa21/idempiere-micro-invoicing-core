package org.idempiere.process

import kotliquery.Row
import org.compiere.accounting.MTimeExpense
import org.compiere.accounting.MTimeExpenseLine
import org.compiere.crm.MBPartner
import org.compiere.process.SvrProcess
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.sql.Timestamp

/**
 * Database part of the Create Sales Orders from Expense Reports process
 */
abstract class BaseExpenseSOrder : SvrProcess() {

    /**
     * No SO generated
     */
    protected var m_noOrders = 0

    /**
     * BPartner
     */
    protected var p_C_BPartner_ID = 0
    /**
     * Date From
     */
    protected var p_DateFrom: Timestamp? = null
    /**
     * Date To
     */
    protected var m_DateTo: Timestamp? = null

    protected abstract fun completeOrder()

    protected abstract fun processLine(te: MTimeExpense?, tel: MTimeExpenseLine, bp: MBPartner?)

    /**
     * Perform process.
     *
     * @return Message to be translated
     * @throws Exception
     */
    override fun doIt(): String {
        val sql = StringBuilder("SELECT * FROM S_TimeExpenseLine el ")
            .append("WHERE el.AD_Client_ID=?") // 	#1
            .append(" AND el.C_BPartner_ID>0 AND el.IsInvoiced='Y'") // 	Business Partner && to be
            // invoiced
            .append(" AND el.C_OrderLine_ID IS NULL") // 	not invoiced yet
            .append(" AND EXISTS (SELECT * FROM S_TimeExpense e ") // 	processed only
            .append("WHERE el.S_TimeExpense_ID=e.S_TimeExpense_ID AND e.Processed='Y')")
        if (p_C_BPartner_ID != 0) sql.append(" AND el.C_BPartner_ID=?") // 	#2
        if (p_DateFrom != null || m_DateTo != null) {
            sql.append(" AND EXISTS (SELECT * FROM S_TimeExpense e ")
                .append("WHERE el.S_TimeExpense_ID=e.S_TimeExpense_ID")
            if (p_DateFrom != null) sql.append(" AND e.DateReport >= ?") // 	#3
            if (m_DateTo != null) sql.append(" AND e.DateReport <= ?") // 	#4
            sql.append(")")
        }
        sql.append(" ORDER BY el.C_BPartner_ID, el.C_Project_ID, el.S_TimeExpense_ID, el.Line")

        val parameters =
            listOf(getClientId()) +
                    (if (p_C_BPartner_ID != 0) listOf(p_C_BPartner_ID) else listOf()) +
                    (if (p_DateFrom != null) listOf(p_DateFrom) else listOf()) +
                    (if (m_DateTo != null) listOf(m_DateTo) else listOf())

        var oldBPartner: MBPartner? = null
        var old_Project_ID = -1
        var te: MTimeExpense? = null

        fun processRow(row: Row): Int {
            val tel = MTimeExpenseLine(ctx, row)
            if (!tel.isInvoiced) return 0

            val localOldBPartner = oldBPartner

            // 	New BPartner - New Order
            if (localOldBPartner == null || localOldBPartner.businessPartnerId != tel.businessPartnerId) {
                completeOrder()
                oldBPartner = MBPartner(ctx, tel.businessPartnerId)
            }
            // 	New Project - New Order
            if (old_Project_ID != tel.projectId) {
                completeOrder()
                old_Project_ID = tel.projectId
            }

            val localTe = te
            if (localTe == null || localTe.timeExpenseId != tel.timeExpenseId)
                te = MTimeExpense(getCtx(), tel.timeExpenseId)
            //
            processLine(localTe, tel, localOldBPartner)

            return 0
        }

        val query = queryOf(sql.toString(), parameters).map { row -> processRow(row) }.asList
        DB.current.run(query).min()

        //
        completeOrder()
        val msgreturn = StringBuilder("@Created@=").append(m_noOrders)
        return msgreturn.toString()
    } // 	doIt
}