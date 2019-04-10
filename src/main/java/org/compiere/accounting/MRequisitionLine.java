package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.IDocLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.order.MCharge;
import org.compiere.orm.Query;
import org.compiere.product.IProductPricing;
import org.compiere.util.MsgKt;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdateEx;
import static software.hsharp.core.util.DBKt.getSQLValueEx;

/**
 * Requisition Line Model
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>BF [ 2419978 ] Voiding PO, requisition don't set on NULL
 * <li>BF [ 2608617 ] Error when I want to delete a PO document
 * <li>BF [ 2609604 ] Add M_RequisitionLine.C_BPartner_ID
 * <li>FR [ 2841841 ] Requisition Improvements
 * https://sourceforge.net/tracker/?func=detail&aid=2841841&group_id=176962&atid=879335
 * @version $Id: MRequisitionLine.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MRequisitionLine extends X_M_RequisitionLine implements IDocLine {
    /**
     *
     */
    private static final long serialVersionUID = -2567343619431184322L;
    /**
     * Parent
     */
    private MRequisition m_parent = null;
    /**
     * PriceList
     */
    private int m_M_PriceList_ID = 0;

    /**
     * Standard Constructor
     *
     * @param ctx                  context
     * @param M_RequisitionLine_ID id
     * @param trxName              transaction
     */
    public MRequisitionLine(int M_RequisitionLine_ID) {
        super(M_RequisitionLine_ID);
        if (M_RequisitionLine_ID == 0) {
            //	setRequisition_ID (0);
            setLine(
                    0); // @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_RequisitionLine WHERE
            // M_Requisition_ID=@M_Requisition_ID@
            setLineNetAmt(Env.ZERO);
            setPriceActual(Env.ZERO);
            setQty(Env.ONE); // 1
        }
    } //	MRequisitionLine

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MRequisitionLine(Row row) {
        super(row);
    } //	MRequisitionLine

    /**
     * Parent Constructor
     *
     * @param req requisition
     */
    public MRequisitionLine(MRequisition req) {
        this(0);
        setClientOrg(req);
        setRequisitionId(req.getRequisitionId());
        m_M_PriceList_ID = req.getPriceListId();
        m_parent = req;
    } //	MRequisitionLine

    /**
     * Get corresponding Requisition Line for given Order Line
     *
     * @param ctx
     * @return Requisition Line
     */
    public static MRequisitionLine[] forC_OrderId(int C_Order_ID) {
        final String whereClause =
                "EXISTS (SELECT 1 FROM C_OrderLine ol"
                        + " WHERE ol.C_OrderLine_ID=M_RequisitionLine.C_OrderLine_ID"
                        + " AND ol.C_Order_ID=?)";
        List<MRequisitionLine> list =
                new Query(I_M_RequisitionLine.Table_Name, whereClause)
                        .setParameters(C_Order_ID)
                        .list();
        return list.toArray(new MRequisitionLine[list.size()]);
    }

    /**
     * UnLink Requisition Lines for given Order
     *
     * @param ctx
     * @param C_Order_ID
     * @param trxName
     */
    public static void unlinkC_OrderId(int C_Order_ID) {
        for (MRequisitionLine line : MRequisitionLine.forC_OrderId(C_Order_ID)) {
            line.setOrderLineId(0);
            line.saveEx();
        }
    }

    /**
     * Get corresponding Requisition Line(s) for given Order Line
     *
     * @param ctx
     * @param C_OrderLine_ID order line
     * @param trxName
     * @return array of Requisition Line(s)
     */
    public static MRequisitionLine[] forC_OrderLineId(
            int C_OrderLine_ID) {
        final String whereClause = I_M_RequisitionLine.COLUMNNAME_C_OrderLine_ID + "=?";
        List<MRequisitionLine> list =
                new Query(I_M_RequisitionLine.Table_Name, whereClause)
                        .setParameters(C_OrderLine_ID)
                        .list();
        return list.toArray(new MRequisitionLine[list.size()]);
    }

    /**
     * UnLink Requisition Lines for given Order Line
     *
     * @param ctx
     * @param C_OrderLine_ID
     * @param trxName
     */
    public static void unlinkC_OrderLineId(int C_OrderLine_ID) {
        for (MRequisitionLine line : forC_OrderLineId(C_OrderLine_ID)) {
            line.setOrderLineId(0);
            line.saveEx();
        }
    }

    /**
     * Get Parent
     *
     * @return parent
     */
    public MRequisition getParent() {
        if (m_parent == null)
            m_parent = new MRequisition(getRequisitionId());
        return m_parent;
    } //	getParent

    /**
     * @return Date when this product is required by planner
     * @see MRequisition#getDateRequired()
     */
    public Timestamp getDateRequired() {
        return getParent().getDateRequired();
    }

    /**
     * Set Price
     */
    public void setPrice() {
        if (getChargeId() != 0) {
            MCharge charge = MCharge.get(getChargeId());
            setPriceActual(charge.getChargeAmt());
        }
        if (getProductId() == 0) return;
        if (m_M_PriceList_ID == 0) m_M_PriceList_ID = getParent().getPriceListId();
        if (m_M_PriceList_ID == 0) {
            throw new AdempiereException("PriceList unknown!");
        }
        setPrice(m_M_PriceList_ID);
    } //	setPrice

    /**
     * Set Price for Product and PriceList
     *
     * @param M_PriceList_ID price list
     */
    public void setPrice(int M_PriceList_ID) {
        if (getProductId() == 0) return;
        //
        if (log.isLoggable(Level.FINE)) log.fine("M_PriceList_ID=" + M_PriceList_ID);
        IProductPricing pp = MProduct.getProductPricing();
        pp.setRequisitionLine(this);
        pp.setPriceListId(M_PriceList_ID);
        //	pp.setPriceDate(getDateOrdered());
        //
        setPriceActual(pp.getPriceStd());
    } //	setPrice

    /**
     * Calculate Line Net Amt
     */
    public void setLineNetAmt() {
        BigDecimal lineNetAmt = getQty().multiply(getPriceActual());
        super.setLineNetAmt(lineNetAmt);
    } //	setLineNetAmt

    /**
     * ************************************************************************ Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        if (newRecord && getParent().isComplete()) {
            log.saveError("ParentComplete", MsgKt.translate("M_RequisitionLine"));
            return false;
        }
        if (getLine() == 0) {
            String sql =
                    "SELECT COALESCE(MAX(Line),0)+10 FROM M_RequisitionLine WHERE M_Requisition_ID=?";
            int ii = getSQLValueEx(sql, getRequisitionId());
            setLine(ii);
        }
        //	Product & ASI - Charge
        if (getProductId() != 0 && getChargeId() != 0) setChargeId(0);
        if (getAttributeSetInstanceId() != 0 && getChargeId() != 0)
            setAttributeSetInstanceId(0);
        // Product UOM
        if (getProductId() > 0 && getUOMId() <= 0) {
            setUOMId(getProduct().getUOMId());
        }
        //
        if (getPriceActual().signum() == 0) setPrice();
        setLineNetAmt();

        /* Carlos Ruiz - globalqss
         * IDEMPIERE-178 Orders and Invoices must disallow amount lines without product/charge
         */
        if (getParent().getDocumentType().isChargeOrProductMandatory()) {
            if (getChargeId() == 0 && getProductId() == 0 && getPriceActual().signum() != 0) {
                log.saveError("FillMandatory", MsgKt.translate("ChargeOrProductMandatory"));
                return false;
            }
        }

        return true;
    } //	beforeSave

    /**
     * After Save. Update Total on Header
     *
     * @param newRecord if new record
     * @param success   save was success
     * @return true if saved
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        return updateHeader();
    } //	afterSave

    /**
     * After Delete
     *
     * @param success
     * @return true/false
     */
    protected boolean afterDelete(boolean success) {
        if (!success) return success;
        return updateHeader();
    } //	afterDelete

    @Override
    public I_M_Product getProduct() {
        return MProduct.get(getProductId());
    }

    /**
     * Update Header
     *
     * @return header updated
     */
    private boolean updateHeader() {
        log.fine("");
        String sql =
                "UPDATE M_Requisition r"
                        + " SET TotalLines="
                        + "(SELECT COALESCE(SUM(LineNetAmt),0) FROM M_RequisitionLine rl "
                        + "WHERE r.M_Requisition_ID=rl.M_Requisition_ID) "
                        + "WHERE M_Requisition_ID=?";
        int no = executeUpdateEx(sql, new Object[]{getRequisitionId()});
        if (no != 1) log.log(Level.SEVERE, "Header update #" + no);
        m_parent = null;
        return no == 1;
    } //	updateHeader
} //	MRequisitionLine
