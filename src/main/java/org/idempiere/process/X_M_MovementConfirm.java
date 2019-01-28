package org.idempiere.process;

import org.compiere.model.I_M_MovementConfirm;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

public class X_M_MovementConfirm extends PO implements I_M_MovementConfirm, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_MovementConfirm(Properties ctx, int M_MovementConfirm_ID, String trxName) {
    super(ctx, M_MovementConfirm_ID, trxName);
    /**
     * if (M_MovementConfirm_ID == 0) { setDocAction (null); setDocStatus (null); setDocumentNo
     * (null); setIsApproved (false); // N setM_MovementConfirm_ID (0); setM_Movement_ID (0);
     * setProcessed (false); }
     */
  }

  /** Load Constructor */
  public X_M_MovementConfirm(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 1 - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  @Override
  public int getTableId() {
    return Table_ID;
  }


  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_MovementConfirm[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Approval Amount.
   *
   * @return Document Approval Amount
   */
  public BigDecimal getApprovalAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_ApprovalAmt);
    if (bd == null) return Env.ZERO;
    return bd;
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

    /** Complete = CO */
  public static final String DOCACTION_Complete = "CO";
    /** Close = CL */
  public static final String DOCACTION_Close = "CL";
    /** <None> = -- */
  public static final String DOCACTION_None = "--";
  /** Prepare = PR */
  public static final String DOCACTION_Prepare = "PR";

    /**
   * Set Document Action.
   *
   * @param DocAction The targeted status of the document
   */
  public void setDocAction(String DocAction) {

    set_Value(COLUMNNAME_DocAction, DocAction);
  }

  /**
   * Get Document Action.
   *
   * @return The targeted status of the document
   */
  public String getDocAction() {
    return (String) get_Value(COLUMNNAME_DocAction);
  }

    /** Drafted = DR */
  public static final String DOCSTATUS_Drafted = "DR";

    /**
   * Set Document Status.
   *
   * @param DocStatus The current status of the document
   */
  public void setDocStatus(String DocStatus) {

    set_Value(COLUMNNAME_DocStatus, DocStatus);
  }

  /**
   * Get Document Status.
   *
   * @return The current status of the document
   */
  public String getDocStatus() {
    return (String) get_Value(COLUMNNAME_DocStatus);
  }

    /**
   * Get Document No.
   *
   * @return Document sequence number of the document
   */
  public String getDocumentNo() {
    return (String) get_Value(COLUMNNAME_DocumentNo);
  }

    /**
   * Set Approved.
   *
   * @param IsApproved Indicates if this document requires approval
   */
  public void setIsApproved(boolean IsApproved) {
    set_Value(COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
  }

  /**
   * Get Approved.
   *
   * @return Indicates if this document requires approval
   */
  public boolean isApproved() {
    Object oo = get_Value(COLUMNNAME_IsApproved);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Set Phys.Inventory.
   *
   * @param M_Inventory_ID Parameters for a Physical Inventory
   */
  public void setM_Inventory_ID(int M_Inventory_ID) {
    if (M_Inventory_ID < 1) set_Value(COLUMNNAME_M_Inventory_ID, null);
    else set_Value(COLUMNNAME_M_Inventory_ID, Integer.valueOf(M_Inventory_ID));
  }

  /**
   * Get Phys.Inventory.
   *
   * @return Parameters for a Physical Inventory
   */
  public int getM_Inventory_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Inventory_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Move Confirm.
   *
   * @return Inventory Move Confirmation
   */
  public int getM_MovementConfirm_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_MovementConfirm_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Inventory Move.
   *
   * @param M_Movement_ID Movement of Inventory
   */
  public void setM_Movement_ID(int M_Movement_ID) {
    if (M_Movement_ID < 1) set_Value(COLUMNNAME_M_Movement_ID, null);
    else set_Value(COLUMNNAME_M_Movement_ID, Integer.valueOf(M_Movement_ID));
  }

  /**
   * Get Inventory Move.
   *
   * @return Movement of Inventory
   */
  public int getM_Movement_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Movement_ID);
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
   * Set Process Now.
   *
   * @param Processing Process Now
   */
  public void setProcessing(boolean Processing) {
    set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
  }

}
