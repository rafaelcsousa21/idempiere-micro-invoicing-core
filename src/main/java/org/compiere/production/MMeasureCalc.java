package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_PA_MeasureCalc;
import org.compiere.orm.MRole;
import org.compiere.orm.MRoleKt;
import org.compiere.orm.MTableKt;
import org.idempiere.common.util.CCache;

import java.sql.Timestamp;
import java.util.ArrayList;

import static software.hsharp.core.util.DBKt.convertDate;

/**
 * Performance Measure Calculation
 *
 * @author Jorg Janke
 * @version $Id: MMeasureCalc.java,v 1.4 2006/09/25 00:59:41 jjanke Exp $
 */
public class MMeasureCalc extends X_PA_MeasureCalc {
    /**
     *
     */
    private static final long serialVersionUID = 4720674127987683534L;
    /**
     * Cache
     */
    private static CCache<Integer, MMeasureCalc> s_cache =
            new CCache<>(I_PA_MeasureCalc.Table_Name, 10);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param PA_MeasureCalc_ID id
     */
    public MMeasureCalc(int PA_MeasureCalc_ID) {
        super(PA_MeasureCalc_ID);
    } //	MMeasureCalc

    /**
     * Load Constructor
     */
    public MMeasureCalc(Row row) {
        super(row);
    } //	MMeasureCalc

