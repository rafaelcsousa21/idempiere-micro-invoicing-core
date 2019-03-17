package org.compiere.conversionrate;

import kotliquery.Row;
import org.compiere.model.I_C_ConversionType;
import org.idempiere.common.util.CCache;

import java.util.Properties;

import static software.hsharp.core.util.DBKt.getSQLValue;


/**
 * Currency Conversion Type Model
 *
 * @author Jorg Janke
 * @version $Id: MConversionType.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public class MConversionType extends X_C_ConversionType {
    /**
     * These static variables won't have to change unless we change the AD. I've added them here for
     * convenience. Daniel Tamm [usrdno] 090528
     */
    public static final int TYPE_SPOT = 114;
    /**
     *
     */
    private static final long serialVersionUID = 7198388106444590667L;
    /**
     * Cache Client-ID
     */
    private static CCache<Integer, Integer> s_cache =
            new CCache<Integer, Integer>(I_C_ConversionType.Table_Name, 4);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx                 context
     * @param C_ConversionType_ID id
     */
    public MConversionType(Properties ctx, int C_ConversionType_ID) {
        super(ctx, C_ConversionType_ID);
    } //	MConversionType

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MConversionType(Properties ctx, Row row) {
        super(ctx, row);
    } //	MConversionType

    /**
     * Get Default Conversion Rate for Client/Org
     *
     * @param AD_Client_ID client
     * @return C_ConversionType_ID or 0 if not found
     */
    public static int getDefault(int AD_Client_ID) {
        //	Try Cache
        Integer key = AD_Client_ID;
        Integer ii = s_cache.get(key);
        if (ii != null) return ii;

        //	Get from DB
        int C_ConversionType_ID = 0;
        String sql =
                "SELECT C_ConversionType_ID "
                        + "FROM C_ConversionType "
                        + "WHERE IsActive='Y'"
                        + " AND clientId IN (0,?) " //	#1
                        + "ORDER BY IsDefault DESC, clientId DESC";
        C_ConversionType_ID = getSQLValue(sql, AD_Client_ID);
        //	Return
        s_cache.put(key, C_ConversionType_ID);
        return C_ConversionType_ID;
    } //	getDefault
} //	MConversionType
