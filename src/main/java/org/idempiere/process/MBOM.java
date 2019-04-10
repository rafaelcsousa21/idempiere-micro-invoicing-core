package org.idempiere.process;

import kotliquery.Row;
import org.compiere.model.I_M_BOM;
import org.compiere.orm.Query;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;

import java.util.List;

public class MBOM extends X_M_BOM {
    /**
     *
     */
    private static final long serialVersionUID = -8885316310068284701L;
    /**
     * Cache
     */
    private static CCache<Integer, MBOM> s_cache = new CCache<Integer, MBOM>(I_M_BOM.Table_Name, 20);
    /**
     * Logger
     */
    @SuppressWarnings("unused")
    private static CLogger s_log = CLogger.getCLogger(MBOM.class);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx      context
     * @param M_BOM_ID id
     * @param trxName  trx
     */
    public MBOM(int M_BOM_ID) {
        super(M_BOM_ID);
        if (M_BOM_ID == 0) {
            //	setProductId (0);
            //	setName (null);
            setBOMType(BOMTYPE_CurrentActive); // A
            setBOMUse(BOMUSE_Master); // A
        }
    } //	MBOM

    /**
     * Load Constructor
     *
     * @param ctx ctx
     */
    public MBOM(Row row) {
        super(row);
    } //	MBOM

    /**
     * Get BOM from Cache
     *
     * @param ctx      context
     * @param M_BOM_ID id
     * @return MBOM
     */
    public static MBOM get(int M_BOM_ID) {
        Integer key = M_BOM_ID;
        MBOM retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MBOM(M_BOM_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Get BOMs Of Product
     *
     * @param ctx          context
     * @param M_Product_ID product
     * @param trxName      trx
     * @param whereClause  optional WHERE clause w/o AND
     * @return array of BOMs
     */
    public static MBOM[] getOfProduct(
            int M_Product_ID, String trxName, String whereClause) {
        // FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
        StringBuilder where = new StringBuilder("M_Product_ID=?");
        if (whereClause != null && whereClause.length() > 0) where.append(" AND ").append(whereClause);
        List<MBOM> list =
                new Query(I_M_BOM.Table_Name, where.toString())
                        .setParameters(M_Product_ID)
                        .list();

        MBOM[] retValue = new MBOM[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getOfProduct

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true/false
     */
    protected boolean beforeSave(boolean newRecord) {
        //	BOM Type
        if (newRecord || isValueChanged("BOMType")) {
            //	Only one Current Active
            if (getBOMType().equals(BOMTYPE_CurrentActive)) {
                StringBuilder msgofp =
                        new StringBuilder("BOMType='A' AND BOMUse='")
                                .append(getBOMUse())
                                .append("' AND IsActive='Y'");
                MBOM[] boms = getOfProduct(getProductId(), null, msgofp.toString());
                if (boms.length != 0 // 	only one = this
                        && (boms.length != 1 || boms[0].getBOMId() != getBOMId())) {
                    log.saveError(
                            "Error",
                            MsgKt.parseTranslation(
                                    "Can only have one Current Active BOM for Product BOM Use ("
                                            + getBOMType()
                                            + ")"));
                    return false;
                }
            }
            //	Only one MTO
            else if (getBOMType().equals(BOMTYPE_Make_To_Order)) {
                MBOM[] boms = getOfProduct(getProductId(), null, "IsActive='Y'");
                if (boms.length != 0 // 	only one = this
                        && (boms.length != 1 || boms[0].getBOMId() != getBOMId())) {
                    log.saveError(
                            "Error",
                            MsgKt.parseTranslation("Can only have single Make-to-Order BOM for Product"));
                    return false;
                }
            }
        } //	BOM Type

        return true;
    } //	beforeSave
} //	MBOM
