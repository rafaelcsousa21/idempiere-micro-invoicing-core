package org.compiere.production;

import kotliquery.Row;
import org.idempiere.icommon.model.IPO;

public class MQualityTestResult extends X_M_QualityTestResult {

    /**
     *
     */
    private static final long serialVersionUID = -5529865718027582930L;

    public MQualityTestResult(int M_QualityTestResult_ID) {
        super(M_QualityTestResult_ID);
    }

    public MQualityTestResult(Row row) {
        super(row);
    }

    @Override
    protected void setClientOrg(IPO po) {
        super.setClientOrg(po);
    }
}
