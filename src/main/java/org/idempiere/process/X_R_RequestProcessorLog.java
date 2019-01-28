package org.idempiere.process;

import org.compiere.model.I_R_RequestProcessorLog;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

public class X_R_RequestProcessorLog extends PO implements I_R_RequestProcessorLog, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_RequestProcessorLog(Properties ctx, int R_RequestProcessorLog_ID, String trxName) {
    super(ctx, R_RequestProcessorLog_ID, trxName);
    /**
     * if (R_RequestProcessorLog_ID == 0) { setIsError (false); setR_RequestProcessor_ID (0);
     * setR_RequestProcessorLog_ID (0); }
     */
  }

  /** Load Constructor */
  public X_R_RequestProcessorLog(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
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
    StringBuffer sb = new StringBuffer("X_R_RequestProcessorLog[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Set Error.
   *
   * @param IsError An Error occurred in the execution
   */
  public void setIsError(boolean IsError) {
    set_Value(COLUMNNAME_IsError, Boolean.valueOf(IsError));
  }

    /**
   * Set Request Processor.
   *
   * @param R_RequestProcessor_ID Processor for Requests
   */
  public void setR_RequestProcessor_ID(int R_RequestProcessor_ID) {
    if (R_RequestProcessor_ID < 1) set_ValueNoCheck(COLUMNNAME_R_RequestProcessor_ID, null);
    else set_ValueNoCheck(COLUMNNAME_R_RequestProcessor_ID, Integer.valueOf(R_RequestProcessor_ID));
  }

  /**
   * Get Request Processor.
   *
   * @return Processor for Requests
   */
  public int getR_RequestProcessor_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_RequestProcessor_ID);
    if (ii == null) return 0;
    return ii;
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
