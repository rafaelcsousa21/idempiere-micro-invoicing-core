package org.compiere.accounting;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_M_CostType;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

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
  public X_M_CostType(Properties ctx, int M_CostType_ID, String trxName) {
    super(ctx, M_CostType_ID, trxName);
  }

  /** Load Constructor */
  public X_M_CostType(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 2 - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  /** Load Meta Data */
  protected POInfo initPO(Properties ctx) {
    POInfo poi = POInfo.getPOInfo(ctx, Table_ID, get_TrxName());
    return poi;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_CostType[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(COLUMNNAME_Description, Description);
  }

  /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
  }

  /**
   * Set Comment/Help.
   *
   * @param Help Comment or Hint
   */
  public void setHelp(String Help) {
    set_Value(COLUMNNAME_Help, Help);
  }

  /**
   * Get Comment/Help.
   *
   * @return Comment or Hint
   */
  public String getHelp() {
    return (String) get_Value(COLUMNNAME_Help);
  }

  /**
   * Set Cost Type.
   *
   * @param M_CostType_ID Type of Cost (e.g. Current, Plan, Future)
   */
  public void setM_CostType_ID(int M_CostType_ID) {
    if (M_CostType_ID < 1) set_ValueNoCheck(COLUMNNAME_M_CostType_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_CostType_ID, Integer.valueOf(M_CostType_ID));
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

  /**
   * Set M_CostType_UU.
   *
   * @param M_CostType_UU M_CostType_UU
   */
  public void setM_CostType_UU(String M_CostType_UU) {
    set_Value(COLUMNNAME_M_CostType_UU, M_CostType_UU);
  }

  /**
   * Get M_CostType_UU.
   *
   * @return M_CostType_UU
   */
  public String getM_CostType_UU() {
    return (String) get_Value(COLUMNNAME_M_CostType_UU);
  }
}
