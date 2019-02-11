package org.compiere.accounting;

import org.compiere.crm.MBPartner;
import org.compiere.docengine.DocumentEngine;
import org.compiere.invoicing.MConversionRate;
import org.compiere.invoicing.MInvoice;
import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;
import org.compiere.orm.*;
import org.compiere.process.CompleteActionResult;
import org.compiere.process.DocAction;
import org.compiere.product.MCurrency;
import org.compiere.util.Msg;
import org.compiere.validation.ModelValidationEngine;
import org.compiere.validation.ModelValidator;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.orm.POKt.I_ZERO;
import static software.hsharp.core.util.DBKt.*;

/**
 * Payment Allocation Model. Allocation Trigger update C_BPartner
 *
 * @author Jorg Janke
 * @version $Id: MAllocationHdr.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *     <li>FR [ 1866214 ]
 *     <li>http://sourceforge.net/tracker/index.php?func=detail&aid=1866214&group_id=176962&atid=879335
 *     <li>FR [ 2520591 ] Support multiples calendar for Org
 *     <li>http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 *     <li>BF [ 2880182 ] Error you can allocate a payment to invoice that was paid
 *     <li>https://sourceforge.net/tracker/index.php?func=detail&aid=2880182&group_id=176962&atid=879332
 */
public class MAllocationHdr extends X_C_AllocationHdr implements DocAction, IPODoc {
  /** */
  private static final long serialVersionUID = -7787519874581251920L;
  /** Tolerance Gain and Loss */
  private static final BigDecimal TOLERANCE = BigDecimal.valueOf(0.02);

