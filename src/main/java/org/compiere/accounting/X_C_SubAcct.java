package org.compiere.accounting;

import org.compiere.model.I_C_SubAcct;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_SubAcct
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_SubAcct extends BasePONameValue implements I_C_SubAcct, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_SubAcct(Properties ctx, int C_SubAcct_ID, String trxName) {
    super(ctx, C_SubAcct_ID, trxName);
    /**
     * if (C_SubAcct_ID == 0) { setC_ElementValue_ID (0); setC_SubAcct_ID (0); setName (null);
     * setValue (null); }
     */
  }

  /** Load Constructor */
  public X_C_SubAcct(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_SubAcct[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Account Element.
   *
   * @return Account Element
   */
  public int getC_ElementValue_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ElementValue_ID);
    if (ii == null) return 0;
    return ii;
  }

    @Override
  public int getTableId() {
    return I_C_SubAcct.Table_ID;
  }
}
