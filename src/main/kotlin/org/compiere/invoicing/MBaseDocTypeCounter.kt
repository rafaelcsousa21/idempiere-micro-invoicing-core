package org.compiere.invoicing

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get (first) valid Counter document for document type
 *
 * @param ctx context
 * @param C_DocType_ID base document
 * @return counter document (may be invalid) or null
 */
fun getCounterDocType(ctx: Properties, C_DocType_ID: Int): MDocTypeCounter? {
    // 	Direct Relationship
    val sql = "SELECT * FROM C_DocTypeCounter WHERE C_DocType_ID=?"
    val query = queryOf(sql, listOf(C_DocType_ID)).map { row -> MDocTypeCounter(ctx, row) }.asList
    val items = DB.current.run(query)
    return items.firstOrNull { it.isValid && it.isCreateCounter } ?: items.firstOrNull()
    // 	nothing found
} // 	getCounterDocType
