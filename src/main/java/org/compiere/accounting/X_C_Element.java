package org.compiere.accounting;

import org.compiere.model.I_C_Element;
import org.compiere.orm.BasePOName;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_Element
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Element extends BasePOName implements I_C_Element {

    /**
     * Account = A
     */
    public static final String ELEMENTTYPE_Account = "A";
    /**
     * User defined = U
     */
    public static final String ELEMENTTYPE_UserDefined = "U";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_Element(Properties ctx, int C_Element_ID) {
        super(ctx, C_Element_ID);
    }


    /**
     * Load Constructor
     */
    public X_C_Element(Properties ctx, ResultSet rs) {
        super(ctx, rs);
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
        StringBuffer sb = new StringBuffer("X_C_Element[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Tree.
     *
     * @return Identifies a Tree
     */
    public int getTreeId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_Tree_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Tree.
     *
     * @param AD_Tree_ID Identifies a Tree
     */
    public void setTreeId(int AD_Tree_ID) {
        if (AD_Tree_ID < 1) setValueNoCheck(COLUMNNAME_AD_Tree_ID, null);
        else setValueNoCheck(COLUMNNAME_AD_Tree_ID, Integer.valueOf(AD_Tree_ID));
    }

    /**
     * Get Element.
     *
     * @return Accounting Element
     */
    public int getElementId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Element_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Type.
     *
     * @return Element Type (account or user defined)
     */
    public String getElementType() {
        return (String) getValue(COLUMNNAME_ElementType);
    }

    /**
     * Set Type.
     *
     * @param ElementType Element Type (account or user defined)
     */
    public void setElementType(String ElementType) {

        setValueNoCheck(COLUMNNAME_ElementType, ElementType);
    }

    /**
     * Set Balancing.
     *
     * @param IsBalancing All transactions within an element value must balance (e.g. cost centers)
     */
    public void setIsBalancing(boolean IsBalancing) {
        setValue(COLUMNNAME_IsBalancing, Boolean.valueOf(IsBalancing));
    }

    /**
     * Set Natural Account.
     *
     * @param IsNaturalAccount The primary natural account
     */
    public void setIsNaturalAccount(boolean IsNaturalAccount) {
        setValue(COLUMNNAME_IsNaturalAccount, Boolean.valueOf(IsNaturalAccount));
    }

    /**
     * Get Natural Account.
     *
     * @return The primary natural account
     */
    public boolean isNaturalAccount() {
        Object oo = getValue(COLUMNNAME_IsNaturalAccount);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

}
