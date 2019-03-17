package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_C_ProjectType;
import org.compiere.orm.MRole;
import org.idempiere.common.util.CCache;

import java.sql.Timestamp;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.convertDate;


/**
 * Project Type Model
 *
 * @author Jorg Janke
 * @version $Id: MProjectType.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MProjectType extends X_C_ProjectType {
    /**
     *
     */
    private static final long serialVersionUID = -6041540981032251476L;
    /**
     * Cache
     */
    private static CCache<Integer, MProjectType> s_cache =
            new CCache<Integer, MProjectType>(I_C_ProjectType.Table_Name, 20);

    /**
     * ************************************************************************ Standrad Constructor
     *
     * @param ctx              context
     * @param C_ProjectType_ID id
     */
    public MProjectType(Properties ctx, int C_ProjectType_ID) {
        super(ctx, C_ProjectType_ID);
    } //	MProjectType

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MProjectType(Properties ctx, Row row) {
        super(ctx, row);
    } //	MProjectType

    /**
     * Get MProjectType from Cache
     *
     * @param ctx              context
     * @param C_ProjectType_ID id
     * @return MProjectType
     */
    public static MProjectType get(Properties ctx, int C_ProjectType_ID) {
        Integer key = C_ProjectType_ID;
        MProjectType retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MProjectType(ctx, C_ProjectType_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuffer sb =
                new StringBuffer("MProjectType[").append(getId()).append("-").append(getName()).append("]");
        return sb.toString();
    } //	toString

    /**
     * ************************************************************************ Get Project Type
     * Phases
     *
     * @return Array of phases
     */
    public MProjectTypePhase[] getPhases() {
        return MBaseProjectTypeKt.getProjectTypePhases(getCtx(), getProjectTypeId());
    } //	getPhases

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
        String pColumn = null;
        //	PlannedAmt -> PlannedQty -> Count
        StringBuilder sb =
                new StringBuilder(
                        "SELECT COALESCE(SUM(PlannedAmt),COALESCE(SUM(PlannedQty),COUNT(*))) "
                                + "FROM C_Project WHERE C_ProjectType_ID="
                                + getProjectTypeId()
                                + " AND Processed<>'Y')");
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
                        sb.toString(), false, restrictions, role, "C_Project", orgColumn, bpColumn, pColumn);

        log.fine(sql);
        return sql;
    } //	getSql

} //	MProjectType
