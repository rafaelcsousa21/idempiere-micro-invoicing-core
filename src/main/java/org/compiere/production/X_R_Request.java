package org.compiere.production;

import org.compiere.model.I_R_Request;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for R_Request
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_Request extends PO implements I_R_Request, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_Request(Properties ctx, int R_Request_ID, String trxName) {
    super(ctx, R_Request_ID, trxName);
    /**
     * if (R_Request_ID == 0) { setConfidentialType (null); // C setConfidentialTypeEntry (null); //
     * C setDocumentNo (null); setDueType (null); // 5 setIsEscalated (false); setIsInvoiced
     * (false); setIsSelfService (false); // N setPriority (null); // 5 setProcessed (false);
     * setRequestAmt (Env.ZERO); setR_Request_ID (0); setR_RequestType_ID (0); setSummary (null); }
     */
  }

  /** Load Constructor */
  public X_R_Request(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_R_Request[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Asset.
   *
   * @return Asset used internally or by customers
   */
  public int getA_Asset_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Role.
   *
   * @return Responsibility Role
   */
  public int getAD_Role_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Role_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Table.
   *
   * @return Database Table information
   */
  public int getAD_Table_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Table_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get User/Contact.
   *
   * @return User within the system - Internal or Business Partner Contact
   */
  public int getAD_User_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_User_ID);
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
   * Get Invoice.
   *
   * @return Invoice Identifier
   */
  public int getC_Invoice_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Invoice_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Request Invoice.
   *
   * @return The generated invoice for this request
   */
  public int getC_InvoiceRequest_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_InvoiceRequest_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Close Date.
   *
   * @param CloseDate Close Date
   */
  public void setCloseDate(Timestamp CloseDate) {
    set_Value(COLUMNNAME_CloseDate, CloseDate);
  }

  /**
   * Get Close Date.
   *
   * @return Close Date
   */
  public Timestamp getCloseDate() {
    return (Timestamp) get_Value(COLUMNNAME_CloseDate);
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

  /** ConfidentialTypeEntry AD_Reference_ID=340 */
  public static final int CONFIDENTIALTYPEENTRY_AD_Reference_ID = 340;
  /** Public Information = A */
  public static final String CONFIDENTIALTYPEENTRY_PublicInformation = "A";
  /** Partner Confidential = C */
  public static final String CONFIDENTIALTYPEENTRY_PartnerConfidential = "C";
  /** Internal = I */
  public static final String CONFIDENTIALTYPEENTRY_Internal = "I";
  /** Private Information = P */
  public static final String CONFIDENTIALTYPEENTRY_PrivateInformation = "P";
  /**
   * Set Entry Confidentiality.
   *
   * @param ConfidentialTypeEntry Confidentiality of the individual entry
   */
  public void setConfidentialTypeEntry(String ConfidentialTypeEntry) {

    set_Value(COLUMNNAME_ConfidentialTypeEntry, ConfidentialTypeEntry);
  }

  /**
   * Get Entry Confidentiality.
   *
   * @return Confidentiality of the individual entry
   */
  public String getConfidentialTypeEntry() {
    return (String) get_Value(COLUMNNAME_ConfidentialTypeEntry);
  }

    /**
   * Get Order.
   *
   * @return Order
   */
  public int getC_Order_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Order_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Payment.
   *
   * @return Payment identifier
   */
  public int getC_Payment_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Payment_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set Last Alert.
   *
   * @param DateLastAlert Date when last alert were sent
   */
  public void setDateLastAlert(Timestamp DateLastAlert) {
    set_Value(COLUMNNAME_DateLastAlert, DateLastAlert);
  }

    /**
   * Set Date next action.
   *
   * @param DateNextAction Date that this request should be acted on
   */
  public void setDateNextAction(Timestamp DateNextAction) {
    set_Value(COLUMNNAME_DateNextAction, DateNextAction);
  }

  /**
   * Get Date next action.
   *
   * @return Date that this request should be acted on
   */
  public Timestamp getDateNextAction() {
    return (Timestamp) get_Value(COLUMNNAME_DateNextAction);
  }

    /**
   * Get Document No.
   *
   * @return Document sequence number of the document
   */
  public String getDocumentNo() {
    return (String) get_Value(COLUMNNAME_DocumentNo);
  }

    /** DueType AD_Reference_ID=222 */
  public static final int DUETYPE_AD_Reference_ID = 222;
  /** Overdue = 3 */
  public static final String DUETYPE_Overdue = "3";
  /** Due = 5 */
  public static final String DUETYPE_Due = "5";
  /** Scheduled = 7 */
  public static final String DUETYPE_Scheduled = "7";
  /**
   * Set Due type.
   *
   * @param DueType Status of the next action for this Request
   */
  public void setDueType(String DueType) {

    set_Value(COLUMNNAME_DueType, DueType);
  }

  /**
   * Get Due type.
   *
   * @return Status of the next action for this Request
   */
  public String getDueType() {
    return (String) get_Value(COLUMNNAME_DueType);
  }

    /**
   * Set Escalated.
   *
   * @param IsEscalated This request has been escalated
   */
  public void setIsEscalated(boolean IsEscalated) {
    set_Value(COLUMNNAME_IsEscalated, Boolean.valueOf(IsEscalated));
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
    set_ValueNoCheck(COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
  }

    /**
   * Set Change Request.
   *
   * @param M_ChangeRequest_ID BOM (Engineering) Change Request
   */
  public void setM_ChangeRequest_ID(int M_ChangeRequest_ID) {
    if (M_ChangeRequest_ID < 1) set_Value(COLUMNNAME_M_ChangeRequest_ID, null);
    else set_Value(COLUMNNAME_M_ChangeRequest_ID, Integer.valueOf(M_ChangeRequest_ID));
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
   * Get Fixed in.
   *
   * @return Fixed in Change Notice
   */
  public int getM_FixChangeNotice_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_FixChangeNotice_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Shipment/Receipt.
   *
   * @return Material Shipment Document
   */
  public int getM_InOut_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_InOut_ID);
    if (ii == null) return 0;
    return ii;
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
   * Get Product Used.
   *
   * @return Product/Resource/Service used in Request
   */
  public int getM_ProductSpent_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_ProductSpent_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get RMA.
   *
   * @return Return Material Authorization
   */
  public int getM_RMA_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_RMA_ID);
    if (ii == null) return 0;
    return ii;
  }

    /** Priority AD_Reference_ID=154 */
  public static final int PRIORITY_AD_Reference_ID = 154;
  /** High = 3 */
  public static final String PRIORITY_High = "3";
  /** Medium = 5 */
  public static final String PRIORITY_Medium = "5";
  /** Low = 7 */
  public static final String PRIORITY_Low = "7";
  /** Urgent = 1 */
  public static final String PRIORITY_Urgent = "1";
  /** Minor = 9 */
  public static final String PRIORITY_Minor = "9";
  /**
   * Set Priority.
   *
   * @param Priority Indicates if this request is of a high, medium or low priority.
   */
  public void setPriority(String Priority) {

    set_Value(COLUMNNAME_Priority, Priority);
  }

  /**
   * Get Priority.
   *
   * @return Indicates if this request is of a high, medium or low priority.
   */
  public String getPriority() {
    return (String) get_Value(COLUMNNAME_Priority);
  }

  /** PriorityUser AD_Reference_ID=154 */
  public static final int PRIORITYUSER_AD_Reference_ID = 154;
  /** High = 3 */
  public static final String PRIORITYUSER_High = "3";
  /** Medium = 5 */
  public static final String PRIORITYUSER_Medium = "5";
  /** Low = 7 */
  public static final String PRIORITYUSER_Low = "7";
  /** Urgent = 1 */
  public static final String PRIORITYUSER_Urgent = "1";
  /** Minor = 9 */
  public static final String PRIORITYUSER_Minor = "9";
  /**
   * Set User Importance.
   *
   * @param PriorityUser Priority of the issue for the User
   */
  public void setPriorityUser(String PriorityUser) {

    set_Value(COLUMNNAME_PriorityUser, PriorityUser);
  }

  /**
   * Get User Importance.
   *
   * @return Priority of the issue for the User
   */
  public String getPriorityUser() {
    return (String) get_Value(COLUMNNAME_PriorityUser);
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
   * Get Category.
   *
   * @return Request Category
   */
  public int getR_Category_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Category_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Request Amount.
   *
   * @param RequestAmt Amount associated with this request
   */
  public void setRequestAmt(BigDecimal RequestAmt) {
    set_Value(COLUMNNAME_RequestAmt, RequestAmt);
  }

    /**
   * Set Result.
   *
   * @param Result Result of the action taken
   */
  public void setResult(String Result) {
    set_Value(COLUMNNAME_Result, Result);
  }

  /**
   * Get Result.
   *
   * @return Result of the action taken
   */
  public String getResult() {
    return (String) get_Value(COLUMNNAME_Result);
  }

    /**
   * Get Group.
   *
   * @return Request Group
   */
  public int getR_Group_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Group_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Mail Template.
   *
   * @return Text templates for mailings
   */
  public int getR_MailText_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_MailText_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Request.
   *
   * @return Request from a Business Partner or Prospect
   */
  public int getR_Request_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Request_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Related Request.
   *
   * @return Related Request (Master Issue, ..)
   */
  public int getR_RequestRelated_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_RequestRelated_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Request Type.
   *
   * @param R_RequestType_ID Type of request (e.g. Inquiry, Complaint, ..)
   */
  public void setR_RequestType_ID(int R_RequestType_ID) {
    if (R_RequestType_ID < 1) set_Value(COLUMNNAME_R_RequestType_ID, null);
    else set_Value(COLUMNNAME_R_RequestType_ID, Integer.valueOf(R_RequestType_ID));
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
   * Get Standard Response.
   *
   * @return Request Standard Response
   */
  public int getR_StandardResponse_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_StandardResponse_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Status.
   *
   * @param R_Status_ID Request Status
   */
  public void setR_Status_ID(int R_Status_ID) {
    if (R_Status_ID < 1) set_Value(COLUMNNAME_R_Status_ID, null);
    else set_Value(COLUMNNAME_R_Status_ID, Integer.valueOf(R_Status_ID));
  }

  /**
   * Get Status.
   *
   * @return Request Status
   */
  public int getR_Status_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Status_ID);
    if (ii == null) return 0;
    return ii;
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

  /**
   * Set Start Date.
   *
   * @param StartDate First effective day (inclusive)
   */
  public void setStartDate(Timestamp StartDate) {
    set_Value(COLUMNNAME_StartDate, StartDate);
  }

  /**
   * Get Start Date.
   *
   * @return First effective day (inclusive)
   */
  public Timestamp getStartDate() {
    return (Timestamp) get_Value(COLUMNNAME_StartDate);
  }

    /**
   * Set Summary.
   *
   * @param Summary Textual summary of this request
   */
  public void setSummary(String Summary) {
    set_Value(COLUMNNAME_Summary, Summary);
  }

  /**
   * Get Summary.
   *
   * @return Textual summary of this request
   */
  public String getSummary() {
    return (String) get_Value(COLUMNNAME_Summary);
  }

    @Override
  public int getTableId() {
    return I_R_Request.Table_ID;
  }
}
