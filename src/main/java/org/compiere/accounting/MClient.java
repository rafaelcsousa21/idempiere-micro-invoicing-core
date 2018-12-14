package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_User;
import org.compiere.model.Server;
import org.compiere.orm.MSysConfig;
import org.compiere.orm.MTree_Base;
import org.compiere.orm.Query;
import org.compiere.orm.X_AD_Tree;
import org.idempiere.common.base.Service;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Language;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.*;

/**
 * Client Model
 *
 * @author Jorg Janke
 * @version $Id: MClient.java,v 1.2 2006/07/30 00:58:37 jjanke Exp $
 * @author Carlos Ruiz - globalqss integrate bug fix reported by Teo Sarca [ 1619085 ] Client setup
 *     creates duplicate trees
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *     <li>BF [ 1886480 ] Print Format Item Trl not updated even if not multilingual
 */
public class MClient extends org.compiere.orm.MClient {
  protected static CCache<Integer, org.compiere.orm.MClient> s_cache =
      new CCache<Integer, org.compiere.orm.MClient>(Table_Name, 3, 120, true);
  /** */
  private static final long serialVersionUID = -4420908648355523008L;

  public MClient(Properties ctx, int ad_client_id, String trxName) {
    super(ctx, ad_client_id, trxName);
  }

  public MClient(Properties ctx, int AD_Client_ID, boolean createNew, String trxName) {
    super(ctx, AD_Client_ID, createNew, trxName);
  }

  public MClient(Properties ctx, Row row) {
    super(ctx, row);
  } //	MClient

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
   * @param ctx context
   * @param order by clause
   * @return clients
   */
  public static MClient[] getAll(Properties ctx, String orderBy) {
    List<MClient> list =
        new Query(ctx, I_AD_Client.Table_Name, null, null).setOrderBy(orderBy).list();
    for (MClient client : list) {
      s_cache.put(new Integer(client. getClientId()), client);
    }
    MClient[] retValue = new MClient[list.size()];
    list.toArray(retValue);
    return retValue;
  } //	getAll

  /** Static Logger */
  @SuppressWarnings("unused")
  private static CLogger s_log = CLogger.getCLogger(MClient.class);

  /**
   * Do we have Multi-Lingual Documents. Set in loadOrgs
   *
   * @param ctx context
   * @return true if multi lingual documents
   */
  public static boolean isMultiLingualDocument(Properties ctx) {
    return MClient.get(ctx).isMultiLingualDocument();
  } //	isMultiLingualDocument

