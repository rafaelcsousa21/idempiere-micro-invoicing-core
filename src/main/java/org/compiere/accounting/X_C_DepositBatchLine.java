package org.compiere.accounting;

import org.compiere.model.I_C_DepositBatchLine;
import org.compiere.orm.PO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_DepositBatchLine
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_DepositBatchLine extends PO implements I_C_DepositBatchLine {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_DepositBatchLine(Properties ctx, int C_DepositBatchLine_ID) {
        super(ctx, C_DepositBatchLine_ID);
        /**
         * if (C_DepositBatchLine_ID == 0) { setC_DepositBatch_ID (0); setC_DepositBatchLine_ID (0);
         * setPaymentId (0); setLine (0); // @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM
         * C_DepositBatchLine WHERE C_DepositBatch_ID=@C_DepositBatch_ID@ setPayAmt (Env.ZERO);
         * setProcessed (false); setProcessing (false); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_DepositBatchLine(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 3 - Client - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_C_DepositBatchLine[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Deposit Batch.
     *
     * @return Deposit Batch
     */
    public int getC_DepositBatch_ID() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_DepositBatch_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Deposit Batch.
     *
     * @param C_DepositBatch_ID Deposit Batch
     */
    public void setC_DepositBatch_ID(int C_DepositBatch_ID) {
        if (C_DepositBatch_ID < 1) setValueNoCheck(COLUMNNAME_C_DepositBatch_ID, null);
        else setValueNoCheck(COLUMNNAME_C_DepositBatch_ID, Integer.valueOf(C_DepositBatch_ID));
    }

    /**
     * Get Payment.
     *
     * @return Payment identifier
     */
    public int getPaymentId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_Payment_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Payment.
     *
     * @param C_Payment_ID Payment identifier
     */
    public void setPaymentId(int C_Payment_ID) {
        if (C_Payment_ID < 1) setValue(COLUMNNAME_C_Payment_ID, null);
        else setValue(COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
    }

    /**
     * Get Line No.
     *
     * @return Unique line for this document
     */
    public int getLine() {
        Integer ii = (Integer) getValue(COLUMNNAME_Line);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Line No.
     *
     * @param Line Unique line for this document
     */
    public void setLine(int Line) {
        setValue(COLUMNNAME_Line, Integer.valueOf(Line));
    }

    /**
     * Set Payment amount.
     *
     * @param PayAmt Amount being paid
     */
    public void setPayAmt(BigDecimal PayAmt) {
        setValue(COLUMNNAME_PayAmt, PayAmt);
    }

    @Override
    public int getTableId() {
        return I_C_DepositBatchLine.Table_ID;
    }
}
