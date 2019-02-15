package org.compiere.invoicing;

import org.idempiere.common.util.Env;
import org.idempiere.orm.PO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.*;

/**
 * Invoice Batch Header Model
 *
 * @author Jorg Janke
 * @version $Id: MInvoiceBatch.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MInvoiceBatch extends X_C_InvoiceBatch {

  /** */
  private static final long serialVersionUID = 3449653049236263604L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param C_InvoiceBatch_ID id
   * @param trxName trx
   */
  public MInvoiceBatch(Properties ctx, int C_InvoiceBatch_ID) {
    super(ctx, C_InvoiceBatch_ID);
    if (C_InvoiceBatch_ID == 0) {
      //	setDocumentNo (null);
      //	setC_Currency_ID (0);	// @$C_Currency_ID@
      setControlAmt(Env.ZERO); // 0
      setDateDoc(new Timestamp(System.currentTimeMillis())); // @#Date@
      setDocumentAmt(Env.ZERO);
      setIsSOTrx(false); // N
      setProcessed(false);
      //	setSalesRep_ID (0);
    }
  } //	MInvoiceBatch

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName trx
   */
  public MInvoiceBatch(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MInvoiceBatch

  /** The Lines */
  private MInvoiceBatchLine[] m_lines = null;

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
    String sql = "SELECT * FROM C_InvoiceBatchLine WHERE C_InvoiceBatch_ID=? ORDER BY Line";
    ArrayList<MInvoiceBatchLine> list = new ArrayList<MInvoiceBatchLine>();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = prepareStatement(sql);
      pstmt.setInt(1, getC_InvoiceBatch_ID());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        list.add(new MInvoiceBatchLine(getCtx(), rs));
      }
    } catch (Exception e) {
      log.log(Level.SEVERE, sql, e);
    } finally {

      rs = null;
      pstmt = null;
    }
    //
    m_lines = new MInvoiceBatchLine[list.size()];
    list.toArray(m_lines);
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
