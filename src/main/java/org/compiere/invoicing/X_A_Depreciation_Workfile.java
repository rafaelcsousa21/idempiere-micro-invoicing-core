package org.compiere.invoicing;

import org.compiere.model.I_A_Depreciation_Workfile;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for A_Depreciation_Workfile
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Depreciation_Workfile extends PO
        implements I_A_Depreciation_Workfile, I_Persistent {

    /**
     * Cofinantare = C
     */
    public static final String A_TIP_FINANTARE_Cofinantare = "C";
    /**
     * Proprie = P
     */
    public static final String A_TIP_FINANTARE_Proprie = "P";
    /**
     * Terti = T
     */
    public static final String A_TIP_FINANTARE_Terti = "T";
    /**
     * Actual = A
     */
    public static final String POSTINGTYPE_Actual = "A";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_A_Depreciation_Workfile(Properties ctx, int A_Depreciation_Workfile_ID) {
        super(ctx, A_Depreciation_Workfile_ID);
        /**
         * if (A_Depreciation_Workfile_ID == 0) { setA_Accumulated_Depr (Env.ZERO); // 0
         * setA_Accumulated_Depr_F (Env.ZERO); // 0 setA_Asset_Cost (Env.ZERO); // 0 setA_Asset_ID (0);
         * setA_Asset_Life_Current_Year (Env.ZERO); // 0 setA_Asset_Life_Years (0); // 0
         * setA_Asset_Life_Years_F (0); // 0 setA_Curr_Dep_Exp (Env.ZERO); // 0
         * setA_Depreciation_Workfile_ID (0); setA_Life_Period (0); // 0 setA_Life_Period_F (0); // 0
         * setA_QTY_Current (Env.ZERO); setA_Salvage_Value (Env.ZERO); // 0 setA_Tip_Finantare (null);
         * // 'P' setProcessed (false); // N setUseLifeMonths (0); // 0 setUseLifeMonths_F (0); // 0
         * setUseLifeYears (0); // 0 setUseLifeYears_F (0); // 0 }
         */
    }

    /**
     * Load Constructor
     */
    public X_A_Depreciation_Workfile(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 7 - System - Client - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_A_Depreciation_Workfile[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Accumulated Depreciation.
     *
     * @return Accumulated Depreciation
     */
    public BigDecimal getA_Accumulated_Depr() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_A_Accumulated_Depr);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Accumulated Depreciation.
     *
     * @param A_Accumulated_Depr Accumulated Depreciation
     */
    public void setA_Accumulated_Depr(BigDecimal A_Accumulated_Depr) {
        set_Value(COLUMNNAME_A_Accumulated_Depr, A_Accumulated_Depr);
    }

    /**
     * Get Accumulated Depreciation (fiscal).
     *
     * @return Accumulated Depreciation (fiscal)
     */
    public BigDecimal getA_Accumulated_Depr_F() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_A_Accumulated_Depr_F);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Accumulated Depreciation (fiscal).
     *
     * @param A_Accumulated_Depr_F Accumulated Depreciation (fiscal)
     */
    public void setA_Accumulated_Depr_F(BigDecimal A_Accumulated_Depr_F) {
        set_Value(COLUMNNAME_A_Accumulated_Depr_F, A_Accumulated_Depr_F);
    }

    /**
     * Get Asset Cost.
     *
     * @return Asset Cost
     */
    public BigDecimal getA_Asset_Cost() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_A_Asset_Cost);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Asset Cost.
     *
     * @param A_Asset_Cost Asset Cost
     */
    public void setA_Asset_Cost(BigDecimal A_Asset_Cost) {
        set_Value(COLUMNNAME_A_Asset_Cost, A_Asset_Cost);
    }

    public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException {
        return (org.compiere.model.I_A_Asset)
                MTable.get(getCtx(), org.compiere.model.I_A_Asset.Table_Name)
                        .getPO(getA_Asset_ID());
    }

    /**
     * Get Asset.
     *
     * @return Asset used internally or by customers
     */
    public int getA_Asset_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Asset.
     *
     * @param A_Asset_ID Asset used internally or by customers
     */
    public void setA_Asset_ID(int A_Asset_ID) {
        if (A_Asset_ID < 1) set_ValueNoCheck(COLUMNNAME_A_Asset_ID, null);
        else set_ValueNoCheck(COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
    }

    /**
     * Set Life Years.
     *
     * @param A_Asset_Life_Years Life Years
     */
    public void setA_Asset_Life_Years(int A_Asset_Life_Years) {
        set_Value(COLUMNNAME_A_Asset_Life_Years, Integer.valueOf(A_Asset_Life_Years));
    }

    /**
     * Set Life Years (fiscal).
     *
     * @param A_Asset_Life_Years_F Life Years (fiscal)
     */
    public void setA_Asset_Life_Years_F(int A_Asset_Life_Years_F) {
        set_Value(COLUMNNAME_A_Asset_Life_Years_F, Integer.valueOf(A_Asset_Life_Years_F));
    }

    /**
     * Get Remaining Amt.
     *
     * @return Remaining Amt
     */
    public BigDecimal getA_Asset_Remaining() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_A_Asset_Remaining);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Remaining Amt.
     *
     * @param A_Asset_Remaining Remaining Amt
     */
    public void setA_Asset_Remaining(BigDecimal A_Asset_Remaining) {
        set_ValueNoCheck(COLUMNNAME_A_Asset_Remaining, A_Asset_Remaining);
    }

    /**
     * Get Remaining Amt (fiscal).
     *
     * @return Remaining Amt (fiscal)
     */
    public BigDecimal getA_Asset_Remaining_F() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_A_Asset_Remaining_F);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Remaining Amt (fiscal).
     *
     * @param A_Asset_Remaining_F Remaining Amt (fiscal)
     */
    public void setA_Asset_Remaining_F(BigDecimal A_Asset_Remaining_F) {
        set_ValueNoCheck(COLUMNNAME_A_Asset_Remaining_F, A_Asset_Remaining_F);
    }

    /**
     * Get Current Period.
     *
     * @return Current Period
     */
    public int getA_Current_Period() {
        Integer ii = (Integer) get_Value(COLUMNNAME_A_Current_Period);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Current Period.
     *
     * @param A_Current_Period Current Period
     */
    public void setA_Current_Period(int A_Current_Period) {
        set_Value(COLUMNNAME_A_Current_Period, Integer.valueOf(A_Current_Period));
    }

    /**
     * Get A_Depreciation_Workfile_ID.
     *
     * @return A_Depreciation_Workfile_ID
     */
    public int getA_Depreciation_Workfile_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_Workfile_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Asset Funding Mode.
     *
     * @return Asset Funding Mode
     */
    public int getA_FundingMode_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_A_FundingMode_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Life Periods.
     *
     * @return Life Periods
     */
    public int getA_Life_Period() {
        Integer ii = (Integer) get_Value(COLUMNNAME_A_Life_Period);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Life Periods.
     *
     * @param A_Life_Period Life Periods
     */
    public void setA_Life_Period(int A_Life_Period) {
        set_Value(COLUMNNAME_A_Life_Period, Integer.valueOf(A_Life_Period));
    }

    /**
     * Set Life Period (fiscal).
     *
     * @param A_Life_Period_F Life Period (fiscal)
     */
    public void setA_Life_Period_F(int A_Life_Period_F) {
        set_Value(COLUMNNAME_A_Life_Period_F, Integer.valueOf(A_Life_Period_F));
    }

    /**
     * Set A_Period_Posted.
     *
     * @param A_Period_Posted A_Period_Posted
     */
    public void setA_Period_Posted(int A_Period_Posted) {
        set_Value(COLUMNNAME_A_Period_Posted, Integer.valueOf(A_Period_Posted));
    }

    /**
     * Get Current Qty.
     *
     * @return Current Qty
     */
    public BigDecimal getA_QTY_Current() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_A_QTY_Current);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Current Qty.
     *
     * @param A_QTY_Current Current Qty
     */
    public void setA_QTY_Current(BigDecimal A_QTY_Current) {
        set_Value(COLUMNNAME_A_QTY_Current, A_QTY_Current);
    }

    /**
     * Get Asset Salvage Value.
     *
     * @return Asset Salvage Value
     */
    public BigDecimal getA_Salvage_Value() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_A_Salvage_Value);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Get Asset Depreciation Date.
     *
     * @return Date of last depreciation
     */
    public Timestamp getAssetDepreciationDate() {
        return (Timestamp) get_Value(COLUMNNAME_AssetDepreciationDate);
    }

    /**
     * Get Account Date.
     *
     * @return Accounting Date
     */
    public Timestamp getDateAcct() {
        return (Timestamp) get_Value(COLUMNNAME_DateAcct);
    }

    /**
     * Set Account Date.
     *
     * @param DateAcct Accounting Date
     */
    public void setDateAcct(Timestamp DateAcct) {
        set_Value(COLUMNNAME_DateAcct, DateAcct);
    }

    /**
     * Set Depreciate.
     *
     * @param IsDepreciated The asset will be depreciated
     */
    public void setIsDepreciated(boolean IsDepreciated) {
        set_Value(COLUMNNAME_IsDepreciated, Boolean.valueOf(IsDepreciated));
    }

    /**
     * Get Depreciate.
     *
     * @return The asset will be depreciated
     */
    public boolean isDepreciated() {
        Object oo = get_Value(COLUMNNAME_IsDepreciated);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get PostingType.
     *
     * @return The type of posted amount for the transaction
     */
    public String getPostingType() {
        return (String) get_Value(COLUMNNAME_PostingType);
    }

    /**
     * Set PostingType.
     *
     * @param PostingType The type of posted amount for the transaction
     */
    public void setPostingType(String PostingType) {

        set_Value(COLUMNNAME_PostingType, PostingType);
    }

    /**
     * Get Usable Life - Months.
     *
     * @return Months of the usable life of the asset
     */
    public int getUseLifeMonths() {
        Integer ii = (Integer) get_Value(COLUMNNAME_UseLifeMonths);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Usable Life - Months.
     *
     * @param UseLifeMonths Months of the usable life of the asset
     */
    public void setUseLifeMonths(int UseLifeMonths) {
        set_Value(COLUMNNAME_UseLifeMonths, Integer.valueOf(UseLifeMonths));
    }

    /**
     * Get Use Life - Months (fiscal).
     *
     * @return Use Life - Months (fiscal)
     */
    public int getUseLifeMonths_F() {
        Integer ii = (Integer) get_Value(COLUMNNAME_UseLifeMonths_F);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Use Life - Months (fiscal).
     *
     * @param UseLifeMonths_F Use Life - Months (fiscal)
     */
    public void setUseLifeMonths_F(int UseLifeMonths_F) {
        set_Value(COLUMNNAME_UseLifeMonths_F, Integer.valueOf(UseLifeMonths_F));
    }

    /**
     * Get Usable Life - Years.
     *
     * @return Years of the usable life of the asset
     */
    public int getUseLifeYears() {
        Integer ii = (Integer) get_Value(COLUMNNAME_UseLifeYears);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Usable Life - Years.
     *
     * @param UseLifeYears Years of the usable life of the asset
     */
    public void setUseLifeYears(int UseLifeYears) {
        set_Value(COLUMNNAME_UseLifeYears, Integer.valueOf(UseLifeYears));
    }

    /**
     * Get Use Life - Years (fiscal).
     *
     * @return Use Life - Years (fiscal)
     */
    public int getUseLifeYears_F() {
        Integer ii = (Integer) get_Value(COLUMNNAME_UseLifeYears_F);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Use Life - Years (fiscal).
     *
     * @param UseLifeYears_F Use Life - Years (fiscal)
     */
    public void setUseLifeYears_F(int UseLifeYears_F) {
        set_Value(COLUMNNAME_UseLifeYears_F, Integer.valueOf(UseLifeYears_F));
    }

    @Override
    public int getTableId() {
        return I_A_Depreciation_Workfile.Table_ID;
    }
}
