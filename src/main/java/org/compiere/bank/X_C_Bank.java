package org.compiere.bank;

import org.compiere.model.I_C_Bank;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_Bank
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Bank extends BasePOName implements I_C_Bank, I_Persistent {

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
    public X_C_Bank(Properties ctx, ResultSet rs) {
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

    public String toString() {
        StringBuffer sb = new StringBuffer("X_C_Bank[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Address.
     *
     * @return Location or Address
     */
    public int getC_Location_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Location_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Routing No.
     *
     * @return Bank Routing Number
     */
    public String getRoutingNo() {
        return (String) get_Value(COLUMNNAME_RoutingNo);
    }

    /**
     * Set Routing No.
     *
     * @param RoutingNo Bank Routing Number
     */
    public void setRoutingNo(String RoutingNo) {
        set_Value(COLUMNNAME_RoutingNo, RoutingNo);
    }

    /**
     * Get Swift code.
     *
     * @return Swift Code or BIC
     */
    public String getSwiftCode() {
        return (String) get_Value(COLUMNNAME_SwiftCode);
    }

    @Override
    public int getTableId() {
        return I_C_Bank.Table_ID;
    }
}
