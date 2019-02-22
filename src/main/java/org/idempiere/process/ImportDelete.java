package org.idempiere.process;

import org.compiere.model.IProcessInfoParameter;
import org.compiere.orm.MTable;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;

import java.math.BigDecimal;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdate;

/**
 * Delete Data in Import Table
 *
 * @author Jorg Janke
 * @version $Id: ImportDelete.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ImportDelete extends SvrProcess {
    /**
     * Table be deleted
     */
    private int p_AD_Table_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (int i = 0; i < para.length; i++) {
            String name = para[i].getParameterName();
            if (name.equals("AD_Table_ID"))
                p_AD_Table_ID = ((BigDecimal) para[i].getParameter()).intValue();
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
    } //	prepare

    /**
     * Perform process.
     *
     * @return clear Message
     * @throws Exception
     */
    protected String doIt() throws Exception {
        StringBuilder msglog = new StringBuilder("AD_Table_ID=").append(p_AD_Table_ID);
        if (log.isLoggable(Level.INFO)) log.info(msglog.toString());
        //	get Table Info
        MTable table = new MTable(getCtx(), p_AD_Table_ID);
        if (table.getId() == 0) {
            StringBuilder msgexc = new StringBuilder("No AD_Table_ID=").append(p_AD_Table_ID);
            throw new IllegalArgumentException(msgexc.toString());
        }
        String tableName = table.getTableName();
        if (!tableName.startsWith("I")) {
            StringBuilder msgexc = new StringBuilder("Not an import table = ").append(tableName);
            throw new IllegalArgumentException(msgexc.toString());
        }

        //	Delete
        StringBuilder sql =
                new StringBuilder("DELETE FROM ")
                        .append(tableName)
                        .append(" WHERE AD_Client_ID=")
                        .append(getClientId());
        int no = executeUpdate(sql.toString());
        StringBuilder msg =
                new StringBuilder()
                        .append(Msg.translate(getCtx(), tableName + "_ID"))
                        .append(" #")
                        .append(no);
        return msg.toString();
    } //	ImportDelete
} //	ImportDelete
