package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_M_QualityTestResult;
import org.compiere.orm.PO;

/**
 * Generated Model for M_QualityTestResult
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_QualityTestResult extends PO implements I_M_QualityTestResult {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_QualityTestResult(int M_QualityTestResult_ID) {
        super(M_QualityTestResult_ID);
    }

    /**
     * Load Constructor
     */
    public X_M_QualityTestResult(Row row) {
        super(row);
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
        return "X_M_QualityTestResult[" + getId() + "]";
    }

    /**
     * Set QC Pass.
     *
     * @param IsQCPass QC Pass
     */
    public void setIsQCPass(boolean IsQCPass) {
        setValue(COLUMNNAME_IsQCPass, IsQCPass);
    }

    /**
     * Set Attribute Set Instance.
     *
     * @param M_AttributeSetInstance_ID Product Attribute Set Instance
     */
    public void setAttributeSetInstanceId(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID < 0) setValueNoCheck(COLUMNNAME_M_AttributeSetInstance_ID, null);
        else
            setValueNoCheck(
                    COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
    }

    /**
     * Set Quality Test.
     *
     * @param M_QualityTest_ID Quality Test
     */
    public void setQualityTestId(int M_QualityTest_ID) {
        if (M_QualityTest_ID < 1) setValueNoCheck(COLUMNNAME_M_QualityTest_ID, null);
        else setValueNoCheck(COLUMNNAME_M_QualityTest_ID, Integer.valueOf(M_QualityTest_ID));
    }

    /**
     * Set Processed.
     *
     * @param Processed The document has been processed
     */
    public void setProcessed(boolean Processed) {
        setValue(COLUMNNAME_Processed, Boolean.valueOf(Processed));
    }

    @Override
    public int getTableId() {
        return I_M_QualityTestResult.Table_ID;
    }
}
