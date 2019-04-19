package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.model.I_M_InOutLine;
import org.compiere.order.MInOutConfirm;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;

/**
 * Ship Confirmation Line Model
 *
 * @author Jorg Janke
 * @version $Id: MInOutLineConfirm.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MInOutLineConfirm extends X_M_InOutLineConfirm {
    /**
     *
     */
    private static final long serialVersionUID = -2753405320678781177L;
    /**
     * Ship Line
     */
    private I_M_InOutLine m_line = null;

    /**
     * Standard Constructor
     *
     * @param M_InOutLineConfirm_ID id
     */
    public MInOutLineConfirm(int M_InOutLineConfirm_ID) {
        super(M_InOutLineConfirm_ID);
        if (M_InOutLineConfirm_ID == 0) {
            setDifferenceQty(Env.ZERO);
            setScrappedQty(Env.ZERO);
            setProcessed(false);
        }
    } //	MInOutLineConfirm

    /**
     * Load Constructor
     *
     */
    public MInOutLineConfirm(Row row) {
        super(row);
    } //	MInOutLineConfirm

    /**
     * Parent Constructor
     *
     * @param header parent
     */
    public MInOutLineConfirm(MInOutConfirm header) {
        this(0);
        setClientOrg(header);
        setInOutConfirmId(header.getInOutConfirmId());
    } //	MInOutLineConfirm

    /**
     * Set Shipment Line
     *
     * @param line shipment line
     */
    public void setInOutLine(I_M_InOutLine line) {
        setInOutLineId(line.getInOutLineId());
        setTargetQty(line.getMovementQty()); // 	Confirmations in Storage UOM
        setConfirmedQty(getTargetQty()); // 	suggestion
        m_line = line;
    } //	setInOutLine

    /**
     * Get Shipment Line
     *
     * @return line
     */
    public I_M_InOutLine getLine() {
        if (m_line == null) m_line = new MInOutLine(getInOutLineId());
        return m_line;
    } //	getLine

    /**
     * Process Confirmation Line. - Update InOut Line
     *
     * @param isSOTrx     sales order
     * @param confirmType type
     * @return success
     */
    public boolean processLine(boolean isSOTrx, String confirmType) {
        I_M_InOutLine line = getLine();

        //	Customer
        if (MInOutConfirm.CONFIRMTYPE_CustomerConfirmation.equals(confirmType)) {
            line.setConfirmedQty(getConfirmedQty());
        }

        //	Drop Ship
        else if (!MInOutConfirm.CONFIRMTYPE_DropShipConfirm.equals(confirmType)) {
            if (MInOutConfirm.CONFIRMTYPE_PickQAConfirm.equals(confirmType)) {
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
        }

        //	Pick or QA


        return line.save();
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
        log.saveError("Error", MsgKt.getMsg("CannotDelete"));
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
