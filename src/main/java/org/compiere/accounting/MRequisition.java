package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.crm.MUserKt;
import org.compiere.docengine.DocumentEngine;
import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.orm.MDocType;
import org.compiere.orm.MDocTypeKt;
import org.compiere.orm.MSequence;
import org.compiere.orm.Query;
import org.compiere.process.CompleteActionResult;
import org.compiere.process.DocAction;
import org.compiere.product.MPriceList;
import org.compiere.util.MsgKt;
import org.compiere.validation.ModelValidationEngine;
import org.compiere.validation.ModelValidator;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

/**
 * Requisition Model
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * <li>FR [ 2520591 ] Support multiples calendar for Org
 * @author red1
 * <li>FR [ 2214883 ] Remove SQL code and Replace for Query
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 2744682 ] Requisition: improve error reporting
 * @version $Id: MRequisition.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 */
public class MRequisition extends X_M_Requisition implements DocAction, IPODoc {
    /**
     *
     */
    private static final long serialVersionUID = 898606565778668659L;
    /**
     * Lines
     */
    private MRequisitionLine[] m_lines = null;
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
     * @param M_Requisition_ID id
     */
    public MRequisition(int M_Requisition_ID) {
        super(M_Requisition_ID);
        if (M_Requisition_ID == 0) {
            setDateDoc(new Timestamp(System.currentTimeMillis()));
            setDateRequired(new Timestamp(System.currentTimeMillis()));
            setDocAction(DocAction.Companion.getACTION_Complete()); // CO
            setDocStatus(DocAction.Companion.getSTATUS_Drafted()); // DR
            setPriorityRule(X_M_Requisition.PRIORITYRULE_Medium); // 5
            setTotalLines(Env.ZERO);
            setIsApproved(false);
            setPosted(false);
            setProcessed(false);
        }
    } //	MRequisition

    /**
     * Load Constructor
     */
    public MRequisition(Row row) {
        super(row);
    } //	MRequisition

    /**
     * Get Lines
     *
     * @return array of lines
     */
    public MRequisitionLine[] getLines() {
        if (m_lines != null) {
            return m_lines;
        }

        // red1 - FR: [ 2214883 ] Remove SQL code and Replace for Query
        final String whereClause = I_M_RequisitionLine.COLUMNNAME_M_Requisition_ID + "=?";
        List<MRequisitionLine> list =
                new Query(I_M_RequisitionLine.Table_Name, whereClause)
                        .setParameters(getId())
                        .setOrderBy(I_M_RequisitionLine.COLUMNNAME_Line)
                        .list();
        //  red1 - end -

        m_lines = new MRequisitionLine[list.size()];
        list.toArray(m_lines);
        return m_lines;
    } //	getLines

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MRequisition[");
        sb.append(getId())
                .append("-")
                .append(getDocumentNo())
                .append(",Status=")
                .append(getDocStatus())
                .append(",Action=")
                .append(getDocAction())
                .append("]");
        return sb.toString();
    } //	toString

    /**
     * Get Document Info
     *
     * @return document info
     */
    @NotNull
    public String getDocumentInfo() {
        return MsgKt.getElementTranslation("M_Requisition_ID") + " " + getDocumentNo();
    } //	getDocumentInfo

