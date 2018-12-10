package org.compiere.accounting;

import org.compiere.model.I_C_ElementValue;
import org.compiere.orm.BasePONameValue;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_ElementValue
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ElementValue extends BasePONameValue implements I_C_ElementValue, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_ElementValue(Properties ctx, int C_ElementValue_ID, String trxName) {
    super(ctx, C_ElementValue_ID, trxName);
  }

  /** Load Constructor */
  public X_C_ElementValue(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 2 - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    return "X_C_ElementValue[" + getId() + "]";
  }

  /** AccountSign AD_Reference_ID=118 */
  public static final int ACCOUNTSIGN_AD_Reference_ID = 118;
  /** Natural = N */
  public static final String ACCOUNTSIGN_Natural = "N";
  /** Debit = D */
  public static final String ACCOUNTSIGN_Debit = "D";
  /** Credit = C */
  public static final String ACCOUNTSIGN_Credit = "C";
  /**
   * Set Account Sign.
   *
   * @param AccountSign Indicates the Natural Sign of the Account as a Debit or Credit
   */
  public void setAccountSign(String AccountSign) {

    set_Value(COLUMNNAME_AccountSign, AccountSign);
  }

  /**
   * Get Account Sign.
   *
   * @return Indicates the Natural Sign of the Account as a Debit or Credit
   */
  public String getAccountSign() {
    return (String) get_Value(COLUMNNAME_AccountSign);
  }

  /** AccountType AD_Reference_ID=117 */
  public static final int ACCOUNTTYPE_AD_Reference_ID = 117;
  /** Asset = A */
  public static final String ACCOUNTTYPE_Asset = "A";
  /** Liability = L */
  public static final String ACCOUNTTYPE_Liability = "L";
  /** Revenue = R */
  public static final String ACCOUNTTYPE_Revenue = "R";
  /** Expense = E */
  public static final String ACCOUNTTYPE_Expense = "E";
  /** Owner's Equity = O */
  public static final String ACCOUNTTYPE_OwnerSEquity = "O";
  /** Memo = M */
  public static final String ACCOUNTTYPE_Memo = "M";
  /**
   * Set Account Type.
   *
   * @param AccountType Indicates the type of account
   */
  public void setAccountType(String AccountType) {

    set_Value(COLUMNNAME_AccountType, AccountType);
  }

  /**
   * Get Account Type.
   *
   * @return Indicates the type of account
   */
  public String getAccountType() {
    return (String) get_Value(COLUMNNAME_AccountType);
  }

  /** BPartnerType AD_Reference_ID=200076 */
  public static final int BPARTNERTYPE_AD_Reference_ID = 200076;
  /** Customer = C */
  public static final String BPARTNERTYPE_Customer = "C";
  /** Vendor = V */
  public static final String BPARTNERTYPE_Vendor = "V";
  /** Employee = E */
  public static final String BPARTNERTYPE_Employee = "E";
  /**
   * Set Business Partner Type.
   *
   * @param BPartnerType Business Partner Type
   */
  public void setBPartnerType(String BPartnerType) {

    set_Value(COLUMNNAME_BPartnerType, BPartnerType);
  }

  /**
   * Get Business Partner Type.
   *
   * @return Business Partner Type
   */
  public String getBPartnerType() {
    return (String) get_Value(COLUMNNAME_BPartnerType);
  }

  public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException {
    return (org.compiere.model.I_C_BankAccount)
        MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
            .getPO(getC_BankAccount_ID(), get_TrxName());
  }

  /**
   * Set Bank Account.
   *
   * @param C_BankAccount_ID Account at the Bank
   */
  public void setC_BankAccount_ID(int C_BankAccount_ID) {
    if (C_BankAccount_ID < 1) set_Value(COLUMNNAME_C_BankAccount_ID, null);
    else set_Value(COLUMNNAME_C_BankAccount_ID, C_BankAccount_ID);
  }

  /**
   * Get Bank Account.
   *
   * @return Account at the Bank
   */
  public int getC_BankAccount_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BankAccount_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException {
    return (org.compiere.model.I_C_Currency)
        MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
            .getPO(getC_Currency_ID(), get_TrxName());
  }

  /**
   * Set Currency.
   *
   * @param C_Currency_ID The Currency for this record
   */
  public void setC_Currency_ID(int C_Currency_ID) {
    if (C_Currency_ID < 1) set_Value(COLUMNNAME_C_Currency_ID, null);
    else set_Value(COLUMNNAME_C_Currency_ID, C_Currency_ID);
  }

  /**
   * Get Currency.
   *
   * @return The Currency for this record
   */
  public int getC_Currency_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Currency_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_C_Element getC_Element() throws RuntimeException {
    return (org.compiere.model.I_C_Element)
        MTable.get(getCtx(), org.compiere.model.I_C_Element.Table_Name)
            .getPO(getC_Element_ID(), get_TrxName());
  }

  /**
   * Set Element.
   *
   * @param C_Element_ID Accounting Element
   */
  public void setC_Element_ID(int C_Element_ID) {
    if (C_Element_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Element_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Element_ID, C_Element_ID);
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
    if (C_ElementValue_ID < 1) set_ValueNoCheck(COLUMNNAME_C_ElementValue_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_ElementValue_ID, C_ElementValue_ID);
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
   * Set C_ElementValue_UU.
   *
   * @param C_ElementValue_UU C_ElementValue_UU
   */
  public void setC_ElementValue_UU(String C_ElementValue_UU) {
    set_Value(COLUMNNAME_C_ElementValue_UU, C_ElementValue_UU);
  }

  /**
   * Get C_ElementValue_UU.
   *
   * @return C_ElementValue_UU
   */
  public String getC_ElementValue_UU() {
    return (String) get_Value(COLUMNNAME_C_ElementValue_UU);
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
   * Set Bank Account.
   *
   * @param IsBankAccount Indicates if this is the Bank Account
   */
  public void setIsBankAccount(boolean IsBankAccount) {
    set_Value(COLUMNNAME_IsBankAccount, IsBankAccount);
  }

  /**
   * Get Bank Account.
   *
   * @return Indicates if this is the Bank Account
   */
  public boolean isBankAccount() {
    Object oo = get_Value(COLUMNNAME_IsBankAccount);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Manage Business Partners.
   *
   * @param IsDetailBPartner Manage Business Partners
   */
  public void setIsDetailBPartner(boolean IsDetailBPartner) {
    set_Value(COLUMNNAME_IsDetailBPartner, IsDetailBPartner);
  }

  /**
   * Get Manage Business Partners.
   *
   * @return Manage Business Partners
   */
  public boolean isDetailBPartner() {
    Object oo = get_Value(COLUMNNAME_IsDetailBPartner);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Manage Products.
   *
   * @param IsDetailProduct Manage Products
   */
  public void setIsDetailProduct(boolean IsDetailProduct) {
    set_Value(COLUMNNAME_IsDetailProduct, IsDetailProduct);
  }

  /**
   * Get Manage Products.
   *
   * @return Manage Products
   */
  public boolean isDetailProduct() {
    Object oo = get_Value(COLUMNNAME_IsDetailProduct);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Document Controlled.
   *
   * @param IsDocControlled Control account - If an account is controlled by a document, you cannot
   *     post manually to it
   */
  public void setIsDocControlled(boolean IsDocControlled) {
    set_Value(COLUMNNAME_IsDocControlled, IsDocControlled);
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
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Foreign Currency Account.
   *
   * @param IsForeignCurrency Balances in foreign currency accounts are held in the nominated
   *     currency
   */
  public void setIsForeignCurrency(boolean IsForeignCurrency) {
    set_Value(COLUMNNAME_IsForeignCurrency, IsForeignCurrency);
  }

  /**
   * Get Foreign Currency Account.
   *
   * @return Balances in foreign currency accounts are held in the nominated currency
   */
  public boolean isForeignCurrency() {
    Object oo = get_Value(COLUMNNAME_IsForeignCurrency);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Summary Level.
   *
   * @param IsSummary This is a summary entity
   */
  public void setIsSummary(boolean IsSummary) {
    set_Value(COLUMNNAME_IsSummary, IsSummary);
  }

  /**
   * Get Summary Level.
   *
   * @return This is a summary entity
   */
  public boolean isSummary() {
    Object oo = get_Value(COLUMNNAME_IsSummary);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Post Actual.
   *
   * @param PostActual Actual Values can be posted
   */
  public void setPostActual(boolean PostActual) {
    set_Value(COLUMNNAME_PostActual, PostActual);
  }

  /**
   * Get Post Actual.
   *
   * @return Actual Values can be posted
   */
  public boolean isPostActual() {
    Object oo = get_Value(COLUMNNAME_PostActual);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Post Budget.
   *
   * @param PostBudget Budget values can be posted
   */
  public void setPostBudget(boolean PostBudget) {
    set_Value(COLUMNNAME_PostBudget, PostBudget);
  }

  /**
   * Get Post Budget.
   *
   * @return Budget values can be posted
   */
  public boolean isPostBudget() {
    Object oo = get_Value(COLUMNNAME_PostBudget);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Post Encumbrance.
   *
   * @param PostEncumbrance Post commitments to this account
   */
  public void setPostEncumbrance(boolean PostEncumbrance) {
    set_Value(COLUMNNAME_PostEncumbrance, PostEncumbrance);
  }

  /**
   * Get Post Encumbrance.
   *
   * @return Post commitments to this account
   */
  public boolean isPostEncumbrance() {
    Object oo = get_Value(COLUMNNAME_PostEncumbrance);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Post Statistical.
   *
   * @param PostStatistical Post statistical quantities to this account?
   */
  public void setPostStatistical(boolean PostStatistical) {
    set_Value(COLUMNNAME_PostStatistical, PostStatistical);
  }

  /**
   * Get Post Statistical.
   *
   * @return Post statistical quantities to this account?
   */
  public boolean isPostStatistical() {
    Object oo = get_Value(COLUMNNAME_PostStatistical);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Valid from.
   *
   * @param ValidFrom Valid from including this date (first day)
   */
  public void setValidFrom(Timestamp ValidFrom) {
    set_Value(COLUMNNAME_ValidFrom, ValidFrom);
  }

  /**
   * Get Valid from.
   *
   * @return Valid from including this date (first day)
   */
  public Timestamp getValidFrom() {
    return (Timestamp) get_Value(COLUMNNAME_ValidFrom);
  }

  /**
   * Set Valid to.
   *
   * @param ValidTo Valid to including this date (last day)
   */
  public void setValidTo(Timestamp ValidTo) {
    set_Value(COLUMNNAME_ValidTo, ValidTo);
  }

  /**
   * Get Valid to.
   *
   * @return Valid to including this date (last day)
   */
  public Timestamp getValidTo() {
    return (Timestamp) get_Value(COLUMNNAME_ValidTo);
  }

  @Override
  public int getTableId() {
    return I_C_ElementValue.Table_ID;
  }
}
