package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_AcctSchema_GL;
import org.compiere.orm.Query;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.KeyNamePair;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Accounting Schema GL info
 *
 * @author Jorg Janke
 * @version $Id: MAcctSchemaGL.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 * @author victor.perez@e-evolution.com, www.e-evolution.com
 *     <li>RF [ 2214883 ] Remove SQL code and Replace for Query
 *         http://sourceforge.net/tracker/index.php?func=detail&aid=2214883&group_id=176962&atid=879335
 */
public class MAcctSchemaGL extends X_C_AcctSchema_GL {

  /** */
  private static final long serialVersionUID = 5303102649110271896L;

  /**
   * Get Accounting Schema GL Info
   *
   * @param ctx context
   * @param C_AcctSchema_ID id
   * @return defaults
   */
  public static MAcctSchemaGL get(Properties ctx, int C_AcctSchema_ID) {
    final String whereClause = "C_AcctSchema_ID=?";
    return new Query(ctx, I_C_AcctSchema_GL.Table_Name, whereClause, null)
        .setParameters(C_AcctSchema_ID)
        .firstOnly();
  } //	get

  /** Logger */
  protected static CLogger s_log = CLogger.getCLogger(MAcctSchemaGL.class);

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param C_AcctSchema_ID AcctSchema
   * @param trxName transaction
   */
  public MAcctSchemaGL(Properties ctx, int C_AcctSchema_ID, String trxName) {
    super(ctx, C_AcctSchema_ID, trxName);
    if (C_AcctSchema_ID == 0) {
      setUseCurrencyBalancing(false);
      setUseSuspenseBalancing(false);
      setUseSuspenseError(false);
    }
  } //	MAcctSchemaGL

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MAcctSchemaGL(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MAcctSchemaGL
  public MAcctSchemaGL(Properties ctx, Row row) {
    super(ctx, row);
  } //	MAcctSchemaGL

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
   * @return true if set
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
} //	MAcctSchemaGL
