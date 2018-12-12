package org.idempiere.process;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_C_BankStatementMatcher;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

public class X_C_BankStatementMatcher extends BasePOName
    implements I_C_BankStatementMatcher, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_BankStatementMatcher(Properties ctx, int C_BankStatementMatcher_ID, String trxName) {
    super(ctx, C_BankStatementMatcher_ID, trxName);
    /**
     * if (C_BankStatementMatcher_ID == 0) { setC_BankStatementMatcher_ID (0); setClassname (null);
     * setName (null); setSeqNo (0); }
     */
  }

  /** Load Constructor */
  public X_C_BankStatementMatcher(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 2 - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  /** Load Meta Data */
  protected POInfo initPO(Properties ctx) {
    POInfo poi = POInfo.getPOInfo(ctx, Table_ID, get_TrxName());
    return poi;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_BankStatementMatcher[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Bank Statement Matcher.
   *
   * @param C_BankStatementMatcher_ID Algorithm to match Bank Statement Info to Business Partners,
   *     Invoices and Payments
   */
  public void setC_BankStatementMatcher_ID(int C_BankStatementMatcher_ID) {
    if (C_BankStatementMatcher_ID < 1) set_ValueNoCheck(COLUMNNAME_C_BankStatementMatcher_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_C_BankStatementMatcher_ID, Integer.valueOf(C_BankStatementMatcher_ID));
  }

  /**
   * Get Bank Statement Matcher.
   *
   * @return Algorithm to match Bank Statement Info to Business Partners, Invoices and Payments
   */
  public int getC_BankStatementMatcher_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BankStatementMatcher_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_BankStatementMatcher_UU.
   *
   * @param C_BankStatementMatcher_UU C_BankStatementMatcher_UU
   */
  public void setC_BankStatementMatcher_UU(String C_BankStatementMatcher_UU) {
    set_Value(COLUMNNAME_C_BankStatementMatcher_UU, C_BankStatementMatcher_UU);
  }

  /**
   * Get C_BankStatementMatcher_UU.
   *
   * @return C_BankStatementMatcher_UU
   */
  public String getC_BankStatementMatcher_UU() {
    return (String) get_Value(COLUMNNAME_C_BankStatementMatcher_UU);
  }

  /**
   * Set Classname.
   *
   * @param Classname Java Classname
   */
  public void setClassname(String Classname) {
    set_Value(COLUMNNAME_Classname, Classname);
  }

  /**
   * Get Classname.
   *
   * @return Java Classname
   */
  public String getClassname() {
    return (String) get_Value(COLUMNNAME_Classname);
  }

  /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(COLUMNNAME_Description, Description);
  }

  /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
  }

  /**
   * Set Sequence.
   *
   * @param SeqNo Method of ordering records; lowest number comes first
   */
  public void setSeqNo(int SeqNo) {
    set_Value(COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
  }

  /**
   * Get Sequence.
   *
   * @return Method of ordering records; lowest number comes first
   */
  public int getSeqNo() {
    Integer ii = (Integer) get_Value(COLUMNNAME_SeqNo);
    if (ii == null) return 0;
    return ii;
  }
}
