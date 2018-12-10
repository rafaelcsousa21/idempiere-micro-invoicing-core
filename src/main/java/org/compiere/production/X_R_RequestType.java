package org.compiere.production;

import org.compiere.model.I_R_RequestType;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for R_RequestType
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_RequestType extends BasePOName implements I_R_RequestType, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_RequestType(Properties ctx, int R_RequestType_ID, String trxName) {
    super(ctx, R_RequestType_ID, trxName);
    /**
     * if (R_RequestType_ID == 0) { setConfidentialType (null); // C setDueDateTolerance (0); // 7
     * setIsAutoChangeRequest (false); setIsConfidentialInfo (false); // N setIsDefault (false); //
     * N setIsEMailWhenDue (false); setIsEMailWhenOverdue (false); setIsIndexed (false);
     * setIsSelfService (true); // Y setName (null); setR_RequestType_ID (0); setR_StatusCategory_ID
     * (0); }
     */
  }

  /** Load Constructor */
  public X_R_RequestType(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_R_RequestType[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Auto Due Date Days.
   *
   * @param AutoDueDateDays Automatic Due Date Days
   */
  public void setAutoDueDateDays(int AutoDueDateDays) {
    set_Value(COLUMNNAME_AutoDueDateDays, Integer.valueOf(AutoDueDateDays));
  }

  /**
   * Get Auto Due Date Days.
   *
   * @return Automatic Due Date Days
   */
  public int getAutoDueDateDays() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AutoDueDateDays);
    if (ii == null) return 0;
    return ii;
  }

  /** ConfidentialType AD_Reference_ID=340 */
  public static final int CONFIDENTIALTYPE_AD_Reference_ID = 340;
  /** Public Information = A */
  public static final String CONFIDENTIALTYPE_PublicInformation = "A";
  /** Partner Confidential = C */
  public static final String CONFIDENTIALTYPE_PartnerConfidential = "C";
  /** Internal = I */
  public static final String CONFIDENTIALTYPE_Internal = "I";
  /** Private Information = P */
  public static final String CONFIDENTIALTYPE_PrivateInformation = "P";
  /**
   * Set Confidentiality.
   *
   * @param ConfidentialType Type of Confidentiality
   */
  public void setConfidentialType(String ConfidentialType) {

    set_Value(COLUMNNAME_ConfidentialType, ConfidentialType);
  }

  /**
   * Get Confidentiality.
   *
   * @return Type of Confidentiality
   */
  public String getConfidentialType() {
    return (String) get_Value(COLUMNNAME_ConfidentialType);
  }

  /**
   * Set Content Color.
   *
   * @param ContentColor Content color of calendar dashlet
   */
  public void setContentColor(String ContentColor) {
    set_Value(COLUMNNAME_ContentColor, ContentColor);
  }

  /**
   * Get Content Color.
   *
   * @return Content color of calendar dashlet
   */
  public String getContentColor() {
    return (String) get_Value(COLUMNNAME_ContentColor);
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
   * Set Due Date Tolerance.
   *
   * @param DueDateTolerance Tolerance in days between the Date Next Action and the date the request
   *     is regarded as overdue
   */
  public void setDueDateTolerance(int DueDateTolerance) {
    set_Value(COLUMNNAME_DueDateTolerance, Integer.valueOf(DueDateTolerance));
  }

  /**
   * Get Due Date Tolerance.
   *
   * @return Tolerance in days between the Date Next Action and the date the request is regarded as
   *     overdue
   */
  public int getDueDateTolerance() {
    Integer ii = (Integer) get_Value(COLUMNNAME_DueDateTolerance);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Header Color.
   *
   * @param HeaderColor Header color of calendar dashlet
   */
  public void setHeaderColor(String HeaderColor) {
    set_Value(COLUMNNAME_HeaderColor, HeaderColor);
  }

  /**
   * Get Header Color.
   *
   * @return Header color of calendar dashlet
   */
  public String getHeaderColor() {
    return (String) get_Value(COLUMNNAME_HeaderColor);
  }

  /**
   * Set Create Change Request.
   *
   * @param IsAutoChangeRequest Automatically create BOM (Engineering) Change Request
   */
  public void setIsAutoChangeRequest(boolean IsAutoChangeRequest) {
    set_Value(COLUMNNAME_IsAutoChangeRequest, Boolean.valueOf(IsAutoChangeRequest));
  }

  /**
   * Get Create Change Request.
   *
   * @return Automatically create BOM (Engineering) Change Request
   */
  public boolean isAutoChangeRequest() {
    Object oo = get_Value(COLUMNNAME_IsAutoChangeRequest);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Confidential Info.
   *
   * @param IsConfidentialInfo Can enter confidential information
   */
  public void setIsConfidentialInfo(boolean IsConfidentialInfo) {
    set_Value(COLUMNNAME_IsConfidentialInfo, Boolean.valueOf(IsConfidentialInfo));
  }

  /**
   * Get Confidential Info.
   *
   * @return Can enter confidential information
   */
  public boolean isConfidentialInfo() {
    Object oo = get_Value(COLUMNNAME_IsConfidentialInfo);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
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
   * Set EMail when Due.
   *
   * @param IsEMailWhenDue Send EMail when Request becomes due
   */
  public void setIsEMailWhenDue(boolean IsEMailWhenDue) {
    set_Value(COLUMNNAME_IsEMailWhenDue, Boolean.valueOf(IsEMailWhenDue));
  }

  /**
   * Get EMail when Due.
   *
   * @return Send EMail when Request becomes due
   */
  public boolean isEMailWhenDue() {
    Object oo = get_Value(COLUMNNAME_IsEMailWhenDue);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set EMail when Overdue.
   *
   * @param IsEMailWhenOverdue Send EMail when Request becomes overdue
   */
  public void setIsEMailWhenOverdue(boolean IsEMailWhenOverdue) {
    set_Value(COLUMNNAME_IsEMailWhenOverdue, Boolean.valueOf(IsEMailWhenOverdue));
  }

  /**
   * Get EMail when Overdue.
   *
   * @return Send EMail when Request becomes overdue
   */
  public boolean isEMailWhenOverdue() {
    Object oo = get_Value(COLUMNNAME_IsEMailWhenOverdue);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Indexed.
   *
   * @param IsIndexed Index the document for the internal search engine
   */
  public void setIsIndexed(boolean IsIndexed) {
    set_Value(COLUMNNAME_IsIndexed, Boolean.valueOf(IsIndexed));
  }

  /**
   * Get Indexed.
   *
   * @return Index the document for the internal search engine
   */
  public boolean isIndexed() {
    Object oo = get_Value(COLUMNNAME_IsIndexed);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Invoiced.
   *
   * @param IsInvoiced Is this invoiced?
   */
  public void setIsInvoiced(boolean IsInvoiced) {
    set_Value(COLUMNNAME_IsInvoiced, Boolean.valueOf(IsInvoiced));
  }

  /**
   * Get Invoiced.
   *
   * @return Is this invoiced?
   */
  public boolean isInvoiced() {
    Object oo = get_Value(COLUMNNAME_IsInvoiced);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Self-Service.
   *
   * @param IsSelfService This is a Self-Service entry or this entry can be changed via Self-Service
   */
  public void setIsSelfService(boolean IsSelfService) {
    set_Value(COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
  }

  /**
   * Get Self-Service.
   *
   * @return This is a Self-Service entry or this entry can be changed via Self-Service
   */
  public boolean isSelfService() {
    Object oo = get_Value(COLUMNNAME_IsSelfService);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Request Type.
   *
   * @param R_RequestType_ID Type of request (e.g. Inquiry, Complaint, ..)
   */
  public void setR_RequestType_ID(int R_RequestType_ID) {
    if (R_RequestType_ID < 1) set_ValueNoCheck(COLUMNNAME_R_RequestType_ID, null);
    else set_ValueNoCheck(COLUMNNAME_R_RequestType_ID, Integer.valueOf(R_RequestType_ID));
  }

  /**
   * Get Request Type.
   *
   * @return Type of request (e.g. Inquiry, Complaint, ..)
   */
  public int getR_RequestType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_RequestType_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set R_RequestType_UU.
   *
   * @param R_RequestType_UU R_RequestType_UU
   */
  public void setR_RequestType_UU(String R_RequestType_UU) {
    set_Value(COLUMNNAME_R_RequestType_UU, R_RequestType_UU);
  }

  /**
   * Get R_RequestType_UU.
   *
   * @return R_RequestType_UU
   */
  public String getR_RequestType_UU() {
    return (String) get_Value(COLUMNNAME_R_RequestType_UU);
  }

  public org.compiere.model.I_R_StatusCategory getR_StatusCategory() throws RuntimeException {
    return (org.compiere.model.I_R_StatusCategory)
        MTable.get(getCtx(), org.compiere.model.I_R_StatusCategory.Table_Name)
            .getPO(getR_StatusCategory_ID(), get_TrxName());
  }

  /**
   * Set Status Category.
   *
   * @param R_StatusCategory_ID Request Status Category
   */
  public void setR_StatusCategory_ID(int R_StatusCategory_ID) {
    if (R_StatusCategory_ID < 1) set_Value(COLUMNNAME_R_StatusCategory_ID, null);
    else set_Value(COLUMNNAME_R_StatusCategory_ID, Integer.valueOf(R_StatusCategory_ID));
  }

  /**
   * Get Status Category.
   *
   * @return Request Status Category
   */
  public int getR_StatusCategory_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_StatusCategory_ID);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_R_RequestType.Table_ID;
  }
}
