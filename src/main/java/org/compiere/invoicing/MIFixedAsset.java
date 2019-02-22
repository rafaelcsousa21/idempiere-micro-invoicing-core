package org.compiere.invoicing;

import org.compiere.accounting.MClient;
import org.compiere.accounting.MProduct;
import org.idempiere.common.util.Util;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Teo Sarca, SC ARHIPAC SRL
 * @version $Id
 */
public class MIFixedAsset extends X_I_FixedAsset {

    /**
     *
     */
    private static final long serialVersionUID = -6394518107160329652L;
    /**
     * Default depreciation method
     */
    private static final String s_defaultDepreciationType = "SL";
    /**
     *
     */
    private int m_M_Product_Category_ID = 0;
    /**
     *
     */
    private int m_A_Asset_Group_ID = 0;
    /**
     * Product
     */
    private MProduct m_product = null;

    /**
     * Standard Constructor
     */
    public MIFixedAsset(Properties ctx, int I_FixedAsset_ID) {
        super(ctx, I_FixedAsset_ID);
    } //	MIFixedAsset

    /**
     * Load Constructor
     *
     * @param ctx context
     * @param rs  result set record
     */
    public MIFixedAsset(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MIFixedAsset

    public MProduct getProduct() {
        if (m_product == null && getM_Product_ID() > 0) {
            m_product = new MProduct(getCtx(), getM_Product_ID());
        }
        return m_product;
    }

    public void setProduct(MProduct product) {
        m_product = product;
        setM_Product_ID(product.getId());
        setProductValue(product.getValue());
        if (Util.isEmpty(getName())) setName(product.getName());
    }

    /**
     * Currency
     */
    public int getStdPrecision() {
        return MClient.get(getCtx()).getAcctSchema().getStdPrecision();
    }

}
