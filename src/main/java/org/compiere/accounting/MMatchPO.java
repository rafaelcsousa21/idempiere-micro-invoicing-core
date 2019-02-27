package org.compiere.accounting;

import org.compiere.conversionrate.MConversionRate;
import org.compiere.crm.MBPGroup;
import org.compiere.invoicing.MInvoiceLine;
import org.compiere.model.*;
import org.compiere.order.MInOutLine;
import org.compiere.order.MOrderLine;
import org.compiere.orm.MDocType;
import org.compiere.orm.MSysConfig;
import org.compiere.orm.PO;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.ValueNamePair;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import static org.compiere.crm.MBaseBPGroupKt.getOfBPartner;
import static software.hsharp.core.util.DBKt.*;

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
    /**
     * Static Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MMatchPO.class);
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
     * @param ctx          context
     * @param M_MatchPO_ID id
     * @param trxName      transaction
     */
    public MMatchPO(Properties ctx, int M_MatchPO_ID) {
        super(ctx, M_MatchPO_ID);
        if (M_MatchPO_ID == 0) {
            //	setC_OrderLine_ID (0);
            //	setDateTrx (new Timestamp(System.currentTimeMillis()));
            //	setM_InOutLine_ID (0);
            //	setM_Product_ID (0);
            setM_AttributeSetInstance_ID(0);
            //	setQty (Env.ZERO);
            setPosted(false);
            setProcessed(false);
            setProcessing(false);
        }
    } //	MMatchPO

    /**
     * Load Construor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MMatchPO(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MMatchPO

    /**
     * Shipment Line Constructor
     *
     * @param sLine   shipment line
     * @param dateTrx optional date
     * @param qty     matched quantity
     */
    public MMatchPO(MInOutLine sLine, Timestamp dateTrx, BigDecimal qty) {
        this(sLine.getCtx(), 0);
        setClientOrg(sLine);
        setM_InOutLine_ID(sLine.getM_InOutLine_ID());
        setC_OrderLine_ID(sLine.getC_OrderLine_ID());
        if (dateTrx != null) setDateTrx(dateTrx);
        setM_Product_ID(sLine.getM_Product_ID());
        setM_AttributeSetInstance_ID(sLine.getMAttributeSetInstance_ID());
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
        this(iLine.getCtx(), 0);
        setClientOrg(iLine);
        setC_InvoiceLine_ID(iLine);
        if (iLine.getC_OrderLine_ID() != 0) setC_OrderLine_ID(iLine.getC_OrderLine_ID());
        if (dateTrx != null) setDateTrx(dateTrx);
        setM_Product_ID(iLine.getM_Product_ID());
        setM_AttributeSetInstance_ID(iLine.getMAttributeSetInstance_ID());
        setQty(qty);
        setProcessed(true); // 	auto
    } //	MMatchPO

    /**
     * Get PO Match of Receipt Line
     *
     * @param ctx            context
     * @param M_InOutLine_ID receipt
     * @param trxName        transaction
     * @return array of matches
     */
    public static MMatchPO[] get(Properties ctx, int M_InOutLine_ID) {
        if (M_InOutLine_ID == 0) return new MMatchPO[]{};
        //
        String sql = "SELECT * FROM M_MatchPO WHERE M_InOutLine_ID=?";
        ArrayList<MMatchPO> list = new ArrayList<MMatchPO>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, M_InOutLine_ID);
            rs = pstmt.executeQuery();
            while (rs.next()) list.add(new MMatchPO(ctx, rs));
        } catch (Exception e) {
            s_log.log(Level.SEVERE, sql, e);
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new IllegalStateException(e);
            }
        } finally {

        }

        MMatchPO[] retValue = new MMatchPO[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	get

    /**
     * Get PO Matches of receipt
     *
     * @param ctx        context
     * @param M_InOut_ID receipt
     * @param trxName    transaction
     * @return array of matches
     */
    public static MMatchPO[] getInOut(Properties ctx, int M_InOut_ID) {
        if (M_InOut_ID == 0) return new MMatchPO[]{};
        //
        String sql =
                "SELECT * FROM M_MatchPO m"
                        + " INNER JOIN M_InOutLine l ON (m.M_InOutLine_ID=l.M_InOutLine_ID) "
                        + "WHERE l.M_InOut_ID=?";
        ArrayList<MMatchPO> list = new ArrayList<MMatchPO>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, M_InOut_ID);
            rs = pstmt.executeQuery();
            while (rs.next()) list.add(new MMatchPO(ctx, rs));
        } catch (Exception e) {
            s_log.log(Level.SEVERE, sql, e);
        } finally {

            rs = null;
            pstmt = null;
        }
        MMatchPO[] retValue = new MMatchPO[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getInOut

    /**
     * Get PO Matches of Invoice
     *
     * @param ctx          context
     * @param C_Invoice_ID invoice
     * @param trxName      transaction
     * @return array of matches
     */
    public static MMatchPO[] getInvoice(Properties ctx, int C_Invoice_ID) {
        if (C_Invoice_ID == 0) return new MMatchPO[]{};
        //
        String sql =
                "SELECT * FROM M_MatchPO mi"
                        + " INNER JOIN C_InvoiceLine il ON (mi.C_InvoiceLine_ID=il.C_InvoiceLine_ID) "
                        + "WHERE il.C_Invoice_ID=?";
        ArrayList<MMatchPO> list = new ArrayList<MMatchPO>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, C_Invoice_ID);
            rs = pstmt.executeQuery();
            while (rs.next()) list.add(new MMatchPO(ctx, rs));
        } catch (Exception e) {
            s_log.log(Level.SEVERE, sql, e);
        } finally {

            rs = null;
            pstmt = null;
        }
        MMatchPO[] retValue = new MMatchPO[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getInvoice

    /**
     * Get PO Matches for OrderLine
     *
     * @param ctx            context
     * @param C_OrderLine_ID order
     * @param trxName        transaction
     * @return array of matches
     */
    public static MMatchPO[] getOrderLine(Properties ctx, int C_OrderLine_ID) {
        if (C_OrderLine_ID == 0) return new MMatchPO[]{};
        //
        String sql = "SELECT * FROM M_MatchPO WHERE C_OrderLine_ID=?";
        ArrayList<MMatchPO> list = new ArrayList<MMatchPO>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, C_OrderLine_ID);
            rs = pstmt.executeQuery();
            while (rs.next()) list.add(new MMatchPO(ctx, rs));
        } catch (Exception e) {
            s_log.log(Level.SEVERE, sql, e);
        } finally {

            rs = null;
            pstmt = null;
        }
        MMatchPO[] retValue = new MMatchPO[list.size()];
        list.toArray(retValue);
        return retValue;
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
            I_C_InvoiceLine iLine, MInOutLine sLine, Timestamp dateTrx, BigDecimal qty) {
        String trxName = null;
        Properties ctx = null;
        int C_OrderLine_ID = 0;
        if (iLine != null) {
            trxName = null;
            ctx = iLine.getCtx();
            C_OrderLine_ID = iLine.getC_OrderLine_ID();
        }
        if (sLine != null) {
            trxName = null;
            ctx = sLine.getCtx();
            C_OrderLine_ID = sLine.getC_OrderLine_ID();
        }

        if (C_OrderLine_ID > 0) {
            return create(ctx, iLine, sLine, C_OrderLine_ID, dateTrx, qty);
        } else {
            if (sLine != null && iLine != null) {
                MMatchPO[] matchpos = MMatchPO.get(ctx, sLine.getM_InOutLine_ID());
                for (MMatchPO matchpo : matchpos) {
                    C_OrderLine_ID = matchpo.getC_OrderLine_ID();
                    MOrderLine orderLine = new MOrderLine(ctx, C_OrderLine_ID);
                    BigDecimal toInvoice = orderLine.getQtyOrdered().subtract(orderLine.getQtyInvoiced());
                    if (toInvoice.signum() <= 0) continue;
                    BigDecimal matchQty = qty;
                    if (matchQty.compareTo(toInvoice) > 0) matchQty = toInvoice;

                    if (matchQty.signum() <= 0) continue;

                    MMatchPO newMatchPO =
                            create(ctx, iLine, sLine, C_OrderLine_ID, dateTrx, matchQty);
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
            Properties ctx,
            I_C_InvoiceLine iLine,
            MInOutLine sLine,
            int C_OrderLine_ID,
            Timestamp dateTrx,
            BigDecimal qty) {
        MMatchPO retValue = null;
        String sql =
                "SELECT * FROM M_MatchPO WHERE C_OrderLine_ID=? and Reversal_ID IS NULL ORDER BY M_MatchPO_ID";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, C_OrderLine_ID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MMatchPO mpo = new MMatchPO(ctx, rs);
                if (qty.compareTo(mpo.getQty()) >= 0) {
                    BigDecimal toMatch = qty;
                    BigDecimal matchQty = mpo.getQty();
                    if (toMatch.compareTo(matchQty) > 0) toMatch = matchQty;
                    if (iLine != null) {
                        if ((mpo.getC_InvoiceLine_ID() == 0)
                                || (mpo.getC_InvoiceLine_ID() == iLine.getC_InvoiceLine_ID())) {
                            if (iLine.getMAttributeSetInstance_ID() != 0) {
                                if (mpo.getMAttributeSetInstance_ID() == 0)
                                    mpo.setM_AttributeSetInstance_ID(iLine.getMAttributeSetInstance_ID());
                                else if (mpo.getMAttributeSetInstance_ID() != iLine.getMAttributeSetInstance_ID())
                                    continue;
                            }
                        } else continue;
                    }
                    if (sLine != null) {
                        if ((mpo.getM_InOutLine_ID() == 0)
                                || (mpo.getM_InOutLine_ID() == sLine.getM_InOutLine_ID())) {

                            if (sLine.getMAttributeSetInstance_ID() != 0) {
                                if (mpo.getMAttributeSetInstance_ID() == 0)
                                    mpo.setM_AttributeSetInstance_ID(sLine.getMAttributeSetInstance_ID());
                                else if (mpo.getMAttributeSetInstance_ID() != sLine.getMAttributeSetInstance_ID())
                                    continue;
                            }
                        } else continue;
                    }
                    if ((iLine != null || mpo.getC_InvoiceLine_ID() > 0)
                            && (sLine != null || mpo.getM_InOutLine_ID() > 0)) {
                        int M_InOutLine_ID =
                                sLine != null ? sLine.getM_InOutLine_ID() : mpo.getM_InOutLine_ID();
                        int C_InvoiceLine_ID =
                                iLine != null ? iLine.getC_InvoiceLine_ID() : mpo.getC_InvoiceLine_ID();

                        // verify invoiceline not already linked to another inoutline
                        int tmpInOutLineId =
                                getSQLValue(
                                        "SELECT M_InOutLine_ID FROM C_InvoiceLine WHERE C_InvoiceLine_ID="
                                                + C_InvoiceLine_ID);
                        if (tmpInOutLineId > 0 && tmpInOutLineId != M_InOutLine_ID) {
                            continue;
                        }

                        // verify m_matchinv not created yet
                        int cnt =
                                getSQLValue(
                                        "SELECT Count(*) FROM M_MatchInv WHERE M_InOutLine_ID="
                                                + M_InOutLine_ID
                                                + " AND C_InvoiceLine_ID="
                                                + C_InvoiceLine_ID);
                        if (cnt <= 0) {
                            MMatchInv matchInv = new MMatchInv(mpo.getCtx(), 0);
                            matchInv.setC_InvoiceLine_ID(C_InvoiceLine_ID);
                            matchInv.setM_Product_ID(mpo.getM_Product_ID());
                            matchInv.setM_InOutLine_ID(M_InOutLine_ID);
                            matchInv.setADClientID(mpo.getClientId());
                            matchInv.setOrgId(mpo.getOrgId());
                            matchInv.setM_AttributeSetInstance_ID(mpo.getMAttributeSetInstance_ID());
                            matchInv.setQty(mpo.getQty());
                            matchInv.setDateTrx(dateTrx);
                            matchInv.setProcessed(true);
                            if (!matchInv.save()) {
                                matchInv.delete(true);
                                String msg = "Failed to auto match invoice.";
                                ValueNamePair error = CLogger.retrieveError();
                                if (error != null) {
                                    msg = msg + " " + error.getName();
                                }
                                // log as debug message and continue
                                s_log.fine(msg);
                                continue;
                            }
                            mpo.setMatchInvCreated(matchInv);
                        }
                    }
                    if (iLine != null) mpo.setC_InvoiceLine_ID(iLine);
                    if (sLine != null) {
                        mpo.setM_InOutLine_ID(sLine.getM_InOutLine_ID());
                        if (!mpo.isPosted()) mpo.setDateAcct(sLine.getParent().getDateAcct());
                    }

                    if (!mpo.save()) {
                        String msg = "Failed to update match po.";
                        ValueNamePair error = CLogger.retrieveError();
                        if (error != null) {
                            msg = msg + " " + error.getName();
                        }
                        throw new RuntimeException(msg);
                    }

                    qty = qty.subtract(toMatch);
                    if (qty.signum() <= 0) {
                        retValue = mpo;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            s_log.log(Level.SEVERE, sql, e);
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new IllegalStateException(e);
            }
        } finally {

        }

        //	Create New
        if (retValue == null) {
            BigDecimal sLineMatchedQty = null;
            if (sLine != null && iLine != null) {
                sLineMatchedQty =
                        getSQLValueBD(
                                "SELECT Sum(Qty) FROM M_MatchPO WHERE C_OrderLine_ID="
                                        + C_OrderLine_ID
                                        + " AND M_InOutLine_ID=?",
                                sLine.getM_InOutLine_ID());
            }

            if (sLine != null
                    && (sLine.getC_OrderLine_ID() == C_OrderLine_ID || iLine == null)
                    && (sLineMatchedQty == null || sLineMatchedQty.signum() <= 0)) {
                if (qty.signum() > 0) {
                    retValue = new MMatchPO(sLine, dateTrx, qty);
                    retValue.setC_OrderLine_ID(C_OrderLine_ID);
                    if (iLine != null) retValue.setC_InvoiceLine_ID(iLine);
                    if (!retValue.save()) {
                        String msg = "Failed to update match po.";
                        ValueNamePair error = CLogger.retrieveError();
                        if (error != null) {
                            msg = msg + " " + error.getName();
                        }
                        throw new RuntimeException(msg);
                    }
                }
            } else if (iLine != null) {
                if (qty.signum() > 0) {
                    retValue = new MMatchPO(iLine, dateTrx, qty);
                    retValue.setC_OrderLine_ID(C_OrderLine_ID);
                    if (!retValue.save()) {
                        String msg = "Failed to update match po.";
                        ValueNamePair error = CLogger.retrieveError();
                        if (error != null) {
                            msg = msg + " " + error.getName();
                        }
                        throw new RuntimeException(msg);
                    }
                }
            }
        }

        return retValue;
    } //	create

    /**
     * Get the match inv created for immediate accounting purposes Is cleared after read, so if you
     * read twice second time it returns null
     *
     * @param matchInv
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
    private void setMatchInvCreated(MMatchInv matchInv) {
        m_matchInv = matchInv;
    }

    /**
     * Set C_InvoiceLine_ID
     *
     * @param line line
     */
    public void setC_InvoiceLine_ID(I_C_InvoiceLine line) {
        m_iLine = line;
        if (line == null) setC_InvoiceLine_ID(0);
        else setC_InvoiceLine_ID(line.getC_InvoiceLine_ID());
    } //	setC_InvoiceLine_ID

    /**
     * Set C_InvoiceLine_ID
     *
     * @param C_InvoiceLine_ID id
     */
    public void setC_InvoiceLine_ID(int C_InvoiceLine_ID) {
        int old = getC_InvoiceLine_ID();
        if (old != C_InvoiceLine_ID) {
            super.setC_InvoiceLine_ID(C_InvoiceLine_ID);
            m_isInvoiceLineChange = true;
        }
    } //	setC_InvoiceLine_ID

    /**
     * Get Invoice Line
     *
     * @return invoice line or null
     */
    public I_C_InvoiceLine getInvoiceLine() {
        if (m_iLine == null && getC_InvoiceLine_ID() != 0)
            m_iLine = new MInvoiceLine(getCtx(), getC_InvoiceLine_ID());
        return m_iLine;
    } //	getInvoiceLine

    /**
     * Set M_InOutLine_ID
     *
     * @param M_InOutLine_ID id
     */
    public void setM_InOutLine_ID(int M_InOutLine_ID) {
        int old = getM_InOutLine_ID();
        if (old != M_InOutLine_ID) {
            super.setM_InOutLine_ID(M_InOutLine_ID);
            m_isInOutLineChange = true;
        }
    } //	setM_InOutLine_ID

    /**
     * Get Order Line
     *
     * @return order line or null
     */
    public MOrderLine getOrderLine() {
        if ((m_oLine == null && getC_OrderLine_ID() != 0)
                || getC_OrderLine_ID() != m_oLine.getC_OrderLine_ID())
            m_oLine = new MOrderLine(getCtx(), getC_OrderLine_ID());
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
                            getCtx(),
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
        if (getMAttributeSetInstance_ID() == 0 && getM_InOutLine_ID() != 0) {
            MInOutLine iol = new MInOutLine(getCtx(), getM_InOutLine_ID());
            setM_AttributeSetInstance_ID(iol.getMAttributeSetInstance_ID());
        }

        // Bayu, Sistematika
        // BF [ 2240484 ] Re MatchingPO, MMatchPO doesn't contains Invoice info
        // If newRecord, set c_invoiceline_id while null
        if (newRecord && getC_InvoiceLine_ID() == 0) {
            MMatchInv[] mpi = MMatchInv.getInOutLine(getCtx(), getM_InOutLine_ID());
            for (int i = 0; i < mpi.length; i++) {
                if (mpi[i].getC_InvoiceLine_ID() != 0
                        && mpi[i].getMAttributeSetInstance_ID() == getMAttributeSetInstance_ID()) {
                    if (mpi[i].getQty().compareTo(getQty()) == 0) // same quantity
                    {
                        setC_InvoiceLine_ID(mpi[i].getC_InvoiceLine_ID());
                        break;
                    } else // create MatchPO record for PO-Invoice if different quantity
                    {
                        MInvoiceLine il =
                                new MInvoiceLine(getCtx(), mpi[i].getC_InvoiceLine_ID());
                        MMatchPO match = new MMatchPO(il, getDateTrx(), mpi[i].getQty());
                        match.setC_OrderLine_ID(getC_OrderLine_ID());
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
        if (getC_OrderLine_ID() == 0) {
            I_C_InvoiceLine il = null;
            if (getC_InvoiceLine_ID() != 0) {
                il = getInvoiceLine();
                if (il.getC_OrderLine_ID() != 0) setC_OrderLine_ID(il.getC_OrderLine_ID());
            } //	get from invoice
            if (getC_OrderLine_ID() == 0 && getM_InOutLine_ID() != 0) {
                MInOutLine iol = new MInOutLine(getCtx(), getM_InOutLine_ID());
                if (iol.getC_OrderLine_ID() != 0) {
                    setC_OrderLine_ID(iol.getC_OrderLine_ID());
                    if (il != null) {
                        il.setC_OrderLine_ID(iol.getC_OrderLine_ID());
                        il.saveEx();
                    }
                }
            } //	get from shipment
        } //	find order line

        //	Price Match Approval
        if (getC_OrderLine_ID() != 0
                && getC_InvoiceLine_ID() != 0
                && (newRecord
                || is_ValueChanged("C_OrderLine_ID")
                || is_ValueChanged("C_InvoiceLine_ID"))) {
            BigDecimal poPrice = getOrderLine().getPriceActual();
            BigDecimal invPrice = getInvoicePriceActual();
            BigDecimal difference = poPrice.subtract(invPrice);
            if (difference.signum() != 0) {
                difference = difference.multiply(getQty());
                setPriceMatchDifference(difference);
                //	Approval
                MBPGroup group = getOfBPartner(getCtx(), getOrderLine().getBusinessPartnerId());
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
            if (getM_InOutLine_ID() > 0 && getC_InvoiceLine_ID() > 0) {
                int cnt =
                        getSQLValue(
                                "SELECT Count(*) FROM M_MatchInv WHERE M_InOutLine_ID="
                                        + getM_InOutLine_ID()
                                        + " AND C_InvoiceLine_ID="
                                        + getC_InvoiceLine_ID());
                if (cnt <= 0) {
                    MInvoiceLine invoiceLine =
                            new MInvoiceLine(getCtx(), getC_InvoiceLine_ID());
                    MInOutLine inoutLine = new MInOutLine(getCtx(), getM_InOutLine_ID());
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
            if (getM_InOutLine_ID() > 0) {
                MInOutLine line = new MInOutLine(getCtx(), getM_InOutLine_ID());
                BigDecimal matchedQty =
                        getSQLValueBD(
                                "SELECT Coalesce(SUM(Qty),0) FROM M_MatchPO WHERE M_InOutLine_ID=?",
                                getM_InOutLine_ID());
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

            if (getC_InvoiceLine_ID() > 0) {
                MInvoiceLine line = new MInvoiceLine(getCtx(), getC_InvoiceLine_ID());
                BigDecimal matchedQty =
                        getSQLValueBD(
                                "SELECT Coalesce(SUM(Qty),0) FROM M_MatchPO WHERE C_InvoiceLine_ID=?",
                                getC_InvoiceLine_ID());
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

            if (getC_OrderLine_ID() > 0) {
                boolean validateOrderedQty =
                        MSysConfig.getBooleanValue(
                                MSysConfig.VALIDATE_MATCHING_TO_ORDERED_QTY, true, Env.getClientId(Env.getCtx()));
                if (validateOrderedQty) {
                    MOrderLine line = new MOrderLine(getCtx(), getC_OrderLine_ID());
                    BigDecimal invoicedQty =
                            getSQLValueBD(
                                    "SELECT Coalesce(SUM(Qty),0) FROM M_MatchPO WHERE C_InvoiceLine_ID > 0 and C_OrderLine_ID=? AND Reversal_ID IS NULL",
                                    getC_OrderLine_ID());
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
                                    getC_OrderLine_ID());
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
        if (success && getC_OrderLine_ID() != 0) {
            MOrderLine orderLine = getOrderLine();
            //
            if (m_isInOutLineChange
                    && (newRecord || getM_InOutLine_ID() != get_ValueOldAsInt("M_InOutLine_ID"))) {
                if (getM_InOutLine_ID() != 0) // 	new delivery
                    orderLine.setQtyDelivered(orderLine.getQtyDelivered().add(getQty()));
                else //	if (getM_InOutLine_ID() == 0)					//	reset to 0
                    orderLine.setQtyDelivered(orderLine.getQtyDelivered().subtract(getQty()));
                orderLine.setDateDelivered(getDateTrx()); // 	overwrite=last
            }
            if (m_isInvoiceLineChange
                    && (newRecord || getC_InvoiceLine_ID() != get_ValueOldAsInt("C_InvoiceLine_ID"))) {
                if (getC_InvoiceLine_ID() != 0) // 	first time
                    orderLine.setQtyInvoiced(orderLine.getQtyInvoiced().add(getQty()));
                else //	if (getC_InvoiceLine_ID() == 0)				//	set to 0
                    orderLine.setQtyInvoiced(orderLine.getQtyInvoiced().subtract(getQty()));
                orderLine.setDateInvoiced(getDateTrx()); // 	overwrite=last
            }

            //	Update Order ASI if full match
            if (orderLine.getMAttributeSetInstance_ID() == 0 && getM_InOutLine_ID() != 0) {
                MInOutLine iol = new MInOutLine(getCtx(), getM_InOutLine_ID());
                if (iol.getMovementQty().compareTo(orderLine.getQtyOrdered()) == 0)
                    orderLine.setM_AttributeSetInstance_ID(iol.getMAttributeSetInstance_ID());
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

        if (getC_InvoiceLine_ID() != 0) {
            String sql =
                    "SELECT i.DateAcct "
                            + "FROM C_InvoiceLine il"
                            + " INNER JOIN C_Invoice i ON (i.C_Invoice_ID=il.C_Invoice_ID) "
                            + "WHERE C_InvoiceLine_ID=?";
            invoiceDate = getSQLValueTS(sql, getC_InvoiceLine_ID());
        }
        //
        if (getM_InOutLine_ID() != 0) {
            String sql =
                    "SELECT io.DateAcct "
                            + "FROM M_InOutLine iol"
                            + " INNER JOIN M_InOut io ON (io.M_InOut_ID=iol.M_InOut_ID) "
                            + "WHERE iol.M_InOutLine_ID=?";
            shipDate = getSQLValueTS(sql, getM_InOutLine_ID());
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
            MPeriod.testPeriodOpen(getCtx(), getDateTrx(), MDocType.DOCBASETYPE_MatchPO, getOrgId());
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
        if (success && getC_OrderLine_ID() != 0) {
            MOrderLine orderLine = new MOrderLine(getCtx(), getC_OrderLine_ID());
            if (getM_InOutLine_ID() != 0)
                orderLine.setQtyDelivered(orderLine.getQtyDelivered().subtract(getQty()));
            if (getC_InvoiceLine_ID() != 0)
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
                .append(getC_OrderLine_ID())
                .append(",M_InOutLine_ID=")
                .append(getM_InOutLine_ID())
                .append(",C_InvoiceLine_ID=")
                .append(getC_InvoiceLine_ID())
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
        if (this.isProcessed() && this.getReversal_ID() == 0) {
            MMatchPO reversal = new MMatchPO(getCtx(), 0);
            reversal.setC_InvoiceLine_ID(getC_InvoiceLine_ID());
            reversal.setM_InOutLine_ID(getM_InOutLine_ID());
            PO.copyValues(this, reversal);
            reversal.setOrgId(this.getOrgId());
            reversal.setDescription("(->" + this.getDocumentNo() + ")");
            reversal.setQty(this.getQty().negate());
            reversal.setDateAcct(reversalDate);
            reversal.setDateTrx(reversalDate);
            reversal.setValueNoCheck("DocumentNo", null);
            reversal.setPosted(false);
            reversal.setReversal_ID(getM_MatchPO_ID());
            reversal.saveEx();
            this.setDescription("(" + reversal.getDocumentNo() + "<-)");
            this.setReversal_ID(reversal.getM_MatchPO_ID());
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
