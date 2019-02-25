package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.model.I_C_Invoice;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_Invoice
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Invoice extends PO {

    /**
     * Complete = CO
     */
    public static final String DOCACTION_Complete = "CO";
    /**
     * Close = CL
     */
    public static final String DOCACTION_Close = "CL";
    /**
     * <None> = --
     */
    public static final String DOCACTION_None = "--";
    /**
     * Prepare = PR
     */
    public static final String DOCACTION_Prepare = "PR";
    /**
     * Drafted = DR
     */
    public static final String DOCSTATUS_Drafted = "DR";
    /**
     * Completed = CO
     */
    public static final String DOCSTATUS_Completed = "CO";
    /**
     * Approved = AP
     */
    public static final String DOCSTATUS_Approved = "AP";
    /**
     * Not Approved = NA
     */
    public static final String DOCSTATUS_NotApproved = "NA";
    /**
     * Voided = VO
     */
    public static final String DOCSTATUS_Voided = "VO";
    /**
     * Invalid = IN
     */
    public static final String DOCSTATUS_Invalid = "IN";
    /**
     * Reversed = RE
     */
    public static final String DOCSTATUS_Reversed = "RE";
    /**
     * Closed = CL
     */
    public static final String DOCSTATUS_Closed = "CL";
    /**
     * In Progress = IP
     */
    public static final String DOCSTATUS_InProgress = "IP";
    /**
     * Cash = B
     */
    public static final String PAYMENTRULE_Cash = "B";
    /**
     * On Credit = P
     */
    public static final String PAYMENTRULE_OnCredit = "P";
    /**
     * Direct Debit = D
     */
    public static final String PAYMENTRULE_DirectDebit = "D";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_Invoice(Properties ctx, int C_Invoice_ID) {
        super(ctx, C_Invoice_ID);
        /**
         * if (C_Invoice_ID == 0) { setC_BPartner_ID (0); setC_BPartner_Location_ID (0);
         * setCurrencyId (0); // @C_Currency_ID@ setC_DocType_ID (0); // 0 setC_DocTypeTarget_ID (0);
         * setC_Invoice_ID (0); setC_PaymentTerm_ID (0); setDateAcct (new Timestamp(
         * System.currentTimeMillis() )); // @#Date@ setDateInvoiced (new Timestamp(
         * System.currentTimeMillis() )); // @#Date@ setDocAction (null); // CO setDocStatus (null); //
         * DR setDocumentNo (null); setGrandTotal (Env.ZERO); setIsApproved (false); // @IsApproved@
         * setIsDiscountPrinted (false); setIsInDispute (false); // N setIsPaid (false);
         * setIsPayScheduleValid (false); setIsPrinted (false); setIsSelfService (false); setIsSOTrx
         * (false); // @IsSOTrx@ setIsTaxIncluded (false); setIsTransferred (false); setM_PriceList_ID
         * (0); setPaymentRule (null); // P setPosted (false); // N setProcessed (false); setSendEMail
         * (false); setTotalLines (Env.ZERO); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_Invoice(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    public X_C_Invoice(Properties ctx, Row row) {
        super(ctx, row);
    } //	MInvoice

    /**
     * AccessLevel
     *
     * @return 1 - Org
     */
    protected int getAccessLevel() {
        return I_C_Invoice.accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_C_Invoice[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Trx Organization.
     *
     * @return Performing or initiating organization
     */
    public int getAD_OrgTrx_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_AD_OrgTrx_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Trx Organization.
     *
     * @param AD_OrgTrx_ID Performing or initiating organization
     */
    public void setAD_OrgTrx_ID(int AD_OrgTrx_ID) {
        if (AD_OrgTrx_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_AD_OrgTrx_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
    }

    /**
     * Get User/Contact.
     *
     * @return User within the system - Internal or Business Partner Contact
     */
    public int getAD_User_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_AD_User_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User/Contact.
     *
     * @param AD_User_ID User within the system - Internal or Business Partner Contact
     */
    public void setAD_User_ID(int AD_User_ID) {
        if (AD_User_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_AD_User_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
    }

    /**
     * Get Activity.
     *
     * @return Business Activity
     */
    public int getC_Activity_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_Activity_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Activity.
     *
     * @param C_Activity_ID Business Activity
     */
    public void setC_Activity_ID(int C_Activity_ID) {
        if (C_Activity_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_C_Activity_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
    }

    public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException {
        return (org.compiere.model.I_C_BPartner)
                MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
                        .getPO(getC_BPartner_ID());
    }

    /**
     * Get Business Partner .
     *
     * @return Identifies a Business Partner
     */
    public int getC_BPartner_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_BPartner_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Business Partner .
     *
     * @param C_BPartner_ID Identifies a Business Partner
     */
    public void setC_BPartner_ID(int C_BPartner_ID) {
        if (C_BPartner_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_C_BPartner_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
    }

    /**
     * Get Partner Location.
     *
     * @return Identifies the (ship to) address for this Business Partner
     */
    public int getC_BPartner_Location_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_BPartner_Location_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Partner Location.
     *
     * @param C_BPartner_Location_ID Identifies the (ship to) address for this Business Partner
     */
    public void setC_BPartner_Location_ID(int C_BPartner_Location_ID) {
        if (C_BPartner_Location_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_C_BPartner_Location_ID, null);
        else
            set_Value(
                    I_C_Invoice.COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
    }

    /**
     * Get Campaign.
     *
     * @return Marketing Campaign
     */
    public int getC_Campaign_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_Campaign_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Campaign.
     *
     * @param C_Campaign_ID Marketing Campaign
     */
    public void setC_Campaign_ID(int C_Campaign_ID) {
        if (C_Campaign_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_C_Campaign_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
    }

    /**
     * Get Cash Journal Line.
     *
     * @return Cash Journal Line
     */
    public int getC_CashLine_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_CashLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Cash Journal Line.
     *
     * @param C_CashLine_ID Cash Journal Line
     */
    public void setC_CashLine_ID(int C_CashLine_ID) {
        if (C_CashLine_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_C_CashLine_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_C_CashLine_ID, Integer.valueOf(C_CashLine_ID));
    }

    /**
     * Get Cash Plan Line.
     *
     * @return Cash Plan Line
     */
    public int getC_CashPlanLine_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_CashPlanLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Cash Plan Line.
     *
     * @param C_CashPlanLine_ID Cash Plan Line
     */
    public void setC_CashPlanLine_ID(int C_CashPlanLine_ID) {
        if (C_CashPlanLine_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_C_CashPlanLine_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_C_CashPlanLine_ID, Integer.valueOf(C_CashPlanLine_ID));
    }

    /**
     * Get Charge.
     *
     * @return Additional document charges
     */
    public int getC_Charge_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_Charge_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Currency Type.
     *
     * @return Currency Conversion Rate Type
     */
    public int getC_ConversionType_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_ConversionType_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Currency Type.
     *
     * @param C_ConversionType_ID Currency Conversion Rate Type
     */
    public void setC_ConversionType_ID(int C_ConversionType_ID) {
        if (C_ConversionType_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_C_ConversionType_ID, null);
        else
            set_Value(I_C_Invoice.COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
    }

    public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException {
        return (org.compiere.model.I_C_Currency)
                MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
                        .getPO(getC_Currency_ID());
    }

    /**
     * Get Currency.
     *
     * @return The Currency for this record
     */
    public int getC_Currency_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_Currency_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Currency.
     *
     * @param C_Currency_ID The Currency for this record
     */
    public void setC_Currency_ID(int C_Currency_ID) {
        if (C_Currency_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_C_Currency_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
    }

    /**
     * Get Document Type.
     *
     * @return Document type or rules
     */
    public int getC_DocType_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_DocType_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Document Type.
     *
     * @param C_DocType_ID Document type or rules
     */
    public void setC_DocType_ID(int C_DocType_ID) {
        if (C_DocType_ID < 0) set_ValueNoCheck(I_C_Invoice.COLUMNNAME_C_DocType_ID, null);
        else set_ValueNoCheck(I_C_Invoice.COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
    }

    public org.compiere.model.I_C_DocType getC_DocTypeTarget() throws RuntimeException {
        return (org.compiere.model.I_C_DocType)
                MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
                        .getPO(getC_DocTypeTarget_ID());
    }

    /**
     * Get Target Document Type.
     *
     * @return Target document type for conversing documents
     */
    public int getC_DocTypeTarget_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_DocTypeTarget_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Target Document Type.
     *
     * @param C_DocTypeTarget_ID Target document type for conversing documents
     */
    public void setC_DocTypeTarget_ID(int C_DocTypeTarget_ID) {
        if (C_DocTypeTarget_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_C_DocTypeTarget_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_C_DocTypeTarget_ID, C_DocTypeTarget_ID);
    }

    /**
     * Get Charge amount.
     *
     * @return Charge Amount
     */
    public BigDecimal getChargeAmt() {
        BigDecimal bd = (BigDecimal) getValue(I_C_Invoice.COLUMNNAME_ChargeAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Charge amount.
     *
     * @param ChargeAmt Charge Amount
     */
    public void setChargeAmt(BigDecimal ChargeAmt) {
        set_Value(I_C_Invoice.COLUMNNAME_ChargeAmt, ChargeAmt);
    }

    /**
     * Get Invoice.
     *
     * @return Invoice Identifier
     */
    public int getC_Invoice_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_Invoice_ID);
        if (ii == null) return 0;
        return ii;
    }

    public org.compiere.model.I_C_Order getC_Order() throws RuntimeException {
        return (org.compiere.model.I_C_Order)
                MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
                        .getPO(getC_Order_ID());
    }

    /**
     * Get Order.
     *
     * @return Order
     */
    public int getC_Order_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_Order_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Order.
     *
     * @param C_Order_ID Order
     */
    public void setC_Order_ID(int C_Order_ID) {
        if (C_Order_ID < 1) set_ValueNoCheck(I_C_Invoice.COLUMNNAME_C_Order_ID, null);
        else set_ValueNoCheck(I_C_Invoice.COLUMNNAME_C_Order_ID, C_Order_ID);
    }

    /**
     * Get Payment.
     *
     * @return Payment identifier
     */
    public int getC_Payment_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_Payment_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Payment.
     *
     * @param C_Payment_ID Payment identifier
     */
    public void setC_Payment_ID(int C_Payment_ID) {
        if (C_Payment_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_C_Payment_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
    }

    /**
     * Get Payment Term.
     *
     * @return The terms of Payment (timing, discount)
     */
    public int getC_PaymentTerm_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_PaymentTerm_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Payment Term.
     *
     * @param C_PaymentTerm_ID The terms of Payment (timing, discount)
     */
    public void setC_PaymentTerm_ID(int C_PaymentTerm_ID) {
        if (C_PaymentTerm_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_C_PaymentTerm_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
    }

    /**
     * Get Project.
     *
     * @return Financial Project
     */
    public int getC_Project_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_C_Project_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Project.
     *
     * @param C_Project_ID Financial Project
     */
    public void setC_Project_ID(int C_Project_ID) {
        if (C_Project_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_C_Project_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
    }

    /**
     * Get Account Date.
     *
     * @return Accounting Date
     */
    public Timestamp getDateAcct() {
        return (Timestamp) getValue(I_C_Invoice.COLUMNNAME_DateAcct);
    }

    /**
     * Set Account Date.
     *
     * @param DateAcct Accounting Date
     */
    public void setDateAcct(Timestamp DateAcct) {
        set_Value(I_C_Invoice.COLUMNNAME_DateAcct, DateAcct);
    }

    /**
     * Get Date Invoiced.
     *
     * @return Date printed on Invoice
     */
    public Timestamp getDateInvoiced() {
        return (Timestamp) getValue(I_C_Invoice.COLUMNNAME_DateInvoiced);
    }

    /**
     * Set Date Invoiced.
     *
     * @param DateInvoiced Date printed on Invoice
     */
    public void setDateInvoiced(Timestamp DateInvoiced) {
        set_Value(I_C_Invoice.COLUMNNAME_DateInvoiced, DateInvoiced);
    }

    /**
     * Get Date Ordered.
     *
     * @return Date of Order
     */
    public Timestamp getDateOrdered() {
        return (Timestamp) getValue(I_C_Invoice.COLUMNNAME_DateOrdered);
    }

    /**
     * Set Date Ordered.
     *
     * @param DateOrdered Date of Order
     */
    public void setDateOrdered(Timestamp DateOrdered) {
        set_ValueNoCheck(I_C_Invoice.COLUMNNAME_DateOrdered, DateOrdered);
    }

    /**
     * Set Date printed.
     *
     * @param DatePrinted Date the document was printed.
     */
    public void setDatePrinted(Timestamp DatePrinted) {
        set_Value(I_C_Invoice.COLUMNNAME_DatePrinted, DatePrinted);
    }

    /**
     * Get Description.
     *
     * @return Optional short description of the record
     */
    public String getDescription() {
        return (String) getValue(I_C_Invoice.COLUMNNAME_Description);
    }

    /**
     * Set Description.
     *
     * @param Description Optional short description of the record
     */
    public void setDescription(String Description) {
        set_Value(I_C_Invoice.COLUMNNAME_Description, Description);
    }

    /**
     * Get Document Action.
     *
     * @return The targeted status of the document
     */
    public String getDocAction() {
        return (String) getValue(I_C_Invoice.COLUMNNAME_DocAction);
    }

    /**
     * Set Document Action.
     *
     * @param DocAction The targeted status of the document
     */
    public void setDocAction(String DocAction) {

        set_Value(I_C_Invoice.COLUMNNAME_DocAction, DocAction);
    }

    /**
     * Get Document Status.
     *
     * @return The current status of the document
     */
    public String getDocStatus() {
        return (String) getValue(I_C_Invoice.COLUMNNAME_DocStatus);
    }

    /**
     * Set Document Status.
     *
     * @param DocStatus The current status of the document
     */
    public void setDocStatus(String DocStatus) {

        set_Value(I_C_Invoice.COLUMNNAME_DocStatus, DocStatus);
    }

    /**
     * Get Document No.
     *
     * @return Document sequence number of the document
     */
    public String getDocumentNo() {
        return (String) getValue(I_C_Invoice.COLUMNNAME_DocumentNo);
    }

    /**
     * Set Document No.
     *
     * @param DocumentNo Document sequence number of the document
     */
    public void setDocumentNo(String DocumentNo) {
        set_ValueNoCheck(I_C_Invoice.COLUMNNAME_DocumentNo, DocumentNo);
    }

    /**
     * Get Grand Total.
     *
     * @return Total amount of document
     */
    public BigDecimal getGrandTotal() {
        BigDecimal bd = (BigDecimal) getValue(I_C_Invoice.COLUMNNAME_GrandTotal);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Grand Total.
     *
     * @param GrandTotal Total amount of document
     */
    public void setGrandTotal(BigDecimal GrandTotal) {
        set_ValueNoCheck(I_C_Invoice.COLUMNNAME_GrandTotal, GrandTotal);
    }

    /**
     * Set Approved.
     *
     * @param IsApproved Indicates if this document requires approval
     */
    public void setIsApproved(boolean IsApproved) {
        set_ValueNoCheck(I_C_Invoice.COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
    }

    /**
     * Get Approved.
     *
     * @return Indicates if this document requires approval
     */
    public boolean isApproved() {
        Object oo = getValue(I_C_Invoice.COLUMNNAME_IsApproved);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Discount Printed.
     *
     * @param IsDiscountPrinted Print Discount on Invoice and Order
     */
    public void setIsDiscountPrinted(boolean IsDiscountPrinted) {
        set_Value(I_C_Invoice.COLUMNNAME_IsDiscountPrinted, Boolean.valueOf(IsDiscountPrinted));
    }

    /**
     * Set In Dispute.
     *
     * @param IsInDispute Document is in dispute
     */
    public void setIsInDispute(boolean IsInDispute) {
        set_Value(I_C_Invoice.COLUMNNAME_IsInDispute, Boolean.valueOf(IsInDispute));
    }

    /**
     * Set Paid.
     *
     * @param IsPaid The document is paid
     */
    public void setIsPaid(boolean IsPaid) {
        set_Value(I_C_Invoice.COLUMNNAME_IsPaid, Boolean.valueOf(IsPaid));
    }

    /**
     * Get Paid.
     *
     * @return The document is paid
     */
    public boolean isPaid() {
        Object oo = getValue(I_C_Invoice.COLUMNNAME_IsPaid);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Pay Schedule valid.
     *
     * @param IsPayScheduleValid Is the Payment Schedule is valid
     */
    public void setIsPayScheduleValid(boolean IsPayScheduleValid) {
        set_ValueNoCheck(
                I_C_Invoice.COLUMNNAME_IsPayScheduleValid, Boolean.valueOf(IsPayScheduleValid));
    }

    /**
     * Get Pay Schedule valid.
     *
     * @return Is the Payment Schedule is valid
     */
    public boolean isPayScheduleValid() {
        Object oo = getValue(I_C_Invoice.COLUMNNAME_IsPayScheduleValid);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Printed.
     *
     * @param IsPrinted Indicates if this document / line is printed
     */
    public void setIsPrinted(boolean IsPrinted) {
        set_ValueNoCheck(I_C_Invoice.COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
    }

    /**
     * Set Self-Service.
     *
     * @param IsSelfService This is a Self-Service entry or this entry can be changed via Self-Service
     */
    public void setIsSelfService(boolean IsSelfService) {
        set_Value(I_C_Invoice.COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
    }

    /**
     * Set Sales Transaction.
     *
     * @param IsSOTrx This is a Sales Transaction
     */
    public void setIsSOTrx(boolean IsSOTrx) {
        set_ValueNoCheck(I_C_Invoice.COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
    }

    /**
     * Get Sales Transaction.
     *
     * @return This is a Sales Transaction
     */
    public boolean isSOTrx() {
        Object oo = getValue(I_C_Invoice.COLUMNNAME_IsSOTrx);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Price includes Tax.
     *
     * @param IsTaxIncluded Tax is included in the price
     */
    public void setIsTaxIncluded(boolean IsTaxIncluded) {
        set_Value(I_C_Invoice.COLUMNNAME_IsTaxIncluded, Boolean.valueOf(IsTaxIncluded));
    }

    /**
     * Get Price includes Tax.
     *
     * @return Tax is included in the price
     */
    public boolean isTaxIncluded() {
        Object oo = getValue(I_C_Invoice.COLUMNNAME_IsTaxIncluded);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Transferred.
     *
     * @param IsTransferred Transferred to General Ledger (i.e. accounted)
     */
    public void setIsTransferred(boolean IsTransferred) {
        set_ValueNoCheck(I_C_Invoice.COLUMNNAME_IsTransferred, IsTransferred);
    }

    public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException {
        return (org.compiere.model.I_M_PriceList)
                MTable.get(getCtx(), org.compiere.model.I_M_PriceList.Table_Name)
                        .getPO(getM_PriceList_ID());
    }

    /**
     * Get Price List.
     *
     * @return Unique identifier of a Price List
     */
    public int getM_PriceList_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_M_PriceList_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Price List.
     *
     * @param M_PriceList_ID Unique identifier of a Price List
     */
    public void setM_PriceList_ID(int M_PriceList_ID) {
        if (M_PriceList_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_M_PriceList_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_M_PriceList_ID, M_PriceList_ID);
    }

    /**
     * Get RMA.
     *
     * @return Return Material Authorization
     */
    public int getM_RMA_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_M_RMA_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set RMA.
     *
     * @param M_RMA_ID Return Material Authorization
     */
    public void setM_RMA_ID(int M_RMA_ID) {
        if (M_RMA_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_M_RMA_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_M_RMA_ID, M_RMA_ID);
    }

    /**
     * Get Payment Rule.
     *
     * @return How you pay the invoice
     */
    public String getPaymentRule() {
        return (String) getValue(I_C_Invoice.COLUMNNAME_PaymentRule);
    }

    /**
     * Set Payment Rule.
     *
     * @param PaymentRule How you pay the invoice
     */
    public void setPaymentRule(String PaymentRule) {

        set_Value(I_C_Invoice.COLUMNNAME_PaymentRule, PaymentRule);
    }

    /**
     * Get Order Reference.
     *
     * @return Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
     */
    public String getPOReference() {
        return (String) getValue(I_C_Invoice.COLUMNNAME_POReference);
    }

    /**
     * Set Order Reference.
     *
     * @param POReference Transaction Reference Number (Sales Order, Purchase Order) of your Business
     *                    Partner
     */
    public void setPOReference(String POReference) {
        set_Value(I_C_Invoice.COLUMNNAME_POReference, POReference);
    }

    /**
     * Get Posted.
     *
     * @return Posting status
     */
    public boolean isPosted() {
        Object oo = getValue(I_C_Invoice.COLUMNNAME_Posted);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Posted.
     *
     * @param Posted Posting status
     */
    public void setPosted(boolean Posted) {
        set_Value(I_C_Invoice.COLUMNNAME_Posted, Boolean.valueOf(Posted));
    }

    /**
     * Get Processed.
     *
     * @return The document has been processed
     */
    public boolean isProcessed() {
        Object oo = getValue(I_C_Invoice.COLUMNNAME_Processed);
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
        set_ValueNoCheck(I_C_Invoice.COLUMNNAME_Processed, Boolean.valueOf(Processed));
    }

    /**
     * Set Process Now.
     *
     * @param Processing Process Now
     */
    public void setProcessing(boolean Processing) {
        set_Value(I_C_Invoice.COLUMNNAME_Processing, Boolean.valueOf(Processing));
    }

    /**
     * Get Referenced Invoice.
     *
     * @return Referenced Invoice
     */
    public int getRef_Invoice_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_Ref_Invoice_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Referenced Invoice.
     *
     * @param Ref_Invoice_ID Referenced Invoice
     */
    public void setRef_Invoice_ID(int Ref_Invoice_ID) {
        if (Ref_Invoice_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_Ref_Invoice_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_Ref_Invoice_ID, Integer.valueOf(Ref_Invoice_ID));
    }

    /**
     * Set Reversal ID.
     *
     * @param Reversal_ID ID of document reversal
     */
    public void setReversal_ID(int Reversal_ID) {
        if (Reversal_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_Reversal_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
    }

    /**
     * Get Sales Representative.
     *
     * @return Sales Representative or Company Agent
     */
    public int getSalesRep_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_SalesRep_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Sales Representative.
     *
     * @param SalesRep_ID Sales Representative or Company Agent
     */
    public void setSalesRep_ID(int SalesRep_ID) {
        if (SalesRep_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_SalesRep_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
    }

    /**
     * Set Send EMail.
     *
     * @param SendEMail Enable sending Document EMail
     */
    public void setSendEMail(boolean SendEMail) {
        set_Value(I_C_Invoice.COLUMNNAME_SendEMail, Boolean.valueOf(SendEMail));
    }

    /**
     * Get Total Lines.
     *
     * @return Total of all document lines
     */
    public BigDecimal getTotalLines() {
        BigDecimal bd = (BigDecimal) getValue(I_C_Invoice.COLUMNNAME_TotalLines);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Total Lines.
     *
     * @param TotalLines Total of all document lines
     */
    public void setTotalLines(BigDecimal TotalLines) {
        set_ValueNoCheck(I_C_Invoice.COLUMNNAME_TotalLines, TotalLines);
    }

    /**
     * Get User Element List 1.
     *
     * @return User defined list element #1
     */
    public int getUser1_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_User1_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User Element List 1.
     *
     * @param User1_ID User defined list element #1
     */
    public void setUser1_ID(int User1_ID) {
        if (User1_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_User1_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
    }

    /**
     * Get User Element List 2.
     *
     * @return User defined list element #2
     */
    public int getUser2_ID() {
        Integer ii = (Integer) getValue(I_C_Invoice.COLUMNNAME_User2_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User Element List 2.
     *
     * @param User2_ID User defined list element #2
     */
    public void setUser2_ID(int User2_ID) {
        if (User2_ID < 1) set_Value(I_C_Invoice.COLUMNNAME_User2_ID, null);
        else set_Value(I_C_Invoice.COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
    }

    @Override
    public int getTableId() {
        return I_C_Invoice.Table_ID;
    }
}
