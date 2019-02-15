package org.compiere.production;

import org.compiere.model.I_C_Project;
import org.compiere.orm.BasePONameValue;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_Project
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Project extends BasePONameValue implements I_C_Project, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_Project(Properties ctx, int C_Project_ID) {
    super(ctx, C_Project_ID);
    /**
     * if (C_Project_ID == 0) { setC_Currency_ID (0); setCommittedAmt (Env.ZERO); setCommittedQty
     * (Env.ZERO); setC_Project_ID (0); setInvoicedAmt (Env.ZERO); setInvoicedQty (Env.ZERO);
     * setIsCommitCeiling (false); setIsCommitment (false); setIsSummary (false); setName (null);
     * setPlannedAmt (Env.ZERO); setPlannedMarginAmt (Env.ZERO); setPlannedQty (Env.ZERO);
     * setProcessed (false); setProjectBalanceAmt (Env.ZERO); setProjectLineLevel (null); // P
     * setProjInvoiceRule (null); // - setValue (null); }
     */
  }

  /** Load Constructor */
  public X_C_Project(Properties ctx, ResultSet rs) {
    super(ctx, rs);
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
    StringBuffer sb = new StringBuffer("X_C_Project[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Trx Organization.
   *
   * @return Performing or initiating organization
   */
  public int getAD_OrgTrx_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_OrgTrx_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set User/Contact.
   *
   * @param AD_User_ID User within the system - Internal or Business Partner Contact
   */
  public void setAD_User_ID(int AD_User_ID) {
    if (AD_User_ID < 1) set_Value(COLUMNNAME_AD_User_ID, null);
    else set_Value(COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
  }

  /**
   * Get User/Contact.
   *
   * @return User within the system - Internal or Business Partner Contact
   */
  public int getAD_User_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_User_ID);
    if (ii == null) return 0;
    return ii;
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
   * Get Business Partner .
   *
   * @return Identifies a Business Partner
   */
  public int getC_BPartner_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BPartner_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Partner Location.
   *
   * @return Identifies the (ship to) address for this Business Partner
   */
  public int getC_BPartner_Location_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BPartner_Location_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get BPartner (Agent).
   *
   * @return Business Partner (Agent or Sales Rep)
   */
  public int getC_BPartnerSR_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_BPartnerSR_ID);
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
   * Set Currency.
   *
   * @param C_Currency_ID The Currency for this record
   */
  public void setC_Currency_ID(int C_Currency_ID) {
    if (C_Currency_ID < 1) set_Value(COLUMNNAME_C_Currency_ID, null);
    else set_Value(COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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

  /**
   * Set Committed Amount.
   *
   * @param CommittedAmt The (legal) commitment amount
   */
  public void setCommittedAmt(BigDecimal CommittedAmt) {
    set_Value(COLUMNNAME_CommittedAmt, CommittedAmt);
  }

    /**
   * Set Committed Quantity.
   *
   * @param CommittedQty The (legal) commitment Quantity
   */
  public void setCommittedQty(BigDecimal CommittedQty) {
    set_Value(COLUMNNAME_CommittedQty, CommittedQty);
  }

    /**
   * Get Payment Term.
   *
   * @return The terms of Payment (timing, discount)
   */
  public int getC_PaymentTerm_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_PaymentTerm_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set Project Type.
   *
   * @param C_ProjectType_ID Type of the project
   */
  public void setC_ProjectType_ID(String C_ProjectType_ID) {
    set_Value(COLUMNNAME_C_ProjectType_ID, C_ProjectType_ID);
  }

  /**
   * Get Project Type.
   *
   * @return Type of the project
   */
  public String getC_ProjectType_ID() {
    return (String) get_Value(COLUMNNAME_C_ProjectType_ID);
  }

    /**
   * Get Contract Date.
   *
   * @return The (planned) effective date of this document.
   */
  public Timestamp getDateContract() {
    return (Timestamp) get_Value(COLUMNNAME_DateContract);
  }

    /**
   * Get Finish Date.
   *
   * @return Finish or (planned) completion date
   */
  public Timestamp getDateFinish() {
    return (Timestamp) get_Value(COLUMNNAME_DateFinish);
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
   * Set Invoiced Amount.
   *
   * @param InvoicedAmt The amount invoiced
   */
  public void setInvoicedAmt(BigDecimal InvoicedAmt) {
    set_ValueNoCheck(COLUMNNAME_InvoicedAmt, InvoicedAmt);
  }

  /**
   * Get Invoiced Amount.
   *
   * @return The amount invoiced
   */
  public BigDecimal getInvoicedAmt() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_InvoicedAmt);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Quantity Invoiced .
   *
   * @param InvoicedQty The quantity invoiced
   */
  public void setInvoicedQty(BigDecimal InvoicedQty) {
    set_ValueNoCheck(COLUMNNAME_InvoicedQty, InvoicedQty);
  }

    /**
   * Set Commitment is Ceiling.
   *
   * @param IsCommitCeiling The commitment amount/quantity is the chargeable ceiling
   */
  public void setIsCommitCeiling(boolean IsCommitCeiling) {
    set_Value(COLUMNNAME_IsCommitCeiling, Boolean.valueOf(IsCommitCeiling));
  }

    /**
   * Set Commitment.
   *
   * @param IsCommitment Is this document a (legal) commitment?
   */
  public void setIsCommitment(boolean IsCommitment) {
    set_Value(COLUMNNAME_IsCommitment, Boolean.valueOf(IsCommitment));
  }

    /**
   * Set Summary Level.
   *
   * @param IsSummary This is a summary entity
   */
  public void setIsSummary(boolean IsSummary) {
    set_Value(COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
  }

    /**
   * Set Price List Version.
   *
   * @param M_PriceList_Version_ID Identifies a unique instance of a Price List
   */
  public void setM_PriceList_Version_ID(int M_PriceList_Version_ID) {
    if (M_PriceList_Version_ID < 1) set_Value(COLUMNNAME_M_PriceList_Version_ID, null);
    else set_Value(COLUMNNAME_M_PriceList_Version_ID, Integer.valueOf(M_PriceList_Version_ID));
  }

  /**
   * Get Price List Version.
   *
   * @return Identifies a unique instance of a Price List
   */
  public int getM_PriceList_Version_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_PriceList_Version_ID);
    if (ii == null) return 0;
    return ii;
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
   * Set Planned Amount.
   *
   * @param PlannedAmt Planned amount for this project
   */
  public void setPlannedAmt(BigDecimal PlannedAmt) {
    set_Value(COLUMNNAME_PlannedAmt, PlannedAmt);
  }

    /**
   * Set Planned Margin.
   *
   * @param PlannedMarginAmt Project's planned margin amount
   */
  public void setPlannedMarginAmt(BigDecimal PlannedMarginAmt) {
    set_Value(COLUMNNAME_PlannedMarginAmt, PlannedMarginAmt);
  }

    /**
   * Set Planned Quantity.
   *
   * @param PlannedQty Planned quantity for this project
   */
  public void setPlannedQty(BigDecimal PlannedQty) {
    set_Value(COLUMNNAME_PlannedQty, PlannedQty);
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
   * Set Project Balance.
   *
   * @param ProjectBalanceAmt Total Project Balance
   */
  public void setProjectBalanceAmt(BigDecimal ProjectBalanceAmt) {
    set_ValueNoCheck(COLUMNNAME_ProjectBalanceAmt, ProjectBalanceAmt);
  }

    /** Asset Project = A */
  public static final String PROJECTCATEGORY_AssetProject = "A";
  /** Work Order (Job) = W */
  public static final String PROJECTCATEGORY_WorkOrderJob = "W";
  /** Service (Charge) Project = S */
  public static final String PROJECTCATEGORY_ServiceChargeProject = "S";
  /**
   * Set Project Category.
   *
   * @param ProjectCategory Project Category
   */
  public void setProjectCategory(String ProjectCategory) {

    set_Value(COLUMNNAME_ProjectCategory, ProjectCategory);
  }

  /**
   * Get Project Category.
   *
   * @return Project Category
   */
  public String getProjectCategory() {
    return (String) get_Value(COLUMNNAME_ProjectCategory);
  }

    /** Project = P */
  public static final String PROJECTLINELEVEL_Project = "P";

    /**
   * Set Line Level.
   *
   * @param ProjectLineLevel Project Line Level
   */
  public void setProjectLineLevel(String ProjectLineLevel) {

    set_Value(COLUMNNAME_ProjectLineLevel, ProjectLineLevel);
  }

    /** None = - */
  public static final String PROJINVOICERULE_None = "-";

    /**
   * Set Invoice Rule.
   *
   * @param ProjInvoiceRule Invoice Rule for the project
   */
  public void setProjInvoiceRule(String ProjInvoiceRule) {

    set_Value(COLUMNNAME_ProjInvoiceRule, ProjInvoiceRule);
  }

    /**
   * Get Sales Representative.
   *
   * @return Sales Representative or Company Agent
   */
  public int getSalesRep_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_SalesRep_ID);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_C_Project.Table_ID;
  }
}
