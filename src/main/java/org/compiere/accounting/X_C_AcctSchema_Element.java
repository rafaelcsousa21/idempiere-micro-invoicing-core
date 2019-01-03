package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_AcctSchema_Element;
import org.compiere.model.I_C_Location;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
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

  public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException {
    return (org.compiere.model.I_AD_Column)
        MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_Name)
            .getPO(getAD_Column_ID(), null);
  }

  /**
   * Set Column.
   *
   * @param AD_Column_ID Column in the table
   */
  public void setAD_Column_ID(int AD_Column_ID) {
    if (AD_Column_ID < 1) set_Value(COLUMNNAME_AD_Column_ID, null);
    else set_Value(COLUMNNAME_AD_Column_ID, AD_Column_ID);
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
   * Set Acct.Schema Element.
   *
   * @param C_AcctSchema_Element_ID Acct.Schema Element
   */
  public void setC_AcctSchema_Element_ID(int C_AcctSchema_Element_ID) {
    if (C_AcctSchema_Element_ID < 1) set_ValueNoCheck(COLUMNNAME_C_AcctSchema_Element_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_AcctSchema_Element_ID, C_AcctSchema_Element_ID);
  }

  /**
   * Get Acct.Schema Element.
   *
   * @return Acct.Schema Element
   */
  public int getC_AcctSchema_Element_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_AcctSchema_Element_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_AcctSchema_Element_UU.
   *
   * @param C_AcctSchema_Element_UU C_AcctSchema_Element_UU
   */
  public void setC_AcctSchema_Element_UU(String C_AcctSchema_Element_UU) {
    set_Value(COLUMNNAME_C_AcctSchema_Element_UU, C_AcctSchema_Element_UU);
  }

  /**
   * Get C_AcctSchema_Element_UU.
   *
   * @return C_AcctSchema_Element_UU
   */
  public String getC_AcctSchema_Element_UU() {
    return (String) get_Value(COLUMNNAME_C_AcctSchema_Element_UU);
  }

  public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException {
    return (org.compiere.model.I_C_AcctSchema)
        MTable.get(getCtx(), org.compiere.model.I_C_AcctSchema.Table_Name)
            .getPO(getC_AcctSchema_ID(), null);
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

  public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException {
    return (org.compiere.model.I_C_Activity)
        MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
            .getPO(getC_Activity_ID(), null);
  }

  /**
   * Set Activity.
   *
   * @param C_Activity_ID Business Activity
   */
  public void setC_Activity_ID(int C_Activity_ID) {
    if (C_Activity_ID < 1) set_Value(COLUMNNAME_C_Activity_ID, null);
    else set_Value(COLUMNNAME_C_Activity_ID, C_Activity_ID);
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

  public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException {
    return (org.compiere.model.I_C_BPartner)
        MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
            .getPO(getC_BPartner_ID(), null);
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

  public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException {
    return (org.compiere.model.I_C_Campaign)
        MTable.get(getCtx(), org.compiere.model.I_C_Campaign.Table_Name)
            .getPO(getC_Campaign_ID(), null);
  }

  /**
   * Set Campaign.
   *
   * @param C_Campaign_ID Marketing Campaign
   */
  public void setC_Campaign_ID(int C_Campaign_ID) {
    if (C_Campaign_ID < 1) set_Value(COLUMNNAME_C_Campaign_ID, null);
    else set_Value(COLUMNNAME_C_Campaign_ID, C_Campaign_ID);
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

  public org.compiere.model.I_C_Element getC_Element() throws RuntimeException {
    return (org.compiere.model.I_C_Element)
        MTable.get(getCtx(), org.compiere.model.I_C_Element.Table_Name)
            .getPO(getC_Element_ID(), null);
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

  public I_C_Location getC_Location() throws RuntimeException {
    return (I_C_Location)
        MTable.get(getCtx(), I_C_Location.Table_Name).getPO(getC_Location_ID(), null);
  }

  /**
   * Set Address.
   *
   * @param C_Location_ID Location or Address
   */
  public void setC_Location_ID(int C_Location_ID) {
    if (C_Location_ID < 1) set_Value(COLUMNNAME_C_Location_ID, null);
    else set_Value(COLUMNNAME_C_Location_ID, C_Location_ID);
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

  public org.compiere.model.I_C_Project getC_Project() throws RuntimeException {
    return (org.compiere.model.I_C_Project)
        MTable.get(getCtx(), org.compiere.model.I_C_Project.Table_Name)
            .getPO(getC_Project_ID(), null);
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

  public org.compiere.model.I_C_SalesRegion getC_SalesRegion() throws RuntimeException {
    return (org.compiere.model.I_C_SalesRegion)
        MTable.get(getCtx(), org.compiere.model.I_C_SalesRegion.Table_Name)
            .getPO(getC_SalesRegion_ID(), null);
  }

  /**
   * Set Sales Region.
   *
   * @param C_SalesRegion_ID Sales coverage region
   */
  public void setC_SalesRegion_ID(int C_SalesRegion_ID) {
    if (C_SalesRegion_ID < 1) set_Value(COLUMNNAME_C_SalesRegion_ID, null);
    else set_Value(COLUMNNAME_C_SalesRegion_ID, C_SalesRegion_ID);
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

  /** ElementType AD_Reference_ID=181 */
  public static final int ELEMENTTYPE_AD_Reference_ID = 181;
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

  public org.compiere.model.I_M_Product getM_Product() throws RuntimeException {
    return (org.compiere.model.I_M_Product)
        MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
            .getPO(getM_Product_ID(), null);
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
