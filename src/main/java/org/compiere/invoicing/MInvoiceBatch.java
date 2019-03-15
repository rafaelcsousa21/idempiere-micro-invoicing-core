package org.compiere.invoicing;

import kotliquery.Row;
import org.idempiere.common.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdate;
import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Invoice Batch Header Model
 *
 * @author Jorg Janke
 * @version $Id: MInvoiceBatch.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MInvoiceBatch extends X_C_InvoiceBatch {

    /**
     *
     */
    private static final long serialVersionUID = 3449653049236263604L;
    /**
     * The Lines
     */
    private MInvoiceBatchLine[] m_lines = null;

    /**
     * Standard Constructor
     *
     * @param ctx               context
     * @param C_InvoiceBatch_ID id
     */
    public MInvoiceBatch(Properties ctx, int C_InvoiceBatch_ID) {
        super(ctx, C_InvoiceBatch_ID);
        if (C_InvoiceBatch_ID == 0) {
            //	setDocumentNo (null);
            //	setCurrencyId (0);	// @$C_Currency_ID@
            setControlAmt(Env.ZERO); // 0
            setDateDoc(new Timestamp(System.currentTimeMillis())); // @#Date@
            setDocumentAmt(Env.ZERO);
            setIsSOTrx(false); // N
            setProcessed(false);
            //	setSalesRepresentativeId (0);
        }
    } //	MInvoiceBatch

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MInvoiceBatch(Properties ctx, Row row) {
        super(ctx, row);
    } //	MInvoiceBatch

    /**
     * Get Lines
     *
     * @param reload reload data
     * @return array of lines
     */
    public MInvoiceBatchLine[] getLines(boolean reload) {
        if (m_lines != null && !reload) {
            return m_lines;
        }
        m_lines = MBaseInvoiceBatchKt.getLines(getCtx(), getC_InvoiceBatch_ID());
        return m_lines;
    } //	getLines

    /**
     * Set Processed
     *
     * @param processed processed
     */
    public void setProcessed(boolean processed) {
        super.setProcessed(processed);
        if (getId() == 0) return;
        StringBuilder set =
                new StringBuilder("SET Processed='")
                        .append((processed ? "Y" : "N"))
                        .append("' WHERE C_InvoiceBatch_ID=")
                        .append(getC_InvoiceBatch_ID());
        int noLine = executeUpdate("UPDATE C_InvoiceBatchLine " + set);
        m_lines = null;
        if (log.isLoggable(Level.FINE)) log.fine(processed + " - Lines=" + noLine);
    } //	setProcessed
} //	MInvoiceBatch
