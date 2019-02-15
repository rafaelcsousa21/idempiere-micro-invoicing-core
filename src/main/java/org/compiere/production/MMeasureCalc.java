package org.compiere.production;

import org.compiere.model.I_PA_MeasureCalc;
import org.compiere.orm.MRole;
import org.compiere.orm.MTable;
import org.compiere.query.MQuery;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.*;

/**
 * Performance Measure Calculation
 *
 * @author Jorg Janke
 * @version $Id: MMeasureCalc.java,v 1.4 2006/09/25 00:59:41 jjanke Exp $
 */
public class MMeasureCalc extends X_PA_MeasureCalc {
  /** */
  private static final long serialVersionUID = 4720674127987683534L;

  /**
   * Get MMeasureCalc from Cache
   *
   * @param ctx context
   * @param PA_MeasureCalc_ID id
   * @return MMeasureCalc
   */
  public static MMeasureCalc get(Properties ctx, int PA_MeasureCalc_ID) {
    Integer key = PA_MeasureCalc_ID;
    MMeasureCalc retValue = (MMeasureCalc) s_cache.get(key);
    if (retValue != null) return retValue;
    retValue = new MMeasureCalc(ctx, PA_MeasureCalc_ID);
    if (retValue.getId() != 0) s_cache.put(key, retValue);
    return retValue;
  } //	get

  /** Cache */
  private static CCache<Integer, MMeasureCalc> s_cache =
      new CCache<Integer, MMeasureCalc>(I_PA_MeasureCalc.Table_Name, 10);

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param PA_MeasureCalc_ID id
   * @param trxName trx
   */
  public MMeasureCalc(Properties ctx, int PA_MeasureCalc_ID) {
    super(ctx, PA_MeasureCalc_ID);
  } //	MMeasureCalc

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName trx
   */
  public MMeasureCalc(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MMeasureCalc

  /**
   * Get Sql to return single value for the Performance Indicator
   *
   * @param restrictions array of goal restrictions
   * @param MeasureScope scope of this value
   * @param MeasureDataType data type
   * @param reportDate optional report date
   * @param role role
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
      String dateString = TO_DATE(reportDate);
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
          .append(TO_DATE(reportDate))
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
   * @param sql existing sql
   * @param restrictions restrictions
   * @param role role
   * @return updated sql
   */
  private String addRestrictions(String sql, MGoalRestriction[] restrictions, MRole role) {
    return addRestrictions(
        sql,
        false,
        restrictions,
        role,
        getTableName(),
        getOrgColumn(),
        getBPartnerColumn(),
        getProductColumn());
  } //	addRestrictions

  /**
   * Add Restrictions to SQL
   *
   * @param sql orig sql
   * @param queryOnly incomplete sql for query restriction
   * @param restrictions restrictions
   * @param role role
   * @param tableName table name
   * @param orgColumn org column
   * @param bpColumn bpartner column
   * @param pColumn product column
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
      ArrayList<Integer> list = new ArrayList<Integer>();
      for (int i = 0; i < restrictions.length; i++) {
        if (MGoalRestriction.GOALRESTRICTIONTYPE_Organization.equals(
            restrictions[i].getGoalRestrictionType())) list.add(restrictions[i].getOrg_ID());
        //	Hierarchy comes here
      }
      if (list.size() == 1) sb.append(" AND ").append(orgColumn).append("=").append(list.get(0));
      else if (list.size() > 1) {
        sb.append(" AND ").append(orgColumn).append(" IN (");
        for (int i = 0; i < list.size(); i++) {
          if (i > 0) sb.append(",");
          sb.append(list.get(i));
        }
        sb.append(")");
      }
    } //	org

    //	BPartner Restrictions
    if (bpColumn != null) {
      ArrayList<Integer> listBP = new ArrayList<Integer>();
      ArrayList<Integer> listBPG = new ArrayList<Integer>();
      for (int i = 0; i < restrictions.length; i++) {
        if (MGoalRestriction.GOALRESTRICTIONTYPE_BusinessPartner.equals(
            restrictions[i].getGoalRestrictionType()))
          listBP.add(restrictions[i].getC_BPartner_ID());
        //	Hierarchy comes here
        if (MGoalRestriction.GOALRESTRICTIONTYPE_BusPartnerGroup.equals(
            restrictions[i].getGoalRestrictionType()))
          listBPG.add(restrictions[i].getC_BP_Group_ID());
      }
      //	BP
      if (listBP.size() == 1) sb.append(" AND ").append(bpColumn).append("=").append(listBP.get(0));
      else if (listBP.size() > 1) {
        sb.append(" AND ").append(bpColumn).append(" IN (");
        for (int i = 0; i < listBP.size(); i++) {
          if (i > 0) sb.append(",");
          sb.append(listBP.get(i));
        }
        sb.append(")");
      }
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
      ArrayList<Integer> listP = new ArrayList<Integer>();
      ArrayList<Integer> listPC = new ArrayList<Integer>();
      for (int i = 0; i < restrictions.length; i++) {
        if (MGoalRestriction.GOALRESTRICTIONTYPE_Product.equals(
            restrictions[i].getGoalRestrictionType())) listP.add(restrictions[i].getM_Product_ID());
        //	Hierarchy comes here
        if (MGoalRestriction.GOALRESTRICTIONTYPE_ProductCategory.equals(
            restrictions[i].getGoalRestrictionType()))
          listPC.add(restrictions[i].getM_Product_Category_ID());
      }
      //	Product
      if (listP.size() == 1) sb.append(" AND ").append(pColumn).append("=").append(listP.get(0));
      else if (listP.size() > 1) {
        sb.append(" AND ").append(pColumn).append(" IN (");
        for (int i = 0; i < listP.size(); i++) {
          if (i > 0) sb.append(",");
          sb.append(listP.get(i));
        }
        sb.append(")");
      }
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
    if (role == null) role = MRole.getDefault();
    String retValue = role.addAccessSQL(finalSQL, tableName, true, false);
    return retValue;
  } //	addRestrictions

  /**
   * Get Table Name
   *
   * @return Table Name
   */
  public String getTableName() {
    return MTable.getTableName(Env.getCtx(), getAD_Table_ID());
  } //	getTavleName

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("MMeasureCalc[");
    sb.append(getId()).append("-").append(getName()).append("]");
    return sb.toString();
  } //	toString
} //	MMeasureCalc
