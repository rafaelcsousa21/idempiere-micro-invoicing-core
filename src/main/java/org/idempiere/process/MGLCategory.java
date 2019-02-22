package org.idempiere.process;

import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;

public class MGLCategory extends X_GL_Category {
    /**
     *
     */
    private static final long serialVersionUID = -272365151811522531L;
    /**
     * Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MGLCategory.class);
    /**
     * Cache
     */
    private static CCache<Integer, MGLCategory> s_cache =
            new CCache<Integer, MGLCategory>(Table_Name, 5);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx            context
     * @param GL_Category_ID id
     * @param trxName        transaction
     */
    public MGLCategory(Properties ctx, int GL_Category_ID) {
        super(ctx, GL_Category_ID);
        if (GL_Category_ID == 0) {
            //	setName (null);
            setCategoryType(CATEGORYTYPE_Manual);
            setIsDefault(false);
        }
    } //	MGLCategory

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MGLCategory(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MGLCategory

    /**
     * Get MGLCategory from Cache
     *
     * @param ctx            context
     * @param GL_Category_ID id
     * @return MGLCategory
     */
    public static MGLCategory get(Properties ctx, int GL_Category_ID) {
        Integer key = new Integer(GL_Category_ID);
        MGLCategory retValue = (MGLCategory) s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MGLCategory(ctx, GL_Category_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Get Default Category
     *
     * @param ctx          context
     * @param CategoryType optional CategoryType (ignored, if not exists)
     * @return GL Category or null
     */
    public static MGLCategory getDefault(Properties ctx, String CategoryType) {
        MGLCategory retValue = null;
        String sql = "SELECT * FROM GL_Category " + "WHERE AD_Client_ID=? AND IsDefault='Y'";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, Env.getClientId(ctx));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MGLCategory temp = new MGLCategory(ctx, rs);
                if (CategoryType != null && CategoryType.equals(temp.getCategoryType())) {
                    retValue = temp;
                    break;
                }
                if (retValue == null) retValue = temp;
            }
        } catch (Exception e) {
            s_log.log(Level.SEVERE, sql, e);
        } finally {

            rs = null;
            pstmt = null;
        }
        return retValue;
    } //	getDefault

    /**
     * Get Default System Category
     *
     * @param ctx context
     * @return GL Category
     */
    public static MGLCategory getDefaultSystem(Properties ctx) {
        MGLCategory retValue = getDefault(ctx, CATEGORYTYPE_SystemGenerated);
        if (retValue == null || !retValue.getCategoryType().equals(CATEGORYTYPE_SystemGenerated)) {
            retValue = new MGLCategory(ctx, 0);
            retValue.setName("Default System");
            retValue.setCategoryType(CATEGORYTYPE_SystemGenerated);
            retValue.setIsDefault(true);
            if (!retValue.save())
                throw new IllegalStateException("Could not save default system GL Category");
        }
        return retValue;
    } //	getDefaultSystem

    @Override
    public String toString() {
        StringBuilder msgreturn =
                new StringBuilder()
                        .append(getClass().getSimpleName())
                        .append("[")
                        .append(getId())
                        .append(", Name=")
                        .append(getName())
                        .append(", IsDefault=")
                        .append(isDefault())
                        .append(", IsActive=")
                        .append(isActive())
                        .append(", CategoryType=")
                        .append(getCategoryType())
                        .append("]");
        return msgreturn.toString();
    }
} //	MGLCategory
