package org.idempiere.process;

import org.compiere.model.I_M_BOM;
import org.compiere.orm.BasePOName;

import java.sql.ResultSet;
import java.util.Properties;

public class X_M_BOM extends BasePOName implements I_M_BOM {

    /**
     * Current Active = A
     */
    public static final String BOMTYPE_CurrentActive = "A";
    /**
     * Make-To-Order = O
     */
    public static final String BOMTYPE_Make_To_Order = "O";
    /**
     * Master = A
     */
    public static final String BOMUSE_Master = "A";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_BOM(Properties ctx, int M_BOM_ID) {
        super(ctx, M_BOM_ID);
        /**
         * if (M_BOM_ID == 0) { setBOMType (null); // A setBOMUse (null); // A setM_BOM_ID (0);
         * setM_Product_ID (0); setName (null); }
         */
    }


    /**
     * Load Constructor
     */
    public X_M_BOM(Properties ctx, ResultSet rs) {
        super(ctx, rs);
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
        StringBuffer sb = new StringBuffer("X_M_BOM[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get BOM Type.
     *
     * @return Type of BOM
     */
    public String getBOMType() {
        return (String) getValue(COLUMNNAME_BOMType);
    }

    /**
     * Set BOM Type.
     *
     * @param BOMType Type of BOM
     */
    public void setBOMType(String BOMType) {

        set_Value(COLUMNNAME_BOMType, BOMType);
    }

    /**
     * Get BOM Use.
     *
     * @return The use of the Bill of Material
     */
    public String getBOMUse() {
        return (String) getValue(COLUMNNAME_BOMUse);
    }

    /**
     * Set BOM Use.
     *
     * @param BOMUse The use of the Bill of Material
     */
    public void setBOMUse(String BOMUse) {

        set_Value(COLUMNNAME_BOMUse, BOMUse);
    }

    /**
     * Get BOM.
     *
     * @return Bill of Material
     */
    public int getM_BOM_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_BOM_ID);
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

}
