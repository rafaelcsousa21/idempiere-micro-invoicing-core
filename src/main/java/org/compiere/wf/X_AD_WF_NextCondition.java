package org.compiere.wf;

import org.compiere.model.I_AD_WF_NextCondition;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_WF_NextCondition
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_NextCondition extends PO implements I_AD_WF_NextCondition, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_WF_NextCondition(Properties ctx, int AD_WF_NextCondition_ID, String trxName) {
    super(ctx, AD_WF_NextCondition_ID, trxName);
    /**
     * if (AD_WF_NextCondition_ID == 0) { setAD_Column_ID (0); setAD_WF_NextCondition_ID (0);
     * setAD_WF_NodeNext_ID (0); // @4|AD_WF_NodeNext_ID@ setAndOr (null); // O setEntityType
     * (null); // @SQL=select get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual setOperation
     * (null); setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM
     * AD_WF_NextCondition WHERE AD_WF_NodeNext_ID=@AD_WF_NodeNext_ID@ setValue (null); }
     */
  }

  /** Load Constructor */
  public X_AD_WF_NextCondition(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_AD_WF_NextCondition[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException {
    return (org.compiere.model.I_AD_Column)
        MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_Name)
            .getPO(getAD_Column_ID(), null);
  }

  /**
   * Set Column.
   *
   * @param AD_Column_ID Column in the table
   */
  public void setAD_Column_ID(int AD_Column_ID) {
    if (AD_Column_ID < 1) set_Value(COLUMNNAME_AD_Column_ID, null);
    else set_Value(COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
  }

  /**
   * Get Column.
   *
   * @return Column in the table
   */
  public int getAD_Column_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Column_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Transition Condition.
   *
   * @param AD_WF_NextCondition_ID Workflow Node Transition Condition
   */
  public void setAD_WF_NextCondition_ID(int AD_WF_NextCondition_ID) {
    if (AD_WF_NextCondition_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_WF_NextCondition_ID, null);
    else
      set_ValueNoCheck(COLUMNNAME_AD_WF_NextCondition_ID, Integer.valueOf(AD_WF_NextCondition_ID));
  }

  /**
   * Get Transition Condition.
   *
   * @return Workflow Node Transition Condition
   */
  public int getAD_WF_NextCondition_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_NextCondition_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set AD_WF_NextCondition_UU.
   *
   * @param AD_WF_NextCondition_UU AD_WF_NextCondition_UU
   */
  public void setAD_WF_NextCondition_UU(String AD_WF_NextCondition_UU) {
    set_Value(COLUMNNAME_AD_WF_NextCondition_UU, AD_WF_NextCondition_UU);
  }

  /**
   * Get AD_WF_NextCondition_UU.
   *
   * @return AD_WF_NextCondition_UU
   */
  public String getAD_WF_NextCondition_UU() {
    return (String) get_Value(COLUMNNAME_AD_WF_NextCondition_UU);
  }

  public org.compiere.model.I_AD_WF_NodeNext getAD_WF_NodeNext() throws RuntimeException {
    return (org.compiere.model.I_AD_WF_NodeNext)
        MTable.get(getCtx(), org.compiere.model.I_AD_WF_NodeNext.Table_Name)
            .getPO(getAD_WF_NodeNext_ID(), null);
  }

  /**
   * Set Node Transition.
   *
   * @param AD_WF_NodeNext_ID Workflow Node Transition
   */
  public void setAD_WF_NodeNext_ID(int AD_WF_NodeNext_ID) {
    if (AD_WF_NodeNext_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_WF_NodeNext_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_WF_NodeNext_ID, Integer.valueOf(AD_WF_NodeNext_ID));
  }

  /**
   * Get Node Transition.
   *
   * @return Workflow Node Transition
   */
  public int getAD_WF_NodeNext_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_NodeNext_ID);
    if (ii == null) return 0;
    return ii;
  }

  /** AndOr AD_Reference_ID=204 */
  public static final int ANDOR_AD_Reference_ID = 204;
  /** And = A */
  public static final String ANDOR_And = "A";
  /** Or = O */
  public static final String ANDOR_Or = "O";
  /**
   * Set And/Or.
   *
   * @param AndOr Logical operation: AND or OR
   */
  public void setAndOr(String AndOr) {

    set_Value(COLUMNNAME_AndOr, AndOr);
  }

  /**
   * Get And/Or.
   *
   * @return Logical operation: AND or OR
   */
  public String getAndOr() {
    return (String) get_Value(COLUMNNAME_AndOr);
  }

  /** EntityType AD_Reference_ID=389 */
  public static final int ENTITYTYPE_AD_Reference_ID = 389;
  /**
   * Set Entity Type.
   *
   * @param EntityType Dictionary Entity Type; Determines ownership and synchronization
   */
  public void setEntityType(String EntityType) {

    set_Value(COLUMNNAME_EntityType, EntityType);
  }

  /**
   * Get Entity Type.
   *
   * @return Dictionary Entity Type; Determines ownership and synchronization
   */
  public String getEntityType() {
    return (String) get_Value(COLUMNNAME_EntityType);
  }

  /** Operation AD_Reference_ID=205 */
  public static final int OPERATION_AD_Reference_ID = 205;
  /** = = == */
  public static final String OPERATION_Eq = "==";
  /** >= = >= */
  public static final String OPERATION_GtEq = ">=";
  /** > = >> */
  public static final String OPERATION_Gt = ">>";
  /** < = << */
  public static final String OPERATION_Le = "<<";
  /** ~ = ~~ */
  public static final String OPERATION_Like = "~~";
  /** <= = <= */
  public static final String OPERATION_LeEq = "<=";
  /** |<x>| = AB */
  public static final String OPERATION_X = "AB";
  /** sql = SQ */
  public static final String OPERATION_Sql = "SQ";
  /** != = != */
  public static final String OPERATION_NotEq = "!=";
  /**
   * Set Operation.
   *
   * @param Operation Compare Operation
   */
  public void setOperation(String Operation) {

    set_Value(COLUMNNAME_Operation, Operation);
  }

  /**
   * Get Operation.
   *
   * @return Compare Operation
   */
  public String getOperation() {
    return (String) get_Value(COLUMNNAME_Operation);
  }

  /**
   * Set Sequence.
   *
   * @param SeqNo Method of ordering records; lowest number comes first
   */
  public void setSeqNo(int SeqNo) {
    set_Value(COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
  }

  /**
   * Get Sequence.
   *
   * @return Method of ordering records; lowest number comes first
   */
  public int getSeqNo() {
    Integer ii = (Integer) get_Value(COLUMNNAME_SeqNo);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Search Key.
   *
   * @param Value Search key for the record in the format required - must be unique
   */
  public void setValue(String Value) {
    set_Value(COLUMNNAME_Value, Value);
  }

  /**
   * Get Search Key.
   *
   * @return Search key for the record in the format required - must be unique
   */
  public String getValue() {
    return (String) get_Value(COLUMNNAME_Value);
  }

  /**
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), getValue());
  }

  /**
   * Set Value To.
   *
   * @param Value2 Value To
   */
  public void setValue2(String Value2) {
    set_Value(COLUMNNAME_Value2, Value2);
  }

  /**
   * Get Value To.
   *
   * @return Value To
   */
  public String getValue2() {
    return (String) get_Value(COLUMNNAME_Value2);
  }

  @Override
  public int getTableId() {
    return I_AD_WF_NextCondition.Table_ID;
  }
}
