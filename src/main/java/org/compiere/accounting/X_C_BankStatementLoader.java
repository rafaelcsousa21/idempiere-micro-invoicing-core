package org.compiere.accounting;

import org.compiere.model.I_C_BankStatementLoader;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

public class X_C_BankStatementLoader extends BasePOName
    implements I_C_BankStatementLoader, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_BankStatementLoader(Properties ctx, int C_BankStatementLoader_ID, String trxName) {
    super(ctx, C_BankStatementLoader_ID, trxName);
    /**
     * if (C_BankStatementLoader_ID == 0) { setC_BankAccount_ID (0); setC_BankStatementLoader_ID
     * (0); setName (null); }
     */
  }

  /** Load Constructor */
  public X_C_BankStatementLoader(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 3 - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  @Override
  public int getTableId() {
    return Table_ID;
  }


  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_BankStatementLoader[").append(getId()).append("]");
    return sb.toString();
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
   * Get Statement Loader Class.
   *
   * @return Class name of the bank statement loader
   */
  public String getStmtLoaderClass() {
    return (String) get_Value(COLUMNNAME_StmtLoaderClass);
  }

}
