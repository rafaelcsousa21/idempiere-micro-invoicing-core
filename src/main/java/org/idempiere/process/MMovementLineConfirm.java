package org.idempiere.process;

import kotliquery.Row;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;

public class MMovementLineConfirm extends X_M_MovementLineConfirm {

    /**
     *
     */
    private static final long serialVersionUID = 2406580342096137696L;
    /**
     * Movement Line
     */
    private MMovementLine m_line = null;

    /**
     * Standard Constructor
     *
     * @param M_MovementLineConfirm_ID id
     */
    public MMovementLineConfirm(int M_MovementLineConfirm_ID) {
        super(M_MovementLineConfirm_ID);
        if (M_MovementLineConfirm_ID == 0) {
            setConfirmedQty(Env.ZERO);
            setDifferenceQty(Env.ZERO);
            setScrappedQty(Env.ZERO);
            setTargetQty(Env.ZERO);
            setProcessed(false);
        }
    } //	M_MovementLineConfirm

    /**
     * M_MovementLineConfirm
     */
    public MMovementLineConfirm(Row row) {
        super(row);
    } //	M_MovementLineConfirm

    /**
     * Parent Constructor
     *
     * @param parent parent
     */
    public MMovementLineConfirm(MMovementConfirm parent) {
        this(0);
        setClientOrg(parent);
        setMovementConfirmId(parent.getMovementConfirmId());
    } //	MMovementLineConfirm

    /**
     * Set Movement Line
     *
     * @param line line
     */
    public void setMovementLine(MMovementLine line) {
        setMovementLineId(line.getMovementLineId());
        setTargetQty(line.getMovementQty());
        setConfirmedQty(getTargetQty()); // 	suggestion
        m_line = line;
    } //	setMovementLine

    /**
     * Get Movement Line
     *
     * @return line
     */
    public MMovementLine getLine() {
        if (m_line == null) m_line = new MMovementLine(getMovementLineId());
        return m_line;
    } //	getLine

    /**
     * Process Confirmation Line. - Update Movement Line
     *
     * @return success
     */
    public boolean processLine() {
        MMovementLine line = getLine();

        line.setTargetQty(getTargetQty());
        line.setMovementQty(getConfirmedQty());
        line.setConfirmedQty(getConfirmedQty());
        line.setScrappedQty(getScrappedQty());

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
        return false;
    } //	beforeDelete

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        //	Calculate Difference = Target - Confirmed
        BigDecimal difference = getTargetQty();
        difference = difference.subtract(getConfirmedQty());
        setDifferenceQty(difference);
        //
        return true;
    } //	beforeSave
} //	M_MovementLineConfirm
