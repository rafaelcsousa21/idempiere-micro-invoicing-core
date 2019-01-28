package org.compiere.accounting;

import org.compiere.model.I_C_PaySelectionCheck;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_PaySelectionCheck
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_PaySelectionCheck extends PO implements I_C_PaySelectionCheck, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_PaySelectionCheck(Properties ctx, int C_PaySelectionCheck_ID, String trxName) {
    super(ctx, C_PaySelectionCheck_ID, trxName);
    /**
     * if (C_PaySelectionCheck_ID == 0) { setC_BPartner_ID (0); setC_PaySelectionCheck_ID (0);
     * setC_PaySelection_ID (0); setDiscountAmt (Env.ZERO); setIsGeneratedDraft (false); // N
     * setIsPrinted (false); setIsReceipt (false); setPayAmt (Env.ZERO); setPaymentRule (null);
     * setProcessed (false); // N setQty (0); setWriteOffAmt (Env.ZERO); // 0 }
     */
  }

  /** Load Constructor */
  public X_C_PaySelectionCheck(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_PaySelectionCheck[").append(getId()).append("]");
    return sb.toString();
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
   * Set Partner Bank Account.
   *
   * @param C_BP_BankAccount_ID Bank Account of the Business Partner
   */
  public void setC_BP_BankAccount_ID(int C_BP_BankAccount_ID) {
    if (C_BP_BankAccount_ID < 1) set_Value(COLUMNNAME_C_BP_BankAccount_ID, null);
    else set_Value(COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
  }

  /**
   * Get Partner Bank Account.
   *
   * @return Bank Account of the Business Partner
   */
  public int getC_BP_BankAccount_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BP_BankAccount_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Payment.
   *
   * @param C_Payment_ID Payment identifier
   */
  public void setC_Payment_ID(int C_Payment_ID) {
    if (C_Payment_ID < 1) set_Value(COLUMNNAME_C_Payment_ID, null);
    else set_Value(COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
  }

  /**
   * Get Payment.
   *
   * @return Payment identifier
   */
  public int getC_Payment_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Payment_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Pay Selection Check.
   *
   * @return Payment Selection Check
   */
  public int getC_PaySelectionCheck_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_PaySelectionCheck_ID);
    if (ii == null) return 0;
    return ii;
  }

    public org.compiere.model.I_C_PaySelection getC_PaySelection() throws RuntimeException {
    return (org.compiere.model.I_C_PaySelection)
        MTable.get(getCtx(), org.compiere.model.I_C_PaySelection.Table_Name)
            .getPO(getC_PaySelection_ID(), null);
  }

  /**
   * Set Payment Selection.
   *
   * @param C_PaySelection_ID Payment Selection
   */
  public void setC_PaySelection_ID(int C_PaySelection_ID) {
    if (C_PaySelection_ID < 1) set_ValueNoCheck(COLUMNNAME_C_PaySelection_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_PaySelection_ID, Integer.valueOf(C_PaySelection_ID));
  }

  /**
   * Get Payment Selection.
   *
   * @return Payment Selection
   */
  public int getC_PaySelection_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_PaySelection_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Discount Amount.
   *
   * @param DiscountAmt Calculated amount of discount
   */
  public void setDiscountAmt(BigDecimal DiscountAmt) {
    set_Value(COLUMNNAME_DiscountAmt, DiscountAmt);
  }

  /**
   * Get Discount Amount.
   *
   * @return Calculated amount of discount
   */
  public BigDecimal getDiscountAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_DiscountAmt);
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
   * Set Generated Draft.
   *
   * @param IsGeneratedDraft Generated Draft
   */
  public void setIsGeneratedDraft(boolean IsGeneratedDraft) {
    set_Value(COLUMNNAME_IsGeneratedDraft, Boolean.valueOf(IsGeneratedDraft));
  }

  /**
   * Get Generated Draft.
   *
   * @return Generated Draft
   */
  public boolean isGeneratedDraft() {
    Object oo = get_Value(COLUMNNAME_IsGeneratedDraft);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Printed.
   *
   * @param IsPrinted Indicates if this document / line is printed
   */
  public void setIsPrinted(boolean IsPrinted) {
    set_Value(COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
  }

    /**
   * Set Receipt.
   *
   * @param IsReceipt This is a sales transaction (receipt)
   */
  public void setIsReceipt(boolean IsReceipt) {
    set_Value(COLUMNNAME_IsReceipt, Boolean.valueOf(IsReceipt));
  }

  /**
   * Get Receipt.
   *
   * @return This is a sales transaction (receipt)
   */
  public boolean isReceipt() {
    Object oo = get_Value(COLUMNNAME_IsReceipt);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Payment amount.
   *
   * @param PayAmt Amount being paid
   */
  public void setPayAmt(BigDecimal PayAmt) {
    set_Value(COLUMNNAME_PayAmt, PayAmt);
  }

  /**
   * Get Payment amount.
   *
   * @return Amount being paid
   */
  public BigDecimal getPayAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_PayAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /** Cash = B */
  public static final String PAYMENTRULE_Cash = "B";
  /** Credit Card = K */
  public static final String PAYMENTRULE_CreditCard = "K";
  /** Direct Deposit = T */
  public static final String PAYMENTRULE_DirectDeposit = "T";
  /** Check = S */
  public static final String PAYMENTRULE_Check = "S";
    /** Direct Debit = D */
  public static final String PAYMENTRULE_DirectDebit = "D";

    /**
   * Set Payment Rule.
   *
   * @param PaymentRule How you pay the invoice
   */
  public void setPaymentRule(String PaymentRule) {

    set_Value(COLUMNNAME_PaymentRule, PaymentRule);
  }

  /**
   * Get Payment Rule.
   *
   * @return How you pay the invoice
   */
  public String getPaymentRule() {
    return (String) get_Value(COLUMNNAME_PaymentRule);
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
   * Set Quantity.
   *
   * @param Qty Quantity
   */
  public void setQty(int Qty) {
    set_Value(COLUMNNAME_Qty, Integer.valueOf(Qty));
  }

  /**
   * Get Quantity.
   *
   * @return Quantity
   */
  public int getQty() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Qty);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Write-off Amount.
   *
   * @param WriteOffAmt Amount to write-off
   */
  public void setWriteOffAmt(BigDecimal WriteOffAmt) {
    set_Value(COLUMNNAME_WriteOffAmt, WriteOffAmt);
  }

  /**
   * Get Write-off Amount.
   *
   * @return Amount to write-off
   */
  public BigDecimal getWriteOffAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_WriteOffAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  @Override
  public int getTableId() {
    return I_C_PaySelectionCheck.Table_ID;
  }
}
