package org.idempiere.process

import org.compiere.orm.MRole
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Bank Statement Matcher Algorithms
 *
 * @param ctx context
 * @return matchers
 */
fun getBankStatementMatcherAlgorithms(): Array<MBankStatementMatcher> {
    val sql = MRole.getDefault(false)
        .addAccessSQL(
            "SELECT * FROM C_BankStatementMatcher ORDER BY SeqNo",
            "C_BankStatementMatcher",
            MRole.SQL_NOTQUALIFIED,
            MRole.SQL_RO
        )
    val query = queryOf(sql, listOf()).map { row -> MBankStatementMatcher(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getMatchers
