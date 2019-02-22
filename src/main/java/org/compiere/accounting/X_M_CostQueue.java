package org.compiere.accounting;

import org.compiere.model.I_M_CostQueue;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_CostQueue
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_CostQueue extends PO implements I_M_CostQueue, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_CostQueue(Properties ctx, int M_CostQueue_ID) {
        super(ctx, M_CostQueue_ID);
        /**
         * if (M_CostQueue_ID == 0) { setC_AcctSchema_ID (0); setCurrentCostPrice (Env.ZERO);
         * setCurrentQty (Env.ZERO); setM_AttributeSetInstance_ID (0); setM_CostElement_ID (0);
         * setM_CostQueue_ID (0); setM_CostType_ID (0); setM_Product_ID (0); }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_CostQueue(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 3 - Client - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    @Override
    public int getTableId() {
        return Table_ID;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_M_CostQueue[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Accounting Schema.
     *
     * @return Rules for accounting
     */
    public int getC_AcctSchema_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_AcctSchema_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Accounting Schema.
     *
     * @param C_AcctSchema_ID Rules for accounting
     */
    public void setC_AcctSchema_ID(int C_AcctSchema_ID) {
        if (C_AcctSchema_ID < 1) set_ValueNoCheck(COLUMNNAME_C_AcctSchema_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
    }

    /**
     * Get Current Cost Price.
     *
     * @return The currently used cost price
     */
    public BigDecimal getCurrentCostPrice() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_CurrentCostPrice);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Current Cost Price.
     *
     * @param CurrentCostPrice The currently used cost price
     */
    public void setCurrentCostPrice(BigDecimal CurrentCostPrice) {
        set_Value(COLUMNNAME_CurrentCostPrice, CurrentCostPrice);
    }

    /**
     * Get Current Quantity.
     *
     * @return Current Quantity
     */
    public BigDecimal getCurrentQty() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_CurrentQty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Current Quantity.
     *
     * @param CurrentQty Current Quantity
     */
    public void setCurrentQty(BigDecimal CurrentQty) {
        set_Value(COLUMNNAME_CurrentQty, CurrentQty);
    }

    /**
     * Set Attribute Set Instance.
     *
     * @param M_AttributeSetInstance_ID Product Attribute Set Instance
     */
    public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID < 0) set_ValueNoCheck(COLUMNNAME_M_AttributeSetInstance_ID, null);
        else
            set_ValueNoCheck(
                    COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
    }

    /**
     * Get Attribute Set Instance.
     *
     * @return Product Attribute Set Instance
     */
    public int getMAttributeSetInstance_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
        if (ii == null) return 0;
        return ii;
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

    /**
     * Set Cost Element.
     *
     * @param M_CostElement_ID Product Cost Element
     */
    public void setM_CostElement_ID(int M_CostElement_ID) {
        if (M_CostElement_ID < 1) set_ValueNoCheck(COLUMNNAME_M_CostElement_ID, null);
        else set_ValueNoCheck(COLUMNNAME_M_CostElement_ID, Integer.valueOf(M_CostElement_ID));
    }

    /**
     * Get Cost Type.
     *
     * @return Type of Cost (e.g. Current, Plan, Future)
     */
    public int getM_CostType_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_CostType_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Cost Type.
     *
     * @param M_CostType_ID Type of Cost (e.g. Current, Plan, Future)
     */
    public void setM_CostType_ID(int M_CostType_ID) {
        if (M_CostType_ID < 1) set_ValueNoCheck(COLUMNNAME_M_CostType_ID, null);
        else set_ValueNoCheck(COLUMNNAME_M_CostType_ID, Integer.valueOf(M_CostType_ID));
    }

    /**
     * Get Product.
     *
     * @return Product, Service, Item
     */
    public int getM_Product_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_Product_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Product.
     *
     * @param M_Product_ID Product, Service, Item
     */
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) set_ValueNoCheck(COLUMNNAME_M_Product_ID, null);
        else set_ValueNoCheck(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
    }
}
