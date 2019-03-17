package org.idempiere.process;

import org.compiere.accounting.MOrder;
import org.compiere.accounting.MOrderLine;
import org.compiere.accounting.MTimeExpense;
import org.compiere.accounting.MTimeExpenseLine;
import org.compiere.conversionrate.MConversionRate;
import org.compiere.crm.MBPartner;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.DocAction;
import org.compiere.production.MProject;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

/**
 * Create Sales Orders from Expense Reports
 *
 * @author Jorg Janke
 * @version $Id: ExpenseSOrder.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class ExpenseSOrder extends BaseExpenseSOrder {

    /**
     * Current Order
     */
    private MOrder m_order = null;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();
            if (iProcessInfoParameter.getParameter() != null || iProcessInfoParameter.getParameterTo() != null)
                if (name.equals("C_BPartner_ID")) setP_C_BPartner_ID(iProcessInfoParameter.getParameterAsInt());
                else if (name.equals("DateExpense")) {
                    setP_DateFrom((Timestamp) iProcessInfoParameter.getParameter());
                    setM_DateTo((Timestamp) iProcessInfoParameter.getParameterTo());
                } else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
    } //	prepare

    /**
     * Process Expense Line
     *
     * @param te  header
     * @param tel line
     * @param bp  bp
     */
    protected void processLine(MTimeExpense te, MTimeExpenseLine tel, MBPartner bp) {
        if (m_order == null) {
            if (log.isLoggable(Level.INFO))
                log.info("New Order for " + bp + ", Project=" + tel.getProjectId());
            m_order = new MOrder(getCtx(), 0);
            m_order.setOrgId(tel.getOrgId());
            m_order.setTargetDocumentTypeId(MOrder.DocSubTypeSO_OnCredit);
            //
            m_order.setBPartner(bp);
            if (m_order.getBusinessPartnerLocationId() == 0) {
                StringBuilder msglog = new StringBuilder("No BP Location: ").append(bp);
                log.log(Level.SEVERE, msglog.toString());
                msglog =
                        new StringBuilder("No Location: ")
                                .append(te.getDocumentNo())
                                .append(" ")
                                .append(bp.getName());
                addLog(0, te.getDateReport(), null, msglog.toString());
                m_order = null;
                return;
            }
            m_order.setWarehouseId(te.getWarehouseId());
            if (tel.getBusinessActivityId() != 0) m_order.setBusinessActivityId(tel.getBusinessActivityId());
            if (tel.getCampaignId() != 0) m_order.setCampaignId(tel.getCampaignId());
            if (tel.getProjectId() != 0) {
                m_order.setProjectId(tel.getProjectId());
                //	Optionally Overwrite BP Price list from Project
                MProject project = new MProject(getCtx(), tel.getProjectId());
                if (project.getPriceListId() != 0)
                    m_order.setPriceListId(project.getPriceListId());
            }
            m_order.setSalesRepresentativeId(te.getDocumentUserId());
            //
            if (!m_order.save()) {
                throw new IllegalStateException("Cannot save Order");
            }
        } else {
            //	Update Header info
            if (tel.getBusinessActivityId() != 0 && tel.getBusinessActivityId() != m_order.getBusinessActivityId())
                m_order.setBusinessActivityId(tel.getBusinessActivityId());
            if (tel.getCampaignId() != 0 && tel.getCampaignId() != m_order.getCampaignId())
                m_order.setCampaignId(tel.getCampaignId());
            if (!m_order.save()) throw new IllegalStateException("Cannot save Order");
        }

        //	OrderLine
        MOrderLine ol = new MOrderLine(m_order);
        //
        if (tel.getProductId() != 0) ol.setProductId(tel.getProductId(), tel.getUOMId());
        if (tel.getResourceAssignmentId() != 0)
            ol.setS_ResourceAssignmentId(tel.getResourceAssignmentId());
        ol.setQty(tel.getQtyInvoiced()); //
        ol.setDescription(tel.getDescription());
        //
        ol.setProjectId(tel.getProjectId());
        ol.setProjectPhaseId(tel.getProjectPhaseId());
        ol.setProjectTaskId(tel.getProjectTaskId());
        ol.setBusinessActivityId(tel.getBusinessActivityId());
        ol.setCampaignId(tel.getCampaignId());
        //
        BigDecimal price = tel.getPriceInvoiced(); //
        if (price != null && price.compareTo(Env.ZERO) != 0) {
            if (tel.getCurrencyId() != m_order.getCurrencyId())
                price =
                        MConversionRate.convert(
                                getCtx(),
                                price,
                                tel.getCurrencyId(),
                                m_order.getCurrencyId(),
                                m_order.getClientId(),
                                m_order.getOrgId());
            ol.setPrice(price);
        } else ol.setPrice();
        if (tel.getUOMId() != 0 && ol.getUOMId() == 0) ol.setUOMId(tel.getUOMId());
        ol.setTax();
        if (!ol.save()) {
            throw new IllegalStateException("Cannot save Order Line");
        }
        //	Update TimeExpense Line
        tel.setOrderLineId(ol.getOrderLineId());
        if (tel.save()) {
            if (log.isLoggable(Level.FINE)) log.fine("Updated " + tel + " with C_OrderLine_ID");
        } else {
            log.log(Level.SEVERE, "Not Updated " + tel + " with C_OrderLine_ID");
        }
    } //	processLine

    /**
     * Complete Order
     */
    protected void completeOrder() {
        if (m_order == null) return;
        m_order.setDocAction(DocAction.Companion.getACTION_Prepare());
        if (!m_order.processIt(DocAction.Companion.getACTION_Prepare())) {
            StringBuilder msglog =
                    new StringBuilder("Order Process Failed: ")
                            .append(m_order)
                            .append(" - ")
                            .append(m_order.getProcessMsg());
            log.warning(msglog.toString());
            msglog =
                    new StringBuilder("Order Process Failed: ")
                            .append(m_order)
                            .append(" - ")
                            .append(m_order.getProcessMsg());
            throw new IllegalStateException(msglog.toString());
        }
        if (!m_order.save()) throw new IllegalStateException("Cannot save Order");
        setM_noOrders(getM_noOrders() + 1);
        addBufferLog(
                m_order.getId(),
                m_order.getDateOrdered(),
                m_order.getGrandTotal(),
                m_order.getDocumentNo(),
                m_order.getTableId(),
                m_order.getOrderId());
        m_order = null;
    } //	completeOrder
} //	ExpenseSOrder
