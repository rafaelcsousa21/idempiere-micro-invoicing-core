package org.idempiere.process;

import java.math.BigDecimal;
import java.util.logging.Level;
import org.compiere.accounting.MOrder;
import org.compiere.accounting.MOrderLine;
import org.compiere.invoicing.MInvoice;
import org.compiere.invoicing.MInvoiceLine;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.idempiere.common.util.Env;

/**
 * Re-Price Order or Invoice
 *
 * @author Jorg Janke
 * @version $Id: OrderRePrice.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class OrderRePrice extends SvrProcess {
  /** Order to re-price */
  private int p_C_Order_ID = 0;
  /** Invoice to re-price */
  private int p_C_Invoice_ID = 0;

  /** Prepare - e.g., get Parameters. */
  protected void prepare() {
    IProcessInfoParameter[] para = getParameter();
    for (int i = 0; i < para.length; i++) {
      String name = para[i].getParameterName();
      if (para[i].getParameter() == null) ;
      else if (name.equals("C_Order_ID"))
        p_C_Order_ID = ((BigDecimal) para[i].getParameter()).intValue();
      else if (name.equals("C_Invoice_ID"))
        p_C_Invoice_ID = ((BigDecimal) para[i].getParameter()).intValue();
      else log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
    }
  } //	prepare

  /**
   * Perform process.
   *
   * @return Message (clear text)
   * @throws Exception if not successful
   */
  protected String doIt() throws Exception {
    if (log.isLoggable(Level.INFO))
      log.info("C_Order_ID=" + p_C_Order_ID + ", C_Invoice_ID=" + p_C_Invoice_ID);
    if (p_C_Order_ID == 0 && p_C_Invoice_ID == 0)
      throw new IllegalArgumentException("Nothing to do");

    StringBuilder retValue = new StringBuilder();
    if (p_C_Order_ID != 0) {
      MOrder order = new MOrder(getCtx(), p_C_Order_ID);
      BigDecimal oldPrice = order.getGrandTotal();
      MOrderLine[] lines = order.getLines();
      for (int i = 0; i < lines.length; i++) {
        lines[i].setPrice(order.getM_PriceList_ID());
        lines[i].saveEx();
      }
      order = new MOrder(getCtx(), p_C_Order_ID);
      BigDecimal newPrice = order.getGrandTotal();
      retValue =
          new StringBuilder()
              .append(order.getDocumentNo())
              .append(":  ")
              .append(oldPrice)
              .append(" -> ")
              .append(newPrice);
    }
    if (p_C_Invoice_ID != 0) {
      MInvoice invoice = new MInvoice(getCtx(), p_C_Invoice_ID);
      BigDecimal oldPrice = invoice.getGrandTotal();
      MInvoiceLine[] lines = invoice.getLines(false);
      for (int i = 0; i < lines.length; i++) {
        lines[i].setPrice(invoice.getM_PriceList_ID(), invoice.getC_BPartner_ID());
        if (lines[i].is_Changed()) {
          lines[i].setTaxAmt();
          lines[i].saveEx();
        }
      }
      invoice = new MInvoice(getCtx(), p_C_Invoice_ID);
      BigDecimal newPrice = invoice.getGrandTotal();
      if (retValue.length() > 0) retValue.append(Env.NL);
      retValue
          .append(invoice.getDocumentNo())
          .append(":  ")
          .append(oldPrice)
          .append(" -> ")
          .append(newPrice);
    }
    //
    return retValue.toString();
  } //	doIt
} //	OrderRePrice
