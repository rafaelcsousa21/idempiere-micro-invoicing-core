package org.compiere.production;

import kotliquery.Row;
import org.compiere.crm.MUser;
import org.compiere.crm.MUserKt;
import org.compiere.orm.MRole;
import org.compiere.orm.MSysConfig;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

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
     * Restrictions
     */
    private MGoalRestriction[] m_restrictions = null;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param PA_Goal_ID id
     */
    public MGoal(int PA_Goal_ID) {
        super(PA_Goal_ID);
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
     */
    public MGoal(Row row) {
        super(row);
    } //	MGoal

    /**
     * Base Constructor
     *
     * @param Name          Name
     * @param Description   Decsription
     * @param MeasureTarget target
     */
    public MGoal(
            String Name, String Description, BigDecimal MeasureTarget) {
        super(0);
        setName(Name);
        setDescription(Description);
        setMeasureTarget(MeasureTarget);
    } //	MGoal

    /**
     * Get Goals with Measure
     *
     * @param PA_Measure_ID measure
     * @return goals
     */
    public static MGoal[] getMeasureGoals(int PA_Measure_ID) {
        return MBaseGoalKt.getGoalsWithMeasure(PA_Measure_ID);
    } //	getMeasureGoals

    /**
     * Get Restriction Lines
     *
     * @param reload reload data
     * @return array of lines
     */
    public MGoalRestriction[] getRestrictions(boolean reload) {
        if (m_restrictions != null && !reload) return m_restrictions;
        m_restrictions = MBaseGoalKt.getRestrictions(getGoalId());
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
        MMeasure measure = MMeasure.get(getMeasureId());

        boolean isUpdateByInterfal = false;
        if (getDateLastRun() != null) {
            // default 30 minute 1800000
            long interval =
                    MSysConfig.getIntValue(
                            MSysConfig.ZK_DASHBOARD_PERFORMANCE_REFRESH_INTERVAL,
                            1800000,
                            Env.getClientId());
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
        if (!isSummary() && getMeasureId() == 0) {
            log.saveError("FillMandatory", MsgKt.getElementTranslation("PA_Measure_ID"));
            return false;
        }
        if (isSummary() && getMeasureId() != 0) setMeasureId(0);

        //	User/Role Check
        if ((newRecord || isValueChanged("AD_User_ID") || isValueChanged("AD_Role_ID"))
                && getUserId() != 0) {
            MUser user = MUserKt.getUser(getUserId());
            MRole[] roles = user.getRoles(getOrgId());
            if (roles.length == 0) // 	No Role
                setRoleId(0);
            else if (roles.length == 1) // 	One
                setRoleId(roles[0].getRoleId());
            else {
                int AD_Role_ID = getRoleId();
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
                    setRoleId(roles[0].getRoleId());
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
        if (newRecord || isValueChanged("MeasureTarget") || isValueChanged("MeasureScope"))
            updateGoal(true);

        return success;
    }
} //	MGoal
