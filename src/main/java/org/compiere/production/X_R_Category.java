package org.compiere.production;

import org.compiere.model.I_R_Category;
import org.compiere.orm.BasePOName;

import java.sql.ResultSet;
import java.util.Properties;

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
    public X_R_Category(Properties ctx, int R_Category_ID) {
        super(ctx, R_Category_ID);
        /** if (R_Category_ID == 0) { setName (null); setR_Category_ID (0); } */
    }

    /**
     * Load Constructor
     */
    public X_R_Category(Properties ctx, ResultSet rs) {
        super(ctx, rs);
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
        StringBuffer sb = new StringBuffer("X_R_Category[").append(getId()).append("]");
        return sb.toString();
    }

    @Override
    public int getTableId() {
        return I_R_Category.Table_ID;
    }
}
