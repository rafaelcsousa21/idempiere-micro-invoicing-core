package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.docengine.DocumentEngine;
import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.orm.MDocType;
import org.compiere.orm.MSequence;
import org.compiere.orm.PO;
import org.compiere.orm.Query;
import org.compiere.process.CompleteActionResult;
import org.compiere.process.DocAction;
import org.compiere.util.Msg;
import org.compiere.validation.ModelValidationEngine;
import org.compiere.validation.ModelValidator;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdate;

/**
 * GL Journal Model
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>BF [ 1619150 ] Usability/Consistency: reversed gl journal description
 * <li>BF [ 1775358 ] GL Journal DateAcct/C_Period_ID issue
 * <li>FR [ 1776045 ] Add ReActivate action to GL Journal
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * <li>FR [ 1948157 ] Is necessary the reference for document reverse
 * @version $Id: MJournal.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * @see http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962
 * <li>FR: [ 2214883 ] Remove SQL code and Replace for Query
 * <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 */
public class MJournal extends X_GL_Journal implements DocAction, IPODoc {
    /**
     *
     */
    private static final long serialVersionUID = -364132249042527640L;
    /**
     * Process Message
     */
    private String m_processMsg = null;
    /**
     * Just Prepared Flag
     */
    private boolean m_justPrepared = false;

    /**
     * Standard Constructor
     *
     * @param ctx           context
     * @param GL_Journal_ID id
     * @param trxName       transaction
     */
    public MJournal(Properties ctx, int GL_Journal_ID) {
        super(ctx, GL_Journal_ID);
        if (GL_Journal_ID == 0) {
            //	setGLJournal_ID (0);		//	PK
            //	setAccountingSchemaId (0);
            //	setCurrencyId (0);
            //	setDocumentTypeId (0);
            //	setPeriodId (0);
            //
            setCurrencyRate(Env.ONE);
            //	setConversionTypeId(0);
            setDateAcct(new Timestamp(System.currentTimeMillis()));
            setDateDoc(new Timestamp(System.currentTimeMillis()));
            //	setDescription (null);
            setDocAction(X_GL_Journal.DOCACTION_Complete);
            setDocStatus(X_GL_Journal.DOCSTATUS_Drafted);
            //	setDocumentNo (null);
            //	setGLCategoryId (0);
            setPostingType(X_GL_Journal.POSTINGTYPE_Actual);
            setTotalCr(Env.ZERO);
            setTotalDr(Env.ZERO);
            setIsApproved(false);
            setIsPrinted(false);
            setPosted(false);
            setProcessed(false);
        }
    } //	MJournal

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MJournal(Properties ctx, Row row) {
        super(ctx, row);
    } //	MJournal

    /**
     * Parent Constructor.
     *
     * @param parent batch
     */
    public MJournal(MJournalBatch parent) {
        this(parent.getCtx(), 0);
        setClientOrg(parent);
        setGLJournalBatchId(parent.getGLJournalBatchId());
        setDocumentTypeId(parent.getDocumentTypeId());
        setPostingType(parent.getPostingType());
        //
        setDateDoc(parent.getDateDoc());
        setPeriodId(parent.getPeriodId());
        setDateAcct(parent.getDateAcct());
        setCurrencyId(parent.getCurrencyId());
    } //	MJournal

    /**
     * Copy Constructor. Dos not copy: Dates/Period
     *
     * @param original original
     */
    public MJournal(MJournal original) {
        this(original.getCtx(), 0);
        setClientOrg(original);
        setGLJournalBatchId(original.getGLJournalBatchId());
        //
        setAccountingSchemaId(original.getAccountingSchemaId());
        setGLBudgetId(original.getGLBudgetId());
        setGLCategoryId(original.getGLCategoryId());
        setPostingType(original.getPostingType());
        setDescription(original.getDescription());
        setDocumentTypeId(original.getDocumentTypeId());
        setControlAmt(original.getControlAmt());
        //
        setCurrencyId(original.getCurrencyId());
        setConversionTypeId(original.getConversionTypeId());
        setCurrencyRate(original.getCurrencyRate());

        //	setDateDoc(original.getDateDoc());
        //	setDateAcct(original.getDateAcct());
        //	setPeriodId(original.getPeriodId());
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
        if (C_Period_ID == 0) log.warning("setDateAcct - Period not found");
        else setPeriodId(C_Period_ID);
    } //	setDateAcct

