package org.compiere.production;

import org.compiere.model.I_M_ChangeRequest;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_ChangeRequest
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_ChangeRequest extends BasePOName implements I_M_ChangeRequest, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_ChangeRequest(Properties ctx, int M_ChangeRequest_ID, String trxName) {
    super(ctx, M_ChangeRequest_ID, trxName);
  }

  /** Load Constructor */
  public X_M_ChangeRequest(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_M_ChangeRequest[").append(getId()).append("]");
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
   * Set Detail Information.
   *
   * @param DetailInfo Additional Detail Information
   */
  public void setDetailInfo(String DetailInfo) {
    set_Value(COLUMNNAME_DetailInfo, DetailInfo);
  }

  /**
   * Get Detail Information.
   *
   * @return Additional Detail Information
   */
  public String getDetailInfo() {
    return (String) get_Value(COLUMNNAME_DetailInfo);
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

  /**
   * Set Approved.
   *
   * @param IsApproved Indicates if this document requires approval
   */
  public void setIsApproved(boolean IsApproved) {
    set_Value(COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
  }

  /**
   * Get Approved.
   *
   * @return Indicates if this document requires approval
   */
  public boolean isApproved() {
    Object oo = get_Value(COLUMNNAME_IsApproved);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  public org.compiere.model.I_M_ChangeNotice getM_ChangeNotice() throws RuntimeException {
    return (org.compiere.model.I_M_ChangeNotice)
        MTable.get(getCtx(), org.compiere.model.I_M_ChangeNotice.Table_Name)
            .getPO(getM_ChangeNotice_ID(), get_TrxName());
  }

  /**
   * Set Change Notice.
   *
   * @param M_ChangeNotice_ID Bill of Materials (Engineering) Change Notice (Version)
   */
  public void setM_ChangeNotice_ID(int M_ChangeNotice_ID) {
    if (M_ChangeNotice_ID < 1) set_ValueNoCheck(COLUMNNAME_M_ChangeNotice_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_ChangeNotice_ID, Integer.valueOf(M_ChangeNotice_ID));
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

  /**
   * Set Change Request.
   *
   * @param M_ChangeRequest_ID BOM (Engineering) Change Request
   */
  public void setM_ChangeRequest_ID(int M_ChangeRequest_ID) {
    if (M_ChangeRequest_ID < 1) set_ValueNoCheck(COLUMNNAME_M_ChangeRequest_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_ChangeRequest_ID, Integer.valueOf(M_ChangeRequest_ID));
  }

  /**
   * Get Change Request.
   *
   * @return BOM (Engineering) Change Request
   */
  public int getM_ChangeRequest_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_ChangeRequest_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set M_ChangeRequest_UU.
   *
   * @param M_ChangeRequest_UU M_ChangeRequest_UU
   */
  public void setM_ChangeRequest_UU(String M_ChangeRequest_UU) {
    set_Value(COLUMNNAME_M_ChangeRequest_UU, M_ChangeRequest_UU);
  }

  /**
   * Get M_ChangeRequest_UU.
   *
   * @return M_ChangeRequest_UU
   */
  public String getM_ChangeRequest_UU() {
    return (String) get_Value(COLUMNNAME_M_ChangeRequest_UU);
  }

  public org.compiere.model.I_M_ChangeNotice getM_FixChangeNotice() throws RuntimeException {
    return (org.compiere.model.I_M_ChangeNotice)
        MTable.get(getCtx(), org.compiere.model.I_M_ChangeNotice.Table_Name)
            .getPO(getM_FixChangeNotice_ID(), get_TrxName());
  }

  /**
   * Set Fixed in.
   *
   * @param M_FixChangeNotice_ID Fixed in Change Notice
   */
  public void setM_FixChangeNotice_ID(int M_FixChangeNotice_ID) {
    if (M_FixChangeNotice_ID < 1) set_ValueNoCheck(COLUMNNAME_M_FixChangeNotice_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_FixChangeNotice_ID, Integer.valueOf(M_FixChangeNotice_ID));
  }

  /**
   * Get Fixed in.
   *
   * @return Fixed in Change Notice
   */
  public int getM_FixChangeNotice_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_FixChangeNotice_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM() throws RuntimeException {
    return (org.eevolution.model.I_PP_Product_BOM)
        MTable.get(getCtx(), org.eevolution.model.I_PP_Product_BOM.Table_Name)
            .getPO(getPP_Product_BOM_ID(), get_TrxName());
  }

  /**
   * Set BOM & Formula.
   *
   * @param PP_Product_BOM_ID BOM & Formula
   */
  public void setPP_Product_BOM_ID(int PP_Product_BOM_ID) {
    if (PP_Product_BOM_ID < 1) set_ValueNoCheck(COLUMNNAME_PP_Product_BOM_ID, null);
    else set_ValueNoCheck(COLUMNNAME_PP_Product_BOM_ID, Integer.valueOf(PP_Product_BOM_ID));
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

  @Override
  public int getTableId() {
    return I_M_ChangeRequest.Table_ID;
  }
}
