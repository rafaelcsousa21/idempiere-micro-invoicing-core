package org.compiere.production;

import kotliquery.Row;
import org.idempiere.common.util.Env;

/**
 * Project Type Phase Model
 *
 * @author Jorg Janke
 * @version $Id: MProjectTypePhase.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MProjectTypePhase extends X_C_Phase {
    /**
     *
     */
    private static final long serialVersionUID = -5111329904215151458L;

    /**
     * Standard Constructor
     *
     * @param ctx        context
     * @param C_Phase_ID id
     */
    public MProjectTypePhase(int C_Phase_ID) {
        super(C_Phase_ID);
        if (C_Phase_ID == 0) {
            //	setPhaseId (0);			//	PK
            //	setProjectTypeId (0);	//	Parent
            //	setName (null);
            setSeqNo(0);
            setStandardQty(Env.ZERO);
        }
    } //	MProjectTypePhase

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MProjectTypePhase(Row row) {
        super(row);
    } //	MProjectTypePhase

    /**
     * Get Project Type Phases
     *
     * @return Array of phases
     */
    public MProjectTypeTask[] getTasks() {
        return MBaseProjectTypePhaseKt.getProjectTypePhaseTasks(getPhaseId());
    } //	getPhases
} //	MProjectTypePhase
