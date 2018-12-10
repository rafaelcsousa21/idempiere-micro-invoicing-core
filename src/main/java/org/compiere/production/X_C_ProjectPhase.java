package org.compiere.production;

import org.compiere.model.I_C_ProjectPhase;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_ProjectPhase
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ProjectPhase extends BasePOName implements I_C_ProjectPhase, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_ProjectPhase(Properties ctx, int C_ProjectPhase_ID, String trxName) {
    super(ctx, C_ProjectPhase_ID, trxName);
    /**
     * if (C_ProjectPhase_ID == 0) { setCommittedAmt (Env.ZERO); setC_Project_ID (0);
     * setC_ProjectPhase_ID (0); setIsCommitCeiling (false); setIsComplete (false); setName (null);
     * setPlannedAmt (Env.ZERO); setProjInvoiceRule (null); // @ProjInvoiceRule@ setSeqNo (0);
     * // @SQL=SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM C_ProjectPhase WHERE
     * C_Project_ID=@C_Project_ID@ }
     */
  }

  /** Load Constructor */
  public X_C_ProjectPhase(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_ProjectPhase[").append(getId()).append("]");
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

  public org.compiere.model.I_C_Order getC_Order() throws RuntimeException {
    return (org.compiere.model.I_C_Order)
        MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
            .getPO(getC_Order_ID(), get_TrxName());
  }

  /**
   * Set Order.
   *
   * @param C_Order_ID Order
   */
  public void setC_Order_ID(int C_Order_ID) {
    if (C_Order_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Order_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
  }

  /**
   * Get Order.
   *
   * @return Order
   */
  public int getC_Order_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Order_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_C_Phase getC_Phase() throws RuntimeException {
    return (org.compiere.model.I_C_Phase)
        MTable.get(getCtx(), org.compiere.model.I_C_Phase.Table_Name)
            .getPO(getC_Phase_ID(), get_TrxName());
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

  public org.compiere.model.I_C_Project getC_Project() throws RuntimeException {
    return (org.compiere.model.I_C_Project)
        MTable.get(getCtx(), org.compiere.model.I_C_Project.Table_Name)
            .getPO(getC_Project_ID(), get_TrxName());
  }

  /**
   * Set Project.
   *
   * @param C_Project_ID Financial Project
   */
  public void setC_Project_ID(int C_Project_ID) {
    if (C_Project_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Project_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
  }

  /**
   * Get Project.
   *
   * @return Financial Project
   */
  public int getC_Project_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Project_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set C_ProjectPhase_UU.
   *
   * @param C_ProjectPhase_UU C_ProjectPhase_UU
   */
  public void setC_ProjectPhase_UU(String C_ProjectPhase_UU) {
    set_Value(COLUMNNAME_C_ProjectPhase_UU, C_ProjectPhase_UU);
  }

  /**
   * Get C_ProjectPhase_UU.
   *
   * @return C_ProjectPhase_UU
   */
  public String getC_ProjectPhase_UU() {
    return (String) get_Value(COLUMNNAME_C_ProjectPhase_UU);
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
   * Set Generate Order.
   *
   * @param GenerateOrder Generate Order
   */
  public void setGenerateOrder(String GenerateOrder) {
    set_Value(COLUMNNAME_GenerateOrder, GenerateOrder);
  }

  /**
   * Get Generate Order.
   *
   * @return Generate Order
   */
  public String getGenerateOrder() {
    return (String) get_Value(COLUMNNAME_GenerateOrder);
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
   * Set Commitment is Ceiling.
   *
   * @param IsCommitCeiling The commitment amount/quantity is the chargeable ceiling
   */
  public void setIsCommitCeiling(boolean IsCommitCeiling) {
    set_Value(COLUMNNAME_IsCommitCeiling, Boolean.valueOf(IsCommitCeiling));
  }

  /**
   * Get Commitment is Ceiling.
   *
   * @return The commitment amount/quantity is the chargeable ceiling
   */
  public boolean isCommitCeiling() {
    Object oo = get_Value(COLUMNNAME_IsCommitCeiling);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Complete.
   *
   * @param IsComplete It is complete
   */
  public void setIsComplete(boolean IsComplete) {
    set_Value(COLUMNNAME_IsComplete, Boolean.valueOf(IsComplete));
  }

  /**
   * Get Complete.
   *
   * @return It is complete
   */
  public boolean isComplete() {
    Object oo = get_Value(COLUMNNAME_IsComplete);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  public org.compiere.model.I_M_Product getM_Product() throws RuntimeException {
    return (org.compiere.model.I_M_Product)
        MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
            .getPO(getM_Product_ID(), get_TrxName());
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

  /**
   * Set Unit Price.
   *
   * @param PriceActual Actual Price
   */
  public void setPriceActual(BigDecimal PriceActual) {
    set_Value(COLUMNNAME_PriceActual, PriceActual);
  }

  /**
   * Get Unit Price.
   *
   * @return Actual Price
   */
  public BigDecimal getPriceActual() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_PriceActual);
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

  @Override
  public int getTableId() {
    return I_C_ProjectPhase.Table_ID;
  }
}
