package org.idempiere.process;

import org.compiere.model.I_R_RequestProcessor_Route;
import org.compiere.orm.PO;

import java.sql.ResultSet;
import java.util.Properties;

public class X_R_RequestProcessor_Route extends PO
        implements I_R_RequestProcessor_Route {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_R_RequestProcessor_Route(
            Properties ctx, int R_RequestProcessor_Route_ID) {
        super(ctx, R_RequestProcessor_Route_ID);
        /**
         * if (R_RequestProcessor_Route_ID == 0) { setUserId (0); setR_RequestProcessor_ID (0);
         * setR_RequestProcessor_Route_ID (0); setSeqNo (0); }
         */
    }

    /**
     * Load Constructor
     */
    public X_R_RequestProcessor_Route(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 2 - Client
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    @Override
    public int getTableId() {
        return Table_ID;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_R_RequestProcessor_Route[").append(getId()).append("]");
        return sb.toString();
    }

}
