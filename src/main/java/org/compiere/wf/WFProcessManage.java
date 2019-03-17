package org.compiere.wf;

import org.compiere.crm.MUser;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;

import java.util.logging.Level;

/**
 * Manage Workflow Process
 *
 * @author Jorg Janke
 * @version $Id: WFProcessManage.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class WFProcessManage extends SvrProcess {
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
    private int p_AD_WF_Process_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (int i = 0; i < para.length; i++) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) ;
            else if (name.equals("IsAbort")) p_IsAbort = "Y".equals(para[i].getParameter());
            else if (name.equals("AD_User_ID")) p_AD_User_ID = para[i].getParameterAsInt();
            else if (name.equals("AD_WF_Responsible_ID"))
                p_AD_WF_Responsible_ID = para[i].getParameterAsInt();
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
        p_AD_WF_Process_ID = getRecordId();
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message (variables are parsed)
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        MWFProcess process = new MWFProcess(getCtx(), p_AD_WF_Process_ID);
        if (log.isLoggable(Level.INFO)) log.info("doIt - " + process);

        MUser user = MUser.get(getCtx(), getUserId());
        //	Abort
        if (p_IsAbort) {
            String msg = user.getName() + ": Abort";
            process.setTextMsg(msg);
            process.setUserId(getUserId());
            process.setWorkflowState(StateEngine.STATE_Aborted);
            return msg;
        }
        String msg = null;
        //	Change User
        if (p_AD_User_ID != 0 && process.getUserId() != p_AD_User_ID) {
            MUser from = MUser.get(getCtx(), process.getUserId());
            MUser to = MUser.get(getCtx(), p_AD_User_ID);
            msg = user.getName() + ": " + from.getName() + " -> " + to.getName();
            process.setTextMsg(msg);
            process.setUserId(p_AD_User_ID);
        }
        //	Change Responsible
        if (p_AD_WF_Responsible_ID != 0
                && process.getWorkFlowResponsibleId() != p_AD_WF_Responsible_ID) {
            MWFResponsible from = MWFResponsible.get(getCtx(), process.getWorkFlowResponsibleId());
            MWFResponsible to = MWFResponsible.get(getCtx(), p_AD_WF_Responsible_ID);
            String msg1 = user.getName() + ": " + from.getName() + " -> " + to.getName();
            process.setTextMsg(msg1);
            process.setWorkFlowResponsibleId(p_AD_WF_Responsible_ID);
            if (msg == null) msg = msg1;
            else msg += " - " + msg1;
        }
        //
        process.saveEx();

        return "OK";
    } //	doIt
} //	WFProcessManage
