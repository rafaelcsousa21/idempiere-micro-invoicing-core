package org.compiere.production;

import org.compiere.model.I_M_ProductionLineMA;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for M_ProductionLineMA
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_ProductionLineMA extends PO implements I_M_ProductionLineMA, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_ProductionLineMA(Properties ctx, int M_ProductionLineMA_ID, String trxName) {
    super(ctx, M_ProductionLineMA_ID, trxName);
    /**
     * if (M_ProductionLineMA_ID == 0) { setM_AttributeSetInstance_ID (0); setMovementQty
     * (Env.ZERO); setM_ProductionLine_ID (0); }
     */
  }

  /** Load Constructor */
  public X_M_ProductionLineMA(Properties ctx, ResultSet rs, String trxName) {
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

  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_ProductionLineMA[").append(getId()).append("]");
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

    /**
   * Set Production Line.
   *
   * @param M_ProductionLine_ID Document Line representing a production
   */
  public void setM_ProductionLine_ID(int M_ProductionLine_ID) {
    if (M_ProductionLine_ID < 1) set_ValueNoCheck(COLUMNNAME_M_ProductionLine_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_ProductionLine_ID, Integer.valueOf(M_ProductionLine_ID));
  }

  /**
   * Get Production Line.
   *
   * @return Document Line representing a production
   */
  public int getM_ProductionLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_ProductionLine_ID);
    if (ii == null) return 0;
    return ii;
  }

    @Override
  public int getTableId() {
    return I_M_ProductionLineMA.Table_ID;
  }
}
