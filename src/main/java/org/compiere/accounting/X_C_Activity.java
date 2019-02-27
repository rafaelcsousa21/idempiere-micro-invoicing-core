package org.compiere.accounting;

import org.compiere.model.I_C_Activity;
import org.compiere.orm.BasePONameValue;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_Activity
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Activity extends BasePONameValue implements I_C_Activity {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_Activity(Properties ctx, int C_Activity_ID) {
        super(ctx, C_Activity_ID);
        /**
         * if (C_Activity_ID == 0) { setBusinessActivityId (0); setIsSummary (false); setName (null);
         * setValue (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_Activity(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_C_Activity[").append(getId()).append("]");
        return sb.toString();
    }

}
