package org.compiere.accounting;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Payment Batch Model
 *
 * @author Jorg Janke
 * @version $Id: MPaymentBatch.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MPaymentBatch extends X_C_PaymentBatch {
  /** */
  private static final long serialVersionUID = 779975501904633495L;

    /**
   * Standard Constructor
   *
   * @param ctx context
   * @param C_PaymentBatch_ID id
   * @param trxName transaction
   */
  public MPaymentBatch(Properties ctx, int C_PaymentBatch_ID) {
    super(ctx, C_PaymentBatch_ID);
    if (C_PaymentBatch_ID == 0) {
      //	setName (null);
      setProcessed(false);
      setProcessing(false);
    }
  } //	MPaymentBatch

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MPaymentBatch(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MPaymentBatch

  /**
   * New Constructor
   *
   * @param ctx context
   * @param Name name
   * @param trxName trx
   */
  public MPaymentBatch(Properties ctx, String Name) {
    this(ctx, 0);
    setName(Name);
  } //	MPaymentBatch

  /**
   * Parent Constructor
   *
   * @param ps Pay Selection
   */
  public MPaymentBatch(MPaySelection ps) {
    this(ps.getCtx(), 0);
    setClientOrg(ps);
    setName(ps.getName());
  } //	MPaymentBatch
} //	MPaymentBatch
