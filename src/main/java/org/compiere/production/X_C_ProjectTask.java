package org.compiere.production;

import org.compiere.model.I_C_ProjectTask;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_ProjectTask
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ProjectTask extends BasePOName implements I_C_ProjectTask, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_ProjectTask(Properties ctx, int C_ProjectTask_ID, String trxName) {
    super(ctx, C_ProjectTask_ID, trxName);
  }

  /** Load Constructor */
  public X_C_ProjectTask(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_ProjectTask[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Committed Amount.
   *
   * @param CommittedAmt The (legal) commitment amount
   */
  public void setCommittedAmt(BigDecimal CommittedAmt) {
    set_Value(COLUMNNAME_CommittedAmt, CommittedAmt);
  }

  /**
   * Get Committed Amount.
   *
   * @return The (legal) commitment amount
   */
  public BigDecimal getCommittedAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_CommittedAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  public org.compiere.model.I_C_ProjectPhase getC_ProjectPhase() throws RuntimeException {
    return (org.compiere.model.I_C_ProjectPhase)
        MTable.get(getCtx(), org.compiere.model.I_C_ProjectPhase.Table_Name)
            .getPO(getC_ProjectPhase_ID(), null);
  }

  /**
   * Set Project Phase.
   *
   * @param C_ProjectPhase_ID Phase of a Project
   */
  public void setC_ProjectPhase_ID(int C_ProjectPhase_ID) {
    if (C_ProjectPhase_ID < 1) set_ValueNoCheck(COLUMNNAME_C_ProjectPhase_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_ProjectPhase_ID, Integer.valueOf(C_ProjectPhase_ID));
  }

  /**
   * Get Project Phase.
   *
   * @return Phase of a Project
   */
  public int getC_ProjectPhase_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ProjectPhase_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Project Task.
   *
   * @param C_ProjectTask_ID Actual Project Task in a Phase
   */
  public void setC_ProjectTask_ID(int C_ProjectTask_ID) {
    if (C_ProjectTask_ID < 1) set_ValueNoCheck(COLUMNNAME_C_ProjectTask_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_ProjectTask_ID, Integer.valueOf(C_ProjectTask_ID));
  }

  /**
   * Get Project Task.
   *
   * @return Actual Project Task in a Phase
   */
  public int getC_ProjectTask_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ProjectTask_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_ProjectTask_UU.
   *
   * @param C_ProjectTask_UU C_ProjectTask_UU
   */
  public void setC_ProjectTask_UU(String C_ProjectTask_UU) {
    set_Value(COLUMNNAME_C_ProjectTask_UU, C_ProjectTask_UU);
  }

  /**
   * Get C_ProjectTask_UU.
   *
   * @return C_ProjectTask_UU
   */
  public String getC_ProjectTask_UU() {
    return (String) get_Value(COLUMNNAME_C_ProjectTask_UU);
  }

  public org.compiere.model.I_C_Task getC_Task() throws RuntimeException {
    return (org.compiere.model.I_C_Task)
        MTable.get(getCtx(), org.compiere.model.I_C_Task.Table_Name)
            .getPO(getC_Task_ID(), null);
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
   * Set Planned Amount.
   *
   * @param PlannedAmt Planned amount for this project
   */
  public void setPlannedAmt(BigDecimal PlannedAmt) {
    set_Value(COLUMNNAME_PlannedAmt, PlannedAmt);
  }

  /**
   * Get Planned Amount.
   *
   * @return Planned amount for this project
   */
  public BigDecimal getPlannedAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_PlannedAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /** ProjInvoiceRule AD_Reference_ID=383 */
  public static final int PROJINVOICERULE_AD_Reference_ID = 383;
  /** None = - */
  public static final String PROJINVOICERULE_None = "-";
  /** Committed Amount = C */
  public static final String PROJINVOICERULE_CommittedAmount = "C";
  /** Time&Material max Comitted = c */
  public static final String PROJINVOICERULE_TimeMaterialMaxComitted = "c";
  /** Time&Material = T */
  public static final String PROJINVOICERULE_TimeMaterial = "T";
  /** Product Quantity = P */
  public static final String PROJINVOICERULE_ProductQuantity = "P";
  /**
   * Set Invoice Rule.
   *
   * @param ProjInvoiceRule Invoice Rule for the project
   */
  public void setProjInvoiceRule(String ProjInvoiceRule) {

    set_Value(COLUMNNAME_ProjInvoiceRule, ProjInvoiceRule);
  }

  /**
   * Get Invoice Rule.
   *
   * @return Invoice Rule for the project
   */
  public String getProjInvoiceRule() {
    return (String) get_Value(COLUMNNAME_ProjInvoiceRule);
  }

  /**
   * Set Quantity.
   *
   * @param Qty Quantity
   */
  public void setQty(BigDecimal Qty) {
    set_Value(COLUMNNAME_Qty, Qty);
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

  @Override
  public int getTableId() {
    return I_C_ProjectTask.Table_ID;
  }
}
