package org.compiere.accounting;

import org.compiere.model.I_I_InOutLineConfirm;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

public class X_I_InOutLineConfirm extends PO implements I_I_InOutLineConfirm, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_I_InOutLineConfirm(Properties ctx, int I_InOutLineConfirm_ID, String trxName) {
    super(ctx, I_InOutLineConfirm_ID, trxName);
    /**
     * if (I_InOutLineConfirm_ID == 0) { setConfirmationNo (null); setConfirmedQty (Env.ZERO);
     * setDifferenceQty (Env.ZERO); setI_InOutLineConfirm_ID (0); setI_IsImported (false);
     * setM_InOutLineConfirm_ID (0); setScrappedQty (Env.ZERO); }
     */
  }

  /** Load Constructor */
  public X_I_InOutLineConfirm(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_I_InOutLineConfirm[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Confirmation No.
   *
   * @return Confirmation Number
   */
  public String getConfirmationNo() {
    return (String) get_Value(COLUMNNAME_ConfirmationNo);
  }

    /**
   * Get Confirmed Quantity.
   *
   * @return Confirmation of a received quantity
   */
  public BigDecimal getConfirmedQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_ConfirmedQty);
    if (bd == null) return Env.ZERO;
    return bd;
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
   * Get Difference.
   *
   * @return Difference Quantity
   */
  public BigDecimal getDifferenceQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_DifferenceQty);
    if (bd == null) return Env.ZERO;
    return bd;
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
   * Get Ship/Receipt Confirmation Import Line.
   *
   * @return Material Shipment or Receipt Confirmation Import Line
   */
  public int getI_InOutLineConfirm_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_I_InOutLineConfirm_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), String.valueOf(getI_InOutLineConfirm_ID()));
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
   * Get Ship/Receipt Confirmation Line.
   *
   * @return Material Shipment or Receipt Confirmation Line
   */
  public int getM_InOutLineConfirm_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_InOutLineConfirm_ID);
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
   * Get Scrapped Quantity.
   *
   * @return The Quantity scrapped due to QA issues
   */
  public BigDecimal getScrappedQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_ScrappedQty);
    if (bd == null) return Env.ZERO;
    return bd;
  }
}
