package org.idempiere.process;

import kotliquery.Row;
import org.compiere.model.I_M_Replenish;
import org.compiere.orm.Query;
import org.idempiere.common.util.Env;

import java.util.List;
import java.util.Properties;

public class MReplenish extends X_M_Replenish {

    /**
     *
     */
    private static final long serialVersionUID = -76806183034687720L;

    /**
     * Standard constructor
     *
     * @param ctx
     * @param M_Replenish_ID
     */
    public MReplenish(Properties ctx, int M_Replenish_ID) {
        super(ctx, M_Replenish_ID);
    }

    /**
     * Standard constructor to create a PO from a resultset.
     *
     * @param ctx
     */
    public MReplenish(Properties ctx, Row row) {
        super(ctx, row);
    }

    /**
     * @param ctx
     * @param M_ProductID
     * @return A list of active replenish lines for given product.
     */
    public static List<MReplenish> getForProduct(Properties ctx, int M_ProductID) {
        final String whereClause = "M_Product_ID=? AND orgId IN (0, ?) ";
        return new Query(ctx, I_M_Replenish.Table_Name, whereClause)
                .setParameters(M_ProductID, Env.getOrgId(ctx))
                .setClientId()
                .setOrderBy("AD_Org_ID")
                .setOnlyActiveRecords(true)
                .list();
    }
}
