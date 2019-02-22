package org.compiere.production;

import org.compiere.model.I_PA_MeasureCalc;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for PA_MeasureCalc
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_PA_MeasureCalc extends BasePOName implements I_PA_MeasureCalc, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_PA_MeasureCalc(Properties ctx, int PA_MeasureCalc_ID) {
        super(ctx, PA_MeasureCalc_ID);
        /**
         * if (PA_MeasureCalc_ID == 0) { setAD_Table_ID (0); setDateColumn (null); // x.Date
         * setEntityType (null); // @SQL=select get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual
         * setKeyColumn (null); setName (null); setOrgColumn (null); // x.orgId setPA_MeasureCalc_ID
         * (0); setSelectClause (null); // SELECT ... FROM ... setWhereClause (null); // WHERE ... }
         */
    }

    /**
     * Load Constructor
     */
    public X_PA_MeasureCalc(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_PA_MeasureCalc[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Table.
     *
     * @return Database Table information
     */
    public int getAD_Table_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_Table_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get B.Partner Column.
     *
     * @return Fully qualified Business Partner key column (C_BPartner_ID)
     */
    public String getBPartnerColumn() {
        return (String) get_Value(COLUMNNAME_BPartnerColumn);
    }

    /**
     * Get Date Column.
     *
     * @return Fully qualified date column
     */
    public String getDateColumn() {
        return (String) get_Value(COLUMNNAME_DateColumn);
    }

    /**
     * Get Key Column.
     *
     * @return Key Column for Table
     */
    public String getKeyColumn() {
        return (String) get_Value(COLUMNNAME_KeyColumn);
    }

    /**
     * Get Org Column.
     *
     * @return Fully qualified Organization column (orgId)
     */
    public String getOrgColumn() {
        return (String) get_Value(COLUMNNAME_OrgColumn);
    }

    /**
     * Get Product Column.
     *
     * @return Fully qualified Product column (M_Product_ID)
     */
    public String getProductColumn() {
        return (String) get_Value(COLUMNNAME_ProductColumn);
    }

    /**
     * Get Sql SELECT.
     *
     * @return SQL SELECT clause
     */
    public String getSelectClause() {
        return (String) get_Value(COLUMNNAME_SelectClause);
    }

    /**
     * Get Sql WHERE.
     *
     * @return Fully qualified SQL WHERE clause
     */
    public String getWhereClause() {
        return (String) get_Value(COLUMNNAME_WhereClause);
    }

    @Override
    public int getTableId() {
        return I_PA_MeasureCalc.Table_ID;
    }
}
