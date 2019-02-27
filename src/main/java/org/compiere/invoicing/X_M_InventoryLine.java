package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_InventoryLine
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_InventoryLine extends PO implements I_M_InventoryLine {

    /**
     * Inventory Difference = D
     */
    public static final String INVENTORYTYPE_InventoryDifference = "D";
    /**
     * Charge Account = C
     */
    public static final String INVENTORYTYPE_ChargeAccount = "C";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_InventoryLine(Properties ctx, int M_InventoryLine_ID) {
        super(ctx, M_InventoryLine_ID);
        /**
         * if (M_InventoryLine_ID == 0) { setInventoryType (null); // D setM_AttributeSetInstance_ID
         * (0); setM_Inventory_ID (0); setM_InventoryLine_ID (0); setM_Product_ID (0); setProcessed
         * (false); setQtyBook (Env.ZERO); setQtyCount (Env.ZERO); setQtyCsv (Env.ZERO); }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_InventoryLine(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    public X_M_InventoryLine(Properties ctx, Row row) {
        super(ctx, row);
    } //	MInventoryLine

    /**
     * AccessLevel
     *
     * @return 1 - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_M_InventoryLine[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Charge.
     *
     * @return Additional document charges
     */
    public int getChargeId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Charge_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Charge.
     *
     * @param C_Charge_ID Additional document charges
     */
    public void setChargeId(int C_Charge_ID) {
        if (C_Charge_ID < 1) setValue(COLUMNNAME_C_Charge_ID, null);
        else setValue(COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
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
        setValueNoCheck(COLUMNNAME_CurrentCostPrice, CurrentCostPrice);
    }

    /**
     * Get Description.
     *
     * @return Optional short description of the record
     */
    public String getDescription() {
        return (String) getValue(COLUMNNAME_Description);
    }

    /**
     * Set Description.
     *
     * @param Description Optional short description of the record
     */
    public void setDescription(String Description) {
        setValue(COLUMNNAME_Description, Description);
    }

    /**
     * Get Inventory Type.
     *
     * @return Type of inventory difference
     */
    public String getInventoryType() {
        return (String) getValue(COLUMNNAME_InventoryType);
    }

    /**
     * Set Inventory Type.
     *
     * @param InventoryType Type of inventory difference
     */
    public void setInventoryType(String InventoryType) {

        setValue(COLUMNNAME_InventoryType, InventoryType);
    }

    /**
     * Get Line No.
     *
     * @return Unique line for this document
     */
    public int getLine() {
        Integer ii = (Integer) getValue(COLUMNNAME_Line);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Line No.
     *
     * @param Line Unique line for this document
     */
    public void setLine(int Line) {
        setValue(COLUMNNAME_Line, Integer.valueOf(Line));
    }

    /**
     * Set Attribute Set Instance.
     *
     * @param M_AttributeSetInstance_ID Product Attribute Set Instance
     */
    public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID < 0) setValue(COLUMNNAME_M_AttributeSetInstance_ID, null);
        else
            setValue(COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
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

    public org.compiere.model.I_M_Inventory getM_Inventory() throws RuntimeException {
        return (org.compiere.model.I_M_Inventory)
                MTable.get(getCtx(), org.compiere.model.I_M_Inventory.Table_Name)
                        .getPO(getM_Inventory_ID());
    }

    /**
     * Get Phys.Inventory.
     *
     * @return Parameters for a Physical Inventory
     */
    public int getM_Inventory_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_Inventory_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Phys.Inventory.
     *
     * @param M_Inventory_ID Parameters for a Physical Inventory
     */
    public void setM_Inventory_ID(int M_Inventory_ID) {
        if (M_Inventory_ID < 1) setValueNoCheck(COLUMNNAME_M_Inventory_ID, null);
        else setValueNoCheck(COLUMNNAME_M_Inventory_ID, Integer.valueOf(M_Inventory_ID));
    }

    /**
     * Get Phys.Inventory Line.
     *
     * @return Unique line in an Inventory document
     */
    public int getM_InventoryLine_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_InventoryLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Locator.
     *
     * @return Warehouse Locator
     */
    public int getM_Locator_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_Locator_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Locator.
     *
     * @param M_Locator_ID Warehouse Locator
     */
    public void setM_Locator_ID(int M_Locator_ID) {
        if (M_Locator_ID < 1) setValue(COLUMNNAME_M_Locator_ID, null);
        else setValue(COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
    }

    public org.compiere.model.I_M_Product getM_Product() throws RuntimeException {
        return (org.compiere.model.I_M_Product)
                MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
                        .getPO(getM_Product_ID());
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
        if (M_Product_ID < 1) setValue(COLUMNNAME_M_Product_ID, null);
        else setValue(COLUMNNAME_M_Product_ID, M_Product_ID);
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
        setValue(COLUMNNAME_NewCostPrice, NewCostPrice);
    }

    /**
     * Set Processed.
     *
     * @param Processed The document has been processed
     */
    public void setProcessed(boolean Processed) {
        setValue(COLUMNNAME_Processed, Boolean.valueOf(Processed));
    }

    /**
     * Get Quantity book.
     *
     * @return Book Quantity
     */
    public BigDecimal getQtyBook() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_QtyBook);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Quantity book.
     *
     * @param QtyBook Book Quantity
     */
    public void setQtyBook(BigDecimal QtyBook) {
        setValueNoCheck(COLUMNNAME_QtyBook, QtyBook);
    }

    /**
     * Get Quantity count.
     *
     * @return Counted Quantity
     */
    public BigDecimal getQtyCount() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_QtyCount);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Quantity count.
     *
     * @param QtyCount Counted Quantity
     */
    public void setQtyCount(BigDecimal QtyCount) {
        setValue(COLUMNNAME_QtyCount, QtyCount);
    }

    /**
     * Get Internal Use Qty.
     *
     * @return Internal Use Quantity removed from Inventory
     */
    public BigDecimal getQtyInternalUse() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_QtyInternalUse);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Internal Use Qty.
     *
     * @param QtyInternalUse Internal Use Quantity removed from Inventory
     */
    public void setQtyInternalUse(BigDecimal QtyInternalUse) {
        setValue(COLUMNNAME_QtyInternalUse, QtyInternalUse);
    }

    /**
     * Set Reversal Line.
     *
     * @param ReversalLine_ID Use to keep the reversal line ID for reversing costing purpose
     */
    public void setReversalLine_ID(int ReversalLine_ID) {
        if (ReversalLine_ID < 1) setValue(COLUMNNAME_ReversalLine_ID, null);
        else setValue(COLUMNNAME_ReversalLine_ID, Integer.valueOf(ReversalLine_ID));
    }

    @Override
    public int getTableId() {
        return I_M_InventoryLine.Table_ID;
    }
}
