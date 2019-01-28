package org.idempiere.process;

import org.compiere.model.I_I_Inventory;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

public class X_I_Inventory extends PO implements I_I_Inventory, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_I_Inventory(Properties ctx, int I_Inventory_ID, String trxName) {
    super(ctx, I_Inventory_ID, trxName);
    /** if (I_Inventory_ID == 0) { setI_Inventory_ID (0); setI_IsImported (false); } */
  }

  /** Load Constructor */
  public X_I_Inventory(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_I_Inventory[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Charge.
   *
   * @return Additional document charges
   */
  public int getC_Charge_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Charge_ID);
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
   * Get Current Cost Price.
   *
   * @return The currently used cost price
   */
  public BigDecimal getCurrentCostPrice() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_CurrentCostPrice);
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
   * Set Import Error Message.
   *
   * @param I_ErrorMsg Messages generated from import process
   */
  public void setI_ErrorMsg(String I_ErrorMsg) {
    set_Value(COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
  }

    /**
   * Get Import Inventory.
   *
   * @return Import Inventory Transactions
   */
  public int getI_Inventory_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_I_Inventory_ID);
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
   * Get Lot No.
   *
   * @return Lot number (alphanumeric)
   */
  public String getLot() {
    return (String) get_Value(COLUMNNAME_Lot);
  }

    /**
   * Set Cost Adjustment Line.
   *
   * @param M_CostingLine_ID Unique line in an Inventory cost adjustment document
   */
  public void setM_CostingLine_ID(int M_CostingLine_ID) {
    if (M_CostingLine_ID < 1) set_Value(COLUMNNAME_M_CostingLine_ID, null);
    else set_Value(COLUMNNAME_M_CostingLine_ID, Integer.valueOf(M_CostingLine_ID));
  }

  /**
   * Get Cost Adjustment Line.
   *
   * @return Unique line in an Inventory cost adjustment document
   */
  public int getM_CostingLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_CostingLine_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Phys.Inventory.
   *
   * @param M_Inventory_ID Parameters for a Physical Inventory
   */
  public void setM_Inventory_ID(int M_Inventory_ID) {
    if (M_Inventory_ID < 1) set_Value(COLUMNNAME_M_Inventory_ID, null);
    else set_Value(COLUMNNAME_M_Inventory_ID, Integer.valueOf(M_Inventory_ID));
  }

  /**
   * Get Phys.Inventory.
   *
   * @return Parameters for a Physical Inventory
   */
  public int getM_Inventory_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Inventory_ID);
    if (ii == null) return 0;
    return ii;
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
   * Get Locator.
   *
   * @return Warehouse Locator
   */
  public int getM_Locator_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Locator_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Movement Date.
   *
   * @return Date a product was moved in or out of inventory
   */
  public Timestamp getMovementDate() {
    return (Timestamp) get_Value(COLUMNNAME_MovementDate);
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
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
  }

    /**
   * Get Quantity book.
   *
   * @return Book Quantity
   */
  public BigDecimal getQtyBook() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_QtyBook);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Get Quantity count.
   *
   * @return Counted Quantity
   */
  public BigDecimal getQtyCount() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_QtyCount);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Get Internal Use Qty.
   *
   * @return Internal Use Quantity removed from Inventory
   */
  public BigDecimal getQtyInternalUse() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_QtyInternalUse);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Get Serial No.
   *
   * @return Product Serial Number
   */
  public String getSerNo() {
    return (String) get_Value(COLUMNNAME_SerNo);
  }

}
