package org.compiere.production;

import org.compiere.crm.MUser;
import org.compiere.model.I_R_RequestUpdate;
import org.compiere.orm.MColumn;
import org.compiere.orm.MRefList;
import org.compiere.orm.MTable;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Request Update Model
 *
 * @author Jorg Janke
 * @version $Id: MRequestUpdate.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 * @author Teo Sarca
 *     <li>FR [ 2884541 ] MRequestUpdate should detect automatically the fields
 *         https://sourceforge.net/tracker/?func=detail&aid=2884541&group_id=176962&atid=879335
 */
public class MRequestUpdate extends X_R_RequestUpdate {
  /** */
  private static final long serialVersionUID = -8862921042436867124L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param R_RequestUpdate_ID id
   * @param trxName trx
   */
  public MRequestUpdate(Properties ctx, int R_RequestUpdate_ID, String trxName) {
    super(ctx, R_RequestUpdate_ID, trxName);
  } //	MRequestUpdate

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName trx
   */
  public MRequestUpdate(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MRequestUpdate

  /**
   * Parent Constructor
   *
   * @param parent request
   */
  public MRequestUpdate(MRequest parent) {
    super(parent.getCtx(), 0, null);
    setClientOrg(parent);
    setR_Request_ID(parent.getR_Request_ID());
    //
    for (final MColumn col : MTable.get(getCtx(), I_R_RequestUpdate.Table_ID).getColumns(false)) {
      if (col.isStandardColumn()
          || col.isKey()
          || col.isParent()
          || col.isUUIDColumn()
          || col.isVirtualColumn()) continue;
      final String columnName = col.getColumnName();
      final int i = parent.get_ColumnIndex(columnName);
      if (i >= 0) {
        set_ValueOfColumn(columnName, parent.get_Value(i));
      }
    }
  } //	MRequestUpdate

  /**
   * Do we have new info
   *
   * @return true if new info
   */
  public boolean isNewInfo() {
    return getResult() != null;
  } //	isNewInfo

  /**
   * Get Name of creator
   *
   * @return name
   */
  public String getCreatedByName() {
    MUser user = MUser.get(getCtx(), getCreatedBy());
    return user.getName();
  } //	getCreatedByName

  /**
   * Get Confidential Entry Text (for jsp)
   *
   * @return text
   */
  public String getConfidentialEntryText() {
    return MRefList.getListName(
        getCtx(),
        X_R_RequestUpdate.CONFIDENTIALTYPEENTRY_AD_Reference_ID,
        getConfidentialTypeEntry());
  } //	getConfidentialTextEntry

  /**
   * Before Save
   *
   * @param newRecord new
   * @return true
   */
  protected boolean beforeSave(boolean newRecord) {
    if (getConfidentialTypeEntry() == null)
      setConfidentialTypeEntry(X_R_RequestUpdate.CONFIDENTIALTYPEENTRY_PublicInformation);
    return true;
  } //	beforeSave
} //	MRequestUpdate
