package org.compiere.accounting;

import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_StorageOnHand;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for M_StorageOnHand
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_StorageOnHand extends PO implements I_M_StorageOnHand, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_StorageOnHand(Properties ctx, int M_StorageOnHand_ID, String trxName) {
    super(ctx, M_StorageOnHand_ID, trxName);
    /**
     * if (M_StorageOnHand_ID == 0) { setDateMaterialPolicy (new Timestamp(
     * System.currentTimeMillis() )); setM_AttributeSetInstance_ID (0); setM_Locator_ID (0);
     * setM_Product_ID (0); setQtyOnHand (Env.ZERO); }
     */
  }

  /** Load Constructor */
  public X_M_StorageOnHand(Properties ctx, ResultSet rs, String trxName) {
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

  @Override
  public int getTableId() {
    return Table_ID;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_StorageOnHand[").append(getId()).append("]");
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
   * Set Date Material Policy.
   *
   * @param DateMaterialPolicy Time used for LIFO and FIFO Material Policy
   */
  public void setDateMaterialPolicy(Timestamp DateMaterialPolicy) {
    set_ValueNoCheck(COLUMNNAME_DateMaterialPolicy, DateMaterialPolicy);
  }

  /**
   * Get Date Material Policy.
   *
   * @return Time used for LIFO and FIFO Material Policy
   */
  public Timestamp getDateMaterialPolicy() {
    return (Timestamp) get_Value(COLUMNNAME_DateMaterialPolicy);
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

  public I_M_Locator getM_Locator() throws RuntimeException {
    return (I_M_Locator)
        MTable.get(getCtx(), I_M_Locator.Table_Name).getPO(getM_Locator_ID(), null);
  }

  /**
   * Set Locator.
   *
   * @param M_Locator_ID Warehouse Locator
   */
  public void setM_Locator_ID(int M_Locator_ID) {
    if (M_Locator_ID < 1) set_ValueNoCheck(COLUMNNAME_M_Locator_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
  }

  /**
   * Get Locator.
   *
   * @return Warehouse Locator
   */
  public int getM_Locator_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Locator_ID);
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
   * Set M_StorageOnHand_UU.
   *
   * @param M_StorageOnHand_UU M_StorageOnHand_UU
   */
  public void setM_StorageOnHand_UU(String M_StorageOnHand_UU) {
    set_Value(COLUMNNAME_M_StorageOnHand_UU, M_StorageOnHand_UU);
  }

  /**
   * Get M_StorageOnHand_UU.
   *
   * @return M_StorageOnHand_UU
   */
  public String getM_StorageOnHand_UU() {
    return (String) get_Value(COLUMNNAME_M_StorageOnHand_UU);
  }

  /**
   * Set On Hand Quantity.
   *
   * @param QtyOnHand On Hand Quantity
   */
  public void setQtyOnHand(BigDecimal QtyOnHand) {
    set_ValueNoCheck(COLUMNNAME_QtyOnHand, QtyOnHand);
  }

  /**
   * Get On Hand Quantity.
   *
   * @return On Hand Quantity
   */
  public BigDecimal getQtyOnHand() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_QtyOnHand);
    if (bd == null) return Env.ZERO;
    return bd;
  }
}
