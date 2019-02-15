package org.idempiere.process;

import org.compiere.accounting.MOrder;
import org.compiere.accounting.MOrderLine;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.order.MInOut;
import org.compiere.order.MInOutLine;
import org.compiere.process.SvrProcess;
import org.idempiere.common.util.Env;

import java.sql.Timestamp;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.getSQLValue;

/**
 * Create (Generate) Invoice from Shipment
 *
 * @author Jorg Janke
 * @version $Id: OrderLineCreateShipment.java,v 1.1 2007/07/23 05:34:35 mfuggle Exp $
 */
public class OrderLineCreateShipment extends SvrProcess {
  /** Shipment */
  private int p_C_OrderLine_ID = 0;

  private Timestamp p_MovementDate = null;

  /** Prepare - e.g., get Parameters. */
  protected void prepare() {
    IProcessInfoParameter[] para = getParameter();
    for (int i = 0; i < para.length; i++) {
      String name = para[i].getParameterName();
      if (para[i].getParameter() == null) ;
      if (name.equals("MovementDate")) p_MovementDate = (Timestamp) para[i].getParameter();
      else log.log(Level.SEVERE, "Unknown Parameter: " + name);
    }

    if (p_MovementDate == null) p_MovementDate = Env.getContextAsDate(getCtx(), "#Date");
    if (p_MovementDate == null) p_MovementDate = new Timestamp(System.currentTimeMillis());

    p_C_OrderLine_ID = getRecord_ID();
  } //	prepare

  /**
   * Create Invoice.
   *
   * @return document no
   * @throws Exception
   */
  protected String doIt() throws Exception {
    if (log.isLoggable(Level.INFO)) log.info("C_OrderLine_ID=" + p_C_OrderLine_ID);
    if (p_C_OrderLine_ID == 0) throw new IllegalArgumentException("No OrderLine");
    //
    MOrderLine line = new MOrderLine(getCtx(), p_C_OrderLine_ID);
    if (line.getId() == 0) throw new IllegalArgumentException("Order line not found");
    MOrder order = new MOrder(getCtx(), line.getC_Order_ID());
    if (!MOrder.DOCSTATUS_Completed.equals(order.getDocStatus()))
      throw new IllegalArgumentException("Order not completed");

    if ((line.getQtyOrdered().subtract(line.getQtyDelivered())).compareTo(Env.ZERO) <= 0)
      return "Ordered quantity already shipped";

    int C_DocTypeShipment_ID =
        getSQLValue(
            "SELECT C_DocTypeShipment_ID FROM C_DocType WHERE C_DocType_ID=?",
            order.getC_DocType_ID());

    MInOut shipment = new MInOut(order, C_DocTypeShipment_ID, p_MovementDate);
    shipment.setM_Warehouse_ID(line.getM_Warehouse_ID());
    shipment.setMovementDate(line.getDatePromised());
    if (!shipment.save()) throw new IllegalArgumentException("Cannot save shipment header");

    MInOutLine sline = new MInOutLine(shipment);
    sline.setOrderLine(line, 0, line.getQtyReserved());
    // sline.setDatePromised(line.getDatePromised());
    sline.setQtyEntered(line.getQtyReserved());
    sline.setC_UOM_ID(line.getC_UOM_ID());
    sline.setQty(line.getQtyReserved());
    sline.setM_Warehouse_ID(line.getM_Warehouse_ID());
    if (!sline.save()) throw new IllegalArgumentException("Cannot save Shipment Line");

    return shipment.getDocumentNo();
  } //	OrderLineCreateShipment
} //	OrderLineCreateShipment
