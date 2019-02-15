package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_AcctSchema
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_AcctSchema extends BasePOName implements I_C_AcctSchema, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_AcctSchema(Properties ctx, int C_AcctSchema_ID) {
    super(ctx, C_AcctSchema_ID);
  }

  /** Load Constructor */
  public X_C_AcctSchema(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  }
  public X_C_AcctSchema(Properties ctx, Row row)  {
    super(ctx, row);
  }


  /**
   * AccessLevel
   *
   * @return 2 - Client
   */
  protected int getAccessLevel() {
    return I_C_AcctSchema.accessLevel.intValue();
  }

  public String toString() {
    return "X_C_AcctSchema[" + getId() + "]";
  }

  /**
   * Set Only Organization.
   *
   * @param AD_OrgOnly_ID Create posting entries only for this organization
   */
  public void setAD_OrgOnly_ID(int AD_OrgOnly_ID) {
    if (AD_OrgOnly_ID < 1) set_Value(I_C_AcctSchema.COLUMNNAME_AD_OrgOnly_ID, null);
    else set_Value(I_C_AcctSchema.COLUMNNAME_AD_OrgOnly_ID, AD_OrgOnly_ID);
  }

  /**
   * Get Only Organization.
   *
   * @return Create posting entries only for this organization
   */
  public int getAD_OrgOnly_ID() {
    Integer ii = (Integer) get_Value(I_C_AcctSchema.COLUMNNAME_AD_OrgOnly_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Automatic Period Control.
   *
   * @param AutoPeriodControl If selected, the periods are automatically opened and closed
   */
  public void setAutoPeriodControl(boolean AutoPeriodControl) {
    set_Value(I_C_AcctSchema.COLUMNNAME_AutoPeriodControl, AutoPeriodControl);
  }

  /**
   * Get Automatic Period Control.
   *
   * @return If selected, the periods are automatically opened and closed
   */
  public boolean isAutoPeriodControl() {
    Object oo = get_Value(I_C_AcctSchema.COLUMNNAME_AutoPeriodControl);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Accounting Schema.
   *
   * @return Rules for accounting
   */
  public int getC_AcctSchema_ID() {
    Integer ii = (Integer) get_Value(I_C_AcctSchema.COLUMNNAME_C_AcctSchema_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Currency.
   *
   * @param C_Currency_ID The Currency for this record
   */
  public void setC_Currency_ID(int C_Currency_ID) {
    if (C_Currency_ID < 1) set_Value(I_C_AcctSchema.COLUMNNAME_C_Currency_ID, null);
    else set_Value(I_C_AcctSchema.COLUMNNAME_C_Currency_ID, C_Currency_ID);
  }

  /**
   * Get Currency.
   *
   * @return The Currency for this record
   */
  public int getC_Currency_ID() {
    Integer ii = (Integer) get_Value(I_C_AcctSchema.COLUMNNAME_C_Currency_ID);
    if (ii == null) return 0;
    return ii;
  }

    /** PO Commitment only = C */
  public static final String COMMITMENTTYPE_POCommitmentOnly = "C";
  /** PO Commitment & Reservation = B */
  public static final String COMMITMENTTYPE_POCommitmentReservation = "B";
  /** None = N */
  public static final String COMMITMENTTYPE_None = "N";
  /** PO/SO Commitment & Reservation = A */
  public static final String COMMITMENTTYPE_POSOCommitmentReservation = "A";
  /** SO Commitment only = S */
  public static final String COMMITMENTTYPE_SOCommitmentOnly = "S";
  /** PO/SO Commitment = O */
  public static final String COMMITMENTTYPE_POSOCommitment = "O";
  /**
   * Set Commitment Type.
   *
   * @param CommitmentType Create Commitment and/or Reservations for Budget Control
   */
  public void setCommitmentType(String CommitmentType) {

    set_Value(I_C_AcctSchema.COLUMNNAME_CommitmentType, CommitmentType);
  }

  /**
   * Get Commitment Type.
   *
   * @return Create Commitment and/or Reservations for Budget Control
   */
  public String getCommitmentType() {
    return (String) get_Value(I_C_AcctSchema.COLUMNNAME_CommitmentType);
  }

    /** Client = C */
  public static final String COSTINGLEVEL_Client = "C";
  /** Organization = O */
  public static final String COSTINGLEVEL_Organization = "O";
  /** Batch/Lot = B */
  public static final String COSTINGLEVEL_BatchLot = "B";
  /**
   * Set Costing Level.
   *
   * @param CostingLevel The lowest level to accumulate Costing Information
   */
  public void setCostingLevel(String CostingLevel) {

    set_Value(I_C_AcctSchema.COLUMNNAME_CostingLevel, CostingLevel);
  }

  /**
   * Get Costing Level.
   *
   * @return The lowest level to accumulate Costing Information
   */
  public String getCostingLevel() {
    return (String) get_Value(I_C_AcctSchema.COLUMNNAME_CostingLevel);
  }

    /** Standard Costing = S */
  public static final String COSTINGMETHOD_StandardCosting = "S";
  /** Average PO = A */
  public static final String COSTINGMETHOD_AveragePO = "A";
    /** Last PO Price = p */
  public static final String COSTINGMETHOD_LastPOPrice = "p";
  /** Average Invoice = I */
  public static final String COSTINGMETHOD_AverageInvoice = "I";

    /**
   * Set Costing Method.
   *
   * @param CostingMethod Indicates how Costs will be calculated
   */
  public void setCostingMethod(String CostingMethod) {

    set_Value(I_C_AcctSchema.COLUMNNAME_CostingMethod, CostingMethod);
  }

  /**
   * Get Costing Method.
   *
   * @return Indicates how Costs will be calculated
   */
  public String getCostingMethod() {
    return (String) get_Value(I_C_AcctSchema.COLUMNNAME_CostingMethod);
  }

    /**
   * Set Period.
   *
   * @param C_Period_ID Period of the Calendar
   */
  public void setC_Period_ID(int C_Period_ID) {
    if (C_Period_ID < 1) set_ValueNoCheck(I_C_AcctSchema.COLUMNNAME_C_Period_ID, null);
    else set_ValueNoCheck(I_C_AcctSchema.COLUMNNAME_C_Period_ID, C_Period_ID);
  }

  /**
   * Get Period.
   *
   * @return Period of the Calendar
   */
  public int getC_Period_ID() {
    Integer ii = (Integer) get_Value(I_C_AcctSchema.COLUMNNAME_C_Period_ID);
    if (ii == null) return 0;
    return ii;
  }

    /** International GAAP = UN */
  public static final String GAAP_InternationalGAAP = "UN";

    /**
   * Set GAAP.
   *
   * @param GAAP Generally Accepted Accounting Principles
   */
  public void setGAAP(String GAAP) {

    set_Value(I_C_AcctSchema.COLUMNNAME_GAAP, GAAP);
  }

  /**
   * Get GAAP.
   *
   * @return Generally Accepted Accounting Principles
   */
  public String getGAAP() {
    return (String) get_Value(I_C_AcctSchema.COLUMNNAME_GAAP);
  }

  /**
   * Set Use Account Alias.
   *
   * @param HasAlias Ability to select (partial) account combinations by an Alias
   */
  public void setHasAlias(boolean HasAlias) {
    set_Value(I_C_AcctSchema.COLUMNNAME_HasAlias, HasAlias);
  }

    /**
   * Set Use Account Combination Control.
   *
   * @param HasCombination Combination of account elements are checked
   */
  public void setHasCombination(boolean HasCombination) {
    set_Value(I_C_AcctSchema.COLUMNNAME_HasCombination, HasCombination);
  }

    /**
   * Set Accrual.
   *
   * @param IsAccrual Indicates if Accrual or Cash Based accounting will be used
   */
  public void setIsAccrual(boolean IsAccrual) {
    set_Value(I_C_AcctSchema.COLUMNNAME_IsAccrual, IsAccrual);
  }

  /**
   * Get Accrual.
   *
   * @return Indicates if Accrual or Cash Based accounting will be used
   */
  public boolean isAccrual() {
    Object oo = get_Value(I_C_AcctSchema.COLUMNNAME_IsAccrual);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Adjust COGS.
   *
   * @param IsAdjustCOGS Adjust Cost of Good Sold
   */
  public void setIsAdjustCOGS(boolean IsAdjustCOGS) {
    set_Value(I_C_AcctSchema.COLUMNNAME_IsAdjustCOGS, IsAdjustCOGS);
  }

    /**
   * Get Allow Negative Posting.
   *
   * @return Allow to post negative accounting values
   */
  public boolean isAllowNegativePosting() {
    Object oo = get_Value(I_C_AcctSchema.COLUMNNAME_IsAllowNegativePosting);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Correct tax for Discounts/Charges.
   *
   * @param IsDiscountCorrectsTax Correct the tax for payment discount and charges
   */
  public void setIsDiscountCorrectsTax(boolean IsDiscountCorrectsTax) {
    set_Value(I_C_AcctSchema.COLUMNNAME_IsDiscountCorrectsTax, IsDiscountCorrectsTax);
  }

  /**
   * Get Correct tax for Discounts/Charges.
   *
   * @return Correct the tax for payment discount and charges
   */
  public boolean isDiscountCorrectsTax() {
    Object oo = get_Value(I_C_AcctSchema.COLUMNNAME_IsDiscountCorrectsTax);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Explicit Cost Adjustment.
   *
   * @param IsExplicitCostAdjustment Post the cost adjustment explicitly
   */
  public void setIsExplicitCostAdjustment(boolean IsExplicitCostAdjustment) {
    set_Value(I_C_AcctSchema.COLUMNNAME_IsExplicitCostAdjustment, IsExplicitCostAdjustment);
  }

  /**
   * Get Explicit Cost Adjustment.
   *
   * @return Post the cost adjustment explicitly
   */
  public boolean isExplicitCostAdjustment() {
    Object oo = get_Value(I_C_AcctSchema.COLUMNNAME_IsExplicitCostAdjustment);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Post if Clearing Equal.
   *
   * @return This flag controls if Adempiere must post when clearing (transit) and final accounts
   *     are the same
   */
  public boolean isPostIfClearingEqual() {
    Object oo = get_Value(I_C_AcctSchema.COLUMNNAME_IsPostIfClearingEqual);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Post Services Separately.
   *
   * @param IsPostServices Differentiate between Services and Product Receivable/Payables
   */
  public void setIsPostServices(boolean IsPostServices) {
    set_Value(I_C_AcctSchema.COLUMNNAME_IsPostServices, IsPostServices);
  }

  /**
   * Get Post Services Separately.
   *
   * @return Differentiate between Services and Product Receivable/Payables
   */
  public boolean isPostServices() {
    Object oo = get_Value(I_C_AcctSchema.COLUMNNAME_IsPostServices);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Post Trade Discount.
   *
   * @param IsTradeDiscountPosted Generate postings for trade discounts
   */
  public void setIsTradeDiscountPosted(boolean IsTradeDiscountPosted) {
    set_Value(I_C_AcctSchema.COLUMNNAME_IsTradeDiscountPosted, IsTradeDiscountPosted);
  }

  /**
   * Get Post Trade Discount.
   *
   * @return Generate postings for trade discounts
   */
  public boolean isTradeDiscountPosted() {
    Object oo = get_Value(I_C_AcctSchema.COLUMNNAME_IsTradeDiscountPosted);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Set Cost Type.
   *
   * @param M_CostType_ID Type of Cost (e.g. Current, Plan, Future)
   */
  public void setM_CostType_ID(int M_CostType_ID) {
    if (M_CostType_ID < 1) set_Value(I_C_AcctSchema.COLUMNNAME_M_CostType_ID, null);
    else set_Value(I_C_AcctSchema.COLUMNNAME_M_CostType_ID, M_CostType_ID);
  }

  /**
   * Get Cost Type.
   *
   * @return Type of Cost (e.g. Current, Plan, Future)
   */
  public int getM_CostType_ID() {
    Integer ii = (Integer) get_Value(I_C_AcctSchema.COLUMNNAME_M_CostType_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Future Days.
   *
   * @param Period_OpenFuture Number of days to be able to post to a future date (based on system
   *     date)
   */
  public void setPeriod_OpenFuture(int Period_OpenFuture) {
    set_Value(I_C_AcctSchema.COLUMNNAME_Period_OpenFuture, Period_OpenFuture);
  }

  /**
   * Get Future Days.
   *
   * @return Number of days to be able to post to a future date (based on system date)
   */
  public int getPeriod_OpenFuture() {
    Integer ii = (Integer) get_Value(I_C_AcctSchema.COLUMNNAME_Period_OpenFuture);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set History Days.
   *
   * @param Period_OpenHistory Number of days to be able to post in the past (based on system date)
   */
  public void setPeriod_OpenHistory(int Period_OpenHistory) {
    set_Value(I_C_AcctSchema.COLUMNNAME_Period_OpenHistory, Period_OpenHistory);
  }

  /**
   * Get History Days.
   *
   * @return Number of days to be able to post in the past (based on system date)
   */
  public int getPeriod_OpenHistory() {
    Integer ii = (Integer) get_Value(I_C_AcctSchema.COLUMNNAME_Period_OpenHistory);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Element Separator.
   *
   * @param Separator Element Separator
   */
  public void setSeparator(String Separator) {
    set_Value(I_C_AcctSchema.COLUMNNAME_Separator, Separator);
  }

  /**
   * Get Element Separator.
   *
   * @return Element Separator
   */
  public String getSeparator() {
    return (String) get_Value(I_C_AcctSchema.COLUMNNAME_Separator);
  }

    /** None = N */
  public static final String TAXCORRECTIONTYPE_None = "N";
  /** Write-off only = W */
  public static final String TAXCORRECTIONTYPE_Write_OffOnly = "W";
  /** Discount only = D */
  public static final String TAXCORRECTIONTYPE_DiscountOnly = "D";
  /** Write-off and Discount = B */
  public static final String TAXCORRECTIONTYPE_Write_OffAndDiscount = "B";
  /**
   * Set Tax Correction.
   *
   * @param TaxCorrectionType Type of Tax Correction
   */
  public void setTaxCorrectionType(String TaxCorrectionType) {

    set_Value(I_C_AcctSchema.COLUMNNAME_TaxCorrectionType, TaxCorrectionType);
  }

  /**
   * Get Tax Correction.
   *
   * @return Type of Tax Correction
   */
  public String getTaxCorrectionType() {
    return (String) get_Value(I_C_AcctSchema.COLUMNNAME_TaxCorrectionType);
  }

  @Override
  public int getTableId() {
    return I_C_AcctSchema.Table_ID;
  }
}
