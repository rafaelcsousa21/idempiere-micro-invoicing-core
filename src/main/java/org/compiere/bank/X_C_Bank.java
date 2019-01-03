package org.compiere.bank;

import org.compiere.model.I_C_Bank;
import org.compiere.model.I_C_Location;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_Bank
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Bank extends BasePOName implements I_C_Bank, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_Bank(Properties ctx, int C_Bank_ID, String trxName) {
    super(ctx, C_Bank_ID, trxName);
  }

  /** Load Constructor */
  public X_C_Bank(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_Bank[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Bank.
   *
   * @param C_Bank_ID Bank
   */
  public void setC_Bank_ID(int C_Bank_ID) {
    if (C_Bank_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Bank_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Bank_ID, Integer.valueOf(C_Bank_ID));
  }

  /**
   * Get Bank.
   *
   * @return Bank
   */
  public int getC_Bank_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Bank_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_Bank_UU.
   *
   * @param C_Bank_UU C_Bank_UU
   */
  public void setC_Bank_UU(String C_Bank_UU) {
    set_Value(COLUMNNAME_C_Bank_UU, C_Bank_UU);
  }

  /**
   * Get C_Bank_UU.
   *
   * @return C_Bank_UU
   */
  public String getC_Bank_UU() {
    return (String) get_Value(COLUMNNAME_C_Bank_UU);
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
   * Set Own Bank.
   *
   * @param IsOwnBank Bank for this Organization
   */
  public void setIsOwnBank(boolean IsOwnBank) {
    set_Value(COLUMNNAME_IsOwnBank, Boolean.valueOf(IsOwnBank));
  }

  /**
   * Get Own Bank.
   *
   * @return Bank for this Organization
   */
  public boolean isOwnBank() {
    Object oo = get_Value(COLUMNNAME_IsOwnBank);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Routing No.
   *
   * @param RoutingNo Bank Routing Number
   */
  public void setRoutingNo(String RoutingNo) {
    set_Value(COLUMNNAME_RoutingNo, RoutingNo);
  }

  /**
   * Get Routing No.
   *
   * @return Bank Routing Number
   */
  public String getRoutingNo() {
    return (String) get_Value(COLUMNNAME_RoutingNo);
  }

  /**
   * Set Swift code.
   *
   * @param SwiftCode Swift Code or BIC
   */
  public void setSwiftCode(String SwiftCode) {
    set_Value(COLUMNNAME_SwiftCode, SwiftCode);
  }

  /**
   * Get Swift code.
   *
   * @return Swift Code or BIC
   */
  public String getSwiftCode() {
    return (String) get_Value(COLUMNNAME_SwiftCode);
  }

  @Override
  public int getTableId() {
    return I_C_Bank.Table_ID;
  }
}
