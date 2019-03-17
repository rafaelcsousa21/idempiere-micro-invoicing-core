package org.compiere.accounting

import org.compiere.model.I_C_InvoiceLine
import org.compiere.order.MInOutLine
import org.idempiere.common.util.CLogger
import software.hsharp.core.util.DB
import software.hsharp.core.util.getSQLValue
import software.hsharp.core.util.getSQLValueBD
import software.hsharp.core.util.queryOf
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.Properties

/**
 * Get PO Match of Receipt Line
 *
 * @param ctx context
 * @param M_InOutLine_ID receipt
 * @return array of matches
 */
internal fun getPOMatchOfReceiptLine(ctx: Properties, M_InOutLine_ID: Int): Array<MMatchPO> {
    if (M_InOutLine_ID == 0) return emptyArray()
    //
    val sql = "SELECT * FROM M_MatchPO WHERE M_InOutLine_ID=?"
    val query = queryOf(sql, listOf(M_InOutLine_ID)).map { row -> MMatchPO(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	get

/**
 * Get PO Matches of receipt
 *
 * @param ctx context
 * @param M_InOut_ID receipt
 * @return array of matches
 */
internal fun getPOMatchesOfReceipt(ctx: Properties, M_InOut_ID: Int): Array<MMatchPO> {
    if (M_InOut_ID == 0) return emptyArray()
    //
    val sql = ("SELECT * FROM M_MatchPO m" +
            " INNER JOIN M_InOutLine l ON (m.M_InOutLine_ID=l.M_InOutLine_ID) " +
            "WHERE l.M_InOut_ID=?")
    val query = queryOf(sql, listOf(M_InOut_ID)).map { row -> MMatchPO(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getInOut

/**
 * Get PO Matches of Invoice
 *
 * @param ctx context
 * @param C_Invoice_ID invoice
 * @return array of matches
 */
internal fun getPOMatchesOfInvoice(ctx: Properties, C_Invoice_ID: Int): Array<MMatchPO> {
    if (C_Invoice_ID == 0) return emptyArray()
    //
    val sql = ("SELECT * FROM M_MatchPO mi" +
            " INNER JOIN C_InvoiceLine il ON (mi.C_InvoiceLine_ID=il.C_InvoiceLine_ID) " +
            "WHERE il.C_Invoice_ID=?")
    val query = queryOf(sql, listOf(C_Invoice_ID)).map { row -> MMatchPO(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getInvoice

/**
 * Get PO Matches for OrderLine
 *
 * @param ctx context
 * @param C_OrderLine_ID order
 * @return array of matches
 */
internal fun getPOMatchesForOrderLine(ctx: Properties, C_OrderLine_ID: Int): Array<MMatchPO> {
    if (C_OrderLine_ID == 0) return emptyArray()
    //
    val sql = "SELECT * FROM M_MatchPO WHERE C_OrderLine_ID=?"
    val query = queryOf(sql, listOf(C_OrderLine_ID)).map { row -> MMatchPO(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getOrderLine

internal fun create(
    ctx: Properties,
    iLine: I_C_InvoiceLine?,
    sLine: MInOutLine?,
    C_OrderLine_ID: Int,
    dateTrx: Timestamp,
    quantity: BigDecimal
): MMatchPO? {
    var qty = quantity
    var retValue: MMatchPO? = null
    val sql = "SELECT * FROM M_MatchPO WHERE C_OrderLine_ID=? and Reversal_ID IS NULL ORDER BY M_MatchPO_ID"
    try {
        val query = queryOf(sql, listOf(C_OrderLine_ID)).map { row -> MMatchPO(ctx, row) }.asList
        val items = DB.current.run(query)

        for (mpo in items) {
            if (qty.compareTo(mpo.qty) >= 0) {
                var toMatch = qty
                val matchQty = mpo.qty
                if (toMatch > matchQty) toMatch = matchQty
                if (iLine != null) {
                    if (mpo.invoiceLineId == 0 || mpo.invoiceLineId == iLine.invoiceLineId) {
                        if (iLine.attributeSetInstanceId != 0) {
                            if (mpo.attributeSetInstanceId == 0)
                                mpo.attributeSetInstanceId = iLine.attributeSetInstanceId
                            else if (mpo.attributeSetInstanceId != iLine.attributeSetInstanceId)
                                continue
                        }
                    } else
                        continue
                }
                if (sLine != null) {
                    if (mpo.getInOutLineId() == 0 || mpo.getInOutLineId() == sLine.inOutLineId) {

                        if (sLine.attributeSetInstanceId != 0) {
                            if (mpo.attributeSetInstanceId == 0)
                                mpo.attributeSetInstanceId = sLine.attributeSetInstanceId
                            else if (mpo.attributeSetInstanceId != sLine.attributeSetInstanceId)
                                continue
                        }
                    } else
                        continue
                }
                if ((iLine != null || mpo.invoiceLineId > 0) && (sLine != null || mpo.inOutLineId > 0)) {
                    val M_InOutLine_ID = if (sLine != null) sLine.inOutLineId else mpo.inOutLineId
                    val C_InvoiceLine_ID =
                        if (iLine != null) iLine.invoiceLineId else mpo.invoiceLineId

                    // verify invoiceline not already linked to another inoutline
                    val tmpInOutLineId = getSQLValue(
                        "SELECT M_InOutLine_ID FROM C_InvoiceLine WHERE C_InvoiceLine_ID=$C_InvoiceLine_ID"
                    )
                    if (tmpInOutLineId > 0 && tmpInOutLineId != M_InOutLine_ID) {
                        continue
                    }

                    // verify m_matchinv not created yet
                    val cnt = getSQLValue(
                        "SELECT Count(*) FROM M_MatchInv WHERE M_InOutLine_ID=" +
                                M_InOutLine_ID +
                                " AND C_InvoiceLine_ID=" +
                                C_InvoiceLine_ID
                    )
                    if (cnt <= 0) {
                        val matchInv = MMatchInv(mpo.ctx, 0)
                        matchInv.invoiceLineId = C_InvoiceLine_ID
                        matchInv.setProductId(mpo.productId)
                        matchInv.inOutLineId = M_InOutLine_ID
                        matchInv.setADClientID(mpo.clientId)
                        matchInv.setOrgId(mpo.orgId)
                        matchInv.attributeSetInstanceId = mpo.attributeSetInstanceId
                        matchInv.qty = mpo.qty
                        matchInv.dateTrx = dateTrx
                        matchInv.isProcessed = true
                        if (!matchInv.save()) {
                            matchInv.delete(true)
                            continue
                        }
                        mpo.matchInvCreated = matchInv
                    }
                }
                if (iLine != null) mpo.setInvoiceLineId(iLine)
                if (sLine != null) {
                    mpo.inOutLineId = sLine.inOutLineId
                    if (!mpo.isPosted) mpo.dateAcct = sLine.parent.dateAcct
                }

                if (!mpo.save()) {
                    var msg = "Failed to update match po."
                    val error = CLogger.retrieveError()
                    if (error != null) {
                        msg = msg + " " + error.name
                    }
                    throw RuntimeException(msg)
                }

                qty = qty.subtract(toMatch)
                if (qty.signum() <= 0) {
                    retValue = mpo
                    break
                }
            }
        }
    } catch (e: Exception) {
        if (e is RuntimeException) {
            throw e
        } else {
            throw IllegalStateException(e)
        }
    }
    // 	Create New
    if (retValue == null) {
        var sLineMatchedQty: BigDecimal? = null
        if (sLine != null && iLine != null) {
            sLineMatchedQty = getSQLValueBD(
                ("SELECT Sum(Qty) FROM M_MatchPO WHERE C_OrderLine_ID=" +
                        C_OrderLine_ID +
                        " AND M_InOutLine_ID=?"),
                sLine.inOutLineId
            )
        }

        if ((sLine != null &&
                    (sLine.orderLineId == C_OrderLine_ID || iLine == null) &&
                    (sLineMatchedQty == null || sLineMatchedQty.signum() <= 0))
        ) {
            if (qty.signum() > 0) {
                retValue = MMatchPO(sLine, dateTrx, qty)
                retValue.orderLineId = C_OrderLine_ID
                if (iLine != null) retValue.setInvoiceLineId(iLine)
                if (!retValue.save()) {
                    var msg = "Failed to update match po."
                    val error = CLogger.retrieveError()
                    if (error != null) {
                        msg = msg + " " + error.name
                    }
                    throw RuntimeException(msg)
                }
            }
        } else if (iLine != null) {
            if (qty.signum() > 0) {
                retValue = MMatchPO(iLine, dateTrx, qty)
                retValue.orderLineId = C_OrderLine_ID
                if (!retValue.save()) {
                    var msg = "Failed to update match po."
                    val error = CLogger.retrieveError()
                    if (error != null) {
                        msg = msg + " " + error.name
                    }
                    throw RuntimeException(msg)
                }
            }
        }
    }

    return retValue
} // 	create