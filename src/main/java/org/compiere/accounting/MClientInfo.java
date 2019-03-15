package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.orm.MClient;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.util.Properties;

/**
 * Client Info Model
 *
 * @author Jorg Janke
 * @version $Id: MClientInfo.java,v 1.2 2006/07/30 00:58:37 jjanke Exp $
 */
public class MClientInfo extends org.compiere.orm.MClientInfo {
    /**
     *
     */
    private static final long serialVersionUID = 4861006368856890116L;
    protected static CCache<Integer, MClientInfo> s_cache =
            new CCache<Integer, MClientInfo>(Table_Name, 2);
    /**
     * Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MClientInfo.class);
    /**
     * Account Schema
     */
    private MAcctSchema m_acctSchema = null;

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MClientInfo(Properties ctx, Row row) {
        super(ctx, row);
    }

    public MClientInfo(
            MClient client,
            int AD_Tree_Org_ID,
            int AD_Tree_BPartner_ID,
            int AD_Tree_Project_ID,
            int AD_Tree_SalesRegion_ID,
            int AD_Tree_Product_ID,
            int AD_Tree_Campaign_ID,
            int AD_Tree_Activity_ID,
            String trxName) {
        super(
                client,
                AD_Tree_Org_ID,
                AD_Tree_BPartner_ID,
                AD_Tree_Project_ID,
                AD_Tree_SalesRegion_ID,
                AD_Tree_Product_ID,
                AD_Tree_Campaign_ID,
                AD_Tree_Activity_ID,
                trxName);
    }

    /**
     * Get optionally cached client
     *
     * @param ctx context
     * @return client
     */
    public static MClientInfo get(Properties ctx) {
        return get(ctx, Env.getClientId(ctx));
    } //	get

    /**
     * Get Client Info
     *
     * @param ctx          context
     * @param AD_Client_ID id
     * @return Client Info
     */
    public static MClientInfo get(Properties ctx, int AD_Client_ID) {
        Integer key = AD_Client_ID;
        MClientInfo info = (MClientInfo) s_cache.get(key);
        if (info != null) return info;

        info = MBaseClientInfoKt.getClientInfo(ctx, AD_Client_ID);

        s_cache.put(key, info);
        return info;
    } //	get

    /**
     * Get primary Acct Schema
     *
     * @return acct schema
     */
    public MAcctSchema getMAcctSchema1() {
        if (m_acctSchema == null && getAcctSchema1Id() != 0)
            m_acctSchema = new MAcctSchema(getCtx(), getAcctSchema1Id());
        return m_acctSchema;
    } //	getMAcctSchema1

    /**
     * Get Default Accounting Currency
     *
     * @return currency or 0
     */
    public int getCurrencyId() {
        if (m_acctSchema == null) getMAcctSchema1();
        if (m_acctSchema != null) return m_acctSchema.getCurrencyId();
        return 0;
    } //	getCurrencyId

    /**
     * Overwrite Save
     *
     * @return true if saved
     * @overwrite
     */
    public boolean save() {
        if (getOrgId() != 0) setOrgId(0);
        if (getCreateNew()) return super.save();
        return saveUpdate();
    } //	save

} //	MClientInfo
