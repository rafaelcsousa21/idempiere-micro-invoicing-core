package org.compiere.invoicing;

import org.compiere.model.HasName;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

public class X_C_InvoiceSchedule extends PO implements I_C_InvoiceSchedule, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_InvoiceSchedule(Properties ctx, int C_InvoiceSchedule_ID) {
    super(ctx, C_InvoiceSchedule_ID);
    /**
     * if (C_InvoiceSchedule_ID == 0) { setAmt (Env.ZERO); setC_InvoiceSchedule_ID (0);
     * setInvoiceDay (0); // 1 setInvoiceFrequency (null); setInvoiceWeekDay (null); setIsAmount
     * (false); setIsDefault (false); setName (null); }
     */
  }

  /** Load Constructor */
  public X_C_InvoiceSchedule(Properties ctx, ResultSet rs) {
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
    StringBuffer sb = new StringBuffer("X_C_InvoiceSchedule[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Amount.
   *
   * @return Amount
   */
  public BigDecimal getAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Amt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Get Invoice Day.
   *
   * @return Day of Invoice Generation
   */
  public int getInvoiceDay() {
    Integer ii = (Integer) get_Value(COLUMNNAME_InvoiceDay);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Invoice day cut-off.
   *
   * @return Last day for including shipments
   */
  public int getInvoiceDayCutoff() {
    Integer ii = (Integer) get_Value(COLUMNNAME_InvoiceDayCutoff);
    if (ii == null) return 0;
    return ii;
  }

    /** Daily = D */
  public static final String INVOICEFREQUENCY_Daily = "D";
  /** Weekly = W */
  public static final String INVOICEFREQUENCY_Weekly = "W";
  /** Monthly = M */
  public static final String INVOICEFREQUENCY_Monthly = "M";
  /** Twice Monthly = T */
  public static final String INVOICEFREQUENCY_TwiceMonthly = "T";

    /**
   * Get Invoice Frequency.
   *
   * @return How often invoices will be generated
   */
  public String getInvoiceFrequency() {
    return (String) get_Value(COLUMNNAME_InvoiceFrequency);
  }

    /** Sunday = 7 */
  public static final String INVOICEWEEKDAY_Sunday = "7";
  /** Monday = 1 */
  public static final String INVOICEWEEKDAY_Monday = "1";
  /** Tuesday = 2 */
  public static final String INVOICEWEEKDAY_Tuesday = "2";
  /** Wednesday = 3 */
  public static final String INVOICEWEEKDAY_Wednesday = "3";
    /** Friday = 5 */
  public static final String INVOICEWEEKDAY_Friday = "5";
  /** Saturday = 6 */
  public static final String INVOICEWEEKDAY_Saturday = "6";

    /**
   * Get Invoice Week Day.
   *
   * @return Day to generate invoices
   */
  public String getInvoiceWeekDay() {
    return (String) get_Value(COLUMNNAME_InvoiceWeekDay);
  }

    /**
   * Get Invoice weekday cutoff.
   *
   * @return Last day in the week for shipments to be included
   */
  public String getInvoiceWeekDayCutoff() {
    return (String) get_Value(COLUMNNAME_InvoiceWeekDayCutoff);
  }

    /**
   * Get Amount Limit.
   *
   * @return Send invoices only if the amount exceeds the limit
   */
  public boolean isAmount() {
    Object oo = get_Value(COLUMNNAME_IsAmount);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Name.
   *
   * @return Alphanumeric identifier of the entity
   */
  public String getName() {
    return (String) get_Value(HasName.Companion.getCOLUMNNAME_Name());
  }

    @Override
  public int getTableId() {
    return I_C_InvoiceSchedule.Table_ID;
  }
}
