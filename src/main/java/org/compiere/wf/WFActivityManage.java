package org.compiere.wf;

import org.compiere.crm.MUser;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;

import java.util.logging.Level;

/**
 * Manage Workflow Activity
 *
 * @author Jorg Janke
 * @version $Id: WFActivityManage.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class WFActivityManage extends SvrProcess {
    /**
     * Abort It
     */
    private boolean p_IsAbort = false;
    /**
     * New User
     */
    private int p_AD_User_ID = 0;
    /**
     * New Responsible
     */
    private int p_AD_WF_Responsible_ID = 0;
    /**
     * Record
     */
    private int p_AD_WF_Activity_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("IsAbort")) p_IsAbort = "Y".equals(iProcessInfoParameter.getParameter());
            else if (name.equals("AD_User_ID")) p_AD_User_ID = iProcessInfoParameter.getParameterAsInt();
            else if (name.equals("AD_WF_Responsible_ID"))
                p_AD_WF_Responsible_ID = iProcessInfoParameter.getParameterAsInt();
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
        p_AD_WF_Activity_ID = getRecordId();
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message (variables are parsed)
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        MWFActivity activity = new MWFActivity(getCtx(), p_AD_WF_Activity_ID);
        if (log.isLoggable(Level.INFO)) log.info("doIt - " + activity);

        MUser user = MUser.get(getCtx(), getUserId());
        //	Abort
        if (p_IsAbort) {
            String msg = user.getName() + ": Abort";
            activity.setTextMsg(msg);
            activity.setUserId(getUserId());
            // 2007-06-14, matthiasO.
            // Set the 'processed'-flag when an activity is aborted; not setting this flag
            // will leave the activity in an "unmanagable" state
            activity.setProcessed(true);
            activity.setWorkflowState(StateEngine.STATE_Aborted);
            return msg;
        }
        String msg = null;
        //	Change User
        if (p_AD_User_ID != 0 && activity.getUserId() != p_AD_User_ID) {
            MUser from = MUser.get(getCtx(), activity.getUserId());
            MUser to = MUser.get(getCtx(), p_AD_User_ID);
            msg = user.getName() + ": " + from.getName() + " -> " + to.getName();
            activity.setTextMsg(msg);
            activity.setUserId(p_AD_User_ID);
        }
        //	Change Responsible
        if (p_AD_WF_Responsible_ID != 0
                && activity.getWorkflowResponsibleId() != p_AD_WF_Responsible_ID) {
            MWFResponsible from = MWFResponsible.get(getCtx(), activity.getWorkflowResponsibleId());
            MWFResponsible to = MWFResponsible.get(getCtx(), p_AD_WF_Responsible_ID);
            String msg1 = user.getName() + ": " + from.getName() + " -> " + to.getName();
            activity.setTextMsg(msg1);
            activity.setWorkflowResponsibleId(p_AD_WF_Responsible_ID);
            if (msg == null) msg = msg1;
            else msg += " - " + msg1;
        }
        //
        activity.saveEx();

        return msg;
    } //	doIt
} //	WFActivityManage
