package org.compiere.invoicing;

import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOutLineMA;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for M_InOutLineMA
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_InOutLineMA extends PO implements I_M_InOutLineMA, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_InOutLineMA(Properties ctx, int M_InOutLineMA_ID, String trxName) {
    super(ctx, M_InOutLineMA_ID, trxName);
    /**
     * if (M_InOutLineMA_ID == 0) { setM_AttributeSetInstance_ID (0); setM_InOutLine_ID (0);
     * setMovementQty (Env.ZERO); // 1 }
     */
  }

  /** Load Constructor */
  public X_M_InOutLineMA(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_M_InOutLineMA[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Date Material Policy.
   *
   * @param DateMaterialPolicy Time used for LIFO and FIFO Material Policy
   */
  public void setDateMaterialPolicy(Timestamp DateMaterialPolicy) {
    set_ValueNoCheck(COLUMNNAME_DateMaterialPolicy, DateMaterialPolicy);
  }

  /**
   * Get Date Material Policy.
   *
   * @return Time used for LIFO and FIFO Material Policy
   */
  public Timestamp getDateMaterialPolicy() {
    return (Timestamp) get_Value(COLUMNNAME_DateMaterialPolicy);
  }

  /**
   * Set Auto Generated.
   *
   * @param IsAutoGenerated Auto Generated
   */
  public void setIsAutoGenerated(boolean IsAutoGenerated) {
    set_ValueNoCheck(COLUMNNAME_IsAutoGenerated, Boolean.valueOf(IsAutoGenerated));
  }

  /**
   * Get Auto Generated.
   *
   * @return Auto Generated
   */
  public boolean isAutoGenerated() {
    Object oo = get_Value(COLUMNNAME_IsAutoGenerated);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
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
    if (M_AttributeSetInstance_ID < 0) set_ValueNoCheck(COLUMNNAME_M_AttributeSetInstance_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
  }

  /**
   * Get Attribute Set Instance.
   *
   * @return Product Attribute Set Instance
   */
  public int getMAttributeSetInstance_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException {
    return (org.compiere.model.I_M_InOutLine)
        MTable.get(getCtx(), org.compiere.model.I_M_InOutLine.Table_Name)
            .getPO(getM_InOutLine_ID(), get_TrxName());
  }

  /**
   * Set Shipment/Receipt Line.
   *
   * @param M_InOutLine_ID Line on Shipment or Receipt document
   */
  public void setM_InOutLine_ID(int M_InOutLine_ID) {
    if (M_InOutLine_ID < 1) set_ValueNoCheck(COLUMNNAME_M_InOutLine_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
  }

  /**
   * Get Shipment/Receipt Line.
   *
   * @return Line on Shipment or Receipt document
   */
  public int getM_InOutLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_InOutLine_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), String.valueOf(getM_InOutLine_ID()));
  }

  /**
   * Set M_InOutLineMA_UU.
   *
   * @param M_InOutLineMA_UU M_InOutLineMA_UU
   */
  public void setM_InOutLineMA_UU(String M_InOutLineMA_UU) {
    set_Value(COLUMNNAME_M_InOutLineMA_UU, M_InOutLineMA_UU);
  }

  /**
   * Get M_InOutLineMA_UU.
   *
   * @return M_InOutLineMA_UU
   */
  public String getM_InOutLineMA_UU() {
    return (String) get_Value(COLUMNNAME_M_InOutLineMA_UU);
  }

  /**
   * Set Movement Quantity.
   *
   * @param MovementQty Quantity of a product moved.
   */
  public void setMovementQty(BigDecimal MovementQty) {
    set_Value(COLUMNNAME_MovementQty, MovementQty);
  }

  /**
   * Get Movement Quantity.
   *
   * @return Quantity of a product moved.
   */
  public BigDecimal getMovementQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_MovementQty);
    if (bd == null) return Env.ZERO;
    return bd;
  }
}
