package org.compiere.wf;

import org.compiere.model.I_R_MailText;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for R_MailText
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_MailText extends BasePOName implements I_R_MailText, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_MailText(Properties ctx, int R_MailText_ID, String trxName) {
    super(ctx, R_MailText_ID, trxName);
  }

  /** Load Constructor */
  public X_R_MailText(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 7 - System - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_R_MailText[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set HTML.
   *
   * @param IsHtml Text has HTML tags
   */
  public void setIsHtml(boolean IsHtml) {
    set_Value(COLUMNNAME_IsHtml, Boolean.valueOf(IsHtml));
  }

  /**
   * Get HTML.
   *
   * @return Text has HTML tags
   */
  public boolean isHtml() {
    Object oo = get_Value(COLUMNNAME_IsHtml);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Subject.
   *
   * @param MailHeader Mail Header (Subject)
   */
  public void setMailHeader(String MailHeader) {
    set_Value(COLUMNNAME_MailHeader, MailHeader);
  }

  /**
   * Get Subject.
   *
   * @return Mail Header (Subject)
   */
  public String getMailHeader() {
    return (String) get_Value(COLUMNNAME_MailHeader);
  }

  /**
   * Set Mail Text.
   *
   * @param MailText Text used for Mail message
   */
  public void setMailText(String MailText) {
    set_Value(COLUMNNAME_MailText, MailText);
  }

  /**
   * Get Mail Text.
   *
   * @return Text used for Mail message
   */
  public String getMailText() {
    return (String) get_Value(COLUMNNAME_MailText);
  }

  /**
   * Set Mail Text 2.
   *
   * @param MailText2 Optional second text part used for Mail message
   */
  public void setMailText2(String MailText2) {
    set_Value(COLUMNNAME_MailText2, MailText2);
  }

  /**
   * Get Mail Text 2.
   *
   * @return Optional second text part used for Mail message
   */
  public String getMailText2() {
    return (String) get_Value(COLUMNNAME_MailText2);
  }

  /**
   * Set Mail Text 3.
   *
   * @param MailText3 Optional third text part used for Mail message
   */
  public void setMailText3(String MailText3) {
    set_Value(COLUMNNAME_MailText3, MailText3);
  }

  /**
   * Get Mail Text 3.
   *
   * @return Optional third text part used for Mail message
   */
  public String getMailText3() {
    return (String) get_Value(COLUMNNAME_MailText3);
  }

  /**
   * Set Mail Template.
   *
   * @param R_MailText_ID Text templates for mailings
   */
  public void setR_MailText_ID(int R_MailText_ID) {
    if (R_MailText_ID < 1) set_ValueNoCheck(COLUMNNAME_R_MailText_ID, null);
    else set_ValueNoCheck(COLUMNNAME_R_MailText_ID, Integer.valueOf(R_MailText_ID));
  }

  /**
   * Get Mail Template.
   *
   * @return Text templates for mailings
   */
  public int getR_MailText_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_MailText_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set R_MailText_UU.
   *
   * @param R_MailText_UU R_MailText_UU
   */
  public void setR_MailText_UU(String R_MailText_UU) {
    set_Value(COLUMNNAME_R_MailText_UU, R_MailText_UU);
  }

  /**
   * Get R_MailText_UU.
   *
   * @return R_MailText_UU
   */
  public String getR_MailText_UU() {
    return (String) get_Value(COLUMNNAME_R_MailText_UU);
  }

  @Override
  public int getTableId() {
    return I_R_MailText.Table_ID;
  }
}
