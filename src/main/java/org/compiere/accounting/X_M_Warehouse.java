package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_M_Warehouse;
import org.compiere.orm.BasePONameValue;

/**
 * Generated Model for M_Warehouse
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_Warehouse extends BasePONameValue implements I_M_Warehouse {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_Warehouse(int M_Warehouse_ID) {
        super(M_Warehouse_ID);
    }

    /**
     * Load Constructor
     */
    public X_M_Warehouse(Row row) {
        super(row);
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
        StringBuffer sb = new StringBuffer("X_M_Warehouse[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Set Address.
     *
     * @param C_Location_ID Location or Address
     */
    public void setLocationId(int C_Location_ID) {
        if (C_Location_ID < 1) setValue(COLUMNNAME_C_Location_ID, null);
        else setValue(COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
    }

    /**
     * Get Disallow Negative Inventory.
     *
     * @return Negative Inventory is not allowed in this warehouse
     */
    public boolean isDisallowNegativeInv() {
        Object oo = getValue(COLUMNNAME_IsDisallowNegativeInv);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get In Transit.
     *
     * @return Movement is in transit
     */
    public boolean isInTransit() {
        Object oo = getValue(COLUMNNAME_IsInTransit);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
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
     * Get Source Warehouse.
     *
     * @return Optional Warehouse to replenish from
     */
    public int getWarehouseSourceId() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_WarehouseSource_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Replenishment Class.
     *
     * @return Custom class to calculate Quantity to Order
     */
    public String getReplenishmentClass() {
        return (String) getValue(COLUMNNAME_ReplenishmentClass);
    }

    /**
     * Set Element Separator.
     *
     * @param Separator Element Separator
     */
    public void setSeparator(String Separator) {
        setValue(COLUMNNAME_Separator, Separator);
    }

    @Override
    public int getTableId() {
        return I_M_Warehouse.Table_ID;
    }
}
