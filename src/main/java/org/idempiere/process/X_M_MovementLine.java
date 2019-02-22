package org.idempiere.process;

import org.compiere.model.I_M_MovementLine;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

public class X_M_MovementLine extends PO implements I_M_MovementLine, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_MovementLine(Properties ctx, int M_MovementLine_ID) {
        super(ctx, M_MovementLine_ID);
        /**
         * if (M_MovementLine_ID == 0) { setLine (0); // @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue
         * FROM M_MovementLine WHERE M_Movement_ID=@M_Movement_ID@ setM_Locator_ID (0);
         * // @M_Locator_ID@ setM_LocatorTo_ID (0); // @M_LocatorTo_ID@ setM_Movement_ID (0);
         * setM_MovementLine_ID (0); setMovementQty (Env.ZERO); // 1 setM_Product_ID (0); setProcessed
         * (false); setTargetQty (Env.ZERO); // 0 }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_MovementLine(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_M_MovementLine[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Set Confirmed Quantity.
     *
     * @param ConfirmedQty Confirmation of a received quantity
     */
    public void setConfirmedQty(BigDecimal ConfirmedQty) {
        set_Value(COLUMNNAME_ConfirmedQty, ConfirmedQty);
    }

    /**
     * Get Distribution Order Line.
     *
     * @return Distribution Order Line
     */
    public int getDD_OrderLine_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_DD_OrderLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Distribution Order Line.
     *
     * @param DD_OrderLine_ID Distribution Order Line
     */
    public void setDD_OrderLine_ID(int DD_OrderLine_ID) {
        if (DD_OrderLine_ID < 1) set_Value(COLUMNNAME_DD_OrderLine_ID, null);
        else set_Value(COLUMNNAME_DD_OrderLine_ID, Integer.valueOf(DD_OrderLine_ID));
    }

    /**
     * Get Description.
     *
     * @return Optional short description of the record
     */
    public String getDescription() {
        return (String) get_Value(COLUMNNAME_Description);
    }

    /**
     * Set Description.
     *
     * @param Description Optional short description of the record
     */
    public void setDescription(String Description) {
        set_Value(COLUMNNAME_Description, Description);
    }

    /**
     * Get Line No.
     *
     * @return Unique line for this document
     */
    public int getLine() {
        Integer ii = (Integer) get_Value(COLUMNNAME_Line);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Line No.
     *
     * @param Line Unique line for this document
     */
    public void setLine(int Line) {
        set_Value(COLUMNNAME_Line, Integer.valueOf(Line));
    }

    /**
     * Set Attribute Set Instance.
     *
     * @param M_AttributeSetInstance_ID Product Attribute Set Instance
     */
    public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID < 0) set_Value(COLUMNNAME_M_AttributeSetInstance_ID, null);
        else
            set_Value(COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
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
     * Set Attribute Set Instance To.
     *
     * @param M_AttributeSetInstanceTo_ID Target Product Attribute Set Instance
     */
    public void setM_AttributeSetInstanceTo_ID(int M_AttributeSetInstanceTo_ID) {
        if (M_AttributeSetInstanceTo_ID < 1)
            set_ValueNoCheck(COLUMNNAME_M_AttributeSetInstanceTo_ID, null);
        else
            set_ValueNoCheck(
                    COLUMNNAME_M_AttributeSetInstanceTo_ID, Integer.valueOf(M_AttributeSetInstanceTo_ID));
    }

    /**
     * Get Attribute Set Instance To.
     *
     * @return Target Product Attribute Set Instance
     */
    public int getMAttributeSetInstanceTo_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_AttributeSetInstanceTo_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Locator.
     *
     * @return Warehouse Locator
     */
    public int getM_Locator_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_Locator_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Locator.
     *
     * @param M_Locator_ID Warehouse Locator
     */
    public void setM_Locator_ID(int M_Locator_ID) {
        if (M_Locator_ID < 1) set_Value(COLUMNNAME_M_Locator_ID, null);
        else set_Value(COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
    }

    /**
     * Get Locator To.
     *
     * @return Location inventory is moved to
     */
    public int getM_LocatorTo_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_LocatorTo_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Locator To.
     *
     * @param M_LocatorTo_ID Location inventory is moved to
     */
    public void setM_LocatorTo_ID(int M_LocatorTo_ID) {
        if (M_LocatorTo_ID < 1) set_Value(COLUMNNAME_M_LocatorTo_ID, null);
        else set_Value(COLUMNNAME_M_LocatorTo_ID, Integer.valueOf(M_LocatorTo_ID));
    }

    public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException {
        return (org.compiere.model.I_M_Movement)
                MTable.get(getCtx(), org.compiere.model.I_M_Movement.Table_Name)
                        .getPO(getM_Movement_ID());
    }

    /**
     * Get Inventory Move.
     *
     * @return Movement of Inventory
     */
    public int getM_Movement_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_Movement_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Inventory Move.
     *
     * @param M_Movement_ID Movement of Inventory
     */
    public void setM_Movement_ID(int M_Movement_ID) {
        if (M_Movement_ID < 1) set_ValueNoCheck(COLUMNNAME_M_Movement_ID, null);
        else set_ValueNoCheck(COLUMNNAME_M_Movement_ID, Integer.valueOf(M_Movement_ID));
    }

    /**
     * Get Move Line.
     *
     * @return Inventory Move document Line
     */
    public int getM_MovementLine_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_MovementLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Movement Quantity.
     *
     * @return Quantity of a product moved.
     */
    public BigDecimal getMovementQty() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_MovementQty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Movement Quantity.
     *
     * @param MovementQty Quantity of a product moved.
     */
    public void setMovementQty(BigDecimal MovementQty) {
        set_Value(COLUMNNAME_MovementQty, MovementQty);
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
        if (M_Product_ID < 1) set_Value(COLUMNNAME_M_Product_ID, null);
        else set_Value(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
    }

    /**
     * Set Processed.
     *
     * @param Processed The document has been processed
     */
    public void setProcessed(boolean Processed) {
        set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
    }

    /**
     * Get Reversal Line.
     *
     * @return Use to keep the reversal line ID for reversing costing purpose
     */
    public int getReversalLine_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_ReversalLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Reversal Line.
     *
     * @param ReversalLine_ID Use to keep the reversal line ID for reversing costing purpose
     */
    public void setReversalLine_ID(int ReversalLine_ID) {
        if (ReversalLine_ID < 1) set_Value(COLUMNNAME_ReversalLine_ID, null);
        else set_Value(COLUMNNAME_ReversalLine_ID, Integer.valueOf(ReversalLine_ID));
    }

    /**
     * Set Scrapped Quantity.
     *
     * @param ScrappedQty The Quantity scrapped due to QA issues
     */
    public void setScrappedQty(BigDecimal ScrappedQty) {
        set_Value(COLUMNNAME_ScrappedQty, ScrappedQty);
    }

    /**
     * Set Target Quantity.
     *
     * @param TargetQty Target Movement Quantity
     */
    public void setTargetQty(BigDecimal TargetQty) {
        set_Value(COLUMNNAME_TargetQty, TargetQty);
    }

}
