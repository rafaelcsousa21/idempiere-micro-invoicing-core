package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.bank.MBankAccount;
import org.compiere.docengine.DocumentEngine;
import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.orm.MDocType;
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
 * Bank Statement Model
 *
 * @author Eldir Tomassen/Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * <li>BF [ 1933645 ] Wrong balance Bank Statement
 * @author Teo Sarca, http://www.arhipac.ro
 * <li>FR [ 2616330 ] Use MPeriod.testPeriodOpen instead of isOpen
 * https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2616330&group_id=176962
 * @version $Id: MBankStatement.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * @see http://sourceforge.net/tracker/?func=detail&atid=879332&aid=1933645&group_id=176962
 * <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 * <li>BF [ 2824951 ] The payments is not release when Bank Statement is void
 * @see http://sourceforge.net/tracker/?func=detail&aid=2824951&group_id=176962&atid=879332
 */
public class MBankStatement extends X_C_BankStatement implements DocAction, IPODoc {
    /**
     *
     */
    private static final long serialVersionUID = -859925588789443186L;
    /**
     * Lines
     */
    private MBankStatementLine[] m_lines = null;
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
     * @param ctx                context
     * @param C_BankStatement_ID id
     */
    public MBankStatement(Properties ctx, int C_BankStatement_ID) {
        super(ctx, C_BankStatement_ID);
        if (C_BankStatement_ID == 0) {
            //	setBankAccountId (0);	//	parent
            setStatementDate(new Timestamp(System.currentTimeMillis())); // @Date@
            setDocAction(X_C_BankStatement.DOCACTION_Complete); // CO
            setDocStatus(X_C_BankStatement.DOCSTATUS_Drafted); // DR
            setBeginningBalance(Env.ZERO);
            setStatementDifference(Env.ZERO);
            setEndingBalance(Env.ZERO);
            setIsApproved(false); // N
            setIsManual(true); // Y
            setPosted(false); // N
            super.setProcessed(false);
        }
    } //	MBankStatement

    /**
     * Load Constructor
     *
     * @param ctx Current context
     */
    public MBankStatement(Properties ctx, Row row) {
        super(ctx, row);
    } //	MBankStatement

    /**
     * Parent Constructor
     *
     * @param account  Bank Account
     * @param isManual Manual statement
     */
    public MBankStatement(MBankAccount account, boolean isManual) {
        this(account.getCtx(), 0);
        setClientOrg(account);
        setBankAccountId(account.getBankAccountId());
        setStatementDate(new Timestamp(System.currentTimeMillis()));
        setDateAcct(new Timestamp(System.currentTimeMillis()));
        setBeginningBalance(account.getCurrentBalance());
        setName(getStatementDate().toString());
        setIsManual(isManual);
    } //	MBankStatement

    /**
     * Create a new Bank Statement
     *
     * @param account Bank Account
     */
    public MBankStatement(MBankAccount account) {
        this(account, false);
    } //	MBankStatement

