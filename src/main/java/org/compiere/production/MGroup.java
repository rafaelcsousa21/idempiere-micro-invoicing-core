package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_R_Group;
import org.idempiere.common.util.CCache;

/**
 * Request Group Model
 *
 * @author Jorg Janke
 * @version $Id: MGroup.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MGroup extends X_R_Group {
    /**
     *
     */
    private static final long serialVersionUID = 3218102715154328611L;
    /**
     * Cache
     */
    private static CCache<Integer, MGroup> s_cache =
            new CCache<Integer, MGroup>(I_R_Group.Table_Name, 20);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx        context
     * @param R_Group_ID group
     * @param trxName    trx
     */
    public MGroup(int R_Group_ID) {
        super(R_Group_ID);
    } //	MGroup

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName trx
     */
    public MGroup(Row row) {
        super(row);
    } //	MGroup

    /**
     * Get MGroup from Cache
     *
     * @param ctx        context
     * @param R_Group_ID id
     * @return MGroup
     */
    public static MGroup get(int R_Group_ID) {
        Integer key = R_Group_ID;
        MGroup retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MGroup(R_Group_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get
} //	MGroup