    /**
     * Set Currency Info
     *
     * @param C_Currency_ID       currenct
     * @param C_ConversionType_ID type
     * @param CurrencyRate        rate
     */
    public void setCurrency(int C_Currency_ID, int C_ConversionType_ID, BigDecimal CurrencyRate) {
        if (C_Currency_ID != 0) setCurrencyId(C_Currency_ID);
        if (C_ConversionType_ID != 0) setConversionTypeId(C_ConversionType_ID);
        if (CurrencyRate != null && CurrencyRate.compareTo(Env.ZERO) == 0)
            setCurrencyRate(CurrencyRate);
    } //	setCurrency

    /**
     * Add to Description
     *
     * @param description text
     * @since 3.1.4
     */
    public void addDescription(String description) {
        String desc = getDescription();
        if (desc == null) setDescription(description);
        else {
            StringBuilder msgd = new StringBuilder(desc).append(" | ").append(description);
            setDescription(msgd.toString());
        }
    }

    /**
     * ************************************************************************ Get Journal Lines
     *
     * @param requery requery
     * @return Array of lines
     */
    public MJournalLine[] getLines(boolean requery) {
        // FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
        final String whereClause = "GL_Journal_ID=?";
        List<MJournalLine> list =
                new Query(getCtx(), I_GL_JournalLine.Table_Name, whereClause)
                        .setParameters(getGLJournalId())
                        .setOrderBy("Line")
                        .list();
        //
        MJournalLine[] retValue = new MJournalLine[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getLines

    /**
     * Copy Lines from other Journal
     *
     * @param fromJournal Journal
     * @param dateAcct    date used - if null original
     * @param typeCR      type of copying (C)orrect=negate - (R)everse=flip dr/cr - otherwise just copy
     * @return number of lines copied
     */
    public int copyLinesFrom(MJournal fromJournal, Timestamp dateAcct, char typeCR) {
        if (isProcessed() || fromJournal == null) return 0;
        int count = 0;
        MJournalLine[] fromLines = fromJournal.getLines(false);
        for (int i = 0; i < fromLines.length; i++) {
            MJournalLine toLine = new MJournalLine(getCtx(), 0);
            PO.copyValues(fromLines[i], toLine, getClientId(), getOrgId());
            toLine.setGLJournalId(getGLJournalId());
            //
            if (dateAcct != null) toLine.setDateAcct(dateAcct);
            //	Amounts
            if (typeCR == 'C') // 	correct
            {
                toLine.setAmtSourceDr(fromLines[i].getAmtSourceDr().negate());
                toLine.setAmtSourceCr(fromLines[i].getAmtSourceCr().negate());
            } else if (typeCR == 'R') // 	reverse
            {
                toLine.setAmtSourceDr(fromLines[i].getAmtSourceCr());
                toLine.setAmtSourceCr(fromLines[i].getAmtSourceDr());
            }
            toLine.setIsGenerated(true);
            toLine.setProcessed(false);
            if (toLine.save()) count++;
        }
        if (fromLines.length != count)
            log.log(
                    Level.SEVERE,
                    "Line difference - JournalLines=" + fromLines.length + " <> Saved=" + count);

        return count;
    } //	copyLinesFrom

    /**
     * Set Processed. Propagate to Lines/Taxes
     *
     * @param processed processed
     */
    public void setProcessed(boolean processed) {
        super.setProcessed(processed);
        if (getId() == 0) return;
        StringBuilder sql =
                new StringBuilder("UPDATE GL_JournalLine SET Processed='")
                        .append((processed ? "Y" : "N"))
                        .append("' WHERE GL_Journal_ID=")
                        .append(getGLJournalId());
        int noLine = executeUpdate(sql.toString());
        if (log.isLoggable(Level.FINE)) log.fine(processed + " - Lines=" + noLine);
    } //	setProcessed

    /**
     * ************************************************************************ Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        //	Imported Journals may not have date
        if (getDateDoc() == null) {
            if (getDateAcct() == null) setDateDoc(new Timestamp(System.currentTimeMillis()));
            else setDateDoc(getDateAcct());
        }
        if (getDateAcct() == null) setDateAcct(getDateDoc());

        // IDEMPIERE-63
        // for documents that can be reactivated we cannot allow changing
        // C_DocTypeTarget_ID or C_DocType_ID if they were already processed and
        // isOverwriteSeqOnComplete
        // neither change the Date if isOverwriteDateOnComplete
        BigDecimal previousProcessedOn = (BigDecimal) getValueOld(I_GL_Journal.COLUMNNAME_ProcessedOn);
        if (!newRecord && previousProcessedOn != null && previousProcessedOn.signum() > 0) {
            int previousDocTypeID = (Integer) getValueOld(I_GL_Journal.COLUMNNAME_C_DocType_ID);
            MDocType previousdt = MDocType.get(getCtx(), previousDocTypeID);
            if (isValueChanged(I_GL_Journal.COLUMNNAME_C_DocType_ID)) {
                if (previousdt.isOverwriteSeqOnComplete()) {
                    log.saveError("Error", Msg.getMsg(getCtx(), "CannotChangeProcessedDocType"));
                    return false;
                }
            }
            if (isValueChanged(I_GL_Journal.COLUMNNAME_DateDoc)) {
                if (previousdt.isOverwriteDateOnComplete()) {
                    log.saveError("Error", Msg.getMsg(getCtx(), "CannotChangeProcessedDate"));
                    return false;
                }
            }
        }

        // Update DateAcct on lines - teo_sarca BF [ 1775358 ]
        if (isValueChanged(I_GL_Journal.COLUMNNAME_DateAcct)) {
            int no =
                    executeUpdate(
                            "UPDATE GL_JournalLine SET "
                                    + MJournalLine.COLUMNNAME_DateAcct
                                    + "=? WHERE GL_Journal_ID=?",
                            new Object[]{getDateAcct(), getGLJournalId()}
                    );
            if (log.isLoggable(Level.FINEST)) log.finest("Updated GL_JournalLine.DateAcct #" + no);
        }
        return true;
    } //	beforeSave

    /**
     * After Save. Update Batch Total
     *
     * @param newRecord true if new record
     * @param success   true if success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        return updateBatch();
    } //	afterSave

    /**
     * After Delete
     *
     * @param success true if deleted
     * @return true if success
     */
    protected boolean afterDelete(boolean success) {
        if (!success) return success;
        return updateBatch();
    } //	afterDelete

    /**
     * Update Batch total
     *
     * @return true if ok
     */
    private boolean updateBatch() {
        if (getGLJournalBatchId() != 0) { // idempiere 344 - nmicoud
            StringBuilder sql =
                    new StringBuilder("UPDATE GL_JournalBatch jb")
                            .append(
                                    " SET (TotalDr, TotalCr) = (SELECT COALESCE(SUM(TotalDr),0), COALESCE(SUM(TotalCr),0)")
                            .append(
                                    " FROM GL_Journal j WHERE j.IsActive='Y' AND jb.GL_JournalBatch_ID=j.GL_JournalBatch_ID) ")
                            .append("WHERE GL_JournalBatch_ID=")
                            .append(getGLJournalBatchId());
            int no = executeUpdate(sql.toString());
            if (no != 1) log.warning("afterSave - Update Batch #" + no);
            return no == 1;
        }
        return true;
    } //	updateBatch

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

        // Get Period
        MPeriod period = (MPeriod) getPeriod();
        if (!period.isInPeriod(getDateAcct())) {
            period = MPeriod.get(getCtx(), getDateAcct(), getOrgId());
            if (period == null) {
                log.warning("No Period for " + getDateAcct());
                m_processMsg = "@PeriodNotFound@";
                return DocAction.Companion.getSTATUS_Invalid();
            }
            //	Standard Period
            if (period.getPeriodId() != getPeriodId() && period.isStandardPeriod()) {
                m_processMsg = "@PeriodNotValid@";
                return DocAction.Companion.getSTATUS_Invalid();
            }
        }
        boolean open = period.isOpen(dt.getDocBaseType(), getDateAcct());
        if (!open) {
            log.warning(
                    period.getName() + ": Not open for " + dt.getDocBaseType() + " (" + getDateAcct() + ")");
            m_processMsg = "@PeriodClosed@";
            return DocAction.Companion.getSTATUS_Invalid();
        }

        //	Lines
        MJournalLine[] lines = getLines(true);
        if (lines.length == 0) {
            m_processMsg = "@NoLines@";
            return DocAction.Companion.getSTATUS_Invalid();
        }

        //	Add up Amounts
        BigDecimal AmtSourceDr = Env.ZERO;
        BigDecimal AmtSourceCr = Env.ZERO;
        for (int i = 0; i < lines.length; i++) {
            MJournalLine line = lines[i];
            if (!isActive()) continue;

            // bcahya, BF [2789319] No check of Actual, Budget, Statistical attribute
            if (!line.getAccountElementValue().isActive()) {
                m_processMsg =
                        "@InActiveAccount@ - @Line@=" + line.getLine() + " - " + line.getAccountElementValue();
                return DocAction.Companion.getSTATUS_Invalid();
            }

            // Michael Judd (mjudd) BUG: [ 2678088 ] Allow posting to system accounts for non-actual
            // postings
            if (line.isDocControlled()
                    && (getPostingType().equals(X_GL_Journal.POSTINGTYPE_Actual)
                    || getPostingType().equals(X_GL_Journal.POSTINGTYPE_Commitment)
                    || getPostingType().equals(X_GL_Journal.POSTINGTYPE_Reservation))) {
                m_processMsg =
                        "@DocControlledError@ - @Line@="
                                + line.getLine()
                                + " - "
                                + line.getAccountElementValue();
                return DocAction.Companion.getSTATUS_Invalid();
            }
            //

            // bcahya, BF [2789319] No check of Actual, Budget, Statistical attribute
            if (getPostingType().equals(X_GL_Journal.POSTINGTYPE_Actual)
                    && !line.getAccountElementValue().isPostActual()) {
                m_processMsg =
                        "@PostingTypeActualError@ - @Line@="
                                + line.getLine()
                                + " - "
                                + line.getAccountElementValue();
                return DocAction.Companion.getSTATUS_Invalid();
            }

            if (getPostingType().equals(X_GL_Journal.POSTINGTYPE_Budget)
                    && !line.getAccountElementValue().isPostBudget()) {
                m_processMsg =
                        "@PostingTypeBudgetError@ - @Line@="
                                + line.getLine()
                                + " - "
                                + line.getAccountElementValue();
                return DocAction.Companion.getSTATUS_Invalid();
            }

            if (getPostingType().equals(X_GL_Journal.POSTINGTYPE_Statistical)
                    && !line.getAccountElementValue().isPostStatistical()) {
                m_processMsg =
                        "@PostingTypeStatisticalError@ - @Line@="
                                + line.getLine()
                                + " - "
                                + line.getAccountElementValue();
                return DocAction.Companion.getSTATUS_Invalid();
            }
            // end BF [2789319] No check of Actual, Budget, Statistical attribute

            AmtSourceDr = AmtSourceDr.add(line.getAmtSourceDr());
            AmtSourceCr = AmtSourceCr.add(line.getAmtSourceCr());
        }
        setTotalDr(AmtSourceDr);
        setTotalCr(AmtSourceCr);

        //	Control Amount
        if (Env.ZERO.compareTo(getControlAmt()) != 0 && getControlAmt().compareTo(getTotalDr()) != 0) {
            m_processMsg = "@ControlAmtError@";
            return DocAction.Companion.getSTATUS_Invalid();
        }

        //	Unbalanced Jornal & Not Suspense
        if (AmtSourceDr.compareTo(AmtSourceCr) != 0) {
            MAcctSchemaGL gl = MAcctSchemaGL.get(getCtx(), getAccountingSchemaId());
            if (gl == null || !gl.isUseSuspenseBalancing()) {
                m_processMsg = "@UnbalancedJornal@";
                return DocAction.Companion.getSTATUS_Invalid();
            }
        }

        if (!X_GL_Journal.DOCACTION_Complete.equals(getDocAction()))
            setDocAction(X_GL_Journal.DOCACTION_Complete);

        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
        if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();

        m_justPrepared = true;
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

        // Set the definite document number after completed (if needed)
        setDefiniteDocumentNo();

        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
        if (m_processMsg != null)
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());

        //	Implicit Approval
        if (!isApproved()) approveIt();
        if (log.isLoggable(Level.INFO)) log.info(toString());
        //	User Validation
        String valid =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
        if (valid != null) {
            m_processMsg = valid;
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
        }

        //
        setProcessed(true);
        setDocAction(X_GL_Journal.DOCACTION_Close);
        return new CompleteActionResult(DocAction.Companion.getSTATUS_Completed());
    } //	completeIt

