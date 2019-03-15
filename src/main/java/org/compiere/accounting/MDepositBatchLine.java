/**
 * ******************************************************************** This file is part of
 * Adempiere ERP Bazaar * http://www.adempiere.org * * Copyright (C) Alejandro Falcone * Copyright
 * (C) Contributors * * This program is free software; you can redistribute it and/or * modify it
 * under the terms of the GNU General Public License * as published by the Free Software Foundation;
 * either version 2 * of the License, or (at your option) any later version. * * This program is
 * distributed in the hope that it will be useful, * but WITHOUT ANY WARRANTY; without even the
 * implied warranty of * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the * GNU General
 * Public License for more details. * * You should have received a copy of the GNU General Public
 * License * along with this program; if not, write to the Free Software * Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, * MA 02110-1301, USA. * * Contributors: * - Alejandro
 * Falcone (afalcone@users.sourceforge.net) * http://www.openbiz.com.ar * * Sponsors: * - Idalica
 * Inc. (http://www.idalica.com) *
 * *********************************************************************
 */
package org.compiere.accounting;

import kotliquery.Row;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.executeUpdateEx;
import static software.hsharp.core.util.DBKt.getSQLValue;

/**
 * Bank Statement Line Model
 *
 * @author Alejandro Falcone
 * @version $Id: MDepositBatchLine.java,v 1.3 2007/07/02 00:51:02 afalcone Exp $
 */
public class MDepositBatchLine extends X_C_DepositBatchLine {
    /**
     *
     */
    private static final long serialVersionUID = -4461960512392850996L;

    /**
     * Standard Constructor
     *
     * @param ctx                    context
     * @param C_BankStatementLine_ID id
     * @param trxName                transaction
     */
    public MDepositBatchLine(Properties ctx, int C_DepositBatchLine_ID) {
        super(ctx, C_DepositBatchLine_ID);
        if (C_DepositBatchLine_ID == 0) {
            setPayAmt(Env.ZERO);
        }
    } //	MDepositBatchLine

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MDepositBatchLine(Properties ctx, Row row) {
        super(ctx, row);
    } //	MDepositBatchLine

    /**
     * Parent Constructor
     *
     * @param statement Bank Statement that the line is part of
     */
    public MDepositBatchLine(MDepositBatch statement) {
        this(statement.getCtx(), 0);
        setClientOrg(statement);
        setC_DepositBatch_ID(statement.getC_DepositBatch_ID());
    } //	MDepositBatchLine

    /**
     * Parent Constructor
     *
     * @param statement Deposit Batch that the line is part of
     * @param lineNo    position of the line within the statement
     */
    public MDepositBatchLine(MDepositBatch statement, int lineNo) {
        this(statement);
        setLine(lineNo);
    } //	MDepositBatchLine

    /**
     * Set Payment
     *
     * @param payment payment
     */
    public void setPayment(MPayment payment) {
        setPaymentId(payment.getPaymentId());
        //
        BigDecimal amt = payment.getPayAmt(true);
        setPayAmt(amt);
        //
    } //	setPayment

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        //	Set Line No
        if (getLine() == 0) {
            String sql =
                    "SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM C_DepositBatchLine WHERE C_DepositBatch_ID=?";
            int ii = getSQLValue(sql, getC_DepositBatch_ID());
            setLine(ii);
        }

        //	Set DepositBatch_ID into C_Payment table
        if (getPaymentId() != 0) {
            String sql = "UPDATE C_Payment p SET C_DepositBatch_ID=? WHERE p.C_Payment_ID=?";
            executeUpdateEx(
                    sql, new Object[]{getC_DepositBatch_ID(), getPaymentId()});

            MPayment payment = new MPayment(getCtx(), getPaymentId());
            setPayment(payment); // set payment amount
        }

        return true;
    } //	beforeSave

    /**
     * After Save
     *
     * @param newRecord new
     * @param success   success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        updateHeader();
        return success;
    } //	afterSave

    /**
     * After Delete
     *
     * @param success success
     * @return success
     */
    protected boolean afterDelete(boolean success) {
        if (!success) return success;
        updateHeader();
        if (getPaymentId() != 0) {
            String sql = "UPDATE C_Payment p SET C_DepositBatch_ID= Null WHERE p.C_Payment_ID=?";
            executeUpdateEx(sql, new Object[]{getPaymentId()});
        }

        return success;
    } //	afterDelete

    /**
     * Update Header
     */
    private void updateHeader() {
        String sql =
                "UPDATE C_DepositBatch dp"
                        + " SET DepositAmt=(SELECT COALESCE(SUM(PayAmt),0) FROM C_DepositBatchLine dpl "
                        + "WHERE dpl.C_DepositBatch_ID=dp.C_DepositBatch_ID AND dpl.IsActive='Y') "
                        + "WHERE C_DepositBatch_ID=?";
        executeUpdateEx(sql, new Object[]{getC_DepositBatch_ID()});
    } //	updateHeader
} //	MDepositBatchLine
