package org.compiere.wf;

import kotliquery.Row;
import org.compiere.model.I_AD_WF_Responsible;
import org.compiere.orm.MRole;
import org.compiere.util.Msg;
import org.idempiere.common.util.CCache;

import java.util.Properties;

/**
 * Workflow Resoinsible
 *
 * @author Jorg Janke
 * @version $Id: MWFResponsible.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWFResponsible extends X_AD_WF_Responsible {
    /**
     * long - serialVersionUID.
     */
    private static final long serialVersionUID = 4167967243996935999L;
    /**
     * Cache
     */
    private static CCache<Integer, MWFResponsible> s_cache =
            new CCache<Integer, MWFResponsible>(I_AD_WF_Responsible.Table_Name, 10);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx                  context
     * @param AD_WF_Responsible_ID id
     */
    public MWFResponsible(Properties ctx, int AD_WF_Responsible_ID) {
        super(ctx, AD_WF_Responsible_ID);
    } //	MWFResponsible

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MWFResponsible(Properties ctx, Row row) {
        super(ctx, row);
    } //	MWFResponsible

    /**
     * Get WF Responsible from Cache
     *
     * @param ctx                  context
     * @param AD_WF_Responsible_ID id
     * @return MWFResponsible
     */
    public static MWFResponsible get(Properties ctx, int AD_WF_Responsible_ID) {
        Integer key = AD_WF_Responsible_ID;
        MWFResponsible retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MWFResponsible(ctx, AD_WF_Responsible_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Invoker - return true if no user and no role
     *
     * @return true if invoker
     */
    public boolean isInvoker() {
        return getUserId() == 0 && getRoleId() == 0 && !isManual();
    } //	isInvoker

    /**
     * Is Role Responsible
     *
     * @return true if role
     */
    public boolean isRole() {
        return X_AD_WF_Responsible.RESPONSIBLETYPE_Role.equals(getResponsibleType())
                && getRoleId() != 0;
    } //	isRole

    /**
     * Is Role Responsible
     *
     * @return true if role
     */
    public MRole getRole() {
        if (!isRole()) return null;
        return MRole.get(getCtx(), getRoleId());
    } //	getRole

    /**
     * Is Human Responsible
     *
     * @return true if human
     */
    public boolean isHuman() {
        return X_AD_WF_Responsible.RESPONSIBLETYPE_Human.equals(getResponsibleType())
                && getUserId() != 0;
    } //	isHuman

    /**
     * Is Org Responsible
     *
     * @return true if Org
     */
    public boolean isOrganization() {
        return X_AD_WF_Responsible.RESPONSIBLETYPE_Organization.equals(getResponsibleType())
                && getOrgId() != 0;
    } //	isOrg

    /**
     * Before Save
     *
     * @param newRecord new
     * @return tre if can be saved
     */
    protected boolean beforeSave(boolean newRecord) {
        //	if (RESPONSIBLETYPE_Human.equals(getResponsibleType()) && getUserId() == 0)
        //		return true;
        if (X_AD_WF_Responsible.RESPONSIBLETYPE_Role.equals(getResponsibleType())
                && getRoleId() == 0
                && getClientId() > 0) {
            log.saveError("Error", Msg.parseTranslation(getCtx(), "@RequiredEnter@ @AD_Role_ID@"));
            return false;
        }
        //	User not used
        if (!X_AD_WF_Responsible.RESPONSIBLETYPE_Human.equals(getResponsibleType())
                && getUserId() > 0) setUserId(0);

        //	Role not used
        if (!X_AD_WF_Responsible.RESPONSIBLETYPE_Role.equals(getResponsibleType())
                && getRoleId() > 0) setRoleId(0);

        if (X_AD_WF_Responsible.RESPONSIBLETYPE_Manual.equals(getResponsibleType())) {
            setUserId(0);
            setRoleId(0);
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
        if (getUserId() != 0) sb.append(",AD_User_ID=").append(getUserId());
        if (getRoleId() != 0) sb.append(",AD_Role_ID=").append(getRoleId());
        sb.append("]");
        return sb.toString();
    } //	toString

    public boolean isManual() {
        return X_AD_WF_Responsible.RESPONSIBLETYPE_Manual.equals(getResponsibleType());
    }
} //	MWFResponsible