  /**
   * Get Allocations of Payment
   *
   * @param ctx context
   * @param C_Payment_ID payment
   * @return allocations of payment
   * @param trxName transaction
   */
  public static MAllocationHdr[] getOfPayment(Properties ctx, int C_Payment_ID, String trxName) {
    String sql =
        "SELECT * FROM C_AllocationHdr h "
            + "WHERE IsActive='Y'"
            + " AND EXISTS (SELECT * FROM C_AllocationLine l "
            + "WHERE h.C_AllocationHdr_ID=l.C_AllocationHdr_ID AND l.C_Payment_ID=?)";
    ArrayList<MAllocationHdr> list = new ArrayList<MAllocationHdr>();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = prepareStatement(sql);
      pstmt.setInt(1, C_Payment_ID);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(new MAllocationHdr(ctx, rs, trxName));
    } catch (Exception e) {
      s_log.log(Level.SEVERE, sql, e);
    } finally {

      rs = null;
      pstmt = null;
    }
    MAllocationHdr[] retValue = new MAllocationHdr[list.size()];
    list.toArray(retValue);
    return retValue;
  } //	getOfPayment

  /**
   * Get Allocations of Invoice
   *
   * @param ctx context
   * @param C_Invoice_ID payment
   * @return allocations of payment
   * @param trxName transaction
   */
  public static MAllocationHdr[] getOfInvoice(Properties ctx, int C_Invoice_ID, String trxName) {
    String sql =
        "SELECT * FROM C_AllocationHdr h "
            + "WHERE IsActive='Y'"
            + " AND EXISTS (SELECT * FROM C_AllocationLine l "
            + "WHERE h.C_AllocationHdr_ID=l.C_AllocationHdr_ID AND l.C_Invoice_ID=?)";
    ArrayList<MAllocationHdr> list = new ArrayList<MAllocationHdr>();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = prepareStatement(sql);
      pstmt.setInt(1, C_Invoice_ID);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(new MAllocationHdr(ctx, rs, trxName));
    } catch (Exception e) {
      s_log.log(Level.SEVERE, sql, e);
    } finally {

      rs = null;
      pstmt = null;
    }
    MAllocationHdr[] retValue = new MAllocationHdr[list.size()];
    list.toArray(retValue);
    return retValue;
  } //	getOfInvoice

  // FR [ 1866214 ]
  /**
   * Get Allocations of Cash
   *
   * @param ctx context
   * @param C_Cash_ID Cash ID
   * @return allocations of payment
   * @param trxName transaction
   */
  public static MAllocationHdr[] getOfCash(Properties ctx, int C_Cash_ID, String trxName) {
    final String whereClause =
        "IsActive='Y'"
            + " AND EXISTS (SELECT 1 FROM C_CashLine cl, C_AllocationLine al "
            + "where cl.C_Cash_ID=? and al.C_CashLine_ID=cl.C_CashLine_ID "
            + "and C_AllocationHdr.C_AllocationHdr_ID=al.C_AllocationHdr_ID)";
    Query query = MTable.get(ctx, I_C_AllocationHdr.Table_ID).createQuery(whereClause, trxName);
    query.setParameters(C_Cash_ID);
    List<MAllocationHdr> list = query.list();
    MAllocationHdr[] retValue = new MAllocationHdr[list.size()];
    list.toArray(retValue);
    return retValue;
  } //	getOfCash

  /** Logger */
  private static CLogger s_log = CLogger.getCLogger(MAllocationHdr.class);

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param C_AllocationHdr_ID id
   * @param trxName transaction
   */
  public MAllocationHdr(Properties ctx, int C_AllocationHdr_ID, String trxName) {
    super(ctx, C_AllocationHdr_ID, trxName);
    if (C_AllocationHdr_ID == 0) {
      //	setDocumentNo (null);
      setDateTrx(new Timestamp(System.currentTimeMillis()));
      setDateAcct(getDateTrx());
      setDocAction(X_C_AllocationHdr.DOCACTION_Complete); // CO
      setDocStatus(X_C_AllocationHdr.DOCSTATUS_Drafted); // DR
      //	setC_Currency_ID (0);
      setApprovalAmt(Env.ZERO);
      setIsApproved(false);
      setIsManual(false);
      //
      setPosted(false);
      setProcessed(false);
      setProcessing(false);
      setC_DocType_ID(MDocType.getDocType("CMA"));
    }
  } //	MAllocation

  /**
   * Mandatory New Constructor
   *
   * @param ctx context
   * @param IsManual manual trx
   * @param DateTrx date (if null today)
   * @param C_Currency_ID currency
   * @param description description
   * @param trxName transaction
   */
  public MAllocationHdr(
      Properties ctx,
      boolean IsManual,
      Timestamp DateTrx,
      int C_Currency_ID,
      String description,
      String trxName) {
    this(ctx, 0, trxName);
    setIsManual(IsManual);
    if (DateTrx != null) {
      setDateTrx(DateTrx);
      setDateAcct(DateTrx);
    }
    setC_Currency_ID(C_Currency_ID);
    if (description != null) setDescription(description);
  } //  create Allocation

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MAllocationHdr(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MAllocation

  /** Lines */
  private MAllocationLine[] m_lines = null;

  /**
   * Get Lines
   *
   * @param requery if true requery
   * @return lines
   */
  public MAllocationLine[] getLines(boolean requery) {
    if (m_lines != null && m_lines.length != 0 && !requery) {
      PO.set_TrxName(m_lines, null);
      return m_lines;
    }
    //
    String sql = "SELECT * FROM C_AllocationLine WHERE C_AllocationHdr_ID=?";
    ArrayList<MAllocationLine> list = new ArrayList<MAllocationLine>();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = prepareStatement(sql);
      pstmt.setInt(1, getC_AllocationHdr_ID());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        MAllocationLine line = new MAllocationLine(getCtx(), rs, null);
        line.setParent(this);
        list.add(line);
      }
    } catch (Exception e) {
      log.log(Level.SEVERE, sql, e);
    } finally {

      rs = null;
      pstmt = null;
    }
    //
    m_lines = new MAllocationLine[list.size()];
    list.toArray(m_lines);
    return m_lines;
  } //	getLines

  /**
   * Set Processed
   *
   * @param processed Processed
   */
  public void setProcessed(boolean processed) {
    super.setProcessed(processed);
    if (getId() == 0) return;
    StringBuilder sql =
        new StringBuilder("UPDATE C_AllocationHdr SET Processed='")
            .append((processed ? "Y" : "N"))
            .append("' WHERE C_AllocationHdr_ID=")
            .append(getC_AllocationHdr_ID());
    int no = executeUpdate(sql.toString());
    m_lines = null;
    if (log.isLoggable(Level.FINE)) log.fine(processed + " - #" + no);
  } //	setProcessed

  /**
   * ************************************************************************ Before Save
   *
   * @param newRecord
   * @return save
   */
  protected boolean beforeSave(boolean newRecord) {
    //	Changed from Not to Active
    if (!newRecord && is_ValueChanged("IsActive") && isActive()) {
      log.severe("Cannot Re-Activate deactivated Allocations");
      return false;
    }
    return true;
  } //	beforeSave

  /**
   * Before Delete.
   *
   * @return true if acct was deleted
   */
  protected boolean beforeDelete() {
    String trxName = null;
    if (trxName == null || trxName.length() == 0) log.warning("No transaction");
    if (isPosted()) {
      MPeriod.testPeriodOpen(
          getCtx(), getDateTrx(), MDocType.DOCBASETYPE_PaymentAllocation,  getOrgId());
      setPosted(false);
      MFactAcct.deleteEx(I_C_AllocationHdr.Table_ID, getId(), trxName);
    }
    //	Mark as Inactive
    setIsActive(false); // 	updated DB for line delete/process
    this.saveEx();

    //	Unlink
    getLines(true);
    if (!updateBP(true)) return false;

    for (int i = 0; i < m_lines.length; i++) {
      MAllocationLine line = m_lines[i];
      line.deleteEx(true, trxName);
    }
    return true;
  } //	beforeDelete

  /**
   * After Save
   *
   * @param newRecord
   * @param success
   * @return success
   */
  protected boolean afterSave(boolean newRecord, boolean success) {
    return success;
  } //	afterSave

  /**
   * ************************************************************************ Process document
   *
   * @param processAction document action
   * @return true if performed
   */
  public boolean processIt(String processAction) {
    m_processMsg = null;
    DocumentEngine engine = new DocumentEngine(this, getDocStatus());
    return engine.processIt(processAction, getDocAction());
  } //	processIt

  /** Process Message */
  private String m_processMsg = null;
  /** Just Prepared Flag */
  private boolean m_justPrepared = false;

  /**
   * Unlock Document.
   *
   * @return true if success
   */
  public boolean unlockIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    setProcessing(false);
    return true;
  } //	unlockIt

  /**
   * Invalidate Document
   *
   * @return true if success
   */
  public boolean invalidateIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    setDocAction(X_C_AllocationHdr.DOCACTION_Prepare);
    return true;
  } //	invalidateIt

  /**
   * Prepare Document
   *
   * @return new status (In Progress or Invalid)
   */
  public String prepareIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
    if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();

    //	Std Period open?
    MPeriod.testPeriodOpen(
        getCtx(), getDateAcct(), MDocType.DOCBASETYPE_PaymentAllocation,  getOrgId());
    getLines(true);
    if (m_lines.length == 0) {
      m_processMsg = "@NoLines@";
      return DocAction.Companion.getSTATUS_Invalid();
    }

    // Stop the Document Workflow if invoice to allocate is as paid
    if (!isReversal()) {
      for (MAllocationLine line : m_lines) {
        if (line.getC_Invoice_ID() != 0) {
          StringBuilder whereClause =
              new StringBuilder(I_C_Invoice.COLUMNNAME_C_Invoice_ID)
                  .append("=? AND ")
                  .append(I_C_Invoice.COLUMNNAME_IsPaid)
                  .append("=? AND ")
                  .append(I_C_Invoice.COLUMNNAME_DocStatus)
                  .append(" NOT IN (?,?)");
          boolean InvoiceIsPaid =
              new Query(getCtx(), I_C_Invoice.Table_Name, whereClause.toString(), null)
                  .setClient_ID()
                  .setParameters(
                      line.getC_Invoice_ID(),
                      "Y",
                      I_C_Invoice.DOCSTATUS_Voided,
                      I_C_Invoice.DOCSTATUS_Reversed)
                  .match();
          if (InvoiceIsPaid && line.getAmount().signum() > 0)
            throw new AdempiereException("@ValidationError@ @C_Invoice_ID@ @IsPaid@");
        }
      }
    }

    //	Add up Amounts & validate
    BigDecimal approval = Env.ZERO;
    for (int i = 0; i < m_lines.length; i++) {
      MAllocationLine line = m_lines[i];
      approval = approval.add(line.getWriteOffAmt()).add(line.getDiscountAmt());
      //	Make sure there is BP
      if (line.getC_BPartner_ID() == 0) {
        m_processMsg = "No Business Partner";
        return DocAction.Companion.getSTATUS_Invalid();
      }

      // IDEMPIERE-1850 - validate date against related docs
      if (line.getC_Invoice_ID() > 0) {
        if (line.getC_Invoice().getDateAcct().after(getDateAcct())) {
          m_processMsg = "Wrong allocation date";
          return DocAction.Companion.getSTATUS_Invalid();
        }
      }
      if (line.getC_Payment_ID() > 0) {
        if (line.getC_Payment().getDateAcct().after(getDateAcct())) {
          m_processMsg = "Wrong allocation date";
          return DocAction.Companion.getSTATUS_Invalid();
        }
      }
    }
    setApprovalAmt(approval);
    //
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
    if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();

    m_justPrepared = true;
    if (!X_C_AllocationHdr.DOCACTION_Complete.equals(getDocAction()))
      setDocAction(X_C_AllocationHdr.DOCACTION_Complete);

    return DocAction.Companion.getSTATUS_InProgress();
  } //	prepareIt

  /**
   * Approve Document
   *
   * @return true if success
   */
  public boolean approveIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    setIsApproved(true);
    return true;
  } //	approveIt

  /**
   * Reject Approval
   *
   * @return true if success
   */
  public boolean rejectIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    setIsApproved(false);
    return true;
  } //	rejectIt

  /**
   * Complete Document
   *
   * @return new status (Complete, In Progress, Invalid, Waiting ..)
   */
  public CompleteActionResult completeIt() {
    //	Re-Check
    if (!m_justPrepared) {
      String status = prepareIt();
      m_justPrepared = false;
      if (!DocAction.Companion.getSTATUS_InProgress().equals(status))
        return new CompleteActionResult(status);
    }

    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
    if (m_processMsg != null)
      return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());

    //	Implicit Approval
    if (!isApproved()) approveIt();
    if (log.isLoggable(Level.INFO)) log.info(toString());

    //	Link
    getLines(false);
    if (!updateBP(isReversal()))
      return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());

    for (int i = 0; i < m_lines.length; i++) {
      MAllocationLine line = m_lines[i];
      line.processIt(isReversal());
    }

    //	User Validation
    String valid =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
    if (valid != null) {
      m_processMsg = valid;
      return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
    }

    setProcessed(true);
    setDocAction(X_C_AllocationHdr.DOCACTION_Close);
    return new CompleteActionResult(DocAction.Companion.getSTATUS_Completed());
  } //	completeIt

  /**
   * Void Document. Same as Close.
   *
   * @return true if success
   */
  public boolean voidIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());

    boolean retValue = false;
    if (X_C_AllocationHdr.DOCSTATUS_Closed.equals(getDocStatus())
        || X_C_AllocationHdr.DOCSTATUS_Reversed.equals(getDocStatus())
        || X_C_AllocationHdr.DOCSTATUS_Voided.equals(getDocStatus())) {
      m_processMsg = "Document Closed: " + getDocStatus();
      setDocAction(X_C_AllocationHdr.DOCACTION_None);
      return false;
    }

    //	Not Processed
    if (X_C_AllocationHdr.DOCSTATUS_Drafted.equals(getDocStatus())
        || X_C_AllocationHdr.DOCSTATUS_Invalid.equals(getDocStatus())
        || X_C_AllocationHdr.DOCSTATUS_InProgress.equals(getDocStatus())
        || X_C_AllocationHdr.DOCSTATUS_Approved.equals(getDocStatus())
        || X_C_AllocationHdr.DOCSTATUS_NotApproved.equals(getDocStatus())) {
      // Before Void
      m_processMsg =
          ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
      if (m_processMsg != null) return false;

      //	Set lines to 0
      MAllocationLine[] lines = getLines(true);
      if (!updateBP(true)) return false;

      for (int i = 0; i < lines.length; i++) {
        MAllocationLine line = lines[i];
        line.setAmount(Env.ZERO);
        line.setDiscountAmt(Env.ZERO);
        line.setWriteOffAmt(Env.ZERO);
        line.setOverUnderAmt(Env.ZERO);
        line.saveEx();
        // Unlink invoices
        line.processIt(true);
      }

      addDescription(Msg.getMsg(getCtx(), "Voided"));
      retValue = true;
    } else {
      boolean accrual = false;
      try {
        MPeriod.testPeriodOpen(
            getCtx(), getDateTrx(), MPeriodControl.DOCBASETYPE_PaymentAllocation,  getOrgId());
      } catch (PeriodClosedException e) {
        accrual = true;
      }

      if (accrual) return reverseAccrualIt();
      else return reverseCorrectIt();
    }

    // After Void
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
    if (m_processMsg != null) return false;

    setProcessed(true);
    setDocAction(X_C_AllocationHdr.DOCACTION_None);

    return retValue;
  } //	voidIt

  /**
   * Close Document. Cancel not delivered Qunatities
   *
   * @return true if success
   */
  public boolean closeIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    // Before Close
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
    if (m_processMsg != null) return false;

    setDocAction(X_C_AllocationHdr.DOCACTION_None);

    // After Close
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
    if (m_processMsg != null) return false;

    return true;
  } //	closeIt

  /**
   * Reverse Correction
   *
   * @return true if success
   */
  public boolean reverseCorrectIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    // Before reverseCorrect
    m_processMsg =
        ModelValidationEngine.get()
            .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
    if (m_processMsg != null) return false;

    boolean retValue = reverseIt(false);

    // After reverseCorrect
    m_processMsg =
        ModelValidationEngine.get()
            .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
    if (m_processMsg != null) return false;

    setDocAction(X_C_AllocationHdr.DOCACTION_None);
    return retValue;
  } //	reverseCorrectionIt

  /**
   * Reverse Accrual - none
   *
   * @return false
   */
  public boolean reverseAccrualIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    // Before reverseAccrual
    m_processMsg =
        ModelValidationEngine.get()
            .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
    if (m_processMsg != null) return false;

    boolean retValue = reverseIt(true);

    // After reverseAccrual
    m_processMsg =
        ModelValidationEngine.get()
            .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
    if (m_processMsg != null) return false;

    setDocAction(X_C_AllocationHdr.DOCACTION_None);
    return retValue;
  } //	reverseAccrualIt

  /**
   * Re-activate
   *
   * @return false
   */
  public boolean reActivateIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    // Before reActivate
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
    if (m_processMsg != null) return false;

    // After reActivate
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
    if (m_processMsg != null) return false;

    return false;
  } //	reActivateIt

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("MAllocationHdr[");
    sb.append(getId()).append("-").append(getSummary()).append("]");
    return sb.toString();
  } //	toString

  /**
   * Get Document Info
   *
   * @return document info (untranslated)
   */
  public String getDocumentInfo() {
    StringBuilder msgreturn =
        new StringBuilder()
            .append(Msg.getElement(getCtx(), "C_AllocationHdr_ID"))
            .append(" ")
            .append(getDocumentNo());
    return msgreturn.toString();
  } //	getDocumentInfo

    /**
   * *********************************************************************** Get Summary
   *
   * @return Summary of Document
   */
  public String getSummary() {
    StringBuilder sb = new StringBuilder();
    sb.append(getDocumentNo());
    //	: Total Lines = 123.00 (#1)
    sb.append(": ")
        .append(Msg.translate(getCtx(), "ApprovalAmt"))
        .append("=")
        .append(getApprovalAmt())
        .append(" (#")
        .append(getLines(false).length)
        .append(")");
    //	 - Description
    if (getDescription() != null && getDescription().length() > 0)
      sb.append(" - ").append(getDescription());
    return sb.toString();
  } //	getSummary

  /**
   * Get Process Message
   *
   * @return clear text error message
   */
  public String getProcessMsg() {
    return m_processMsg;
  } //	getProcessMsg

  /**
   * Get Document Owner (Responsible)
   *
   * @return AD_User_ID
   */
  public int getDoc_User_ID() {
    return getCreatedBy();
  } //	getDoc_User_ID

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
   * ************************************************************************ Reverse Allocation.
   * Period needs to be open
   *
   * @return true if reversed
   */
  private boolean reverseIt(boolean accrual) {
    if (!isActive()
        || getDocStatus().equals(X_C_AllocationHdr.DOCSTATUS_Voided) // Goodwill.co.id
        || getDocStatus().equals(X_C_AllocationHdr.DOCSTATUS_Reversed)) {
      // Goodwill: don't throw exception here
      //	BF: Reverse is not allowed at Payment void when Allocation is already reversed at Invoice
      // void
      // throw new IllegalStateException("Allocation already reversed (not active)");
      log.warning("Allocation already reversed (not active)");
      return true;
    }

    Timestamp reversalDate = accrual ? Env.getContextAsDate(getCtx(), "#Date") : getDateAcct();
    if (reversalDate == null) {
      reversalDate = new Timestamp(System.currentTimeMillis());
    }

    //	Can we delete posting
    MPeriod.testPeriodOpen(
        getCtx(), reversalDate, MPeriodControl.DOCBASETYPE_PaymentAllocation,  getOrgId());

    if (accrual) {
      //	Deep Copy
      MAllocationHdr reversal = copyFrom(this, reversalDate, reversalDate, null);
      if (reversal == null) {
        m_processMsg = "Could not create Payment Allocation Reversal";
        return false;
      }
      reversal.setReversal_ID(getC_AllocationHdr_ID());

      //	Reverse Line Amt
      MAllocationLine[] rLines = reversal.getLines(false);
      for (MAllocationLine rLine : rLines) {
        rLine.setAmount(rLine.getAmount().negate());
        rLine.setDiscountAmt(rLine.getDiscountAmt().negate());
        rLine.setWriteOffAmt(rLine.getWriteOffAmt().negate());
        rLine.setOverUnderAmt(rLine.getOverUnderAmt().negate());
        if (!rLine.save(null)) {
          m_processMsg = "Could not correct Payment Allocation Reversal Line";
          return false;
        }
      }
      reversal.setReversal(true);
      reversal.setDocumentNo(getDocumentNo() + "^");
      reversal.addDescription("{->" + getDocumentNo() + ")");
      //
      if (!DocumentEngine.processIt(reversal, DocAction.Companion.getACTION_Complete())) {
        m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
        return false;
      }

      DocumentEngine.processIt(reversal, DocAction.Companion.getACTION_Close());
      reversal.setProcessing(false);
      reversal.setDocStatus(X_C_AllocationHdr.DOCSTATUS_Reversed);
      reversal.setDocAction(X_C_AllocationHdr.DOCACTION_None);
      reversal.saveEx();
      m_processMsg = reversal.getDocumentNo();
      addDescription("(" + reversal.getDocumentNo() + "<-)");

    } else {
      //	Set Inactive
      setIsActive(false);
      if (!isPosted()) setPosted(true);
      setDocumentNo(getDocumentNo() + "^");
      setDocStatus(X_C_AllocationHdr.DOCSTATUS_Reversed); // 	for direct calls
      if (!save() || isActive()) throw new IllegalStateException("Cannot de-activate allocation");

      //	Delete Posting
      MFactAcct.deleteEx(MAllocationHdr.Table_ID, getC_AllocationHdr_ID(), null);

      //	Unlink Invoices
      getLines(true);
      if (!updateBP(true)) return false;

      for (int i = 0; i < m_lines.length; i++) {
        MAllocationLine line = m_lines[i];
        line.setIsActive(false);
        line.setAmount(Env.ZERO);
        line.setDiscountAmt(Env.ZERO);
        line.setWriteOffAmt(Env.ZERO);
        line.setOverUnderAmt(Env.ZERO);
        line.saveEx();
        line.processIt(true); // 	reverse
      }

      addDescription(Msg.getMsg(getCtx(), "Voided"));
    }

    setProcessed(true);
    setDocStatus(X_C_AllocationHdr.DOCSTATUS_Reversed); // 	may come from void
    setDocAction(X_C_AllocationHdr.DOCACTION_None);
    return true;
  } //	reverse

  private boolean updateBP(boolean reverse) {

    getLines(false);
    for (MAllocationLine line : m_lines) {
      int C_Payment_ID = line.getC_Payment_ID();
      int C_BPartner_ID = line.getC_BPartner_ID();
      int M_Invoice_ID = line.getC_Invoice_ID();

      if ((C_BPartner_ID == 0) || ((M_Invoice_ID == 0) && (C_Payment_ID == 0))) continue;

      boolean isSOTrxInvoice = false;
      MInvoice invoice =
          M_Invoice_ID > 0 ? new MInvoice(getCtx(), M_Invoice_ID, null) : null;
      if (M_Invoice_ID > 0) isSOTrxInvoice = invoice.isSOTrx();

      MBPartner bpartner = new MBPartner(getCtx(), line.getC_BPartner_ID(), null);
      forUpdate(bpartner, 0);

      BigDecimal allocAmt = line.getAmount().add(line.getDiscountAmt()).add(line.getWriteOffAmt());
      BigDecimal openBalanceDiff = Env.ZERO;
      org.compiere.accounting.MClient client =
          org.compiere.accounting.MClient.get(getCtx(),  getClientId());

      boolean paymentProcessed = false;
      boolean paymentIsReceipt = false;

      // Retrieve payment information
      if (C_Payment_ID > 0) {
        MPayment payment = null;
        int convTypeID = 0;
        Timestamp paymentDate = null;

        payment = new MPayment(getCtx(), C_Payment_ID, null);
        convTypeID = payment.getC_ConversionType_ID();
        paymentDate = payment.getDateAcct();
        paymentProcessed = payment.isProcessed();
        paymentIsReceipt = payment.isReceipt();

        // Adjust open amount with allocated amount.
        if (paymentProcessed) {
          if (invoice != null) {
            // If payment is already processed, only adjust open balance by discount and write off
            // amounts.
            BigDecimal amt =
                MConversionRate.convertBase(
                    getCtx(),
                    line.getWriteOffAmt().add(line.getDiscountAmt()),
                    getC_Currency_ID(),
                    paymentDate,
                    convTypeID,
                     getClientId(),
                     getOrgId());
            if (amt == null) {
              m_processMsg =
                  MConversionRateUtil.getErrorMessage(
                      getCtx(),
                      "ErrorConvertingAllocationCurrencyToBaseCurrency",
                      getC_Currency_ID(),
                      org.compiere.accounting.MClient.get(getCtx()).getC_Currency_ID(),
                      convTypeID,
                      paymentDate,
                      null);
              return false;
            }
            openBalanceDiff = openBalanceDiff.add(amt);
          } else {
            // Allocating payment to payment.
            BigDecimal amt =
                MConversionRate.convertBase(
                    getCtx(),
                    allocAmt,
                    getC_Currency_ID(),
                    paymentDate,
                    convTypeID,
                     getClientId(),
                     getOrgId());
            if (amt == null) {
              m_processMsg =
                  MConversionRateUtil.getErrorMessage(
                      getCtx(),
                      "ErrorConvertingAllocationCurrencyToBaseCurrency",
                      getC_Currency_ID(),
                      org.compiere.accounting.MClient.get(getCtx()).getC_Currency_ID(),
                      convTypeID,
                      paymentDate,
                      null);
              return false;
            }
            openBalanceDiff = openBalanceDiff.add(amt);
          }
        } else {
          // If payment has not been processed, adjust open balance by entire allocated amount.
          BigDecimal allocAmtBase =
              MConversionRate.convertBase(
                  getCtx(),
                  allocAmt,
                  getC_Currency_ID(),
                  getDateAcct(),
                  convTypeID,
                   getClientId(),
                   getOrgId());
          if (allocAmtBase == null) {
            m_processMsg =
                MConversionRateUtil.getErrorMessage(
                    getCtx(),
                    "ErrorConvertingAllocationCurrencyToBaseCurrency",
                    getC_Currency_ID(),
                    org.compiere.accounting.MClient.get(getCtx()).getC_Currency_ID(),
                    convTypeID,
                    getDateAcct(),
                    null);
            return false;
          }

          openBalanceDiff = openBalanceDiff.add(allocAmtBase);
        }
      } else if (invoice != null) {
        // adjust open balance by discount and write off amounts.
        BigDecimal amt =
            MConversionRate.convertBase(
                getCtx(),
                line.getWriteOffAmt().add(line.getDiscountAmt()),
                getC_Currency_ID(),
                invoice.getDateAcct(),
                invoice.getC_ConversionType_ID(),
                 getClientId(),
                 getOrgId());
        if (amt == null) {
          m_processMsg =
              MConversionRateUtil.getErrorMessage(
                  getCtx(),
                  "ErrorConvertingAllocationCurrencyToBaseCurrency",
                  getC_Currency_ID(),
                  org.compiere.accounting.MClient.get(getCtx()).getC_Currency_ID(),
                  invoice.getC_ConversionType_ID(),
                  invoice.getDateAcct(),
                  null);
          return false;
        }
        openBalanceDiff = openBalanceDiff.add(amt);
      }

      // Adjust open amount for currency gain/loss
      if ((invoice != null)
          && ((getC_Currency_ID() != client.getC_Currency_ID())
              || (getC_Currency_ID() != invoice.getC_Currency_ID()))) {
        if (getC_Currency_ID() != invoice.getC_Currency_ID()) {
          allocAmt =
              MConversionRate.convert(
                  getCtx(),
                  allocAmt,
                  getC_Currency_ID(),
                  invoice.getC_Currency_ID(),
                  getDateAcct(),
                  invoice.getC_ConversionType_ID(),
                   getClientId(),
                   getOrgId());
          if (allocAmt == null) {
            m_processMsg =
                MConversionRateUtil.getErrorMessage(
                    getCtx(),
                    "ErrorConvertingAllocationCurrencyToInvoiceCurrency",
                    getC_Currency_ID(),
                    invoice.getC_Currency_ID(),
                    invoice.getC_ConversionType_ID(),
                    getDateAcct(),
                    null);
            return false;
          }
        }
        BigDecimal invAmtAccted =
            MConversionRate.convertBase(
                getCtx(),
                invoice.getGrandTotal(),
                invoice.getC_Currency_ID(),
                invoice.getDateAcct(),
                invoice.getC_ConversionType_ID(),
                 getClientId(),
                 getOrgId());
        if (invAmtAccted == null) {
          m_processMsg =
              MConversionRateUtil.getErrorMessage(
                  getCtx(),
                  "ErrorConvertingInvoiceCurrencyToBaseCurrency",
                  invoice.getC_Currency_ID(),
                  org.compiere.accounting.MClient.get(getCtx()).getC_Currency_ID(),
                  invoice.getC_ConversionType_ID(),
                  invoice.getDateAcct(),
                  null);
          return false;
        }

        BigDecimal allocAmtAccted =
            MConversionRate.convertBase(
                getCtx(),
                allocAmt,
                invoice.getC_Currency_ID(),
                getDateAcct(),
                invoice.getC_ConversionType_ID(),
                 getClientId(),
                 getOrgId());
        if (allocAmtAccted == null) {
          m_processMsg =
              MConversionRateUtil.getErrorMessage(
                  getCtx(),
                  "ErrorConvertingInvoiceCurrencyToBaseCurrency",
                  invoice.getC_Currency_ID(),
                  MClient.get(getCtx()).getC_Currency_ID(),
                  invoice.getC_ConversionType_ID(),
                  getDateAcct(),
                  null);
          return false;
        }

        if (allocAmt.compareTo(invoice.getGrandTotal()) == 0) {
          openBalanceDiff = openBalanceDiff.add(invAmtAccted).subtract(allocAmtAccted);
        } else {
          //	allocation as a percentage of the invoice
          double multiplier = allocAmt.doubleValue() / invoice.getGrandTotal().doubleValue();
          //	Reduce Orig Invoice Accounted
          invAmtAccted = invAmtAccted.multiply(BigDecimal.valueOf(multiplier));
          //	Difference based on percentage of Orig Invoice
          openBalanceDiff =
              openBalanceDiff.add(invAmtAccted).subtract(allocAmtAccted); // 	gain is negative
          //	ignore Tolerance
          if (openBalanceDiff.abs().compareTo(TOLERANCE) < 0) openBalanceDiff = Env.ZERO;
          //	Round
          int precision = MCurrency.getStdPrecision(getCtx(), client.getC_Currency_ID());
          if (openBalanceDiff.scale() > precision)
            openBalanceDiff = openBalanceDiff.setScale(precision, BigDecimal.ROUND_HALF_UP);
        }
      }

      //	Total Balance
      BigDecimal newBalance = bpartner.getTotalOpenBalance();
      if (newBalance == null) newBalance = Env.ZERO;

      BigDecimal originalBalance = new BigDecimal(newBalance.toString());

      if (openBalanceDiff.signum() != 0) {
        if (reverse) newBalance = newBalance.add(openBalanceDiff);
        else newBalance = newBalance.subtract(openBalanceDiff);
      }

      // Update BP Credit Used only for Customer Invoices and for payment-to-payment allocations.
      BigDecimal newCreditAmt = Env.ZERO;
      if (isSOTrxInvoice || (invoice == null && paymentIsReceipt && paymentProcessed)) {
        if (invoice == null) openBalanceDiff = openBalanceDiff.negate();

        newCreditAmt = bpartner.getSO_CreditUsed();

        if (reverse) {
          if (newCreditAmt == null) newCreditAmt = openBalanceDiff;
          else newCreditAmt = newCreditAmt.add(openBalanceDiff);
        } else {
          if (newCreditAmt == null) newCreditAmt = openBalanceDiff.negate();
          else newCreditAmt = newCreditAmt.subtract(openBalanceDiff);
        }

        if (log.isLoggable(Level.FINE)) {
          log.fine(
              "TotalOpenBalance="
                  + bpartner.getTotalOpenBalance()
                  + "("
                  + openBalanceDiff
                  + ", Credit="
                  + bpartner.getSO_CreditUsed()
                  + "->"
                  + newCreditAmt
                  + ", Balance="
                  + bpartner.getTotalOpenBalance()
                  + " -> "
                  + newBalance);
        }
        bpartner.setSO_CreditUsed(newCreditAmt);
      } else {
        if (log.isLoggable(Level.FINE)) {
          log.fine(
              "TotalOpenBalance="
                  + bpartner.getTotalOpenBalance()
                  + "("
                  + openBalanceDiff
                  + ", Balance="
                  + bpartner.getTotalOpenBalance()
                  + " -> "
                  + newBalance);
        }
      }

      if (newBalance.compareTo(originalBalance) != 0) bpartner.setTotalOpenBalance(newBalance);

      bpartner.setSOCreditStatus();
      if (!bpartner.save(null)) {
        m_processMsg = "Could not update Business Partner";
        return false;
      }
    } // for all lines

    return true;
  } //	updateBP

  /**
   * Document Status is Complete or Closed
   *
   * @return true if CO, CL or RE
   */
  public boolean isComplete() {
    String ds = getDocStatus();
    return X_C_AllocationHdr.DOCSTATUS_Completed.equals(ds)
        || X_C_AllocationHdr.DOCSTATUS_Closed.equals(ds)
        || X_C_AllocationHdr.DOCSTATUS_Reversed.equals(ds);
  } //	isComplete

  /**
   * Create new Allocation by copying
   *
   * @param from allocation
   * @param dateAcct date of the document accounting date
   * @param dateTrx date of the document transaction.
   * @param trxName
   * @return Allocation
   */
  public static MAllocationHdr copyFrom(
      MAllocationHdr from, Timestamp dateAcct, Timestamp dateTrx, String trxName) {
    MAllocationHdr to = new MAllocationHdr(from.getCtx(), 0, trxName);
    PO.copyValues(from, to, from. getClientId(), from. getOrgId());
    to.set_ValueNoCheck("DocumentNo", null);
    //
    to.setDocStatus(X_C_AllocationHdr.DOCSTATUS_Drafted); // 	Draft
    to.setDocAction(X_C_AllocationHdr.DOCACTION_Complete);
    //
    to.setDateTrx(dateAcct);
    to.setDateAcct(dateTrx);
    to.setIsManual(false);
    //
    to.setIsApproved(false);
    //
    to.setPosted(false);
    to.setProcessed(false);

    to.saveEx();

    //	Lines
    if (to.copyLinesFrom(from) == 0)
      throw new AdempiereException("Could not create Allocation Lines");

    return to;
  } //	copyFrom

  /**
   * Copy Lines From other Allocation.
   *
   * @param otherAllocation allocation
   * @return number of lines copied
   */
  public int copyLinesFrom(MAllocationHdr otherAllocation) {
    if (isProcessed() || isPosted() || (otherAllocation == null)) return 0;
    MAllocationLine[] fromLines = otherAllocation.getLines(false);
    int count = 0;
    for (MAllocationLine fromLine : fromLines) {
      MAllocationLine line = new MAllocationLine(getCtx(), 0, null);
      PO.copyValues(fromLine, line, fromLine. getClientId(), fromLine. getOrgId());
      line.setC_AllocationHdr_ID(getC_AllocationHdr_ID());
      line.setParent(this);
      line.set_ValueNoCheck("C_AllocationLine_ID", I_ZERO); // new

      if (line.getC_Payment_ID() != 0) {
        MPayment payment = new MPayment(getCtx(), line.getC_Payment_ID(), null);
        if (X_C_AllocationHdr.DOCSTATUS_Reversed.equals(payment.getDocStatus())) {
          MPayment reversal = (MPayment) payment.getReversal();
          if (reversal != null) {
            line.setPaymentInfo(reversal.getC_Payment_ID(), 0);
          }
        }
      }

      line.saveEx();
      count++;
    }
    if (fromLines.length != count)
      log.log(Level.WARNING, "Line difference - From=" + fromLines.length + " <> Saved=" + count);
    return count;
  } //	copyLinesFrom

  // Goodwill.co.id
  /** Reversal Flag */
  private boolean m_reversal = false;

  /**
   * Set Reversal
   *
   * @param reversal reversal
   */
  private void setReversal(boolean reversal) {
    m_reversal = reversal;
  } //	setReversal

  /**
   * Is Reversal
   *
   * @return reversal
   */
  private boolean isReversal() {
    return m_reversal;
  } //	isReversal

    @Override
  public void setDoc(IDoc doc) {}

  @Override
  public void setProcessedOn(String processed, boolean b, boolean b1) {}
} //  MAllocation
