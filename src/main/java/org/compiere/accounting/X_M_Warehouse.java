package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_Warehouse;
import org.compiere.orm.BasePONameValue;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_Warehouse
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_Warehouse extends BasePONameValue implements I_M_Warehouse, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_Warehouse(Properties ctx, int M_Warehouse_ID, String trxName) {
    super(ctx, M_Warehouse_ID, trxName);
  }

  /** Load Constructor */
  public X_M_Warehouse(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }
  public X_M_Warehouse(Properties ctx, Row row) {
    super(ctx, row);
  } //	MWarehouse

  /**
   * AccessLevel
   *
   * @return 3 - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_Warehouse[").append(getId()).append("]");
    return sb.toString();
  }

  public I_C_Location getC_Location() throws RuntimeException {
    return (I_C_Location)
        MTable.get(getCtx(), I_C_Location.Table_Name).getPO(getC_Location_ID(), null);
  }

  /**
   * Set Address.
   *
   * @param C_Location_ID Location or Address
   */
  public void setC_Location_ID(int C_Location_ID) {
    if (C_Location_ID < 1) set_Value(COLUMNNAME_C_Location_ID, null);
    else set_Value(COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
  }

  /**
   * Get Address.
   *
   * @return Location or Address
   */
  public int getC_Location_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Location_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set Disallow Negative Inventory.
   *
   * @param IsDisallowNegativeInv Negative Inventory is not allowed in this warehouse
   */
  public void setIsDisallowNegativeInv(boolean IsDisallowNegativeInv) {
    set_Value(COLUMNNAME_IsDisallowNegativeInv, Boolean.valueOf(IsDisallowNegativeInv));
  }

  /**
   * Get Disallow Negative Inventory.
   *
   * @return Negative Inventory is not allowed in this warehouse
   */
  public boolean isDisallowNegativeInv() {
    Object oo = get_Value(COLUMNNAME_IsDisallowNegativeInv);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set In Transit.
   *
   * @param IsInTransit Movement is in transit
   */
  public void setIsInTransit(boolean IsInTransit) {
    set_Value(COLUMNNAME_IsInTransit, Boolean.valueOf(IsInTransit));
  }

  /**
   * Get In Transit.
   *
   * @return Movement is in transit
   */
  public boolean isInTransit() {
    Object oo = get_Value(COLUMNNAME_IsInTransit);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  public org.compiere.model.I_M_Locator getM_ReserveLocator() throws RuntimeException {
    return (org.compiere.model.I_M_Locator)
        MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
            .getPO(getM_ReserveLocator_ID(), null);
  }

  /**
   * Set Reservation Locator.
   *
   * @param M_ReserveLocator_ID Reservation Locator (just for reporting purposes)
   */
  public void setM_ReserveLocator_ID(int M_ReserveLocator_ID) {
    if (M_ReserveLocator_ID < 1) set_Value(COLUMNNAME_M_ReserveLocator_ID, null);
    else set_Value(COLUMNNAME_M_ReserveLocator_ID, Integer.valueOf(M_ReserveLocator_ID));
  }

  /**
   * Get Reservation Locator.
   *
   * @return Reservation Locator (just for reporting purposes)
   */
  public int getM_ReserveLocator_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_ReserveLocator_ID);
    if (ii == null) return 0;
    return ii;
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

  public org.compiere.model.I_M_Warehouse getM_WarehouseSource() throws RuntimeException {
    return (org.compiere.model.I_M_Warehouse)
        MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
            .getPO(getM_WarehouseSource_ID(), null);
  }

  /**
   * Set Source Warehouse.
   *
   * @param M_WarehouseSource_ID Optional Warehouse to replenish from
   */
  public void setM_WarehouseSource_ID(int M_WarehouseSource_ID) {
    if (M_WarehouseSource_ID < 1) set_Value(COLUMNNAME_M_WarehouseSource_ID, null);
    else set_Value(COLUMNNAME_M_WarehouseSource_ID, Integer.valueOf(M_WarehouseSource_ID));
  }

  /**
   * Get Source Warehouse.
   *
   * @return Optional Warehouse to replenish from
   */
  public int getM_WarehouseSource_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_WarehouseSource_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set M_Warehouse_UU.
   *
   * @param M_Warehouse_UU M_Warehouse_UU
   */
  public void setM_Warehouse_UU(String M_Warehouse_UU) {
    set_Value(COLUMNNAME_M_Warehouse_UU, M_Warehouse_UU);
  }

  /**
   * Get M_Warehouse_UU.
   *
   * @return M_Warehouse_UU
   */
  public String getM_Warehouse_UU() {
    return (String) get_Value(COLUMNNAME_M_Warehouse_UU);
  }

  /**
   * Set Replenishment Class.
   *
   * @param ReplenishmentClass Custom class to calculate Quantity to Order
   */
  public void setReplenishmentClass(String ReplenishmentClass) {
    set_Value(COLUMNNAME_ReplenishmentClass, ReplenishmentClass);
  }

  /**
   * Get Replenishment Class.
   *
   * @return Custom class to calculate Quantity to Order
   */
  public String getReplenishmentClass() {
    return (String) get_Value(COLUMNNAME_ReplenishmentClass);
  }

  /**
   * Set Element Separator.
   *
   * @param Separator Element Separator
   */
  public void setSeparator(String Separator) {
    set_Value(COLUMNNAME_Separator, Separator);
  }

  /**
   * Get Element Separator.
   *
   * @return Element Separator
   */
  public String getSeparator() {
    return (String) get_Value(COLUMNNAME_Separator);
  }

  @Override
  public int getTableId() {
    return I_M_Warehouse.Table_ID;
  }
}
