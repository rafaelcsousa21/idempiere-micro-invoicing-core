package org.compiere.accounting;

import org.compiere.model.I_M_CostType;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_CostType
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_CostType extends BasePOName implements I_M_CostType, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_CostType(Properties ctx, int M_CostType_ID) {
    super(ctx, M_CostType_ID);
  }

  /** Load Constructor */
  public X_M_CostType(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  }

  /**
   * AccessLevel
   *
   * @return 2 - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_CostType[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Cost Type.
   *
   * @return Type of Cost (e.g. Current, Plan, Future)
   */
  public int getM_CostType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_CostType_ID);
    if (ii == null) return 0;
    return ii;
  }

    @Override
  public int getTableId() {
    return I_M_CostType.Table_ID;
  }
}
