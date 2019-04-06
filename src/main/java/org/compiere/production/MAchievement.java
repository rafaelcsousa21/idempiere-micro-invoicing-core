package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_PA_Achievement;
import org.compiere.orm.Query;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.sql.Timestamp;
import java.util.List;

/**
 * Performance Achievement
 *
 * @author Jorg Janke
 * @version $Id: MAchievement.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $ red1 - [ 2214883 ] Remove
 * SQL code and Replace for Query
 */
public class MAchievement extends X_PA_Achievement {

    private static final long serialVersionUID = -1438593600498523664L;
    /**
     * Logger
     */
    @SuppressWarnings("unused")
    private static CLogger s_log = CLogger.getCLogger(MAchievement.class);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx               context
     * @param PA_Achievement_ID id
     */
    public MAchievement(int PA_Achievement_ID) {
        super(PA_Achievement_ID);
    } //	MAchievement

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MAchievement(Row row) {
        super(row);
    } //	MAchievement

    /**
     * Get achieved Achievements Of Measure
     *
     * @param ctx           context
     * @param PA_Measure_ID measure id
     * @return array of Achievements
     */
    public static MAchievement[] getOfMeasure(int PA_Measure_ID) {
        final String whereClause = "PA_Measure_ID=? AND IsAchieved='Y'";
        List<MAchievement> list =
                new Query(I_PA_Achievement.Table_Name, whereClause)
                        .setParameters(PA_Measure_ID)
                        .setOrderBy("SeqNo, DateDoc")
                        .list();

        MAchievement[] retValue = new MAchievement[list.size()];
        retValue = list.toArray(retValue);
        return retValue;
    } //	getOfMeasure

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        return "MAchievement[" + getId() + "-" + getName() + "]";
    } //	toString

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        if (isAchieved()) {
            if (getManualActual().signum() == 0) setManualActual(Env.ONE);
            if (getDateDoc() == null) setDateDoc(new Timestamp(System.currentTimeMillis()));
        }
        return true;
    } //	beforeSave

    /**
     * After Save
     *
     * @param newRecord new
     * @param success   success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (success) updateAchievementGoals();
        return success;
    } //	afterSave

    /**
     * After Delete
     *
     * @param success success
     * @return success
     */
    protected boolean afterDelete(boolean success) {
        if (success) updateAchievementGoals();
        return success;
    } //	afterDelete

    /**
     * Update Goals with Achievement
     */
    private void updateAchievementGoals() {
        MMeasure measure = MMeasure.get(getMeasureId());
        measure.updateGoals();
    } //	updateAchievementGoals
} //	MAchievement
