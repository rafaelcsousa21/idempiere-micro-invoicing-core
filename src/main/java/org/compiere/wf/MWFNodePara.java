package org.compiere.wf;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.model.I_AD_WF_Node_Para;
import org.compiere.orm.Query;
import org.compiere.process.MProcessPara;

/**
 * Workflow Node Process Parameter Model
 *
 * @author Jorg Janke
 * @version $Id: MWFNodePara.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWFNodePara extends X_AD_WF_Node_Para {
  /** */
  private static final long serialVersionUID = 4132254339643230238L;

  /**
   * Get Parameters for a node
   *
   * @param ctx context
   * @param AD_WF_Node_ID node
   * @return array of parameters
   */
  public static MWFNodePara[] getParameters(Properties ctx, int AD_WF_Node_ID) {

    List<MWFNodePara> list =
        new Query(ctx, I_AD_WF_Node_Para.Table_Name, "AD_WF_Node_ID=?")
            .setParameters(new Object[] {AD_WF_Node_ID})
            .list();
    MWFNodePara[] retValue = new MWFNodePara[list.size()];
    list.toArray(retValue);
    return retValue;
  } //	getParameters

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param id id
   * @param trxName transaction
   */
  public MWFNodePara(Properties ctx, int id) {
    super(ctx, id);
  } //	MWFNodePara

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MWFNodePara(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MWFNodePara

  /** Linked Process Parameter */
  private MProcessPara m_processPara = null;

  /**
   * Get Process Parameter
   *
   * @return process parameter
   */
  public MProcessPara getProcessPara() {
    if (m_processPara == null)
      m_processPara = new MProcessPara(getCtx(), getAD_Process_Para_ID());
    return m_processPara;
  } //	getProcessPara

  /**
   * Get Attribute Name. If not set - retrieve it
   *
   * @return attribute name
   */
  public String getAttributeName() {
    String an = super.getAttributeName();
    if (an == null || an.length() == 0 && getAD_Process_Para_ID() != 0) {
      an = getProcessPara().getColumnName();
      setAttributeName(an);
      saveEx();
    }
    return an;
  } //	getAttributeName

  /**
   * Get Display Type
   *
   * @return display type
   */
  public int getDisplayType() {
    return getProcessPara().getReferenceId();
  } //	getDisplayType

  /**
   * Is Mandatory
   *
   * @return true if mandatory
   */
  public boolean isMandatory() {
    return getProcessPara().isMandatory();
  } //	isMandatory

  /**
   * Set AD_Process_Para_ID
   *
   * @param AD_Process_Para_ID id
   */
  public void setAD_Process_Para_ID(int AD_Process_Para_ID) {
    super.setAD_Process_Para_ID(AD_Process_Para_ID);
    setAttributeName(null);
  }
} //	MWFNodePara
