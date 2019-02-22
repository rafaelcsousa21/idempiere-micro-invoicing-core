package org.compiere.invoicing;

import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoicePaySchedule;
import org.compiere.order.MPaySchedule;
import org.compiere.orm.Query;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

public class MPaymentTerm extends org.compiere.order.MPaymentTerm {
    public MPaymentTerm(Properties ctx, int C_PaymentTerm_ID) {
        super(ctx, C_PaymentTerm_ID);
    }

    /**
     * Apply Payment Term to Invoice
     *
     * @param invoice invoice
     * @return true if payment schedule is valid
     */
    public boolean apply(MInvoice invoice) {
        if (invoice == null || invoice.getId() == 0) {
            log.log(Level.SEVERE, "No valid invoice - " + invoice);
            return false;
        }

        // do not apply payment term if the invoice is not on credit or if total is zero
        if ((!(I_C_Invoice.PAYMENTRULE_OnCredit.equals(invoice.getPaymentRule())
                || I_C_Invoice.PAYMENTRULE_DirectDebit.equals(invoice.getPaymentRule())))
                || invoice.getGrandTotal().signum() == 0) return false;

        if (!isValid()) return applyNoSchedule(invoice);
        //
        MPaySchedule[] m_schedule = getSchedule(true);
        if (m_schedule.length <= 0) // Allow schedules with just one record
            return applyNoSchedule(invoice);
        else //	only if valid
            return applySchedule(invoice);
    } //	apply

    /**
     * Apply Payment Term with schedule to Invoice
     *
     * @param invoice invoice
     * @return true if payment schedule is valid
     */
    private boolean applySchedule(MInvoice invoice) {
        deleteInvoicePaySchedule(invoice.getC_Invoice_ID());
        //	Create Schedule
        MInvoicePaySchedule ips = null;
        BigDecimal remainder = invoice.getGrandTotal();
        MPaySchedule[] m_schedule = getSchedule(false);
        for (int i = 0; i < m_schedule.length; i++) {
            ips = new MInvoicePaySchedule(invoice, m_schedule[i]);
            ips.saveEx();
            if (log.isLoggable(Level.FINE)) log.fine(ips.toString());
            remainder = remainder.subtract(ips.getDueAmt());
        } //	for all schedules
        //	Remainder - update last
        if (remainder.compareTo(Env.ZERO) != 0 && ips != null) {
            ips.setDueAmt(ips.getDueAmt().add(remainder));
            ips.saveEx();
            if (log.isLoggable(Level.FINE)) log.fine("Remainder=" + remainder + " - " + ips);
        }

        //	updateInvoice
        if (invoice.getC_PaymentTerm_ID() != getC_PaymentTerm_ID())
            invoice.setC_PaymentTerm_ID(getC_PaymentTerm_ID());
        return invoice.validatePaySchedule();
    } //	applySchedule

    /**
     * Apply Payment Term without schedule to Invoice
     *
     * @param invoice invoice
     * @return false as no payment schedule
     */
    private boolean applyNoSchedule(I_C_Invoice invoice) {
        deleteInvoicePaySchedule(invoice.getC_Invoice_ID());
        //	updateInvoice
        if (invoice.getC_PaymentTerm_ID() != getC_PaymentTerm_ID())
            invoice.setC_PaymentTerm_ID(getC_PaymentTerm_ID());
        if (invoice.isPayScheduleValid()) invoice.setIsPayScheduleValid(false);
        return false;
    } //	applyNoSchedule

    /**
     * Delete existing Invoice Payment Schedule
     *
     * @param C_Invoice_ID id
     * @param trxName      transaction
     */
    private void deleteInvoicePaySchedule(int C_Invoice_ID) {
        Query query =
                new Query(Env.getCtx(), I_C_InvoicePaySchedule.Table_Name, "C_Invoice_ID=?");
        List<I_C_InvoicePaySchedule> ipsList = query.setParameters(C_Invoice_ID).list();
        for (I_C_InvoicePaySchedule ips : ipsList) {
            ips.deleteEx(true);
        }
        if (log.isLoggable(Level.FINE))
            log.fine("C_Invoice_ID=" + C_Invoice_ID + " - #" + ipsList.size());
    } //	deleteInvoicePaySchedule
}
