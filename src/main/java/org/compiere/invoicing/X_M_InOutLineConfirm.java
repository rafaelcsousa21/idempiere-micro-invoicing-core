package org.compiere.invoicing;

import org.compiere.model.I_M_InOutLineConfirm;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_InOutLineConfirm
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_InOutLineConfirm extends PO implements I_M_InOutLineConfirm, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_InOutLineConfirm(Properties ctx, int M_InOutLineConfirm_ID, String trxName) {
    super(ctx, M_InOutLineConfirm_ID, trxName);
    /**
     * if (M_InOutLineConfirm_ID == 0) { setConfirmedQty (Env.ZERO); setM_InOutConfirm_ID (0);
     * setM_InOutLineConfirm_ID (0); setM_InOutLine_ID (0); setProcessed (false); setTargetQty
     * (Env.ZERO); }
     */
  }

  /** Load Constructor */
  public X_M_InOutLineConfirm(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_M_InOutLineConfirm[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException {
    return (org.compiere.model.I_C_InvoiceLine)
        MTable.get(getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name)
            .getPO(getC_InvoiceLine_ID(), null);
  }

  /**
   * Set Invoice Line.
   *
   * @param C_InvoiceLine_ID Invoice Detail Line
   */
  public void setC_InvoiceLine_ID(int C_InvoiceLine_ID) {
    if (C_InvoiceLine_ID < 1) set_Value(COLUMNNAME_C_InvoiceLine_ID, null);
    else set_Value(COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
  }

  /**
   * Get Invoice Line.
   *
   * @return Invoice Detail Line
   */
  public int getC_InvoiceLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_InvoiceLine_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Confirmation No.
   *
   * @param ConfirmationNo Confirmation Number
   */
  public void setConfirmationNo(String ConfirmationNo) {
    set_Value(COLUMNNAME_ConfirmationNo, ConfirmationNo);
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
   * Set Confirmed Quantity.
   *
   * @param ConfirmedQty Confirmation of a received quantity
   */
  public void setConfirmedQty(BigDecimal ConfirmedQty) {
    set_Value(COLUMNNAME_ConfirmedQty, ConfirmedQty);
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
   * Set Difference.
   *
   * @param DifferenceQty Difference Quantity
   */
  public void setDifferenceQty(BigDecimal DifferenceQty) {
    set_Value(COLUMNNAME_DifferenceQty, DifferenceQty);
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

  public org.compiere.model.I_M_InOutConfirm getM_InOutConfirm() throws RuntimeException {
    return (org.compiere.model.I_M_InOutConfirm)
        MTable.get(getCtx(), org.compiere.model.I_M_InOutConfirm.Table_Name)
            .getPO(getM_InOutConfirm_ID(), null);
  }

  /**
   * Set Ship/Receipt Confirmation.
   *
   * @param M_InOutConfirm_ID Material Shipment or Receipt Confirmation
   */
  public void setM_InOutConfirm_ID(int M_InOutConfirm_ID) {
    if (M_InOutConfirm_ID < 1) set_ValueNoCheck(COLUMNNAME_M_InOutConfirm_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_InOutConfirm_ID, Integer.valueOf(M_InOutConfirm_ID));
  }

  /**
   * Get Ship/Receipt Confirmation.
   *
   * @return Material Shipment or Receipt Confirmation
   */
  public int getM_InOutConfirm_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_InOutConfirm_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Ship/Receipt Confirmation Line.
   *
   * @param M_InOutLineConfirm_ID Material Shipment or Receipt Confirmation Line
   */
  public void setM_InOutLineConfirm_ID(int M_InOutLineConfirm_ID) {
    if (M_InOutLineConfirm_ID < 1) set_ValueNoCheck(COLUMNNAME_M_InOutLineConfirm_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_InOutLineConfirm_ID, Integer.valueOf(M_InOutLineConfirm_ID));
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
   * Set M_InOutLineConfirm_UU.
   *
   * @param M_InOutLineConfirm_UU M_InOutLineConfirm_UU
   */
  public void setM_InOutLineConfirm_UU(String M_InOutLineConfirm_UU) {
    set_Value(COLUMNNAME_M_InOutLineConfirm_UU, M_InOutLineConfirm_UU);
  }

  /**
   * Get M_InOutLineConfirm_UU.
   *
   * @return M_InOutLineConfirm_UU
   */
  public String getM_InOutLineConfirm_UU() {
    return (String) get_Value(COLUMNNAME_M_InOutLineConfirm_UU);
  }

  public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException {
    return (org.compiere.model.I_M_InOutLine)
        MTable.get(getCtx(), org.compiere.model.I_M_InOutLine.Table_Name)
            .getPO(getM_InOutLine_ID(), null);
  }

  /**
   * Set Shipment/Receipt Line.
   *
   * @param M_InOutLine_ID Line on Shipment or Receipt document
   */
  public void setM_InOutLine_ID(int M_InOutLine_ID) {
    if (M_InOutLine_ID < 1) set_ValueNoCheck(COLUMNNAME_M_InOutLine_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
  }

  /**
   * Get Shipment/Receipt Line.
   *
   * @return Line on Shipment or Receipt document
   */
  public int getM_InOutLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_InOutLine_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), String.valueOf(getM_InOutLine_ID()));
  }

  public org.compiere.model.I_M_InventoryLine getM_InventoryLine() throws RuntimeException {
    return (org.compiere.model.I_M_InventoryLine)
        MTable.get(getCtx(), org.compiere.model.I_M_InventoryLine.Table_Name)
            .getPO(getM_InventoryLine_ID(), null);
  }

  /**
   * Set Phys.Inventory Line.
   *
   * @param M_InventoryLine_ID Unique line in an Inventory document
   */
  public void setM_InventoryLine_ID(int M_InventoryLine_ID) {
    if (M_InventoryLine_ID < 1) set_Value(COLUMNNAME_M_InventoryLine_ID, null);
    else set_Value(COLUMNNAME_M_InventoryLine_ID, Integer.valueOf(M_InventoryLine_ID));
  }

  /**
   * Get Phys.Inventory Line.
   *
   * @return Unique line in an Inventory document
   */
  public int getM_InventoryLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_InventoryLine_ID);
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
   * Set Scrapped Quantity.
   *
   * @param ScrappedQty The Quantity scrapped due to QA issues
   */
  public void setScrappedQty(BigDecimal ScrappedQty) {
    set_Value(COLUMNNAME_ScrappedQty, ScrappedQty);
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

  /**
   * Set Target Quantity.
   *
   * @param TargetQty Target Movement Quantity
   */
  public void setTargetQty(BigDecimal TargetQty) {
    set_ValueNoCheck(COLUMNNAME_TargetQty, TargetQty);
  }

  /**
   * Get Target Quantity.
   *
   * @return Target Movement Quantity
   */
  public BigDecimal getTargetQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_TargetQty);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  @Override
  public int getTableId() {
    return I_M_InOutLineConfirm.Table_ID;
  }
}
