package org.idempiere.process;

import org.compiere.accounting.MOrder;
import org.compiere.accounting.MOrderLine;
import org.compiere.process.SvrProcess;
import org.compiere.production.MProject;
import org.compiere.production.MProjectLine;
import org.idempiere.common.util.Env;

import java.util.logging.Level;

/**
 * Generate Sales Order from Project.
 *
 * @author Jorg Janke
 * @version $Id: ProjectGenOrder.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class ProjectGenOrder extends SvrProcess {
    /**
     * Project ID from project directly
     */
    private int m_C_Project_ID = 0;

    /**
     * Get and validate Project
     *
     * @param ctx          context
     * @param C_Project_ID id
     * @param trxName      transaction
     * @return valid project
     */
    protected static MProject getProject(int C_Project_ID) {
        MProject fromProject = new MProject(C_Project_ID);
        if (fromProject.getProjectId() == 0)
            throw new IllegalArgumentException("Project not found C_Project_ID=" + C_Project_ID);
        if (fromProject.getPriceListVersionId() == 0)
            throw new IllegalArgumentException("Project has no Price List");
        if (fromProject.getWarehouseId() == 0)
            throw new IllegalArgumentException("Project has no Warehouse");
        if (fromProject.getBusinessPartnerId() == 0 || fromProject.getBusinessPartnerLocationId() == 0)
            throw new IllegalArgumentException("Project has no Business Partner/Location");
        return fromProject;
    } //	getProject

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        m_C_Project_ID = getRecordId();
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message (clear text)
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO)) log.info("C_Project_ID=" + m_C_Project_ID);
        if (m_C_Project_ID == 0) throw new IllegalArgumentException("C_Project_ID == 0");
        MProject fromProject = getProject(m_C_Project_ID);
        Env.setSOTrx(true); // 	Set SO context

        /** @todo duplicate invoice prevention */
        MOrder order = new MOrder(fromProject, true, MOrder.DocSubTypeSO_OnCredit);
        if (!order.save()) throw new Exception("Could not create Order");

        //	***	Lines ***
        int count = 0;

        //	Service Project
        if (MProject.PROJECTCATEGORY_ServiceChargeProject.equals(fromProject.getProjectCategory())) {
            /** @todo service project invoicing */
            throw new Exception("Service Charge Projects are on the TODO List");
        } //	Service Lines
        else //	Order Lines
        {
            MProjectLine[] lines = fromProject.getLines();
            for (int i = 0; i < lines.length; i++) {
                MOrderLine ol = new MOrderLine(order);
                ol.setLine(lines[i].getLine());
                ol.setDescription(lines[i].getDescription());
                //
                ol.setProductId(lines[i].getProductId(), true);
                ol.setQty(lines[i].getPlannedQty().subtract(lines[i].getInvoicedQty()));
                ol.setPrice();
                if (lines[i].getPlannedPrice() != null
                        && lines[i].getPlannedPrice().compareTo(Env.ZERO) != 0)
                    ol.setPrice(lines[i].getPlannedPrice());
                ol.setDiscount();
                ol.setTax();
                if (ol.save()) count++;
            } //	for all lines
            if (lines.length != count)
                log.log(
                        Level.SEVERE, "Lines difference - ProjectLines=" + lines.length + " <> Saved=" + count);
        } //	Order Lines

        StringBuilder msgreturn =
                new StringBuilder("@C_Order_ID@ ")
                        .append(order.getDocumentNo())
                        .append(" (")
                        .append(count)
                        .append(")");
        return msgreturn.toString();
    } //	doIt
} //	ProjectGenOrder
