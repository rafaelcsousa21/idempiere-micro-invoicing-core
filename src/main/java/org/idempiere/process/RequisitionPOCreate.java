package org.idempiere.process;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.compiere.accounting.MOrder;
import org.compiere.accounting.MOrderLine;
import org.compiere.accounting.MProduct;
import org.compiere.accounting.MProductPO;
import org.compiere.accounting.MRequisition;
import org.compiere.accounting.MRequisitionLine;
import org.compiere.crm.MBPartner;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.model.I_C_BPartner;
import org.compiere.order.MCharge;
import org.compiere.orm.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.idempiere.common.util.AdempiereUserError;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

/**
 * Create PO from Requisition
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>BF [ 2609760 ] RequisitionPOCreate not using DateRequired
 * <li>BF [ 2605888 ] CreatePOfromRequisition creates more PO than needed
 * <li>BF [ 2811718 ] Create PO from Requsition without any parameter teminate in NPE
 * http://sourceforge.net/tracker/?func=detail&atid=879332&aid=2811718&group_id=176962
 * <li>FR [ 2844074 ] Requisition PO Create - more selection fields
 * https://sourceforge.net/tracker/?func=detail&aid=2844074&group_id=176962&atid=879335
 * @version $Id: RequisitionPOCreate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class RequisitionPOCreate extends SvrProcess {
    /**
     * Org
     */
    private int p_AD_Org_ID = 0;
    /**
     * Warehouse
     */
    private int p_M_Warehouse_ID = 0;
    /**
     * Doc Date From
     */
    private Timestamp p_DateDoc_From;
    /**
     * Doc Date To
     */
    private Timestamp p_DateDoc_To;
    /**
     * Doc Date From
     */
    private Timestamp p_DateRequired_From;
    /**
     * Doc Date To
     */
    private Timestamp p_DateRequired_To;
    /**
     * Priority
     */
    private String p_PriorityRule = null;
    /**
     * User
     */
    private int p_AD_User_ID = 0;
    /**
     * Product
     */
    private int p_M_Product_ID = 0;
    /**
     * Product Category
     */
    private int p_M_Product_Category_ID = 0;
    /**
     * BPartner Group
     */
    private int p_C_BP_Group_ID = 0;
    /**
     * Requisition
     */
    private int p_M_Requisition_ID = 0;

    /**
     * Consolidate
     */
    private boolean p_ConsolidateDocument = false;

    /**
     * Order
     */
    private MOrder m_order = null;
    /**
     * Order Line
     */
    private MOrderLine m_orderLine = null;
    /**
     * Orders Cache : (C_BPartner_ID, DateRequired, M_PriceList_ID) -> MOrder
     */
    private HashMap<MultiKey, MOrder> m_cacheOrders = new HashMap<MultiKey, MOrder>();
    private int m_M_Requisition_ID = 0;
    private int m_M_Product_ID = 0;
    private int m_M_AttributeSetInstance_ID = 0;
    /**
     * BPartner
     */
    private I_C_BPartner m_bpartner = null;
    private List<Integer> m_excludedVendors = new ArrayList<Integer>();

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (int i = 0; i < para.length; i++) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null && para[i].getParameterTo() == null) ;
            else if (name.equals("AD_Org_ID")) p_AD_Org_ID = para[i].getParameterAsInt();
            else if (name.equals("M_Warehouse_ID")) p_M_Warehouse_ID = para[i].getParameterAsInt();
            else if (name.equals("DateDoc")) {
                p_DateDoc_From = (Timestamp) para[i].getParameter();
                p_DateDoc_To = (Timestamp) para[i].getParameterTo();
            } else if (name.equals("DateRequired")) {
                p_DateRequired_From = (Timestamp) para[i].getParameter();
                p_DateRequired_To = (Timestamp) para[i].getParameterTo();
            } else if (name.equals("PriorityRule")) p_PriorityRule = (String) para[i].getParameter();
            else if (name.equals("AD_User_ID")) p_AD_User_ID = para[i].getParameterAsInt();
            else if (name.equals("M_Product_ID")) p_M_Product_ID = para[i].getParameterAsInt();
            else if (name.equals("M_Product_Category_ID"))
                p_M_Product_Category_ID = para[i].getParameterAsInt();
            else if (name.equals("C_BP_Group_ID")) p_C_BP_Group_ID = para[i].getParameterAsInt();
            else if (name.equals("M_Requisition_ID")) p_M_Requisition_ID = para[i].getParameterAsInt();
            else if (name.equals("ConsolidateDocument"))
                p_ConsolidateDocument = "Y".equals(para[i].getParameter());
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
    } //	prepare

    /**
     * Process
     *
     * @return info
     * @throws Exception
     */
    protected String doIt() throws Exception {
        //	Specific
        if (p_M_Requisition_ID != 0) {
            if (log.isLoggable(Level.INFO)) log.info("M_Requisition_ID=" + p_M_Requisition_ID);
            MRequisition req = new MRequisition(p_M_Requisition_ID);
            if (!MRequisition.DOCSTATUS_Completed.equals(req.getDocStatus())) {
                throw new AdempiereUserError("@DocStatus@ = " + req.getDocStatus());
            }
            MRequisitionLine[] lines = req.getLines();
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].getOrderLineId() == 0) {
                    process(lines[i]);
                }
            }
            closeOrder();
            return "";
        } //	single Requisition

        //
        if (log.isLoggable(Level.INFO))
            log.info(
                    "AD_Org_ID="
                            + p_AD_Org_ID
                            + ", M_Warehouse_ID="
                            + p_M_Warehouse_ID
                            + ", DateDoc="
                            + p_DateDoc_From
                            + "/"
                            + p_DateDoc_To
                            + ", DateRequired="
                            + p_DateRequired_From
                            + "/"
                            + p_DateRequired_To
                            + ", PriorityRule="
                            + p_PriorityRule
                            + ", AD_User_ID="
                            + p_AD_User_ID
                            + ", M_Product_ID="
                            + p_M_Product_ID
                            + ", ConsolidateDocument"
                            + p_ConsolidateDocument);

        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder whereClause = new StringBuilder("C_OrderLine_ID IS NULL");
        if (p_AD_Org_ID > 0) {
            whereClause.append(" AND AD_Org_ID=?");
            params.add(p_AD_Org_ID);
        }
        if (p_M_Product_ID > 0) {
            whereClause.append(" AND M_Product_ID=?");
            params.add(p_M_Product_ID);
        } else if (p_M_Product_Category_ID > 0) {
            whereClause
                    .append(
                            " AND EXISTS (SELECT 1 FROM M_Product p WHERE M_RequisitionLine.M_Product_ID=p.M_Product_ID")
                    .append(" AND p.M_Product_Category_ID=?)");
            params.add(p_M_Product_Category_ID);
        }

        if (p_C_BP_Group_ID > 0) {
            whereClause
                    .append(" AND (")
                    .append("M_RequisitionLine.C_BPartner_ID IS NULL")
                    .append(
                            " OR EXISTS (SELECT 1 FROM C_BPartner bp WHERE M_RequisitionLine.C_BPartner_ID=bp.C_BPartner_ID AND bp.C_BP_Group_ID=?)")
                    .append(")");
            params.add(p_C_BP_Group_ID);
        }

        //
        //	Requisition Header
        whereClause
                .append(
                        " AND EXISTS (SELECT 1 FROM M_Requisition r WHERE M_RequisitionLine.M_Requisition_ID=r.M_Requisition_ID")
                .append(" AND r.DocStatus=?");
        params.add(MRequisition.DOCSTATUS_Completed);
        if (p_M_Warehouse_ID > 0) {
            whereClause.append(" AND r.M_Warehouse_ID=?");
            params.add(p_M_Warehouse_ID);
        }
        if (p_DateDoc_From != null) {
            whereClause.append(" AND r.DateDoc >= ?");
            params.add(p_DateDoc_From);
        }
        if (p_DateDoc_To != null) {
            whereClause.append(" AND r.DateDoc <= ?");
            params.add(p_DateDoc_To);
        }
        if (p_DateRequired_From != null) {
            whereClause.append(" AND r.DateRequired >= ?");
            params.add(p_DateRequired_From);
        }
        if (p_DateRequired_To != null) {
            whereClause.append(" AND r.DateRequired <= ?");
            params.add(p_DateRequired_To);
        }
        if (p_PriorityRule != null) {
            whereClause.append(" AND r.PriorityRule >= ?");
            params.add(p_PriorityRule);
        }
        if (p_AD_User_ID > 0) {
            whereClause.append(" AND r.AD_User_ID=?");
            params.add(p_AD_User_ID);
        }
        whereClause.append(")"); // End Requisition Header
        //
        // ORDER BY clause
        StringBuilder orderClause = new StringBuilder();
        if (!p_ConsolidateDocument) {
            orderClause.append("M_Requisition_ID, ");
        }
        orderClause.append(
                "(SELECT DateRequired FROM M_Requisition r WHERE M_RequisitionLine.M_Requisition_ID=r.M_Requisition_ID),");
        orderClause.append("M_Product_ID, C_Charge_ID, M_AttributeSetInstance_ID");

        List<MRequisitionLine> result =
                new Query(MRequisitionLine.Table_Name, whereClause.toString())
                        .setParameters(params)
                        .setOrderBy(orderClause.toString())
                        .setClientId()
                        .list();
        for (MRequisitionLine line : result) {
            process(line);
        }

        closeOrder();
        return "";
    } //	doit

    /**
     * Process Line
     *
     * @param rLine request line
     * @throws Exception
     */
    private void process(MRequisitionLine rLine) throws Exception {
        if (rLine.getProductId() == 0 && rLine.getChargeId() == 0) {
            log.warning(
                    "Ignored Line"
                            + rLine.getLine()
                            + " "
                            + rLine.getDescription()
                            + " - "
                            + rLine.getLineNetAmt());
            return;
        }

        if (!p_ConsolidateDocument && rLine.getRequisitionId() != m_M_Requisition_ID) {
            closeOrder();
        }
        if (m_orderLine == null
                || rLine.getProductId() != m_M_Product_ID
                || rLine.getAttributeSetInstanceId() != m_M_AttributeSetInstance_ID
                || rLine.getChargeId() != 0 // 	single line per charge
                || m_order == null
                || m_order.getDatePromised().compareTo(rLine.getDateRequired()) != 0) {
            newLine(rLine);
            // No Order Line was produced (vendor was not valid/allowed) => SKIP
            if (m_orderLine == null) return;
        }

        //	Update Order Line
        m_orderLine.setQty(m_orderLine.getQtyOrdered().add(rLine.getQty()));
        //	Update Requisition Line
        rLine.setOrderLineId(m_orderLine.getOrderLineId());
        rLine.saveEx();
    } //	process

    /**
     * Create new Order
     *
     * @param rLine         request line
     * @param C_BPartner_ID b.partner
     * @throws Exception
     */
    private void newOrder(MRequisitionLine rLine, int C_BPartner_ID) throws Exception {
        if (m_order != null) {
            closeOrder();
        }

        //	BPartner
        if (m_bpartner == null || C_BPartner_ID != m_bpartner.getId()) {
            m_bpartner = MBPartner.get(C_BPartner_ID);
        }

        //	Order
        Timestamp DateRequired = rLine.getDateRequired();
        int M_PriceList_ID = rLine.getParent().getPriceListId();
        MultiKey key = new MultiKey(C_BPartner_ID, DateRequired, M_PriceList_ID);
        m_order = m_cacheOrders.get(key);
        if (m_order == null) {
            m_order = new MOrder(0);
            m_order.setOrgId(rLine.getOrgId());
            m_order.setWarehouseId(rLine.getParent().getWarehouseId());
            m_order.setDatePromised(DateRequired);
            m_order.setIsSOTrx(false);
            m_order.setTargetDocumentTypeId();
            m_order.setBPartner(m_bpartner);
            m_order.setPriceListId(M_PriceList_ID);
            //	default po document type
            if (!p_ConsolidateDocument) {
                StringBuilder msgsd =
                        new StringBuilder()
                                .append(Msg.getElement("M_Requisition_ID"))
                                .append(": ")
                                .append(rLine.getParent().getDocumentNo());
                m_order.setDescription(msgsd.toString());
            }

            //	Prepare Save
            m_order.saveEx();
            // Put to cache
            m_cacheOrders.put(key, m_order);
        }
        m_M_Requisition_ID = rLine.getRequisitionId();
    } //	newOrder

    /**
     * Close Order
     *
     * @throws Exception
     */
    private void closeOrder() throws Exception {
        if (m_orderLine != null) {
            m_orderLine.saveEx();
        }
        if (m_order != null) {
            m_order.load();
            String message = Msg.parseTranslation("@GeneratedPO@ " + m_order.getDocumentNo());
            addBufferLog(
                    0,
                    null,
                    m_order.getGrandTotal(),
                    message,
                    m_order.getTableId(),
                    m_order.getOrderId());
        }
        m_order = null;
        m_orderLine = null;
    } //	closeOrder

    /**
     * New Order Line (different Product)
     *
     * @param rLine request line
     * @throws Exception
     */
    private void newLine(MRequisitionLine rLine) throws Exception {
        if (m_orderLine != null) {
            m_orderLine.saveEx();
        }
        m_orderLine = null;
        MProduct product = MProduct.get(rLine.getProductId());

        //	Get Business Partner
        int C_BPartner_ID = rLine.getBusinessPartnerId();
        if (C_BPartner_ID != 0) {
        } else if (rLine.getChargeId() != 0) {
            MCharge charge = MCharge.get(rLine.getChargeId());
            C_BPartner_ID = charge.getBusinessPartnerId();
            if (C_BPartner_ID == 0) {
                throw new AdempiereUserError("No Vendor for Charge " + charge.getName());
            }
        } else {
            // Find Strategic Vendor for Product
            // TODO: refactor
            MProductPO[] ppos = MProductPO.getOfProduct(product.getProductId());
            for (int i = 0; i < ppos.length; i++) {
                if (ppos[i].isCurrentVendor() && ppos[i].getBusinessPartnerId() != 0) {
                    C_BPartner_ID = ppos[i].getBusinessPartnerId();
                    break;
                }
            }
            if (C_BPartner_ID == 0 && ppos.length > 0) {
                C_BPartner_ID = ppos[0].getBusinessPartnerId();
            }
            if (C_BPartner_ID == 0) {
                throw new NoVendorForProductException(product.getName());
            }
        }

        if (!isGenerateForVendor(C_BPartner_ID)) {
            if (log.isLoggable(Level.INFO)) log.info("Skip for partner " + C_BPartner_ID);
            return;
        }

        //	New Order - Different Vendor
        if (m_order == null
                || m_order.getBusinessPartnerId() != C_BPartner_ID
                || m_order.getDatePromised().compareTo(rLine.getDateRequired()) != 0) {
            newOrder(rLine, C_BPartner_ID);
        }

        //	No Order Line
        m_orderLine = new MOrderLine(m_order);
        m_orderLine.setDatePromised(rLine.getDateRequired());
        if (product != null) {
            m_orderLine.setProduct(product);
            m_orderLine.setAttributeSetInstanceId(rLine.getAttributeSetInstanceId());
        } else {
            m_orderLine.setChargeId(rLine.getChargeId());
            m_orderLine.setPriceActual(rLine.getPriceActual());
        }
        m_orderLine.setOrgId(rLine.getOrgId());

        //	Prepare Save
        m_M_Product_ID = rLine.getProductId();
        m_M_AttributeSetInstance_ID = rLine.getAttributeSetInstanceId();
        m_orderLine.saveEx();
    } //	newLine

    /**
     * Do we need to generate Purchase Orders for given Vendor
     *
     * @param C_BPartner_ID
     * @return true if it's allowed
     */
    private boolean isGenerateForVendor(int C_BPartner_ID) {
        // No filter group was set => generate for all vendors
        if (p_C_BP_Group_ID <= 0) return true;

        if (m_excludedVendors.contains(C_BPartner_ID)) return false;
        //
        boolean match =
                new Query(
                        MBPartner.Table_Name,
                        "C_BPartner_ID=? AND C_BP_Group_ID=?"
                )
                        .setParameters(C_BPartner_ID, p_C_BP_Group_ID)
                        .match();
        if (!match) {
            m_excludedVendors.add(C_BPartner_ID);
        }
        return match;
    }
} //	RequisitionPOCreate
