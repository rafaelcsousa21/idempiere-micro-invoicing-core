package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_R_Resolution;
import org.idempiere.common.util.CCache;

import java.util.Properties;

/**
 * Request Resolution Model
 *
 * @author Jorg Janke
 * @version $Id: MResolution.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MResolution extends X_R_Resolution {
    /**
     *
     */
    private static final long serialVersionUID = 9163046533055602877L;
    /**
     * Cache
     */
    private static CCache<Integer, MResolution> s_cache =
            new CCache<Integer, MResolution>(I_R_Resolution.Table_Name, 10);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx             context
     * @param R_Resolution_ID id
     */
    public MResolution(Properties ctx, int R_Resolution_ID) {
        super(ctx, R_Resolution_ID);
    } //	MResolution

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MResolution(Properties ctx, Row row) {
        super(ctx, row);
    } //	MResolution

    /**
     * Get MResolution from Cache
     *
     * @param ctx             context
     * @param R_Resolution_ID id
     * @return MResolution
     */
    public static MResolution get(Properties ctx, int R_Resolution_ID) {
        Integer key = R_Resolution_ID;
        MResolution retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MResolution(ctx, R_Resolution_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get
} //	MResolution
