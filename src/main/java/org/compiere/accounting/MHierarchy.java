package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_PA_Hierarchy;
import org.idempiere.common.util.CCache;

import java.util.Properties;

/**
 * Reporting Hierarchy Model
 *
 * @author Jorg Janke
 * @version $Id: MHierarchy.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MHierarchy extends X_PA_Hierarchy {
    /**
     *
     */
    private static final long serialVersionUID = 3278979908976853690L;
    /**
     * Cache
     */
    private static CCache<Integer, MHierarchy> s_cache =
            new CCache<Integer, MHierarchy>(I_PA_Hierarchy.Table_Name, 20);

    /**
     * Default Constructor
     *
     * @param ctx             context
     * @param PA_Hierarchy_ID id
     * @param trxName         trx
     */
    public MHierarchy(Properties ctx, int PA_Hierarchy_ID) {
        super(ctx, PA_Hierarchy_ID);
    } //	MHierarchy

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName trx
     */
    public MHierarchy(Properties ctx, Row row) {
        super(ctx, row);
    } //	MHierarchy

    /**
     * Get MHierarchy from Cache
     *
     * @param ctx             context
     * @param PA_Hierarchy_ID id
     * @return MHierarchy
     */
    public static MHierarchy get(Properties ctx, int PA_Hierarchy_ID) {
        Integer key = new Integer(PA_Hierarchy_ID);
        MHierarchy retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MHierarchy(ctx, PA_Hierarchy_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Get AD_Tree_ID based on tree type
     *
     * @param TreeType Tree Type
     * @return id or 0
     */
    public int getAD_TreeId(String TreeType) {
        if (MTree.TREETYPE_Activity.equals(TreeType)) return getTreeActivityId();
        if (MTree.TREETYPE_BPartner.equals(TreeType)) return getTreeBPartnerId();
        if (MTree.TREETYPE_Campaign.equals(TreeType)) return getTreeCampaignId();
        if (MTree.TREETYPE_ElementValue.equals(TreeType)) return getTreeAccountId();
        if (MTree.TREETYPE_Organization.equals(TreeType)) return getTreeOrgId();
        if (MTree.TREETYPE_Product.equals(TreeType)) return getTreeProductId();
        if (MTree.TREETYPE_Project.equals(TreeType)) return getTreeProjectId();
        if (MTree.TREETYPE_SalesRegion.equals(TreeType)) return getTreeSalesRegionId();
        //
        log.warning("Not supported: " + TreeType);
        return 0;
    } //	getTreeId
} //	MHierarchy
