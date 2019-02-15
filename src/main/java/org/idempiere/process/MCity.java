package org.idempiere.process;

import org.compiere.crm.MRegion;
import org.compiere.model.I_C_City;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.Collator;
import java.util.Comparator;
import java.util.Properties;

public class MCity extends X_C_City implements Comparator<Object>, Serializable {
  /** */
  private static final long serialVersionUID = -8905525315954621942L;

  /**
   * Get City (cached)
   *
   * @param ctx context
   * @param C_City_ID ID
   * @return City
   */
  public static MCity get(Properties ctx, int C_City_ID) {
    Integer key = new Integer(C_City_ID);
    MCity r = s_Cities.get(key);
    if (r != null) return r;
    r = new MCity(ctx, C_City_ID);
    if (r.getC_City_ID() == C_City_ID) {
      s_Cities.put(key, r);
      return r;
    }
    return null;
  } //	get

  /** City Cache */
  private static CCache<Integer, MCity> s_Cities =
      new CCache<Integer, MCity>(I_C_City.Table_Name, 20);;
  /** Static Logger */
  @SuppressWarnings("unused")
  private static CLogger s_log = CLogger.getCLogger(MCity.class);

  /** Region Cache */

  /**
   * ************************************************************************ Create empty City
   *
   * @param ctx context
   * @param C_City_ID id
   * @param trxName transaction
   */
  public MCity(Properties ctx, int C_City_ID) {
    super(ctx, C_City_ID);
    if (C_City_ID == 0) {}
  } //  MCity

  /**
   * Create City from current row in ResultSet
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MCity(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MCity

  /**
   * Parent Constructor
   *
   * @param Region Region
   * @param CityName City Name
   */
  public MCity(MRegion region, String cityName) {
    super(region.getCtx(), 0);
    setC_Region_ID(region.getC_Region_ID());
    setName(cityName);
  } //  MCity

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
