package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.conversionrate.MConversionRate;
import org.compiere.crm.MBPGroup;
import org.compiere.invoicing.MInvoiceLine;
import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchPO;
import org.compiere.order.MInOutLine;
import org.compiere.order.MOrderLine;
import org.compiere.orm.MDocType;
import org.compiere.orm.MSysConfig;
import org.compiere.orm.PO;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.ValueNamePair;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import static org.compiere.crm.MBaseBPGroupKt.getOfBPartner;
import static software.hsharp.core.util.DBKt.getSQLValue;
import static software.hsharp.core.util.DBKt.getSQLValueBD;
import static software.hsharp.core.util.DBKt.getSQLValueTS;

/**
 * Match PO Model. = Created when processing Shipment or Order - Updates Order (delivered, invoiced)
 * - Creates PPV acct
 *
 * @author Jorg Janke
 * @author Bayu Cahya, Sistematika
 * <li>BF [ 2240484 ] Re MatchingPO, MMatchPO doesn't contains Invoice info
 * @author Teo Sarca, www.arhipac.ro
 * <li>BF [ 2314749 ] MatchPO not considering currency PriceMatchDifference
 * @author Armen Rizal, Goodwill Consulting
 * <li>BF [ 2215840 ] MatchPO Bug Collection
 * <li>BF [ 2858043 ] Correct Included Tax in Average Costing
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * <li>FR [ 2520591 ] Support multiples calendar for Org
 * @version $Id: MMatchPO.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 */
public class MMatchPO extends X_M_MatchPO implements IPODoc {
    /**
     *
     */
    private static final long serialVersionUID = -3669451656879485463L;
    private MMatchInv m_matchInv;
    /**
     * Invoice Changed
     */
    private boolean m_isInvoiceLineChange = false;

    // MZ Goodwill
    /**
     * InOut Changed
     */
    private boolean m_isInOutLineChange = false;
    // end MZ
    /**
     * Order Line
     */
    private MOrderLine m_oLine = null;
    /**
     * Invoice Line
     */
    private I_C_InvoiceLine m_iLine = null;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param M_MatchPO_ID id
     */
    public MMatchPO(int M_MatchPO_ID) {
        super(M_MatchPO_ID);
        if (M_MatchPO_ID == 0) {
            setAttributeSetInstanceId(0);
            setPosted(false);
            setProcessed(false);
            setProcessing(false);
        }
    } //	MMatchPO

    /**
     * Load Construor
     *
     */
    public MMatchPO(Row row) {
        super(row);
    } //	MMatchPO

    /**
     * Shipment Line Constructor
     *
     * @param sLine   shipment line
     * @param dateTrx optional date
     * @param qty     matched quantity
     */
    public MMatchPO(I_M_InOutLine sLine, Timestamp dateTrx, BigDecimal qty) {
        this(0);
        setClientOrg(sLine);
        setInOutLineId(sLine.getInOutLineId());
        setOrderLineId(sLine.getOrderLineId());
        if (dateTrx != null) setDateTrx(dateTrx);
        setProductId(sLine.getProductId());
        setAttributeSetInstanceId(sLine.getAttributeSetInstanceId());
        setQty(qty);
        setProcessed(true); // 	auto
    } //	MMatchPO

    /**
     * Invoice Line Constructor
     *
     * @param iLine   invoice line
     * @param dateTrx optional date
     * @param qty     matched quantity
     */
    public MMatchPO(I_C_InvoiceLine iLine, Timestamp dateTrx, BigDecimal qty) {
        this(0);
        setClientOrg(iLine);
        setInvoiceLineId(iLine);
        if (iLine.getOrderLineId() != 0) setOrderLineId(iLine.getOrderLineId());
        if (dateTrx != null) setDateTrx(dateTrx);
        setProductId(iLine.getProductId());
        setAttributeSetInstanceId(iLine.getAttributeSetInstanceId());
        setQty(qty);
        setProcessed(true); // 	auto
    } //	MMatchPO

    /**
     * Get PO Match of Receipt Line
     *
     * @param M_InOutLine_ID receipt
     * @return array of matches
     */
    public static MMatchPO[] get(int M_InOutLine_ID) {
        return MBaseMatchPOKt.getPOMatchOfReceiptLine(M_InOutLine_ID);
    } //	get

