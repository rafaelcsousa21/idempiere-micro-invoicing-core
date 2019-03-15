package org.compiere.wf;

import kotliquery.Row;
import org.compiere.model.I_AD_Workflow_Access;
import org.compiere.orm.PO;

import java.util.Properties;

/**
 * Generated Model for AD_Workflow_Access
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Workflow_Access extends PO implements I_AD_Workflow_Access {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_Workflow_Access(Properties ctx, int AD_Workflow_Access_ID) {
        super(ctx, AD_Workflow_Access_ID);
    }

    /**
     * Load Constructor
     */
    public X_AD_Workflow_Access(Properties ctx, Row row) {
        super(ctx, row);
    }

    /**
     * AccessLevel
     *
     * @return 6 - System - Client
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }


    @Override
    public int getTableId() {
        return Table_ID;
    }

    public String toString() {
        return "X_AD_Workflow_Access[" + getId() + "]";
    }

    /**
     * Set Role.
     *
     * @param AD_Role_ID Responsibility Role
     */
    public void setRoleId(int AD_Role_ID) {
        if (AD_Role_ID < 0) setValueNoCheck(COLUMNNAME_AD_Role_ID, null);
        else setValueNoCheck(COLUMNNAME_AD_Role_ID, AD_Role_ID);
    }

    /**
     * Set Workflow.
     *
     * @param AD_Workflow_ID Workflow or combination of tasks
     */
    public void setWorkflowId(int AD_Workflow_ID) {
        if (AD_Workflow_ID < 1) setValueNoCheck(COLUMNNAME_AD_Workflow_ID, null);
        else setValueNoCheck(COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
    }

    /**
     * Set Read Write.
     *
     * @param IsReadWrite Field is read / write
     */
    public void setIsReadWrite(boolean IsReadWrite) {
        setValue(COLUMNNAME_IsReadWrite, Boolean.valueOf(IsReadWrite));
    }

}
