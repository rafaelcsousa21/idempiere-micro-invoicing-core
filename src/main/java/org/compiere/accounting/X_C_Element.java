package org.compiere.accounting;

import org.compiere.model.I_C_Element;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

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


  @Override
  public int getTableId() {
    return Table_ID;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_Element[").append(getId()).append("]");
    return sb.toString();
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
   * Get Element.
   *
   * @return Accounting Element
   */
  public int getC_Element_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Element_ID);
    if (ii == null) return 0;
    return ii;
  }

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

}
