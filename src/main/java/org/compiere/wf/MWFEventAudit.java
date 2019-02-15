package org.compiere.wf;

import org.compiere.model.HasName;
import org.compiere.model.I_AD_WF_EventAudit;
import org.compiere.orm.Query;
import org.idempiere.common.util.Env;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Workflow Event Audit
 *
 * @author Jorg Janke
 * @version $Id: MWFEventAudit.java,v 1.3 2006/07/30 00:51:06 jjanke Exp $
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *     <li>BF [ 1801842 ] DB connection fix & improvements for concurrent threads
 *     <li>BF [ 1943723 ] WF Activity History is not translated
 */
public class MWFEventAudit extends X_AD_WF_EventAudit {
  /** */
  private static final long serialVersionUID = 3760514881823970823L;

  /**
   * Get Event Audit for node
   *
   * @param ctx context
   * @param AD_WF_Process_ID process
   * @param AD_WF_Node_ID optional node
   * @param trxName
   * @return event audit or null
   */
  public static MWFEventAudit[] get(
      Properties ctx, int AD_WF_Process_ID, int AD_WF_Node_ID) {
    ArrayList<Object> params = new ArrayList<Object>();
    StringBuilder whereClause = new StringBuilder("AD_WF_Process_ID=?");
    params.add(AD_WF_Process_ID);
    if (AD_WF_Node_ID > 0) {
      whereClause.append(" AND AD_WF_Node_ID=?");
      params.add(AD_WF_Node_ID);
    }
    List<MWFEventAudit> list =
        new Query(ctx, I_AD_WF_EventAudit.Table_Name, whereClause.toString())
            .setParameters(params)
            .setOrderBy(I_AD_WF_EventAudit.COLUMNNAME_AD_WF_EventAudit_ID)
            .list();
    //
    MWFEventAudit[] retValue = new MWFEventAudit[list.size()];
    list.toArray(retValue);
    return retValue;
  } //	get

  /**
   * Get Event Audit for node
   *
   * @param ctx context
   * @param AD_WF_Process_ID process
   * @param trxName
   * @return event audit or null
   */
  public static MWFEventAudit[] get(Properties ctx, int AD_WF_Process_ID) {
    return get(ctx, AD_WF_Process_ID, 0);
  } //	get

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param AD_WF_EventAudit_ID id
   * @param trxName transaction
   */
  public MWFEventAudit(Properties ctx, int AD_WF_EventAudit_ID) {
    super(ctx, AD_WF_EventAudit_ID);
  } //	MWFEventAudit

  /**
   * Load Cosntructors
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MWFEventAudit(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MWFEventAudit

  /**
   * Activity Constructor
   *
   * @param activity activity
   */
  public MWFEventAudit(MWFActivity activity) {
    super(activity.getCtx(), 0);
    setAD_WF_Process_ID(activity.getAD_WF_Process_ID());
    setAD_WF_Node_ID(activity.getAD_WF_Node_ID());
    setAD_Table_ID(activity.getAD_Table_ID());
    setRecord_ID(activity.getRecord_ID());
    //
    setAD_WF_Responsible_ID(activity.getAD_WF_Responsible_ID());
    setAD_User_ID(activity.getAD_User_ID());
    //
    setWFState(activity.getWFState());
    setEventType(X_AD_WF_EventAudit.EVENTTYPE_ProcessCreated);
    setElapsedTimeMS(Env.ZERO);
    //
    MWFNode node = activity.getNode();
    if (node != null && node.getId() != 0) {
      String action = node.getAction();
      if (MWFNode.ACTION_SetVariable.equals(action) || MWFNode.ACTION_UserChoice.equals(action)) {
        setAttributeName(node.getAttributeName());
        setOldValue(String.valueOf(activity.getAttributeValue()));
        if (MWFNode.ACTION_SetVariable.equals(action)) setNewValue(node.getAttributeValue());
      }
    }
  } //	MWFEventAudit

  /**
   * Get Node Name
   *
   * @return node name
   */
  public String getNodeName() {
    MWFNode node = MWFNode.get(getCtx(), getAD_WF_Node_ID());
    if (node.getId() == 0) return "?";
    return node.get_Translation(HasName.Companion.getCOLUMNNAME_Name());
  } //	getNodeName
} //	MWFEventAudit
