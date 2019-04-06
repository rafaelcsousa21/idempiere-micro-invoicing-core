package org.idempiere.process;

import org.compiere.accounting.MClient;
import org.compiere.accounting.MClientKt;
import org.compiere.accounting.MOrder;
import org.compiere.accounting.MOrderLine;
import org.compiere.accounting.MProduct;
import org.compiere.accounting.MRequisition;
import org.compiere.accounting.MRequisitionLine;
import org.compiere.accounting.MStorageOnHand;
import org.compiere.accounting.MWarehouse;
import org.compiere.crm.MBPartner;
import org.compiere.invoicing.MLocatorType;
import org.compiere.model.ClientWithAccounting;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.model.ReplenishInterface;
import org.compiere.orm.MDocType;
import org.compiere.orm.MOrg;
import org.compiere.orm.MOrgKt;
import org.compiere.process.SvrProcess;
import org.compiere.production.MLocator;
import org.compiere.util.Msg;
import org.idempiere.common.util.AdempiereSystemError;
import org.idempiere.common.util.AdempiereUserError;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import static org.idempiere.process.ReplenishReportProduction.getReplenishInterface;
import static software.hsharp.core.util.DBKt.executeUpdate;

/**
 * Replenishment Report
 *
 * @author Jorg Janke
 * @version $Id: ReplenishReport.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 * <p>Carlos Ruiz globalqss - integrate bug fixing from Chris Farley [ 1619517 ] Replenish
 * report fails when no records in m_storage
 */
