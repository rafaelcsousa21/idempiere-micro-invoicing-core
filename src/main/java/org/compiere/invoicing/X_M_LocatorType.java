package org.compiere.invoicing;

import org.compiere.model.I_M_LocatorType;
import org.compiere.orm.BasePOName;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_LocatorType
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_LocatorType extends BasePOName implements I_M_LocatorType {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_LocatorType(Properties ctx, int M_LocatorType_ID) {
        super(ctx, M_LocatorType_ID);
        /**
         * if (M_LocatorType_ID == 0) { setIsAvailableForReplenishment (true); // Y
         * setIsAvailableForReservation (true); // Y setIsAvailableForShipping (true); // Y
         * setM_LocatorType_ID (0); setName (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_LocatorType(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_M_LocatorType[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Set Available for Replenishment.
     *
     * @param IsAvailableForReplenishment Available for Replenishment
     */
    public void setIsAvailableForReplenishment(boolean IsAvailableForReplenishment) {
        set_Value(COLUMNNAME_IsAvailableForReplenishment, Boolean.valueOf(IsAvailableForReplenishment));
    }

    /**
     * Get Available for Replenishment.
     *
     * @return Available for Replenishment
     */
    public boolean isAvailableForReplenishment() {
        Object oo = getValue(COLUMNNAME_IsAvailableForReplenishment);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Available for Reservation.
     *
     * @param IsAvailableForReservation Available for Reservation
     */
    public void setIsAvailableForReservation(boolean IsAvailableForReservation) {
        set_Value(COLUMNNAME_IsAvailableForReservation, Boolean.valueOf(IsAvailableForReservation));
    }

    /**
     * Set Available for Shipping.
     *
     * @param IsAvailableForShipping Available for Shipping
     */
    public void setIsAvailableForShipping(boolean IsAvailableForShipping) {
        set_Value(COLUMNNAME_IsAvailableForShipping, Boolean.valueOf(IsAvailableForShipping));
    }

    /**
     * Get Available for Shipping.
     *
     * @return Available for Shipping
     */
    public boolean isAvailableForShipping() {
        Object oo = getValue(COLUMNNAME_IsAvailableForShipping);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public int getTableId() {
        return I_M_LocatorType.Table_ID;
    }
}
