package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_OrderLandedCost;
import org.compiere.model.I_C_OrderLandedCostAllocation;
import org.compiere.model.I_C_OrderLine;
import org.compiere.orm.Query;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
     * @param C_OrderLandedCost_ID
     */
    public MOrderLandedCost(int C_OrderLandedCost_ID) {
        super(C_OrderLandedCost_ID);
    }

    /**
     */
    public MOrderLandedCost(Row row) {
        super(row);
    }

    /**
     * Get allocation
     *
     * @param C_Order_ID
     * @return lines
     */
    public static I_C_OrderLandedCost[] getOfOrder(int C_Order_ID) {
        List<I_C_OrderLandedCost> list =
                new Query(I_C_OrderLandedCost.Table_Name, I_C_OrderLandedCost.COLUMNNAME_C_Order_ID + "=?")
                        .setParameters(C_Order_ID)
                        .list();
        return list.toArray(new I_C_OrderLandedCost[0]);
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
                        I_C_OrderLandedCostAllocation.Table_Name,
                        whereClauseFinal.toString()
                )
                        .setParameters(getOrderLandedCostId())
                        .list();
        return list.toArray(new MOrderLandedCostAllocation[0]);
    } //	getLines

    public String distributeLandedCost() {
        MOrderLandedCostAllocation[] lines = getLines("");
        if (lines.length == 0) {
            MOrder order = (MOrder) getOrder();
            I_C_OrderLine[] orderLines = order.getLines().toArray(new I_C_OrderLine[0]);
            if (orderLines.length > 0) {
                List<MOrderLandedCostAllocation> list = new ArrayList<>();
                for (I_C_OrderLine line : orderLines) {
                    if (line.getProductId() > 0) {
                        MOrderLandedCostAllocation allocation =
                                new MOrderLandedCostAllocation(0);
                        allocation.setOrderLandedCostId(getOrderLandedCostId());
                        allocation.setOrderLineId(line.getOrderLineId());
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
            MOrderLine orderLine = (MOrderLine) lines[0].getOrderLine();
            BigDecimal base = orderLine.getBase(getLandedCostDistribution());
            if (base.signum() == 0) {
                return "Total of Base values is 0 - " + getLandedCostDistribution();
            }
            lines[0].setBase(base);
            lines[0].setQty(orderLine.getQtyOrdered());
            lines[0].setAmt(getAmt());
            lines[0].saveEx();
        } else if (lines.length > 1) {
            //	Calculate total & base
            BigDecimal total = Env.ZERO;
            for (MOrderLandedCostAllocation allocation : lines) {
                MOrderLine orderLine = (MOrderLine) allocation.getOrderLine();
                total = total.add(orderLine.getBase(getLandedCostDistribution()));
            }
            if (total.signum() == 0) {
                return "Total of Base values is 0 - " + getLandedCostDistribution();
            }
            //	Create Allocations
            for (MOrderLandedCostAllocation allocation : lines) {
                MOrderLine orderLine = (MOrderLine) allocation.getOrderLine();
                BigDecimal base = orderLine.getBase(getLandedCostDistribution());
                allocation.setBase(base);
                allocation.setQty(orderLine.getQtyOrdered());
                // end MZ
                if (base.signum() != 0) {
                    BigDecimal result = getAmt().multiply(base);
                    result =
                            result.divide(
                                    total,
                                    orderLine.getParent().getCurrency().getCostingPrecision(),
                                    BigDecimal.ROUND_HALF_UP);
                    allocation.setAmt(
                            result.doubleValue(), orderLine.getParent().getCurrency().getCostingPrecision());
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
                                + largestAmtAllocation.getOrderLandedCostAllocationId()
                                + ", Amt"
                                + largestAmtAllocation.getAmt());
        }
    } //	allocateLandedCostRounding
}
