package org.idempiere.process;

import org.compiere.model.I_M_Replenish;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

public class X_M_Replenish extends PO implements I_M_Replenish {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_Replenish(Properties ctx, int M_Replenish_ID) {
        super(ctx, M_Replenish_ID);
        /**
         * if (M_Replenish_ID == 0) { setLevel_Max (Env.ZERO); setLevel_Min (Env.ZERO); setM_Product_ID
         * (0); setWarehouseId (0); setReplenishType (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_Replenish(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_M_Replenish[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Warehouse.
     *
     * @return Storage Warehouse and Service Point
     */
    public int getM_Warehouse_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_Warehouse_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Qty Batch Size.
     *
     * @return Qty Batch Size
     */
    public BigDecimal getQtyBatchSize() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_QtyBatchSize);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    @Override
    public int getTableId() {
        return Table_ID;
    }
}
