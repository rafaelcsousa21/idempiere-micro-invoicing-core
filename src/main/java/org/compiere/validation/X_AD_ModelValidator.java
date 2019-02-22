package org.compiere.validation;

import kotliquery.Row;
import org.compiere.model.I_AD_ModelValidator;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_ModelValidator
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_ModelValidator extends BasePOName implements I_AD_ModelValidator, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_ModelValidator(Properties ctx, int AD_ModelValidator_ID) {
        super(ctx, AD_ModelValidator_ID);
    }

    /**
     * Load Constructor
     */
    public X_AD_ModelValidator(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    public X_AD_ModelValidator(Properties ctx, Row row) {
        super(ctx, row);
    }

    /**
     * AccessLevel
     *
     * @return 4 - System
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }


    @Override
    public int getTableId() {
        return Table_ID;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_ModelValidator[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Model Validation Class.
     *
     * @return Model Validation Class
     */
    public String getModelValidationClass() {
        return (String) get_Value(COLUMNNAME_ModelValidationClass);
    }

}
