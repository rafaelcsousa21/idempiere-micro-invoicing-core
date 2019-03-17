package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_OrderLandedCostAllocation;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Generated Model for C_OrderLandedCostAllocation
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_OrderLandedCostAllocation extends PO
        implements I_C_OrderLandedCostAllocation {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_OrderLandedCostAllocation(
            Properties ctx, int C_OrderLandedCostAllocation_ID) {
        super(ctx, C_OrderLandedCostAllocation_ID);
    }

    /**
     * Load Constructor
     */
    public X_C_OrderLandedCostAllocation(Properties ctx, Row row) {
        super(ctx, row);
    }

    /**
     * AccessLevel
     *
     * @return 1 - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        return "X_C_OrderLandedCostAllocation[" + getId() + "]";
    }

    /**
     * Get Amount.
     *
     * @return Amount
     */
    public BigDecimal getAmt() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_Amt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Amount.
     *
     * @param Amt Amount
     */
    public void setAmt(BigDecimal Amt) {
        setValue(COLUMNNAME_Amt, Amt);
    }

    /**
     * Set Base.
     *
     * @param Base Calculation Base
     */
    public void setBase(BigDecimal Base) {
        setValue(COLUMNNAME_Base, Base);
    }

    /**
     * Get Estimated Landed Cost Allocation.
     *
     * @return Estimated Landed Cost Allocation
     */
    public int getOrderLandedCostAllocationId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_OrderLandedCostAllocation_ID);
        if (ii == null) return 0;
        return ii;
    }

    public org.compiere.model.I_C_OrderLandedCost getOrderLandedCost() throws RuntimeException {
        return (org.compiere.model.I_C_OrderLandedCost)
                MTable.get(getCtx(), org.compiere.model.I_C_OrderLandedCost.Table_Name)
                        .getPO(getOrderLandedCostId());
    }

    /**
     * Get Estimated Landed Cost.
     *
     * @return Estimated Landed Cost
     */
    public int getOrderLandedCostId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_OrderLandedCost_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Estimated Landed Cost.
     *
     * @param C_OrderLandedCost_ID Estimated Landed Cost
     */
    public void setOrderLandedCostId(int C_OrderLandedCost_ID) {
        if (C_OrderLandedCost_ID < 1) setValueNoCheck(COLUMNNAME_C_OrderLandedCost_ID, null);
        else setValueNoCheck(COLUMNNAME_C_OrderLandedCost_ID, C_OrderLandedCost_ID);
    }

    public org.compiere.model.I_C_OrderLine getOrderLine() throws RuntimeException {
        return (org.compiere.model.I_C_OrderLine)
                MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
                        .getPO(getOrderLineId());
    }

    /**
     * Get Sales Order Line.
     *
     * @return Sales Order Line
     */
    public int getOrderLineId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_OrderLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Sales Order Line.
     *
     * @param C_OrderLine_ID Sales Order Line
     */
    public void setOrderLineId(int C_OrderLine_ID) {
        if (C_OrderLine_ID < 1) setValueNoCheck(COLUMNNAME_C_OrderLine_ID, null);
        else setValueNoCheck(COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
    }

    /**
     * Get Quantity.
     *
     * @return Quantity
     */
    public BigDecimal getQty() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_Qty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Quantity.
     *
     * @param Qty Quantity
     */
    public void setQty(BigDecimal Qty) {
        setValue(COLUMNNAME_Qty, Qty);
    }

    @Override
    public int getTableId() {
        return I_C_OrderLandedCostAllocation.Table_ID;
    }
}
