package org.compiere.wf;

import static org.idempiere.common.util.Env.getCtx;

import java.util.logging.Level;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.idempiere.common.util.AdempiereUserError;

/**
 * Validate Workflow Process
 *
 * @author Jorg Janke
 * @version $Id: WorkflowValidate.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class WorkflowValidate extends SvrProcess {
  private int p_AD_Worlflow_ID = 0;

  /** Prepare */
  protected void prepare() {
    p_AD_Worlflow_ID = getRecord_ID();
  } //	prepare

  /**
   * Process
   *
   * @return info
   * @throws Exception
   */
  protected String doIt() throws Exception {
    MWorkflow wf = MWorkflow.get(getCtx(), p_AD_Worlflow_ID);
    if (log.isLoggable(Level.INFO)) log.info("WF=" + wf);

    String msg = wf.validate();
    wf.saveEx();
    if (msg.length() > 0)
      throw new AdempiereUserError(Msg.getMsg(getCtx(), "WorflowNotValid") + " - " + msg);
    return wf.isValid() ? "@OK@" : "@Error@";
  } //	doIt
} //	WorkflowValidate
