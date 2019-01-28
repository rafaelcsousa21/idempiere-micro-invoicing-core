package org.compiere.invoicing;

import org.compiere.model.I_A_Depreciation;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for A_Depreciation
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Depreciation extends BasePOName implements I_A_Depreciation, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_A_Depreciation(Properties ctx, int A_Depreciation_ID, String trxName) {
    super(ctx, A_Depreciation_ID, trxName);
  }

  /** Load Constructor */
  public X_A_Depreciation(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 7 - System - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_A_Depreciation[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get DepreciationType.
   *
   * @return DepreciationType
   */
  public String getDepreciationType() {
    return (String) get_Value(COLUMNNAME_DepreciationType);
  }

    @Override
  public int getTableId() {
    return I_A_Depreciation.Table_ID;
  }
}
