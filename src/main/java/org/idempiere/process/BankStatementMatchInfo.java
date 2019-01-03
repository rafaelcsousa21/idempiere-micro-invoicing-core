package org.idempiere.process;

public class BankStatementMatchInfo {
  /** Standard Constructor */
  public BankStatementMatchInfo() {
    super();
  } //	BankStatementMatchInfo

  private int m_C_BPartner_ID = 0;
  private int m_C_Payment_ID = 0;
  private int m_C_Invoice_ID = 0;

  /**
   * Do we have a match?
   *
   * @return true if something could be matched
   */
  public boolean isMatched() {
    return m_C_BPartner_ID > 0 || m_C_Payment_ID > 0 || m_C_Invoice_ID > 0;
  } //	isValid

  /**
   * Get matched BPartner
   *
   * @return BPartner
   */
  public int getC_BPartner_ID() {
    return m_C_BPartner_ID;
  }
  /**
   * Set matched BPartner
   *
   * @param C_BPartner_ID BPartner
   */
  public void setC_BPartner_ID(int C_BPartner_ID) {
    m_C_BPartner_ID = C_BPartner_ID;
  }

  /**
   * Get matched Payment
   *
   * @return Payment
   */
  public int getC_Payment_ID() {
    return m_C_Payment_ID;
  }
  /**
   * Set matched Payment
   *
   * @param C_Payment_ID payment
   */
  public void setC_Payment_ID(int C_Payment_ID) {
    m_C_Payment_ID = C_Payment_ID;
  }

  /**
   * Get matched Invoice
   *
   * @return invoice
   */
  public int getC_Invoice_ID() {
    return m_C_Invoice_ID;
  }
  /**
   * Set matched Invoice
   *
   * @param C_Invoice_ID invoice
   */
  public void setC_Invoice_ID(int C_Invoice_ID) {
    m_C_Invoice_ID = C_Invoice_ID;
  }
} //	BankStatementMatchInfo
