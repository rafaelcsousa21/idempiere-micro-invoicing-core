package org.idempiere.process;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_I_GLJournal;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

public class X_I_GLJournal extends PO implements I_I_GLJournal, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_I_GLJournal(Properties ctx, int I_GLJournal_ID, String trxName) {
    super(ctx, I_GLJournal_ID, trxName);
    /** if (I_GLJournal_ID == 0) { setI_GLJournal_ID (0); setI_IsImported (false); } */
  }

  /** Load Constructor */
  public X_I_GLJournal(Properties ctx, ResultSet rs, String trxName) {
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

  @Override
  public int getTableId() {
    return Table_ID;
  }


  public String toString() {
    StringBuffer sb = new StringBuffer("X_I_GLJournal[").append(getId()).append("]");
    return sb.toString();
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
   * Get Document Org.
   *
   * @return Document Organization (independent from account organization)
   */
  public int getAD_OrgDoc_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_OrgDoc_ID);
    if (ii == null) return 0;
    return ii;
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
   * Get Batch Description.
   *
   * @return Description of the Batch
   */
  public String getBatchDescription() {
    return (String) get_Value(COLUMNNAME_BatchDescription);
  }

    /**
   * Get Batch Document No.
   *
   * @return Document Number of the Batch
   */
  public String getBatchDocumentNo() {
    return (String) get_Value(COLUMNNAME_BatchDocumentNo);
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
   * Get Document Type.
   *
   * @return Document type or rules
   */
  public int getC_DocType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_DocType_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set Journal Batch.
   *
   * @param GL_JournalBatch_ID General Ledger Journal Batch
   */
  public void setGL_JournalBatch_ID(int GL_JournalBatch_ID) {
    if (GL_JournalBatch_ID < 1) set_Value(COLUMNNAME_GL_JournalBatch_ID, null);
    else set_Value(COLUMNNAME_GL_JournalBatch_ID, Integer.valueOf(GL_JournalBatch_ID));
  }

  /**
   * Get Journal Batch.
   *
   * @return General Ledger Journal Batch
   */
  public int getGL_JournalBatch_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_GL_JournalBatch_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Journal.
   *
   * @param GL_Journal_ID General Ledger Journal
   */
  public void setGL_Journal_ID(int GL_Journal_ID) {
    if (GL_Journal_ID < 1) set_Value(COLUMNNAME_GL_Journal_ID, null);
    else set_Value(COLUMNNAME_GL_Journal_ID, Integer.valueOf(GL_Journal_ID));
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
   * Set Journal Line.
   *
   * @param GL_JournalLine_ID General Ledger Journal Line
   */
  public void setGL_JournalLine_ID(int GL_JournalLine_ID) {
    if (GL_JournalLine_ID < 1) set_Value(COLUMNNAME_GL_JournalLine_ID, null);
    else set_Value(COLUMNNAME_GL_JournalLine_ID, Integer.valueOf(GL_JournalLine_ID));
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
   * Set Import Error Message.
   *
   * @param I_ErrorMsg Messages generated from import process
   */
  public void setI_ErrorMsg(String I_ErrorMsg) {
    set_Value(COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
  }

    /**
   * Get Import GL Journal.
   *
   * @return Import General Ledger Journal
   */
  public int getI_GLJournal_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_I_GLJournal_ID);
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
   * Get Create New Batch.
   *
   * @return If selected a new batch is created
   */
  public boolean isCreateNewBatch() {
    Object oo = get_Value(COLUMNNAME_IsCreateNewBatch);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Create New Journal.
   *
   * @return If selected a new journal within the batch is created
   */
  public boolean isCreateNewJournal() {
    Object oo = get_Value(COLUMNNAME_IsCreateNewJournal);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Journal Document No.
   *
   * @return Document number of the Journal
   */
  public String getJournalDocumentNo() {
    return (String) get_Value(COLUMNNAME_JournalDocumentNo);
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
   * Get PostingType.
   *
   * @return The type of posted amount for the transaction
   */
  public String getPostingType() {
    return (String) get_Value(COLUMNNAME_PostingType);
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
   * Get User Element List 2.
   *
   * @return User defined list element #2
   */
  public int getUser2_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_User2_ID);
    if (ii == null) return 0;
    return ii;
  }
}
