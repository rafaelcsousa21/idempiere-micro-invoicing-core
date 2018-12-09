package org.compiere.invoicing;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_M_LocatorType;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

/**
 * Generated Model for M_LocatorType
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_LocatorType extends BasePOName implements I_M_LocatorType, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_LocatorType(Properties ctx, int M_LocatorType_ID, String trxName) {
    super(ctx, M_LocatorType_ID, trxName);
    /**
     * if (M_LocatorType_ID == 0) { setIsAvailableForReplenishment (true); // Y
     * setIsAvailableForReservation (true); // Y setIsAvailableForShipping (true); // Y
     * setM_LocatorType_ID (0); setName (null); }
     */
  }

  /** Load Constructor */
  public X_M_LocatorType(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_M_LocatorType[").append(getId()).append("]");
    return sb.toString();
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
   * Set Comment/Help.
   *
   * @param Help Comment or Hint
   */
  public void setHelp(String Help) {
    set_Value(COLUMNNAME_Help, Help);
  }

  /**
   * Get Comment/Help.
   *
   * @return Comment or Hint
   */
  public String getHelp() {
    return (String) get_Value(COLUMNNAME_Help);
  }

  /**
   * Set Available for Replenishment.
   *
   * @param IsAvailableForReplenishment Available for Replenishment
   */
  public void setIsAvailableForReplenishment(boolean IsAvailableForReplenishment) {
    set_Value(COLUMNNAME_IsAvailableForReplenishment, Boolean.valueOf(IsAvailableForReplenishment));
  }

  /**
   * Get Available for Replenishment.
   *
   * @return Available for Replenishment
   */
  public boolean isAvailableForReplenishment() {
    Object oo = get_Value(COLUMNNAME_IsAvailableForReplenishment);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Available for Reservation.
   *
   * @param IsAvailableForReservation Available for Reservation
   */
  public void setIsAvailableForReservation(boolean IsAvailableForReservation) {
    set_Value(COLUMNNAME_IsAvailableForReservation, Boolean.valueOf(IsAvailableForReservation));
  }

  /**
   * Get Available for Reservation.
   *
   * @return Available for Reservation
   */
  public boolean isAvailableForReservation() {
    Object oo = get_Value(COLUMNNAME_IsAvailableForReservation);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Available for Shipping.
   *
   * @param IsAvailableForShipping Available for Shipping
   */
  public void setIsAvailableForShipping(boolean IsAvailableForShipping) {
    set_Value(COLUMNNAME_IsAvailableForShipping, Boolean.valueOf(IsAvailableForShipping));
  }

  /**
   * Get Available for Shipping.
   *
   * @return Available for Shipping
   */
  public boolean isAvailableForShipping() {
    Object oo = get_Value(COLUMNNAME_IsAvailableForShipping);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Locator Type.
   *
   * @param M_LocatorType_ID Locator Type
   */
  public void setM_LocatorType_ID(int M_LocatorType_ID) {
    if (M_LocatorType_ID < 1) set_ValueNoCheck(COLUMNNAME_M_LocatorType_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_LocatorType_ID, Integer.valueOf(M_LocatorType_ID));
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
   * Set M_LocatorType_UU.
   *
   * @param M_LocatorType_UU M_LocatorType_UU
   */
  public void setM_LocatorType_UU(String M_LocatorType_UU) {
    set_Value(COLUMNNAME_M_LocatorType_UU, M_LocatorType_UU);
  }

  /**
   * Get M_LocatorType_UU.
   *
   * @return M_LocatorType_UU
   */
  public String getM_LocatorType_UU() {
    return (String) get_Value(COLUMNNAME_M_LocatorType_UU);
  }
}
