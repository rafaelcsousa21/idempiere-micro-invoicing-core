package org.idempiere.process;

import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.production.MProject;

import java.math.BigDecimal;
import java.util.logging.Level;

/**
 * Copy Project Details
 *
 * @author Jorg Janke
 * @version $Id: CopyFromProject.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class CopyFromProject extends SvrProcess {
    private int m_C_Project_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("C_Project_ID"))
                m_C_Project_ID = ((BigDecimal) iProcessInfoParameter.getParameter()).intValue();
            else log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message (clear text)
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        int To_C_Project_ID = getRecordId();
        if (log.isLoggable(Level.INFO))
            log.info("doIt - From C_Project_ID=" + m_C_Project_ID + " to " + To_C_Project_ID);
        if (To_C_Project_ID == 0) throw new IllegalArgumentException("Target C_Project_ID == 0");
        if (m_C_Project_ID == 0) throw new IllegalArgumentException("Source C_Project_ID == 0");
        MProject from = new MProject(getCtx(), m_C_Project_ID);
        MProject to = new MProject(getCtx(), To_C_Project_ID);
        //
        int no = to.copyDetailsFrom(from);

        return "@Copied@=" + no;
    } //	doIt
} //	CopyFromProject
