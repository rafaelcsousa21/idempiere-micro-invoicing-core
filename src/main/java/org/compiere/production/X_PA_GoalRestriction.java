package org.compiere.production;

import org.compiere.model.I_PA_GoalRestriction;
import org.compiere.orm.BasePOName;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for PA_GoalRestriction
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_PA_GoalRestriction extends BasePOName implements I_PA_GoalRestriction {

    /**
     * Organization = O
     */
    public static final String GOALRESTRICTIONTYPE_Organization = "O";
    /**
     * Business Partner = B
     */
    public static final String GOALRESTRICTIONTYPE_BusinessPartner = "B";
    /**
     * Product = P
     */
    public static final String GOALRESTRICTIONTYPE_Product = "P";
    /**
     * Bus.Partner Group = G
     */
    public static final String GOALRESTRICTIONTYPE_BusPartnerGroup = "G";
    /**
     * Product Category = C
     */
    public static final String GOALRESTRICTIONTYPE_ProductCategory = "C";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_PA_GoalRestriction(Properties ctx, int PA_GoalRestriction_ID) {
        super(ctx, PA_GoalRestriction_ID);
    }

    /**
     * Load Constructor
     */
    public X_PA_GoalRestriction(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_PA_GoalRestriction[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Business Partner .
     *
     * @return Identifies a Business Partner
     */
    public int getBusinessPartnerId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_BPartner_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Business Partner Group.
     *
     * @return Business Partner Group
     */
    public int getC_BP_Group_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_BP_Group_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Restriction Type.
     *
     * @return Goal Restriction Type
     */
    public String getGoalRestrictionType() {
        return (String) getValue(COLUMNNAME_GoalRestrictionType);
    }

    /**
     * Get Product Category.
     *
     * @return Category of a Product
     */
    public int getM_Product_Category_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_Product_Category_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Product.
     *
     * @return Product, Service, Item
     */
    public int getM_Product_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_Product_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Organization.
     *
     * @return Organizational entity within client
     */
    public int getOrg_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_Org_ID);
        if (ii == null) return 0;
        return ii;
    }

    @Override
    public int getTableId() {
        return I_PA_GoalRestriction.Table_ID;
    }
}
