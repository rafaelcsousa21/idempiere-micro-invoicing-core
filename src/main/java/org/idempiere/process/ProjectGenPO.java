package org.idempiere.process;

import org.compiere.accounting.MOrder;
import org.compiere.accounting.MOrderLine;
import org.compiere.accounting.MProductPO;
import org.compiere.conversionrate.MConversionRate;
import org.compiere.crm.MBPartner;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.production.MProject;
import org.compiere.production.MProjectLine;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Generate Purchase Order from Project.
 *
 * @author Jorg Janke
 * @version $Id: ProjectGenPO.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ProjectGenPO extends SvrProcess {
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
     * Consolidate Document
     */
    private boolean m_ConsolidateDocument = true;
    /**
     * List of POs for Consolidation
     */
    private ArrayList<MOrder> m_pos = new ArrayList<MOrder>();

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
                case "ConsolidateDocument":
                    m_ConsolidateDocument = "Y".equals(iProcessInfoParameter.getParameter());
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
                    "doIt - C_Project_ID="
                            + m_C_Project_ID
                            + " - C_ProjectLine_ID="
                            + m_C_ProjectLine_ID
                            + " - Consolidate="
                            + m_ConsolidateDocument);
        if (m_C_ProjectLine_ID != 0) {
            MProjectLine projectLine = new MProjectLine(m_C_ProjectLine_ID);
            MProject project = new MProject(projectLine.getProjectId());
            createPO(project, projectLine);
        } else if (m_C_ProjectPhase_ID != 0) {
            MProject project = new MProject(m_C_Project_ID);
            for (MProjectLine line : project.getPhaseLines(m_C_ProjectPhase_ID)) {
                if (line.isActive()) {
                    createPO(project, line);
                }
            }
        } else {
            MProject project = new MProject(m_C_Project_ID);
            for (MProjectLine line : project.getLines()) {
                if (line.isActive()) {
                    createPO(project, line);
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
    private void createPO(MProject project, MProjectLine projectLine) {
        if (projectLine.getProductId() == 0) {
            addLog(projectLine.getLine(), null, null, "Line has no Product");
            return;
        }
        if (projectLine.getOrderPOId() != 0) {
            addLog(projectLine.getLine(), null, null, "Line was ordered previously");
            return;
        }

        //	PO Record
        MProductPO[] pos =
                MProductPO.getOfProduct(projectLine.getProductId());
        if (pos == null || pos.length == 0) {
            addLog(projectLine.getLine(), null, null, "Product has no PO record");
            return;
        }

        //	Create to Order
        MOrder order = null;
        //	try to find PO to C_BPartner
        for (int i = 0; i < m_pos.size(); i++) {
            MOrder test = m_pos.get(i);
            if (test.getBusinessPartnerId() == pos[0].getBusinessPartnerId()) {
                order = test;
                break;
            }
        }
        if (order == null) // 	create new Order
        {
            //	Vendor
            MBPartner bp = new MBPartner(pos[0].getBusinessPartnerId());
            //	New Order
            order = new MOrder(project, false, null);
            int AD_Org_ID = projectLine.getOrgId();
            if (AD_Org_ID == 0) {
                log.warning("createPOfromProjectLine - orgId=0");
                AD_Org_ID = Env.getOrgId();
                if (AD_Org_ID != 0) projectLine.setOrgId(AD_Org_ID);
            }
            order.setClientOrg(projectLine.getClientId(), AD_Org_ID);
            order.setBPartner(bp);
            order.saveEx();
            //	optionally save for consolidation
            if (m_ConsolidateDocument) m_pos.add(order);
        }

        //	Create Line
        MOrderLine orderLine = new MOrderLine(order);
        orderLine.setProductId(projectLine.getProductId(), true);
        orderLine.setQty(projectLine.getPlannedQty());
        orderLine.setDescription(projectLine.getDescription());

        //	(Vendor) PriceList Price
        orderLine.setPrice();
        if (orderLine.getPriceActual().signum() == 0) {
            //	Try to find purchase price
            BigDecimal poPrice = pos[0].getPricePO();
            int C_Currency_ID = pos[0].getCurrencyId();
            //
            if (poPrice == null || poPrice.signum() == 0) poPrice = pos[0].getPriceLastPO();
            if (poPrice == null || poPrice.signum() == 0) poPrice = pos[0].getPriceList();
            //	We have a price
            if (poPrice != null && poPrice.signum() != 0) {
                if (order.getCurrencyId() != C_Currency_ID)
                    poPrice =
                            MConversionRate.convert(
                                    poPrice,
                                    C_Currency_ID,
                                    order.getCurrencyId(),
                                    order.getDateAcct(),
                                    order.getConversionTypeId(),
                                    order.getClientId(),
                                    order.getOrgId());
                orderLine.setPrice(poPrice);
            }
        }

        orderLine.setTax();
        orderLine.saveEx();

        //	update ProjectLine
        projectLine.setOrderPOId(order.getOrderId());
        projectLine.saveEx();
        addBufferLog(
                order.getOrderId(),
                order.getDateOrdered(),
                new BigDecimal(orderLine.getLine()),
                MsgKt.getElementTranslation(Env.getADLanguage(), "C_Order_ID", false)
                        + ":"
                        + order.getDocumentNo(),
                order.getTableId(),
                order.getOrderId());
    } //	createPOfromProjectLine
} //	ProjectGenPO
