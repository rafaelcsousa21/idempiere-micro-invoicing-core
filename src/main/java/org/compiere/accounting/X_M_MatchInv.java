package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_M_MatchInv;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for M_MatchInv
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_MatchInv extends PO implements I_M_MatchInv {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_MatchInv(Properties ctx, int M_MatchInv_ID) {
        super(ctx, M_MatchInv_ID);
        /*
         * if (M_MatchInv_ID == 0) { setC_InvoiceLine_ID (0); setDateAcct (new Timestamp(
         * System.currentTimeMillis() )); setDateTrx (new Timestamp( System.currentTimeMillis() ));
         * setM_InOutLine_ID (0); setM_MatchInv_ID (0); setM_Product_ID (0); setPosted (false);
         * setProcessed (false); setProcessing (false); setQty (Env.ZERO); }
         */
    }

    /**
     * Load Constructor
     */
    public X_M_MatchInv(Properties ctx, Row row) {
        super(ctx, row);
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
        return "X_M_MatchInv[" + getId() + "]";
    }

    /**
     * Get Invoice Line.
     *
     * @return Invoice Detail Line
     */
    public int getC_InvoiceLine_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_InvoiceLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Invoice Line.
     *
     * @param C_InvoiceLine_ID Invoice Detail Line
     */
    public void setC_InvoiceLine_ID(int C_InvoiceLine_ID) {
        if (C_InvoiceLine_ID < 1) setValueNoCheck(COLUMNNAME_C_InvoiceLine_ID, null);
        else setValueNoCheck(COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
    }

    /**
     * Get Account Date.
     *
     * @return Accounting Date
     */
    public Timestamp getDateAcct() {
        return (Timestamp) getValue(COLUMNNAME_DateAcct);
    }

    /**
     * Set Account Date.
     *
     * @param DateAcct Accounting Date
     */
    public void setDateAcct(Timestamp DateAcct) {
        setValue(COLUMNNAME_DateAcct, DateAcct);
    }

    /**
     * Get Transaction Date.
     *
     * @return Transaction Date
     */
    public Timestamp getDateTrx() {
        return (Timestamp) getValue(COLUMNNAME_DateTrx);
    }

    /**
     * Set Transaction Date.
     *
     * @param DateTrx Transaction Date
     */
    public void setDateTrx(Timestamp DateTrx) {
        setValueNoCheck(COLUMNNAME_DateTrx, DateTrx);
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
        setValue(COLUMNNAME_Description, Description);
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
     * Set Attribute Set Instance.
     *
     * @param M_AttributeSetInstance_ID Product Attribute Set Instance
     */
    public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID < 0) setValueNoCheck(COLUMNNAME_M_AttributeSetInstance_ID, null);
        else
            setValueNoCheck(
                    COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
    }

    /**
     * Get Attribute Set Instance.
     *
     * @return Product Attribute Set Instance
     */
    public int getMAttributeSetInstance_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_AttributeSetInstance_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Shipment/Receipt Line.
     *
     * @return Line on Shipment or Receipt document
     */
    public int getM_InOutLine_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_InOutLine_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Shipment/Receipt Line.
     *
     * @param M_InOutLine_ID Line on Shipment or Receipt document
     */
    public void setM_InOutLine_ID(int M_InOutLine_ID) {
        if (M_InOutLine_ID < 1) setValueNoCheck(COLUMNNAME_M_InOutLine_ID, null);
        else setValueNoCheck(COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
    }

    /**
     * Get Match Invoice.
     *
     * @return Match Shipment/Receipt to Invoice
     */
    public int getM_MatchInv_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_M_MatchInv_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Product.
     *
     * @param M_Product_ID Product, Service, Item
     */
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) setValueNoCheck(COLUMNNAME_M_Product_ID, null);
        else setValueNoCheck(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
    }

    /**
     * Get Posted.
     *
     * @return Posting status
     */
    public boolean isPosted() {
        Object oo = getValue(COLUMNNAME_Posted);
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
        setValueNoCheck(COLUMNNAME_Posted, Boolean.valueOf(Posted));
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
        setValueNoCheck(COLUMNNAME_Processed, Boolean.valueOf(Processed));
    }

    /**
     * Set Process Now.
     *
     * @param Processing Process Now
     */
    public void setProcessing(boolean Processing) {
        setValue(COLUMNNAME_Processing, Boolean.valueOf(Processing));
    }

    /**
     * Get Quantity.
     *
     * @return Quantity
     */
    public BigDecimal getQty() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_Qty);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Quantity.
     *
     * @param Qty Quantity
     */
    public void setQty(BigDecimal Qty) {
        setValueNoCheck(COLUMNNAME_Qty, Qty);
    }

    /**
     * Get Reversal ID.
     *
     * @return ID of document reversal
     */
    public int getReversal_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_Reversal_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Reversal ID.
     *
     * @param Reversal_ID ID of document reversal
     */
    public void setReversal_ID(int Reversal_ID) {
        if (Reversal_ID < 1) setValue(COLUMNNAME_Reversal_ID, null);
        else setValue(COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
    }

    @Override
    public int getTableId() {
        return I_M_MatchInv.Table_ID;
    }
}
