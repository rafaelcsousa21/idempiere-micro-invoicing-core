package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_POSTenderType;
import org.compiere.orm.BasePONameValue;

import java.util.Properties;

/**
 * Generated Model for C_POSTenderType
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_POSTenderType extends BasePONameValue implements I_C_POSTenderType {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_POSTenderType(Properties ctx, int C_POSTenderType_ID) {
        super(ctx, C_POSTenderType_ID);
    }

    /**
     * Load Constructor
     */
    public X_C_POSTenderType(Properties ctx, Row row) {
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
        return "X_C_POSTenderType[" + getId() + "]";
    }

    /**
     * Get Guarantee.
     *
     * @return Guarantee for a Credit
     */
    public boolean isGuarantee() {
        Object oo = getValue(COLUMNNAME_IsGuarantee);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

}
