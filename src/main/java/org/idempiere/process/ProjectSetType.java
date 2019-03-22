package org.idempiere.process;

import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.production.MProject;
import org.compiere.production.MProjectType;

import java.math.BigDecimal;
import java.util.logging.Level;

/**
 * Set Project Type
 *
 * @author Jorg Janke
 * @version $Id: ProjectSetType.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class ProjectSetType extends SvrProcess {
    /**
     * Project directly from Project
     */
    private int m_C_Project_ID = 0;
    /**
     * Project Type Parameter
     */
    private int m_C_ProjectType_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (int i = 0; i < para.length; i++) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            else if (name.equals("C_ProjectType_ID"))
                m_C_ProjectType_ID = ((BigDecimal) para[i].getParameter()).intValue();
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
        m_C_Project_ID = getRecordId();
        if (log.isLoggable(Level.INFO))
            log.info(
                    "doIt - C_Project_ID=" + m_C_Project_ID + ", C_ProjectType_ID=" + m_C_ProjectType_ID);
        //
        MProject project = new MProject(getCtx(), m_C_Project_ID);
        if (project.getProjectId() == 0 || project.getProjectId() != m_C_Project_ID)
            throw new IllegalArgumentException("Project not found C_Project_ID=" + m_C_Project_ID);
        if (project.getProjectTypeIdAsInt() > 0)
            throw new IllegalArgumentException(
                    "Project already has Type (Cannot overwrite) " + project.getProjectTypeId());
        //
        MProjectType type = new MProjectType(getCtx(), m_C_ProjectType_ID);
        if (type.getProjectTypeId() == 0 || type.getProjectTypeId() != m_C_ProjectType_ID)
            throw new IllegalArgumentException(
                    "Project Type not found C_ProjectType_ID=" + m_C_ProjectType_ID);

        //	Set & Copy if Service
        project.setProjectType(type);
        if (!project.save()) throw new Exception("@Error@");
        //
        return "@OK@";
    } //	doIt
} //	ProjectSetType
