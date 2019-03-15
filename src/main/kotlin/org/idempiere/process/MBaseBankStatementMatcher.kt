package org.idempiere.process

import org.compiere.orm.MRole
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get Bank Statement Matcher Algorithms
 *
 * @param ctx context
 * @return matchers
 */
fun getBankStatementMatcherAlgorithms(ctx: Properties): Array<MBankStatementMatcher> {
    val sql = MRole.getDefault(ctx, false)
        .addAccessSQL(
            "SELECT * FROM C_BankStatementMatcher ORDER BY SeqNo",
            "C_BankStatementMatcher",
            MRole.SQL_NOTQUALIFIED,
            MRole.SQL_RO
        )
    val query = queryOf(sql, listOf()).map { row -> MBankStatementMatcher(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getMatchers
