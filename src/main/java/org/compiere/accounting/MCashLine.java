package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.bank.MBankAccount;
import org.compiere.invoicing.MInvoice;
import org.compiere.model.IDocLine;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.Env;

import static software.hsharp.core.orm.POKt.I_ZERO;
import static software.hsharp.core.util.DBKt.executeUpdate;
import static software.hsharp.core.util.DBKt.getSQLValue;

/**
 * Cash Line Model
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>BF [ 1760240 ] CashLine bank account is filled even if is not bank transfer
 * <li>BF [ 1918266 ] MCashLine.updateHeader should ignore not active lines
 * <li>BF [ 1918290 ] MCashLine.createReversal should inactivate if not processed
 * @version $Id: MCashLine.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MCashLine extends X_C_CashLine implements IDocLine {
    /**
     *
     */
    private static final long serialVersionUID = 2962077554051498950L;
    /**
     * Parent
     */
    private MCash m_parent = null;
    /**
     * Cash Book
     */
    private MCashBook m_cashBook = null;
    /**
     * Bank Account
     */
    private MBankAccount m_bankAccount = null;
    /**
     * Invoice
     */
    private I_C_Invoice m_invoice = null;

    /**
     * Standard Constructor
     *
     * @param ctx           context
     * @param C_CashLine_ID id
     * @param trxName       transaction
     */
    public MCashLine(int C_CashLine_ID) {
        super(C_CashLine_ID);
        if (C_CashLine_ID == 0) {
            //	setLine (0);
            //	setCashType (CASHTYPE_GeneralExpense);
            setAmount(Env.ZERO);
            setDiscountAmt(Env.ZERO);
            setWriteOffAmt(Env.ZERO);
            setIsGenerated(false);
        }
    } //	MCashLine

    /**
     * Load Cosntructor
     *
     * @param ctx context
     */
    public MCashLine(Row row) {
        super(row);
    } //	MCashLine

    /**
     * Parent Cosntructor
     *
     * @param cash parent
     */
    public MCashLine(MCash cash) {
        this(0);
        setClientOrg(cash);
        setCashId(cash.getCashId());
        m_parent = cash;
        m_cashBook = m_parent.getCashBook();
    } //	MCashLine

    /**
     * Add to Description
     *
     * @param description text
     */
    public void addDescription(String description) {
        String desc = getDescription();
        if (desc == null) setDescription(description);
        else setDescription(desc + " | " + description);
    } //	addDescription

    /**
     * Get Cash (parent)
     *
     * @return cash
     */
    public MCash getParent() {
        if (m_parent == null) m_parent = new MCash(getCashId());
        return m_parent;
    } //	getCash

    /**
     * Get CashBook
     *
     * @return cash book
     */
    public MCashBook getCashBook() {
        if (m_cashBook == null) m_cashBook = MCashBook.get(getParent().getCashBookId());
        return m_cashBook;
    } //	getCashBook

    /**
     * Get Bank Account
     *
     * @return bank account
     */
    public MBankAccount getBankAccount() {
        if (m_bankAccount == null && getBankAccountId() != 0)
            m_bankAccount = MBankAccount.get(getBankAccountId());
        return m_bankAccount;
    } //	getBankAccount

    /**
     * Get Invoice
     *
     * @return invoice
     */
    public I_C_Invoice getInvoice() {
        if (m_invoice == null && getInvoiceId() != 0)
            m_invoice = MInvoice.get(getInvoiceId());
        return m_invoice;
    } //	getInvoice

    /**
     * ************************************************************************ Before Delete
     *
     * @return true/false
     */
    protected boolean beforeDelete() {
        //	Cannot Delete generated Invoices
        Boolean generated = (Boolean) getValueOld("IsGenerated");
        if (generated != null && generated.booleanValue()) {
            if (getValueOld("C_Invoice_ID") != null) {
                log.saveError("Error", MsgKt.getMsg("CannotDeleteCashGenInvoice"));
                return false;
            }
        }
        return true;
    } //	beforeDelete

    /**
     * After Delete
     *
     * @param success
     * @return true/false
     */
    protected boolean afterDelete(boolean success) {
        if (!success) return success;
        return updateHeader();
    } //	afterDelete

    /**
     * Before Save
     *
     * @param newRecord
     * @return true/false
     */
    protected boolean beforeSave(boolean newRecord) {
        if (newRecord && getParent().isComplete()) {
            log.saveError("ParentComplete", MsgKt.translate("C_CashLine"));
            return false;
        }
        //	Cannot change generated Invoices
        if (isValueChanged(I_C_CashLine.COLUMNNAME_C_Invoice_ID)) {
            Object generated = getValueOld(I_C_CashLine.COLUMNNAME_IsGenerated);
            if (generated != null && ((Boolean) generated).booleanValue()) {
                log.saveError("Error", MsgKt.getMsg("CannotChangeCashGenInvoice"));
                return false;
            }
        }

        //	Verify CashType
        if (X_C_CashLine.CASHTYPE_Invoice.equals(getCashType()) && getInvoiceId() == 0)
            setCashType(X_C_CashLine.CASHTYPE_GeneralExpense);
        if (X_C_CashLine.CASHTYPE_BankAccountTransfer.equals(getCashType())
                && getBankAccountId() == 0) setCashType(X_C_CashLine.CASHTYPE_GeneralExpense);
        if (X_C_CashLine.CASHTYPE_Charge.equals(getCashType()) && getChargeId() == 0)
            setCashType(X_C_CashLine.CASHTYPE_GeneralExpense);

        boolean verify =
                newRecord
                        || isValueChanged("CashType")
                        || isValueChanged("C_Invoice_ID")
                        || isValueChanged("C_BankAccount_ID");
        if (verify) {
            //	Verify Currency
            if (X_C_CashLine.CASHTYPE_BankAccountTransfer.equals(getCashType()))
                setCurrencyId(getBankAccount().getCurrencyId());
            else if (X_C_CashLine.CASHTYPE_Invoice.equals(getCashType()))
                setCurrencyId(getInvoice().getCurrencyId());
            else //	Cash
                setCurrencyId(getCashBook().getCurrencyId());

            //	Set Organization
            if (X_C_CashLine.CASHTYPE_BankAccountTransfer.equals(getCashType()))
                setOrgId(getBankAccount().getOrgId());
                //	Cash Book
            else if (X_C_CashLine.CASHTYPE_Invoice.equals(getCashType()))
                setOrgId(getCashBook().getOrgId());
            //	otherwise (charge) - leave it
            //	Enforce Org
            if (getOrgId() == 0) setOrgId(getParent().getOrgId());
        }

        // If CashType is not Bank Account Transfer, set C_BankAccount_ID to null - teo_sarca BF [
        // 1760240 ]
        if (!X_C_CashLine.CASHTYPE_BankAccountTransfer.equals(getCashType()))
            setBankAccountId(I_ZERO);

        /**
         * General fix of Currency UPDATE C_CashLine cl SET C_Currency_ID = (SELECT C_Currency_ID FROM
         * C_Invoice i WHERE i.C_Invoice_ID=cl.C_Invoice_ID) WHERE C_Currency_ID IS NULL AND
         * C_Invoice_ID IS NOT NULL; UPDATE C_CashLine cl SET C_Currency_ID = (SELECT C_Currency_ID FROM
         * C_BankAccount b WHERE b.C_BankAccount_ID=cl.C_BankAccount_ID) WHERE C_Currency_ID IS NULL AND
         * C_BankAccount_ID IS NOT NULL; UPDATE C_CashLine cl SET C_Currency_ID = (SELECT
         * b.C_Currency_ID FROM C_Cash c, C_CashBook b WHERE c.C_Cash_ID=cl.C_Cash_ID AND
         * c.C_CashBook_ID=b.C_CashBook_ID) WHERE C_Currency_ID IS NULL;
         */

        //	Get Line No
        if (getLine() == 0) {
            String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM C_CashLine WHERE C_Cash_ID=?";
            int ii = getSQLValue(sql, getCashId());
            setLine(ii);
        }

        return true;
    } //	beforeSave

    /**
     * After Save
     *
     * @param newRecord
     * @param success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        return updateHeader();
    } //	afterSave

    /**
     * Update Cash Header. Statement Difference, Ending Balance
     *
     * @return true if success
     */
    private boolean updateHeader() {
        String sql =
                "UPDATE C_Cash c"
                        + " SET StatementDifference="
                        // replace null with 0 there is no difference with this
                        + "(SELECT COALESCE(SUM(currencyConvert(cl.Amount, cl.C_Currency_ID, cb.C_Currency_ID, c.DateAcct, 0, c.AD_Client_ID, c.orgId)),0) "
                        + "FROM C_CashLine cl, C_CashBook cb "
                        + "WHERE cb.C_CashBook_ID=c.C_CashBook_ID"
                        + " AND cl.C_Cash_ID=c.C_Cash_ID"
                        + " AND cl.IsActive='Y'"
                        + ") "
                        + "WHERE C_Cash_ID="
                        + getCashId();
        int no = executeUpdate(sql);
        if (no != 1) log.warning("Difference #" + no);
        //	Ending Balance
        sql =
                "UPDATE C_Cash"
                        + " SET EndingBalance = BeginningBalance + StatementDifference "
                        + "WHERE C_Cash_ID="
                        + getCashId();
        no = executeUpdate(sql);
        if (no != 1) log.warning("Balance #" + no);
        return no == 1;
    } //	updateHeader
} //	MCashLine
