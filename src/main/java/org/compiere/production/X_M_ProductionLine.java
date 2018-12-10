package org.compiere.production;

import org.compiere.model.IDocLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_ProductionLine;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_ProductionLine
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_ProductionLine extends PO implements I_M_ProductionLine, I_Persistent, IDocLine {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_ProductionLine(Properties ctx, int M_ProductionLine_ID, String trxName) {
    super(ctx, M_ProductionLine_ID, trxName);
    /**
     * if (M_ProductionLine_ID == 0) { setLine (0); // @SQL=SELECT NVL(MAX(Line),0)+10 AS
     * DefaultValue FROM M_ProductionLine WHERE M_Production_ID=@M_Production_ID@
     * setM_AttributeSetInstance_ID (0); setM_Locator_ID (0); // @M_Locator_ID@ setMovementQty
     * (Env.ZERO); setM_Product_ID (0); setM_ProductionLine_ID (0); setProcessed (false); }
     */
  }

  /** Load Constructor */
  public X_M_ProductionLine(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 1 - Org
   */
  protected int getAccessLevel() {
    return I_M_ProductionLine.accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_ProductionLine[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(I_M_ProductionLine.COLUMNNAME_Description, Description);
  }

  /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(I_M_ProductionLine.COLUMNNAME_Description);
  }

  /**
   * Set End Product.
   *
   * @param IsEndProduct End Product of production
   */
  public void setIsEndProduct(boolean IsEndProduct) {
    set_Value(I_M_ProductionLine.COLUMNNAME_IsEndProduct, Boolean.valueOf(IsEndProduct));
  }

  /**
   * Get End Product.
   *
   * @return End Product of production
   */
  public boolean isEndProduct() {
    Object oo = get_Value(I_M_ProductionLine.COLUMNNAME_IsEndProduct);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Line No.
   *
   * @param Line Unique line for this document
   */
  public void setLine(int Line) {
    set_Value(I_M_ProductionLine.COLUMNNAME_Line, Integer.valueOf(Line));
  }

  /**
   * Get Line No.
   *
   * @return Unique line for this document
   */
  public int getLine() {
    Integer ii = (Integer) get_Value(I_M_ProductionLine.COLUMNNAME_Line);
    if (ii == null) return 0;
    return ii;
  }

  public I_M_AttributeSetInstance getMAttributeSetInstance() throws RuntimeException {
    return (I_M_AttributeSetInstance)
        MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
            .getPO(getMAttributeSetInstance_ID(), get_TrxName());
  }

  /**
   * Set Attribute Set Instance.
   *
   * @param M_AttributeSetInstance_ID Product Attribute Set Instance
   */
  public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
    if (M_AttributeSetInstance_ID < 0)
      set_Value(I_M_ProductionLine.COLUMNNAME_M_AttributeSetInstance_ID, null);
    else
      set_Value(
          I_M_ProductionLine.COLUMNNAME_M_AttributeSetInstance_ID,
          Integer.valueOf(M_AttributeSetInstance_ID));
  }

  /**
   * Get Attribute Set Instance.
   *
   * @return Product Attribute Set Instance
   */
  public int getMAttributeSetInstance_ID() {
    Integer ii = (Integer) get_Value(I_M_ProductionLine.COLUMNNAME_M_AttributeSetInstance_ID);
    if (ii == null) return 0;
    return ii;
  }

  public I_M_Locator getM_Locator() throws RuntimeException {
    return (I_M_Locator)
        MTable.get(getCtx(), I_M_Locator.Table_Name).getPO(getM_Locator_ID(), get_TrxName());
  }

  /**
   * Set Locator.
   *
   * @param M_Locator_ID Warehouse Locator
   */
  public void setM_Locator_ID(int M_Locator_ID) {
    if (M_Locator_ID < 1) set_Value(I_M_ProductionLine.COLUMNNAME_M_Locator_ID, null);
    else set_Value(I_M_ProductionLine.COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
  }

  /**
   * Get Locator.
   *
   * @return Warehouse Locator
   */
  public int getM_Locator_ID() {
    Integer ii = (Integer) get_Value(I_M_ProductionLine.COLUMNNAME_M_Locator_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Movement Quantity.
   *
   * @param MovementQty Quantity of a product moved.
   */
  public void setMovementQty(BigDecimal MovementQty) {
    set_Value(I_M_ProductionLine.COLUMNNAME_MovementQty, MovementQty);
  }

  /**
   * Get Movement Quantity.
   *
   * @return Quantity of a product moved.
   */
  public BigDecimal getMovementQty() {
    BigDecimal bd = (BigDecimal) get_Value(I_M_ProductionLine.COLUMNNAME_MovementQty);
    if (bd == null) return Env.ZERO;
    return bd;
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
    if (M_Product_ID < 1) set_Value(I_M_ProductionLine.COLUMNNAME_M_Product_ID, null);
    else set_Value(I_M_ProductionLine.COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
  }

  /**
   * Get Product.
   *
   * @return Product, Service, Item
   */
  public int getM_Product_ID() {
    Integer ii = (Integer) get_Value(I_M_ProductionLine.COLUMNNAME_M_Product_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_M_Production getM_Production() throws RuntimeException {
    return (org.compiere.model.I_M_Production)
        MTable.get(getCtx(), org.compiere.model.I_M_Production.Table_Name)
            .getPO(getM_Production_ID(), get_TrxName());
  }

  /**
   * Set Production.
   *
   * @param M_Production_ID Plan for producing a product
   */
  public void setM_Production_ID(int M_Production_ID) {
    if (M_Production_ID < 1) set_ValueNoCheck(I_M_ProductionLine.COLUMNNAME_M_Production_ID, null);
    else
      set_ValueNoCheck(
          I_M_ProductionLine.COLUMNNAME_M_Production_ID, Integer.valueOf(M_Production_ID));
  }

  /**
   * Get Production.
   *
   * @return Plan for producing a product
   */
  public int getM_Production_ID() {
    Integer ii = (Integer) get_Value(I_M_ProductionLine.COLUMNNAME_M_Production_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), String.valueOf(getM_Production_ID()));
  }

  /**
   * Set Production Line.
   *
   * @param M_ProductionLine_ID Document Line representing a production
   */
  public void setM_ProductionLine_ID(int M_ProductionLine_ID) {
    if (M_ProductionLine_ID < 1)
      set_ValueNoCheck(I_M_ProductionLine.COLUMNNAME_M_ProductionLine_ID, null);
    else
      set_ValueNoCheck(
          I_M_ProductionLine.COLUMNNAME_M_ProductionLine_ID, Integer.valueOf(M_ProductionLine_ID));
  }

  /**
   * Get Production Line.
   *
   * @return Document Line representing a production
   */
  public int getM_ProductionLine_ID() {
    Integer ii = (Integer) get_Value(I_M_ProductionLine.COLUMNNAME_M_ProductionLine_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set M_ProductionLine_UU.
   *
   * @param M_ProductionLine_UU M_ProductionLine_UU
   */
  public void setM_ProductionLine_UU(String M_ProductionLine_UU) {
    set_Value(I_M_ProductionLine.COLUMNNAME_M_ProductionLine_UU, M_ProductionLine_UU);
  }

  /**
   * Get M_ProductionLine_UU.
   *
   * @return M_ProductionLine_UU
   */
  public String getM_ProductionLine_UU() {
    return (String) get_Value(I_M_ProductionLine.COLUMNNAME_M_ProductionLine_UU);
  }

  public org.compiere.model.I_M_ProductionPlan getM_ProductionPlan() throws RuntimeException {
    return (org.compiere.model.I_M_ProductionPlan)
        MTable.get(getCtx(), org.compiere.model.I_M_ProductionPlan.Table_Name)
            .getPO(getM_ProductionPlan_ID(), get_TrxName());
  }

  /**
   * Set Production Plan.
   *
   * @param M_ProductionPlan_ID Plan for how a product is produced
   */
  public void setM_ProductionPlan_ID(int M_ProductionPlan_ID) {
    if (M_ProductionPlan_ID < 1)
      set_ValueNoCheck(I_M_ProductionLine.COLUMNNAME_M_ProductionPlan_ID, null);
    else
      set_ValueNoCheck(
          I_M_ProductionLine.COLUMNNAME_M_ProductionPlan_ID, Integer.valueOf(M_ProductionPlan_ID));
  }

  /**
   * Get Production Plan.
   *
   * @return Plan for how a product is produced
   */
  public int getM_ProductionPlan_ID() {
    Integer ii = (Integer) get_Value(I_M_ProductionLine.COLUMNNAME_M_ProductionPlan_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Planned Quantity.
   *
   * @param PlannedQty Planned quantity for this project
   */
  public void setPlannedQty(BigDecimal PlannedQty) {
    set_Value(I_M_ProductionLine.COLUMNNAME_PlannedQty, PlannedQty);
  }

  /**
   * Get Planned Quantity.
   *
   * @return Planned quantity for this project
   */
  public BigDecimal getPlannedQty() {
    BigDecimal bd = (BigDecimal) get_Value(I_M_ProductionLine.COLUMNNAME_PlannedQty);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(I_M_ProductionLine.COLUMNNAME_Processed, Boolean.valueOf(Processed));
  }

  /**
   * Get Processed.
   *
   * @return The document has been processed
   */
  public boolean isProcessed() {
    Object oo = get_Value(I_M_ProductionLine.COLUMNNAME_Processed);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Product Type.
   *
   * @param ProductType Type of product
   */
  public void setProductType(String ProductType) {
    throw new IllegalArgumentException("ProductType is virtual column");
  }

  /**
   * Get Product Type.
   *
   * @return Type of product
   */
  public String getProductType() {
    return (String) get_Value(I_M_ProductionLine.COLUMNNAME_ProductType);
  }

  /**
   * Set Available Quantity.
   *
   * @param QtyAvailable Available Quantity (On Hand - Reserved)
   */
  public void setQtyAvailable(BigDecimal QtyAvailable) {
    throw new IllegalArgumentException("QtyAvailable is virtual column");
  }

  /**
   * Get Available Quantity.
   *
   * @return Available Quantity (On Hand - Reserved)
   */
  public BigDecimal getQtyAvailable() {
    BigDecimal bd = (BigDecimal) get_Value(I_M_ProductionLine.COLUMNNAME_QtyAvailable);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Quantity Used.
   *
   * @param QtyUsed Quantity Used
   */
  public void setQtyUsed(BigDecimal QtyUsed) {
    set_Value(I_M_ProductionLine.COLUMNNAME_QtyUsed, QtyUsed);
  }

  /**
   * Get Quantity Used.
   *
   * @return Quantity Used
   */
  public BigDecimal getQtyUsed() {
    BigDecimal bd = (BigDecimal) get_Value(I_M_ProductionLine.COLUMNNAME_QtyUsed);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  @Override
  public int getTableId() {
    return I_M_ProductionLine.Table_ID;
  }
}