    /**
     * Get Bank Statement Lines
     *
     * @param requery requery
     * @return line array
     */
    public MBankStatementLine[] getLines(boolean requery) {
        if (m_lines != null && !requery) {
            return m_lines;
        }
        //
        final String whereClause = I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID + "=?";
        List<MBankStatementLine> list =
                new Query(getCtx(), I_C_BankStatementLine.Table_Name, whereClause)
                        .setParameters(getBankStatementId())
                        .setOrderBy("Line")
                        .list();
        MBankStatementLine[] retValue = new MBankStatementLine[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getLines

    /**
     * Add to Description
     *
     * @param description text
     */
    public void addDescription(String description) {
        String desc = getDescription();
        if (desc == null) setDescription(description);
        else {
            StringBuilder msgd = new StringBuilder(desc).append(" | ").append(description);
            setDescription(msgd.toString());
        }
    } //	addDescription

    /**
     * Set Processed. Propergate to Lines/Taxes
     *
     * @param processed processed
     */
    public void setProcessed(boolean processed) {
        super.setProcessed(processed);
        if (getId() == 0) return;
        StringBuilder sql =
                new StringBuilder("UPDATE C_BankStatementLine SET Processed='")
                        .append((processed ? "Y" : "N"))
                        .append("' WHERE C_BankStatement_ID=")
                        .append(getBankStatementId());
        int noLine = executeUpdate(sql.toString());
        m_lines = null;
        if (log.isLoggable(Level.FINE)) log.fine("setProcessed - " + processed + " - Lines=" + noLine);
    } //	setProcessed

    /**
     * Get Bank Account
     *
     * @return bank Account
     */
    public MBankAccount getBankAccount() {
        return MBankAccount.get(getCtx(), getBankAccountId());
    } //	getBankAccount

    /**
     * Get Document No
     *
     * @return name
     */
    public String getDocumentNo() {
        return getName();
    } //	getDocumentNo

    /**
     * Get Document Info
     *
     * @return document info (untranslated)
     */
    public String getDocumentInfo() {
        StringBuilder msgreturn =
                new StringBuilder().append(getBankAccount().getName()).append(" ").append(getDocumentNo());
        return msgreturn.toString();
    } //	getDocumentInfo

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        if (!isProcessed() && getBeginningBalance().compareTo(Env.ZERO) == 0) {
            MBankAccount ba = getBankAccount();
            ba.load();
            setBeginningBalance(ba.getCurrentBalance());
        }
        setEndingBalance(getBeginningBalance().add(getStatementDifference()));
        return true;
    } //	beforeSave

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
        setDocAction(X_C_BankStatement.DOCACTION_Prepare);
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
                getCtx(), getStatementDate(), MDocType.DOCBASETYPE_BankStatement, getOrgId());
        MBankStatementLine[] lines = getLines(true);
        if (lines.length == 0) {
            m_processMsg = "@NoLines@";
            return DocAction.Companion.getSTATUS_Invalid();
        }
        //	Lines
        BigDecimal total = Env.ZERO;
        Timestamp minDate = getStatementDate();
        Timestamp maxDate = minDate;
        for (int i = 0; i < lines.length; i++) {
            MBankStatementLine line = lines[i];
            if (!line.isActive()) continue;
            total = total.add(line.getStmtAmt());
            if (line.getDateAcct().before(minDate)) minDate = line.getDateAcct();
            if (line.getDateAcct().after(maxDate)) maxDate = line.getDateAcct();
        }
        setStatementDifference(total);
        setEndingBalance(getBeginningBalance().add(total));
        MPeriod.testPeriodOpen(getCtx(), minDate, MDocType.DOCBASETYPE_BankStatement, 0);
        MPeriod.testPeriodOpen(getCtx(), maxDate, MDocType.DOCBASETYPE_BankStatement, 0);

        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
        if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();

        m_justPrepared = true;
        if (!X_C_BankStatement.DOCACTION_Complete.equals(getDocAction()))
            setDocAction(X_C_BankStatement.DOCACTION_Complete);
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
        if (log.isLoggable(Level.INFO)) log.info("completeIt - " + toString());

        //	Set Payment reconciled
        MBankStatementLine[] lines = getLines(false);
        for (int i = 0; i < lines.length; i++) {
            MBankStatementLine line = lines[i];
            if (line.getPaymentId() != 0) {
                MPayment payment = new MPayment(getCtx(), line.getPaymentId());
                payment.setIsReconciled(true);
                payment.saveEx();
            }
        }
        //	Update Bank Account
        MBankAccount ba = getBankAccount();
        ba.load();
        // BF 1933645
        ba.setCurrentBalance(ba.getCurrentBalance().add(getStatementDifference()));
        ba.saveEx();

