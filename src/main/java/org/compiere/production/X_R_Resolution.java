package org.compiere.production;

import org.compiere.model.I_R_Resolution;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for R_Resolution
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_Resolution extends BasePOName implements I_R_Resolution, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_Resolution(Properties ctx, int R_Resolution_ID) {
    super(ctx, R_Resolution_ID);
    /** if (R_Resolution_ID == 0) { setName (null); setR_Resolution_ID (0); } */
  }

  /** Load Constructor */
  public X_R_Resolution(Properties ctx, ResultSet rs) {
    super(ctx, rs);
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
    StringBuffer sb = new StringBuffer("X_R_Resolution[").append(getId()).append("]");
    return sb.toString();
  }

    @Override
  public int getTableId() {
    return I_R_Resolution.Table_ID;
  }
}
