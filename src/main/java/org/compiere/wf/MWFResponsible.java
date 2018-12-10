package org.compiere.wf;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_AD_WF_Responsible;
import org.compiere.orm.MRole;
import org.compiere.util.Msg;
import org.idempiere.common.util.CCache;

/**
 * Workflow Resoinsible
 *
 * @author Jorg Janke
 * @version $Id: MWFResponsible.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWFResponsible extends X_AD_WF_Responsible {
  /** long - serialVersionUID. */
  private static final long serialVersionUID = 4167967243996935999L;

  /**
   * Get WF Responsible from Cache
   *
   * @param ctx context
   * @param AD_WF_Responsible_ID id
   * @return MWFResponsible
   */
  public static MWFResponsible get(Properties ctx, int AD_WF_Responsible_ID) {
    Integer key = new Integer(AD_WF_Responsible_ID);
    MWFResponsible retValue = (MWFResponsible) s_cache.get(key);
    if (retValue != null) return retValue;
    retValue = new MWFResponsible(ctx, AD_WF_Responsible_ID, null);
    if (retValue.getId() != 0) s_cache.put(key, retValue);
    return retValue;
  } //	get

  /** Cache */
  private static CCache<Integer, MWFResponsible> s_cache =
      new CCache<Integer, MWFResponsible>(I_AD_WF_Responsible.Table_Name, 10);

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param AD_WF_Responsible_ID id
   * @param trxName transaction
   */
  public MWFResponsible(Properties ctx, int AD_WF_Responsible_ID, String trxName) {
    super(ctx, AD_WF_Responsible_ID, trxName);
  } //	MWFResponsible

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MWFResponsible(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MWFResponsible

  /**
   * Invoker - return true if no user and no role
   *
   * @return true if invoker
   */
  public boolean isInvoker() {
    return getAD_User_ID() == 0 && getAD_Role_ID() == 0 && !isManual();
  } //	isInvoker

  /**
   * Is Role Responsible
   *
   * @return true if role
   */
  public boolean isRole() {
    return X_AD_WF_Responsible.RESPONSIBLETYPE_Role.equals(getResponsibleType())
        && getAD_Role_ID() != 0;
  } //	isRole

  /**
   * Is Role Responsible
   *
   * @return true if role
   */
  public MRole getRole() {
    if (!isRole()) return null;
    return MRole.get(getCtx(), getAD_Role_ID());
  } //	getRole

  /**
   * Is Human Responsible
   *
   * @return true if human
   */
  public boolean isHuman() {
    return X_AD_WF_Responsible.RESPONSIBLETYPE_Human.equals(getResponsibleType())
        && getAD_User_ID() != 0;
  } //	isHuman

  /**
   * Is Org Responsible
   *
   * @return true if Org
   */
  public boolean isOrganization() {
    return X_AD_WF_Responsible.RESPONSIBLETYPE_Organization.equals(getResponsibleType())
        &&  getOrgId() != 0;
  } //	isOrg

  /**
   * Before Save
   *
   * @param newRecord new
   * @return tre if can be saved
   */
  protected boolean beforeSave(boolean newRecord) {
    //	if (RESPONSIBLETYPE_Human.equals(getResponsibleType()) && getAD_User_ID() == 0)
    //		return true;
    if (X_AD_WF_Responsible.RESPONSIBLETYPE_Role.equals(getResponsibleType())
        && getAD_Role_ID() == 0
        && getADClientID() > 0) {
      log.saveError("Error", Msg.parseTranslation(getCtx(), "@RequiredEnter@ @AD_Role_ID@"));
      return false;
    }
    //	User not used
    if (!X_AD_WF_Responsible.RESPONSIBLETYPE_Human.equals(getResponsibleType())
        && getAD_User_ID() > 0) setAD_User_ID(0);

    //	Role not used
    if (!X_AD_WF_Responsible.RESPONSIBLETYPE_Role.equals(getResponsibleType())
        && getAD_Role_ID() > 0) setAD_Role_ID(0);

    if (X_AD_WF_Responsible.RESPONSIBLETYPE_Manual.equals(getResponsibleType())) {
      setAD_User_ID(0);
      setAD_Role_ID(0);
    }
    return true;
  } //	beforeSave

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("MWFResponsible[");
    sb.append(getId()).append("-").append(getName()).append(",Type=").append(getResponsibleType());
    if (getAD_User_ID() != 0) sb.append(",AD_User_ID=").append(getAD_User_ID());
    if (getAD_Role_ID() != 0) sb.append(",AD_Role_ID=").append(getAD_Role_ID());
    sb.append("]");
    return sb.toString();
  } //	toString

  public boolean isManual() {
    return X_AD_WF_Responsible.RESPONSIBLETYPE_Manual.equals(getResponsibleType());
  }
} //	MWFResponsible
