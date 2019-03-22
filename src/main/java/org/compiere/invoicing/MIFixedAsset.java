package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.accounting.MClient;
import org.compiere.accounting.MProduct;
import org.idempiere.common.util.Util;

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
     */
    public MIFixedAsset(Properties ctx, Row row) {
        super(ctx, row);
    } //	MIFixedAsset

    public MProduct getProduct() {
        if (m_product == null && getProductId() > 0) {
            m_product = new MProduct(getCtx(), getProductId());
        }
        return m_product;
    }

    public void setProduct(MProduct product) {
        m_product = product;
        setProductId(product.getId());
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
