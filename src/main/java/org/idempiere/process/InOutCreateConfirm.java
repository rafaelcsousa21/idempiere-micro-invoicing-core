package org.idempiere.process;

import org.compiere.invoicing.MInOut;
import org.compiere.invoicing.MInOutConfirm;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.model.I_M_InOutConfirm;
import org.compiere.process.SvrProcess;

import java.util.logging.Level;

/**
 * Create Confirmation From Shipment
 *
 * @author Jorg Janke
 * @version $Id: InOutCreateConfirm.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class InOutCreateConfirm extends SvrProcess {
    /**
     * Shipment
     */
    private int p_M_InOut_ID = 0;
    /**
     * Confirmation Type
     */
    private String p_ConfirmType = null;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("ConfirmType")) p_ConfirmType = (String) iProcessInfoParameter.getParameter();
            else log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
        p_M_InOut_ID = getRecordId();
    } //	prepare

    /**
     * Create Confirmation
     *
     * @return document no
     * @throws Exception
     */
    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO))
            log.info("M_InOut_ID=" + p_M_InOut_ID + ", Type=" + p_ConfirmType);
        MInOut shipment = new MInOut(p_M_InOut_ID);
        if (shipment.getId() == 0)
            throw new IllegalArgumentException("Not found M_InOut_ID=" + p_M_InOut_ID);
        //
        I_M_InOutConfirm confirm = MInOutConfirm.create(shipment, p_ConfirmType, true);
        if (confirm == null)
            throw new Exception("Cannot create Confirmation for " + shipment.getDocumentNo());
        //

        addLog(
                confirm.getInOutConfirmId(),
                null,
                null,
                confirm.getDocumentNo(),
                confirm.getTableId(),
                confirm.getInOutConfirmId());

        return confirm.getDocumentNo();
    } //	doIt
} //	InOutCreateConfirm
