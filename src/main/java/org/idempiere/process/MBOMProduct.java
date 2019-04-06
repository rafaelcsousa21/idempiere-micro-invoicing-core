package org.idempiere.process;

import kotliquery.Row;
import org.compiere.model.I_M_BOMProduct;
import org.compiere.orm.Query;
import org.compiere.util.Msg;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.util.List;

import static software.hsharp.core.util.DBKt.getSQLValue;

public class MBOMProduct extends X_M_BOMProduct {
    /**
     *
     */
    private static final long serialVersionUID = 3431041011059529621L;
    /**
     * Logger
     */
    @SuppressWarnings("unused")
    private static CLogger s_log = CLogger.getCLogger(MBOMProduct.class);
    /**
     * BOM Parent
     */
    private MBOM m_bom = null;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx             context
     * @param M_BOMProduct_ID id
     * @param trxName         trx
     */
    public MBOMProduct(int M_BOMProduct_ID) {
        super(M_BOMProduct_ID);
        if (M_BOMProduct_ID == 0) {
            //	setBOM_ID (0);
            setBOMProductType(BOMPRODUCTTYPE_StandardProduct); // S
            setBOMQty(Env.ONE);
            setIsPhantom(false);
            setLeadTimeOffset(0);
            //	setLine (0);	// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM M_BOMProduct WHERE
            // M_BOM_ID=@M_BOM_ID@
        }
    } //	MBOMProduct

    /**
     * Parent Constructor
     *
     * @param bom product
     */
    public MBOMProduct(MBOM bom) {
        this(0);
        m_bom = bom;
    } //	MBOMProduct

    /**
     * Load Constructor
     */
    public MBOMProduct(Row row) {
        super(row);
    } //	MBOMProduct

    /**
     * Get Products of BOM
     *
     * @param bom bom
     * @return array of BOM Products
     */
    public static MBOMProduct[] getOfBOM(MBOM bom) {
        // FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
        String whereClause = "M_BOM_ID=?";
        List<MBOMProduct> list =
                new Query(I_M_BOMProduct.Table_Name, whereClause)
                        .setParameters(bom.getBOMId())
                        .setOrderBy("SeqNo")
                        .list();

        MBOMProduct[] retValue = new MBOMProduct[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getOfProduct

    /**
     * Get Parent
     *
     * @return parent
     */
    private MBOM getBOM() {
        if (m_bom == null && getBOMId() != 0) m_bom = MBOM.get(getBOMId());
        return m_bom;
    } //	getBOM

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true/false
     */
    protected boolean beforeSave(boolean newRecord) {
        //	Product
        if (getBOMProductType().equals(BOMPRODUCTTYPE_OutsideProcessing)) {
            if (getProductBOMId() != 0) setProductBOMId(0);
        } else if (getProductBOMId() == 0) {
            log.saveError("Error", Msg.parseTranslation("@NotFound@ @M_ProductBOM_ID@"));
            return false;
        }
        //	Operation
        if (getProductOperationId() == 0) {
            if (getSeqNo() != 0) setSeqNo(0);
        } else if (getSeqNo() == 0) {
            log.saveError("Error", Msg.parseTranslation("@NotFound@ @SeqNo@"));
            return false;
        }
        //	Product Attribute Instance
        if (getAttributeSetInstanceId() != 0) {
            getBOM();
            if (m_bom == null || !MBOM.BOMTYPE_Make_To_Order.equals(m_bom.getBOMType())) {
                log.saveError(
                        "Error",
                        Msg.parseTranslation("Reset @M_AttributeSetInstance_ID@: Not Make-to-Order"));
                setAttributeSetInstanceId(0);
                return false;
            }
        }
        //	Alternate
        if ((getBOMProductType().equals(BOMPRODUCTTYPE_Alternative)
                || getBOMProductType().equals(BOMPRODUCTTYPE_AlternativeDefault))
                && getBOMAlternativeGroupId() == 0) {
            log.saveError("Error", Msg.parseTranslation("@NotFound@ @M_BOMAlternative_ID@"));
            return false;
        }
        //	Operation
        if (getProductOperationId() != 0) {
            if (getSeqNo() == 0) {
                log.saveError("Error", Msg.parseTranslation("@NotFound@ @SeqNo@"));
                return false;
            }
        } else //	no op
        {
            if (getSeqNo() != 0) setSeqNo(0);
            if (getLeadTimeOffset() != 0) setLeadTimeOffset(0);
        }

        //	Set Line Number
        if (getLine() == 0) {
            String sql = "SELECT NVL(MAX(Line),0)+10 FROM M_BOMProduct WHERE M_BOM_ID=?";
            int ii = getSQLValue(sql, getBOMId());
            setLine(ii);
        }

        return true;
    } //	beforeSave
} //	MBOMProduct
