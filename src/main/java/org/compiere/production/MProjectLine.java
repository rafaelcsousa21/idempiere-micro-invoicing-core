package org.compiere.production;

import kotliquery.Row;
import org.compiere.accounting.MProduct;
import org.compiere.model.I_C_ProjectIssue;
import org.compiere.product.IProductPricing;
import org.compiere.product.MProductCategory;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
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
    /**
     *
     */
    private static final long serialVersionUID = 2668549463273628848L;
    /**
     * Parent
     */
    private MProject m_parent = null;

    /**
     * Standard Constructor
     *
     * @param C_ProjectLine_ID id
     */
    public MProjectLine(int C_ProjectLine_ID) {
        super(C_ProjectLine_ID);
        if (C_ProjectLine_ID == 0) {
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
     */
    public MProjectLine(Row row) {
        super(row);
    } //	MProjectLine

    /**
     * Parent Constructor
     *
     * @param project parent
     */
    public MProjectLine(MProject project) {
        this(0);
        setClientOrg(project);
        setProjectId(project.getProjectId()); // Parent
        setLine();
    } //	MProjectLine

    /**
     * Get the next Line No
     */
    private void setLine() {
        setLine(
                getSQLValue(
                        "SELECT COALESCE(MAX(Line),0)+10 FROM C_ProjectLine WHERE C_Project_ID=?",
                        getProjectId()));
    } //	setLine

    /**
     * Set Product, committed qty, etc.
     *
     * @param pi project issue
     */
    public void setMProjectIssue(I_C_ProjectIssue pi) {
        setProjectIssueId(pi.getProjectIssueId());
        setProductId(pi.getProductId());
        setCommittedQty(pi.getMovementQty());
        if (getDescription() != null) setDescription(pi.getDescription());
    } //	setMProjectIssue

    /**
     * Set PO
     *
     * @param C_OrderPO_ID po id
     */
    public void setOrderPOId(int C_OrderPO_ID) {
        super.setOrderPOId(C_OrderPO_ID);
    } //	setOrderPOId

    /**
     * Get Project
     *
     * @return parent
     */
    public MProject getProject() {
        if (m_parent == null && getProjectId() != 0) {
            m_parent = new MProject(getProjectId());
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
        if (getProductId() == 0) return limitPrice;
        if (getProject() == null) return limitPrice;
        IProductPricing pp = MProduct.getProductPricing();
        pp.setProjectLine(this);
        pp.setPriceListId(m_parent.getPriceListId());
        if (pp.calculatePrice()) limitPrice = pp.getPriceLimit();
        return limitPrice;
    } //	getLimitPrice

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        return "MProjectLine[" + getId() +
                "-" +
                getLine() +
                ",C_Project_ID=" +
                getProjectId() +
                ",C_ProjectPhase_ID=" +
                getProjectPhaseId() +
                ",C_ProjectTask_ID=" +
                getProjectTaskId() +
                ",C_ProjectIssue_ID=" +
                getProjectIssueId() +
                ", M_Product_ID=" +
                getProductId() +
                ", PlannedQty=" +
                getPlannedQty() +
                "]";
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
        if (isValueChanged("M_Product_ID")
                || isValueChanged("M_Product_Category_ID")
                || isValueChanged("PlannedQty")
                || isValueChanged("PlannedPrice")) {
            if (getProductId() != 0) {
                BigDecimal marginEach = getPlannedPrice().subtract(getLimitPrice());
                setPlannedMarginAmt(marginEach.multiply(getPlannedQty()));
            } else if (getProductCategoryId() != 0) {
                MProductCategory category = MProductCategory.get(getProductCategoryId());
                BigDecimal marginEach = category.getPlannedMargin();
                setPlannedMarginAmt(marginEach.multiply(getPlannedQty()));
            }
        }

        //	Phase/Task
        if (isValueChanged("C_ProjectTask_ID") && getProjectTaskId() != 0) {
            MProjectTask pt = new MProjectTask(getProjectTaskId());
            if (pt == null || pt.getId() == 0) {
                log.warning("Project Task Not Found - ID=" + getProjectTaskId());
                return false;
            } else setProjectPhaseId(pt.getProjectPhaseId());
        }
        if (isValueChanged("C_ProjectPhase_ID") && getProjectPhaseId() != 0) {
            MProjectPhase pp = new MProjectPhase(getProjectPhaseId());
            if (pp == null || pp.getId() == 0) {
                log.warning("Project Phase Not Found - " + getProjectPhaseId());
                return false;
            } else setProjectId(pp.getProjectId());
        }

        return true;
    } //	beforeSave

    /**
     * After Save
     *
     * @param newRecord new
     * @param success   success
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

    /**
     * Update Header
     */
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
                        + getProjectId();
        int no = executeUpdate(sql);
        if (no != 1) log.log(Level.SEVERE, "updateHeader project - #" + no);
        /*onhate + globalqss BF 3060367*/
        if (getProjectPhaseId() != 0) {
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
                            + getProjectId()
                            + "  AND x.C_ProjectPhase_ID="
                            + getProjectPhaseId();
            no = executeUpdate(sql);
            if (no != 1) log.log(Level.SEVERE, "updateHeader project phase - #" + no);
        }
        if (getProjectTaskId() != 0) {
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
                            + getProjectPhaseId()
                            + "  AND x.C_ProjectTask_ID="
                            + getProjectTaskId();
            no = executeUpdate(sql);
            if (no != 1) log.log(Level.SEVERE, "updateHeader project task - #" + no);
        }
        /*onhate + globalqss BF 3060367*/
    } // updateHeader
} // MProjectLine
