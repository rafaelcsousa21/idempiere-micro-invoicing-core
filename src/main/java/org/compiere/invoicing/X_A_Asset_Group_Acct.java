package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.model.I_A_Asset_Group_Acct;
import org.compiere.orm.PO;

import java.util.Properties;

/**
 * Generated Model for A_Asset_Group_Acct
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Asset_Group_Acct extends PO implements I_A_Asset_Group_Acct {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_A_Asset_Group_Acct(Properties ctx, int A_Asset_Group_Acct_ID) {
        super(ctx, A_Asset_Group_Acct_ID);
    }

    /**
     * Load Constructor
     */
    public X_A_Asset_Group_Acct(Properties ctx, Row row) {
        super(ctx, row);
    }

    /**
     * AccessLevel
     *
     * @return 3 - Client - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    @Override
    public int getTableId() {
        return Table_ID;
    }

    public String toString() {
        return "X_A_Asset_Group_Acct[" + getId() + "]";
    }

}
