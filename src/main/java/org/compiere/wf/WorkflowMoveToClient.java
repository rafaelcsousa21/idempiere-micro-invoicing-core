package org.compiere.wf;

import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.idempiere.common.util.AdempiereSystemError;

import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdate;


/**
 * Move Workflow Customizations to Client
 *
 * @author Jorg Janke
 * @version $Id: WorkflowMoveToClient.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class WorkflowMoveToClient extends SvrProcess {
  /** The new Client */
  private int p_AD_Client_ID = 0;
  /** The Workflow */
  private int p_AD_Workflow_ID = 0;

  /** Prepare - e.g., get Parameters. */
  protected void prepare() {
    IProcessInfoParameter[] para = getParameter();
    for (int i = 0; i < para.length; i++) {
      String name = para[i].getParameterName();
      if (para[i].getParameter() == null) ;
      else if (name.equals("AD_Client_ID")) p_AD_Client_ID = para[i].getParameterAsInt();
      else if (name.equals("AD_Workflow_ID")) p_AD_Workflow_ID = para[i].getParameterAsInt();
      else log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
    }
  } //	prepare

  /**
   * Process
   *
   * @return message
   * @throws Exception
   */
  protected String doIt() throws Exception {
    if (log.isLoggable(Level.INFO))
      log.info("doIt - AD_Client_ID=" + p_AD_Client_ID + ", AD_Workflow_ID=" + p_AD_Workflow_ID);

    int changes = 0;
    //	WF
    String sql =
        "UPDATE AD_Workflow SET AD_Client_ID="
            + p_AD_Client_ID
            + " WHERE AD_Client_ID=0 AND EntityType NOT IN ('D','C')"
            + " AND AD_Workflow_ID="
            + p_AD_Workflow_ID;
    int no = executeUpdate(sql, get_TrxName());
    if (no == -1) throw new AdempiereSystemError("Error updating Workflow");
    changes += no;

    //	Node
    sql =
        "UPDATE AD_WF_Node SET AD_Client_ID="
            + p_AD_Client_ID
            + " WHERE AD_Client_ID=0 AND EntityType NOT IN ('D','C')"
            + " AND AD_Workflow_ID="
            + p_AD_Workflow_ID;
    no = executeUpdate(sql, get_TrxName());
    if (no == -1) throw new AdempiereSystemError("Error updating Workflow Node");
    changes += no;

    //	Node Next
    sql =
        "UPDATE AD_WF_NodeNext SET AD_Client_ID="
            + p_AD_Client_ID
            + " WHERE AD_Client_ID=0 AND EntityType NOT IN ('D','C')"
            + " AND (AD_WF_Node_ID IN (SELECT AD_WF_Node_ID FROM AD_WF_Node WHERE AD_Workflow_ID="
            + p_AD_Workflow_ID
            + ") OR AD_WF_Next_ID IN (SELECT AD_WF_Node_ID FROM AD_WF_Node WHERE AD_Workflow_ID="
            + p_AD_Workflow_ID
            + "))";
    no = executeUpdate(sql, get_TrxName());
    if (no == -1) throw new AdempiereSystemError("Error updating Workflow Transition");
    changes += no;

    //	Node Parameters
    sql =
        "UPDATE AD_WF_Node_Para SET AD_Client_ID="
            + p_AD_Client_ID
            + " WHERE AD_Client_ID=0 AND EntityType NOT IN ('D','C')"
            + " AND AD_WF_Node_ID IN (SELECT AD_WF_Node_ID FROM AD_WF_Node WHERE AD_Workflow_ID="
            + p_AD_Workflow_ID
            + ")";
    no = executeUpdate(sql, get_TrxName());
    if (no == -1) throw new AdempiereSystemError("Error updating Workflow Node Parameters");
    changes += no;

    //	Node Next Condition
    sql =
        "UPDATE AD_WF_NextCondition SET AD_Client_ID="
            + p_AD_Client_ID
            + " WHERE AD_Client_ID=0 AND EntityType NOT IN ('D','C')"
            + " AND AD_WF_NodeNext_ID IN ("
            + "SELECT AD_WF_NodeNext_ID FROM AD_WF_NodeNext "
            + "WHERE AD_WF_Node_ID IN (SELECT AD_WF_Node_ID FROM AD_WF_Node WHERE AD_Workflow_ID="
            + p_AD_Workflow_ID
            + ") OR AD_WF_Next_ID IN (SELECT AD_WF_Node_ID FROM AD_WF_Node WHERE AD_Workflow_ID="
            + p_AD_Workflow_ID
            + "))";
    no = executeUpdate(sql, get_TrxName());
    if (no == -1) throw new AdempiereSystemError("Error updating Workflow Transition Condition");
    changes += no;

    return "@Updated@ - #" + changes;
  } //	doIt
} //	WorkflowMoveToClient
