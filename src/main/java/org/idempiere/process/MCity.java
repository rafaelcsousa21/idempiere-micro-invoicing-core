package org.idempiere.process;

import kotliquery.Row;
import org.compiere.crm.MRegion;
import org.compiere.model.I_C_City;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;

public class MCity extends X_C_City implements Comparator<Object>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8905525315954621942L;
    /**
     * City Cache
     */
    private static CCache<Integer, MCity> s_Cities =
            new CCache<Integer, MCity>(I_C_City.Table_Name, 20);
    /**
     * Static Logger
     */
    @SuppressWarnings("unused")
    private static CLogger s_log = CLogger.getCLogger(MCity.class);

    /**
     * ************************************************************************ Create empty City
     *
     * @param C_City_ID id
     */
    public MCity(int C_City_ID) {
        super(C_City_ID);
    } //  MCity

    /**
     * Create City from current row in ResultSet
     */
    public MCity(Row row) {
        super(row);
    } //	MCity

    /**
     * Parent Constructor
     */
    public MCity(MRegion region, String cityName) {
        super(0);
        setRegionId(region.getRegionId());
        setName(cityName);
    } //  MCity

    /**
     * Get City (cached)
     *
     * @param C_City_ID ID
     * @return City
     */
    public static MCity get(int C_City_ID) {
        Integer key = C_City_ID;
        MCity r = s_Cities.get(key);
        if (r != null) return r;
        r = new MCity(C_City_ID);
        if (r.getCityId() == C_City_ID) {
            s_Cities.put(key, r);
            return r;
        }
        return null;
    } //	get

    /**
     * Return Name
     *
     * @return Name
     */
    public String toString() {
        return getName();
    } //  toString

    /**
     * Compare
     *
     * @param o1 object 1
     * @param o2 object 2
     * @return -1,0, 1
     */
    public int compare(Object o1, Object o2) {
        String s1 = o1.toString();
        if (s1 == null) s1 = "";
        String s2 = o2.toString();
        if (s2 == null) s2 = "";
        Collator collator = Collator.getInstance();
        return collator.compare(s1, s2);
    } //	compare
} //	MCity
