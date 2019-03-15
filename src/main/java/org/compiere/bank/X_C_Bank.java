package org.compiere.bank;

import kotliquery.Row;
import org.compiere.model.I_C_Bank;
import org.compiere.orm.BasePOName;

import java.util.Properties;

/**
 * Generated Model for C_Bank
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Bank extends BasePOName implements I_C_Bank {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_Bank(Properties ctx, int C_Bank_ID) {
        super(ctx, C_Bank_ID);
    }

    /**
     * Load Constructor
     */
    public X_C_Bank(Properties ctx, Row row) {
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

    public String toString() {
        return "X_C_Bank[" + getId() + "]";
    }

    /**
     * Get Routing No.
     *
     * @return Bank Routing Number
     */
    public String getRoutingNo() {
        return (String) getValue(COLUMNNAME_RoutingNo);
    }

    /**
     * Set Routing No.
     *
     * @param RoutingNo Bank Routing Number
     */
    public void setRoutingNo(String RoutingNo) {
        setValue(COLUMNNAME_RoutingNo, RoutingNo);
    }

    /**
     * Get Swift code.
     *
     * @return Swift Code or BIC
     */
    public String getSwiftCode() {
        return (String) getValue(COLUMNNAME_SwiftCode);
    }

    @Override
    public int getTableId() {
        return I_C_Bank.Table_ID;
    }
}
