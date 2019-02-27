package org.compiere.accounting;

import org.compiere.model.I_M_CostHistory;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_CostHistory
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_CostHistory extends PO implements I_M_CostHistory {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_CostHistory(Properties ctx, int M_CostHistory_ID) {
        super(ctx, M_CostHistory_ID);
        /**
         * if (M_CostHistory_ID == 0) { setM_AttributeSetInstance_ID (0); setM_CostDetail_ID (0);
         * setM_CostElement_ID (0); setM_CostHistory_ID (0); setM_CostHistory_UU (null);
         * setCostTypeId (0); setNewCostPrice (Env.ZERO); setNewQty (Env.ZERO); setOldCostPrice
         * (Env.ZERO); setOldQty (Env.ZERO); }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_CostHistory(Properties ctx, ResultSet rs) {
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

    public String toString() {
        StringBuffer sb = new StringBuffer("X_M_CostHistory[").append(getId()).append("]");
        return sb.toString();
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
     * Set Cost Detail.
     *
     * @param M_CostDetail_ID Cost Detail Information
     */
    public void setM_CostDetail_ID(int M_CostDetail_ID) {
        if (M_CostDetail_ID < 1) setValueNoCheck(COLUMNNAME_M_CostDetail_ID, null);
        else setValueNoCheck(COLUMNNAME_M_CostDetail_ID, Integer.valueOf(M_CostDetail_ID));
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
     * Set New Accumulated Amt.
     *
     * @param NewCAmt Accumulated Amt after processing of M_CostDetail
     */
    public void setNewCAmt(BigDecimal NewCAmt) {
        setValueNoCheck(COLUMNNAME_NewCAmt, NewCAmt);
    }

    /**
     * Get New Cost Price.
     *
     * @return New current cost price after processing of M_CostDetail
     */
    public BigDecimal getNewCostPrice() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_NewCostPrice);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set New Cost Price.
     *
     * @param NewCostPrice New current cost price after processing of M_CostDetail
     */
    public void setNewCostPrice(BigDecimal NewCostPrice) {
        setValueNoCheck(COLUMNNAME_NewCostPrice, NewCostPrice);
    }

    /**
     * Set New Accumulated Qty.
     *
     * @param NewCQty New Accumulated Qty after processing of M_CostDetail
     */
    public void setNewCQty(BigDecimal NewCQty) {
        setValueNoCheck(COLUMNNAME_NewCQty, NewCQty);
    }

    /**
     * Get New Current Quantity.
     *
     * @return New current quantity after processing of M_CostDetail
     */
    public BigDecimal getNewQty() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_NewQty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set New Current Quantity.
     *
     * @param NewQty New current quantity after processing of M_CostDetail
     */
    public void setNewQty(BigDecimal NewQty) {
        setValueNoCheck(COLUMNNAME_NewQty, NewQty);
    }

    /**
     * Get Old Accumulated Amt.
     *
     * @return Old accumulated amt before the processing of M_CostDetail
     */
    public BigDecimal getOldCAmt() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_OldCAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Old Accumulated Amt.
     *
     * @param OldCAmt Old accumulated amt before the processing of M_CostDetail
     */
    public void setOldCAmt(BigDecimal OldCAmt) {
        setValueNoCheck(COLUMNNAME_OldCAmt, OldCAmt);
    }

    /**
     * Get Old Current Cost Price.
     *
     * @return Old current cost price before the processing of M_CostDetail
     */
    public BigDecimal getOldCostPrice() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_OldCostPrice);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Old Current Cost Price.
     *
     * @param OldCostPrice Old current cost price before the processing of M_CostDetail
     */
    public void setOldCostPrice(BigDecimal OldCostPrice) {
        setValueNoCheck(COLUMNNAME_OldCostPrice, OldCostPrice);
    }

    /**
     * Get Old Accumulated Qty.
     *
     * @return Old accumulated qty before the processing of M_CostDetail
     */
    public BigDecimal getOldCQty() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_OldCQty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Old Accumulated Qty.
     *
     * @param OldCQty Old accumulated qty before the processing of M_CostDetail
     */
    public void setOldCQty(BigDecimal OldCQty) {
        setValueNoCheck(COLUMNNAME_OldCQty, OldCQty);
    }

    /**
     * Get Old Current Quantity.
     *
     * @return Old current quantity before the processing of M_CostDetail
     */
    public BigDecimal getOldQty() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_OldQty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Old Current Quantity.
     *
     * @param OldQty Old current quantity before the processing of M_CostDetail
     */
    public void setOldQty(BigDecimal OldQty) {
        setValueNoCheck(COLUMNNAME_OldQty, OldQty);
    }

    public void setClientOrg(int a, int b) {
        super.setClientOrg(a, b);
    }

    @Override
    public int getTableId() {
        return I_M_CostHistory.Table_ID;
    }
}