  /** Language */
  private Language m_language = null;
  /** Client Info Setup Tree for Account */
  private int m_AD_Tree_Account_ID;

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuilder sb =
        new StringBuilder("MClient[").append(getId()).append("-").append(getValue()).append("]");
    return sb.toString();
  } //	toString

  /**
   * Get Client Info
   *
   * @return Client Info
   */
  @Override
  public MClientInfo getInfo() {
    if (m_info == null)
      m_info = org.compiere.orm.MClientInfo.get(getCtx(),  getClientId(), get_TrxName());
    return (MClientInfo) m_info;
  } //	getMClientInfo

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
   * Set AD_Language
   *
   * @param AD_Language new language
   */
  public void setADLanguage(String AD_Language) {
    m_language = null;
    super.setADLanguage(AD_Language);
  } //	setADLanguage

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
   * ************************************************************************ Create Trees and Setup
   * Client Info
   *
   * @param language language
   * @return true if created
   */
  public boolean setupClientInfo(String language) {
    //	Create Trees
    StringBuilder sql = null;
    if (Env.isBaseLanguage(language, "AD_Ref_List")) // 	Get TreeTypes & Name
    sql =
          new StringBuilder(
              "SELECT Value, Name FROM AD_Ref_List WHERE AD_Reference_ID=120 AND IsActive='Y'");
    else
      sql =
          new StringBuilder("SELECT l.Value, t.Name FROM AD_Ref_List l, AD_Ref_List_Trl t ")
              .append(
                  "WHERE l.AD_Reference_ID=120 AND l.AD_Ref_List_ID=t.AD_Ref_List_ID AND l.IsActive='Y'")
              .append(" AND t.AD_Language=")
              .append(TO_STRING(language));

    //  Tree IDs
    int AD_Tree_Org_ID = 0,
        AD_Tree_BPartner_ID = 0,
        AD_Tree_Project_ID = 0,
        AD_Tree_SalesRegion_ID = 0,
        AD_Tree_Product_ID = 0,
        AD_Tree_Campaign_ID = 0,
        AD_Tree_Activity_ID = 0;

    boolean success = false;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = prepareStatement(sql.toString(), get_TrxName());
      rs = stmt.executeQuery();
      MTree_Base tree = null;
      while (rs.next()) {
        String value = rs.getString(1);
        StringBuilder name =
            new StringBuilder().append(getName()).append(" ").append(rs.getString(2));
        //
        if (value.equals(X_AD_Tree.TREETYPE_Organization)) {
          tree = new MTree_Base(this, name.toString(), value);
          success = tree.save();
          AD_Tree_Org_ID = tree.getAD_Tree_ID();
        } else if (value.equals(X_AD_Tree.TREETYPE_BPartner)) {
          tree = new MTree_Base(this, name.toString(), value);
          success = tree.save();
          AD_Tree_BPartner_ID = tree.getAD_Tree_ID();
        } else if (value.equals(X_AD_Tree.TREETYPE_Project)) {
          tree = new MTree_Base(this, name.toString(), value);
          success = tree.save();
          AD_Tree_Project_ID = tree.getAD_Tree_ID();
        } else if (value.equals(X_AD_Tree.TREETYPE_SalesRegion)) {
          tree = new MTree_Base(this, name.toString(), value);
          success = tree.save();
          AD_Tree_SalesRegion_ID = tree.getAD_Tree_ID();
        } else if (value.equals(X_AD_Tree.TREETYPE_Product)) {
          tree = new MTree_Base(this, name.toString(), value);
          success = tree.save();
          AD_Tree_Product_ID = tree.getAD_Tree_ID();
        } else if (value.equals(X_AD_Tree.TREETYPE_ElementValue)) {
          tree = new MTree_Base(this, name.toString(), value);
          success = tree.save();
          m_AD_Tree_Account_ID = tree.getAD_Tree_ID();
        } else if (value.equals(X_AD_Tree.TREETYPE_Campaign)) {
          tree = new MTree_Base(this, name.toString(), value);
          success = tree.save();
          AD_Tree_Campaign_ID = tree.getAD_Tree_ID();
        } else if (value.equals(X_AD_Tree.TREETYPE_Activity)) {
          tree = new MTree_Base(this, name.toString(), value);
          success = tree.save();
          AD_Tree_Activity_ID = tree.getAD_Tree_ID();
        } else if (value.equals(X_AD_Tree.TREETYPE_Menu) // 	No Menu
            || value.equals(X_AD_Tree.TREETYPE_CustomTable) // 	No Custom Table
            || value.equals(X_AD_Tree.TREETYPE_User1) // 	No custom user trees
            || value.equals(X_AD_Tree.TREETYPE_User2)
            || value.equals(X_AD_Tree.TREETYPE_User3)
            || value.equals(X_AD_Tree.TREETYPE_User4)) success = true;
        else //	PC (Product Category), BB (BOM)
        {
          tree = new MTree_Base(this, name.toString(), value);
          success = tree.save();
        }
        if (!success) {
          log.log(Level.SEVERE, "Tree NOT created: " + name);
          break;
        }
      }
    } catch (SQLException e1) {
      log.log(Level.SEVERE, "Trees", e1);
      success = false;
    } finally {
      close(rs, stmt);
      rs = null;
      stmt = null;
    }

    if (!success) return false;

    //	Create ClientInfo
    MClientInfo clientInfo =
        new MClientInfo(
            this,
            AD_Tree_Org_ID,
            AD_Tree_BPartner_ID,
            AD_Tree_Project_ID,
            AD_Tree_SalesRegion_ID,
            AD_Tree_Product_ID,
            AD_Tree_Campaign_ID,
            AD_Tree_Activity_ID,
            get_TrxName());
    success = clientInfo.save();
    return success;
  } //	createTrees

  /**
   * Get AD_Tree_Account_ID created in setup client info
   *
   * @return Account Tree ID
   */
  public int getSetup_AD_Tree_Account_ID() {
    return m_AD_Tree_Account_ID;
  } //	getSetup_AD_Tree_Account_ID

  /**
   * Is Auto Archive on
   *
   * @return true if auto archive
   */
  public boolean isAutoArchive() {
    String aa = getAutoArchive();
    return aa != null && !aa.equals(AUTOARCHIVE_None);
  } //	isAutoArchive

  /**
   * Get Primary Accounting Schema
   *
   * @return Acct Schema or null
   */
  public MAcctSchema getAcctSchema() {
    if (m_info == null) m_info = MClientInfo.get(getCtx(),  getClientId(), get_TrxName());
    if (m_info != null) {
      int C_AcctSchema_ID = m_info.getC_AcctSchema1_ID();
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

  private static Server m_server;

  /**
   * Get Server
   *
   * @return Server
   */
  public static Server getServer() {
    if (m_server == null) {
      m_server = Service.Companion.locator().locate(Server.class).getService();
    }
    return m_server;
  } //	getServer

  public static boolean isClientAccounting() {
    String ca =
        MSysConfig.getValue(
            MSysConfig.CLIENT_ACCOUNTING,
            CLIENT_ACCOUNTING_QUEUE, // default
            Env.getClientId(Env.getCtx()));
    return (ca.equalsIgnoreCase(CLIENT_ACCOUNTING_IMMEDIATE)
        || ca.equalsIgnoreCase(CLIENT_ACCOUNTING_QUEUE));
  }

  public static boolean isClientAccountingQueue() {
    String ca =
        MSysConfig.getValue(
            MSysConfig.CLIENT_ACCOUNTING,
            CLIENT_ACCOUNTING_QUEUE, // default
            Env.getClientId(Env.getCtx()));
    return ca.equalsIgnoreCase(CLIENT_ACCOUNTING_QUEUE);
  }

  /*  2870483 - SaaS too slow opening windows */
  /** Field Access */
  private ArrayList<Integer> m_fieldAccess = null;
  /**
   * Define is a field is displayed based on ASP rules
   *
   * @param ad_field_id
   * @return boolean indicating if it's displayed or not
   */
  public boolean isDisplayField(int aDFieldID) {
    if (!isUseASP()) return true;

    if (m_fieldAccess == null) {
      m_fieldAccess = new ArrayList<Integer>(11000);
      StringBuilder sqlvalidate =
          new StringBuilder("SELECT AD_Field_ID ")
              .append("  FROM AD_Field ")
              .append(" WHERE (   AD_Field_ID NOT IN ( ")
              // ASP subscribed fields for client)
              .append("              SELECT f.AD_Field_ID ")
              .append(
                  "                FROM ASP_Field f, ASP_Tab t, ASP_Window w, ASP_Level l, ASP_ClientLevel cl ")
              .append("               WHERE w.ASP_Level_ID = l.ASP_Level_ID ")
              .append("                 AND cl.AD_Client_ID = ")
              .append( getClientId())
              .append("                 AND cl.ASP_Level_ID = l.ASP_Level_ID ")
              .append("                 AND f.ASP_Tab_ID = t.ASP_Tab_ID ")
              .append("                 AND t.ASP_Window_ID = w.ASP_Window_ID ")
              .append("                 AND f.IsActive = 'Y' ")
              .append("                 AND t.IsActive = 'Y' ")
              .append("                 AND w.IsActive = 'Y' ")
              .append("                 AND l.IsActive = 'Y' ")
              .append("                 AND cl.IsActive = 'Y' ")
              .append("                 AND f.ASP_Status = 'H' ")
              .append("                  AND f.AD_Field_ID NOT IN (")
              .append(" 				 SELECT AD_Field_ID")
              .append(" 				 FROM ASP_ClientException ce")
              .append(" 				 WHERE ce.AD_Client_ID =")
              .append( getClientId())
              .append(" 				 AND ce.IsActive = 'Y'")
              .append("                  AND ce.AD_Field_ID IS NOT NULL")
              .append(" 				 AND ce.ASP_Status <> 'H')")
              .append("   UNION ALL ")
              // minus ASP hide exceptions for client
              .append("          SELECT AD_Field_ID ")
              .append("            FROM ASP_ClientException ce ")
              .append("           WHERE ce.AD_Client_ID = ")
              .append( getClientId())
              .append("             AND ce.IsActive = 'Y' ")
              .append("             AND ce.AD_Field_ID IS NOT NULL ")
              .append("             AND ce.ASP_Status = 'H'))")
              .append(" ORDER BY AD_Field_ID");
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        pstmt = prepareStatement(sqlvalidate.toString(), get_TrxName());
        rs = pstmt.executeQuery();
        while (rs.next()) m_fieldAccess.add(rs.getInt(1));
      } catch (Exception e) {
        log.log(Level.SEVERE, sqlvalidate.toString(), e);
      } finally {
        close(rs, pstmt);
      }
    }
    return (Collections.binarySearch(m_fieldAccess, aDFieldID) > 0);
  }

  @Override
  public String getRequestUser() {
    // IDEMPIERE-722
    if ( getClientId() != 0 && isSendCredentialsSystem()) {
      MClient sysclient = MClient.get(getCtx(), 0);
      return sysclient.getRequestUser();
    }
    return super.getRequestUser();
  }

  @Override
  public String getRequestUserPW() {
    // IDEMPIERE-722
    if ( getClientId() != 0 && isSendCredentialsSystem()) {
      MClient sysclient = MClient.get(getCtx(), 0);
      return sysclient.getRequestUserPW();
    }
    return super.getRequestUserPW();
  }

  @Override
  public boolean isSmtpAuthorization() {
    // IDEMPIERE-722
    if ( getClientId() != 0 && isSendCredentialsSystem()) {
      MClient sysclient = MClient.get(getCtx(), 0);
      return sysclient.isSmtpAuthorization();
    }
    return super.isSmtpAuthorization();
  }

  @Override
  public int getSMTPPort() {
    // IDEMPIERE-722
    if ( getClientId() != 0 && isSendCredentialsSystem()) {
      MClient sysclient = MClient.get(getCtx(), 0);
      return sysclient.getSMTPPort();
    }
    return super.getSMTPPort();
  }

  @Override
  public boolean isSecureSMTP() {
    // IDEMPIERE-722
    if ( getClientId() != 0 && isSendCredentialsSystem()) {
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
    if ( getClientId() != 0 && isSendCredentialsSystem()) {
      MClient sysclient = MClient.get(getCtx(), 0);
      s = sysclient.getSMTPHost();
    } else {
      s = super.getSMTPHost();
    }
    if (s == null) s = "localhost";
    return s;
  } //	getSMTPHost

  // IDEMPIERE-722
  private static final String MAIL_SEND_CREDENTIALS_USER = "U";
  private static final String MAIL_SEND_CREDENTIALS_CLIENT = "C";
  private static final String MAIL_SEND_CREDENTIALS_SYSTEM = "S";

  public static boolean isSendCredentialsClient() {
    String msc =
        MSysConfig.getValue(
            MSysConfig.MAIL_SEND_CREDENTIALS,
            MAIL_SEND_CREDENTIALS_USER, // default
            Env.getClientId(Env.getCtx()));
    return (MAIL_SEND_CREDENTIALS_CLIENT.equalsIgnoreCase(msc));
  }

  public static boolean isSendCredentialsSystem() {
    String msc =
        MSysConfig.getValue(
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
   * @param ctx context
   * @param AD_Client_ID id
   * @return client
   */
  public static MClient get(Properties ctx, int AD_Client_ID) {
    Integer key = AD_Client_ID;
    MClient client = (MClient) s_cache.get(key);
    if (client != null) return client;
    client = new MClient(ctx, AD_Client_ID, null);
    s_cache.put(key, client);
    return client;
  } //	get

  public MClient(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * Get Default Accounting Currency
   *
   * @return currency or 0
   */
  public int getC_Currency_ID() {
    if (m_info == null) getInfo();
    if (m_info != null) return getInfo().getC_Currency_ID();
    return 0;
  } //	getC_Currency_ID

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
          List<File> fileList) {}

} //	MClient
