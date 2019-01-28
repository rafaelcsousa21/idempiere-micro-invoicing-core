package org.compiere.accounting;

import org.compiere.model.I_C_POSTenderType;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_POSTenderType
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_POSTenderType extends BasePONameValue implements I_C_POSTenderType, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_POSTenderType(Properties ctx, int C_POSTenderType_ID, String trxName) {
    super(ctx, C_POSTenderType_ID, trxName);
  }

  /** Load Constructor */
  public X_C_POSTenderType(Properties ctx, ResultSet rs, String trxName) {
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


  @Override
  public int getTableId() {
    return Table_ID;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_POSTenderType[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Guarantee.
   *
   * @return Guarantee for a Credit
   */
  public boolean isGuarantee() {
    Object oo = get_Value(COLUMNNAME_IsGuarantee);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

}
