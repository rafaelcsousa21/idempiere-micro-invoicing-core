package org.compiere.invoicing;

import org.compiere.order.MInOutConfirm;
import org.compiere.util.Msg;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Ship Confirmation Line Model
 *
 * @author Jorg Janke
 * @version $Id: MInOutLineConfirm.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MInOutLineConfirm extends X_M_InOutLineConfirm {
  /** */
  private static final long serialVersionUID = -2753405320678781177L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param M_InOutLineConfirm_ID id
   * @param trxName transaction
   */
  public MInOutLineConfirm(Properties ctx, int M_InOutLineConfirm_ID, String trxName) {
    super(ctx, M_InOutLineConfirm_ID, trxName);
    if (M_InOutLineConfirm_ID == 0) {
      //	setM_InOutConfirm_ID (0);
      //	setM_InOutLine_ID (0);
      //	setTargetQty (Env.ZERO);
      //	setConfirmedQty (Env.ZERO);
      setDifferenceQty(Env.ZERO);
      setScrappedQty(Env.ZERO);
      setProcessed(false);
    }
  } //	MInOutLineConfirm

  /**
   * Load Construvtor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MInOutLineConfirm(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MInOutLineConfirm

  /**
   * Parent Construvtor
   *
   * @param header parent
   */
  public MInOutLineConfirm(MInOutConfirm header) {
    this(header.getCtx(), 0, null);
    setClientOrg(header);
    setM_InOutConfirm_ID(header.getM_InOutConfirm_ID());
  } //	MInOutLineConfirm

  /** Ship Line */
  private MInOutLine m_line = null;

  /**
   * Set Shipment Line
   *
   * @param line shipment line
   */
  public void setInOutLine(MInOutLine line) {
    setM_InOutLine_ID(line.getM_InOutLine_ID());
    setTargetQty(line.getMovementQty()); // 	Confirmations in Storage UOM
    setConfirmedQty(getTargetQty()); // 	suggestion
    m_line = line;
  } //	setInOutLine

  /**
   * Get Shipment Line
   *
   * @return line
   */
  public MInOutLine getLine() {
    if (m_line == null) m_line = new MInOutLine(getCtx(), getM_InOutLine_ID(), null);
    return m_line;
  } //	getLine

  /**
   * Process Confirmation Line. - Update InOut Line
   *
   * @param isSOTrx sales order
   * @param confirmType type
   * @return success
   */
  public boolean processLine(boolean isSOTrx, String confirmType) {
    MInOutLine line = getLine();

    //	Customer
    if (MInOutConfirm.CONFIRMTYPE_CustomerConfirmation.equals(confirmType)) {
      line.setConfirmedQty(getConfirmedQty());
    }

    //	Drop Ship
    else if (MInOutConfirm.CONFIRMTYPE_DropShipConfirm.equals(confirmType)) {

    }

    //	Pick or QA
    else if (MInOutConfirm.CONFIRMTYPE_PickQAConfirm.equals(confirmType)) {
      line.setTargetQty(getTargetQty());
      line.setMovementQty(getConfirmedQty()); // 	Entered NOT changed
      line.setPickedQty(getConfirmedQty());
      //
      line.setScrappedQty(getScrappedQty());
    }

    //	Ship or Receipt
    else if (MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm.equals(confirmType)) {
      line.setTargetQty(getTargetQty());
      BigDecimal qty = getConfirmedQty();
      if (!isSOTrx) //	In PO, we have the responsibility for scapped
      qty = qty.add(getScrappedQty());
      line.setMovementQty(qty); // 	Entered NOT changed
      //
      line.setScrappedQty(getScrappedQty());
    }
    //	Vendor
    else if (MInOutConfirm.CONFIRMTYPE_VendorConfirmation.equals(confirmType)) {
      line.setConfirmedQty(getConfirmedQty());
    }

    return line.save(null);
  } //	processConfirmation

  /**
   * Is Fully Confirmed
   *
   * @return true if Target = Confirmed qty
   */
  public boolean isFullyConfirmed() {
    return getTargetQty().compareTo(getConfirmedQty()) == 0;
  } //	isFullyConfirmed

  /**
   * Before Delete - do not delete
   *
   * @return false
   */
  protected boolean beforeDelete() {
    log.saveError("Error", Msg.getMsg(getCtx(), "CannotDelete"));
    return false;
  } //	beforeDelete

  /**
   * Before Save
   *
   * @param newRecord new
   * @return true
   */
  protected boolean beforeSave(boolean newRecord) {
    //	Calculate Difference = Target - Confirmed - Scrapped
    BigDecimal difference = getTargetQty();
    difference = difference.subtract(getConfirmedQty());
    difference = difference.subtract(getScrappedQty());
    setDifferenceQty(difference);
    //
    return true;
  } //	beforeSave
} //	MInOutLineConfirm
