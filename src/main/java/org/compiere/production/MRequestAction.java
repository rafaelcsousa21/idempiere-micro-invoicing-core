package org.compiere.production;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Request History Model
 *
 * @author Jorg Janke
 * @version $Id: MRequestAction.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MRequestAction extends X_R_RequestAction {
  /** */
  private static final long serialVersionUID = 2902231219773596011L;

  /**
   * Persistency Constructor
   *
   * @param ctx context
   * @param R_RequestAction_ID id
   */
  public MRequestAction(Properties ctx, int R_RequestAction_ID) {
    super(ctx, R_RequestAction_ID);
  } //	MRequestAction

  /**
   * Load Construtor
   *
   * @param ctx context
   * @param rs result set
   */
  public MRequestAction(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MRequestAction

  /**
   * Parent Action Constructor
   *
   * @param request parent
   * @param newRecord new (copy all)
   */
  public MRequestAction(MRequest request, boolean newRecord) {
    this(request.getCtx(), 0);
    setClientOrg(request);
    setR_Request_ID(request.getR_Request_ID());
  } //	MRequestAction

    /**
   * Before Save
   *
   * @param newRecord new
   * @return true
   */
  protected boolean beforeSave(boolean newRecord) {
    return true;
  } //	beforeSave
} //	MRequestAction
