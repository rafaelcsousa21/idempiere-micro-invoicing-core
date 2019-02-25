package org.compiere.production;

import org.compiere.model.I_R_Request;
import org.compiere.orm.PO;

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
public class X_R_Request extends PO implements I_R_Request {

    /**
     * ConfidentialType AD_Reference_ID=340
     */
    public static final int CONFIDENTIALTYPE_AD_Reference_ID = 340;
    /**
     * Public Information = A
     */
    public static final String CONFIDENTIALTYPE_PublicInformation = "A";
    /**
     * Partner Confidential = C
     */
    public static final String CONFIDENTIALTYPE_PartnerConfidential = "C";
    /**
     * Internal = I
     */
    public static final String CONFIDENTIALTYPE_Internal = "I";
    /**
     * Private Information = P
     */
    public static final String CONFIDENTIALTYPE_PrivateInformation = "P";
    /**
     * ConfidentialTypeEntry AD_Reference_ID=340
     */
    public static final int CONFIDENTIALTYPEENTRY_AD_Reference_ID = 340;
    /**
     * Public Information = A
     */
    public static final String CONFIDENTIALTYPEENTRY_PublicInformation = "A";
    /**
     * Partner Confidential = C
     */
    public static final String CONFIDENTIALTYPEENTRY_PartnerConfidential = "C";
    /**
     * Internal = I
     */
    public static final String CONFIDENTIALTYPEENTRY_Internal = "I";
    /**
     * Private Information = P
     */
    public static final String CONFIDENTIALTYPEENTRY_PrivateInformation = "P";
    /**
     * DueType AD_Reference_ID=222
     */
    public static final int DUETYPE_AD_Reference_ID = 222;
    /**
     * Overdue = 3
     */
    public static final String DUETYPE_Overdue = "3";
    /**
     * Due = 5
     */
    public static final String DUETYPE_Due = "5";
    /**
     * Scheduled = 7
     */
    public static final String DUETYPE_Scheduled = "7";
    /**
     * Priority AD_Reference_ID=154
     */
    public static final int PRIORITY_AD_Reference_ID = 154;
    /**
     * High = 3
     */
    public static final String PRIORITY_High = "3";
    /**
     * Medium = 5
     */
    public static final String PRIORITY_Medium = "5";
    /**
     * Low = 7
     */
    public static final String PRIORITY_Low = "7";
    /**
     * Urgent = 1
     */
    public static final String PRIORITY_Urgent = "1";
    /**
     * Minor = 9
     */
    public static final String PRIORITY_Minor = "9";
    /**
     * PriorityUser AD_Reference_ID=154
     */
    public static final int PRIORITYUSER_AD_Reference_ID = 154;
    /**
     * High = 3
     */
    public static final String PRIORITYUSER_High = "3";
    /**
     * Medium = 5
     */
    public static final String PRIORITYUSER_Medium = "5";
    /**
     * Low = 7
     */
    public static final String PRIORITYUSER_Low = "7";
    /**
     * Urgent = 1
     */
    public static final String PRIORITYUSER_Urgent = "1";
    /**
     * Minor = 9
     */
    public static final String PRIORITYUSER_Minor = "9";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;
    /**
     * Standard Constructor
     */
    public X_R_Request(Properties ctx, int R_Request_ID) {
        super(ctx, R_Request_ID);
        /**
         * if (R_Request_ID == 0) { setConfidentialType (null); // C setConfidentialTypeEntry (null); //
         * C setDocumentNo (null); setDueType (null); // 5 setIsEscalated (false); setIsInvoiced
         * (false); setIsSelfService (false); // N setPriority (null); // 5 setProcessed (false);
         * setRequestAmt (Env.ZERO); setR_Request_ID (0); setR_RequestType_ID (0); setSummary (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_R_Request(Properties ctx, ResultSet rs) {
        super(ctx, rs);
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
     * Get Business Partner .
     *
     * @return Identifies a Business Partner
     */
    public int getC_BPartner_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_BPartner_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Close Date.
     *
     * @return Close Date
     */
    public Timestamp getCloseDate() {
        return (Timestamp) getValue(COLUMNNAME_CloseDate);
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
     * Get Confidentiality.
     *
     * @return Type of Confidentiality
     */
    public String getConfidentialType() {
        return (String) getValue(COLUMNNAME_ConfidentialType);
    }

    /**
     * Set Confidentiality.
     *
     * @param ConfidentialType Type of Confidentiality
     */
    public void setConfidentialType(String ConfidentialType) {

        set_Value(COLUMNNAME_ConfidentialType, ConfidentialType);
    }

    /**
     * Get Entry Confidentiality.
     *
     * @return Confidentiality of the individual entry
     */
    public String getConfidentialTypeEntry() {
        return (String) getValue(COLUMNNAME_ConfidentialTypeEntry);
    }

    /**
     * Set Entry Confidentiality.
     *
     * @param ConfidentialTypeEntry Confidentiality of the individual entry
     */
    public void setConfidentialTypeEntry(String ConfidentialTypeEntry) {

        set_Value(COLUMNNAME_ConfidentialTypeEntry, ConfidentialTypeEntry);
    }

    /**
     * Get Date next action.
     *
     * @return Date that this request should be acted on
     */
    public Timestamp getDateNextAction() {
        return (Timestamp) getValue(COLUMNNAME_DateNextAction);
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
     * Get Document No.
     *
     * @return Document sequence number of the document
     */
    public String getDocumentNo() {
        return (String) getValue(COLUMNNAME_DocumentNo);
    }

    /**
     * Set Due type.
     *
     * @param DueType Status of the next action for this Request
     */
    public void setDueType(String DueType) {

        set_Value(COLUMNNAME_DueType, DueType);
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
        Object oo = getValue(COLUMNNAME_IsInvoiced);
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
     * Get Change Request.
     *
     * @return BOM (Engineering) Change Request
     */
    public int getM_ChangeRequest_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_ChangeRequest_ID);
        if (ii == null) return 0;
        return ii;
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
     * Get Priority.
     *
     * @return Indicates if this request is of a high, medium or low priority.
     */
    public String getPriority() {
        return (String) getValue(COLUMNNAME_Priority);
    }

    /**
     * Set Priority.
     *
     * @param Priority Indicates if this request is of a high, medium or low priority.
     */
    public void setPriority(String Priority) {

        set_Value(COLUMNNAME_Priority, Priority);
    }

    /**
     * Get User Importance.
     *
     * @return Priority of the issue for the User
     */
    public String getPriorityUser() {
        return (String) getValue(COLUMNNAME_PriorityUser);
    }

    /**
     * Set User Importance.
     *
     * @param PriorityUser Priority of the issue for the User
     */
    public void setPriorityUser(String PriorityUser) {

        set_Value(COLUMNNAME_PriorityUser, PriorityUser);
    }

    /**
     * Get Processed.
     *
     * @return The document has been processed
     */
    public boolean isProcessed() {
        Object oo = getValue(COLUMNNAME_Processed);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
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
     * Set Request Amount.
     *
     * @param RequestAmt Amount associated with this request
     */
    public void setRequestAmt(BigDecimal RequestAmt) {
        set_Value(COLUMNNAME_RequestAmt, RequestAmt);
    }

    /**
     * Get Result.
     *
     * @return Result of the action taken
     */
    public String getResult() {
        return (String) getValue(COLUMNNAME_Result);
    }

    /**
     * Get Group.
     *
     * @return Request Group
     */
    public int getR_Group_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_R_Group_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Request.
     *
     * @return Request from a Business Partner or Prospect
     */
    public int getR_Request_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_R_Request_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Request Type.
     *
     * @return Type of request (e.g. Inquiry, Complaint, ..)
     */
    public int getR_RequestType_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_R_RequestType_ID);
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
     * Get Status.
     *
     * @return Request Status
     */
    public int getR_Status_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_R_Status_ID);
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
     * Get Sales Representative.
     *
     * @return Sales Representative or Company Agent
     */
    public int getSalesRep_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_SalesRep_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Sales Representative.
     *
     * @param SalesRep_ID Sales Representative or Company Agent
     */
    public void setSalesRep_ID(int SalesRep_ID) {
        if (SalesRep_ID < 1) set_Value(COLUMNNAME_SalesRep_ID, null);
        else set_Value(COLUMNNAME_SalesRep_ID, SalesRep_ID);
    }

    /**
     * Get Start Date.
     *
     * @return First effective day (inclusive)
     */
    public Timestamp getStartDate() {
        return (Timestamp) getValue(COLUMNNAME_StartDate);
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
     * Get Summary.
     *
     * @return Textual summary of this request
     */
    public String getSummary() {
        return (String) getValue(COLUMNNAME_Summary);
    }

    /**
     * Set Summary.
     *
     * @param Summary Textual summary of this request
     */
    public void setSummary(String Summary) {
        set_Value(COLUMNNAME_Summary, Summary);
    }

    @Override
    public int getTableId() {
        return I_R_Request.Table_ID;
    }
}
