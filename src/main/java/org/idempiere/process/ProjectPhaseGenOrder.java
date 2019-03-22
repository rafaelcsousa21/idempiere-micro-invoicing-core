/**
 * **************************************************************************** Product: Adempiere
 * ERP & CRM Smart Business Solution * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it * under the terms version
 * 2 of the GNU General Public License as published * by the Free Software Foundation. This program
 * is distributed in the hope * that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. * See the GNU General
 * Public License for more details. * You should have received a copy of the GNU General Public
 * License along * with this program; if not, write to the Free Software Foundation, Inc., * 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA. * For the text or an alternative of this
 * public license, you may reach us * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA
 * 95054, USA * or via info@compiere.org or http://www.compiere.org/license.html *
 * ***************************************************************************
 */
package org.idempiere.process;

import org.compiere.accounting.MOrder;
import org.compiere.accounting.MOrderLine;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.production.MProject;
import org.compiere.production.MProjectLine;
import org.compiere.production.MProjectPhase;
import org.compiere.production.MProjectTask;
import org.idempiere.common.util.Env;

import java.util.logging.Level;

/**
 * Generate Order from Project Phase
 *
 * @author Jorg Janke
 * @version $Id: ProjectPhaseGenOrder.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ProjectPhaseGenOrder extends SvrProcess {
    private int m_C_ProjectPhase_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message (clear text)
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        m_C_ProjectPhase_ID = getRecordId();
        if (log.isLoggable(Level.INFO)) log.info("doIt - C_ProjectPhase_ID=" + m_C_ProjectPhase_ID);
        if (m_C_ProjectPhase_ID == 0) throw new IllegalArgumentException("C_ProjectPhase_ID == 0");
        MProjectPhase fromPhase = new MProjectPhase(getCtx(), m_C_ProjectPhase_ID);
        MProject fromProject =
                ProjectGenOrder.getProject(getCtx(), fromPhase.getProjectId());
        MOrder order = new MOrder(fromProject, true, MOrder.DocSubTypeSO_OnCredit);
        order.setDescription(order.getDescription() + " - " + fromPhase.getName());
        if (!order.save()) throw new Exception("Could not create Order");

        //	Create an order on Phase Level
        if (fromPhase.getProductId() != 0) {
            MOrderLine ol = new MOrderLine(order);
            ol.setLine(fromPhase.getSeqNo());
            StringBuilder sb = new StringBuilder().append(fromPhase.getName());
            if (fromPhase.getDescription() != null && fromPhase.getDescription().length() > 0)
                sb.append(" - ").append(fromPhase.getDescription());
            ol.setDescription(sb.toString());
            //
            ol.setProductId(fromPhase.getProductId(), true);
            ol.setQty(fromPhase.getQty());
            ol.setPrice();
            if (fromPhase.getPriceActual() != null && fromPhase.getPriceActual().compareTo(Env.ZERO) != 0)
                ol.setPrice(fromPhase.getPriceActual());
            ol.setTax();
            if (!ol.save()) log.log(Level.SEVERE, "doIt - Lines not generated");
            StringBuilder msgreturn =
                    new StringBuilder("@C_Order_ID@ ").append(order.getDocumentNo()).append(" (1)");
            return msgreturn.toString();
        }

        //	Project Phase Lines
        int count = 0;
        MProjectLine[] lines = fromPhase.getLines();
        for (int i = 0; i < lines.length; i++) {
            MOrderLine ol = new MOrderLine(order);
            ol.setLine(lines[i].getLine());
            ol.setDescription(lines[i].getDescription());
            //
            ol.setProductId(lines[i].getProductId(), true);
            ol.setQty(lines[i].getPlannedQty().subtract(lines[i].getInvoicedQty()));
            ol.setPrice();
            if (lines[i].getPlannedPrice() != null && lines[i].getPlannedPrice().compareTo(Env.ZERO) != 0)
                ol.setPrice(lines[i].getPlannedPrice());
            ol.setDiscount();
            ol.setTax();
            if (ol.save()) count++;
        } //	for all lines
        if (lines.length != count)
            log.log(
                    Level.SEVERE, "Lines difference - ProjectLines=" + lines.length + " <> Saved=" + count);

        //	Project Tasks
        MProjectTask[] tasks = fromPhase.getTasks();
        for (int i = 0; i < tasks.length; i++) {
            MOrderLine ol = new MOrderLine(order);
            ol.setLine(tasks[i].getSeqNo());
            StringBuilder sb = new StringBuilder().append(tasks[i].getName());
            if (tasks[i].getDescription() != null && tasks[i].getDescription().length() > 0)
                sb.append(" - ").append(tasks[i].getDescription());
            ol.setDescription(sb.toString());
            //
            ol.setProductId(tasks[i].getProductId(), true);
            ol.setQty(tasks[i].getQty());
            ol.setPrice();
            ol.setTax();
            if (ol.save()) count++;
        } //	for all lines
        if (tasks.length != count - lines.length)
            log.log(
                    Level.SEVERE,
                    "doIt - Lines difference - ProjectTasks=" + tasks.length + " <> Saved=" + count);

        StringBuilder msgreturn =
                new StringBuilder("@C_Order_ID@ ")
                        .append(order.getDocumentNo())
                        .append(" (")
                        .append(count)
                        .append(")");
        return msgreturn.toString();
    } //	doIt
} //	ProjectPhaseGenOrder
