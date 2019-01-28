package org.compiere.invoicing;

import org.compiere.model.I_A_Asset_Type;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for A_Asset_Type
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Asset_Type extends BasePONameValue implements I_A_Asset_Type, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_A_Asset_Type(Properties ctx, int A_Asset_Type_ID, String trxName) {
    super(ctx, A_Asset_Type_ID, trxName);
  }

  /** Load Constructor */
  public X_A_Asset_Type(Properties ctx, ResultSet rs, String trxName) {
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

  public String toString() {
    StringBuffer sb = new StringBuffer("X_A_Asset_Type[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Is Depreciable.
   *
   * @return This asset CAN be depreciated
   */
  public String getIsDepreciable() {
    return (String) get_Value(COLUMNNAME_IsDepreciable);
  }

    /**
   * Get In Possession.
   *
   * @return The asset is in the possession of the organization
   */
  public String getIsInPosession() {
    return (String) get_Value(COLUMNNAME_IsInPosession);
  }

    /**
   * Get Owned.
   *
   * @return The asset is owned by the organization
   */
  public String getIsOwned() {
    return (String) get_Value(COLUMNNAME_IsOwned);
  }

  @Override
  public int getTableId() {
    return I_A_Asset_Type.Table_ID;
  }
}
