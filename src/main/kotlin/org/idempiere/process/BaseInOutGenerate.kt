package org.idempiere.process

import kotliquery.Row
import org.compiere.accounting.MClient
import org.compiere.accounting.MOrder
import org.compiere.accounting.MOrderLine
import org.compiere.accounting.MStorageOnHand
import org.compiere.invoicing.MInOut
import org.compiere.invoicing.MInOutLine
import org.compiere.process.SvrProcess
import org.idempiere.common.util.AdempiereUserError
import org.idempiere.common.util.Env
import software.hsharp.core.util.DB
import software.hsharp.core.util.TO_DATE
import software.hsharp.core.util.queryOf
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.logging.Level

/**
 * Base for Shipments Generation. Manual or Automatic
 *
 */
abstract class BaseInOutGenerate : SvrProcess() {
    /**
     * Warehouse
     */
    protected var p_M_Warehouse_ID = 0

    /**
     * Manual Selection
     */
    protected var p_Selection = false

    /**
     * Promise Date
     */
    protected var p_DatePromised: Timestamp? = null

    /**
     * BPartner
     */
    protected var p_C_BPartner_ID = 0

    /**
     * Consolidate
     */
    protected var p_ConsolidateDocument = true

    /**
     * The current Shipment
     */
    protected var m_shipment: MInOut? = null

    /**
     * Movement Date
     */
    protected var m_movementDate: Timestamp? = null

    protected abstract fun completeShipment()

    /**
     * Include Orders w. unconfirmed Shipments
     */
    protected var p_IsUnconfirmedInOut = false

    protected abstract fun createLine(
        order: MOrder,
        orderLine: MOrderLine,
        qty: BigDecimal,
        storages: Array<MStorageOnHand>?,
        force: Boolean
    )

    protected abstract fun getStorages(
        M_Warehouse_ID: Int,
        M_Product_ID: Int,
        M_AttributeSetInstance_ID: Int,
        minGuaranteeDate: Timestamp?,
        FiFo: Boolean
    ): Array<MStorageOnHand>

    /**
     * Line Number
     */
    protected var m_line = 0

    /**
     * Number of Shipments being created
     */
    protected var m_created = 0

