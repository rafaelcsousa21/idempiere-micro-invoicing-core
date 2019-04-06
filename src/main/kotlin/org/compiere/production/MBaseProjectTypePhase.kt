package org.compiere.production

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Project Type Phases
 *
 * @return Array of phases
 */
fun getProjectTypePhaseTasks(projectPhaseId: Int): Array<MProjectTypeTask> {
    val sql = "SELECT * FROM C_Task WHERE C_Phase_ID=? ORDER BY SeqNo"
    val query = queryOf(sql, listOf(projectPhaseId)).map { row -> MProjectTypeTask(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getPhases
