package org.compiere.accounting;

import org.compiere.model.I_M_CostQueue;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_CostQueue
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_CostQueue extends PO implements I_M_CostQueue {

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
         * if (M_CostQueue_ID == 0) { setAccountingSchemaId (0); setCurrentCostPrice (Env.ZERO);
         * setCurrentQty (Env.ZERO); setM_AttributeSetInstance_ID (0); setM_CostElement_ID (0);
         * setM_CostQueue_ID (0); setCostTypeId (0); setM_Product_ID (0); }
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
     * Set Accounting Schema.
     *
     * @param C_AcctSchema_ID Rules for accounting
     */
    public void setC_AcctSchema_ID(int C_AcctSchema_ID) {
        if (C_AcctSchema_ID < 1) setValueNoCheck(COLUMNNAME_C_AcctSchema_ID, null);
        else setValueNoCheck(COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
    }

    /**
     * Get Current Cost Price.
     *
     * @return The currently used cost price
     */
    public BigDecimal getCurrentCostPrice() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_CurrentCostPrice);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Current Cost Price.
     *
     * @param CurrentCostPrice The currently used cost price
     */
    public void setCurrentCostPrice(BigDecimal CurrentCostPrice) {
        setValue(COLUMNNAME_CurrentCostPrice, CurrentCostPrice);
    }

    /**
     * Get Current Quantity.
     *
     * @return Current Quantity
     */
    public BigDecimal getCurrentQty() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_CurrentQty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Current Quantity.
     *
     * @param CurrentQty Current Quantity
     */
    public void setCurrentQty(BigDecimal CurrentQty) {
        setValue(COLUMNNAME_CurrentQty, CurrentQty);
    }

    /**
     * Set Attribute Set Instance.
     *
     * @param M_AttributeSetInstance_ID Product Attribute Set Instance
     */
    public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID < 0) setValueNoCheck(COLUMNNAME_M_AttributeSetInstance_ID, null);
        else
            setValueNoCheck(
                    COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
    }

    /**
     * Get Attribute Set Instance.
     *
     * @return Product Attribute Set Instance
     */
    public int getMAttributeSetInstance_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_AttributeSetInstance_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Cost Element.
     *
     * @param M_CostElement_ID Product Cost Element
     */
    public void setM_CostElement_ID(int M_CostElement_ID) {
        if (M_CostElement_ID < 1) setValueNoCheck(COLUMNNAME_M_CostElement_ID, null);
        else setValueNoCheck(COLUMNNAME_M_CostElement_ID, Integer.valueOf(M_CostElement_ID));
    }

    /**
     * Set Cost Type.
     *
     * @param M_CostType_ID Type of Cost (e.g. Current, Plan, Future)
     */
    public void setM_CostType_ID(int M_CostType_ID) {
        if (M_CostType_ID < 1) setValueNoCheck(COLUMNNAME_M_CostType_ID, null);
        else setValueNoCheck(COLUMNNAME_M_CostType_ID, Integer.valueOf(M_CostType_ID));
    }

    /**
     * Set Product.
     *
     * @param M_Product_ID Product, Service, Item
     */
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) setValueNoCheck(COLUMNNAME_M_Product_ID, null);
        else setValueNoCheck(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
    }
}
