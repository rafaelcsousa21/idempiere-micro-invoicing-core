package org.compiere.wf;

import org.compiere.model.I_AD_WF_NextCondition;
import org.compiere.orm.PO;
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
  public X_AD_WF_NextCondition(Properties ctx, int AD_WF_NextCondition_ID) {
    super(ctx, AD_WF_NextCondition_ID);
    /**
     * if (AD_WF_NextCondition_ID == 0) { setAD_Column_ID (0); setAD_WF_NextCondition_ID (0);
     * setAD_WF_NodeNext_ID (0); // @4|AD_WF_NodeNext_ID@ setAndOr (null); // O setEntityType
     * (null); // @SQL=select get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual setOperation
     * (null); setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM
     * AD_WF_NextCondition WHERE AD_WF_NodeNext_ID=@AD_WF_NodeNext_ID@ setValue (null); }
     */
  }

  /** Load Constructor */
  public X_AD_WF_NextCondition(Properties ctx, ResultSet rs) {
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
    StringBuffer sb = new StringBuffer("X_AD_WF_NextCondition[").append(getId()).append("]");
    return sb.toString();
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
   * Get Node Transition.
   *
   * @return Workflow Node Transition
   */
  public int getAD_WF_NodeNext_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_NodeNext_ID);
    if (ii == null) return 0;
    return ii;
  }

    /** Or = O */
  public static final String ANDOR_Or = "O";

    /**
   * Get And/Or.
   *
   * @return Logical operation: AND or OR
   */
  public String getAndOr() {
    return (String) get_Value(COLUMNNAME_AndOr);
  }

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
   * Get Operation.
   *
   * @return Compare Operation
   */
  public String getOperation() {
    return (String) get_Value(COLUMNNAME_Operation);
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
   * Get Search Key.
   *
   * @return Search key for the record in the format required - must be unique
   */
  public String getValue() {
    return (String) get_Value(COLUMNNAME_Value);
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
