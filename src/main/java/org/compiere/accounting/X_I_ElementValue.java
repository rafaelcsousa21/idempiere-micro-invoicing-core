package org.compiere.accounting;

import org.compiere.model.I_I_ElementValue;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for I_ElementValue
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_I_ElementValue extends BasePONameValue implements I_I_ElementValue, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_I_ElementValue(Properties ctx, int I_ElementValue_ID, String trxName) {
    super(ctx, I_ElementValue_ID, trxName);
    /** if (I_ElementValue_ID == 0) { setI_ElementValue_ID (0); setI_IsImported (false); } */
  }

  /** Load Constructor */
  public X_I_ElementValue(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
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
    StringBuffer sb = new StringBuffer("X_I_ElementValue[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Account Sign.
   *
   * @return Indicates the Natural Sign of the Account as a Debit or Credit
   */
  public String getAccountSign() {
    return (String) get_Value(COLUMNNAME_AccountSign);
  }

    /**
   * Get Account Type.
   *
   * @return Indicates the type of account
   */
  public String getAccountType() {
    return (String) get_Value(COLUMNNAME_AccountType);
  }

    /**
   * Get Column.
   *
   * @return Column in the table
   */
  public int getAD_Column_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Column_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Element.
   *
   * @return Accounting Element
   */
  public int getC_Element_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Element_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Account Element.
   *
   * @param C_ElementValue_ID Account Element
   */
  public void setC_ElementValue_ID(int C_ElementValue_ID) {
    if (C_ElementValue_ID < 1) set_Value(COLUMNNAME_C_ElementValue_ID, null);
    else set_Value(COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
  }

  /**
   * Get Account Element.
   *
   * @return Account Element
   */
  public int getC_ElementValue_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ElementValue_ID);
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
   * Get Import Account.
   *
   * @return Import Account Value
   */
  public int getI_ElementValue_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_I_ElementValue_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Imported.
   *
   * @param I_IsImported Has this import been processed
   */
  public void setI_IsImported(boolean I_IsImported) {
    set_Value(COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
  }

    /**
   * Get Document Controlled.
   *
   * @return Control account - If an account is controlled by a document, you cannot post manually
   *     to it
   */
  public boolean isDocControlled() {
    Object oo = get_Value(COLUMNNAME_IsDocControlled);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Summary Level.
   *
   * @return This is a summary entity
   */
  public boolean isSummary() {
    Object oo = get_Value(COLUMNNAME_IsSummary);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Parent Account.
   *
   * @return The parent (summary) account
   */
  public int getParentElementValue_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_ParentElementValue_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Post Actual.
   *
   * @return Actual Values can be posted
   */
  public boolean isPostActual() {
    Object oo = get_Value(COLUMNNAME_PostActual);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Post Budget.
   *
   * @return Budget values can be posted
   */
  public boolean isPostBudget() {
    Object oo = get_Value(COLUMNNAME_PostBudget);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Post Encumbrance.
   *
   * @return Post commitments to this account
   */
  public boolean isPostEncumbrance() {
    Object oo = get_Value(COLUMNNAME_PostEncumbrance);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Post Statistical.
   *
   * @return Post statistical quantities to this account?
   */
  public boolean isPostStatistical() {
    Object oo = get_Value(COLUMNNAME_PostStatistical);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    @Override
  public int getTableId() {
    return I_I_ElementValue.Table_ID;
  }
}
