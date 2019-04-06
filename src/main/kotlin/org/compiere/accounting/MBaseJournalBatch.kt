package org.compiere.accounting

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Journal Lines
 *
 * @param requery requery
 * @return Array of lines
 */
fun getJournalLines(GL_JournalBatch_ID: Int): Array<MJournal> {
    val sql = "SELECT * FROM GL_Journal WHERE GL_JournalBatch_ID=? ORDER BY DocumentNo"

    val query = queryOf(sql, listOf(GL_JournalBatch_ID)).map { row -> MJournal(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getJournals