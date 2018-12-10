// package org.compiere.mfg.model;
package org.compiere.production;

import org.compiere.orm.Query;
import org.compiere.product.MProduct;
import org.eevolution.model.I_PP_Product_BOM;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.Env;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.getSQLValueEx;

/**
 * PP Product BOM Model.
 *
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class MPPProductBOM extends X_PP_Product_BOM {

  /** */
  private static final long serialVersionUID = 1561124355655122911L;
  /** Cache */
  private static CCache<Integer, MPPProductBOM> s_cache =
      new CCache<Integer, MPPProductBOM>(I_PP_Product_BOM.Table_Name, 40, 5);
  /** BOM Lines */
  private List<MPPProductBOMLine> m_lines = null;

  /**
   * get the Product BOM for a product
   *
   * @param product
   * @return return List with <MPPProductBOM
   */
  public static List<MPPProductBOM> getProductBOMs(MProduct product) {
    String whereClause = I_PP_Product_BOM.COLUMNNAME_Value + "=? AND M_Product_ID=?";
    return new Query(
            product.getCtx(), I_PP_Product_BOM.Table_Name, whereClause, product.get_TrxName())
        .setClient_ID()
        .setParameters(product.getValue(), product.getM_Product_ID())
        .list();
  }
  /**
   * Get Product BOM by ID (cached)
   *
   * @param ctx
   * @param PP_Product_BOM_ID
   * @return product bom
   */
  public static MPPProductBOM get(Properties ctx, int PP_Product_BOM_ID) {
    if (PP_Product_BOM_ID <= 0) return null;
    MPPProductBOM bom = s_cache.get(PP_Product_BOM_ID);
    if (bom != null) return bom;
    bom = new MPPProductBOM(ctx, PP_Product_BOM_ID, null);
    if (bom.getId() == PP_Product_BOM_ID) {
      s_cache.put(PP_Product_BOM_ID, bom);
    } else {
      bom = null;
    }
    return bom;
  }

  /**
   * Get PP_Product_BOM_ID for given M_Product_ID
   *
   * @param M_Product_ID
   * @return PP_Product_BOM_ID
   */
  public static int getBOMSearchKey(MProduct product) {
    int AD_Client_ID = Env.getADClientID(product.getCtx());
    String sql =
        "SELECT PP_Product_BOM_ID FROM PP_Product_BOM"
            + " WHERE Value=? AND M_Product_ID=? AND AD_Client_ID=?";
    return getSQLValueEx(null, sql, product.getValue(), product.getId(), AD_Client_ID);
  }

  /**
   * Get BOM with Default Logic (Product = BOM Product and BOM Value = Product Value)
   *
   * @param product
   * @param trxName
   * @return product BOM
   */
  public static MPPProductBOM getDefault(MProduct product, String trxName) {
    MPPProductBOM bom =
        new Query(
                product.getCtx(),
                I_PP_Product_BOM.Table_Name,
                "M_Product_ID=? AND Value=?",
                trxName)
            .setParameters(new Object[] {product.getM_Product_ID(), product.getValue()})
            .setClient_ID()
            .firstOnly();
    // If outside trx, then cache it
    if (bom != null && trxName == null) {
      s_cache.put(bom.getId(), bom);
    }
    //
    return bom;
  }

  /**
   * Get BOM for Product
   *
   * @param product product
   * @param ad_org_id Organization ID
   * @param trxName Transaction Name
   * @return BOM
   */
  public static MPPProductBOM get(MProduct product, int ad_org_id, String trxName) {
    MPPProductBOM bom = null;
    Properties ctx = product.getCtx();
    // find Default BOM in Product Data Planning
    if (ad_org_id > 0) {
      MPPProductPlanning pp =
          MPPProductPlanning.get(
              ctx, product.getClientId(), ad_org_id, product.getM_Product_ID(), trxName);
      if (pp != null && pp.getPP_Product_BOM_ID() > 0) {
        bom = new MPPProductBOM(ctx, pp.getPP_Product_BOM_ID(), trxName);
      }
    }
    if (bom == null) {
      // Find BOM with Default Logic where product = bom product and bom value = value
      bom = getDefault(product, trxName);
    }

    return bom;
  }

  /**
   * Get BOM with valid dates for Product
   *
   * @param product product
   * @param ad_org_id Organization ID
   * @param valid Date to Validate
   * @param trxName Transaction Name
   * @return BOM
   */
  public static MPPProductBOM get(
      MProduct product, int ad_org_id, Timestamp valid, String trxName) {
    MPPProductBOM bom = get(product, ad_org_id, trxName);
    if (bom != null && bom.isValidFromTo(valid)) {
      return bom;
    }
    return null;
  }

  public MPPProductBOM(Properties ctx, int PP_Product_BOM_ID, String trxName) {
    super(ctx, PP_Product_BOM_ID, trxName);
  }

  public MPPProductBOM(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * Get BOM Lines valid date for Product BOM
   *
   * @param valid Date to Validate
   * @return BOM Lines
   */
  public MPPProductBOMLine[] getLines(Timestamp valid) {
    List<MPPProductBOMLine> list = new ArrayList<MPPProductBOMLine>(); // Selected BOM Lines Only
    for (MPPProductBOMLine bl : getLines(true)) {
      if (bl.isValidFromTo(valid)) {
        list.add(bl);
      }
    }
    //
    return list.toArray(new MPPProductBOMLine[list.size()]);
  } //	getLines

  /**
   * Get BOM Lines for Product BOM from cache
   *
   * @return BOM Lines
   */
  public MPPProductBOMLine[] getLines() {
    return getLines(false);
  }

  /**
   * Get BOM Lines for Product BOM
   *
   * @return BOM Lines
   */
  public MPPProductBOMLine[] getLines(boolean reload) {
    if (this.m_lines == null || reload) {
      final String whereClause = MPPProductBOMLine.COLUMNNAME_PP_Product_BOM_ID + "=?";
      this.m_lines =
          new Query(getCtx(), MPPProductBOMLine.Table_Name, whereClause, get_TrxName())
              .setParameters(new Object[] {getPP_Product_BOM_ID()})
              .setOnlyActiveRecords(true)
              .setOrderBy(MPPProductBOMLine.COLUMNNAME_Line)
              .list();
    }
    return this.m_lines.toArray(new MPPProductBOMLine[this.m_lines.size()]);
  } //	getLines

  public boolean isValidFromTo(Timestamp date) {
    Timestamp validFrom = getValidFrom();
    Timestamp validTo = getValidTo();

    if (validFrom != null && date.before(validFrom)) return false;
    if (validTo != null && date.after(validTo)) return false;
    return true;
  }

  @Override
  protected boolean afterDelete(boolean success) {
    if (!success) return false;

    updateProduct();
    return true;
  }

  @Override
  protected boolean afterSave(boolean newRecord, boolean success) {
    if (!success) return false;

    if (newRecord || is_ValueChanged("IsActive")) {
      updateProduct();
    }
    return true;
  }

  private void updateProduct() {
    int count =
        new Query(
                getCtx(),
                I_PP_Product_BOM.Table_Name,
                I_PP_Product_BOM.COLUMNNAME_M_Product_ID + "=?",
                get_TrxName())
            .setParameters(new Object[] {getM_Product_ID()})
            .setOnlyActiveRecords(true)
            .count();
    MProduct product = new MProduct(getCtx(), getM_Product_ID(), get_TrxName());
    product.setIsBOM(count > 0);
    product.saveEx();
  }

  @Override
  public String toString() {
    StringBuffer sb =
        new StringBuffer("MPPProductBOM[")
            .append(getId())
            .append("-")
            .append(getDocumentNo())
            .append(", Value=")
            .append(getValue())
            .append("]");
    return sb.toString();
  }
} //	MPPProductBOM
