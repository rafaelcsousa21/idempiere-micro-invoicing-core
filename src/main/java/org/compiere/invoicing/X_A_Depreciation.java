package org.compiere.invoicing;

import org.compiere.model.I_A_Depreciation;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for A_Depreciation
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Depreciation extends BasePOName implements I_A_Depreciation, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_A_Depreciation(Properties ctx, int A_Depreciation_ID, String trxName) {
    super(ctx, A_Depreciation_ID, trxName);
  }

  /** Load Constructor */
  public X_A_Depreciation(Properties ctx, ResultSet rs, String trxName) {
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

  public String toString() {
    StringBuffer sb = new StringBuffer("X_A_Depreciation[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Depreciation.
   *
   * @param A_Depreciation_ID Depreciation
   */
  public void setA_Depreciation_ID(int A_Depreciation_ID) {
    if (A_Depreciation_ID < 1) set_ValueNoCheck(COLUMNNAME_A_Depreciation_ID, null);
    else set_ValueNoCheck(COLUMNNAME_A_Depreciation_ID, Integer.valueOf(A_Depreciation_ID));
  }

  /**
   * Get Depreciation.
   *
   * @return Depreciation
   */
  public int getA_Depreciation_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Depreciation_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set A_Depreciation_UU.
   *
   * @param A_Depreciation_UU A_Depreciation_UU
   */
  public void setA_Depreciation_UU(String A_Depreciation_UU) {
    set_Value(COLUMNNAME_A_Depreciation_UU, A_Depreciation_UU);
  }

  /**
   * Get A_Depreciation_UU.
   *
   * @return A_Depreciation_UU
   */
  public String getA_Depreciation_UU() {
    return (String) get_Value(COLUMNNAME_A_Depreciation_UU);
  }

  /**
   * Set DepreciationType.
   *
   * @param DepreciationType DepreciationType
   */
  public void setDepreciationType(String DepreciationType) {
    set_Value(COLUMNNAME_DepreciationType, DepreciationType);
  }

  /**
   * Get DepreciationType.
   *
   * @return DepreciationType
   */
  public String getDepreciationType() {
    return (String) get_Value(COLUMNNAME_DepreciationType);
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
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
  }

  /**
   * Get Processed.
   *
   * @return The document has been processed
   */
  public boolean isProcessed() {
    Object oo = get_Value(COLUMNNAME_Processed);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Description.
   *
   * @param Text Description
   */
  public void setText(String Text) {
    set_Value(COLUMNNAME_Text, Text);
  }

  /**
   * Get Description.
   *
   * @return Description
   */
  public String getText() {
    return (String) get_Value(COLUMNNAME_Text);
  }

  @Override
  public int getTableId() {
    return I_A_Depreciation.Table_ID;
  }
}
