package org.compiere.accounting;

import org.compiere.bank.MBankAccount;
import org.compiere.invoicing.MInvoice;
import org.compiere.model.IDocLine;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.orm.MDocType;
import org.compiere.process.DocAction;
import org.compiere.util.Msg;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import static software.hsharp.core.orm.POKt.I_ZERO;
import static software.hsharp.core.util.DBKt.executeUpdate;
import static software.hsharp.core.util.DBKt.getSQLValue;

/**
 * Cash Line Model
 *
 * @author Jorg Janke
 * @version $Id: MCashLine.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *     <li>BF [ 1760240 ] CashLine bank account is filled even if is not bank transfer
 *     <li>BF [ 1918266 ] MCashLine.updateHeader should ignore not active lines
 *     <li>BF [ 1918290 ] MCashLine.createReversal should inactivate if not processed
 */
public class MCashLine extends X_C_CashLine implements IDocLine {
  /** */
  private static final long serialVersionUID = 2962077554051498950L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param C_CashLine_ID id
   * @param trxName transaction
   */
  public MCashLine(Properties ctx, int C_CashLine_ID, String trxName) {
    super(ctx, C_CashLine_ID, trxName);
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
   * @param rs result set
   * @param trxName transaction
   */
  public MCashLine(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MCashLine

  /**
   * Parent Cosntructor
   *
   * @param cash parent
   */
  public MCashLine(MCash cash) {
    this(cash.getCtx(), 0, null);
    setClientOrg(cash);
    setC_Cash_ID(cash.getC_Cash_ID());
    m_parent = cash;
    m_cashBook = m_parent.getCashBook();
  } //	MCashLine

  /** Parent */
  private MCash m_parent = null;
  /** Cash Book */
  private MCashBook m_cashBook = null;
  /** Bank Account */
  private MBankAccount m_bankAccount = null;
  /** Invoice */
  private I_C_Invoice m_invoice = null;

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
    if (m_parent == null) m_parent = new MCash(getCtx(), getC_Cash_ID(), null);
    return m_parent;
  } //	getCash

  /**
   * Get CashBook
   *
   * @return cash book
   */
  public MCashBook getCashBook() {
    if (m_cashBook == null) m_cashBook = MCashBook.get(getCtx(), getParent().getC_CashBook_ID());
    return m_cashBook;
  } //	getCashBook

  /**
   * Get Bank Account
   *
   * @return bank account
   */
  public MBankAccount getBankAccount() {
    if (m_bankAccount == null && getC_BankAccount_ID() != 0)
      m_bankAccount = MBankAccount.get(getCtx(), getC_BankAccount_ID());
    return m_bankAccount;
  } //	getBankAccount

  /**
   * Get Invoice
   *
   * @return invoice
   */
  public I_C_Invoice getInvoice() {
    if (m_invoice == null && getC_Invoice_ID() != 0)
      m_invoice = MInvoice.get(getCtx(), getC_Invoice_ID());
    return m_invoice;
  } //	getInvoice

  /**
   * ************************************************************************ Before Delete
   *
   * @return true/false
   */
  protected boolean beforeDelete() {
    //	Cannot Delete generated Invoices
    Boolean generated = (Boolean) get_ValueOld("IsGenerated");
    if (generated != null && generated.booleanValue()) {
      if (get_ValueOld("C_Invoice_ID") != null) {
        log.saveError("Error", Msg.getMsg(getCtx(), "CannotDeleteCashGenInvoice"));
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
      log.saveError("ParentComplete", Msg.translate(getCtx(), "C_CashLine"));
      return false;
    }
    //	Cannot change generated Invoices
    if (is_ValueChanged(I_C_CashLine.COLUMNNAME_C_Invoice_ID)) {
      Object generated = get_ValueOld(I_C_CashLine.COLUMNNAME_IsGenerated);
      if (generated != null && ((Boolean) generated).booleanValue()) {
        log.saveError("Error", Msg.getMsg(getCtx(), "CannotChangeCashGenInvoice"));
        return false;
      }
    }

    //	Verify CashType
    if (X_C_CashLine.CASHTYPE_Invoice.equals(getCashType()) && getC_Invoice_ID() == 0)
      setCashType(X_C_CashLine.CASHTYPE_GeneralExpense);
    if (X_C_CashLine.CASHTYPE_BankAccountTransfer.equals(getCashType())
        && getC_BankAccount_ID() == 0) setCashType(X_C_CashLine.CASHTYPE_GeneralExpense);
    if (X_C_CashLine.CASHTYPE_Charge.equals(getCashType()) && getC_Charge_ID() == 0)
      setCashType(X_C_CashLine.CASHTYPE_GeneralExpense);

    boolean verify =
        newRecord
            || is_ValueChanged("CashType")
            || is_ValueChanged("C_Invoice_ID")
            || is_ValueChanged("C_BankAccount_ID");
    if (verify) {
      //	Verify Currency
      if (X_C_CashLine.CASHTYPE_BankAccountTransfer.equals(getCashType()))
        setC_Currency_ID(getBankAccount().getC_Currency_ID());
      else if (X_C_CashLine.CASHTYPE_Invoice.equals(getCashType()))
        setC_Currency_ID(getInvoice().getC_Currency_ID());
      else //	Cash
      setC_Currency_ID(getCashBook().getC_Currency_ID());

      //	Set Organization
      if (X_C_CashLine.CASHTYPE_BankAccountTransfer.equals(getCashType()))
        setAD_Org_ID(getBankAccount(). getOrgId());
      //	Cash Book
      else if (X_C_CashLine.CASHTYPE_Invoice.equals(getCashType()))
        setAD_Org_ID(getCashBook(). getOrgId());
      //	otherwise (charge) - leave it
      //	Enforce Org
      if ( getOrgId() == 0) setAD_Org_ID(getParent(). getOrgId());
    }

    // If CashType is not Bank Account Transfer, set C_BankAccount_ID to null - teo_sarca BF [
    // 1760240 ]
    if (!X_C_CashLine.CASHTYPE_BankAccountTransfer.equals(getCashType()))
      setC_BankAccount_ID(I_ZERO);

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
      int ii = getSQLValue(sql, getC_Cash_ID());
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
            + getC_Cash_ID();
    int no = executeUpdate(sql);
    if (no != 1) log.warning("Difference #" + no);
    //	Ending Balance
    sql =
        "UPDATE C_Cash"
            + " SET EndingBalance = BeginningBalance + StatementDifference "
            + "WHERE C_Cash_ID="
            + getC_Cash_ID();
    no = executeUpdate(sql);
    if (no != 1) log.warning("Balance #" + no);
    return no == 1;
  } //	updateHeader
} //	MCashLine
