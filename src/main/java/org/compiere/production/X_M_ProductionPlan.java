package org.compiere.production;

import org.compiere.model.I_M_ProductionPlan;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_ProductionPlan
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_ProductionPlan extends PO implements I_M_ProductionPlan {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_ProductionPlan(Properties ctx, int M_ProductionPlan_ID) {
        super(ctx, M_ProductionPlan_ID);
        /**
         * if (M_ProductionPlan_ID == 0) { setLine (0); // @SQL=SELECT NVL(MAX(Line),0)+10 AS
         * DefaultValue FROM M_ProductionPlan WHERE M_Production_ID=@M_Production_ID@ setM_Locator_ID
         * (0); // @M_Locator_ID@ setM_Product_ID (0); setM_Production_ID (0); setM_ProductionPlan_ID
         * (0); setProcessed (false); setProductionQty (Env.ZERO); // 1 }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_ProductionPlan(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_M_ProductionPlan[").append(getId()).append("]");
        return sb.toString();
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
     * Set Production.
     *
     * @param M_Production_ID Plan for producing a product
     */
    public void setM_Production_ID(int M_Production_ID) {
        if (M_Production_ID < 1) setValueNoCheck(COLUMNNAME_M_Production_ID, null);
        else setValueNoCheck(COLUMNNAME_M_Production_ID, Integer.valueOf(M_Production_ID));
    }

    /**
     * Get Production Plan.
     *
     * @return Plan for how a product is produced
     */
    public int getM_ProductionPlan_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_ProductionPlan_ID);
        if (ii == null) return 0;
        return ii;
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
     * Get Production Quantity.
     *
     * @return Quantity of products to produce
     */
    public BigDecimal getProductionQty() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_ProductionQty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Production Quantity.
     *
     * @param ProductionQty Quantity of products to produce
     */
    public void setProductionQty(BigDecimal ProductionQty) {
        setValue(COLUMNNAME_ProductionQty, ProductionQty);
    }

    @Override
    public int getTableId() {
        return I_M_ProductionPlan.Table_ID;
    }
}
