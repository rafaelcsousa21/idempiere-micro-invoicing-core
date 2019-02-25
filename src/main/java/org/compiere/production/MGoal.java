package org.compiere.production;

import org.compiere.crm.MUser;
import org.compiere.orm.MRole;
import org.compiere.orm.MSysConfig;
import org.compiere.util.Msg;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Performance Goal
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>BF [ 1887674 ] Deadlock when try to modify PA Goal's Measure Target
 * <li>BF [ 1760482 ] New Dashboard broke old functionality
 * <li>BF [ 1887691 ] I get NPE if the PA Goal's target is 0
 * @version $Id: MGoal.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MGoal extends X_PA_Goal {
    /**
     *
     */
    private static final long serialVersionUID = -4612113288233473730L;
    /**
     * Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MGoal.class);
    /**
     * Restrictions
     */
    private MGoalRestriction[] m_restrictions = null;
    /**
     * Performance Color
     */
    private Color m_color = null;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx        context
     * @param PA_Goal_ID id
     * @param trxName    trx
     */
    public MGoal(Properties ctx, int PA_Goal_ID) {
        super(ctx, PA_Goal_ID);
        if (PA_Goal_ID == 0) {
            //	setName (null);
            //	setUserId (0);
            //	setPA_ColorSchema_ID (0);
            setSeqNo(0);
            setIsSummary(false);
            setMeasureScope(X_PA_Goal.MEASUREDISPLAY_Year);
            setGoalPerformance(Env.ZERO);
            setRelativeWeight(Env.ONE);
            setMeasureTarget(Env.ZERO);
            setMeasureActual(Env.ZERO);
        }
    } //	MGoal

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName trx
     */
    public MGoal(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MGoal

    /**
     * Base Constructor
     *
     * @param ctx           context
     * @param Name          Name
     * @param Description   Decsription
     * @param MeasureTarget target
     * @param trxName       trx
     */
    public MGoal(
            Properties ctx, String Name, String Description, BigDecimal MeasureTarget) {
        super(ctx, 0);
        setName(Name);
        setDescription(Description);
        setMeasureTarget(MeasureTarget);
    } //	MGoal

    /**
     * Get Goals with Measure
     *
     * @param ctx           context
     * @param PA_Measure_ID measure
     * @return goals
     */
    public static MGoal[] getMeasureGoals(Properties ctx, int PA_Measure_ID) {
        ArrayList<MGoal> list = new ArrayList<MGoal>();
        String sql = "SELECT * FROM PA_Goal WHERE IsActive='Y' AND PA_Measure_ID=? " + "ORDER BY SeqNo";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, PA_Measure_ID);
            rs = pstmt.executeQuery();
            while (rs.next()) list.add(new MGoal(ctx, rs));
        } catch (Exception e) {
            s_log.log(Level.SEVERE, sql, e);
        } finally {

            rs = null;
            pstmt = null;
        }
        MGoal[] retValue = new MGoal[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getMeasureGoals

    /**
     * Get Restriction Lines
     *
     * @param reload reload data
     * @return array of lines
     */
    public MGoalRestriction[] getRestrictions(boolean reload) {
        if (m_restrictions != null && !reload) return m_restrictions;
        ArrayList<MGoalRestriction> list = new ArrayList<MGoalRestriction>();
        //
        String sql =
                "SELECT * FROM PA_GoalRestriction "
                        + "WHERE PA_Goal_ID=? AND IsActive='Y' "
                        + "ORDER BY Org_ID, C_BPartner_ID, M_Product_ID";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, getPA_Goal_ID());
            rs = pstmt.executeQuery();
            while (rs.next()) list.add(new MGoalRestriction(getCtx(), rs));
        } catch (Exception e) {
            log.log(Level.SEVERE, sql, e);
        } finally {

            rs = null;
            pstmt = null;
        }
        //
        m_restrictions = new MGoalRestriction[list.size()];
        list.toArray(m_restrictions);
        return m_restrictions;
    } //	getRestrictions

    /**
     * ************************************************************************ Update/save Goals for
     * the same measure
     *
     * @param force force to update goal (default once per day)
     * @return true if updated
     */
    public boolean updateGoal(boolean force) {
        if (log.isLoggable(Level.CONFIG)) log.config("Force=" + force);
        MMeasure measure = MMeasure.get(getCtx(), getPA_Measure_ID());

        boolean isUpdateByInterfal = false;
        if (getDateLastRun() != null) {
            // default 30 minute 1800000
            long interval =
                    MSysConfig.getIntValue(
                            MSysConfig.ZK_DASHBOARD_PERFORMANCE_REFRESH_INTERVAL,
                            1800000,
                            Env.getClientId(Env.getCtx()));
            isUpdateByInterfal = (System.currentTimeMillis() - getDateLastRun().getTime()) > interval;
        }

        if (force || getDateLastRun() == null || isUpdateByInterfal) {
            if (measure.updateGoals()) // 	saves
            {
                load(getId());
                return true;
            }
        }
        return false;
    } //	updateGoal

    /**
     * Set Measure Actual
     *
     * @param MeasureActual actual
     */
    public void setMeasureActual(BigDecimal MeasureActual) {
        if (MeasureActual == null) return;
        super.setMeasureActual(MeasureActual);
        setDateLastRun(new Timestamp(System.currentTimeMillis()));
        setGoalPerformance();
    } //	setMeasureActual

    /**
     * Calculate Performance Goal as multiplier
     */
    public void setGoalPerformance() {
        BigDecimal MeasureTarget = getMeasureTarget();
        BigDecimal MeasureActual = getMeasureActual();
        BigDecimal GoalPerformance = Env.ZERO;
        if (MeasureTarget.signum() != 0)
            GoalPerformance = MeasureActual.divide(MeasureTarget, 6, BigDecimal.ROUND_HALF_UP);
        super.setGoalPerformance(GoalPerformance);
        m_color = null;
    } //	setGoalPerformance

    /**
     * Get Measure Display
     *
     * @return Measure Display
     */
    public String getMeasureDisplay() {
        String s = super.getMeasureDisplay();
        if (s == null) {
            if (X_PA_Goal.MEASURESCOPE_Week.equals(getMeasureScope())) s = X_PA_Goal.MEASUREDISPLAY_Week;
            else if (X_PA_Goal.MEASURESCOPE_Day.equals(getMeasureScope()))
                s = X_PA_Goal.MEASUREDISPLAY_Day;
            else s = X_PA_Goal.MEASUREDISPLAY_Month;
        }
        return s;
    } //	getMeasureDisplay

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MGoal[");
        sb.append(getId())
                .append("-")
                .append(getName())
                .append(",")
                .append(getGoalPerformance())
                .append("]");
        return sb.toString();
    } //	toString

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        //	if (getMultiplier(this) == null)	//	error
        //		setMeasureDisplay(getMeasureScope());

        //	Measure required if nor Summary
        if (!isSummary() && getPA_Measure_ID() == 0) {
            log.saveError("FillMandatory", Msg.getElement(getCtx(), "PA_Measure_ID"));
            return false;
        }
        if (isSummary() && getPA_Measure_ID() != 0) setPA_Measure_ID(0);

        //	User/Role Check
        if ((newRecord || is_ValueChanged("AD_User_ID") || is_ValueChanged("AD_Role_ID"))
                && getAD_User_ID() != 0) {
            MUser user = MUser.get(getCtx(), getAD_User_ID());
            MRole[] roles = user.getRoles(getOrgId());
            if (roles.length == 0) // 	No Role
                setAD_Role_ID(0);
            else if (roles.length == 1) // 	One
                setAD_Role_ID(roles[0].getRoleId());
            else {
                int AD_Role_ID = getAD_Role_ID();
                if (AD_Role_ID != 0) // 	validate
                {
                    boolean found = false;
                    for (int i = 0; i < roles.length; i++) {
                        if (AD_Role_ID == roles[i].getRoleId()) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) AD_Role_ID = 0;
                }
                if (AD_Role_ID == 0) // 	set to first one
                    setAD_Role_ID(roles[0].getRoleId());
            } //	multiple roles
        } //	user check

        return true;
    } //	beforeSave

    /**
     * After Save
     *
     * @param newRecord new
     * @param success   success
     * @return true
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;

        //	Update Goal if Target / Scope Changed
        if (newRecord || is_ValueChanged("MeasureTarget") || is_ValueChanged("MeasureScope"))
            updateGoal(true);

        return success;
    }
} //	MGoal
