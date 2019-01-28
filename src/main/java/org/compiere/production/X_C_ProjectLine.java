package org.compiere.production;

import org.compiere.model.I_C_ProjectLine;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_ProjectLine
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ProjectLine extends PO implements I_C_ProjectLine, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_ProjectLine(Properties ctx, int C_ProjectLine_ID, String trxName) {
    super(ctx, C_ProjectLine_ID, trxName);
    /**
     * if (C_ProjectLine_ID == 0) { setC_Project_ID (0); setC_ProjectLine_ID (0); setInvoicedAmt
     * (Env.ZERO); setInvoicedQty (Env.ZERO); // 0 setIsPrinted (true); // Y setLine (0);
     * // @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM C_ProjectLine WHERE
     * C_Project_ID=@C_Project_ID@ setPlannedAmt (Env.ZERO); setPlannedMarginAmt (Env.ZERO);
     * setPlannedPrice (Env.ZERO); setPlannedQty (Env.ZERO); // 1 setProcessed (false); // N }
     */
  }

  /** Load Constructor */
  public X_C_ProjectLine(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_ProjectLine[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Set Committed Quantity.
   *
   * @param CommittedQty The (legal) commitment Quantity
   */
  public void setCommittedQty(BigDecimal CommittedQty) {
    set_Value(COLUMNNAME_CommittedQty, CommittedQty);
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

    /**
   * Set Purchase Order.
   *
   * @param C_OrderPO_ID Purchase Order
   */
  public void setC_OrderPO_ID(int C_OrderPO_ID) {
    if (C_OrderPO_ID < 1) set_ValueNoCheck(COLUMNNAME_C_OrderPO_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_OrderPO_ID, Integer.valueOf(C_OrderPO_ID));
  }

  /**
   * Get Purchase Order.
   *
   * @return Purchase Order
   */
  public int getC_OrderPO_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_OrderPO_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_C_Project getC_Project() throws RuntimeException {
    return (org.compiere.model.I_C_Project)
        MTable.get(getCtx(), org.compiere.model.I_C_Project.Table_Name)
            .getPO(getC_Project_ID(), null);
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
   * Set Project Issue.
   *
   * @param C_ProjectIssue_ID Project Issues (Material, Labor)
   */
  public void setC_ProjectIssue_ID(int C_ProjectIssue_ID) {
    if (C_ProjectIssue_ID < 1) set_ValueNoCheck(COLUMNNAME_C_ProjectIssue_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_ProjectIssue_ID, Integer.valueOf(C_ProjectIssue_ID));
  }

  /**
   * Get Project Issue.
   *
   * @return Project Issues (Material, Labor)
   */
  public int getC_ProjectIssue_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ProjectIssue_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Project Line.
   *
   * @return Task or step in a project
   */
  public int getC_ProjectLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ProjectLine_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Project Phase.
   *
   * @param C_ProjectPhase_ID Phase of a Project
   */
  public void setC_ProjectPhase_ID(int C_ProjectPhase_ID) {
    if (C_ProjectPhase_ID < 1) set_Value(COLUMNNAME_C_ProjectPhase_ID, null);
    else set_Value(COLUMNNAME_C_ProjectPhase_ID, Integer.valueOf(C_ProjectPhase_ID));
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
    if (C_ProjectTask_ID < 1) set_Value(COLUMNNAME_C_ProjectTask_ID, null);
    else set_Value(COLUMNNAME_C_ProjectTask_ID, Integer.valueOf(C_ProjectTask_ID));
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
   * Set Invoiced Amount.
   *
   * @param InvoicedAmt The amount invoiced
   */
  public void setInvoicedAmt(BigDecimal InvoicedAmt) {
    set_Value(COLUMNNAME_InvoicedAmt, InvoicedAmt);
  }

    /**
   * Set Quantity Invoiced .
   *
   * @param InvoicedQty The quantity invoiced
   */
  public void setInvoicedQty(BigDecimal InvoicedQty) {
    set_Value(COLUMNNAME_InvoicedQty, InvoicedQty);
  }

  /**
   * Get Quantity Invoiced .
   *
   * @return The quantity invoiced
   */
  public BigDecimal getInvoicedQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_InvoicedQty);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Printed.
   *
   * @param IsPrinted Indicates if this document / line is printed
   */
  public void setIsPrinted(boolean IsPrinted) {
    set_Value(COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
  }

    /**
   * Set Line No.
   *
   * @param Line Unique line for this document
   */
  public void setLine(int Line) {
    set_Value(COLUMNNAME_Line, Integer.valueOf(Line));
  }

  /**
   * Get Line No.
   *
   * @return Unique line for this document
   */
  public int getLine() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Line);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), String.valueOf(getLine()));
  }

    /**
   * Get Product Category.
   *
   * @return Category of a Product
   */
  public int getM_Product_Category_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Product_Category_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set Production.
   *
   * @param M_Production_ID Plan for producing a product
   */
  public void setM_Production_ID(int M_Production_ID) {
    if (M_Production_ID < 1) set_ValueNoCheck(COLUMNNAME_M_Production_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_Production_ID, Integer.valueOf(M_Production_ID));
  }

  /**
   * Get Production.
   *
   * @return Plan for producing a product
   */
  public int getM_Production_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Production_ID);
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
   * Set Planned Margin.
   *
   * @param PlannedMarginAmt Project's planned margin amount
   */
  public void setPlannedMarginAmt(BigDecimal PlannedMarginAmt) {
    set_Value(COLUMNNAME_PlannedMarginAmt, PlannedMarginAmt);
  }

    /**
   * Set Planned Price.
   *
   * @param PlannedPrice Planned price for this project line
   */
  public void setPlannedPrice(BigDecimal PlannedPrice) {
    set_Value(COLUMNNAME_PlannedPrice, PlannedPrice);
  }

  /**
   * Get Planned Price.
   *
   * @return Planned price for this project line
   */
  public BigDecimal getPlannedPrice() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_PlannedPrice);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Planned Quantity.
   *
   * @param PlannedQty Planned quantity for this project
   */
  public void setPlannedQty(BigDecimal PlannedQty) {
    set_Value(COLUMNNAME_PlannedQty, PlannedQty);
  }

  /**
   * Get Planned Quantity.
   *
   * @return Planned quantity for this project
   */
  public BigDecimal getPlannedQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_PlannedQty);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
  }

    @Override
  public int getTableId() {
    return I_C_ProjectLine.Table_ID;
  }
}
