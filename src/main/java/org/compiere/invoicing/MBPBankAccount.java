package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.bank.IBAN;
import org.compiere.bank.MBank;
import org.compiere.crm.MBPartner;
import org.compiere.crm.MLocation;
import org.compiere.crm.MUser;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.orm.MSysConfig;
import org.compiere.orm.Query;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;

import java.util.List;

/**
 * BP Bank Account Model
 *
 * @author Jorg Janke
 * @version $Id: MBPBankAccount.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MBPBankAccount extends X_C_BP_BankAccount {
    /**
     *
     */
    private static final long serialVersionUID = 6826961806519015878L;
    /**
     * Logger
     */
    @SuppressWarnings("unused")
    private static CLogger s_log = CLogger.getCLogger(MBPBankAccount.class);
    /**
     * Bank Link
     */
    private MBank m_bank = null;

    /**
     * ************************************************************************ Constructor
     *
     * @param ctx                 context
     * @param C_BP_BankAccount_ID BP bank account
     * @param trxName             transaction
     */
    public MBPBankAccount(int C_BP_BankAccount_ID) {
        super(C_BP_BankAccount_ID);
        if (C_BP_BankAccount_ID == 0) {
            //	setBusinessPartnerId (0);
            setIsACH(false);
            setBPBankAcctUse(X_C_BP_BankAccount.BPBANKACCTUSE_Both);
        }
    } //	MBP_BankAccount

    /**
     * Constructor
     *
     * @param ctx context
     */
    public MBPBankAccount(Row row) {
        super(row);
    } //	MBP_BankAccount

    /**
     * Constructor
     *
     * @param ctx      context
     * @param bp       BP
     * @param bpc      BP Contact
     * @param location Location
     */
    public MBPBankAccount(MBPartner bp, MUser bpc, MLocation location) {
        this(0);
        setIsACH(false);
        //
        setBusinessPartnerId(bp.getBusinessPartnerId());
        //
        setAccountName(bpc.getName());
        setAccountEMail(bpc.getEMail());
        //
        setAccountStreet(location.getAddress1());
        setAccountCity(location.getCity());
        setAccountZip(location.getPostal());
        setAccountState(location.getRegionName(true));
        setAccountCountry(location.getCountryName());
    } //	MBP_BankAccount

    /**
     * Get Accounts Of BPartner
     *
     * @param ctx           context
     * @param C_BPartner_ID bpartner
     * @return
     */
    public static MBPBankAccount[] getOfBPartner(int C_BPartner_ID) {
        final String whereClause = MBPBankAccount.COLUMNNAME_C_BPartner_ID + "=?";
        List<MBPBankAccount> list =
                new Query(I_C_BP_BankAccount.Table_Name, whereClause)
                        .setParameters(C_BPartner_ID)
                        .setOnlyActiveRecords(true)
                        .list();

        MBPBankAccount[] retValue = new MBPBankAccount[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getOfBPartner

    /**
     * Is Direct Deposit
     *
     * @return true if dd
     */
    public boolean isDirectDeposit() {
        if (!isACH()) return false;
        String s = getBPBankAcctUse();
        if (s == null) return true;
        return (s.equals(X_C_BP_BankAccount.BPBANKACCTUSE_Both)
                || s.equals(X_C_BP_BankAccount.BPBANKACCTUSE_DirectDeposit));
    } //	isDirectDeposit

    /**
     * Is Direct Debit
     *
     * @return true if dd
     */
    public boolean isDirectDebit() {
        if (!isACH()) return false;
        String s = getBPBankAcctUse();
        if (s == null) return true;
        return (s.equals(X_C_BP_BankAccount.BPBANKACCTUSE_Both)
                || s.equals(X_C_BP_BankAccount.BPBANKACCTUSE_DirectDebit));
    } //	isDirectDebit

    /**
     * Get Bank
     *
     * @return bank
     */
    public MBank getBank() {
        int C_Bank_ID = getBankId();
        if (C_Bank_ID == 0) return null;
        if (m_bank == null) m_bank = new MBank(C_Bank_ID);
        return m_bank;
    } //	getBank

    /**
     * Get Routing No
     *
     * @return routing No
     */
    public String getRoutingNo() {
        MBank bank = getBank();
        String rt = super.getRoutingNo();
        if (bank != null) rt = bank.getRoutingNo();
        return rt;
    } //	getRoutingNo

    /**
     * Get SwiftCode
     *
     * @return SwiftCode
     */
    public String getSwiftCode() {
        MBank bank = getBank();
        if (bank != null) return bank.getSwiftCode();
        return null;
    } //	getSwiftCode

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        //	maintain routing on bank level
        if (isACH() && getBank() != null) setRoutingNo(null);
        //
        if (getCreditCardNumber() != null) {
            String encrpytedCCNo = PaymentUtil.encrpytCreditCard(getCreditCardNumber());
            if (!encrpytedCCNo.equals(getCreditCardNumber())) setCreditCardNumber(encrpytedCCNo);
        }

        if (getCreditCardVV() != null) {
            String encrpytedCvv = PaymentUtil.encrpytCvv(getCreditCardVV());
            if (!encrpytedCvv.equals(getCreditCardVV())) setCreditCardVV(encrpytedCvv);
        }

        if (MSysConfig.getBooleanValue(
                MSysConfig.IBAN_VALIDATION, true, Env.getClientId())) {
            if (!Util.isEmpty(getIBAN())) {
                setIBAN(IBAN.normalizeIBAN(getIBAN()));
                if (!IBAN.isValid(getIBAN())) {
                    log.saveError("Error", "IBAN is invalid");
                    return false;
                }
            }
        }

        return true;
    } //	beforeSave

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb =
                new StringBuilder("MBP_BankAccount[")
                        .append(getId())
                        .append(", Name=")
                        .append(getAccountName())
                        .append("]");
        return sb.toString();
    } //	toString
} //	MBPBankAccount
