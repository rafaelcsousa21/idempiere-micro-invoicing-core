package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_R_RequestType;
import org.compiere.orm.MRole;
import org.compiere.orm.Query;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.sql.Timestamp;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.convertDate;

/**
 * Request Type Model
 *
 * @author Jorg Janke
 * @version $Id: MRequestType.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * <p>Teo Sarca - bug fix [ 1642833 ] MRequestType minor typo bug
 */
public class MRequestType extends X_R_RequestType {

    /**
     *
     */
    private static final long serialVersionUID = 6235793036503665638L;
    /**
     * Static Logger
     */
    @SuppressWarnings("unused")
    private static CLogger s_log = CLogger.getCLogger(MRequestType.class);
    /**
     * Cache
     */
    private static CCache<Integer, MRequestType> s_cache =
            new CCache<Integer, MRequestType>(I_R_RequestType.Table_Name, 10);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx              context
     * @param R_RequestType_ID id
     * @param trxName          transaction
     */
    public MRequestType(Properties ctx, int R_RequestType_ID) {
        super(ctx, R_RequestType_ID);
        if (R_RequestType_ID == 0) {
            //	setRequestTypeId (0);
            //	setName (null);
            setDueDateTolerance(7);
            setIsDefault(false);
            setIsEMailWhenDue(false);
            setIsEMailWhenOverdue(false);
            setIsSelfService(true); // Y
            setAutoDueDateDays(0);
            setConfidentialType(X_R_RequestType.CONFIDENTIALTYPE_PublicInformation);
            setIsAutoChangeRequest(false);
            setIsConfidentialInfo(false);
            setIsIndexed(true);
            setIsInvoiced(false);
        }
    } //	MRequestType

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MRequestType(Properties ctx, Row row) {
        super(ctx, row);
    } //	MRequestType

    /**
     * Get Request Type (cached)
     *
     * @param ctx              context
     * @param R_RequestType_ID id
     * @return Request Type
     */
    public static MRequestType get(Properties ctx, int R_RequestType_ID) {
        Integer key = R_RequestType_ID;
        MRequestType retValue = s_cache.get(key);
        if (retValue == null) {
            retValue = new MRequestType(ctx, R_RequestType_ID);
            s_cache.put(key, retValue);
        }
        return retValue;
    } //	get

    /**
     * Get Default Request Type
     *
     * @param ctx context
     * @return Request Type
     */
    public static MRequestType getDefault(Properties ctx) {
        int AD_Client_ID = Env.getClientId(ctx);

        // FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
        final String whereClause = "AD_Client_ID IN (0," + AD_Client_ID + ")";
        MRequestType retValue =
                new Query(ctx, I_R_RequestType.Table_Name, whereClause)
                        .setOrderBy("IsDefault DESC, clientId DESC")
                        .first();

        if (retValue != null && !retValue.isDefault()) retValue = null;

        return retValue;
    } //	get

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        if (getStatusCategoryId() == 0) {
            MStatusCategory sc = MStatusCategory.getDefault(getCtx());
            if (sc != null && sc.getStatusCategoryId() != 0)
                setStatusCategoryId(sc.getStatusCategoryId());
        }
        return true;
    } //	beforeSave

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MRequestType[");
        sb.append(getId()).append("-").append(getName()).append("]");
        return sb.toString();
    } //	toString

    /**
     * Get Sql to return single value for the Performance Indicator
     *
     * @param restrictions    array of goal restrictions
     * @param MeasureScope    scope of this value
     * @param MeasureDataType data type
     * @param reportDate      optional report date
     * @param role            role
     * @return sql for performance indicator
     */
    public String getSqlPI(
            MGoalRestriction[] restrictions,
            String MeasureScope,
            String MeasureDataType,
            Timestamp reportDate,
            MRole role) {
        String dateColumn = "Created";
        String orgColumn = "AD_Org_ID";
        String bpColumn = "C_BPartner_ID";
        String pColumn = "M_Product_ID";
        //	PlannedAmt -> PlannedQty -> Count
        StringBuilder sb =
                new StringBuilder(
                        "SELECT COUNT(*) "
                                + "FROM R_Request WHERE R_RequestType_ID="
                                + getRequestTypeId()
                                + " AND Processed<>'Y'");
        //	Date Restriction

        if (MMeasure.MEASUREDATATYPE_QtyAmountInTime.equals(MeasureDataType)
                && !MGoal.MEASUREDISPLAY_Total.equals(MeasureScope)) {
            if (reportDate == null) reportDate = new Timestamp(System.currentTimeMillis());
            @SuppressWarnings("unused")
            String dateString = convertDate(reportDate);
            String trunc = "D";
            if (MGoal.MEASUREDISPLAY_Year.equals(MeasureScope)) trunc = "Y";
            else if (MGoal.MEASUREDISPLAY_Quarter.equals(MeasureScope)) trunc = "Q";
            else if (MGoal.MEASUREDISPLAY_Month.equals(MeasureScope)) trunc = "MM";
            else if (MGoal.MEASUREDISPLAY_Week.equals(MeasureScope)) trunc = "W";
            //	else if (MGoal.MEASUREDISPLAY_Day.equals(MeasureDisplay))
            //		;
            sb.append(" AND TRUNC(")
                    .append(dateColumn)
                    .append(",'")
                    .append(trunc)
                    .append("')=TRUNC(")
                    .append(convertDate(reportDate))
                    .append(",'")
                    .append(trunc)
                    .append("')");
        } //	date
        //
        String sql =
                MMeasureCalc.addRestrictions(
                        sb.toString(), false, restrictions, role, "R_Request", orgColumn, bpColumn, pColumn);

        log.fine(sql);
        return sql;
    } //	getSqlPI

} //	MRequestType