    /**
     * Get PO Matches of receipt
     *
     * @param M_InOut_ID receipt
     * @return array of matches
     */
    public static MMatchPO[] getInOut(int M_InOut_ID) {
        return MBaseMatchPOKt.getPOMatchesOfReceipt(M_InOut_ID);
    } //	getInOut

    /**
     * Get PO Matches of Invoice
     *
     * @param C_Invoice_ID invoice
     * @return array of matches
     */
    public static MMatchPO[] getInvoice(int C_Invoice_ID) {
        return MBaseMatchPOKt.getPOMatchesOfInvoice(C_Invoice_ID);
    } //	getInvoice

    /**
     * Get PO Matches for OrderLine
     *
     * @param C_OrderLine_ID order
     * @return array of matches
     */
    public static MMatchPO[] getOrderLine(int C_OrderLine_ID) {
        return MBaseMatchPOKt.getPOMatchesForOrderLine(C_OrderLine_ID);
    } //	getOrderLine

    /**
     * Find/Create PO(Inv) Match
     *
     * @param iLine   invoice line
     * @param sLine   receipt line
     * @param dateTrx date
     * @param qty     qty
     * @return Match Record
     */
    public static MMatchPO create(
            I_C_InvoiceLine iLine, I_M_InOutLine sLine, Timestamp dateTrx, BigDecimal qty) {

        int C_OrderLine_ID = 0;
        if (iLine != null) {
            C_OrderLine_ID = iLine.getOrderLineId();
        }
        if (sLine != null) {
            C_OrderLine_ID = sLine.getOrderLineId();
        }

        if (C_OrderLine_ID > 0) {
            return create(iLine, sLine, C_OrderLine_ID, dateTrx, qty);
        } else {
            if (sLine != null && iLine != null) {
                MMatchPO[] matchpos = MMatchPO.get(sLine.getInOutLineId());
                for (MMatchPO matchpo : matchpos) {
                    C_OrderLine_ID = matchpo.getOrderLineId();
                    MOrderLine orderLine = new MOrderLine(C_OrderLine_ID);
                    BigDecimal toInvoice = orderLine.getQtyOrdered().subtract(orderLine.getQtyInvoiced());
                    if (toInvoice.signum() <= 0) continue;
                    BigDecimal matchQty = qty;
                    if (matchQty.compareTo(toInvoice) > 0) matchQty = toInvoice;

                    if (matchQty.signum() <= 0) continue;

                    MMatchPO newMatchPO =
                            create(iLine, sLine, C_OrderLine_ID, dateTrx, matchQty);
                    if (!newMatchPO.save()) {
                        String msg = "Failed to update match po.";
                        ValueNamePair error = CLogger.retrieveError();
                        if (error != null) {
                            msg = msg + " " + error.getName();
                        }
                        throw new RuntimeException(msg);
                    }
                    qty = qty.subtract(matchQty);
                    if (qty.signum() <= 0) return newMatchPO;
                }
            }
            return null;
        }
    }

    private static MMatchPO create(

            I_C_InvoiceLine iLine,
            I_M_InOutLine sLine,
            int C_OrderLine_ID,
            Timestamp dateTrx,
            BigDecimal qty) {
        return MBaseMatchPOKt.create(iLine, sLine, C_OrderLine_ID, dateTrx, qty);
    } //	create

    /**
     * Get the match inv created for immediate accounting purposes Is cleared after read, so if you
     * read twice second time it returns null
     */
    public MMatchInv getMatchInvCreated() {
        MMatchInv tmp = m_matchInv;
        m_matchInv = null;
        return tmp;
    }

    /**
     * Register the match inv created for immediate accounting purposes
     *
     * @param matchInv
     */
    void setMatchInvCreated(MMatchInv matchInv) {
        m_matchInv = matchInv;
    }

    /**
     * Set C_InvoiceLine_ID
     *
     * @param line line
     */
    public void setInvoiceLineId(I_C_InvoiceLine line) {
        m_iLine = line;
        if (line == null) setInvoiceLineId(0);
        else setInvoiceLineId(line.getInvoiceLineId());
    } //	setInvoiceLineId

