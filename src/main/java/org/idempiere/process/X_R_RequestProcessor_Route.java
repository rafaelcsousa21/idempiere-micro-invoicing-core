package org.idempiere.process;

import org.compiere.model.I_R_RequestProcessor_Route;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

public class X_R_RequestProcessor_Route extends PO
    implements I_R_RequestProcessor_Route, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_RequestProcessor_Route(
      Properties ctx, int R_RequestProcessor_Route_ID, String trxName) {
    super(ctx, R_RequestProcessor_Route_ID, trxName);
    /**
     * if (R_RequestProcessor_Route_ID == 0) { setAD_User_ID (0); setR_RequestProcessor_ID (0);
     * setR_RequestProcessor_Route_ID (0); setSeqNo (0); }
     */
  }

  /** Load Constructor */
  public X_R_RequestProcessor_Route(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 2 - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  @Override
  public int getTableId() {
    return Table_ID;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_R_RequestProcessor_Route[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_AD_User getAD_User() throws RuntimeException {
    return (org.compiere.model.I_AD_User)
        MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
            .getPO(getAD_User_ID(), null);
  }

  /**
   * Set User/Contact.
   *
   * @param AD_User_ID User within the system - Internal or Business Partner Contact
   */
  public void setAD_User_ID(int AD_User_ID) {
    if (AD_User_ID < 1) set_Value(COLUMNNAME_AD_User_ID, null);
    else set_Value(COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
  }

  /**
   * Get User/Contact.
   *
   * @return User within the system - Internal or Business Partner Contact
   */
  public int getAD_User_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_User_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Keyword.
   *
   * @param Keyword Case insensitive keyword
   */
  public void setKeyword(String Keyword) {
    set_Value(COLUMNNAME_Keyword, Keyword);
  }

  /**
   * Get Keyword.
   *
   * @return Case insensitive keyword
   */
  public String getKeyword() {
    return (String) get_Value(COLUMNNAME_Keyword);
  }

  public org.compiere.model.I_R_RequestProcessor getR_RequestProcessor() throws RuntimeException {
    return (org.compiere.model.I_R_RequestProcessor)
        MTable.get(getCtx(), org.compiere.model.I_R_RequestProcessor.Table_Name)
            .getPO(getR_RequestProcessor_ID(), null);
  }

  /**
   * Set Request Processor.
   *
   * @param R_RequestProcessor_ID Processor for Requests
   */
  public void setR_RequestProcessor_ID(int R_RequestProcessor_ID) {
    if (R_RequestProcessor_ID < 1) set_ValueNoCheck(COLUMNNAME_R_RequestProcessor_ID, null);
    else set_ValueNoCheck(COLUMNNAME_R_RequestProcessor_ID, Integer.valueOf(R_RequestProcessor_ID));
  }

  /**
   * Get Request Processor.
   *
   * @return Processor for Requests
   */
  public int getR_RequestProcessor_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_RequestProcessor_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Request Routing.
   *
   * @param R_RequestProcessor_Route_ID Automatic routing of requests
   */
  public void setR_RequestProcessor_Route_ID(int R_RequestProcessor_Route_ID) {
    if (R_RequestProcessor_Route_ID < 1)
      set_ValueNoCheck(COLUMNNAME_R_RequestProcessor_Route_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_R_RequestProcessor_Route_ID, Integer.valueOf(R_RequestProcessor_Route_ID));
  }

  /**
   * Get Request Routing.
   *
   * @return Automatic routing of requests
   */
  public int getR_RequestProcessor_Route_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_RequestProcessor_Route_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set R_RequestProcessor_Route_UU.
   *
   * @param R_RequestProcessor_Route_UU R_RequestProcessor_Route_UU
   */
  public void setR_RequestProcessor_Route_UU(String R_RequestProcessor_Route_UU) {
    set_Value(COLUMNNAME_R_RequestProcessor_Route_UU, R_RequestProcessor_Route_UU);
  }

  /**
   * Get R_RequestProcessor_Route_UU.
   *
   * @return R_RequestProcessor_Route_UU
   */
  public String getR_RequestProcessor_Route_UU() {
    return (String) get_Value(COLUMNNAME_R_RequestProcessor_Route_UU);
  }

  public org.compiere.model.I_R_RequestType getR_RequestType() throws RuntimeException {
    return (org.compiere.model.I_R_RequestType)
        MTable.get(getCtx(), org.compiere.model.I_R_RequestType.Table_Name)
            .getPO(getR_RequestType_ID(), null);
  }

  /**
   * Set Request Type.
   *
   * @param R_RequestType_ID Type of request (e.g. Inquiry, Complaint, ..)
   */
  public void setR_RequestType_ID(int R_RequestType_ID) {
    if (R_RequestType_ID < 1) set_Value(COLUMNNAME_R_RequestType_ID, null);
    else set_Value(COLUMNNAME_R_RequestType_ID, Integer.valueOf(R_RequestType_ID));
  }

  /**
   * Get Request Type.
   *
   * @return Type of request (e.g. Inquiry, Complaint, ..)
   */
  public int getR_RequestType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_RequestType_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Sequence.
   *
   * @param SeqNo Method of ordering records; lowest number comes first
   */
  public void setSeqNo(int SeqNo) {
    set_Value(COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
  }

  /**
   * Get Sequence.
   *
   * @return Method of ordering records; lowest number comes first
   */
  public int getSeqNo() {
    Integer ii = (Integer) get_Value(COLUMNNAME_SeqNo);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), String.valueOf(getSeqNo()));
  }
}
