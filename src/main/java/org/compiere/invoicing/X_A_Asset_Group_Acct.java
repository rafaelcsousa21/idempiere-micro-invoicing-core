package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.model.AssetGroupAccounting;
import org.compiere.orm.PO;

/**
 * Generated Model for A_Asset_Group_Acct
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Asset_Group_Acct extends PO implements AssetGroupAccounting {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_A_Asset_Group_Acct(int A_Asset_Group_Acct_ID) {
        super(A_Asset_Group_Acct_ID);
    }

    /**
     * Load Constructor
     */
    public X_A_Asset_Group_Acct(Row row) {
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

    @Override
    public int getTableId() {
        return Table_ID;
    }

    public String toString() {
        return "X_A_Asset_Group_Acct[" + getId() + "]";
    }

}
