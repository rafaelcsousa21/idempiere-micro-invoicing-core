package org.idempiere.process;

import org.compiere.model.I_M_MovementConfirm;
import org.compiere.orm.MTable;
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
   * Set Approval Amount.
   *
   * @param ApprovalAmt Document Approval Amount
   */
  public void setApprovalAmt(BigDecimal ApprovalAmt) {
    set_Value(COLUMNNAME_ApprovalAmt, ApprovalAmt);
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

  /** DocAction AD_Reference_ID=135 */
  public static final int DOCACTION_AD_Reference_ID = 135;
  /** Complete = CO */
  public static final String DOCACTION_Complete = "CO";
  /** Approve = AP */
  public static final String DOCACTION_Approve = "AP";
  /** Reject = RJ */
  public static final String DOCACTION_Reject = "RJ";
  /** Post = PO */
  public static final String DOCACTION_Post = "PO";
  /** Void = VO */
  public static final String DOCACTION_Void = "VO";
  /** Close = CL */
  public static final String DOCACTION_Close = "CL";
  /** Reverse - Correct = RC */
  public static final String DOCACTION_Reverse_Correct = "RC";
  /** Reverse - Accrual = RA */
  public static final String DOCACTION_Reverse_Accrual = "RA";
  /** Invalidate = IN */
  public static final String DOCACTION_Invalidate = "IN";
  /** Re-activate = RE */
  public static final String DOCACTION_Re_Activate = "RE";
  /** <None> = -- */
  public static final String DOCACTION_None = "--";
  /** Prepare = PR */
  public static final String DOCACTION_Prepare = "PR";
  /** Unlock = XL */
  public static final String DOCACTION_Unlock = "XL";
  /** Wait Complete = WC */
  public static final String DOCACTION_WaitComplete = "WC";
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

  /** DocStatus AD_Reference_ID=131 */
  public static final int DOCSTATUS_AD_Reference_ID = 131;
  /** Drafted = DR */
  public static final String DOCSTATUS_Drafted = "DR";
  /** Completed = CO */
  public static final String DOCSTATUS_Completed = "CO";
  /** Approved = AP */
  public static final String DOCSTATUS_Approved = "AP";
  /** Not Approved = NA */
  public static final String DOCSTATUS_NotApproved = "NA";
  /** Voided = VO */
  public static final String DOCSTATUS_Voided = "VO";
  /** Invalid = IN */
  public static final String DOCSTATUS_Invalid = "IN";
  /** Reversed = RE */
  public static final String DOCSTATUS_Reversed = "RE";
  /** Closed = CL */
  public static final String DOCSTATUS_Closed = "CL";
  /** Unknown = ?? */
  public static final String DOCSTATUS_Unknown = "??";
  /** In Progress = IP */
  public static final String DOCSTATUS_InProgress = "IP";
  /** Waiting Payment = WP */
  public static final String DOCSTATUS_WaitingPayment = "WP";
  /** Waiting Confirmation = WC */
  public static final String DOCSTATUS_WaitingConfirmation = "WC";
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
   * Set Document No.
   *
   * @param DocumentNo Document sequence number of the document
   */
  public void setDocumentNo(String DocumentNo) {
    set_Value(COLUMNNAME_DocumentNo, DocumentNo);
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
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), getDocumentNo());
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

  public org.compiere.model.I_M_Inventory getM_Inventory() throws RuntimeException {
    return (org.compiere.model.I_M_Inventory)
        MTable.get(getCtx(), org.compiere.model.I_M_Inventory.Table_Name)
            .getPO(getM_Inventory_ID(), get_TrxName());
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
   * Set Move Confirm.
   *
   * @param M_MovementConfirm_ID Inventory Move Confirmation
   */
  public void setM_MovementConfirm_ID(int M_MovementConfirm_ID) {
    if (M_MovementConfirm_ID < 1) set_ValueNoCheck(COLUMNNAME_M_MovementConfirm_ID, null);
    else set_ValueNoCheck(COLUMNNAME_M_MovementConfirm_ID, Integer.valueOf(M_MovementConfirm_ID));
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
   * Set M_MovementConfirm_UU.
   *
   * @param M_MovementConfirm_UU M_MovementConfirm_UU
   */
  public void setM_MovementConfirm_UU(String M_MovementConfirm_UU) {
    set_Value(COLUMNNAME_M_MovementConfirm_UU, M_MovementConfirm_UU);
  }

  /**
   * Get M_MovementConfirm_UU.
   *
   * @return M_MovementConfirm_UU
   */
  public String getM_MovementConfirm_UU() {
    return (String) get_Value(COLUMNNAME_M_MovementConfirm_UU);
  }

  public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException {
    return (org.compiere.model.I_M_Movement)
        MTable.get(getCtx(), org.compiere.model.I_M_Movement.Table_Name)
            .getPO(getM_Movement_ID(), get_TrxName());
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

  /**
   * Get Process Now.
   *
   * @return Process Now
   */
  public boolean isProcessing() {
    Object oo = get_Value(COLUMNNAME_Processing);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }
}
