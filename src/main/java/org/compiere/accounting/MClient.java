package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_User;
import org.compiere.orm.MSysConfig;
import org.compiere.orm.Query;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Language;

import java.io.File;
import java.sql.ResultSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * Client Model
 *
 * @author Jorg Janke
 * @author Carlos Ruiz - globalqss integrate bug fix reported by Teo Sarca [ 1619085 ] Client setup
 * creates duplicate trees
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>BF [ 1886480 ] Print Format Item Trl not updated even if not multilingual
 * @version $Id: MClient.java,v 1.2 2006/07/30 00:58:37 jjanke Exp $
 */
public class MClient extends MBaseClient {
    /**
     *
     */
    private static final long serialVersionUID = -4420908648355523008L;
    // IDEMPIERE-722
    private static final String MAIL_SEND_CREDENTIALS_USER = "U";
    private static final String MAIL_SEND_CREDENTIALS_SYSTEM = "S";
    protected static CCache<Integer, org.compiere.orm.MClient> s_cache =
            new CCache<Integer, org.compiere.orm.MClient>(Table_Name, 3, 120, true);
    /**
     * Static Logger
     */
    @SuppressWarnings("unused")
    private static CLogger s_log = CLogger.getCLogger(MClient.class);
    /**
     * Language
     */
    private Language m_language = null;

    public MClient(Properties ctx, int ad_client_id) {
        super(ctx, ad_client_id);
    }

    public MClient(Properties ctx, int AD_Client_ID, boolean createNew) {
        super(ctx, AD_Client_ID, createNew);
    }

    public MClient(Properties ctx, Row row) {
        super(ctx, row);
    } //	MClient

