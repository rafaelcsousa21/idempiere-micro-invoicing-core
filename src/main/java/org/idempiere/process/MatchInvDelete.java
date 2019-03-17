package org.idempiere.process;

import org.compiere.accounting.MMatchInv;
import org.compiere.process.SvrProcess;
import org.idempiere.common.util.AdempiereUserError;

import java.util.logging.Level;

/**
 * Delete Inv Match
 *
 * @author Jorg Janke
 * @version $Id: MatchInvDelete.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class MatchInvDelete extends SvrProcess {
    /**
     * ID
     */
    private int p_M_MatchInv_ID = 0;

    /**
     * Prepare
     */
    protected void prepare() {
        p_M_MatchInv_ID = getRecordId();
    } //	prepare

    /**
     * Process
     *
     * @return message
     * @throws Exception
     */
    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO)) log.info("M_MatchInv_ID=" + p_M_MatchInv_ID);
        MMatchInv inv = new MMatchInv(getCtx(), p_M_MatchInv_ID);
        if (inv.getId() == 0)
            throw new AdempiereUserError("@NotFound@ @M_MatchInv_ID@ " + p_M_MatchInv_ID);
        if (inv.delete(true)) return "@OK@";
        inv.saveEx();
        return "@Error@";
    } //	doIt
} //	MatchInvDelete
