package org.compiere.invoicing;

import org.compiere.model.I_I_FixedAsset;
import org.compiere.orm.BasePOName;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for I_FixedAsset
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_I_FixedAsset extends BasePOName implements I_I_FixedAsset, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_I_FixedAsset(Properties ctx, int I_FixedAsset_ID, String trxName) {
    super(ctx, I_FixedAsset_ID, trxName);
    /**
     * if (I_FixedAsset_ID == 0) { setA_Asset_Cost (Env.ZERO); // 0 setA_Remaining_Period (0); // 0
     * setAssetPeriodDepreciationAmt (Env.ZERO); // 0 setDocAction (null); // 'CO' setI_IsImported
     * (false); // 'N' }
     */
  }

  /** Load Constructor */
  public X_I_FixedAsset(Properties ctx, ResultSet rs, String trxName) {
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

  public String toString() {
    StringBuffer sb = new StringBuffer("X_I_FixedAsset[").append(getId()).append("]");
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
   * Get Asset class.
   *
   * @return Asset class
   */
  public int getA_Asset_Class_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_Class_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set Asset Group.
   *
   * @param A_Asset_Group_ID Group of Assets
   */
  public void setA_Asset_Group_ID(int A_Asset_Group_ID) {
    if (A_Asset_Group_ID < 1) set_Value(COLUMNNAME_A_Asset_Group_ID, null);
    else set_Value(COLUMNNAME_A_Asset_Group_ID, Integer.valueOf(A_Asset_Group_ID));
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
   * Get Asset Type.
   *
   * @return Asset Type
   */
  public int getA_Asset_Type_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_Type_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Remaining Period.
   *
   * @return Remaining Period
   */
  public int getA_Remaining_Period() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Remaining_Period);
    if (ii == null) return 0;
    return ii;
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
   * Get In Service Date.
   *
   * @return Date when Asset was put into service
   */
  public Timestamp getAssetServiceDate() {
    return (Timestamp) get_Value(COLUMNNAME_AssetServiceDate);
  }

    /**
   * Get BPartner (Agent).
   *
   * @return Business Partner (Agent or Sales Rep)
   */
  public int getC_BPartnerSR_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BPartnerSR_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get City.
   *
   * @return City
   */
  public int getC_City_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_City_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get UOM.
   *
   * @return Unit of Measure
   */
  public int getC_UOM_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_UOM_ID);
    if (ii == null) return 0;
    return ii;
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
   * Get Account Date.
   *
   * @return Accounting Date
   */
  public Timestamp getDateAcct() {
    return (Timestamp) get_Value(COLUMNNAME_DateAcct);
  }

    /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
  }

  /** DocAction AD_Reference_ID=135 */
  public static final int DOCACTION_AD_Reference_ID = 135;
  /** Complete = CO */
  public static final String DOCACTION_Complete = "CO";
  /** Approve = AP */
  public static final String DOCACTION_Approve = "AP";
  /** Reject = RJ */
  public static final String DOCACTION_Reject = "RJ";
  /** Post = PO */
  public static final String DOCACTION_Post = "PO";
  /** Void = VO */
  public static final String DOCACTION_Void = "VO";
  /** Close = CL */
  public static final String DOCACTION_Close = "CL";
  /** Reverse - Correct = RC */
  public static final String DOCACTION_Reverse_Correct = "RC";
  /** Reverse - Accrual = RA */
  public static final String DOCACTION_Reverse_Accrual = "RA";
  /** Invalidate = IN */
  public static final String DOCACTION_Invalidate = "IN";
  /** Re-activate = RE */
  public static final String DOCACTION_Re_Activate = "RE";
  /** <None> = -- */
  public static final String DOCACTION_None = "--";
  /** Prepare = PR */
  public static final String DOCACTION_Prepare = "PR";
  /** Unlock = XL */
  public static final String DOCACTION_Unlock = "XL";
  /** Wait Complete = WC */
  public static final String DOCACTION_WaitComplete = "WC";

    /**
   * Set Import Error Message.
   *
   * @param I_ErrorMsg Messages generated from import process
   */
  public void setI_ErrorMsg(String I_ErrorMsg) {
    set_Value(COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
  }

    /**
   * Get Inventory No.
   *
   * @return Inventory No
   */
  public String getInventoryNo() {
    return (String) get_Value(COLUMNNAME_InventoryNo);
  }

    /**
   * Get Locator.
   *
   * @return Warehouse Locator
   */
  public int getM_Locator_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Locator_ID);
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
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
  }

  /**
   * Get Processed.
   *
   * @return The document has been processed
   */
  public boolean isProcessed() {
    Object oo = get_Value(COLUMNNAME_Processed);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Set Product Key.
   *
   * @param ProductValue Key of the Product
   */
  public void setProductValue(String ProductValue) {
    set_Value(COLUMNNAME_ProductValue, ProductValue);
  }

  /**
   * Get Product Key.
   *
   * @return Key of the Product
   */
  public String getProductValue() {
    return (String) get_Value(COLUMNNAME_ProductValue);
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
   * Get Use Life - Months (fiscal).
   *
   * @return Use Life - Months (fiscal)
   */
  public int getUseLifeMonths_F() {
    Integer ii = (Integer) get_Value(COLUMNNAME_UseLifeMonths_F);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_I_FixedAsset.Table_ID;
  }
}
