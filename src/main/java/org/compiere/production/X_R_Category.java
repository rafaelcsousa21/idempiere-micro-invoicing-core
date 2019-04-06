package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_R_Category;
import org.compiere.orm.BasePOName;

/**
 * Generated Model for R_Category
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_Category extends BasePOName implements I_R_Category {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_R_Category(int R_Category_ID) {
        super(R_Category_ID);
    }

    /**
     * Load Constructor
     */
    public X_R_Category(Row row) {
        super(row);
    }

    /**
     * AccessLevel
     *
     * @return 6 - System - Client
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        return "X_R_Category[" + getId() + "]";
    }

    @Override
    public int getTableId() {
        return I_R_Category.Table_ID;
    }
}
