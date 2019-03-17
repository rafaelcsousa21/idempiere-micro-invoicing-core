package org.compiere.accounting

import org.compiere.bo.MCurrency
import org.compiere.tax.MTax
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.math.BigDecimal
import java.util.Properties
import java.util.logging.Level
import kotlin.collections.HashMap
import kotlin.collections.filterNotNull
import kotlin.collections.indices
import kotlin.collections.listOf
import kotlin.collections.map
import kotlin.collections.set
import kotlin.collections.toTypedArray

/**
 * Get Commitments Sales
 *
 * @param doc document
 * @param maxQuantity Qty invoiced/matched
 * @return commitments (order lines)
 */
fun getCommitmentsSales(doc: Doc, maxQuantity: BigDecimal, M_InOutLine_ID: Int): Array<DocLine> {
    var precision = -1
    var maxQty = maxQuantity

    val sql = StringBuilder("SELECT * FROM C_OrderLine ol ")
        .append("WHERE EXISTS ")
        .append("(SELECT * FROM M_InOutLine il ")
        .append("WHERE il.C_OrderLine_ID=ol.C_OrderLine_ID")
        .append(" AND il.M_InOutLine_ID=?)")

    val query = queryOf(sql.toString(), listOf(M_InOutLine_ID)).map { row -> MOrderLine(doc.ctx, row) }.asList
    val items = DB.current.run(query)

    return items.map { line ->

        val docLine = DocLine(line, doc)
        // 	Currency
        if (precision == -1) {
            doc.currencyId = docLine.currencyId
            precision = MCurrency.getStdPrecision(doc.ctx, docLine.currencyId)
        }
        // 	Qty
        val qty = line.qtyOrdered.max(maxQty)
        docLine.setQty(qty, false)
        //
        val priceActual = line.priceActual
        val priceCost = line.priceCost
        var LineNetAmt: BigDecimal?
        if (priceCost != null && priceCost.signum() != 0)
            LineNetAmt = qty.multiply(priceCost)
        else if (qty == maxQty)
            LineNetAmt = line.getLineNetAmt()
        else
            LineNetAmt = qty.multiply(priceActual)
        maxQty = maxQty.subtract(qty)

        docLine.setAmount(LineNetAmt) // 	DR
        var PriceList = line.getPriceList()
        val C_Tax_ID = docLine.taxId
        // 	Correct included Tax
        if (C_Tax_ID != 0 && line.getParent().isTaxIncluded()) {
            val tax = MTax.get(doc.ctx, C_Tax_ID)
            if (!tax.isZeroTax) {
                val LineNetAmtTax = tax.calculateTax(LineNetAmt, true, precision)
                if (Doc.s_log.isLoggable(Level.FINE))
                    Doc.s_log.fine("LineNetAmt=$LineNetAmt - Tax=$LineNetAmtTax")
                LineNetAmt = LineNetAmt!!.subtract(LineNetAmtTax)
                val PriceListTax = tax.calculateTax(PriceList, true, precision)
                PriceList = PriceList.subtract(PriceListTax)
            }
        } // 	correct included Tax

        docLine.setAmount(LineNetAmt, PriceList, qty)
        docLine
    }.toTypedArray()
} // 	getCommitmentsSales

/**
 * Load Requisitions
 *
 * @return requisition lines of Order
 */
