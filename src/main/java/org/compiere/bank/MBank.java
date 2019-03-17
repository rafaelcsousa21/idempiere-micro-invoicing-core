package org.compiere.bank;

import kotliquery.Row;
import org.compiere.model.I_C_Bank;
import org.idempiere.common.util.CCache;

import java.util.Properties;

/**
 * Bank Model
 *
 * @author Jorg Janke
 * @version $Id: MBank.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MBank extends X_C_Bank {
    /**
     *
     */
    private static final long serialVersionUID = 3459010882027283811L;
    /**
     * Cache
     */
    private static CCache<Integer, MBank> s_cache =
            new CCache<Integer, MBank>(I_C_Bank.Table_Name, 3);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx       context
     * @param C_Bank_ID bank
     */
    public MBank(Properties ctx, int C_Bank_ID) {
        super(ctx, C_Bank_ID);
    } //	MBank

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MBank(Properties ctx, Row row) {
        super(ctx, row);
    } //	MBank

    /**
     * Get MBank from Cache
     *
     * @param ctx       context
     * @param C_Bank_ID id
     * @return MBank
     */
    public static MBank get(Properties ctx, int C_Bank_ID) {
        Integer key = C_Bank_ID;
        MBank retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MBank(ctx, C_Bank_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MBank[");
        sb.append(getId()).append("-").append(getName()).append("]");
        return sb.toString();
    } //	toString
} //	MBank
