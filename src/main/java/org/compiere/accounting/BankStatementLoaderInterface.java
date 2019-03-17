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
    boolean init(MBankStatementLoader controller);

    /**
     * Verify whether the data to be imported is valid
     *
     * @return Data is valid If the actual loaders does not do any validity checks it will just return
     * true.
     */
    boolean isValid();

    /**
     * Start importing statement lines
     *
     * @return Statement lines imported successfully
     */
    boolean loadLines();

    /**
     * Return the most recent error
     *
     * @return Error message This error message will be handled as a ADempiere message, (e.g. it can
     * be translated)
     */
    String getLastErrorMessage();

    /**
     * Return the most recent error description
     *
     * @return Error description This is an additional error description, it can be used to provided
     * descriptive information, such as a file name or SQL error, that can not be translated by
     * the ADempiere message system.
     */
    String getLastErrorDescription();

    /**
     * The routing number of the bank account for the statement line.
     *
     * @return Routing number
     */
    String getRoutingNo();

    /**
     * The account number of the bank account for the statement line.
     *
     * @return Bank account number
     */
    String getBankAccountNo();

    /**
     * The IBAN of the bank account for the statement line.
     *
     * @return IBAN
     */
    String getIBAN();

    /**
     * Additional reference information Statement level reference information. If a specific loader
     * class does not provided this, it is allowed to return null.
     *
     * @return Statement Reference
     */
    String getStatementReference();

    /**
     * Statement Date Date of the bank statement. If a specific loader does not provide this, it is
     * allowed to return null.
     *
     * @return Statement Date
     */
    Timestamp getStatementDate();

    /**
     * Transaction ID assigned by the bank. For OFX this is the <FITID> If a specific loader does not
     * provide this, it is allowed to return null.
     *
     * @return Transaction ID
     */
    String getTrxID();

    /**
     * Additional reference information Statement line level reference information. For OFX this is
     * the <REFNUM> field. If a specific loader does not provided this, it is allowed to return null.
     *
     * @return Reference
     */
    String getReference();

    /**
     * Check number Check number, in case the transaction was initiated by a check. For OFX this is
     * the <CHECKNUM> field, for MS-Money (OFC) this is the <CHKNUM> field. If a specific loader does
     * not provide this, it is allowed to return null.
     *
     * @return Transaction reference
     */
    String getCheckNo();

    /**
     * Payee name Name information, for OFX this is the <NAME> or <PAYEE><NAME> field If a specific
     * loader class does not provide this, it is allowed to return null.
     *
     * @return Payee name
     */
    String getPayeeName();

    /**
     * Payee account Account information of "the other party" If a specific loader class does not
     * provide this, it is allowed to return null.
     *
     * @return Payee bank account number
     */
    String getPayeeAccountNo();

    /**
     * Statement line date This has to be provided by all loader classes.
     *
     * @return Statement line date
     */
    Timestamp getStatementLineDate();

    /**
     * Effective date Date at the funds became available. If a specific loader does not provide this,
     * it is allowed to return null.
     *
     * @return Effective date
     */
    Timestamp getValutaDate();

    /**
     * Transaction type
     *
     * @return Transaction type This returns the transaction type as used by the bank Whether a
     * transaction is credit or debit depends on the amount (i.e. negative), this field is for
     * reference only. If a specific loader class does not provide this, it is allowed to return
     * null.
     */
    String getTrxType();

    /**
     * Currency
     *
     * @return Currency Return the currency, if included in the statement data. It is returned as it
     * appears in the import data, it should not be processed by the loader in any way. If a
     * specific loader class does not provide this, it is allowed to return null.
     */
    String getCurrency();

    /**
     * Statement line amount
     *
     * @return Statement Line Amount This has to be provided by all loader classes.
     */
    BigDecimal getStmtAmt();

    /**
     * Transaction Amount
     *
     * @return Transaction Amount
     */
    BigDecimal getTrxAmt();

    /**
     * Interest Amount
     *
     * @return Interest Amount
     */
    BigDecimal getInterestAmt();

    /**
     * Transaction memo
     *
     * @return Memo Additional descriptive information. For OFX this is the <MEMO> filed, for SWIFT
     * MT940 this is the "86" line. If a specific loader does not provide this, it is allowed to
     * return null.
     */
    String getMemo();

    /**
     * Charge name
     *
     * @return Charge name Name of the charge, in case this transaction is a bank charge. If a
     * specific loader class does not provide this, it is allowed to return null.
     */
    String getChargeName();

    /**
     * Charge amount
     *
     * @return Charge amount Name of the charge, in case this transaction is a bank charge. If a
     * specific loader class does not provide this, it is allowed to return null.
     */
    BigDecimal getChargeAmt();
}
