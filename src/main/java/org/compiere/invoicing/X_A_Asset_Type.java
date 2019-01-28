package org.compiere.invoicing;

import org.compiere.model.I_A_Asset_Type;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

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

  public String toString() {
    StringBuffer sb = new StringBuffer("X_A_Asset_Type[").append(getId()).append("]");
    return sb.toString();
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
   * Get Owned.
   *
   * @return The asset is owned by the organization
   */
  public String getIsOwned() {
    return (String) get_Value(COLUMNNAME_IsOwned);
  }

  @Override
  public int getTableId() {
    return I_A_Asset_Type.Table_ID;
  }
}
