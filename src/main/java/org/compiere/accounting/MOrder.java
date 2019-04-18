package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.bank.MBankAccount;
import org.compiere.crm.MBPartner;
import org.compiere.docengine.DocumentEngine;
import org.compiere.invoicing.MConversionRate;
import org.compiere.invoicing.MDocTypeCounter;
import org.compiere.invoicing.MInOut;
import org.compiere.invoicing.MInvoice;
import org.compiere.invoicing.MInvoiceLine;
import org.compiere.invoicing.MInvoicePaySchedule;
import org.compiere.invoicing.MPaymentTerm;
import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.model.I_C_BankAccount;
import org.compiere.model.DocumentType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_OrderLandedCost;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_OrderTax;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.order.MInOutLine;
import org.compiere.order.MOrderPaySchedule;
import org.compiere.orm.MDocType;
import org.compiere.orm.MDocTypeKt;
import org.compiere.orm.MOrgInfo;
import org.compiere.orm.MOrgInfoKt;
import org.compiere.orm.MOrgKt;
import org.compiere.orm.MSequence;
import org.compiere.orm.MSysConfig;
import org.compiere.orm.Query;
import org.compiere.process.CompleteActionResult;
import org.compiere.process.DocAction;
import org.compiere.product.MPriceList;
import org.compiere.production.MProject;
import org.compiere.util.MsgKt;
import org.compiere.validation.ModelValidationEngine;
import org.compiere.validation.ModelValidator;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.exceptions.FillMandatoryException;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

import static software.hsharp.core.util.DBKt.getSQLValue;
import static software.hsharp.core.util.DBKt.getSQLValueEx;

public class MOrder extends org.compiere.order.MOrder implements DocAction, IPODoc {

    /**
     * Order Lines
     */
    protected I_C_OrderLine[] m_lines = null;

    /**
     * ************************************************************************ Default Constructor
     *
     * @param C_Order_ID order to load, (0 create new order)
     */
    public MOrder(int C_Order_ID) {
        super(C_Order_ID);
    }

    /**
     * Load Constructor
     */
    public MOrder(Row row) {
        super(row);
    } //	MOrder

    /**
     * ************************************************************************ Project Constructor
     *
     * @param project      Project to create Order from
     * @param IsSOTrx      sales order
     * @param DocSubTypeSO if SO DocType Target (default DocSubTypeSO_OnCredit)
     */
    public MOrder(MProject project, boolean IsSOTrx, String DocSubTypeSO) {
        this(0);
        setClientId(project.getClientId());
        setOrgId(project.getOrgId());
        setCampaignId(project.getCampaignId());
        setSalesRepresentativeId(project.getSalesRepresentativeId());
        //
        setProjectId(project.getProjectId());
        setDescription(project.getName());
        Timestamp ts = project.getDateContract();
        if (ts != null) setDateOrdered(ts);
        ts = project.getDateFinish();
        if (ts != null) setDatePromised(ts);
        //
        setBusinessPartnerId(project.getBusinessPartnerId());
        setBusinessPartnerLocationId(project.getBusinessPartnerLocationId());
        setUserId(project.getUserId());
        //
        setWarehouseId(project.getWarehouseId());
        setPriceListId(project.getPriceListId());
        setPaymentTermId(project.getPaymentTermId());
        //
        setIsSOTrx(IsSOTrx);
        if (IsSOTrx) {
            if (DocSubTypeSO == null || DocSubTypeSO.length() == 0)
                setTargetDocumentTypeId(DocSubTypeSO_OnCredit);
            else setTargetDocumentTypeId(DocSubTypeSO);
        } else setTargetDocumentTypeId();
    } //	MOrder

    /**
     * Create new Order by copying
     *
     * @param from               order
     * @param dateDoc            date of the document date
     * @param C_DocTypeTarget_ID target document type
     * @param isSOTrx            sales order
     * @param counter            create counter links
     * @param copyASI            copy line attributes Attribute Set Instance, Resaouce Assignment
     * @return Order
     */
    public static MOrder copyFrom(
            MOrder from,
            Timestamp dateDoc,
            int C_DocTypeTarget_ID,
            boolean isSOTrx,
            boolean counter,
            boolean copyASI) {
        MOrder to = new MOrder(0);
        return (MOrder)
                doCopyFrom(from, dateDoc, C_DocTypeTarget_ID, isSOTrx, counter, copyASI, to);
    }

    /**
     * Create Invoice
     *
     * @param dt          order document type
     * @param shipment    optional shipment
     * @param invoiceDate invoice date
     * @return invoice or null
     */
    protected I_C_Invoice createInvoice(MDocType dt, MInOut shipment, Timestamp invoiceDate) {
        if (log.isLoggable(Level.INFO)) log.info(dt.toString());
        MInvoice invoice = new MInvoice(this, dt.getDocTypeInvoiceId(), invoiceDate);
        if (!invoice.save()) {
            m_processMsg = "Could not create Invoice";
            return null;
        }

        //	If we have a Shipment - use that as a base
        if (shipment != null) {
            if (!INVOICERULE_AfterDelivery.equals(getInvoiceRule()))
                setInvoiceRule(INVOICERULE_AfterDelivery);
            //
            I_M_InOutLine[] sLines = shipment.getLines(false);
            for (I_M_InOutLine sLine : sLines) {
                //
                MInvoiceLine iLine = new MInvoiceLine(invoice);
                iLine.setShipLine(sLine);
                //	Qty = Delivered
                if (sLine.sameOrderLineUOM()) iLine.setQtyEntered(sLine.getQtyEntered());
                else iLine.setQtyEntered(sLine.getMovementQty());
                iLine.setQtyInvoiced(sLine.getMovementQty());
                if (!iLine.save()) {
                    m_processMsg = "Could not create Invoice Line from Shipment Line";
                    return null;
                }
                //
                sLine.setIsInvoiced(true);
                if (!sLine.save()) {
                    log.warning("Could not update Shipment line: " + sLine);
                }
            }
        } else //	Create Invoice from Order
        {
            if (!INVOICERULE_Immediate.equals(getInvoiceRule())) setInvoiceRule(INVOICERULE_Immediate);
            //
            I_C_OrderLine[] oLines = getLines().toArray(new I_C_OrderLine[0]);
            for (I_C_OrderLine oLine : oLines) {
                //
                MInvoiceLine iLine = new MInvoiceLine(invoice);
                iLine.setOrderLine(oLine);
                //	Qty = Ordered - Invoiced
                iLine.setQtyInvoiced(oLine.getQtyOrdered().subtract(oLine.getQtyInvoiced()));
                if (oLine.getQtyOrdered().compareTo(oLine.getQtyEntered()) == 0)
                    iLine.setQtyEntered(iLine.getQtyInvoiced());
                else
                    iLine.setQtyEntered(
                            iLine
                                    .getQtyInvoiced()
                                    .multiply(oLine.getQtyEntered())
                                    .divide(oLine.getQtyOrdered(), 12, BigDecimal.ROUND_HALF_UP));
                if (!iLine.save()) {
                    m_processMsg = "Could not create Invoice Line from Order Line";
                    return null;
                }
            }
        }

        // Copy payment schedule from order to invoice if any
        for (MOrderPaySchedule ops :
                MOrderPaySchedule.getOrderPaySchedule(getOrderId(), 0)) {
            MInvoicePaySchedule ips = new MInvoicePaySchedule(0);
            copyValues(ops, ips);
            ips.setInvoiceId(invoice.getInvoiceId());
            ips.setOrgId(ops.getOrgId());
            ips.setProcessing(ops.isProcessing());
            ips.setIsActive(ops.isActive());
            if (!ips.save()) {
                m_processMsg = "ERROR: creating pay schedule for invoice from : " + ops.toString();
                return null;
            }
        }

        // added AdempiereException by zuhri
        if (!invoice.processIt(DocAction.Companion.getACTION_Complete()))
            throw new AdempiereException("Failed when processing document - " + invoice.getProcessMsg());
        // end added
        invoice.saveEx();
        setCashLineId(invoice.getCashLineId());
        if (!DOCSTATUS_Completed.equals(invoice.getDocStatus())) {
            m_processMsg = "@C_Invoice_ID@: " + invoice.getProcessMsg();
            return null;
        }
        return invoice;
    } //	createInvoice

