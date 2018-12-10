package org.compiere.wf;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.AdempiereProcessorLog;

/**
 * Processor Log
 *
 * @author Jorg Janke
 * @version $Id: MWorkflowProcessorLog.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWorkflowProcessorLog extends X_AD_WorkflowProcessorLog
    implements AdempiereProcessorLog {
  /** */
  private static final long serialVersionUID = 7646579803939292482L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param AD_WorkflowProcessorLog_ID id
   * @param trxName transaction
   */
  public MWorkflowProcessorLog(Properties ctx, int AD_WorkflowProcessorLog_ID, String trxName) {
    super(ctx, AD_WorkflowProcessorLog_ID, trxName);
    if (AD_WorkflowProcessorLog_ID == 0) {
      setIsError(false);
    }
  } //	MWorkflowProcessorLog

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MWorkflowProcessorLog(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MWorkflowProcessorLog

  /**
   * Parent Constructor
   *
   * @param parent parent
   * @param Summary Summary
   */
  public MWorkflowProcessorLog(MWorkflowProcessor parent, String Summary) {
    this(parent.getCtx(), 0, parent.get_TrxName());
    setClientOrg(parent);
    setAD_WorkflowProcessor_ID(parent.getAD_WorkflowProcessor_ID());
    setSummary(Summary);
  } //	MWorkflowProcessorLog
} //	MWorkflowProcessorLog
