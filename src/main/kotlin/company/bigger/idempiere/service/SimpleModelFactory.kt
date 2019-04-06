package company.bigger.idempiere.service

import kotliquery.Row
import org.compiere.orm.IModelFactory
import org.idempiere.common.exceptions.AdempiereException
import org.idempiere.icommon.model.IPO

class SimpleModelFactory : IModelFactory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : IPO> getPO(tableName: String, recordId: Int): T {
        return if (simpleMapperId.containsKey(tableName)) simpleMapperId[tableName]?.invoke(recordId) as T else throw AdempiereException("Table '$tableName' is unknown for id $recordId")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : IPO> getPO(tableName: String, row: Row): T {
        return if (simpleMapperRow.containsKey(tableName)) simpleMapperRow[tableName]?.invoke(row) as T else throw AdempiereException("Table '$tableName' is unknown")
    }
}