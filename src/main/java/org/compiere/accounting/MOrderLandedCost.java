package org.compiere.accounting;

import org.compiere.model.I_C_OrderLandedCost;
import org.compiere.model.I_C_OrderLandedCostAllocation;
import org.compiere.orm.Query;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

/**
 * @author hengsin
 */
public class MOrderLandedCost extends X_C_OrderLandedCost {

    /**
     * generated serial id
     */
    private static final long serialVersionUID = 2629138678703667123L;

    /**
     * @param ctx
     * @param C_OrderLandedCost_ID
     * @param trxName
     */
    public MOrderLandedCost(Properties ctx, int C_OrderLandedCost_ID) {
        super(ctx, C_OrderLandedCost_ID);
    }

    /**
     * @param ctx
     * @param rs
     * @param trxName
     */
    public MOrderLandedCost(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * Get allocation
     *
     * @param C_Order_ID
     * @return lines
     */
    public static MOrderLandedCost[] getOfOrder(int C_Order_ID) {
        StringBuilder whereClause =
                new StringBuilder(I_C_OrderLandedCost.COLUMNNAME_C_Order_ID).append("=?");
        List<MOrderLandedCostAllocation> list =
                new Query(Env.getCtx(), I_C_OrderLandedCost.Table_Name, whereClause.toString())
                        .setParameters(C_Order_ID)
                        .list();
        return list.toArray(new MOrderLandedCost[list.size()]);
    } //	getLines

    /**
     * Get Lines of allocation
     *
     * @param whereClause starting with AND
     * @return lines
     */
    public MOrderLandedCostAllocation[] getLines(String whereClause) {
        StringBuilder whereClauseFinal =
                new StringBuilder(I_C_OrderLandedCost.COLUMNNAME_C_OrderLandedCost_ID).append("=?");
        if (!Util.isEmpty(whereClause)) whereClauseFinal.append(" ").append(whereClause);
        List<MOrderLandedCostAllocation> list =
                new Query(
                        getCtx(),
                        I_C_OrderLandedCostAllocation.Table_Name,
                        whereClauseFinal.toString()
                )
                        .setParameters(getC_OrderLandedCost_ID())
                        .list();
        return list.toArray(new MOrderLandedCostAllocation[list.size()]);
    } //	getLines

    public String distributeLandedCost() {
        MOrderLandedCostAllocation[] lines = getLines("");
        if (lines.length == 0) {
            MOrder order = (MOrder) getC_Order();
            MOrderLine[] orderLines = order.getLines();
            if (orderLines.length > 0) {
                List<MOrderLandedCostAllocation> list = new ArrayList<MOrderLandedCostAllocation>();
                for (MOrderLine line : orderLines) {
                    if (line.getM_Product_ID() > 0) {
                        MOrderLandedCostAllocation allocation =
                                new MOrderLandedCostAllocation(getCtx(), 0);
                        allocation.setC_OrderLandedCost_ID(getC_OrderLandedCost_ID());
                        allocation.setC_OrderLine_ID(line.getC_OrderLine_ID());
                        allocation.setClientOrg(getClientId(), getOrgId());
                        allocation.setAmt(BigDecimal.ZERO);
                        allocation.setBase(BigDecimal.ZERO);
                        allocation.setQty(BigDecimal.ZERO);
                        allocation.saveEx();
                        list.add(allocation);
                    }
                }
                if (list.size() > 0) {
                    lines = list.toArray(lines);
                }
            }
        }

        if (lines.length == 1) {
            MOrderLine orderLine = (MOrderLine) lines[0].getC_OrderLine();
            BigDecimal base = orderLine.getBase(getLandedCostDistribution());
            if (base.signum() == 0) {
                StringBuilder msgreturn =
                        new StringBuilder("Total of Base values is 0 - ").append(getLandedCostDistribution());
                return msgreturn.toString();
            }
            lines[0].setBase(base);
            lines[0].setQty(orderLine.getQtyOrdered());
            lines[0].setAmt(getAmt());
            lines[0].saveEx();
        } else if (lines.length > 1) {
            //	Calculate total & base
            BigDecimal total = Env.ZERO;
            for (MOrderLandedCostAllocation allocation : lines) {
                MOrderLine orderLine = (MOrderLine) allocation.getC_OrderLine();
                total = total.add(orderLine.getBase(getLandedCostDistribution()));
            }
            if (total.signum() == 0) {
                StringBuilder msgreturn =
                        new StringBuilder("Total of Base values is 0 - ").append(getLandedCostDistribution());
                return msgreturn.toString();
            }
            //	Create Allocations
            for (MOrderLandedCostAllocation allocation : lines) {
                MOrderLine orderLine = (MOrderLine) allocation.getC_OrderLine();
                BigDecimal base = orderLine.getBase(getLandedCostDistribution());
                allocation.setBase(base);
                allocation.setQty(orderLine.getQtyOrdered());
                // end MZ
                if (base.signum() != 0) {
                    BigDecimal result = getAmt().multiply(base);
                    result =
                            result.divide(
                                    total,
                                    orderLine.getParent().getC_Currency().getCostingPrecision(),
                                    BigDecimal.ROUND_HALF_UP);
                    allocation.setAmt(
                            result.doubleValue(), orderLine.getParent().getC_Currency().getCostingPrecision());
                }
                allocation.saveEx();
            }
            allocateLandedCostRounding(lines);
        }
        return "";
    }

    /**
     * Allocate Landed Cost - Enforce Rounding
     *
     * @param lines
     */
    private void allocateLandedCostRounding(MOrderLandedCostAllocation[] lines) {
        MOrderLandedCostAllocation largestAmtAllocation = null;
        BigDecimal allocationAmt = Env.ZERO;
        for (MOrderLandedCostAllocation allocation : lines) {
            if (largestAmtAllocation == null
                    || allocation.getAmt().compareTo(largestAmtAllocation.getAmt()) > 0)
                largestAmtAllocation = allocation;
            allocationAmt = allocationAmt.add(allocation.getAmt());
        }
        BigDecimal difference = getAmt().subtract(allocationAmt);
        if (difference.signum() != 0) {
            largestAmtAllocation.setAmt(largestAmtAllocation.getAmt().add(difference));
            largestAmtAllocation.saveEx();
            if (log.isLoggable(Level.CONFIG))
                log.config(
                        "Difference="
                                + difference
                                + ", C_OrderLandedCostAllocation_ID="
                                + largestAmtAllocation.getC_OrderLandedCostAllocation_ID()
                                + ", Amt"
                                + largestAmtAllocation.getAmt());
        }
    } //	allocateLandedCostRounding
}
