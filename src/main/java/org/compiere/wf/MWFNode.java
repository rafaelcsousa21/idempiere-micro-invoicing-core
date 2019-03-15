package org.compiere.wf;

import kotliquery.Row;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.orm.MColumn;
import org.compiere.orm.MRole;
import org.compiere.orm.Query;
import org.compiere.util.Msg;
import org.idempiere.common.exceptions.DBException;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Workflow Node Model
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 2214883 ] Remove SQL code and Replace for Query
 * <li>BF [ 2815732 ] MWFNode.getWorkflow not working in trx
 * https://sourceforge.net/tracker/?func=detail&aid=2815732&group_id=176962&atid=879332
 * @version $Id: MWFNode.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWFNode extends X_AD_WF_Node {
    /**
     *
     */
    private static final long serialVersionUID = 4330589837679937718L;
    /**
     * Cache
     */
    private static CCache<String, MWFNode> s_cache =
            new CCache<String, MWFNode>(I_AD_WF_Node.Table_Name, 50);
    /**
     * Next Modes
     */
    private List<MWFNodeNext> m_next = new ArrayList<MWFNodeNext>();
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
     * Column
     */
    private MColumn m_column = null;
    /**
     * Process Parameters
     */
    private MWFNodePara[] m_paras = null;
    /**
     * Duration Base MS
     */
    private long m_durationBaseMS = -1;

    /**
     * ************************************************************************ Standard Constructor -
     * save to cache
     *
     * @param ctx           context
     * @param AD_WF_Node_ID id
     * @param trxName       transaction
     */
    public MWFNode(Properties ctx, int AD_WF_Node_ID) {
        super(ctx, AD_WF_Node_ID);
        if (AD_WF_Node_ID == 0) {
            //	setAD_WF_Node_ID (0);
            //	setAD_Workflow_ID (0);
            //	setValue (null);
            //	setName (null);
            setAction(X_AD_WF_Node.ACTION_WaitSleep);
            setCost(Env.ZERO);
            setDuration(0);
            setEntityType(org.idempiere.orm.PO.ENTITYTYPE_UserMaintained); // U
            setIsCentrallyMaintained(true); // Y
            setJoinElement(X_AD_WF_Node.JOINELEMENT_XOR); // X
            setLimit(0);
            setSplitElement(X_AD_WF_Node.SPLITELEMENT_XOR); // X
            setWaitingTime(0);
            setXPosition(0);
            setYPosition(0);
        }
        if (getWorkflowNodeId() > 0) {
            loadNext();
            loadTrl();
        }
    } //	MWFNode

    /**
     * Parent Constructor
     *
     * @param wf    workflow (parent)
     * @param Value value
     * @param Name  name
     */
    public MWFNode(MWorkflow wf, String Value, String Name) {
        this(wf.getCtx(), 0);
        setClientOrg(wf);
        setWorkflowId(wf.getWorkflowId());
        setSearchKey(Value);
        setName(Name);
        m_durationBaseMS = wf.getDurationBaseSec() * 1000;
    } //	MWFNode

    /**
     * Load Constructor - save to cache
     *
     * @param ctx     context
     * @param rs      result set to load info from
     * @param trxName transaction
     */
    public MWFNode(Properties ctx, Row row) {
        super(ctx, row);
        loadNext();
        loadTrl();
        //	Save to Cache
        String key = null;
        Integer wfnodeid = row.intOrNull("AD_WF_Node_ID");
        if (wfnodeid != null && wfnodeid > 0)
            key = Env.getADLanguage(ctx) + "_" + wfnodeid;
        if (key != null && !s_cache.containsKey(key)) s_cache.put(key, this);
    } //	MWFNode

    /**
     * Get WF Node from Cache
     *
     * @param ctx           context
     * @param AD_WF_Node_ID id
     * @return MWFNode
     */
    public static MWFNode get(Properties ctx, int AD_WF_Node_ID) {
        String key = Env.getADLanguage(ctx) + "_" + AD_WF_Node_ID;
        MWFNode retValue = (MWFNode) s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MWFNode(ctx, AD_WF_Node_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Set Client Org
     *
     * @param AD_Client_ID client
     * @param AD_Org_ID    org
     */
    public void setClientOrg(int AD_Client_ID, int AD_Org_ID) {
        super.setClientOrg(AD_Client_ID, AD_Org_ID);
    } //	setClientOrg

    /**
     * Load Next
     */
    private void loadNext() {
        m_next =
                new Query(getCtx(), MWFNodeNext.Table_Name, "AD_WF_Node_ID=?")
                        .setParameters(new Object[]{getId()})
                        .setOnlyActiveRecords(true)
                        .setOrderBy(MWFNodeNext.COLUMNNAME_SeqNo)
                        .list();
        boolean splitAnd = X_AD_WF_Node.SPLITELEMENT_AND.equals(getSplitElement());
        for (MWFNodeNext next : m_next) {
            next.setFromSplitAnd(splitAnd);
        }
        if (log.isLoggable(Level.FINE)) log.fine("#" + m_next.size());
    } //	loadNext

    /**
     * Load Translation
     */
    private void loadTrl() {
        if (Env.isBaseLanguage(getCtx(), "AD_Workflow") || getId() == 0) return;
        final String sql =
                "SELECT Name, Description, Help FROM AD_WF_Node_Trl"
                        + " WHERE AD_WF_Node_ID=? AND AD_Language=?";
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
        if (log.isLoggable(Level.FINE)) log.fine("Trl=" + m_translated);
    } //	loadTrl

    /**
     * Get Number of Next Nodes
     *
     * @return number of next nodes
     */
    public int getNextNodeCount() {
        return m_next.size();
    } //	getNextNodeCount

    /**
     * Get the transitions
     *
     * @param AD_Client_ID for client
     * @return array of next nodes
     */
    public MWFNodeNext[] getTransitions(int AD_Client_ID) {
        loadNext();
        ArrayList<MWFNodeNext> list = new ArrayList<MWFNodeNext>();
        for (int i = 0; i < m_next.size(); i++) {
            MWFNodeNext next = m_next.get(i);
            if (next.getClientId() == 0 || next.getClientId() == AD_Client_ID) list.add(next);
        }
        MWFNodeNext[] retValue = new MWFNodeNext[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getNextNodes

    /**
     * ************************************************************************ Get Name
     *
     * @param translated translated
     * @return Name
     */
    public String getName(boolean translated) {
        if (translated && m_translated) return m_name_trl;
        return getName();
    } //	getName

    /**
     * Get Description
     *
     * @param translated translated
     * @return Description
     */
    public String getDescription(boolean translated) {
        if (translated && m_translated) return m_description_trl;
        return getDescription();
    } //	getDescription

    /**
     * Get Help
     *
     * @param translated translated
     * @return Name
     */
    public String getHelp(boolean translated) {
        if (translated && m_translated) return m_help_trl;
        return getHelp();
    } //	getHelp

    /**
     * Get Action Info
     *
     * @return info
     */
    public String getActionInfo() {
        String action = getAction();
        if (X_AD_WF_Node.ACTION_AppsProcess.equals(action))
            return "Process:AD_Process_ID=" + getProcessId();
        else if (X_AD_WF_Node.ACTION_DocumentAction.equals(action))
            return "DocumentAction=" + getDocAction();
        else if (X_AD_WF_Node.ACTION_AppsReport.equals(action))
            return "Report:AD_Process_ID=" + getProcessId();
        else if (X_AD_WF_Node.ACTION_AppsTask.equals(action))
            return "Task:AD_Task_ID=" + getTaskId();
        else if (X_AD_WF_Node.ACTION_SetVariable.equals(action))
            return "SetVariable:AD_Column_ID=" + getColumnId();
        else if (X_AD_WF_Node.ACTION_SubWorkflow.equals(action))
            return "Workflow:AD_Workflow_ID=" + getWorkflowId();
        else if (X_AD_WF_Node.ACTION_UserChoice.equals(action))
            return "UserChoice:AD_Column_ID=" + getColumnId();
    /*
    else if (ACTION_UserWorkbench.equals(action))
    	return "Workbench:?";*/
        else if (X_AD_WF_Node.ACTION_UserForm.equals(action))
            return "Form:AD_Form_ID=" + getFormId();
        else if (X_AD_WF_Node.ACTION_UserWindow.equals(action))
            return "Window:AD_Window_ID=" + getWindowId();
        else if (X_AD_WF_Node.ACTION_UserInfo.equals(action))
            return "Window:AD_InfoWindow_ID=" + getInfoWindowId();
        else if (X_AD_WF_Node.ACTION_WaitSleep.equals(action)) return "Sleep:WaitTime=" + getWaitTime();
        return "??";
    } //	getActionInfo

    /**
     * Get Attribute Name
     *
     * @return Attribute Name
     * @see X_AD_WF_Node#getAttributeName()
     */
    public String getAttributeName() {
        if (getColumnId() == 0) return super.getAttributeName();
        //	We have a column
        String attribute = super.getAttributeName();
        if (attribute != null && attribute.length() > 0) return attribute;
        setAttributeName(getColumn().getColumnName());
        return super.getAttributeName();
    } //	getAttributeName

    /**
     * Get Column
     *
     * @return column if valid
     */
    public MColumn getColumn() {
        if (getColumnId() == 0) return null;
        if (m_column == null) m_column = MColumn.get(getCtx(), getColumnId());
        return m_column;
    } //	getColumn

    /**
     * Is this an Approval setp?
     *
     * @return true if User Approval
     */
    public boolean isUserApproval() {
        if (!X_AD_WF_Node.ACTION_UserChoice.equals(getAction())) return false;
        return getColumn() != null && "IsApproved".equals(getColumn().getColumnName());
    } //	isApproval

    /**
     * Is this a User Choice step?
     *
     * @return true if User Choice
     */
    public boolean isUserChoice() {
        return X_AD_WF_Node.ACTION_UserChoice.equals(getAction());
    } //	isUserChoice

    /**
     * Is this a Manual user step?
     *
     * @return true if Window/Form/Workbench
     */
    public boolean isUserManual() {
        if (X_AD_WF_Node.ACTION_UserForm.equals(getAction())
                || X_AD_WF_Node.ACTION_UserWindow.equals(getAction())
                || X_AD_WF_Node.ACTION_UserInfo.equals(getAction())
            /*|| ACTION_UserWorkbench.equals(getAction())*/) return true;
        return false;
    } //	isUserManual

    /**
     * Get Duration Limit in ms
     *
     * @return duration limit in ms
     */
    public long getLimitMS() {
        long limit = super.getLimit();
        if (limit == 0) return 0;
        if (m_durationBaseMS == -1) m_durationBaseMS = getWorkflow().getDurationBaseSec() * 1000;
        return limit * m_durationBaseMS;
    } //	getLimitMS

    /**
     * ************************************************************************ Get Parameters
     *
     * @return array of parameters
     */
    public MWFNodePara[] getParameters() {
        if (m_paras == null) m_paras = MWFNodePara.getParameters(getCtx(), getWorkflowNodeId());
        return m_paras;
    } //	getParameters

    @Override
    public MWorkflow getWorkflow() {
        if (null == null) return MWorkflow.get(getCtx(), getWorkflowId());
        else return (MWorkflow) super.getWorkflow();
    }

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MWFNode[");
        sb.append(getId())
                .append("-")
                .append(getName())
                .append(",Action=")
                .append(getActionInfo())
                .append("]");
        return sb.toString();
    } //	toString

    /**
     * User String Representation
     *
     * @return info
     */
    public String toStringX() {
        StringBuilder sb = new StringBuilder("MWFNode[");
        sb.append(getName()).append("-").append(getActionInfo()).append("]");
        return sb.toString();
    } //	toStringX

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true if can be saved
     */
    @Override
    protected boolean beforeSave(boolean newRecord) {
        String action = getAction();
        if (action.equals(X_AD_WF_Node.ACTION_WaitSleep)) ;
        else if (action.equals(X_AD_WF_Node.ACTION_AppsProcess)
                || action.equals(X_AD_WF_Node.ACTION_AppsReport)) {
            if (getProcessId() == 0) {
                log.saveError("FillMandatory", Msg.getElement(getCtx(), "AD_Process_ID"));
                return false;
            }
        } else if (action.equals(X_AD_WF_Node.ACTION_AppsTask)) {
            if (getTaskId() == 0) {
                log.saveError("FillMandatory", Msg.getElement(getCtx(), "AD_Task_ID"));
                return false;
            }
        } else if (action.equals(X_AD_WF_Node.ACTION_DocumentAction)) {
            if (getDocAction() == null || getDocAction().length() == 0) {
                log.saveError("FillMandatory", Msg.getElement(getCtx(), "DocAction"));
                return false;
            }
        } else if (action.equals(X_AD_WF_Node.ACTION_EMail)) {
            if (getR_MailText_ID() == 0) {
                log.saveError("FillMandatory", Msg.getElement(getCtx(), "R_MailText_ID"));
                return false;
            }
        } else if (action.equals(X_AD_WF_Node.ACTION_SetVariable)) {
            if (getAttributeValue() == null) {
                log.saveError("FillMandatory", Msg.getElement(getCtx(), "AttributeValue"));
                return false;
            }
            if (getColumnId() > 0) {
                // validate that just advanced roles can manipulate secure content via workflows
                MColumn column = MColumn.get(getCtx(), getColumnId());
                if (column.isSecure() || column.isAdvanced()) {
                    if (!MRole.getDefault().isAccessAdvanced()) {
                        log.saveError("AccessTableNoUpdate", Msg.getElement(getCtx(), column.getColumnName()));
                        return false;
                    }
                }
            }
        } else if (action.equals(X_AD_WF_Node.ACTION_SubWorkflow)) {
            if (getWorkflowId() == 0) {
                log.saveError("FillMandatory", Msg.getElement(getCtx(), "AD_Workflow_ID"));
                return false;
            }
        } else if (action.equals(X_AD_WF_Node.ACTION_UserChoice)) {
            if (getColumnId() == 0) {
                log.saveError("FillMandatory", Msg.getElement(getCtx(), "AD_Column_ID"));
                return false;
            }
        } else if (action.equals(X_AD_WF_Node.ACTION_UserForm)) {
            if (getFormId() == 0) {
                log.saveError("FillMandatory", Msg.getElement(getCtx(), "AD_Form_ID"));
                return false;
            }
        } else if (action.equals(X_AD_WF_Node.ACTION_UserWindow)) {
            if (getWindowId() == 0) {
                log.saveError("FillMandatory", Msg.getElement(getCtx(), "AD_Window_ID"));
                return false;
            }
        } else if (action.equals(X_AD_WF_Node.ACTION_UserInfo)) {
            if (getInfoWindowId() == 0) {
                log.saveError("FillMandatory", Msg.getElement(getCtx(), "AD_InfoWindow_ID"));
                return false;
            }
        }
        //		else if (action.equals(ACTION_UserWorkbench))
        //		{
        //		&& getAD_Workbench_ID() == 0)
        //			log.saveError("FillMandatory", Msg.getElement(getCtx(), "AD_Workbench_ID"));
        //			return false;
        //		}

        return true;
    } //	beforeSave

    /**
     * After Save
     *
     * @param newRecord new
     * @param success   success
     * @return saved
     */
    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        return true;
    } //	afterSave

    /**
     * After Delete
     *
     * @param success success
     * @return deleted
     */
    @Override
    protected boolean afterDelete(boolean success) {
        return success;
    } //	afterDelete

} //	M_WFNext
