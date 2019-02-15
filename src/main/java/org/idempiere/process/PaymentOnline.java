package org.idempiere.process;

// import org.compiere.process.*;
import java.util.logging.Level;
import org.compiere.accounting.MPayment;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;

/**
 * Online Payment Process
 *
 * @author Jorg Janke
 * @version $Id: PaymentOnline.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class PaymentOnline extends SvrProcess {
  /** Prepare - e.g., get Parameters. */
  protected void prepare() {
    IProcessInfoParameter[] para = getParameter();
    for (int i = 0; i < para.length; i++) {
      String name = para[i].getParameterName();
      if (para[i].getParameter() == null) ;
      else log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
    }
  } //	prepare

  /**
   * Perform process.
   *
   * @return Message
   * @throws Exception
   */
  protected String doIt() throws Exception {
    if (log.isLoggable(Level.INFO)) log.info("Record_ID=" + getRecord_ID());
    //	get Payment
    MPayment pp = new MPayment(getCtx(), getRecord_ID());

    //  Process it
    boolean ok = pp.processOnline();
    pp.saveEx();
    if (!ok) throw new Exception(pp.getErrorMessage());
    return "OK";
  } //	doIt
} //	PaymentOnline
