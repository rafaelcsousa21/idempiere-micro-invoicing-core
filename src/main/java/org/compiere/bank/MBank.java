package org.compiere.bank;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_C_Bank;
import org.idempiere.common.util.CCache;

/**
 * Bank Model
 *
 * @author Jorg Janke
 * @version $Id: MBank.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MBank extends X_C_Bank {
  /** */
  private static final long serialVersionUID = 3459010882027283811L;

  /**
   * Get MBank from Cache
   *
   * @param ctx context
   * @param C_Bank_ID id
   * @return MBank
   */
  public static MBank get(Properties ctx, int C_Bank_ID) {
    Integer key = new Integer(C_Bank_ID);
    MBank retValue = (MBank) s_cache.get(key);
    if (retValue != null) return retValue;
    retValue = new MBank(ctx, C_Bank_ID, null);
    if (retValue.getId() != 0) s_cache.put(key, retValue);
    return retValue;
  } //	get

  /** Cache */
  private static CCache<Integer, MBank> s_cache =
      new CCache<Integer, MBank>(I_C_Bank.Table_Name, 3);

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param C_Bank_ID bank
   * @param trxName trx
   */
  public MBank(Properties ctx, int C_Bank_ID, String trxName) {
    super(ctx, C_Bank_ID, trxName);
  } //	MBank

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName trx
   */
  public MBank(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MBank

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("MBank[");
    sb.append(getId()).append("-").append(getName()).append("]");
    return sb.toString();
  } //	toString
} //	MBank
