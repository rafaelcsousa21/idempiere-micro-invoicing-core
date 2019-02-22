package org.idempiere.process;

import org.compiere.model.I_T_Replenish;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

public class X_T_Replenish extends PO implements I_T_Replenish, I_Persistent {

    /**
     * Custom = 9
     */
    public static final String REPLENISHTYPE_Custom = "9";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_T_Replenish(Properties ctx, int T_Replenish_ID) {
        super(ctx, T_Replenish_ID);
        /**
         * if (T_Replenish_ID == 0) { setAD_PInstance_ID (0); setC_BPartner_ID (0); setLevel_Max
         * (Env.ZERO); setLevel_Min (Env.ZERO); setM_Product_ID (0); setM_Warehouse_ID (0);
         * setReplenishType (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_T_Replenish(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_T_Replenish[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Process Instance.
     *
     * @return Instance of the process
     */
    public int getAD_PInstance_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_PInstance_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Business Partner .
     *
     * @return Identifies a Business Partner
     */
    public int getC_BPartner_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_BPartner_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Document Type.
     *
     * @return Document type or rules
     */
    public int getC_DocType_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_DocType_ID);
        if (ii == null) return 0;
        return ii;
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
     * Get Warehouse.
     *
     * @return Storage Warehouse and Service Point
     */
    public int getM_Warehouse_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_Warehouse_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Source Warehouse.
     *
     * @return Optional Warehouse to replenish from
     */
    public int getM_WarehouseSource_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_WarehouseSource_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Quantity to Order.
     *
     * @return Quantity to Order
     */
    public BigDecimal getQtyToOrder() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_QtyToOrder);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Quantity to Order.
     *
     * @param QtyToOrder Quantity to Order
     */
    public void setQtyToOrder(BigDecimal QtyToOrder) {
        set_Value(COLUMNNAME_QtyToOrder, QtyToOrder);
    }

    /**
     * Get Replenish Type.
     *
     * @return Method for re-ordering a product
     */
    public String getReplenishType() {
        return (String) get_Value(COLUMNNAME_ReplenishType);
    }

    @Override
    public int getTableId() {
        return Table_ID;
    }
}
