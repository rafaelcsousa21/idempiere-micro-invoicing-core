package org.compiere.wf;

import org.compiere.orm.MRole;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Worflow Access Model
 *
 * @author Jorg Janke
 * @version $Id: MWorkflowAccess.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWorkflowAccess extends X_AD_Workflow_Access {

  /** */
  private static final long serialVersionUID = 2598861248782340850L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param ignored -
   * @param trxName transaction
   */
  public MWorkflowAccess(Properties ctx, int ignored, String trxName) {
    super(ctx, 0, trxName);
    if (ignored != 0) throw new IllegalArgumentException("Multi-Key");
    else {
      //	setAD_Role_ID (0);
      //	setAD_Workflow_ID (0);
      setIsReadWrite(true);
    }
  } //	MWorkflowAccess

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MWorkflowAccess(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MWorkflowAccess

  /**
   * Parent Constructor
   *
   * @param parent parent
   * @param AD_Role_ID role id
   */
  public MWorkflowAccess(MWorkflow parent, int AD_Role_ID) {
    super(parent.getCtx(), 0, null);
    MRole role = MRole.get(parent.getCtx(), AD_Role_ID);
    setClientOrg(role);
    setAD_Workflow_ID(parent.getAD_Workflow_ID());
    setAD_Role_ID(AD_Role_ID);
  } //	MWorkflowAccess
} //	MWorkflowAccess
