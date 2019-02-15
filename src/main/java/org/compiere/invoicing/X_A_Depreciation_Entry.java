package org.compiere.invoicing;

import org.compiere.model.I_A_Depreciation_Entry;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for A_Depreciation_Entry
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Depreciation_Entry extends PO implements I_A_Depreciation_Entry, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_A_Depreciation_Entry(Properties ctx, int A_Depreciation_Entry_ID) {
    super(ctx, A_Depreciation_Entry_ID);
  }

  /** Load Constructor */
  public X_A_Depreciation_Entry(Properties ctx, ResultSet rs) {
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

  @Override
  public int getTableId() {
    return Table_ID;
  }

  public String toString() {
    return "X_A_Depreciation_Entry[" + getId() + "]";
  }

    /**
   * Get Depreciation Entry.
   *
   * @return Depreciation Entry
   */
  public int getA_Depreciation_Entry_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_Entry_ID);
    if (ii == null) return 0;
    return ii;
  }

    /** Depreciation = DEP */
  public static final String A_ENTRY_TYPE_Depreciation = "DEP";

    /**
   * Set Entry Type.
   *
   * @param A_Entry_Type Entry Type
   */
  public void setA_Entry_Type(String A_Entry_Type) {

    set_Value(COLUMNNAME_A_Entry_Type, A_Entry_Type);
  }

    /**
   * Set Accounting Schema.
   *
   * @param C_AcctSchema_ID Rules for accounting
   */
  public void setC_AcctSchema_ID(int C_AcctSchema_ID) {
    if (C_AcctSchema_ID < 1) set_Value(COLUMNNAME_C_AcctSchema_ID, null);
    else set_Value(COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
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
   * Set Period.
   *
   * @param C_Period_ID Period of the Calendar
   */
  public void setC_Period_ID(int C_Period_ID) {
    if (C_Period_ID < 1) set_Value(COLUMNNAME_C_Period_ID, null);
    else set_Value(COLUMNNAME_C_Period_ID, C_Period_ID);
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
   * Get Account Date.
   *
   * @return Accounting Date
   */
  public Timestamp getDateAcct() {
    return (Timestamp) get_Value(COLUMNNAME_DateAcct);
  }

    /** Complete = CO */
  public static final String DOCACTION_Complete = "CO";
    /** Close = CL */
  public static final String DOCACTION_Close = "CL";
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

    /**
   * Get Document Status.
   *
   * @return The current status of the document
   */
  public String getDocStatus() {
    return (String) get_Value(COLUMNNAME_DocStatus);
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
   * Set Approved.
   *
   * @param IsApproved Indicates if this document requires approval
   */
  public void setIsApproved(boolean IsApproved) {
    set_Value(COLUMNNAME_IsApproved, IsApproved);
  }

  /**
   * Get Approved.
   *
   * @return Indicates if this document requires approval
   */
  public boolean isApproved() {
    Object oo = get_Value(COLUMNNAME_IsApproved);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Posted.
   *
   * @param Posted Posting status
   */
  public void setPosted(boolean Posted) {
    set_Value(COLUMNNAME_Posted, Posted);
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
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Processed);
  }

  /**
   * Get Processed.
   *
   * @return The document has been processed
   */
  public boolean isProcessed() {
    Object oo = get_Value(COLUMNNAME_Processed);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
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
    set_Value(COLUMNNAME_Processing, Processing);
  }

}
