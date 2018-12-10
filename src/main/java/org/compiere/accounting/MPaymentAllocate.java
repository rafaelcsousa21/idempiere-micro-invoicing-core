package org.compiere.accounting;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.invoicing.MInvoice;
import org.compiere.model.I_C_PaymentAllocate;
import org.compiere.orm.MTable;
import org.compiere.orm.Query;
import org.compiere.util.Msg;
import org.idempiere.common.util.Env;

/**
 * Payment Allocate Model
 *
 * @author Jorg Janke
 * @version $Id: MPaymentAllocate.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MPaymentAllocate extends X_C_PaymentAllocate {
  /** */
  private static final long serialVersionUID = 2894385378672375131L;

  /**
   * Get active Payment Allocation of Payment
   *
   * @param parent payment
   * @return array of allocations
   */
  public static MPaymentAllocate[] get(MPayment parent) {
    final String whereClause = "C_Payment_ID=?";
    Query query =
        MTable.get(parent.getCtx(), I_C_PaymentAllocate.Table_ID)
            .createQuery(whereClause, parent.get_TrxName());
    query.setParameters(parent.getC_Payment_ID()).setOnlyActiveRecords(true);
    List<MPaymentAllocate> list = query.list();
    return list.toArray(new MPaymentAllocate[list.size()]);
  } //	get

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param C_PaymentAllocate_ID id
   * @param trxName trx
   */
  public MPaymentAllocate(Properties ctx, int C_PaymentAllocate_ID, String trxName) {
    super(ctx, C_PaymentAllocate_ID, trxName);
    if (C_PaymentAllocate_ID == 0) {
      //	setC_Payment_ID (0);	//	Parent
      //	setC_Invoice_ID (0);
      setAmount(Env.ZERO);
      setDiscountAmt(Env.ZERO);
      setOverUnderAmt(Env.ZERO);
      setWriteOffAmt(Env.ZERO);
      setInvoiceAmt(Env.ZERO);
    }
  } //	MPaymentAllocate

  /** The Invoice */
  private MInvoice m_invoice = null;

  /**
   * Load Cosntructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName trx
   */
  public MPaymentAllocate(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MPaymentAllocate

  /**
   * Set C_Invoice_ID
   *
   * @param C_Invoice_ID id
   */
  public void setC_Invoice_ID(int C_Invoice_ID) {
    super.setC_Invoice_ID(C_Invoice_ID);
    m_invoice = null;
  } //	setC_Invoice_ID

  /**
   * Get Invoice
   *
   * @return invoice
   */
  public MInvoice getInvoice() {
    if (m_invoice == null && getC_Invoice_ID() != 0)
      m_invoice = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
    return m_invoice;
  } //	getInvoice

  /**
   * Get BPartner of Invoice
   *
   * @return bp
   */
  public int getC_BPartner_ID() {
    if (m_invoice == null) getInvoice();
    if (m_invoice == null) return 0;
    return m_invoice.getC_BPartner_ID();
  } //	getC_BPartner_ID

  /**
   * Before Save
   *
   * @param newRecord new
   * @return true
   */
  protected boolean beforeSave(boolean newRecord) {
    MPayment payment = new MPayment(getCtx(), getC_Payment_ID(), get_TrxName());
    if ((newRecord || is_ValueChanged("C_Invoice_ID"))
        && (payment.getC_Charge_ID() != 0
            || payment.getC_Invoice_ID() != 0
            || payment.getC_Order_ID() != 0)) {
      log.saveError("PaymentIsAllocated", "");
      return false;
    }

    BigDecimal check =
        getAmount().add(getDiscountAmt()).add(getWriteOffAmt()).add(getOverUnderAmt());
    if (check.compareTo(getInvoiceAmt()) != 0) {
      log.saveError(
          "Error",
          Msg.parseTranslation(
              getCtx(), "@InvoiceAmt@(" + getInvoiceAmt() + ") <> @Totals@(" + check + ")"));
      return false;
    }

    //	Org
    if (newRecord || is_ValueChanged("C_Invoice_ID")) {
      getInvoice();
      if (m_invoice != null) setAD_Org_ID(m_invoice. getOrgId());
    }

    return true;
  } //	beforeSave
} //	MPaymentAllocate
