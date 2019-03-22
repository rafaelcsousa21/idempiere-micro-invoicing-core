package org.idempiere.process;

import org.compiere.crm.MBPartner;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;

import java.math.BigDecimal;
import java.util.logging.Level;

/**
 * UnLink Business Partner from Organization
 *
 * @author Jorg Janke
 * @version $Id: BPartnerOrgUnLink.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class BPartnerOrgUnLink extends SvrProcess {
    /**
     * Business Partner
     */
    private int p_C_BPartner_ID;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("C_BPartner_ID"))
                p_C_BPartner_ID = ((BigDecimal) iProcessInfoParameter.getParameter()).intValue();
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message (text with variables)
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO)) log.info("doIt - C_BPartner_ID=" + p_C_BPartner_ID);
        if (p_C_BPartner_ID == 0) throw new IllegalArgumentException("No Business Partner ID");
        MBPartner bp = new MBPartner(getCtx(), p_C_BPartner_ID);
        if (bp.getId() == 0)
            throw new IllegalArgumentException(
                    "Business Partner not found - C_BPartner_ID=" + p_C_BPartner_ID);
        //
        if (bp.getLinkedOrganizationId() == 0)
            throw new IllegalArgumentException("Business Partner not linked to an Organization");
        bp.setLinkedOrganizationId(null);
        if (!bp.save()) throw new IllegalArgumentException("Business Partner not changed");

        return "OK";
    } //	doIt
} //	BPartnerOrgUnLink
