package org.compiere.accounting;

import org.compiere.model.I_C_Cash;
import org.compiere.orm.BasePOName;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_Cash
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Cash extends BasePOName implements I_C_Cash, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_Cash(Properties ctx, int C_Cash_ID, String trxName) {
    super(ctx, C_Cash_ID, trxName);
  }

  /** Load Constructor */
  public X_C_Cash(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 3 - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_Cash[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Set Beginning Balance.
   *
   * @param BeginningBalance Balance prior to any transactions
   */
  public void setBeginningBalance(BigDecimal BeginningBalance) {
    set_Value(COLUMNNAME_BeginningBalance, BeginningBalance);
  }

  /**
   * Get Beginning Balance.
   *
   * @return Balance prior to any transactions
   */
  public BigDecimal getBeginningBalance() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_BeginningBalance);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Get Activity.
   *
   * @return Business Activity
   */
  public int getC_Activity_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Activity_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Campaign.
   *
   * @return Marketing Campaign
   */
  public int getC_Campaign_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Campaign_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Cash Book.
   *
   * @param C_CashBook_ID Cash Book for recording petty cash transactions
   */
  public void setC_CashBook_ID(int C_CashBook_ID) {
    if (C_CashBook_ID < 1) set_ValueNoCheck(COLUMNNAME_C_CashBook_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_CashBook_ID, Integer.valueOf(C_CashBook_ID));
  }

  /**
   * Get Cash Book.
   *
   * @return Cash Book for recording petty cash transactions
   */
  public int getC_CashBook_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_CashBook_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Cash Journal.
   *
   * @return Cash Journal
   */
  public int getC_Cash_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Cash_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Project.
   *
   * @return Financial Project
   */
  public int getC_Project_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Project_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Account Date.
   *
   * @param DateAcct Accounting Date
   */
  public void setDateAcct(Timestamp DateAcct) {
    set_Value(COLUMNNAME_DateAcct, DateAcct);
  }

  /**
   * Get Account Date.
   *
   * @return Accounting Date
   */
  public Timestamp getDateAcct() {
    return (Timestamp) get_Value(COLUMNNAME_DateAcct);
  }

  /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(COLUMNNAME_Description, Description);
  }

  /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
  }

  /** DocAction AD_Reference_ID=135 */
  public static final int DOCACTION_AD_Reference_ID = 135;
  /** Complete = CO */
  public static final String DOCACTION_Complete = "CO";
  /** Approve = AP */
  public static final String DOCACTION_Approve = "AP";
  /** Reject = RJ */
  public static final String DOCACTION_Reject = "RJ";
  /** Post = PO */
  public static final String DOCACTION_Post = "PO";
  /** Void = VO */
  public static final String DOCACTION_Void = "VO";
  /** Close = CL */
  public static final String DOCACTION_Close = "CL";
  /** Reverse - Correct = RC */
  public static final String DOCACTION_Reverse_Correct = "RC";
  /** Reverse - Accrual = RA */
  public static final String DOCACTION_Reverse_Accrual = "RA";
  /** Invalidate = IN */
  public static final String DOCACTION_Invalidate = "IN";
  /** Re-activate = RE */
  public static final String DOCACTION_Re_Activate = "RE";
  /** <None> = -- */
  public static final String DOCACTION_None = "--";
  /** Prepare = PR */
  public static final String DOCACTION_Prepare = "PR";
  /** Unlock = XL */
  public static final String DOCACTION_Unlock = "XL";
  /** Wait Complete = WC */
  public static final String DOCACTION_WaitComplete = "WC";
  /**
   * Set Document Action.
   *
   * @param DocAction The targeted status of the document
   */
  public void setDocAction(String DocAction) {

    set_Value(COLUMNNAME_DocAction, DocAction);
  }

  /**
   * Get Document Action.
   *
   * @return The targeted status of the document
   */
  public String getDocAction() {
    return (String) get_Value(COLUMNNAME_DocAction);
  }

  /** DocStatus AD_Reference_ID=131 */
  public static final int DOCSTATUS_AD_Reference_ID = 131;
  /** Drafted = DR */
  public static final String DOCSTATUS_Drafted = "DR";
  /** Completed = CO */
  public static final String DOCSTATUS_Completed = "CO";
  /** Approved = AP */
  public static final String DOCSTATUS_Approved = "AP";
  /** Not Approved = NA */
  public static final String DOCSTATUS_NotApproved = "NA";
  /** Voided = VO */
  public static final String DOCSTATUS_Voided = "VO";
  /** Invalid = IN */
  public static final String DOCSTATUS_Invalid = "IN";
  /** Reversed = RE */
  public static final String DOCSTATUS_Reversed = "RE";
  /** Closed = CL */
  public static final String DOCSTATUS_Closed = "CL";
  /** Unknown = ?? */
  public static final String DOCSTATUS_Unknown = "??";
  /** In Progress = IP */
  public static final String DOCSTATUS_InProgress = "IP";
  /** Waiting Payment = WP */
  public static final String DOCSTATUS_WaitingPayment = "WP";
  /** Waiting Confirmation = WC */
  public static final String DOCSTATUS_WaitingConfirmation = "WC";
  /**
   * Set Document Status.
   *
   * @param DocStatus The current status of the document
   */
  public void setDocStatus(String DocStatus) {

    set_Value(COLUMNNAME_DocStatus, DocStatus);
  }

  /**
   * Get Document Status.
   *
   * @return The current status of the document
   */
  public String getDocStatus() {
    return (String) get_Value(COLUMNNAME_DocStatus);
  }

  /**
   * Set Ending balance.
   *
   * @param EndingBalance Ending or closing balance
   */
  public void setEndingBalance(BigDecimal EndingBalance) {
    set_Value(COLUMNNAME_EndingBalance, EndingBalance);
  }

  /**
   * Get Ending balance.
   *
   * @return Ending or closing balance
   */
  public BigDecimal getEndingBalance() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_EndingBalance);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Approved.
   *
   * @param IsApproved Indicates if this document requires approval
   */
  public void setIsApproved(boolean IsApproved) {
    set_Value(COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
  }

  /**
   * Get Approved.
   *
   * @return Indicates if this document requires approval
   */
  public boolean isApproved() {
    Object oo = get_Value(COLUMNNAME_IsApproved);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Posted.
   *
   * @param Posted Posting status
   */
  public void setPosted(boolean Posted) {
    set_Value(COLUMNNAME_Posted, Boolean.valueOf(Posted));
  }

    /**
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
  }

  /**
   * Get Processed.
   *
   * @return The document has been processed
   */
  public boolean isProcessed() {
    Object oo = get_Value(COLUMNNAME_Processed);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Set Process Now.
   *
   * @param Processing Process Now
   */
  public void setProcessing(boolean Processing) {
    set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
  }

    /**
   * Set Statement date.
   *
   * @param StatementDate Date of the statement
   */
  public void setStatementDate(Timestamp StatementDate) {
    set_Value(COLUMNNAME_StatementDate, StatementDate);
  }

  /**
   * Get Statement date.
   *
   * @return Date of the statement
   */
  public Timestamp getStatementDate() {
    return (Timestamp) get_Value(COLUMNNAME_StatementDate);
  }

  /**
   * Set Statement difference.
   *
   * @param StatementDifference Difference between statement ending balance and actual ending
   *     balance
   */
  public void setStatementDifference(BigDecimal StatementDifference) {
    set_Value(COLUMNNAME_StatementDifference, StatementDifference);
  }

  /**
   * Get Statement difference.
   *
   * @return Difference between statement ending balance and actual ending balance
   */
  public BigDecimal getStatementDifference() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_StatementDifference);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Get User Element List 1.
   *
   * @return User defined list element #1
   */
  public int getUser1_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_User1_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get User Element List 2.
   *
   * @return User defined list element #2
   */
  public int getUser2_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_User2_ID);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_C_Cash.Table_ID;
  }
}
