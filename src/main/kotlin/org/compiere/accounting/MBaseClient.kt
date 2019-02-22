package org.compiere.accounting

import kotliquery.Row
import org.compiere.orm.MTree_Base
import org.compiere.orm.X_AD_Tree
import org.idempiere.common.util.Env
import software.hsharp.core.util.DB
import software.hsharp.core.util.TO_STRING
import software.hsharp.core.util.queryOf
import java.sql.ResultSet
import java.util.*
import java.util.logging.Level

open class MBaseClient : org.compiere.orm.MClient {
    constructor(ctx: Properties, ID: Int) : super(ctx, ID)
    constructor (ctx: Properties, rs: ResultSet) : super(ctx, rs)
    constructor(ctx: Properties, rs: Row) : super(ctx, rs)
    constructor(ctx: Properties, clientId: Int, createNew: Boolean) : super(ctx, clientId, createNew)

    /** Client Info Setup Tree for Account  */
    private var m_AD_Tree_Account_ID: Int = 0

    fun setupClientInfo(language: String): Boolean {
        //  Tree IDs
        var AD_Tree_Org_ID = 0
        var AD_Tree_BPartner_ID = 0
        var AD_Tree_Project_ID = 0
        var AD_Tree_SalesRegion_ID = 0
        var AD_Tree_Product_ID = 0
        var AD_Tree_Campaign_ID = 0
        var AD_Tree_Activity_ID = 0

        fun mapRow(row: Row): Boolean {
            val value = row.string(1)
            val name = StringBuilder().append(name).append(" ").append(row.string(2))
            val success =
            //
                if (value == X_AD_Tree.TREETYPE_Organization) {
                    val tree = MTree_Base(this, name.toString(), value)
                    val result = tree.save()
                    AD_Tree_Org_ID = tree.aD_Tree_ID
                    result
                } else if (value == X_AD_Tree.TREETYPE_BPartner) {
                    val tree = MTree_Base(this, name.toString(), value)
                    val result = tree.save()
                    AD_Tree_BPartner_ID = tree.aD_Tree_ID
                    result
                } else if (value == X_AD_Tree.TREETYPE_Project) {
                    val tree = MTree_Base(this, name.toString(), value)
                    val result = tree.save()
                    AD_Tree_Project_ID = tree.aD_Tree_ID
                    result
                } else if (value == X_AD_Tree.TREETYPE_SalesRegion) {
                    val tree = MTree_Base(this, name.toString(), value)
                    val result = tree.save()
                    AD_Tree_SalesRegion_ID = tree.aD_Tree_ID
                    result
                } else if (value == X_AD_Tree.TREETYPE_Product) {
                    val tree = MTree_Base(this, name.toString(), value)
                    val result = tree.save()
                    AD_Tree_Product_ID = tree.aD_Tree_ID
                    result
                } else if (value == X_AD_Tree.TREETYPE_ElementValue) {
                    val tree = MTree_Base(this, name.toString(), value)
                    val result = tree.save()
                    m_AD_Tree_Account_ID = tree.aD_Tree_ID
                    result
                } else if (value == X_AD_Tree.TREETYPE_Campaign) {
                    val tree = MTree_Base(this, name.toString(), value)
                    val result = tree.save()
                    AD_Tree_Campaign_ID = tree.aD_Tree_ID
                    result
                } else if (value == X_AD_Tree.TREETYPE_Activity) {
                    val tree = MTree_Base(this, name.toString(), value)
                    val result = tree.save()
                    AD_Tree_Activity_ID = tree.aD_Tree_ID
                    result
                } else if (value == X_AD_Tree.TREETYPE_Menu // 	No Menu

                    || value == X_AD_Tree.TREETYPE_CustomTable // 	No Custom Table

                    || value == X_AD_Tree.TREETYPE_User1 // 	No custom user trees

                    || value == X_AD_Tree.TREETYPE_User2
                    || value == X_AD_Tree.TREETYPE_User3
                    || value == X_AD_Tree.TREETYPE_User4
                )
                    true
                else
                //	PC (Product Category), BB (BOM)
                {
                    val tree = MTree_Base(this, name.toString(), value)
                    tree.save()
                }

            if (!success) {
                log.log(Level.SEVERE, "Tree NOT created: $name")
            }

            return success
        }

        //	Create Trees
        val sql: StringBuilder? =
            if (Env.isBaseLanguage(language, "AD_Ref_List"))
            // 	Get TreeTypes & Name
                StringBuilder(
                    "SELECT Value, Name FROM AD_Ref_List WHERE AD_Reference_ID=120 AND IsActive='Y'"
                )
            else
                StringBuilder("SELECT l.Value, t.Name FROM AD_Ref_List l, AD_Ref_List_Trl t ")
                    .append(
                        "WHERE l.AD_Reference_ID=120 AND l.AD_Ref_List_ID=t.AD_Ref_List_ID AND l.IsActive='Y'"
                    )
                    .append(" AND t.AD_Language=")
                    .append(TO_STRING(language))

        val loadQuery = queryOf(sql.toString(), listOf()).map { mapRow(it) }.asList
        val success = DB.current.run(loadQuery).min() ?: false

        if (!success) return false

        //	Create ClientInfo
        val clientInfo = MClientInfo(
            this,
            AD_Tree_Org_ID,
            AD_Tree_BPartner_ID,
            AD_Tree_Project_ID,
            AD_Tree_SalesRegion_ID,
            AD_Tree_Product_ID,
            AD_Tree_Campaign_ID,
            AD_Tree_Activity_ID,
            null
        )
        val result = clientInfo.save()
        return result
    }

    /**
     * Get AD_Tree_Account_ID created in setup client info
     *
     * @return Account Tree ID
     */
    val setup_AD_Tree_Account_ID get() = m_AD_Tree_Account_ID
}