    /**
     * Set C_InvoiceLine_ID
     *
     * @param C_InvoiceLine_ID id
     */
    public void setInvoiceLineId(int C_InvoiceLine_ID) {
        int old = getInvoiceLineId();
        if (old != C_InvoiceLine_ID) {
            super.setInvoiceLineId(C_InvoiceLine_ID);
            m_isInvoiceLineChange = true;
        }
    } //	setInvoiceLineId

    /**
     * Get Invoice Line
     *
     * @return invoice line or null
     */
    public I_C_InvoiceLine getInvoiceLine() {
        if (m_iLine == null && getInvoiceLineId() != 0)
            m_iLine = new MInvoiceLine(getInvoiceLineId());
        return m_iLine;
    } //	getInvoiceLine

    /**
     * Set M_InOutLine_ID
     *
     * @param M_InOutLine_ID id
     */
    public void setInOutLineId(int M_InOutLine_ID) {
        int old = getInOutLineId();
        if (old != M_InOutLine_ID) {
            super.setInOutLineId(M_InOutLine_ID);
            m_isInOutLineChange = true;
        }
    } //	setInOutLineId

    /**
     * Get Order Line
     *
     * @return order line or null
     */
    public MOrderLine getOrderLine() {
        if ((m_oLine == null && getOrderLineId() != 0)
                || getOrderLineId() != m_oLine.getOrderLineId())
            m_oLine = new MOrderLine(getOrderLineId());
        return m_oLine;
    } //	getOrderLine

