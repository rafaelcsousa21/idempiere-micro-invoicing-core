package org.compiere.accounting;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_C_Activity;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

/**
 * Generated Model for C_Activity
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Activity extends BasePONameValue implements I_C_Activity, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_Activity(Properties ctx, int C_Activity_ID, String trxName) {
    super(ctx, C_Activity_ID, trxName);
    /**
     * if (C_Activity_ID == 0) { setC_Activity_ID (0); setIsSummary (false); setName (null);
     * setValue (null); }
     */
  }

  /** Load Constructor */
  public X_C_Activity(Properties ctx, ResultSet rs, String trxName) {
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

  /** Load Meta Data */
  protected POInfo initPO(Properties ctx) {
    POInfo poi = POInfo.getPOInfo(ctx, Table_ID, get_TrxName());
    return poi;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_Activity[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Activity.
   *
   * @param C_Activity_ID Business Activity
   */
  public void setC_Activity_ID(int C_Activity_ID) {
    if (C_Activity_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Activity_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
  }

  /**
   * Get Activity.
   *
   * @return Business Activity
   */
  public int getC_Activity_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Activity_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_Activity_UU.
   *
   * @param C_Activity_UU C_Activity_UU
   */
  public void setC_Activity_UU(String C_Activity_UU) {
    set_Value(COLUMNNAME_C_Activity_UU, C_Activity_UU);
  }

  /**
   * Get C_Activity_UU.
   *
   * @return C_Activity_UU
   */
  public String getC_Activity_UU() {
    return (String) get_Value(COLUMNNAME_C_Activity_UU);
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
   * Set Summary Level.
   *
   * @param IsSummary This is a summary entity
   */
  public void setIsSummary(boolean IsSummary) {
    set_Value(COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
  }

  /**
   * Get Summary Level.
   *
   * @return This is a summary entity
   */
  public boolean isSummary() {
    Object oo = get_Value(COLUMNNAME_IsSummary);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }
}
