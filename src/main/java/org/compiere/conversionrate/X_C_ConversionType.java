package org.compiere.conversionrate;

import kotliquery.Row;
import org.compiere.model.I_C_ConversionType;
import org.compiere.orm.BasePONameValue;

/**
 * Generated Model for C_ConversionType
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ConversionType extends BasePONameValue
        implements I_C_ConversionType {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_ConversionType(int C_ConversionType_ID) {
        super(C_ConversionType_ID);
    }

    /**
     * Load Constructor
     */
    public X_C_ConversionType(Row row) {
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
        return "X_C_ConversionType[" + getId() + "]";
    }

    @Override
    public int getTableId() {
        return I_C_ConversionType.Table_ID;
    }
}