    /**
     * Set the definite document number after completed
     */
    private void setDefiniteDocumentNo() {
        MDocType dt = MDocType.get(getCtx(), getDocumentTypeId());
        if (dt.isOverwriteDateOnComplete()) {
            if (this.getProcessedOn().signum() == 0) {
                setDateDoc(new Timestamp(System.currentTimeMillis()));
                if (getDateAcct().before(getDateDoc())) {
                    setDateAcct(getDateDoc());
                    MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getDocumentTypeId(), getOrgId());
                }
            }
        }
        if (dt.isOverwriteSeqOnComplete()) {
            if (this.getProcessedOn().signum() == 0) {
                String value = MSequence.getDocumentNo(getDocumentTypeId(), null, true, this);
                if (value != null) setDocumentNo(value);
            }
        }
    }

    /**
     * Void Document.
     *
     * @return true if success
     */
    public boolean voidIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        // Before Void
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
        if (m_processMsg != null) return false;

        boolean ok_to_void = false;
        if (X_GL_Journal.DOCSTATUS_Drafted.equals(getDocStatus())
                || X_GL_Journal.DOCSTATUS_Invalid.equals(getDocStatus())) {
            // set lines to 0
            MJournalLine[] lines = getLines(false);
            for (int i = 0; i < lines.length; i++) {
                MJournalLine line = lines[i];
                if (line.getAmtAcctDr().signum() != 0 || line.getAmtAcctCr().signum() != 0) {
                    line.setAmtAcctDr(Env.ZERO);
                    line.setAmtAcctCr(Env.ZERO);
                    line.setAmtSourceDr(Env.ZERO);
                    line.setAmtSourceCr(Env.ZERO);
                    line.setQty(Env.ZERO);
                    line.saveEx();
                }
            }
            setProcessed(true);
            setDocAction(X_GL_Journal.DOCACTION_None);
            ok_to_void = true;
        } else {
            return false;
        }

        // After Void
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
        if (m_processMsg != null) return false;

        return ok_to_void;
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

        boolean ok_to_close = false;
        if (X_GL_Journal.DOCSTATUS_Completed.equals(getDocStatus())) {
            setProcessed(true);
            setDocAction(X_GL_Journal.DOCACTION_None);
            ok_to_close = true;
        } else {
            return false;
        }

        // After Close
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
        if (m_processMsg != null) return false;

        return ok_to_close;
    } //	closeIt

    /**
     * Reverse Correction (in same batch). As if nothing happened - same date
     *
     * @return true if success
     */
    public boolean reverseCorrectIt() {
        // Before reverseCorrect
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
        if (m_processMsg != null) return false;

        boolean ok_correct = (reverseCorrectIt(getGLJournalBatchId()) != null);

        if (!ok_correct) return false;

        // After reverseCorrect
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
        if (m_processMsg != null) return false;

        return ok_correct;
    } //	reverseCorrectIt

    /**
     * Reverse Correction. As if nothing happened - same date
     *
     * @param GL_JournalBatch_ID reversal batch
     * @return reversed Journal or null
     */
    public MJournal reverseCorrectIt(int GL_JournalBatch_ID) {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        //	Journal
        MJournal reverse = new MJournal(this);
        reverse.setGLJournalBatchId(GL_JournalBatch_ID);
        reverse.setDateDoc(getDateDoc());
        reverse.setPeriodId(getPeriodId());
        reverse.setDateAcct(getDateAcct());
        //	Reverse indicator
        StringBuilder msgd = new StringBuilder("(->").append(getDocumentNo()).append(")");
        reverse.addDescription(msgd.toString());
        reverse.setControlAmt(getControlAmt().negate());
        // FR [ 1948157  ]
        reverse.setReversalId(getGLJournalId());
        if (!reverse.save()) return null;

        //	Lines
        reverse.copyLinesFrom(this, null, 'C');
        //
        if (!reverse.processIt(DocAction.Companion.getACTION_Complete())) {
            m_processMsg = "Reversal ERROR: " + reverse.getProcessMsg();
            return null;
        }
        reverse.closeIt();
        reverse.setProcessing(false);
        reverse.setDocStatus(X_GL_Journal.DOCSTATUS_Reversed);
        reverse.setDocAction(X_GL_Journal.DOCACTION_None);
        reverse.saveEx();
        //
        msgd = new StringBuilder("(").append(reverse.getDocumentNo()).append("<-)");
        addDescription(msgd.toString());

        //
        setProcessed(true);
        // FR [ 1948157  ]
        setReversalId(reverse.getGLJournalId());
        setDocStatus(X_GL_Journal.DOCSTATUS_Reversed);
        setDocAction(X_GL_Journal.DOCACTION_None);
        return reverse;
    } //	reverseCorrectionIt

    /**
     * Reverse Accrual (sane batch). Flip Dr/Cr - Use Today's date
     *
     * @return true if success
     */
    public boolean reverseAccrualIt() {
        // Before reverseAccrual
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
        if (m_processMsg != null) return false;

        boolean ok_reverse = (reverseAccrualIt(getGLJournalBatchId()) != null);

        if (!ok_reverse) return false;

        // After reverseAccrual
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
        if (m_processMsg != null) return false;

        return ok_reverse;
    } //	reverseAccrualIt

    /**
     * Reverse Accrual. Flip Dr/Cr - Use Today's date
     *
     * @param GL_JournalBatch_ID reversal batch
     * @return reversed journal or null
     */
    public MJournal reverseAccrualIt(int GL_JournalBatch_ID) {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        //	Journal
        MJournal reverse = new MJournal(this);
        reverse.setGLJournalBatchId(GL_JournalBatch_ID);
        Timestamp reversalDate = Env.getContextAsDate(getCtx(), "#Date");
        if (reversalDate == null) {
            reversalDate = new Timestamp(System.currentTimeMillis());
        }
        reverse.setDateDoc(reversalDate);
        reverse.setValueNoCheck("C_Period_ID", null); // 	reset
        reverse.setDateAcct(reversalDate);
        //	Reverse indicator
        StringBuilder msgd = new StringBuilder("(->").append(getDocumentNo()).append(")");
        reverse.addDescription(msgd.toString());
        reverse.setReversalId(getGLJournalId());
        if (!reverse.save()) return null;
        //	Lines
        reverse.copyLinesFrom(this, reverse.getDateAcct(), 'R');
        //
        if (!reverse.processIt(DocAction.Companion.getACTION_Complete())) {
            m_processMsg = "Reversal ERROR: " + reverse.getProcessMsg();
            return null;
        }
        reverse.closeIt();
        reverse.setProcessing(false);
        reverse.setDocStatus(X_GL_Journal.DOCSTATUS_Reversed);
        reverse.setDocAction(X_GL_Journal.DOCACTION_None);
        reverse.saveEx();
        //
        msgd = new StringBuilder("(").append(reverse.getDocumentNo()).append("<-)");
        addDescription(msgd.toString());

        setProcessed(true);
        setReversalId(reverse.getGLJournalId());
        setDocStatus(X_GL_Journal.DOCSTATUS_Reversed);
        setDocAction(X_GL_Journal.DOCACTION_None);
        return reverse;
    } //	reverseAccrualIt

    /**
     * Re-activate
     *
     * @return true if success
     */
    public boolean reActivateIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        // Before reActivate
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
        if (m_processMsg != null) return false;

        // teo_sarca - FR [ 1776045 ] Add ReActivate action to GL Journal
        MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getDocumentTypeId(), getOrgId());
        MFactAcct.deleteEx(MJournal.Table_ID, getId());
        setPosted(false);
        setProcessed(false);
        setDocAction(X_GL_Journal.DOCACTION_Complete);

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
                .append(getLines(false).length)
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
        StringBuilder sb = new StringBuilder("MJournal[");
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
     * @return AD_User_ID (Created)
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

    /**
     * Document Status is Complete or Closed
     *
     * @return true if CO, CL or RE
     */
    public boolean isComplete() {
        String ds = getDocStatus();
        return X_GL_Journal.DOCSTATUS_Completed.equals(ds)
                || X_GL_Journal.DOCSTATUS_Closed.equals(ds)
                || X_GL_Journal.DOCSTATUS_Reversed.equals(ds);
    } //	isComplete

    @Override
    public void setDoc(IDoc doc) {
    }

    @Override
    public void setProcessedOn(String processed, boolean b, boolean b1) {
    }
} //	MJournal
