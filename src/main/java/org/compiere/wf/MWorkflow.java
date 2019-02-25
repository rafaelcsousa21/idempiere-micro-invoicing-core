package org.compiere.wf;

import org.compiere.model.HasName;
import org.compiere.model.IProcessInfo;
import org.compiere.model.I_AD_Workflow;
import org.compiere.orm.PO;
import org.compiere.orm.Query;
import org.compiere.util.Msg;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.exceptions.DBException;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * WorkFlow Model
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 2214883 ] Remove SQL code and Replace for Query
 * <li>BF [ 2665963 ] Copy Workflow name in Activity name
 * @version $Id: MWorkflow.java,v 1.4 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWorkflow extends X_AD_Workflow {
    /**
     *
     */
    private static final long serialVersionUID = 1905448790453650036L;
    /**
     * Single Cache
     */
    private static CCache<String, MWorkflow> s_cache =
            new CCache<String, MWorkflow>(I_AD_Workflow.Table_Name, 20);
    /**
     * Document Value Cache
     */
    private static CCache<String, MWorkflow[]> s_cacheDocValue =
            new CCache<String, MWorkflow[]>(
                    I_AD_Workflow.Table_Name, I_AD_Workflow.Table_Name + "_Document_Value", 5);
    /**
     * Static Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MWorkflow.class);
    /**
     * WF Nodes
     */
    private List<MWFNode> m_nodes = new ArrayList<MWFNode>();
    /**
     * Translated Name
     */
    private String m_name_trl = null;
    /**
     * Translated Description
     */
    private String m_description_trl = null;
    /**
     * Translated Help
     */
    private String m_help_trl = null;
    /**
     * Translation Flag
     */
    private boolean m_translated = false;

    /**
     * ************************************************************************ Create/Load Workflow
     *
     * @param ctx            Context
     * @param AD_Workflow_ID ID
     * @param trxName        transaction
     */
    public MWorkflow(Properties ctx, int AD_Workflow_ID) {
        super(ctx, AD_Workflow_ID);
        if (AD_Workflow_ID == 0) {
            setWFAccessLevel(X_AD_Workflow.ACCESSLEVEL_Organization);
            setAuthor("ComPiere, Inc.");
            setDurationUnit(X_AD_Workflow.DURATIONUNIT_Day);
            setDuration(1);
            setEntityType(PO.ENTITYTYPE_UserMaintained); // U
            setIsDefault(false);
            setPublishStatus(X_AD_Workflow.PUBLISHSTATUS_UnderRevision); // U
            setVersion(0);
            setCost(Env.ZERO);
            setWaitingTime(0);
            setWorkingTime(0);
            setIsBetaFunctionality(false);
        }
        loadTrl();
        loadNodes();
    } //	MWorkflow
    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MWorkflow(Properties ctx, ResultSet rs) {
        super(ctx, rs);
        loadTrl();
        loadNodes();
    } //	Workflow

    /**
     * Get Workflow from Cache
     *
     * @param ctx            context
     * @param AD_Workflow_ID id
     * @return workflow
     */
    public static MWorkflow get(Properties ctx, int AD_Workflow_ID) {
        String key = Env.getADLanguage(ctx) + "_" + AD_Workflow_ID;
        MWorkflow retValue = (MWorkflow) s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MWorkflow(ctx, AD_Workflow_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Get Doc Value Workflow
     *
     * @param ctx          context
     * @param AD_Client_ID client
     * @param AD_Table_ID  table
     * @return document value workflow array or null
     */
    public static MWorkflow[] getDocValue(
            Properties ctx,
            int AD_Client_ID,
            int AD_Table_ID,
            String trxName // Bug 1568766 Trx should be kept all along the road
    ) {
        String key = "C" + AD_Client_ID + "T" + AD_Table_ID;
        //	Reload
        if (s_cacheDocValue.isReset()) {
            final String whereClause = "WorkflowType=? AND IsValid=?";
            List<MWorkflow> workflows =
                    new Query(ctx, I_AD_Workflow.Table_Name, whereClause)
                            .setParameters(new Object[]{X_AD_Workflow.WORKFLOWTYPE_DocumentValue, true})
                            .setOnlyActiveRecords(true)
                            .setOrderBy("AD_Client_ID, AD_Table_ID")
                            .list();
            ArrayList<MWorkflow> list = new ArrayList<MWorkflow>();
            String oldKey = "";
            String newKey = null;
            for (MWorkflow wf : workflows) {
                newKey = "C" + wf.getClientId() + "T" + wf.getDBTableId();
                if (!newKey.equals(oldKey) && list.size() > 0) {
                    MWorkflow[] wfs = new MWorkflow[list.size()];
                    list.toArray(wfs);
                    s_cacheDocValue.put(oldKey, wfs);
                    list = new ArrayList<MWorkflow>();
                }
                oldKey = newKey;
                list.add(wf);
            }

            //	Last one
            if (list.size() > 0) {
                MWorkflow[] wfs = new MWorkflow[list.size()];
                list.toArray(wfs);
                s_cacheDocValue.put(oldKey, wfs);
            }
            if (s_log.isLoggable(Level.CONFIG)) s_log.config("#" + s_cacheDocValue.size());
        }
        //	Look for Entry
        MWorkflow[] retValue = (MWorkflow[]) s_cacheDocValue.get(key);
        // hengsin: this is not threadsafe
    /*
    //set trxName to all workflow instance
    if ( retValue != null && retValue.length > 0 )
    {
    	for(int i = 0; i < retValue.length; i++)
    	{
    		retValue[i].set_TrxName(trxName);
    	}
    }*/
        return retValue;
    } //	getDocValue

    /**
     * Load Translation
     */
    private void loadTrl() {
        if (Env.isBaseLanguage(getCtx(), "AD_Workflow") || getId() == 0) return;
        String sql =
                "SELECT Name, Description, Help FROM AD_Workflow_Trl WHERE AD_Workflow_ID=? AND AD_Language=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, getId());
            pstmt.setString(2, Env.getADLanguage(getCtx()));
            rs = pstmt.executeQuery();
            if (rs.next()) {
                m_name_trl = rs.getString(1);
                m_description_trl = rs.getString(2);
                m_help_trl = rs.getString(3);
                m_translated = true;
            }
        } catch (SQLException e) {
            // log.log(Level.SEVERE, sql, e);
            throw new DBException(e, sql);
        } finally {

            rs = null;
            pstmt = null;
        }
        if (log.isLoggable(Level.FINE)) log.fine("Translated=" + m_translated);
    } //	loadTrl

    /**
     * Load All Nodes
     */
    private void loadNodes() {
        m_nodes =
                new Query(getCtx(), MWFNode.Table_Name, "AD_WorkFlow_ID=?")
                        .setParameters(new Object[]{getId()})
                        .setOnlyActiveRecords(true)
                        .list();
        if (log.isLoggable(Level.FINE)) log.fine("#" + m_nodes.size());
    } //	loadNodes

    /**
     * Get Node with ID in Workflow
     *
     * @param AD_WF_Node_ID ID
     * @return node or null
     */
    protected MWFNode getNode(int AD_WF_Node_ID) {
        for (int i = 0; i < m_nodes.size(); i++) {
            MWFNode node = (MWFNode) m_nodes.get(i);
            if (node.getWorkflowNodeId() == AD_WF_Node_ID) return node;
        }
        return null;
    } //	getNode

    /**
     * Get The Nodes in Sequence Order
     *
     * @param AD_Client_ID client
     * @return Nodes in sequence
     */
    private MWFNode[] getNodesInOrder(int AD_Client_ID) {
        ArrayList<MWFNode> list = new ArrayList<MWFNode>();
        addNodesSF(list, getWorkflowNodeId(), AD_Client_ID); // 	start with first
        //	Remaining Nodes
        if (m_nodes.size() != list.size()) {
            //	Add Stand alone
            for (int n = 0; n < m_nodes.size(); n++) {
                MWFNode node = (MWFNode) m_nodes.get(n);
                if (!node.isActive()) continue;
                if (node.getClientId() == 0 || node.getClientId() == AD_Client_ID) {
                    boolean found = false;
                    for (int i = 0; i < list.size(); i++) {
                        MWFNode existing = (MWFNode) list.get(i);
                        if (existing.getWorkflowNodeId() == node.getWorkflowNodeId()) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        log.log(Level.WARNING, "Added Node w/o transition: " + node);
                        list.add(node);
                    }
                }
            }
        }
        //
        MWFNode[] nodeArray = new MWFNode[list.size()];
        list.toArray(nodeArray);
        return nodeArray;
    } //	getNodesInOrder

    /**
     * Add Nodes recursively (depth first) to Ordered List
     *
     * @param list list to add to
     * @param AD_WF_Node_ID start node id
     * @param clientId for client
     */
  /*private void addNodesDF (ArrayList<MWFNode> list, int AD_WF_Node_ID, int clientId)
  {
  	MWFNode node = getNode (AD_WF_Node_ID);
  	if (node != null && !list.contains(node))
  	{
  		list.add(node);
  		//	Get Dependent
  		MWFNodeNext[] nexts = node.getTransitions(clientId);
  		for (int i = 0; i < nexts.length; i++)
  		{
  			if (nexts[i].isActive())
  				addNodesDF (list, nexts[i].getAD_WF_Next_ID(), clientId);
  		}
  	}
  }	//	addNodesDF*/

    /**
     * Add Nodes recursively (sibling first) to Ordered List
     *
     * @param list          list to add to
     * @param AD_WF_Node_ID start node id
     * @param AD_Client_ID  for client
     */
    private void addNodesSF(ArrayList<MWFNode> list, int AD_WF_Node_ID, int AD_Client_ID) {
        ArrayList<MWFNode> tmplist = new ArrayList<MWFNode>();
        MWFNode node = getNode(AD_WF_Node_ID);
        if (node != null && (node.getClientId() == 0 || node.getClientId() == AD_Client_ID)) {
            if (!list.contains(node)) list.add(node);
            MWFNodeNext[] nexts = node.getTransitions(AD_Client_ID);
            for (int i = 0; i < nexts.length; i++) {
                MWFNode child = getNode(nexts[i].getWorkflowNextId());
                if (!child.isActive()) continue;
                if (child.getClientId() == 0 || child.getClientId() == AD_Client_ID) {
                    if (!list.contains(child)) {
                        list.add(child);
                        tmplist.add(child);
                    }
                }
            }
            //	Remainder Nodes not connected
            for (int i = 0; i < tmplist.size(); i++)
                addNodesSF(list, tmplist.get(i).getId(), AD_Client_ID);
        }
    } //	addNodesSF

    /**
     * Get Transitions (NodeNext) of ID
     *
     * @param AD_WF_Node_ID id
     * @param AD_Client_ID  for client
     * @return array of next nodes
     */
    public MWFNodeNext[] getNodeNexts(int AD_WF_Node_ID, int AD_Client_ID) {
        MWFNode[] nodes = getNodesInOrder(AD_Client_ID);
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].getWorkflowNodeId() == AD_WF_Node_ID) {
                return nodes[i].getTransitions(AD_Client_ID);
            }
        }
        return null;
    } //	getNext

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MWorkflow[");
        sb.append(getId()).append("-").append(getName()).append("]");
        return sb.toString();
    } //	toString

    /**
     * ************************************************************************ Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        validate();
        return true;
    } //	beforeSave

    /**
     * After Save.
     *
     * @param newRecord new record
     * @param success   success
     * @return true if save complete (if not overwritten true)
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (log.isLoggable(Level.FINE)) log.fine("Success=" + success);
        if (!success) {
            return false;
        }
        if (newRecord) {
            //	save all nodes -- Creating new Workflow
            MWFNode[] nodes = getNodesInOrder(0);
            for (int i = 0; i < nodes.length; i++) {
                nodes[i].saveEx();
            }
        }

        if (newRecord) {
            int AD_Role_ID = Env.getAD_Role_ID(getCtx());
            MWorkflowAccess wa = new MWorkflowAccess(this, AD_Role_ID);
            wa.saveEx();
        }
        //	Menu/Workflow
        else if (is_ValueChanged("IsActive")
                || is_ValueChanged(HasName.Companion.getCOLUMNNAME_Name())
                || is_ValueChanged(I_AD_Workflow.COLUMNNAME_Description)) {
      /* TODO Add DAP
      MMenu[] menues = MMenu.get(getCtx(), "AD_Workflow_ID=" + getAD_Workflow_ID(), null);
      for (int i = 0; i < menues.length; i++)
      {
      	menues[i].setIsActive(isActive());
      	menues[i].setName(getName());
      	menues[i].setDescription(getDescription());
      	menues[i].saveEx();
      }*/
        }

        return success;
    } //  afterSave

    /**
     * ************************************************************************ Start Workflow.
     *
     * @param pi Process Info (Record_ID)
     * @return process
     */
    public MWFProcess start(IProcessInfo pi) {
        MWFProcess retValue = null;
        try {
            retValue = new MWFProcess(this, pi);
            retValue.saveEx();
            pi.setSummary(Msg.getMsg(getCtx(), "Processing"));
            retValue.startWork();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getLocalizedMessage(), e);
            pi.setSummary(e.getMessage(), true);
            retValue = null;
            throw new AdempiereException(e);
        }

        if (retValue != null) {
            String summary = retValue.getProcessMsg();
            StateEngine state = retValue.getState();
            if (summary == null || summary.trim().length() == 0) summary = state.toString();
            pi.setSummary(summary, state.isTerminated() || state.isAborted());
        }

        return retValue;
    } //	MWFProcess

    /**
     * Get Duration Base in Seconds
     *
     * @return duration unit in seconds
     */
    public long getDurationBaseSec() {
        if (getDurationUnit() == null) return 0;
        else if (X_AD_Workflow.DURATIONUNIT_Second.equals(getDurationUnit())) return 1;
        else if (X_AD_Workflow.DURATIONUNIT_Minute.equals(getDurationUnit())) return 60;
        else if (X_AD_Workflow.DURATIONUNIT_Hour.equals(getDurationUnit())) return 3600;
        else if (X_AD_Workflow.DURATIONUNIT_Day.equals(getDurationUnit())) return 86400;
        else if (X_AD_Workflow.DURATIONUNIT_Month.equals(getDurationUnit())) return 2592000;
        else if (X_AD_Workflow.DURATIONUNIT_Year.equals(getDurationUnit())) return 31536000;
        return 0;
    } //	getDurationBaseSec

    /**
     * Get Duration CalendarField
     *
     * @return Calendar.MINUTE, etc.
     */
    public int getDurationCalendarField() {
        if (getDurationUnit() == null) return Calendar.MINUTE;
        else if (X_AD_Workflow.DURATIONUNIT_Second.equals(getDurationUnit())) return Calendar.SECOND;
        else if (X_AD_Workflow.DURATIONUNIT_Minute.equals(getDurationUnit())) return Calendar.MINUTE;
        else if (X_AD_Workflow.DURATIONUNIT_Hour.equals(getDurationUnit())) return Calendar.HOUR;
        else if (X_AD_Workflow.DURATIONUNIT_Day.equals(getDurationUnit())) return Calendar.DAY_OF_YEAR;
        else if (X_AD_Workflow.DURATIONUNIT_Month.equals(getDurationUnit())) return Calendar.MONTH;
        else if (X_AD_Workflow.DURATIONUNIT_Year.equals(getDurationUnit())) return Calendar.YEAR;
        return Calendar.MINUTE;
    } //	getDurationCalendarField

    /**
     * ************************************************************************ Validate workflow.
     * Sets Valid flag
     *
     * @return errors or ""
     */
    public String validate() {
        StringBuffer errors = new StringBuffer();
        //
        if (getWorkflowNodeId() == 0) errors.append(" - No Start Node");
        //
        if (X_AD_Workflow.WORKFLOWTYPE_DocumentValue.equals(getWorkflowType())
                && (getDocValueLogic() == null || getDocValueLogic().length() == 0))
            errors.append(" - No Document Value Logic");
        //

        //
        if (getWorkflowType().equals(MWorkflow.WORKFLOWTYPE_Manufacturing)) {
            this.setDBTableId(0);
        }

        //	final
        boolean valid = errors.length() == 0;
        setIsValid(valid);
        if (!valid) if (log.isLoggable(Level.INFO)) log.info("validate: " + errors);
        return errors.toString();
    } //	validate

} //	MWorkflow_ID