fun loadRequisitions(ctx: Properties, order: MOrder, docOrder: Doc_Order): Array<DocLine> {
    val oLines = order.lines
    val qtys = HashMap<Int, BigDecimal>()
    for (i in oLines.indices) {
        val line = oLines[i]
        qtys[line.orderLineId] = line.qtyOrdered
    }
    //
    val sql = ("SELECT * FROM M_RequisitionLine rl " +
            "WHERE EXISTS (SELECT * FROM C_Order o " +
            " INNER JOIN C_OrderLine ol ON (o.C_Order_ID=ol.C_Order_ID) " +
            "WHERE ol.C_OrderLine_ID=rl.C_OrderLine_ID" +
            " AND o.C_Order_ID=?) " +
            "ORDER BY rl.C_OrderLine_ID")

    val query = queryOf(sql.toString(), listOf(order.orderId)).map { row -> MRequisitionLine(ctx, row) }.asList
    val items = DB.current.run(query)
    return items.map { line ->
        val docLine = DocLine(line, docOrder)
        // 	Quantity - not more then OrderLine
        // 	Issue: Split of Requisition to multiple POs & different price
        val key = line.getOrderLineId()
        val maxQty = qtys[key]
        val Qty = line.getQty().max(maxQty)
        if (Qty.signum() != 0) {
            docLine.setQty(Qty, false)
            qtys[key] = maxQty!!.subtract(Qty)
            //
            val PriceActual = line.getPriceActual()
            var LineNetAmt = line.getLineNetAmt()
            if (line.getQty().compareTo(Qty) != 0) LineNetAmt = PriceActual.multiply(Qty)
            docLine.setAmount(LineNetAmt) // DR
            docLine
        } else null
    }.filterNotNull().toTypedArray()
} // loadRequisitions

/**
 * Get Commitments
 *
 * @param doc document
 * @param QtyInvoicedOrMatched Qty invoiced/matched
 * @param C_InvoiceLine_ID invoice line
 * @return commitments (order lines)
 */
fun getCommitments(doc: Doc, QtyInvoicedOrMatched: BigDecimal, C_InvoiceLine_ID: Int): Array<DocLine> {
    var maxQty = QtyInvoicedOrMatched
    var precision = -1
    //
    val sql = StringBuilder("SELECT * FROM C_OrderLine ol ")
        .append("WHERE EXISTS ")
        .append("(SELECT * FROM C_InvoiceLine il ")
        .append("WHERE il.C_OrderLine_ID=ol.C_OrderLine_ID")
        .append(" AND il.C_InvoiceLine_ID=?)")
        .append(" OR EXISTS ")
        .append("(SELECT * FROM M_MatchPO po ")
        .append("WHERE po.C_OrderLine_ID=ol.C_OrderLine_ID")
        .append(" AND po.C_InvoiceLine_ID=?)")

    val query =
        queryOf(sql.toString(), listOf(C_InvoiceLine_ID, C_InvoiceLine_ID)).map { row -> MOrderLine(doc.getCtx(), row) }
            .asList
    val items = DB.current.run(query)
    return items.map { line ->
        val docLine = DocLine(line, doc)
        // 	Currency
        if (precision == -1) {
            doc.currencyId = docLine.currencyId
            precision = MCurrency.getStdPrecision(doc.ctx, docLine.currencyId)
        }
        // 	Qty
        val Qty = line.getQtyOrdered().max(maxQty)
        docLine.setQty(Qty, false)
        //
        val PriceActual = line.getPriceActual()
        val PriceCost = line.getPriceCost()
        var LineNetAmt: BigDecimal?
        if (PriceCost != null && PriceCost.signum() != 0)
            LineNetAmt = Qty.multiply(PriceCost)
        else if (Qty == maxQty)
            LineNetAmt = line.getLineNetAmt()
        else
            LineNetAmt = Qty.multiply(PriceActual)
        maxQty = maxQty.subtract(Qty)

        docLine.setAmount(LineNetAmt) // 	DR
        var PriceList = line.getPriceList()
        val C_Tax_ID = docLine.taxId
        // 	Correct included Tax
        if (C_Tax_ID != 0 && line.getParent().isTaxIncluded()) {
            val tax = MTax.get(doc.ctx, C_Tax_ID)
            if (!tax.isZeroTax) {
                val LineNetAmtTax = tax.calculateTax(LineNetAmt, true, precision)
                if (Doc.s_log.isLoggable(Level.FINE))
                    Doc.s_log.fine("LineNetAmt=$LineNetAmt - Tax=$LineNetAmtTax")
                LineNetAmt = LineNetAmt!!.subtract(LineNetAmtTax)
                val PriceListTax = tax.calculateTax(PriceList, true, precision)
                PriceList = PriceList.subtract(PriceListTax)
            }
        } // 	correct included Tax

        docLine.setAmount(LineNetAmt, PriceList, Qty)
        docLine
    }.toTypedArray()
} // 	getCommitments