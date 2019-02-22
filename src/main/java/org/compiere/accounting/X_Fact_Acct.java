package org.compiere.accounting;

import org.compiere.model.I_Fact_Acct;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for Fact_Acct
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_Fact_Acct extends PO implements I_Fact_Acct, I_Persistent {

    /**
     * Actual = A
     */
    public static final String POSTINGTYPE_Actual = "A";
    /**
     * Budget = B
     */
    public static final String POSTINGTYPE_Budget = "B";
    /**
     * Commitment = E
     */
    public static final String POSTINGTYPE_Commitment = "E";
    /**
     * Reservation = R
     */
    public static final String POSTINGTYPE_Reservation = "R";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_Fact_Acct(Properties ctx, int Fact_Acct_ID) {
        super(ctx, Fact_Acct_ID);
        /**
         * if (Fact_Acct_ID == 0) { setAccount_ID (0); setAD_Table_ID (0); setAmtAcctCr (Env.ZERO);
         * setAmtAcctDr (Env.ZERO); setAmtSourceCr (Env.ZERO); setAmtSourceDr (Env.ZERO);
         * setC_AcctSchema_ID (0); setC_Currency_ID (0); setC_Period_ID (0); setDateAcct (new Timestamp(
         * System.currentTimeMillis() )); setDateTrx (new Timestamp( System.currentTimeMillis() ));
         * setFact_Acct_ID (0); setGL_Category_ID (0); setPostingType (null); setRecord_ID (0); }
         */
    }

    /**
     * Load Constructor
     */
    public X_Fact_Acct(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_Fact_Acct[").append(getId()).append("]");
        return sb.toString();
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
        else set_ValueNoCheck(COLUMNNAME_Account_ID, Integer.valueOf(Account_ID));
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
     * Get Table.
     *
     * @return Database Table information
     */
    public int getAD_Table_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_Table_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Table.
     *
     * @param AD_Table_ID Database Table information
     */
    public void setAD_Table_ID(int AD_Table_ID) {
        if (AD_Table_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_Table_ID, null);
        else set_ValueNoCheck(COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
    }

    /**
     * Get Accounted Credit.
     *
     * @return Accounted Credit Amount
     */
    public BigDecimal getAmtAcctCr() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_AmtAcctCr);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Accounted Credit.
     *
     * @param AmtAcctCr Accounted Credit Amount
     */
    public void setAmtAcctCr(BigDecimal AmtAcctCr) {
        set_ValueNoCheck(COLUMNNAME_AmtAcctCr, AmtAcctCr);
    }

    /**
     * Get Accounted Debit.
     *
     * @return Accounted Debit Amount
     */
    public BigDecimal getAmtAcctDr() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_AmtAcctDr);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Accounted Debit.
     *
     * @param AmtAcctDr Accounted Debit Amount
     */
    public void setAmtAcctDr(BigDecimal AmtAcctDr) {
        set_ValueNoCheck(COLUMNNAME_AmtAcctDr, AmtAcctDr);
    }

    /**
     * Get Source Credit.
     *
     * @return Source Credit Amount
     */
    public BigDecimal getAmtSourceCr() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_AmtSourceCr);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Source Credit.
     *
     * @param AmtSourceCr Source Credit Amount
     */
    public void setAmtSourceCr(BigDecimal AmtSourceCr) {
        set_ValueNoCheck(COLUMNNAME_AmtSourceCr, AmtSourceCr);
    }

    /**
     * Get Source Debit.
     *
     * @return Source Debit Amount
     */
    public BigDecimal getAmtSourceDr() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_AmtSourceDr);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Source Debit.
     *
     * @param AmtSourceDr Source Debit Amount
     */
    public void setAmtSourceDr(BigDecimal AmtSourceDr) {
        set_ValueNoCheck(COLUMNNAME_AmtSourceDr, AmtSourceDr);
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
     * Get Currency.
     *
     * @return The Currency for this record
     */
    public int getC_Currency_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Currency_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Currency.
     *
     * @param C_Currency_ID The Currency for this record
     */
    public void setC_Currency_ID(int C_Currency_ID) {
        if (C_Currency_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Currency_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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
     * Get Period.
     *
     * @return Period of the Calendar
     */
    public int getC_Period_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Period_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Period.
     *
     * @param C_Period_ID Period of the Calendar
     */
    public void setC_Period_ID(int C_Period_ID) {
        if (C_Period_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Period_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
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
     * Get Project Phase.
     *
     * @return Phase of a Project
     */
    public int getC_ProjectPhase_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_ProjectPhase_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Project Phase.
     *
     * @param C_ProjectPhase_ID Phase of a Project
     */
    public void setC_ProjectPhase_ID(int C_ProjectPhase_ID) {
        if (C_ProjectPhase_ID < 1) set_ValueNoCheck(COLUMNNAME_C_ProjectPhase_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_ProjectPhase_ID, Integer.valueOf(C_ProjectPhase_ID));
    }

    /**
     * Get Project Task.
     *
     * @return Actual Project Task in a Phase
     */
    public int getC_ProjectTask_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_ProjectTask_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Project Task.
     *
     * @param C_ProjectTask_ID Actual Project Task in a Phase
     */
    public void setC_ProjectTask_ID(int C_ProjectTask_ID) {
        if (C_ProjectTask_ID < 1) set_ValueNoCheck(COLUMNNAME_C_ProjectTask_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_ProjectTask_ID, Integer.valueOf(C_ProjectTask_ID));
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
        if (C_SubAcct_ID < 1) set_Value(COLUMNNAME_C_SubAcct_ID, null);
        else set_Value(COLUMNNAME_C_SubAcct_ID, Integer.valueOf(C_SubAcct_ID));
    }

    /**
     * Get Tax.
     *
     * @return Tax identifier
     */
    public int getC_Tax_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Tax_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Tax.
     *
     * @param C_Tax_ID Tax identifier
     */
    public void setC_Tax_ID(int C_Tax_ID) {
        if (C_Tax_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Tax_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
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
     * Set UOM.
     *
     * @param C_UOM_ID Unit of Measure
     */
    public void setC_UOM_ID(int C_UOM_ID) {
        if (C_UOM_ID < 1) set_ValueNoCheck(COLUMNNAME_C_UOM_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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
        set_ValueNoCheck(COLUMNNAME_DateAcct, DateAcct);
    }

    /**
     * Set Transaction Date.
     *
     * @param DateTrx Transaction Date
     */
    public void setDateTrx(Timestamp DateTrx) {
        set_ValueNoCheck(COLUMNNAME_DateTrx, DateTrx);
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
     * Get Accounting Fact.
     *
     * @return Accounting Fact
     */
    public int getFact_Acct_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_Fact_Acct_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Budget.
     *
     * @return General Ledger Budget
     */
    public int getGL_Budget_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_GL_Budget_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Budget.
     *
     * @param GL_Budget_ID General Ledger Budget
     */
    public void setGL_Budget_ID(int GL_Budget_ID) {
        if (GL_Budget_ID < 1) set_ValueNoCheck(COLUMNNAME_GL_Budget_ID, null);
        else set_ValueNoCheck(COLUMNNAME_GL_Budget_ID, Integer.valueOf(GL_Budget_ID));
    }

    /**
     * Get GL Category.
     *
     * @return General Ledger Category
     */
    public int getGL_Category_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_GL_Category_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set GL Category.
     *
     * @param GL_Category_ID General Ledger Category
     */
    public void setGL_Category_ID(int GL_Category_ID) {
        if (GL_Category_ID < 1) set_ValueNoCheck(COLUMNNAME_GL_Category_ID, null);
        else set_ValueNoCheck(COLUMNNAME_GL_Category_ID, Integer.valueOf(GL_Category_ID));
    }

    /**
     * Get Line ID.
     *
     * @return Transaction line ID (internal)
     */
    public int getLine_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_Line_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Line ID.
     *
     * @param Line_ID Transaction line ID (internal)
     */
    public void setLine_ID(int Line_ID) {
        if (Line_ID < 1) set_ValueNoCheck(COLUMNNAME_Line_ID, null);
        else set_ValueNoCheck(COLUMNNAME_Line_ID, Integer.valueOf(Line_ID));
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
     * Set Locator.
     *
     * @param M_Locator_ID Warehouse Locator
     */
    public void setM_Locator_ID(int M_Locator_ID) {
        if (M_Locator_ID < 1) set_ValueNoCheck(COLUMNNAME_M_Locator_ID, null);
        else set_ValueNoCheck(COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
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

        set_ValueNoCheck(COLUMNNAME_PostingType, PostingType);
    }

    /**
     * Get Quantity.
     *
     * @return Quantity
     */
    public BigDecimal getQty() {
        BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Qty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Quantity.
     *
     * @param Qty Quantity
     */
    public void setQty(BigDecimal Qty) {
        set_ValueNoCheck(COLUMNNAME_Qty, Qty);
    }

    /**
     * Get Record ID.
     *
     * @return Direct internal record ID
     */
    public int getRecord_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_Record_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Record ID.
     *
     * @param Record_ID Direct internal record ID
     */
    public void setRecord_ID(int Record_ID) {
        if (Record_ID < 0) set_ValueNoCheck(COLUMNNAME_Record_ID, null);
        else set_ValueNoCheck(COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
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
        if (UserElement1_ID < 1) set_ValueNoCheck(COLUMNNAME_UserElement1_ID, null);
        else set_ValueNoCheck(COLUMNNAME_UserElement1_ID, Integer.valueOf(UserElement1_ID));
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
        if (UserElement2_ID < 1) set_ValueNoCheck(COLUMNNAME_UserElement2_ID, null);
        else set_ValueNoCheck(COLUMNNAME_UserElement2_ID, Integer.valueOf(UserElement2_ID));
    }

    @Override
    public int getTableId() {
        return I_Fact_Acct.Table_ID;
    }
}
