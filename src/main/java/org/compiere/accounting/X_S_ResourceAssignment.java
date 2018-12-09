package org.compiere.accounting;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_S_ResourceAssignment;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

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
  public X_S_ResourceAssignment(Properties ctx, int S_ResourceAssignment_ID, String trxName) {
    super(ctx, S_ResourceAssignment_ID, trxName);
  }

  /** Load Constructor */
  public X_S_ResourceAssignment(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 1 - Org
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
   * Set Assign To.
   *
   * @param AssignDateTo Assign resource until
   */
  public void setAssignDateTo(Timestamp AssignDateTo) {
    set_ValueNoCheck(COLUMNNAME_AssignDateTo, AssignDateTo);
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
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
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
   * Set Resource Assignment.
   *
   * @param S_ResourceAssignment_ID Resource Assignment
   */
  public void setS_ResourceAssignment_ID(int S_ResourceAssignment_ID) {
    if (S_ResourceAssignment_ID < 1) set_ValueNoCheck(COLUMNNAME_S_ResourceAssignment_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_S_ResourceAssignment_ID, Integer.valueOf(S_ResourceAssignment_ID));
  }

  /**
   * Get Resource Assignment.
   *
   * @return Resource Assignment
   */
  public int getS_ResourceAssignment_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_S_ResourceAssignment_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set S_ResourceAssignment_UU.
   *
   * @param S_ResourceAssignment_UU S_ResourceAssignment_UU
   */
  public void setS_ResourceAssignment_UU(String S_ResourceAssignment_UU) {
    set_Value(COLUMNNAME_S_ResourceAssignment_UU, S_ResourceAssignment_UU);
  }

  /**
   * Get S_ResourceAssignment_UU.
   *
   * @return S_ResourceAssignment_UU
   */
  public String getS_ResourceAssignment_UU() {
    return (String) get_Value(COLUMNNAME_S_ResourceAssignment_UU);
  }

  public org.compiere.model.I_S_Resource getS_Resource() throws RuntimeException {
    return (org.compiere.model.I_S_Resource)
        MTable.get(getCtx(), org.compiere.model.I_S_Resource.Table_Name)
            .getPO(getS_Resource_ID(), get_TrxName());
  }

  /**
   * Set Resource.
   *
   * @param S_Resource_ID Resource
   */
  public void setS_Resource_ID(int S_Resource_ID) {
    if (S_Resource_ID < 1) set_ValueNoCheck(COLUMNNAME_S_Resource_ID, null);
    else set_ValueNoCheck(COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
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
}
