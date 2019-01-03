package org.compiere.accounting;

import org.compiere.model.I_C_SubAcct;
import org.compiere.orm.BasePONameValue;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_SubAcct
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_SubAcct extends BasePONameValue implements I_C_SubAcct, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_SubAcct(Properties ctx, int C_SubAcct_ID, String trxName) {
    super(ctx, C_SubAcct_ID, trxName);
    /**
     * if (C_SubAcct_ID == 0) { setC_ElementValue_ID (0); setC_SubAcct_ID (0); setName (null);
     * setValue (null); }
     */
  }

  /** Load Constructor */
  public X_C_SubAcct(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_SubAcct[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException {
    return (org.compiere.model.I_C_ElementValue)
        MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
            .getPO(getC_ElementValue_ID(), null);
  }

  /**
   * Set Account Element.
   *
   * @param C_ElementValue_ID Account Element
   */
  public void setC_ElementValue_ID(int C_ElementValue_ID) {
    if (C_ElementValue_ID < 1) set_ValueNoCheck(COLUMNNAME_C_ElementValue_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
  }

  /**
   * Get Account Element.
   *
   * @return Account Element
   */
  public int getC_ElementValue_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ElementValue_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Sub Account.
   *
   * @param C_SubAcct_ID Sub account for Element Value
   */
  public void setC_SubAcct_ID(int C_SubAcct_ID) {
    if (C_SubAcct_ID < 1) set_ValueNoCheck(COLUMNNAME_C_SubAcct_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_SubAcct_ID, Integer.valueOf(C_SubAcct_ID));
  }

  /**
   * Get Sub Account.
   *
   * @return Sub account for Element Value
   */
  public int getC_SubAcct_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_SubAcct_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_SubAcct_UU.
   *
   * @param C_SubAcct_UU C_SubAcct_UU
   */
  public void setC_SubAcct_UU(String C_SubAcct_UU) {
    set_Value(COLUMNNAME_C_SubAcct_UU, C_SubAcct_UU);
  }

  /**
   * Get C_SubAcct_UU.
   *
   * @return C_SubAcct_UU
   */
  public String getC_SubAcct_UU() {
    return (String) get_Value(COLUMNNAME_C_SubAcct_UU);
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

  @Override
  public int getTableId() {
    return I_C_SubAcct.Table_ID;
  }
}
