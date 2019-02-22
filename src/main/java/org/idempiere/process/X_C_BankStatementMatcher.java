package org.idempiere.process;

import org.compiere.model.I_C_BankStatementMatcher;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

public class X_C_BankStatementMatcher extends BasePOName
        implements I_C_BankStatementMatcher, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_BankStatementMatcher(Properties ctx, int C_BankStatementMatcher_ID) {
        super(ctx, C_BankStatementMatcher_ID);
        /**
         * if (C_BankStatementMatcher_ID == 0) { setC_BankStatementMatcher_ID (0); setClassname (null);
         * setName (null); setSeqNo (0); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_BankStatementMatcher(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_C_BankStatementMatcher[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Classname.
     *
     * @return Java Classname
     */
    public String getClassname() {
        return (String) get_Value(COLUMNNAME_Classname);
    }

}
