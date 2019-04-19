package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.model.AssetType;
import org.compiere.orm.BasePONameValue;

/**
 * Generated Model for A_Asset_Type
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Asset_Type extends BasePONameValue implements AssetType {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_A_Asset_Type(int A_Asset_Type_ID) {
        super(A_Asset_Type_ID);
    }

    /**
     * Load Constructor
     */
    public X_A_Asset_Type(Row row) {
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

    public String toString() {
        StringBuffer sb = new StringBuffer("X_A_Asset_Type[").append(getId()).append("]");
        return sb.toString();
    }

    @Override
    public int getTableId() {
        return AssetType.Table_ID;
    }
}
