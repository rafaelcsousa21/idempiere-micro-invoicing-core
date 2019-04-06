package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.orm.Query;
import org.idempiere.common.util.CCache;

/**
 * Product Category Account Model
 *
 * @author Jorg Janke
 * @version $Id: MProductCategoryAcct.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MProductCategoryAcct extends X_M_Product_Category_Acct {
    /**
     *
     */
    private static final long serialVersionUID = 2075372131034904732L;
    /**
     * Static cache
     */
    private static CCache<String, MProductCategoryAcct> s_cache =
            new CCache<String, MProductCategoryAcct>(I_M_Product_Category_Acct.Table_Name, 40, 5);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx     context
     * @param ignored ignored
     * @param trxName
     */
    public MProductCategoryAcct(int ignored) {
        super(ignored);
        if (ignored != 0) throw new IllegalArgumentException("Multi-Key");
    } //	MProductCategoryAcct

    /**
     * Load Cosntructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName trx
     */
    public MProductCategoryAcct(Row row) {
        super(row);
    } //	MProductCategoryAcct

    /**
     * Get Category Acct
     *
     * @param ctx                   context
     * @param M_Product_Category_ID category
     * @param C_AcctSchema_ID       acct schema
     * @return category acct
     */
    public static MProductCategoryAcct get(
            int M_Product_Category_ID, int C_AcctSchema_ID) {
        String key = M_Product_Category_ID + "#" + C_AcctSchema_ID;
        MProductCategoryAcct acct = s_cache.get(key);
        if (acct != null) return acct;

        final String whereClause = "M_Product_Category_ID=? AND C_AcctSchema_ID=?";
        acct =
                new Query(I_M_Product_Category_Acct.Table_Name, whereClause)
                        .setParameters(M_Product_Category_ID, C_AcctSchema_ID)
                        .firstOnly();
        if (acct != null) {
            s_cache.put(key, acct);
        }
        return acct;
    } //	get

    /**
     * Check Costing Setup
     */
    public void checkCosting() {
        //	Create Cost Elements
        if (getCostingMethod() != null && getCostingMethod().length() > 0)
            MCostElement.getMaterialCostElement(this, getCostingMethod());
    } //	checkCosting

    /**
     * After Save
     *
     * @param newRecord new
     * @param success   success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        checkCosting();
        return success;
    } //	afterSave

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MProductCategoryAcct[");
        sb.append(getId())
                .append(",M_Product_Category_ID=")
                .append(getProductCategoryId())
                .append(",C_AcctSchema_ID=")
                .append(getAccountingSchemaId())
                .append(",CostingLevel=")
                .append(getCostingLevel())
                .append(",CostingMethod=")
                .append(getCostingMethod())
                .append("]");
        return sb.toString();
    } //	toString
} //	MProductCategoryAcct
