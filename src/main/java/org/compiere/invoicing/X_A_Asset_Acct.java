package org.compiere.invoicing;

import org.compiere.model.I_A_Asset_Acct;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for A_Asset_Acct
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Asset_Acct extends PO implements I_A_Asset_Acct, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_A_Asset_Acct(Properties ctx, int A_Asset_Acct_ID) {
    super(ctx, A_Asset_Acct_ID);
    /**
     * if (A_Asset_Acct_ID == 0) { setA_Accumdepreciation_Acct (0); setA_Asset_Acct (0);
     * setA_Asset_Acct_ID (0); setA_Asset_ID (0); setA_Depreciation_Acct (0); setA_Depreciation_F_ID
     * (0); setA_Depreciation_ID (0); setA_Disposal_Loss_Acct (0); setA_Disposal_Revenue_Acct (0);
     * setA_Period_End (0); setA_Period_Start (0); setA_Split_Percent (Env.ZERO); // 1
     * setC_AcctSchema_ID (0); setPostingType (null); // 'A' }
     */
  }

  /** Load Constructor */
  public X_A_Asset_Acct(Properties ctx, ResultSet rs) {
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
    StringBuffer sb = new StringBuffer("X_A_Asset_Acct[").append(getId()).append("]");
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
   * Get A_Asset_Acct_ID.
   *
   * @return A_Asset_Acct_ID
   */
  public int getA_Asset_Acct_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_Acct_ID);
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
   * Get A_Asset_Spread_ID.
   *
   * @return A_Asset_Spread_ID
   */
  public int getA_Asset_Spread_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_Spread_ID);
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
   * Set Depreciation (fiscal).
   *
   * @param A_Depreciation_F_ID Depreciation (fiscal)
   */
  public void setA_Depreciation_F_ID(int A_Depreciation_F_ID) {
    if (A_Depreciation_F_ID < 1) set_Value(COLUMNNAME_A_Depreciation_F_ID, null);
    else set_Value(COLUMNNAME_A_Depreciation_F_ID, Integer.valueOf(A_Depreciation_F_ID));
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
   * Set Depreciation.
   *
   * @param A_Depreciation_ID Depreciation
   */
  public void setA_Depreciation_ID(int A_Depreciation_ID) {
    if (A_Depreciation_ID < 1) set_Value(COLUMNNAME_A_Depreciation_ID, null);
    else set_Value(COLUMNNAME_A_Depreciation_ID, Integer.valueOf(A_Depreciation_ID));
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
   * Get Variable Percent.
   *
   * @return Variable Percent
   */
  public BigDecimal getA_Depreciation_Variable_Perc() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_A_Depreciation_Variable_Perc);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Get Variable Percent (fiscal).
   *
   * @return Variable Percent (fiscal)
   */
  public BigDecimal getA_Depreciation_Variable_Perc_F() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_A_Depreciation_Variable_Perc_F);
    if (bd == null) return Env.ZERO;
    return bd;
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
   * Set A_Period_End.
   *
   * @param A_Period_End A_Period_End
   */
  public void setA_Period_End(int A_Period_End) {
    set_Value(COLUMNNAME_A_Period_End, Integer.valueOf(A_Period_End));
  }

    /**
   * Set A_Period_Start.
   *
   * @param A_Period_Start A_Period_Start
   */
  public void setA_Period_Start(int A_Period_Start) {
    set_Value(COLUMNNAME_A_Period_Start, Integer.valueOf(A_Period_Start));
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
   * Set Asset Salvage Value.
   *
   * @param A_Salvage_Value Asset Salvage Value
   */
  public void setA_Salvage_Value(BigDecimal A_Salvage_Value) {
    set_Value(COLUMNNAME_A_Salvage_Value, A_Salvage_Value);
  }

    public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException {
    return (org.compiere.model.I_C_AcctSchema)
        MTable.get(getCtx(), org.compiere.model.I_C_AcctSchema.Table_Name)
            .getPO(getC_AcctSchema_ID());
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
   * Get PostingType.
   *
   * @return The type of posted amount for the transaction
   */
  public String getPostingType() {
    return (String) get_Value(COLUMNNAME_PostingType);
  }

    /**
   * Set Valid from.
   *
   * @param ValidFrom Valid from including this date (first day)
   */
  public void setValidFrom(Timestamp ValidFrom) {
    set_ValueNoCheck(COLUMNNAME_ValidFrom, ValidFrom);
  }

  /**
   * Get Valid from.
   *
   * @return Valid from including this date (first day)
   */
  public Timestamp getValidFrom() {
    return (Timestamp) get_Value(COLUMNNAME_ValidFrom);
  }

  @Override
  public int getTableId() {
    return Table_ID;
  }
}
