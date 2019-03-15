package org.compiere.wf

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get Message (cached)
 *
 * @param ctx context
 * @param Value message value
 * @return message
 */
fun getMessage(ctx: Properties, Value: String?): MMessage? {
    val sql = "SELECT * FROM AD_Message WHERE Value=?"
    val query = queryOf(sql, listOf(Value)).map { row -> MMessage(ctx, row) }.asSingle
    return DB.current.run(query)
} // 	get
