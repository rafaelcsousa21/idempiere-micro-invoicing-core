package org.compiere.wf;

import org.compiere.model.I_AD_WF_Node_Para;
import org.compiere.orm.PO;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_WF_Node_Para
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_Node_Para extends PO implements I_AD_WF_Node_Para {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_WF_Node_Para(Properties ctx, int AD_WF_Node_Para_ID) {
        super(ctx, AD_WF_Node_Para_ID);
        /**
         * if (AD_WF_Node_Para_ID == 0) { setAD_WF_Node_ID (0); // @1|AD_WF_Node_ID@
         * setAD_WF_Node_Para_ID (0); setEntityType (null); // @SQL=select
         * get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual }
         */
    }

    /**
     * Load Constructor
     */
    public X_AD_WF_Node_Para(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 6 - System - Client
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_WF_Node_Para[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Process Parameter.
     *
     * @return Process Parameter
     */
    public int getProcessParameterId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_Process_Para_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Process Parameter.
     *
     * @param AD_Process_Para_ID Process Parameter
     */
    public void setProcessParameterId(int AD_Process_Para_ID) {
        if (AD_Process_Para_ID < 1) set_Value(COLUMNNAME_AD_Process_Para_ID, null);
        else set_Value(COLUMNNAME_AD_Process_Para_ID, Integer.valueOf(AD_Process_Para_ID));
    }

    /**
     * Get Attribute Name.
     *
     * @return Name of the Attribute
     */
    public String getAttributeName() {
        return (String) getValue(COLUMNNAME_AttributeName);
    }

    /**
     * Set Attribute Name.
     *
     * @param AttributeName Name of the Attribute
     */
    public void setAttributeName(String AttributeName) {
        set_Value(COLUMNNAME_AttributeName, AttributeName);
    }

    /**
     * Get Attribute Value.
     *
     * @return Value of the Attribute
     */
    public String getAttributeValue() {
        return (String) getValue(COLUMNNAME_AttributeValue);
    }

    @Override
    public int getTableId() {
        return I_AD_WF_Node_Para.Table_ID;
    }
}
