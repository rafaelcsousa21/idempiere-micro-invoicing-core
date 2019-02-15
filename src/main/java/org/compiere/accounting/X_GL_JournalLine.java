package org.compiere.accounting;

import org.compiere.model.I_GL_JournalLine;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for GL_JournalLine
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_GL_JournalLine extends PO implements I_GL_JournalLine, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_GL_JournalLine(Properties ctx, int GL_JournalLine_ID) {
    super(ctx, GL_JournalLine_ID);
    /**
     * if (GL_JournalLine_ID == 0) { setAmtAcctCr (Env.ZERO); setAmtAcctDr (Env.ZERO);
     * setAmtSourceCr (Env.ZERO); setAmtSourceDr (Env.ZERO); setC_ConversionType_ID (0);
     * setC_Currency_ID (0); // @C_Currency_ID@ setCurrencyRate (Env.ZERO); // @CurrencyRate@;1
     * setDateAcct (new Timestamp( System.currentTimeMillis() )); // @DateAcct@ setGL_Journal_ID
     * (0); setGL_JournalLine_ID (0); setIsGenerated (false); setLine (0); // @SQL=SELECT
     * NVL(MAX(Line),0)+10 AS DefaultValue FROM GL_JournalLine WHERE GL_Journal_ID=@GL_Journal_ID@
     * setProcessed (false); }
     */
  }

  /** Load Constructor */
  public X_GL_JournalLine(Properties ctx, ResultSet rs) {
    super(ctx, rs);
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
    StringBuffer sb = new StringBuffer("X_GL_JournalLine[").append(getId()).append("]");
    return sb.toString();
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
   * Set Account.
   *
   * @param Account_ID Account used
   */
  public void setAccount_ID(int Account_ID) {
    if (Account_ID < 1) set_Value(COLUMNNAME_Account_ID, null);
    else set_Value(COLUMNNAME_Account_ID, Integer.valueOf(Account_ID));
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
   * Set Trx Organization.
   *
   * @param AD_OrgTrx_ID Performing or initiating organization
   */
  public void setAD_OrgTrx_ID(int AD_OrgTrx_ID) {
    if (AD_OrgTrx_ID < 1) set_Value(COLUMNNAME_AD_OrgTrx_ID, null);
    else set_Value(COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
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
   * Set Alias List.
   *
   * @param Alias_ValidCombination_ID Valid Account Alias List
   */
  public void setAlias_ValidCombination_ID(int Alias_ValidCombination_ID) {
    if (Alias_ValidCombination_ID < 1) set_Value(COLUMNNAME_Alias_ValidCombination_ID, null);
    else
      set_Value(COLUMNNAME_Alias_ValidCombination_ID, Integer.valueOf(Alias_ValidCombination_ID));
  }

  /**
   * Get Alias List.
   *
   * @return Valid Account Alias List
   */
  public int getAlias_ValidCombination_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Alias_ValidCombination_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set Accounted Debit.
   *
   * @param AmtAcctDr Accounted Debit Amount
   */
  public void setAmtAcctDr(BigDecimal AmtAcctDr) {
    set_ValueNoCheck(COLUMNNAME_AmtAcctDr, AmtAcctDr);
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
   * Set Source Credit.
   *
   * @param AmtSourceCr Source Credit Amount
   */
  public void setAmtSourceCr(BigDecimal AmtSourceCr) {
    set_Value(COLUMNNAME_AmtSourceCr, AmtSourceCr);
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
   * Set Source Debit.
   *
   * @param AmtSourceDr Source Debit Amount
   */
  public void setAmtSourceDr(BigDecimal AmtSourceDr) {
    set_Value(COLUMNNAME_AmtSourceDr, AmtSourceDr);
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
   * Set Activity.
   *
   * @param C_Activity_ID Business Activity
   */
  public void setC_Activity_ID(int C_Activity_ID) {
    if (C_Activity_ID < 1) set_Value(COLUMNNAME_C_Activity_ID, null);
    else set_Value(COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
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
   * Set Business Partner .
   *
   * @param C_BPartner_ID Identifies a Business Partner
   */
  public void setC_BPartner_ID(int C_BPartner_ID) {
    if (C_BPartner_ID < 1) set_Value(COLUMNNAME_C_BPartner_ID, null);
    else set_Value(COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
   * Set Campaign.
   *
   * @param C_Campaign_ID Marketing Campaign
   */
  public void setC_Campaign_ID(int C_Campaign_ID) {
    if (C_Campaign_ID < 1) set_Value(COLUMNNAME_C_Campaign_ID, null);
    else set_Value(COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
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
   * Set Currency Type.
   *
   * @param C_ConversionType_ID Currency Conversion Rate Type
   */
  public void setC_ConversionType_ID(int C_ConversionType_ID) {
    if (C_ConversionType_ID < 1) set_Value(COLUMNNAME_C_ConversionType_ID, null);
    else set_Value(COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
  }

  /**
   * Get Currency Type.
   *
   * @return Currency Conversion Rate Type
   */
  public int getC_ConversionType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ConversionType_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Currency.
   *
   * @param C_Currency_ID The Currency for this record
   */
  public void setC_Currency_ID(int C_Currency_ID) {
    if (C_Currency_ID < 1) set_Value(COLUMNNAME_C_Currency_ID, null);
    else set_Value(COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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
   * Set Location From.
   *
   * @param C_LocFrom_ID Location that inventory was moved from
   */
  public void setC_LocFrom_ID(int C_LocFrom_ID) {
    if (C_LocFrom_ID < 1) set_Value(COLUMNNAME_C_LocFrom_ID, null);
    else set_Value(COLUMNNAME_C_LocFrom_ID, Integer.valueOf(C_LocFrom_ID));
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
   * Set Location To.
   *
   * @param C_LocTo_ID Location that inventory was moved to
   */
  public void setC_LocTo_ID(int C_LocTo_ID) {
    if (C_LocTo_ID < 1) set_Value(COLUMNNAME_C_LocTo_ID, null);
    else set_Value(COLUMNNAME_C_LocTo_ID, Integer.valueOf(C_LocTo_ID));
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
   * Set Project.
   *
   * @param C_Project_ID Financial Project
   */
  public void setC_Project_ID(int C_Project_ID) {
    if (C_Project_ID < 1) set_Value(COLUMNNAME_C_Project_ID, null);
    else set_Value(COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
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
   * Set Sales Region.
   *
   * @param C_SalesRegion_ID Sales coverage region
   */
  public void setC_SalesRegion_ID(int C_SalesRegion_ID) {
    if (C_SalesRegion_ID < 1) set_Value(COLUMNNAME_C_SalesRegion_ID, null);
    else set_Value(COLUMNNAME_C_SalesRegion_ID, Integer.valueOf(C_SalesRegion_ID));
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
   * Set Sub Account.
   *
   * @param C_SubAcct_ID Sub account for Element Value
   */
  public void setC_SubAcct_ID(int C_SubAcct_ID) {
    if (C_SubAcct_ID < 1) set_Value(COLUMNNAME_C_SubAcct_ID, null);
    else set_Value(COLUMNNAME_C_SubAcct_ID, Integer.valueOf(C_SubAcct_ID));
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
   * Set UOM.
   *
   * @param C_UOM_ID Unit of Measure
   */
  public void setC_UOM_ID(int C_UOM_ID) {
    if (C_UOM_ID < 1) set_Value(COLUMNNAME_C_UOM_ID, null);
    else set_Value(COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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
   * Set Rate.
   *
   * @param CurrencyRate Currency Conversion Rate
   */
  public void setCurrencyRate(BigDecimal CurrencyRate) {
    set_ValueNoCheck(COLUMNNAME_CurrencyRate, CurrencyRate);
  }

  /**
   * Get Rate.
   *
   * @return Currency Conversion Rate
   */
  public BigDecimal getCurrencyRate() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_CurrencyRate);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Set Combination.
   *
   * @param C_ValidCombination_ID Valid Account Combination
   */
  public void setC_ValidCombination_ID(int C_ValidCombination_ID) {
    if (C_ValidCombination_ID < 1) set_Value(COLUMNNAME_C_ValidCombination_ID, null);
    else set_Value(COLUMNNAME_C_ValidCombination_ID, Integer.valueOf(C_ValidCombination_ID));
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
   * Set Account Date.
   *
   * @param DateAcct Accounting Date
   */
  public void setDateAcct(Timestamp DateAcct) {
    set_Value(COLUMNNAME_DateAcct, DateAcct);
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
   * Set Journal.
   *
   * @param GL_Journal_ID General Ledger Journal
   */
  public void setGL_Journal_ID(int GL_Journal_ID) {
    if (GL_Journal_ID < 1) set_ValueNoCheck(COLUMNNAME_GL_Journal_ID, null);
    else set_ValueNoCheck(COLUMNNAME_GL_Journal_ID, Integer.valueOf(GL_Journal_ID));
  }

  /**
   * Get Journal.
   *
   * @return General Ledger Journal
   */
  public int getGL_Journal_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_GL_Journal_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Journal Line.
   *
   * @return General Ledger Journal Line
   */
  public int getGL_JournalLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_GL_JournalLine_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Generated.
   *
   * @param IsGenerated This Line is generated
   */
  public void setIsGenerated(boolean IsGenerated) {
    set_ValueNoCheck(COLUMNNAME_IsGenerated, Boolean.valueOf(IsGenerated));
  }

    /**
   * Set Line No.
   *
   * @param Line Unique line for this document
   */
  public void setLine(int Line) {
    set_Value(COLUMNNAME_Line, Integer.valueOf(Line));
  }

  /**
   * Get Line No.
   *
   * @return Unique line for this document
   */
  public int getLine() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Line);
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
   * Set Quantity.
   *
   * @param Qty Quantity
   */
  public void setQty(BigDecimal Qty) {
    set_Value(COLUMNNAME_Qty, Qty);
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
   * Set User Element List 1.
   *
   * @param User1_ID User defined list element #1
   */
  public void setUser1_ID(int User1_ID) {
    if (User1_ID < 1) set_Value(COLUMNNAME_User1_ID, null);
    else set_Value(COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
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
   * Set User Element List 2.
   *
   * @param User2_ID User defined list element #2
   */
  public void setUser2_ID(int User2_ID) {
    if (User2_ID < 1) set_Value(COLUMNNAME_User2_ID, null);
    else set_Value(COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
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

  @Override
  public int getTableId() {
    return I_GL_JournalLine.Table_ID;
  }
}
