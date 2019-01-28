package org.compiere.accounting;

import org.compiere.model.I_C_SalesRegion;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_SalesRegion
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_SalesRegion extends BasePONameValue implements I_C_SalesRegion, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_SalesRegion(Properties ctx, int C_SalesRegion_ID, String trxName) {
    super(ctx, C_SalesRegion_ID, trxName);
    /**
     * if (C_SalesRegion_ID == 0) { setC_SalesRegion_ID (0); setIsDefault (false); setIsSummary
     * (false); setName (null); setValue (null); }
     */
  }

  /** Load Constructor */
  public X_C_SalesRegion(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_SalesRegion[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Sales Region.
   *
   * @return Sales coverage region
   */
  public int getC_SalesRegion_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_SalesRegion_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Sales Representative.
   *
   * @return Sales Representative or Company Agent
   */
  public int getSalesRep_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_SalesRep_ID);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_C_SalesRegion.Table_ID;
  }
}
