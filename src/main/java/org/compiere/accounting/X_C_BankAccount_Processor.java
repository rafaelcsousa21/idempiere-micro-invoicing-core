package org.compiere.accounting;

import org.compiere.model.I_C_BankAccount_Processor;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_BankAccount_Processor
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_BankAccount_Processor extends PO
        implements I_C_BankAccount_Processor, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_BankAccount_Processor(Properties ctx, int C_BankAccount_Processor_ID) {
        super(ctx, C_BankAccount_Processor_ID);
        /**
         * if (C_BankAccount_Processor_ID == 0) { setAcceptAMEX (false); // N setAcceptATM (false); // N
         * setAcceptCheck (false); // N setAcceptCorporate (false); // N setAcceptDiners (false); // N
         * setAcceptDirectDebit (false); // N setAcceptDirectDeposit (false); // N setAcceptDiscover
         * (false); // N setAcceptMC (false); // N setAcceptVisa (false); // N setC_BankAccount_ID (0);
         * setC_PaymentProcessor_ID (0); setPassword (null); setRequireVV (false); // N setUserID
         * (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_BankAccount_Processor(Properties ctx, ResultSet rs) {
        super(ctx, rs);
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
        StringBuffer sb = new StringBuffer("X_C_BankAccount_Processor[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Accept AMEX.
     *
     * @return Accept American Express Card
     */
    public boolean isAcceptAMEX() {
        Object oo = get_Value(COLUMNNAME_AcceptAMEX);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Accept ATM.
     *
     * @return Accept Bank ATM Card
     */
    public boolean isAcceptATM() {
        Object oo = get_Value(COLUMNNAME_AcceptATM);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Accept Electronic Check.
     *
     * @return Accept ECheck (Electronic Checks)
     */
    public boolean isAcceptCheck() {
        Object oo = get_Value(COLUMNNAME_AcceptCheck);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Accept Corporate.
     *
     * @return Accept Corporate Purchase Cards
     */
    public boolean isAcceptCorporate() {
        Object oo = get_Value(COLUMNNAME_AcceptCorporate);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Accept Diners.
     *
     * @return Accept Diner's Club
     */
    public boolean isAcceptDiners() {
        Object oo = get_Value(COLUMNNAME_AcceptDiners);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Accept Direct Debit.
     *
     * @return Accept Direct Debits (vendor initiated)
     */
    public boolean isAcceptDirectDebit() {
        Object oo = get_Value(COLUMNNAME_AcceptDirectDebit);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Accept Direct Deposit.
     *
     * @return Accept Direct Deposit (payee initiated)
     */
    public boolean isAcceptDirectDeposit() {
        Object oo = get_Value(COLUMNNAME_AcceptDirectDeposit);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Accept Discover.
     *
     * @return Accept Discover Card
     */
    public boolean isAcceptDiscover() {
        Object oo = get_Value(COLUMNNAME_AcceptDiscover);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Accept MasterCard.
     *
     * @return Accept Master Card
     */
    public boolean isAcceptMC() {
        Object oo = get_Value(COLUMNNAME_AcceptMC);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Accept Visa.
     *
     * @return Accept Visa Cards
     */
    public boolean isAcceptVisa() {
        Object oo = get_Value(COLUMNNAME_AcceptVisa);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
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
     * Set Bank Account.
     *
     * @param C_BankAccount_ID Account at the Bank
     */
    public void setC_BankAccount_ID(int C_BankAccount_ID) {
        if (C_BankAccount_ID < 1) set_ValueNoCheck(COLUMNNAME_C_BankAccount_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
    }

    /**
     * Get Currency.
     *
     * @return The Currency for this record
     */
    public int getC_Currency_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_Currency_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Payment Processor.
     *
     * @return Payment processor for electronic payments
     */
    public int getC_PaymentProcessor_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_C_PaymentProcessor_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Payment Processor.
     *
     * @param C_PaymentProcessor_ID Payment processor for electronic payments
     */
    public void setC_PaymentProcessor_ID(int C_PaymentProcessor_ID) {
        if (C_PaymentProcessor_ID < 1) set_ValueNoCheck(COLUMNNAME_C_PaymentProcessor_ID, null);
        else set_ValueNoCheck(COLUMNNAME_C_PaymentProcessor_ID, Integer.valueOf(C_PaymentProcessor_ID));
    }

    @Override
    public int getTableId() {
        return I_C_BankAccount_Processor.Table_ID;
    }
}