    /**
     * Generate Shipments
     *
     * @param pstmt Order Query
     * @return info
     */
    private fun generate(sql: String, parameters: List<Any?>): String {
        fun load(row: Row): Int {
            val order = MOrder(ctx, row)

            val shipment = m_shipment

            // 	New Header different Shipper, Shipment Location
            if (!p_ConsolidateDocument || shipment != null && (shipment.getBusinessPartnerLocationId() != order.getBusinessPartnerLocationId() || shipment.getShipperId() != order.getShipperId()))
                completeShipment()
            if (log.isLoggable(Level.FINE))
                log.fine("check: " + order + " - DeliveryRule=" + order.getDeliveryRule())
            //
            val minGuaranteeDate = m_movementDate
            var completeOrder = MOrder.DELIVERYRULE_CompleteOrder == order.getDeliveryRule()
            // 	OrderLine WHERE
            val where = StringBuilder(" AND M_Warehouse_ID=").append(p_M_Warehouse_ID)
            if (p_DatePromised != null)
                where
                    .append(" AND (TRUNC(DatePromised)<=")
                    .append(TO_DATE(p_DatePromised, true))
                    .append(" OR DatePromised IS NULL)")
            // 	Exclude Auto Delivery if not Force
            if (MOrder.DELIVERYRULE_Force != order.getDeliveryRule())
                where
                    .append(" AND (C_OrderLine.M_Product_ID IS NULL")
                    .append(" OR EXISTS (SELECT * FROM M_Product p ")
                    .append("WHERE C_OrderLine.M_Product_ID=p.M_Product_ID")
                    .append(" AND IsExcludeAutoDelivery='N'))")
            // 	Exclude Unconfirmed
            if (!p_IsUnconfirmedInOut)
                where
                    .append(" AND NOT EXISTS (SELECT * FROM M_InOutLine iol")
                    .append(" INNER JOIN M_InOut io ON (iol.M_InOut_ID=io.M_InOut_ID) ")
                    .append(
                        "WHERE iol.C_OrderLine_ID=C_OrderLine.C_OrderLine_ID AND io.DocStatus IN ('DR','IN','IP','WC'))"
                    )
            // 	Deadlock Prevention - Order by M_Product_ID
            val lines = order.getLines(where.toString(), "C_BPartner_Location_ID, M_Product_ID")
            for (i in lines.indices) {
                val line = lines[i]
                if (line.getWarehouseId() != p_M_Warehouse_ID) continue
                if (log.isLoggable(Level.FINE)) log.fine("check: $line")
                var onHand = Env.ZERO
                var toDeliver = line.getQtyOrdered().subtract(line.getQtyDelivered())
                val product = line.getProduct()
                // 	Nothing to Deliver
                if (product != null && toDeliver.signum() == 0) continue

                // or it's a charge - Bug#: 1603966
                if (line.getChargeId() != 0 && toDeliver.signum() == 0) continue

                // 	Check / adjust for confirmations
                var unconfirmedShippedQty = Env.ZERO
                if (p_IsUnconfirmedInOut && product != null && toDeliver.signum() != 0) {
                    val where2 =
                        "EXISTS (SELECT * FROM M_InOut io WHERE io.M_InOut_ID=M_InOutLine.M_InOut_ID AND io.DocStatus IN ('DR','IN','IP','WC'))"
                    val iols = MInOutLine.getOfOrderLine(ctx, line.getC_OrderLine_ID(), where2)
                    for (j in iols.indices)
                        unconfirmedShippedQty = unconfirmedShippedQty.add(iols[j].movementQty)
                    val logInfo = StringBuilder("Unconfirmed Qty=")
                        .append(unconfirmedShippedQty)
                        .append(" - ToDeliver=")
                        .append(toDeliver)
                        .append("->")
                    toDeliver = toDeliver.subtract(unconfirmedShippedQty)
                    logInfo.append(toDeliver)
                    if (toDeliver.signum() < 0) {
                        toDeliver = Env.ZERO
                        logInfo.append(" (set to 0)")
                    }
                    // 	Adjust On Hand
                    onHand = onHand.subtract(unconfirmedShippedQty)
                    if (log.isLoggable(Level.FINE)) log.fine(logInfo.toString())
                }

                // 	Comments & lines w/o product & services
                if ((product == null || !product!!.isStocked()) && (line.getQtyOrdered().signum() == 0 || // 	comments
                            toDeliver.signum() != 0)
                )
                // 	lines w/o product
                {
                    if (MOrder.DELIVERYRULE_CompleteOrder != order.getDeliveryRule())
                    // 	printed later
                        createLine(order, line, toDeliver, null, false)
                    continue
                }

                // 	Stored Product
                val MMPolicy = product!!.getMMPolicy()

                val storages = getStorages(
                    line.getWarehouseId(),
                    line.getM_Product_ID(),
                    line.getMAttributeSetInstance_ID(),
                    minGuaranteeDate,
                    MClient.MMPOLICY_FiFo == MMPolicy
                )

                for (j in storages.indices) {
                    val storage = storages[j]
                    onHand = onHand.add(storage.getQtyOnHand())
                }
                val fullLine = onHand.compareTo(toDeliver) >= 0 || toDeliver.signum() < 0

                // 	Complete Order
                if (completeOrder && !fullLine) {
                    if (log.isLoggable(Level.FINE))
                        log.fine(
                            "Failed CompleteOrder - OnHand=" +
                                    onHand +
                                    " (Unconfirmed=" +
                                    unconfirmedShippedQty +
                                    "), ToDeliver=" +
                                    toDeliver +
                                    " - " +
                                    line
                        )
                    completeOrder = false
                    break
                } else if (fullLine && MOrder.DELIVERYRULE_CompleteLine == order.getDeliveryRule()) {
                    if (log.isLoggable(Level.FINE))
                        log.fine(
                            ("CompleteLine - OnHand=" +
                                    onHand +
                                    " (Unconfirmed=" +
                                    unconfirmedShippedQty +
                                    ", ToDeliver=" +
                                    toDeliver +
                                    " - " +
                                    line)
                        )
                    //
                    createLine(order, line, toDeliver, storages, false)
                } else if ((MOrder.DELIVERYRULE_Availability == order.getDeliveryRule() && (onHand.signum() > 0 || toDeliver.signum() < 0))) {
                    var deliver = toDeliver
                    if (deliver.compareTo(onHand) > 0) deliver = onHand
                    if (log.isLoggable(Level.FINE))
                        log.fine(
                            ("Available - OnHand=" +
                                    onHand +
                                    " (Unconfirmed=" +
                                    unconfirmedShippedQty +
                                    "), ToDeliver=" +
                                    toDeliver +
                                    ", Delivering=" +
                                    deliver +
                                    " - " +
                                    line)
                        )
                    //
                    createLine(order, line, deliver, storages, false)
                } else if (MOrder.DELIVERYRULE_Force == order.getDeliveryRule()) {
                    val deliver = toDeliver
                    if (log.isLoggable(Level.FINE))
                        log.fine(
                            ("Force - OnHand=" +
                                    onHand +
                                    " (Unconfirmed=" +
                                    unconfirmedShippedQty +
                                    "), ToDeliver=" +
                                    toDeliver +
                                    ", Delivering=" +
                                    deliver +
                                    " - " +
                                    line)
                        )
                    //
                    createLine(order, line, deliver, storages, true)
                } else if (MOrder.DELIVERYRULE_Manual == order.getDeliveryRule()) {
                    if (log.isLoggable(Level.FINE))
                        log.fine(
                            ("Manual - OnHand=" +
                                    onHand +
                                    " (Unconfirmed=" +
                                    unconfirmedShippedQty +
                                    ") - " +
                                    line)
                        )
                } else {
                    if (log.isLoggable(Level.FINE))
                        log.fine(
                            ("Failed: " +
                                    order.getDeliveryRule() +
                                    " - OnHand=" +
                                    onHand +
                                    " (Unconfirmed=" +
                                    unconfirmedShippedQty +
                                    "), ToDeliver=" +
                                    toDeliver +
                                    " - " +
                                    line)
                        )
                } // 	Manual
                // 	Force
                // 	Availability
                // 	Complete Line
            } // 	for all order lines

            // 	Complete Order successful
            if (completeOrder && MOrder.DELIVERYRULE_CompleteOrder == order.getDeliveryRule()) {
                for (i in lines.indices) {
                    val line = lines[i]
                    if (line.getWarehouseId() != p_M_Warehouse_ID) continue
                    val product = line.getProduct()
                    val toDeliver = line.getQtyOrdered().subtract(line.getQtyDelivered())
                    //
                    var storages: Array<MStorageOnHand>? = null
                    if (product != null && product!!.isStocked()) {
                        val MMPolicy = product!!.getMMPolicy()
                        storages = getStorages(
                            line.getWarehouseId(),
                            line.getM_Product_ID(),
                            line.getMAttributeSetInstance_ID(),
                            minGuaranteeDate,
                            MClient.MMPOLICY_FiFo == MMPolicy
                        )
                    }
                    //
                    createLine(order, line, toDeliver, storages, false)
                }
            }
            m_line += 1000

            return 0
        }

        val query = queryOf(sql, parameters).map { row -> load(row) }.asList
        DB.current.run(query).min()

        completeShipment()
        val msgreturn = StringBuilder("@Created@ = ").append(m_created)
        return msgreturn.toString()
    } // 	generate

