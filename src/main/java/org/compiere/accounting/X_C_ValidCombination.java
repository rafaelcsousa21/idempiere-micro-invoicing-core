package org.compiere.accounting;

import org.compiere.model.I_C_ValidCombination;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_ValidCombination
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ValidCombination extends PO implements I_C_ValidCombination, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_ValidCombination(Properties ctx, int C_ValidCombination_ID) {
        super(ctx, C_ValidCombination_ID);
        /**
         * if (C_ValidCombination_ID == 0) { setAccount_ID (0); setC_AcctSchema_ID (0);
         * setC_ValidCombination_ID (0); setIsFullyQualified (false); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_ValidCombination(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_C_ValidCombination[").append(getId()).append("]");
        return sb.toString();
    }

    public org.compiere.model.I_C_ElementValue getAccount() throws RuntimeException {
        return (org.compiere.model.I_C_ElementValue)
                MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
                        .getPO(getAccount_ID());
    }

    /**
     * Get Account.
     *
     * @return Account used
     */
    public int getAccount_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_Account_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Account.
     *
     * @param Account_ID Account used
     */
    public void setAccount_ID(int Account_ID) {
        if (Account_ID < 1) set_ValueNoCheck(COLUMNNAME_Account_ID, null);
        else set_ValueNoCheck(COLUMNNAME_Account_ID, Account_ID);
    }

    /**
     * Get Trx Organization.
     *
     * @return Performing or initiating organization
     */
    public int getAD_OrgTrx_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_OrgTrx_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Trx Organization.
     *
     * @param AD_OrgTrx_ID Performing or initiating organization
     */
    public void setAD_OrgTrx_ID(int AD_OrgTrx_ID) {
        if (AD_OrgTrx_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_OrgTrx_ID, null);
        else set_ValueNoCheck(COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
    }

    /**
     * Get Alias.
     *
     * @return Defines an alternate method of indicating an account combination.
     */
    public String getAlias() {
        return (String) get_Value(COLUMNNAME_Alias);
    }

    /**
     * Set Alias.
     *
     * @param Alias Defines an alternate method of indicating an account combination.
     */
    public void setAlias(String Alias) {
        set_Value(COLUMNNAME_Alias, Alias);
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
     * Set Accounting Schema.
     *
     * @param C_AcctSchema_ID Rules for accounting
     */
    public void setC_AcctSchema_ID(int C_AcctSchema_ID) {
        if (C_AcctSchema_ID < 1) set_ValueNoCheck(COLUMNNAME_C_AcctSchema_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
    }

    /**
     * Get Activity.
     *
     * @return Business Activity
     */
    public int getC_Activity_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Activity_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Activity.
     *
     * @param C_Activity_ID Business Activity
     */
    public void setC_Activity_ID(int C_Activity_ID) {
        if (C_Activity_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Activity_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
    }

    /**
     * Get Business Partner .
     *
     * @return Identifies a Business Partner
     */
    public int getC_BPartner_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_BPartner_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Business Partner .
     *
     * @param C_BPartner_ID Identifies a Business Partner
     */
    public void setC_BPartner_ID(int C_BPartner_ID) {
        if (C_BPartner_ID < 1) set_ValueNoCheck(COLUMNNAME_C_BPartner_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
    }

    /**
     * Get Campaign.
     *
     * @return Marketing Campaign
     */
    public int getC_Campaign_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Campaign_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Campaign.
     *
     * @param C_Campaign_ID Marketing Campaign
     */
    public void setC_Campaign_ID(int C_Campaign_ID) {
        if (C_Campaign_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Campaign_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
    }

    /**
     * Get Location From.
     *
     * @return Location that inventory was moved from
     */
    public int getC_LocFrom_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_LocFrom_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Location From.
     *
     * @param C_LocFrom_ID Location that inventory was moved from
     */
    public void setC_LocFrom_ID(int C_LocFrom_ID) {
        if (C_LocFrom_ID < 1) set_ValueNoCheck(COLUMNNAME_C_LocFrom_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_LocFrom_ID, Integer.valueOf(C_LocFrom_ID));
    }

    /**
     * Get Location To.
     *
     * @return Location that inventory was moved to
     */
    public int getC_LocTo_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_LocTo_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Location To.
     *
     * @param C_LocTo_ID Location that inventory was moved to
     */
    public void setC_LocTo_ID(int C_LocTo_ID) {
        if (C_LocTo_ID < 1) set_ValueNoCheck(COLUMNNAME_C_LocTo_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_LocTo_ID, Integer.valueOf(C_LocTo_ID));
    }

    /**
     * Get Combination.
     *
     * @return Unique combination of account elements
     */
    public String getCombination() {
        return (String) get_Value(COLUMNNAME_Combination);
    }

    /**
     * Set Combination.
     *
     * @param Combination Unique combination of account elements
     */
    public void setCombination(String Combination) {
        set_ValueNoCheck(COLUMNNAME_Combination, Combination);
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
     * Get Sales Region.
     *
     * @return Sales coverage region
     */
    public int getC_SalesRegion_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_SalesRegion_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Sales Region.
     *
     * @param C_SalesRegion_ID Sales coverage region
     */
    public void setC_SalesRegion_ID(int C_SalesRegion_ID) {
        if (C_SalesRegion_ID < 1) set_ValueNoCheck(COLUMNNAME_C_SalesRegion_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_SalesRegion_ID, Integer.valueOf(C_SalesRegion_ID));
    }

    /**
     * Get Sub Account.
     *
     * @return Sub account for Element Value
     */
    public int getC_SubAcct_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_SubAcct_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Sub Account.
     *
     * @param C_SubAcct_ID Sub account for Element Value
     */
    public void setC_SubAcct_ID(int C_SubAcct_ID) {
        if (C_SubAcct_ID < 1) set_ValueNoCheck(COLUMNNAME_C_SubAcct_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_SubAcct_ID, Integer.valueOf(C_SubAcct_ID));
    }

    /**
     * Get Combination.
     *
     * @return Valid Account Combination
     */
    public int getC_ValidCombination_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_ValidCombination_ID);
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
        set_ValueNoCheck(COLUMNNAME_Description, Description);
    }

    /**
     * Set Fully Qualified.
     *
     * @param IsFullyQualified This account is fully qualified
     */
    public void setIsFullyQualified(boolean IsFullyQualified) {
        set_ValueNoCheck(COLUMNNAME_IsFullyQualified, Boolean.valueOf(IsFullyQualified));
    }

    /**
     * Get Fully Qualified.
     *
     * @return This account is fully qualified
     */
    public boolean isFullyQualified() {
        Object oo = get_Value(COLUMNNAME_IsFullyQualified);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
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
        if (M_Product_ID < 1) set_ValueNoCheck(COLUMNNAME_M_Product_ID, null);
        else set_ValueNoCheck(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
    }

    /**
     * Get User Element List 1.
     *
     * @return User defined list element #1
     */
    public int getUser1_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_User1_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User Element List 1.
     *
     * @param User1_ID User defined list element #1
     */
    public void setUser1_ID(int User1_ID) {
        if (User1_ID < 1) set_ValueNoCheck(COLUMNNAME_User1_ID, null);
        else set_ValueNoCheck(COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
    }

    /**
     * Get User Element List 2.
     *
     * @return User defined list element #2
     */
    public int getUser2_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_User2_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User Element List 2.
     *
     * @param User2_ID User defined list element #2
     */
    public void setUser2_ID(int User2_ID) {
        if (User2_ID < 1) set_ValueNoCheck(COLUMNNAME_User2_ID, null);
        else set_ValueNoCheck(COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
    }

    /**
     * Get User Column 1.
     *
     * @return User defined accounting Element
     */
    public int getUserElement1_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_UserElement1_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User Column 1.
     *
     * @param UserElement1_ID User defined accounting Element
     */
    public void setUserElement1_ID(int UserElement1_ID) {
        if (UserElement1_ID < 1) set_Value(COLUMNNAME_UserElement1_ID, null);
        else set_Value(COLUMNNAME_UserElement1_ID, Integer.valueOf(UserElement1_ID));
    }

    /**
     * Get User Column 2.
     *
     * @return User defined accounting Element
     */
    public int getUserElement2_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_UserElement2_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User Column 2.
     *
     * @param UserElement2_ID User defined accounting Element
     */
    public void setUserElement2_ID(int UserElement2_ID) {
        if (UserElement2_ID < 1) set_Value(COLUMNNAME_UserElement2_ID, null);
        else set_Value(COLUMNNAME_UserElement2_ID, Integer.valueOf(UserElement2_ID));
    }

    @Override
    public int getTableId() {
        return I_C_ValidCombination.Table_ID;
    }
}
