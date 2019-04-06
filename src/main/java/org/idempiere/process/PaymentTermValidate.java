package org.idempiere.process;

import org.compiere.order.MPaymentTerm;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.idempiere.common.util.AdempiereUserError;

import java.util.logging.Level;

/**
 * Validate Payment Term and Schedule
 *
 * @author Jorg Janke
 * @version $Id: PaymentTermValidate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class PaymentTermValidate extends SvrProcess {
    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO)) log.info("C_PaymentTerm_ID=" + getRecordId());
        MPaymentTerm pt = new MPaymentTerm(getRecordId());
        String msg = pt.validate();
        pt.saveEx();
        //
        String validMsg = Msg.parseTranslation("@OK@");
        if (validMsg.equals(msg)) return msg;
        throw new AdempiereUserError(msg);
    } //	doIt
} //	PaymentTermValidate
