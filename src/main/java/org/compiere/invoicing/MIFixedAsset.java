package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.accounting.MClientKt;
import org.compiere.accounting.MProduct;
import org.idempiere.common.util.Util;

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
    public MIFixedAsset(int I_FixedAsset_ID) {
        super(I_FixedAsset_ID);
    } //	MIFixedAsset

    /**
     * Load Constructor
     */
    public MIFixedAsset(Row row) {
        super(row);
    } //	MIFixedAsset

    public MProduct getProduct() {
        if (m_product == null && getProductId() > 0) {
            m_product = new MProduct(getProductId());
        }
        return m_product;
    }

    public void setProduct(MProduct product) {
        m_product = product;
        setProductId(product.getId());
        setProductValue(product.getSearchKey());
        if (Util.isEmpty(getName())) setName(product.getName());
    }

    /**
     * Currency
     */
    public int getStdPrecision() {
        return MClientKt.getClientWithAccounting().getAcctSchema().getStdPrecision();
    }

}
