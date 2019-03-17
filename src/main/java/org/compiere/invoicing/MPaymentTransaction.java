package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.accounting.MBankAccountProcessor;
import org.compiere.accounting.MPayment;
import org.compiere.accounting.MPaymentProcessor;
import org.compiere.bank.IBAN;
import org.compiere.model.IPaymentProcessor;
import org.compiere.model.IProcessInfo;
import org.compiere.model.I_C_PaymentTransaction;
import org.compiere.model.PaymentInterface;
import org.compiere.order.MOnlineTrxHistory;
import org.compiere.orm.MSysConfig;
import org.compiere.orm.PO;
import org.compiere.process.DocAction;
import org.compiere.process.IProcessUI;
import org.compiere.process.ProcessCall;
import org.compiere.util.Msg;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.orm.POKt.I_ZERO;
import static software.hsharp.core.orm.POKt.getAllIDs;

/**
 * @author Elaine
 */
public class MPaymentTransaction extends X_C_PaymentTransaction
        implements ProcessCall, PaymentInterface {

    /**
     *
     */
    private static final long serialVersionUID = 8722189788479132158L;
    /**
     * Temporary Bank Account Processors
     */
    private MBankAccountProcessor[] m_mBankAccountProcessors = null;
    /**
     * Temporary Bank Account Processor
     */
    private MBankAccountProcessor m_mBankAccountProcessor = null;
    /**
     * Error Message
     */
    private String m_errorMessage = null;
    @SuppressWarnings("unused")
    private IProcessUI m_processUI;

    public MPaymentTransaction(Properties ctx, int C_PaymentTransaction_ID) {
        super(ctx, C_PaymentTransaction_ID);

        //  New
        if (C_PaymentTransaction_ID == 0) {
            setTrxType(X_C_PaymentTransaction.TRXTYPE_Sales);
            //
            setAddressVerified(X_C_PaymentTransaction.R_AVSZIP_Unavailable);
            setVerifiedZip(X_C_PaymentTransaction.R_AVSZIP_Unavailable);
            //
            setIsReceipt(true);
            setIsApproved(false);
            setIsOnline(false);
            setIsSelfService(false);
            setIsDelayedCapture(false);
            setProcessed(false);
            //
            setPayAmt(Env.ZERO);
            setTaxAmt(Env.ZERO);
            //
            setDateTrx(new Timestamp(System.currentTimeMillis()));
            setTenderType(X_C_PaymentTransaction.TENDERTYPE_Check);
        }
    }

    public MPaymentTransaction(Properties ctx, Row row) {
        super(ctx, row);
    }

    public static IPaymentProcessor createPaymentProcessor(
            MBankAccountProcessor mbap, PaymentInterface mp) {
        if (s_log.isLoggable(Level.FINE)) s_log.fine("create for " + mbap);

        MPaymentProcessor mpp =
                new MPaymentProcessor(mbap.getCtx(), mbap.getPaymentProcessorId());
        String className = mpp.getPayProcessorClass();
        if (className == null || className.length() == 0) {
            s_log.log(Level.SEVERE, "No PaymentProcessor class name in " + mbap);
            return null;
        }
        //
        IPaymentProcessor myProcessor = null;

        if (myProcessor == null) {
            s_log.log(Level.SEVERE, "Not found in service/extension registry and classpath");
            return null;
        }

        //  Initialize
        myProcessor.initialize(mbap, mp);
        //
        return myProcessor;
    }

    public static MPaymentTransaction copyFrom(
            MPaymentTransaction from,
            Timestamp dateTrx,
            String trxType,
            String orig_TrxID,
            String trxName) {
        MPaymentTransaction to = new MPaymentTransaction(from.getCtx(), 0);
        PO.copyValues(from, to, from.getClientId(), from.getOrgId());
        to.setValueNoCheck(I_C_PaymentTransaction.COLUMNNAME_C_PaymentTransaction_ID, I_ZERO);
        //
        to.setAccountCity(from.getAccountCity());
        to.setAccountCountry(from.getAccountCountry());
        to.setAccountEMail(from.getAccountEMail());
        to.setPaymentIdentificationDriverLicence(from.getPaymentIdentificationDriverLicence());
        to.setSocialSecurityNoPaymentIdentification(from.getSocialSecurityNoPaymentIdentification());
        to.setAccountName(from.getAccountName());
        to.setAccountState(from.getAccountState());
        to.setAccountStreet(from.getAccountStreet());
        to.setAccountZip(from.getAccountZip());
        to.setAccountNo(from.getAccountNo());
        to.setIBAN(from.getIBAN());
        to.setOrgId(from.getOrgId());
        to.setBankAccountId(from.getBankAccountId());
        to.setBusinessPartnerBankAccountId(from.getBusinessPartnerBankAccountId());
        to.setBusinessPartnerId(from.getBusinessPartnerId());
        to.setConversionTypeId(from.getConversionTypeId());
        to.setCurrencyId(from.getCurrencyId());
        to.setInvoiceId(from.getInvoiceId());
        to.setOrderId(from.getOrderId());
        to.setPaymentProcessorId(from.getPaymentProcessorId());
        to.setPOSTenderTypeId(from.getPOSTenderTypeId());
        to.setCheckNo(from.getCheckNo());
        to.setCreditCardExpMM(from.getCreditCardExpMM());
        to.setCreditCardExpYY(from.getCreditCardExpYY());
        to.setCreditCardNumber(from.getCreditCardNumber());
        to.setCreditCardType(from.getCreditCardType());
        to.setCreditCardVV(from.getCreditCardVV());
        to.setCustomerAddressID(from.getCustomerAddressID());
        to.setCustomerPaymentProfileID(from.getCustomerPaymentProfileID());
        to.setCustomerProfileID(from.getCustomerProfileID());
        to.setDateTrx(dateTrx);
        to.setDescription(from.getDescription());
        to.setIsActive(from.isActive());
        to.setIsApproved(false);
        to.setIsDelayedCapture(false);
        to.setIsOnline(from.isOnline());
        to.setIsReceipt(from.isReceipt());
        to.setIsSelfService(from.isSelfService());
        to.setIsVoided(false);
        to.setMicr(from.getMicr());
        to.setOriginalTransactionId(orig_TrxID);
        to.setPayAmt(from.getPayAmt());
        to.setPONum(from.getPONum());
        to.setProcessed(false);
        to.setAuthorizationCode(null);
        to.setAddressVerified(null);
        to.setVerifiedZip(null);
        to.setCVV2Match(false);
        to.setResponseInfo(null);
        to.setPaymentReference(null);
        to.setResponseMessage(null);
        to.setTransmissionResult(null);
        to.setVoidMessage(null);
        to.setRoutingNo(from.getRoutingNo());
        to.setSwiftCode(from.getSwiftCode());
        to.setTaxAmt(from.getTaxAmt());
        to.setTenderType(from.getTenderType());
        to.setTrxType(trxType);
        to.setVoiceAuthCode(from.getVoiceAuthCode());
        //
        if (!to.save()) throw new IllegalStateException("Could not create Payment Transaction");

        return to;
    }

    public static int[] getAuthorizationPaymentTransactionIDs(
            int[] orderIDList, int C_Invoice_ID) {
        StringBuilder sb = new StringBuilder();
        if (orderIDList != null) {
            for (int orderID : orderIDList) sb.append(orderID).append(",");
        }

        String orderIDs = sb.toString();
        if (orderIDs.length() > 0) orderIDs = orderIDs.substring(0, orderIDs.length() - 1);

        StringBuilder whereClause = new StringBuilder();
        whereClause
                .append("TenderType='")
                .append(MPaymentTransaction.TENDERTYPE_CreditCard)
                .append("' ");
        whereClause
                .append("AND TrxType='")
                .append(MPaymentTransaction.TRXTYPE_Authorization)
                .append("' ");
        if (orderIDs.length() > 0 && C_Invoice_ID > 0)
            whereClause
                    .append(" AND (C_Order_ID IN (")
                    .append(orderIDs)
                    .append(") OR C_Invoice_ID=")
                    .append(C_Invoice_ID)
                    .append(")");
        else if (orderIDs.length() > 0)
            whereClause.append(" AND C_Order_ID IN ('").append(orderIDs).append(")");
        else if (C_Invoice_ID > 0) whereClause.append(" AND C_Invoice_ID=").append(C_Invoice_ID);
        whereClause.append(" AND IsApproved='Y' AND IsVoided='N' AND IsDelayedCapture='N' ");
        whereClause.append("ORDER BY DateTrx DESC");

        return getAllIDs(
                I_C_PaymentTransaction.Table_Name, whereClause.toString());
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {
        if (getCreditCardVV() != null) {
            String encrpytedCvv = PaymentUtil.encrpytCvv(getCreditCardVV());
            if (!encrpytedCvv.equals(getCreditCardVV())) setCreditCardVV(encrpytedCvv);
        }

        if (MSysConfig.getBooleanValue(
                MSysConfig.IBAN_VALIDATION, true, Env.getClientId(Env.getCtx()))) {
            if (!Util.isEmpty(getIBAN())) {
                setIBAN(IBAN.normalizeIBAN(getIBAN()));
                if (!IBAN.isValid(getIBAN())) {
                    log.saveError("Error", "IBAN is invalid");
                    return false;
                }
            }
        }

        return true;
    }

    public boolean setPaymentProcessor() {
        return setPaymentProcessor(getTenderType(), getCreditCardType(), getPaymentProcessorId());
    }

    public boolean setPaymentProcessor(String tender, String CCType, int C_PaymentProcessor_ID) {
        m_mBankAccountProcessor = null;
        //	Get Processor List
        if (m_mBankAccountProcessors == null || m_mBankAccountProcessors.length == 0)
            m_mBankAccountProcessors =
                    MBankAccountProcessor.find(
                            getCtx(),
                            tender,
                            CCType,
                            getClientId(),
                            getCurrencyId(),
                            getPayAmt()
                    );
        //	Relax Amount
        if (m_mBankAccountProcessors == null || m_mBankAccountProcessors.length == 0)
            m_mBankAccountProcessors =
                    MBankAccountProcessor.find(
                            getCtx(),
                            tender,
                            CCType,
                            getClientId(),
                            getCurrencyId(),
                            Env.ZERO
                    );
        if (m_mBankAccountProcessors == null || m_mBankAccountProcessors.length == 0) return false;

        //	Find the first right one
        for (int i = 0; i < m_mBankAccountProcessors.length; i++) {
            MBankAccountProcessor bankAccountProcessor = m_mBankAccountProcessors[i];
            if (bankAccountProcessor.accepts(tender, CCType)) {
                if (C_PaymentProcessor_ID == 0
                        || bankAccountProcessor.getPaymentProcessorId() == C_PaymentProcessor_ID) {
                    m_mBankAccountProcessor = m_mBankAccountProcessors[i];
                    break;
                }
            }
        }
        if (m_mBankAccountProcessor != null) {
            setBankAccountId(m_mBankAccountProcessor.getBankAccountId());
            setPaymentProcessorId(m_mBankAccountProcessor.getPaymentProcessorId());
        }
        //
        return m_mBankAccountProcessor != null;
    }

    /**
     * ************************************************************************ Process Payment
     *
     * @return true if approved
     */
    public boolean processOnline() {
        if (log.isLoggable(Level.INFO)) log.info("Amt=" + getPayAmt());
        //
        setIsOnline(true);
        setErrorMessage(null);
        //	prevent charging twice
        if (getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_Void)
                || getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_CreditPayment)) {
            if (isVoided()) {
                if (log.isLoggable(Level.INFO))
                    log.info("Already voided - " + getTransmissionResult() + " - " + getResponseMessage());
                setErrorMessage(Msg.getMsg(Env.getCtx(), "PaymentTransactionAlreadyVoided"));
                return true;
            }
        } else if (getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_DelayedCapture)) {
            if (isDelayedCapture()) {
                if (log.isLoggable(Level.INFO))
                    log.info("Already delayed capture - " + getTransmissionResult() + " - " + getResponseMessage());
                setErrorMessage(Msg.getMsg(Env.getCtx(), "PaymentTransactionAlreadyDelayedCapture"));
                return true;
            }
        } else {
            if (isApproved()) {
                if (log.isLoggable(Level.INFO))
                    log.info("Already processed - " + getTransmissionResult() + " - " + getResponseMessage());
                setErrorMessage(Msg.getMsg(Env.getCtx(), "PaymentTransactionAlreadyProcessed"));
                return true;
            }
        }

        if (m_mBankAccountProcessor == null) setPaymentProcessor();
        if (m_mBankAccountProcessor == null) {
            if (getPaymentProcessorId() > 0) {
                MPaymentProcessor pp =
                        new MPaymentProcessor(getCtx(), getPaymentProcessorId());
                log.log(Level.WARNING, "No Payment Processor Model " + pp.toString());
                setErrorMessage(Msg.getMsg(Env.getCtx(), "PaymentNoProcessorModel") + ": " + pp.toString());
            } else {
                log.log(Level.WARNING, "No Payment Processor Model");
                setErrorMessage(Msg.getMsg(Env.getCtx(), "PaymentNoProcessorModel"));
            }
            return false;
        }

        boolean approved = false;
        boolean processed = false;

        try {
            IPaymentProcessor pp = createPaymentProcessor(m_mBankAccountProcessor, this);
            if (pp == null) setErrorMessage(Msg.getMsg(Env.getCtx(), "PaymentNoProcessor"));
            else {
                // Validate before trying to process
                //				String msg = pp.validate();
                //				if (msg!=null && msg.trim().length()>0) {
                //					setErrorMessage(Msg.getMsg(getCtx(), msg));
                //				} else {
                // Process if validation succeeds
                approved = pp.processCC();
                setCreditCardNumber(PaymentUtil.encrpytCreditCard(getCreditCardNumber()));
                setCreditCardVV(PaymentUtil.encrpytCvv(getCreditCardVV()));
                setIsApproved(approved);

                if (getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_Void)
                        || getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_CreditPayment))
                    setIsVoided(approved);
                else if (getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_DelayedCapture))
                    setIsDelayedCapture(approved);

                if (approved) {
                    setErrorMessage(null);

                    if (!getTrxType().equals(MPaymentTransaction.TRXTYPE_Authorization)
                            && !getTrxType().equals(MPaymentTransaction.TRXTYPE_VoiceAuthorization)
                            && !getTrxType().equals(MPaymentTransaction.TRXTYPE_Void)) {
                        MPayment m_mPayment = createPayment(null);
                        m_mPayment.saveEx();
                        setPaymentId(m_mPayment.getPaymentId());
                        processed = m_mPayment.processIt(DocAction.Companion.getACTION_Complete());
                        if (!processed) setErrorMessage(Msg.getMsg(Env.getCtx(), "PaymentNotProcessed"));
                        else m_mPayment.saveEx();
                    } else processed = true;
                } else {
                    if (getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_Void)
                            || getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_CreditPayment))
                        setErrorMessage("From " + getCreditCardName() + ": " + getVoidMessage());
                    else setErrorMessage("From " + getCreditCardName() + ": " + getResponseMessage());
                }
                //				}
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "processOnline", e);
            setErrorMessage(Msg.getMsg(Env.getCtx(), "PaymentNotProcessed") + ": " + e.getMessage());
        }

        setIsApproved(approved);
        setProcessed(processed);

        if (getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_Void)
                || getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_CreditPayment)) setIsVoided(approved);
        else if (getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_DelayedCapture))
            setIsDelayedCapture(approved);

        MOnlineTrxHistory history = new MOnlineTrxHistory(getCtx(), 0);
        history.setRowTableId(MPaymentTransaction.Table_ID);
        history.setRecordId(getPaymentTransactionId());
        history.setIsError(!(approved && processed));
        history.setProcessed(approved && processed);

        StringBuilder msg = new StringBuilder();
        if (approved) {
            if (getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_Void)
                    || getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_CreditPayment))
                msg.append(getVoidMessage() + "\n");
            else {
                msg.append("Result: " + getTransmissionResult() + "\n");
                msg.append("Response Message: " + getResponseMessage() + "\n");
                msg.append("Reference: " + getPaymentReference() + "\n");
                msg.append("Authorization Code: " + getAuthorizationCode() + "\n");
            }
        } else msg.append("ERROR: " + getErrorMessage() + "\n");
        msg.append("Transaction Type: " + getTrxType());
        history.setTextMsg(msg.toString());

        history.saveEx();

        return approved && processed;
    }

    public boolean voidOnlineAuthorizationPaymentTransaction() {
        if (getTenderType().equals(X_C_PaymentTransaction.TENDERTYPE_CreditCard)
                && isOnline()
                && getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_Authorization)
                && !isVoided()
                && !isDelayedCapture()) {
            boolean ok = false;
            try {
                MPaymentTransaction m_mPaymentTransaction =
                        copyFrom(
                                this,
                                new Timestamp(System.currentTimeMillis()),
                                X_C_PaymentTransaction.TRXTYPE_Void,
                                getPaymentReference(),
                                null);
                m_mPaymentTransaction.setIsApproved(false);
                m_mPaymentTransaction.setIsVoided(false);
                m_mPaymentTransaction.setIsDelayedCapture(false);
                ok = m_mPaymentTransaction.processOnline();
                m_mPaymentTransaction.setReferencedPaymentTransactionId(getPaymentTransactionId());
                m_mPaymentTransaction.saveEx();

                if (ok) {
                    setIsVoided(true);
                    setVoidMessage(m_mPaymentTransaction.getVoidMessage());
                    setReferencedPaymentTransactionId(m_mPaymentTransaction.getPaymentTransactionId());
                } else setErrorMessage(m_mPaymentTransaction.getErrorMessage());
            } catch (Exception e) {
                log.log(Level.SEVERE, "voidOnlineAuthorizationPaymentTransaction", e);
                setErrorMessage(Msg.getMsg(Env.getCtx(), "PaymentNotProcessed") + ": " + e.getMessage());
            }

            return ok;
        }

        return true;
    }

    public boolean delayCaptureOnlineAuthorizationPaymentTransaction(int C_Invoice_ID) {
        if (getTenderType().equals(X_C_PaymentTransaction.TENDERTYPE_CreditCard)
                && isOnline()
                && getTrxType().equals(X_C_PaymentTransaction.TRXTYPE_Authorization)
                && !isVoided()
                && !isDelayedCapture()) {
            boolean ok = false;
            try {
                MPaymentTransaction m_mPaymentTransaction =
                        copyFrom(
                                this,
                                new Timestamp(System.currentTimeMillis()),
                                X_C_PaymentTransaction.TRXTYPE_DelayedCapture,
                                getPaymentReference(),
                                null);
                m_mPaymentTransaction.setIsApproved(false);
                m_mPaymentTransaction.setIsVoided(false);
                m_mPaymentTransaction.setIsDelayedCapture(false);

                if (C_Invoice_ID != 0) m_mPaymentTransaction.setInvoiceId(C_Invoice_ID);

                ok = m_mPaymentTransaction.processOnline();
                m_mPaymentTransaction.setReferencedPaymentTransactionId(getPaymentTransactionId());
                m_mPaymentTransaction.saveEx();

                if (ok) {
                    if (C_Invoice_ID != 0) setInvoiceId(C_Invoice_ID);
                    setIsDelayedCapture(true);
                    setReferencedPaymentTransactionId(m_mPaymentTransaction.getPaymentTransactionId());
                } else setErrorMessage(m_mPaymentTransaction.getErrorMessage());
            } catch (Exception e) {
                log.log(Level.SEVERE, "delayCaptureOnlineAuthorizationPaymentTransaction", e);
                setErrorMessage(Msg.getMsg(Env.getCtx(), "PaymentNotProcessed") + ": " + e.getMessage());
            }

            return ok;
        }

        return true;
    }

    public String getCreditCardName() {
        return getCreditCardName(getCreditCardType());
    }

    public String getCreditCardName(String CreditCardType) {
        if (CreditCardType == null) return "--";
        else if (X_C_PaymentTransaction.CREDITCARDTYPE_MasterCard.equals(CreditCardType))
            return "MasterCard";
        else if (X_C_PaymentTransaction.CREDITCARDTYPE_Visa.equals(CreditCardType)) return "Visa";
        else if (X_C_PaymentTransaction.CREDITCARDTYPE_Amex.equals(CreditCardType)) return "Amex";
        else if (X_C_PaymentTransaction.CREDITCARDTYPE_ATM.equals(CreditCardType)) return "ATM";
        else if (X_C_PaymentTransaction.CREDITCARDTYPE_Diners.equals(CreditCardType)) return "Diners";
        else if (X_C_PaymentTransaction.CREDITCARDTYPE_Discover.equals(CreditCardType))
            return "Discover";
        else if (X_C_PaymentTransaction.CREDITCARDTYPE_PurchaseCard.equals(CreditCardType))
            return "PurchaseCard";
        return "?" + CreditCardType + "?";
    }

    public String getErrorMessage() {
        return m_errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        m_errorMessage = errorMessage;
    }

    public MPayment createPayment(String trxName) {
        MPayment payment = new MPayment(getCtx(), 0);
        payment.setAccountCity(getAccountCity());
        payment.setAccountCountry(getAccountCountry());
        payment.setAccountEMail(getAccountEMail());
        payment.setPaymentIdentificationDriverLicence(getPaymentIdentificationDriverLicence());
        payment.setSocialSecurityNoPaymentIdentification(getSocialSecurityNoPaymentIdentification());
        payment.setAccountName(getAccountName());
        payment.setAccountState(getAccountState());
        payment.setAccountStreet(getAccountStreet());
        payment.setAccountZip(getAccountZip());
        payment.setAccountNo(getAccountNo());
        payment.setIBAN(getIBAN());
        payment.setOrgId(getOrgId());
        payment.setBankAccountId(getBankAccountId());
        payment.setBusinessPartnerBankAccountId(getBusinessPartnerBankAccountId());
        payment.setBusinessPartnerId(getBusinessPartnerId());
        payment.setConversionTypeId(getConversionTypeId());
        payment.setCurrencyId(getCurrencyId());
        payment.setInvoiceId(getInvoiceId());
        payment.setOrderId(getOrderId());
        payment.setPaymentProcessorId(getPaymentProcessorId());
        payment.setPOSTenderTypeId(getPOSTenderTypeId());
        payment.setCheckNo(getCheckNo());
        payment.setCreditCardExpMM(getCreditCardExpMM());
        payment.setCreditCardExpYY(getCreditCardExpYY());
        payment.setCreditCardNumber(getCreditCardNumber());
        payment.setCreditCardType(getCreditCardType());
        payment.setCreditCardVV(getCreditCardVV());
        payment.setCustomerAddressID(getCustomerAddressID());
        payment.setCustomerPaymentProfileID(getCustomerPaymentProfileID());
        payment.setCustomerProfileID(getCustomerProfileID());
        payment.setDateTrx(getDateTrx());
        payment.setDescription(getDescription());
        payment.setIsActive(isActive());
        payment.setIsApproved(isApproved());
        payment.setIsDelayedCapture(isDelayedCapture());
        payment.setIsOnline(isOnline());
        payment.setIsReceipt(isReceipt());
        payment.setIsSelfService(isSelfService());
        payment.setIsVoided(isVoided());
        payment.setMicr(getMicr());
        payment.setOriginalTransactionId(getOriginalTransactionId());
        payment.setPayAmt(getPayAmt());
        payment.setPONum(getPONum());
        payment.setProcessed(isProcessed());
        payment.setAuthorizationCode(getAuthorizationCode());
        payment.setAddressVerified(getAddressVerified());
        payment.setVerifiedZip(getVerifiedZip());
        payment.setCVV2Match(isCVV2Match());
        payment.setResponseInfo(getResponseInfo());
        payment.setPaymentReference(getPaymentReference());
        payment.setResponseMessage(getResponseMessage());
        payment.setTransmissionResult(getTransmissionResult());
        payment.setVoidMessage(getVoidMessage());
        payment.setRoutingNo(getRoutingNo());
        payment.setSwiftCode(getSwiftCode());
        payment.setTaxAmt(getTaxAmt());
        payment.setTenderType(getTenderType());
        payment.setTrxType(getTrxType());
        payment.setVoiceAuthCode(getVoiceAuthCode());

        payment.setDateAcct(payment.getDateTrx());

        return payment;
    }

    /**
     * Process Online Payment. implements ProcessCall after standard constructor Called when pressing
     * the Process_Online button in C_Payment
     *
     * @param ctx Context
     * @param pi  Process Info
     * @param trx transaction
     * @return true if the next process should be performed
     */
    @Override
    public boolean startProcess(Properties ctx, IProcessInfo pi) {
        if (log.isLoggable(Level.INFO)) log.info("startProcess - " + pi.getRecordId());
        boolean retValue = false;
        //
        if (pi.getRecordId() != getId()) {
            log.log(Level.SEVERE, "startProcess - Not same Payment - " + pi.getRecordId());
            return false;
        }
        //  Process it
        retValue = processOnline();
        saveEx();
        return retValue;
    }

    @Override
    public void setProcessUI(IProcessUI processUI) {
        m_processUI = processUI;
    }

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MPaymentTransaction[");
        sb.append(getId())
                .append("-")
                .append(",Receipt=")
                .append(isReceipt())
                .append(",PayAmt=")
                .append(getPayAmt());
        return sb.toString();
    } //	toString
}