    /**
     * Generate Shipments
     *
     * @return info
     * @throws Exception
     */
    override fun doIt(): String {
        if (p_M_Warehouse_ID == 0) throw AdempiereUserError("@NotFound@ @M_Warehouse_ID@")
        val m_sql: StringBuffer

        if (p_Selection)
        // 	VInOutGen
        {
            m_sql = StringBuffer("SELECT C_Order.* FROM C_Order, T_Selection ")
                .append(
                    "WHERE C_Order.DocStatus='CO' AND C_Order.IsSOTrx='Y' AND C_Order.AD_Client_ID=? "
                )
                .append("AND C_Order.C_Order_ID = T_Selection.T_Selection_ID ")
                .append("AND T_Selection.AD_PInstance_ID=? ")
        } else {
            m_sql = StringBuffer("SELECT * FROM C_Order o ")
                .append("WHERE DocStatus='CO' AND IsSOTrx='Y'")
                // 	No Offer,POS
                .append(" AND o.C_DocType_ID IN (SELECT C_DocType_ID FROM C_DocType ")
                .append("WHERE DocBaseType='SOO' AND DocSubTypeSO NOT IN ('ON','OB','WR'))")
                .append("	AND o.IsDropShip='N'")
                // 	No Manual
                .append(" AND o.DeliveryRule<>'M'")
                // 	Open Order Lines with Warehouse
                .append(" AND EXISTS (SELECT * FROM C_OrderLine ol ")
                .append("WHERE ol.M_Warehouse_ID=?") // 	#1
            if (p_DatePromised != null) m_sql.append(" AND TRUNC(ol.DatePromised)<=?") // 	#2
            m_sql.append(" AND o.C_Order_ID=ol.C_Order_ID AND ol.QtyOrdered<>ol.QtyDelivered)")
            //
            if (p_C_BPartner_ID != 0) m_sql.append(" AND o.C_BPartner_ID=?") // 	#3
        }
        m_sql.append(
            " ORDER BY M_Warehouse_ID, PriorityRule, M_Shipper_ID, C_BPartner_ID, C_BPartner_Location_ID, C_Order_ID"
        )
        // 	m_sql += " FOR UPDATE";

        val parameters =
            if (p_Selection) {
                listOf(Env.getClientId(ctx), aD_PInstance_ID)
            } else {
                listOf(p_M_Warehouse_ID) +
                        (if (p_DatePromised != null) listOf(p_DatePromised) else emptyList()) +
                        (if (p_C_BPartner_ID != 0) listOf(p_C_BPartner_ID) else emptyList())
            }

        return generate(m_sql.toString(), parameters)
    } // 	doIt
}