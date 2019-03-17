package org.idempiere.process;

import kotliquery.Row;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;

import java.util.Properties;

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
     * @param ctx context
     */
    public MGLCategory(Properties ctx, Row row) {
        super(ctx, row);
    } //	MGLCategory

    /**
     * Get MGLCategory from Cache
     *
     * @param ctx            context
     * @param GL_Category_ID id
     * @return MGLCategory
     */
    public static MGLCategory get(Properties ctx, int GL_Category_ID) {
        Integer key = GL_Category_ID;
        MGLCategory retValue = s_cache.get(key);
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
        return MBaseGLCategoryKt.getDefault(ctx, CategoryType);
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
