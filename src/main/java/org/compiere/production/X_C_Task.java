package org.compiere.production;

import org.compiere.model.I_C_Task;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
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
  public X_C_Task(Properties ctx, int C_Task_ID, String trxName) {
    super(ctx, C_Task_ID, trxName);
  }

  /** Load Constructor */
  public X_C_Task(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_Task[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_C_Phase getC_Phase() throws RuntimeException {
    return (org.compiere.model.I_C_Phase)
        MTable.get(getCtx(), org.compiere.model.I_C_Phase.Table_Name)
            .getPO(getC_Phase_ID(), null);
  }

  /**
   * Set Standard Phase.
   *
   * @param C_Phase_ID Standard Phase of the Project Type
   */
  public void setC_Phase_ID(int C_Phase_ID) {
    if (C_Phase_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Phase_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Phase_ID, Integer.valueOf(C_Phase_ID));
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
   * Set Standard Task.
   *
   * @param C_Task_ID Standard Project Type Task
   */
  public void setC_Task_ID(int C_Task_ID) {
    if (C_Task_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Task_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Task_ID, Integer.valueOf(C_Task_ID));
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
   * Set C_Task_UU.
   *
   * @param C_Task_UU C_Task_UU
   */
  public void setC_Task_UU(String C_Task_UU) {
    set_Value(COLUMNNAME_C_Task_UU, C_Task_UU);
  }

  /**
   * Get C_Task_UU.
   *
   * @return C_Task_UU
   */
  public String getC_Task_UU() {
    return (String) get_Value(COLUMNNAME_C_Task_UU);
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

  public org.compiere.model.I_M_Product getM_Product() throws RuntimeException {
    return (org.compiere.model.I_M_Product)
        MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
            .getPO(getM_Product_ID(), null);
  }

  /**
   * Set Product.
   *
   * @param M_Product_ID Product, Service, Item
   */
  public void setM_Product_ID(int M_Product_ID) {
    if (M_Product_ID < 1) set_Value(COLUMNNAME_M_Product_ID, null);
    else set_Value(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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
