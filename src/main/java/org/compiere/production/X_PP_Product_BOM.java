package org.compiere.production;

import org.compiere.model.HasName;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.eevolution.model.I_PP_Product_BOM;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for PP_Product_BOM
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_PP_Product_BOM extends PO implements I_PP_Product_BOM, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_PP_Product_BOM(Properties ctx, int PP_Product_BOM_ID) {
    super(ctx, PP_Product_BOM_ID);
    /**
     * if (PP_Product_BOM_ID == 0) { setM_Product_ID (0); setName (null); setPP_Product_BOM_ID (0);
     * setValidFrom (new Timestamp( System.currentTimeMillis() )); // @#Date@ setValue (null); }
     */
  }

  /** Load Constructor */
  public X_PP_Product_BOM(Properties ctx, ResultSet rs) {
    super(ctx, rs);
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
    StringBuffer sb = new StringBuffer("X_PP_Product_BOM[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Set BOM Type.
   *
   * @param BOMType Type of BOM
   */
  public void setBOMType(String BOMType) {

    set_Value(COLUMNNAME_BOMType, BOMType);
  }

  /**
   * Get BOM Type.
   *
   * @return Type of BOM
   */
  public String getBOMType() {
    return (String) get_Value(COLUMNNAME_BOMType);
  }

    /**
   * Set BOM Use.
   *
   * @param BOMUse The use of the Bill of Material
   */
  public void setBOMUse(String BOMUse) {

    set_Value(COLUMNNAME_BOMUse, BOMUse);
  }

  /**
   * Get BOM Use.
   *
   * @return The use of the Bill of Material
   */
  public String getBOMUse() {
    return (String) get_Value(COLUMNNAME_BOMUse);
  }

  /**
   * Set Copy From.
   *
   * @param CopyFrom Copy From Record
   */
  public void setCopyFrom(String CopyFrom) {
    set_Value(COLUMNNAME_CopyFrom, CopyFrom);
  }

  /**
   * Get Copy From.
   *
   * @return Copy From Record
   */
  public String getCopyFrom() {
    return (String) get_Value(COLUMNNAME_CopyFrom);
  }

  public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException {
    return (org.compiere.model.I_C_UOM)
        MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
            .getPO(getC_UOM_ID());
  }

  /**
   * Set UOM.
   *
   * @param C_UOM_ID Unit of Measure
   */
  public void setC_UOM_ID(int C_UOM_ID) {
    if (C_UOM_ID < 1) set_Value(COLUMNNAME_C_UOM_ID, null);
    else set_Value(COLUMNNAME_C_UOM_ID, C_UOM_ID);
  }

  /**
   * Get UOM.
   *
   * @return Unit of Measure
   */
  public int getC_UOM_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_UOM_ID);
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
   * Set Document No.
   *
   * @param DocumentNo Document sequence number of the document
   */
  public void setDocumentNo(String DocumentNo) {
    set_Value(COLUMNNAME_DocumentNo, DocumentNo);
  }

  /**
   * Get Document No.
   *
   * @return Document sequence number of the document
   */
  public String getDocumentNo() {
    return (String) get_Value(COLUMNNAME_DocumentNo);
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

  public I_M_AttributeSetInstance getMAttributeSetInstance() throws RuntimeException {
    return (I_M_AttributeSetInstance)
        MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
            .getPO(getMAttributeSetInstance_ID());
  }

  /**
   * Set Attribute Set Instance.
   *
   * @param M_AttributeSetInstance_ID Product Attribute Set Instance
   */
  public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
    if (M_AttributeSetInstance_ID < 0) set_Value(COLUMNNAME_M_AttributeSetInstance_ID, null);
    else
      set_Value(COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
  }

  /**
   * Get Attribute Set Instance.
   *
   * @return Product Attribute Set Instance
   */
  public int getMAttributeSetInstance_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_M_ChangeNotice getM_ChangeNotice() throws RuntimeException {
    return (org.compiere.model.I_M_ChangeNotice)
        MTable.get(getCtx(), org.compiere.model.I_M_ChangeNotice.Table_Name)
            .getPO(getM_ChangeNotice_ID());
  }

  /**
   * Set Change Notice.
   *
   * @param M_ChangeNotice_ID Bill of Materials (Engineering) Change Notice (Version)
   */
  public void setM_ChangeNotice_ID(int M_ChangeNotice_ID) {
    if (M_ChangeNotice_ID < 1) set_Value(COLUMNNAME_M_ChangeNotice_ID, null);
    else set_Value(COLUMNNAME_M_ChangeNotice_ID, M_ChangeNotice_ID);
  }

  /**
   * Get Change Notice.
   *
   * @return Bill of Materials (Engineering) Change Notice (Version)
   */
  public int getM_ChangeNotice_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_ChangeNotice_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_M_Product getM_Product() throws RuntimeException {
    return (org.compiere.model.I_M_Product)
        MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
            .getPO(getM_Product_ID());
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
   * Set Name.
   *
   * @param Name Alphanumeric identifier of the entity
   */
  public void setName(String Name) {
    set_Value(HasName.Companion.getCOLUMNNAME_Name(), Name);
  }

  /**
   * Get Name.
   *
   * @return Alphanumeric identifier of the entity
   */
  public String getName() {
    return (String) get_Value(HasName.Companion.getCOLUMNNAME_Name());
  }

  /**
   * Set BOM & Formula.
   *
   * @param PP_Product_BOM_ID BOM & Formula
   */
  public void setPP_Product_BOM_ID(int PP_Product_BOM_ID) {
    if (PP_Product_BOM_ID < 1) set_ValueNoCheck(COLUMNNAME_PP_Product_BOM_ID, null);
    else set_ValueNoCheck(COLUMNNAME_PP_Product_BOM_ID, PP_Product_BOM_ID);
  }

  /**
   * Get BOM & Formula.
   *
   * @return BOM & Formula
   */
  public int getPP_Product_BOM_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PP_Product_BOM_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set PP_Product_BOM_UU.
   *
   * @param PP_Product_BOM_UU PP_Product_BOM_UU
   */
  public void setPP_Product_BOM_UU(String PP_Product_BOM_UU) {
    set_Value(COLUMNNAME_PP_Product_BOM_UU, PP_Product_BOM_UU);
  }

  /**
   * Get PP_Product_BOM_UU.
   *
   * @return PP_Product_BOM_UU
   */
  public String getPP_Product_BOM_UU() {
    return (String) get_Value(COLUMNNAME_PP_Product_BOM_UU);
  }

  /**
   * Set Process Now.
   *
   * @param Processing Process Now
   */
  public void setProcessing(boolean Processing) {
    set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
  }

  /**
   * Get Process Now.
   *
   * @return Process Now
   */
  public boolean isProcessing() {
    Object oo = get_Value(COLUMNNAME_Processing);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Revision.
   *
   * @param Revision Revision
   */
  public void setRevision(String Revision) {
    set_Value(COLUMNNAME_Revision, Revision);
  }

  /**
   * Get Revision.
   *
   * @return Revision
   */
  public String getRevision() {
    return (String) get_Value(COLUMNNAME_Revision);
  }

  /**
   * Set Valid from.
   *
   * @param ValidFrom Valid from including this date (first day)
   */
  public void setValidFrom(Timestamp ValidFrom) {
    set_Value(COLUMNNAME_ValidFrom, ValidFrom);
  }

  /**
   * Get Valid from.
   *
   * @return Valid from including this date (first day)
   */
  public Timestamp getValidFrom() {
    return (Timestamp) get_Value(COLUMNNAME_ValidFrom);
  }

  /**
   * Set Valid to.
   *
   * @param ValidTo Valid to including this date (last day)
   */
  public void setValidTo(Timestamp ValidTo) {
    set_Value(COLUMNNAME_ValidTo, ValidTo);
  }

  /**
   * Get Valid to.
   *
   * @return Valid to including this date (last day)
   */
  public Timestamp getValidTo() {
    return (Timestamp) get_Value(COLUMNNAME_ValidTo);
  }

  /**
   * Set Search Key.
   *
   * @param Value Search key for the record in the format required - must be unique
   */
  public void setValue(String Value) {
    set_Value(COLUMNNAME_Value, Value);
  }

  /**
   * Get Search Key.
   *
   * @return Search key for the record in the format required - must be unique
   */
  public String getValue() {
    return (String) get_Value(COLUMNNAME_Value);
  }

    @Override
  public int getTableId() {
    return I_PP_Product_BOM.Table_ID;
  }
}
