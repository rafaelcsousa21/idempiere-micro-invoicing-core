package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.docengine.DocumentEngine;
import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.orm.MDocType;
import org.compiere.orm.MSequence;
import org.compiere.orm.PO;
import org.compiere.process.CompleteActionResult;
import org.compiere.process.DocAction;
import org.compiere.util.Msg;
import org.compiere.validation.ModelValidationEngine;
import org.compiere.validation.ModelValidator;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Journal Batch Model
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * <li>FR [ 1948157 ] Is necessary the reference for document reverse
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 1776045 ] Add ReActivate action to GL Journal
 * @version $Id: MJournalBatch.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * @see http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962
 * <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 */
public class MJournalBatch extends X_GL_JournalBatch implements DocAction, IPODoc {
    /**
     *
     */
    private static final long serialVersionUID = -2494833602067696046L;
    /**
     * Process Message
     */
    private String m_processMsg = null;
    /**
     * Just Prepared Flag
     */
    private boolean m_justPrepared = false;

    /**
     * ************************************************************************ Standard Construvtore
     *
     * @param ctx                context
     * @param GL_JournalBatch_ID id if 0 - create actual batch
     */
    public MJournalBatch(Properties ctx, int GL_JournalBatch_ID) {
        super(ctx, GL_JournalBatch_ID);
        if (GL_JournalBatch_ID == 0) {
            //	setGLJournalBatch_ID (0);	PK
            //	setDescription (null);
            //	setDocumentNo (null);
            //	setDocumentTypeId (0);
            setPostingType(X_GL_JournalBatch.POSTINGTYPE_Actual);
            setDocAction(X_GL_JournalBatch.DOCACTION_Complete);
            setDocStatus(X_GL_JournalBatch.DOCSTATUS_Drafted);
            setTotalCr(Env.ZERO);
            setTotalDr(Env.ZERO);
            setProcessed(false);
            setProcessing(false);
            setIsApproved(false);
        }
    } //	MJournalBatch

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MJournalBatch(Properties ctx, Row row) {
        super(ctx, row);
    } //	MJournalBatch

    /**
     * Copy Constructor. Dos not copy: Dates/Period
     *
     * @param original original
     */
    public MJournalBatch(MJournalBatch original) {
        this(original.getCtx(), 0);
        setClientOrg(original);
        //
        setGLCategoryId(original.getGLCategoryId());
        setPostingType(original.getPostingType());
        setDescription(original.getDescription());
        setDocumentTypeId(original.getDocumentTypeId());
        setControlAmt(original.getControlAmt());
        //
        setCurrencyId(original.getCurrencyId());
    } //	MJournal

    /**
     * Overwrite Client/Org if required
     *
     * @param AD_Client_ID client
     * @param AD_Org_ID    org
     */
    public void setClientOrg(int AD_Client_ID, int AD_Org_ID) {
        super.setClientOrg(AD_Client_ID, AD_Org_ID);
    } //	setClientOrg

    /**
     * Set Accounting Date. Set also Period if not set earlier
     *
     * @param DateAcct date
     */
    public void setDateAcct(Timestamp DateAcct) {
        super.setDateAcct(DateAcct);
        if (DateAcct == null) return;
        if (getPeriodId() != 0) return;
        int C_Period_ID = MPeriod.getPeriodId(getCtx(), DateAcct, getOrgId());
        if (C_Period_ID == 0) log.warning("Period not found");
        else setPeriodId(C_Period_ID);
    } //	setDateAcct

    /**
     * Get Journal Lines
     *
     * @param requery requery
     * @return Array of lines
     */
    public MJournal[] getJournals(boolean requery) {
        return MBaseJournalBatchKt.getJournalLines(getCtx(), getGLJournalBatchId());
    } //	getJournals