public class ReplenishReport extends SvrProcess {
    /**
     * Warehouse
     */
    private int p_M_Warehouse_ID = 0;
    /**
     * Optional BPartner
     */
    private int p_C_BPartner_ID = 0;
    /**
     * Create (POO)Purchse Order or (POR)Requisition or (MMM)Movements
     */
    private String p_ReplenishmentCreate = null;
    /**
     * Document Type
     */
    private int p_C_DocType_ID = 0;
    /**
     * Return Info
     */
    private StringBuffer m_info = new StringBuffer();

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            switch (name) {
                case "M_Warehouse_ID":
                    p_M_Warehouse_ID = iProcessInfoParameter.getParameterAsInt();
                    break;
                case "C_BPartner_ID":
                    p_C_BPartner_ID = iProcessInfoParameter.getParameterAsInt();
                    break;
                case "ReplenishmentCreate":
                    p_ReplenishmentCreate = (String) iProcessInfoParameter.getParameter();
                    break;
                case "C_DocType_ID":
                    p_C_DocType_ID = iProcessInfoParameter.getParameterAsInt();
                    break;
                default:
                    log.log(Level.SEVERE, "Unknown Parameter: " + name);
                    break;
            }
        }
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        StringBuilder msglog =
                new StringBuilder("M_Warehouse_ID=")
                        .append(p_M_Warehouse_ID)
                        .append(", C_BPartner_ID=")
                        .append(p_C_BPartner_ID)
                        .append(" - ReplenishmentCreate=")
                        .append(p_ReplenishmentCreate)
                        .append(", C_DocType_ID=")
                        .append(p_C_DocType_ID);
        if (log.isLoggable(Level.INFO)) log.info(msglog.toString());
        if (p_ReplenishmentCreate != null && p_C_DocType_ID == 0)
            throw new AdempiereUserError("@FillMandatory@ @C_DocType_ID@");

        MWarehouse wh = MWarehouse.get(p_M_Warehouse_ID);
        if (wh.getId() == 0) throw new AdempiereSystemError("@FillMandatory@ @M_Warehouse_ID@");
        //
        prepareTable();
        fillTable(wh);
        //
        if (p_ReplenishmentCreate == null) return "OK";
        //
        MDocType dt = MDocType.get(p_C_DocType_ID);
        if (!dt.getDocBaseType().equals(p_ReplenishmentCreate))
            throw new AdempiereSystemError(
                    "@C_DocType_ID@=" + dt.getName() + " <> " + p_ReplenishmentCreate);
        //
        switch (p_ReplenishmentCreate) {
            case "POO":
                createPO();
                break;
            case "POR":
                createRequisition();
                break;
            case "MMM":
                createMovements();
                break;
            case "DOO":
                createDO();
                break;
        }
        return m_info.toString();
    } //	doIt

    /**
     * Prepare/Check Replenishment Table
     */
    private void prepareTable() {
        //	Level_Max must be >= Level_Max
        StringBuilder sql =
                new StringBuilder("UPDATE M_Replenish")
                        .append(" SET Level_Max = Level_Min ")
                        .append("WHERE Level_Max < Level_Min");
        int no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Corrected Max_Level=" + no);

        //	Minimum Order should be 1
        sql =
                new StringBuilder("UPDATE M_Product_PO")
                        .append(" SET Order_Min = 1 ")
                        .append("WHERE Order_Min IS NULL OR Order_Min < 1");
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Corrected Order Min=" + no);

        //	Pack should be 1
        sql =
                new StringBuilder("UPDATE M_Product_PO")
                        .append(" SET Order_Pack = 1 ")
                        .append("WHERE Order_Pack IS NULL OR Order_Pack < 1");
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Corrected Order Pack=" + no);

        //	Set Current Vendor where only one vendor

        sql =
                new StringBuilder("UPDATE M_Product_PO p")
                        .append(" SET IsCurrentVendor='Y' ")
                        .append("WHERE IsCurrentVendor<>'Y'")
                        .append(" AND EXISTS (SELECT pp.M_Product_ID FROM M_Product_PO pp ")
                        .append("WHERE p.M_Product_ID=pp.M_Product_ID ")
                        .append("GROUP BY pp.M_Product_ID ")
                        .append("HAVING COUNT(*) = 1)");
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Corrected CurrentVendor(Y)=" + no);

        //	More then one current vendor
        sql =
                new StringBuilder("UPDATE M_Product_PO p")
                        .append(" SET IsCurrentVendor='N' ")
                        .append("WHERE IsCurrentVendor = 'Y'")
                        .append(" AND EXISTS (SELECT pp.M_Product_ID FROM M_Product_PO pp ")
                        .append("WHERE p.M_Product_ID=pp.M_Product_ID AND pp.IsCurrentVendor='Y' ")
                        .append("GROUP BY pp.M_Product_ID ")
                        .append("HAVING COUNT(*) > 1)");
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Corrected CurrentVendor(N)=" + no);

        //	Just to be sure
        sql =
                new StringBuilder("DELETE T_Replenish WHERE AD_PInstance_ID=").append(getProcessInstanceId());
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Delete Existing Temp=" + no);
    } //	prepareTable

    /**
     * Fill Table
     *
     * @param wh warehouse
     */
    private void fillTable(MWarehouse wh) throws Exception {
        StringBuilder sql = new StringBuilder("INSERT INTO T_Replenish ");
        sql.append("(AD_PInstance_ID, M_Warehouse_ID, M_Product_ID, AD_Client_ID, AD_Org_ID,");
        sql.append(" ReplenishType, Level_Min, Level_Max,");
        sql.append(" C_BPartner_ID, Order_Min, Order_Pack, QtyToOrder, ReplenishmentCreate) ");
        sql.append("SELECT ").append(getProcessInstanceId());
        sql.append(", r.M_Warehouse_ID, r.M_Product_ID, r.AD_Client_ID, r.AD_Org_ID,");
        sql.append(" r.ReplenishType, r.Level_Min, r.Level_Max,");
        sql.append(" po.C_BPartner_ID, po.Order_Min, po.Order_Pack, 0, ");

        if (p_ReplenishmentCreate == null) sql.append("null");
        else sql.append("'").append(p_ReplenishmentCreate).append("'");
        sql.append(" FROM M_Replenish r");
        sql.append(" INNER JOIN M_Product_PO po ON (r.M_Product_ID=po.M_Product_ID) ");
        sql.append("WHERE po.IsCurrentVendor='Y'"); // 	Only Current Vendor
        sql.append(" AND r.ReplenishType<>'0'");
        sql.append(" AND po.IsActive='Y' AND r.IsActive='Y'");
        sql.append(" AND r.M_Warehouse_ID=").append(p_M_Warehouse_ID);
        if (p_C_BPartner_ID != 0) sql.append(" AND po.C_BPartner_ID=").append(p_C_BPartner_ID);
        int no = executeUpdate(sql.toString());
        if (log.isLoggable(Level.FINEST)) log.finest(sql.toString());
        if (log.isLoggable(Level.FINE)) log.fine("Insert (1) #" + no);

        if (p_C_BPartner_ID == 0) {
            sql = new StringBuilder("INSERT INTO T_Replenish ");
            sql.append("(AD_PInstance_ID, M_Warehouse_ID, M_Product_ID, AD_Client_ID, AD_Org_ID,");
            sql.append(" ReplenishType, Level_Min, Level_Max,");
            sql.append(" C_BPartner_ID, Order_Min, Order_Pack, QtyToOrder, ReplenishmentCreate) ");
            sql.append("SELECT ").append(getProcessInstanceId());
            sql.append(", r.M_Warehouse_ID, r.M_Product_ID, r.AD_Client_ID, r.AD_Org_ID,");
            sql.append(" r.ReplenishType, r.Level_Min, r.Level_Max,");
            sql.append(" 0, 1, 1, 0, ");
            if (p_ReplenishmentCreate == null) sql.append("null");
            else sql.append("'").append(p_ReplenishmentCreate).append("'");
            sql.append(" FROM M_Replenish r ");
            sql.append("WHERE r.ReplenishType<>'0' AND r.IsActive='Y'");
            sql.append(" AND r.M_Warehouse_ID=").append(p_M_Warehouse_ID);
            sql.append(" AND NOT EXISTS (SELECT * FROM T_Replenish t ");
            sql.append("WHERE r.M_Product_ID=t.M_Product_ID");
            sql.append(" AND AD_PInstance_ID=").append(getProcessInstanceId()).append(")");
            no = executeUpdate(sql.toString());
            if (log.isLoggable(Level.FINE)) log.fine("Insert (BP) #" + no);
        }
        sql = new StringBuilder("UPDATE T_Replenish t SET ");
        sql.append(
                "QtyOnHand = (SELECT COALESCE(SUM(QtyOnHand),0) FROM M_StorageOnHand s, M_Locator l WHERE t.M_Product_ID=s.M_Product_ID");
        sql.append(" AND l.M_Locator_ID=s.M_Locator_ID AND l.M_Warehouse_ID=t.M_Warehouse_ID),");
        sql.append(
                "QtyReserved = (SELECT COALESCE(SUM(Qty),0) FROM M_StorageReservation s WHERE t.M_Product_ID=s.M_Product_ID");
        sql.append(" AND t.M_Warehouse_ID=s.M_Warehouse_ID AND s.IsSOTrx='Y'),");
        sql.append(
                "QtyOrdered = (SELECT COALESCE(SUM(Qty),0) FROM M_StorageReservation s WHERE t.M_Product_ID=s.M_Product_ID");
        sql.append(" AND t.M_Warehouse_ID=s.M_Warehouse_ID AND s.IsSOTrx='N')");
        if (p_C_DocType_ID != 0) sql.append(", C_DocType_ID=").append(p_C_DocType_ID);
        sql.append(" WHERE AD_PInstance_ID=").append(getProcessInstanceId());
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Update #" + no);

        //	Delete inactive products and replenishments
        sql = new StringBuilder("DELETE T_Replenish r ");
        sql.append("WHERE (EXISTS (SELECT * FROM M_Product p ");
        sql.append("WHERE p.M_Product_ID=r.M_Product_ID AND p.IsActive='N')");
        sql.append(" OR EXISTS (SELECT * FROM M_Replenish rr ");
        sql.append(" WHERE rr.M_Product_ID=r.M_Product_ID AND rr.IsActive='N'");
        sql.append(" AND rr.M_Warehouse_ID=").append(p_M_Warehouse_ID).append(" ))");
        sql.append(" AND AD_PInstance_ID=").append(getProcessInstanceId());
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Delete Inactive=" + no);

        //	Ensure Data consistency
        sql = new StringBuilder("UPDATE T_Replenish SET QtyOnHand = 0 WHERE QtyOnHand IS NULL");
        no = executeUpdate(sql.toString());
        sql = new StringBuilder("UPDATE T_Replenish SET QtyReserved = 0 WHERE QtyReserved IS NULL");
        no = executeUpdate(sql.toString());
        sql = new StringBuilder("UPDATE T_Replenish SET QtyOrdered = 0 WHERE QtyOrdered IS NULL");
        no = executeUpdate(sql.toString());

        //	Set Minimum / Maximum Maintain Level
        //	X_M_Replenish.REPLENISHTYPE_ReorderBelowMinimumLevel
        sql = new StringBuilder("UPDATE T_Replenish");
        sql.append(" SET QtyToOrder = CASE WHEN QtyOnHand - QtyReserved + QtyOrdered <= Level_Min ");
        sql.append(" THEN Level_Max - QtyOnHand + QtyReserved - QtyOrdered ");
        sql.append(" ELSE 0 END ");
        sql.append("WHERE ReplenishType='1'");
        sql.append(" AND AD_PInstance_ID=").append(getProcessInstanceId());
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Update Type-1=" + no);
        //
        //	X_M_Replenish.REPLENISHTYPE_MaintainMaximumLevel
        sql = new StringBuilder("UPDATE T_Replenish");
        sql.append(" SET QtyToOrder = Level_Max - QtyOnHand + QtyReserved - QtyOrdered ");
        sql.append("WHERE ReplenishType='2'");
        sql.append(" AND AD_PInstance_ID=").append(getProcessInstanceId());
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Update Type-2=" + no);

        //	Minimum Order Quantity
        sql = new StringBuilder("UPDATE T_Replenish");
        sql.append(" SET QtyToOrder = Order_Min ");
        sql.append("WHERE QtyToOrder < Order_Min");
        sql.append(" AND QtyToOrder > 0");
        sql.append(" AND AD_PInstance_ID=").append(getProcessInstanceId());
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Set MinOrderQty=" + no);

        //	Even dividable by Pack
        sql = new StringBuilder("UPDATE T_Replenish");
        sql.append(" SET QtyToOrder = QtyToOrder - MOD(QtyToOrder, Order_Pack) + Order_Pack ");
        sql.append("WHERE MOD(QtyToOrder, Order_Pack) <> 0");
        sql.append(" AND QtyToOrder > 0");
        sql.append(" AND AD_PInstance_ID=").append(getProcessInstanceId());
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Set OrderPackQty=" + no);

        //	Source from other warehouse
        if (wh.getWarehouseSourceId() != 0) {
            sql = new StringBuilder("UPDATE T_Replenish");
            sql.append(" SET M_WarehouseSource_ID=").append(wh.getWarehouseSourceId());
            sql.append(" WHERE AD_PInstance_ID=").append(getProcessInstanceId());
            no = executeUpdate(sql.toString());
            if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Set Source Warehouse=" + no);
        }
        //	Check Source Warehouse
        sql = new StringBuilder("UPDATE T_Replenish");
        sql.append(" SET M_WarehouseSource_ID = NULL ");
        sql.append("WHERE M_Warehouse_ID=M_WarehouseSource_ID");
        sql.append(" AND AD_PInstance_ID=").append(getProcessInstanceId());
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Set same Source Warehouse=" + no);

        //	Custom Replenishment
        String className = wh.getReplenishmentClass();
        if (className != null && className.length() > 0) {
            //	Get Replenishment Class
            ReplenishInterface custom = null;
            try {
                custom = getReplenishInterface(className);
                if (custom == null) {
                    // if no OSGi plugin is found try the legacy way (in my own classpath)
                    Class<?> clazz = Class.forName(className);
                    custom = (ReplenishInterface) clazz.newInstance();
                }
            } catch (Exception e) {
                throw new AdempiereUserError(
                        "No custom Replenishment class " + className + " - " + e.toString());
            }

            X_T_Replenish[] replenishs = getReplenish("ReplenishType='9'");
            for (int i = 0; i < replenishs.length; i++) {
                X_T_Replenish replenish = replenishs[i];
                if (replenish.getReplenishType().equals(X_T_Replenish.REPLENISHTYPE_Custom)) {
                    BigDecimal qto = null;
                    try {
                        qto = custom.getQtyToOrder(wh, replenish);
                    } catch (Exception e) {
                        log.log(Level.SEVERE, custom.toString(), e);
                    }
                    if (qto == null) qto = Env.ZERO;
                    replenish.setQtyToOrder(qto);
                    replenish.saveEx();
                }
            }
        }
        //	Delete rows where nothing to order
        sql = new StringBuilder("DELETE T_Replenish ");
        sql.append("WHERE QtyToOrder < 1");
        sql.append(" AND AD_PInstance_ID=").append(getProcessInstanceId());
        no = executeUpdate(sql.toString());
        if (no != 0) if (log.isLoggable(Level.FINE)) log.fine("Delete No QtyToOrder=" + no);
    } //	fillTable

    /**
     * Create PO's
     */
    private void createPO() {
        int noOrders = 0;
        StringBuilder info = new StringBuilder();
        //
        MOrder order = null;
        MWarehouse wh = null;
        X_T_Replenish[] replenishs = getReplenish("M_WarehouseSource_ID IS NULL");
        for (int i = 0; i < replenishs.length; i++) {
            X_T_Replenish replenish = replenishs[i];
            if (wh == null || wh.getWarehouseId() != replenish.getWarehouseId())
                wh = MWarehouse.get(replenish.getWarehouseId());
            //
            if (order == null
                    || order.getBusinessPartnerId() != replenish.getBusinessPartnerId()
                    || order.getWarehouseId() != replenish.getWarehouseId()) {
                order = new MOrder(0);
                order.setIsSOTrx(false);
                order.setTargetDocumentTypeId(p_C_DocType_ID);
                MBPartner bp = new MBPartner(replenish.getBusinessPartnerId());
                order.setBPartner(bp);
                order.setSalesRepresentativeId(getUserId());
                order.setDescription(Msg.getMsg("Replenishment"));
                //	Set Org/WH
                order.setOrgId(wh.getOrgId());
                order.setWarehouseId(wh.getWarehouseId());
                if (!order.save()) return;
                addBufferLog(
                        order.getOrderId(),
                        order.getDateOrdered(),
                        null,
                        Msg.parseTranslation("@C_Order_ID@ @Created@"),
                        MOrder.Table_ID,
                        order.getOrderId());
                if (log.isLoggable(Level.FINE)) log.fine(order.toString());
                noOrders++;
                info.append(" - ");
                info.append(order.getDocumentNo());
            }
            MOrderLine line = new MOrderLine(order);
            line.setProductId(replenish.getProductId());
            line.setQty(replenish.getQtyToOrder());
            line.setPrice();
            line.saveEx();
        }
        m_info = new StringBuffer("#").append(noOrders).append(info.toString());
        if (log.isLoggable(Level.INFO)) log.info(m_info.toString());
    } //	createPO

    /**
     * Create Requisition
     */
    private void createRequisition() {
        int noReqs = 0;
        StringBuilder info = new StringBuilder();
        //
        MRequisition requisition = null;
        MWarehouse wh = null;
        X_T_Replenish[] replenishs = getReplenish("M_WarehouseSource_ID IS NULL");
        for (int i = 0; i < replenishs.length; i++) {
            X_T_Replenish replenish = replenishs[i];
            if (wh == null || wh.getWarehouseId() != replenish.getWarehouseId())
                wh = MWarehouse.get(replenish.getWarehouseId());
            //
            if (requisition == null || requisition.getWarehouseId() != replenish.getWarehouseId()) {
                requisition = new MRequisition(0);
                requisition.setUserId(getUserId());
                requisition.setDocumentTypeId(p_C_DocType_ID);
                requisition.setDescription(Msg.getMsg("Replenishment"));
                //	Set Org/WH
                requisition.setOrgId(wh.getOrgId());
                requisition.setWarehouseId(wh.getWarehouseId());
                if (!requisition.save()) return;
                addBufferLog(
                        requisition.getRequisitionId(),
                        requisition.getDateDoc(),
                        null,
                        Msg.parseTranslation("@M_Requisition_ID@ @Created@"),
                        MRequisition.Table_ID,
                        requisition.getRequisitionId());
                if (log.isLoggable(Level.FINE)) log.fine(requisition.toString());
                noReqs++;
                info.append(" - ");
                info.append(requisition.getDocumentNo());
            }
            //
            MRequisitionLine line = new MRequisitionLine(requisition);
            line.setProductId(replenish.getProductId());
            line.setBusinessPartnerId(replenish.getBusinessPartnerId());
            line.setQty(replenish.getQtyToOrder());
            line.setPrice();
            line.saveEx();
        }
        m_info = new StringBuffer("#").append(noReqs).append(info.toString());
        if (log.isLoggable(Level.INFO)) log.info(m_info.toString());
    } //	createRequisition

    /**
     * Create Inventory Movements
     */
    private void createMovements() {
        int noMoves = 0;
        StringBuilder info = new StringBuilder();
        //
        ClientWithAccounting client = null;
        MMovement move = null;
        int M_Warehouse_ID = 0;
        int M_WarehouseSource_ID = 0;
        MWarehouse whSource = null;
        MWarehouse wh = null;
        X_T_Replenish[] replenishs = getReplenish("M_WarehouseSource_ID IS NOT NULL");
        for (X_T_Replenish replenish : replenishs) {
            if (whSource == null
                    || whSource.getWarehouseSourceId() != replenish.getWarehouseSourceId())
                whSource = MWarehouse.get(replenish.getWarehouseSourceId());
            if (wh == null || wh.getWarehouseId() != replenish.getWarehouseId())
                wh = MWarehouse.get(replenish.getWarehouseId());
            if (client == null || client.getClientId() != whSource.getClientId())
                client = MClientKt.getClientWithAccounting(whSource.getClientId());
            //
            if (move == null
                    || M_WarehouseSource_ID != replenish.getWarehouseSourceId()
                    || M_Warehouse_ID != replenish.getWarehouseId()) {
                M_WarehouseSource_ID = replenish.getWarehouseSourceId();
                M_Warehouse_ID = replenish.getWarehouseId();

                move = new MMovement(0);
                move.setDocumentTypeId(p_C_DocType_ID);
                move.setDescription(
                        Msg.getMsg("Replenishment")
                                + ": "
                                + whSource.getName()
                                + "->"
                                + wh.getName());
                //	Set Org
                move.setOrgId(whSource.getOrgId());
                if (!move.save()) return;
                addBufferLog(
                        move.getMovementId(),
                        move.getMovementDate(),
                        null,
                        Msg.parseTranslation("@M_Movement_ID@ @Created@"),
                        MMovement.Table_ID,
                        move.getMovementId());
                if (log.isLoggable(Level.FINE)) log.fine(move.toString());
                noMoves++;
                info.append(" - ").append(move.getDocumentNo());
            }
            //	To
            int M_LocatorTo_ID = wh.getDefaultLocator().getLocatorId();
            //	From: Look-up Storage
            MProduct product = MProduct.get(replenish.getProductId());
            String MMPolicy = product.getMMPolicy();
            MStorageOnHand[] storages =
                    MStorageOnHand.getWarehouse(
                            whSource.getWarehouseId(),
                            replenish.getProductId(),
                            0,
                            null,
                            MClient.MMPOLICY_FiFo.equals(MMPolicy),
                            false,
                            0,
                            null);
            //
            BigDecimal target = replenish.getQtyToOrder();
            for (int j = 0; j < storages.length; j++) {
                MStorageOnHand storage = storages[j];
                if (storage.getQtyOnHand().signum() <= 0) continue;

                /* IDEMPIERE-2668 - filter just locators enabled for replenishment */
                MLocator loc = MLocator.get(storage.getLocatorId());
                MLocatorType lt = null;
                if (loc.getLocatorTypeId() > 0)
                    lt = MLocatorType.get(loc.getLocatorTypeId());
                if (lt != null && !lt.isAvailableForReplenishment()) continue;

                BigDecimal moveQty = target;
                if (storage.getQtyOnHand().compareTo(moveQty) < 0) moveQty = storage.getQtyOnHand();
                //
                MMovementLine line = new MMovementLine(move);
                line.setProductId(replenish.getProductId());
                line.setMovementQty(moveQty);
                if (replenish.getQtyToOrder().compareTo(moveQty) != 0)
                    line.setDescription("Total: " + replenish.getQtyToOrder());
                line.setLocatorId(storage.getLocatorId()); // 	from
                line.setAttributeSetInstanceId(storage.getAttributeSetInstanceId());
                line.setLocatorToId(M_LocatorTo_ID); // 	to
                line.setAttributeSetInstanceToId(storage.getAttributeSetInstanceId());
                line.saveEx();
                //
                target = target.subtract(moveQty);
                if (target.signum() == 0) break;
            }
        }
        if (replenishs.length == 0) {
            m_info = new StringBuffer("No Source Warehouse");
            log.warning(m_info.toString());
        } else {
            m_info = new StringBuffer("#").append(noMoves).append(info);
            if (log.isLoggable(Level.INFO)) log.info(m_info.toString());
        }
    } //	Create Inventory Movements

    /**
     * Create Distribution Order
     */
    private void createDO() throws Exception {
        int noMoves = 0;
        StringBuilder info = new StringBuilder();
        //
        ClientWithAccounting client = null;
        MDDOrder order = null;
        int M_Warehouse_ID = 0;
        int M_WarehouseSource_ID = 0;
        MWarehouse whSource = null;
        MWarehouse wh = null;
        X_T_Replenish[] replenishs = getReplenishDO("M_WarehouseSource_ID IS NOT NULL");
        for (X_T_Replenish replenish : replenishs) {
            if (whSource == null
                    || whSource.getWarehouseSourceId() != replenish.getWarehouseSourceId())
                whSource = MWarehouse.get(replenish.getWarehouseSourceId());
            if (wh == null || wh.getWarehouseId() != replenish.getWarehouseId())
                wh = MWarehouse.get(replenish.getWarehouseId());
            if (client == null || client.getClientId() != whSource.getClientId())
                client = MClientKt.getClientWithAccounting(whSource.getClientId());
            //
            if (order == null
                    || M_WarehouseSource_ID != replenish.getWarehouseSourceId()
                    || M_Warehouse_ID != replenish.getWarehouseId()) {
                M_WarehouseSource_ID = replenish.getWarehouseSourceId();
                M_Warehouse_ID = replenish.getWarehouseId();

                order = new MDDOrder(0);
                order.setDocumentTypeId(p_C_DocType_ID);
                String msgsd = Msg.getMsg("Replenishment") +
                        ": " +
                        whSource.getName() +
                        "->" +
                        wh.getName();
                order.setDescription(msgsd);
                //	Set Org
                order.setOrgId(whSource.getOrgId());
                // Set Org Trx
                MOrg orgTrx = MOrgKt.getOrg(wh.getOrgId());
                order.setTransactionOrganizationId(orgTrx.getOrgId());
                int C_BPartner_ID = orgTrx.getLinkedBusinessPartnerId();
                if (C_BPartner_ID == 0)
                    throw new AdempiereUserError(
                            Msg.translate("C_BPartner_ID") + " @FillMandatory@ ");
                MBPartner bp = new MBPartner(C_BPartner_ID);
                // Set BPartner Link to Org
                order.setBPartner(bp);
                order.setDateOrdered(new Timestamp(System.currentTimeMillis()));
                // order.setDatePromised(DatePromised);
                order.setDeliveryRule(MDDOrder.DELIVERYRULE_Availability);
                order.setDeliveryViaRule(MDDOrder.DELIVERYVIARULE_Delivery);
                order.setPriorityRule(MDDOrder.PRIORITYRULE_Medium);
                order.setIsInDispute(false);
                order.setIsApproved(false);
                order.setIsDropShip(false);
                order.setIsDelivered(false);
                order.setIsInTransit(false);
                order.setIsPrinted(false);
                order.setIsSelected(false);
                order.setIsSOTrx(false);
                // Warehouse in Transit
                MWarehouse[] whsInTransit = MWarehouse.getForOrg(whSource.getOrgId());
                for (MWarehouse whInTransit : whsInTransit) {
                    if (whInTransit.isInTransit()) order.setWarehouseId(whInTransit.getWarehouseId());
                }
                if (order.getWarehouseId() == 0)
                    throw new AdempiereUserError("Warehouse inTransit is @FillMandatory@ ");

                if (!order.save()) return;
                addBufferLog(
                        order.getDistributionOrderId(),
                        order.getDateOrdered(),
                        null,
                        Msg.parseTranslation("@DD_Order_ID@ @Created@"),
                        MDDOrder.Table_ID,
                        order.getDistributionOrderId());
                if (log.isLoggable(Level.FINE)) log.fine(order.toString());
                noMoves++;
                info.append(" - ").append(order.getDocumentNo());
            }

            //	To
            int M_LocatorTo_ID = wh.getDefaultLocator().getLocatorId();
            int M_Locator_ID = whSource.getDefaultLocator().getLocatorId();
            if (M_LocatorTo_ID == 0 || M_Locator_ID == 0)
                throw new AdempiereUserError(Msg.translate("M_Locator_ID") + " @FillMandatory@ ");

            //	From: Look-up Storage
      /*MProduct product = MProduct.get(replenish.getProductId());
      MProductCategory pc = MProductCategory.get(product.getProductCategoryId());
      String MMPolicy = pc.getMMPolicy();
      if (MMPolicy == null || MMPolicy.length() == 0)
      	MMPolicy = client.getMMPolicy();
      //
      MStorage[] storages = MStorage.getWarehouse(
      	whSource.getWarehouseId(), replenish.getProductId(), 0, 0,
      	true, null,
      	MClient.MMPOLICY_FiFo.equals(MMPolicy), null);


      BigDecimal target = replenish.getQtyToOrder();
      for (int j = 0; j < storages.length; j++)
      {
      	MStorage storage = storages[j];
      	if (storage.getQtyOnHand().signum() <= 0)
      		continue;
      	BigDecimal moveQty = target;
      	if (storage.getQtyOnHand().compareTo(moveQty) < 0)
      		moveQty = storage.getQtyOnHand();
      	//
      	MDDOrderLine line = new MDDOrderLine(order);
      	line.setProductId(replenish.getProductId());
      	line.setQtyEntered(moveQty);
      	if (replenish.getQtyToOrder().compareTo(moveQty) != 0)
      		line.setDescription("Total: " + replenish.getQtyToOrder());
      	line.setLocatorId(storage.getLocatorId());		//	from
      	line.setAttributeSetInstanceId(storage.getAttributeSetInstanceId());
      	line.setLocatorToId(M_LocatorTo_ID);					//	to
      	line.setAttributeSetInstanceToId(storage.getAttributeSetInstanceId());
      	line.setIsInvoiced(false);
      	line.saveEx();
      	//
      	target = target.subtract(moveQty);
      	if (target.signum() == 0)
      		break;
      }*/

            MDDOrderLine line = new MDDOrderLine(order);
            line.setProductId(replenish.getProductId());
            line.setQty(replenish.getQtyToOrder());
            if (replenish.getQtyToOrder().compareTo(replenish.getQtyToOrder()) != 0)
                line.setDescription("Total: " + replenish.getQtyToOrder());
            line.setLocatorId(M_Locator_ID); // 	from
            line.setAttributeSetInstanceId(0);
            line.setLocatorToId(M_LocatorTo_ID); // 	to
            line.setAttributeSetInstanceToId(0);
            line.setIsInvoiced(false);
            line.saveEx();
        }
        if (replenishs.length == 0) {
            m_info = new StringBuffer("No Source Warehouse");
            log.warning(m_info.toString());
        } else {
            m_info = new StringBuffer("#").append(noMoves).append(info);
            if (log.isLoggable(Level.INFO)) log.info(m_info.toString());
        }
    } //	create Distribution Order

    /**
     * Get Replenish Records
     *
     * @return replenish
     */
    private X_T_Replenish[] getReplenish(String where) {
        return BaseReplenishReportKt.getReplenishRecords(where, getProcessInstanceId());
    } //	getReplenish

    /**
     * Get Replenish Records
     *
     * @return replenish
     */
    private X_T_Replenish[] getReplenishDO(String where) {
        return BaseReplenishReportKt.getReplenishDO(where, getProcessInstanceId());
    } //	getReplenish
} //	Replenish
