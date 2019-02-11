package org.compiere.accounting;

import org.compiere.invoicing.MConversionRate;
import org.compiere.util.Msg;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.executeUpdate;

public class MTimeExpenseLine extends X_S_TimeExpenseLine {
  /** */
  private static final long serialVersionUID = -815975460880303779L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param S_TimeExpenseLine_ID id
   * @param trxName transaction
   */
  public MTimeExpenseLine(Properties ctx, int S_TimeExpenseLine_ID, String trxName) {
    super(ctx, S_TimeExpenseLine_ID, trxName);
    if (S_TimeExpenseLine_ID == 0) {
      //	setS_TimeExpenseLine_ID (0);		//	PK
      //	setS_TimeExpense_ID (0);			//	Parent
      setQty(Env.ONE);
      setQtyInvoiced(Env.ZERO);
      setQtyReimbursed(Env.ZERO);
      //
      setExpenseAmt(Env.ZERO);
      setConvertedAmt(Env.ZERO);
      setPriceReimbursed(Env.ZERO);
      setInvoicePrice(Env.ZERO);
      setPriceInvoiced(Env.ZERO);
      //
      setDateExpense(new Timestamp(System.currentTimeMillis()));
      setIsInvoiced(false);
      setIsTimeReport(false);
      setLine(10);
      setProcessed(false);
    }
  } //	MTimeExpenseLine

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MTimeExpenseLine(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MTimeExpenseLine

  /** Parent */
  private MTimeExpense m_parent = null;

  /**
   * Get Parent
   *
   * @return parent
   */
  public MTimeExpense getParent() {
    if (m_parent == null)
      m_parent = new MTimeExpense(getCtx(), getS_TimeExpense_ID(), null);
    return m_parent;
  } //	getParent

  /** Currency of Report */
  private int m_C_Currency_Report_ID = 0;

  /**
   * Get Qty Invoiced
   *
   * @return entered or qty
   */
  public BigDecimal getQtyInvoiced() {
    BigDecimal bd = super.getQtyInvoiced();
    if (Env.ZERO.compareTo(bd) == 0) return getQty();
    return bd;
  } //	getQtyInvoiced

  /**
   * Get Qty Reimbursed
   *
   * @return entered or qty
   */
  public BigDecimal getQtyReimbursed() {
    BigDecimal bd = super.getQtyReimbursed();
    if (Env.ZERO.compareTo(bd) == 0) return getQty();
    return bd;
  } //	getQtyReimbursed

  /**
   * Get Price Invoiced
   *
   * @return entered or invoice price
   */
  public BigDecimal getPriceInvoiced() {
    BigDecimal bd = super.getPriceInvoiced();
    if (Env.ZERO.compareTo(bd) == 0) return getInvoicePrice();
    return bd;
  } //	getPriceInvoiced

  /**
   * Get Price Reimbursed
   *
   * @return entered or converted amt
   */
  public BigDecimal getPriceReimbursed() {
    BigDecimal bd = super.getPriceReimbursed();
    if (Env.ZERO.compareTo(bd) == 0) return getConvertedAmt();
    return bd;
  } //	getPriceReimbursed

  /**
   * Get Approval Amt
   *
   * @return qty * converted amt
   */
  public BigDecimal getApprovalAmt() {
    return getQty().multiply(getConvertedAmt());
  } //	getApprovalAmt

  /**
   * Get C_Currency_ID of Report (Price List)
   *
   * @return currency
   */
  public int getC_Currency_Report_ID() {
    if (m_C_Currency_Report_ID != 0) return m_C_Currency_Report_ID;
    //	Get it from header
    MTimeExpense te = new MTimeExpense(getCtx(), getS_TimeExpense_ID(), null);
    m_C_Currency_Report_ID = te.getC_Currency_ID();
    return m_C_Currency_Report_ID;
  } //	getC_Currency_Report_ID

  /**
   * Set C_Currency_ID of Report (Price List)
   *
   * @param C_Currency_ID currency
   */
  protected void setC_Currency_Report_ID(int C_Currency_ID) {
    m_C_Currency_Report_ID = C_Currency_ID;
  } //	getC_Currency_Report_ID

  /**
   * Before Save. Calculate converted amt
   *
   * @param newRecord new
   * @return true
   */
  protected boolean beforeSave(boolean newRecord) {
    if (newRecord && getParent().isComplete()) {
      log.saveError("ParentComplete", Msg.translate(getCtx(), "S_TimeExpenseLine"));
      return false;
    }
    //	Calculate Converted Amount
    if (newRecord || is_ValueChanged("ExpenseAmt") || is_ValueChanged("C_Currency_ID")) {
      if (getC_Currency_ID() == getC_Currency_Report_ID()) setConvertedAmt(getExpenseAmt());
      else {
        setConvertedAmt(
            MConversionRate.convert(
                getCtx(),
                getExpenseAmt(),
                getC_Currency_ID(),
                getC_Currency_Report_ID(),
                getDateExpense(),
                0,
                 getClientId(),
                 getOrgId()));
      }
    }
    if (isTimeReport()) {
      setExpenseAmt(Env.ZERO);
      setConvertedAmt(Env.ZERO);
    }
    return true;
  } //	beforeSave

  /**
   * After Save
   *
   * @param newRecord new
   * @param success success
   * @return success
   */
  protected boolean afterSave(boolean newRecord, boolean success) {
    if (success) {
      updateHeader();
      if (newRecord || is_ValueChanged("S_ResourceAssignment_ID")) {
        int S_ResourceAssignment_ID = getS_ResourceAssignment_ID();
        int old_S_ResourceAssignment_ID = 0;
        if (!newRecord) {
          Object ii = get_ValueOld("S_ResourceAssignment_ID");
          if (ii instanceof Integer) {
            old_S_ResourceAssignment_ID = ((Integer) ii).intValue();
            //	Changed Assignment
            if (old_S_ResourceAssignment_ID != S_ResourceAssignment_ID
                && old_S_ResourceAssignment_ID != 0) {
              MResourceAssignment ra =
                  new MResourceAssignment(getCtx(), old_S_ResourceAssignment_ID, null);
              ra.delete(false);
            }
          }
        }
        //	Sync Assignment
        if (S_ResourceAssignment_ID != 0) {
          MResourceAssignment ra =
              new MResourceAssignment(getCtx(), S_ResourceAssignment_ID, null);
          if (getQty().compareTo(ra.getQty()) != 0) {
            ra.setQty(getQty());
            if (getDescription() != null && getDescription().length() > 0)
              ra.setDescription(getDescription());
            ra.saveEx();
          }
        }
      }
    }
    return success;
  } //	afterSave

  /**
   * After Delete
   *
   * @param success success
   * @return success
   */
  protected boolean afterDelete(boolean success) {
    if (success) {
      updateHeader();
      //
      Object ii = get_ValueOld("S_ResourceAssignment_ID");
      if (ii instanceof Integer) {
        int old_S_ResourceAssignment_ID = ((Integer) ii).intValue();
        //	Deleted Assignment
        if (old_S_ResourceAssignment_ID != 0) {
          MResourceAssignment ra =
              new MResourceAssignment(getCtx(), old_S_ResourceAssignment_ID, null);
          ra.delete(false);
        }
      }
    }
    return success;
  } //	afterDelete

  /** Update Header. Set Approved Amount */
  private void updateHeader() {
    String sql =
        "UPDATE S_TimeExpense te"
            + " SET ApprovalAmt = "
            + "(SELECT SUM(Qty*ConvertedAmt) FROM S_TimeExpenseLine tel "
            + "WHERE te.S_TimeExpense_ID=tel.S_TimeExpense_ID) "
            + "WHERE S_TimeExpense_ID="
            + getS_TimeExpense_ID();
    @SuppressWarnings("unused")
    int no = executeUpdate(sql);
  } //	updateHeader
} //	MTimeExpenseLine
