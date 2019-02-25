package org.compiere.production;

import org.compiere.model.I_R_RequestAction;
import org.compiere.orm.PO;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for R_RequestAction
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_RequestAction extends PO implements I_R_RequestAction {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_R_RequestAction(Properties ctx, int R_RequestAction_ID) {
        super(ctx, R_RequestAction_ID);
        /** if (R_RequestAction_ID == 0) { setR_RequestAction_ID (0); setR_Request_ID (0); } */
    }

    /**
     * Load Constructor
     */
    public X_R_RequestAction(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_R_RequestAction[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Set Request.
     *
     * @param R_Request_ID Request from a Business Partner or Prospect
     */
    public void setR_Request_ID(int R_Request_ID) {
        if (R_Request_ID < 1) set_ValueNoCheck(COLUMNNAME_R_Request_ID, null);
        else set_ValueNoCheck(COLUMNNAME_R_Request_ID, Integer.valueOf(R_Request_ID));
    }

    @Override
    public int getTableId() {
        return I_R_RequestAction.Table_ID;
    }
}
