package org.compiere.accounting;

import org.compiere.model.I_M_Product_PO;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_Product_PO
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_Product_PO extends PO implements I_M_Product_PO {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_Product_PO(Properties ctx, int M_Product_PO_ID) {
        super(ctx, M_Product_PO_ID);
        /**
         * if (M_Product_PO_ID == 0) { setC_BPartner_ID (0); setIsCurrentVendor (true); // Y
         * setM_Product_ID (0); // @M_Product_ID@ setVendorProductNo (null); // @Value@ }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_Product_PO(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_M_Product_PO[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Business Partner .
     *
     * @return Identifies a Business Partner
     */
    public int getC_BPartner_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_BPartner_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Currency.
     *
     * @return The Currency for this record
     */
    public int getC_Currency_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Currency_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get UOM.
     *
     * @return Unit of Measure
     */
    public int getC_UOM_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_UOM_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Current vendor.
     *
     * @param IsCurrentVendor Use this Vendor for pricing and stock replenishment
     */
    public void setIsCurrentVendor(boolean IsCurrentVendor) {
        set_Value(COLUMNNAME_IsCurrentVendor, Boolean.valueOf(IsCurrentVendor));
    }

    /**
     * Get Current vendor.
     *
     * @return Use this Vendor for pricing and stock replenishment
     */
    public boolean isCurrentVendor() {
        Object oo = getValue(COLUMNNAME_IsCurrentVendor);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
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
     * Get Last PO Price.
     *
     * @return Price of the last purchase order for the product
     */
    public BigDecimal getPriceLastPO() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_PriceLastPO);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Get List Price.
     *
     * @return List Price
     */
    public BigDecimal getPriceList() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_PriceList);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Get PO Price.
     *
     * @return Price based on a purchase order
     */
    public BigDecimal getPricePO() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_PricePO);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    @Override
    public int getTableId() {
        return I_M_Product_PO.Table_ID;
    }
}
