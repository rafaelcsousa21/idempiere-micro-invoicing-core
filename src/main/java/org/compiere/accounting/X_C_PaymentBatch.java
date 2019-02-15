package org.compiere.accounting;

import org.compiere.model.I_C_PaymentBatch;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_PaymentBatch
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_PaymentBatch extends BasePOName implements I_C_PaymentBatch, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_PaymentBatch(Properties ctx, int C_PaymentBatch_ID) {
    super(ctx, C_PaymentBatch_ID);
    /**
     * if (C_PaymentBatch_ID == 0) { setC_PaymentBatch_ID (0); setC_PaymentProcessor_ID (0); setName
     * (null); setProcessed (false); setProcessing (false); }
     */
  }

  /** Load Constructor */
  public X_C_PaymentBatch(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  }

  /**
   * AccessLevel
   *
   * @return 1 - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_PaymentBatch[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Payment Batch.
   *
   * @return Payment batch for EFT
   */
  public int getC_PaymentBatch_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_PaymentBatch_ID);
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
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Processed);
  }

    /**
   * Set Process Now.
   *
   * @param Processing Process Now
   */
  public void setProcessing(boolean Processing) {
    set_Value(COLUMNNAME_Processing, Processing);
  }

    @Override
  public int getTableId() {
    return I_C_PaymentBatch.Table_ID;
  }
}
