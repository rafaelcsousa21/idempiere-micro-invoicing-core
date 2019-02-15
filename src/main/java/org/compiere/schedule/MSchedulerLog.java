package org.compiere.schedule;

import org.compiere.model.AdempiereProcessorLog;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Scheduler Log
 *
 * @author Jorg Janke
 * @version $Id: MSchedulerLog.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MSchedulerLog extends X_AD_SchedulerLog implements AdempiereProcessorLog {
  /** */
  private static final long serialVersionUID = -8105976307507562851L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param AD_SchedulerLog_ID id
   * @param trxName transaction
   */
  public MSchedulerLog(Properties ctx, int AD_SchedulerLog_ID) {
    super(ctx, AD_SchedulerLog_ID);
    if (AD_SchedulerLog_ID == 0) setIsError(false);
  } //	MSchedulerLog

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MSchedulerLog(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MSchedulerLog

  /**
   * Parent Constructor
   *
   * @param parent parent
   * @param summary summary
   */
  public MSchedulerLog(MScheduler parent, String summary) {
    this(parent.getCtx(), 0);
    setClientOrg(parent);
    setAD_Scheduler_ID(parent.getAD_Scheduler_ID());
    setSummary(summary);
  } //	MSchedulerLog
} //	MSchedulerLog
