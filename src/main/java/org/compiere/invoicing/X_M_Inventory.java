package org.compiere.invoicing;

import org.compiere.model.I_M_Inventory;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for M_Inventory
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_Inventory extends PO implements I_M_Inventory, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_Inventory(Properties ctx, int M_Inventory_ID, String trxName) {
    super(ctx, M_Inventory_ID, trxName);
    /**
     * if (M_Inventory_ID == 0) { setC_DocType_ID (0); setDocAction (null); // CO setDocStatus
     * (null); // DR setDocumentNo (null); setIsApproved (false); setM_Inventory_ID (0);
     * setMovementDate (new Timestamp( System.currentTimeMillis() )); // @#Date@ setPosted (false);
     * setProcessed (false); }
     */
  }

  /** Load Constructor */
  public X_M_Inventory(Properties ctx, ResultSet rs, String trxName) {
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

  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_Inventory[").append(getId()).append("]");
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
   * Get Activity.
   *
   * @return Business Activity
   */
  public int getC_Activity_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Activity_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Campaign.
   *
   * @return Marketing Campaign
   */
  public int getC_Campaign_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Campaign_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Currency Type.
   *
   * @return Currency Conversion Rate Type
   */
  public int getC_ConversionType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_ConversionType_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Currency.
   *
   * @return The Currency for this record
   */
  public int getC_Currency_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Currency_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException {
    return (org.compiere.model.I_C_DocType)
        MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
            .getPO(getC_DocType_ID(), null);
  }

  /**
   * Set Document Type.
   *
   * @param C_DocType_ID Document type or rules
   */
  public void setC_DocType_ID(int C_DocType_ID) {
    if (C_DocType_ID < 0) set_Value(COLUMNNAME_C_DocType_ID, null);
    else set_Value(COLUMNNAME_C_DocType_ID, C_DocType_ID);
  }

  /**
   * Get Document Type.
   *
   * @return Document type or rules
   */
  public int getC_DocType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_DocType_ID);
    if (ii == null) return 0;
    return ii;
  }

  /** CostingMethod AD_Reference_ID=122 */
  public static final int COSTINGMETHOD_AD_Reference_ID = 122;
  /** Standard Costing = S */
  public static final String COSTINGMETHOD_StandardCosting = "S";
  /** Average PO = A */
  public static final String COSTINGMETHOD_AveragePO = "A";
  /** Lifo = L */
  public static final String COSTINGMETHOD_Lifo = "L";
  /** Fifo = F */
  public static final String COSTINGMETHOD_Fifo = "F";
  /** Last PO Price = p */
  public static final String COSTINGMETHOD_LastPOPrice = "p";
  /** Average Invoice = I */
  public static final String COSTINGMETHOD_AverageInvoice = "I";
  /** Last Invoice = i */
  public static final String COSTINGMETHOD_LastInvoice = "i";
  /** User Defined = U */
  public static final String COSTINGMETHOD_UserDefined = "U";
  /** _ = x */
  public static final String COSTINGMETHOD__ = "x";
  /**
   * Set Costing Method.
   *
   * @param CostingMethod Indicates how Costs will be calculated
   */
  public void setCostingMethod(String CostingMethod) {

    set_Value(COLUMNNAME_CostingMethod, CostingMethod);
  }

  /**
   * Get Costing Method.
   *
   * @return Indicates how Costs will be calculated
   */
  public String getCostingMethod() {
    return (String) get_Value(COLUMNNAME_CostingMethod);
  }

    /**
   * Get Project.
   *
   * @return Financial Project
   */
  public int getC_Project_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Project_ID);
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
   * Set Movement Date.
   *
   * @param MovementDate Date a product was moved in or out of inventory
   */
  public void setMovementDate(Timestamp MovementDate) {
    set_Value(COLUMNNAME_MovementDate, MovementDate);
  }

  /**
   * Get Movement Date.
   *
   * @return Date a product was moved in or out of inventory
   */
  public Timestamp getMovementDate() {
    return (Timestamp) get_Value(COLUMNNAME_MovementDate);
  }

    /**
   * Get Perpetual Inventory.
   *
   * @return Rules for generating physical inventory
   */
  public int getM_PerpetualInv_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_PerpetualInv_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Warehouse.
   *
   * @param M_Warehouse_ID Storage Warehouse and Service Point
   */
  public void setM_Warehouse_ID(int M_Warehouse_ID) {
    if (M_Warehouse_ID < 1) set_Value(COLUMNNAME_M_Warehouse_ID, null);
    else set_Value(COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
  }

  /**
   * Get Warehouse.
   *
   * @return Storage Warehouse and Service Point
   */
  public int getM_Warehouse_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_Warehouse_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Posted.
   *
   * @param Posted Posting status
   */
  public void setPosted(boolean Posted) {
    set_Value(COLUMNNAME_Posted, Boolean.valueOf(Posted));
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
   * Set Reversal ID.
   *
   * @param Reversal_ID ID of document reversal
   */
  public void setReversal_ID(int Reversal_ID) {
    if (Reversal_ID < 1) set_Value(COLUMNNAME_Reversal_ID, null);
    else set_Value(COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
  }

  /**
   * Get Reversal ID.
   *
   * @return ID of document reversal
   */
  public int getReversal_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Reversal_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get User Element List 1.
   *
   * @return User defined list element #1
   */
  public int getUser1_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_User1_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get User Element List 2.
   *
   * @return User defined list element #2
   */
  public int getUser2_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_User2_ID);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_M_Inventory.Table_ID;
  }
}
