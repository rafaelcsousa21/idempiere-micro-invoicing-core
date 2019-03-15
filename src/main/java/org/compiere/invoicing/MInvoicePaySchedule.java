package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.bo.MCurrency;
import org.compiere.order.MPaySchedule;
import org.compiere.orm.TimeUtil;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Invoice Payment Schedule Model
 *
 * @author Jorg Janke
 * @version $Id: MInvoicePaySchedule.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MInvoicePaySchedule extends X_C_InvoicePaySchedule {
    /**
     *
     */
    private static final long serialVersionUID = 4613382619117842586L;
    /**
     * Static Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MInvoicePaySchedule.class);
    /**
     * Parent
     */
    private MInvoice m_parent = null;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx                     context
     * @param C_InvoicePaySchedule_ID id
     */
    public MInvoicePaySchedule(Properties ctx, int C_InvoicePaySchedule_ID) {
        super(ctx, C_InvoicePaySchedule_ID);
        if (C_InvoicePaySchedule_ID == 0) {
            setIsValid(false);
        }
    } //	MInvoicePaySchedule

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MInvoicePaySchedule(Properties ctx, Row row) {
        super(ctx, row);
    } //	MInvoicePaySchedule

    /**
     * Parent Constructor
     *
     * @param invoice     invoice
     * @param paySchedule payment schedule
     */
    public MInvoicePaySchedule(MInvoice invoice, MPaySchedule paySchedule) {
        super(invoice.getCtx(), 0);
        m_parent = invoice;
        setClientOrg(invoice);
        setInvoiceId(invoice.getInvoiceId());
        setC_PaySchedule_ID(paySchedule.getC_PaySchedule_ID());

        //	Amounts
        int scale = MCurrency.getStdPrecision(getCtx(), invoice.getCurrencyId());
        BigDecimal due = invoice.getGrandTotal();
        if (due.compareTo(Env.ZERO) == 0) {
            setDueAmt(Env.ZERO);
            setDiscountAmt(Env.ZERO);
            setIsValid(false);
        } else {
            due =
                    due.multiply(paySchedule.getPercentage())
                            .divide(Env.ONEHUNDRED, scale, BigDecimal.ROUND_HALF_UP);
            setDueAmt(due);
            BigDecimal discount =
                    due.multiply(paySchedule.getDiscount())
                            .divide(Env.ONEHUNDRED, scale, BigDecimal.ROUND_HALF_UP);
            setDiscountAmt(discount);
            setIsValid(true);
        }

        //	Dates
        Timestamp dueDate = TimeUtil.addDays(invoice.getDateInvoiced(), paySchedule.getNetDays());
        setDueDate(dueDate);
        Timestamp discountDate =
                TimeUtil.addDays(invoice.getDateInvoiced(), paySchedule.getDiscountDays());
        setDiscountDate(discountDate);
    } //	MInvoicePaySchedule

    /**
     * Get Payment Schedule of the invoice
     *
     * @param ctx                     context
     * @param C_Invoice_ID            invoice id (direct)
     * @param C_InvoicePaySchedule_ID id (indirect)
     * @return array of schedule
     */
    public static MInvoicePaySchedule[] getInvoicePaySchedule(
            Properties ctx, int C_Invoice_ID, int C_InvoicePaySchedule_ID) {
        return MBaseInvoicePayScheduleKt.getInvoicePaySchedule(ctx, C_Invoice_ID, C_InvoicePaySchedule_ID);
    } //	getSchedule

    /**
     * @return Returns the parent.
     */
    public MInvoice getParent() {
        if (m_parent == null) m_parent = new MInvoice(getCtx(), getInvoiceId());
        return m_parent;
    } //	getParent

    /**
     * @param parent The parent to set.
     */
    public void setParent(MInvoice parent) {
        m_parent = parent;
    } //	setParent

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MInvoicePaySchedule[");
        sb.append(getId())
                .append("-Due=")
                .append(getDueDate())
                .append("/")
                .append(getDueAmt())
                .append(";Discount=")
                .append(getDiscountDate())
                .append("/")
                .append(getDiscountAmt())
                .append("]");
        return sb.toString();
    } //	toString

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        if (is_ValueChanged("DueAmt")) {
            log.fine("beforeSave");
            setIsValid(false);
        }
        return true;
    } //	beforeSave

    /**
     * After Save
     *
     * @param newRecord new
     * @param success   success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        if (is_ValueChanged("DueAmt") || is_ValueChanged("IsActive")) {
            log.fine("afterSave");
            getParent();
            m_parent.validatePaySchedule();
            m_parent.saveEx();
        }
        return success;
    } //	afterSave

    @Override
    protected boolean afterDelete(boolean success) {
        if (!success) return success;
        log.fine("afterDelete");
        getParent();
        m_parent.validatePaySchedule();
        m_parent.saveEx();
        return success;
    }
} //	MInvoicePaySchedule
