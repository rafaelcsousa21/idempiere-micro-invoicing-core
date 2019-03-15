package org.compiere.production;

import kotliquery.Row;
import org.idempiere.icommon.model.IPO;

import java.util.Properties;

public class MQualityTestResult extends X_M_QualityTestResult {

    /**
     *
     */
    private static final long serialVersionUID = -5529865718027582930L;

    public MQualityTestResult(Properties ctx, int M_QualityTestResult_ID) {
        super(ctx, M_QualityTestResult_ID);
    }

    public MQualityTestResult(Properties ctx, Row row) {
        super(ctx, row);
    }

    @Override
    protected void setClientOrg(IPO po) {
        super.setClientOrg(po);
    }
}
