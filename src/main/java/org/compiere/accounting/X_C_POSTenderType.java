package org.compiere.accounting;

import org.compiere.model.I_C_POSTenderType;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_POSTenderType
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_POSTenderType extends BasePONameValue implements I_C_POSTenderType, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_POSTenderType(Properties ctx, int C_POSTenderType_ID, String trxName) {
    super(ctx, C_POSTenderType_ID, trxName);
  }

  /** Load Constructor */
  public X_C_POSTenderType(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_POSTenderType[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set POS Tender Type.
   *
   * @param C_POSTenderType_ID POS Tender Type
   */
  public void setC_POSTenderType_ID(int C_POSTenderType_ID) {
    if (C_POSTenderType_ID < 1) set_ValueNoCheck(COLUMNNAME_C_POSTenderType_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_POSTenderType_ID, Integer.valueOf(C_POSTenderType_ID));
  }

  /**
   * Get POS Tender Type.
   *
   * @return POS Tender Type
   */
  public int getC_POSTenderType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_POSTenderType_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_POSTenderType_UU.
   *
   * @param C_POSTenderType_UU C_POSTenderType_UU
   */
  public void setC_POSTenderType_UU(String C_POSTenderType_UU) {
    set_Value(COLUMNNAME_C_POSTenderType_UU, C_POSTenderType_UU);
  }

  /**
   * Get C_POSTenderType_UU.
   *
   * @return C_POSTenderType_UU
   */
  public String getC_POSTenderType_UU() {
    return (String) get_Value(COLUMNNAME_C_POSTenderType_UU);
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
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
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
   * Get Comment/Help.
   *
   * @return Comment or Hint
   */
  public String getHelp() {
    return (String) get_Value(COLUMNNAME_Help);
  }

  /**
   * Set Guarantee.
   *
   * @param IsGuarantee Guarantee for a Credit
   */
  public void setIsGuarantee(boolean IsGuarantee) {
    set_Value(COLUMNNAME_IsGuarantee, Boolean.valueOf(IsGuarantee));
  }

  /**
   * Get Guarantee.
   *
   * @return Guarantee for a Credit
   */
  public boolean isGuarantee() {
    Object oo = get_Value(COLUMNNAME_IsGuarantee);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Post Dated.
   *
   * @param IsPostDated Post Dated
   */
  public void setIsPostDated(boolean IsPostDated) {
    set_Value(COLUMNNAME_IsPostDated, Boolean.valueOf(IsPostDated));
  }

  /**
   * Get Post Dated.
   *
   * @return Post Dated
   */
  public boolean isPostDated() {
    Object oo = get_Value(COLUMNNAME_IsPostDated);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /** TenderType AD_Reference_ID=214 */
  public static final int TENDERTYPE_AD_Reference_ID = 214;
  /** Credit Card = C */
  public static final String TENDERTYPE_CreditCard = "C";
  /** Check = K */
  public static final String TENDERTYPE_Check = "K";
  /** Direct Deposit = A */
  public static final String TENDERTYPE_DirectDeposit = "A";
  /** Direct Debit = D */
  public static final String TENDERTYPE_DirectDebit = "D";
  /** Account = T */
  public static final String TENDERTYPE_Account = "T";
  /** Cash = X */
  public static final String TENDERTYPE_Cash = "X";
  /**
   * Set Tender type.
   *
   * @param TenderType Method of Payment
   */
  public void setTenderType(String TenderType) {

    set_Value(COLUMNNAME_TenderType, TenderType);
  }

  /**
   * Get Tender type.
   *
   * @return Method of Payment
   */
  public String getTenderType() {
    return (String) get_Value(COLUMNNAME_TenderType);
  }
}
