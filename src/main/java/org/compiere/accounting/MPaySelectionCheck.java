package org.compiere.accounting;

import org.compiere.invoicing.MBPBankAccount;
import org.compiere.order.X_C_Order;
import org.compiere.process.DocAction;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Payment Print/Export model.
 *
 * @author Jorg Janke
 * @version $Id: MPaySelectionCheck.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MPaySelectionCheck extends X_C_PaySelectionCheck {
  /** */
  private static final long serialVersionUID = 2130445794890189020L;

  /**
   * Get Check for Payment
   *
   * @param ctx context
   * @param C_Payment_ID id
   * @param trxName transaction
   * @return pay selection check for payment or null
   */
  public static MPaySelectionCheck getOfPayment(Properties ctx, int C_Payment_ID) {
    MPaySelectionCheck retValue = null;
    String sql = "SELECT * FROM C_PaySelectionCheck WHERE C_Payment_ID=?";
    int count = 0;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = prepareStatement(sql);
      pstmt.setInt(1, C_Payment_ID);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        MPaySelectionCheck psc = new MPaySelectionCheck(ctx, rs);
        if (retValue == null) retValue = psc;
        else if (!retValue.isProcessed() && psc.isProcessed()) retValue = psc;
        count++;
      }
    } catch (Exception e) {
      s_log.log(Level.SEVERE, sql, e);
    } finally {

      rs = null;
      pstmt = null;
    }
    if (count > 1) s_log.warning("More then one for C_Payment_ID=" + C_Payment_ID);
    return retValue;
  } //	getOfPayment

    /**
   * ************************************************************************ Confirm Print for a
   * payment selection check Create Payment the first time
   *
   * @param check check
   * @param batch batch
   */
  public static void confirmPrint(MPaySelectionCheck check, MPaymentBatch batch) {
    boolean localTrx = false;
    String trxName = null;
    try {
      MPayment payment = new MPayment(check.getCtx(), check.getC_Payment_ID());
      //	Existing Payment
      if (check.getC_Payment_ID() != 0) {
        //	Update check number
        if (check.getPaymentRule().equals(X_C_PaySelectionCheck.PAYMENTRULE_Check)) {
          payment.setCheckNo(check.getDocumentNo());
          payment.saveEx();
        }
      } else //	New Payment
      {
        payment = new MPayment(check.getCtx(), 0);
        payment.setAD_Org_ID(check. getOrgId());
        //
        if (check.getPaymentRule().equals(X_C_PaySelectionCheck.PAYMENTRULE_Check))
          payment.setBankCheck(
              check.getParent().getC_BankAccount_ID(), false, check.getDocumentNo());
        else if (check.getPaymentRule().equals(X_C_PaySelectionCheck.PAYMENTRULE_CreditCard))
          payment.setTenderType(X_C_Payment.TENDERTYPE_CreditCard);
        else if (check.getPaymentRule().equals(X_C_PaySelectionCheck.PAYMENTRULE_DirectDeposit)
            || check.getPaymentRule().equals(X_C_PaySelectionCheck.PAYMENTRULE_DirectDebit))
          payment.setBankACH(check);
        else {
          s_log.log(Level.SEVERE, "Unsupported Payment Rule=" + check.getPaymentRule());
          return;
        }
        payment.setTrxType(X_C_Payment.TRXTYPE_CreditPayment);
        payment.setAmount(check.getParent().getC_Currency_ID(), check.getPayAmt());
        payment.setDiscountAmt(check.getDiscountAmt());
        payment.setWriteOffAmt(check.getWriteOffAmt());
        payment.setDateTrx(check.getParent().getPayDate());
        payment.setDateAcct(payment.getDateTrx()); // globalqss [ 2030685 ]
        payment.setC_BPartner_ID(check.getC_BPartner_ID());
        //	Link to Batch
        if (batch != null) {
          if (batch.getC_PaymentBatch_ID() == 0) batch.saveEx(); // 	new
          payment.setC_PaymentBatch_ID(batch.getC_PaymentBatch_ID());
        }
        //	Link to Invoice
        MPaySelectionLine[] psls = check.getPaySelectionLines(true);
        if (s_log.isLoggable(Level.FINE))
          s_log.fine("confirmPrint - " + check + " (#SelectionLines=" + psls.length + ")");
        if (check.getQty() == 1 && psls != null && psls.length == 1) {
          MPaySelectionLine psl = psls[0];
          if (s_log.isLoggable(Level.FINE)) s_log.fine("Map to Invoice " + psl);
          //
          payment.setC_Invoice_ID(psl.getC_Invoice_ID());
          payment.setDiscountAmt(psl.getDiscountAmt());
          payment.setWriteOffAmt(psl.getWriteOffAmt());
          BigDecimal overUnder =
              psl.getOpenAmt()
                  .subtract(psl.getPayAmt())
                  .subtract(psl.getDiscountAmt())
                  .subtract(psl.getWriteOffAmt())
                  .subtract(psl.getDifferenceAmt());
          payment.setOverUnderAmt(overUnder);
        } else {
          payment.setWriteOffAmt(Env.ZERO);
          payment.setDiscountAmt(Env.ZERO);
        }
        payment.saveEx();
        //
        int C_Payment_ID = payment.getId();
        if (C_Payment_ID < 1) s_log.log(Level.SEVERE, "Payment not created=" + check);
        else {
          check.setC_Payment_ID(C_Payment_ID);
          check.saveEx(); // 	Payment process needs it
          // added AdempiereException by zuhri
          if (!payment.processIt(DocAction.Companion.getACTION_Complete()))
            throw new AdempiereException(
                "Failed when processing document - " + payment.getProcessMsg());
          // end added
          payment.saveEx();
        }
      } //	new Payment

      check.setIsPrinted(true);
      check.setProcessed(true);
      check.saveEx();
    } catch (Exception e) {
      throw new AdempiereException(e);
    }
  } //	confirmPrint

    /** Logger */
  private static CLogger s_log = CLogger.getCLogger(MPaySelectionCheck.class);

  /**
   * ************************************************************************ Constructor
   *
   * @param ctx context
   * @param C_PaySelectionCheck_ID C_PaySelectionCheck_ID
   * @param trxName transaction
   */
  public MPaySelectionCheck(Properties ctx, int C_PaySelectionCheck_ID) {
    super(ctx, C_PaySelectionCheck_ID);
    if (C_PaySelectionCheck_ID == 0) {
      //	setC_PaySelection_ID (0);
      //	setC_BPartner_ID (0);
      //	setPaymentRule (null);
      setPayAmt(Env.ZERO);
      setDiscountAmt(Env.ZERO);
      setWriteOffAmt(Env.ZERO);
      setIsPrinted(false);
      setIsReceipt(false);
      setQty(0);
    }
  } //  MPaySelectionCheck

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MPaySelectionCheck(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //  MPaySelectionCheck

  /**
   * Create from Line
   *
   * @param line payment selection
   * @param PaymentRule payment rule
   */
  public MPaySelectionCheck(MPaySelectionLine line, String PaymentRule) {
    this(line.getCtx(), 0);
    setClientOrg(line);
    setC_PaySelection_ID(line.getC_PaySelection_ID());
    int C_BPartner_ID = line.getInvoice().getC_BPartner_ID();
    setC_BPartner_ID(C_BPartner_ID);
    //
    if (X_C_Order.PAYMENTRULE_DirectDebit.equals(PaymentRule)) {
      MBPBankAccount[] bas = MBPBankAccount.getOfBPartner(line.getCtx(), C_BPartner_ID);
      for (int i = 0; i < bas.length; i++) {
        MBPBankAccount account = bas[i];
        if (account.isDirectDebit()) {
          setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
          break;
        }
      }
    } else if (X_C_Order.PAYMENTRULE_DirectDeposit.equals(PaymentRule)) {
      MBPBankAccount[] bas = MBPBankAccount.getOfBPartner(line.getCtx(), C_BPartner_ID);
      for (int i = 0; i < bas.length; i++) {
        MBPBankAccount account = bas[i];
        if (account.isDirectDeposit()) {
          setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
          break;
        }
      }
    }
    setPaymentRule(PaymentRule);
    //
    setIsReceipt(line.isSOTrx());
    setPayAmt(line.getPayAmt());
    setDiscountAmt(line.getDiscountAmt());
    setWriteOffAmt(line.getWriteOffAmt());
    setQty(1);
  } //	MPaySelectionCheck

  /**
   * Create from Pay Selection
   *
   * @param ps payment selection
   * @param PaymentRule payment rule
   */
  public MPaySelectionCheck(MPaySelection ps, String PaymentRule) {
    this(ps.getCtx(), 0);
    setClientOrg(ps);
    setC_PaySelection_ID(ps.getC_PaySelection_ID());
    setPaymentRule(PaymentRule);
  } //	MPaySelectionCheck

  /** Parent */
  private MPaySelection m_parent = null;
  /** Payment Selection lines of this check */
  private MPaySelectionLine[] m_lines = null;

  /**
   * Add Payment Selection Line
   *
   * @param line line
   */
  public void addLine(MPaySelectionLine line) {
    if (getC_BPartner_ID() != line.getInvoice().getC_BPartner_ID())
      throw new IllegalArgumentException("Line for different BPartner");
    //
    if (isReceipt() == line.isSOTrx()) {
      setPayAmt(getPayAmt().add(line.getPayAmt()));
      setDiscountAmt(getDiscountAmt().add(line.getDiscountAmt()));
      setWriteOffAmt(getWriteOffAmt().add(line.getWriteOffAmt()));
    } else {
      setPayAmt(getPayAmt().subtract(line.getPayAmt()));
      setDiscountAmt(getDiscountAmt().subtract(line.getDiscountAmt()));
      setWriteOffAmt(getWriteOffAmt().subtract(line.getWriteOffAmt()));
    }
    setQty(getQty() + 1);
  } //	addLine

  /**
   * Get Parent
   *
   * @return parent
   */
  public MPaySelection getParent() {
    if (m_parent == null)
      m_parent = new MPaySelection(getCtx(), getC_PaySelection_ID());
    return m_parent;
  } //	getParent

  /**
   * Is this a valid Prepared Payment
   *
   * @return true if valid
   */
  public boolean isValid() {
    if (getC_BP_BankAccount_ID() != 0) return true;
    return !isDirect();
  } //	isValid

  /**
   * Is this a direct Debit or Deposit
   *
   * @return true if direct
   */
  public boolean isDirect() {
    return (X_C_Order.PAYMENTRULE_DirectDeposit.equals(getPaymentRule())
        || X_C_Order.PAYMENTRULE_DirectDebit.equals(getPaymentRule()));
  } //	isDirect

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("MPaymentCheck[");
    sb.append(getId())
        .append("-")
        .append(getDocumentNo())
        .append("-")
        .append(getPayAmt())
        .append(",PaymetRule=")
        .append(getPaymentRule())
        .append(",Qty=")
        .append(getQty())
        .append("]");
    return sb.toString();
  } //	toString

  /**
   * Get Payment Selection Lines of this check
   *
   * @param requery requery
   * @return array of payment selection lines
   */
  public MPaySelectionLine[] getPaySelectionLines(boolean requery) {
    if (m_lines != null && !requery) {
      return m_lines;
    }
    ArrayList<MPaySelectionLine> list = new ArrayList<MPaySelectionLine>();
    String sql = "SELECT * FROM C_PaySelectionLine WHERE C_PaySelectionCheck_ID=? ORDER BY Line";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = prepareStatement(sql);
      pstmt.setInt(1, getC_PaySelectionCheck_ID());
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(new MPaySelectionLine(getCtx(), rs));
    } catch (Exception e) {
      log.log(Level.SEVERE, sql, e);
    } finally {

      rs = null;
      pstmt = null;
    }
    //
    m_lines = new MPaySelectionLine[list.size()];
    list.toArray(m_lines);
    return m_lines;
  } //	getPaySelectionLines

  /**
   * Delete Payment Selection when generated as Draft (Print Preview)
   *
   * @param ctx context
   * @param C_Payment_ID id
   * @param trxName transaction
   * @return
   */
  public static boolean deleteGeneratedDraft(Properties ctx, int C_Payment_ID) {

    MPaySelectionCheck mpsc = MPaySelectionCheck.getOfPayment(ctx, C_Payment_ID);

    if (mpsc != null && mpsc.isGeneratedDraft()) {
      MPaySelection mps = new MPaySelection(ctx, mpsc.getC_PaySelection_ID());
      MPaySelectionLine[] mpsl = mps.getLines(true);

      // Delete Pay Selection lines
      for (int i = 0; i < mpsl.length; i++) {
        if (!mpsl[i].delete(true)) return false;
      }
      // Delete Pay Selection Check
      if (!mpsc.delete(true)) return false;

      // Delete Pay Selection
      if (!mps.delete(true)) return false;
    }
    return true;
  }
} //  MPaySelectionCheck
