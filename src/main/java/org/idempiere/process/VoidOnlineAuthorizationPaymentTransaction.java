package org.idempiere.process;

import org.compiere.invoicing.MPaymentTransaction;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.util.logging.Level;

public class VoidOnlineAuthorizationPaymentTransaction extends SvrProcess {

    protected void prepare() {
    }

    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO)) log.info("Record_ID=" + getRecordId());
        //	get Payment
        MPaymentTransaction pt = new MPaymentTransaction(getCtx(), getRecordId());

        if (!pt.getTenderType().equals(MPaymentTransaction.TENDERTYPE_CreditCard)
                || !pt.isOnline()
                || !pt.getTrxType().equals(MPaymentTransaction.TRXTYPE_Authorization))
            throw new AdempiereException(Msg.getMsg(Env.getCtx(), "ActionNotSupported"));
        if (pt.isVoided())
            throw new AdempiereException(Msg.getMsg(Env.getCtx(), "PaymentTransactionAlreadyVoided"));
        if (pt.isDelayedCapture())
            throw new AdempiereException(
                    Msg.getMsg(Env.getCtx(), "PaymentTransactionAlreadyDelayedCapture"));

        //  Process it
        boolean ok = pt.voidOnlineAuthorizationPaymentTransaction();
        pt.saveEx();
        if (!ok) throw new Exception(pt.getErrorMessage());
        return "OK";
    }
}
