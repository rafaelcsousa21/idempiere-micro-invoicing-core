package org.compiere.wf;

import org.compiere.model.HasName;
import org.compiere.model.I_AD_WF_Responsible;
import org.compiere.orm.BasePOUser;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_WF_Responsible
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_Responsible extends BasePOUser implements I_AD_WF_Responsible {

    /**
     * Organization = O
     */
    public static final String RESPONSIBLETYPE_Organization = "O";
    /**
     * Human = H
     */
    public static final String RESPONSIBLETYPE_Human = "H";
    /**
     * Role = R
     */
    public static final String RESPONSIBLETYPE_Role = "R";
    /**
     * Manual = M
     */
    public static final String RESPONSIBLETYPE_Manual = "M";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_WF_Responsible(Properties ctx, int AD_WF_Responsible_ID) {
        super(ctx, AD_WF_Responsible_ID);
    }

    /**
     * Load Constructor
     */
    public X_AD_WF_Responsible(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 6 - System - Client
     */
    protected int getAccessLevel() {
        return I_AD_WF_Responsible.accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_WF_Responsible[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Role.
     *
     * @return Responsibility Role
     */
    public int getRoleId() {
        Integer ii = (Integer) getValue(I_AD_WF_Responsible.COLUMNNAME_AD_Role_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Role.
     *
     * @param AD_Role_ID Responsibility Role
     */
    public void setRoleId(int AD_Role_ID) {
        if (AD_Role_ID < 0) setValue(I_AD_WF_Responsible.COLUMNNAME_AD_Role_ID, null);
        else setValue(I_AD_WF_Responsible.COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
    }

    /**
     * Get Name.
     *
     * @return Alphanumeric identifier of the entity
     */
    public String getName() {
        return (String) getValue(HasName.Companion.getCOLUMNNAME_Name());
    }

    /**
     * Get Responsible Type.
     *
     * @return Type of the Responsibility for a workflow
     */
    public String getResponsibleType() {
        return (String) getValue(I_AD_WF_Responsible.COLUMNNAME_ResponsibleType);
    }

    @Override
    public int getTableId() {
        return I_AD_WF_Responsible.Table_ID;
    }
}
