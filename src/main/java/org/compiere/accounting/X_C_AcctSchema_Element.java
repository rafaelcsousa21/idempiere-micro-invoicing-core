package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_AcctSchema_Element;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_AcctSchema_Element
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_AcctSchema_Element extends BasePOName
    implements I_C_AcctSchema_Element, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_AcctSchema_Element(Properties ctx, int C_AcctSchema_Element_ID, String trxName) {
    super(ctx, C_AcctSchema_Element_ID, trxName);
  }

  /** Load Constructor */
  public X_C_AcctSchema_Element(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }
  public X_C_AcctSchema_Element(Properties ctx, Row row) {
    super(ctx, row);
  } //	MAcctSchemaElement


  /**
   * AccessLevel
   *
   * @return 2 - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    return "X_C_AcctSchema_Element[" + getId() + "]";
  }

    /**
   * Get Column.
   *
   * @return Column in the table
   */
  public int getAD_Column_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Column_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Accounting Schema.
   *
   * @param C_AcctSchema_ID Rules for accounting
   */
  public void setC_AcctSchema_ID(int C_AcctSchema_ID) {
    if (C_AcctSchema_ID < 1) set_ValueNoCheck(COLUMNNAME_C_AcctSchema_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
  }

  /**
   * Get Accounting Schema.
   *
   * @return Rules for accounting
   */
  public int getC_AcctSchema_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_AcctSchema_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set Business Partner .
   *
   * @param C_BPartner_ID Identifies a Business Partner
   */
  public void setC_BPartner_ID(int C_BPartner_ID) {
    if (C_BPartner_ID < 1) set_Value(COLUMNNAME_C_BPartner_ID, null);
    else set_Value(COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
  }

  /**
   * Get Business Partner .
   *
   * @return Identifies a Business Partner
   */
  public int getC_BPartner_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BPartner_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Campaign.
   *
   * @return Marketing Campaign
   */
  public int getC_Campaign_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Campaign_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Element.
   *
   * @param C_Element_ID Accounting Element
   */
  public void setC_Element_ID(int C_Element_ID) {
    if (C_Element_ID < 1) set_Value(COLUMNNAME_C_Element_ID, null);
    else set_Value(COLUMNNAME_C_Element_ID, C_Element_ID);
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
   * Set Account Element.
   *
   * @param C_ElementValue_ID Account Element
   */
  public void setC_ElementValue_ID(int C_ElementValue_ID) {
    if (C_ElementValue_ID < 1) set_Value(COLUMNNAME_C_ElementValue_ID, null);
    else set_Value(COLUMNNAME_C_ElementValue_ID, C_ElementValue_ID);
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
   * Set Project.
   *
   * @param C_Project_ID Financial Project
   */
  public void setC_Project_ID(int C_Project_ID) {
    if (C_Project_ID < 1) set_Value(COLUMNNAME_C_Project_ID, null);
    else set_Value(COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
  }

  /**
   * Get Project.
   *
   * @return Financial Project
   */
  public int getC_Project_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Project_ID);
    if (ii == null) return 0;
    return ii;
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

    /** Organization = OO */
  public static final String ELEMENTTYPE_Organization = "OO";
  /** Account = AC */
  public static final String ELEMENTTYPE_Account = "AC";
  /** Product = PR */
  public static final String ELEMENTTYPE_Product = "PR";
  /** BPartner = BP */
  public static final String ELEMENTTYPE_BPartner = "BP";
  /** Org Trx = OT */
  public static final String ELEMENTTYPE_OrgTrx = "OT";
  /** Location From = LF */
  public static final String ELEMENTTYPE_LocationFrom = "LF";
  /** Location To = LT */
  public static final String ELEMENTTYPE_LocationTo = "LT";
  /** Sales Region = SR */
  public static final String ELEMENTTYPE_SalesRegion = "SR";
  /** Project = PJ */
  public static final String ELEMENTTYPE_Project = "PJ";
  /** Campaign = MC */
  public static final String ELEMENTTYPE_Campaign = "MC";
  /** User Element List 1 = U1 */
  public static final String ELEMENTTYPE_UserElementList1 = "U1";
  /** User Element List 2 = U2 */
  public static final String ELEMENTTYPE_UserElementList2 = "U2";
  /** Activity = AY */
  public static final String ELEMENTTYPE_Activity = "AY";
  /** Sub Account = SA */
  public static final String ELEMENTTYPE_SubAccount = "SA";
  /** User Column 1 = X1 */
  public static final String ELEMENTTYPE_UserColumn1 = "X1";
  /** User Column 2 = X2 */
  public static final String ELEMENTTYPE_UserColumn2 = "X2";
  /**
   * Set Type.
   *
   * @param ElementType Element Type (account or user defined)
   */
  public void setElementType(String ElementType) {

    set_Value(COLUMNNAME_ElementType, ElementType);
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
   * Set Balanced.
   *
   * @param IsBalanced Balanced
   */
  public void setIsBalanced(boolean IsBalanced) {
    set_Value(COLUMNNAME_IsBalanced, IsBalanced);
  }

  /**
   * Get Balanced.
   *
   * @return Balanced
   */
  public boolean isBalanced() {
    Object oo = get_Value(COLUMNNAME_IsBalanced);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Mandatory.
   *
   * @param IsMandatory Data entry is required in this column
   */
  public void setIsMandatory(boolean IsMandatory) {
    set_Value(COLUMNNAME_IsMandatory, IsMandatory);
  }

  /**
   * Get Mandatory.
   *
   * @return Data entry is required in this column
   */
  public boolean isMandatory() {
    Object oo = get_Value(COLUMNNAME_IsMandatory);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Set Product.
   *
   * @param M_Product_ID Product, Service, Item
   */
  public void setM_Product_ID(int M_Product_ID) {
    if (M_Product_ID < 1) set_Value(COLUMNNAME_M_Product_ID, null);
    else set_Value(COLUMNNAME_M_Product_ID, M_Product_ID);
  }

  /**
   * Get Product.
   *
   * @return Product, Service, Item
   */
  public int getM_Product_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Product_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Organization.
   *
   * @param Org_ID Organizational entity within client
   */
  public void setOrg_ID(int Org_ID) {
    if (Org_ID < 1) set_Value(COLUMNNAME_Org_ID, null);
    else set_Value(COLUMNNAME_Org_ID, Org_ID);
  }

  /**
   * Get Organization.
   *
   * @return Organizational entity within client
   */
  public int getOrg_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Org_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Sequence.
   *
   * @param SeqNo Method of ordering records; lowest number comes first
   */
  public void setSeqNo(int SeqNo) {
    set_Value(COLUMNNAME_SeqNo, SeqNo);
  }

  /**
   * Get Sequence.
   *
   * @return Method of ordering records; lowest number comes first
   */
  public int getSeqNo() {
    Integer ii = (Integer) get_Value(COLUMNNAME_SeqNo);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_C_AcctSchema_Element.Table_ID;
  }
}
