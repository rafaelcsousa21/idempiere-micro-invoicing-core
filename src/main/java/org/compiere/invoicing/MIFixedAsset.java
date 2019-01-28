package org.compiere.invoicing;

import org.compiere.accounting.MClient;
import org.compiere.accounting.MProduct;
import org.idempiere.common.util.Util;

import java.sql.ResultSet;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.TO_STRING;
import static software.hsharp.core.util.DBKt.getSQLValueEx;

/**
 * @author Teo Sarca, SC ARHIPAC SRL
 * @version $Id
 */
public class MIFixedAsset extends X_I_FixedAsset {

  /** */
  private static final long serialVersionUID = -6394518107160329652L;
  /** Default depreciation method */
  private static final String s_defaultDepreciationType = "SL";

  /** Standard Constructor */
  public MIFixedAsset(Properties ctx, int I_FixedAsset_ID, String trxName) {
    super(ctx, I_FixedAsset_ID, trxName);
  } //	MIFixedAsset

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set record
   */
  public MIFixedAsset(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MIFixedAsset

    /** */
  private int m_M_Product_Category_ID = 0;

    /** */
  private int m_A_Asset_Group_ID = 0;

    /** Product */
  private MProduct m_product = null;

  public void setProduct(MProduct product) {
    m_product = product;
    setM_Product_ID(product.getId());
    setProductValue(product.getValue());
    if (Util.isEmpty(getName())) setName(product.getName());
  }

  public MProduct getProduct() {
    if (m_product == null && getM_Product_ID() > 0) {
      m_product = new MProduct(getCtx(), getM_Product_ID(), null);
    }
    return m_product;
  }

    /** Currency */
  public int getStdPrecision() {
    return MClient.get(getCtx()).getAcctSchema().getStdPrecision();
  }

}
