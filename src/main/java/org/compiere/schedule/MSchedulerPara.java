package org.compiere.schedule;

import kotliquery.Row;
import org.compiere.process.MProcessPara;

import java.util.Properties;

/**
 * Scheduler Parameter Model
 *
 * @author Jorg Janke
 * @version $Id: MSchedulerPara.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MSchedulerPara extends X_AD_Scheduler_Para {
    /**
     *
     */
    private static final long serialVersionUID = -703173920039087748L;
    /**
     * Parameter Column Name
     */
    private MProcessPara m_parameter = null;

    /**
     * Standard Constructor
     *
     * @param ctx                  context
     * @param AD_Scheduler_Para_ID id
     */
    public MSchedulerPara(Properties ctx, int AD_Scheduler_Para_ID) {
        super(ctx, AD_Scheduler_Para_ID);
    } //	MSchedulerPara

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MSchedulerPara(Properties ctx, Row row) {
        super(ctx, row);
    } //	MSchedulerPara

    /**
     * Get Parameter Column Name
     *
     * @return column name
     */
    public String getColumnName() {
        if (m_parameter == null) m_parameter = MProcessPara.get(getCtx(), getProcessParameterId());
        return m_parameter.getColumnName();
    } //	getColumnName

    /**
     * Get Display Type
     *
     * @return display type
     */
    public int getDisplayType() {
        if (m_parameter == null) m_parameter = MProcessPara.get(getCtx(), getProcessParameterId());
        return m_parameter.getReferenceId();
    } //	getDisplayType

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MSchedulerPara[");
        sb.append(getId())
                .append("-")
                .append(getColumnName())
                .append("=")
                .append(getParameterDefault())
                .append("]");
        return sb.toString();
    } //	toString
} //	MSchedulerPara
