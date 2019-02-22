package org.compiere.production;

import org.compiere.model.I_R_Group;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for R_Group
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_Group extends BasePOName implements I_R_Group, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_R_Group(Properties ctx, int R_Group_ID) {
        super(ctx, R_Group_ID);
        /** if (R_Group_ID == 0) { setName (null); setR_Group_ID (0); } */
    }

    /**
     * Load Constructor
     */
    public X_R_Group(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_R_Group[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Change Notice.
     *
     * @return Bill of Materials (Engineering) Change Notice (Version)
     */
    public int getM_ChangeNotice_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_ChangeNotice_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get BOM & Formula.
     *
     * @return BOM & Formula
     */
    public int getPP_Product_BOM_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_PP_Product_BOM_ID);
        if (ii == null) return 0;
        return ii;
    }

    @Override
    public int getTableId() {
        return I_R_Group.Table_ID;
    }
}
