package org.compiere.production;

import org.compiere.accounting.MProduct;
import org.compiere.product.IProductPricing;
import org.compiere.product.MProductCategory;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdate;
import static software.hsharp.core.util.DBKt.getSQLValue;

/**
 * Project Line Model
 *
 * @author Jorg Janke
 * @version $Id: MProjectLine.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MProjectLine extends X_C_ProjectLine {
  /** */
  private static final long serialVersionUID = 2668549463273628848L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param C_ProjectLine_ID id
   * @param trxName transaction
   */
  public MProjectLine(Properties ctx, int C_ProjectLine_ID, String trxName) {
    super(ctx, C_ProjectLine_ID, trxName);
    if (C_ProjectLine_ID == 0) {
      //  setC_Project_ID (0);
      //	setC_ProjectLine_ID (0);
      setLine(0);
      setIsPrinted(true);
      setProcessed(false);
      setInvoicedAmt(Env.ZERO);
      setInvoicedQty(Env.ZERO);
      //
      setPlannedAmt(Env.ZERO);
      setPlannedMarginAmt(Env.ZERO);
      setPlannedPrice(Env.ZERO);
      setPlannedQty(Env.ONE);
    }
  } //	MProjectLine

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MProjectLine(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MProjectLine

  /**
   * Parent Constructor
   *
   * @param project parent
   */
  public MProjectLine(MProject project) {
    this(project.getCtx(), 0, null);
    setClientOrg(project);
    setC_Project_ID(project.getC_Project_ID()); // Parent
    setLine();
  } //	MProjectLine

  /** Parent */
  private MProject m_parent = null;

  /** Get the next Line No */
  private void setLine() {
    setLine(
        getSQLValue(
            "SELECT COALESCE(MAX(Line),0)+10 FROM C_ProjectLine WHERE C_Project_ID=?",
            getC_Project_ID()));
  } //	setLine

  /**
   * Set Product, committed qty, etc.
   *
   * @param pi project issue
   */
  public void setMProjectIssue(MProjectIssue pi) {
    setC_ProjectIssue_ID(pi.getC_ProjectIssue_ID());
    setM_Product_ID(pi.getM_Product_ID());
    setCommittedQty(pi.getMovementQty());
    if (getDescription() != null) setDescription(pi.getDescription());
  } //	setMProjectIssue

  /**
   * Set PO
   *
   * @param C_OrderPO_ID po id
   */
  public void setC_OrderPO_ID(int C_OrderPO_ID) {
    super.setC_OrderPO_ID(C_OrderPO_ID);
  } //	setC_OrderPO_ID

  /**
   * Get Project
   *
   * @return parent
   */
  public MProject getProject() {
    if (m_parent == null && getC_Project_ID() != 0) {
      m_parent = new MProject(getCtx(), getC_Project_ID(), null);
      if (null != null) m_parent.load();
    }
    return m_parent;
  } //	getProject

  /**
   * Get Limit Price if exists
   *
   * @return limit
   */
  public BigDecimal getLimitPrice() {
    BigDecimal limitPrice = getPlannedPrice();
    if (getM_Product_ID() == 0) return limitPrice;
    if (getProject() == null) return limitPrice;
    IProductPricing pp = MProduct.getProductPricing();
    pp.setProjectLine(this, null);
    pp.setM_PriceList_ID(m_parent.getM_PriceList_ID());
    if (pp.calculatePrice()) limitPrice = pp.getPriceLimit();
    return limitPrice;
  } //	getLimitPrice

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("MProjectLine[");
    sb.append(getId())
        .append("-")
        .append(getLine())
        .append(",C_Project_ID=")
        .append(getC_Project_ID())
        .append(",C_ProjectPhase_ID=")
        .append(getC_ProjectPhase_ID())
        .append(",C_ProjectTask_ID=")
        .append(getC_ProjectTask_ID())
        .append(",C_ProjectIssue_ID=")
        .append(getC_ProjectIssue_ID())
        .append(", M_Product_ID=")
        .append(getM_Product_ID())
        .append(", PlannedQty=")
        .append(getPlannedQty())
        .append("]");
    return sb.toString();
  } //	toString

  /**
   * Before Save
   *
   * @param newRecord new
   * @return true
   */
  protected boolean beforeSave(boolean newRecord) {
    if (getLine() == 0) setLine();

    //	Planned Amount
    setPlannedAmt(getPlannedQty().multiply(getPlannedPrice()));

    //	Planned Margin
    if (is_ValueChanged("M_Product_ID")
        || is_ValueChanged("M_Product_Category_ID")
        || is_ValueChanged("PlannedQty")
        || is_ValueChanged("PlannedPrice")) {
      if (getM_Product_ID() != 0) {
        BigDecimal marginEach = getPlannedPrice().subtract(getLimitPrice());
        setPlannedMarginAmt(marginEach.multiply(getPlannedQty()));
      } else if (getM_Product_Category_ID() != 0) {
        MProductCategory category = MProductCategory.get(getCtx(), getM_Product_Category_ID());
        BigDecimal marginEach = category.getPlannedMargin();
        setPlannedMarginAmt(marginEach.multiply(getPlannedQty()));
      }
    }

    //	Phase/Task
    if (is_ValueChanged("C_ProjectTask_ID") && getC_ProjectTask_ID() != 0) {
      MProjectTask pt = new MProjectTask(getCtx(), getC_ProjectTask_ID(), null);
      if (pt == null || pt.getId() == 0) {
        log.warning("Project Task Not Found - ID=" + getC_ProjectTask_ID());
        return false;
      } else setC_ProjectPhase_ID(pt.getC_ProjectPhase_ID());
    }
    if (is_ValueChanged("C_ProjectPhase_ID") && getC_ProjectPhase_ID() != 0) {
      MProjectPhase pp = new MProjectPhase(getCtx(), getC_ProjectPhase_ID(), null);
      if (pp == null || pp.getId() == 0) {
        log.warning("Project Phase Not Found - " + getC_ProjectPhase_ID());
        return false;
      } else setC_Project_ID(pp.getC_Project_ID());
    }

    return true;
  } //	beforeSave

  /**
   * After Save
   *
   * @param newRecord new
   * @param success success
   * @return success
   */
  protected boolean afterSave(boolean newRecord, boolean success) {
    if (!success) return success;
    updateHeader();
    return success;
  } //	afterSave

  /**
   * After Delete
   *
   * @param success success
   * @return success
   */
  protected boolean afterDelete(boolean success) {
    if (!success) return success;
    updateHeader();
    return success;
  } //	afterDelete

  /** Update Header */
  private void updateHeader() {
    String sql =
        "UPDATE C_Project p "
            + "SET (PlannedAmt,PlannedQty,PlannedMarginAmt,"
            + "	CommittedAmt,CommittedQty,"
            + " InvoicedAmt, InvoicedQty) = "
            + "(SELECT COALESCE(SUM(pl.PlannedAmt),0),COALESCE(SUM(pl.PlannedQty),0),COALESCE(SUM(pl.PlannedMarginAmt),0),"
            + " COALESCE(SUM(pl.CommittedAmt),0),COALESCE(SUM(pl.CommittedQty),0),"
            + " COALESCE(SUM(pl.InvoicedAmt),0), COALESCE(SUM(pl.InvoicedQty),0) "
            + "FROM C_ProjectLine pl "
            + "WHERE pl.C_Project_ID=p.C_Project_ID AND pl.IsActive='Y') "
            + "WHERE C_Project_ID="
            + getC_Project_ID();
    int no = executeUpdate(sql);
    if (no != 1) log.log(Level.SEVERE, "updateHeader project - #" + no);
    /*onhate + globalqss BF 3060367*/
    if (getC_ProjectPhase_ID() != 0) {
      sql =
          "UPDATE C_ProjectPhase x SET "
              + "	(PlannedAmt, CommittedAmt) = "
              + "(SELECT "
              + "	COALESCE(SUM(l.PlannedAmt),0), "
              + "	COALESCE(SUM(l.CommittedAmt),0) "
              + "FROM C_ProjectLine l "
              + "WHERE l.C_Project_ID=x.C_Project_ID "
              + "  AND l.C_ProjectPhase_ID=x.C_ProjectPhase_ID "
              + "  AND l.IsActive='Y') "
              + "WHERE x.C_Project_ID="
              + getC_Project_ID()
              + "  AND x.C_ProjectPhase_ID="
              + getC_ProjectPhase_ID();
      no = executeUpdate(sql);
      if (no != 1) log.log(Level.SEVERE, "updateHeader project phase - #" + no);
    }
    if (getC_ProjectTask_ID() != 0) {
      sql =
          "UPDATE C_ProjectTask x SET "
              + "	(PlannedAmt, CommittedAmt) = "
              + "(SELECT "
              + "	COALESCE(SUM(l.PlannedAmt),0), "
              + "	COALESCE(SUM(l.CommittedAmt),0) "
              + "FROM C_ProjectLine l "
              + "WHERE l.C_ProjectPhase_ID=x.C_ProjectPhase_ID "
              + "  AND l.C_ProjectTask_ID=x.C_ProjectTask_ID "
              + "  AND l.IsActive='Y') "
              + "WHERE x.C_ProjectPhase_ID="
              + getC_ProjectPhase_ID()
              + "  AND x.C_ProjectTask_ID="
              + getC_ProjectTask_ID();
      no = executeUpdate(sql);
      if (no != 1) log.log(Level.SEVERE, "updateHeader project task - #" + no);
    }
    /*onhate + globalqss BF 3060367*/
  } // updateHeader
} // MProjectLine
