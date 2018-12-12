package org.idempiere.process

import org.compiere.accounting.MOrder
import org.compiere.accounting.MOrderLine
import org.compiere.accounting.MProduct
import org.compiere.accounting.MWarehouse
import org.compiere.model.I_C_Order
import org.compiere.model.I_M_Production
import org.compiere.orm.MDocType
import org.compiere.process.SvrProcess
import org.compiere.production.MProduction
import org.compiere.util.Msg
import org.idempiere.common.util.Env
import software.hsharp.core.util.getSQLValueString
import java.sql.Timestamp
import java.util.logging.Level

/**
 * Create (Generate) Invoice from Shipment
 *
 * @author Jorg Janke
 * @version $Id: OrderLineCreateProduction.java,v 1.1 2007/07/23 05:34:35 mfuggle Exp $
 */
class OrderLineCreateProduction(
    var p_C_OrderLine_ID: Int = 0,
    var p_MovementDate: Timestamp? = null,
    var ignorePrevProduction: Boolean = false
) : SvrProcess() {
    /**	Shipment					 */

    /**
     * Prepare - e.g., get Parameters.
     */
    override fun prepare() {
        val para = parameter
        for (i in para.indices) {
            val name = para[i].parameterName
            if (para[i].parameter == null)

            if (name == "MovementDate")
                p_MovementDate = para[i].parameter as Timestamp
            else if (name == "IgnorePrevProduction")
                ignorePrevProduction = "Y" == para[i].parameter
            else
                log.log(Level.SEVERE, "Unknown Parameter: $name")
        }

        if (p_MovementDate == null)
            p_MovementDate = Env.getContextAsDate(ctx, "#Date")
        if (p_MovementDate == null)
            p_MovementDate = Timestamp(System.currentTimeMillis())

        if (p_C_OrderLine_ID == 0) p_C_OrderLine_ID = record_ID
    } // 	prepare

    /**
     * Create Production Header and Plan for single ordered product
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun doIt(): String {
        if (log.isLoggable(Level.INFO)) log.info("C_OrderLine_ID=$p_C_OrderLine_ID")
        if (p_C_OrderLine_ID == 0)
            throw IllegalArgumentException("No OrderLine")
        //
        val line = MOrderLine(ctx, p_C_OrderLine_ID, _TrxName)
        if (line.id == 0)
            throw IllegalArgumentException("Order line not found")
        val order: I_C_Order = MOrder(ctx, line.c_Order_ID, _TrxName)
        if (MOrder.DOCSTATUS_Completed != order.docStatus)
            throw IllegalArgumentException("Order not completed")

        val doc = MDocType(ctx, order.c_DocType_ID, _TrxName)

        if (line.qtyOrdered.subtract(line.qtyDelivered).compareTo(Env.ZERO) <= 0) {
            if (doc.docSubTypeSO != "ON")
            // Consignment and stock orders both have subtype of ON
            {
                return "Ordered quantity already shipped"
            }
        }

        // If we don't ignore previous production, and there has been a previous one,
        // throw an exception
        if (!ignorePrevProduction) {
            val docNo = getSQLValueString(_TrxName,
                    "SELECT max(DocumentNo) " + "FROM M_Production WHERE C_OrderLine_ID = ?",
                    p_C_OrderLine_ID)
            if (docNo != null) {
                throw IllegalArgumentException("Production has already been created: $docNo")
            }
        }

        val production: I_M_Production = MProduction(line)
        val product = MProduct(ctx, line.m_Product_ID, _TrxName)

        production.m_Product_ID = line.m_Product_ID
        production.productionQty = line.qtyOrdered.subtract(line.qtyDelivered)
        production.datePromised = line.datePromised
        if (product.m_Locator_ID > 0)
            production.m_Locator_ID = product.m_Locator_ID
        production.c_OrderLine_ID = p_C_OrderLine_ID

        var locator = product.m_Locator_ID
        if (locator == 0)
            locator = MWarehouse.get(ctx, line.m_Warehouse_ID).defaultLocator!!.id
        production.m_Locator_ID = locator

        if (line.c_BPartner_ID > 0) {
            production.c_BPartner_ID = order.c_BPartner_ID
        }

        if (line.c_Project_ID > 0) {
            production.c_Project_ID = line.c_Project_ID
        } else {
            production.c_Project_ID = order.c_Project_ID
        }

        if (line.c_Campaign_ID > 0) {
            production.c_Campaign_ID = line.c_Campaign_ID
        } else {
            production.c_Campaign_ID = order.c_Campaign_ID
        }

        if (line.c_Activity_ID > 0) {
            production.c_Activity_ID = line.c_Activity_ID
        } else {
            production.c_Activity_ID = order.c_Activity_ID
        }

        if (line.user1_ID > 0) {
            production.user1_ID = line.user1_ID
        } else {
            production.user1_ID = order.user1_ID
        }

        if (line.user2_ID > 0) {
            production.user2_ID = line.user2_ID
        } else {
            production.user2_ID = order.user2_ID
        }

        if (line.aD_OrgTrx_ID > 0) {
            production.aD_OrgTrx_ID = line.aD_OrgTrx_ID
        } else {
            production.aD_OrgTrx_ID = order.aD_OrgTrx_ID
        }

        production.saveEx()

        production.createLines(false)
        production.isCreated = "Y"
        production.saveEx()

        val msg = Msg.parseTranslation(ctx, "@M_Production_ID@ @Created@ " + production.getDocumentNo())
        addLog(production.m_Production_ID, null, null, msg, MProduction.Table_ID, production.m_Production_ID)
        return "@OK@"
    } // 	OrderLineCreateShipment
} // 	OrderLineCreateShipment
