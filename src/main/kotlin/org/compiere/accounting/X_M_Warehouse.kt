package org.compiere.accounting

import kotliquery.Row
import org.compiere.model.I_M_Warehouse
import org.compiere.orm.BasePONameValue
import org.idempiere.common.util.AdempiereSystemError

/**
 * Generated Model for M_Warehouse
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
abstract class X_M_Warehouse : BasePONameValue, I_M_Warehouse {

    override val tableId: Int
        get() = I_M_Warehouse.Table_ID

    /**
     * Standard Constructor
     */
    constructor(M_Warehouse_ID: Int) : super(M_Warehouse_ID) {}

    /**
     * Load Constructor
     */
    constructor(row: Row) : super(row) {}

    /**
     * AccessLevel
     *
     * @return 3 - Client - Org
     */
    override fun getAccessLevel(): Int {
        return I_M_Warehouse.accessLevel.toInt()
    }

    override fun toString(): String {
        return "X_M_Warehouse[" + id + "]"
    }

    /**
     * Set Address.
     *
     * @param C_Location_ID Location or Address
     */
    override fun setLocationId(C_Location_ID: Int) {
        if (C_Location_ID < 1)
            setValue(I_M_Warehouse.COLUMNNAME_C_Location_ID, null)
        else
            setValue(I_M_Warehouse.COLUMNNAME_C_Location_ID, C_Location_ID)
    }

    /**
     * Get Disallow Negative Inventory.
     *
     * @return Negative Inventory is not allowed in this warehouse
     */
    override fun isDisallowNegativeInv(): Boolean {
        val oo = getValue<Any>(I_M_Warehouse.COLUMNNAME_IsDisallowNegativeInv)
        return if (oo != null) {
            if (oo is Boolean) (oo as Boolean?)!! else "Y" == oo
        } else false
    }

    /**
     * Get In Transit.
     *
     * @return Movement is in transit
     */
    override fun isInTransit(): Boolean {
        val oo = getValue<Any>(I_M_Warehouse.COLUMNNAME_IsInTransit)
        return if (oo != null) {
            if (oo is Boolean) (oo as Boolean?)!! else "Y" == oo
        } else false
    }

    /**
     * Get Warehouse.
     *
     * @return Storage Warehouse and Service Point
     */
    override fun getWarehouseId(): Int {
        return getValue<Int>(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID) ?: return 0
    }

    /**
     * Get Source Warehouse.
     *
     * @return Optional Warehouse to replenish from
     */
    override fun getWarehouseSourceId(): Int {
        return getValue<Int>(I_M_Warehouse.COLUMNNAME_M_WarehouseSource_ID) ?: return 0
    }

    /**
     * Get Replenishment Class.
     *
     * @return Custom class to calculate Quantity to Order
     */
    override fun getReplenishmentClass(): String {
        return getValue<String>(I_M_Warehouse.COLUMNNAME_ReplenishmentClass) ?: throw AdempiereSystemError("Replenishment Class not defined")
    }

    /**
     * Set Element Separator.
     *
     * @param Separator Element Separator
     */
    override fun setSeparator(Separator: String) {
        setValue(I_M_Warehouse.COLUMNNAME_Separator, Separator)
    }

    companion object {
        private const val serialVersionUID = 20171031L
    }
}
