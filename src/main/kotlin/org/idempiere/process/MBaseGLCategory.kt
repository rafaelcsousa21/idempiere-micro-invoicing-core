package org.idempiere.process

import org.idempiere.common.util.Env
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get Default Category
 *
 * @param ctx context
 * @param CategoryType optional CategoryType (ignored, if not exists)
 * @return GL Category or null
 */
fun getDefault(ctx: Properties, CategoryType: String?): MGLCategory? {
    val sql = "SELECT * FROM GL_Category WHERE AD_Client_ID=? AND IsDefault='Y'"

    val query = queryOf(sql, listOf(Env.getClientId(ctx))).map { row -> MGLCategory(ctx, row) }.asList
    val items = DB.current.run(query)

    return items.firstOrNull { CategoryType != null && it.categoryType == CategoryType } ?: items.firstOrNull()
} // 	getDefault
