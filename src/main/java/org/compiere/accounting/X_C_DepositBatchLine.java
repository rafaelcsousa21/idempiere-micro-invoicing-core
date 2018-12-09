package org.compiere.accounting;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_C_DepositBatchLine;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

/**
 * Generated Model for C_DepositBatchLine
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_DepositBatchLine extends PO implements I_C_DepositBatchLine, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_DepositBatchLine(Properties ctx, int C_DepositBatchLine_ID, String trxName) {
    super(ctx, C_DepositBatchLine_ID, trxName);
    /**
     * if (C_DepositBatchLine_ID == 0) { setC_DepositBatch_ID (0); setC_DepositBatchLine_ID (0);
     * setC_Payment_ID (0); setLine (0); // @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM
     * C_DepositBatchLine WHERE C_DepositBatch_ID=@C_DepositBatch_ID@ setPayAmt (Env.ZERO);
     * setProcessed (false); setProcessing (false); }
     */
  }

  /** Load Constructor */
  public X_C_DepositBatchLine(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 3 - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  /** Load Meta Data */
  protected POInfo initPO(Properties ctx) {
    POInfo poi = POInfo.getPOInfo(ctx, Table_ID, get_TrxName());
    return poi;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_DepositBatchLine[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_C_DepositBatch getC_DepositBatch() throws RuntimeException {
    return (org.compiere.model.I_C_DepositBatch)
        MTable.get(getCtx(), org.compiere.model.I_C_DepositBatch.Table_Name)
            .getPO(getC_DepositBatch_ID(), get_TrxName());
  }

  /**
   * Set Deposit Batch.
   *
   * @param C_DepositBatch_ID Deposit Batch
   */
  public void setC_DepositBatch_ID(int C_DepositBatch_ID) {
    if (C_DepositBatch_ID < 1) set_ValueNoCheck(COLUMNNAME_C_DepositBatch_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_DepositBatch_ID, Integer.valueOf(C_DepositBatch_ID));
  }

  /**
   * Get Deposit Batch.
   *
   * @return Deposit Batch
   */
  public int getC_DepositBatch_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_DepositBatch_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), String.valueOf(getC_DepositBatch_ID()));
  }

  /**
   * Set Deposit Batch Line.
   *
   * @param C_DepositBatchLine_ID Deposit Batch Line
   */
  public void setC_DepositBatchLine_ID(int C_DepositBatchLine_ID) {
    if (C_DepositBatchLine_ID < 1) set_ValueNoCheck(COLUMNNAME_C_DepositBatchLine_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_DepositBatchLine_ID, Integer.valueOf(C_DepositBatchLine_ID));
  }

  /**
   * Get Deposit Batch Line.
   *
   * @return Deposit Batch Line
   */
  public int getC_DepositBatchLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_DepositBatchLine_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_DepositBatchLine_UU.
   *
   * @param C_DepositBatchLine_UU C_DepositBatchLine_UU
   */
  public void setC_DepositBatchLine_UU(String C_DepositBatchLine_UU) {
    set_Value(COLUMNNAME_C_DepositBatchLine_UU, C_DepositBatchLine_UU);
  }

  /**
   * Get C_DepositBatchLine_UU.
   *
   * @return C_DepositBatchLine_UU
   */
  public String getC_DepositBatchLine_UU() {
    return (String) get_Value(COLUMNNAME_C_DepositBatchLine_UU);
  }

  public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException {
    return (org.compiere.model.I_C_Payment)
        MTable.get(getCtx(), org.compiere.model.I_C_Payment.Table_Name)
            .getPO(getC_Payment_ID(), get_TrxName());
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
}
