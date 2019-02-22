package org.compiere.production;

import org.compiere.model.I_C_ProjectPhase;
import org.compiere.orm.BasePOName;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_ProjectPhase
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ProjectPhase extends BasePOName implements I_C_ProjectPhase, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_ProjectPhase(Properties ctx, int C_ProjectPhase_ID) {
        super(ctx, C_ProjectPhase_ID);
        /**
         * if (C_ProjectPhase_ID == 0) { setCommittedAmt (Env.ZERO); setC_Project_ID (0);
         * setC_ProjectPhase_ID (0); setIsCommitCeiling (false); setIsComplete (false); setName (null);
         * setPlannedAmt (Env.ZERO); setProjInvoiceRule (null); // @ProjInvoiceRule@ setSeqNo (0);
         * // @SQL=SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM C_ProjectPhase WHERE
         * C_Project_ID=@C_Project_ID@ }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_ProjectPhase(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_C_ProjectPhase[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Set Committed Amount.
     *
     * @param CommittedAmt The (legal) commitment amount
     */
    public void setCommittedAmt(BigDecimal CommittedAmt) {
        set_Value(COLUMNNAME_CommittedAmt, CommittedAmt);
    }

    /**
     * Get Order.
     *
     * @return Order
     */
    public int getC_Order_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Order_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Order.
     *
     * @param C_Order_ID Order
     */
    public void setC_Order_ID(int C_Order_ID) {
        if (C_Order_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Order_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
    }

    /**
     * Get Standard Phase.
     *
     * @return Standard Phase of the Project Type
     */
    public int getC_Phase_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Phase_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Standard Phase.
     *
     * @param C_Phase_ID Standard Phase of the Project Type
     */
    public void setC_Phase_ID(int C_Phase_ID) {
        if (C_Phase_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Phase_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_Phase_ID, Integer.valueOf(C_Phase_ID));
    }

    /**
     * Get Project.
     *
     * @return Financial Project
     */
    public int getC_Project_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Project_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Project.
     *
     * @param C_Project_ID Financial Project
     */
    public void setC_Project_ID(int C_Project_ID) {
        if (C_Project_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Project_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
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
     * Set Commitment is Ceiling.
     *
     * @param IsCommitCeiling The commitment amount/quantity is the chargeable ceiling
     */
    public void setIsCommitCeiling(boolean IsCommitCeiling) {
        set_Value(COLUMNNAME_IsCommitCeiling, Boolean.valueOf(IsCommitCeiling));
    }

    /**
     * Set Complete.
     *
     * @param IsComplete It is complete
     */
    public void setIsComplete(boolean IsComplete) {
        set_Value(COLUMNNAME_IsComplete, Boolean.valueOf(IsComplete));
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
     * Get Unit Price.
     *
     * @return Actual Price
     */
    public BigDecimal getPriceActual() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_PriceActual);
        if (bd == null) return Env.ZERO;
        return bd;
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
        return I_C_ProjectPhase.Table_ID;
    }
}
