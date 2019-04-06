package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_R_Status;
import org.idempiere.common.util.CCache;

/**
 * Request Status Model
 *
 * @author Jorg Janke
 * @version $Id: MStatus.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MStatus extends X_R_Status {
    /**
     *
     */
    private static final long serialVersionUID = -4549127671165860354L;
    /**
     * Cache
     */
    private static CCache<Integer, MStatus> s_cache =
            new CCache<Integer, MStatus>(I_R_Status.Table_Name, 10);
    /**
     * Default Cache (Key=Client)
     */
    private static CCache<Integer, MStatus> s_cacheDefault =
            new CCache<Integer, MStatus>(I_R_Status.Table_Name, "R_Status_Default", 10);

    /**
     * ************************************************************************ Default Constructor
     *
     * @param ctx         context
     * @param R_Status_ID is
     */
    public MStatus(int R_Status_ID) {
        super(R_Status_ID);
        if (R_Status_ID == 0) {
            //	setValue (null);
            //	setName (null);
            setIsClosed(false); // N
            setIsDefault(false);
            setIsFinalClose(false); // N
            setIsOpen(false);
            setIsWebCanUpdate(true);
        }
    } //	MStatus

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MStatus(Row row) {
        super(row);
    } //	MStatus

    /**
     * Get Request Status (cached)
     *
     * @param ctx         context
     * @param R_Status_ID id
     * @return Request Status or null
     */
    public static MStatus get(int R_Status_ID) {
        if (R_Status_ID == 0) return null;
        Integer key = R_Status_ID;
        MStatus retValue = s_cache.get(key);
        if (retValue == null) {
            retValue = new MStatus(R_Status_ID);
            s_cache.put(key, retValue);
        }
        return retValue;
    } //	get

    /**
     * Get Default Request Status
     *
     * @param ctx              context
     * @param R_RequestType_ID request type
     * @return Request Type
     */
    public static MStatus getDefault(int R_RequestType_ID) {
        Integer key = R_RequestType_ID;
        MStatus retValue = s_cacheDefault.get(key);
        if (retValue != null) return retValue;
        retValue = MBaseStatusKt.getDefaultRequestStatus(R_RequestType_ID);
        if (retValue != null) s_cacheDefault.put(key, retValue);
        return retValue;
    } //	getDefault

    /**
     * Get Closed Status
     *
     * @param ctx context
     * @return Request Type
     */
    public static MStatus[] getClosed() {
        return MBaseStatusKt.getClosedStatuses();
    } //	get

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        if (isOpen() && isClosed()) setIsClosed(false);
        if (isFinalClose() && !isClosed()) setIsFinalClose(false);
        //
        if (!isWebCanUpdate() && getUpdateStatusId() != 0) setUpdateStatusId(0);
        if (getTimeoutDays() == 0 && getNextStatusId() != 0) setNextStatusId(0);
        //
        return true;
    } //	beforeSave

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        return "MStatus[" + getId() + "-" + getName() + "]";
    } //	toString
} //	MStatus
