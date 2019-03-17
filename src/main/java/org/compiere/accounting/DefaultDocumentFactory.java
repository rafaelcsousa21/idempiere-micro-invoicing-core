package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.orm.MTable;
import org.idempiere.common.util.AdempiereUserError;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

/**
 * @author hengsin
 */
public class DefaultDocumentFactory extends BaseDefaultDocumentFactory {

    private static final CLogger s_log = CLogger.getCLogger(DefaultDocumentFactory.class);

    @Override
    public Doc getDocument(I_C_AcctSchema as, int AD_Table_ID, Row rs) {
        Doc doc = null;

    /* Classname of the Doc class follows this convention:
    * if the prefix (letters before the first underscore _) is 1 character, then the class is Doc_TableWithoutPrefixWithoutUnderscores
    * otherwise Doc_WholeTableWithoutUnderscores
    * i.e. following this query
               SELECT t.ad_table_id, tablename,
               	CASE
               		WHEN instr(tablename, '_') = 2
               		THEN 'Doc_' || substr(tablename, 3)
               		WHEN instr(tablename, '_') > 2
               		THEN 'Doc_' ||
               		ELSE ''
               	REPLACE
               		(
               			tablename, '_', ''
               		)
               	END AS classname
               FROM ad_table t, ad_column C
               WHERE t.ad_table_id = C.ad_table_id AND
               	C.columnname = 'Posted' AND
               	isview = 'N'
               ORDER BY 1
    * This is:
    * 224		GL_Journal			Doc_GLJournal
    * 259		C_Order				Doc_Order
    * 318		C_Invoice			Doc_Invoice
    * 319		M_InOut				Doc_InOut
    * 321		M_Inventory			Doc_Inventory
    * 323		M_Movement			Doc_Movement
    * 325		M_Production		Doc_Production
    * 335		C_Payment			Doc_Payment
    * 392		C_BankStatement		Doc_BankStatement
    * 407		C_Cash				Doc_Cash
    * 472		M_MatchInv			Doc_MatchInv
    * 473		M_MatchPO			Doc_MatchPO
    * 623		C_ProjectIssue		Doc_ProjectIssue
    * 702		M_Requisition		Doc_Requisition
    * 735		C_AllocationHdr		Doc_AllocationHdr
    * 53027	PP_Order			Doc_PPOrder
    * 53035	PP_Cost_Collector	Doc_PPCostCollector
    * 53037	DD_Order			Doc_DDOrder
    * 53092	HR_Process			Doc_HRProcess
    */

        String tableName = MTable.getDbTableName(Env.getCtx(), AD_Table_ID);
        String packageName = "org.compiere.accounting";
        String className = null;

        int firstUnderscore = tableName.indexOf("_");
        if (firstUnderscore == 1)
            className = packageName + ".Doc_" + tableName.substring(2).replaceAll("_", "");
        else className = packageName + ".Doc_" + tableName.replaceAll("_", "");

        try {
            Class<?> cClass = Class.forName(className);
            Constructor<?> cnstr =
                    cClass.getConstructor(MAcctSchema.class, Row.class);
            doc = (Doc) cnstr.newInstance(as, rs);
        } catch (Exception e) {
            s_log.log(Level.SEVERE, "Doc Class invalid: " + className + " (" + e.toString() + ")");
            throw new AdempiereUserError(
                    "Doc Class invalid: " + className + " (" + e.toString() + ")", e);
        }

        if (doc == null) s_log.log(Level.SEVERE, "Unknown AD_Table_ID=" + AD_Table_ID);
        return doc;
    }
}
