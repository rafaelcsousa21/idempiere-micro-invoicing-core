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

import org.compiere.accounting.*;
import org.compiere.invoicing.MInvoice;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.idempiere.common.util.AdempiereSystemError;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Automatic Allocation Process
 *
 * @author Jorg Janke
 * @version $Id: AllocationAuto.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class AllocationAuto extends SvrProcess {
    private static String ONLY_AP = "P";
    private static String ONLY_AR = "R";
    /**
     * BP Group
     */
    private int p_C_BP_Group_ID = 0;
    /**
     * BPartner
     */
    private int p_C_BPartner_ID = 0;
    /**
     * Allocate Oldest Setting
     */
    private boolean p_AllocateOldest = true;
    /**
     * Only AP/AR Transactions
     */
    private String p_APAR = "A";
    /**
     * Payments
     */
    private MPayment[] m_payments = null;
    /**
     * Invoices
     */
    private MInvoice[] m_invoices = null;
    /**
     * Allocation
     */
    private MAllocationHdr m_allocation = null;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (int i = 0; i < para.length; i++) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) ;
            else if (name.equals("C_BP_Group_ID")) p_C_BP_Group_ID = para[i].getParameterAsInt();
            else if (name.equals("C_BPartner_ID")) p_C_BPartner_ID = para[i].getParameterAsInt();
            else if (name.equals("AllocateOldest")) p_AllocateOldest = "Y".equals(para[i].getParameter());
            else if (name.equals("APAR")) p_APAR = (String) para[i].getParameter();
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
    } //	prepare

    /**
     * Process
     *
     * @return message
     * @throws Exception
     */
    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO))
            log.info(
                    "C_BP_Group_ID="
                            + p_C_BP_Group_ID
                            + ", C_BPartner_ID="
                            + p_C_BPartner_ID
                            + ", Oldest="
                            + p_AllocateOldest
                            + ", AP/AR="
                            + p_APAR);
        int countBP = 0;
        int countAlloc = 0;
        if (p_C_BPartner_ID != 0) {
            countAlloc = allocateBP(p_C_BPartner_ID);
            if (countAlloc > 0) countBP++;
        } else if (p_C_BP_Group_ID != 0) {
            String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE C_BP_Group_ID=? ORDER BY Value";
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                pstmt = prepareStatement(sql);
                pstmt.setInt(1, p_C_BP_Group_ID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int C_BPartner_ID = rs.getInt(1);
                    int count = allocateBP(C_BPartner_ID);
                    if (count > 0) {
                        countBP++;
                        countAlloc += count;
                    }
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, sql, e);
            } finally {

                rs = null;
                pstmt = null;
            }
        } else {
            String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE AD_Client_ID=? ORDER BY Value";
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                pstmt = prepareStatement(sql);
                pstmt.setInt(1, Env.getClientId(getCtx()));
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int C_BPartner_ID = rs.getInt(1);
                    int count = allocateBP(C_BPartner_ID);
                    if (count > 0) {
                        countBP++;
                        countAlloc += count;
                    }
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, sql, e);
            } finally {

                rs = null;
                pstmt = null;
            }
        }
        //
        StringBuilder msgreturn =
                new StringBuilder("@Created@ #").append(countBP).append("/").append(countAlloc);
        return msgreturn.toString();
    } //	doIt

    /**
     * Allocate BP
     *
     * @param C_BPartner_ID
     * @return number of allocations
     */
    private int allocateBP(int C_BPartner_ID) throws Exception {
        getPayments(C_BPartner_ID);
        getInvoices(C_BPartner_ID);
        if (log.isLoggable(Level.INFO))
            log.info(
                    "(1) - C_BPartner_ID="
                            + C_BPartner_ID
                            + " - #Payments="
                            + m_payments.length
                            + ", #Invoices="
                            + m_invoices.length);
        if (m_payments.length + m_invoices.length < 2) return 0;

        //	Payment Info - Invoice or Pay Selection
        int count = allocateBPPaymentWithInfo();
        if (count != 0) {
            getPayments(C_BPartner_ID); // 	for next
            getInvoices(C_BPartner_ID);
            if (log.isLoggable(Level.INFO))
                log.info(
                        "(2) - C_BPartner_ID="
                                + C_BPartner_ID
                                + " - #Payments="
                                + m_payments.length
                                + ", #Invoices="
                                + m_invoices.length);
            if (m_payments.length + m_invoices.length < 2) return count;
        }

        //	All
        int newCount = allocateBPartnerAll();
        if (newCount != 0) {
            count += newCount;
            getPayments(C_BPartner_ID); // 	for next
            getInvoices(C_BPartner_ID);
            processAllocation();
            if (log.isLoggable(Level.INFO))
                log.info(
                        "(3) - C_BPartner_ID="
                                + C_BPartner_ID
                                + " - #Payments="
                                + m_payments.length
                                + ", #Invoices="
                                + m_invoices.length);
            if (m_payments.length + m_invoices.length < 2) return count;
        }

        //	One:One
        newCount = allocateBPOneToOne();
        if (newCount != 0) {
            count += newCount;
            getPayments(C_BPartner_ID); // 	for next
            getInvoices(C_BPartner_ID);
            processAllocation();
            if (log.isLoggable(Level.INFO))
                log.info(
                        "(4) - C_BPartner_ID="
                                + C_BPartner_ID
                                + " - #Payments="
                                + m_payments.length
                                + ", #Invoices="
                                + m_invoices.length);
            if (m_payments.length + m_invoices.length < 2) return count;
        }

        //	Oldest First
        if (p_AllocateOldest) {
            newCount = allocateBPOldestFirst();
            if (newCount != 0) {
                count += newCount;
                getPayments(C_BPartner_ID); // 	for next
                getInvoices(C_BPartner_ID);
                processAllocation();
                if (log.isLoggable(Level.INFO))
                    log.info(
                            "(5) - C_BPartner_ID="
                                    + C_BPartner_ID
                                    + " - #Payments="
                                    + m_payments.length
                                    + ", #Invoices="
                                    + m_invoices.length);
                if (m_payments.length + m_invoices.length < 2) return count;
            }
        }

        //	Other, e.g.
        //	Allocation if "close" % and $

        return count;
    } //	alloc

    /**
     * Get Payments of BP
     *
     * @param C_BPartner_ID id
     * @return unallocated payments
     */
    private MPayment[] getPayments(int C_BPartner_ID) {
        ArrayList<MPayment> list = new ArrayList<MPayment>();
        StringBuilder sql =
                new StringBuilder("SELECT * FROM C_Payment ")
                        .append("WHERE IsAllocated='N' AND Processed='Y' AND C_BPartner_ID=?")
                        .append(" AND IsPrepayment='N' AND C_Charge_ID IS NULL ");
        if (ONLY_AP.equals(p_APAR)) sql.append("AND IsReceipt='N' ");
        else if (ONLY_AR.equals(p_APAR)) sql.append("AND IsReceipt='Y' ");
        sql.append("ORDER BY DateTrx");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql.toString());
            pstmt.setInt(1, C_BPartner_ID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MPayment payment = new MPayment(getCtx(), rs);
                BigDecimal allocated = payment.getAllocatedAmt();
                if (allocated != null && allocated.compareTo(payment.getPayAmt()) == 0) {
                    payment.setIsAllocated(true);
                    payment.saveEx();
                } else list.add(payment);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, sql.toString(), e);
        } finally {

            rs = null;
            pstmt = null;
        }
        m_payments = new MPayment[list.size()];
        list.toArray(m_payments);
        return m_payments;
    } //	getPayments

    /**
     * Get Invoices of BP
     *
     * @param C_BPartner_ID id
     * @return unallocated Invoices
     */
    private MInvoice[] getInvoices(int C_BPartner_ID) {
        ArrayList<MInvoice> list = new ArrayList<MInvoice>();
        StringBuilder sql =
                new StringBuilder("SELECT * FROM C_Invoice ")
                        .append("WHERE IsPaid='N' AND Processed='Y' AND C_BPartner_ID=? ");
        if (ONLY_AP.equals(p_APAR)) sql.append("AND IsSOTrx='N' ");
        else if (ONLY_AR.equals(p_APAR)) sql.append("AND IsSOTrx='Y' ");
        sql.append("ORDER BY DateInvoiced");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql.toString());
            pstmt.setInt(1, C_BPartner_ID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MInvoice invoice = new MInvoice(getCtx(), rs);
                if (invoice.getOpenAmt(false, null).signum() == 0) {
                    invoice.setIsPaid(true);
                    invoice.saveEx();
                } else list.add(invoice);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, sql.toString(), e);
        } finally {

            rs = null;
            pstmt = null;
        }
        m_invoices = new MInvoice[list.size()];
        list.toArray(m_invoices);
        return m_invoices;
    } //	getInvoices

    /**
     * ************************************************************************ Allocate Individual
     * Payments with payment references
     *
     * @return number of allocations
     */
    private int allocateBPPaymentWithInfo() {
        int count = 0;

        // ****	See if there is a direct link (Invoice or Pay Selection)
        for (int p = 0; p < m_payments.length; p++) {
            MPayment payment = m_payments[p];
            if (payment.isAllocated()) continue;
            BigDecimal allocatedAmt = payment.getAllocatedAmt();
            if (log.isLoggable(Level.INFO)) log.info(payment + ", Allocated=" + allocatedAmt);
            if (allocatedAmt != null && allocatedAmt.signum() != 0) continue;
            BigDecimal availableAmt =
                    payment
                            .getPayAmt()
                            .add(payment.getDiscountAmt())
                            .add(payment.getWriteOffAmt())
                            .add(payment.getOverUnderAmt());
            if (!payment.isReceipt()) availableAmt = availableAmt.negate();
            if (log.isLoggable(Level.FINE)) log.fine("Available=" + availableAmt);
            //
            if (payment.getInvoiceId() != 0) {
                for (int i = 0; i < m_invoices.length; i++) {
                    MInvoice invoice = m_invoices[i];
                    if (invoice.isPaid()) continue;
                    //	log.fine("allocateIndividualPayments - " + invoice);
                    if (payment.getInvoiceId() == invoice.getInvoiceId()) {
                        if (payment.getCurrencyId() == invoice.getCurrencyId()) {
                            BigDecimal openAmt = invoice.getOpenAmt(true, null);
                            if (!invoice.isSOTrx()) openAmt = openAmt.negate();
                            if (log.isLoggable(Level.FINE)) log.fine(invoice + ", Open=" + openAmt);
                            //	With Discount, etc.
                            if (availableAmt.compareTo(openAmt) == 0) {
                                if (payment.allocateIt()) {
                                    String message =
                                            Msg.parseTranslation(
                                                    getCtx(), "@PaymentAllocated@ " + payment.getDocumentNo() + " [1]");
                                    addBufferLog(
                                            0,
                                            payment.getDateAcct(),
                                            openAmt,
                                            message,
                                            payment.getTableId(),
                                            payment.getPaymentId());
                                    count++;
                                }
                                break;
                            }
                        } else //	Mixed Currency
                        {
                        }
                    } //	invoice found
                } //	for all invoices
            } //	payment has invoice
            else //	No direct invoice
            {
                MPaySelectionCheck psCheck =
                        MPaySelectionCheck.getOfPayment(getCtx(), payment.getPaymentId());
                if (psCheck == null) continue;
                //
                BigDecimal totalInvoice = Env.ZERO;
                MPaySelectionLine[] psLines = psCheck.getPaySelectionLines(false);
                for (int i = 0; i < psLines.length; i++) {
                    MPaySelectionLine line = psLines[i];
                    MInvoice invoice = line.getInvoice();
                    if (payment.getCurrencyId() == invoice.getCurrencyId()) {
                        BigDecimal invoiceAmt = invoice.getOpenAmt(true, null);
                        BigDecimal overUnder =
                                line.getOpenAmt()
                                        .subtract(line.getPayAmt())
                                        .subtract(line.getDiscountAmt())
                                        .subtract(line.getWriteOffAmt())
                                        .subtract(line.getDifferenceAmt());
                        invoiceAmt =
                                invoiceAmt
                                        .subtract(line.getDiscountAmt())
                                        .subtract(line.getWriteOffAmt())
                                        .subtract(line.getDifferenceAmt())
                                        .subtract(overUnder);
                        if (!invoice.isSOTrx()) invoiceAmt = invoiceAmt.negate();
                        if (log.isLoggable(Level.FINE)) log.fine(invoice + ", Invoice=" + invoiceAmt);
                        totalInvoice = totalInvoice.add(invoiceAmt);
                    } else //	Multi-Currency
                    {
                    }
                }
                if (availableAmt.compareTo(totalInvoice) == 0) {
                    if (payment.allocateIt()) {
                        String message =
                                Msg.parseTranslation(
                                        getCtx(), "@PaymentAllocated@ " + payment.getDocumentNo() + " [n]");
                        addBufferLog(
                                0,
                                payment.getDateAcct(),
                                availableAmt,
                                message,
                                payment.getTableId(),
                                payment.getPaymentId());
                        count++;
                    }
                }
            } //	No direct invoice
        }
        // ****	See if there is a direct link

        return count;
    } //	allocateIndividualPayments

    /**
     * Allocate Payment:Invoice 1:1
     *
     * @return allocations
     */
    private int allocateBPOneToOne() throws Exception {
        int count = 0;
        for (int p = 0; p < m_payments.length; p++) {
            MPayment payment = m_payments[p];
            if (payment.isAllocated()) continue;
            BigDecimal allocatedAmt = payment.getAllocatedAmt();
            if (log.isLoggable(Level.INFO)) log.info(payment + ", Allocated=" + allocatedAmt);
            if (allocatedAmt != null && allocatedAmt.signum() != 0) continue;
            BigDecimal availableAmt =
                    payment
                            .getPayAmt()
                            .add(payment.getDiscountAmt())
                            .add(payment.getWriteOffAmt())
                            .add(payment.getOverUnderAmt());
            if (!payment.isReceipt()) availableAmt = availableAmt.negate();
            if (log.isLoggable(Level.FINE)) log.fine("Available=" + availableAmt);
            for (int i = 0; i < m_invoices.length; i++) {
                MInvoice invoice = m_invoices[i];
                if (invoice == null || invoice.isPaid()) continue;
                if (payment.getCurrencyId() == invoice.getCurrencyId()) {
                    //	log.fine("allocateBPartnerAll - " + invoice);
                    BigDecimal openAmt = invoice.getOpenAmt(true, null);
                    if (!invoice.isSOTrx()) openAmt = openAmt.negate();
                    BigDecimal difference = availableAmt.subtract(openAmt).abs();
                    if (log.isLoggable(Level.FINE))
                        log.fine(invoice + ", Open=" + openAmt + " - Difference=" + difference);
                    if (difference.signum() == 0) {
                        Timestamp dateAcct = payment.getDateAcct();
                        if (invoice.getDateAcct().after(dateAcct)) dateAcct = invoice.getDateAcct();
                        if (!createAllocation(
                                payment.getCurrencyId(),
                                "1:1 (" + availableAmt + ")",
                                dateAcct,
                                availableAmt,
                                null,
                                null,
                                null,
                                invoice.getBusinessPartnerId(),
                                payment.getPaymentId(),
                                invoice.getInvoiceId(),
                                invoice.getOrgId())) {
                            throw new AdempiereSystemError("Cannot create Allocation");
                        }
                        processAllocation();
                        count++;
                        m_invoices[i] = null; // 	remove invoice
                        m_payments[p] = null;
                        payment = null;
                        break;
                    }
                } else //	Multi-Currency
                {
                }
            } //	for all invoices
        } //	for all payments
        return count;
    } //	allocateOneToOne

    /**
     * Allocate all Payments/Invoices using Accounting currency
     *
     * @return allocations
     */
    private int allocateBPartnerAll() throws Exception {
        int C_Currency_ID = MClient.get(getCtx()).getCurrencyId();
        Timestamp dateAcct = null;
        //	Payments
        BigDecimal totalPayments = Env.ZERO;
        for (int p = 0; p < m_payments.length; p++) {
            MPayment payment = m_payments[p];
            if (payment.isAllocated()) continue;
            BigDecimal allocatedAmt = payment.getAllocatedAmt();
            //	log.info("allocateBPartnerAll - " + payment + ", Allocated=" + allocatedAmt);
            if (allocatedAmt != null && allocatedAmt.signum() != 0) continue;
            BigDecimal availableAmt =
                    payment
                            .getPayAmt()
                            .add(payment.getDiscountAmt())
                            .add(payment.getWriteOffAmt())
                            .add(payment.getOverUnderAmt());
            if (!payment.isReceipt()) availableAmt = availableAmt.negate();
            //	Foreign currency
            if (payment.getCurrencyId() != C_Currency_ID) continue;
            //	log.fine("allocateBPartnerAll - Available=" + availableAmt);
            if (dateAcct == null || payment.getDateAcct().after(dateAcct))
                dateAcct = payment.getDateAcct();
            totalPayments = totalPayments.add(availableAmt);
        }
        //	Invoices
        BigDecimal totalInvoices = Env.ZERO;
        for (int i = 0; i < m_invoices.length; i++) {
            MInvoice invoice = m_invoices[i];
            if (invoice.isPaid()) continue;
            //	log.info("allocateBPartnerAll - " + invoice);
            BigDecimal openAmt = invoice.getOpenAmt(true, null);
            if (!invoice.isSOTrx()) openAmt = openAmt.negate();
            //	Foreign currency
            if (invoice.getCurrencyId() != C_Currency_ID) continue;
            //	log.fine("allocateBPartnerAll - Open=" + openAmt);
            if (dateAcct == null || invoice.getDateAcct().after(dateAcct))
                dateAcct = invoice.getDateAcct();
            totalInvoices = totalInvoices.add(openAmt);
        }

        BigDecimal difference = totalInvoices.subtract(totalPayments);
        if (log.isLoggable(Level.INFO))
            log.info(
                    "= Invoices="
                            + totalInvoices
                            + " - Payments="
                            + totalPayments
                            + " = Difference="
                            + difference);

        if (difference.signum() == 0) {
            for (int p = 0; p < m_payments.length; p++) {
                MPayment payment = m_payments[p];
                if (payment.isAllocated()) continue;
                BigDecimal allocatedAmt = payment.getAllocatedAmt();
                if (allocatedAmt != null && allocatedAmt.signum() != 0) continue;
                BigDecimal availableAmt =
                        payment
                                .getPayAmt()
                                .add(payment.getDiscountAmt())
                                .add(payment.getWriteOffAmt())
                                .add(payment.getOverUnderAmt());
                if (!payment.isReceipt()) availableAmt = availableAmt.negate();
                //	Foreign currency
                if (payment.getCurrencyId() != C_Currency_ID) continue;
                if (!createAllocation(
                        C_Currency_ID,
                        "BP All",
                        dateAcct,
                        availableAmt,
                        null,
                        null,
                        null,
                        payment.getBusinessPartnerId(),
                        payment.getPaymentId(),
                        0,
                        payment.getOrgId())) {
                    throw new AdempiereSystemError("Cannot create Allocation");
                }
            } //	for all payments
            //
            for (int i = 0; i < m_invoices.length; i++) {
                MInvoice invoice = m_invoices[i];
                if (invoice.isPaid()) continue;
                BigDecimal openAmt = invoice.getOpenAmt(true, null);
                if (!invoice.isSOTrx()) openAmt = openAmt.negate();
                //	Foreign currency
                if (invoice.getCurrencyId() != C_Currency_ID) continue;
                if (!createAllocation(
                        C_Currency_ID,
                        "BP All",
                        dateAcct,
                        openAmt,
                        null,
                        null,
                        null,
                        invoice.getBusinessPartnerId(),
                        0,
                        invoice.getInvoiceId(),
                        invoice.getOrgId())) {
                    throw new AdempiereSystemError("Cannot create Allocation");
                }
            } //	for all invoices
            processAllocation();
            return 1;
        } //	Difference OK

        return 0;
    } //	allocateBPartnerAll

    /**
     * Allocate Oldest First using Accounting currency
     *
     * @return allocations
     */
    private int allocateBPOldestFirst() throws Exception {
        int C_Currency_ID = MClient.get(getCtx()).getCurrencyId();
        Timestamp dateAcct = null;
        //	Payments
        BigDecimal totalPayments = Env.ZERO;
        for (int p = 0; p < m_payments.length; p++) {
            MPayment payment = m_payments[p];
            if (payment.isAllocated()) continue;
            if (payment.getCurrencyId() != C_Currency_ID) continue;
            BigDecimal allocatedAmt = payment.getAllocatedAmt();
            if (log.isLoggable(Level.INFO)) log.info(payment + ", Allocated=" + allocatedAmt);
            BigDecimal availableAmt =
                    payment
                            .getPayAmt()
                            .add(payment.getDiscountAmt())
                            .add(payment.getWriteOffAmt())
                            .add(payment.getOverUnderAmt());
            availableAmt = availableAmt.subtract(allocatedAmt);
            if (!payment.isReceipt()) availableAmt = availableAmt.negate();
            if (log.isLoggable(Level.FINE)) log.fine("Available=" + availableAmt);
            if (dateAcct == null || payment.getDateAcct().after(dateAcct))
                dateAcct = payment.getDateAcct();
            totalPayments = totalPayments.add(availableAmt);
        }
        //	Invoices
        BigDecimal totalInvoices = Env.ZERO;
        for (int i = 0; i < m_invoices.length; i++) {
            MInvoice invoice = m_invoices[i];
            if (invoice.isPaid()) continue;
            if (invoice.getCurrencyId() != C_Currency_ID) continue;
            BigDecimal openAmt = invoice.getOpenAmt(true, null);
            if (log.isLoggable(Level.FINE)) log.fine("" + invoice);
            if (!invoice.isSOTrx()) openAmt = openAmt.negate();
            //	Foreign currency
            if (log.isLoggable(Level.FINE)) log.fine("Open=" + openAmt);
            if (dateAcct == null || invoice.getDateAcct().after(dateAcct))
                dateAcct = invoice.getDateAcct();
            totalInvoices = totalInvoices.add(openAmt);
        }

        //	must be either AP or AR balance
        if (totalInvoices.signum() != totalPayments.signum()) {
            if (log.isLoggable(Level.FINE))
                log.fine(
                        "Signum - Invoices="
                                + totalInvoices.signum()
                                + " <> Payments="
                                + totalPayments.signum());
            return 0;
        }

        BigDecimal difference = totalInvoices.subtract(totalPayments);
        BigDecimal maxAmt = totalInvoices.abs().min(totalPayments.abs());
        if (totalInvoices.signum() < 0) maxAmt = maxAmt.negate();
        if (log.isLoggable(Level.INFO))
            log.info(
                    "= Invoices="
                            + totalInvoices
                            + " - Payments="
                            + totalPayments
                            + " = Difference="
                            + difference
                            + " - Max="
                            + maxAmt);

        //	Allocate Payments up to max
        BigDecimal allocatedPayments = Env.ZERO;
        for (int p = 0; p < m_payments.length; p++) {
            MPayment payment = m_payments[p];
            if (payment.isAllocated()) continue;
            if (payment.getCurrencyId() != C_Currency_ID) continue;
            BigDecimal allocatedAmt = payment.getAllocatedAmt();
            // comment following lines to allow partial allocation
            // if (allocatedAmt != null && allocatedAmt.signum() != 0)
            // 	continue;
            BigDecimal availableAmt =
                    payment
                            .getPayAmt()
                            .add(payment.getDiscountAmt())
                            .add(payment.getWriteOffAmt())
                            .add(payment.getOverUnderAmt());
            availableAmt = availableAmt.subtract(allocatedAmt);
            if (!payment.isReceipt()) availableAmt = availableAmt.negate();
            allocatedPayments = allocatedPayments.add(availableAmt);
            if ((totalInvoices.signum() > 0 && allocatedPayments.compareTo(maxAmt) > 0)
                    || (totalInvoices.signum() < 0 && allocatedPayments.compareTo(maxAmt) < 0)) {
                BigDecimal diff = allocatedPayments.subtract(maxAmt);
                availableAmt = availableAmt.subtract(diff);
                allocatedPayments = allocatedPayments.subtract(diff);
            }
            if (log.isLoggable(Level.FINE)) log.fine("Payment Allocated=" + availableAmt);
            if (!createAllocation(
                    C_Currency_ID,
                    "BP Oldest (" + difference.abs() + ")",
                    dateAcct,
                    availableAmt,
                    null,
                    null,
                    null,
                    payment.getBusinessPartnerId(),
                    payment.getPaymentId(),
                    0,
                    payment.getOrgId())) {
                throw new AdempiereSystemError("Cannot create Allocation");
            }
            if (allocatedPayments.compareTo(maxAmt) == 0) break;
        } //	for all payments
        //	Allocated Invoices up to max
        BigDecimal allocatedInvoices = Env.ZERO;
        for (int i = 0; i < m_invoices.length; i++) {
            MInvoice invoice = m_invoices[i];
            if (invoice.isPaid()) continue;
            if (invoice.getCurrencyId() != C_Currency_ID) continue;
            BigDecimal openAmt = invoice.getOpenAmt(true, null);
            if (!invoice.isSOTrx()) openAmt = openAmt.negate();
            allocatedInvoices = allocatedInvoices.add(openAmt);
            if ((totalInvoices.signum() > 0 && allocatedInvoices.compareTo(maxAmt) > 0)
                    || (totalInvoices.signum() < 0 && allocatedInvoices.compareTo(maxAmt) < 0)) {
                BigDecimal diff = allocatedInvoices.subtract(maxAmt);
                openAmt = openAmt.subtract(diff);
                allocatedInvoices = allocatedInvoices.subtract(diff);
            }
            if (openAmt.signum() == 0) break;
            if (log.isLoggable(Level.FINE)) log.fine("Invoice Allocated=" + openAmt);
            if (!createAllocation(
                    C_Currency_ID,
                    "BP Oldest (" + difference.abs() + ")",
                    dateAcct,
                    openAmt,
                    null,
                    null,
                    null,
                    invoice.getBusinessPartnerId(),
                    0,
                    invoice.getInvoiceId(),
                    invoice.getOrgId())) {
                throw new AdempiereSystemError("Cannot create Allocation");
            }
            if (allocatedInvoices.compareTo(maxAmt) == 0) break;
        } //	for all invoices

        if (allocatedPayments.compareTo(allocatedInvoices) != 0) {
            StringBuilder msg =
                    new StringBuilder("Allocated Payments=")
                            .append(allocatedPayments)
                            .append(" <> Invoices=")
                            .append(allocatedInvoices);
            throw new AdempiereSystemError(msg.toString());
        }
        processAllocation();
        return 1;
    } //	allocateOldestFirst

    /**
     * ********************************************************************************************
     * Create Allocation allocation
     *
     * @param C_Currency_ID currency
     * @param description   decription
     * @param Amount        amount
     * @param DiscountAmt   discount
     * @param WriteOffAmt   write off
     * @param OverUnderAmt  over under
     * @param C_BPartner_ID partner
     * @param C_Payment_ID  payment
     * @param C_Invoice_ID  invoice
     * @return true if created
     */
    private boolean createAllocation(
            int C_Currency_ID,
            String description,
            Timestamp dateAcct,
            BigDecimal Amount,
            BigDecimal DiscountAmt,
            BigDecimal WriteOffAmt,
            BigDecimal OverUnderAmt,
            int C_BPartner_ID,
            int C_Payment_ID,
            int C_Invoice_ID,
            int AD_Org_ID) {
        //	Process old Allocation
        if (m_allocation != null && m_allocation.getCurrencyId() != C_Currency_ID)
            processAllocation();

        //	New Allocation
        if (m_allocation == null) {
            m_allocation =
                    new MAllocationHdr(
                            getCtx(),
                            false,
                            dateAcct, //	automatic
                            C_Currency_ID,
                            "Auto " + description,
                            null);
            m_allocation.setOrgId(AD_Org_ID);
            if (!m_allocation.save()) return false;
        }

        //	New Allocation Line
        MAllocationLine aLine =
                new MAllocationLine(m_allocation, Amount, DiscountAmt, WriteOffAmt, OverUnderAmt);
        aLine.setBusinessPartnerId(C_BPartner_ID);
        aLine.setPaymentId(C_Payment_ID);
        aLine.setInvoiceId(C_Invoice_ID);
        return aLine.save();
    } //	createAllocation

    /**
     * Process Allocation
     *
     * @return true if processes/saved or none
     */
    private boolean processAllocation() {
        if (m_allocation == null) return true;
        boolean success = m_allocation.processIt(MAllocationHdr.DOCACTION_Complete);
        if (!success) {
            StringBuilder msg =
                    new StringBuilder("Allocation Process Failed ")
                            .append(m_allocation.getDocumentNo())
                            .append(" ")
                            .append(m_allocation.getProcessMsg());
            throw new IllegalStateException(msg.toString());
        } else m_allocation.saveEx();
        String message =
                Msg.parseTranslation(getCtx(), "@AllocationProcessed@ " + m_allocation.getDescription());
        addLog(0, m_allocation.getDateAcct(), null, message);
        m_allocation = null;
        return success;
    } //	processAllocation
} //	AllocationAuto
