package org.compiere.production;

import kotliquery.Row;
import org.idempiere.common.util.Env;

import java.util.Properties;

/**
 * Project Type Phase Task Model
 *
 * @author Jorg Janke
 * @version $Id: MProjectTypeTask.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MProjectTypeTask extends X_C_Task {
    /**
     *
     */
    private static final long serialVersionUID = -5649262800489348606L;

    public MProjectTypeTask(Properties ctx, int C_Task_ID) {
        super(ctx, C_Task_ID);
        if (C_Task_ID == 0) {
            //	setC_Task_ID (0);		//	PK
            //	setC_Phase_ID (0);		//	Parent
            //	setName (null);
            setSeqNo(0);
            setStandardQty(Env.ZERO);
        }
    } //	MProjectTypeTask

    public MProjectTypeTask(Properties ctx, Row row) {
        super(ctx, row);
    } //	MProjectTypeTask
} //	MProjectTypeTask
