package org.idempiere.process;

import org.compiere.accounting.MOrder;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;

import java.math.BigDecimal;
import java.util.logging.Level;

/**
 * Copy Order Lines
 *
 * @author Jorg Janke
 * @version $Id: CopyFromOrder.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class CopyFromOrder extends SvrProcess {
    /**
     * The Order
     */
    private int p_C_Order_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (int i = 0; i < para.length; i++) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) ;
            else if (name.equals("C_Order_ID"))
                p_C_Order_ID = ((BigDecimal) para[i].getParameter()).intValue();
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message (clear text)
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        int To_C_Order_ID = getRecordId();
        if (log.isLoggable(Level.INFO))
            log.info("From C_Order_ID=" + p_C_Order_ID + " to " + To_C_Order_ID);
        if (To_C_Order_ID == 0) throw new IllegalArgumentException("Target C_Order_ID == 0");
        if (p_C_Order_ID == 0) throw new IllegalArgumentException("Source C_Order_ID == 0");
        MOrder from = new MOrder(getCtx(), p_C_Order_ID);
        MOrder to = new MOrder(getCtx(), To_C_Order_ID);
        //
        int no = to.copyLinesFrom(from, false, false); // 	no Attributes
        //
        return "@Copied@=" + no;
    } //	doIt
} //	CopyFromOrder
