package org.idempiere.process;

import org.compiere.model.I_R_RequestProcessor_Route;
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
