package org.compiere.invoicing;

import kotliquery.Row;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Landed Cost Allocation Model
 *
 * @author Jorg Janke
 * @version $Id: MLandedCostAllocation.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MLandedCostAllocation extends X_C_LandedCostAllocation {
    /**
     *
     */
    private static final long serialVersionUID = -8645283018475474574L;
    /**
     * Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MLandedCostAllocation.class);

    /**
     * ************************************************************************* Standard Constructor
     *
     * @param ctx                       context
     * @param C_LandedCostAllocation_ID id
     */
    public MLandedCostAllocation(Properties ctx, int C_LandedCostAllocation_ID) {
        super(ctx, C_LandedCostAllocation_ID);
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
     */
    public MLandedCostAllocation(Properties ctx, Row row) {
        super(ctx, row);
    } //	MLandedCostAllocation

    /**
     * Parent Constructor
     *
     * @param parent           parent
     * @param M_CostElement_ID cost element
     */
    public MLandedCostAllocation(MInvoiceLine parent, int M_CostElement_ID) {
        this(parent.getCtx(), 0);
        setClientOrg(parent);
        setC_InvoiceLine_ID(parent.getC_InvoiceLine_ID());
        setM_CostElement_ID(M_CostElement_ID);
    } //	MLandedCostAllocation

    /**
     * Get Cost Allocations for invoice Line
     *
     * @param ctx              context
     * @param C_InvoiceLine_ID invoice line
     * @return landed cost alloc
     */
    public static MLandedCostAllocation[] getOfInvoiceLine(
            Properties ctx, int C_InvoiceLine_ID) {
        return MBaseLandedCostAllocationKt.getCostAllocationsforInvoiceLine(ctx, C_InvoiceLine_ID);
    } //	getOfInvliceLine

    /**
     * Set Amt
     *
     * @param Amt       amount
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
