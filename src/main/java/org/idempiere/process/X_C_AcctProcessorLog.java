package org.idempiere.process;

import kotliquery.Row;
import org.compiere.model.I_C_AcctProcessorLog;
import org.compiere.orm.PO;

public class X_C_AcctProcessorLog extends PO implements I_C_AcctProcessorLog {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_AcctProcessorLog(int C_AcctProcessorLog_ID) {
        super(C_AcctProcessorLog_ID);
        /**
         * if (C_AcctProcessorLog_ID == 0) { setAccountingProcessorId (0); setAcctProcessorLog_ID (0);
         * setIsError (false); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_AcctProcessorLog(Row row) {
        super(row);
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
     * Set Accounting Processor.
     *
     * @param C_AcctProcessor_ID Accounting Processor/Server Parameters
     */
    public void setAccountingProcessorId(int C_AcctProcessor_ID) {
        if (C_AcctProcessor_ID < 1) setValueNoCheck(COLUMNNAME_C_AcctProcessor_ID, null);
        else setValueNoCheck(COLUMNNAME_C_AcctProcessor_ID, Integer.valueOf(C_AcctProcessor_ID));
    }

    /**
     * Set Summary.
     *
     * @param Summary Textual summary of this request
     */
    public void setSummary(String Summary) {
        setValue(COLUMNNAME_Summary, Summary);
    }

}
