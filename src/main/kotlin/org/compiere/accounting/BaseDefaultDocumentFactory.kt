package org.compiere.accounting

import org.compiere.model.IDoc
import org.compiere.model.IDocFactory
import org.compiere.model.I_C_AcctSchema
import org.compiere.orm.MTable
import org.idempiere.common.util.Env
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

abstract class BaseDefaultDocumentFactory : IDocFactory {
    override fun getDocument(`as`: I_C_AcctSchema?, AD_Table_ID: Int, Record_ID: Int): IDoc? {
        val tableName = MTable.getDbTableName(Env.getCtx(), AD_Table_ID)
        //
        val sql = StringBuffer("SELECT * FROM ")
            .append(tableName)
            .append(" WHERE ")
            .append(tableName)
            .append("_ID=? AND Processed='Y'")

        val query =
            queryOf(sql.toString(), listOf(Record_ID))
            .map { row -> getDocument(`as`, AD_Table_ID, row) }.asSingle
        return DB.current.run(query)
    }
}