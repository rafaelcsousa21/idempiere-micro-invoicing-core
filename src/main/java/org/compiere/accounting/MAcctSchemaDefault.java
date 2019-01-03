package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.orm.Query;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.KeyNamePair;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Default Accounts for MAcctSchema
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, www.e-evolution.com
 *     <li>RF [ 2214883 ] Remove SQL code and Replace for Query
 *         http://sourceforge.net/tracker/index.php?func=detail&aid=2214883&group_id=176962&atid=879335
 * @version $Id: MAcctSchemaDefault.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MAcctSchemaDefault extends X_C_AcctSchema_Default {

  /** */
  private static final long serialVersionUID = 199959007595802866L;

  /**
   * Get Accounting Schema Default Info
   *
   * @param ctx context
   * @param C_AcctSchema_ID id
   * @return defaults
   */
  public static MAcctSchemaDefault get(Properties ctx, int C_AcctSchema_ID) {
    final String whereClause = "C_AcctSchema_ID=?";
    return new Query(ctx, I_C_AcctSchema_Default.Table_Name, whereClause, null)
        .setParameters(C_AcctSchema_ID)
        .firstOnly();
  } //	get

  /** Logger */
  protected static CLogger s_log = CLogger.getCLogger(MAcctSchemaDefault.class);

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param C_AcctSchema_ID parent
   * @param trxName transaction
   */
  public MAcctSchemaDefault(Properties ctx, int C_AcctSchema_ID, String trxName) {
    super(ctx, C_AcctSchema_ID, trxName);
  } //	MAcctSchemaDefault

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MAcctSchemaDefault(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MAcctSchemaDefault
  public MAcctSchemaDefault(Properties ctx, Row row) {
    super(ctx, row);
  } //	MAcctSchemaDefault

  /**
   * Get Realized Gain Acct for currency
   *
   * @param C_Currency_ID currency
   * @return gain acct
   */
  //    IDEMPIERE-362 Hide things that don't work on iDempiere

  //	public int getRealizedGain_Acct (int C_Currency_ID)
  //	{
  //		MCurrencyAcct acct = MCurrencyAcct.get (this, C_Currency_ID);
  //		if (acct != null)
  //			return acct.getRealizedGain_Acct();
  //		return super.getRealizedGain_Acct();
  //	}	//	getRealizedGain_Acct

  /**
   * Get Realized Loss Acct for currency
   *
   * @param C_Currency_ID currency
   * @return loss acct
   */

  //  IDEMPIERE-362 Hide things that don't work on iDempiere

  //	public int getRealizedLoss_Acct (int C_Currency_ID)
  //	{
  //		MCurrencyAcct acct = MCurrencyAcct.get (this, C_Currency_ID);
  //		if (acct != null)
  //			return acct.getRealizedLoss_Acct();
  //		return super.getRealizedLoss_Acct();
  //	}	//	getRealizedLoss_Acct

  /**
   * Get Acct Info list
   *
   * @return list
   */
  public ArrayList<KeyNamePair> getAcctInfo() {
    ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();
    for (int i = 0; i < get_ColumnCount(); i++) {
      String columnName = get_ColumnName(i);
      if (columnName.endsWith("Acct")) {
        int id = ((Integer) get_Value(i));
        list.add(new KeyNamePair(id, columnName));
      }
    }
    return list;
  } //	getAcctInfo

  /**
   * Set Value (don't use)
   *
   * @param columnName column name
   * @param value value
   * @return true if value set
   */
  public boolean setValue(String columnName, Integer value) {
    return super.set_Value(columnName, value);
  } //	setValue

  /**
   * Before Save
   *
   * @param newRecord new
   * @return true
   */
  protected boolean beforeSave(boolean newRecord) {
    if ( getOrgId() != 0) setAD_Org_ID(0);
    return true;
  } //	beforeSave
} //	MAcctSchemaDefault
