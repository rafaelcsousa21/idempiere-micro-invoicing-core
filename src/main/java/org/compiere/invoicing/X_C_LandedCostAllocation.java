package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.model.I_C_LandedCostAllocation;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Generated Model for C_LandedCostAllocation
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_LandedCostAllocation extends PO implements I_C_LandedCostAllocation {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_LandedCostAllocation(Properties ctx, int C_LandedCostAllocation_ID) {
        super(ctx, C_LandedCostAllocation_ID);
        /**
         * if (C_LandedCostAllocation_ID == 0) { setAmt (Env.ZERO); setBase (Env.ZERO);
         * setC_InvoiceLine_ID (0); setC_LandedCostAllocation_ID (0); setM_CostElement_ID (0);
         * setM_Product_ID (0); setQty (Env.ZERO); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_LandedCostAllocation(Properties ctx, Row row) {
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
        StringBuffer sb = new StringBuffer("X_C_LandedCostAllocation[").append(getId()).append("]");
        return sb.toString();
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
     * Get Base.
     *
     * @return Calculation Base
     */
    public BigDecimal getBase() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_Base);
        if (bd == null) return Env.ZERO;
        return bd;
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
     * Set Invoice Line.
     *
     * @param C_InvoiceLine_ID Invoice Detail Line
     */
    public void setC_InvoiceLine_ID(int C_InvoiceLine_ID) {
        if (C_InvoiceLine_ID < 1) setValueNoCheck(COLUMNNAME_C_InvoiceLine_ID, null);
        else setValueNoCheck(COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
    }

    /**
     * Get Landed Cost Allocation.
     *
     * @return Allocation for Land Costs
     */
    public int getC_LandedCostAllocation_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_LandedCostAllocation_ID);
        if (ii == null) return 0;
        return ii;
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
     * Get Cost Element.
     *
     * @return Product Cost Element
     */
    public int getM_CostElement_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_CostElement_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Cost Element.
     *
     * @param M_CostElement_ID Product Cost Element
     */
    public void setM_CostElement_ID(int M_CostElement_ID) {
        if (M_CostElement_ID < 1) setValue(COLUMNNAME_M_CostElement_ID, null);
        else setValue(COLUMNNAME_M_CostElement_ID, Integer.valueOf(M_CostElement_ID));
    }

    public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException {
        return (org.compiere.model.I_M_InOutLine)
                MTable.get(getCtx(), org.compiere.model.I_M_InOutLine.Table_Name)
                        .getPO(getM_InOutLine_ID());
    }

    /**
     * Get Shipment/Receipt Line.
     *
     * @return Line on Shipment or Receipt document
     */
    public int getM_InOutLine_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_InOutLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Shipment/Receipt Line.
     *
     * @param M_InOutLine_ID Line on Shipment or Receipt document
     */
    public void setM_InOutLine_ID(int M_InOutLine_ID) {
        if (M_InOutLine_ID < 1) setValueNoCheck(COLUMNNAME_M_InOutLine_ID, null);
        else setValueNoCheck(COLUMNNAME_M_InOutLine_ID, M_InOutLine_ID);
    }

    /**
     * Get Product.
     *
     * @return Product, Service, Item
     */
    public int getM_Product_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_Product_ID);
        if (ii == null) return 0;
        return ii;
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
        return I_C_LandedCostAllocation.Table_ID;
    }
}
