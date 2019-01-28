package org.compiere.invoicing;

import org.compiere.model.I_A_Asset_Group_Acct;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for A_Asset_Group_Acct
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Asset_Group_Acct extends PO implements I_A_Asset_Group_Acct, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_A_Asset_Group_Acct(Properties ctx, int A_Asset_Group_Acct_ID, String trxName) {
    super(ctx, A_Asset_Group_Acct_ID, trxName);
    /**
     * if (A_Asset_Group_Acct_ID == 0) { setA_Accumdepreciation_Acct (0); setA_Asset_Acct (0);
     * setA_Asset_Group_Acct_ID (0); setA_Asset_Group_ID (0); setA_Depreciation_Acct (0);
     * setA_Depreciation_F_ID (0); setA_Depreciation_ID (0); setA_Disposal_Loss_Acct (0);
     * setA_Disposal_Revenue_Acct (0); setA_Split_Percent (Env.ZERO); // 1 setC_AcctSchema_ID (0);
     * setPostingType (null); // 'A' setUseLifeMonths_F (0); // 0 setUseLifeYears_F (0); // 0 }
     */
  }

  /** Load Constructor */
  public X_A_Asset_Group_Acct(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 3 - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  @Override
  public int getTableId() {
    return Table_ID;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_A_Asset_Group_Acct[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Accumulated Depreciation Account.
   *
   * @return Accumulated Depreciation Account
   */
  public int getA_Accumdepreciation_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Accumdepreciation_Acct);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Asset Acct.
   *
   * @return Asset Acct
   */
  public int getA_Asset_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_Acct);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Asset Group Accounting.
   *
   * @return Asset Group Accounting
   */
  public int getA_Asset_Group_Acct_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_Group_Acct_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Asset Group.
   *
   * @param A_Asset_Group_ID Group of Assets
   */
  public void setA_Asset_Group_ID(int A_Asset_Group_ID) {
    if (A_Asset_Group_ID < 1) set_ValueNoCheck(COLUMNNAME_A_Asset_Group_ID, null);
    else set_ValueNoCheck(COLUMNNAME_A_Asset_Group_ID, Integer.valueOf(A_Asset_Group_ID));
  }

  /**
   * Get Asset Group.
   *
   * @return Group of Assets
   */
  public int getA_Asset_Group_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_Group_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Spread Type.
   *
   * @return Spread Type
   */
  public int getA_Asset_Spread_Type() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_Spread_Type);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Depreciation Account.
   *
   * @return Depreciation Account
   */
  public int getA_Depreciation_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_Acct);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Calculation Type.
   *
   * @return Calculation Type
   */
  public int getA_Depreciation_Calc_Type() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_Calc_Type);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Depreciation Convention (fiscal).
   *
   * @return Depreciation Convention (fiscal)
   */
  public int getA_Depreciation_Conv_F_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_Conv_F_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Convention Type.
   *
   * @return Convention Type
   */
  public int getA_Depreciation_Conv_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_Conv_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Depreciation (fiscal).
   *
   * @return Depreciation (fiscal)
   */
  public int getA_Depreciation_F_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_F_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Depreciation.
   *
   * @return Depreciation
   */
  public int getA_Depreciation_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Depreciation Method (fiscal).
   *
   * @return Depreciation Method (fiscal)
   */
  public int getA_Depreciation_Method_F_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_Method_F_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Depreciation Method.
   *
   * @return Depreciation Method
   */
  public int getA_Depreciation_Method_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_Method_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get A_Depreciation_Table_Header_ID.
   *
   * @return A_Depreciation_Table_Header_ID
   */
  public int getA_Depreciation_Table_Header_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_Table_Header_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Disposal Gain.
   *
   * @return Disposal Gain
   */
  public int getA_Disposal_Gain() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Disposal_Gain);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Disposal Gain Acct.
   *
   * @return Disposal Gain Acct
   */
  public int getA_Disposal_Gain_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Disposal_Gain_Acct);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Loss on Disposal.
   *
   * @return Loss on Disposal
   */
  public int getA_Disposal_Loss() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Disposal_Loss);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Disposal Loss Acct.
   *
   * @return Disposal Loss Acct
   */
  public int getA_Disposal_Loss_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Disposal_Loss_Acct);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Disposal Revenue.
   *
   * @return Disposal Revenue
   */
  public int getA_Disposal_Revenue() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Disposal_Revenue);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Disposal Revenue Acct.
   *
   * @return Disposal Revenue Acct
   */
  public int getA_Disposal_Revenue_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Disposal_Revenue_Acct);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Revaluation Accumulated Depreciation Offset for Current Year.
   *
   * @return Revaluation Accumulated Depreciation Offset for Current Year
   */
  public int getA_Reval_Accumdep_Offset_Cur() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Reval_Accumdep_Offset_Cur);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Revaluation Accumulated Depreciation Offset for Prior Year.
   *
   * @return Revaluation Accumulated Depreciation Offset for Prior Year
   */
  public int getA_Reval_Accumdep_Offset_Prior() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Reval_Accumdep_Offset_Prior);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get A_Reval_Accumdep_Offset_Cur.
   *
   * @return A_Reval_Accumdep_Offset_Cur
   */
  public int getA_Reval_Adep_Offset_Cur_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Reval_Adep_Offset_Cur_Acct);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get A_Reval_Accumdep_Offset_Prior.
   *
   * @return A_Reval_Accumdep_Offset_Prior
   */
  public int getA_Reval_Adep_Offset_Prior_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Reval_Adep_Offset_Prior_Acct);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Revaluation Cost Offset for Current Year.
   *
   * @return Revaluation Cost Offset for Current Year
   */
  public int getA_Reval_Cost_Offset() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Reval_Cost_Offset);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Reval Cost Offset Acct.
   *
   * @return Reval Cost Offset Acct
   */
  public int getA_Reval_Cost_Offset_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Reval_Cost_Offset_Acct);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Revaluation Cost Offset for Prior Year.
   *
   * @return Revaluation Cost Offset for Prior Year
   */
  public int getA_Reval_Cost_Offset_Prior() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Reval_Cost_Offset_Prior);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Reval Cost Offset Prior Acct.
   *
   * @return Reval Cost Offset Prior Acct
   */
  public int getA_Reval_Cost_Offset_Prior_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Reval_Cost_Offset_Prior_Acct);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Revaluation Expense Offs.
   *
   * @return Revaluation Expense Offs
   */
  public int getA_Reval_Depexp_Offset() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Reval_Depexp_Offset);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Reval Depexp Offset Acct.
   *
   * @return Reval Depexp Offset Acct
   */
  public int getA_Reval_Depexp_Offset_Acct() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Reval_Depexp_Offset_Acct);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Accounting Schema.
   *
   * @return Rules for accounting
   */
  public int getC_AcctSchema_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_AcctSchema_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get ConventionType.
   *
   * @return ConventionType
   */
  public int getConventionType() {
    Integer ii = (Integer) get_Value(COLUMNNAME_ConventionType);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get DepreciationType.
   *
   * @return DepreciationType
   */
  public int getDepreciationType() {
    Integer ii = (Integer) get_Value(COLUMNNAME_DepreciationType);
    if (ii == null) return 0;
    return ii;
  }

}
