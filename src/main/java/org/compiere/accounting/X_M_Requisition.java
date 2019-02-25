package org.compiere.accounting;

import org.compiere.model.I_M_Requisition;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for M_Requisition
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_Requisition extends PO implements I_M_Requisition {

    /**
     * Completed = CO
     */
    public static final String DOCSTATUS_Completed = "CO";
    /**
     * Reversed = RE
     */
    public static final String DOCSTATUS_Reversed = "RE";
    /**
     * Closed = CL
     */
    public static final String DOCSTATUS_Closed = "CL";
    /**
     * Medium = 5
     */
    public static final String PRIORITYRULE_Medium = "5";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_Requisition(Properties ctx, int M_Requisition_ID) {
        super(ctx, M_Requisition_ID);
        /**
         * if (M_Requisition_ID == 0) { setUserId (0); setC_DocType_ID (0); setDateDoc (new
         * Timestamp( System.currentTimeMillis() )); // @#Date@ setDateRequired (new Timestamp(
         * System.currentTimeMillis() )); setDocAction (null); // CO setDocStatus (null); // DR
         * setDocumentNo (null); setIsApproved (false); setM_PriceList_ID (0); setM_Requisition_ID (0);
         * setWarehouseId (0); setPosted (false); setPriorityRule (null); // 5 setProcessed (false);
         * setTotalLines (Env.ZERO); }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_Requisition(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 1 - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_M_Requisition[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get User/Contact.
     *
     * @return User within the system - Internal or Business Partner Contact
     */
    public int getAD_User_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_User_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User/Contact.
     *
     * @param AD_User_ID User within the system - Internal or Business Partner Contact
     */
    public void setAD_User_ID(int AD_User_ID) {
        if (AD_User_ID < 1) set_Value(COLUMNNAME_AD_User_ID, null);
        else set_Value(COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
    }

    public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException {
        return (org.compiere.model.I_C_DocType)
                MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
                        .getPO(getC_DocType_ID());
    }

    /**
     * Get Document Type.
     *
     * @return Document type or rules
     */
    public int getC_DocType_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_DocType_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Document Type.
     *
     * @param C_DocType_ID Document type or rules
     */
    public void setC_DocType_ID(int C_DocType_ID) {
        if (C_DocType_ID < 0) set_Value(COLUMNNAME_C_DocType_ID, null);
        else set_Value(COLUMNNAME_C_DocType_ID, C_DocType_ID);
    }

    /**
     * Get Document Date.
     *
     * @return Date of the Document
     */
    public Timestamp getDateDoc() {
        return (Timestamp) getValue(COLUMNNAME_DateDoc);
    }

    /**
     * Set Document Date.
     *
     * @param DateDoc Date of the Document
     */
    public void setDateDoc(Timestamp DateDoc) {
        set_Value(COLUMNNAME_DateDoc, DateDoc);
    }

    /**
     * Get Date Required.
     *
     * @return Date when required
     */
    public Timestamp getDateRequired() {
        return (Timestamp) getValue(COLUMNNAME_DateRequired);
    }

    /**
     * Set Date Required.
     *
     * @param DateRequired Date when required
     */
    public void setDateRequired(Timestamp DateRequired) {
        set_Value(COLUMNNAME_DateRequired, DateRequired);
    }

    /**
     * Get Description.
     *
     * @return Optional short description of the record
     */
    public String getDescription() {
        return (String) getValue(COLUMNNAME_Description);
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
     * Get Document Action.
     *
     * @return The targeted status of the document
     */
    public String getDocAction() {
        return (String) getValue(COLUMNNAME_DocAction);
    }

    /**
     * Set Document Action.
     *
     * @param DocAction The targeted status of the document
     */
    public void setDocAction(String DocAction) {

        set_Value(COLUMNNAME_DocAction, DocAction);
    }

    /**
     * Get Document Status.
     *
     * @return The current status of the document
     */
    public String getDocStatus() {
        return (String) getValue(COLUMNNAME_DocStatus);
    }

    /**
     * Set Document Status.
     *
     * @param DocStatus The current status of the document
     */
    public void setDocStatus(String DocStatus) {

        set_Value(COLUMNNAME_DocStatus, DocStatus);
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
     * Set Document No.
     *
     * @param DocumentNo Document sequence number of the document
     */
    public void setDocumentNo(String DocumentNo) {
        set_ValueNoCheck(COLUMNNAME_DocumentNo, DocumentNo);
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
        Object oo = getValue(COLUMNNAME_IsApproved);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Price List.
     *
     * @return Unique identifier of a Price List
     */
    public int getM_PriceList_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_PriceList_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Price List.
     *
     * @param M_PriceList_ID Unique identifier of a Price List
     */
    public void setM_PriceList_ID(int M_PriceList_ID) {
        if (M_PriceList_ID < 1) set_Value(COLUMNNAME_M_PriceList_ID, null);
        else set_Value(COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
    }

    /**
     * Get Requisition.
     *
     * @return Material Requisition
     */
    public int getM_Requisition_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_Requisition_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Warehouse.
     *
     * @return Storage Warehouse and Service Point
     */
    public int getM_Warehouse_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_Warehouse_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Warehouse.
     *
     * @param M_Warehouse_ID Storage Warehouse and Service Point
     */
    public void setM_Warehouse_ID(int M_Warehouse_ID) {
        if (M_Warehouse_ID < 1) set_Value(COLUMNNAME_M_Warehouse_ID, null);
        else set_Value(COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
    }

    /**
     * Set Posted.
     *
     * @param Posted Posting status
     */
    public void setPosted(boolean Posted) {
        set_Value(COLUMNNAME_Posted, Boolean.valueOf(Posted));
    }

    /**
     * Set Priority.
     *
     * @param PriorityRule Priority of a document
     */
    public void setPriorityRule(String PriorityRule) {

        set_Value(COLUMNNAME_PriorityRule, PriorityRule);
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
     * Set Process Now.
     *
     * @param Processing Process Now
     */
    public void setProcessing(boolean Processing) {
        set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
    }

    /**
     * Get Total Lines.
     *
     * @return Total of all document lines
     */
    public BigDecimal getTotalLines() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_TotalLines);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Total Lines.
     *
     * @param TotalLines Total of all document lines
     */
    public void setTotalLines(BigDecimal TotalLines) {
        set_Value(COLUMNNAME_TotalLines, TotalLines);
    }

    @Override
    public int getTableId() {
        return I_M_Requisition.Table_ID;
    }
}
