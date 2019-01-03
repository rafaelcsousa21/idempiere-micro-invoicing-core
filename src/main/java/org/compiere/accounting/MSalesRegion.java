package org.compiere.accounting;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_C_SalesRegion;
import org.compiere.orm.MTree_Base;
import org.idempiere.common.util.CCache;

/**
 * Sales Region Model
 *
 * @author Jorg Janke
 * @version $Id: MSalesRegion.java,v 1.3 2006/07/30 00:54:54 jjanke Exp $
 */
public class MSalesRegion extends X_C_SalesRegion {
  /** */
  private static final long serialVersionUID = -6166934441386906620L;

  /**
   * Get SalesRegion from Cache
   *
   * @param ctx context
   * @param C_SalesRegion_ID id
   * @return MSalesRegion
   */
  public static MSalesRegion get(Properties ctx, int C_SalesRegion_ID) {
    Integer key = new Integer(C_SalesRegion_ID);
    MSalesRegion retValue = (MSalesRegion) s_cache.get(key);
    if (retValue != null) return retValue;
    retValue = new MSalesRegion(ctx, C_SalesRegion_ID, null);
    if (retValue.getId() != 0) s_cache.put(key, retValue);
    return retValue;
  } //	get

  /** Cache */
  private static CCache<Integer, MSalesRegion> s_cache =
      new CCache<Integer, MSalesRegion>(I_C_SalesRegion.Table_Name, 10);

  /**
   * ************************************************************************ Default Constructor
   *
   * @param ctx context
   * @param C_SalesRegion_ID id
   * @param trxName transaction
   */
  public MSalesRegion(Properties ctx, int C_SalesRegion_ID, String trxName) {
    super(ctx, C_SalesRegion_ID, trxName);
  } //	MSalesRegion

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MSalesRegion(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MSalesRegion

  /**
   * After Save. Insert - create tree
   *
   * @param newRecord insert
   * @param success save success
   * @return success
   */
  protected boolean afterSave(boolean newRecord, boolean success) {
    if (!success) return success;
    if (newRecord) insert_Tree(MTree_Base.TREETYPE_SalesRegion);
    if (newRecord || is_ValueChanged(I_C_SalesRegion.COLUMNNAME_Value))
      update_Tree(MTree_Base.TREETYPE_SalesRegion);
    //	Value/Name change
    if (!newRecord && (is_ValueChanged("Value") || is_ValueChanged("Name")))
      MAccount.updateValueDescription(
          getCtx(), "C_SalesRegion_ID=" + getC_SalesRegion_ID(), null);

    return true;
  } //	afterSave

  /**
   * After Delete
   *
   * @param success
   * @return deleted
   */
  protected boolean afterDelete(boolean success) {
    if (success) delete_Tree(MTree_Base.TREETYPE_SalesRegion);
    return success;
  } //	afterDelete
} //	MSalesRegion
