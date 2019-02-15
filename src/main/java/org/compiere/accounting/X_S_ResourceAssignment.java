package org.compiere.accounting;

import org.compiere.model.I_S_ResourceAssignment;
import org.compiere.orm.BasePOName;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for S_ResourceAssignment
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_S_ResourceAssignment extends BasePOName
    implements I_S_ResourceAssignment, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_S_ResourceAssignment(Properties ctx, int S_ResourceAssignment_ID) {
    super(ctx, S_ResourceAssignment_ID);
  }

  /** Load Constructor */
  public X_S_ResourceAssignment(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  }

  /**
   * AccessLevel
   *
   * @return 1 - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_S_ResourceAssignment[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Assign From.
   *
   * @param AssignDateFrom Assign resource from
   */
  public void setAssignDateFrom(Timestamp AssignDateFrom) {
    set_ValueNoCheck(COLUMNNAME_AssignDateFrom, AssignDateFrom);
  }

  /**
   * Get Assign From.
   *
   * @return Assign resource from
   */
  public Timestamp getAssignDateFrom() {
    return (Timestamp) get_Value(COLUMNNAME_AssignDateFrom);
  }

    /**
   * Get Assign To.
   *
   * @return Assign resource until
   */
  public Timestamp getAssignDateTo() {
    return (Timestamp) get_Value(COLUMNNAME_AssignDateTo);
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
   * Set Confirmed.
   *
   * @param IsConfirmed Assignment is confirmed
   */
  public void setIsConfirmed(boolean IsConfirmed) {
    set_ValueNoCheck(COLUMNNAME_IsConfirmed, Boolean.valueOf(IsConfirmed));
  }

  /**
   * Get Confirmed.
   *
   * @return Assignment is confirmed
   */
  public boolean isConfirmed() {
    Object oo = get_Value(COLUMNNAME_IsConfirmed);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Quantity.
   *
   * @param Qty Quantity
   */
  public void setQty(BigDecimal Qty) {
    set_ValueNoCheck(COLUMNNAME_Qty, Qty);
  }

  /**
   * Get Quantity.
   *
   * @return Quantity
   */
  public BigDecimal getQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Qty);
    if (bd == null) return Env.ZERO;
    return bd;
  }

    /**
   * Get Resource.
   *
   * @return Resource
   */
  public int getS_Resource_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_S_Resource_ID);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_S_ResourceAssignment.Table_ID;
  }
}
