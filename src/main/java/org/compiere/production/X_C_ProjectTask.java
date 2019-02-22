package org.compiere.production;

import org.compiere.model.I_C_ProjectTask;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_ProjectTask
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ProjectTask extends BasePOName implements I_C_ProjectTask, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_ProjectTask(Properties ctx, int C_ProjectTask_ID) {
        super(ctx, C_ProjectTask_ID);
    }

    /**
     * Load Constructor
     */
    public X_C_ProjectTask(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 3 - Client - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_C_ProjectTask[").append(getId()).append("]");
        return sb.toString();
    }

    public org.compiere.model.I_C_ProjectPhase getC_ProjectPhase() throws RuntimeException {
        return (org.compiere.model.I_C_ProjectPhase)
                MTable.get(getCtx(), org.compiere.model.I_C_ProjectPhase.Table_Name)
                        .getPO(getC_ProjectPhase_ID());
    }

    /**
     * Get Project Phase.
     *
     * @return Phase of a Project
     */
    public int getC_ProjectPhase_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_ProjectPhase_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Project Phase.
     *
     * @param C_ProjectPhase_ID Phase of a Project
     */
    public void setC_ProjectPhase_ID(int C_ProjectPhase_ID) {
        if (C_ProjectPhase_ID < 1) set_ValueNoCheck(COLUMNNAME_C_ProjectPhase_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_ProjectPhase_ID, C_ProjectPhase_ID);
    }

    /**
     * Get Project Task.
     *
     * @return Actual Project Task in a Phase
     */
    public int getC_ProjectTask_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_ProjectTask_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Standard Task.
     *
     * @return Standard Project Type Task
     */
    public int getC_Task_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Task_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Standard Task.
     *
     * @param C_Task_ID Standard Project Type Task
     */
    public void setC_Task_ID(int C_Task_ID) {
        if (C_Task_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Task_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_Task_ID, Integer.valueOf(C_Task_ID));
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
     * Set Description.
     *
     * @param Description Optional short description of the record
     */
    public void setDescription(String Description) {
        set_Value(COLUMNNAME_Description, Description);
    }

    /**
     * Set Comment/Help.
     *
     * @param Help Comment or Hint
     */
    public void setHelp(String Help) {
        set_Value(COLUMNNAME_Help, Help);
    }

    /**
     * Get Product.
     *
     * @return Product, Service, Item
     */
    public int getM_Product_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_Product_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Product.
     *
     * @param M_Product_ID Product, Service, Item
     */
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) set_Value(COLUMNNAME_M_Product_ID, null);
        else set_Value(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
    }

    /**
     * Get Quantity.
     *
     * @return Quantity
     */
    public BigDecimal getQty() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Qty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Quantity.
     *
     * @param Qty Quantity
     */
    public void setQty(BigDecimal Qty) {
        set_Value(COLUMNNAME_Qty, Qty);
    }

    /**
     * Get Sequence.
     *
     * @return Method of ordering records; lowest number comes first
     */
    public int getSeqNo() {
        Integer ii = (Integer) get_Value(COLUMNNAME_SeqNo);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Sequence.
     *
     * @param SeqNo Method of ordering records; lowest number comes first
     */
    public void setSeqNo(int SeqNo) {
        set_Value(COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
    }

    @Override
    public int getTableId() {
        return I_C_ProjectTask.Table_ID;
    }
}
