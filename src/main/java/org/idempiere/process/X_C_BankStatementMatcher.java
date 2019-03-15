package org.idempiere.process;

import kotliquery.Row;
import org.compiere.model.I_C_BankStatementMatcher;
import org.compiere.orm.BasePOName;

import java.util.Properties;

public class X_C_BankStatementMatcher extends BasePOName
        implements I_C_BankStatementMatcher {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_BankStatementMatcher(Properties ctx, int C_BankStatementMatcher_ID) {
        super(ctx, C_BankStatementMatcher_ID);
    }

    /**
     * Load Constructor
     */
    public X_C_BankStatementMatcher(Properties ctx, Row row) {
        super(ctx, row);
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
        return "X_C_BankStatementMatcher[" + getId() + "]";
    }

    /**
     * Get Classname.
     *
     * @return Java Classname
     */
    public String getClassname() {
        return (String) getValue(COLUMNNAME_Classname);
    }

}
