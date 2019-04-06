package org.idempiere.process;

import org.compiere.accounting.MBankStatement;
import org.compiere.accounting.MBankStatementLine;
import org.compiere.accounting.MPayment;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;

import java.math.BigDecimal;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.getSQLValueEx;

/**
 * Copy BankStatement Lines : - lines without payment only if bank statement is CO/CL (otherwise,
 * line amounts are set to 0) - lines with a payment only if this payment is not on another CO/CL/DR
 * bank statement
 *
 * @author Nicolas Micoud - IDEMPIERE 448
 */
public class CopyFromBankStmt extends SvrProcess {
    private int m_C_BankStatement_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("C_BankStatement_ID"))
                m_C_BankStatement_ID = ((BigDecimal) iProcessInfoParameter.getParameter()).intValue();
            else log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        int To_C_BankStatement_ID = getRecordId();
        if (log.isLoggable(Level.INFO))
            log.info("From C_BankStatement_ID=" + m_C_BankStatement_ID + " to " + To_C_BankStatement_ID);
        if (To_C_BankStatement_ID == 0)
            throw new IllegalArgumentException("Target C_BankStatement_ID == 0");
        if (m_C_BankStatement_ID == 0)
            throw new IllegalArgumentException("Source C_BankStatement_ID == 0");

        MBankStatement from = new MBankStatement(m_C_BankStatement_ID);
        MBankStatement to = new MBankStatement(To_C_BankStatement_ID);
        int no = 0;

        if (!(MBankStatement.DOCSTATUS_Completed.equals(from.getDocStatus())
                || MBankStatement.DOCSTATUS_Closed.equals(from.getDocStatus())))
            throw new IllegalArgumentException("Source must be closed or complete");

        for (MBankStatementLine fromLine : from.getLines(false)) {
            if (!fromLine.isActive()) continue;
            if (fromLine.getPaymentId() > 0) {
                // check if payment is used on another statement
                String sql =
                        "SELECT C_BankStatementLine_ID"
                                + " FROM C_BankStatementLine bsl, C_BankStatement bs"
                                + " WHERE bs.C_BankStatement_ID=bsl.C_BankStatement_ID"
                                + " AND bs.DocStatus IN ('DR', 'CO', 'CL')"
                                + " AND bsl.C_Payment_ID=?";
                if (getSQLValueEx(sql, fromLine.getPaymentId()) < 0) {
                    MBankStatementLine toLine = new MBankStatementLine(to);
                    toLine.setPayment(new MPayment(fromLine.getPaymentId()));
                    toLine.saveEx();
                    no++;
                } else {
                    log.info(
                            "C_BankStatementLine not copied - related to a payment already present in a bank statement");
                }
            } else {
                MBankStatementLine toLine = new MBankStatementLine(to);
                toLine.setCurrencyId(fromLine.getCurrencyId());
                toLine.setChargeId(fromLine.getChargeId());
                toLine.setStmtAmt(fromLine.getStmtAmt());
                toLine.setTrxAmt(fromLine.getTrxAmt());
                toLine.setChargeAmt(fromLine.getChargeAmt());
                toLine.setInterestAmt(fromLine.getInterestAmt());
                toLine.setDescription(fromLine.getDescription());
                toLine.saveEx();
                no++;
            }
        }
        return "@Copied@=" + no;
    } //	doIt
} //	CopyFromBankStmt