    /**
     * Get PriceActual from Invoice and convert it to Order Currency
     *
     * @return Price Actual in Order Currency
     */
    public BigDecimal getInvoicePriceActual() {
        I_C_InvoiceLine iLine = getInvoiceLine();
        I_C_Invoice invoice = iLine.getParent();
        I_C_Order order = getOrderLine().getParent();

        BigDecimal priceActual = iLine.getPriceActual();
        int invoiceCurrency_ID = invoice.getCurrencyId();
        int orderCurrency_ID = order.getCurrencyId();
        if (invoiceCurrency_ID != orderCurrency_ID) {
            priceActual =
                    MConversionRate.convert(
                            priceActual,
                            invoiceCurrency_ID,
                            orderCurrency_ID,
                            invoice.getDateInvoiced(),
                            invoice.getConversionTypeId(),
                            getClientId(),
                            getOrgId());
        }
        return priceActual;
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {
        //	Set Trx Date
        if (getDateTrx() == null) setDateTrx(new Timestamp(System.currentTimeMillis()));
        //	Set Acct Date
        if (getDateAcct() == null) {
            Timestamp ts = getNewerDateAcct();
            if (ts == null) ts = getDateTrx();
            setDateAcct(ts);
        }
        //	Set ASI from Receipt
        if (getAttributeSetInstanceId() == 0 && getInOutLineId() != 0) {
            MInOutLine iol = new MInOutLine(getInOutLineId());
            setAttributeSetInstanceId(iol.getAttributeSetInstanceId());
        }

        // Bayu, Sistematika
        // BF [ 2240484 ] Re MatchingPO, MMatchPO doesn't contains Invoice info
        // If newRecord, set c_invoiceline_id while null
        if (newRecord && getInvoiceLineId() == 0) {
            MMatchInv[] mpi = MMatchInv.getInOutLine(getInOutLineId());
            for (int i = 0; i < mpi.length; i++) {
                if (mpi[i].getInvoiceLineId() != 0
                        && mpi[i].getAttributeSetInstanceId() == getAttributeSetInstanceId()) {
                    if (mpi[i].getQty().compareTo(getQty()) == 0) // same quantity
                    {
                        setInvoiceLineId(mpi[i].getInvoiceLineId());
                        break;
                    } else // create MatchPO record for PO-Invoice if different quantity
                    {
                        MInvoiceLine il =
                                new MInvoiceLine(mpi[i].getInvoiceLineId());
                        MMatchPO match = new MMatchPO(il, getDateTrx(), mpi[i].getQty());
                        match.setOrderLineId(getOrderLineId());
                        if (!match.save()) {
                            String msg = "Failed to create match po";
                            ValueNamePair error = CLogger.retrieveError();
                            if (error != null) msg = msg + " " + error.getName();
                            throw new RuntimeException(msg);
                        }
                    }
                }
            }
        }
        // end Bayu

        //	Find OrderLine
        if (getOrderLineId() == 0) {
            I_C_InvoiceLine il = null;
            if (getInvoiceLineId() != 0) {
                il = getInvoiceLine();
                if (il.getOrderLineId() != 0) setOrderLineId(il.getOrderLineId());
            } //	get from invoice
            if (getOrderLineId() == 0 && getInOutLineId() != 0) {
                MInOutLine iol = new MInOutLine(getInOutLineId());
                if (iol.getOrderLineId() != 0) {
                    setOrderLineId(iol.getOrderLineId());
                    if (il != null) {
                        il.setOrderLineId(iol.getOrderLineId());
                        il.saveEx();
                    }
                }
            } //	get from shipment
        } //	find order line

        //	Price Match Approval
        if (getOrderLineId() != 0
                && getInvoiceLineId() != 0
                && (newRecord
                || isValueChanged("C_OrderLine_ID")
                || isValueChanged("C_InvoiceLine_ID"))) {
            BigDecimal poPrice = getOrderLine().getPriceActual();
            BigDecimal invPrice = getInvoicePriceActual();
            BigDecimal difference = poPrice.subtract(invPrice);
            if (difference.signum() != 0) {
                difference = difference.multiply(getQty());
                setPriceMatchDifference(difference);
                //	Approval
                MBPGroup group = getOfBPartner(getOrderLine().getBusinessPartnerId());
                BigDecimal mt = group.getPriceMatchTolerance();
                if (mt != null && mt.signum() != 0) {
                    BigDecimal poAmt = poPrice.multiply(getQty());
                    BigDecimal maxTolerance = poAmt.multiply(mt);
                    maxTolerance = maxTolerance.abs().divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP);
                    difference = difference.abs();
                    boolean ok = difference.compareTo(maxTolerance) <= 0;
                    if (log.isLoggable(Level.CONFIG))
                        log.config(
                                "Difference=" + getPriceMatchDifference() + ", Max=" + maxTolerance + " => " + ok);
                    setIsApproved(ok);
                }
            } else {
                setPriceMatchDifference(difference);
                setIsApproved(true);
            }

            // validate against M_MatchInv
            if (getInOutLineId() > 0 && getInvoiceLineId() > 0) {
                int cnt =
                        getSQLValue(
                                "SELECT Count(*) FROM M_MatchInv WHERE M_InOutLine_ID="
                                        + getInOutLineId()
                                        + " AND C_InvoiceLine_ID="
                                        + getInvoiceLineId());
                if (cnt <= 0) {
                    MInvoiceLine invoiceLine =
                            new MInvoiceLine(getInvoiceLineId());
                    MInOutLine inoutLine = new MInOutLine(getInOutLineId());
                    throw new IllegalStateException(
                            "[MatchPO] Missing corresponding invoice matching record for invoice line "
                                    + invoiceLine
                                    + " and receipt line "
                                    + inoutLine);
                }
            }
        }

        return true;
    } //	beforeSave

