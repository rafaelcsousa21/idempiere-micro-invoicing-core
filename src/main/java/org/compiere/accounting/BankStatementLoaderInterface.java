package org.compiere.accounting;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface BankStatementLoaderInterface {
  /**
   * Initialize the loader
   *
   * @param controller Reference to the MBankStatementLoader controller object
   * @return Initialized successfully
   */
  public boolean init(MBankStatementLoader controller);

  /**
   * Verify whether the data to be imported is valid
   *
   * @return Data is valid If the actual loaders does not do any validity checks it will just return
   *     true.
   */
  public boolean isValid();

  /**
   * Start importing statement lines
   *
   * @return Statement lines imported successfully
   */
  public boolean loadLines();

  /**
   * Return the most recent error
   *
   * @return Error message This error message will be handled as a ADempiere message, (e.g. it can
   *     be translated)
   */
  public String getLastErrorMessage();

  /**
   * Return the most recent error description
   *
   * @return Error description This is an additional error description, it can be used to provided
   *     descriptive information, such as a file name or SQL error, that can not be translated by
   *     the ADempiere message system.
   */
  public String getLastErrorDescription();

  /**
   * The last time this loader aquired bank statement data. For OFX this is the <DTEND> value. This
   * is generally only available\ after loadLines() has been called. If a specific loader class does
   * not provided this information it is allowed to return null
   *
   * @return Date last run
   */
  public Timestamp getDateLastRun();

  /**
   * The routing number of the bank account for the statement line.
   *
   * @return Routing number
   */
  public String getRoutingNo();

  /**
   * The account number of the bank account for the statement line.
   *
   * @return Bank account number
   */
  public String getBankAccountNo();

  /**
   * The IBAN of the bank account for the statement line.
   *
   * @return IBAN
   */
  public String getIBAN();

  /**
   * Additional reference information Statement level reference information. If a specific loader
   * class does not provided this, it is allowed to return null.
   *
   * @return Statement Reference
   */
  public String getStatementReference();

  /**
   * Statement Date Date of the bank statement. If a specific loader does not provide this, it is
   * allowed to return null.
   *
   * @return Statement Date
   */
  public Timestamp getStatementDate();

  /**
   * Transaction ID assigned by the bank. For OFX this is the <FITID> If a specific loader does not
   * provide this, it is allowed to return null.
   *
   * @return Transaction ID
   */
  public String getTrxID();

  /**
   * Additional reference information Statement line level reference information. For OFX this is
   * the <REFNUM> field. If a specific loader does not provided this, it is allowed to return null.
   *
   * @return Reference
   */
  public String getReference();

  /**
   * Check number Check number, in case the transaction was initiated by a check. For OFX this is
   * the <CHECKNUM> field, for MS-Money (OFC) this is the <CHKNUM> field. If a specific loader does
   * not provide this, it is allowed to return null.
   *
   * @return Transaction reference
   */
  public String getCheckNo();

  /**
   * Payee name Name information, for OFX this is the <NAME> or <PAYEE><NAME> field If a specific
   * loader class does not provide this, it is allowed to return null.
   *
   * @return Payee name
   */
  public String getPayeeName();

  /**
   * Payee account Account information of "the other party" If a specific loader class does not
   * provide this, it is allowed to return null.
   *
   * @return Payee bank account number
   */
  public String getPayeeAccountNo();

  /**
   * Statement line date This has to be provided by all loader classes.
   *
   * @return Statement line date
   */
  public Timestamp getStatementLineDate();

  /**
   * Effective date Date at the funds became available. If a specific loader does not provide this,
   * it is allowed to return null.
   *
   * @return Effective date
   */
  public Timestamp getValutaDate();

  /**
   * Transaction type
   *
   * @return Transaction type This returns the transaction type as used by the bank Whether a
   *     transaction is credit or debit depends on the amount (i.e. negative), this field is for
   *     reference only. If a specific loader class does not provide this, it is allowed to return
   *     null.
   */
  public String getTrxType();

  /**
   * Indicates whether this transaction is a reversal
   *
   * @return true if this is a reversal
   */
  public boolean getIsReversal();

  /**
   * Currency
   *
   * @return Currency Return the currency, if included in the statement data. It is returned as it
   *     appears in the import data, it should not be processed by the loader in any way. If a
   *     specific loader class does not provide this, it is allowed to return null.
   */
  public String getCurrency();

  /**
   * Statement line amount
   *
   * @return Statement Line Amount This has to be provided by all loader classes.
   */
  public BigDecimal getStmtAmt();

  /**
   * Transaction Amount
   *
   * @return Transaction Amount
   */
  public BigDecimal getTrxAmt();

  /**
   * Interest Amount
   *
   * @return Interest Amount
   */
  public BigDecimal getInterestAmt();

  /**
   * Transaction memo
   *
   * @return Memo Additional descriptive information. For OFX this is the <MEMO> filed, for SWIFT
   *     MT940 this is the "86" line. If a specific loader does not provide this, it is allowed to
   *     return null.
   */
  public String getMemo();

  /**
   * Charge name
   *
   * @return Charge name Name of the charge, in case this transaction is a bank charge. If a
   *     specific loader class does not provide this, it is allowed to return null.
   */
  public String getChargeName();

  /**
   * Charge amount
   *
   * @return Charge amount Name of the charge, in case this transaction is a bank charge. If a
   *     specific loader class does not provide this, it is allowed to return null.
   */
  public BigDecimal getChargeAmt();
}
