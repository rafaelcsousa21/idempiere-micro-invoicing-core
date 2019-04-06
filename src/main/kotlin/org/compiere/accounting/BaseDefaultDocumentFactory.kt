package org.compiere.accounting

import org.compiere.model.IDoc
import org.compiere.model.IDocFactory
import org.compiere.model.I_C_AcctSchema
import org.compiere.orm.MTable
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Base functionality to [getDocument] by [recordId].
 */
abstract class BaseDefaultDocumentFactory : IDocFactory {
    override fun getDocument(accountingSchema: I_C_AcctSchema?, tableId: Int, recordId: Int): IDoc? {
        val tableName = MTable.getDbTableName(tableId)
        //
        val sql = StringBuffer("SELECT * FROM ")
            .append(tableName)
            .append(" WHERE ")
            .append(tableName)
            .append("_ID=? AND Processed='Y'")

        val query =
            queryOf(sql.toString(), listOf(recordId))
            .map { row -> getDocument(accountingSchema, tableId, row) }.asSingle
        return DB.current.run(query)
    }
}