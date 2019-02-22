package org.compiere.schedule;

import org.compiere.model.I_AD_Scheduler_Para;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_Scheduler_Para
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Scheduler_Para extends PO implements I_AD_Scheduler_Para, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_Scheduler_Para(Properties ctx, int AD_Scheduler_Para_ID) {
        super(ctx, AD_Scheduler_Para_ID);
        /** if (AD_Scheduler_Para_ID == 0) { setAD_Process_Para_ID (0); setAD_Scheduler_ID (0); } */
    }

    /**
     * Load Constructor
     */
    public X_AD_Scheduler_Para(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_AD_Scheduler_Para[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Process Parameter.
     *
     * @return Process Parameter
     */
    public int getAD_Process_Para_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_Process_Para_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Scheduler.
     *
     * @return Schedule Processes
     */
    public int getAD_Scheduler_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_Scheduler_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Description.
     *
     * @return Optional short description of the record
     */
    public String getDescription() {
        return (String) get_Value(COLUMNNAME_Description);
    }

    /**
     * Get Default Parameter.
     *
     * @return Default value of the parameter
     */
    public String getParameterDefault() {
        return (String) get_Value(COLUMNNAME_ParameterDefault);
    }

    /**
     * Get Default To Parameter.
     *
     * @return Default value of the to parameter
     */
    public String getParameterToDefault() {
        return (String) get_Value(COLUMNNAME_ParameterToDefault);
    }

    @Override
    public int getTableId() {
        return I_AD_Scheduler_Para.Table_ID;
    }
}
