package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.IDocLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.order.MCharge;
import org.compiere.orm.Query;
import org.compiere.product.IProductPricing;
import org.compiere.util.Msg;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
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
    public MRequisitionLine(Properties ctx, int M_RequisitionLine_ID) {
        super(ctx, M_RequisitionLine_ID);
        if (M_RequisitionLine_ID == 0) {
            //	setM_Requisition_ID (0);
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
    public MRequisitionLine(Properties ctx, Row row) {
        super(ctx, row);
    } //	MRequisitionLine

    /**
     * Parent Constructor
     *
     * @param req requisition
     */
    public MRequisitionLine(MRequisition req) {
        this(req.getCtx(), 0);
        setClientOrg(req);
        setM_Requisition_ID(req.getM_Requisition_ID());
        m_M_PriceList_ID = req.getPriceListId();
        m_parent = req;
    } //	MRequisitionLine

    /**
     * Get corresponding Requisition Line for given Order Line
     *
     * @param ctx
     * @return Requisition Line
     */
    public static MRequisitionLine[] forC_Order_ID(Properties ctx, int C_Order_ID) {
        final String whereClause =
                "EXISTS (SELECT 1 FROM C_OrderLine ol"
                        + " WHERE ol.C_OrderLine_ID=M_RequisitionLine.C_OrderLine_ID"
                        + " AND ol.C_Order_ID=?)";
        List<MRequisitionLine> list =
                new Query(ctx, I_M_RequisitionLine.Table_Name, whereClause)
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
    public static void unlinkC_Order_ID(Properties ctx, int C_Order_ID) {
        for (MRequisitionLine line : MRequisitionLine.forC_Order_ID(ctx, C_Order_ID)) {
            line.setC_OrderLine_ID(0);
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
    public static MRequisitionLine[] forC_OrderLine_ID(
            Properties ctx, int C_OrderLine_ID) {
        final String whereClause = I_M_RequisitionLine.COLUMNNAME_C_OrderLine_ID + "=?";
        List<MRequisitionLine> list =
                new Query(ctx, I_M_RequisitionLine.Table_Name, whereClause)
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
    public static void unlinkC_OrderLine_ID(Properties ctx, int C_OrderLine_ID) {
        for (MRequisitionLine line : forC_OrderLine_ID(ctx, C_OrderLine_ID)) {
            line.setC_OrderLine_ID(0);
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
            m_parent = new MRequisition(getCtx(), getM_Requisition_ID());
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
            MCharge charge = MCharge.get(getCtx(), getChargeId());
            setPriceActual(charge.getChargeAmt());
        }
        if (getM_Product_ID() == 0) return;
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
        if (getM_Product_ID() == 0) return;
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
            log.saveError("ParentComplete", Msg.translate(getCtx(), "M_RequisitionLine"));
            return false;
        }
        if (getLine() == 0) {
            String sql =
                    "SELECT COALESCE(MAX(Line),0)+10 FROM M_RequisitionLine WHERE M_Requisition_ID=?";
            int ii = getSQLValueEx(sql, getM_Requisition_ID());
            setLine(ii);
        }
        //	Product & ASI - Charge
        if (getM_Product_ID() != 0 && getChargeId() != 0) setChargeId(0);
        if (getMAttributeSetInstance_ID() != 0 && getChargeId() != 0)
            setM_AttributeSetInstance_ID(0);
        // Product UOM
        if (getM_Product_ID() > 0 && getC_UOM_ID() <= 0) {
            setC_UOM_ID(getM_Product().getC_UOM_ID());
        }
        //
        if (getPriceActual().signum() == 0) setPrice();
        setLineNetAmt();

        /* Carlos Ruiz - globalqss
         * IDEMPIERE-178 Orders and Invoices must disallow amount lines without product/charge
         */
        if (getParent().getDocumentType().isChargeOrProductMandatory()) {
            if (getChargeId() == 0 && getM_Product_ID() == 0 && getPriceActual().signum() != 0) {
                log.saveError("FillMandatory", Msg.translate(getCtx(), "ChargeOrProductMandatory"));
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
    public I_M_Product getM_Product() {
        return MProduct.get(getCtx(), getM_Product_ID());
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
        int no = executeUpdateEx(sql, new Object[]{getM_Requisition_ID()});
        if (no != 1) log.log(Level.SEVERE, "Header update #" + no);
        m_parent = null;
        return no == 1;
    } //	updateHeader
} //	MRequisitionLine
