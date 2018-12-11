package org.compiere.schedule;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.process.MProcessPara;

/**
 * Scheduler Parameter Model
 *
 * @author Jorg Janke
 * @version $Id: MSchedulerPara.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MSchedulerPara extends X_AD_Scheduler_Para {
  /** */
  private static final long serialVersionUID = -703173920039087748L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param AD_Scheduler_Para_ID id
   * @param trxName transaction
   */
  public MSchedulerPara(Properties ctx, int AD_Scheduler_Para_ID, String trxName) {
    super(ctx, AD_Scheduler_Para_ID, trxName);
  } //	MSchedulerPara

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MSchedulerPara(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MSchedulerPara

  /** Parameter Column Name */
  private MProcessPara m_parameter = null;

  /**
   * Get Parameter Column Name
   *
   * @return column name
   */
  public String getColumnName() {
    if (m_parameter == null) m_parameter = MProcessPara.get(getCtx(), getAD_Process_Para_ID());
    return m_parameter.getColumnName();
  } //	getColumnName

  /**
   * Get Display Type
   *
   * @return display type
   */
  public int getDisplayType() {
    if (m_parameter == null) m_parameter = MProcessPara.get(getCtx(), getAD_Process_Para_ID());
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
