package org.compiere.invoicing;

import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.close;
import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Landed Cost Allocation Model
 *
 * @author Jorg Janke
 * @version $Id: MLandedCostAllocation.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MLandedCostAllocation extends X_C_LandedCostAllocation {
  /** */
  private static final long serialVersionUID = -8645283018475474574L;

  /**
   * Get Cost Allocations for invoice Line
   *
   * @param ctx context
   * @param C_InvoiceLine_ID invoice line
   * @param trxName trx
   * @return landed cost alloc
   */
  public static MLandedCostAllocation[] getOfInvoiceLine(
      Properties ctx, int C_InvoiceLine_ID, String trxName) {
    ArrayList<MLandedCostAllocation> list = new ArrayList<MLandedCostAllocation>();
    String sql = "SELECT * FROM C_LandedCostAllocation WHERE C_InvoiceLine_ID=?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = prepareStatement(sql, trxName);
      pstmt.setInt(1, C_InvoiceLine_ID);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(new MLandedCostAllocation(ctx, rs, trxName));
    } catch (Exception e) {
      s_log.log(Level.SEVERE, sql, e);
    } finally {
      close(rs, pstmt);
      rs = null;
      pstmt = null;
    }
    MLandedCostAllocation[] retValue = new MLandedCostAllocation[list.size()];
    list.toArray(retValue);
    return retValue;
  } //	getOfInvliceLine

  /** Logger */
  private static CLogger s_log = CLogger.getCLogger(MLandedCostAllocation.class);

  /**
   * ************************************************************************* Standard Constructor
   *
   * @param ctx context
   * @param C_LandedCostAllocation_ID id
   * @param trxName trx
   */
  public MLandedCostAllocation(Properties ctx, int C_LandedCostAllocation_ID, String trxName) {
    super(ctx, C_LandedCostAllocation_ID, trxName);
    if (C_LandedCostAllocation_ID == 0) {
      //	setM_CostElement_ID(0);
      setAmt(Env.ZERO);
      setQty(Env.ZERO);
      setBase(Env.ZERO);
    }
  } //	MLandedCostAllocation

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result name
   * @param trxName trx
   */
  public MLandedCostAllocation(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MLandedCostAllocation

  /**
   * Parent Constructor
   *
   * @param parent parent
   * @param M_CostElement_ID cost element
   */
  public MLandedCostAllocation(MInvoiceLine parent, int M_CostElement_ID) {
    this(parent.getCtx(), 0, parent.get_TrxName());
    setClientOrg(parent);
    setC_InvoiceLine_ID(parent.getC_InvoiceLine_ID());
    setM_CostElement_ID(M_CostElement_ID);
  } //	MLandedCostAllocation

  /**
   * Set Amt
   *
   * @param Amt amount
   * @param precision precision
   */
  public void setAmt(double Amt, int precision) {
    BigDecimal bd = BigDecimal.valueOf(Amt);
    if (bd.scale() > precision) bd = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
    super.setAmt(bd);
  } //	setAmt

  /**
   * Set Allocation Qty (e.g. free products)
   *
   * @param Qty
   */
  public void setQty(BigDecimal Qty) {
    super.setQty(Qty);
  } //	setQty
} //	MLandedCostAllocation