    /**
     * Copy Journal/Lines from other Journal Batch
     *
     * @param jb Journal Batch
     * @return number of journals + lines copied
     */
    public int copyDetailsFrom(MJournalBatch jb) {
        if (isProcessed() || jb == null) return 0;
        int count = 0;
        int lineCount = 0;
        MJournal[] fromJournals = jb.getJournals(false);
        for (int i = 0; i < fromJournals.length; i++) {
            MJournal toJournal = new MJournal(getCtx(), 0);
            PO.copyValues(fromJournals[i], toJournal, getClientId(), getOrgId());
            toJournal.setGLJournalBatchId(getGLJournalBatchId());
            toJournal.setValueNoCheck("DocumentNo", null); // 	create new
            toJournal.setValueNoCheck("C_Period_ID", null);
            toJournal.setDateDoc(getDateDoc()); // 	dates from this Batch
            toJournal.setDateAcct(getDateAcct());
            toJournal.setDocStatus(MJournal.DOCSTATUS_Drafted);
            toJournal.setDocAction(MJournal.DOCACTION_Complete);
            toJournal.setTotalCr(Env.ZERO);
            toJournal.setTotalDr(Env.ZERO);
            toJournal.setIsApproved(false);
            toJournal.setIsPrinted(false);
            toJournal.setPosted(false);
            toJournal.setProcessed(false);
            if (toJournal.save()) {
                count++;
                lineCount += toJournal.copyLinesFrom(fromJournals[i], getDateAcct(), 'x');
            }
        }
        if (fromJournals.length != count)
            log.log(
                    Level.SEVERE, "Line difference - Journals=" + fromJournals.length + " <> Saved=" + count);

        return count + lineCount;
    } //	copyLinesFrom

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
    } //	process

    /**
     * Unlock Document.
     *
     * @return true if success
     */
    public boolean unlockIt() {
        if (log.isLoggable(Level.INFO)) log.info("unlockIt - " + toString());
        setProcessing(false);
        return true;
    } //	unlockIt

    /**
     * Invalidate Document
     *
     * @return true if success
     */
    public boolean invalidateIt() {
        if (log.isLoggable(Level.INFO)) log.info("invalidateIt - " + toString());
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
        MDocType dt = MDocType.get(getCtx(), getDocumentTypeId());

        //	Std Period open?
        if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(), getOrgId())) {
            m_processMsg = "@PeriodClosed@";
            return DocAction.Companion.getSTATUS_Invalid();
        }

        //	Add up Amounts & prepare them
        MJournal[] journals = getJournals(false);
        if (journals.length == 0) {
            m_processMsg = "@NoLines@";
            return DocAction.Companion.getSTATUS_Invalid();
        }

        BigDecimal TotalDr = Env.ZERO;
        BigDecimal TotalCr = Env.ZERO;
        for (int i = 0; i < journals.length; i++) {
            MJournal journal = journals[i];
            if (!journal.isActive()) continue;
            //	Prepare if not closed
            if (X_GL_JournalBatch.DOCSTATUS_Closed.equals(journal.getDocStatus())
                    || X_GL_JournalBatch.DOCSTATUS_Voided.equals(journal.getDocStatus())
                    || X_GL_JournalBatch.DOCSTATUS_Reversed.equals(journal.getDocStatus())
                    || X_GL_JournalBatch.DOCSTATUS_Completed.equals(journal.getDocStatus())) ;
            else {
                String status = journal.prepareIt();
                if (!DocAction.Companion.getSTATUS_InProgress().equals(status)) {
                    journal.setDocStatus(status);
                    journal.saveEx();
                    m_processMsg = journal.getProcessMsg();
                    return status;
                }
                journal.setDocStatus(X_GL_JournalBatch.DOCSTATUS_InProgress);
                journal.saveEx();
            }
            //
            TotalDr = TotalDr.add(journal.getTotalDr());
            TotalCr = TotalCr.add(journal.getTotalCr());
        }
        setTotalDr(TotalDr);
        setTotalCr(TotalCr);

        //	Control Amount
        if (Env.ZERO.compareTo(getControlAmt()) != 0 && getControlAmt().compareTo(getTotalDr()) != 0) {
            m_processMsg = "@ControlAmtError@";
            return DocAction.Companion.getSTATUS_Invalid();
        }

        //		 Bug 1353695 Currency Rate and COnbversion Type should get copied from journal to lines
        for (int i = 0; i < journals.length; i++) {
            MJournal journal = journals[i];
            MJournalLine[] lines = journal.getLines(true);
            if (journal.getCurrencyRate() != null && journal.getCurrencyRate().compareTo(Env.ZERO) != 0) {
                for (int j = 0; j < lines.length; j++) {
                    MJournalLine line = lines[j];
                    line.setCurrencyRate(journal.getCurrencyRate());
                    line.saveEx();
                }
            }
            if (journal.getConversionTypeId() > 0) {
                for (int j = 0; j < lines.length; j++) {
                    MJournalLine line = lines[j];
                    line.setConversionTypeId(journal.getConversionTypeId());
                    line.saveEx();
                }
            }
        }

        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
        if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();

        //	Add up Amounts
        m_justPrepared = true;
        return DocAction.Companion.getSTATUS_InProgress();
    } //	prepareIt

    /**
     * Approve Document
     *
     * @return true if success
     */
    public boolean approveIt() {
        if (log.isLoggable(Level.INFO)) log.info("approveIt - " + toString());
        setIsApproved(true);
        return true;
    } //	approveIt

    /**
     * Reject Approval
     *
     * @return true if success
     */
    public boolean rejectIt() {
        if (log.isLoggable(Level.INFO)) log.info("rejectIt - " + toString());
        setIsApproved(false);
        return true;
    } //	rejectIt

    /**
     * Complete Document
     *
     * @return new status (Complete, In Progress, Invalid, Waiting ..)
     */
    public CompleteActionResult completeIt() {
        if (log.isLoggable(Level.INFO)) log.info("completeIt - " + toString());
        //	Re-Check
        if (!m_justPrepared) {
            String status = prepareIt();
            m_justPrepared = false;
            if (!DocAction.Companion.getSTATUS_InProgress().equals(status))
                return new CompleteActionResult(status);
        }

        // Set the definite document number after completed (if needed)
        setDefiniteDocumentNo();

        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
        if (m_processMsg != null)
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());

        //	Implicit Approval
        approveIt();

        //	Add up Amounts & complete them
        MJournal[] journals = getJournals(true);
        BigDecimal TotalDr = Env.ZERO;
        BigDecimal TotalCr = Env.ZERO;
        for (int i = 0; i < journals.length; i++) {
            MJournal journal = journals[i];
            if (!journal.isActive()) {
                journal.setProcessed(true);
                journal.setDocStatus(X_GL_JournalBatch.DOCSTATUS_Voided);
                journal.setDocAction(X_GL_JournalBatch.DOCACTION_None);
                journal.saveEx();
                continue;
            }
            //	Complete if not closed
            if (X_GL_JournalBatch.DOCSTATUS_Closed.equals(journal.getDocStatus())
                    || X_GL_JournalBatch.DOCSTATUS_Voided.equals(journal.getDocStatus())
                    || X_GL_JournalBatch.DOCSTATUS_Reversed.equals(journal.getDocStatus())
                    || X_GL_JournalBatch.DOCSTATUS_Completed.equals(journal.getDocStatus())) ;
            else {
                // added AdempiereException by zuhri
                if (!journal.processIt(DocAction.Companion.getACTION_Complete()))
                    throw new AdempiereException(
                            "Failed when processing document - " + journal.getProcessMsg());
                // end added
                journal.saveEx();
                if (!DocAction.Companion.getSTATUS_Completed().equals(journal.getDocStatus())) {
                    m_processMsg = journal.getProcessMsg();
                    return new CompleteActionResult(journal.getDocStatus());
                }
            }
            //
            TotalDr = TotalDr.add(journal.getTotalDr());
            TotalCr = TotalCr.add(journal.getTotalCr());
        }
        setTotalDr(TotalDr);
        setTotalCr(TotalCr);
        //	User Validation
        String valid =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
        if (valid != null) {
            m_processMsg = valid;
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
        }

        //
        setProcessed(true);
        setDocAction(X_GL_JournalBatch.DOCACTION_Close);
        return new CompleteActionResult(DocAction.Companion.getSTATUS_Completed());
    } //	completeIt

    /**
     * Set the definite document number after completed
     */
    private void setDefiniteDocumentNo() {
        MDocType dt = MDocType.get(getCtx(), getDocumentTypeId());
        if (dt.isOverwriteDateOnComplete()) {
            setDateDoc(new Timestamp(System.currentTimeMillis()));
            if (getDateAcct().before(getDateDoc())) {
                setDateAcct(getDateDoc());
                MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getDocumentTypeId(), getOrgId());
            }
        }
        if (dt.isOverwriteSeqOnComplete()) {
            String value = MSequence.getDocumentNo(getDocumentTypeId(), null, true, this);
            if (value != null) setDocumentNo(value);
        }
    }

    /**
     * Void Document.
     *
     * @return false
     */
    public boolean voidIt() {
        if (log.isLoggable(Level.INFO)) log.info("voidIt - " + toString());
        // Before Void
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
        if (m_processMsg != null) return false;
        // After Void
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
        if (m_processMsg != null) return false;

        return false;
    } //	voidIt

    /**
     * Close Document.
     *
     * @return true if success
     */
    public boolean closeIt() {
        if (log.isLoggable(Level.INFO)) log.info("closeIt - " + toString());
        // Before Close
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
        if (m_processMsg != null) return false;

        MJournal[] journals = getJournals(true);
        for (int i = 0; i < journals.length; i++) {
            MJournal journal = journals[i];
            if (!journal.isActive() && !journal.isProcessed()) {
                journal.setProcessed(true);
                journal.setDocStatus(X_GL_JournalBatch.DOCSTATUS_Voided);
                journal.setDocAction(X_GL_JournalBatch.DOCACTION_None);
                journal.saveEx();
                continue;
            }
            if (X_GL_JournalBatch.DOCSTATUS_Drafted.equals(journal.getDocStatus())
                    || X_GL_JournalBatch.DOCSTATUS_InProgress.equals(journal.getDocStatus())
                    || X_GL_JournalBatch.DOCSTATUS_Invalid.equals(journal.getDocStatus())) {
                m_processMsg = "Journal not Completed: " + journal.getSummary();
                return false;
            }

            //	Close if not closed
            if (X_GL_JournalBatch.DOCSTATUS_Closed.equals(journal.getDocStatus())
                    || X_GL_JournalBatch.DOCSTATUS_Voided.equals(journal.getDocStatus())
                    || X_GL_JournalBatch.DOCSTATUS_Reversed.equals(journal.getDocStatus())) ;
            else {
                if (!journal.closeIt()) {
                    m_processMsg = "Cannot close: " + journal.getSummary();
                    return false;
                }
                journal.saveEx();
            }
        }
        // After Close
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
        return m_processMsg == null;
    } //	closeIt

    /**
     * Reverse Correction. As if nothing happened - same date
     *
     * @return true if success
     */
    public boolean reverseCorrectIt() {
        if (log.isLoggable(Level.INFO)) log.info("reverseCorrectIt - " + toString());
        // Before reverseCorrect
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
        if (m_processMsg != null) return false;

        MJournal[] journals = getJournals(true);
        //	check prerequisites
        for (int i = 0; i < journals.length; i++) {
            MJournal journal = journals[i];
            if (!journal.isActive()) continue;
            //	All need to be closed/Completed
            if (X_GL_JournalBatch.DOCSTATUS_Completed.equals(journal.getDocStatus())) ;
            else {
                m_processMsg = "All Journals need to be Completed: " + journal.getSummary();
                return false;
            }
        }

        //	Reverse it
        MJournalBatch reverse = new MJournalBatch(this);
        reverse.setDateDoc(getDateDoc());
        reverse.setPeriodId(getPeriodId());
        reverse.setDateAcct(getDateAcct());
        //	Reverse indicator
        StringBuilder msgd = new StringBuilder("(->").append(getDocumentNo()).append(")");
        reverse.addDescription(msgd.toString());
        reverse.setControlAmt(getControlAmt().negate());
        // [ 1948157  ]
        reverse.setReversalId(getGLJournalBatchId());
        reverse.saveEx();

        //	Reverse Journals
        for (int i = 0; i < journals.length; i++) {
            MJournal journal = journals[i];
            if (!journal.isActive()) continue;
            if (journal.reverseCorrectIt(reverse.getGLJournalBatchId()) == null) {
                m_processMsg = "Could not reverse " + journal;
                return false;
            }
            journal.saveEx();
        }
        //
        if (!reverse.processIt(DocAction.Companion.getACTION_Complete())) {
            m_processMsg = "Reversal ERROR: " + reverse.getProcessMsg();
            return false;
        }
        reverse.closeIt();
        reverse.setProcessing(false);
        reverse.setDocStatus(X_GL_JournalBatch.DOCSTATUS_Reversed);
        reverse.setDocAction(X_GL_JournalBatch.DOCACTION_None);
        reverse.saveEx();
        //
        msgd = new StringBuilder("(").append(reverse.getDocumentNo()).append("<-)");
        addDescription(msgd.toString());

        setProcessed(true);
        // [ 1948157  ]
        setReversalId(reverse.getGLJournalBatchId());
        setDocStatus(X_GL_JournalBatch.DOCSTATUS_Reversed);
        setDocAction(X_GL_JournalBatch.DOCACTION_None);
        saveEx();
        // After reverseCorrect
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
        return m_processMsg == null;
    } //	reverseCorrectionIt

    /**
     * Reverse Accrual. Flip Dr/Cr - Use Today's date
     *
     * @return true if success
     */
    public boolean reverseAccrualIt() {
        if (log.isLoggable(Level.INFO)) log.info("reverseAccrualIt - " + toString());
        // Before reverseAccrual
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
        if (m_processMsg != null) return false;

        MJournal[] journals = getJournals(true);
        //	check prerequisites
        for (int i = 0; i < journals.length; i++) {
            MJournal journal = journals[i];
            if (!journal.isActive()) continue;
            //	All need to be closed/Completed
            if (X_GL_JournalBatch.DOCSTATUS_Completed.equals(journal.getDocStatus())) ;
            else {
                m_processMsg = "All Journals need to be Completed: " + journal.getSummary();
                return false;
            }
        }
        //	Reverse it
        MJournalBatch reverse = new MJournalBatch(this);
        reverse.setPeriodId(0);
        Timestamp reversalDate = Env.getContextAsDate(getCtx(), "#Date");
        if (reversalDate == null) {
            reversalDate = new Timestamp(System.currentTimeMillis());
        }
        reverse.setDateDoc(reversalDate);
        reverse.setDateAcct(reversalDate);
        //	Reverse indicator
        StringBuilder msgd = new StringBuilder("(->").append(getDocumentNo()).append(")");
        reverse.addDescription(msgd.toString());
        reverse.setReversalId(getGLJournalBatchId());
        reverse.saveEx();

        //	Reverse Journals
        for (int i = 0; i < journals.length; i++) {
            MJournal journal = journals[i];
            if (!journal.isActive()) continue;
            if (journal.reverseAccrualIt(reverse.getGLJournalBatchId()) == null) {
                m_processMsg = "Could not reverse " + journal;
                return false;
            }
            journal.saveEx();
        }
        //
        if (!reverse.processIt(DocAction.Companion.getACTION_Complete())) {
            m_processMsg = "Reversal ERROR: " + reverse.getProcessMsg();
            return false;
        }
        reverse.closeIt();
        reverse.setProcessing(false);
        reverse.setDocStatus(X_GL_JournalBatch.DOCSTATUS_Reversed);
        reverse.setDocAction(X_GL_JournalBatch.DOCACTION_None);
        reverse.saveEx();
        //
        msgd = new StringBuilder("(").append(reverse.getDocumentNo()).append("<-)");
        addDescription(msgd.toString());

        setProcessed(true);
        setReversalId(reverse.getGLJournalBatchId());
        setDocStatus(X_GL_JournalBatch.DOCSTATUS_Reversed);
        setDocAction(X_GL_JournalBatch.DOCACTION_None);
        saveEx();
        // After reverseAccrual
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
        return m_processMsg == null;
    } //	reverseAccrualIt

    /**
     * Re-activate - same as reverse correct
     *
     * @return true if success
     */
    public boolean reActivateIt() {
        if (log.isLoggable(Level.INFO)) log.info("reActivateIt - " + toString());

        // Before reActivate
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
        if (m_processMsg != null) return false;

        for (MJournal journal : getJournals(true)) {
            if (X_GL_JournalBatch.DOCSTATUS_Completed.equals(journal.getDocStatus())) {
                if (journal.processIt(X_GL_JournalBatch.DOCACTION_Re_Activate)) {
                    journal.saveEx();
                } else {
                    throw new AdempiereException(journal.getProcessMsg());
                }
            }
        }
        setProcessed(false);
        setDocAction(X_GL_JournalBatch.DOCACTION_Complete);

        // After reActivate
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
        return m_processMsg == null;
    } //	reActivateIt

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
                .append(Msg.translate(getCtx(), "TotalDr"))
                .append("=")
                .append(getTotalDr())
                .append(" ")
                .append(Msg.translate(getCtx(), "TotalCR"))
                .append("=")
                .append(getTotalCr())
                .append(" (#")
                .append(getJournals(false).length)
                .append(")");
        //	 - Description
        if (getDescription() != null && getDescription().length() > 0)
            sb.append(" - ").append(getDescription());
        return sb.toString();
    } //	getSummary

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MJournalBatch[");
        sb.append(getId())
                .append(",")
                .append(getDescription())
                .append(",DR=")
                .append(getTotalDr())
                .append(",CR=")
                .append(getTotalCr())
                .append("]");
        return sb.toString();
    } //	toString

    /**
     * Get Document Info
     *
     * @return document info (untranslated)
     */
    public String getDocumentInfo() {
        MDocType dt = MDocType.get(getCtx(), getDocumentTypeId());
        StringBuilder msgreturn =
                new StringBuilder().append(dt.getNameTrl()).append(" ").append(getDocumentNo());
        return msgreturn.toString();
    } //	getDocumentInfo

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
     * @return AD_User_ID (Created By)
     */
    public int getDoc_UserId() {
        return getCreatedBy();
    } //	getDoc_User_ID

    /**
     * Get Document Approval Amount
     *
     * @return DR amount
     */
    public BigDecimal getApprovalAmt() {
        return getTotalDr();
    } //	getApprovalAmt

    public void addDescription(String description) {
        String desc = getDescription();
        if (desc == null) setDescription(description);
        else {
            StringBuilder msgd = new StringBuilder(desc).append(" | ").append(description);
            setDescription(msgd.toString());
        }
    }

    @Override
    public void setDoc(IDoc doc) {
    }

    @Override
    public void setProcessedOn(String processed, boolean b, boolean b1) {
    }
} //	MJournalBatch
