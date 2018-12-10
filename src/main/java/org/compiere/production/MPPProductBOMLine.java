package org.compiere.production;

import org.compiere.orm.Query;
import org.compiere.product.MProduct;
import org.compiere.product.MUOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

/**
 * PP Product BOM Line Model. <code>
 * 			MPPProductBOMLine l = new MPPProductBOMLine(bom);
 * 			l.setM_Product_ID(wbl.getM_Product_ID());
 * 			l.setQty(wbl.getQuantity());
 * 			l.saveEx();
 * </code>
 *
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
public class MPPProductBOMLine extends X_PP_Product_BOMLine {

  /** */
  private static final long serialVersionUID = -5792418944606756221L;

  MPPProductBOM m_bom = null;

  /**
   * Get all the Product BOM line for a Component
   *
   * @param product Product
   * @return list of MPPProductBOMLine
   */
  public static List<MPPProductBOMLine> getByProduct(MProduct product) {
    final String whereClause = I_PP_Product_BOMLine.COLUMNNAME_M_Product_ID + "=?";
    return new Query(
            product.getCtx(), I_PP_Product_BOMLine.Table_Name, whereClause, product.get_TrxName())
        .setParameters(product.getM_Product_ID())
        .list();
  }

  /**
   * Default Constructor
   *
   * @param ctx context
   * @param PP_Product_BOMLine BOM line to load
   * @param Transaction Line
   */
  public MPPProductBOMLine(Properties ctx, int PP_Product_BOMLine, String trxName) {
    super(ctx, PP_Product_BOMLine, trxName);
  } //	MPPProductBOMLine

  /**
   * Parent Constructor.
   *
   * @param bom parent BOM
   */
  public MPPProductBOMLine(MPPProductBOM bom) {
    super(bom.getCtx(), 0, bom.get_TableName());
    if (bom.getId() <= 0) throw new IllegalArgumentException("Header not saved");
    setPP_Product_BOM_ID(bom.getPP_Product_BOM_ID()); // 	parent
  }

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set record
   */
  public MPPProductBOMLine(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	 MPPProductBOMLine

  /**
   * Calculate Low Level of a Product
   *
   * @param ID Product
   * @return int low level
   */
  public int getLowLevel() {
    return new ProductLowLevelCalculator(getCtx(), get_TrxName()).getLowLevel(getM_Product_ID());
  }

  /**
   * get Parent BOM
   *
   * @return
   */
  public MPPProductBOM getParent() {
    if (m_bom == null) {
      m_bom = new MPPProductBOM(getCtx(), this.getPP_Product_BOM_ID(), get_TrxName());
    }
    return m_bom;
  }

  public MProduct getProduct() {
    return MProduct.get(getCtx(), getM_Product_ID());
  }

  /**
   * Calculate Low Level of a Product
   *
   * @param ID Product
   * @return int low level
   */
  public static int getLowLevel(Properties ctx, int M_Product_ID, String trxName) {
    return new ProductLowLevelCalculator(ctx, trxName).getLowLevel(M_Product_ID);
  }

  @Override
  protected boolean beforeSave(boolean newRecord) {
    //
    // For Co/By Products, Qty should be always negative:
    if (isCoProduct() && getQty(false).signum() >= 0) {
      throw new AdempiereException("@Qty@ > 0");
    }
    //
    // Update Line#
    if (getLine() <= 0) {
      final String sql =
          "SELECT COALESCE(MAX("
              + I_PP_Product_BOMLine.COLUMNNAME_Line
              + "),0) + 10 FROM "
              + I_PP_Product_BOMLine.Table_Name
              + " WHERE "
              + I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID
              + "=?";
      int line = getSQLValueEx(get_TrxName(), sql, getPP_Product_BOM_ID());
      setLine(line);
    }

    return true;
  }

  @Override
  protected boolean afterSave(boolean newRecord, boolean success) {
    if (!success) return false;

    int lowlevel = getLowLevel();
    MProduct product = new MProduct(getCtx(), getM_Product_ID(), get_TrxName());
    product.setLowLevel(lowlevel); // update lowlevel
    product.saveEx();

    return true;
  }

  public boolean isValidFromTo(Timestamp date) {
    Timestamp validFrom = getValidFrom();
    Timestamp validTo = getValidTo();

    if (validFrom != null && date.before(validFrom)) return false;
    if (validTo != null && date.after(validTo)) return false;
    return true;
  }

  public boolean isByProduct() {
    String componentType = getComponentType();
    return X_PP_Product_BOMLine.COMPONENTTYPE_By_Product.equals(componentType);
  }

  public boolean isCoProduct() {
    String componentType = getComponentType();
    return X_PP_Product_BOMLine.COMPONENTTYPE_Co_Product.equals(componentType);
  }

  /**
   * Return absolute (unified) quantity value. If IsQtyPercentage then QtyBatch / 100 will be
   * returned. Else QtyBOM will be returned.
   *
   * @param includeScrapQty if true, scrap qty will be used for calculating qty
   * @return qty
   */
  public BigDecimal getQty(boolean includeScrapQty) {
    int precision = getPrecision();
    BigDecimal qty;
    if (isQtyPercentage()) {
      precision += 2;
      qty = getQtyBatch().divide(Env.ONEHUNDRED, precision, RoundingMode.HALF_UP);
    } else {
      qty = getQtyBOM();
    }
    //
    if (includeScrapQty) {
      BigDecimal scrapDec = getScrap().divide(Env.ONEHUNDRED, 12, BigDecimal.ROUND_UP);
      qty = qty.divide(Env.ONE.subtract(scrapDec), precision, BigDecimal.ROUND_HALF_UP);
    }
    //
    if (qty.scale() > precision) {
      qty = qty.setScale(precision, RoundingMode.HALF_UP);
    }
    //
    return qty;
  }

  /** Like {@link #getQty(boolean)}, includeScrapQty = false */
  public BigDecimal getQty() {
    return getQty(false);
  }

  /** @return UOM precision */
  public int getPrecision() {
    return MUOM.getPrecision(getCtx(), getC_UOM_ID());
  }

  /**
   * @param fallback use QtyBOM/QtyPercentage if CostAllocationPerc is zero
   * @return co-product cost allocation percent (i.e. -1/qty)
   */
  public BigDecimal getCostAllocationPerc(boolean fallback) {
    BigDecimal allocationPercent = super.getCostAllocationPerc();
    if (allocationPercent.signum() != 0) return allocationPercent;
    //
    // Fallback and try to calculate it from Qty
    if (fallback) {
      BigDecimal qty = getQty(false).negate();
      if (qty.signum() != 0) {
        allocationPercent = Env.ONE.divide(qty, 4, RoundingMode.HALF_UP);
      }
    }
    return allocationPercent;
  }
}
