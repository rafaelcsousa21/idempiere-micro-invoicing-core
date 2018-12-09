package org.compiere.accounting;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_C_Element;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

/**
 * Generated Model for C_Element
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Element extends BasePOName implements I_C_Element, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_Element(Properties ctx, int C_Element_ID, String trxName) {
    super(ctx, C_Element_ID, trxName);
  }

  /** Load Constructor */
  public X_C_Element(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_Element[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_AD_Tree getAD_Tree() throws RuntimeException {
    return (org.compiere.model.I_AD_Tree)
        MTable.get(getCtx(), org.compiere.model.I_AD_Tree.Table_Name)
            .getPO(getAD_Tree_ID(), get_TrxName());
  }

  /**
   * Set Tree.
   *
   * @param AD_Tree_ID Identifies a Tree
   */
  public void setAD_Tree_ID(int AD_Tree_ID) {
    if (AD_Tree_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_Tree_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_Tree_ID, Integer.valueOf(AD_Tree_ID));
  }

  /**
   * Get Tree.
   *
   * @return Identifies a Tree
   */
  public int getAD_Tree_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Tree_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Element.
   *
   * @param C_Element_ID Accounting Element
   */
  public void setC_Element_ID(int C_Element_ID) {
    if (C_Element_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Element_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Element_ID, Integer.valueOf(C_Element_ID));
  }

  /**
   * Get Element.
   *
   * @return Accounting Element
   */
  public int getC_Element_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Element_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_Element_UU.
   *
   * @param C_Element_UU C_Element_UU
   */
  public void setC_Element_UU(String C_Element_UU) {
    set_Value(COLUMNNAME_C_Element_UU, C_Element_UU);
  }

  /**
   * Get C_Element_UU.
   *
   * @return C_Element_UU
   */
  public String getC_Element_UU() {
    return (String) get_Value(COLUMNNAME_C_Element_UU);
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

  /** ElementType AD_Reference_ID=116 */
  public static final int ELEMENTTYPE_AD_Reference_ID = 116;
  /** Account = A */
  public static final String ELEMENTTYPE_Account = "A";
  /** User defined = U */
  public static final String ELEMENTTYPE_UserDefined = "U";
  /**
   * Set Type.
   *
   * @param ElementType Element Type (account or user defined)
   */
  public void setElementType(String ElementType) {

    set_ValueNoCheck(COLUMNNAME_ElementType, ElementType);
  }

  /**
   * Get Type.
   *
   * @return Element Type (account or user defined)
   */
  public String getElementType() {
    return (String) get_Value(COLUMNNAME_ElementType);
  }

  /**
   * Set Balancing.
   *
   * @param IsBalancing All transactions within an element value must balance (e.g. cost centers)
   */
  public void setIsBalancing(boolean IsBalancing) {
    set_Value(COLUMNNAME_IsBalancing, Boolean.valueOf(IsBalancing));
  }

  /**
   * Get Balancing.
   *
   * @return All transactions within an element value must balance (e.g. cost centers)
   */
  public boolean isBalancing() {
    Object oo = get_Value(COLUMNNAME_IsBalancing);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Natural Account.
   *
   * @param IsNaturalAccount The primary natural account
   */
  public void setIsNaturalAccount(boolean IsNaturalAccount) {
    set_Value(COLUMNNAME_IsNaturalAccount, Boolean.valueOf(IsNaturalAccount));
  }

  /**
   * Get Natural Account.
   *
   * @return The primary natural account
   */
  public boolean isNaturalAccount() {
    Object oo = get_Value(COLUMNNAME_IsNaturalAccount);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Value Format.
   *
   * @param VFormat Format of the value; Can contain fixed format elements, Variables:
   *     "_lLoOaAcCa09"
   */
  public void setVFormat(String VFormat) {
    set_Value(COLUMNNAME_VFormat, VFormat);
  }

  /**
   * Get Value Format.
   *
   * @return Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
   */
  public String getVFormat() {
    return (String) get_Value(COLUMNNAME_VFormat);
  }
}
