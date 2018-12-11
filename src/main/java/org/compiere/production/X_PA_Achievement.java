package org.compiere.production;

import org.compiere.model.I_PA_Achievement;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for PA_Achievement
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_PA_Achievement extends BasePOName implements I_PA_Achievement, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_PA_Achievement(Properties ctx, int PA_Achievement_ID, String trxName) {
    super(ctx, PA_Achievement_ID, trxName);
  }

  /** Load Constructor */
  public X_PA_Achievement(Properties ctx, ResultSet rs, String trxName) {
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


  @Override
  public int getTableId() {
    return Table_ID;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_PA_Achievement[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Document Date.
   *
   * @param DateDoc Date of the Document
   */
  public void setDateDoc(Timestamp DateDoc) {
    set_Value(COLUMNNAME_DateDoc, DateDoc);
  }

  /**
   * Get Document Date.
   *
   * @return Date of the Document
   */
  public Timestamp getDateDoc() {
    return (Timestamp) get_Value(COLUMNNAME_DateDoc);
  }

  /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(COLUMNNAME_Description, Description);
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
   * Set Achieved.
   *
   * @param IsAchieved The goal is achieved
   */
  public void setIsAchieved(boolean IsAchieved) {
    set_Value(COLUMNNAME_IsAchieved, Boolean.valueOf(IsAchieved));
  }

  /**
   * Get Achieved.
   *
   * @return The goal is achieved
   */
  public boolean isAchieved() {
    Object oo = get_Value(COLUMNNAME_IsAchieved);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Manual Actual.
   *
   * @param ManualActual Manually entered actual value
   */
  public void setManualActual(BigDecimal ManualActual) {
    set_Value(COLUMNNAME_ManualActual, ManualActual);
  }

  /**
   * Get Manual Actual.
   *
   * @return Manually entered actual value
   */
  public BigDecimal getManualActual() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_ManualActual);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Note.
   *
   * @param Note Optional additional user defined information
   */
  public void setNote(String Note) {
    set_Value(COLUMNNAME_Note, Note);
  }

  /**
   * Get Note.
   *
   * @return Optional additional user defined information
   */
  public String getNote() {
    return (String) get_Value(COLUMNNAME_Note);
  }

  /**
   * Set Achievement.
   *
   * @param PA_Achievement_ID Performance Achievement
   */
  public void setPA_Achievement_ID(int PA_Achievement_ID) {
    if (PA_Achievement_ID < 1) set_ValueNoCheck(COLUMNNAME_PA_Achievement_ID, null);
    else set_ValueNoCheck(COLUMNNAME_PA_Achievement_ID, Integer.valueOf(PA_Achievement_ID));
  }

  /**
   * Get Achievement.
   *
   * @return Performance Achievement
   */
  public int getPA_Achievement_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PA_Achievement_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set PA_Achievement_UU.
   *
   * @param PA_Achievement_UU PA_Achievement_UU
   */
  public void setPA_Achievement_UU(String PA_Achievement_UU) {
    set_Value(COLUMNNAME_PA_Achievement_UU, PA_Achievement_UU);
  }

  /**
   * Get PA_Achievement_UU.
   *
   * @return PA_Achievement_UU
   */
  public String getPA_Achievement_UU() {
    return (String) get_Value(COLUMNNAME_PA_Achievement_UU);
  }

  public org.compiere.model.I_PA_Measure getPA_Measure() throws RuntimeException {
    return (org.compiere.model.I_PA_Measure)
        MTable.get(getCtx(), org.compiere.model.I_PA_Measure.Table_Name)
            .getPO(getPA_Measure_ID(), get_TrxName());
  }

  /**
   * Set Measure.
   *
   * @param PA_Measure_ID Concrete Performance Measurement
   */
  public void setPA_Measure_ID(int PA_Measure_ID) {
    if (PA_Measure_ID < 1) set_ValueNoCheck(COLUMNNAME_PA_Measure_ID, null);
    else set_ValueNoCheck(COLUMNNAME_PA_Measure_ID, Integer.valueOf(PA_Measure_ID));
  }

  /**
   * Get Measure.
   *
   * @return Concrete Performance Measurement
   */
  public int getPA_Measure_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_PA_Measure_ID);
    if (ii == null) return 0;
    return ii;
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
}
