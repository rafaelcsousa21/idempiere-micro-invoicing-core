package org.idempiere.process;

import org.compiere.model.I_C_AcctProcessorLog;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

public class X_C_AcctProcessorLog extends PO implements I_C_AcctProcessorLog, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_AcctProcessorLog(Properties ctx, int C_AcctProcessorLog_ID) {
        super(ctx, C_AcctProcessorLog_ID);
        /**
         * if (C_AcctProcessorLog_ID == 0) { setC_AcctProcessor_ID (0); setC_AcctProcessorLog_ID (0);
         * setIsError (false); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_AcctProcessorLog(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_C_AcctProcessorLog[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Accounting Processor.
     *
     * @return Accounting Processor/Server Parameters
     */
    public int getC_AcctProcessor_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_AcctProcessor_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Accounting Processor.
     *
     * @param C_AcctProcessor_ID Accounting Processor/Server Parameters
     */
    public void setC_AcctProcessor_ID(int C_AcctProcessor_ID) {
        if (C_AcctProcessor_ID < 1) set_ValueNoCheck(COLUMNNAME_C_AcctProcessor_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_AcctProcessor_ID, Integer.valueOf(C_AcctProcessor_ID));
    }

    /**
     * Set Summary.
     *
     * @param Summary Textual summary of this request
     */
    public void setSummary(String Summary) {
        set_Value(COLUMNNAME_Summary, Summary);
    }

}
