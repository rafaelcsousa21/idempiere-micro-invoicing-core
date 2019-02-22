package org.compiere.production;

import org.compiere.model.I_C_ProjectIssue;
import org.compiere.model.I_M_Locator;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_ProjectIssue
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ProjectIssue extends PO implements I_C_ProjectIssue, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_ProjectIssue(Properties ctx, int C_ProjectIssue_ID) {
        super(ctx, C_ProjectIssue_ID);
        /**
         * if (C_ProjectIssue_ID == 0) { setC_Project_ID (0); setC_ProjectIssue_ID (0); setLine (0);
         * // @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM C_ProjectIssue WHERE
         * C_Project_ID=@C_Project_ID@ setM_AttributeSetInstance_ID (0); setM_Locator_ID (0);
         * setMovementDate (new Timestamp( System.currentTimeMillis() )); setMovementQty (Env.ZERO);
         * setM_Product_ID (0); setPosted (false); // N setProcessed (false); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_ProjectIssue(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_C_ProjectIssue[").append(getId()).append("]");
        return sb.toString();
    }

    public org.compiere.model.I_C_Project getC_Project() throws RuntimeException {
        return (org.compiere.model.I_C_Project)
                MTable.get(getCtx(), org.compiere.model.I_C_Project.Table_Name)
                        .getPO(getC_Project_ID());
    }

    /**
     * Get Project.
     *
     * @return Financial Project
     */
    public int getC_Project_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Project_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Project.
     *
     * @param C_Project_ID Financial Project
     */
    public void setC_Project_ID(int C_Project_ID) {
        if (C_Project_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Project_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
    }

    /**
     * Get Project Issue.
     *
     * @return Project Issues (Material, Labor)
     */
    public int getC_ProjectIssue_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_ProjectIssue_ID);
        if (ii == null) return 0;
        return ii;
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
     * Get Shipment/Receipt Line.
     *
     * @return Line on Shipment or Receipt document
     */
    public int getM_InOutLine_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_InOutLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Shipment/Receipt Line.
     *
     * @param M_InOutLine_ID Line on Shipment or Receipt document
     */
    public void setM_InOutLine_ID(int M_InOutLine_ID) {
        if (M_InOutLine_ID < 1) set_Value(COLUMNNAME_M_InOutLine_ID, null);
        else set_Value(COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
    }

    public I_M_Locator getM_Locator() throws RuntimeException {
        return (I_M_Locator)
                MTable.get(getCtx(), I_M_Locator.Table_Name).getPO(getM_Locator_ID());
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
        else set_Value(COLUMNNAME_M_Locator_ID, M_Locator_ID);
    }

    /**
     * Get Movement Date.
     *
     * @return Date a product was moved in or out of inventory
     */
    public Timestamp getMovementDate() {
        return (Timestamp) get_Value(COLUMNNAME_MovementDate);
    }

    /**
     * Set Movement Date.
     *
     * @param MovementDate Date a product was moved in or out of inventory
     */
    public void setMovementDate(Timestamp MovementDate) {
        set_Value(COLUMNNAME_MovementDate, MovementDate);
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
        else set_Value(COLUMNNAME_M_Product_ID, M_Product_ID);
    }

    /**
     * Set Posted.
     *
     * @param Posted Posting status
     */
    public void setPosted(boolean Posted) {
        set_Value(COLUMNNAME_Posted, Posted);
    }

    /**
     * Set Processed.
     *
     * @param Processed The document has been processed
     */
    public void setProcessed(boolean Processed) {
        set_Value(COLUMNNAME_Processed, Processed);
    }

    /**
     * Get Expense Line.
     *
     * @return Time and Expense Report Line
     */
    public int getS_TimeExpenseLine_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_S_TimeExpenseLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Expense Line.
     *
     * @param S_TimeExpenseLine_ID Time and Expense Report Line
     */
    public void setS_TimeExpenseLine_ID(int S_TimeExpenseLine_ID) {
        if (S_TimeExpenseLine_ID < 1) set_Value(COLUMNNAME_S_TimeExpenseLine_ID, null);
        else set_Value(COLUMNNAME_S_TimeExpenseLine_ID, S_TimeExpenseLine_ID);
    }

    @Override
    public int getTableId() {
        return I_C_ProjectIssue.Table_ID;
    }
}
