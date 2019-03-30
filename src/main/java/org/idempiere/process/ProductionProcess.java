package org.idempiere.process;

import org.compiere.model.IProcessInfoParameter;
import org.compiere.model.I_M_ProductionPlan;
import org.compiere.orm.Query;
import org.compiere.process.ProcessInfo;
import org.compiere.process.SvrProcess;
import org.compiere.production.MProduction;
import org.compiere.production.MProductionLine;
import org.compiere.production.MProductionPlan;
import org.compiere.server.ServerProcessCtl;
import org.idempiere.common.util.AdempiereUserError;
import org.idempiere.common.util.Env;

import java.util.List;
import java.util.logging.Level;

/**
 * Process to create production lines based on the plans defined for a particular production header
 *
 * @author Paul Bowden
 */
public class ProductionProcess extends SvrProcess {

    private int p_M_Production_ID = 0;
    private MProduction m_production = null;

    public static int procesProduction(
            MProduction production) {
        ProcessInfo pi = ServerProcessCtl.runDocumentActionWorkflow(production, "CO");
        if (pi.isError()) {
            throw new RuntimeException(pi.getSummary());
        } else {
            if (production.isUseProductionPlan()) {
                Query planQuery =
                        new Query(
                                Env.getCtx(),
                                I_M_ProductionPlan.Table_Name,
                                "M_ProductionPlan.M_Production_ID=?"
                        );
                List<MProductionPlan> plans =
                        planQuery.setParameters(production.getProductionId()).list();
                int linesCount = 0;
                for (MProductionPlan plan : plans) {
                    MProductionLine[] lines = plan.getLines();
                    linesCount += lines.length;
                }
                return linesCount;
            } else {
                return production.getLines().length;
            }
        }
    }

    protected void prepare() {
        p_M_Production_ID = getRecordId();
        if (p_M_Production_ID > 0)
            m_production = new MProduction(getCtx(), p_M_Production_ID);
    } // prepare

    @Override
    protected String doIt() throws Exception {
        if (m_production == null || m_production.getId() == 0)
            throw new AdempiereUserError("Could not load production header");

        try {
            int processed = ProductionProcess.procesProduction(m_production);
            return "@Processed@ #" + processed;
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return e.getMessage();
        }
    }
}
