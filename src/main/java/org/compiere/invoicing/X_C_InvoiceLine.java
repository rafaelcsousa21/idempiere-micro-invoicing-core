package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_InvoiceLine
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_InvoiceLine extends PO implements I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_InvoiceLine(Properties ctx, int C_InvoiceLine_ID) {
        super(ctx, C_InvoiceLine_ID);
        /**
         * if (C_InvoiceLine_ID == 0) { setC_Invoice_ID (0); setC_InvoiceLine_ID (0); setC_Tax_ID (0);
         * setIsDescription (false); // N setIsPrinted (true); // Y setLine (0); // @SQL=SELECT
         * NVL(MAX(Line),0)+10 AS DefaultValue FROM C_InvoiceLine WHERE C_Invoice_ID=@C_Invoice_ID@
         * setLineNetAmt (Env.ZERO); setM_AttributeSetInstance_ID (0); setPriceActual (Env.ZERO);
         * setPriceEntered (Env.ZERO); setPriceLimit (Env.ZERO); setPriceList (Env.ZERO); setProcessed
         * (false); setQtyEntered (Env.ZERO); // 1 setQtyInvoiced (Env.ZERO); // 1 }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_InvoiceLine(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    public X_C_InvoiceLine(Properties ctx, Row row) {
        super(ctx, row);
    } //	MInvoiceLine

    /**
     * AccessLevel
     *
     * @return 1 - Org
     */
    protected int getAccessLevel() {
        return I_C_InvoiceLine.accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_C_InvoiceLine[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Asset Group.
     *
     * @return Group of Assets
     */
    public int getA_Asset_Group_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_A_Asset_Group_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Asset.
     *
     * @param A_Asset_ID Asset used internally or by customers
     */
    public void setA_Asset_ID(int A_Asset_ID) {
        if (A_Asset_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_A_Asset_ID, null);
        else set_Value(I_C_InvoiceLine.COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
    }

    /**
     * Get Trx Organization.
     *
     * @return Performing or initiating organization
     */
    public int getAD_OrgTrx_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_AD_OrgTrx_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Trx Organization.
     *
     * @param AD_OrgTrx_ID Performing or initiating organization
     */
    public void setAD_OrgTrx_ID(int AD_OrgTrx_ID) {
        if (AD_OrgTrx_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_AD_OrgTrx_ID, null);
        else set_Value(I_C_InvoiceLine.COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
    }

    /**
     * Set 1099 Box.
     *
     * @param C_1099Box_ID 1099 Box
     */
    public void setC_1099Box_ID(int C_1099Box_ID) {
        if (C_1099Box_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_C_1099Box_ID, null);
        else set_Value(I_C_InvoiceLine.COLUMNNAME_C_1099Box_ID, Integer.valueOf(C_1099Box_ID));
    }

    /**
     * Get Activity.
     *
     * @return Business Activity
     */
    public int getC_Activity_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_C_Activity_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Activity.
     *
     * @param C_Activity_ID Business Activity
     */
    public void setC_Activity_ID(int C_Activity_ID) {
        if (C_Activity_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_C_Activity_ID, null);
        else set_Value(I_C_InvoiceLine.COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
    }

    /**
     * Get Campaign.
     *
     * @return Marketing Campaign
     */
    public int getC_Campaign_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_C_Campaign_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Campaign.
     *
     * @param C_Campaign_ID Marketing Campaign
     */
    public void setC_Campaign_ID(int C_Campaign_ID) {
        if (C_Campaign_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_C_Campaign_ID, null);
        else set_Value(I_C_InvoiceLine.COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
    }

    /**
     * Get Charge.
     *
     * @return Additional document charges
     */
    public int getC_Charge_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_C_Charge_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Charge.
     *
     * @param C_Charge_ID Additional document charges
     */
    public void setC_Charge_ID(int C_Charge_ID) {
        if (C_Charge_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_C_Charge_ID, null);
        else set_Value(I_C_InvoiceLine.COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
    }

    public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException {
        return (org.compiere.model.I_C_Invoice)
                MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
                        .getPO(getC_Invoice_ID());
    }

    /**
     * Get Invoice.
     *
     * @return Invoice Identifier
     */
    public int getC_Invoice_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Invoice.
     *
     * @param C_Invoice_ID Invoice Identifier
     */
    public void setC_Invoice_ID(int C_Invoice_ID) {
        if (C_Invoice_ID < 1) set_ValueNoCheck(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, null);
        else set_ValueNoCheck(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
    }

    /**
     * Get Invoice Line.
     *
     * @return Invoice Detail Line
     */
    public int getC_InvoiceLine_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Sales Order Line.
     *
     * @return Sales Order Line
     */
    public int getC_OrderLine_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Sales Order Line.
     *
     * @param C_OrderLine_ID Sales Order Line
     */
    public void setC_OrderLine_ID(int C_OrderLine_ID) {
        if (C_OrderLine_ID < 1) set_ValueNoCheck(I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID, null);
        else
            set_ValueNoCheck(I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
    }

    /**
     * Get Project.
     *
     * @return Financial Project
     */
    public int getC_Project_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_C_Project_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Project.
     *
     * @param C_Project_ID Financial Project
     */
    public void setC_Project_ID(int C_Project_ID) {
        if (C_Project_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_C_Project_ID, null);
        else set_Value(I_C_InvoiceLine.COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
    }

    /**
     * Get Project Phase.
     *
     * @return Phase of a Project
     */
    public int getC_ProjectPhase_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_C_ProjectPhase_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Project Phase.
     *
     * @param C_ProjectPhase_ID Phase of a Project
     */
    public void setC_ProjectPhase_ID(int C_ProjectPhase_ID) {
        if (C_ProjectPhase_ID < 1) set_ValueNoCheck(I_C_InvoiceLine.COLUMNNAME_C_ProjectPhase_ID, null);
        else
            set_ValueNoCheck(
                    I_C_InvoiceLine.COLUMNNAME_C_ProjectPhase_ID, Integer.valueOf(C_ProjectPhase_ID));
    }

    /**
     * Get Project Task.
     *
     * @return Actual Project Task in a Phase
     */
    public int getC_ProjectTask_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_C_ProjectTask_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Project Task.
     *
     * @param C_ProjectTask_ID Actual Project Task in a Phase
     */
    public void setC_ProjectTask_ID(int C_ProjectTask_ID) {
        if (C_ProjectTask_ID < 1) set_ValueNoCheck(I_C_InvoiceLine.COLUMNNAME_C_ProjectTask_ID, null);
        else
            set_ValueNoCheck(
                    I_C_InvoiceLine.COLUMNNAME_C_ProjectTask_ID, Integer.valueOf(C_ProjectTask_ID));
    }

    /**
     * Get Tax.
     *
     * @return Tax identifier
     */
    public int getC_Tax_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_C_Tax_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Tax.
     *
     * @param C_Tax_ID Tax identifier
     */
    public void setC_Tax_ID(int C_Tax_ID) {
        if (C_Tax_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_C_Tax_ID, null);
        else set_Value(I_C_InvoiceLine.COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
    }

    /**
     * Get UOM.
     *
     * @return Unit of Measure
     */
    public int getC_UOM_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_C_UOM_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set UOM.
     *
     * @param C_UOM_ID Unit of Measure
     */
    public void setC_UOM_ID(int C_UOM_ID) {
        if (C_UOM_ID < 1) set_ValueNoCheck(I_C_InvoiceLine.COLUMNNAME_C_UOM_ID, null);
        else set_ValueNoCheck(I_C_InvoiceLine.COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
    }

    /**
     * Get Description.
     *
     * @return Optional short description of the record
     */
    public String getDescription() {
        return (String) get_Value(I_C_InvoiceLine.COLUMNNAME_Description);
    }

    /**
     * Set Description Only.
     *
     * @param IsDescription if true, the line is just description and no transaction
     */
    public void setIsDescription(boolean IsDescription) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_IsDescription, Boolean.valueOf(IsDescription));
    }

    /**
     * Get Description Only.
     *
     * @return if true, the line is just description and no transaction
     */
    public boolean isDescription() {
        Object oo = get_Value(I_C_InvoiceLine.COLUMNNAME_IsDescription);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Description.
     *
     * @param Description Optional short description of the record
     */
    public void setDescription(String Description) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_Description, Description);
    }

    /**
     * Set Printed.
     *
     * @param IsPrinted Indicates if this document / line is printed
     */
    public void setIsPrinted(boolean IsPrinted) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
    }

    /**
     * Get Line No.
     *
     * @return Unique line for this document
     */
    public int getLine() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_Line);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Line No.
     *
     * @param Line Unique line for this document
     */
    public void setLine(int Line) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_Line, Integer.valueOf(Line));
    }

    /**
     * Get Line Amount.
     *
     * @return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
     */
    public BigDecimal getLineNetAmt() {
        BigDecimal bd = (BigDecimal) get_Value(I_C_InvoiceLine.COLUMNNAME_LineNetAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Line Amount.
     *
     * @param LineNetAmt Line Extended Amount (Quantity * Actual Price) without Freight and Charges
     */
    public void setLineNetAmt(BigDecimal LineNetAmt) {
        set_ValueNoCheck(I_C_InvoiceLine.COLUMNNAME_LineNetAmt, LineNetAmt);
    }

    /**
     * Get Line Total.
     *
     * @return Total line amount incl. Tax
     */
    public BigDecimal getLineTotalAmt() {
        BigDecimal bd = (BigDecimal) get_Value(I_C_InvoiceLine.COLUMNNAME_LineTotalAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Line Total.
     *
     * @param LineTotalAmt Total line amount incl. Tax
     */
    public void setLineTotalAmt(BigDecimal LineTotalAmt) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_LineTotalAmt, LineTotalAmt);
    }

    /**
     * Set Attribute Set Instance.
     *
     * @param M_AttributeSetInstance_ID Product Attribute Set Instance
     */
    public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID < 0)
            set_Value(I_C_InvoiceLine.COLUMNNAME_M_AttributeSetInstance_ID, null);
        else
            set_Value(
                    I_C_InvoiceLine.COLUMNNAME_M_AttributeSetInstance_ID,
                    Integer.valueOf(M_AttributeSetInstance_ID));
    }

    /**
     * Get Attribute Set Instance.
     *
     * @return Product Attribute Set Instance
     */
    public int getMAttributeSetInstance_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_M_AttributeSetInstance_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Shipment/Receipt Line.
     *
     * @return Line on Shipment or Receipt document
     */
    public int getM_InOutLine_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Shipment/Receipt Line.
     *
     * @param M_InOutLine_ID Line on Shipment or Receipt document
     */
    public void setM_InOutLine_ID(int M_InOutLine_ID) {
        if (M_InOutLine_ID < 1) set_ValueNoCheck(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID, null);
        else
            set_ValueNoCheck(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
    }

    /**
     * Get Product.
     *
     * @return Product, Service, Item
     */
    public int getM_Product_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_M_Product_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Product.
     *
     * @param M_Product_ID Product, Service, Item
     */
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_M_Product_ID, null);
        else set_Value(I_C_InvoiceLine.COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
    }

    /**
     * Get RMA Line.
     *
     * @return Return Material Authorization Line
     */
    public int getM_RMALine_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_M_RMALine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set RMA Line.
     *
     * @param M_RMALine_ID Return Material Authorization Line
     */
    public void setM_RMALine_ID(int M_RMALine_ID) {
        if (M_RMALine_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_M_RMALine_ID, null);
        else set_Value(I_C_InvoiceLine.COLUMNNAME_M_RMALine_ID, Integer.valueOf(M_RMALine_ID));
    }

    /**
     * Get Unit Price.
     *
     * @return Actual Price
     */
    public BigDecimal getPriceActual() {
        BigDecimal bd = (BigDecimal) get_Value(I_C_InvoiceLine.COLUMNNAME_PriceActual);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Get Price.
     *
     * @return Price Entered - the price based on the selected/base UoM
     */
    public BigDecimal getPriceEntered() {
        BigDecimal bd = (BigDecimal) get_Value(I_C_InvoiceLine.COLUMNNAME_PriceEntered);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Price.
     *
     * @param PriceEntered Price Entered - the price based on the selected/base UoM
     */
    public void setPriceEntered(BigDecimal PriceEntered) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_PriceEntered, PriceEntered);
    }

    /**
     * Get Limit Price.
     *
     * @return Lowest price for a product
     */
    public BigDecimal getPriceLimit() {
        BigDecimal bd = (BigDecimal) get_Value(I_C_InvoiceLine.COLUMNNAME_PriceLimit);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Limit Price.
     *
     * @param PriceLimit Lowest price for a product
     */
    public void setPriceLimit(BigDecimal PriceLimit) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_PriceLimit, PriceLimit);
    }

    /**
     * Get List Price.
     *
     * @return List Price
     */
    public BigDecimal getPriceList() {
        BigDecimal bd = (BigDecimal) get_Value(I_C_InvoiceLine.COLUMNNAME_PriceList);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set List Price.
     *
     * @param PriceList List Price
     */
    public void setPriceList(BigDecimal PriceList) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_PriceList, PriceList);
    }

    /**
     * Get Processed.
     *
     * @return The document has been processed
     */
    public boolean isProcessed() {
        Object oo = get_Value(I_C_InvoiceLine.COLUMNNAME_Processed);
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
        set_Value(I_C_InvoiceLine.COLUMNNAME_Processed, Boolean.valueOf(Processed));
    }

    /**
     * Get Quantity.
     *
     * @return The Quantity Entered is based on the selected UoM
     */
    public BigDecimal getQtyEntered() {
        BigDecimal bd = (BigDecimal) get_Value(I_C_InvoiceLine.COLUMNNAME_QtyEntered);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Quantity.
     *
     * @param QtyEntered The Quantity Entered is based on the selected UoM
     */
    public void setQtyEntered(BigDecimal QtyEntered) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_QtyEntered, QtyEntered);
    }

    /**
     * Get Quantity Invoiced.
     *
     * @return Invoiced Quantity
     */
    public BigDecimal getQtyInvoiced() {
        BigDecimal bd = (BigDecimal) get_Value(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Quantity Invoiced.
     *
     * @param QtyInvoiced Invoiced Quantity
     */
    public void setQtyInvoiced(BigDecimal QtyInvoiced) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced, QtyInvoiced);
    }

    /**
     * Set Referenced Invoice Line.
     *
     * @param Ref_InvoiceLine_ID Referenced Invoice Line
     */
    public void setRef_InvoiceLine_ID(int Ref_InvoiceLine_ID) {
        if (Ref_InvoiceLine_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_Ref_InvoiceLine_ID, null);
        else
            set_Value(I_C_InvoiceLine.COLUMNNAME_Ref_InvoiceLine_ID, Integer.valueOf(Ref_InvoiceLine_ID));
    }

    /**
     * Set Revenue Recognition Amt.
     *
     * @param RRAmt Revenue Recognition Amount
     */
    public void setRRAmt(BigDecimal RRAmt) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_RRAmt, RRAmt);
    }

    /**
     * Set Revenue Recognition Start.
     *
     * @param RRStartDate Revenue Recognition Start Date
     */
    public void setRRStartDate(Timestamp RRStartDate) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_RRStartDate, RRStartDate);
    }

    /**
     * Set Resource Assignment.
     *
     * @param S_ResourceAssignment_ID Resource Assignment
     */
    public void setS_ResourceAssignment_ID(int S_ResourceAssignment_ID) {
        if (S_ResourceAssignment_ID < 1)
            set_ValueNoCheck(I_C_InvoiceLine.COLUMNNAME_S_ResourceAssignment_ID, null);
        else
            set_ValueNoCheck(
                    I_C_InvoiceLine.COLUMNNAME_S_ResourceAssignment_ID,
                    Integer.valueOf(S_ResourceAssignment_ID));
    }

    /**
     * Get Tax Amount.
     *
     * @return Tax Amount for a document
     */
    public BigDecimal getTaxAmt() {
        BigDecimal bd = (BigDecimal) get_Value(I_C_InvoiceLine.COLUMNNAME_TaxAmt);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Tax Amount.
     *
     * @param TaxAmt Tax Amount for a document
     */
    public void setTaxAmt(BigDecimal TaxAmt) {
        set_Value(I_C_InvoiceLine.COLUMNNAME_TaxAmt, TaxAmt);
    }

    /**
     * Get User Element List 1.
     *
     * @return User defined list element #1
     */
    public int getUser1_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_User1_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User Element List 1.
     *
     * @param User1_ID User defined list element #1
     */
    public void setUser1_ID(int User1_ID) {
        if (User1_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_User1_ID, null);
        else set_Value(I_C_InvoiceLine.COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
    }

    /**
     * Get User Element List 2.
     *
     * @return User defined list element #2
     */
    public int getUser2_ID() {
        Integer ii = (Integer) get_Value(I_C_InvoiceLine.COLUMNNAME_User2_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set User Element List 2.
     *
     * @param User2_ID User defined list element #2
     */
    public void setUser2_ID(int User2_ID) {
        if (User2_ID < 1) set_Value(I_C_InvoiceLine.COLUMNNAME_User2_ID, null);
        else set_Value(I_C_InvoiceLine.COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
    }

    @Override
    public int getTableId() {
        return I_C_InvoiceLine.Table_ID;
    }
}
