package org.compiere.production;

import org.compiere.model.I_R_Resolution;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for R_Resolution
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_Resolution extends BasePOName implements I_R_Resolution, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_Resolution(Properties ctx, int R_Resolution_ID, String trxName) {
    super(ctx, R_Resolution_ID, trxName);
    /** if (R_Resolution_ID == 0) { setName (null); setR_Resolution_ID (0); } */
  }

  /** Load Constructor */
  public X_R_Resolution(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 6 - System - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_R_Resolution[").append(getId()).append("]");
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
   * Set Resolution.
   *
   * @param R_Resolution_ID Request Resolution
   */
  public void setR_Resolution_ID(int R_Resolution_ID) {
    if (R_Resolution_ID < 1) set_ValueNoCheck(COLUMNNAME_R_Resolution_ID, null);
    else set_ValueNoCheck(COLUMNNAME_R_Resolution_ID, Integer.valueOf(R_Resolution_ID));
  }

  /**
   * Get Resolution.
   *
   * @return Request Resolution
   */
  public int getR_Resolution_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Resolution_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set R_Resolution_UU.
   *
   * @param R_Resolution_UU R_Resolution_UU
   */
  public void setR_Resolution_UU(String R_Resolution_UU) {
    set_Value(COLUMNNAME_R_Resolution_UU, R_Resolution_UU);
  }

  /**
   * Get R_Resolution_UU.
   *
   * @return R_Resolution_UU
   */
  public String getR_Resolution_UU() {
    return (String) get_Value(COLUMNNAME_R_Resolution_UU);
  }

  @Override
  public int getTableId() {
    return I_R_Resolution.Table_ID;
  }
}
