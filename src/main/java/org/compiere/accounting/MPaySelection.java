package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.orm.Query;
import org.idempiere.common.util.Env;

import java.util.List;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.getSQLValue;

/**
 * AP Payment Selection
 *
 * @author Jorg Janke
 * @version $Id: MPaySelection.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MPaySelection extends X_C_PaySelection {

    /**
     *
     */
    private static final long serialVersionUID = -6521282913549455131L;
    /**
     * Lines
     */
    private MPaySelectionLine[] m_lines = null;
    /**
     * Currency of Bank Account
     */
    private int m_C_Currency_ID = 0;

    /**
     * Default Constructor
     *
     * @param ctx               context
     * @param C_PaySelection_ID id
     * @param trxName           transaction
     */
    public MPaySelection(Properties ctx, int C_PaySelection_ID) {
        super(ctx, C_PaySelection_ID);
        if (C_PaySelection_ID == 0) {
            //	setC_BankAccount_ID (0);
            //	setName (null);	// @#Date@
            //	setPayDate (new Timestamp(System.currentTimeMillis()));	// @#Date@
            setTotalAmt(Env.ZERO);
            setIsApproved(false);
            setProcessed(false);
            setProcessing(false);
        }
    } //	MPaySelection

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MPaySelection(Properties ctx, Row row) {
        super(ctx, row);
    } //	MPaySelection

    /**
     * Get Lines
     *
     * @param requery requery
     * @return lines
     */
    public MPaySelectionLine[] getLines(boolean requery) {
        if (m_lines != null && !requery) {
            return m_lines;
        }
        // FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
        final String whereClause = "C_PaySelection_ID=?";
        List<MPaySelectionLine> list =
                new Query(getCtx(), I_C_PaySelectionLine.Table_Name, whereClause)
                        .setParameters(getC_PaySelection_ID())
                        .setOrderBy("Line")
                        .list();
        //
        m_lines = new MPaySelectionLine[list.size()];
        list.toArray(m_lines);
        return m_lines;
    } //	getLines

    /**
     * Get Currency of Bank Account
     *
     * @return C_Currency_ID
     */
    public int getCurrencyId() {
        if (m_C_Currency_ID == 0) {
            String sql = "SELECT C_Currency_ID FROM C_BankAccount " + "WHERE C_BankAccount_ID=?";
            m_C_Currency_ID = getSQLValue(sql, getC_BankAccount_ID());
        }
        return m_C_Currency_ID;
    } //	getCurrencyId

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MPaySelection[");
        sb.append(getId()).append(",").append(getName()).append("]");
        return sb.toString();
    } //	toString
} //	MPaySelection
