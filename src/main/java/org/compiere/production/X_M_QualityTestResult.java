package org.compiere.production;

import org.compiere.model.I_M_QualityTestResult;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_QualityTestResult
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_QualityTestResult extends PO implements I_M_QualityTestResult, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_QualityTestResult(Properties ctx, int M_QualityTestResult_ID, String trxName) {
    super(ctx, M_QualityTestResult_ID, trxName);
    /**
     * if (M_QualityTestResult_ID == 0) { setIsQCPass (false); // N setM_AttributeSetInstance_ID
     * (0); setM_QualityTest_ID (0); setM_QualityTestResult_ID (0); setProcessed (false); // N }
     */
  }

  /** Load Constructor */
  public X_M_QualityTestResult(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_M_QualityTestResult[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Set QC Pass.
   *
   * @param IsQCPass QC Pass
   */
  public void setIsQCPass(boolean IsQCPass) {
    set_Value(COLUMNNAME_IsQCPass, Boolean.valueOf(IsQCPass));
  }

    /**
   * Set Attribute Set Instance.
   *
   * @param M_AttributeSetInstance_ID Product Attribute Set Instance
   */
  public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
    if (M_AttributeSetInstance_ID < 0) set_ValueNoCheck(COLUMNNAME_M_AttributeSetInstance_ID, null);
    else
      set_ValueNoCheck(
          COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
  }

  /**
   * Get Attribute Set Instance.
   *
   * @return Product Attribute Set Instance
   */
  public int getMAttributeSetInstance_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Quality Test.
   *
   * @param M_QualityTest_ID Quality Test
   */
  public void setM_QualityTest_ID(int M_QualityTest_ID) {
    if (M_QualityTest_ID < 1) set_ValueNoCheck(COLUMNNAME_M_QualityTest_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_QualityTest_ID, Integer.valueOf(M_QualityTest_ID));
  }

  /**
   * Get Quality Test.
   *
   * @return Quality Test
   */
  public int getM_QualityTest_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_QualityTest_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
  }

    @Override
  public int getTableId() {
    return I_M_QualityTestResult.Table_ID;
  }
}
