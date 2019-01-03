package org.compiere.invoicing;

import org.compiere.model.I_C_InvoiceBatch;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_InvoiceBatch
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_InvoiceBatch extends PO implements I_C_InvoiceBatch, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_InvoiceBatch(Properties ctx, int C_InvoiceBatch_ID, String trxName) {
    super(ctx, C_InvoiceBatch_ID, trxName);
    /**
     * if (C_InvoiceBatch_ID == 0) { setC_Currency_ID (0); // @$C_Currency_ID@ setC_InvoiceBatch_ID
     * (0); setControlAmt (Env.ZERO); // 0 setDateDoc (new Timestamp( System.currentTimeMillis() ));
     * // @#Date@ setDocumentAmt (Env.ZERO); setDocumentNo (null); setIsSOTrx (false); // N
     * setProcessed (false); setSalesRep_ID (0); }
     */
  }

  /** Load Constructor */
  public X_C_InvoiceBatch(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
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
    StringBuffer sb = new StringBuffer("X_C_InvoiceBatch[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_C_ConversionType getC_ConversionType() throws RuntimeException {
    return (org.compiere.model.I_C_ConversionType)
        MTable.get(getCtx(), org.compiere.model.I_C_ConversionType.Table_Name)
            .getPO(getC_ConversionType_ID(), null);
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

  public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException {
    return (org.compiere.model.I_C_Currency)
        MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
            .getPO(getC_Currency_ID(), null);
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
   * Set Invoice Batch.
   *
   * @param C_InvoiceBatch_ID Expense Invoice Batch Header
   */
  public void setC_InvoiceBatch_ID(int C_InvoiceBatch_ID) {
    if (C_InvoiceBatch_ID < 1) set_ValueNoCheck(COLUMNNAME_C_InvoiceBatch_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_InvoiceBatch_ID, Integer.valueOf(C_InvoiceBatch_ID));
  }

  /**
   * Get Invoice Batch.
   *
   * @return Expense Invoice Batch Header
   */
  public int getC_InvoiceBatch_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_InvoiceBatch_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_InvoiceBatch_UU.
   *
   * @param C_InvoiceBatch_UU C_InvoiceBatch_UU
   */
  public void setC_InvoiceBatch_UU(String C_InvoiceBatch_UU) {
    set_Value(COLUMNNAME_C_InvoiceBatch_UU, C_InvoiceBatch_UU);
  }

  /**
   * Get C_InvoiceBatch_UU.
   *
   * @return C_InvoiceBatch_UU
   */
  public String getC_InvoiceBatch_UU() {
    return (String) get_Value(COLUMNNAME_C_InvoiceBatch_UU);
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

  /**
   * Set Document Amt.
   *
   * @param DocumentAmt Document Amount
   */
  public void setDocumentAmt(BigDecimal DocumentAmt) {
    set_ValueNoCheck(COLUMNNAME_DocumentAmt, DocumentAmt);
  }

  /**
   * Get Document Amt.
   *
   * @return Document Amount
   */
  public BigDecimal getDocumentAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_DocumentAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Document No.
   *
   * @param DocumentNo Document sequence number of the document
   */
  public void setDocumentNo(String DocumentNo) {
    set_Value(COLUMNNAME_DocumentNo, DocumentNo);
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
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), getDocumentNo());
  }

  /**
   * Set Sales Transaction.
   *
   * @param IsSOTrx This is a Sales Transaction
   */
  public void setIsSOTrx(boolean IsSOTrx) {
    set_Value(COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
  }

  /**
   * Get Sales Transaction.
   *
   * @return This is a Sales Transaction
   */
  public boolean isSOTrx() {
    Object oo = get_Value(COLUMNNAME_IsSOTrx);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
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
   * Get Process Now.
   *
   * @return Process Now
   */
  public boolean isProcessing() {
    Object oo = get_Value(COLUMNNAME_Processing);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException {
    return (org.compiere.model.I_AD_User)
        MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
            .getPO(getSalesRep_ID(), null);
  }

  /**
   * Set Sales Representative.
   *
   * @param SalesRep_ID Sales Representative or Company Agent
   */
  public void setSalesRep_ID(int SalesRep_ID) {
    if (SalesRep_ID < 1) set_Value(COLUMNNAME_SalesRep_ID, null);
    else set_Value(COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
  }

  /**
   * Get Sales Representative.
   *
   * @return Sales Representative or Company Agent
   */
  public int getSalesRep_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_SalesRep_ID);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_C_InvoiceBatch.Table_ID;
  }
}
