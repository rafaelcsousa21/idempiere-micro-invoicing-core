package org.compiere.accounting;

import org.compiere.model.I_C_OrderLandedCost;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_OrderLandedCost
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_OrderLandedCost extends PO implements I_C_OrderLandedCost, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_OrderLandedCost(Properties ctx, int C_OrderLandedCost_ID) {
        super(ctx, C_OrderLandedCost_ID);
        /**
         * if (C_OrderLandedCost_ID == 0) { setAmt (Env.ZERO); // 0 setC_Order_ID (0);
         * setC_OrderLandedCost_ID (0); setLandedCostDistribution (null); // Q setM_CostElement_ID (0);
         * setProcessed (false); // N }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_OrderLandedCost(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 1 - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    @Override
    public int getTableId() {
        return Table_ID;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer("X_C_OrderLandedCost[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Amount.
     *
     * @return Amount
     */
    public BigDecimal getAmt() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Amt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    public org.compiere.model.I_C_Order getC_Order() throws RuntimeException {
        return (org.compiere.model.I_C_Order)
                MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
                        .getPO(getC_Order_ID());
    }

    /**
     * Get Order.
     *
     * @return Order
     */
    public int getC_Order_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Order_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Estimated Landed Cost.
     *
     * @return Estimated Landed Cost
     */
    public int getC_OrderLandedCost_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_OrderLandedCost_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Cost Distribution.
     *
     * @return Landed Cost Distribution
     */
    public String getLandedCostDistribution() {
        return (String) get_Value(COLUMNNAME_LandedCostDistribution);
    }

    /**
     * Get Cost Element.
     *
     * @return Product Cost Element
     */
    public int getM_CostElement_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_CostElement_ID);
        if (ii == null) return 0;
        return ii;
    }

}