    /**
     * After Save. Set Order Qty Delivered/Invoiced
     *
     * @param newRecord new
     * @param success   success
     * @return success
     */
    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
        // perform matched qty validation
        if (success) {
            if (getInOutLineId() > 0) {
                MInOutLine line = new MInOutLine(getInOutLineId());
                BigDecimal matchedQty =
                        getSQLValueBD(
                                "SELECT Coalesce(SUM(Qty),0) FROM M_MatchPO WHERE M_InOutLine_ID=?",
                                getInOutLineId());
                if (matchedQty != null && matchedQty.compareTo(line.getMovementQty()) > 0) {
                    throw new IllegalStateException(
                            "Total matched qty > movement qty. MatchedQty="
                                    + matchedQty
                                    + ", MovementQty="
                                    + line.getMovementQty()
                                    + ", Line="
                                    + line);
                }
            }

            if (getInvoiceLineId() > 0) {
                MInvoiceLine line = new MInvoiceLine(getInvoiceLineId());
                BigDecimal matchedQty =
                        getSQLValueBD(
                                "SELECT Coalesce(SUM(Qty),0) FROM M_MatchPO WHERE C_InvoiceLine_ID=?",
                                getInvoiceLineId());
                if (matchedQty != null && matchedQty.compareTo(line.getQtyInvoiced()) > 0) {
                    throw new IllegalStateException(
                            "Total matched qty > invoiced qty. MatchedQty="
                                    + matchedQty
                                    + ", InvoicedQty="
                                    + line.getQtyInvoiced()
                                    + ", Line="
                                    + line);
                }
            }

            if (getOrderLineId() > 0) {
                boolean validateOrderedQty =
                        MSysConfig.getBooleanValue(
                                MSysConfig.VALIDATE_MATCHING_TO_ORDERED_QTY, true, Env.getClientId());
                if (validateOrderedQty) {
                    MOrderLine line = new MOrderLine(getOrderLineId());
                    BigDecimal invoicedQty =
                            getSQLValueBD(
                                    "SELECT Coalesce(SUM(Qty),0) FROM M_MatchPO WHERE C_InvoiceLine_ID > 0 and C_OrderLine_ID=? AND Reversal_ID IS NULL",
                                    getOrderLineId());
                    if (invoicedQty != null && invoicedQty.compareTo(line.getQtyOrdered()) > 0) {
                        throw new IllegalStateException(
                                "Total matched invoiced qty > ordered qty. MatchedInvoicedQty="
                                        + invoicedQty
                                        + ", OrderedQty="
                                        + line.getQtyOrdered()
                                        + ", Line="
                                        + line);
                    }

                    BigDecimal deliveredQty =
                            getSQLValueBD(
                                    "SELECT Coalesce(SUM(Qty),0) FROM M_MatchPO WHERE M_InOutLine_ID > 0 and C_OrderLine_ID=? AND Reversal_ID IS NULL",
                                    getOrderLineId());
                    if (deliveredQty != null && deliveredQty.compareTo(line.getQtyOrdered()) > 0) {
                        throw new IllegalStateException(
                                "Total matched delivered qty > ordered qty. MatchedDeliveredQty="
                                        + deliveredQty
                                        + ", OrderedQty="
                                        + line.getQtyOrdered()
                                        + ", Line="
                                        + line);
                    }
                }
            }
        }