    public MClient(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * Get all clients
     *
     * @param ctx context
     * @return clients
     */
    public static MClient[] getAll(Properties ctx) {
        return getAll(ctx, "");
    } //	getAll

    /**
     * Get all clients
     *
     * @param ctx   context
     * @param order by clause
     * @return clients
     */
    public static MClient[] getAll(Properties ctx, String orderBy) {
        List<MClient> list =
                new Query(ctx, I_AD_Client.Table_Name, null).setOrderBy(orderBy).list();
        for (MClient client : list) {
            s_cache.put(client.getClientId(), client);
        }
        MClient[] retValue = new MClient[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getAll

    public static boolean isClientAccounting() {
        String ca =
                MSysConfig.getConfigValue(
                        MSysConfig.CLIENT_ACCOUNTING,
                        CLIENT_ACCOUNTING_QUEUE, // default
                        Env.getClientId(Env.getCtx()));
        return (ca.equalsIgnoreCase(CLIENT_ACCOUNTING_IMMEDIATE)
                || ca.equalsIgnoreCase(CLIENT_ACCOUNTING_QUEUE));
    }

    public static boolean isSendCredentialsSystem() {
        String msc =
                MSysConfig.getConfigValue(
                        MSysConfig.MAIL_SEND_CREDENTIALS,
                        MAIL_SEND_CREDENTIALS_USER, // default
                        Env.getClientId(Env.getCtx()));
        return (MAIL_SEND_CREDENTIALS_SYSTEM.equalsIgnoreCase(msc));
    }

    /**
     * Get optionally cached client
     *
     * @param ctx context
     * @return client
     */
    public static MClient get(Properties ctx) {
        return get(ctx, Env.getClientId(ctx));
    } //	get

    /**
     * Get client
     *
     * @param ctx          context
     * @param AD_Client_ID id
     * @return client
     */
    public static MClient get(Properties ctx, int AD_Client_ID) {
        Integer key = AD_Client_ID;
        MClient client = (MClient) s_cache.get(key);
        if (client != null) return client;
        client = new MClient(ctx, AD_Client_ID);
        s_cache.put(key, client);
        return client;
    } //	get

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb =
                new StringBuilder("MClient[").append(getId()).append("-").append(getSearchKey()).append("]");
        return sb.toString();
    } //	toString

    /**
     * Get Client Info
     *
     * @return Client Info
     */
    public MClientInfo getInfo() {
        if (m_info == null)
            m_info = org.compiere.orm.MClientInfo.get(getCtx(), getClientId());
        return (MClientInfo) m_info;
    } //	getMClientInfo

    /*  2870483 - SaaS too slow opening windows */

    /**
     * Get Language
     *
     * @return client language
     */
    public Language getLanguage() {
        if (m_language == null) {
            m_language = Language.getLanguage(getADLanguage());
            // DAP Env.verifyLanguage(getCtx(), m_language);
        }
        return m_language;
    } //	getLanguage

    /**
     * Get AD_Language
     *
     * @return Language
     */
    public String getADLanguage() {
        String s = super.getADLanguage();
        if (s == null) return Language.getBaseAD_Language();
        return s;
    } //	getADLanguage

    /**
     * Set AD_Language
     *
     * @param AD_Language new language
     */
    public void setADLanguage(String AD_Language) {
        m_language = null;
        super.setADLanguage(AD_Language);
    } //	setADLanguage

    /**
     * Get Locale
     *
     * @return locale
     */
    public Locale getLocale() {
        Language lang = getLanguage();
        if (lang != null) return lang.getLocale();
        return Locale.getDefault();
    } //	getLocale

    /**
     * Get Primary Accounting Schema
     *
     * @return Acct Schema or null
     */
    public MAcctSchema getAcctSchema() {
        if (m_info == null) m_info = MClientInfo.get(getCtx(), getClientId());
        if (m_info != null) {
            int C_AcctSchema_ID = m_info.getAcctSchema1Id();
            if (C_AcctSchema_ID != 0) return MAcctSchema.get(getCtx(), C_AcctSchema_ID);
        }
        return null;
    } //	getMClientInfo

    /**
     * Save
     *
     * @return true if saved
     */
    public boolean save() {
        if (getId() == 0 && !getCreateNew()) return saveUpdate();
        return super.save();
    } //	save

    @Override
    public String getRequestUser() {
        // IDEMPIERE-722
        if (getClientId() != 0 && isSendCredentialsSystem()) {
            MClient sysclient = MClient.get(getCtx(), 0);
            return sysclient.getRequestUser();
        }
        return super.getRequestUser();
    }

    @Override
    public String getRequestUserPW() {
        // IDEMPIERE-722
        if (getClientId() != 0 && isSendCredentialsSystem()) {
            MClient sysclient = MClient.get(getCtx(), 0);
            return sysclient.getRequestUserPW();
        }
        return super.getRequestUserPW();
    }

    @Override
    public boolean isSmtpAuthorization() {
        // IDEMPIERE-722
        if (getClientId() != 0 && isSendCredentialsSystem()) {
            MClient sysclient = MClient.get(getCtx(), 0);
            return sysclient.isSmtpAuthorization();
        }
        return super.isSmtpAuthorization();
    }

    @Override
    public int getSMTPPort() {
        // IDEMPIERE-722
        if (getClientId() != 0 && isSendCredentialsSystem()) {
            MClient sysclient = MClient.get(getCtx(), 0);
            return sysclient.getSMTPPort();
        }
        return super.getSMTPPort();
    }

    @Override
    public boolean isSecureSMTP() {
        // IDEMPIERE-722
        if (getClientId() != 0 && isSendCredentialsSystem()) {
            MClient sysclient = MClient.get(getCtx(), 0);
            return sysclient.isSecureSMTP();
        }
        return super.isSecureSMTP();
    }

    /**
     * Get SMTP Host
     *
     * @return SMTP or loaclhost
     */
    @Override
    public String getSMTPHost() {
        String s = null;
        if (getClientId() != 0 && isSendCredentialsSystem()) {
            MClient sysclient = MClient.get(getCtx(), 0);
            s = sysclient.getSMTPHost();
        } else {
            s = super.getSMTPHost();
        }
        if (s == null) s = "localhost";
        return s;
    } //	getSMTPHost

    /**
     * Get Default Accounting Currency
     *
     * @return currency or 0
     */
    public int getC_Currency_ID() {
        if (m_info == null) getInfo();
        if (m_info != null) return getInfo().getC_Currency_ID();
        return 0;
    } //	getCurrencyId

    public boolean sendEMail(
            I_AD_User from,
            I_AD_User to,
            String subject,
            String message,
            File attachment,
            boolean isHtml) {
        return true;
    }

    public boolean sendEMail(
            I_AD_User from, I_AD_User to, String subject, String message, File attachment) {
        return true;
    }

    public void sendEMailAttachments(
            I_AD_User from,
            I_AD_User user,
            String schedulerName,
            String mailContent,
            List<File> fileList) {
    }

} //	MClient
