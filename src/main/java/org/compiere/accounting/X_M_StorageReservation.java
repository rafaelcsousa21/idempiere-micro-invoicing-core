package org.compiere.accounting;

import org.compiere.model.I_M_StorageReservation;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_StorageReservation
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_StorageReservation extends PO implements I_M_StorageReservation {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_StorageReservation(Properties ctx, int M_StorageReservation_ID) {
        super(ctx, M_StorageReservation_ID);
        /**
         * if (M_StorageReservation_ID == 0) { setIsSOTrx (true); // Y setM_AttributeSetInstance_ID (0);
         * setM_Product_ID (0); setWarehouseId (0); setQty (Env.ZERO); }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_StorageReservation(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_M_StorageReservation[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Set Sales Transaction.
     *
     * @param IsSOTrx This is a Sales Transaction
     */
    public void setIsSOTrx(boolean IsSOTrx) {
        setValueNoCheck(COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
    }

    /**
     * Get Sales Transaction.
     *
     * @return This is a Sales Transaction
     */
    public boolean isSOTrx() {
        Object oo = getValue(COLUMNNAME_IsSOTrx);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
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
     * Get Warehouse.
     *
     * @return Storage Warehouse and Service Point
     */
    public int getWarehouseId() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_Warehouse_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Warehouse.
     *
     * @param M_Warehouse_ID Storage Warehouse and Service Point
     */
    public void setWarehouseId(int M_Warehouse_ID) {
        if (M_Warehouse_ID < 1) setValueNoCheck(COLUMNNAME_M_Warehouse_ID, null);
        else setValueNoCheck(COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
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
        setValueNoCheck(COLUMNNAME_Qty, Qty);
    }

    @Override
    public int getTableId() {
        return I_M_StorageReservation.Table_ID;
    }
}
