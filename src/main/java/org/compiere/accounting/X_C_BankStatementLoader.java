package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_BankStatementLoader;
import org.compiere.orm.BasePOName;

import java.util.Properties;

public class X_C_BankStatementLoader extends BasePOName
        implements I_C_BankStatementLoader {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_BankStatementLoader(Properties ctx, int C_BankStatementLoader_ID) {
        super(ctx, C_BankStatementLoader_ID);
        /**
         * if (C_BankStatementLoader_ID == 0) { setC_BankAccount_ID (0); setC_BankStatementLoader_ID
         * (0); setName (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_BankStatementLoader(Properties ctx, Row row) {
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
        StringBuffer sb = new StringBuffer("X_C_BankStatementLoader[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Statement Loader Class.
     *
     * @return Class name of the bank statement loader
     */
    public String getStmtLoaderClass() {
        return (String) getValue(COLUMNNAME_StmtLoaderClass);
    }

}
