package org.idempiere.process;

import org.compiere.accounting.DocManager;
import org.compiere.accounting.MAcctSchema;
import org.compiere.accounting.MClient;
import org.compiere.accounting.MClientKt;
import org.compiere.accounting.MCost;
import org.compiere.model.ClientWithAccounting;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.AdempiereUserError;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.getSQLValueTS;
import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Client Accounting Processor
 *
 * @author Carlos Ruiz
 */
public class ClientAcctProcessor extends SvrProcess {
    /* The Accounting Schema */
    private int p_C_AcctSchema_ID;
    /* The Table */
    private int p_AD_Table_ID;

    /**
     * Last Summary
     */
    private StringBuffer m_summary = new StringBuffer();
    /**
     * Client info
     */
    private ClientWithAccounting m_client = null;
    /**
     * Accounting Schema
     */
    private MAcctSchema[] m_ass = null;

    /**
     * Prepare
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("C_AcctSchema_ID")) p_C_AcctSchema_ID = iProcessInfoParameter.getParameterAsInt();
            else if (name.equals("AD_Table_ID")) p_AD_Table_ID = iProcessInfoParameter.getParameterAsInt();
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
    } //	prepare

    /**
     * Process
     *
     * @return info
     * @throws Exception
     */
    protected String doIt() throws Exception {
        StringBuilder msglog =
                new StringBuilder("C_AcctSchema_ID=")
                        .append(p_C_AcctSchema_ID)
                        .append(", AD_Table_ID=")
                        .append(p_AD_Table_ID);
        if (log.isLoggable(Level.INFO)) log.info(msglog.toString());

        if (!MClient.Companion.isClientAccounting())
            throw new AdempiereUserError(MsgKt.getMsg("ClientAccountingNotEnabled"));

        m_client = MClientKt.getClientWithAccounting(getClientId());

        if (p_C_AcctSchema_ID == 0) m_ass = MAcctSchema.getClientAcctSchema(getClientId());
        else //	only specific accounting schema
            m_ass = new MAcctSchema[]{new MAcctSchema(p_C_AcctSchema_ID)};

        postSession();
        MCost.create(m_client);

        addLog(m_summary.toString());

        return "@OK@";
    } //	doIt

    /**
     * Post Session
     */
    private void postSession() {
        List<BigDecimal> listProcessedOn = new ArrayList<BigDecimal>();
        listProcessedOn.add(Env.ZERO); // to include potential null values

        // get current time from db
        Timestamp ts = getSQLValueTS("SELECT CURRENT_TIMESTAMP FROM DUAL");

        // go back 2 second to be safe (to avoid posting documents being completed at this precise
        // moment)
        long ms = ts.getTime() - (2 * 1000);
        ts = new Timestamp(ms);
        long mili = ts.getTime();
        BigDecimal value = new BigDecimal(Long.toString(mili));

        // first pass, collect all ts (FR 2962094 - required for weighted average costing)
        int[] documentsTableID = DocManager.INSTANCE.getDocumentsTableID();
        String[] documentsTableName = DocManager.INSTANCE.getDocumentsTableName();
        for (int i = 0; i < documentsTableID.length; i++) {
            int AD_Table_ID = documentsTableID[i];
            String TableName = documentsTableName[i];
            //	Post only special documents
            if (p_AD_Table_ID != 0 && p_AD_Table_ID != AD_Table_ID) continue;

            StringBuilder sql =
                    new StringBuilder("SELECT DISTINCT ProcessedOn FROM ")
                            .append(TableName)
                            .append(" WHERE AD_Client_ID=? AND ProcessedOn<?")
                            .append(" AND Processed='Y' AND Posted='N' AND IsActive='Y'");
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                pstmt = prepareStatement(sql.toString());
                pstmt.setInt(1, getClientId());
                pstmt.setBigDecimal(2, value);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    BigDecimal processedOn = rs.getBigDecimal(1);
                    if (!listProcessedOn.contains(processedOn)) listProcessedOn.add(processedOn);
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, sql.toString(), e);
            }
        }

        // initialize counters per table
        int[] count = new int[documentsTableID.length];
        int[] countError = new int[documentsTableID.length];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
            countError[i] = 0;
        }

        // sort and post in the processed date order
        Collections.sort(listProcessedOn);
        for (BigDecimal processedOn : listProcessedOn) {

            for (int i = 0; i < documentsTableID.length; i++) {
                int AD_Table_ID = documentsTableID[i];
                String TableName = documentsTableName[i];
                //	Post only special documents
                if (p_AD_Table_ID != 0 && p_AD_Table_ID != AD_Table_ID) continue;
                //  SELECT * FROM table
                StringBuilder sql =
                        new StringBuilder("SELECT * FROM ")
                                .append(TableName)
                                .append(" WHERE AD_Client_ID=? AND (ProcessedOn");
                if (processedOn.compareTo(Env.ZERO) != 0) sql.append("=?");
                else sql.append(" IS NULL OR ProcessedOn=0");
                sql.append(") AND Processed='Y' AND Posted='N' AND IsActive='Y'")
                        .append(" ORDER BY Created");
                //

                BaseClientAccountingProcessorKt.postDocumentsInPostSession(
                        sql.toString(), getClientId(), processedOn, m_ass, AD_Table_ID
                );
            } // for tableID
        } // for processedOn

        for (int i = 0; i < documentsTableID.length; i++) {
            String TableName = documentsTableName[i];
            if (count[i] > 0) {
                m_summary.append(TableName).append("=").append(count[i]);
                if (countError[i] > 0) m_summary.append("(Errors=").append(countError[i]).append(")");
                m_summary.append(" - ");
                StringBuilder msglog =
                        new StringBuilder().append(getName()).append(": ").append(m_summary.toString());
                if (log.isLoggable(Level.FINER)) log.finer(msglog.toString());
            } else {
                StringBuilder msglog =
                        new StringBuilder()
                                .append(getName())
                                .append(": ")
                                .append(TableName)
                                .append(" - no work");
                if (log.isLoggable(Level.FINER)) log.finer(msglog.toString());
            }
        }
    } //	postSession
} //	ClientAcctProcessor
