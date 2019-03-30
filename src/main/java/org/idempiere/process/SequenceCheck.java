package org.idempiere.process;

import org.compiere.accounting.MClient;
import org.compiere.orm.MSequence;
import org.compiere.process.SvrProcess;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.orm.MBaseSequenceKt.doCheckClientSequences;
import static software.hsharp.core.util.DBKt.executeUpdateEx;
import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * System + Document Sequence Check
 *
 * @author Jorg Janke
 * @version $Id: SequenceCheck.java,v 1.3 2006/07/30 00:54:44 jjanke Exp $
 */
public class SequenceCheck extends SvrProcess {
    /**
     * Static Logger
     */
    private static CLogger s_log = CLogger.getCLogger(SequenceCheck.class);

    /**
     * ************************************************************************ Check existence of
     * Table Sequences.
     *
     * @param ctx context
     * @param sp  server process or null
     */
    private static void checkTableSequences(Properties ctx, SvrProcess sp) {

        String sql =
                "SELECT TableName "
                        + "FROM AD_Table t "
                        + "WHERE IsActive='Y' AND IsView='N'"
                        + " AND NOT EXISTS (SELECT * FROM AD_Sequence s "
                        + "WHERE UPPER(s.Name)=UPPER(t.TableName) AND s.IsTableID='Y')";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String tableName = rs.getString(1);
                if (MSequence.createTableSequence(ctx, tableName)) {
                    if (sp != null) sp.addLog(0, null, null, tableName);
                    else s_log.fine(tableName);
                } else {
                    throw new Exception("Error creating Table Sequence for " + tableName);
                }
            }
        } catch (Exception e) {
            s_log.log(Level.SEVERE, sql, e);
            throw new AdempiereException(e);
        }

        //	Sync Table Name case
        sql =
                "UPDATE AD_Sequence s "
                        + "SET Name = (SELECT TableName FROM AD_Table t "
                        + "WHERE t.IsView='N' AND t.IsActive='Y' AND UPPER(s.Name)=UPPER(t.TableName)) "
                        + "WHERE s.IsTableID='Y'"
                        + " AND EXISTS (SELECT * FROM AD_Table t "
                        + "WHERE t.IsActive='Y' AND t.IsView='N'"
                        + " AND UPPER(s.Name)=UPPER(t.TableName) AND s.Name<>t.TableName)";
        int no = executeUpdateEx(sql);
        if (no > 0) {
            if (sp != null) {
                StringBuilder msglog = new StringBuilder("SyncName #").append(no);
                sp.addLog(0, null, null, msglog.toString());
            } else if (s_log.isLoggable(Level.FINE)) s_log.fine("Sync #" + no);
        }
        if (no >= 0) return;

        /* Find Duplicates */
        sql =
                "SELECT TableName, s.Name "
                        + "FROM AD_Table t, AD_Sequence s "
                        + "WHERE t.IsActive='Y' AND t.IsView='N'"
                        + " AND UPPER(s.Name)=UPPER(t.TableName) AND s.Name<>t.TableName";
        //
        try {
            pstmt = prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String TableName = rs.getString(1);
                String SeqName = rs.getString(2);
                StringBuilder msglog =
                        new StringBuilder("ERROR: TableName=")
                                .append(TableName)
                                .append(" - Sequence=")
                                .append(SeqName);
                sp.addLog(0, null, null, msglog.toString());
            }
        } catch (Exception e) {
            s_log.log(Level.SEVERE, sql, e);
            throw new AdempiereException(e);
        }
    } //	checkTableSequences

    /**
     * Check Table Sequence ID values
     *  @param ctx context
     *
     */
    private static void checkTableID(Properties ctx) {
        MBaseSequenceCheckKt.checkTableID(ctx);
    } //	checkTableID

    /**
     * Check/Initialize DocumentNo/Value Sequences for all Clients
     *  @param ctx context
     *
     */
    private static void checkClientSequences(Properties ctx) {
        //	Sequence for DocumentNo/Value
        MClient[] clients = MClient.getAll(ctx);
        for (MClient client : clients) {
            if (!client.isActive()) continue;
            doCheckClientSequences(ctx, client.getClientId());
        } //	for all clients
    } //	checkClientSequences

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
    } // 	prepare

    /**
     * Perform process. (see also MSequenve.validate)
     *
     * @return Message to be translated
     * @throws Exception
     */
    protected String doIt() throws java.lang.Exception {
        log.info("");
        //
        checkTableSequences(Env.getCtx(), this);
        checkTableID(Env.getCtx());
        checkClientSequences(Env.getCtx());
        return "Sequence Check";
    } //	doIt
} //	SequenceCheck
