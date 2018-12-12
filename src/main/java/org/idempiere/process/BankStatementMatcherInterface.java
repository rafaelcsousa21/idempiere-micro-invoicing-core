package org.idempiere.process;

import org.compiere.accounting.MBankStatementLine;
import org.compiere.accounting.X_I_BankStatement;

public interface BankStatementMatcherInterface {
  /**
   * Match Bank Statement Line
   *
   * @param bsl bank statement line
   * @return found matches or null
   */
  public BankStatementMatchInfo findMatch(MBankStatementLine bsl);

  /**
   * Match Bank Statement Import Line
   *
   * @param ibs bank statement import line
   * @return found matches or null
   */
  public BankStatementMatchInfo findMatch(X_I_BankStatement ibs);
} //	BankStatementMatcherInterface
