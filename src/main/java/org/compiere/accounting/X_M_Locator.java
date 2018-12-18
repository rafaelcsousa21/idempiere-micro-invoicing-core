package org.compiere.accounting;

import org.compiere.model.I_M_Locator;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_Locator
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_Locator extends PO implements I_M_Locator, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_Locator(Properties ctx, int M_Locator_ID, String trxName) {
    super(ctx, M_Locator_ID, trxName);
    /**
     * if (M_Locator_ID == 0) { setIsDefault (false); setM_Locator_ID (0); setM_Warehouse_ID (0);
     * setPriorityNo (0); // 50 setValue (null); setX (null); setY (null); setZ (null); }
     */
  }

  /** Load Constructor */
  public X_M_Locator(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 7 - System - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  @Override
  public int getTableId() {
    return Table_ID;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_Locator[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Default.
   *
   * @param IsDefault Default value
   */
  public void setIsDefault(boolean IsDefault) {
    set_Value(COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
  }

  /**
   * Get Default.
   *
   * @return Default value
   */
  public boolean isDefault() {
    Object oo = get_Value(COLUMNNAME_IsDefault);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
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

  public org.compiere.model.I_M_LocatorType getM_LocatorType() throws RuntimeException {
    return (org.compiere.model.I_M_LocatorType)
        MTable.get(getCtx(), org.compiere.model.I_M_LocatorType.Table_Name)
            .getPO(getM_LocatorType_ID(), null);
  }

  /**
   * Set Locator Type.
   *
   * @param M_LocatorType_ID Locator Type
   */
  public void setM_LocatorType_ID(int M_LocatorType_ID) {
    if (M_LocatorType_ID < 1) set_Value(COLUMNNAME_M_LocatorType_ID, null);
    else set_Value(COLUMNNAME_M_LocatorType_ID, Integer.valueOf(M_LocatorType_ID));
  }

  /**
   * Get Locator Type.
   *
   * @return Locator Type
   */
  public int getM_LocatorType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_LocatorType_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set M_Locator_UU.
   *
   * @param M_Locator_UU M_Locator_UU
   */
  public void setM_Locator_UU(String M_Locator_UU) {
    set_Value(COLUMNNAME_M_Locator_UU, M_Locator_UU);
  }

  /**
   * Get M_Locator_UU.
   *
   * @return M_Locator_UU
   */
  public String getM_Locator_UU() {
    return (String) get_Value(COLUMNNAME_M_Locator_UU);
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
   * Set Relative Priority.
   *
   * @param PriorityNo Where inventory should be picked from first
   */
  public void setPriorityNo(int PriorityNo) {
    set_Value(COLUMNNAME_PriorityNo, Integer.valueOf(PriorityNo));
  }

  /**
   * Get Relative Priority.
   *
   * @return Where inventory should be picked from first
   */
  public int getPriorityNo() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PriorityNo);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Search Key.
   *
   * @param Value Search key for the record in the format required - must be unique
   */
  public void setValue(String Value) {
    set_Value(COLUMNNAME_Value, Value);
  }

  /**
   * Get Search Key.
   *
   * @return Search key for the record in the format required - must be unique
   */
  public String getValue() {
    return (String) get_Value(COLUMNNAME_Value);
  }

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), getValue());
  }

  /**
   * Set Aisle (X).
   *
   * @param X X dimension, e.g., Aisle
   */
  public void setX(String X) {
    set_Value(COLUMNNAME_X, X);
  }

  /**
   * Get Aisle (X).
   *
   * @return X dimension, e.g., Aisle
   */
  public String getX() {
    return (String) get_Value(COLUMNNAME_X);
  }

  /**
   * Set Bin (Y).
   *
   * @param Y Y dimension, e.g., Bin
   */
  public void setY(String Y) {
    set_Value(COLUMNNAME_Y, Y);
  }

  /**
   * Get Bin (Y).
   *
   * @return Y dimension, e.g., Bin
   */
  public String getY() {
    return (String) get_Value(COLUMNNAME_Y);
  }

  /**
   * Set Level (Z).
   *
   * @param Z Z dimension, e.g., Level
   */
  public void setZ(String Z) {
    set_Value(COLUMNNAME_Z, Z);
  }

  /**
   * Get Level (Z).
   *
   * @return Z dimension, e.g., Level
   */
  public String getZ() {
    return (String) get_Value(COLUMNNAME_Z);
  }
}
