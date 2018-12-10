package org.compiere.production;

import java.sql.ResultSet;
import java.util.Properties;
import org.idempiere.icommon.model.IPO;

public class MQualityTestResult extends X_M_QualityTestResult {

  /** */
  private static final long serialVersionUID = -5529865718027582930L;

  public MQualityTestResult(Properties ctx, int M_QualityTestResult_ID, String trxName) {
    super(ctx, M_QualityTestResult_ID, trxName);
  }

  public MQualityTestResult(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  @Override
  protected void setClientOrg(IPO po) {
    super.setClientOrg(po);
  }
}