    /**
     * Get MMeasureCalc from Cache
     *
     * @param PA_MeasureCalc_ID id
     * @return MMeasureCalc
     */
    public static MMeasureCalc get(int PA_MeasureCalc_ID) {
        Integer key = PA_MeasureCalc_ID;
        MMeasureCalc retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MMeasureCalc(PA_MeasureCalc_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Add Restrictions to SQL
     *
     * @param sql          orig sql
     * @param queryOnly    incomplete sql for query restriction
     * @param restrictions restrictions
     * @param role         role
     * @param tableName    table name
     * @param orgColumn    org column
     * @param bpColumn     bpartner column
     * @param pColumn      product column
     * @return updated sql
     */
    public static String addRestrictions(
            String sql,
            boolean queryOnly,
            MGoalRestriction[] restrictions,
            MRole role,
            String tableName,
            String orgColumn,
            String bpColumn,
            String pColumn) {
        StringBuilder sb = new StringBuilder(sql);
        //	Org Restrictions
        if (orgColumn != null) {
            ArrayList<Integer> list = new ArrayList<>();
            for (MGoalRestriction restriction : restrictions) {
                if (MGoalRestriction.GOALRESTRICTIONTYPE_Organization.equals(
                        restriction.getGoalRestrictionType())) list.add(restriction.getOrgId());
                //	Hierarchy comes here
            }
            addOrgFilter(orgColumn, sb, list);
        } //	org

        //	BPartner Restrictions
        if (bpColumn != null) {
            ArrayList<Integer> listBP = new ArrayList<>();
            ArrayList<Integer> listBPG = new ArrayList<>();
            for (MGoalRestriction restriction : restrictions) {
                if (MGoalRestriction.GOALRESTRICTIONTYPE_BusinessPartner.equals(
                        restriction.getGoalRestrictionType()))
                    listBP.add(restriction.getBusinessPartnerId());
                //	Hierarchy comes here
                if (MGoalRestriction.GOALRESTRICTIONTYPE_BusPartnerGroup.equals(
                        restriction.getGoalRestrictionType()))
                    listBPG.add(restriction.getBPGroupId());
            }
            //	BP
            addOrgFilter(bpColumn, sb, listBP);
            //	BPG
            if (bpColumn.indexOf('.') == -1) bpColumn = tableName + "." + bpColumn;
            if (listBPG.size() == 1)
                sb.append(" AND EXISTS (SELECT * FROM C_BPartner bpx WHERE ")
                        .append(bpColumn)
                        .append("=bpx.C_BPartner_ID AND bpx.C_BP_GROUP_ID=")
                        .append(listBPG.get(0))
                        .append(")");
            else if (listBPG.size() > 1) {
                sb.append(" AND EXISTS (SELECT * FROM C_BPartner bpx WHERE ")
                        .append(bpColumn)
                        .append("=bpx.C_BPartner_ID AND bpx.C_BP_GROUP_ID IN (");
                for (int i = 0; i < listBPG.size(); i++) {
                    if (i > 0) sb.append(",");
                    sb.append(listBPG.get(i));
                }
                sb.append("))");
            }
        } //	bp

        //	Product Restrictions
        if (pColumn != null) {
            ArrayList<Integer> listP = new ArrayList<>();
            ArrayList<Integer> listPC = new ArrayList<>();
            for (MGoalRestriction restriction : restrictions) {
                if (MGoalRestriction.GOALRESTRICTIONTYPE_Product.equals(
                        restriction.getGoalRestrictionType())) listP.add(restriction.getProductId());
                //	Hierarchy comes here
                if (MGoalRestriction.GOALRESTRICTIONTYPE_ProductCategory.equals(
                        restriction.getGoalRestrictionType()))
                    listPC.add(restriction.getProductCategoryId());
            }
            //	Product
            addOrgFilter(pColumn, sb, listP);
            //	Category
            if (pColumn.indexOf('.') == -1) pColumn = tableName + "." + pColumn;
            if (listPC.size() == 1)
                sb.append(" AND EXISTS (SELECT * FROM M_Product px WHERE ")
                        .append(pColumn)
                        .append("=px.M_Product_ID AND px.M_Product_Category_ID=")
                        .append(listPC.get(0))
                        .append(")");
            else if (listPC.size() > 1) {
                sb.append(" AND EXISTS (SELECT * FROM M_Product px WHERE ")
                        .append(pColumn)
                        .append("=px.M_Product_ID AND px.M_Product_Category_ID IN (");
                for (int i = 0; i < listPC.size(); i++) {
                    if (i > 0) sb.append(",");
                    sb.append(listPC.get(i));
                }
                sb.append("))");
            }
        } //	product
        String finalSQL = sb.toString();
        if (queryOnly) return finalSQL;
        if (role == null) role = MRoleKt.getDefaultRole();
        return role.addAccessSQL(finalSQL, tableName, true, false);
    } //	addRestrictions

    private static void addOrgFilter(String orgColumn, StringBuilder sb, ArrayList<Integer> list) {
        if (list.size() == 1) sb.append(" AND ").append(orgColumn).append("=").append(list.get(0));
        else if (list.size() > 1) {
            sb.append(" AND ").append(orgColumn).append(" IN (");
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append(list.get(i));
            }
            sb.append(")");
        }
    }

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
        StringBuilder sb = new StringBuilder(getSelectClause()).append(" ").append(getWhereClause());
        //	Date Restriction
        if (getDateColumn() != null
                && MMeasure.MEASUREDATATYPE_QtyAmountInTime.equals(MeasureDataType)
                && !MGoal.MEASUREDISPLAY_Total.equals(MeasureScope)) {
            if (reportDate == null) reportDate = new Timestamp(System.currentTimeMillis());
            @SuppressWarnings("unused")
            String dateString = convertDate(reportDate);
            // http://download-west.oracle.com/docs/cd/B14117_01/server.101/b10759/functions207.htm#i1002084
            String trunc = "DD";
            if (MGoal.MEASUREDISPLAY_Year.equals(MeasureScope)) trunc = "Y";
            else if (MGoal.MEASUREDISPLAY_Quarter.equals(MeasureScope)) trunc = "Q";
            else if (MGoal.MEASUREDISPLAY_Month.equals(MeasureScope)) trunc = "MM";
            else if (MGoal.MEASUREDISPLAY_Week.equals(MeasureScope)) trunc = "D";
            //	else if (MGoal.MEASUREDISPLAY_Day.equals(MeasureDisplay))
            //		;
            sb.append(" AND TRUNC(")
                    .append(getDateColumn())
                    .append(",'")
                    .append(trunc)
                    .append("')=TRUNC(")
                    .append(convertDate(reportDate))
                    .append(",'")
                    .append(trunc)
                    .append("')");
        } //	date
        String sql = addRestrictions(sb.toString(), restrictions, role);

        log.fine(sql);
        return sql;
    } //	getSql

    /**
     * Add Restrictions
     *
     * @param sql          existing sql
     * @param restrictions restrictions
     * @param role         role
     * @return updated sql
     */
    private String addRestrictions(String sql, MGoalRestriction[] restrictions, MRole role) {
        return addRestrictions(
                sql,
                false,
                restrictions,
                role,
                getDbTableName(),
                getOrgColumn(),
                getBPartnerColumn(),
                getProductColumn());
    } //	addRestrictions

    /**
     * Get Table Name
     *
     * @return Table Name
     */
    public String getDbTableName() {
        return MTableKt.getDbTableName(getRowTableId());
    } //	getTavleName

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        return "MMeasureCalc[" + getId() + "-" + getName() + "]";
    } //	toString
} //	MMeasureCalc