    /**
     * ************************************************************************ Before Save
     *
     * @param newRecord new
     * @return save
     */
    @Override
    protected boolean beforeSave(boolean newRecord) {
        //	Client/Org Check
        if (getOrgId() == 0) {
            int context_AD_Org_ID = Env.getOrgId();
            if (context_AD_Org_ID != 0) {
                setOrgId(context_AD_Org_ID);
                log.warning("Changed Org to Context=" + context_AD_Org_ID);
            }
        }
        if (getClientId() == 0) {
            m_processMsg = "AD_Client_ID = 0";
            return false;
        }

        //	New Record Doc Type - make sure DocType set to 0
        if (newRecord && getDocumentTypeId() == 0) setDocumentTypeId(0);

        //	Default Warehouse
        if (getWarehouseId() == 0) {
            int ii = Env.getContextAsInt("#M_Warehouse_ID");
            if (ii != 0) setWarehouseId(ii);
            else {
                throw new FillMandatoryException(COLUMNNAME_M_Warehouse_ID);
            }
        }
        MWarehouse wh = MWarehouse.get(getWarehouseId());
        //	Warehouse Org
        if (newRecord || isValueChanged("AD_Org_ID") || isValueChanged("M_Warehouse_ID")) {
            if (wh.getOrgId() != getOrgId()) log.saveWarning("WarehouseOrgConflict", "");
        }

        boolean disallowNegInv = wh.isDisallowNegativeInv();
        String DeliveryRule = getDeliveryRule();
        if ((disallowNegInv && DELIVERYRULE_Force.equals(DeliveryRule))
                || (DeliveryRule == null || DeliveryRule.length() == 0))
            setDeliveryRule(DELIVERYRULE_Availability);

        //	Reservations in Warehouse
        if (!newRecord && isValueChanged("M_Warehouse_ID")) {
            I_C_OrderLine[] lines = getLines(false, null).toArray(new I_C_OrderLine[0]);
            for (I_C_OrderLine line : lines) {
                if (!line.canChangeWarehouse()) return false;
            }
        }

        //	No Partner Info - set Template
        if (getBusinessPartnerId() == 0)
            setBPartner(org.compiere.crm.MBPartner.getTemplate(getClientId()));
        if (getBusinessPartnerLocationId() == 0)
            setBPartner(new MBPartner(getBusinessPartnerId()));
        //	No Bill - get from Ship
        if (getBill_BPartnerId() == 0) {
            setBill_BPartnerId(getBusinessPartnerId());
            setBusinessPartnerInvoicingLocationId(getBusinessPartnerLocationId());
        }
        if (getBusinessPartnerInvoicingLocationId() == 0)
            setBusinessPartnerInvoicingLocationId(getBusinessPartnerLocationId());

        //	Default Price List
        if (getPriceListId() == 0) {
            int ii =
                    getSQLValueEx(
                            "SELECT M_PriceList_ID FROM M_PriceList "
                                    + "WHERE AD_Client_ID=? AND IsSOPriceList=? AND IsActive=?"
                                    + "ORDER BY IsDefault DESC",
                            getClientId(),
                            isSOTrx(),
                            true);
            if (ii != 0) setPriceListId(ii);
        }
        //	Default Currency
        if (getCurrencyId() == 0) {
            String sql = "SELECT C_Currency_ID FROM M_PriceList WHERE M_PriceList_ID=?";
            int ii = getSQLValue(sql, getPriceListId());
            if (ii != 0) setCurrencyId(ii);
            else setCurrencyId(Env.getContextAsInt("#C_Currency_ID"));
        }

        //	Default Sales Rep
        if (getSalesRepresentativeId() == 0) {
            int ii = Env.getContextAsInt("#SalesRep_ID");
            if (ii != 0) setSalesRepresentativeId(ii);
        }

        //	Default Document Type
        if (getTargetDocumentTypeId() == 0) setTargetDocumentTypeId(DocSubTypeSO_Standard);

        //	Default Payment Term
        if (getPaymentTermId() == 0) {
            int ii = Env.getContextAsInt("#C_PaymentTerm_ID");
            if (ii != 0) setPaymentTermId(ii);
            else {
                String sql =
                        "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE AD_Client_ID=? AND IsDefault='Y' AND IsActive='Y'";
                ii = getSQLValue(sql, getClientId());
                if (ii != 0) setPaymentTermId(ii);
            }
        }

        // IDEMPIERE-63
        // for documents that can be reactivated we cannot allow changing
        // C_DocTypeTarget_ID or C_DocType_ID if they were already processed and
        // isOverwriteSeqOnComplete
        // neither change the Date if isOverwriteDateOnComplete
        BigDecimal previousProcessedOn = (BigDecimal) getValueOld(COLUMNNAME_ProcessedOn);
        if (!newRecord && previousProcessedOn != null && previousProcessedOn.signum() > 0) {
            int previousDocTypeID = (Integer) getValueOld(COLUMNNAME_C_DocTypeTarget_ID);
            MDocType previousdt = MDocTypeKt.getDocumentType(previousDocTypeID);
            if (isValueChanged(COLUMNNAME_C_DocType_ID)
                    || isValueChanged(COLUMNNAME_C_DocTypeTarget_ID)) {
                if (previousdt.isOverwriteSeqOnComplete()) {
                    log.saveError("Error", MsgKt.getMsg("CannotChangeProcessedDocType"));
                    return false;
                }
            }
            if (isValueChanged(COLUMNNAME_DateOrdered)) {
                if (previousdt.isOverwriteDateOnComplete()) {
                    log.saveError("Error", MsgKt.getMsg("CannotChangeProcessedDate"));
                    return false;
                }
            }
        }

        // IDEMPIERE-1597 Price List and Date must be not-updateable
        if (!newRecord
                && (isValueChanged(COLUMNNAME_M_PriceList_ID)
                || isValueChanged(COLUMNNAME_DateOrdered))) {
            int cnt =
                    getSQLValueEx(
                            "SELECT COUNT(*) FROM C_OrderLine WHERE C_Order_ID=? AND M_Product_ID>0",
                            getOrderId());
            if (cnt > 0) {
                if (isValueChanged(COLUMNNAME_M_PriceList_ID)) {
                    log.saveError("Error", MsgKt.getMsg("CannotChangePl"));
                    return false;
                }
                if (isValueChanged(COLUMNNAME_DateOrdered)) {
                    I_M_PriceList pList = MPriceList.get(getPriceListId());
                    I_M_PriceList_Version plOld =
                            pList.getPriceListVersion((Timestamp) getValueOld(COLUMNNAME_DateOrdered));
                    I_M_PriceList_Version plNew =
                            pList.getPriceListVersion((Timestamp) getValue(COLUMNNAME_DateOrdered));
                    if (plNew == null || !plNew.equals(plOld)) {
                        log.saveError("Error", MsgKt.getMsg("CannotChangeDateOrdered"));
                        return false;
                    }
                }
            }
        }

        if (!recursiveCall && (!newRecord && isValueChanged(COLUMNNAME_C_PaymentTerm_ID))) {
            recursiveCall = true;
            try {
                MPaymentTerm pt = new MPaymentTerm(getPaymentTermId());
                boolean valid = pt.applyOrder(this);
                setIsPayScheduleValid(valid);
            } finally {
                recursiveCall = false;
            }
        }

        return true;
    } //	beforeSave

