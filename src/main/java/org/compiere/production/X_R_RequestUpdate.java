package org.compiere.production;

import org.compiere.model.I_R_RequestUpdate;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for R_RequestUpdate
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_RequestUpdate extends PO implements I_R_RequestUpdate {

    /**
     * ConfidentialTypeEntry AD_Reference_ID=340
     */
    public static final int CONFIDENTIALTYPEENTRY_AD_Reference_ID = 340;
    /**
     * Public Information = A
     */
    public static final String CONFIDENTIALTYPEENTRY_PublicInformation = "A";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_R_RequestUpdate(Properties ctx, int R_RequestUpdate_ID) {
        super(ctx, R_RequestUpdate_ID);
        /**
         * if (R_RequestUpdate_ID == 0) { setConfidentialTypeEntry (null); setR_Request_ID (0);
         * setR_RequestUpdate_ID (0); }
         */
    }

    /**
     * Load Constructor
     */
    public X_R_RequestUpdate(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 7 - System - Client - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_R_RequestUpdate[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Entry Confidentiality.
     *
     * @return Confidentiality of the individual entry
     */
    public String getConfidentialTypeEntry() {
        return (String) getValue(COLUMNNAME_ConfidentialTypeEntry);
    }

    /**
     * Set Entry Confidentiality.
     *
     * @param ConfidentialTypeEntry Confidentiality of the individual entry
     */
    public void setConfidentialTypeEntry(String ConfidentialTypeEntry) {

        setValue(COLUMNNAME_ConfidentialTypeEntry, ConfidentialTypeEntry);
    }

    /**
     * Get Product Used.
     *
     * @return Product/Resource/Service used in Request
     */
    public int getM_ProductSpent_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_ProductSpent_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Quantity Invoiced.
     *
     * @return Invoiced Quantity
     */
    public BigDecimal getQtyInvoiced() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_QtyInvoiced);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Request.
     *
     * @param R_Request_ID Request from a Business Partner or Prospect
     */
    public void setR_Request_ID(int R_Request_ID) {
        if (R_Request_ID < 1) setValueNoCheck(COLUMNNAME_R_Request_ID, null);
        else setValueNoCheck(COLUMNNAME_R_Request_ID, Integer.valueOf(R_Request_ID));
    }

    @Override
    public int getTableId() {
        return I_R_RequestUpdate.Table_ID;
    }
}
