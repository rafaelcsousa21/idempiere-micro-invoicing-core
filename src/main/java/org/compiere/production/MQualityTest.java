package org.compiere.production;

import kotliquery.Row;

public class MQualityTest extends X_M_QualityTest {

    /**
     *
     */
    private static final long serialVersionUID = -8585270006299484402L;

    public MQualityTest(int M_QualityTest_ID) {
        super(M_QualityTest_ID);
    }

    public MQualityTest(Row row) {
        super(row);
    }

    public MQualityTestResult createResult(int m_attributesetinstance_id) {
        MQualityTestResult result = new MQualityTestResult(0);
        result.setClientOrg(this);
        result.setQualityTestId(getQualityTestId());
        result.setAttributeSetInstanceId(m_attributesetinstance_id);
        result.setProcessed(false);
        result.setIsQCPass(false);
        result.saveEx();
        return result;
    }
}
