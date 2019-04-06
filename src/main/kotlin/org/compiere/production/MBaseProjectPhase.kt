package org.compiere.production

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Project Phase Tasks.
 *
 * @return Array of tasks
 */
fun getProjectPhaseTasks(projectPhaseId: Int): Array<MProjectTask> {
    val sql = "SELECT * FROM C_ProjectTask WHERE C_ProjectPhase_ID=? ORDER BY SeqNo"
    val query = queryOf(sql, listOf(projectPhaseId)).map { row -> MProjectTask(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getTasks
