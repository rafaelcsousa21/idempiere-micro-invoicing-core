package org.compiere.wf;

import kotliquery.Row;
import org.compiere.model.I_AD_WF_NodeNext;
import org.compiere.orm.PO;

import java.util.Properties;

/**
 * Generated Model for AD_WF_NodeNext
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_NodeNext extends PO implements I_AD_WF_NodeNext {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_WF_NodeNext(Properties ctx, int AD_WF_NodeNext_ID) {
        super(ctx, AD_WF_NodeNext_ID);
        /**
         * if (AD_WF_NodeNext_ID == 0) { setAD_WF_Next_ID (0); setAD_WF_Node_ID (0);
         * // @1|AD_WF_Node_ID@ setAD_WF_NodeNext_ID (0); setEntityType (null); // @SQL=select
         * get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual setIsStdUserWorkflow (false); setSeqNo
         * (0); // 10 }
         */
    }

    /**
     * Load Constructor
     */
    public X_AD_WF_NodeNext(Properties ctx, Row row) {
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

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_WF_NodeNext[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Next Node.
     *
     * @return Next Node in workflow
     */
    public int getWorkflowNextId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_WF_Next_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Next Node.
     *
     * @param AD_WF_Next_ID Next Node in workflow
     */
    public void setWorkflowNextId(int AD_WF_Next_ID) {
        if (AD_WF_Next_ID < 1) setValue(COLUMNNAME_AD_WF_Next_ID, null);
        else setValue(COLUMNNAME_AD_WF_Next_ID, Integer.valueOf(AD_WF_Next_ID));
    }

    /**
     * Get Node.
     *
     * @return Workflow Node (activity), step or process
     */
    public int getWorkflowNodeId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_WF_Node_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Node.
     *
     * @param AD_WF_Node_ID Workflow Node (activity), step or process
     */
    public void setWorkflowNodeId(int AD_WF_Node_ID) {
        if (AD_WF_Node_ID < 1) setValueNoCheck(COLUMNNAME_AD_WF_Node_ID, null);
        else setValueNoCheck(COLUMNNAME_AD_WF_Node_ID, Integer.valueOf(AD_WF_Node_ID));
    }

    /**
     * Get Node Transition.
     *
     * @return Workflow Node Transition
     */
    public int getWorkflowNodeNextId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_WF_NodeNext_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Description.
     *
     * @return Optional short description of the record
     */
    public String getDescription() {
        return (String) getValue(COLUMNNAME_Description);
    }

    /**
     * Set Entity Type.
     *
     * @param EntityType Dictionary Entity Type; Determines ownership and synchronization
     */
    public void setEntityType(String EntityType) {

        setValue(COLUMNNAME_EntityType, EntityType);
    }

    /**
     * Set Std User Workflow.
     *
     * @param IsStdUserWorkflow Standard Manual User Approval Workflow
     */
    public void setIsStdUserWorkflow(boolean IsStdUserWorkflow) {
        setValue(COLUMNNAME_IsStdUserWorkflow, Boolean.valueOf(IsStdUserWorkflow));
    }

    /**
     * Get Std User Workflow.
     *
     * @return Standard Manual User Approval Workflow
     */
    public boolean isStdUserWorkflow() {
        Object oo = getValue(COLUMNNAME_IsStdUserWorkflow);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Sequence.
     *
     * @return Method of ordering records; lowest number comes first
     */
    public int getSeqNo() {
        Integer ii = (Integer) getValue(COLUMNNAME_SeqNo);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Sequence.
     *
     * @param SeqNo Method of ordering records; lowest number comes first
     */
    public void setSeqNo(int SeqNo) {
        setValue(COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
    }

    @Override
    public int getTableId() {
        return I_AD_WF_NodeNext.Table_ID;
    }
}
