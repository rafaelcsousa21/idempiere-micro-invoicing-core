package org.compiere.invoicing;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_A_Asset_Type;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

/**
 * Generated Model for A_Asset_Type
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Asset_Type extends BasePONameValue implements I_A_Asset_Type, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_A_Asset_Type(Properties ctx, int A_Asset_Type_ID, String trxName) {
    super(ctx, A_Asset_Type_ID, trxName);
  }

  /** Load Constructor */
  public X_A_Asset_Type(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 3 - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  /** Load Meta Data */
  protected POInfo initPO(Properties ctx) {
    POInfo poi = POInfo.getPOInfo(ctx, Table_ID, get_TrxName());
    return poi;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_A_Asset_Type[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Asset Type.
   *
   * @param A_Asset_Type_ID Asset Type
   */
  public void setA_Asset_Type_ID(int A_Asset_Type_ID) {
    if (A_Asset_Type_ID < 1) set_ValueNoCheck(COLUMNNAME_A_Asset_Type_ID, null);
    else set_ValueNoCheck(COLUMNNAME_A_Asset_Type_ID, Integer.valueOf(A_Asset_Type_ID));
  }

  /**
   * Get Asset Type.
   *
   * @return Asset Type
   */
  public int getA_Asset_Type_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_Type_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set A_Asset_Type_UU.
   *
   * @param A_Asset_Type_UU A_Asset_Type_UU
   */
  public void setA_Asset_Type_UU(String A_Asset_Type_UU) {
    set_Value(COLUMNNAME_A_Asset_Type_UU, A_Asset_Type_UU);
  }

  /**
   * Get A_Asset_Type_UU.
   *
   * @return A_Asset_Type_UU
   */
  public String getA_Asset_Type_UU() {
    return (String) get_Value(COLUMNNAME_A_Asset_Type_UU);
  }

  /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(COLUMNNAME_Description, Description);
  }

  /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
  }

  /** IsDepreciable AD_Reference_ID=53365 */
  public static final int ISDEPRECIABLE_AD_Reference_ID = 53365;
  /** No = NX */
  public static final String ISDEPRECIABLE_No = "NX";
  /** - / Default No = XN */
  public static final String ISDEPRECIABLE__DefaultNo = "XN";
  /** - = XX */
  public static final String ISDEPRECIABLE__ = "XX";
  /** - / Default Yes = XY */
  public static final String ISDEPRECIABLE__DefaultYes = "XY";
  /** Yes = YX */
  public static final String ISDEPRECIABLE_Yes = "YX";
  /**
   * Set Is Depreciable.
   *
   * @param IsDepreciable This asset CAN be depreciated
   */
  public void setIsDepreciable(String IsDepreciable) {

    set_Value(COLUMNNAME_IsDepreciable, IsDepreciable);
  }

  /**
   * Get Is Depreciable.
   *
   * @return This asset CAN be depreciated
   */
  public String getIsDepreciable() {
    return (String) get_Value(COLUMNNAME_IsDepreciable);
  }

  /** IsInPosession AD_Reference_ID=53365 */
  public static final int ISINPOSESSION_AD_Reference_ID = 53365;
  /** No = NX */
  public static final String ISINPOSESSION_No = "NX";
  /** - / Default No = XN */
  public static final String ISINPOSESSION__DefaultNo = "XN";
  /** - = XX */
  public static final String ISINPOSESSION__ = "XX";
  /** - / Default Yes = XY */
  public static final String ISINPOSESSION__DefaultYes = "XY";
  /** Yes = YX */
  public static final String ISINPOSESSION_Yes = "YX";
  /**
   * Set In Possession.
   *
   * @param IsInPosession The asset is in the possession of the organization
   */
  public void setIsInPosession(String IsInPosession) {

    set_Value(COLUMNNAME_IsInPosession, IsInPosession);
  }

  /**
   * Get In Possession.
   *
   * @return The asset is in the possession of the organization
   */
  public String getIsInPosession() {
    return (String) get_Value(COLUMNNAME_IsInPosession);
  }

  /** IsOwned AD_Reference_ID=53365 */
  public static final int ISOWNED_AD_Reference_ID = 53365;
  /** No = NX */
  public static final String ISOWNED_No = "NX";
  /** - / Default No = XN */
  public static final String ISOWNED__DefaultNo = "XN";
  /** - = XX */
  public static final String ISOWNED__ = "XX";
  /** - / Default Yes = XY */
  public static final String ISOWNED__DefaultYes = "XY";
  /** Yes = YX */
  public static final String ISOWNED_Yes = "YX";
  /**
   * Set Owned.
   *
   * @param IsOwned The asset is owned by the organization
   */
  public void setIsOwned(String IsOwned) {

    set_Value(COLUMNNAME_IsOwned, IsOwned);
  }

  /**
   * Get Owned.
   *
   * @return The asset is owned by the organization
   */
  public String getIsOwned() {
    return (String) get_Value(COLUMNNAME_IsOwned);
  }
}
