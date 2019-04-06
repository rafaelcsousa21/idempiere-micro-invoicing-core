package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_R_Category;
import org.idempiere.common.util.CCache;

/**
 * Request Category Model
 *
 * @author Jorg Janke
 * @version $Id: MRequestCategory.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MRequestCategory extends X_R_Category {
    /**
     *
     */
    private static final long serialVersionUID = 9174605980194362716L;
    /**
     * Cache
     */
    private static CCache<Integer, MRequestCategory> s_cache =
            new CCache<Integer, MRequestCategory>(I_R_Category.Table_Name, 20);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx           context
     * @param R_Category_ID id
     */
    public MRequestCategory(int R_Category_ID) {
        super(R_Category_ID);
    } //	MCategory

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MRequestCategory(Row row) {
        super(row);
    } //	MCategory

    /**
     * Get MCategory from Cache
     *
     * @param ctx           context
     * @param R_Category_ID id
     * @return MCategory
     */
    public static MRequestCategory get(int R_Category_ID) {
        Integer key = R_Category_ID;
        MRequestCategory retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MRequestCategory(R_Category_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get
} //	MCategory
