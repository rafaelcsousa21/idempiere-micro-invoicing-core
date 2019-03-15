package org.idempiere.process

import org.compiere.orm.MSequence
import org.compiere.process.SvrProcess
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * Check Table Sequence ID values
 *
 * @param ctx context
 * @param sp server process or null
 */
internal fun checkTableID(ctx: Properties, sp: SvrProcess?) {
    val sql = "SELECT * FROM AD_Sequence " + "WHERE IsTableID='Y' " + "ORDER BY Name"

    val query = queryOf(sql, listOf()).map { row -> MSequence(ctx, row).validateTableIDValue() }.asList
    DB.current.run(query)
} // 	checkTableID