package org.compiere.accounting;

import org.idempiere.common.exceptions.DBException;
import org.idempiere.common.util.CLogger;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdateEx;


/**
 * Accounting Fact Model
 *
 * @author Jorg Janke
 * @version $Id: MFactAcct.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * @author Teo Sarca, http://www.arhipac.ro
 *     <li>FR [ 2079083 ] Add MFactAcct.deleteEx method
 */
public class MFactAcct extends X_Fact_Acct {
  /** */
  private static final long serialVersionUID = 5251847162314796574L;

  /**
   * Delete Accounting
   *
   * @param AD_Table_ID table
   * @param Record_ID record
   * @param trxName transaction
   * @return number of rows or -1 for error
   * @deprecated Since ADempiere 3.5.2a; please use {@link #deleteEx(int, int, String)} instead.
   */
  public static int delete(int AD_Table_ID, int Record_ID, String trxName) {
    int no = -1;
    try {
      no = deleteEx(AD_Table_ID, Record_ID, trxName);
    } catch (DBException e) {
      s_log.log(Level.SEVERE, "failed: AD_Table_ID=" + AD_Table_ID + ", Record_ID" + Record_ID, e);
      no = -1;
    }
    return no;
  } //	delete

  /**
   * Delete Accounting
   *
   * @param AD_Table_ID table
   * @param Record_ID record
   * @param trxName transaction
   * @return number of rows deleted
   * @throws DBException on database exception
   */
  public static int deleteEx(int AD_Table_ID, int Record_ID, String trxName) throws DBException {
    final String sql = "DELETE Fact_Acct WHERE AD_Table_ID=? AND Record_ID=?";
    int no = executeUpdateEx(sql, new Object[] {AD_Table_ID, Record_ID});
    if (s_log.isLoggable(Level.FINE))
      s_log.fine("delete - AD_Table_ID=" + AD_Table_ID + ", Record_ID=" + Record_ID + " - #" + no);
    return no;
  }

  /** Static Logger */
  private static CLogger s_log = CLogger.getCLogger(MFactAcct.class);

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param Fact_Acct_ID id
   * @param trxName transaction
   */
  public MFactAcct(Properties ctx, int Fact_Acct_ID, String trxName) {
    super(ctx, Fact_Acct_ID, trxName);
  } //	MFactAcct

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MFactAcct(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MFactAcct

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("MFactAcct[");
    sb.append(getId())
        .append("-Acct=")
        .append(getAccount_ID())
        .append(",Dr=")
        .append(getAmtSourceDr())
        .append("|")
        .append(getAmtAcctDr())
        .append(",Cr=")
        .append(getAmtSourceCr())
        .append("|")
        .append(getAmtAcctCr())
        .append("]");
    return sb.toString();
  } //	toString

  /**
   * Derive MAccount from record
   *
   * @return Valid Account Combination
   */
  public MAccount getMAccount() {
    MAccount acct =
        MAccount.get(
            getCtx(),
             getClientId(),
             getOrgId(),
            getC_AcctSchema_ID(),
            getAccount_ID(),
            getC_SubAcct_ID(),
            getM_Product_ID(),
            getC_BPartner_ID(),
            getAD_OrgTrx_ID(),
            getC_LocFrom_ID(),
            getC_LocTo_ID(),
            getC_SalesRegion_ID(),
            getC_Project_ID(),
            getC_Campaign_ID(),
            getC_Activity_ID(),
            getUser1_ID(),
            getUser2_ID(),
            getUserElement1_ID(),
            getUserElement2_ID(),
            null);
    if (acct != null && acct.getId() == 0) acct.saveEx();
    return acct;
  } //	getMAccount
} //	MFactAcct
