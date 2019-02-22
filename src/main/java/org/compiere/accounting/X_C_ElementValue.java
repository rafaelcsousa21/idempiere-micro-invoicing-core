package org.compiere.accounting;

import org.compiere.model.I_C_ElementValue;
import org.compiere.orm.BasePONameValue;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_ElementValue
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ElementValue extends BasePONameValue implements I_C_ElementValue, I_Persistent {

    /**
     * Natural = N
     */
    public static final String ACCOUNTSIGN_Natural = "N";
    /**
     * Asset = A
     */
    public static final String ACCOUNTTYPE_Asset = "A";
    /**
     * Liability = L
     */
    public static final String ACCOUNTTYPE_Liability = "L";
    /**
     * Expense = E
     */
    public static final String ACCOUNTTYPE_Expense = "E";
    /**
     * Owner's Equity = O
     */
    public static final String ACCOUNTTYPE_OwnerSEquity = "O";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_ElementValue(Properties ctx, int C_ElementValue_ID) {
        super(ctx, C_ElementValue_ID);
    }

    /**
     * Load Constructor
     */
    public X_C_ElementValue(Properties ctx, ResultSet rs) {
        super(ctx, rs);
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

    /**
     * Set Account Sign.
     *
     * @param AccountSign Indicates the Natural Sign of the Account as a Debit or Credit
     */
    public void setAccountSign(String AccountSign) {

        set_Value(COLUMNNAME_AccountSign, AccountSign);
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
     * Set Account Type.
     *
     * @param AccountType Indicates the type of account
     */
    public void setAccountType(String AccountType) {

        set_Value(COLUMNNAME_AccountType, AccountType);
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
                        .getPO(getC_Element_ID());
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
     * Set Element.
     *
     * @param C_Element_ID Accounting Element
     */
    public void setC_Element_ID(int C_Element_ID) {
        if (C_Element_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Element_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_Element_ID, C_Element_ID);
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
     * Set Description.
     *
     * @param Description Optional short description of the record
     */
    public void setDescription(String Description) {
        set_Value(COLUMNNAME_Description, Description);
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
     * Set Document Controlled.
     *
     * @param IsDocControlled Control account - If an account is controlled by a document, you cannot
     *                        post manually to it
     */
    public void setIsDocControlled(boolean IsDocControlled) {
        set_Value(COLUMNNAME_IsDocControlled, IsDocControlled);
    }

    /**
     * Get Document Controlled.
     *
     * @return Control account - If an account is controlled by a document, you cannot post manually
     * to it
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
     *                          currency
     */
    public void setIsForeignCurrency(boolean IsForeignCurrency) {
        set_Value(COLUMNNAME_IsForeignCurrency, IsForeignCurrency);
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
     * Set Post Actual.
     *
     * @param PostActual Actual Values can be posted
     */
    public void setPostActual(boolean PostActual) {
        set_Value(COLUMNNAME_PostActual, PostActual);
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
     * Set Post Budget.
     *
     * @param PostBudget Budget values can be posted
     */
    public void setPostBudget(boolean PostBudget) {
        set_Value(COLUMNNAME_PostBudget, PostBudget);
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
     * Set Post Statistical.
     *
     * @param PostStatistical Post statistical quantities to this account?
     */
    public void setPostStatistical(boolean PostStatistical) {
        set_Value(COLUMNNAME_PostStatistical, PostStatistical);
    }

    @Override
    public int getTableId() {
        return I_C_ElementValue.Table_ID;
    }
}