    /**
     * Set default PriceList
     */
    public void setPriceListId() {
        MPriceList defaultPL = MPriceList.getDefault(false);
        if (defaultPL == null) defaultPL = MPriceList.getDefault(true);
        if (defaultPL != null) setPriceListId(defaultPL.getPriceListId());
    } //	setPriceListId()

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        if (getPriceListId() == 0) setPriceListId();
        return true;
    } //	beforeSave

    @Override
    protected boolean beforeDelete() {
        for (MRequisitionLine line : getLines()) {
            line.deleteEx(true);
        }
        return true;
    }

    /**
     * ************************************************************************ Process document
     *
     * @param processAction document action
     * @return true if performed
     */
    public boolean processIt(@NotNull String processAction) {
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
    @NotNull
    public String prepareIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
        if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();
        MRequisitionLine[] lines = getLines();

        //	Invalid
        if (getUserId() == 0 || getPriceListId() == 0 || getWarehouseId() == 0) {
            return DocAction.Companion.getSTATUS_Invalid();
        }

        if (lines.length == 0) {
            throw new AdempiereException("@NoLines@");
        }

        //	Std Period open?
        MPeriod.testPeriodOpen(
                getDateDoc(), MDocType.DOCBASETYPE_PurchaseRequisition, getOrgId());

        //	Add up Amounts
        int precision = MPriceList.getStandardPrecision(getPriceListId());
        BigDecimal totalLines = Env.ZERO;
        for (int i = 0; i < lines.length; i++) {
            MRequisitionLine line = lines[i];
            BigDecimal lineNet = line.getQty().multiply(line.getPriceActual());
            lineNet = lineNet.setScale(precision, BigDecimal.ROUND_HALF_UP);
            if (lineNet.compareTo(line.getLineNetAmt()) != 0) {
                line.setLineNetAmt(lineNet);
                line.saveEx();
            }
            totalLines = totalLines.add(line.getLineNetAmt());
        }
        if (totalLines.compareTo(getTotalLines()) != 0) {
            setTotalLines(totalLines);
            saveEx();
        }

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
    @NotNull
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
        setDocAction(DocAction.Companion.getACTION_Close());
        return new CompleteActionResult(DocAction.Companion.getSTATUS_Completed());
    } //	completeIt

    /**
     * Set the definite document number after completed
     */
    private void setDefiniteDocumentNo() {
        MDocType dt = MDocTypeKt.getDocumentType(getDocumentTypeId());
        if (dt.isOverwriteDateOnComplete()) {
            setDateDoc(new Timestamp(System.currentTimeMillis()));
            MPeriod.testPeriodOpen(
                    getDateDoc(), MDocType.DOCBASETYPE_PurchaseRequisition, getOrgId());
        }
        if (dt.isOverwriteSeqOnComplete()) {
            String value = MSequence.getDocumentNo(getDocumentTypeId(), true, this);
            if (value != null) setDocumentNo(value);
        }
    }

    /**
     * Void Document. Same as Close.
     *
     * @return true if success
     */
    public boolean voidIt() {
        if (log.isLoggable(Level.INFO)) log.info("voidIt - " + toString());
        // Before Void
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
        if (m_processMsg != null) return false;

        if (!closeIt()) return false;

        // After Void
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
        return m_processMsg == null;
    } //	voidIt

    /**
     * Close Document. Cancel not delivered Qunatities
     *
     * @return true if success
     */
    public boolean closeIt() {
        if (log.isLoggable(Level.INFO)) log.info("closeIt - " + toString());
        // Before Close
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
        if (m_processMsg != null) return false;

        //	Close Not delivered Qty
        MRequisitionLine[] lines = getLines();
        BigDecimal totalLines = Env.ZERO;
        for (int i = 0; i < lines.length; i++) {
            MRequisitionLine line = lines[i];
            BigDecimal finalQty = line.getQty();
            if (line.getOrderLineId() == 0) finalQty = Env.ZERO;
            else {
                MOrderLine ol = new MOrderLine(line.getOrderLineId());
                finalQty = ol.getQtyOrdered();
            }
            //	final qty is not line qty
            if (finalQty.compareTo(line.getQty()) != 0) {
                String description = line.getDescription();
                if (description == null) description = "";
                description += " [" + line.getQty() + "]";
                line.setDescription(description);
                line.setQty(finalQty);
                line.setLineNetAmt();
                line.saveEx();
            }
            totalLines = totalLines.add(line.getLineNetAmt());
        }
        if (totalLines.compareTo(getTotalLines()) != 0) {
            setTotalLines(totalLines);
            saveEx();
        }
        // After Close
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
        return m_processMsg == null;
    } //	closeIt

    /**
     * Reverse Correction
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

        // After reverseCorrect
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
        if (m_processMsg != null) return false;

        return false;
    } //	reverseCorrectionIt

    /**
     * Reverse Accrual - none
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
     * @return true if success
     */
    public boolean reActivateIt() {
        if (log.isLoggable(Level.INFO)) log.info("reActivateIt - " + toString());
        // Before reActivate
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
        if (m_processMsg != null) return false;

        //	setProcessed(false);
        if (!reverseCorrectIt()) return false;

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
    @NotNull
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDocumentNo());
        //	 - User
        sb.append(" - ").append(getUserName());
        //	: Total Lines = 123.00 (#1)
        sb.append(": ")
                .append(MsgKt.translate("TotalLines"))
                .append("=")
                .append(getTotalLines())
                .append(" (#")
                .append(getLines().length)
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
    @NotNull
    public String getProcessMsg() {
        return m_processMsg;
    } //	getProcessMsg

    /**
     * Get Document Owner
     *
     * @return AD_User_ID
     */
    public int getDocumentUserId() {
        return getUserId();
    }

    /**
     * Get Document Currency
     *
     * @return C_Currency_ID
     */
    public int getCurrencyId() {
        MPriceList pl = MPriceList.get(getPriceListId());
        return pl.getCurrencyId();
    }

    /**
     * Get Document Approval Amount
     *
     * @return amount
     */
    @NotNull
    public BigDecimal getApprovalAmt() {
        return getTotalLines();
    }

    /**
     * Get User Name
     *
     * @return user name
     */
    public String getUserName() {
        return MUserKt.getUser(getUserId()).getName();
    } //	getUserName

    /**
     * Document Status is Complete or Closed
     *
     * @return true if CO, CL or RE
     */
    public boolean isComplete() {
        String ds = getDocStatus();
        return X_M_Requisition.DOCSTATUS_Completed.equals(ds)
                || X_M_Requisition.DOCSTATUS_Closed.equals(ds)
                || X_M_Requisition.DOCSTATUS_Reversed.equals(ds);
    } //	isComplete

    @Override
    public void setDoc(IDoc doc) {
    }

    @Override
    public void setProcessedOn(String processed, boolean b, boolean b1) {
    }
} //	MRequisition
