package org.compiere.invoicing;

import org.compiere.accounting.MAcctSchema;
import org.compiere.accounting.MClient;
import org.compiere.accounting.MPeriod;
import org.compiere.docengine.DocumentEngine;
import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.model.I_A_Depreciation_Entry;
import org.compiere.orm.Query;
import org.compiere.orm.TimeUtil;
import org.compiere.process.CompleteActionResult;
import org.compiere.process.DocAction;
import org.compiere.validation.ModelValidationEngine;
import org.compiere.validation.ModelValidator;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Trx;
import org.idempiere.common.util.TrxRunnable;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdateEx;

/**
 * Depreciation Entry
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class MDepreciationEntry extends X_A_Depreciation_Entry implements DocAction, IPODoc {

  /** */
  private static final long serialVersionUID = 6631244784741228058L;

  /** Standard Constructor */
  public MDepreciationEntry(Properties ctx, int A_Depreciation_Entry_ID, String trxName) {
    super(ctx, A_Depreciation_Entry_ID, trxName);
    if (A_Depreciation_Entry_ID == 0) {
      MAcctSchema acctSchema = MClient.get(getCtx()).getAcctSchema();
      setC_AcctSchema_ID(acctSchema.getId());
      setC_Currency_ID(acctSchema.getC_Currency_ID());
      setA_Entry_Type(X_A_Depreciation_Entry.A_ENTRY_TYPE_Depreciation); // TODO: workaround
      setPostingType(X_A_Depreciation_Entry.POSTINGTYPE_Actual); // A
      setProcessed(false);
      setProcessing(false);
      setPosted(false);
    }
  }

  /** Load Constructor */
  public MDepreciationEntry(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  protected boolean beforeSave(boolean newRecord) {
    setC_Period_ID();
    return true;
  }

  protected boolean afterSave(boolean newRecord, boolean success) {
    if (!success) {
      return false;
    }
    if (!isProcessed()
        && (newRecord || is_ValueChanged(I_A_Depreciation_Entry.COLUMNNAME_DateAcct))) {
      selectLines();
    }
    return true;
  }

  protected boolean afterDelete(boolean success) {
    if (!success) {
      return false;
    }

    unselectLines();
    return true;
  }

  public void setC_Period_ID() {
    MPeriod period = MPeriod.get(getCtx(), getDateAcct(),  getOrgId(), null);
    if (period == null) {
      throw new AdempiereException("@NotFound@ @C_Period_ID@");
    }
    setC_Period_ID(period.getId());
  }

  private void unselectLines() {
    String sql =
        "UPDATE "
            + MDepreciationExp.Table_Name
            + " SET "
            + MDepreciationExp.COLUMNNAME_A_Depreciation_Entry_ID
            + "=NULL "
            + " WHERE "
            + MDepreciationExp.COLUMNNAME_A_Depreciation_Entry_ID
            + "=?";
    int id = getId();
    if (id <= 0) { // Use old ID is current ID is missing (i.e. object was deleted)
      id = get_IDOld();
    }
    int no = executeUpdateEx(sql, new Object[] {id}, null);
    if (log.isLoggable(Level.FINE)) log.fine("Updated #" + no);
  }

  private void selectLines() {
    // Reset selected lines:
    unselectLines();
    // Select lines:
    final String sql =
        "UPDATE "
            + MDepreciationExp.Table_Name
            + " SET "
            + MDepreciationExp.COLUMNNAME_A_Depreciation_Entry_ID
            + "=?"
            + " WHERE "
            + MDepreciationExp.COLUMNNAME_A_Depreciation_Entry_ID
            + " IS NULL"
            + " AND TRUNC("
            + MDepreciationExp.COLUMNNAME_DateAcct
            + ",'MONTH') = ?"
            + " AND clientId=? AND orgId=?";
    ;
    Timestamp dateAcct = TimeUtil.trunc(getDateAcct(), TimeUtil.TRUNC_MONTH);
    int no =
        executeUpdateEx(
            sql, new Object[] {getId(), dateAcct,  getClientId(),  getOrgId()}, null);
    if (log.isLoggable(Level.FINE)) log.fine("Updated #" + no);
  }

  /** Get Lines */
  public Iterator<MDepreciationExp> getLinesIterator(boolean onlyNotProcessed) {
    final String trxName = null;
    final List<Object> params = new ArrayList<Object>();
    String whereClause = MDepreciationExp.COLUMNNAME_A_Depreciation_Entry_ID + "=?";
    params.add(getId());

    if (onlyNotProcessed) {
      whereClause += " AND " + MDepreciationExp.COLUMNNAME_Processed + "=?";
      params.add(false);
    }

    // ORDER BY clause - very important
    String orderBy =
        MDepreciationExp.COLUMNNAME_A_Asset_ID
            + ","
            + MDepreciationExp.COLUMNNAME_PostingType
            + ","
            + MDepreciationExp.COLUMNNAME_A_Period
            + ","
            + MDepreciationExp.COLUMNNAME_A_Entry_Type;

    Iterator<MDepreciationExp> it =
        new Query(getCtx(), MDepreciationExp.Table_Name, whereClause, trxName)
            .setOrderBy(orderBy)
            .setParameters(params)
            .iterate();
    return it;
  }

  public boolean processIt(String processAction) {
    m_processMsg = null;
    DocumentEngine engine = new DocumentEngine(this, getDocStatus());
    return engine.processIt(processAction, getDocAction());
  } //	processIt

  /** Process Message */
  private String m_processMsg = null;
  /** Just Prepared Flag */
  private boolean m_justPrepared = false;

  public boolean unlockIt() {
    if (log.isLoggable(Level.INFO)) log.info("unlockIt - " + toString());
    //	setProcessing(false);
    return true;
  } //	unlockIt

  public boolean invalidateIt() {
    return false;
  }

  public String prepareIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
    if (m_processMsg != null) {
      return DocAction.Companion.getSTATUS_Invalid();
    }

    MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(),  getOrgId());

    m_justPrepared = true;

    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
    if (m_processMsg != null) {
      return DocAction.Companion.getSTATUS_Invalid();
    }

    setDocAction(X_A_Depreciation_Entry.DOCACTION_Complete);
    return DocAction.Companion.getSTATUS_InProgress();
  }

  public boolean approveIt() {
    if (log.isLoggable(Level.INFO)) log.info("approveIt - " + toString());
    setIsApproved(true);
    return true;
  }

  public boolean rejectIt() {
    if (log.isLoggable(Level.INFO)) log.info("rejectIt - " + toString());
    setIsApproved(false);
    return true;
  } //	rejectIt

  public CompleteActionResult completeIt() {
    //	Re-Check
    if (!m_justPrepared) {
      String status = prepareIt();
      m_justPrepared = false;
      if (!DocAction.Companion.getSTATUS_InProgress().equals(status))
        return new CompleteActionResult(status);
    }
    //	Implicit Approval
    if (!isApproved()) {
      approveIt();
    }

    final MPeriod period = MPeriod.get(getCtx(), getC_Period_ID());

    final ArrayList<Exception> errors = new ArrayList<Exception>();
    final Iterator<MDepreciationExp> it = getLinesIterator(true);
    //
    while (it.hasNext()) {
      try {
        Trx.run(
            null,
            new TrxRunnable() {

              public void run(String trxName) {
                MDepreciationExp depexp = it.next();
                // Check if is in Period
                if (!period.isInPeriod(depexp.getDateAcct())) {
                  throw new AssetException(
                      "The date is not within this Period"
                          + " ("
                          + depexp
                          + ", Data="
                          + depexp.getDateAcct()
                          + ", Period="
                          + period.getName()
                          + ")"); // TODO: translate
                }
                depexp.process();
              }
            });
      } catch (Exception e) {
        log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        errors.add(e);
      }
    }
    //
    if (errors.size() > 0) {
      throw new AssetArrayException(errors);
    }

    //	User Validation
    String valid =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
    if (valid != null) {
      m_processMsg = valid;
      return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
    }

    setProcessed(true);
    setDocAction(X_A_Depreciation_Entry.DOCACTION_Close);
    return new CompleteActionResult(DocAction.Companion.getSTATUS_Completed());
  } //	completeIt

  public boolean voidIt() {
    return false;
  }

  public boolean closeIt() {
    setDocAction(X_A_Depreciation_Entry.DOCACTION_None);
    return true;
  }

  public boolean reverseCorrectIt() {
    return false;
  }

  public boolean reverseAccrualIt() {
    return false;
  }

  public boolean reActivateIt() {
    return false;
  } //	reActivateIt

  public String getSummary() {
    return toString();
  }

  public String getProcessMsg() {
    return m_processMsg;
  }

  public int getDoc_User_ID() {
    return getCreatedBy();
  }

  public BigDecimal getApprovalAmt() {
    return null;
  }

  public File createPDF() {
    return null;
  }

  public String getDocumentInfo() {
    return getDocumentNo();
  }

  public static void deleteFacts(MDepreciationExp depexp) {
    final String sql = "DELETE FROM Fact_Acct WHERE AD_Table_ID=? AND Record_ID=? AND Line_ID=?";
    Object[] params =
        new Object[] {
          I_A_Depreciation_Entry.Table_ID, depexp.getA_Depreciation_Entry_ID(), depexp.getId()
        };
    executeUpdateEx(sql, params, null);
  }

  @Override
  public void setDoc(IDoc doc) {}

  @Override
  public void setProcessedOn(String processed, boolean b, boolean b1) {}
}
