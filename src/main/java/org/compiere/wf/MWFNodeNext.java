package org.compiere.wf;

import kotliquery.Row;
import org.compiere.orm.Query;
import org.compiere.process.DocAction;
import org.idempiere.common.util.Env;
import org.idempiere.orm.PO;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Workflow Node Next - Transition
 *
 * @author Jorg Janke
 * @version $Id: MWFNodeNext.java,v 1.3 2006/10/06 00:42:24 jjanke Exp $
 */
public class MWFNodeNext extends X_AD_WF_NodeNext {
    /**
     *
     */
    private static final long serialVersionUID = -7925133581626319200L;
    /**
     * From (Split Eleemnt) is AND
     */
    public Boolean m_fromSplitAnd = null;
    /**
     * To (Join Element) is AND
     */
    public Boolean m_toJoinAnd = null;
    /**
     * Transition Conditions
     */
    private MWFNextCondition[] m_conditions = null;

    /**
     * Standard Costructor
     *
     * @param ctx               context
     * @param AD_WF_NodeNext_ID id
     * @param trxName           transaction
     */
    public MWFNodeNext(Properties ctx, int AD_WF_NodeNext_ID) {
        super(ctx, AD_WF_NodeNext_ID);
        if (AD_WF_NodeNext_ID == 0) {
            //	setAD_WF_Next_ID (0);
            //	setAD_WF_Node_ID (0);
            setEntityType(PO.ENTITYTYPE_UserMaintained); // U
            setIsStdUserWorkflow(false);
            setSeqNo(10); // 10
        }
    } //	MWFNodeNext

    /**
     * Default Constructor
     *
     * @param ctx context
     */
    public MWFNodeNext(Properties ctx, Row row) {
        super(ctx, row);
    } //	MWFNodeNext

    /**
     * Parent constructor
     *
     * @param parent        patent
     * @param AD_WF_Next_ID Next
     */
    public MWFNodeNext(MWFNode parent, int AD_WF_Next_ID) {
        this(parent.getCtx(), 0);
        setClientOrg(parent);
        setWorkflowNodeId(parent.getWorkflowNodeId());
        setWorkflowNextId(AD_WF_Next_ID);
    } //	MWFNodeNext

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
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MWFNodeNext[");
        sb.append(getSeqNo())
                .append(":Node=")
                .append(getWorkflowNodeId())
                .append("->Next=")
                .append(getWorkflowNextId());
        if (m_conditions != null) sb.append(",#").append(m_conditions.length);
        if (getDescription() != null && getDescription().length() > 0)
            sb.append(",").append(getDescription());
        sb.append("]");
        return sb.toString();
    } //	toString

    /**
     * *********************************************************************** Get Conditions
     *
     * @param requery true if requery
     * @return Array of Conditions
     */
    public MWFNextCondition[] getConditions(boolean requery) {
        if (!requery && m_conditions != null) return m_conditions;
        //
        final String whereClause = "AD_WF_NodeNext_ID=? AND clientId IN (0,?)";
        List<MWFNextCondition> list =
                new Query(getCtx(), MWFNextCondition.Table_Name, whereClause)
                        .setParameters(new Object[]{getWorkflowNodeNextId(), Env.getClientId(Env.getCtx())})
                        .setOnlyActiveRecords(true)
                        .setOrderBy(MWFNextCondition.COLUMNNAME_SeqNo)
                        .list();
        m_conditions = new MWFNextCondition[list.size()];
        list.toArray(m_conditions);
        return m_conditions;
    } //	getConditions

    /**
     * Is this a Valid Transition For ..
     *
     * @param activity activity
     * @return true if valid
     */
    public boolean isValidFor(MWFActivity activity) {
        if (isStdUserWorkflow()) {
            PO po = activity.getPO();
            if (po instanceof DocAction) {
                DocAction da = (DocAction) po;
                String docStatus = da.getDocStatus();
                String docAction = da.getDocAction();
                if (!DocAction.Companion.getACTION_Complete().equals(docAction)
                        || DocAction.Companion.getSTATUS_Completed().equals(docStatus)
                        || DocAction.Companion.getSTATUS_WaitingConfirmation().equals(docStatus)
                        || DocAction.Companion.getSTATUS_WaitingPayment().equals(docStatus)
                        || DocAction.Companion.getSTATUS_Voided().equals(docStatus)
                        || DocAction.Companion.getSTATUS_Closed().equals(docStatus)
                        || DocAction.Companion.getSTATUS_Reversed().equals(docStatus))
        /*
        || DocAction.ACTION_Complete.equals(docAction)
        || DocAction.ACTION_ReActivate.equals(docAction)
        || DocAction.ACTION_None.equals(docAction)
        || DocAction.ACTION_Post.equals(docAction)
        || DocAction.ACTION_Unlock.equals(docAction)
        || DocAction.ACTION_Invalidate.equals(docAction)	) */ {
                    if (log.isLoggable(Level.FINE))
                        log.fine("isValidFor =NO= StdUserWF - Status=" + docStatus + " - Action=" + docAction);
                    return false;
                }
            }
        }
        //	No Conditions
        if (getConditions(false).length == 0) {
            if (log.isLoggable(Level.FINE)) log.fine("#0 " + toString());
            return true;
        }
        //	First condition always AND
        boolean ok = m_conditions[0].evaluate(activity);
        for (int i = 1; i < m_conditions.length; i++) {
            if (m_conditions[i].isOr()) ok = ok || m_conditions[i].evaluate(activity);
            else ok = ok && m_conditions[i].evaluate(activity);
        } //	for all conditions
        if (log.isLoggable(Level.FINE)) log.fine("isValidFor (" + ok + ") " + toString());
        return ok;
    } //	isValidFor

    /**
     * Split Element is AND. Set by MWFNode.loadNodes
     *
     * @param fromSplitAnd The from Split And
     */
    public void setFromSplitAnd(boolean fromSplitAnd) {
        m_fromSplitAnd = new Boolean(fromSplitAnd);
    } //	setFromSplitAnd

} //	MWFNodeNext
