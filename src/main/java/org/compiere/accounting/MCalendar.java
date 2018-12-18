package org.compiere.accounting;

import org.compiere.crm.MClientInfo;
import org.compiere.model.I_C_Calendar;
import org.compiere.orm.MClient;
import org.compiere.util.Msg;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.Env;

import java.sql.ResultSet;
import java.util.Locale;
import java.util.Properties;

/**
 * Calendar Model
 *
 * @author Jorg Janke
 * @version $Id: MCalendar.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MCalendar extends X_C_Calendar {
  /** */
  private static final long serialVersionUID = 7721451326626542420L;

  /**
   * Get MCalendar from Cache
   *
   * @param ctx context
   * @param C_Calendar_ID id
   * @return MCalendar
   */
  public static MCalendar get(Properties ctx, int C_Calendar_ID) {
    Integer key = new Integer(C_Calendar_ID);
    MCalendar retValue = (MCalendar) s_cache.get(key);
    if (retValue != null) return retValue;
    retValue = new MCalendar(ctx, C_Calendar_ID, null);
    if (retValue.getId() != 0) s_cache.put(key, retValue);
    return retValue;
  } //	get

  /**
   * Get Default Calendar for Client
   *
   * @param ctx context
   * @param AD_Client_ID id
   * @return MCalendar
   */
  public static MCalendar getDefault(Properties ctx, int AD_Client_ID) {
    MClientInfo info = MClientInfo.get(ctx, AD_Client_ID);
    return get(ctx, info.getC_Calendar_ID());
  } //	getDefault

  /**
   * Get Default Calendar for Client
   *
   * @param ctx context
   * @return MCalendar
   */
  public static MCalendar getDefault(Properties ctx) {
    return getDefault(ctx, Env.getClientId(ctx));
  } //	getDefault

  /** Cache */
  private static CCache<Integer, MCalendar> s_cache =
      new CCache<Integer, MCalendar>(I_C_Calendar.Table_Name, 20);

  /**
   * *********************************************************************** Standard Constructor
   *
   * @param ctx context
   * @param C_Calendar_ID id
   * @param trxName transaction
   */
  public MCalendar(Properties ctx, int C_Calendar_ID, String trxName) {
    super(ctx, C_Calendar_ID, trxName);
  } //	MCalendar

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MCalendar(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MCalendar

  /**
   * Parent Constructor
   *
   * @param client parent
   */
  public MCalendar(MClient client) {
    super(client.getCtx(), 0, null);
    setClientOrg(client);
    StringBuilder msgset =
        new StringBuilder()
            .append(client.getName())
            .append(" ")
            .append(Msg.translate(client.getCtx(), "C_Calendar_ID"));
    setName(msgset.toString());
  } //	MCalendar

  /**
   * Create (current) Calendar Year
   *
   * @param locale locale
   * @return The Year
   */
  public MYear createYear(Locale locale) {
    if (getId() == 0) return null;
    MYear year = new MYear(this);
    year.saveEx();
    year.createStdPeriods(locale);
    //
    return year;
  } //	createYear
} //	MCalendar
