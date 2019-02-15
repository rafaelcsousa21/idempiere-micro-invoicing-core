package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_M_Warehouse;
import org.compiere.orm.BasePONameValue;
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
  public X_M_Warehouse(Properties ctx, int M_Warehouse_ID) {
    super(ctx, M_Warehouse_ID);
  }

  /** Load Constructor */
  public X_M_Warehouse(Properties ctx, ResultSet rs) {
    super(ctx, rs);
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

    @Override
  public int getTableId() {
    return I_M_Warehouse.Table_ID;
  }
}
