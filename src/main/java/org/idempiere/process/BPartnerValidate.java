package org.idempiere.process;

import org.compiere.accounting.MPayment;
import org.compiere.crm.MBPartner;
import org.compiere.invoicing.MInvoice;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.orm.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.AdempiereUserError;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Validate Business Partner
 *
 * @author Jorg Janke
 * @version $Id: BPartnerValidate.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $ FR: [ 2214883 ] Remove
 * SQL code and Replace for Query - red1, teo_sarca
 */
public class BPartnerValidate extends SvrProcess {
    /**
     * BPartner ID
     */
    int p_C_BPartner_ID = 0;
    /**
     * BPartner Group
     */
    int p_C_BP_Group_ID = 0;

    /**
     * Prepare
     */
    protected void prepare() {
        p_C_BPartner_ID = getRecordId();
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("C_BPartner_ID")) p_C_BPartner_ID = iProcessInfoParameter.getParameterAsInt();
            else if (name.equals("C_BP_Group_ID")) p_C_BP_Group_ID = iProcessInfoParameter.getParameterAsInt();
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
    } //	prepare

    /**
     * Process
     *
     * @return info
     * @throws Exception
     */
    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO))
            log.info("C_BPartner_ID=" + p_C_BPartner_ID + ", C_BP_Group_ID=" + p_C_BP_Group_ID);
        if (p_C_BPartner_ID == 0 && p_C_BP_Group_ID == 0)
            throw new AdempiereUserError("No Business Partner/Group selected");

        if (p_C_BP_Group_ID == 0) {
            MBPartner bp = new MBPartner(p_C_BPartner_ID);
            if (bp.getId() == 0)
                throw new AdempiereUserError(
                        "Business Partner not found - C_BPartner_ID=" + p_C_BPartner_ID);
            checkBP(bp);
        } else {
            final String whereClause = "C_BP_Group_ID=?";
            List<I_C_BPartner> it =
                    new Query<I_C_BPartner>(I_C_BPartner.Table_Name, whereClause)
                            .setParameters(p_C_BP_Group_ID)
                            .setOnlyActiveRecords(true)
                            .list();
            for (I_C_BPartner partner : it) {
                checkBP(partner);
            }
        }
        //
        return "OK";
    } //	doIt

    /**
     * Check BP
     *
     * @param bp bp
     * @throws SQLException
     */
    private void checkBP(I_C_BPartner bp) throws SQLException {
        addLog(0, null, null, bp.getName() + ":");
        //	See also VMerge.postMerge
        checkPayments(bp);
        checkInvoices(bp);
        //
        bp.setTotalOpenBalance();
        bp.setActualLifeTimeValue();
        bp.saveEx();
        //
        //	if (bp.getSalesOrderCreditUsed().signum() != 0)
        addLog(0, null, bp.getSalesOrderCreditUsed(), MsgKt.getElementTranslation("SO_CreditUsed"));
        addLog(0, null, bp.getTotalOpenBalance(), MsgKt.getElementTranslation("TotalOpenBalance"));
        addLog(0, null, bp.getActualLifeTimeValue(), MsgKt.getElementTranslation("ActualLifeTimeValue"));
        //
    } //	checkBP

    /**
     * Check Payments
     *
     * @param bp business partner
     */
    private void checkPayments(I_C_BPartner bp) {
        //	See also VMerge.postMerge
        int changed = 0;
        I_C_Payment[] payments = MPayment.getOfBPartner(bp.getBusinessPartnerId());
        for (I_C_Payment payment : payments) {
            if (payment.testAllocation()) {
                payment.saveEx();
                changed++;
            }
        }
        if (changed != 0)
            addLog(
                    0,
                    null,
                    new BigDecimal(payments.length),
                    MsgKt.getElementTranslation("C_Payment_ID") + " - #" + changed);
    } //	checkPayments

    /**
     * Check Invoices
     *
     * @param bp business partner
     */
    private void checkInvoices(I_C_BPartner bp) {
        //	See also VMerge.postMerge
        int changed = 0;
        I_C_Invoice[] invoices = MInvoice.getOfBPartner(bp.getBusinessPartnerId());
        for (I_C_Invoice invoice : invoices) {
            if (invoice.testAllocation()) {
                invoice.saveEx();
                changed++;
            }
        }
        if (changed != 0)
            addLog(
                    0,
                    null,
                    new BigDecimal(invoices.length),
                    MsgKt.getElementTranslation("C_Invoice_ID") + " - #" + changed);
    } //	checkInvoices
} //	BPartnerValidate
