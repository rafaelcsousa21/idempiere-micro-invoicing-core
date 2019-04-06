package org.compiere.production

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf

/**
 * Get Goals with Measure
 *
 * @param ctx context
 * @param PA_Measure_ID measure
 * @return goals
 */
fun getGoalsWithMeasure(PA_Measure_ID: Int): Array<MGoal> {
    val sql = "SELECT * FROM PA_Goal WHERE IsActive='Y' AND PA_Measure_ID=? " + "ORDER BY SeqNo"
    val query = queryOf(sql, listOf(PA_Measure_ID)).map { row -> MGoal(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getMeasureGoals

/**
 * Get Restriction Lines
 *
 * @param reload reload data
 * @return array of lines
 */
fun getRestrictions(PA_Goal_ID: Int): Array<MGoalRestriction> {
    //
    val sql = ("SELECT * FROM PA_GoalRestriction " +
            "WHERE PA_Goal_ID=? AND IsActive='Y' " +
            "ORDER BY Org_ID, C_BPartner_ID, M_Product_ID")

    val query = queryOf(sql, listOf(PA_Goal_ID)).map { row -> MGoalRestriction(row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getRestrictions