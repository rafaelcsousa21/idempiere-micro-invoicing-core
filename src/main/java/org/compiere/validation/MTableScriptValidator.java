package org.compiere.validation;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.compiere.orm.Query;
import org.idempiere.common.util.CCache;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.getSQLValueEx;


/**
 * Table Validator Scripts
 *
 * @author Carlos Ruiz
 * @version $Id: MTableScriptValidator.java
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *     <li>BF [ 1885496 ] Performance NEEDS
 *     <li>BF [ 2819654 ] Table Script Validator SeqNo is not set
 *         https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2819654&group_id=176962
 */
public class MTableScriptValidator extends X_AD_Table_ScriptValidator {
  /** */
  private static final long serialVersionUID = 6272423660330749776L;

  /**
   * Get table script validator from cache
   *
   * @param ctx context
   * @param AD_Table_ScriptValidator_ID id
   * @return MTableScriptValidator
   */
  public static MTableScriptValidator get(Properties ctx, int AD_Table_ScriptValidator_ID) {
    final Integer key = AD_Table_ScriptValidator_ID;
    MTableScriptValidator retValue = (MTableScriptValidator) s_cache.get(key);
    if (retValue != null) return retValue;
    retValue = new MTableScriptValidator(ctx, AD_Table_ScriptValidator_ID, null);
    if (retValue.getId() != 0) s_cache.put(key, retValue);
    return retValue;
  } //	get

  /**
   * Get Model Validation Script Rules for a table/event
   *
   * @param ctx context
   * @param table_id AD_Table_ID
   * @param event Event
   * @return array of MTableScriptValidator or null if error or no validators found
   */
  public static List<MTableScriptValidator> getModelValidatorRules(
      Properties ctx, int ad_table_id, String event) {
    // Try cache
    final MultiKey key = new MultiKey(ad_table_id, event);
    List<MTableScriptValidator> mvrs = s_cacheTableEvent.get(key);
    if (mvrs != null) {
      if (mvrs.size() > 0) return mvrs;
      else return null;
    }
    //
    // Fetch now
    final String whereClause = "AD_Table_ID=? AND EventModelValidator=?";
    mvrs =
        new Query(ctx, Table_Name, whereClause, null)
            .setParameters(ad_table_id, event)
            .setOnlyActiveRecords(true)
            .setOrderBy(COLUMNNAME_SeqNo)
            .list();
    // Store to cache
    for (MTableScriptValidator rule : mvrs) {
      s_cache.put(rule.getId(), rule);
    }

    // Store to cache
    if (mvrs != null) s_cacheTableEvent.put(key, mvrs);
    //
    if (mvrs != null && mvrs.size() > 0) return mvrs;
    else return null;
  } //	getModelValidatorRules

  /** Cache */
  private static CCache<Integer, MTableScriptValidator> s_cache =
      new CCache<Integer, MTableScriptValidator>(Table_Name, 20);
  /** Cache / Table Event */
  private static CCache<MultiKey, List<MTableScriptValidator>> s_cacheTableEvent =
      new CCache<MultiKey, List<MTableScriptValidator>>(
          null, Table_Name + "_TableEvent", 20, false);

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param AD_Table_ScriptValidator_ID id
   * @param trxName transaction
   */
  public MTableScriptValidator(Properties ctx, int AD_Table_ScriptValidator_ID, String trxName) {
    super(ctx, AD_Table_ScriptValidator_ID, trxName);
  } //	MTableScriptValidator

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MTableScriptValidator(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MTableScriptValidator

  @Override
  protected boolean beforeSave(boolean newRecord) {
    if (getSeqNo() == 0) {
      final String sql =
          "SELECT COALESCE(MAX(SeqNo),0) + 10 FROM "
              + Table_Name
              + " WHERE AD_Table_ID=? AND EventModelValidator=?";
      int seqNo = getSQLValueEx(null, sql, getAD_Table_ID(), getEventModelValidator());
      setSeqNo(seqNo);
    }
    //
    return true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("MTableScriptValidator[");
    sb.append(getId())
        .append("-")
        .append(getAD_Table_ID())
        .append("-")
        .append(getEventModelValidator())
        .append("]");
    return sb.toString();
  } //	toString
} //	MTableScriptValidator
