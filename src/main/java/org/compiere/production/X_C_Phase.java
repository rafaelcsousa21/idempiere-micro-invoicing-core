package org.compiere.production;

import org.compiere.model.I_C_Phase;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_Phase
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Phase extends BasePOName implements I_C_Phase, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_Phase(Properties ctx, int C_Phase_ID, String trxName) {
    super(ctx, C_Phase_ID, trxName);
  }

  /** Load Constructor */
  public X_C_Phase(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_Phase[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Standard Phase.
   *
   * @param C_Phase_ID Standard Phase of the Project Type
   */
  public void setC_Phase_ID(int C_Phase_ID) {
    if (C_Phase_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Phase_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Phase_ID, Integer.valueOf(C_Phase_ID));
  }

  /**
   * Get Standard Phase.
   *
   * @return Standard Phase of the Project Type
   */
  public int getC_Phase_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Phase_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_Phase_UU.
   *
   * @param C_Phase_UU C_Phase_UU
   */
  public void setC_Phase_UU(String C_Phase_UU) {
    set_Value(COLUMNNAME_C_Phase_UU, C_Phase_UU);
  }

  /**
   * Get C_Phase_UU.
   *
   * @return C_Phase_UU
   */
  public String getC_Phase_UU() {
    return (String) get_Value(COLUMNNAME_C_Phase_UU);
  }

  public org.compiere.model.I_C_ProjectType getC_ProjectType() throws RuntimeException {
    return (org.compiere.model.I_C_ProjectType)
        MTable.get(getCtx(), org.compiere.model.I_C_ProjectType.Table_Name)
            .getPO(getC_ProjectType_ID(), get_TrxName());
  }

  /**
   * Set Project Type.
   *
   * @param C_ProjectType_ID Type of the project
   */
  public void setC_ProjectType_ID(int C_ProjectType_ID) {
    if (C_ProjectType_ID < 1) set_ValueNoCheck(COLUMNNAME_C_ProjectType_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_ProjectType_ID, Integer.valueOf(C_ProjectType_ID));
  }

  /**
   * Get Project Type.
   *
   * @return Type of the project
   */
  public int getC_ProjectType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ProjectType_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set Comment/Help.
   *
   * @param Help Comment or Hint
   */
  public void setHelp(String Help) {
    set_Value(COLUMNNAME_Help, Help);
  }

  /**
   * Get Comment/Help.
   *
   * @return Comment or Hint
   */
  public String getHelp() {
    return (String) get_Value(COLUMNNAME_Help);
  }

  public org.compiere.model.I_M_Product getM_Product() throws RuntimeException {
    return (org.compiere.model.I_M_Product)
        MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
            .getPO(getM_Product_ID(), get_TrxName());
  }

  /**
   * Set Product.
   *
   * @param M_Product_ID Product, Service, Item
   */
  public void setM_Product_ID(int M_Product_ID) {
    if (M_Product_ID < 1) set_Value(COLUMNNAME_M_Product_ID, null);
    else set_Value(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
  }

  /**
   * Get Product.
   *
   * @return Product, Service, Item
   */
  public int getM_Product_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Product_ID);
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

  /**
   * Set Standard Quantity.
   *
   * @param StandardQty Standard Quantity
   */
  public void setStandardQty(BigDecimal StandardQty) {
    set_Value(COLUMNNAME_StandardQty, StandardQty);
  }

  /**
   * Get Standard Quantity.
   *
   * @return Standard Quantity
   */
  public BigDecimal getStandardQty() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_StandardQty);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  @Override
  public int getTableId() {
    return I_C_Phase.Table_ID;
  }
}
