package org.compiere.accounting;

import org.compiere.model.I_C_BankStatement;
import org.compiere.orm.BasePOName;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_BankStatement
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_BankStatement extends BasePOName implements I_C_BankStatement, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_BankStatement(Properties ctx, int C_BankStatement_ID, String trxName) {
    super(ctx, C_BankStatement_ID, trxName);
    /**
     * if (C_BankStatement_ID == 0) { setC_BankAccount_ID (0); setC_BankStatement_ID (0);
     * setDateAcct (new Timestamp( System.currentTimeMillis() )); setDocAction (null); // CO
     * setDocStatus (null); // DR setEndingBalance (Env.ZERO); setIsApproved (false); // N
     * setIsManual (true); // Y setName (null); // @#Date@ setPosted (false); // N setProcessed
     * (false); setStatementDate (new Timestamp( System.currentTimeMillis() )); // @Date@ }
     */
  }

  /** Load Constructor */
  public X_C_BankStatement(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_BankStatement[").append(getId()).append("]");
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
   * Set Bank Account.
   *
   * @param C_BankAccount_ID Account at the Bank
   */
  public void setC_BankAccount_ID(int C_BankAccount_ID) {
    if (C_BankAccount_ID < 1) set_Value(COLUMNNAME_C_BankAccount_ID, null);
    else set_Value(COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
  }

  /**
   * Get Bank Account.
   *
   * @return Account at the Bank
   */
  public int getC_BankAccount_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BankAccount_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Bank Statement.
   *
   * @return Bank Statement of account
   */
  public int getC_BankStatement_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BankStatement_ID);
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

    /** Complete = CO */
  public static final String DOCACTION_Complete = "CO";
    /** Close = CL */
  public static final String DOCACTION_Close = "CL";
    /** <None> = -- */
  public static final String DOCACTION_None = "--";
  /** Prepare = PR */
  public static final String DOCACTION_Prepare = "PR";

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
    /** In Progress = IP */
  public static final String DOCSTATUS_InProgress = "IP";

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
   * Set EFT Statement Date.
   *
   * @param EftStatementDate Electronic Funds Transfer Statement Date
   */
  public void setEftStatementDate(Timestamp EftStatementDate) {
    set_Value(COLUMNNAME_EftStatementDate, EftStatementDate);
  }

    /**
   * Set EFT Statement Reference.
   *
   * @param EftStatementReference Electronic Funds Transfer Statement Reference
   */
  public void setEftStatementReference(String EftStatementReference) {
    set_Value(COLUMNNAME_EftStatementReference, EftStatementReference);
  }

  /**
   * Get EFT Statement Reference.
   *
   * @return Electronic Funds Transfer Statement Reference
   */
  public String getEftStatementReference() {
    return (String) get_Value(COLUMNNAME_EftStatementReference);
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
   * Set Manual.
   *
   * @param IsManual This is a manual process
   */
  public void setIsManual(boolean IsManual) {
    set_Value(COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
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

  @Override
  public int getTableId() {
    return I_C_BankStatement.Table_ID;
  }
}
