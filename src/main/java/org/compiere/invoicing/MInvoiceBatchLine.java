package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.util.Msg;
import org.idempiere.common.util.Env;

import java.sql.Timestamp;

import static software.hsharp.core.util.DBKt.executeUpdate;

/**
 * Invoice Batch Line Model
 *
 * @author Jorg Janke
 * @version $Id: MInvoiceBatchLine.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MInvoiceBatchLine extends X_C_InvoiceBatchLine {
    /**
     *
     */
    private static final long serialVersionUID = -4022629343631759064L;

    /**
     * Standard Constructor
     *
     * @param ctx                   context
     * @param C_InvoiceBatchLine_ID id
     * @param trxName               trx
     */
    public MInvoiceBatchLine(int C_InvoiceBatchLine_ID) {
        super(C_InvoiceBatchLine_ID);
        if (C_InvoiceBatchLine_ID == 0) {
            setDateAcct(new Timestamp(System.currentTimeMillis())); // @DateDoc@
            setDateInvoiced(new Timestamp(System.currentTimeMillis())); // @DateDoc@
            setIsTaxIncluded(false);
            setLineNetAmt(Env.ZERO);
            setLineTotalAmt(Env.ZERO);
            setPriceEntered(Env.ZERO);
            setQtyEntered(Env.ONE); // 1
            setTaxAmt(Env.ZERO);
            setProcessed(false);
        }
    } //	MInvoiceBatchLine

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MInvoiceBatchLine(Row row) {
        super(row);
    } //	MInvoiceBatchLine

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        // Amount
        if (getPriceEntered().signum() == 0) {
            log.saveError("FillMandatory", Msg.getElement("PriceEntered"));
            return false;
        }
        return true;
    } //	beforeSave

    /**
     * After Save. Update Header
     *
     * @param newRecord new
     * @param success   success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (success) {
            StringBuilder sql =
                    new StringBuilder("UPDATE C_InvoiceBatch h ")
                            .append("SET DocumentAmt = NVL((SELECT SUM(LineTotalAmt) FROM C_InvoiceBatchLine l ")
                            .append("WHERE h.C_InvoiceBatch_ID=l.C_InvoiceBatch_ID AND l.IsActive='Y'),0) ")
                            .append("WHERE C_InvoiceBatch_ID=")
                            .append(getInvoiceBatchId());
            executeUpdate(sql.toString());
        }
        return success;
    } //	afterSave
} //	MInvoiceBatchLine
