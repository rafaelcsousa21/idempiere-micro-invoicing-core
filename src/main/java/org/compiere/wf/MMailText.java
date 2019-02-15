package org.compiere.wf;

import org.compiere.crm.MBPartner;
import org.compiere.crm.MUser;
import org.compiere.model.I_R_MailText;
import org.compiere.orm.MColumn;
import org.compiere.orm.MSequence;
import org.compiere.orm.PO;
import org.compiere.util.DisplayType;
import org.compiere.util.Msg;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Request Mail Template Model. Cannot be cached as it holds PO/BPartner/User to parse
 *
 * @author Jorg Janke
 * @version $Id: MMailText.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MMailText extends X_R_MailText {
  /** */
  private static final long serialVersionUID = -5088779317275846829L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param R_MailText_ID id
   * @param trxName transaction
   */
  public MMailText(Properties ctx, int R_MailText_ID) {
    super(ctx, R_MailText_ID);
  } //	MMailText

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MMailText(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MMailText

  /** Parse User */
  private MUser m_user = null;
  /** Parse BPartner */
  private MBPartner m_bpartner = null;
  /** Parse PO */
  private org.compiere.orm.PO m_po = null;
  /** Translated Header */
  private String m_MailHeader = null;
  /** Translated Text */
  private String m_MailText = null;
  /** Translated Text 2 */
  private String m_MailText2 = null;
  /** Translated Text 3 */
  private String m_MailText3 = null;
  /** Translation Cache */
  private static CCache<String, MMailTextTrl> s_cacheTrl =
      new CCache<String, MMailTextTrl>(I_R_MailText.Table_Name, 20);

  private String m_language = null;

  /**
   * Get parsed/translated Mail Text
   *
   * @param all concatinate all
   * @return parsed/translated text
   */
  public String getMailText(boolean all) {
    if (m_MailText == null) translate();
    if (!all) return parse(m_MailText);
    //
    StringBuilder sb = new StringBuilder();
    sb.append(m_MailText);
    String s = m_MailText2;
    if (s != null && s.length() > 0) sb.append("\n").append(s);
    s = m_MailText3;
    if (s != null && s.length() > 0) sb.append("\n").append(s);
    //
    return parse(sb.toString());
  } //	getMailText

  /**
   * Get parsed/translated Mail Text
   *
   * @return parsed/translated text
   */
  public String getMailText() {
    if (m_MailText == null) translate();
    return parse(m_MailText);
  } //	getMailText

  /**
   * Get parsed/translated Mail Text 2
   *
   * @return parsed/translated text
   */
  public String getMailText2() {
    if (m_MailText == null) translate();
    return parse(m_MailText2);
  } //	getMailText2

  /**
   * Get parsed/translated Mail Text 2
   *
   * @return parsed/translated text
   */
  public String getMailText3() {
    if (m_MailText == null) translate();
    return parse(m_MailText3);
  } //	getMailText3

  /**
   * Get parsed/translated Mail Header
   *
   * @return parsed/translated text
   */
  public String getMailHeader() {
    if (m_MailHeader == null) translate();
    return parse(m_MailHeader);
  } //	getMailHeader

  /**
   * ************************************************************************ Parse Text
   *
   * @param text text
   * @return parsed text
   */
  private String parse(String text) {
    if (Util.isEmpty(text) || text.indexOf('@') == -1) return text;
    //	Parse User
    text = parse(text, m_user);
    //	Parse BP
    text = parse(text, m_bpartner);
    //	Parse PO
    text = parse(text, m_po);
    //
    return text;
  } //	parse

  /**
   * Parse text
   *
   * @param text text
   * @param po object
   * @return parsed text
   */
  private String parse(String text, org.compiere.orm.PO po) {
    if (po == null || Util.isEmpty(text) || text.indexOf('@') == -1) return text;

    String inStr = text;
    String token;
    StringBuilder outStr = new StringBuilder();

    int i = inStr.indexOf('@');
    while (i != -1) {
      outStr.append(inStr.substring(0, i)); // up to @
      inStr = inStr.substring(i + 1, inStr.length()); // from first @

      int j = inStr.indexOf('@'); // next @
      if (j < 0) // no second tag
      {
        inStr = "@" + inStr;
        break;
      }

      token = inStr.substring(0, j);
      outStr.append(parseVariable(token, po)); // replace context

      inStr = inStr.substring(j + 1, inStr.length()); // from second @
      i = inStr.indexOf('@');
    }

    outStr.append(inStr); // 	add remainder
    return outStr.toString();
  } //	parse

  /**
   * Parse Variable
   *
   * @param variable variable
   * @param po po
   * @return translated variable or if not found the original tag
   */
  private String parseVariable(String variable, org.compiere.orm.PO po) {
    if (variable.contains("<") && variable.contains(">")) { // IDEMPIERE-3096
      return MSequence.parseVariable("@" + variable + "@", po, null, true);
    }
    // special default formatting cases for dates/times/boolean in mail text not covered by
    // MSequence.parseVariable
    int index = po.get_ColumnIndex(variable);
    if (index == -1) {
      StringBuilder msgreturn = new StringBuilder("@").append(variable).append("@");
      return msgreturn.toString(); // 	keep for next
    }
    //
    MColumn col = MColumn.get(Env.getCtx(), po.get_TableName(), variable);
    Object value = null;
    if (col != null && col.isSecure()) {
      value = "********";
    } else if (col.getReferenceId() == DisplayType.Date
        || col.getReferenceId() == DisplayType.DateTime
        || col.getReferenceId() == DisplayType.Time) {
      SimpleDateFormat sdf = DisplayType.getDateFormat(col.getReferenceId());
      value = sdf.format(po.get_Value(index));
    } else if (col.getReferenceId() == DisplayType.YesNo) {
      if (po.get_ValueAsBoolean(variable)) value = Msg.getMsg(Env.getCtx(), "Yes");
      else value = Msg.getMsg(Env.getCtx(), "No");
    } else {
      value = po.get_Value(index);
    }
    if (value == null) return "";
    return value.toString();
  } //	translate

  /**
   * Set User for parse
   *
   * @param AD_User_ID user
   */
  public void setUser(int AD_User_ID) {
    m_user = MUser.get(getCtx(), AD_User_ID);
  } //	setUser

  /**
   * Set User for parse
   *
   * @param user user
   */
  public void setUser(MUser user) {
    m_user = user;
  } //	setUser

  /**
   * Set BPartner for parse
   *
   * @param C_BPartner_ID bp
   */
  public void setBPartner(int C_BPartner_ID) {
    m_bpartner = new MBPartner(getCtx(), C_BPartner_ID);
  } //	setBPartner

    /**
   * Set PO for parse
   *
   * @param po po
   */
  /*public void setPO (PO po)
  {
  	m_po = po;
  }	//	setPO

  public void setPO (org.compiere.orm.PO po)
  {
  	m_po = po;
  }	//	setPO*/

  /**
   * Set PO for parse
   *
   * @param po po
   * @param analyse if set to true, search for BPartner/User
   */
  public void setPO(PO po, boolean analyse) {
    m_po = po;
    if (analyse) {
      int index = po.get_ColumnIndex("C_BPartner_ID");
      if (index > 0) {
        Object oo = po.get_Value(index);
        if (oo instanceof Integer) {
          int C_BPartner_ID = ((Integer) oo).intValue();
          setBPartner(C_BPartner_ID);
        }
      }
      index = po.get_ColumnIndex("AD_User_ID");
      if (index > 0) {
        Object oo = po.get_Value(index);
        if (oo instanceof Integer) {
          int AD_User_ID = ((Integer) oo).intValue();
          setUser(AD_User_ID);
        }
      }
    }
  } //	setPO

  /** Translate to BPartner Language */
  private void translate() {
    //	Default if no Translation
    m_MailHeader = super.getMailHeader();
    m_MailText = super.getMailText();
    m_MailText2 = super.getMailText2();
    m_MailText3 = super.getMailText3();
    if ((m_bpartner != null && m_bpartner.getADLanguage() != null) || !Util.isEmpty(m_language)) {
      String adLanguage = m_bpartner != null ? m_bpartner.getADLanguage() : m_language;
      StringBuilder key = new StringBuilder().append(adLanguage).append(getId());
      MMailTextTrl trl = s_cacheTrl.get(key.toString());
      if (trl == null) {
        trl = getTranslation(adLanguage);
        if (trl != null) s_cacheTrl.put(key.toString(), trl);
      }
      if (trl != null) {
        m_MailHeader = trl.MailHeader;
        m_MailText = trl.MailText;
        m_MailText2 = trl.MailText2;
        m_MailText3 = trl.MailText3;
      }
    }
  } //	translate

  /**
   * Get Translation
   *
   * @param AD_Language language
   * @return trl
   */
  private MMailTextTrl getTranslation(String AD_Language) {
    MMailTextTrl trl = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "SELECT * FROM R_MailText_Trl WHERE R_MailText_ID=? AND AD_Language=?";
    try {
      pstmt = prepareStatement(sql);
      pstmt.setInt(1, getR_MailText_ID());
      pstmt.setString(2, AD_Language);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        trl = new MMailTextTrl();
        trl.AD_Language = rs.getString("AD_Language");
        trl.MailHeader = rs.getString("MailHeader");
        trl.MailText = rs.getString("MailText");
        trl.MailText2 = rs.getString("MailText2");
        trl.MailText3 = rs.getString("MailText3");
      }
    } catch (Exception e) {
      log.log(Level.SEVERE, sql, e);
    } finally {

      rs = null;
      pstmt = null;
    }

    return trl;
  } //	getTranslation

  /** MailText Translation VO */
  static class MMailTextTrl {
      /** Language */
      String AD_Language = null;
      /** Translated Header */
    String MailHeader = null;
    /** Translated Text */
    String MailText = null;
    /** Translated Text 2 */
    String MailText2 = null;
    /** Translated Text 3 */
    String MailText3 = null;
  } //	MMailTextTrl

  public void setLanguage(String language) {
    m_language = language;
  }

} //	MMailText
