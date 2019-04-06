package org.compiere.production;

import kotliquery.Row;

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
     * @param PA_GoalRestriction_ID id
     */
    public MGoalRestriction(int PA_GoalRestriction_ID) {
        super(PA_GoalRestriction_ID);
    } //	MGoalRestriction

    /**
     * Load Constructor
     */
    public MGoalRestriction(Row row) {
        super(row);
    } //	MGoalRestriction

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        return "MGoalRestriction[" + getId() + "-" + getName() + "]";
    } //	toString
} //	MGoalRestriction
