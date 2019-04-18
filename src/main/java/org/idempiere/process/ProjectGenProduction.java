package org.idempiere.process;

import org.compiere.accounting.MProduct;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.model.I_C_ProjectLine;
import org.compiere.process.SvrProcess;
import org.compiere.production.MProduction;
import org.compiere.production.MProject;
import org.compiere.production.MProjectLine;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.logging.Level;

/**
 * Generate Production from Project.
 *
 * @author Chuck Boecking
 */
public class ProjectGenProduction extends SvrProcess {
    /**
     * Project Parameter
     */
    private int m_C_Project_ID = 0;
    /**
     * Opt Project Line Parameter
     */
    private int m_C_ProjectPhase_ID = 0;
    /**
     * Opt Project Line Parameter
     */
    private int m_C_ProjectLine_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            switch (name) {
                case "C_Project_ID":
                    m_C_Project_ID = ((BigDecimal) iProcessInfoParameter.getParameter()).intValue();
                    break;
                case "C_ProjectPhase_ID":
                    m_C_ProjectPhase_ID = ((BigDecimal) iProcessInfoParameter.getParameter()).intValue();
                    break;
                case "C_ProjectLine_ID":
                    m_C_ProjectLine_ID = ((BigDecimal) iProcessInfoParameter.getParameter()).intValue();
                    break;
                default:
                    log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
                    break;
            }
        }
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO))
            log.info(
                    "doIt - C_Project_ID=" + m_C_Project_ID + " - C_ProjectLine_ID=" + m_C_ProjectLine_ID);
        if (m_C_ProjectLine_ID != 0) {
            MProjectLine projectLine = new MProjectLine(m_C_ProjectLine_ID);
            MProject project = new MProject(projectLine.getProjectId());
            createProduction(project, projectLine);
        } else if (m_C_ProjectPhase_ID != 0) {
            MProject project = new MProject(m_C_Project_ID);
            for (I_C_ProjectLine line : project.getPhaseLines(m_C_ProjectPhase_ID)) {
                if (line.isActive()) {
                    createProduction(project, line);
                }
            }
        } else {
            MProject project = new MProject(m_C_Project_ID);
            for (I_C_ProjectLine line : project.getLines()) {
                if (line.isActive()) {
                    createProduction(project, line);
                }
            }
        }
        return "";
    } //	doIt

    /**
     * Create PO from Planned Amt/Qty
     *
     * @param projectLine project line
     */
    private void createProduction(MProject project, I_C_ProjectLine projectLine) {
        if (projectLine.getProductId() == 0) {
            addLog(
                    project.getProjectId(),
                    project.getCreated(),
                    new BigDecimal(0),
                    "ISSUE: Line has no Product - ProjectLine:"
                            + projectLine.getLine()
                            + " Desc:"
                            + projectLine.getDescription(),
                    projectLine.getTableId(),
                    projectLine.getProjectLineId());
            return;
        }

        MProduct M_Product = new MProduct(projectLine.getProductId());
        if (!M_Product.isManufactured()) {
            addLog(
                    project.getProjectId(),
                    project.getCreated(),
                    new BigDecimal(0),
                    "ISSUE: Product is not manufactured - ProjectLine:"
                            + projectLine.getLine()
                            + " Desc:"
                            + projectLine.getDescription(),
                    projectLine.getTableId(),
                    projectLine.getProjectLineId());
            return;
        }

        if (projectLine.getProductionId() != 0) {
            addLog(projectLine.getLine(), null, null, "Line was produced previously");
            return;
        }

        //	Create to Production
        MProduction production = null;

        //	New Production Header
        production = new MProduction(projectLine);
        int AD_Org_ID = projectLine.getOrgId();
        if (AD_Org_ID == 0) {
            log.warning("createProductionfromProjectLine - orgId=0");
            AD_Org_ID = Env.getOrgId();
            if (AD_Org_ID != 0) projectLine.setOrgId(AD_Org_ID);
        }
        production.setBusinessPartnerId(project.getBusinessPartnerId());
        production.saveEx();

        //	update ProjectLine
        projectLine.setProductionId(production.getProductionId());
        projectLine.saveEx();

        addBufferLog(
                production.getProductionId(),
                production.getMovementDate(),
                new BigDecimal(0),
                MsgKt.getElementTranslation(Env.getADLanguage(), "M_Production_ID", false)
                        + ":"
                        + production.getDocumentNo(),
                production.getTableId(),
                production.getProductionId());
    } //	createProductionfromProjectLine
} //	ProjectGenProduction
