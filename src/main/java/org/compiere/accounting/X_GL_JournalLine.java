package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for GL_JournalLine
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_GL_JournalLine extends PO implements I_GL_JournalLine {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_GL_JournalLine(Properties ctx, int GL_JournalLine_ID) {
        super(ctx, GL_JournalLine_ID);
    }

    /**
     * Load Constructor
     */
    public X_GL_JournalLine(Properties ctx, Row row) {
        super(ctx, row);
    }

    /**
     * AccessLevel
     *
     * @return 1 - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        return "X_GL_JournalLine[" + getId() + "]";
    }

    /**
     * Get Account.
     *
     * @return Account used
     */
    public int getAccount_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_Account_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Account.
     *
     * @param Account_ID Account used
     */
    public void setAccount_ID(int Account_ID) {
        if (Account_ID < 1) setValue(COLUMNNAME_Account_ID, null);
        else setValue(COLUMNNAME_Account_ID, Integer.valueOf(Account_ID));
    }

    /**
     * Get Trx Organization.
     *
     * @return Performing or initiating organization
     */
    public int getTransactionOrganizationId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_OrgTrx_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Trx Organization.
     *
     * @param AD_OrgTrx_ID Performing or initiating organization
     */
    public void setTransactionOrganizationId(int AD_OrgTrx_ID) {
        if (AD_OrgTrx_ID < 1) setValue(COLUMNNAME_AD_OrgTrx_ID, null);
        else setValue(COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
    }

    /**
     * Set Alias List.
     *
     * @param Alias_ValidCombination_ID Valid Account Alias List
     */
    public void setAlias_ValidCombination_ID(int Alias_ValidCombination_ID) {
        if (Alias_ValidCombination_ID < 1) setValue(COLUMNNAME_Alias_ValidCombination_ID, null);
        else
            setValue(COLUMNNAME_Alias_ValidCombination_ID, Integer.valueOf(Alias_ValidCombination_ID));
    }

    /**
     * Get Accounted Credit.
     *
     * @return Accounted Credit Amount
     */
    public BigDecimal getAmtAcctCr() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_AmtAcctCr);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Accounted Credit.
     *
     * @param AmtAcctCr Accounted Credit Amount
     */
    public void setAmtAcctCr(BigDecimal AmtAcctCr) {
        setValueNoCheck(COLUMNNAME_AmtAcctCr, AmtAcctCr);
    }

    /**
     * Get Accounted Debit.
     *
     * @return Accounted Debit Amount
     */
    public BigDecimal getAmtAcctDr() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_AmtAcctDr);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Accounted Debit.
     *
     * @param AmtAcctDr Accounted Debit Amount
     */
    public void setAmtAcctDr(BigDecimal AmtAcctDr) {
        setValueNoCheck(COLUMNNAME_AmtAcctDr, AmtAcctDr);
    }

    /**
     * Get Source Credit.
     *
     * @return Source Credit Amount
     */
    public BigDecimal getAmtSourceCr() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_AmtSourceCr);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Source Credit.
     *
     * @param AmtSourceCr Source Credit Amount
     */
    public void setAmtSourceCr(BigDecimal AmtSourceCr) {
        setValue(COLUMNNAME_AmtSourceCr, AmtSourceCr);
    }

    /**
     * Get Source Debit.
     *
     * @return Source Debit Amount
     */
    public BigDecimal getAmtSourceDr() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_AmtSourceDr);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Source Debit.
     *
     * @param AmtSourceDr Source Debit Amount
     */
    public void setAmtSourceDr(BigDecimal AmtSourceDr) {
        setValue(COLUMNNAME_AmtSourceDr, AmtSourceDr);
    }

    /**
     * Get Activity.
     *
     * @return Business Activity
     */
    public int getBusinessActivityId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Activity_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Activity.
     *
     * @param C_Activity_ID Business Activity
     */
    public void setBusinessActivityId(int C_Activity_ID) {
        if (C_Activity_ID < 1) setValue(COLUMNNAME_C_Activity_ID, null);
        else setValue(COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
    }

    /**
     * Get Business Partner .
     *
     * @return Identifies a Business Partner
     */
    public int getBusinessPartnerId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_BPartner_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Business Partner .
     *
     * @param C_BPartner_ID Identifies a Business Partner
     */
    public void setBusinessPartnerId(int C_BPartner_ID) {
        if (C_BPartner_ID < 1) setValue(COLUMNNAME_C_BPartner_ID, null);
        else setValue(COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
    }

    /**
     * Get Campaign.
     *
     * @return Marketing Campaign
     */
    public int getCampaignId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Campaign_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Campaign.
     *
     * @param C_Campaign_ID Marketing Campaign
     */
    public void setCampaignId(int C_Campaign_ID) {
        if (C_Campaign_ID < 1) setValue(COLUMNNAME_C_Campaign_ID, null);
        else setValue(COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
    }

    /**
     * Set Currency Type.
     *
     * @param C_ConversionType_ID Currency Conversion Rate Type
     */
    public void setConversionTypeId(int C_ConversionType_ID) {
        if (C_ConversionType_ID < 1) setValue(COLUMNNAME_C_ConversionType_ID, null);
        else setValue(COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
    }

    /**
     * Set Currency.
     *
     * @param C_Currency_ID The Currency for this record
     */
    public void setCurrencyId(int C_Currency_ID) {
        if (C_Currency_ID < 1) setValue(COLUMNNAME_C_Currency_ID, null);
        else setValue(COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
    }

    /**
     * Get Location From.
     *
     * @return Location that inventory was moved from
     */
    public int getC_LocFrom_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_LocFrom_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Location From.
     *
     * @param C_LocFrom_ID Location that inventory was moved from
     */
    public void setC_LocFrom_ID(int C_LocFrom_ID) {
        if (C_LocFrom_ID < 1) setValue(COLUMNNAME_C_LocFrom_ID, null);
        else setValue(COLUMNNAME_C_LocFrom_ID, Integer.valueOf(C_LocFrom_ID));
    }

    /**
     * Get Location To.
     *
     * @return Location that inventory was moved to
     */
    public int getC_LocTo_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_LocTo_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Location To.
     *
     * @param C_LocTo_ID Location that inventory was moved to
     */
    public void setC_LocTo_ID(int C_LocTo_ID) {
        if (C_LocTo_ID < 1) setValue(COLUMNNAME_C_LocTo_ID, null);
        else setValue(COLUMNNAME_C_LocTo_ID, Integer.valueOf(C_LocTo_ID));
    }

    /**
     * Get Project.
     *
     * @return Financial Project
     */
    public int getProjectId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Project_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Project.
     *
     * @param C_Project_ID Financial Project
     */
    public void setProjectId(int C_Project_ID) {
        if (C_Project_ID < 1) setValue(COLUMNNAME_C_Project_ID, null);
        else setValue(COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
    }

    /**
     * Get Sales Region.
     *
     * @return Sales coverage region
     */
    public int getC_SalesRegion_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_SalesRegion_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Sales Region.
     *
     * @param C_SalesRegion_ID Sales coverage region
     */
    public void setC_SalesRegion_ID(int C_SalesRegion_ID) {
        if (C_SalesRegion_ID < 1) setValue(COLUMNNAME_C_SalesRegion_ID, null);
        else setValue(COLUMNNAME_C_SalesRegion_ID, Integer.valueOf(C_SalesRegion_ID));
    }

    /**
     * Get Sub Account.
     *
     * @return Sub account for Element Value
     */
    public int getC_SubAcct_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_SubAcct_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Sub Account.
     *
     * @param C_SubAcct_ID Sub account for Element Value
     */
    public void setC_SubAcct_ID(int C_SubAcct_ID) {
        if (C_SubAcct_ID < 1) setValue(COLUMNNAME_C_SubAcct_ID, null);
        else setValue(COLUMNNAME_C_SubAcct_ID, Integer.valueOf(C_SubAcct_ID));
    }

    /**
     * Set UOM.
     *
     * @param C_UOM_ID Unit of Measure
     */
    public void setC_UOM_ID(int C_UOM_ID) {
        if (C_UOM_ID < 1) setValue(COLUMNNAME_C_UOM_ID, null);
        else setValue(COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
    }

    /**
     * Get Rate.
     *
     * @return Currency Conversion Rate
     */
    public BigDecimal getCurrencyRate() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_CurrencyRate);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Rate.
     *
     * @param CurrencyRate Currency Conversion Rate
     */
    public void setCurrencyRate(BigDecimal CurrencyRate) {
        setValueNoCheck(COLUMNNAME_CurrencyRate, CurrencyRate);
    }

    /**
     * Get Combination.
     *
     * @return Valid Account Combination
     */
    public int getC_ValidCombination_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_ValidCombination_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Combination.
     *
     * @param C_ValidCombination_ID Valid Account Combination
     */
    public void setC_ValidCombination_ID(int C_ValidCombination_ID) {
        if (C_ValidCombination_ID < 1) setValue(COLUMNNAME_C_ValidCombination_ID, null);
        else setValue(COLUMNNAME_C_ValidCombination_ID, Integer.valueOf(C_ValidCombination_ID));
    }

    /**
     * Set Account Date.
     *
     * @param DateAcct Accounting Date
     */
    public void setDateAcct(Timestamp DateAcct) {
        setValue(COLUMNNAME_DateAcct, DateAcct);
    }

    /**
     * Set Description.
     *
     * @param Description Optional short description of the record
     */
    public void setDescription(String Description) {
        setValue(COLUMNNAME_Description, Description);
    }

    /**
     * Get Journal.
     *
     * @return General Ledger Journal
     */
    public int getGL_Journal_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_GL_Journal_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Journal.
     *
     * @param GL_Journal_ID General Ledger Journal
     */
    public void setGL_Journal_ID(int GL_Journal_ID) {
        if (GL_Journal_ID < 1) setValueNoCheck(COLUMNNAME_GL_Journal_ID, null);
        else setValueNoCheck(COLUMNNAME_GL_Journal_ID, Integer.valueOf(GL_Journal_ID));
    }

    /**
     * Get Journal Line.
     *
     * @return General Ledger Journal Line
     */
    public int getGL_JournalLine_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_GL_JournalLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Generated.
     *
     * @param IsGenerated This Line is generated
     */
    public void setIsGenerated(boolean IsGenerated) {
        setValueNoCheck(COLUMNNAME_IsGenerated, Boolean.valueOf(IsGenerated));
    }

    /**
     * Get Line No.
     *
     * @return Unique line for this document
     */
    public int getLine() {
        Integer ii = (Integer) getValue(COLUMNNAME_Line);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Line No.
     *
     * @param Line Unique line for this document
     */
    public void setLine(int Line) {
        setValue(COLUMNNAME_Line, Integer.valueOf(Line));
    }

    /**
     * Get Product.
     *
     * @return Product, Service, Item
     */
    public int getM_Product_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_Product_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Product.
     *
     * @param M_Product_ID Product, Service, Item
     */
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) setValue(COLUMNNAME_M_Product_ID, null);
        else setValue(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
    }

    /**
     * Set Processed.
     *
     * @param Processed The document has been processed
     */
    public void setProcessed(boolean Processed) {
        setValue(COLUMNNAME_Processed, Boolean.valueOf(Processed));
    }

    /**
     * Get Quantity.
     *
     * @return Quantity
     */
    public BigDecimal getQty() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_Qty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Quantity.
     *
     * @param Qty Quantity
     */
    public void setQty(BigDecimal Qty) {
        setValue(COLUMNNAME_Qty, Qty);
    }

    /**
     * Get User Element List 1.
     *
     * @return User defined list element #1
     */
    public int getUser1Id() {
        Integer ii = (Integer) getValue(COLUMNNAME_User1_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User Element List 1.
     *
     * @param User1_ID User defined list element #1
     */
    public void setUser1Id(int User1_ID) {
        if (User1_ID < 1) setValue(COLUMNNAME_User1_ID, null);
        else setValue(COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
    }

    /**
     * Get User Element List 2.
     *
     * @return User defined list element #2
     */
    public int getUser2Id() {
        Integer ii = (Integer) getValue(COLUMNNAME_User2_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User Element List 2.
     *
     * @param User2_ID User defined list element #2
     */
    public void setUser2Id(int User2_ID) {
        if (User2_ID < 1) setValue(COLUMNNAME_User2_ID, null);
        else setValue(COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
    }

    @Override
    public int getTableId() {
        return I_GL_JournalLine.Table_ID;
    }
}
