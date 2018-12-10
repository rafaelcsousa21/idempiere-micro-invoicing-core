package org.compiere.accounting;

import org.compiere.model.I_M_CostElement;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_CostElement
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_CostElement extends BasePOName implements I_M_CostElement, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_CostElement(Properties ctx, int M_CostElement_ID, String trxName) {
    super(ctx, M_CostElement_ID, trxName);
  }

  /** Load Constructor */
  public X_M_CostElement(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 2 - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    return "X_M_CostElement[" + getId() + "]";
  }

  /** CostElementType AD_Reference_ID=338 */
  public static final int COSTELEMENTTYPE_AD_Reference_ID = 338;
  /** Material = M */
  public static final String COSTELEMENTTYPE_Material = "M";
  /** Overhead = O */
  public static final String COSTELEMENTTYPE_Overhead = "O";
  /** Burden (M.Overhead) = B */
  public static final String COSTELEMENTTYPE_BurdenMOverhead = "B";
  /** Outside Processing = X */
  public static final String COSTELEMENTTYPE_OutsideProcessing = "X";
  /** Resource = R */
  public static final String COSTELEMENTTYPE_Resource = "R";
  /**
   * Set Cost Element Type.
   *
   * @param CostElementType Type of Cost Element
   */
  public void setCostElementType(String CostElementType) {

    set_Value(COLUMNNAME_CostElementType, CostElementType);
  }

  /**
   * Get Cost Element Type.
   *
   * @return Type of Cost Element
   */
  public String getCostElementType() {
    return (String) get_Value(COLUMNNAME_CostElementType);
  }

  /** CostingMethod AD_Reference_ID=122 */
  public static final int COSTINGMETHOD_AD_Reference_ID = 122;
  /** Standard Costing = S */
  public static final String COSTINGMETHOD_StandardCosting = "S";
  /** Average PO = A */
  public static final String COSTINGMETHOD_AveragePO = "A";
  /** Lifo = L */
  public static final String COSTINGMETHOD_Lifo = "L";
  /** Fifo = F */
  public static final String COSTINGMETHOD_Fifo = "F";
  /** Last PO Price = p */
  public static final String COSTINGMETHOD_LastPOPrice = "p";
  /** Average Invoice = I */
  public static final String COSTINGMETHOD_AverageInvoice = "I";
  /** Last Invoice = i */
  public static final String COSTINGMETHOD_LastInvoice = "i";
  /** User Defined = U */
  public static final String COSTINGMETHOD_UserDefined = "U";
  /** _ = x */
  public static final String COSTINGMETHOD__ = "x";
  /**
   * Set Costing Method.
   *
   * @param CostingMethod Indicates how Costs will be calculated
   */
  public void setCostingMethod(String CostingMethod) {

    set_Value(COLUMNNAME_CostingMethod, CostingMethod);
  }

  /**
   * Get Costing Method.
   *
   * @return Indicates how Costs will be calculated
   */
  public String getCostingMethod() {
    return (String) get_Value(COLUMNNAME_CostingMethod);
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
   * Set Calculated.
   *
   * @param IsCalculated The value is calculated by the system
   */
  public void setIsCalculated(boolean IsCalculated) {
    set_Value(COLUMNNAME_IsCalculated, IsCalculated);
  }

  /**
   * Get Calculated.
   *
   * @return The value is calculated by the system
   */
  public boolean isCalculated() {
    Object oo = get_Value(COLUMNNAME_IsCalculated);
    if (oo != null) {
      if (oo instanceof Boolean) return (Boolean) oo;
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Cost Element.
   *
   * @param M_CostElement_ID Product Cost Element
   */
  public void setM_CostElement_ID(int M_CostElement_ID) {
    if (M_CostElement_ID < 1) set_ValueNoCheck(COLUMNNAME_M_CostElement_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_CostElement_ID, M_CostElement_ID);
  }

  /**
   * Get Cost Element.
   *
   * @return Product Cost Element
   */
  public int getM_CostElement_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_CostElement_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set M_CostElement_UU.
   *
   * @param M_CostElement_UU M_CostElement_UU
   */
  public void setM_CostElement_UU(String M_CostElement_UU) {
    set_Value(COLUMNNAME_M_CostElement_UU, M_CostElement_UU);
  }

  /**
   * Get M_CostElement_UU.
   *
   * @return M_CostElement_UU
   */
  public String getM_CostElement_UU() {
    return (String) get_Value(COLUMNNAME_M_CostElement_UU);
  }

  @Override
  public int getTableId() {
    return I_M_CostElement.Table_ID;
  }
}
