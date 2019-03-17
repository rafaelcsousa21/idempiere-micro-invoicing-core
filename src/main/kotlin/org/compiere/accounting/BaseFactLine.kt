package org.compiere.accounting

import org.compiere.model.I_M_Movement
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.util.Properties

/**
 * ************************************************************************ Update Line with
 * reversed Original Amount in Accounting Currency. Also copies original dimensions like Project,
 * etc. Called from Doc_MatchInv
 *
 * @param AD_Table_ID table
 * @param Record_ID record
 * @param Line_ID line
 * @param multiplier targetQty/documentQty
 * @return true if success
 */
fun updateReverseLineGetData(
    AD_Table_ID: Int,
    Record_ID: Int,
    Line_ID: Int,
    C_AcctSchema_ID: Int,
    Account_ID: Int,
    M_Locator_ID: Int,
    ctx: Properties
): MFactAcct? {
    val sql = StringBuilder("SELECT * ")
        .append("FROM Fact_Acct ")
        .append("WHERE C_AcctSchema_ID=? AND AD_Table_ID=? AND Record_ID=?")
        .append(" AND Account_ID=?")
    if (Line_ID > 0) {
        sql.append(" AND Line_ID=? ")
    } else {
        sql.append(" AND Line_ID IS NULL ")
    }
    // MZ Goodwill
    // for Inventory Move
    if (I_M_Movement.Table_ID == AD_Table_ID) sql.append(" AND M_Locator_ID=?")
    // end MZ

    val parameters = listOf(
        C_AcctSchema_ID,
        AD_Table_ID,
        Record_ID,
        Account_ID
    ) + (if (Line_ID > 0)
        listOf(Line_ID) else emptyList()
            ) + (if (I_M_Movement.Table_ID == AD_Table_ID) listOf(M_Locator_ID) else emptyList())

    // MZ Goodwill
    // for Inventory Move

    // end MZ

    val query = queryOf(sql.toString(), parameters).map { row -> MFactAcct(ctx, row) }.asSingle
    return DB.current.run(query)
}