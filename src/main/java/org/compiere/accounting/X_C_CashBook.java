package org.compiere.accounting;

import org.compiere.model.I_C_CashBook;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_CashBook
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_CashBook extends BasePOName implements I_C_CashBook, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_CashBook(Properties ctx, int C_CashBook_ID, String trxName) {
    super(ctx, C_CashBook_ID, trxName);
    /**
     * if (C_CashBook_ID == 0) { setC_CashBook_ID (0); setC_Currency_ID (0); setIsDefault (false);
     * setName (null); }
     */
  }

  /** Load Constructor */
  public X_C_CashBook(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_CashBook[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Cash Book.
   *
   * @param C_CashBook_ID Cash Book for recording petty cash transactions
   */
  public void setC_CashBook_ID(int C_CashBook_ID) {
    if (C_CashBook_ID < 1) set_ValueNoCheck(COLUMNNAME_C_CashBook_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_CashBook_ID, Integer.valueOf(C_CashBook_ID));
  }

  /**
   * Get Cash Book.
   *
   * @return Cash Book for recording petty cash transactions
   */
  public int getC_CashBook_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_CashBook_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_CashBook_UU.
   *
   * @param C_CashBook_UU C_CashBook_UU
   */
  public void setC_CashBook_UU(String C_CashBook_UU) {
    set_Value(COLUMNNAME_C_CashBook_UU, C_CashBook_UU);
  }

  /**
   * Get C_CashBook_UU.
   *
   * @return C_CashBook_UU
   */
  public String getC_CashBook_UU() {
    return (String) get_Value(COLUMNNAME_C_CashBook_UU);
  }

  public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException {
    return (org.compiere.model.I_C_Currency)
        MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
            .getPO(getC_Currency_ID(), null);
  }

  /**
   * Set Currency.
   *
   * @param C_Currency_ID The Currency for this record
   */
  public void setC_Currency_ID(int C_Currency_ID) {
    if (C_Currency_ID < 1) set_Value(COLUMNNAME_C_Currency_ID, null);
    else set_Value(COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
  }

  /**
   * Get Currency.
   *
   * @return The Currency for this record
   */
  public int getC_Currency_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Currency_ID);
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

  @Override
  public int getTableId() {
    return I_C_CashBook.Table_ID;
  }
}
