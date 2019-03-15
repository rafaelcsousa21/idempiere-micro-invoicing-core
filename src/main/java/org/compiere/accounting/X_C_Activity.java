package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_Activity;
import org.compiere.orm.BasePONameValue;

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
    }

    /**
     * Load Constructor
     */
    public X_C_Activity(Properties ctx, Row row) {
        super(ctx, row);
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
        return "X_C_Activity[" + getId() + "]";
    }

}
