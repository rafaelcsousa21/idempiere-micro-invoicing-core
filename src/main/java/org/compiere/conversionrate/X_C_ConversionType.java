package org.compiere.conversionrate;

import org.compiere.model.I_C_ConversionType;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_ConversionType
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_ConversionType extends BasePONameValue
    implements I_C_ConversionType, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_ConversionType(Properties ctx, int C_ConversionType_ID, String trxName) {
    super(ctx, C_ConversionType_ID, trxName);
  }

  /** Load Constructor */
  public X_C_ConversionType(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 6 - System - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_C_ConversionType[").append(getId()).append("]");
    return sb.toString();
  }

    @Override
  public int getTableId() {
    return I_C_ConversionType.Table_ID;
  }
}
