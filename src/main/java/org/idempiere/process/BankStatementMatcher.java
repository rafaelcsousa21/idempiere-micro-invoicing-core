/**
 * **************************************************************************** Product: Adempiere
 * ERP & CRM Smart Business Solution * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it * under the terms version
 * 2 of the GNU General Public License as published * by the Free Software Foundation. This program
 * is distributed in the hope * that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. * See the GNU General
 * Public License for more details. * You should have received a copy of the GNU General Public
 * License along * with this program; if not, write to the Free Software Foundation, Inc., * 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA. * For the text or an alternative of this
 * public license, you may reach us * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA
 * 95054, USA * or via info@compiere.org or http://www.compiere.org/license.html *
 * ***************************************************************************
 */
package org.idempiere.process;

import org.compiere.accounting.MBankStatement;
import org.compiere.accounting.MBankStatementLine;
import org.compiere.accounting.X_I_BankStatement;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;

import java.util.logging.Level;

/**
 * Bank Statement Matching
 *
 * @author Jorg Janke
 * @version $Id: BankStatementMatcher.java,v 1.3 2006/09/25 00:59:41 jjanke Exp $
 */
public class BankStatementMatcher extends SvrProcess {
    /**
     * Matchers
     */
    MBankStatementMatcher[] m_matchers = null;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (int i = 0; i < para.length; i++) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) ;
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
        m_matchers = MBankStatementMatcher.getMatchers(getCtx());
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        int Table_ID = getTable_ID();
        int Record_ID = getRecord_ID();
        if (m_matchers == null || m_matchers.length == 0)
            throw new IllegalStateException("No Matchers found");
        //
        if (log.isLoggable(Level.INFO))
            log.info(
                    "doIt - Table_ID="
                            + Table_ID
                            + ", Record_ID="
                            + Record_ID
                            + ", Matchers="
                            + m_matchers.length);

        if (Table_ID == X_I_BankStatement.Table_ID)
            return match(new X_I_BankStatement(getCtx(), Record_ID));
        else if (Table_ID == MBankStatement.Table_ID)
            return match(new MBankStatement(getCtx(), Record_ID));
        else if (Table_ID == MBankStatementLine.Table_ID)
            return match(new MBankStatementLine(getCtx(), Record_ID));

        return "??";
    } //	doIt

    /**
     * Perform Match
     *
     * @param ibs import bank statement line
     * @return Message
     */
    private String match(X_I_BankStatement ibs) {
        if (m_matchers == null || ibs == null || ibs.getPaymentId() != 0) return "--";

        if (log.isLoggable(Level.FINE)) log.fine("" + ibs);
        BankStatementMatchInfo info = null;
        for (int i = 0; i < m_matchers.length; i++) {
            if (m_matchers[i].isMatcherValid()) {
                info = m_matchers[i].getMatcher().findMatch(ibs);
                if (info != null && info.isMatched()) {
                    if (info.getPaymentId() > 0) ibs.setPaymentId(info.getPaymentId());
                    if (info.getInvoiceId() > 0) ibs.setInvoiceId(info.getInvoiceId());
                    if (info.getBusinessPartnerId() > 0) ibs.setBusinessPartnerId(info.getBusinessPartnerId());
                    ibs.saveEx();
                    return "OK";
                }
            }
        } //	for all matchers
        return "--";
    } //	match

    /**
     * Perform Match
     *
     * @param bsl bank statement line
     * @return Message
     */
    private String match(MBankStatementLine bsl) {
        if (m_matchers == null || bsl == null || bsl.getPaymentId() != 0) return "--";

        if (log.isLoggable(Level.FINE)) log.fine("match - " + bsl);
        BankStatementMatchInfo info = null;
        for (int i = 0; i < m_matchers.length; i++) {
            if (m_matchers[i].isMatcherValid()) {
                info = m_matchers[i].getMatcher().findMatch(bsl);
                if (info != null && info.isMatched()) {
                    if (info.getPaymentId() > 0) bsl.setPaymentId(info.getPaymentId());
                    if (info.getInvoiceId() > 0) bsl.setInvoiceId(info.getInvoiceId());
                    if (info.getBusinessPartnerId() > 0) bsl.setBusinessPartnerId(info.getBusinessPartnerId());
                    bsl.saveEx();
                    return "OK";
                }
            }
        } //	for all matchers
        return "--";
    } //	match

    /**
     * Perform Match
     *
     * @param bs bank statement
     * @return Message
     */
    private String match(MBankStatement bs) {
        if (m_matchers == null || bs == null) return "--";
        if (log.isLoggable(Level.FINE)) log.fine("match - " + bs);
        int count = 0;
        MBankStatementLine[] lines = bs.getLines(false);
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].getPaymentId() == 0) {
                match(lines[i]);
                count++;
            }
        }
        return String.valueOf(count);
    } //	match
} //	BankStatementMatcher
