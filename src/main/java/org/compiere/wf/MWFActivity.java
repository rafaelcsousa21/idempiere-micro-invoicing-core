package org.compiere.wf;

import kotliquery.Row;
import org.compiere.accounting.MClient;
import org.compiere.conversionrate.MConversionRate;
import org.compiere.crm.MUser;
import org.compiere.orm.MAttachment;
import org.compiere.orm.MColumn;
import org.compiere.orm.MOrg;
import org.compiere.orm.MOrgInfo;
import org.compiere.orm.MRefList;
import org.compiere.orm.MRole;
import org.compiere.orm.MTable;
import org.compiere.orm.MUserRoles;
import org.compiere.orm.PO;
import org.compiere.process.DocAction;
import org.compiere.process.MPInstance;
import org.compiere.process.MPInstancePara;
import org.compiere.process.MProcess;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DisplayType;
import org.compiere.util.Msg;
import org.compiere.util.SystemIDs;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Trace;
import org.idempiere.common.util.Util;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;

import static org.compiere.crm.MBaseUserKt.getWithRole;
import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Workflow Activity Model. Controlled by WF Process: set Node - startWork
 *
 * @author Jorg Janke
 * @version $Id: MWFActivity.java,v 1.4 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWFActivity extends X_AD_WF_Activity implements Runnable {
    /**
     *
     */
    private static final long serialVersionUID = -3282235931100223816L;
    /**
     * State Machine
     */
    private StateEngine m_state = null;
    /**
     * Workflow Node
     */
    private MWFNode m_node = null;
    /**
     * Audit
     */
    private MWFEventAudit m_audit = null;
    /**
     * Persistent Object
     */
    private PO m_po = null;
    /**
     * Document Status
     */
    private String m_docStatus = null;
    /**
     * New Value to save in audit
     */
    private String m_newValue = null;
    /** Transaction */
    // private Trx 				m_trx = null;
    /**
     * Process
     */
    private MWFProcess m_process = null;
    /**
     * List of email recipients
     */
    private ArrayList<String> m_emails = new ArrayList<String>();

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx               context
     * @param AD_WF_Activity_ID id
     * @param trxName           transaction
     */
    public MWFActivity(Properties ctx, int AD_WF_Activity_ID) {
        super(ctx, AD_WF_Activity_ID);
        if (AD_WF_Activity_ID == 0)
            throw new IllegalArgumentException("Cannot create new WF Activity directly");
        m_state = new StateEngine(getWorkflowState());
    } //	MWFActivity

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MWFActivity(Properties ctx, Row row) {
        super(ctx, row);
        m_state = new StateEngine(getWorkflowState());
    } //	MWFActivity

    /**
     * Parent Contructor
     *
     * @param process       process
     * @param AD_WF_Node_ID start node
     */
    public MWFActivity(MWFProcess process, int AD_WF_Node_ID) {
        super(process.getCtx(), 0);
        setWorkflowProcessId(process.getWorkFlowProcessId());
        setPriority(process.getPriority());
        //	Document Link
        setDBTableId(process.getDBTableId());
        setRecordId(process.getRecordId());
        // modified by Rob Klein
        setADClientID(process.getClientId());
        setOrgId(process.getOrgId());
        //	Status
        super.setWorkflowState(X_AD_WF_Activity.WFSTATE_NotStarted);
        m_state = new StateEngine(getWorkflowState());
        setProcessed(false);
        //	Set Workflow Node
        setWorkflowId(process.getWorkflowId());
        setWorkflowNodeId(AD_WF_Node_ID);
        //	Node Priority & End Duration
        MWFNode node = MWFNode.get(getCtx(), AD_WF_Node_ID);
        int priority = node.getPriority();
        if (priority != 0 && priority != getPriority()) setPriority(priority);
        long limitMS = node.getLimitMS();
        if (limitMS != 0) setEndWaitTime(new Timestamp(limitMS + System.currentTimeMillis()));
        //	Responsible
        setResponsible(process);
        saveEx();
        //
        m_audit = new MWFEventAudit(this);
        m_audit.saveEx();
        //
        m_process = process;
    } //	MWFActivity

    /**
     * Parent Contructor
     *
     * @param process       process
     * @param AD_WF_Node_ID start node
     * @param lastPO        PO from the previously executed node
     */
    public MWFActivity(MWFProcess process, int next_ID, PO lastPO) {
        this(process, next_ID);
        if (lastPO != null) {
            // Compare if the last PO is the same type and record needed here, if yes, use it
            if (lastPO.getTableId() == getDBTableId() && lastPO.getId() == getRecordId()) {
                m_po = lastPO;
            }
        }
    }

    /**
     * ************************************************************************ Get State
     *
     * @return state
     */
    public StateEngine getState() {
        return m_state;
    } //	getState

    /**
     * Set Activity State. It also validates the new state and if is valid, then create event audit
     * and call {@link MWFProcess#checkActivities(String, PO)}
     *
     * @param WFState
     */
    @Override
    public void setWorkflowState(String WFState) {
        if (m_state == null) m_state = new StateEngine(getWorkflowState());
        if (m_state.isClosed()) return;
        if (getWorkflowState().equals(WFState)) return;
        //
        if (m_state.isValidNewState(WFState)) {
            String oldState = getWorkflowState();
            if (log.isLoggable(Level.FINE)) log.fine(oldState + "->" + WFState + ", Msg=" + getTextMsg());
            super.setWorkflowState(WFState);
            m_state = new StateEngine(getWorkflowState());
            saveEx(); //	closed in MWFProcess.checkActivities()
            updateEventAudit();

            //	Inform Process
            if (m_process == null)
                m_process = new MWFProcess(getCtx(), getWorkflowProcessId());
            m_process.checkActivities(null, m_po);
        } else {
            String msg =
                    "Set WFState - Ignored Invalid Transformation - New="
                            + WFState
                            + ", Current="
                            + getWorkflowState();
            log.log(Level.SEVERE, msg);
            Trace.printStack();
            setTextMsg(msg);
            saveEx();
            // TODO: teo_sarca: throw exception ? please analyze the call hierarchy first
        }
    } //	setWFState

    /**
     * Is Activity closed
     *
     * @return true if closed
     */
    public boolean isClosed() {
        return m_state.isClosed();
    } //	isClosed

    /**
     * *********************************************************************** Update Event Audit
     */
    private void updateEventAudit() {
        //	log.fine("");
        getEventAudit();
        m_audit.setTextMsg(getTextMsg());
        m_audit.setWFState(getWorkflowState());
        if (m_newValue != null) m_audit.setNewValue(m_newValue);
        if (m_state.isClosed()) {
            m_audit.setEventType(MWFEventAudit.EVENTTYPE_ProcessCompleted);
            long ms = System.currentTimeMillis() - m_audit.getCreated().getTime();
            m_audit.setElapsedTimeMS(new BigDecimal(ms));
        } else m_audit.setEventType(MWFEventAudit.EVENTTYPE_StateChanged);
        m_audit.saveEx();
    } //	updateEventAudit

    /**
     * Get/Create Event Audit
     *
     * @return event
     */
    public MWFEventAudit getEventAudit() {
        if (m_audit != null) return m_audit;
        MWFEventAudit[] events =
                MWFEventAudit.get(getCtx(), getWorkflowProcessId(), getWorkflowNodeId());
        if (events == null || events.length == 0) m_audit = new MWFEventAudit(this);
        else m_audit = events[events.length - 1]; // 	last event
        return m_audit;
    } //	getEventAudit

    /**
     * ************************************************************************ Get Persistent Object
     * in Transaction
     *
     * @param trx transaction
     * @return po
     */
    public PO getPO() {
        if (m_po != null) {
            return m_po;
        }

        MTable table = MTable.get(getCtx(), getDBTableId());
        m_po = (PO) table.getPO(getRecordId());
        return m_po;
    } //	getPO


    /**
     * Get PO clientId
     *
     * @return client of PO
     */
    public int getPO_AD_ClientId() {
        if (m_po == null) getPO();
        if (m_po != null) return m_po.getClientId();
        return 0;
    } //	getPO_AD_Client_ID

    /**
     * Get Attribute Value (based on Node) of PO
     *
     * @return Attribute Value or null
     */
    public Object getAttributeValue() {
        MWFNode node = getNode();
        if (node == null) return null;
        int AD_Column_ID = node.getColumnId();
        if (AD_Column_ID == 0) return null;
        PO po = getPO();
        if (po.getId() == 0) return null;
        return po.getValueOfColumn(AD_Column_ID);
    } //	getAttributeValue

    /**
     * ************************************************************************ Set AD_WF_Node_ID.
     * (Re)Set to Not Started
     *
     * @param AD_WF_Node_ID now node
     */
    @Override
    public void setWorkflowNodeId(int AD_WF_Node_ID) {
        if (AD_WF_Node_ID == 0) throw new IllegalArgumentException("Workflow Node is not defined");
        super.setWorkflowNodeId(AD_WF_Node_ID);
        //
        if (!X_AD_WF_Activity.WFSTATE_NotStarted.equals(getWorkflowState())) {
            super.setWorkflowState(X_AD_WF_Activity.WFSTATE_NotStarted);
            m_state = new StateEngine(getWorkflowState());
        }
        if (isProcessed()) setProcessed(false);
    } //	setAD_WF_Node_ID

    /**
     * Get WF Node
     *
     * @return node
     */
    public MWFNode getNode() {
        if (m_node == null) m_node = MWFNode.get(getCtx(), getWorkflowNodeId());
        return m_node;
    } //	getNode

    /**
     * Get Node Help
     *
     * @return translated node help
     */
    public String getNodeHelp() {
        return getNode().getHelp(true);
    } //	getNodeHelp

    /**
     * Set Text Msg (add to existing)
     *
     * @param TextMsg
     */
    public void setTextMsg(String TextMsg) {
        if (TextMsg == null || TextMsg.length() == 0) return;
        String oldText = getTextMsg();
        if (oldText == null || oldText.length() == 0) super.setTextMsg(Util.trimSize(TextMsg, 1000));
        else if (TextMsg != null && TextMsg.length() > 0)
            super.setTextMsg(Util.trimSize(oldText + "\n - " + TextMsg, 1000));
    } //	setTextMsg

    /**
     * Add to Text Msg
     *
     * @param obj some object
     */
    public void addTextMsg(Object obj) {
        if (obj == null) return;
        //
        StringBuilder TextMsg = new StringBuilder();
        if (obj instanceof Exception) {
            Exception ex = (Exception) obj;
            if (ex.getMessage() != null && ex.getMessage().trim().length() > 0) {
                TextMsg.append(ex.toString());
            } else if (ex instanceof NullPointerException) {
                TextMsg.append(ex.getClass().getName());
            }
            while (ex != null) {
                StackTraceElement[] st = ex.getStackTrace();
                for (int i = 0; i < st.length; i++) {
                    StackTraceElement ste = st[i];
                    if (i == 0
                            || ste.getClassName().startsWith("org.compiere")
                            || ste.getClassName().startsWith("org.adempiere"))
                        TextMsg.append(" (").append(i).append("): ").append(ste.toString()).append("\n");
                }
                if (ex.getCause() instanceof Exception) ex = (Exception) ex.getCause();
                else ex = null;
            }
        } else {
            TextMsg.append(obj.toString());
        }
        //
        String oldText = getTextMsg();
        if (oldText == null || oldText.length() == 0)
            super.setTextMsg(Util.trimSize(TextMsg.toString(), 1000));
        else if (TextMsg != null && TextMsg.length() > 0)
            super.setTextMsg(Util.trimSize(oldText + "\n - " + TextMsg.toString(), 1000));
    } //	setTextMsg

    /**
     * Get WF State text
     *
     * @return state text
     */
    public String getWFStateText() {
        return MRefList.getListName(getCtx(), X_AD_WF_Activity.WFSTATE_AD_Reference_ID, getWorkflowState());
    } //	getWFStateText

    /**
     * Get Responsible
     *
     * @return responsible
     */
    public MWFResponsible getResponsible() {
        MWFResponsible resp = MWFResponsible.get(getCtx(), getWorkflowResponsibleId());
        return resp;
    } //	isInvoker

    /**
     * Set Responsible and User from Process / Node
     *
     * @param process process
     */
    private void setResponsible(MWFProcess process) {
        //	Responsible
        int AD_WF_Responsible_ID = getNode().getWorkflowResponsibleId();
        if (AD_WF_Responsible_ID == 0) // 	not defined on Node Level
            AD_WF_Responsible_ID = process.getWorkFlowResponsibleId();
        setWorkflowResponsibleId(AD_WF_Responsible_ID);
        MWFResponsible resp = getResponsible();

        //	User - Directly responsible
        int AD_User_ID = resp.getUserId();
        //	Invoker - get Sales Rep or last updater of document
        if (AD_User_ID == 0 && resp.isInvoker()) AD_User_ID = process.getUserId();
        //
        setUserId(AD_User_ID);
    } //	setResponsible

    /**
     * Is Invoker (no user & no role)
     *
     * @return true if invoker
     */
    public boolean isInvoker() {
        return getResponsible().isInvoker();
    } //	isInvoker

    /**
     * Get Approval User. If the returned user is the same, the document is approved.
     *
     * @param AD_User_ID    starting User
     * @param C_Currency_ID currency
     * @param amount        amount
     * @param AD_Org_ID     document organization
     * @param ownDocument   the document is owned by AD_User_ID
     * @return AD_User_ID - if -1 no Approver
     */
    public int getApprovalUser(
            int AD_User_ID, int C_Currency_ID, BigDecimal amount, int AD_Org_ID, boolean ownDocument) {
        //	Nothing to approve
        if (amount == null || amount.signum() == 0) return AD_User_ID;

        //	Starting user
        MUser user = MUser.get(getCtx(), AD_User_ID);
        if (log.isLoggable(Level.INFO))
            log.info("For User=" + user + ", Amt=" + amount + ", Own=" + ownDocument);

        MUser oldUser = null;
        while (user != null) {
            if (user.equals(oldUser)) {
                if (log.isLoggable(Level.INFO)) log.info("Loop - " + user.getName());
                user = null;
                break;
            }
            oldUser = user;
            if (log.isLoggable(Level.FINE)) log.fine("User=" + user.getName());
            //	Get Roles of User
            MRole[] roles = user.getRoles(AD_Org_ID);
            for (int i = 0; i < roles.length; i++) {
                MRole role = roles[i];
                if (ownDocument && !role.isCanApproveOwnDoc())
                    continue; //	find a role with allows them to approve own
                BigDecimal roleAmt = role.getAmtApproval();
                if (roleAmt == null || roleAmt.signum() == 0) continue;
                if (C_Currency_ID != role.getCurrencyId()
                        && role.getCurrencyId() != 0) // 	No currency = amt only
                {
                    roleAmt =
                            MConversionRate.convert(
                                    getCtx(), //	today & default rate
                                    roleAmt,
                                    role.getCurrencyId(),
                                    C_Currency_ID,
                                    getClientId(),
                                    AD_Org_ID);
                    if (roleAmt == null || roleAmt.signum() == 0) continue;
                }
                boolean approved = amount.compareTo(roleAmt) <= 0;
                if (log.isLoggable(Level.FINE))
                    log.fine(
                            "Approved="
                                    + approved
                                    + " - User="
                                    + user.getName()
                                    + ", Role="
                                    + role.getName()
                                    + ", ApprovalAmt="
                                    + roleAmt);
                if (approved) {
                    // Verify accumulated amount approval - FR [3123769] - Carlos Ruiz - GlobalQSS
                    BigDecimal roleAmtAcc = role.getAmtApprovalAccum();
                    Integer daysAmtAcc = role.getDaysApprovalAccum();
                    if (roleAmtAcc != null
                            && roleAmtAcc.signum() > 0
                            && daysAmtAcc != null
                            && daysAmtAcc.intValue() > 0) {
                        BigDecimal amtApprovedAccum =
                                getAmtAccum(m_po, daysAmtAcc.intValue(), user.getUserId());
                        amtApprovedAccum = amtApprovedAccum.add(amount); // include amount of current doc
                        approved = amtApprovedAccum.compareTo(roleAmtAcc) <= 0;
                        if (log.isLoggable(Level.INFO))
                            log.info(
                                    "ApprovedAccum="
                                            + approved
                                            + " - User="
                                            + user.getName()
                                            + ", Role="
                                            + role.getName()
                                            + ", ApprovalAmtAccum="
                                            + roleAmtAcc
                                            + ", AccumDocsApproved="
                                            + amtApprovedAccum
                                            + " in past "
                                            + daysAmtAcc.intValue()
                                            + " days");
                    }
                }

                if (approved) return user.getUserId();
            }

            //	**** Find next User
            //	Get Supervisor
            if (user.getSupervisorId() != 0) {
                user = MUser.get(getCtx(), user.getSupervisorId());
                if (log.isLoggable(Level.FINE)) log.fine("Supervisor: " + user.getName());
            } else {
                log.fine("No Supervisor");
                MOrg org = MOrg.get(getCtx(), AD_Org_ID);
                MOrgInfo orgInfo = org.getInfo();
                //	Get Org Supervisor
                if (orgInfo.getSupervisorId() != 0) {
                    user = MUser.get(getCtx(), orgInfo.getSupervisorId());
                    if (log.isLoggable(Level.FINE))
                        log.fine("Org=" + org.getName() + ",Supervisor: " + user.getName());
                } else {
                    log.fine("No Org Supervisor");
                    //	Get Parent Org Supervisor
                    if (orgInfo.getParentOrgId() != 0) {
                        org = MOrg.get(getCtx(), orgInfo.getParentOrgId());
                        orgInfo = org.getInfo();
                        if (orgInfo.getSupervisorId() != 0) {
                            user = MUser.get(getCtx(), orgInfo.getSupervisorId());
                            if (log.isLoggable(Level.FINE)) log.fine("Parent Org Supervisor: " + user.getName());
                        }
                    }
                }
            } //	No Supervisor
            // ownDocument should always be false for the next user
            ownDocument = false;
        } //	while there is a user to approve

        log.fine("No user found");
        return -1;
    } //	getApproval

    /**
     * Get The Amount of Accumulated Approvals of this same document within the indicated days
     *
     * @param po     - the document being approved
     * @param days   - the days to check
     * @param userid - user approving
     * @return amount - approval amount of approved documents within the indicated days
     */
    private BigDecimal getAmtAccum(PO po, int days, int userid) {
        BigDecimal amtaccum = Env.ZERO;
        String tablename = po.getTableName();
        MTable tablepo = MTable.get(getCtx(), po.getTableId());
        String checkSameSO = "";
        if (po.getColumnIndex("IsSOTrx") > 0) {
            checkSameSO = " AND doc.IsSOTrx='" + ((Boolean) po.getValue("IsSOTrx") ? "Y" : "N") + "'";
        }
        String checkSameReceipt = "";
        if (po.getColumnIndex("IsReceipt") > 0) {
            checkSameReceipt =
                    " AND doc.IsReceipt='" + ((Boolean) po.getValue("IsReceipt") ? "Y" : "N") + "'";
        }
        String checkDocAction = "";
        if (po.getColumnIndex("DocStatus") > 0) {
            checkDocAction = " AND DocStatus IN ('CO','CL')";
        }
        String sql =
                ""
                        + "SELECT DISTINCT doc."
                        + tablename
                        + "_ID "
                        + " FROM  "
                        + tablename
                        + " doc, "
                        + "       AD_WF_Activity a, "
                        + "       AD_WF_Node n, "
                        + "       AD_Column c "
                        + " WHERE a.AD_WF_Node_ID = n.AD_WF_Node_ID "
                        + "       AND n.AD_Column_ID = c.AD_Column_ID "
                        + "       AND a.AD_Table_ID = "
                        + po.getTableId()
                        + "       AND a.Record_ID = doc."
                        + tablename
                        + "_ID "
                        + "       AND a.Record_ID != "
                        + po.getId()
                        + "       AND c.ColumnName = 'IsApproved' "
                        + "       AND n.Action = 'C' "
                        + "       AND a.WFState = 'CC' "
                        + "       AND a.UpdatedBy = "
                        + userid
                        + "       AND a.Updated > Trunc(SYSDATE) - "
                        + (days - 1)
                        + checkSameSO
                        + checkSameReceipt
                        + checkDocAction;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int doc_id = rs.getInt(1);
                PO doc = (PO) tablepo.getPO(doc_id);
                BigDecimal docamt = ((DocAction) doc).getApprovalAmt();
                if (docamt != null) amtaccum = amtaccum.add(docamt);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, sql, e);
        } finally {

            rs = null;
            pstmt = null;
        }

        return amtaccum;
    }

    /**
     * ************************************************************************ Execute Work. Called
     * from MWFProcess.startNext Feedback to Process via setWFState -> checkActivities
     */
    public void run() {
        if (log.isLoggable(Level.INFO)) log.info("Node=" + getNode());
        m_newValue = null;

        // m_trx = Trx.get(, true);
        Savepoint savepoint = null;

        //
        try {

            if (!m_state.isValidAction(StateEngine.ACTION_Start)) {
                setTextMsg("State=" + getWorkflowState() + " - cannot start");
                addTextMsg(new Exception(""));
                setWorkflowState(StateEngine.STATE_Terminated);
                return;
            }
            //
            setWorkflowState(StateEngine.STATE_Running);

            if (getNode().getId() == 0) {
                setTextMsg("Node not found - AD_WF_Node_ID=" + getWorkflowNodeId());
                setWorkflowState(StateEngine.STATE_Aborted);
                return;
            }
            //	Do Work
            /** ** Trx Start *** */
            boolean done = performWork();

            /** ** Trx End *** */
            // teo_sarca [ 1708835 ]
            // Reason: if the commit fails the document should be put in Invalid state

            setWorkflowState(done ? StateEngine.STATE_Completed : StateEngine.STATE_Suspended);

        } catch (Exception e) {
            log.log(Level.WARNING, "" + getNode(), e);
            /** ** Trx Rollback *** */
            //
            if (e.getCause() != null) log.log(Level.WARNING, "Cause", e.getCause());

            String processMsg = e.getLocalizedMessage();
            if (processMsg == null || processMsg.length() == 0) processMsg = e.getMessage();
            setTextMsg(processMsg);
            // addTextMsg(e); // do not add the exception text
            boolean contextLost = false;
            if (e instanceof AdempiereException && "Context lost".equals(e.getMessage())) {
                contextLost = true;
                m_docStatus = DocAction.Companion.getSTATUS_Invalid();
            }
            try {
                if (contextLost) {
                    Env.getCtx()
                            .setProperty(
                                    "#clientId", (m_po != null ? Integer.toString(m_po.getClientId()) : "0"));
                    m_state = new StateEngine(X_AD_WF_Activity.WFSTATE_Running);
                    setProcessed(true);
                    setWorkflowState(StateEngine.STATE_Aborted);
                } else {
                    setWorkflowState(StateEngine.STATE_Terminated); // 	unlocks
                }

                //	Set Document Status
                if (m_po != null && m_po instanceof DocAction && m_docStatus != null) {
                    m_po.load();
                    DocAction doc = (DocAction) m_po;
                    doc.setDocStatus(m_docStatus);
                    m_po.saveEx();
                }
                if (m_process != null) {
                    m_process.setProcessMsg(this.getTextMsg());
                    m_process.saveEx();
                }
            } finally {
                if (contextLost) Env.getCtx().remove("#clientId");
            }

            throw new AdempiereException(e);
        }
    } //	run

    /**
     * Perform Work. Set Text Msg.
     *
     * @param trx transaction
     * @return true if completed, false otherwise
     * @throws Exception if error
     */
    private boolean performWork() throws Exception {
        if (log.isLoggable(Level.INFO))
            log.info(m_node + " [" + "]");
        m_docStatus = null;
        if (m_node.getPriority() != 0) // 	overwrite priority if defined
            setPriority(m_node.getPriority());
        String action = m_node.getAction();

        /* **** Sleep (Start/End) ***** */
        if (MWFNode.ACTION_WaitSleep.equals(action)) {
            if (log.isLoggable(Level.FINE)) log.fine("Sleep:WaitTime=" + m_node.getWaitTime());
            if (m_node.getWaitTime() == 0) // IDEMPIERE-73 Carlos Ruiz - globalqss
                return true; //	done
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, m_node.getWaitTime());
            setEndWaitTime(new Timestamp(cal.getTimeInMillis()));
            return false; //	not done
        }

        /* **** Document Action ***** */
        else if (MWFNode.ACTION_DocumentAction.equals(action)) {
            if (log.isLoggable(Level.FINE)) log.fine("DocumentAction=" + m_node.getDocAction());
            getPO();
            if (m_po == null)
                throw new Exception(
                        "Persistent Object not found - AD_Table_ID="
                                + getDBTableId()
                                + ", Record_ID="
                                + getRecordId());
            boolean success = false;
            String processMsg = null;
            DocAction doc = null;
            if (m_po instanceof DocAction) {
                doc = (DocAction) m_po;
                //
                try {
                    success = doc.processIt(m_node.getDocAction()); // 	** Do the work
                    setTextMsg(doc.getSummary());
                    processMsg = doc.getProcessMsg();
                    // Bug 1904717 - Invoice reversing has incorrect doc status
                    // Just prepare and complete return a doc status to take into account
                    // the rest of methods return boolean, so doc status must not be taken into account when
                    // not successful
                    if (DocAction.Companion.getACTION_Prepare().equals(m_node.getDocAction())
                            || DocAction.Companion.getACTION_Complete().equals(m_node.getDocAction())
                            || success) m_docStatus = doc.getDocStatus();
                } catch (Exception e) {
                    if (m_process != null) m_process.setProcessMsg(e.getLocalizedMessage());
                    throw e;
                }
                if (m_process != null) m_process.setProcessMsg(processMsg);
            } else
                throw new IllegalStateException(
                        "Persistent Object not DocAction - "
                                + m_po.getClass().getName()
                                + " - AD_Table_ID="
                                + getDBTableId()
                                + ", Record_ID="
                                + getRecordId());
            //
            if (!m_po.save()) {
                success = false;
                m_docStatus = null;
                processMsg = "SaveError";
            }
            if (!success) {
                if (processMsg == null || processMsg.length() == 0) {
                    processMsg = "PerformWork Error - " + m_node.toStringX();
                    if (doc != null) // 	problem: status will be rolled back
                        processMsg += " - DocStatus=" + doc.getDocStatus();
                }
                throw new Exception(processMsg);
            }
            return success;
        } //	DocumentAction

        /** **** Report ***** */
        else if (MWFNode.ACTION_AppsReport.equals(action)) {
            if (log.isLoggable(Level.FINE)) log.fine("Report:AD_Process_ID=" + m_node.getProcessId());
            //	Process
            MProcess process = MProcess.get(getCtx(), m_node.getProcessId());
            if (!process.isReport() || process.getReportViewId() == 0)
                throw new IllegalStateException("Not a Report AD_Process_ID=" + m_node.getProcessId());
            //
            ProcessInfo pi =
                    new ProcessInfo(
                            m_node.getName(true), m_node.getProcessId(), getDBTableId(), getRecordId());
            pi.setUserId(getUserId());
            pi.setADClientID(getClientId());
            MPInstance pInstance = new MPInstance(process, getRecordId());
            fillParameter(pInstance);
            pi.setProcessInstanceId(pInstance.getPInstanceId());
            File report = null;
            //	Notice
            int AD_Message_ID = SystemIDs.MESSAGE_WORKFLOWRESULT; // 	HARDCODED WorkflowResult
            MNote note = new MNote(getCtx(), AD_Message_ID, getUserId());
            note.setTextMsg(m_node.getName(true));
            note.setDescription(m_node.getDescription(true));
            note.setRecord(getDBTableId(), getRecordId());
            note.saveEx();
            //	Attachment
            MAttachment attachment =
                    new MAttachment(getCtx(), MNote.Table_ID, note.getNoteId());
            attachment.addEntry(report);
            attachment.setTextMsg(m_node.getName(true));
            attachment.saveEx();
            return true;
        }

        /** **** Process ***** */
        else if (MWFNode.ACTION_AppsProcess.equals(action)) {
            if (log.isLoggable(Level.FINE))
                log.fine("Process:AD_Process_ID=" + m_node.getProcessId());
            //	Process
            MProcess process = MProcess.get(getCtx(), m_node.getProcessId());
            MPInstance pInstance = new MPInstance(process, getRecordId());
            fillParameter(pInstance);
            //
            ProcessInfo pi =
                    new ProcessInfo(
                            m_node.getName(true), m_node.getProcessId(), getDBTableId(), getRecordId());
            pi.setUserId(getUserId());
            pi.setADClientID(getClientId());
            pi.setProcessInstanceId(pInstance.getPInstanceId());
            return process.processItWithoutTrxClose(pi);
        }

        /**
         * **** Start Task (Probably redundant; same can be achieved by attaching a Workflow node
         * sequentially) *****
         */
    /*
    else if (MWFNode.ACTION_AppsTask.equals(action))
    {
    	log.warning ("Task:AD_Task_ID=" + m_node.getAD_TaskId());
    	log.warning("Start Task is not implemented yet");
    }
    */

        /** **** EMail ***** */
        else if (MWFNode.ACTION_EMail.equals(action)) {
            if (log.isLoggable(Level.FINE))
                log.fine("EMail:EMailRecipient=" + m_node.getEMailRecipient());
            getPO();
            if (m_po == null)
                throw new Exception(
                        "Persistent Object not found - AD_Table_ID="
                                + getDBTableId()
                                + ", Record_ID="
                                + getRecordId());
            if (m_po instanceof DocAction) {
                m_emails = new ArrayList<String>();
                sendEMail();
                setTextMsg(m_emails.toString());
            } else {
                MClient client = MClient.get(getCtx(), getClientId());
                MMailText mailtext = new MMailText(getCtx(), getNode().getMailTemplateId());
                mailtext.setPO(m_po, false);

                String subject = getNode().getDescription() + ": " + mailtext.getMailHeader();

                String message = mailtext.getMailText(true) + "\n-----\n" + getNodeHelp();
                String to = getNode().getEMail();

                client.sendEMail(to, subject, message, null);
            }
            return true; //	done
        } //	EMail

        /** **** Set Variable ***** */
        else if (MWFNode.ACTION_SetVariable.equals(action)) {
            String value = m_node.getAttributeValue();
            if (log.isLoggable(Level.FINE))
                log.fine("SetVariable:AD_Column_ID=" + m_node.getColumnId() + " to " + value);
            MColumn column = m_node.getColumn();
            int dt = column.getReferenceId();
            return setVariable(value, dt, null);
        } //	SetVariable

        /** **** TODO Start WF Instance ***** */
        else if (MWFNode.ACTION_SubWorkflow.equals(action)) {
            log.warning("Workflow:AD_Workflow_ID=" + m_node.getWorkflowId());
            log.warning("Start WF Instance is not implemented yet");
        }

        /** **** User Choice ***** */
        else if (MWFNode.ACTION_UserChoice.equals(action)) {
            if (log.isLoggable(Level.FINE))
                log.fine("UserChoice:AD_Column_ID=" + m_node.getColumnId());
            //	Approval
            if (m_node.isUserApproval() && getPO() instanceof DocAction) {
                DocAction doc = (DocAction) m_po;
                boolean autoApproval = false;
                //	Approval Hierarchy
                if (isInvoker()) {
                    //	Set Approver
                    int startAD_User_ID = Env.getUserId(getCtx());
                    if (startAD_User_ID == 0) startAD_User_ID = doc.getDocumentUserId();
                    int nextAD_User_ID =
                            getApprovalUser(
                                    startAD_User_ID,
                                    doc.getCurrencyId(),
                                    doc.getApprovalAmt(),
                                    doc.getOrgId(),
                                    startAD_User_ID == doc.getDocumentUserId()); // 	own doc
                    if (nextAD_User_ID <= 0) {
                        m_docStatus = DocAction.Companion.getSTATUS_Invalid();
                        throw new AdempiereException(Msg.getMsg(getCtx(), "NoApprover"));
                    }
                    //	same user = approved
                    autoApproval = startAD_User_ID == nextAD_User_ID;
                    if (!autoApproval) setUserId(nextAD_User_ID);
                } else //	fixed Approver
                {
                    MWFResponsible resp = getResponsible();
                    // MZ Goodwill
                    // [ 1742751 ] Workflow: User Choice is not working
                    if (resp.isHuman()) {
                        autoApproval = resp.getUserId() == Env.getUserId(getCtx());
                        if (!autoApproval && resp.getUserId() != 0) setUserId(resp.getUserId());
                    } else if (resp.isRole()) {
                        MUserRoles[] urs = MUserRoles.getOfRole(getCtx(), resp.getRoleId());
                        for (int i = 0; i < urs.length; i++) {
                            if (urs[i].getUserId() == Env.getUserId(getCtx())) {
                                autoApproval = true;
                                break;
                            }
                        }
                    } else if (resp.isManual()) {
                        MWFActivityApprover[] approvers =
                                MWFActivityApprover.getOfActivity(getCtx(), getWorkflowActivityId());
                        for (int i = 0; i < approvers.length; i++) {
                            if (approvers[i].getUserId() == Env.getUserId(getCtx())) {
                                autoApproval = true;
                                break;
                            }
                        }
                    } else if (resp.isOrganization()) {
                        throw new AdempiereException("Support not implemented for " + resp);
                    } else {
                        throw new AdempiereException("@NotSupported@ " + resp);
                    }
                    // end MZ
                }
                //	done
                return autoApproval && doc.processIt(DocAction.Companion.getACTION_Approve()) && doc.save();
            } //	approval
            return false; //	wait for user
        }
        /** **** User Form ***** */
        else if (MWFNode.ACTION_UserForm.equals(action)) {
            if (log.isLoggable(Level.FINE)) log.fine("Form:AD_Form_ID=" + m_node.getFormId());
            return false;
        }
        /** **** User Window ***** */
        else if (MWFNode.ACTION_UserWindow.equals(action)) {
            if (log.isLoggable(Level.FINE)) log.fine("Window:AD_Window_ID=" + m_node.getWindowId());
            return false;
        }
        /** **** User Info ***** */
        else if (MWFNode.ACTION_UserInfo.equals(action)) {
            if (log.isLoggable(Level.FINE))
                log.fine("InfoWindow:AD_InfoWindow_ID=" + m_node.getInfoWindowId());
            return false;
        }
        //
        throw new IllegalArgumentException("Invalid Action (Not Implemented) =" + action);
    } //	performWork

    /**
     * Set Variable
     *
     * @param value       new Value
     * @param displayType display type
     * @param textMsg     optional Message
     * @return true if set
     * @throws Exception if error
     */
    private boolean setVariable(String value, int displayType, String textMsg)
            throws Exception {
        m_newValue = null;
        getPO();
        if (m_po == null)
            throw new Exception(
                    "Persistent Object not found - AD_Table_ID="
                            + getDBTableId()
                            + ", Record_ID="
                            + getRecordId());
        //	Set Value
        Object dbValue = null;
        if (value == null) ;
        else if (displayType == DisplayType.YesNo) dbValue = new Boolean("Y".equals(value));
        else if (DisplayType.isNumeric(displayType)) dbValue = new BigDecimal(value);
        else if (DisplayType.isID(displayType)) {
            MColumn column = MColumn.get(Env.getCtx(), getNode().getColumnId());
            String referenceTableName = column.getReferenceTableName();
            if (referenceTableName != null) {
                MTable refTable = MTable.get(Env.getCtx(), referenceTableName);
                dbValue = Integer.valueOf(value);
                boolean validValue = true;
                PO po = (PO) refTable.getPO((Integer) dbValue);
                if (po == null || po.getId() == 0) {
                    // foreign key does not exist
                    validValue = false;
                }
                if (validValue && po.getClientId() != Env.getClientId(Env.getCtx())) {
                    validValue = false;
                    if (po.getClientId() == 0) {
                        String accessLevel = refTable.getTableAccessLevel();
                        if (MTable.ACCESSLEVEL_All.equals(accessLevel)
                                || MTable.ACCESSLEVEL_SystemPlusClient.equals(accessLevel)) {
                            // client foreign keys are OK if the table has reference All or System+Client
                            validValue = true;
                        }
                    }
                }
                if (!validValue) {
                    throw new Exception(
                            "Persistent Object not updated - AD_Table_ID="
                                    + getDBTableId()
                                    + ", Record_ID="
                                    + getRecordId()
                                    + " - Value="
                                    + value
                                    + " is not valid for a foreign key");
                }
            }
        } else dbValue = value;
        if (!m_po.set_ValueOfColumnReturningBoolean(getNode().getColumnId(), dbValue)) {
            throw new Exception(
                    "Persistent Object not updated - AD_Table_ID="
                            + getDBTableId()
                            + ", Record_ID="
                            + getRecordId()
                            + " - Value="
                            + value
                            + " error : "
                            + CLogger.retrieveErrorString("check logs"));
        }
        m_po.saveEx();
        if (dbValue != null && !dbValue.equals(m_po.getValueOfColumn(getNode().getColumnId())))
            throw new Exception(
                    "Persistent Object not updated - AD_Table_ID="
                            + getDBTableId()
                            + ", Record_ID="
                            + getRecordId()
                            + " - Should="
                            + value
                            + ", Is="
                            + m_po.getValueOfColumn(m_node.getColumnId()));
        //	Info
        String msg = getNode().getAttributeName() + "=" + value;
        if (textMsg != null && textMsg.length() > 0) msg += " - " + textMsg;
        setTextMsg(msg);
        m_newValue = value;
        return true;
    } //	setVariable

    /**
     * Fill Parameter
     *
     * @param pInstance process instance
     * @param trx       transaction
     */
    private void fillParameter(MPInstance pInstance) {
        getPO();
        //
        MWFNodePara[] nParams = m_node.getParameters();
        MPInstancePara[] iParams = pInstance.getParameters();
        for (int pi = 0; pi < iParams.length; pi++) {
            MPInstancePara iPara = iParams[pi];
            for (int np = 0; np < nParams.length; np++) {
                MWFNodePara nPara = nParams[np];
                if (iPara.getParameterName().equals(nPara.getAttributeName())) {
                    String variableName = nPara.getAttributeValue();
                    if (log.isLoggable(Level.FINE)) log.fine(nPara.getAttributeName() + " = " + variableName);
                    //	Value - Constant/Variable
                    Object value = variableName;
                    if (variableName == null || (variableName != null && variableName.length() == 0))
                        value = null;
                    else if (variableName.indexOf('@') != -1 && m_po != null) // 	we have a variable
                    {
                        //	Strip
                        int index = variableName.indexOf('@');
                        String columnName = variableName.substring(index + 1);
                        index = columnName.indexOf('@');
                        if (index == -1) {
                            log.warning(nPara.getAttributeName() + " - cannot evaluate=" + variableName);
                            break;
                        }
                        columnName = columnName.substring(0, index);
                        index = m_po.getColumnIndex(columnName);
                        if (index != -1) {
                            value = m_po.getValue(index);
                        } else //	not a column
                        {
                            //	try Env
                            String env = Env.getContext(getCtx(), columnName);
                            if (env.length() == 0) {
                                log.warning(
                                        nPara.getAttributeName()
                                                + " - not column nor environment ="
                                                + columnName
                                                + "("
                                                + variableName
                                                + ")");
                                break;
                            } else value = env;
                        }
                    } //	@variable@

                    //	No Value
                    if (value == null) {
                        if (nPara.isMandatory())
                            log.warning(nPara.getAttributeName() + " - empty - mandatory!");
                        else if (log.isLoggable(Level.FINE)) log.fine(nPara.getAttributeName() + " - empty");
                        break;
                    }

                    //	Convert to Type
                    try {
                        if (DisplayType.isNumeric(nPara.getDisplayType())
                                || DisplayType.isID(nPara.getDisplayType())) {
                            BigDecimal bd = null;
                            if (value instanceof BigDecimal) bd = (BigDecimal) value;
                            else if (value instanceof Integer) bd = new BigDecimal(((Integer) value).intValue());
                            else bd = new BigDecimal(value.toString());
                            iPara.setProcessNumber(bd);
                            if (log.isLoggable(Level.FINE))
                                log.fine(nPara.getAttributeName() + " = " + variableName + " (=" + bd + "=)");
                        } else if (DisplayType.isDate(nPara.getDisplayType())) {
                            Timestamp ts = null;
                            if (value instanceof Timestamp) ts = (Timestamp) value;
                            else ts = Timestamp.valueOf(value.toString());
                            iPara.setProcessDate(ts);
                            if (log.isLoggable(Level.FINE))
                                log.fine(nPara.getAttributeName() + " = " + variableName + " (=" + ts + "=)");
                        } else {
                            iPara.setProcessString(value.toString());
                            if (log.isLoggable(Level.FINE))
                                log.fine(
                                        nPara.getAttributeName()
                                                + " = "
                                                + variableName
                                                + " (="
                                                + value
                                                + "=) "
                                                + value.getClass().getName());
                        }
                        if (!iPara.save()) log.warning("Not Saved - " + nPara.getAttributeName());
                    } catch (Exception e) {
                        log.warning(
                                nPara.getAttributeName()
                                        + " = "
                                        + variableName
                                        + " ("
                                        + value
                                        + ") "
                                        + value.getClass().getName()
                                        + " - "
                                        + e.getLocalizedMessage());
                    }
                    break;
                }
            } //	node parameter loop
        } //	instance parameter loop
    } //	fillParameter

    /**
     * ****************************** Send EMail
     */
    private void sendEMail() {
        DocAction doc = (DocAction) m_po;
        MMailText text = new MMailText(getCtx(), m_node.getMailTemplateId());
        text.setPO(m_po, true);
        //
        String subject = doc.getDocumentInfo() + ": " + text.getMailHeader();
        String message =
                text.getMailText(true) + "\n-----\n" + doc.getDocumentInfo() + "\n" + doc.getSummary();
        File pdf = null; //doc.createPDF();
        //
        MClient client = MClient.get(doc.getCtx(), doc.getClientId());

        //	Explicit EMail
        sendEMail(client, 0, m_node.getEMail(), subject, message, pdf, text.isHtml());
        //	Recipient Type
        String recipient = m_node.getEMailRecipient();
        //	email to document user
        if (recipient == null || recipient.length() == 0)
            sendEMail(client, doc.getDocumentUserId(), null, subject, message, pdf, text.isHtml());
        else if (recipient.equals(MWFNode.EMAILRECIPIENT_DocumentBusinessPartner)) {
            int index = m_po.getColumnIndex("AD_User_ID");
            if (index > 0) {
                Object oo = m_po.getValue(index);
                if (oo instanceof Integer) {
                    int AD_User_ID = ((Integer) oo).intValue();
                    if (AD_User_ID != 0)
                        sendEMail(client, AD_User_ID, null, subject, message, pdf, text.isHtml());
                    else log.fine("No User in Document");
                } else log.fine("Empty User in Document");
            } else log.fine("No User Field in Document");
        } else if (recipient.equals(MWFNode.EMAILRECIPIENT_DocumentOwner))
            sendEMail(client, doc.getDocumentUserId(), null, subject, message, pdf, text.isHtml());
        else if (recipient.equals(MWFNode.EMAILRECIPIENT_WFResponsible)) {
            MWFResponsible resp = getResponsible();
            if (resp.isInvoker())
                sendEMail(client, doc.getDocumentUserId(), null, subject, message, pdf, text.isHtml());
            else if (resp.isHuman())
                sendEMail(client, resp.getUserId(), null, subject, message, pdf, text.isHtml());
            else if (resp.isRole()) {
                MRole role = resp.getRole();
                if (role != null) {
                    MUser[] users = getWithRole(role);
                    for (int i = 0; i < users.length; i++)
                        sendEMail(client, users[i].getUserId(), null, subject, message, pdf, text.isHtml());
                }
            } else if (resp.isOrganization()) {
                MOrgInfo org = MOrgInfo.get(getCtx(), m_po.getOrgId());
                if (org.getSupervisorId() == 0) {
                    if (log.isLoggable(Level.FINE))
                        log.fine("No Supervisor for AD_Org_ID=" + m_po.getOrgId());
                } else {
                    sendEMail(client, org.getSupervisorId(), null, subject, message, pdf, text.isHtml());
                }
            }
        }
    } //	sendEMail

    /**
     * Send actual EMail
     *
     * @param client     client
     * @param AD_User_ID user
     * @param email      email string
     * @param subject    subject
     * @param message    message
     * @param pdf        attachment
     * @param isHtml     isHtml
     */
    private void sendEMail(
            MClient client,
            int AD_User_ID,
            String email,
            String subject,
            String message,
            File pdf,
            boolean isHtml) {
        if (AD_User_ID != 0) {
            MUser user = new MUser(getCtx(), AD_User_ID);
            email = user.getEMail();
            if (email != null && email.length() > 0) {
                email = email.trim();
                if (!m_emails.contains(email)) {
                    client.sendEMail(null, user, subject, message, pdf, isHtml);
                    m_emails.add(email);
                }
            } else if (log.isLoggable(Level.INFO)) log.info("No EMail for User " + user.getName());
        } else if (email != null && email.length() > 0) {
            //	Just one
            if (email.indexOf(';') == -1) {
                email = email.trim();
                if (!m_emails.contains(email)) {
                    client.sendEMail(email, subject, message, pdf, isHtml);
                    m_emails.add(email);
                }
                return;
            }
            //	Multiple EMail
            StringTokenizer st = new StringTokenizer(email, ";");
            while (st.hasMoreTokens()) {
                String email1 = st.nextToken().trim();
                if (email1.length() == 0) continue;
                if (!m_emails.contains(email1)) {
                    client.sendEMail(email1, subject, message, pdf, isHtml);
                    m_emails.add(email1);
                }
            }
        }
    } //	sendEMail

    /**
     * ************************************************************************ Does the underlying PO
     * (!) object have a PDF Attachment
     *
     * @return true if there is a pdf attachment
     */
  /*
  public boolean isPdfAttachment()
  {
  	if (getPO() == null)
  		return false;
  	return m_po.isPdfAttachment();
  }	//	isPDFAttachment

  /**
   * 	Get PDF Attachment of underlying PO (!) object
   *	@return pdf data or null
   */
  /*
  	public byte[] getPdfAttachment()
  	{
  		if (getPO() == null)
  			return null;
  		return m_po.getPdfAttachment();
  	}	//	getPdfAttachment
  */

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MWFActivity[");
        sb.append(getId()).append(",Node=");
        if (m_node == null) sb.append(getWorkflowNodeId());
        else sb.append(m_node.getName());
        sb.append(",State=")
                .append(getWorkflowState())
                .append(",AD_User_ID=")
                .append(getUserId())
                .append(",")
                .append(getCreated())
                .append("]");
        return sb.toString();
    } //	toString

} //	MWFActivity
