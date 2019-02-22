package org.compiere.production;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Performance Goal Restriction
 *
 * @author Jorg Janke
 * @version $Id: MGoalRestriction.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MGoalRestriction extends X_PA_GoalRestriction {
    /**
     *
     */
    private static final long serialVersionUID = 4027980875091517732L;

    /**
     * Standard Constructor
     *
     * @param ctx                   context
     * @param PA_GoalRestriction_ID id
     * @param trxName               trx
     */
    public MGoalRestriction(Properties ctx, int PA_GoalRestriction_ID) {
        super(ctx, PA_GoalRestriction_ID);
    } //	MGoalRestriction

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName trx
     */
    public MGoalRestriction(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MGoalRestriction

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MGoalRestriction[");
        sb.append(getId()).append("-").append(getName()).append("]");
        return sb.toString();
    } //	toString
} //	MGoalRestriction
