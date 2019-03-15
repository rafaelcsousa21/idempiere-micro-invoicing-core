package org.compiere.production

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Get Project Type Phases
 *
 * @return Array of phases
 */
fun getProjectTypePhaseTasks(ctx: Properties, projectPhaseId: Int): Array<MProjectTypeTask> {
    val sql = "SELECT * FROM C_Task WHERE C_Phase_ID=? ORDER BY SeqNo"
    val query = queryOf(sql, listOf(projectPhaseId)).map { row -> MProjectTypeTask(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getPhases
