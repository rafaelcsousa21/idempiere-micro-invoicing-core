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

import org.compiere.invoicing.MInvoice;
import org.compiere.invoicing.MInvoiceBatch;
import org.compiere.invoicing.MInvoiceBatchLine;
import org.compiere.invoicing.MInvoiceLine;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.idempiere.common.util.AdempiereUserError;

import java.util.logging.Level;

/**
 * Process Invoice Batch
 *
 * @author Jorg Janke
 * @version $Id: InvoiceBatchProcess.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class InvoiceBatchProcess extends SvrProcess {
    /**
     * Batch to process
     */
    private int p_C_InvoiceBatch_ID = 0;
    /**
     * Action
     */
    private String p_DocAction = null;

    /**
     * Invoice
     */
    private MInvoice m_invoice = null;
    /**
     * Old DocumentNo
     */
    private String m_oldDocumentNo = null;
    /**
     * Old BPartner
     */
    private int m_oldC_BPartner_ID = 0;
    /**
     * Old BPartner Loc
     */
    private int m_oldC_BPartner_Location_ID = 0;

    /**
     * Counter
     */
    private int m_count = 0;

    /**
     * Prepare - get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("DocAction")) p_DocAction = (String) iProcessInfoParameter.getParameter();
        }
        p_C_InvoiceBatch_ID = getRecordId();
    } //  prepare

    /**
     * Process Invoice Batch
     *
     * @return message
     * @throws Exception
     */
    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO))
            log.info("C_InvoiceBatch_ID=" + p_C_InvoiceBatch_ID + ", DocAction=" + p_DocAction);
        if (p_C_InvoiceBatch_ID == 0) throw new AdempiereUserError("C_InvoiceBatch_ID = 0");
        MInvoiceBatch batch = new MInvoiceBatch(p_C_InvoiceBatch_ID);
        if (batch.getId() == 0)
            throw new AdempiereUserError("@NotFound@: @C_InvoiceBatch_ID@ - " + p_C_InvoiceBatch_ID);
        if (batch.isProcessed()) throw new AdempiereUserError("@Processed@");
        //
        if (batch.getControlAmt().signum() != 0
                && batch.getControlAmt().compareTo(batch.getDocumentAmt()) != 0)
            throw new AdempiereUserError("@ControlAmt@ <> @DocumentAmt@");
        //
        MInvoiceBatchLine[] lines = batch.getLines(false);
        for (int i = 0; i < lines.length; i++) {
            MInvoiceBatchLine line = lines[i];
            if (line.getInvoiceId() != 0 || line.getInvoiceLineId() != 0) continue;

            if ((m_oldDocumentNo != null && !m_oldDocumentNo.equals(line.getDocumentNo()))
                    || m_oldC_BPartner_ID != line.getBusinessPartnerId()
                    || m_oldC_BPartner_Location_ID != line.getBusinessPartnerLocationId()) completeInvoice();
            //	New Invoice
            if (m_invoice == null) {
                m_invoice = new MInvoice(batch, line);
                if (!m_invoice.save()) throw new AdempiereUserError("Cannot save Invoice");
                //
                m_oldDocumentNo = line.getDocumentNo();
                m_oldC_BPartner_ID = line.getBusinessPartnerId();
                m_oldC_BPartner_Location_ID = line.getBusinessPartnerLocationId();
            }

            if (line.isTaxIncluded() != m_invoice.isTaxIncluded()) {
                //	rollback
                throw new AdempiereUserError("Line " + line.getLine() + " TaxIncluded inconsistent");
            }

            //	Add Line
            MInvoiceLine invoiceLine = new MInvoiceLine(m_invoice);
            invoiceLine.setDescription(line.getDescription());
            invoiceLine.setChargeId(line.getChargeId());
            invoiceLine.setQty(line.getQtyEntered()); // Entered/Invoiced
            invoiceLine.setPrice(line.getPriceEntered());
            invoiceLine.setTaxId(line.getTaxId());
            invoiceLine.setTaxAmt(line.getTaxAmt());
            invoiceLine.setLineNetAmt(line.getLineNetAmt());
            invoiceLine.setLineTotalAmt(line.getLineTotalAmt());
            if (!invoiceLine.save()) {
                //	rollback
                throw new AdempiereUserError("Cannot save Invoice Line");
            }

            //	Update Batch Line
            line.setInvoiceId(m_invoice.getInvoiceId());
            line.setInvoiceLineId(invoiceLine.getInvoiceLineId());
            line.saveEx();
        } //	for all lines
        completeInvoice();
        //
        batch.setProcessed(true);
        batch.saveEx();

        StringBuilder msgreturn = new StringBuilder("#").append(m_count);
        return msgreturn.toString();
    } //	doIt

    /**
     * Complete Invoice
     */
    private void completeInvoice() {
        if (m_invoice == null) return;

        m_invoice.setDocAction(p_DocAction);
        if (!m_invoice.processIt(p_DocAction)) {
            log.warning("Invoice Process Failed: " + m_invoice + " - " + m_invoice.getProcessMsg());
            throw new IllegalStateException(
                    "Invoice Process Failed: " + m_invoice + " - " + m_invoice.getProcessMsg());
        }
        m_invoice.saveEx();

        String message =
                Msg.parseTranslation("@InvoiceProcessed@ " + m_invoice.getDocumentNo());
        addBufferLog(
                0,
                m_invoice.getDateInvoiced(),
                m_invoice.getGrandTotal(),
                message,
                m_invoice.getTableId(),
                m_invoice.getInvoiceId());
        m_count++;

        m_invoice = null;
    } //	completeInvoice
} //	InvoiceBatchProcess
