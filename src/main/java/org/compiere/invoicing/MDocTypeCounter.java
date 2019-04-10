package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.model.I_C_DocTypeCounter;
import org.compiere.orm.MDocType;
import org.compiere.orm.MDocTypeKt;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;

import java.util.logging.Level;


/**
 * Counter Document Type Model
 *
 * @author Jorg Janke
 * @version $Id: MDocTypeCounter.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MDocTypeCounter extends X_C_DocTypeCounter {
    /**
     *
     */
    private static final long serialVersionUID = 3469046560457430527L;
    /**
     * Counter Relationship Cache
     */
    private static CCache<Integer, MDocTypeCounter> s_counter =
            new CCache<>(
                    I_C_DocTypeCounter.Table_Name, "C_DocTypeCounter_Relation", 20);
    /**
     * Static Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MDocTypeCounter.class);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param C_DocTypeCounter_ID id
     */
    public MDocTypeCounter(int C_DocTypeCounter_ID) {
        super(C_DocTypeCounter_ID);
        if (C_DocTypeCounter_ID == 0) {
            setIsCreateCounter(true); // Y
            setIsValid(false);
        }
    } //	MDocTypeCounter

    /**
     * Load Constructor
     */
    public MDocTypeCounter(Row row) {
        super(row);
    } //	MDocTypeCounter

    /**
     * Get Counter document for document type
     *
     * @param C_DocType_ID base document
     * @return counter document C_DocType_ID or 0 or -1 if no counter doc
     */
    public static int getCounterDocTypeId(int C_DocType_ID) {
        //	Direct Relationship
        MDocTypeCounter dtCounter = getCounterDocType(C_DocType_ID);
        if (dtCounter != null) {
            if (!dtCounter.isCreateCounter() || !dtCounter.isValid()) return -1;
            return dtCounter.getCounterDocTypeId();
        }

        //	Indirect Relationship
        int Counter_C_DocType_ID = 0;
        MDocType dt = MDocTypeKt.getDocumentType(C_DocType_ID);
        if (!dt.isCreateCounter()) return -1;
        String cDocBaseType = getCounterDocBaseType(dt.getDocBaseType());
        if (cDocBaseType == null) return 0;
        MDocType[] counters = MDocTypeKt.getDocumentTypeOfDocBaseType(cDocBaseType);
        for (int i = 0; i < counters.length; i++) {
            MDocType counter = counters[i];
            if (counter.isDefaultCounterDoc()) {
                Counter_C_DocType_ID = counter.getDocTypeId();
                break;
            }
            if (counter.isDefault()) Counter_C_DocType_ID = counter.getDocTypeId();
            else if (i == 0) Counter_C_DocType_ID = counter.getDocTypeId();
        }
        return Counter_C_DocType_ID;
    } // getCounterDocType_ID

    /**
     * Get (first) valid Counter document for document type
     *
     * @param C_DocType_ID base document
     * @return counter document (may be invalid) or null
     */
    public static MDocTypeCounter getCounterDocType(int C_DocType_ID) {
        Integer key = C_DocType_ID;
        MDocTypeCounter retValue = s_counter.get(key);
        if (retValue != null) return retValue;

        //	Direct Relationship
        return MBaseDocTypeCounterKt.getCounterDocType(C_DocType_ID);
    } //	getCounterDocType

    /**
     * Get Counter Document BaseType
     *
     * @param DocBaseType Document Base Type (e.g. SOO)
     * @return Counter Document BaseType (e.g. POO) or null if there is none
     */
    public static String getCounterDocBaseType(String DocBaseType) {
        if (DocBaseType == null) return null;
        String retValue = null;
        //	SO/PO
        switch (DocBaseType) {
            case MDocType.DOCBASETYPE_SalesOrder:
                retValue = MDocType.DOCBASETYPE_PurchaseOrder;
                break;
            case MDocType.DOCBASETYPE_PurchaseOrder:
                retValue = MDocType.DOCBASETYPE_SalesOrder;
                break;
            //	AP/AR Invoice
            case MDocType.DOCBASETYPE_APInvoice:
                retValue = MDocType.DOCBASETYPE_ARInvoice;
                break;
            case MDocType.DOCBASETYPE_ARInvoice:
                retValue = MDocType.DOCBASETYPE_APInvoice;
                break;
            //	Shipment
            case MDocType.DOCBASETYPE_MaterialDelivery:
                retValue = MDocType.DOCBASETYPE_MaterialReceipt;
                break;
            case MDocType.DOCBASETYPE_MaterialReceipt:
                retValue = MDocType.DOCBASETYPE_MaterialDelivery;
                break;
            //	AP/AR CreditMemo
            case MDocType.DOCBASETYPE_APCreditMemo:
                retValue = MDocType.DOCBASETYPE_ARCreditMemo;
                break;
            case MDocType.DOCBASETYPE_ARCreditMemo:
                retValue = MDocType.DOCBASETYPE_APCreditMemo;
                break;
            //	Receipt / Payment
            case MDocType.DOCBASETYPE_ARReceipt:
                retValue = MDocType.DOCBASETYPE_APPayment;
                break;
            case MDocType.DOCBASETYPE_APPayment:
                retValue = MDocType.DOCBASETYPE_ARReceipt;
                break;
            //
            default:
                s_log.log(Level.SEVERE, "getCounterDocBaseType for " + DocBaseType + ": None found");
                break;
        }
        return retValue;
    } //	getCounterDocBaseType

    /**
     * Set C_DocType_ID
     *
     * @param C_DocType_ID id
     */
    public void setDocumentTypeId(int C_DocType_ID) {
        super.setDocumentTypeId(C_DocType_ID);
        if (isValid()) setIsValid(false);
    } //	setDocumentTypeId

    /**
     * Set Counter C_DocType_ID
     *
     * @param Counter_C_DocType_ID id
     */
    public void setCounterDocTypeId(int Counter_C_DocType_ID) {
        super.setCounterDocTypeId(Counter_C_DocType_ID);
        if (isValid()) setIsValid(false);
    } //	setCounterDocTypeId

    /**
     * Get Doc Type
     *
     * @return doc type or null if not existing
     */
    public MDocType getDocType() {
        MDocType dt = null;
        if (getDocumentTypeId() > 0) {
            dt = MDocTypeKt.getDocumentType(getDocumentTypeId());
            if (dt.getId() == 0) dt = null;
        }
        return dt;
    } //	getDocType

    /**
     * Get Counter Doc Type
     *
     * @return counter doc type or null if not existing
     */
    public MDocType getCounterDocType() {
        MDocType dt = null;
        if (getCounterDocTypeId() > 0) {
            dt = MDocTypeKt.getDocumentType(getCounterDocTypeId());
            if (dt.getId() == 0) dt = null;
        }
        return dt;
    } //	getCounterDocType

    /**
     * ************************************************************************ Validate Document Type
     * compatability
     *
     * @return Error message or null if valid
     */
    public String validate() {
        MDocType dt = getDocType();
        if (dt == null) {
            log.log(Level.SEVERE, "No DocType=" + getDocumentTypeId());
            setIsValid(false);
            return "No Document Type";
        }
        MDocType c_dt = getCounterDocType();
        if (c_dt == null) {
            log.log(Level.SEVERE, "No Counter DocType=" + getCounterDocTypeId());
            setIsValid(false);
            return "No Counter Document Type";
        }
        //
        String dtBT = dt.getDocBaseType();
        String c_dtBT = c_dt.getDocBaseType();
        if (log.isLoggable(Level.FINE)) log.fine(dtBT + " -> " + c_dtBT);

        //	SO / PO
        if ((MDocType.DOCBASETYPE_SalesOrder.equals(dtBT)
                && MDocType.DOCBASETYPE_PurchaseOrder.equals(c_dtBT))
                || (MDocType.DOCBASETYPE_SalesOrder.equals(c_dtBT)
                && MDocType.DOCBASETYPE_PurchaseOrder.equals(dtBT))) setIsValid(true);
            //	AP/AR Invoice
        else if ((MDocType.DOCBASETYPE_APInvoice.equals(dtBT)
                && MDocType.DOCBASETYPE_ARInvoice.equals(c_dtBT))
                || (MDocType.DOCBASETYPE_APInvoice.equals(c_dtBT)
                && MDocType.DOCBASETYPE_ARInvoice.equals(dtBT))) setIsValid(true);
            //	Shipment
        else if ((MDocType.DOCBASETYPE_MaterialDelivery.equals(dtBT)
                && MDocType.DOCBASETYPE_MaterialReceipt.equals(c_dtBT))
                || (MDocType.DOCBASETYPE_MaterialDelivery.equals(c_dtBT)
                && MDocType.DOCBASETYPE_MaterialReceipt.equals(dtBT))) setIsValid(true);
            //	AP/AR CreditMemo
        else if ((MDocType.DOCBASETYPE_APCreditMemo.equals(dtBT)
                && MDocType.DOCBASETYPE_ARCreditMemo.equals(c_dtBT))
                || (MDocType.DOCBASETYPE_APCreditMemo.equals(c_dtBT)
                && MDocType.DOCBASETYPE_ARCreditMemo.equals(dtBT))) setIsValid(true);
            //	Receipt / Payment
        else if ((MDocType.DOCBASETYPE_ARReceipt.equals(dtBT)
                && MDocType.DOCBASETYPE_APPayment.equals(c_dtBT))
                || (MDocType.DOCBASETYPE_ARReceipt.equals(c_dtBT)
                && MDocType.DOCBASETYPE_APPayment.equals(dtBT))) setIsValid(true);
        else {
            log.warning("NOT - " + dtBT + " -> " + c_dtBT);
            setIsValid(false);
            return "Not valid";
        }
        //	Counter should have document numbering
        if (!c_dt.isDocNoControlled())
            return "Counter Document Type should be automatically Document Number controlled";
        return null;
    } //	validate

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        return "MDocTypeCounter[" + getId() +
                "," +
                getName() +
                ",C_DocType_ID=" +
                getDocumentTypeId() +
                ",Counter=" +
                getCounterDocTypeId() +
                ",DocAction=" +
                getDocAction() +
                "]";
    } //	toString

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        if (getOrgId() != 0) setOrgId(0);

        if (!newRecord && (isValueChanged("C_DocType_ID") || isValueChanged("Counter_C_DocType_ID")))
            setIsValid(false);

        //	try to validate
        if (!isValid()) validate();
        return true;
    } //	beforeSave
} //	MDocTypeCounter