        //	Purchase Order Delivered/Invoiced
        //	(Reserved in VMatch and MInOut.completeIt)
        if (success && getOrderLineId() != 0) {
            MOrderLine orderLine = getOrderLine();
            //
            if (m_isInOutLineChange
                    && (newRecord || getInOutLineId() != getValueOldAsInt("M_InOutLine_ID"))) {
                if (getInOutLineId() != 0) // 	new delivery
                    orderLine.setQtyDelivered(orderLine.getQtyDelivered().add(getQty()));
                else //	if (getInOutLineId() == 0)					//	reset to 0
                    orderLine.setQtyDelivered(orderLine.getQtyDelivered().subtract(getQty()));
                orderLine.setDateDelivered(getDateTrx()); // 	overwrite=last
            }
            if (m_isInvoiceLineChange
                    && (newRecord || getInvoiceLineId() != getValueOldAsInt("C_InvoiceLine_ID"))) {
                if (getInvoiceLineId() != 0) // 	first time
                    orderLine.setQtyInvoiced(orderLine.getQtyInvoiced().add(getQty()));
                else //	if (getInvoiceLineId() == 0)				//	set to 0
                    orderLine.setQtyInvoiced(orderLine.getQtyInvoiced().subtract(getQty()));
                orderLine.setDateInvoiced(getDateTrx()); // 	overwrite=last
            }

            //	Update Order ASI if full match
            if (orderLine.getAttributeSetInstanceId() == 0 && getInOutLineId() != 0) {
                MInOutLine iol = new MInOutLine(getInOutLineId());
                if (iol.getMovementQty().compareTo(orderLine.getQtyOrdered()) == 0)
                    orderLine.setAttributeSetInstanceId(iol.getAttributeSetInstanceId());
            }
            return orderLine.save();
        }
        //
        return success;
    } //	afterSave

    /**
     * Get the later Date Acct from invoice or shipment
     *
     * @return date or null
     */
    public Timestamp getNewerDateAcct() {
        Timestamp invoiceDate = null;
        Timestamp shipDate = null;

        if (getInvoiceLineId() != 0) {
            String sql =
                    "SELECT i.DateAcct "
                            + "FROM C_InvoiceLine il"
                            + " INNER JOIN C_Invoice i ON (i.C_Invoice_ID=il.C_Invoice_ID) "
                            + "WHERE C_InvoiceLine_ID=?";
            invoiceDate = getSQLValueTS(sql, getInvoiceLineId());
        }
        //
        if (getInOutLineId() != 0) {
            String sql =
                    "SELECT io.DateAcct "
                            + "FROM M_InOutLine iol"
                            + " INNER JOIN M_InOut io ON (io.M_InOut_ID=iol.M_InOut_ID) "
                            + "WHERE iol.M_InOutLine_ID=?";
            shipDate = getSQLValueTS(sql, getInOutLineId());
        }
        //
        //	Assuming that order date is always earlier
        if (invoiceDate == null) return shipDate;
        if (shipDate == null) return invoiceDate;
        if (invoiceDate.after(shipDate)) return invoiceDate;
        return shipDate;
    } //	getNewerDateAcct

    /**
     * Before Delete
     *
     * @return true if acct was deleted
     */
    @Override
    protected boolean beforeDelete() {
        if (isPosted()) {
            MPeriod.testPeriodOpen(getDateTrx(), MDocType.DOCBASETYPE_MatchPO, getOrgId());
            setPosted(false);
            MFactAcct.deleteEx(I_M_MatchPO.Table_ID, getId());
        }
        return true;
    } //	beforeDelete

    /**
     * After Delete. Set Order Qty Delivered/Invoiced
     *
     * @param success success
     * @return success
     */
    @Override
    protected boolean afterDelete(boolean success) {
        //	Order Delivered/Invoiced
        //	(Reserved in VMatch and MInOut.completeIt)
        if (success && getOrderLineId() != 0) {
            MOrderLine orderLine = new MOrderLine(getOrderLineId());
            if (getInOutLineId() != 0)
                orderLine.setQtyDelivered(orderLine.getQtyDelivered().subtract(getQty()));
            if (getInvoiceLineId() != 0)
                orderLine.setQtyInvoiced(orderLine.getQtyInvoiced().subtract(getQty()));
            return orderLine.save();
        }
        return success;
    } //	afterDelete

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MMatchPO[");
        sb.append(getId())
                .append(",Qty=")
                .append(getQty())
                .append(",C_OrderLine_ID=")
                .append(getOrderLineId())
                .append(",M_InOutLine_ID=")
                .append(getInOutLineId())
                .append(",C_InvoiceLine_ID=")
                .append(getInvoiceLineId())
                .append("]");
        return sb.toString();
    } //	toString

    /**
     * Reverse MatchPO.
     *
     * @param reversalDate
     * @return boolean
     * @throws Exception
     */
    public boolean reverse(Timestamp reversalDate) {
        if (this.isProcessed() && this.getReversalId() == 0) {
            MMatchPO reversal = new MMatchPO(0);
            reversal.setInvoiceLineId(getInvoiceLineId());
            reversal.setInOutLineId(getInOutLineId());
            PO.copyValues(this, reversal);
            reversal.setOrgId(this.getOrgId());
            reversal.setDescription("(->" + this.getDocumentNo() + ")");
            reversal.setQty(this.getQty().negate());
            reversal.setDateAcct(reversalDate);
            reversal.setDateTrx(reversalDate);
            reversal.setValueNoCheck("DocumentNo", null);
            reversal.setPosted(false);
            reversal.setReversalId(getMatchPOId());
            reversal.saveEx();
            this.setDescription("(" + reversal.getDocumentNo() + "<-)");
            this.setReversalId(reversal.getMatchPOId());
            this.saveEx();
            return true;
        }
        return false;
    }

    @Override
    public void setDoc(IDoc doc) {
    }

    @Override
    public void setProcessedOn(String processed, boolean b, boolean b1) {
    }
} //	MMatchPO
