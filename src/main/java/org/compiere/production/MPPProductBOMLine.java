package org.compiere.production;

import kotliquery.Row;
import org.compiere.product.MProduct;
import org.compiere.product.MUOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.getSQLValueEx;

/**
 * PP Product BOM Line Model. <code>
 * MPPProductBOMLine l = new MPPProductBOMLine(bom);
 * l.setProductId(wbl.getProductId());
 * l.setQty(wbl.getQuantity());
 * l.saveEx();
 * </code>
 *
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
public class MPPProductBOMLine extends X_PP_Product_BOMLine {

    /**
     *
     */
    private static final long serialVersionUID = -5792418944606756221L;

    /**
     * Default Constructor
     *
     * @param ctx                context
     * @param PP_Product_BOMLine BOM line to load
     * @param Transaction        Line
     */
    public MPPProductBOMLine(Properties ctx, int PP_Product_BOMLine) {
        super(ctx, PP_Product_BOMLine);
    } //	MPPProductBOMLine

    /**
     * Parent Constructor.
     *
     * @param bom parent BOM
     */
    public MPPProductBOMLine(MPPProductBOM bom) {
        super(bom.getCtx(), 0);
        if (bom.getId() <= 0) throw new IllegalArgumentException("Header not saved");
        setPP_Product_BOMId(bom.getPP_Product_BOMId()); // 	parent
    }

    /**
     * Load Constructor
     *
     * @param ctx context
     * @param rs  result set record
     */
    public MPPProductBOMLine(Properties ctx, Row row) {
        super(ctx, row);
    } //	 MPPProductBOMLine

    /**
     * Calculate Low Level of a Product
     *
     * @param ID Product
     * @return int low level
     */
    public int getLowLevel() {
        return new ProductLowLevelCalculator(getCtx()).getLowLevel(getProductId());
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
            int line = getSQLValueEx(sql, getPP_Product_BOMId());
            setLine(line);
        }

        return true;
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return false;

        int lowlevel = getLowLevel();
        MProduct product = new MProduct(getCtx(), getProductId());
        product.setLowLevel(lowlevel); // update lowlevel
        product.saveEx();

        return true;
    }

    public boolean isValidFromTo(Timestamp date) {
        Timestamp validFrom = getValidFrom();
        Timestamp validTo = getValidTo();

        if (validFrom != null && date.before(validFrom)) return false;
        return validTo == null || !date.after(validTo);
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

    /**
     * @return UOM precision
     */
    public int getPrecision() {
        return MUOM.getPrecision(getCtx(), getUOMId());
    }

}
