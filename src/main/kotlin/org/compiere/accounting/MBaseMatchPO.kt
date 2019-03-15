package org.compiere.accounting

import org.compiere.model.I_C_InvoiceLine
import org.compiere.order.MInOutLine
import org.idempiere.common.util.CLogger
import software.hsharp.core.util.DB
import software.hsharp.core.util.getSQLValue
import software.hsharp.core.util.getSQLValueBD
import software.hsharp.core.util.queryOf
import java.math.BigDecimal
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import java.util.Properties

/**
 * Get PO Match of Receipt Line
 *
 * @param ctx context
 * @param M_InOutLine_ID receipt
 * @return array of matches
 */
fun getPOMatchOfReceiptLine(ctx: Properties, M_InOutLine_ID: Int): Array<MMatchPO> {
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
fun getPOMatchesOfReceipt(ctx: Properties, M_InOut_ID: Int): Array<MMatchPO> {
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
fun getPOMatchesOfInvoice(ctx: Properties, C_Invoice_ID: Int): Array<MMatchPO> {
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
fun getPOMatchesForOrderLine(ctx: Properties, C_OrderLine_ID: Int): Array<MMatchPO> {
    if (C_OrderLine_ID == 0) return emptyArray()
    //
    val sql = "SELECT * FROM M_MatchPO WHERE C_OrderLine_ID=?"
    val query = queryOf(sql, listOf(C_OrderLine_ID)).map { row -> MMatchPO(ctx, row) }.asList
    return DB.current.run(query).toTypedArray()
} // 	getOrderLine

fun create(
    ctx: Properties,
    iLine: I_C_InvoiceLine?,
    sLine: MInOutLine?,
    C_OrderLine_ID: Int,
    dateTrx: Timestamp,
    qty: BigDecimal
): MMatchPO? {
    var qty = qty
    var retValue: MMatchPO? = null
    val sql = "SELECT * FROM M_MatchPO WHERE C_OrderLine_ID=? and Reversal_ID IS NULL ORDER BY M_MatchPO_ID"
    var pstmt: PreparedStatement? = null
    var rs: ResultSet? = null
    try {
        val query = queryOf(sql, listOf(C_OrderLine_ID)).map { row -> MMatchPO(ctx, row) }.asList
        val items = DB.current.run(query)

        for (mpo in items) {
            if (qty.compareTo(mpo.getQty()) >= 0) {
                var toMatch = qty
                val matchQty = mpo.getQty()
                if (toMatch.compareTo(matchQty) > 0) toMatch = matchQty
                if (iLine != null) {
                    if (mpo.getC_InvoiceLine_ID() == 0 || mpo.getC_InvoiceLine_ID() == iLine.c_InvoiceLine_ID) {
                        if (iLine.mAttributeSetInstance_ID != 0) {
                            if (mpo.getMAttributeSetInstance_ID() == 0)
                                mpo.setM_AttributeSetInstance_ID(iLine.mAttributeSetInstance_ID)
                            else if (mpo.getMAttributeSetInstance_ID() != iLine.mAttributeSetInstance_ID)
                                continue
                        }
                    } else
                        continue
                }
                if (sLine != null) {
                    if (mpo.getM_InOutLine_ID() == 0 || mpo.getM_InOutLine_ID() == sLine!!.m_InOutLine_ID) {

                        if (sLine!!.mAttributeSetInstance_ID != 0) {
                            if (mpo.getMAttributeSetInstance_ID() == 0)
                                mpo.setM_AttributeSetInstance_ID(sLine!!.mAttributeSetInstance_ID)
                            else if (mpo.getMAttributeSetInstance_ID() != sLine!!.mAttributeSetInstance_ID)
                                continue
                        }
                    } else
                        continue
                }
                if ((iLine != null || mpo.getC_InvoiceLine_ID() > 0) && (sLine != null || mpo.getM_InOutLine_ID() > 0)) {
                    val M_InOutLine_ID = if (sLine != null) sLine!!.m_InOutLine_ID else mpo.getM_InOutLine_ID()
                    val C_InvoiceLine_ID =
                        if (iLine != null) iLine!!.c_InvoiceLine_ID else mpo.getC_InvoiceLine_ID()

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
                        matchInv.c_InvoiceLine_ID = C_InvoiceLine_ID
                        matchInv.setM_Product_ID(mpo.getM_Product_ID())
                        matchInv.m_InOutLine_ID = M_InOutLine_ID
                        matchInv.setADClientID(mpo.clientId)
                        matchInv.setOrgId(mpo.orgId)
                        matchInv.setM_AttributeSetInstance_ID(mpo.getMAttributeSetInstance_ID())
                        matchInv.qty = mpo.getQty()
                        matchInv.dateTrx = dateTrx
                        matchInv.isProcessed = true
                        if (!matchInv.save()) {
                            matchInv.delete(true)
                            var msg = "Failed to auto match invoice."
                            val error = CLogger.retrieveError()
                            if (error != null) {
                                msg = msg + " " + error!!.name
                            }
                            continue
                        }
                        mpo.setMatchInvCreated(matchInv)
                    }
                }
                if (iLine != null) mpo.setC_InvoiceLine_ID(iLine)
                if (sLine != null) {
                    mpo.setM_InOutLine_ID(sLine!!.m_InOutLine_ID)
                    if (!mpo.isPosted()) mpo.setDateAcct(sLine!!.parent.dateAcct)
                }

                if (!mpo.save()) {
                    var msg = "Failed to update match po."
                    val error = CLogger.retrieveError()
                    if (error != null) {
                        msg = msg + " " + error!!.name
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
            throw e as RuntimeException
        } else {
            throw IllegalStateException(e)
        }
    } finally {
    }

    // 	Create New
    if (retValue == null) {
        var sLineMatchedQty: BigDecimal? = null
        if (sLine != null && iLine != null) {
            sLineMatchedQty = getSQLValueBD(
                ("SELECT Sum(Qty) FROM M_MatchPO WHERE C_OrderLine_ID=" +
                        C_OrderLine_ID +
                        " AND M_InOutLine_ID=?"),
                sLine!!.m_InOutLine_ID
            )
        }

        if ((sLine != null &&
                    (sLine!!.c_OrderLine_ID == C_OrderLine_ID || iLine == null) &&
                    (sLineMatchedQty == null || sLineMatchedQty!!.signum() <= 0))
        ) {
            if (qty.signum() > 0) {
                retValue = MMatchPO(sLine!!, dateTrx, qty)
                retValue!!.c_OrderLine_ID = C_OrderLine_ID
                if (iLine != null) retValue!!.setC_InvoiceLine_ID(iLine)
                if (!retValue!!.save()) {
                    var msg = "Failed to update match po."
                    val error = CLogger.retrieveError()
                    if (error != null) {
                        msg = msg + " " + error!!.name
                    }
                    throw RuntimeException(msg)
                }
            }
        } else if (iLine != null) {
            if (qty.signum() > 0) {
                retValue = MMatchPO(iLine!!, dateTrx, qty)
                retValue!!.c_OrderLine_ID = C_OrderLine_ID
                if (!retValue!!.save()) {
                    var msg = "Failed to update match po."
                    val error = CLogger.retrieveError()
                    if (error != null) {
                        msg = msg + " " + error!!.name
                    }
                    throw RuntimeException(msg)
                }
            }
        }
    }

    return retValue
} // 	create