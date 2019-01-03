package org.compiere.accounting;

import org.compiere.model.I_C_SalesRegion;
import org.compiere.orm.BasePONameValue;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_SalesRegion
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_SalesRegion extends BasePONameValue implements I_C_SalesRegion, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_SalesRegion(Properties ctx, int C_SalesRegion_ID, String trxName) {
    super(ctx, C_SalesRegion_ID, trxName);
    /**
     * if (C_SalesRegion_ID == 0) { setC_SalesRegion_ID (0); setIsDefault (false); setIsSummary
     * (false); setName (null); setValue (null); }
     */
  }

  /** Load Constructor */
  public X_C_SalesRegion(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_SalesRegion[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Sales Region.
   *
   * @param C_SalesRegion_ID Sales coverage region
   */
  public void setC_SalesRegion_ID(int C_SalesRegion_ID) {
    if (C_SalesRegion_ID < 1) set_ValueNoCheck(COLUMNNAME_C_SalesRegion_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_SalesRegion_ID, Integer.valueOf(C_SalesRegion_ID));
  }

  /**
   * Get Sales Region.
   *
   * @return Sales coverage region
   */
  public int getC_SalesRegion_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_SalesRegion_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_SalesRegion_UU.
   *
   * @param C_SalesRegion_UU C_SalesRegion_UU
   */
  public void setC_SalesRegion_UU(String C_SalesRegion_UU) {
    set_Value(COLUMNNAME_C_SalesRegion_UU, C_SalesRegion_UU);
  }

  /**
   * Get C_SalesRegion_UU.
   *
   * @return C_SalesRegion_UU
   */
  public String getC_SalesRegion_UU() {
    return (String) get_Value(COLUMNNAME_C_SalesRegion_UU);
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

  public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException {
    return (org.compiere.model.I_AD_User)
        MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
            .getPO(getSalesRep_ID(), null);
  }

  /**
   * Set Sales Representative.
   *
   * @param SalesRep_ID Sales Representative or Company Agent
   */
  public void setSalesRep_ID(int SalesRep_ID) {
    if (SalesRep_ID < 1) set_Value(COLUMNNAME_SalesRep_ID, null);
    else set_Value(COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
  }

  /**
   * Get Sales Representative.
   *
   * @return Sales Representative or Company Agent
   */
  public int getSalesRep_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_SalesRep_ID);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_C_SalesRegion.Table_ID;
  }
}