        //	User Validation
        String valid =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
        if (valid != null) {
            m_processMsg = valid;
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
        }
        //
        setProcessed(true);
        setDocAction(X_C_BankStatement.DOCACTION_Close);
        return new CompleteActionResult(DocAction.Companion.getSTATUS_Completed());
    } //	completeIt

    /**
     * Void Document.
     *
     * @return false
     */
    public boolean voidIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        // Before Void
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
        if (m_processMsg != null) return false;

        if (X_C_BankStatement.DOCSTATUS_Closed.equals(getDocStatus())
                || X_C_BankStatement.DOCSTATUS_Reversed.equals(getDocStatus())
                || X_C_BankStatement.DOCSTATUS_Voided.equals(getDocStatus())) {
            m_processMsg = "Document Closed: " + getDocStatus();
            setDocAction(X_C_BankStatement.DOCACTION_None);
            return false;
        }

        //	Not Processed
        if (X_C_BankStatement.DOCSTATUS_Drafted.equals(getDocStatus())
                || X_C_BankStatement.DOCSTATUS_Invalid.equals(getDocStatus())
                || X_C_BankStatement.DOCSTATUS_InProgress.equals(getDocStatus())
                || X_C_BankStatement.DOCSTATUS_Approved.equals(getDocStatus())
                || X_C_BankStatement.DOCSTATUS_NotApproved.equals(getDocStatus())) ;
            //	Std Period open?
        else {
            MPeriod.testPeriodOpen(
                    getCtx(), getStatementDate(), MDocType.DOCBASETYPE_BankStatement, getOrgId());
            MFactAcct.deleteEx(I_C_BankStatement.Table_ID, getBankStatementId());
        }

        if (isProcessed()) {
            // Added Lines by AZ Goodwill
            // Restore Bank Account Balance
            MBankAccount ba = getBankAccount();
            ba.load();
            ba.setCurrentBalance(ba.getCurrentBalance().subtract(getStatementDifference()));
            ba.saveEx();
            // End of Added Lines
        }

        //	Set lines to 0
        MBankStatementLine[] lines = getLines(true);
        for (int i = 0; i < lines.length; i++) {
            MBankStatementLine line = lines[i];
            if (line.getStmtAmt().compareTo(Env.ZERO) != 0) {
                StringBuilder description =
                        new StringBuilder(Msg.getMsg(getCtx(), "Voided"))
                                .append(" (")
                                .append(Msg.translate(getCtx(), "StmtAmt"))
                                .append("=")
                                .append(line.getStmtAmt());
                if (line.getTrxAmt().compareTo(Env.ZERO) != 0)
                    description
                            .append(", ")
                            .append(Msg.translate(getCtx(), "TrxAmt"))
                            .append("=")
                            .append(line.getTrxAmt());
                if (line.getChargeAmt().compareTo(Env.ZERO) != 0)
                    description
                            .append(", ")
                            .append(Msg.translate(getCtx(), "ChargeAmt"))
                            .append("=")
                            .append(line.getChargeAmt());
                if (line.getInterestAmt().compareTo(Env.ZERO) != 0)
                    description
                            .append(", ")
                            .append(Msg.translate(getCtx(), "InterestAmt"))
                            .append("=")
                            .append(line.getInterestAmt());
                description.append(")");
                line.addDescription(description.toString());
                //
                line.setStmtAmt(Env.ZERO);
                line.setTrxAmt(Env.ZERO);
                line.setChargeAmt(Env.ZERO);
                line.setInterestAmt(Env.ZERO);
                if (line.getPaymentId() != 0) {
                    MPayment payment = new MPayment(getCtx(), line.getPaymentId());
                    payment.setIsReconciled(false);
                    payment.saveEx();
                    line.setPaymentId(0);
                }
                line.saveEx();
            }
        }
        addDescription(Msg.getMsg(getCtx(), "Voided"));
        setStatementDifference(Env.ZERO);

        // After Void
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
        if (m_processMsg != null) return false;

        setProcessed(true);
        setDocAction(X_C_BankStatement.DOCACTION_None);
        return true;
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

        setDocAction(X_C_BankStatement.DOCACTION_None);

        // After Close
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
        return m_processMsg == null;
    } //	closeIt

    /**
     * Reverse Correction
     *
     * @return false
     */
    public boolean reverseCorrectIt() {
        if (log.isLoggable(Level.INFO)) log.info("reverseCorrectIt - " + toString());
        // Before reverseCorrect
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
        if (m_processMsg != null) return false;

        // After reverseCorrect
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
        if (m_processMsg != null) return false;

        return false;
    } //	reverseCorrectionIt

    /**
     * Reverse Accrual
     *
     * @return false
     */
    public boolean reverseAccrualIt() {
        if (log.isLoggable(Level.INFO)) log.info("reverseAccrualIt - " + toString());
        // Before reverseAccrual
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
        if (m_processMsg != null) return false;

        // After reverseAccrual
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
        if (m_processMsg != null) return false;

        return false;
    } //	reverseAccrualIt

    /**
     * Re-activate
     *
     * @return false
     */
    public boolean reActivateIt() {
        if (log.isLoggable(Level.INFO)) log.info("reActivateIt - " + toString());
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
     * *********************************************************************** Get Summary
     *
     * @return Summary of Document
     */
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        //	: Total Lines = 123.00 (#1)
        sb.append(": ")
                .append(Msg.translate(getCtx(), "StatementDifference"))
                .append("=")
                .append(getStatementDifference())
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
    public int getDoc_UserId() {
        return getUpdatedBy();
    } //	getDoc_User_ID

    /**
     * Get Document Approval Amount. Statement Difference
     *
     * @return amount
     */
    public BigDecimal getApprovalAmt() {
        return getStatementDifference();
    } //	getApprovalAmt

    /**
     * Get Document Currency
     *
     * @return C_Currency_ID
     */
    public int getCurrencyId() {
        //	MPriceList pl = MPriceList.get(getCtx(), getPriceListId());
        //	return pl.getCurrencyId();
        return 0;
    } //	getCurrencyId

    /**
     * Document Status is Complete or Closed
     *
     * @return true if CO, CL or RE
     */
    public boolean isComplete() {
        String ds = getDocStatus();
        return X_C_BankStatement.DOCSTATUS_Completed.equals(ds)
                || X_C_BankStatement.DOCSTATUS_Closed.equals(ds)
                || X_C_BankStatement.DOCSTATUS_Reversed.equals(ds);
    } //	isComplete

    @Override
    public void setDoc(IDoc doc) {
    }

    @Override
    public void setProcessedOn(String processed, boolean b, boolean b1) {
    }
} //	MBankStatement
