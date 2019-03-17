package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_C_ProjectLine;
import org.compiere.orm.PO;
import org.compiere.orm.Query;
import org.idempiere.common.util.Env;

import java.util.List;
import java.util.Properties;

/**
 * Project Phase Task Model
 *
 * @author Jorg Janke
 * @version $Id: MProjectTask.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MProjectTask extends X_C_ProjectTask {
    /**
     *
     */
    private static final long serialVersionUID = 6714520156233475723L;
    private int C_Project_ID = 0;

    /**
     * Standard Constructor
     *
     * @param ctx              context
     * @param C_ProjectTask_ID id
     * @param trxName          transaction
     */
    public MProjectTask(Properties ctx, int C_ProjectTask_ID) {
        super(ctx, C_ProjectTask_ID);
        if (C_ProjectTask_ID == 0) {
            //	setProjectTaskId (0);	//	PK
            //	setProjectPhaseId (0);	//	Parent
            //	setTask_ID (0);			//	FK
            setSeqNo(0);
            //	setName (null);
            setQty(Env.ZERO);
        }
    } //	MProjectTask

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MProjectTask(Properties ctx, Row row) {
        super(ctx, row);
    } //	MProjectTask

    /**
     * Parent Constructor
     *
     * @param phase parent
     */
    public MProjectTask(MProjectPhase phase) {
        this(phase.getCtx(), 0);
        setClientOrg(phase);
        setProjectPhaseId(phase.getProjectPhaseId());
    } //	MProjectTask

    /**
     * Copy Constructor
     *
     * @param phase parent
     * @param task  type copy
     */
    public MProjectTask(MProjectPhase phase, MProjectTypeTask task) {
        this(phase);
        //
        setTaskId(task.getTaskId()); // 	FK
        setSeqNo(task.getSeqNo());
        setName(task.getName());
        setDescription(task.getDescription());
        setHelp(task.getHelp());
        if (task.getProductId() != 0) setProductId(task.getProductId());
        setQty(task.getStandardQty());
    } //	MProjectTask

    /**
     * ************************************************************************ Get Project Lines BF
     * 3067850 - monhate
     *
     * @return Array of lines
     */
    public MProjectLine[] getLines() {
        final String whereClause = "C_ProjectPhase_ID=? and C_ProjectTask_ID=? ";
        List<MProjectLine> list =
                new Query(getCtx(), I_C_ProjectLine.Table_Name, whereClause)
                        .setParameters(getProjectPhaseId(), getProjectTaskId())
                        .setOrderBy("Line")
                        .list();
        //
        MProjectLine[] retValue = new MProjectLine[list.size()];
        list.toArray(retValue);
        return retValue;
    }

    /**
     * Copy Lines from other Task BF 3067850 - monhate
     *
     * @param fromTask from Task
     * @return number of lines copied
     */
    public int copyLinesFrom(MProjectTask fromTask) {
        if (fromTask == null) return 0;
        int count = 0;
        //
        MProjectLine[] fromLines = fromTask.getLines();
        //	Copy Project Lines
        for (int i = 0; i < fromLines.length; i++) {
            MProjectLine toLine = new MProjectLine(getCtx(), 0);
            PO.copyValues(fromLines[i], toLine, getClientId(), getOrgId());
            toLine.setProjectId(getProjectId(false));
            toLine.setProjectPhaseId(getProjectPhaseId());
            toLine.setProjectTaskId(getProjectTaskId());
            toLine.saveEx();
            count++;
        }
        if (fromLines.length != count)
            log.warning("Count difference - ProjectLine=" + fromLines.length + " <> Saved=" + count);

        return count;
    }

    private int getProjectId(boolean reQuery) {
        if (C_Project_ID == 0 || reQuery) C_Project_ID = getProjectPhase().getProjectId();
        return C_Project_ID;
    }

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MProjectTask[");
        sb.append(getId()).append("-").append(getSeqNo()).append("-").append(getName()).append("]");
        return sb.toString();
    } //	toString
} //	MProjectTask
