package org.compiere.production;

import org.compiere.model.I_R_RequestType;
import org.compiere.orm.MRole;
import org.compiere.orm.Query;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.TO_DATE;
import static software.hsharp.core.util.DBKt.prepareStatement;

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
     * Next time stats to be created
     */
    private long m_nextStats = 0;
    private int m_openNo = 0;
    private int m_totalNo = 0;
    private int m_new30No = 0;
    private int m_closed30No = 0;

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
            //	setR_RequestType_ID (0);
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
    public MRequestType(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MRequestType

    /**
     * Get Request Type (cached)
     *
     * @param ctx              context
     * @param R_RequestType_ID id
     * @return Request Type
     */
    public static MRequestType get(Properties ctx, int R_RequestType_ID) {
        Integer key = new Integer(R_RequestType_ID);
        MRequestType retValue = (MRequestType) s_cache.get(key);
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
     * Update Statistics
     */
    private synchronized void updateStatistics() {
        if (System.currentTimeMillis() < m_nextStats) return;

        String sql =
                "SELECT "
                        + "(SELECT COUNT(*) FROM R_Request r"
                        + " INNER JOIN R_Status s ON (r.R_Status_ID=s.R_Status_ID AND s.IsOpen='Y') "
                        + "WHERE r.R_RequestType_ID=x.R_RequestType_ID) AS OpenNo, "
                        + "(SELECT COUNT(*) FROM R_Request r "
                        + "WHERE r.R_RequestType_ID=x.R_RequestType_ID) AS TotalNo, "
                        + "(SELECT COUNT(*) FROM R_Request r "
                        + "WHERE r.R_RequestType_ID=x.R_RequestType_ID AND Created>addDays(SysDate,-30)) AS New30No, "
                        + "(SELECT COUNT(*) FROM R_Request r"
                        + " INNER JOIN R_Status s ON (r.R_Status_ID=s.R_Status_ID AND s.IsClosed='Y') "
                        + "WHERE r.R_RequestType_ID=x.R_RequestType_ID AND r.Updated>addDays(SysDate,-30)) AS Closed30No "
                        //
                        + "FROM R_RequestType x WHERE R_RequestType_ID=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, getR_RequestType_ID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                m_openNo = rs.getInt(1);
                m_totalNo = rs.getInt(2);
                m_new30No = rs.getInt(3);
                m_closed30No = rs.getInt(4);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, sql, e);
        } finally {

            rs = null;
            pstmt = null;
        }

        m_nextStats = System.currentTimeMillis() + 3600000; // 	every hour
    } //	updateStatistics

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        if (getR_StatusCategory_ID() == 0) {
            MStatusCategory sc = MStatusCategory.getDefault(getCtx());
            if (sc != null && sc.getR_StatusCategory_ID() != 0)
                setR_StatusCategory_ID(sc.getR_StatusCategory_ID());
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
                                + getR_RequestType_ID()
                                + " AND Processed<>'Y'");
        //	Date Restriction

        if (MMeasure.MEASUREDATATYPE_QtyAmountInTime.equals(MeasureDataType)
                && !MGoal.MEASUREDISPLAY_Total.equals(MeasureScope)) {
            if (reportDate == null) reportDate = new Timestamp(System.currentTimeMillis());
            @SuppressWarnings("unused")
            String dateString = TO_DATE(reportDate);
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
                    .append(TO_DATE(reportDate))
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
