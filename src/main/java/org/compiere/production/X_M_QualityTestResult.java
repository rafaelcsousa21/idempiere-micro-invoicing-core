package org.compiere.production;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_QualityTestResult;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

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

  /** Load Meta Data */
  protected POInfo initPO(Properties ctx) {
    POInfo poi = POInfo.getPOInfo(ctx, Table_ID, get_TrxName());
    return poi;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_QualityTestResult[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    throw new IllegalArgumentException("Description is virtual column");
  }

  /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
  }

  /**
   * Set Expected Result.
   *
   * @param ExpectedResult Expected Result
   */
  public void setExpectedResult(String ExpectedResult) {
    throw new IllegalArgumentException("ExpectedResult is virtual column");
  }

  /**
   * Get Expected Result.
   *
   * @return Expected Result
   */
  public String getExpectedResult() {
    return (String) get_Value(COLUMNNAME_ExpectedResult);
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
   * Get QC Pass.
   *
   * @return QC Pass
   */
  public boolean isQCPass() {
    Object oo = get_Value(COLUMNNAME_IsQCPass);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  public I_M_AttributeSetInstance getMAttributeSetInstance() throws RuntimeException {
    return (I_M_AttributeSetInstance)
        MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
            .getPO(getMAttributeSetInstance_ID(), get_TrxName());
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

  public org.compiere.model.I_M_QualityTest getM_QualityTest() throws RuntimeException {
    return (org.compiere.model.I_M_QualityTest)
        MTable.get(getCtx(), org.compiere.model.I_M_QualityTest.Table_Name)
            .getPO(getM_QualityTest_ID(), get_TrxName());
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
   * Set Quality Test Result.
   *
   * @param M_QualityTestResult_ID Quality Test Result
   */
  public void setM_QualityTestResult_ID(int M_QualityTestResult_ID) {
    if (M_QualityTestResult_ID < 1) set_ValueNoCheck(COLUMNNAME_M_QualityTestResult_ID, null);
    else
      set_ValueNoCheck(COLUMNNAME_M_QualityTestResult_ID, Integer.valueOf(M_QualityTestResult_ID));
  }

  /**
   * Get Quality Test Result.
   *
   * @return Quality Test Result
   */
  public int getM_QualityTestResult_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_QualityTestResult_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set M_QualityTestResult_UU.
   *
   * @param M_QualityTestResult_UU M_QualityTestResult_UU
   */
  public void setM_QualityTestResult_UU(String M_QualityTestResult_UU) {
    set_Value(COLUMNNAME_M_QualityTestResult_UU, M_QualityTestResult_UU);
  }

  /**
   * Get M_QualityTestResult_UU.
   *
   * @return M_QualityTestResult_UU
   */
  public String getM_QualityTestResult_UU() {
    return (String) get_Value(COLUMNNAME_M_QualityTestResult_UU);
  }

  /**
   * Set Processed.
   *
   * @param Processed The document has been processed
   */
  public void setProcessed(boolean Processed) {
    set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
  }

  /**
   * Get Processed.
   *
   * @return The document has been processed
   */
  public boolean isProcessed() {
    Object oo = get_Value(COLUMNNAME_Processed);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Result.
   *
   * @param Result Result of the action taken
   */
  public void setResult(String Result) {
    set_Value(COLUMNNAME_Result, Result);
  }

  /**
   * Get Result.
   *
   * @return Result of the action taken
   */
  public String getResult() {
    return (String) get_Value(COLUMNNAME_Result);
  }
}
