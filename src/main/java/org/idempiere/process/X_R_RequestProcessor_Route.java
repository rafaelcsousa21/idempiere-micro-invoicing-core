package org.idempiere.process;

import kotliquery.Row;
import org.compiere.model.I_R_RequestProcessor_Route;
import org.compiere.orm.PO;

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
            int R_RequestProcessor_Route_ID) {
        super(R_RequestProcessor_Route_ID);
    }

    /**
     * Load Constructor
     */
    public X_R_RequestProcessor_Route(Row row) {
        super(row);
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
        return "X_R_RequestProcessor_Route[" + getId() + "]";
    }

}
