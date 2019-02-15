package org.compiere.wf;

import org.compiere.model.I_AD_Message;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;


/**
 * Message Model
 *
 * @author Jorg Janke
 * @version $Id: MMessage.java,v 1.3 2006/07/30 00:54:54 jjanke Exp $
 */
public class MMessage extends X_AD_Message {
  /** */
  private static final long serialVersionUID = -7362947218094337783L;

  /**
   * Get Message (cached)
   *
   * @param ctx context
   * @param Value message value
   * @return message
   */
  public static MMessage get(Properties ctx, String Value) {
    if (Value == null || Value.length() == 0) return null;
    MMessage retValue = (MMessage) s_cache.get(Value);
    //
    if (retValue == null) {
      String sql = "SELECT * FROM AD_Message WHERE Value=?";
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        pstmt = prepareStatement(sql);
        pstmt.setString(1, Value);
        rs = pstmt.executeQuery();
        if (rs.next()) retValue = new MMessage(ctx, rs);
      } catch (Exception e) {
        s_log.log(Level.SEVERE, "get", e);
      } finally {

        rs = null;
        pstmt = null;
      }
      if (retValue != null) s_cache.put(Value, retValue);
    }
    return retValue;
  } //	get

  /**
   * Get Message (cached)
   *
   * @param ctx context
   * @param AD_Message_ID id
   * @return message
   */
  public static MMessage get(Properties ctx, int AD_Message_ID) {
    String key = String.valueOf(AD_Message_ID);
    MMessage retValue = (MMessage) s_cache.get(key);
    if (retValue == null) {
      retValue = new MMessage(ctx, AD_Message_ID);
      s_cache.put(key, retValue);
    }
    return retValue;
  } //	get

  /**
   * Get Message ID (cached)
   *
   * @param ctx context
   * @param Value message value
   * @return AD_Message_ID
   */
  public static int getAD_Message_ID(Properties ctx, String Value) {
    MMessage msg = get(ctx, Value);
    if (msg == null) return 0;
    return msg.getAD_Message_ID();
  } //	getAD_Message_ID

  /** Cache */
  private static CCache<String, MMessage> s_cache =
      new CCache<String, MMessage>(I_AD_Message.Table_Name, 100);
  /** Static Logger */
  private static CLogger s_log = CLogger.getCLogger(MMessage.class);

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param AD_Message_ID id
   * @param trxName transaction
   */
  public MMessage(Properties ctx, int AD_Message_ID) {
    super(ctx, AD_Message_ID);
  } //	MMessage

  /**
   * Load Cosntructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MMessage(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MMessage
} //	MMessage
