package org.compiere.invoicing;

import org.compiere.model.I_C_LandedCost;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_LandedCost
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_LandedCost extends PO implements I_C_LandedCost, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_LandedCost(Properties ctx, int C_LandedCost_ID, String trxName) {
    super(ctx, C_LandedCost_ID, trxName);
    /**
     * if (C_LandedCost_ID == 0) { setC_InvoiceLine_ID (0); setC_LandedCost_ID (0);
     * setLandedCostDistribution (null); // Q setM_CostElement_ID (0); }
     */
  }

  /** Load Constructor */
  public X_C_LandedCost(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_LandedCost[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Set Invoice Line.
   *
   * @param C_InvoiceLine_ID Invoice Detail Line
   */
  public void setC_InvoiceLine_ID(int C_InvoiceLine_ID) {
    if (C_InvoiceLine_ID < 1) set_ValueNoCheck(COLUMNNAME_C_InvoiceLine_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
  }

  /**
   * Get Invoice Line.
   *
   * @return Invoice Detail Line
   */
  public int getC_InvoiceLine_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_InvoiceLine_ID);
    if (ii == null) return 0;
    return ii;
  }

    /** Quantity = Q */
  public static final String LANDEDCOSTDISTRIBUTION_Quantity = "Q";
  /** Line = L */
  public static final String LANDEDCOSTDISTRIBUTION_Line = "L";
  /** Volume = V */
  public static final String LANDEDCOSTDISTRIBUTION_Volume = "V";
  /** Weight = W */
  public static final String LANDEDCOSTDISTRIBUTION_Weight = "W";
  /** Costs = C */
  public static final String LANDEDCOSTDISTRIBUTION_Costs = "C";
  /**
   * Set Cost Distribution.
   *
   * @param LandedCostDistribution Landed Cost Distribution
   */
  public void setLandedCostDistribution(String LandedCostDistribution) {

    set_Value(COLUMNNAME_LandedCostDistribution, LandedCostDistribution);
  }

  /**
   * Get Cost Distribution.
   *
   * @return Landed Cost Distribution
   */
  public String getLandedCostDistribution() {
    return (String) get_Value(COLUMNNAME_LandedCostDistribution);
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
   * Get Shipment/Receipt.
   *
   * @return Material Shipment Document
   */
  public int getM_InOut_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_InOut_ID);
    if (ii == null) return 0;
    return ii;
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

    @Override
  public int getTableId() {
    return I_C_LandedCost.Table_ID;
  }
}
