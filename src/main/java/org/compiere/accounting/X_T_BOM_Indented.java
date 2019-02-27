package org.compiere.accounting;

import org.compiere.model.I_T_BOM_Indented;
import org.compiere.orm.PO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

public class X_T_BOM_Indented extends PO implements I_T_BOM_Indented {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_T_BOM_Indented(Properties ctx, int T_BOM_Indented_ID) {
        super(ctx, T_BOM_Indented_ID);
        /** if (T_BOM_Indented_ID == 0) { setT_BOM_Indented_ID (0); } */
    }

    /**
     * Load Constructor
     */
    public X_T_BOM_Indented(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_T_BOM_Indented[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Set Process Instance.
     *
     * @param AD_PInstance_ID Instance of the process
     */
    public void setAD_PInstance_ID(int AD_PInstance_ID) {
        if (AD_PInstance_ID < 1) setValue(COLUMNNAME_AD_PInstance_ID, null);
        else setValue(COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
    }

    /**
     * Set Accounting Schema.
     *
     * @param C_AcctSchema_ID Rules for accounting
     */
    public void setC_AcctSchema_ID(int C_AcctSchema_ID) {
        if (C_AcctSchema_ID < 1) setValue(COLUMNNAME_C_AcctSchema_ID, null);
        else setValue(COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
    }

    /**
     * Set Cost.
     *
     * @param Cost Cost information
     */
    public void setCost(BigDecimal Cost) {
        setValue(COLUMNNAME_Cost, Cost);
    }

    /**
     * Set Future Cost.
     *
     * @param CostFuture Cost information
     */
    public void setCostFuture(BigDecimal CostFuture) {
        setValue(COLUMNNAME_CostFuture, CostFuture);
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
     * Set Current Cost Price Lower Level.
     *
     * @param CurrentCostPriceLL Current Price Lower Level Is the sum of the costs of the components
     *                           of this product manufactured for this level.
     */
    public void setCurrentCostPriceLL(BigDecimal CurrentCostPriceLL) {
        setValue(COLUMNNAME_CurrentCostPriceLL, CurrentCostPriceLL);
    }

    /**
     * Set Future Cost Price.
     *
     * @param FutureCostPrice Future Cost Price
     */
    public void setFutureCostPrice(BigDecimal FutureCostPrice) {
        setValue(COLUMNNAME_FutureCostPrice, FutureCostPrice);
    }

    /**
     * Set Future Cost Price Lower Level.
     *
     * @param FutureCostPriceLL Future Cost Price Lower Level
     */
    public void setFutureCostPriceLL(BigDecimal FutureCostPriceLL) {
        setValue(COLUMNNAME_FutureCostPriceLL, FutureCostPriceLL);
    }

    /**
     * Set Level no.
     *
     * @param LevelNo Level no
     */
    public void setLevelNo(int LevelNo) {
        setValue(COLUMNNAME_LevelNo, Integer.valueOf(LevelNo));
    }

    /**
     * Set Levels.
     *
     * @param Levels Levels
     */
    public void setLevels(String Levels) {
        setValue(COLUMNNAME_Levels, Levels);
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

    /**
     * Set Product.
     *
     * @param M_Product_ID Product, Service, Item
     */
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) setValue(COLUMNNAME_M_Product_ID, null);
        else setValue(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
    }

    /**
     * Set Quantity.
     *
     * @param Qty Quantity
     */
    public void setQty(BigDecimal Qty) {
        setValue(COLUMNNAME_Qty, Qty);
    }

    /**
     * Set Quantity.
     *
     * @param QtyBOM Indicate the Quantity use in this BOM
     */
    public void setQtyBOM(BigDecimal QtyBOM) {
        setValue(COLUMNNAME_QtyBOM, QtyBOM);
    }

    /**
     * Set Selected Product.
     *
     * @param Sel_Product_ID Selected Product
     */
    public void setSel_Product_ID(int Sel_Product_ID) {
        if (Sel_Product_ID < 1) setValue(COLUMNNAME_Sel_Product_ID, null);
        else setValue(COLUMNNAME_Sel_Product_ID, Integer.valueOf(Sel_Product_ID));
    }

    /**
     * Set Sequence.
     *
     * @param SeqNo Method of ordering records; lowest number comes first
     */
    public void setSeqNo(int SeqNo) {
        setValue(COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
    }

    @Override
    public int getTableId() {
        return I_T_BOM_Indented.Table_ID;
    }
}
