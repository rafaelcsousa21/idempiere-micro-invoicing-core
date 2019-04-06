package org.idempiere.process;

import org.compiere.accounting.MOrder;
import org.compiere.accounting.MOrderLine;
import org.compiere.crm.MBPartner;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.orm.MOrgInfo;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.idempiere.common.util.AdempiereUserError;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Generate PO from Sales Order
 *
 * @author Jorg Janke
 * @version $Id: OrderPOCreate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 * <p>Contributor: Carlos Ruiz - globalqss Fix [1709952] - Process: "Generate PO from Sales
 * order" bug
 */
public class OrderPOCreate extends SvrProcess {
    /**
     * Order Date From
     */
    private Timestamp p_DateOrdered_From;
    /**
     * Order Date To
     */
    private Timestamp p_DateOrdered_To;
    /**
     * Customer
     */
    private int p_C_BPartner_ID;
    /**
     * Vendor
     */
    private int p_Vendor_ID;
    /**
     * Sales Order
     */
    private int p_C_Order_ID;
    /**
     * Drop Ship
     */
    private boolean p_IsDropShip = false;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (int i = 0; i < para.length; i++) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null && para[i].getParameterTo() == null) ;
            else if (name.equals("DateOrdered")) {
                p_DateOrdered_From = (Timestamp) para[i].getParameter();
                p_DateOrdered_To = (Timestamp) para[i].getParameterTo();
            } else if (name.equals("C_BPartner_ID"))
                p_C_BPartner_ID = ((BigDecimal) para[i].getParameter()).intValue();
            else if (name.equals("Vendor_ID"))
                p_Vendor_ID = ((BigDecimal) para[i].getParameter()).intValue();
            else if (name.equals("C_Order_ID"))
                p_C_Order_ID = ((BigDecimal) para[i].getParameter()).intValue();
            else if (name.equals("IsDropShip"))
                p_IsDropShip = para[i].getParameter().equals("Y");
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }

        // called from order window w/o parameters
        if (getTableId() == MOrder.Table_ID && getRecordId() > 0) p_C_Order_ID = getRecordId();
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO))
            log.info(
                    "DateOrdered="
                            + p_DateOrdered_From
                            + " - "
                            + p_DateOrdered_To
                            + " - C_BPartner_ID="
                            + p_C_BPartner_ID
                            + " - Vendor_ID="
                            + p_Vendor_ID
                            + " - IsDropShip="
                            + p_IsDropShip
                            + " - C_Order_ID="
                            + p_C_Order_ID);
        if (p_C_Order_ID == 0
                && p_DateOrdered_From == null
                && p_DateOrdered_To == null
                && p_C_BPartner_ID == 0
                && p_Vendor_ID == 0) throw new AdempiereUserError("You need to restrict selection");
        //
        StringBuilder sql =
                new StringBuilder("SELECT * FROM C_Order o ")
                        .append("WHERE o.IsSOTrx='Y'")
                        //	No Duplicates
                        //	" AND o.Link_Order_ID IS NULL"
                        .append(
                                " AND NOT EXISTS (SELECT * FROM C_OrderLine ol WHERE o.C_Order_ID=ol.C_Order_ID AND ol.Link_OrderLine_ID IS NOT NULL)");
        if (p_C_Order_ID != 0) sql.append(" AND o.C_Order_ID=?");
        else {
            if (p_C_BPartner_ID != 0) sql.append(" AND o.C_BPartner_ID=?");
            if (p_Vendor_ID != 0)
                sql.append(" AND EXISTS (SELECT * FROM C_OrderLine ol")
                        .append(" INNER JOIN M_Product_PO po ON (ol.M_Product_ID=po.M_Product_ID) ")
                        .append("WHERE o.C_Order_ID=ol.C_Order_ID AND po.C_BPartner_ID=?)");
            if (p_DateOrdered_From != null && p_DateOrdered_To != null)
                sql.append("AND TRUNC(o.DateOrdered) BETWEEN ? AND ?");
            else if (p_DateOrdered_From != null && p_DateOrdered_To == null)
                sql.append("AND TRUNC(o.DateOrdered) >= ?");
            else if (p_DateOrdered_From == null && p_DateOrdered_To != null)
                sql.append("AND TRUNC(o.DateOrdered) <= ?");
        }
        int counter = 0;

        MOrder[] orders = BaseOrderPOCreateKt.getOrdersForPO(sql.toString(),
                p_C_Order_ID, p_C_BPartner_ID, p_Vendor_ID,
                p_DateOrdered_From, p_DateOrdered_To
        );
        for (MOrder order : orders) {
            counter += createPOFromSO(order);
        }

        if (counter == 0) if (log.isLoggable(Level.FINE)) log.fine(sql.toString());
        StringBuilder msgreturn = new StringBuilder("@Created@ ").append(counter);
        return msgreturn.toString();
    } //	doIt

    /**
     * Create PO From SO
     *
     * @param so sales order
     * @return number of POs created
     * @throws Exception
     */
    private int createPOFromSO(MOrder so) throws Exception {
        if (log.isLoggable(Level.INFO)) log.info(so.toString());
        MOrderLine[] soLines = so.getLines(true, null);
        if (soLines == null || soLines.length == 0) {
            log.warning("No Lines - " + so);
            return 0;
        }
        //
        int counter = 0;
        //	Order Lines with a Product which has a current vendor
        String sql =
                "SELECT MIN(po.C_BPartner_ID), po.M_Product_ID "
                        + "FROM M_Product_PO po"
                        + " INNER JOIN C_OrderLine ol ON (po.M_Product_ID=ol.M_Product_ID) "
                        + "WHERE ol.C_Order_ID=? AND po.IsCurrentVendor='Y' "
                        + "AND po.IsActive='Y' "
                        + ((p_Vendor_ID > 0) ? " AND po.C_BPartner_ID=? " : "")
                        + "GROUP BY po.M_Product_ID "
                        + "ORDER BY 1";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        MOrder po = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, so.getOrderId());
            if (p_Vendor_ID != 0) pstmt.setInt(2, p_Vendor_ID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                //	New Order
                int C_BPartner_ID = rs.getInt(1);
                if (po == null || po.getBill_BPartnerId() != C_BPartner_ID) {
                    po = createPOForVendor(rs.getInt(1), so);
                    String message = Msg.parseTranslation("@OrderCreated@ " + po.getDocumentNo());
                    addBufferLog(0, null, null, message, po.getTableId(), po.getOrderId());
                    counter++;
                }

                //	Line
                int M_Product_ID = rs.getInt(2);
                for (int i = 0; i < soLines.length; i++) {
                    if (soLines[i].getProductId() == M_Product_ID) {
                        MOrderLine poLine = new MOrderLine(po);
                        poLine.setLink_OrderLineId(soLines[i].getOrderLineId());
                        poLine.setProductId(soLines[i].getProductId());
                        poLine.setChargeId(soLines[i].getChargeId());
                        poLine.setAttributeSetInstanceId(soLines[i].getAttributeSetInstanceId());
                        poLine.setUOMId(soLines[i].getUOMId());
                        poLine.setQtyEntered(soLines[i].getQtyEntered());
                        poLine.setQtyOrdered(soLines[i].getQtyOrdered());
                        poLine.setDescription(soLines[i].getDescription());
                        poLine.setDatePromised(soLines[i].getDatePromised());
                        poLine.setPrice();
                        poLine.saveEx();

                        soLines[i].setLink_OrderLineId(poLine.getOrderLineId());
                        soLines[i].saveEx();
                    }
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, sql, e);
            throw e;
        } finally {

            rs = null;
            pstmt = null;
        }
        //	Set Reference to PO
        if (counter == 1 && po != null) {
            so.setLink_OrderId(po.getOrderId());
            so.saveEx();
        }
        return counter;
    } //	createPOFromSO

    /**
     * Create PO for Vendor
     *
     * @param C_BPartner_ID vendor
     * @param so            sales order
     */
    public MOrder createPOForVendor(int C_BPartner_ID, MOrder so) {
        MOrder po = new MOrder(0);
        po.setClientOrg(so.getClientId(), so.getOrgId());
        po.setLink_OrderId(so.getOrderId());
        po.setIsSOTrx(false);
        po.setTargetDocumentTypeId();
        //
        po.setDescription(so.getDescription());
        po.setPOReference(so.getDocumentNo());
        po.setPriorityRule(so.getPriorityRule());
        po.setSalesRepresentativeId(so.getSalesRepresentativeId());
        po.setWarehouseId(so.getWarehouseId());
        //	Set Vendor
        MBPartner vendor = new MBPartner(C_BPartner_ID);
        po.setBPartner(vendor);
        //	Drop Ship
        if (p_IsDropShip) {
            po.setIsDropShip(p_IsDropShip);
            po.setDeliveryViaRule(so.getDeliveryViaRule());
            po.setShipperId(so.getShipperId());

            if (so.isDropShip() && so.getDropShipBPartnerId() != 0) {
                po.setDropShipBPartnerId(so.getDropShipBPartnerId());
                po.setDropShipLocationId(so.getDropShipLocationId());
                po.setDropShipUserId(so.getDropShipUserId());
            } else {
                po.setDropShipBPartnerId(so.getBusinessPartnerId());
                po.setDropShipLocationId(so.getBusinessPartnerLocationId());
                po.setDropShipUserId(so.getUserId());
            }
            // get default drop ship warehouse
            MOrgInfo orginfo = MOrgInfo.get(po.getOrgId());
            if (orginfo.getDropShipWarehouseId() != 0)
                po.setWarehouseId(orginfo.getDropShipWarehouseId());
            else log.log(Level.SEVERE, "Must specify drop ship warehouse in org info.");
        }
        //	References
        po.setBusinessActivityId(so.getBusinessActivityId());
        po.setCampaignId(so.getCampaignId());
        po.setProjectId(so.getProjectId());
        po.setUser1Id(so.getUser1Id());
        po.setUser2Id(so.getUser2Id());
        //
        po.saveEx();
        return po;
    } //	createPOForVendor
} //	doIt