    /**
     * ************************************************************************ Process document
     *
     * @param processAction document action
     * @return true if performed
     */
    public boolean processIt(@NotNull String processAction) {
        m_processMsg = null;
        DocumentEngine engine = new DocumentEngine(this, getDocStatus());
        return engine.processIt(processAction, getDocAction());
    } //	processIt

    /**
     * Get Lines of Order. (used by web store)
     *
     * @return lines
     */
    public List<I_C_OrderLine> getLines() {
        return getLines(false, null);
    } //	getLines

    /**
     * Get Lines of Order
     *
     * @param requery requery
     * @param orderBy optional order by column
     * @return lines
     */
    public List<I_C_OrderLine> getLines(boolean requery, String orderBy) {
        if (m_lines != null && !requery) {
            return Arrays.asList(m_lines);
        }
        //
        String orderClause = "";
        if (orderBy != null && orderBy.length() > 0) orderClause += orderBy;
        else orderClause += "Line";
        m_lines = getLines(null, orderClause);
        return Arrays.asList(m_lines);
    } //	getLines

    /**
     * ************************************************************************ Get Lines of Order
     *
     * @param whereClause where clause or null (starting with AND)
     * @param orderClause order clause
     * @return lines
     */
    public I_C_OrderLine[] getLines(String whereClause, String orderClause) {
        // red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
        StringBuilder whereClauseFinal = new StringBuilder(MOrderLine.COLUMNNAME_C_Order_ID + "=? ");
        if (!Util.isEmpty(whereClause, true)) whereClauseFinal.append(whereClause);
        if (orderClause.length() == 0) orderClause = MOrderLine.COLUMNNAME_Line;
        //
        List<I_C_OrderLine> list =
                new Query<I_C_OrderLine>(I_C_OrderLine.Table_Name, whereClauseFinal.toString())
                        .setParameters(getId())
                        .setOrderBy(orderClause)
                        .list();
        for (I_C_OrderLine ol : list) {
            ol.setHeaderInfo(this);
        }
        //
        return list.toArray(new I_C_OrderLine[0]);
    } //	getLines

    /**
     * ************************************************************************ Prepare Document
     *
     * @return new status (In Progress or Invalid)
     */
    @NotNull
    public String prepareIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
        if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();
        MDocType dt = MDocTypeKt.getDocumentType(getTargetDocumentTypeId());

        //	Std Period open?
        if (!MPeriod.isOpen(getDateAcct(), dt.getDocBaseType(), getOrgId())) {
            m_processMsg = "@PeriodClosed@";
            return DocAction.Companion.getSTATUS_Invalid();
        }

        if (isSOTrx() && getDeliveryViaRule().equals(DELIVERYVIARULE_Shipper)) {
            if (getShipperId() == 0) {
                m_processMsg = "@FillMandatory@" + MsgKt.getElementTranslation(COLUMNNAME_M_Shipper_ID);
                return DocAction.Companion.getSTATUS_Invalid();
            }

            if (!calculateFreightCharge()) return DocAction.Companion.getSTATUS_Invalid();
        }

