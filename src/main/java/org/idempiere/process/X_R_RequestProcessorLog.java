package org.idempiere.process;

import kotliquery.Row;
import org.compiere.model.I_R_RequestProcessorLog;
import org.compiere.orm.PO;

public class X_R_RequestProcessorLog extends PO implements I_R_RequestProcessorLog {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_R_RequestProcessorLog(int R_RequestProcessorLog_ID) {
        super(R_RequestProcessorLog_ID);
    }

    /**
     * Load Constructor
     */
    public X_R_RequestProcessorLog(Row row) {
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
        return "X_R_RequestProcessorLog[" + getId() + "]";
    }

    /**
     * Set Error.
     *
     * @param IsError An Error occurred in the execution
     */
    public void setIsError(boolean IsError) {
        setValue(COLUMNNAME_IsError, IsError);
    }

    /**
     * Set Request Processor.
     *
     * @param R_RequestProcessor_ID Processor for Requests
     */
    public void setRequestProcessorId(int R_RequestProcessor_ID) {
        if (R_RequestProcessor_ID < 1) setValueNoCheck(COLUMNNAME_R_RequestProcessor_ID, null);
        else setValueNoCheck(COLUMNNAME_R_RequestProcessor_ID, Integer.valueOf(R_RequestProcessor_ID));
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
