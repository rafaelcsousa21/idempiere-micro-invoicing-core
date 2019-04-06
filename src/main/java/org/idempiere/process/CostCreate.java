package org.idempiere.process;

import org.compiere.accounting.MCostDetail;
import org.compiere.accounting.MProduct;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.idempiere.common.util.AdempiereUserError;

import java.util.logging.Level;

/**
 * Create/Update Costing for Product
 *
 * @author Jorg Janke
 * @version $Id: CostCreate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class CostCreate extends SvrProcess {
    /**
     * Product
     */
    private int p_M_Product_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();
            //	log.fine("prepare - " + para[i]);

            if (name.equals("M_Product_ID")) p_M_Product_ID = iProcessInfoParameter.getParameterAsInt();
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
        if (log.isLoggable(Level.INFO)) log.info("M_Product_ID=" + p_M_Product_ID);
        if (p_M_Product_ID == 0)
            throw new AdempiereUserError("@NotFound@: @M_Product_ID@ = " + p_M_Product_ID);
        MProduct product = MProduct.get(p_M_Product_ID);
        if (product.getId() != p_M_Product_ID)
            throw new AdempiereUserError("@NotFound@: @M_Product_ID@ = " + p_M_Product_ID);
        //
        if (MCostDetail.processProduct(product)) return "@OK@";
        return "@Error@";
    } //	doIt
} //	CostCreate