        //	Lines
        I_C_OrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID).toArray(new I_C_OrderLine[0]);
        if (lines.length == 0) {
            m_processMsg = "@NoLines@";
            return DocAction.Companion.getSTATUS_Invalid();
        }

        // Bug 1564431
        if (getDeliveryRule() != null && getDeliveryRule().equals(DELIVERYRULE_CompleteOrder)) {
            for (I_C_OrderLine line : lines) {
                I_M_Product product = line.getProduct();
                if (product != null && product.isExcludeAutoDelivery()) {
                    m_processMsg = "@M_Product_ID@ " + product.getSearchKey() + " @IsExcludeAutoDelivery@";
                    return DocAction.Companion.getSTATUS_Invalid();
                }
            }
        }

        //	Convert DocType to Target
        if (getDocumentTypeId() != getTargetDocumentTypeId()) {
            //	Cannot change Std to anything else if different warehouses
            if (getDocumentTypeId() != 0) {
                MDocType dtOld = MDocTypeKt.getDocumentType(getDocumentTypeId());
                if (MDocType.DOCSUBTYPESO_StandardOrder.equals(dtOld.getDocSubTypeSO()) // 	From SO
                        && !MDocType.DOCSUBTYPESO_StandardOrder.equals(dt.getDocSubTypeSO())) // 	To !SO
                {
                    for (I_C_OrderLine line : lines) {
                        if (line.getWarehouseId() != getWarehouseId()) {
                            log.warning("different Warehouse " + line);
                            m_processMsg = "@CannotChangeDocType@";
                            return DocAction.Companion.getSTATUS_Invalid();
                        }
                    }
                }
            }

            //	New or in Progress/Invalid
            if (DOCSTATUS_Drafted.equals(getDocStatus())
                    || DOCSTATUS_InProgress.equals(getDocStatus())
                    || DOCSTATUS_Invalid.equals(getDocStatus())
                    || getDocumentTypeId() == 0) {
                setDocumentTypeId(getTargetDocumentTypeId());
            } else //	convert only if offer
            {
                if (dt.isOffer()) setDocumentTypeId(getTargetDocumentTypeId());
                else {
                    m_processMsg = "@CannotChangeDocType@";
                    return DocAction.Companion.getSTATUS_Invalid();
                }
            }
        } //	convert DocType

        //	Mandatory Product Attribute Set Instance
        for (I_C_OrderLine line : getLines()) {
            if (line.getProductId() > 0 && line.getAttributeSetInstanceId() == 0) {
                I_M_Product product = line.getProduct();
                if (product.isASIMandatory(isSOTrx())) {
                    if (product.getAttributeSet() == null) {
                        m_processMsg = "@NoAttributeSet@=" + product.getSearchKey();
                        return DocAction.Companion.getSTATUS_Invalid();
                    }
                    if (!product.getAttributeSet().excludeTableEntry(MOrderLine.Table_ID, isSOTrx())) {
                        m_processMsg = "@M_AttributeSet_ID@ @IsMandatory@ (@Line@ #" +
                                line.getLine() +
                                ", @M_Product_ID@=" +
                                product.getSearchKey() +
                                ")";
                        return DocAction.Companion.getSTATUS_Invalid();
                    }
                }
            }
        }

        //	Lines
        if (explodeBOM()) lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID).toArray(new I_C_OrderLine[0]);
        if (!reserveStock(dt, lines)) {
            String innerMsg = CLogger.retrieveErrorString("");
            m_processMsg = "Cannot reserve Stock";
            if (!Util.isEmpty(innerMsg)) m_processMsg = m_processMsg + " -> " + innerMsg;
            return DocAction.Companion.getSTATUS_Invalid();
        }
        if (!calculateTaxTotal()) {
            m_processMsg = "Error calculating tax";
            return DocAction.Companion.getSTATUS_Invalid();
        }

        if (getGrandTotal().signum() != 0
                && (PAYMENTRULE_OnCredit.equals(getPaymentRule())
                || PAYMENTRULE_DirectDebit.equals(getPaymentRule()))) {
            if (!createPaySchedule()) {
                m_processMsg = "@ErrorPaymentSchedule@";
                return DocAction.Companion.getSTATUS_Invalid();
            }
        } else {
            if (MOrderPaySchedule.getOrderPaySchedule(getOrderId(), 0).length
                    > 0) {
                m_processMsg = "@ErrorPaymentSchedule@";
                return DocAction.Companion.getSTATUS_Invalid();
            }
        }

        //	Credit Check
        if (isSOTrx()) {
            if (!MDocType.DOCSUBTYPESO_POSOrder.equals(dt.getDocSubTypeSO())
                    || !PAYMENTRULE_Cash.equals(getPaymentRule())
                    || MSysConfig.getBooleanValue(
                    MSysConfig.CHECK_CREDIT_ON_CASH_POS_ORDER, true, getClientId(), getOrgId())) {
                if (!MDocType.DOCSUBTYPESO_PrepayOrder.equals(dt.getDocSubTypeSO())
                        || MSysConfig.getBooleanValue(
                        MSysConfig.CHECK_CREDIT_ON_PREPAY_ORDER, true, getClientId(), getOrgId())) {
                    MBPartner bp =
                            new MBPartner(
                                    getBill_BPartnerId()); // bill bp is guaranteed on beforeSave

                    if (getGrandTotal().signum()
                            > 0) // IDEMPIERE-365 - just check credit if is going to increase the debt
                    {

                        if (MBPartner.SOCREDITSTATUS_CreditStop.equals(bp.getSOCreditStatus())) {
                            m_processMsg =
                                    "@BPartnerCreditStop@ - @TotalOpenBalance@="
                                            + bp.getTotalOpenBalance()
                                            + ", @SO_CreditLimit@="
                                            + bp.getSalesOrderCreditLimit();
                            return DocAction.Companion.getSTATUS_Invalid();
                        }
                        if (MBPartner.SOCREDITSTATUS_CreditHold.equals(bp.getSOCreditStatus())) {
                            m_processMsg =
                                    "@BPartnerCreditHold@ - @TotalOpenBalance@="
                                            + bp.getTotalOpenBalance()
                                            + ", @SO_CreditLimit@="
                                            + bp.getSalesOrderCreditLimit();
                            return DocAction.Companion.getSTATUS_Invalid();
                        }
                        BigDecimal grandTotal =
                                MConversionRate.convertBase(

                                        getGrandTotal(),
                                        getCurrencyId(),
                                        getDateOrdered(),
                                        getConversionTypeId(),
                                        getClientId(),
                                        getOrgId());
                        if (MBPartner.SOCREDITSTATUS_CreditHold.equals(bp.getSOCreditStatus(grandTotal))) {
                            m_processMsg =
                                    "@BPartnerOverOCreditHold@ - @TotalOpenBalance@="
                                            + bp.getTotalOpenBalance()
                                            + ", @GrandTotal@="
                                            + grandTotal
                                            + ", @SO_CreditLimit@="
                                            + bp.getSalesOrderCreditLimit();
                            return DocAction.Companion.getSTATUS_Invalid();
                        }
                    }
                }
            }
        }

        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
        if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();

        m_justPrepared = true;
        //	if (!DOCACTION_Complete.equals(getDocAction()))		don't set for just prepare
        //		setDocAction(DOCACTION_Complete);
        return DocAction.Companion.getSTATUS_InProgress();
    } //	prepareIt

    /**
     * Reserve Inventory. Counterpart: MInOut.completeIt()
     *
     * @param dt    document type or null
     * @param lines order lines (ordered by M_Product_ID for deadlock prevention)
     * @return true if (un) reserved
     */
    protected boolean reserveStock(MDocType dt, I_C_OrderLine[] lines) {
        if (dt == null) dt = MDocTypeKt.getDocumentType(getDocumentTypeId());

        //	Binding
        boolean binding = !dt.isProposal();
        //	Not binding - i.e. Target=0
        if (DOCACTION_Void.equals(getDocAction())
                //	Closing Binding Quotation
                || (MDocType.DOCSUBTYPESO_Quotation.equals(dt.getDocSubTypeSO())
                && DOCACTION_Close.equals(getDocAction()))) // || isDropShip() )
            binding = false;
        boolean isSOTrx = isSOTrx();
        if (log.isLoggable(Level.FINE)) log.fine("Binding=" + binding + " - IsSOTrx=" + isSOTrx);
        //	Force same WH for all but SO/PO
        int header_M_Warehouse_ID = getWarehouseId();
        if (MDocType.DOCSUBTYPESO_StandardOrder.equals(dt.getDocSubTypeSO())
                || MDocType.DOCBASETYPE_PurchaseOrder.equals(dt.getDocBaseType()))
            header_M_Warehouse_ID = 0; // 	don't enforce

        BigDecimal Volume = Env.ZERO;
        BigDecimal Weight = Env.ZERO;

        //	Always check and (un) Reserve Inventory
        for (I_C_OrderLine line : lines) {
            //	Check/set WH/Org
            if (header_M_Warehouse_ID != 0) // 	enforce WH
            {
                if (header_M_Warehouse_ID != line.getWarehouseId())
                    line.setWarehouseId(header_M_Warehouse_ID);
                if (getOrgId() != line.getOrgId()) line.setOrgId(getOrgId());
            }
            //	Binding
            BigDecimal target = binding ? line.getQtyOrdered() : Env.ZERO;
            BigDecimal difference =
                    target.subtract(line.getQtyReserved()).subtract(line.getQtyDelivered());

            if (difference.signum() == 0 || line.getQtyOrdered().signum() < 0) {
                if (difference.signum() == 0 || line.getQtyReserved().signum() == 0) {
                    I_M_Product product = line.getProduct();
                    if (product != null) {
                        Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
                        Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
                    }
                    continue;
                } else if (line.getQtyOrdered().signum() < 0 && line.getQtyReserved().signum() > 0) {
                    difference = line.getQtyReserved().negate();
                }
            }

            if (log.isLoggable(Level.FINE))
                log.fine(
                        "Line="
                                + line.getLine()
                                + " - Target="
                                + target
                                + ",Difference="
                                + difference
                                + " - Ordered="
                                + line.getQtyOrdered()
                                + ",Reserved="
                                + line.getQtyReserved()
                                + ",Delivered="
                                + line.getQtyDelivered());

            //	Check Product - Stocked and Item
            I_M_Product product = line.getProduct();
            if (product != null) {
                if (product.isStocked()) {
                    //	Update Reservation Storage
                    if (!MStorageReservation.add(

                            line.getWarehouseId(),
                            line.getProductId(),
                            line.getAttributeSetInstanceId(),
                            difference,
                            isSOTrx
                    )) return false;
                } //	stocked
                //	update line
                line.setQtyReserved(line.getQtyReserved().add(difference));
                if (!line.save()) return false;
                //
                Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
                Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
            } //	product
        } //	reverse inventory

        setVolume(Volume);
        setWeight(Weight);
        return true;
    } //	reserveStock

    /**
     * ************************************************************************ Complete Document
     *
     * @return new status (Complete, In Progress, Invalid, Waiting ..)
     */
    @NotNull
    public CompleteActionResult completeIt() {
        MDocType dt = MDocTypeKt.getDocumentType(getDocumentTypeId());
        String DocSubTypeSO = dt.getDocSubTypeSO();

        //	Just prepare
        if (DOCACTION_Prepare.equals(getDocAction())) {
            setProcessed(false);
            return new CompleteActionResult(DocAction.Companion.getSTATUS_InProgress());
        }

        // Set the definite document number after completed (if needed)
        setDefiniteDocumentNo();

        //	Offers
        if (MDocType.DOCSUBTYPESO_Proposal.equals(DocSubTypeSO)
                || MDocType.DOCSUBTYPESO_Quotation.equals(DocSubTypeSO)) {
            //	Binding
            if (MDocType.DOCSUBTYPESO_Quotation.equals(DocSubTypeSO))
                reserveStock(dt, getLines(true, MOrderLine.COLUMNNAME_M_Product_ID).toArray(new I_C_OrderLine[0]));
            m_processMsg =
                    ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
            if (m_processMsg != null)
                return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
            m_processMsg =
                    ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
            if (m_processMsg != null)
                return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
            setProcessed(true);
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Completed());
        }
        //	Waiting Payment - until we have a payment
        if (!m_forceCreation
                && MDocType.DOCSUBTYPESO_PrepayOrder.equals(DocSubTypeSO)
                && getPaymentId() == 0
                && getCashLineId() == 0) {
            setProcessed(true);
            return new CompleteActionResult(DocAction.Companion.getSTATUS_WaitingPayment());
        }

        //	Re-Check
        if (!m_justPrepared) {
            String status = prepareIt();
            m_justPrepared = false;
            if (!DocAction.Companion.getSTATUS_InProgress().equals(status))
                return new CompleteActionResult(status);
        }

        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
        if (m_processMsg != null)
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());

        //	Implicit Approval
        if (!isApproved()) approveIt();
        getLines(true, null);
        if (log.isLoggable(Level.INFO)) log.info(toString());
        StringBuilder info = new StringBuilder();

        boolean realTimePOS =
                MSysConfig.getBooleanValue(MSysConfig.REAL_TIME_POS, false, getClientId());

        //	Create SO Shipment - Force Shipment
        MInOut shipment = null;
        if (MDocType.DOCSUBTYPESO_OnCreditOrder.equals(DocSubTypeSO) // 	(W)illCall(I)nvoice
                || MDocType.DOCSUBTYPESO_WarehouseOrder.equals(DocSubTypeSO) // 	(W)illCall(P)ickup
                || MDocType.DOCSUBTYPESO_POSOrder.equals(DocSubTypeSO) // 	(W)alkIn(R)eceipt
                || MDocType.DOCSUBTYPESO_PrepayOrder.equals(DocSubTypeSO)) {
            if (!DELIVERYRULE_Force.equals(getDeliveryRule())) {
                MWarehouse wh = new MWarehouse(getWarehouseId());
                if (!wh.isDisallowNegativeInv()) setDeliveryRule(DELIVERYRULE_Force);
            }
            //
            shipment = createShipment(dt, realTimePOS ? null : getDateOrdered());
            if (shipment == null)
                return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
            info.append("@M_InOut_ID@: ").append(shipment.getDocumentNo());
            String msg = shipment.getProcessMsg();
            if (msg != null && msg.length() > 0) info.append(" (").append(msg).append(")");
        } //	Shipment

        I_C_Invoice invoice = null;
        //	Create SO Invoice - Always invoice complete Order
        if (MDocType.DOCSUBTYPESO_POSOrder.equals(DocSubTypeSO)
                || MDocType.DOCSUBTYPESO_OnCreditOrder.equals(DocSubTypeSO)
                || MDocType.DOCSUBTYPESO_PrepayOrder.equals(DocSubTypeSO)) {
            invoice = createInvoice(dt, shipment, realTimePOS ? null : getDateOrdered());
            if (invoice == null) return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
            info.append(" - @C_Invoice_ID@: ").append(invoice.getDocumentNo());
            String msg = invoice.getProcessMsg();
            if (msg != null && msg.length() > 0) info.append(" (").append(msg).append(")");
        } //	Invoice

        String msg = createPOSPayments();
        if (msg != null) {
            m_processMsg = msg;
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
        }

        //	Counter Documents
        org.compiere.order.MOrder counter = createCounterDoc();
        if (counter != null) info.append(" - @CounterDoc@: @Order@=").append(counter.getDocumentNo());
        //	User Validation
        String valid =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
        if (valid != null) {
            if (info.length() > 0) info.append(" - ");
            info.append(valid);
            m_processMsg = info.toString();
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
        }

        // landed cost
        if (!isSOTrx()) {
            String error = landedCostAllocation();
            if (!Util.isEmpty(error)) {
                m_processMsg = error;
                return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
            }
        }

        setProcessed(true);
        m_processMsg = info.toString();
        //
        setDocAction(DOCACTION_Close);
        return new CompleteActionResult(DocAction.Companion.getSTATUS_Completed(), invoice);
    } //	completeIt

    protected String landedCostAllocation() {
        I_C_OrderLandedCost[] landedCosts = MOrderLandedCost.getOfOrder(getOrderId());
        for (I_C_OrderLandedCost landedCost : landedCosts) {
            String error = landedCost.distributeLandedCost();
            if (!Util.isEmpty(error)) return error;
        }
        return "";
    }

    protected String createPOSPayments() {

        // Just for POS order with payment rule mixed
        if (!this.isSOTrx()) return null;
        if (!org.compiere.order.MOrder.DocSubTypeSO_POS.equals(this.getDocumentType().getDocSubTypeSO()))
            return null;
        if (!PAYMENTRULE_MixedPOSPayment.equals(this.getPaymentRule())) return null;

        // Verify sum of all payments pos must be equal to the grandtotal of POS invoice (minus
        // withholdings)
        I_C_Invoice[] invoices = this.getInvoices();
        if (invoices == null || invoices.length == 0) return "@NoPOSInvoices@";
        I_C_Invoice lastInvoice = invoices[0];
        BigDecimal grandTotal = lastInvoice.getGrandTotal();

        List<X_C_POSPayment> pps =
                new Query(X_C_POSPayment.Table_Name, "C_Order_ID=?")
                        .setParameters(this.getOrderId())
                        .setOnlyActiveRecords(true)
                        .list();
        BigDecimal totalPOSPayments = Env.ZERO;
        for (X_C_POSPayment pp : pps) {
            totalPOSPayments = totalPOSPayments.add(pp.getPayAmt());
        }
        if (totalPOSPayments.compareTo(grandTotal) != 0)
            return "@POSPaymentDiffers@ - @C_POSPayment_ID@="
                    + totalPOSPayments
                    + ", @GrandTotal@="
                    + grandTotal;

        String whereClause = "AD_Org_ID=? AND C_Currency_ID=?";
        I_C_BankAccount ba =
                new Query<I_C_BankAccount>(MBankAccount.Table_Name, whereClause)
                        .setParameters(this.getOrgId(), this.getCurrencyId())
                        .setOrderBy("IsDefault DESC")
                        .first();
        if (ba == null) return "@NoAccountOrgCurrency@";

        DocumentType[] doctypes = MDocTypeKt.getDocumentTypeOfDocBaseType(MDocType.DOCBASETYPE_ARReceipt);
        if (doctypes.length == 0) return "No document type for AR Receipt";
        DocumentType doctype = null;
        for (DocumentType doc : doctypes) {
            if (doc.getOrgId() == this.getOrgId()) {
                doctype = doc;
                break;
            }
        }
        if (doctype == null) doctype = doctypes[0];

        // Create a payment for each non-guarantee record
        // associate the payment id and mark the record as processed
        for (X_C_POSPayment pp : pps) {
            X_C_POSTenderType tt =
                    new X_C_POSTenderType(pp.getPOSTenderTypeId());
            if (tt.isGuarantee()) continue;
            if (pp.isPostDated()) continue;

            MPayment payment = new MPayment(0);
            payment.setOrgId(this.getOrgId());

            payment.setTenderType(pp.getTenderType());
            if (MPayment.TENDERTYPE_CreditCard.equals(pp.getTenderType())) {
                payment.setTrxType(MPayment.TRXTYPE_Sales);
                payment.setCreditCardType(pp.getCreditCardType());
                payment.setCreditCardNumber(pp.getCreditCardNumber());
                payment.setVoiceAuthCode(pp.getVoiceAuthCode());
            }

            payment.setBankAccountId(ba.getBankAccountId());
            payment.setRoutingNo(pp.getRoutingNo());
            payment.setAccountNo(pp.getAccountNo());
            payment.setSwiftCode(pp.getSwiftCode());
            payment.setIBAN(pp.getIBAN());
            payment.setCheckNo(pp.getCheckNo());
            payment.setMicr(pp.getMicr());
            payment.setIsPrepayment(false);

            payment.setDateAcct(this.getDateAcct());
            payment.setDateTrx(this.getDateOrdered());
            //
            payment.setBusinessPartnerId(this.getBusinessPartnerId());
            payment.setInvoiceId(lastInvoice.getInvoiceId());
            // payment.setOrderId(this.getOrderId()); / do not set order to avoid the prepayment
            // flag
            payment.setDocumentTypeId(doctype.getDocTypeId());
            payment.setCurrencyId(this.getCurrencyId());

            payment.setPayAmt(pp.getPayAmt());

            //	Copy statement line reference data
            payment.setAccountName(pp.getAccountName());

            payment.setPOSTenderTypeId(pp.getPOSTenderTypeId());

            //	Save payment
            payment.saveEx();

            pp.setPaymentId(payment.getPaymentId());
            pp.setProcessed(true);
            pp.saveEx();

            payment.setDocAction(MPayment.DOCACTION_Complete);
            if (!payment.processIt(MPayment.DOCACTION_Complete))
                return "Cannot Complete the Payment :" + payment;

            payment.saveEx();
        }

        return null;
    }

    /**
     * Set the definite document number after completed
     */
    protected void setDefiniteDocumentNo() {
        MDocType dt = MDocTypeKt.getDocumentType(getDocumentTypeId());
        if (dt.isOverwriteDateOnComplete()) {
            /* a42niem - BF IDEMPIERE-63 - check if document has been completed before */
            if (this.getProcessedOn().signum() == 0) {
                setDateOrdered(new Timestamp(System.currentTimeMillis()));
                if (getDateAcct().before(getDateOrdered())) {
                    setDateAcct(getDateOrdered());
                    MPeriod.testPeriodOpen(getDateAcct(), getDocumentTypeId(), getOrgId());
                }
            }
        }
        if (dt.isOverwriteSeqOnComplete()) {
            /* a42niem - BF IDEMPIERE-63 - check if document has been completed before */
            if (this.getProcessedOn().signum() == 0) {
                String value = MSequence.getDocumentNo(getDocumentTypeId(), true, this);
                if (value != null) setDocumentNo(value);
            }
        }
    }

    /**
     * Create Shipment
     *
     * @param dt           order document type
     * @param movementDate optional movement date (default today)
     * @return shipment or null
     */
    protected MInOut createShipment(MDocType dt, Timestamp movementDate) {
        if (log.isLoggable(Level.INFO)) log.info("For " + dt);
        MInOut shipment = new MInOut(this, dt.getDocTypeShipmentId(), movementDate);
        //	shipment.setDateAcct(getDateAcct());
        if (!shipment.save()) {
            m_processMsg = "Could not create Shipment";
            return null;
        }
        //
        I_C_OrderLine[] oLines = getLines(true, null).toArray(new I_C_OrderLine[0]);
        for (I_C_OrderLine oLine : oLines) {
            //
            MInOutLine ioLine = new MInOutLine(shipment);
            //	Qty = Ordered - Delivered
            BigDecimal MovementQty = oLine.getQtyOrdered().subtract(oLine.getQtyDelivered());
            //	Location
            int M_Locator_ID =
                    MStorageOnHand.getLocatorId(
                            oLine.getWarehouseId(),
                            oLine.getProductId(),
                            oLine.getAttributeSetInstanceId(),
                            MovementQty,
                            null);
            if (M_Locator_ID == 0) // 	Get default Location
            {
                MWarehouse wh = MWarehouse.get(oLine.getWarehouseId());
                M_Locator_ID = wh.getDefaultLocator().getLocatorId();
            }
            //
            ioLine.setOrderLine(oLine, M_Locator_ID, MovementQty);
            ioLine.setQty(MovementQty);
            if (oLine.getQtyEntered().compareTo(oLine.getQtyOrdered()) != 0)
                ioLine.setQtyEntered(
                        MovementQty.multiply(oLine.getQtyEntered())
                                .divide(oLine.getQtyOrdered(), 6, BigDecimal.ROUND_HALF_UP));
            if (!ioLine.save()) {
                m_processMsg = "Could not create Shipment Line";
                return null;
            }
        }
        // added AdempiereException by zuhri
        if (!shipment.processIt(DocAction.Companion.getACTION_Complete()))
            throw new AdempiereException("Failed when processing document - " + shipment.getProcessMsg());
        // end added
        shipment.saveEx();
        if (!DOCSTATUS_Completed.equals(shipment.getDocStatus())) {
            m_processMsg = "@M_InOut_ID@: " + shipment.getProcessMsg();
            return null;
        }
        return shipment;
    } //	createShipment

    /**
     * Create Counter Document
     *
     * @return counter order
     */
    protected org.compiere.order.MOrder createCounterDoc() {
        //	Is this itself a counter doc ?
        if (getRef_OrderId() != 0) return null;

        //	Org Must be linked to BPartner
        org.compiere.orm.MOrg org = MOrgKt.getOrg(getOrgId());
        int counterC_BPartner_ID = org.getLinkedBusinessPartnerId();
        if (counterC_BPartner_ID == 0) return null;
        //	Business Partner needs to be linked to Org
        MBPartner bp = new MBPartner(getBusinessPartnerId());
        int counterAD_Org_ID = bp.getLinkedOrganizationId();
        if (counterAD_Org_ID == 0) return null;

        MBPartner counterBP = new MBPartner(counterC_BPartner_ID);
        MOrgInfo counterOrgInfo = MOrgInfoKt.getOrganizationInfo(counterAD_Org_ID);
        if (log.isLoggable(Level.INFO)) log.info("Counter BP=" + counterBP.getName());

        //	Document Type
        int C_DocTypeTarget_ID;
        MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(getDocumentTypeId());
        if (counterDT != null) {
            if (log.isLoggable(Level.FINE)) log.fine(counterDT.toString());
            if (!counterDT.isCreateCounter() || !counterDT.isValid()) return null;
            C_DocTypeTarget_ID = counterDT.getCounterDocTypeId();
        } else //	indirect
        {
            C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocTypeId(getDocumentTypeId());
            if (log.isLoggable(Level.FINE)) log.fine("Indirect C_DocTypeTarget_ID=" + C_DocTypeTarget_ID);
            if (C_DocTypeTarget_ID <= 0) return null;
        }
        //	Deep Copy
        MOrder counter =
                copyFrom(
                        this, getDateOrdered(), C_DocTypeTarget_ID, !isSOTrx(), true, false);
        //
        counter.setOrgId(counterAD_Org_ID);
        counter.setWarehouseId(counterOrgInfo.getWarehouseId());
        //
        //		counter.setBPartner(counterBP); // was set on copyFrom
        counter.setDatePromised(getDatePromised()); // default is date ordered
        //	References (Should not be required)
        counter.setSalesRepresentativeId(getSalesRepresentativeId());
        counter.saveEx();

        //	Update copied lines
        I_C_OrderLine[] counterLines = counter.getLines(true, null).toArray(new I_C_OrderLine[0]);
        for (I_C_OrderLine counterLine : counterLines) {
            counterLine.setOrder(counter); // 	copies header values (BP, etc.)
            counterLine.setPrice();
            counterLine.setTax();
            counterLine.saveEx();
        }
        if (log.isLoggable(Level.FINE)) log.fine(counter.toString());

        //	Document Action
        if (counterDT != null) {
            if (counterDT.getDocAction() != null) {
                counter.setDocAction(counterDT.getDocAction());
                // added AdempiereException by zuhri
                if (!counter.processIt(counterDT.getDocAction()))
                    throw new AdempiereException(
                            "Failed when processing document - " + counter.getProcessMsg());
                // end added
                counter.saveEx();
            }
        }
        return counter;
    } //	createCounterDoc

    /**
     * Void Document. Set Qtys to 0 - Sales: reverse all documents
     *
     * @return true if success
     */
    public boolean voidIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        // Before Void
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
        if (m_processMsg != null) return false;

        if (getLink_OrderId() > 0) {
            org.compiere.order.MOrder so =
                    new org.compiere.order.MOrder(getLink_OrderId());
            so.setLink_OrderId(0);
            so.saveEx();
        }

        if (!createReversals()) return false;

        I_C_OrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID).toArray(new I_C_OrderLine[0]);
        for (I_C_OrderLine line : lines) {
            BigDecimal old = line.getQtyOrdered();
            if (old.signum() != 0) {
                line.addDescription(MsgKt.getMsg("Voided") + " (" + old + ")");
                line.setQty(Env.ZERO);
                line.setLineNetAmt(Env.ZERO);
                line.saveEx();
            }
            // AZ Goodwill
            if (!isSOTrx()) {
                deleteMatchPOCostDetail(line);
            }
            if (line.getLink_OrderLineId() > 0) {
                MOrderLine soline = new MOrderLine(line.getLink_OrderLineId());
                soline.setLink_OrderLineId(0);
                soline.saveEx();
            }
        }

        // update taxes
        I_C_OrderTax[] taxes = getTaxes(true);
        for (I_C_OrderTax tax : taxes) {
            if (!(tax.calculateTaxFromLines() && tax.save())) return false;
        }

        addDescription(MsgKt.getMsg("Voided"));
        //	Clear Reservations
        if (!reserveStock(null, lines)) {
            m_processMsg = "Cannot unreserve Stock (void)";
            return false;
        }

        // UnLink All Requisitions
        MRequisitionLine.unlinkC_OrderId(getId());

        /* globalqss - 2317928 - Reactivating/Voiding order must reset posted */
        MFactAcct.deleteEx(Table_ID, getOrderId());
        setPosted(false);

        // After Void
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
        if (m_processMsg != null) return false;

        setTotalLines(Env.ZERO);
        setGrandTotal(Env.ZERO);
        setProcessed(true);
        setDocAction(DOCACTION_None);
        return true;
    } //	voidIt

    /**
     * Get Shipments of Order
     *
     * @return shipments
     */
    public I_M_InOut[] getShipments() {
        final String whereClause =
                "EXISTS (SELECT 1 FROM M_InOutLine iol, C_OrderLine ol"
                        + " WHERE iol.M_InOut_ID=M_InOut.M_InOut_ID"
                        + " AND iol.C_OrderLine_ID=ol.C_OrderLine_ID"
                        + " AND ol.C_Order_ID=?)";
        List<I_M_InOut> list =
                new Query<I_M_InOut>(I_M_InOut.Table_Name, whereClause)
                        .setParameters(getId())
                        .setOrderBy("M_InOut_ID DESC")
                        .list();
        return list.toArray(new I_M_InOut[0]);
    } //	getShipments

    /**
     * Create Shipment/Invoice Reversals
     *
     * @return true if success
     */
    protected boolean createReversals() {
        //	Cancel only Sales
        if (!isSOTrx()) return true;

        log.info("createReversals");
        StringBuilder info = new StringBuilder();

        //	Reverse All *Shipments*
        info.append("@M_InOut_ID@:");
        I_M_InOut[] shipments = getShipments();
        for (I_M_InOut ship : shipments) {
            //	if closed - ignore
            if (MInOut.DOCSTATUS_Closed.equals(ship.getDocStatus())
                    || MInOut.DOCSTATUS_Reversed.equals(ship.getDocStatus())
                    || MInOut.DOCSTATUS_Voided.equals(ship.getDocStatus())) continue;

            //	If not completed - void - otherwise reverse it
            if (!MInOut.DOCSTATUS_Completed.equals(ship.getDocStatus())) {
                if (ship.voidIt()) ship.setDocStatus(MInOut.DOCSTATUS_Voided);
            } else if (ship.reverseCorrectIt()) // 	completed shipment
            {
                ship.setDocStatus(MInOut.DOCSTATUS_Reversed);
                info.append(" ").append(ship.getDocumentNo());
            } else {
                m_processMsg = "Could not reverse Shipment " + ship;
                return false;
            }
            ship.setDocAction(MInOut.DOCACTION_None);
            ship.saveEx();
        } //	for all shipments

        //	Reverse All *Invoices*
        info.append(" - @C_Invoice_ID@:");
        I_C_Invoice[] invoices = getInvoices();
        for (I_C_Invoice invoice : invoices) {
            //	if closed - ignore
            if (I_C_Invoice.DOCSTATUS_Closed.equals(invoice.getDocStatus())
                    || I_C_Invoice.DOCSTATUS_Reversed.equals(invoice.getDocStatus())
                    || I_C_Invoice.DOCSTATUS_Voided.equals(invoice.getDocStatus())) continue;

            //	If not completed - void - otherwise reverse it
            if (!I_C_Invoice.DOCSTATUS_Completed.equals(invoice.getDocStatus())) {
                if (invoice.voidIt()) invoice.setDocStatus(I_C_Invoice.DOCSTATUS_Voided);
            } else if (invoice.reverseCorrectIt()) // 	completed invoice
            {
                invoice.setDocStatus(I_C_Invoice.DOCSTATUS_Reversed);
                info.append(" ").append(invoice.getDocumentNo());
            } else {
                m_processMsg = "Could not reverse Invoice " + invoice;
                return false;
            }
            invoice.setDocAction(MInvoice.DOCACTION_None);
            invoice.saveEx();
        } //	for all shipments

        m_processMsg = info.toString();
        return true;
    } //	createReversals

    /**
     * Close Document. Cancel not delivered Quantities
     *
     * @return true if success
     */
    public boolean closeIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        // Before Close
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
        if (m_processMsg != null) return false;

        //	Close Not delivered Qty - SO/PO
        I_C_OrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID).toArray(new I_C_OrderLine[0]);
        for (I_C_OrderLine line : lines) {
            BigDecimal old = line.getQtyOrdered();
            if (old.compareTo(line.getQtyDelivered()) != 0) {
                line.setQtyLostSales(line.getQtyOrdered().subtract(line.getQtyDelivered()));
                line.setQtyOrdered(line.getQtyDelivered());
                //	QtyEntered unchanged
                line.addDescription("Close (" + old + ")");
                line.saveEx();
            }
        }
        //	Clear Reservations
        if (!reserveStock(null, lines)) {
            m_processMsg = "Cannot unreserve Stock (close)";
            return false;
        }

        setProcessed(true);
        setDocAction(DOCACTION_None);
        // After Close
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
        return m_processMsg == null;
    } //	closeIt

    /**
     * @author: phib re-open a closed order (reverse steps of close())
     */
    public String reopenIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        if (!DOCSTATUS_Closed.equals(getDocStatus())) {
            return "Not closed - can't reopen";
        }

        //
        I_C_OrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID).toArray(new I_C_OrderLine[0]);
        for (I_C_OrderLine line : lines) {
            if (Env.ZERO.compareTo(line.getQtyLostSales()) != 0) {
                line.setQtyOrdered(line.getQtyLostSales().add(line.getQtyDelivered()));
                line.setQtyLostSales(Env.ZERO);
                //	QtyEntered unchanged

                // Strip Close() tags from description
                String desc = line.getDescription();
                if (desc == null) desc = "";
                Pattern pattern = Pattern.compile("( \\| )?Close \\(.*\\)");
                String[] parts = pattern.split(desc);
                desc = "";
                for (String s : parts) {
                    desc = desc.concat(s);
                }
                line.setDescription(desc);
                if (!line.save()) return "Couldn't save orderline";
            }
        }
        //	Clear Reservations
        if (!reserveStock(null, lines)) {
            m_processMsg = "Cannot unreserve Stock (close)";
            return "Failed to update reservations";
        }

        setDocStatus(DOCSTATUS_Completed);
        setDocAction(DOCACTION_Close);
        if (!this.save()) return "Couldn't save reopened order";
        else return "";
    } //	reopenIt

    /**
     * Reverse Correction - same void
     *
     * @return true if success
     */
    public boolean reverseCorrectIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        // Before reverseCorrect
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
        if (m_processMsg != null) return false;

        // After reverseCorrect
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
        if (m_processMsg != null) return false;

        return voidIt();
    } //	reverseCorrectionIt

    /**
     * Reverse Accrual - none
     *
     * @return false
     */
    public boolean reverseAccrualIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        // Before reverseAccrual
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
        if (m_processMsg != null) return false;

        // After reverseAccrual
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
        if (m_processMsg != null) return false;

        return false;
    } //	reverseAccrualIt

    /**
     * Re-activate.
     *
     * @return true if success
     */
    public boolean reActivateIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        // Before reActivate
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
        if (m_processMsg != null) return false;

        MDocType dt = MDocTypeKt.getDocumentType(getDocumentTypeId());
        String DocSubTypeSO = dt.getDocSubTypeSO();

        //	Replace Prepay with POS to revert all doc
        if (MDocType.DOCSUBTYPESO_PrepayOrder.equals(DocSubTypeSO)) {
            DocumentType newDT = null;
            DocumentType[] dts = MDocTypeKt.getGetClientDocumentTypes();
            for (DocumentType type : dts) {
                if (MDocType.DOCSUBTYPESO_PrepayOrder.equals(type.getDocSubTypeSO())) {
                    if (type.isDefault() || newDT == null) newDT = type;
                }
            }
            if (newDT == null) return false;
            else setDocumentTypeId(newDT.getDocTypeId());
        }

        //	PO - just re-open
        if (!isSOTrx()) {
            if (log.isLoggable(Level.INFO)) log.info("Existing documents not modified - " + dt);
            //	Reverse Direct Documents
        } else if (MDocType.DOCSUBTYPESO_OnCreditOrder.equals(DocSubTypeSO) // 	(W)illCall(I)nvoice
                || MDocType.DOCSUBTYPESO_WarehouseOrder.equals(DocSubTypeSO) // 	(W)illCall(P)ickup
                || MDocType.DOCSUBTYPESO_POSOrder.equals(DocSubTypeSO)) // 	(W)alkIn(R)eceipt
        {
            if (!createReversals()) return false;
        } else {
            if (log.isLoggable(Level.INFO))
                log.info("Existing documents not modified - SubType=" + DocSubTypeSO);
        }

        /* globalqss - 2317928 - Reactivating/Voiding order must reset posted */
        MFactAcct.deleteEx(Table_ID, getOrderId());
        setPosted(false);

        // After reActivate
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
        if (m_processMsg != null) return false;

        setDocAction(DOCACTION_Complete);
        setProcessed(false);
        return true;
    } //	reActivateIt

    // AZ Goodwill
    protected String deleteMatchPOCostDetail(I_C_OrderLine line) {
        // Get Account Schemas to delete MCostDetail
        MAcctSchema[] acctschemas = MAcctSchema.getClientAcctSchema(getClientId());
        for (MAcctSchema as : acctschemas) {
            if (as.isSkipOrg(getOrgId())) {
                continue;
            }

            // update/delete Cost Detail and recalculate Current Cost
            MMatchPO[] mPO = MMatchPO.getOrderLine(line.getOrderLineId());
            // delete Cost Detail if the Matched PO has been deleted
            if (mPO.length == 0) {
                I_M_CostDetail cd =
                        MCostDetail.get(

                                "C_OrderLine_ID=?",
                                line.getOrderLineId(),
                                line.getAttributeSetInstanceId(),
                                as.getAccountingSchemaId());
                if (cd != null) {
                    cd.setProcessed(false);
                    cd.delete(true);
                }
            }
        }

        return "";
    }

    @Override
    public void setDoc(IDoc doc) {
    }

    @Override
    public void setProcessedOn(String processed, boolean b, boolean b1) {
    }

    /**
     * *********************************************************************** Get Summary
     *
     * @return Summary of Document
     */
    @NotNull
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDocumentNo());
        //	: Grand Total = 123.00 (#1)
        sb.append(": ")
                .append(MsgKt.translate("GrandTotal"))
                .append("=")
                .append(getGrandTotal());
        if (m_lines != null) sb.append(" (#").append(m_lines.length).append(")");
        //	 - Description
        if (getDescription() != null && getDescription().length() > 0)
            sb.append(" - ").append(getDescription());
        return sb.toString();
    } //	getSummary

    /**
     * Get Document Owner (Responsible)
     *
     * @return AD_User_ID
     */
    public int getDocumentUserId() {
        return getSalesRepresentativeId();
    } //	getDoc_User_ID

    /**
     * Get Document Approval Amount
     *
     * @return amount
     */
    @NotNull
    public BigDecimal getApprovalAmt() {
        return getGrandTotal();
    } //	getApprovalAmt

    /**
     * Unlock Document.
     *
     * @return true if success
     */
    public boolean unlockIt() {
        if (log.isLoggable(Level.INFO)) log.info("unlockIt - " + toString());
        setProcessing(false);
        return true;
    } //	unlockIt

    /**
     * Invalidate Document
     *
     * @return true if success
     */
    public boolean invalidateIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        setDocAction(DOCACTION_Prepare);
        return true;
    } //	invalidateIt

    /**
     * Reject Approval
     *
     * @return true if success
     */
    public boolean rejectIt() {
        if (log.isLoggable(Level.INFO)) log.info("rejectIt - " + toString());
        setIsApproved(false);
        return true;
    } //	rejectIt

}
