package org.idempiere.process;

import org.compiere.model.AdempiereProcessorLog;

import java.sql.ResultSet;
import java.util.Properties;

public class MAcctProcessorLog extends X_C_AcctProcessorLog implements AdempiereProcessorLog {

  /** */
  private static final long serialVersionUID = 3668544104375224987L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param C_AcctProcessorLog_ID id
   * @param trxName transaction
   */
  public MAcctProcessorLog(Properties ctx, int C_AcctProcessorLog_ID, String trxName) {
    super(ctx, C_AcctProcessorLog_ID, trxName);
  } //	MAcctProcessorLog

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MAcctProcessorLog(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MAcctProcessorLog

  /**
   * Parent Constructor
   *
   * @param parent parent
   * @param summary summary
   */
  public MAcctProcessorLog(MAcctProcessor parent, String summary) {
    this(parent.getCtx(), 0, null);
    setClientOrg(parent);
    setC_AcctProcessor_ID(parent.getC_AcctProcessor_ID());
    setSummary(summary);
  } //	MAcctProcessorLog
} //	MAcctProcessorLog
