package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.IFact;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Post GL Journal Documents.
 *
 * <pre>
 *  Table:              GL_Journal (224)
 *  Document Types:     GLJ
 *  </pre>
 *
 * @author Jorg Janke
 * @version $Id: Doc_GLJournal.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_GLJournal extends Doc {
    /**
     * Posting Type
     */
    protected String m_PostingType = null;
    protected int m_C_AcctSchema_ID = 0;

    /**
     * Constructor
     *
     * @param as      accounting schema
     * @param rs      record
     * @param trxName trx
     */
    public Doc_GLJournal(MAcctSchema as, Row rs) {
        super(as, MJournal.class, rs, null);
    } //	Doc_GL_Journal

    /**
     * Load Specific Document Details
     *
     * @return error message or null
     */
    protected String loadDocumentDetails() {
        MJournal journal = (MJournal) getPO();
        m_PostingType = journal.getPostingType();
        m_C_AcctSchema_ID = journal.getC_AcctSchema_ID();

        //	Contained Objects
        p_lines = loadLines(journal);
        if (log.isLoggable(Level.FINE)) log.fine("Lines=" + p_lines.length);
        return null;
    } //  loadDocumentDetails

    /**
     * Load Invoice Line
     *
     * @param journal journal
     * @return DocLine Array
     */
    protected DocLine[] loadLines(MJournal journal) {
        ArrayList<DocLine> list = new ArrayList<DocLine>();
        MJournalLine[] lines = journal.getLines(false);
        for (int i = 0; i < lines.length; i++) {
            MJournalLine line = lines[i];
            DocLine docLine = new DocLine(line, this);
            // -- Quantity
            docLine.setQty(line.getQty(), false);
            //  --  Source Amounts
            docLine.setAmount(line.getAmtSourceDr(), line.getAmtSourceCr());
            //  --  Converted Amounts
            docLine.setConvertedAmt(m_C_AcctSchema_ID, line.getAmtAcctDr(), line.getAmtAcctCr());
            //  --  Account
            MAccount account = line.getAccount_Combi();
            docLine.setAccount(account);
            //	--	Organization of Line was set to Org of Account
            list.add(docLine);
        }
        //	Return Array
        int size = list.size();
        DocLine[] dls = new DocLine[size];
        list.toArray(dls);
        return dls;
    } //	loadLines

    /**
     * ************************************************************************ Get Source Currency
     * Balance - subtracts line and tax amounts from total - no rounding
     *
     * @return positive amount, if total invoice is bigger than lines
     */
    public BigDecimal getBalance() {
        BigDecimal retValue = Env.ZERO;
        StringBuilder sb = new StringBuilder(" [");
        //  Lines
        for (int i = 0; i < p_lines.length; i++) {
            retValue = retValue.add(p_lines[i].getAmtSource());
            sb.append("+").append(p_lines[i].getAmtSource());
        }
        sb.append("]");
        //
        if (log.isLoggable(Level.FINE)) log.fine(toString() + " Balance=" + retValue + sb.toString());
        return retValue;
    } //  getBalance

    /**
     * Create Facts (the accounting logic) for GLJ. (only for the accounting scheme, it was created)
     *
     * <pre>
     *      account     DR          CR
     *  </pre>
     *
     * @param as acct schema
     * @return Fact
     */
    public ArrayList<IFact> createFacts(MAcctSchema as) {
        ArrayList<IFact> facts = new ArrayList<IFact>();
        //	Other Acct Schema
        if (as.getAccountingSchemaId() != m_C_AcctSchema_ID) return facts;

        //  create Fact Header
        Fact fact = new Fact(this, as, m_PostingType);

        //  GLJ
        if (getDocumentType().equals(DOCTYPE_GLJournal)) {
            //  account     DR      CR
            for (int i = 0; i < p_lines.length; i++) {
                if (p_lines[i].getC_AcctSchema_ID() == as.getAccountingSchemaId()) {
                    @SuppressWarnings("unused")
                    FactLine line =
                            fact.createLine(
                                    p_lines[i],
                                    p_lines[i].getAccount(),
                                    getCurrencyId(),
                                    p_lines[i].getAmtSourceDr(),
                                    p_lines[i].getAmtSourceCr());
                }
            } //	for all lines
        } else {
            p_Error = "DocumentType unknown: " + getDocumentType();
            log.log(Level.SEVERE, p_Error);
            fact = null;
        }
        //
        facts.add(fact);
        return facts;
    } //  createFact
} //  Doc_GLJournal
