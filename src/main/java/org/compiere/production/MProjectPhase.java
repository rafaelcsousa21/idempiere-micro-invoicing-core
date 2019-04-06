package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_C_ProjectLine;
import org.compiere.orm.PO;
import org.compiere.orm.Query;
import org.idempiere.common.util.Env;

import java.util.List;
import java.util.logging.Level;

/**
 * Project Phase Model
 *
 * @author Jorg Janke
 * @version $Id: MProjectPhase.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MProjectPhase extends X_C_ProjectPhase {
    /**
     *
     */
    private static final long serialVersionUID = 5824045445920353065L;

    /**
     * Standard Constructor
     *
     * @param ctx               context
     * @param C_ProjectPhase_ID id
     */
    public MProjectPhase(int C_ProjectPhase_ID) {
        super(C_ProjectPhase_ID);
        if (C_ProjectPhase_ID == 0) {
            //	setProjectPhaseId (0);	//	PK
            //	setProjectId (0);		//	Parent
            //	setPhaseId (0);			//	FK
            setCommittedAmt(Env.ZERO);
            setIsCommitCeiling(false);
            setIsComplete(false);
            setSeqNo(0);
            //	setName (null);
            setQty(Env.ZERO);
        }
    } //	MProjectPhase

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MProjectPhase(Row row) {
        super(row);
    } //	MProjectPhase

    /**
     * Parent Constructor
     *
     * @param project parent
     */
    public MProjectPhase(MProject project) {
        this(0);
        setClientOrg(project);
        setProjectId(project.getProjectId());
    } //	MProjectPhase

    /**
     * Copy Constructor
     *
     * @param project parent
     * @param phase   copy
     */
    public MProjectPhase(MProject project, MProjectTypePhase phase) {
        this(project);
        //
        setPhaseId(phase.getPhaseId()); // 	FK
        setName(phase.getName());
        setSeqNo(phase.getSeqNo());
        setDescription(phase.getDescription());
        setHelp(phase.getHelp());
        if (phase.getProductId() != 0) setProductId(phase.getProductId());
        setQty(phase.getStandardQty());
    } //	MProjectPhase

    /**
     * Get Project Phase Tasks.
     *
     * @return Array of tasks
     */
    public MProjectTask[] getTasks() {
        return MBaseProjectPhaseKt.getProjectPhaseTasks(getProjectPhaseId());
    } //	getTasks

    /**
     * Copy Lines from other Phase BF 3067850 - monhate
     *
     * @param fromPhase from phase
     * @return number of tasks copied
     */
    public int copyLinesFrom(MProjectPhase fromPhase) {
        if (fromPhase == null) return 0;
        int count = 0;
        //
        MProjectLine[] fromLines = fromPhase.getLines();
        //	Copy Project Lines
        for (MProjectLine fromLine : fromLines) {
            if (fromLine.getProjectTaskId() != 0) continue;
            MProjectLine toLine = new MProjectLine(0);
            PO.copyValues(fromLine, toLine, getClientId(), getOrgId());
            toLine.setProjectId(getProjectId());
            toLine.setProjectPhaseId(getProjectPhaseId());
            toLine.saveEx();
            count++;
        }
        if (fromLines.length != count)
            log.warning("Count difference - ProjectLine=" + fromLines.length + " <> Saved=" + count);

        return count;
    }

    /**
     * Copy Tasks from other Phase BF 3067850 - monhate
     *
     * @param fromPhase from phase
     * @return number of tasks copied
     */
    public int copyTasksFrom(MProjectPhase fromPhase) {
        if (fromPhase == null) return 0;
        int count = 0, countLine = 0;
        //
        MProjectTask[] myTasks = getTasks();
        MProjectTask[] fromTasks = fromPhase.getTasks();
        //	Copy Project Tasks
        for (MProjectTask fromTask : fromTasks) {
            //	Check if Task already exists
            int C_Task_ID = fromTask.getTaskId();
            boolean exists = false;
            if (C_Task_ID == 0) exists = false;
            else {
                for (MProjectTask myTask : myTasks) {
                    if (myTask.getTaskId() == C_Task_ID) {
                        exists = true;
                        break;
                    }
                }
            }
            //	Phase exist
            if (exists) {
                if (log.isLoggable(Level.INFO))
                    log.info("Task already exists here, ignored - " + fromTask);
            } else {
                MProjectTask toTask = new MProjectTask(0);
                PO.copyValues(fromTask, toTask, getClientId(), getOrgId());
                toTask.setProjectPhaseId(getProjectPhaseId());
                toTask.saveEx();
                count++;
                // BF 3067850 - monhate
                countLine += toTask.copyLinesFrom(fromTask);
            }
        }
        if (fromTasks.length != count)
            log.warning("Count difference - ProjectPhase=" + fromTasks.length + " <> Saved=" + count);

        return count + countLine;
    } //	copyTasksFrom

    /**
     * Copy Tasks from other Phase
     *
     * @param fromPhase from phase
     * @return number of tasks copied
     */
    public int copyTasksFrom(MProjectTypePhase fromPhase) {
        if (fromPhase == null) return 0;
        int count = 0;
        //	Copy Type Tasks
        MProjectTypeTask[] fromTasks = fromPhase.getTasks();
        for (MProjectTypeTask fromTask : fromTasks) {
            MProjectTask toTask = new MProjectTask(this, fromTask);
            if (toTask.save()) count++;
        }
        if (log.isLoggable(Level.FINE)) log.fine("#" + count + " - " + fromPhase);
        if (fromTasks.length != count)
            log.log(
                    Level.SEVERE, "Count difference - TypePhase=" + fromTasks.length + " <> Saved=" + count);

        return count;
    } //	copyTasksFrom

    /**
     * ************************************************************************ Get Project Lines BF
     * 3067850 - monhate
     *
     * @return Array of lines
     */
    public MProjectLine[] getLines() {
        final String whereClause = "C_Project_ID=? and C_ProjectPhase_ID=?";
        List<MProjectLine> list =
                new Query(I_C_ProjectLine.Table_Name, whereClause)
                        .setParameters(getProjectId(), getProjectPhaseId())
                        .setOrderBy("Line")
                        .list();
        //
        MProjectLine[] retValue = new MProjectLine[list.size()];
        list.toArray(retValue);
        return retValue;
    }

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        return "MProjectPhase[" + getId() + "-" + getSeqNo() + "-" + getName() + "]";
    } //	toString
} //	MProjectPhase
