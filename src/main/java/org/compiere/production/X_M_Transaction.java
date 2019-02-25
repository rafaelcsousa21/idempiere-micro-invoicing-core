package org.compiere.production;

import org.compiere.model.I_M_Transaction;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for M_Transaction
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_Transaction extends PO implements I_M_Transaction {

    /**
     * Inventory Out = I-
     */
    public static final String MOVEMENTTYPE_InventoryOut = "I-";
    /**
     * Inventory In = I+
     */
    public static final String MOVEMENTTYPE_InventoryIn = "I+";
    /**
     * Movement From = M-
     */
    public static final String MOVEMENTTYPE_MovementFrom = "M-";
    /**
     * Movement To = M+
     */
    public static final String MOVEMENTTYPE_MovementTo = "M+";
    /**
     * Production + = P+
     */
    public static final String MOVEMENTTYPE_ProductionPlus = "P+";
    /**
     * Production - = P-
     */
    public static final String MOVEMENTTYPE_Production_ = "P-";
    /**
     * Work Order + = W+
     */
    public static final String MOVEMENTTYPE_WorkOrderPlus = "W+";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_Transaction(Properties ctx, int M_Transaction_ID) {
        super(ctx, M_Transaction_ID);
        /**
         * if (M_Transaction_ID == 0) { setM_AttributeSetInstance_ID (0); setM_Locator_ID (0);
         * setMovementDate (new Timestamp( System.currentTimeMillis() )); setMovementQty (Env.ZERO);
         * setMovementType (null); setM_Product_ID (0); setM_Transaction_ID (0); }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_Transaction(Properties ctx, ResultSet rs) {
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

    public String toString() {
        StringBuffer sb = new StringBuffer("X_M_Transaction[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Set Project Issue.
     *
     * @param C_ProjectIssue_ID Project Issues (Material, Labor)
     */
    public void setC_ProjectIssue_ID(int C_ProjectIssue_ID) {
        if (C_ProjectIssue_ID < 1) set_ValueNoCheck(COLUMNNAME_C_ProjectIssue_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_ProjectIssue_ID, Integer.valueOf(C_ProjectIssue_ID));
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
        Integer ii = (Integer) getValue(COLUMNNAME_M_AttributeSetInstance_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Shipment/Receipt Line.
     *
     * @param M_InOutLine_ID Line on Shipment or Receipt document
     */
    public void setM_InOutLine_ID(int M_InOutLine_ID) {
        if (M_InOutLine_ID < 1) set_ValueNoCheck(COLUMNNAME_M_InOutLine_ID, null);
        else set_ValueNoCheck(COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
    }

    /**
     * Set Phys.Inventory Line.
     *
     * @param M_InventoryLine_ID Unique line in an Inventory document
     */
    public void setM_InventoryLine_ID(int M_InventoryLine_ID) {
        if (M_InventoryLine_ID < 1) set_ValueNoCheck(COLUMNNAME_M_InventoryLine_ID, null);
        else set_ValueNoCheck(COLUMNNAME_M_InventoryLine_ID, Integer.valueOf(M_InventoryLine_ID));
    }

    /**
     * Set Locator.
     *
     * @param M_Locator_ID Warehouse Locator
     */
    public void setM_Locator_ID(int M_Locator_ID) {
        if (M_Locator_ID < 1) set_ValueNoCheck(COLUMNNAME_M_Locator_ID, null);
        else set_ValueNoCheck(COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
    }

    /**
     * Set Move Line.
     *
     * @param M_MovementLine_ID Inventory Move document Line
     */
    public void setM_MovementLine_ID(int M_MovementLine_ID) {
        if (M_MovementLine_ID < 1) set_ValueNoCheck(COLUMNNAME_M_MovementLine_ID, null);
        else set_ValueNoCheck(COLUMNNAME_M_MovementLine_ID, Integer.valueOf(M_MovementLine_ID));
    }

    /**
     * Set Movement Date.
     *
     * @param MovementDate Date a product was moved in or out of inventory
     */
    public void setMovementDate(Timestamp MovementDate) {
        set_ValueNoCheck(COLUMNNAME_MovementDate, MovementDate);
    }

    /**
     * Get Movement Quantity.
     *
     * @return Quantity of a product moved.
     */
    public BigDecimal getMovementQty() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_MovementQty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Movement Quantity.
     *
     * @param MovementQty Quantity of a product moved.
     */
    public void setMovementQty(BigDecimal MovementQty) {
        set_ValueNoCheck(COLUMNNAME_MovementQty, MovementQty);
    }

    /**
     * Get Movement Type.
     *
     * @return Method of moving the inventory
     */
    public String getMovementType() {
        return (String) getValue(COLUMNNAME_MovementType);
    }

    /**
     * Set Movement Type.
     *
     * @param MovementType Method of moving the inventory
     */
    public void setMovementType(String MovementType) {

        set_ValueNoCheck(COLUMNNAME_MovementType, MovementType);
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
        if (M_Product_ID < 1) set_ValueNoCheck(COLUMNNAME_M_Product_ID, null);
        else set_ValueNoCheck(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
    }

    /**
     * Set Production Line.
     *
     * @param M_ProductionLine_ID Document Line representing a production
     */
    public void setM_ProductionLine_ID(int M_ProductionLine_ID) {
        if (M_ProductionLine_ID < 1) set_ValueNoCheck(COLUMNNAME_M_ProductionLine_ID, null);
        else set_ValueNoCheck(COLUMNNAME_M_ProductionLine_ID, Integer.valueOf(M_ProductionLine_ID));
    }

    @Override
    public int getTableId() {
        return I_M_Transaction.Table_ID;
    }
}
