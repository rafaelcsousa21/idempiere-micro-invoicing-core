package org.compiere.accounting;

import org.compiere.invoicing.MInvoiceLine;
import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.order.MInOutLine;
import org.compiere.orm.MDocType;
import org.compiere.orm.PO;
import org.compiere.orm.Query;
import org.idempiere.common.util.CLogger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.getSQLValueBD;
import static software.hsharp.core.util.DBKt.getSQLValueTS;


/**
 * Match Invoice (Receipt<>Invoice) Model. Accounting: - Not Invoiced Receipts (relief) - IPV
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>BF [ 1926113 ] MMatchInv.getNewerDateAcct() should work in trx
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * <li>FR [ 2520591 ] Support multiples calendar for Org
 * @author Bayu Cahya, Sistematika
 * <li>BF [ 2240484 ] Re MatchingPO, MMatchPO doesn't contains Invoice info
 * @version $Id: MMatchInv.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 */
public class MMatchInv extends X_M_MatchInv implements IPODoc {
    /**
     *
     */
    private static final long serialVersionUID = 3668871839074170205L;
    /**
     * Static Logger
     */
    @SuppressWarnings("unused")
    private static CLogger s_log = CLogger.getCLogger(MMatchInv.class);

    // MZ Goodwill

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx           context
     * @param M_MatchInv_ID id
     * @param trxName       transaction
     */
    public MMatchInv(Properties ctx, int M_MatchInv_ID) {
        super(ctx, M_MatchInv_ID);
        if (M_MatchInv_ID == 0) {
            //	setDateTrx (new Timestamp(System.currentTimeMillis()));
            //	setC_InvoiceLine_ID (0);
            //	setM_InOutLine_ID (0);
            //	setM_Product_ID (0);
            setM_AttributeSetInstance_ID(0);
            //	setQty (Env.ZERO);
            setPosted(false);
            setProcessed(false);
            setProcessing(false);
        }
    } //	MMatchInv
    // end MZ

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MMatchInv(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MMatchInv

    /**
     * Invoice Line Constructor
     *
     * @param iLine   invoice line
     * @param dateTrx optional date
     * @param qty     matched quantity
     */
    public MMatchInv(I_C_InvoiceLine iLine, Timestamp dateTrx, BigDecimal qty) {
        this(iLine.getCtx(), 0);
        setClientOrg(iLine);
        setC_InvoiceLine_ID(iLine.getC_InvoiceLine_ID());
        setM_InOutLine_ID(iLine.getM_InOutLine_ID());
        if (dateTrx != null) setDateTrx(dateTrx);
        setM_Product_ID(iLine.getM_Product_ID());
        setM_AttributeSetInstance_ID(iLine.getMAttributeSetInstance_ID());
        setQty(qty);
        setProcessed(true); // 	auto
    } //	MMatchInv

    /**
     * Get InOut-Invoice Matches
     *
     * @param ctx              context
     * @param M_InOutLine_ID   shipment
     * @param C_InvoiceLine_ID invoice
     * @param trxName          transaction
     * @return array of matches
     */
    public static MMatchInv[] get(
            Properties ctx, int M_InOutLine_ID, int C_InvoiceLine_ID) {
        if (M_InOutLine_ID <= 0 || C_InvoiceLine_ID <= 0) return new MMatchInv[]{};
        //
        final String whereClause = "M_InOutLine_ID=? AND C_InvoiceLine_ID=?";
        List<MMatchInv> list =
                new Query(ctx, I_M_MatchInv.Table_Name, whereClause)
                        .setParameters(M_InOutLine_ID, C_InvoiceLine_ID)
                        .list();
        return list.toArray(new MMatchInv[list.size()]);
    } //	get

    /**
     * Get Inv Matches for InvoiceLine
     *
     * @param ctx              context
     * @param C_InvoiceLine_ID invoice
     * @param trxName          transaction
     * @return array of matches
     */
    public static MMatchInv[] getInvoiceLine(Properties ctx, int C_InvoiceLine_ID) {
        if (C_InvoiceLine_ID <= 0) return new MMatchInv[]{};
        //
        String whereClause = "C_InvoiceLine_ID=?";
        List<MMatchInv> list =
                new Query(ctx, I_M_MatchInv.Table_Name, whereClause)
                        .setParameters(C_InvoiceLine_ID)
                        .list();
        return list.toArray(new MMatchInv[list.size()]);
    } //	getInvoiceLine

    /**
     * Get Inv Matches for InOut
     *
     * @param ctx        context
     * @param M_InOut_ID shipment
     * @param trxName    transaction
     * @return array of matches
     */
    public static MMatchInv[] getInOut(Properties ctx, int M_InOut_ID) {
        if (M_InOut_ID <= 0) return new MMatchInv[]{};
        //
        final String whereClause =
                "EXISTS (SELECT 1 FROM M_InOutLine l"
                        + " WHERE M_MatchInv.M_InOutLine_ID=l.M_InOutLine_ID AND l.M_InOut_ID=?)";
        List<MMatchInv> list =
                new Query(ctx, I_M_MatchInv.Table_Name, whereClause)
                        .setParameters(M_InOut_ID)
                        .list();
        return list.toArray(new MMatchInv[list.size()]);
    } //	getInOut

    /**
     * Get Inv Matches for Invoice
     *
     * @param ctx          context
     * @param C_Invoice_ID invoice
     * @param trxName      transaction
     * @return array of matches
     */
    public static MMatchInv[] getInvoice(Properties ctx, int C_Invoice_ID) {
        if (C_Invoice_ID == 0) return new MMatchInv[]{};
        //
        final String whereClause =
                " EXISTS (SELECT 1 FROM C_InvoiceLine il"
                        + " WHERE M_MatchInv.C_InvoiceLine_ID=il.C_InvoiceLine_ID AND il.C_Invoice_ID=?)";
        List<MMatchInv> list =
                new Query(ctx, I_M_MatchInv.Table_Name, whereClause)
                        .setParameters(C_Invoice_ID)
                        .list();
        return list.toArray(new MMatchInv[list.size()]);
    } //	getInvoice

    /**
     * Get Inv Matches for InOutLine
     *
     * @param ctx            context
     * @param M_InOutLine_ID shipment
     * @param trxName        transaction
     * @return array of matches
     */
    public static MMatchInv[] getInOutLine(Properties ctx, int M_InOutLine_ID) {
        if (M_InOutLine_ID <= 0) {
            return new MMatchInv[]{};
        }
        //
        final String whereClause = MMatchInv.COLUMNNAME_M_InOutLine_ID + "=?";
        List<MMatchInv> list =
                new Query(ctx, I_M_MatchInv.Table_Name, whereClause)
                        .setParameters(M_InOutLine_ID)
                        .list();
        return list.toArray(new MMatchInv[list.size()]);
    } //	getInOutLine

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        //	Set Trx Date
        if (getDateTrx() == null) setDateTrx(new Timestamp(System.currentTimeMillis()));
        //	Set Acct Date
        if (getDateAcct() == null) {
            Timestamp ts = getNewerDateAcct();
            if (ts == null) ts = getDateTrx();
            setDateAcct(ts);
        }
        if (getMAttributeSetInstance_ID() == 0 && getM_InOutLine_ID() != 0) {
            MInOutLine iol = new MInOutLine(getCtx(), getM_InOutLine_ID());
            setM_AttributeSetInstance_ID(iol.getMAttributeSetInstance_ID());
        }
        return true;
    } //	beforeSave

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return false;

