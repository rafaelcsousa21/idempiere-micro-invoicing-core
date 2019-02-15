package org.compiere.production;

import org.compiere.model.I_C_Task;
import org.compiere.orm.BasePOName;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_Task
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Task extends BasePOName implements I_C_Task, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_Task(Properties ctx, int C_Task_ID) {
    super(ctx, C_Task_ID);
  }

  /** Load Constructor */
  public X_C_Task(Properties ctx, ResultSet rs) {
    super(ctx, rs);
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
    StringBuffer sb = new StringBuffer("X_C_Task[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Standard Phase.
   *
   * @return Standard Phase of the Project Type
   */
  public int getC_Phase_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Phase_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Standard Task.
   *
   * @return Standard Project Type Task
   */
  public int getC_Task_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Task_ID);
    if (ii == null) return 0;
    return ii;
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
   * Get Comment/Help.
   *
   * @return Comment or Hint
   */
  public String getHelp() {
    return (String) get_Value(COLUMNNAME_Help);
  }

    /**
   * Get Product.
   *
   * @return Product, Service, Item
   */
  public int getM_Product_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Product_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Sequence.
   *
   * @param SeqNo Method of ordering records; lowest number comes first
   */
  public void setSeqNo(int SeqNo) {
    set_Value(COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
  }

  /**
   * Get Sequence.
   *
   * @return Method of ordering records; lowest number comes first
   */
  public int getSeqNo() {
    Integer ii = (Integer) get_Value(COLUMNNAME_SeqNo);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Standard Quantity.
   *
   * @param StandardQty Standard Quantity
   */
  public void setStandardQty(BigDecimal StandardQty) {
    set_Value(COLUMNNAME_StandardQty, StandardQty);
  }

  /**
   * Get Standard Quantity.
   *
   * @return Standard Quantity
   */
  public BigDecimal getStandardQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_StandardQty);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  @Override
  public int getTableId() {
    return I_C_Task.Table_ID;
  }
}
