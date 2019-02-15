package org.compiere.accounting;

import org.compiere.model.I_GL_JournalBatch;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for GL_JournalBatch
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_GL_JournalBatch extends PO implements I_GL_JournalBatch, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_GL_JournalBatch(Properties ctx, int GL_JournalBatch_ID) {
    super(ctx, GL_JournalBatch_ID);
    /**
     * if (GL_JournalBatch_ID == 0) { setC_DocType_ID (0); setDescription (null); setDocAction
     * (null); // CO setDocStatus (null); // DR setDocumentNo (null); setGL_JournalBatch_ID (0);
     * setPostingType (null); // A setProcessed (false); setProcessing (false); setTotalCr
     * (Env.ZERO); setTotalDr (Env.ZERO); }
     */
  }

  /** Load Constructor */
  public X_GL_JournalBatch(Properties ctx, ResultSet rs) {
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
    StringBuffer sb = new StringBuffer("X_GL_JournalBatch[").append(getId()).append("]");
    return sb.toString();
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
   * Set Document Type.
   *
   * @param C_DocType_ID Document type or rules
   */
  public void setC_DocType_ID(int C_DocType_ID) {
    if (C_DocType_ID < 0) set_Value(COLUMNNAME_C_DocType_ID, null);
    else set_Value(COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
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
   * Set Control Amount.
   *
   * @param ControlAmt If not zero, the Debit amount of the document must be equal this amount
   */
  public void setControlAmt(BigDecimal ControlAmt) {
    set_Value(COLUMNNAME_ControlAmt, ControlAmt);
  }

  /**
   * Get Control Amount.
   *
   * @return If not zero, the Debit amount of the document must be equal this amount
   */
  public BigDecimal getControlAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_ControlAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Set Period.
   *
   * @param C_Period_ID Period of the Calendar
   */
  public void setC_Period_ID(int C_Period_ID) {
    if (C_Period_ID < 1) set_Value(COLUMNNAME_C_Period_ID, null);
    else set_Value(COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
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
   * Set Document Date.
   *
   * @param DateDoc Date of the Document
   */
  public void setDateDoc(Timestamp DateDoc) {
    set_Value(COLUMNNAME_DateDoc, DateDoc);
  }

  /**
   * Get Document Date.
   *
   * @return Date of the Document
   */
  public Timestamp getDateDoc() {
    return (Timestamp) get_Value(COLUMNNAME_DateDoc);
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

    /** Complete = CO */
  public static final String DOCACTION_Complete = "CO";
    /** Close = CL */
  public static final String DOCACTION_Close = "CL";
    /** Re-activate = RE */
  public static final String DOCACTION_Re_Activate = "RE";
  /** <None> = -- */
  public static final String DOCACTION_None = "--";

    /**
   * Set Document Action.
   *
   * @param DocAction The targeted status of the document
   */
  public void setDocAction(String DocAction) {

    set_Value(COLUMNNAME_DocAction, DocAction);
  }

  /**
   * Get Document Action.
   *
   * @return The targeted status of the document
   */
  public String getDocAction() {
    return (String) get_Value(COLUMNNAME_DocAction);
  }

    /** Drafted = DR */
  public static final String DOCSTATUS_Drafted = "DR";
  /** Completed = CO */
  public static final String DOCSTATUS_Completed = "CO";
    /** Voided = VO */
  public static final String DOCSTATUS_Voided = "VO";
  /** Invalid = IN */
  public static final String DOCSTATUS_Invalid = "IN";
  /** Reversed = RE */
  public static final String DOCSTATUS_Reversed = "RE";
  /** Closed = CL */
  public static final String DOCSTATUS_Closed = "CL";
    /** In Progress = IP */
  public static final String DOCSTATUS_InProgress = "IP";

    /**
   * Set Document Status.
   *
   * @param DocStatus The current status of the document
   */
  public void setDocStatus(String DocStatus) {

    set_Value(COLUMNNAME_DocStatus, DocStatus);
  }

  /**
   * Get Document Status.
   *
   * @return The current status of the document
   */
  public String getDocStatus() {
    return (String) get_Value(COLUMNNAME_DocStatus);
  }

  /**
   * Set Document No.
   *
   * @param DocumentNo Document sequence number of the document
   */
  public void setDocumentNo(String DocumentNo) {
    set_ValueNoCheck(COLUMNNAME_DocumentNo, DocumentNo);
  }

  /**
   * Get Document No.
   *
   * @return Document sequence number of the document
   */
  public String getDocumentNo() {
    return (String) get_Value(COLUMNNAME_DocumentNo);
  }

    /**
   * Set GL Category.
   *
   * @param GL_Category_ID General Ledger Category
   */
  public void setGL_Category_ID(int GL_Category_ID) {
    if (GL_Category_ID < 1) set_Value(COLUMNNAME_GL_Category_ID, null);
    else set_Value(COLUMNNAME_GL_Category_ID, Integer.valueOf(GL_Category_ID));
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
   * Set Approved.
   *
   * @param IsApproved Indicates if this document requires approval
   */
  public void setIsApproved(boolean IsApproved) {
    set_Value(COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
  }

    /** Actual = A */
  public static final String POSTINGTYPE_Actual = "A";

    /**
   * Set PostingType.
   *
   * @param PostingType The type of posted amount for the transaction
   */
  public void setPostingType(String PostingType) {

    set_Value(COLUMNNAME_PostingType, PostingType);
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
   * Set Process Now.
   *
   * @param Processing Process Now
   */
  public void setProcessing(boolean Processing) {
    set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
  }

    /**
   * Set Reversal ID.
   *
   * @param Reversal_ID ID of document reversal
   */
  public void setReversal_ID(int Reversal_ID) {
    if (Reversal_ID < 1) set_Value(COLUMNNAME_Reversal_ID, null);
    else set_Value(COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
  }

  /**
   * Get Reversal ID.
   *
   * @return ID of document reversal
   */
  public int getReversal_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Reversal_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Total Credit.
   *
   * @param TotalCr Total Credit in document currency
   */
  public void setTotalCr(BigDecimal TotalCr) {
    set_ValueNoCheck(COLUMNNAME_TotalCr, TotalCr);
  }

  /**
   * Get Total Credit.
   *
   * @return Total Credit in document currency
   */
  public BigDecimal getTotalCr() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_TotalCr);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Total Debit.
   *
   * @param TotalDr Total debit in document currency
   */
  public void setTotalDr(BigDecimal TotalDr) {
    set_ValueNoCheck(COLUMNNAME_TotalDr, TotalDr);
  }

  /**
   * Get Total Debit.
   *
   * @return Total debit in document currency
   */
  public BigDecimal getTotalDr() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_TotalDr);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  @Override
  public int getTableId() {
    return I_GL_JournalBatch.Table_ID;
  }
}