        if (getM_InOutLine_ID() > 0) {
            MInOutLine line = new MInOutLine(getCtx(), getM_InOutLine_ID());
            BigDecimal matchedQty =
                    getSQLValueBD(
                            "SELECT Coalesce(SUM(Qty),0) FROM M_MatchInv WHERE M_InOutLine_ID=?",
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
                            "SELECT Coalesce(SUM(Qty),0) FROM M_MatchInv WHERE C_InvoiceLine_ID=?",
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
        return true;
    }

    /**
     * Get the later Date Acct from invoice or shipment
     *
     * @return date or null
     */
    public Timestamp getNewerDateAcct() {
        String sql =
                "SELECT i.DateAcct "
                        + "FROM C_InvoiceLine il"
                        + " INNER JOIN C_Invoice i ON (i.C_Invoice_ID=il.C_Invoice_ID) "
                        + "WHERE C_InvoiceLine_ID=?";
        Timestamp invoiceDate = getSQLValueTS(sql, getC_InvoiceLine_ID());
        //
        sql =
                "SELECT io.DateAcct "
                        + "FROM M_InOutLine iol"
                        + " INNER JOIN M_InOut io ON (io.M_InOut_ID=iol.M_InOut_ID) "
                        + "WHERE iol.M_InOutLine_ID=?";
        Timestamp shipDate = getSQLValueTS(sql, getM_InOutLine_ID());
        //
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
    protected boolean beforeDelete() {
        if (isPosted()) {
            MPeriod.testPeriodOpen(
                    getCtx(), getDateTrx(), MDocType.DOCBASETYPE_MatchInvoice, getOrgId());
            setPosted(false);
            MFactAcct.deleteEx(I_M_MatchInv.Table_ID, getId());
        }
        return true;
    } //	beforeDelete

    /**
     * After Delete
     *
     * @param success success
     * @return success
     */
    protected boolean afterDelete(boolean success) {
        if (success) {
            // AZ Goodwill
            deleteMatchInvCostDetail();
            // end AZ
        }
        return success;
    } //	afterDelete

    // Bayu, Sistematika

    //
    // AZ Goodwill
    private String deleteMatchInvCostDetail() {
        // Get Account Schemas to delete MCostDetail
        MAcctSchema[] acctschemas = MAcctSchema.getClientAcctSchema(getCtx(), getClientId());
        for (int asn = 0; asn < acctschemas.length; asn++) {
            MAcctSchema as = acctschemas[asn];

            MCostDetail cd =
                    MCostDetail.get(
                            getCtx(),
                            "M_MatchInv_ID=?",
                            getM_MatchInv_ID(),
                            getMAttributeSetInstance_ID(),
                            as.getC_AcctSchema_ID());
            if (cd != null) {
                cd.deleteEx(true);
            }
        }

        return "";
    }
    // end Bayu

    /**
     * Reverse MatchPO.
     *
     * @param reversalDate
     * @return message
     * @throws Exception
     */
    public boolean reverse(Timestamp reversalDate) {
        if (this.isProcessed() && this.getReversal_ID() == 0) {
            MMatchInv reversal = new MMatchInv(getCtx(), 0);
            PO.copyValues(this, reversal);
            reversal.setAD_Org_ID(this.getOrgId());
            reversal.setDescription("(->" + this.getDocumentNo() + ")");
            reversal.setQty(this.getQty().negate());
            reversal.setDateAcct(reversalDate);
            reversal.setDateTrx(reversalDate);
            reversal.set_ValueNoCheck("DocumentNo", null);
            reversal.setPosted(false);
            reversal.setReversal_ID(getM_MatchInv_ID());
            reversal.saveEx();
            this.setDescription("(" + reversal.getDocumentNo() + "<-)");
            this.setReversal_ID(reversal.getM_MatchInv_ID());
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

    public void setADClientID(int a) {
        super.setADClientID(a);
    }
} //	MMatchInv
