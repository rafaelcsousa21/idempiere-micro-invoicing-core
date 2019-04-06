package org.compiere.accounting;

import kotliquery.Row;

/**
 * Period Control Model
 *
 * @author Jorg Janke
 * @version $Id: MPeriodControl.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MPeriodControl extends X_C_PeriodControl {
    /**
     *
     */
    private static final long serialVersionUID = -3743823984541572396L;

    /**
     * Standard Constructor
     *
     * @param ctx                context
     * @param C_PeriodControl_ID 0
     */
    public MPeriodControl(int C_PeriodControl_ID) {
        super(C_PeriodControl_ID);
        if (C_PeriodControl_ID == 0) {
            setPeriodAction(X_C_PeriodControl.PERIODACTION_NoAction);
            setPeriodStatus(X_C_PeriodControl.PERIODSTATUS_NeverOpened);
        }
    } //	MPeriodControl

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MPeriodControl(Row row) {
        super(row);
    } //	MPeriodControl

    /**
     * Parent Constructor
     *
     * @param period      parent
     * @param DocBaseType doc base type
     */
    public MPeriodControl(MPeriod period, String DocBaseType) {
        this(

                period.getClientId(),
                period.getPeriodId(),
                DocBaseType);
        setClientOrg(period);
    } //	MPeriodControl

    /**
     * New Constructor
     *
     * @param ctx          context
     * @param AD_Client_ID client
     * @param C_Period_ID  period
     * @param DocBaseType  doc base type
     */
    public MPeriodControl(
            int AD_Client_ID, int C_Period_ID, String DocBaseType) {
        this(0);
        setClientOrg(AD_Client_ID, 0);
        setPeriodId(C_Period_ID);
        setDocBaseType(DocBaseType);
    } //	MPeriodControl

    /**
     * Is Period Open
     *
     * @return true if open
     */
    public boolean isOpen() {
        return X_C_PeriodControl.PERIODSTATUS_Open.equals(getPeriodStatus());
    } //	isOpen

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        return "MPeriodControl[" + getId() +
                "," +
                getDocBaseType() +
                ",Status=" +
                getPeriodStatus() +
                "]";
    } //	toString
} //	MPeriodControl
