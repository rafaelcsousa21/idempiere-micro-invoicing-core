package org.compiere.production;

import org.compiere.model.I_C_ProjectType;
import org.compiere.orm.MRole;
import org.compiere.query.MQuery;
import org.idempiere.common.util.CCache;
import software.hsharp.core.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.*;


/**
 * Project Type Model
 *
 * @author Jorg Janke
 * @version $Id: MProjectType.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MProjectType extends X_C_ProjectType {
  /** */
  private static final long serialVersionUID = -6041540981032251476L;

  /**
   * Get MProjectType from Cache
   *
   * @param ctx context
   * @param C_ProjectType_ID id
   * @return MProjectType
   */
  public static MProjectType get(Properties ctx, int C_ProjectType_ID) {
    Integer key = new Integer(C_ProjectType_ID);
    MProjectType retValue = (MProjectType) s_cache.get(key);
    if (retValue != null) return retValue;
    retValue = new MProjectType(ctx, C_ProjectType_ID, null);
    if (retValue.getId() != 0) s_cache.put(key, retValue);
    return retValue;
  } //	get

  /** Cache */
  private static CCache<Integer, MProjectType> s_cache =
      new CCache<Integer, MProjectType>(I_C_ProjectType.Table_Name, 20);

  /**
   * ************************************************************************ Standrad Constructor
   *
   * @param ctx context
   * @param C_ProjectType_ID id
   * @param trxName trx
   */
  public MProjectType(Properties ctx, int C_ProjectType_ID, String trxName) {
    super(ctx, C_ProjectType_ID, trxName);
    /** if (C_ProjectType_ID == 0) { setC_ProjectType_ID (0); setName (null); } */
  } //	MProjectType

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName trx
   */
  public MProjectType(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MProjectType

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
    ArrayList<MProjectTypePhase> list = new ArrayList<MProjectTypePhase>();
    String sql = "SELECT * FROM C_Phase WHERE C_ProjectType_ID=? ORDER BY SeqNo";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = prepareStatement(sql);
      pstmt.setInt(1, getC_ProjectType_ID());
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(new MProjectTypePhase(getCtx(), rs, null));
    } catch (SQLException ex) {
      log.log(Level.SEVERE, sql, ex);
    } finally {
      rs = null;
      pstmt = null;
    }
    //
    MProjectTypePhase[] retValue = new MProjectTypePhase[list.size()];
    list.toArray(retValue);
    return retValue;
  } //	getPhases

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
    String dateColumn = "Created";
    String orgColumn = "AD_Org_ID";
    String bpColumn = "C_BPartner_ID";
    String pColumn = null;
    //	PlannedAmt -> PlannedQty -> Count
    StringBuilder sb =
        new StringBuilder(
            "SELECT COALESCE(SUM(PlannedAmt),COALESCE(SUM(PlannedQty),COUNT(*))) "
                + "FROM C_Project WHERE C_ProjectType_ID="
                + getC_ProjectType_ID()
                + " AND Processed<>'Y')");
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
            sb.toString(), false, restrictions, role, "C_Project", orgColumn, bpColumn, pColumn);

    log.fine(sql);
    return sql;
  } //	getSql

} //	MProjectType
