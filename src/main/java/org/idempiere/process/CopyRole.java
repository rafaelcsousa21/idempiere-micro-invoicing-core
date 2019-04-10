package org.idempiere.process;

import org.compiere.model.IProcessInfoParameter;
import org.compiere.model.I_AD_Role_Included;
import org.compiere.orm.MRoleKt;
import org.compiere.process.SvrProcess;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;

import static software.hsharp.core.util.DBKt.executeUpdateEx;

/**
 * Copy role access records
 *
 * @author Robert Klein @ author Paul Bowden
 * @version $Id: CopyRole.java,v 1.0$
 */
public class CopyRole extends SvrProcess {
    private int m_AD_Role_ID_From = 0;
    private int m_AD_Role_ID_To = 0;
    private int m_AD_Client_ID = 0;
    private int m_AD_Org_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {

        IProcessInfoParameter[] para = getParameter();
        for (int i = 0; i < para.length; i++) {
            String name = para[i].getParameterName();

            if (name.equals("AD_Role_ID") && i == 0) m_AD_Role_ID_From = para[i].getParameterAsInt();
            else if (name.equals("AD_Role_ID") && i == 1) m_AD_Role_ID_To = para[i].getParameterAsInt();
            else if (name.equals("AD_Client_ID")) m_AD_Client_ID = para[i].getParameterAsInt();
            else if (name.equals("AD_Org_ID")) m_AD_Org_ID = para[i].getParameterAsInt();
        }
    } //	prepare

    /**
     * Copy the role access records
     *
     * @return info
     * @throws Exception
     */
    protected String doIt() throws Exception {
        if (!MRoleKt.getDefaultRole().isAccessAdvanced()) {
            return "@Error@ @Advanced@ @Process@";
        }

        if (m_AD_Role_ID_From == m_AD_Role_ID_To)
            throw new AdempiereException("Roles must be different");

        String[] tables =
                new String[]{
                        "AD_Window_Access",
                        "AD_Process_Access",
                        "AD_Form_Access",
                        "AD_Workflow_Access",
                        "AD_Task_Access",
                        "AD_Document_Action_Access",
                        "AD_InfoWindow_Access",
                        I_AD_Role_Included.Table_Name
                };
        String[] keycolumns =
                new String[]{
                        "AD_Window_ID",
                        "AD_Process_ID",
                        "AD_Form_ID",
                        "AD_Workflow_ID",
                        "AD_Task_ID",
                        "C_DocType_ID, AD_Ref_List_ID",
                        "AD_InfoWindow_ID",
                        I_AD_Role_Included.COLUMNNAME_Included_Role_ID
                };

        int action = 0;

        for (int i = 0; i < tables.length; i++) {
            String table = tables[i];
            String keycolumn = keycolumns[i];

            StringBuilder sql =
                    new StringBuilder("DELETE FROM ")
                            .append(table)
                            .append(" WHERE AD_Role_ID = ")
                            .append(m_AD_Role_ID_To);
            int no = executeUpdateEx(sql.toString());
            addLog(action++, null, BigDecimal.valueOf(no), "Old records deleted from " + table);

            final boolean column_IsReadWrite =
                    !table.equals("AD_Document_Action_Access")
                            && !table.equals("AD_InfoWindow_Access")
                            && !table.equals(I_AD_Role_Included.Table_Name);
            final boolean column_SeqNo = table.equals(I_AD_Role_Included.Table_Name);

            sql =
                    new StringBuilder("INSERT INTO ")
                            .append(table)
                            .append(" (AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, ")
                            .append("AD_Role_ID, ")
                            .append(keycolumn)
                            .append(", isActive");
            if (column_SeqNo) sql.append(", SeqNo ");
            if (column_IsReadWrite) sql.append(", isReadWrite) ");
            else sql.append(") ");
            sql.append("SELECT ")
                    .append(m_AD_Client_ID)
                    .append(", ")
                    .append(m_AD_Org_ID)
                    .append(", SYSDATE, ")
                    .append(Env.getUserId())
                    .append(", SYSDATE, ")
                    .append(Env.getUserId())
                    .append(", ")
                    .append(m_AD_Role_ID_To)
                    .append(", ")
                    .append(keycolumn)
                    .append(", IsActive ");
            if (column_SeqNo) sql.append(", SeqNo ");
            if (column_IsReadWrite) sql.append(", isReadWrite ");
            sql.append("FROM ").append(table).append(" WHERE AD_Role_ID = ").append(m_AD_Role_ID_From);

            no = executeUpdateEx(sql.toString());

            addLog(action++, null, new BigDecimal(no), "New records inserted into " + table);
        }

        return "Role copied";
    } //	doIt
} //	CopyRole
