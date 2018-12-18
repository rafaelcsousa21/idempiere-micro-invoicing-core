package org.compiere.accounting;

import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_StorageReservation;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for M_StorageReservation
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_StorageReservation extends PO implements I_M_StorageReservation, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_StorageReservation(Properties ctx, int M_StorageReservation_ID, String trxName) {
    super(ctx, M_StorageReservation_ID, trxName);
    /**
     * if (M_StorageReservation_ID == 0) { setIsSOTrx (true); // Y setM_AttributeSetInstance_ID (0);
     * setM_Product_ID (0); setM_Warehouse_ID (0); setQty (Env.ZERO); }
     */
  }

  /** Load Constructor */
  public X_M_StorageReservation(Properties ctx, ResultSet rs, String trxName) {
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

  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_StorageReservation[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Date last inventory count.
   *
   * @param DateLastInventory Date of Last Inventory Count
   */
  public void setDateLastInventory(Timestamp DateLastInventory) {
    set_Value(COLUMNNAME_DateLastInventory, DateLastInventory);
  }

  /**
   * Get Date last inventory count.
   *
   * @return Date of Last Inventory Count
   */
  public Timestamp getDateLastInventory() {
    return (Timestamp) get_Value(COLUMNNAME_DateLastInventory);
  }

  /**
   * Set Sales Transaction.
   *
   * @param IsSOTrx This is a Sales Transaction
   */
  public void setIsSOTrx(boolean IsSOTrx) {
    set_ValueNoCheck(COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
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

  public I_M_AttributeSetInstance getMAttributeSetInstance() throws RuntimeException {
    return (I_M_AttributeSetInstance)
        MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
            .getPO(getMAttributeSetInstance_ID(), null);
  }

  /**
   * Set Attribute Set Instance.
   *
   * @param M_AttributeSetInstance_ID Product Attribute Set Instance
   */
  public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
    if (M_AttributeSetInstance_ID < 0) set_ValueNoCheck(COLUMNNAME_M_AttributeSetInstance_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
  }

  /**
   * Get Attribute Set Instance.
   *
   * @return Product Attribute Set Instance
   */
  public int getMAttributeSetInstance_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_M_Product getM_Product() throws RuntimeException {
    return (org.compiere.model.I_M_Product)
        MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
            .getPO(getM_Product_ID(), null);
  }

  /**
   * Set Product.
   *
   * @param M_Product_ID Product, Service, Item
   */
  public void setM_Product_ID(int M_Product_ID) {
    if (M_Product_ID < 1) set_ValueNoCheck(COLUMNNAME_M_Product_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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
   * Set M_StorageReservation_UU.
   *
   * @param M_StorageReservation_UU M_StorageReservation_UU
   */
  public void setM_StorageReservation_UU(String M_StorageReservation_UU) {
    set_Value(COLUMNNAME_M_StorageReservation_UU, M_StorageReservation_UU);
  }

  /**
   * Get M_StorageReservation_UU.
   *
   * @return M_StorageReservation_UU
   */
  public String getM_StorageReservation_UU() {
    return (String) get_Value(COLUMNNAME_M_StorageReservation_UU);
  }

  public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException {
    return (org.compiere.model.I_M_Warehouse)
        MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
            .getPO(getM_Warehouse_ID(), null);
  }

  /**
   * Set Warehouse.
   *
   * @param M_Warehouse_ID Storage Warehouse and Service Point
   */
  public void setM_Warehouse_ID(int M_Warehouse_ID) {
    if (M_Warehouse_ID < 1) set_ValueNoCheck(COLUMNNAME_M_Warehouse_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
  }

  /**
   * Get Warehouse.
   *
   * @return Storage Warehouse and Service Point
   */
  public int getM_Warehouse_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Warehouse_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Quantity.
   *
   * @param Qty Quantity
   */
  public void setQty(BigDecimal Qty) {
    set_ValueNoCheck(COLUMNNAME_Qty, Qty);
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

  @Override
  public int getTableId() {
    return I_M_StorageReservation.Table_ID;
  }
}
