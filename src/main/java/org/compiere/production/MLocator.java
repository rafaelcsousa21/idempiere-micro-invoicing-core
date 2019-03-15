package org.compiere.production;

import kotliquery.Row;
import org.compiere.accounting.MWarehouse;
import org.compiere.accounting.X_M_Locator;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;

import java.util.Properties;


/**
 * Warehouse Locator Object
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com
 * @version $Id: MLocator.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 * @see [ 1966333 ] New Method to get the Default Locator based in Warehouse
 * http://sourceforge.net/tracker/index.php?func=detail&aid=1966333&group_id=176962&atid=879335
 */
public class MLocator extends X_M_Locator {
    /**
     *
     */
    private static final long serialVersionUID = 3649134803161895263L;
    /**
     * Cache
     */
    private static volatile CCache<Integer, MLocator> s_cache;
    /**
     * Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MLocator.class);

    /**
     * ************************************************************************ Standard Locator
     * Constructor
     *
     * @param ctx          Context
     * @param M_Locator_ID id
     */
    public MLocator(Properties ctx, int M_Locator_ID) {
        super(ctx, M_Locator_ID);
        if (M_Locator_ID == 0) {
            //	setM_Locator_ID (0);		//	PK
            //	setWarehouseId (0);		//	Parent
            setIsDefault(false);
            setPriorityNo(50);
            //	setValue (null);
            //	setX (null);
            //	setY (null);
            //	setZ (null);
        }
    } //	MLocator

    /**
     * New Locator Constructor with XYZ=000
     *
     * @param warehouse parent
     * @param Value     value
     */
    public MLocator(MWarehouse warehouse, String Value) {
        this(warehouse.getCtx(), 0);
        setClientOrg(warehouse);
        setWarehouseId(warehouse.getWarehouseId()); // 	Parent
        setValue(Value);
        setXYZ("0", "0", "0");
    } //	MLocator

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MLocator(Properties ctx, Row row) {
        super(ctx, row);
    } //	MLocator

    /**
     * FR [ 1966333 ] Get oldest Default Locator of warehouse with locator
     *
     * @return locator or null
     */
    public static MLocator getDefault(I_M_Warehouse warehouse) {
        return MBaseLocatorKt.getOldestDefaultLocatorOfWarehouseWithLocator(warehouse);
    } //	getDefault

    /**
     * Get Locator from Cache
     *
     * @param ctx          context
     * @param M_Locator_ID id
     * @return MLocator
     */
    public static MLocator get(Properties ctx, int M_Locator_ID) {
        if (s_cache == null) s_cache = new CCache<Integer, MLocator>(I_M_Locator.Table_Name, 20);
        Integer key = M_Locator_ID;
        MLocator retValue = (MLocator) s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MLocator(ctx, M_Locator_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Get String Representation
     *
     * @return Value
     */
    public String toString() {
        return getValue();
    } //	getValue

    /**
     * Set Location
     *
     * @param X x
     * @param Y y
     * @param Z z
     */
    public void setXYZ(String X, String Y, String Z) {
        setX(X);
        setY(Y);
        setZ(Z);
    } //	setXYZ

} //	MLocator
