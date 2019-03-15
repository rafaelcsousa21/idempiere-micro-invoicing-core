package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_R_StatusCategory;
import org.compiere.util.Msg;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdate;
import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Request Status Category Model
 *
 * @author Jorg Janke
 * @version $Id: MStatusCategory.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MStatusCategory extends X_R_StatusCategory {
    /**
     *
     */
    private static final long serialVersionUID = -7538457243144691380L;
    /**
     * Cache
     */
    private static CCache<Integer, MStatusCategory> s_cache =
            new CCache<Integer, MStatusCategory>(I_R_StatusCategory.Table_Name, 20);
    /**
     * Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MStatusCategory.class);
    /**
     * The Status
     */
    private MStatus[] m_status = null;

    /**
     * ************************************************************************ Default Constructor
     *
     * @param ctx                 context
     * @param R_StatusCategory_ID id
     */
    public MStatusCategory(Properties ctx, int R_StatusCategory_ID) {
        super(ctx, R_StatusCategory_ID);
        if (R_StatusCategory_ID == 0) {
            //	setName (null);
            setIsDefault(false);
        }
    } //	RStatusCategory

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MStatusCategory(Properties ctx, Row row) {
        super(ctx, row);
    } //	RStatusCategory

    /**
     * Get Default Status category for Client
     *
     * @param ctx context
     * @return status category or null
     */
    public static MStatusCategory getDefault(Properties ctx) {
        int AD_Client_ID = Env.getClientId(ctx);
        return MBaseStatusCategoryKt.getDefaultStatusCategoryForClient(ctx, AD_Client_ID);
    } //	getDefault

    /**
     * Get Default Status Categpru for Client
     *
     * @param ctx context
     * @return status category or null
     */
    public static MStatusCategory createDefault(Properties ctx) {
        int AD_Client_ID = Env.getClientId(ctx);
        MStatusCategory retValue = new MStatusCategory(ctx, 0);
        retValue.setClientOrg(AD_Client_ID, 0);
        retValue.setName(Msg.getMsg(ctx, "Standard"));
        retValue.setIsDefault(true);
        if (!retValue.save()) return null;
        String sql =
                "UPDATE R_Status SET R_StatusCategory_ID="
                        + retValue.getR_StatusCategory_ID()
                        + " WHERE R_StatusCategory_ID IS NULL AND AD_Client_ID="
                        + AD_Client_ID;
        int no = executeUpdate(sql);
        if (s_log.isLoggable(Level.INFO))
            s_log.info("Default for AD_Client_ID=" + AD_Client_ID + " - Status #" + no);
        return retValue;
    } //	createDefault

    /**
     * Get Request Status Category from Cache
     *
     * @param ctx                 context
     * @param R_StatusCategory_ID id
     * @return RStatusCategory
     */
    public static MStatusCategory get(Properties ctx, int R_StatusCategory_ID) {
        Integer key = new Integer(R_StatusCategory_ID);
        MStatusCategory retValue = (MStatusCategory) s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MStatusCategory(ctx, R_StatusCategory_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Get all Status
     *
     * @param reload reload
     * @return Status array
     */
    public MStatus[] getStatus(boolean reload) {
        if (m_status != null && !reload) return m_status;
        m_status = MBaseStatusCategoryKt.getAllStatus(getCtx(), getR_StatusCategory_ID());
        return m_status;
    } //	getStatus

    /**
     * Get Default R_Status_ID
     *
     * @return id or 0
     */
    public int getDefaultR_Status_ID() {
        if (m_status == null) getStatus(false);
        for (int i = 0; i < m_status.length; i++) {
            if (m_status[i].isDefault() && m_status[i].isActive()) return m_status[i].getR_Status_ID();
        }
        if (m_status.length > 0 && m_status[0].isActive()) return m_status[0].getR_Status_ID();
        return 0;
    } //	getDefaultR_Status_ID

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("RStatusCategory[");
        sb.append(getId()).append("-").append(getName()).append("]");
        return sb.toString();
    } //	toString
} //	RStatusCategory
