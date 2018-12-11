package org.compiere.accounting;

import org.compiere.model.I_C_Campaign;
import org.compiere.orm.BasePONameValue;
import org.compiere.orm.MTable;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_Campaign
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Campaign extends BasePONameValue implements I_C_Campaign, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_Campaign(Properties ctx, int C_Campaign_ID, String trxName) {
    super(ctx, C_Campaign_ID, trxName);
  }

  /** Load Constructor */
  public X_C_Campaign(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_Campaign[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Campaign.
   *
   * @param C_Campaign_ID Marketing Campaign
   */
  public void setC_Campaign_ID(int C_Campaign_ID) {
    if (C_Campaign_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Campaign_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
  }

  /**
   * Get Campaign.
   *
   * @return Marketing Campaign
   */
  public int getC_Campaign_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Campaign_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_Campaign_UU.
   *
   * @param C_Campaign_UU C_Campaign_UU
   */
  public void setC_Campaign_UU(String C_Campaign_UU) {
    set_Value(COLUMNNAME_C_Campaign_UU, C_Campaign_UU);
  }

  /**
   * Get C_Campaign_UU.
   *
   * @return C_Campaign_UU
   */
  public String getC_Campaign_UU() {
    return (String) get_Value(COLUMNNAME_C_Campaign_UU);
  }

  public org.compiere.model.I_C_Channel getC_Channel() throws RuntimeException {
    return (org.compiere.model.I_C_Channel)
        MTable.get(getCtx(), org.compiere.model.I_C_Channel.Table_Name)
            .getPO(getC_Channel_ID(), get_TrxName());
  }

  /**
   * Set Channel.
   *
   * @param C_Channel_ID Sales Channel
   */
  public void setC_Channel_ID(int C_Channel_ID) {
    if (C_Channel_ID < 1) set_Value(COLUMNNAME_C_Channel_ID, null);
    else set_Value(COLUMNNAME_C_Channel_ID, Integer.valueOf(C_Channel_ID));
  }

  /**
   * Get Channel.
   *
   * @return Sales Channel
   */
  public int getC_Channel_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Channel_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Costs.
   *
   * @param Costs Costs in accounting currency
   */
  public void setCosts(BigDecimal Costs) {
    set_Value(COLUMNNAME_Costs, Costs);
  }

  /**
   * Get Costs.
   *
   * @return Costs in accounting currency
   */
  public BigDecimal getCosts() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Costs);
    if (bd == null) return Env.ZERO;
    return bd;
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
   * Set End Date.
   *
   * @param EndDate Last effective date (inclusive)
   */
  public void setEndDate(Timestamp EndDate) {
    set_Value(COLUMNNAME_EndDate, EndDate);
  }

  /**
   * Get End Date.
   *
   * @return Last effective date (inclusive)
   */
  public Timestamp getEndDate() {
    return (Timestamp) get_Value(COLUMNNAME_EndDate);
  }

  /**
   * Set Summary Level.
   *
   * @param IsSummary This is a summary entity
   */
  public void setIsSummary(boolean IsSummary) {
    set_Value(COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
  }

  /**
   * Get Summary Level.
   *
   * @return This is a summary entity
   */
  public boolean isSummary() {
    Object oo = get_Value(COLUMNNAME_IsSummary);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Start Date.
   *
   * @param StartDate First effective day (inclusive)
   */
  public void setStartDate(Timestamp StartDate) {
    set_Value(COLUMNNAME_StartDate, StartDate);
  }

  /**
   * Get Start Date.
   *
   * @return First effective day (inclusive)
   */
  public Timestamp getStartDate() {
    return (Timestamp) get_Value(COLUMNNAME_StartDate);
  }
}
