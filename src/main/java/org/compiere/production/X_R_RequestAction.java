package org.compiere.production;

import org.compiere.model.I_R_RequestAction;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for R_RequestAction
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_R_RequestAction extends PO implements I_R_RequestAction, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_R_RequestAction(Properties ctx, int R_RequestAction_ID, String trxName) {
    super(ctx, R_RequestAction_ID, trxName);
    /** if (R_RequestAction_ID == 0) { setR_RequestAction_ID (0); setR_Request_ID (0); } */
  }

  /** Load Constructor */
  public X_R_RequestAction(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 7 - System - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_R_RequestAction[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Asset.
   *
   * @return Asset used internally or by customers
   */
  public int getA_Asset_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_A_Asset_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Role.
   *
   * @return Responsibility Role
   */
  public int getAD_Role_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Role_ID);
    if (ii == null) return 0;
    return ii;
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
   * Get Invoice.
   *
   * @return Invoice Identifier
   */
  public int getC_Invoice_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Invoice_ID);
    if (ii == null) return 0;
    return ii;
  }

  /** ConfidentialType AD_Reference_ID=340 */
  public static final int CONFIDENTIALTYPE_AD_Reference_ID = 340;
  /** Public Information = A */
  public static final String CONFIDENTIALTYPE_PublicInformation = "A";
  /** Partner Confidential = C */
  public static final String CONFIDENTIALTYPE_PartnerConfidential = "C";
  /** Internal = I */
  public static final String CONFIDENTIALTYPE_Internal = "I";
  /** Private Information = P */
  public static final String CONFIDENTIALTYPE_PrivateInformation = "P";

    /**
   * Get Order.
   *
   * @return Order
   */
  public int getC_Order_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Order_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Payment.
   *
   * @return Payment identifier
   */
  public int getC_Payment_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Payment_ID);
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

    /** IsEscalated AD_Reference_ID=319 */
  public static final int ISESCALATED_AD_Reference_ID = 319;
  /** Yes = Y */
  public static final String ISESCALATED_Yes = "Y";
  /** No = N */
  public static final String ISESCALATED_No = "N";

    /** IsSelfService AD_Reference_ID=319 */
  public static final int ISSELFSERVICE_AD_Reference_ID = 319;
  /** Yes = Y */
  public static final String ISSELFSERVICE_Yes = "Y";
  /** No = N */
  public static final String ISSELFSERVICE_No = "N";

    /**
   * Get Shipment/Receipt.
   *
   * @return Material Shipment Document
   */
  public int getM_InOut_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_InOut_ID);
    if (ii == null) return 0;
    return ii;
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
   * Get Product Used.
   *
   * @return Product/Resource/Service used in Request
   */
  public int getM_ProductSpent_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_ProductSpent_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get RMA.
   *
   * @return Return Material Authorization
   */
  public int getM_RMA_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_RMA_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Null Columns.
   *
   * @param NullColumns Columns with NULL value
   */
  public void setNullColumns(String NullColumns) {
    set_ValueNoCheck(COLUMNNAME_NullColumns, NullColumns);
  }

  /**
   * Get Null Columns.
   *
   * @return Columns with NULL value
   */
  public String getNullColumns() {
    return (String) get_Value(COLUMNNAME_NullColumns);
  }

  /** Priority AD_Reference_ID=154 */
  public static final int PRIORITY_AD_Reference_ID = 154;
  /** High = 3 */
  public static final String PRIORITY_High = "3";
  /** Medium = 5 */
  public static final String PRIORITY_Medium = "5";
  /** Low = 7 */
  public static final String PRIORITY_Low = "7";
  /** Urgent = 1 */
  public static final String PRIORITY_Urgent = "1";
  /** Minor = 9 */
  public static final String PRIORITY_Minor = "9";

    /** PriorityUser AD_Reference_ID=154 */
  public static final int PRIORITYUSER_AD_Reference_ID = 154;
  /** High = 3 */
  public static final String PRIORITYUSER_High = "3";
  /** Medium = 5 */
  public static final String PRIORITYUSER_Medium = "5";
  /** Low = 7 */
  public static final String PRIORITYUSER_Low = "7";
  /** Urgent = 1 */
  public static final String PRIORITYUSER_Urgent = "1";
  /** Minor = 9 */
  public static final String PRIORITYUSER_Minor = "9";

    /**
   * Get Category.
   *
   * @return Request Category
   */
  public int getR_Category_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Category_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Group.
   *
   * @return Request Group
   */
  public int getR_Group_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Group_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Request.
   *
   * @param R_Request_ID Request from a Business Partner or Prospect
   */
  public void setR_Request_ID(int R_Request_ID) {
    if (R_Request_ID < 1) set_ValueNoCheck(COLUMNNAME_R_Request_ID, null);
    else set_ValueNoCheck(COLUMNNAME_R_Request_ID, Integer.valueOf(R_Request_ID));
  }

  /**
   * Get Request.
   *
   * @return Request from a Business Partner or Prospect
   */
  public int getR_Request_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Request_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Request Type.
   *
   * @return Type of request (e.g. Inquiry, Complaint, ..)
   */
  public int getR_RequestType_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_RequestType_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Resolution.
   *
   * @return Request Resolution
   */
  public int getR_Resolution_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Resolution_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Status.
   *
   * @return Request Status
   */
  public int getR_Status_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_Status_ID);
    if (ii == null) return 0;
    return ii;
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

    /** TaskStatus AD_Reference_ID=366 */
  public static final int TASKSTATUS_AD_Reference_ID = 366;
  /** 0% Not Started = 0 */
  public static final String TASKSTATUS_0NotStarted = "0";
  /** 100% Complete = D */
  public static final String TASKSTATUS_100Complete = "D";
  /** 20% Started = 2 */
  public static final String TASKSTATUS_20Started = "2";
  /** 80% Nearly Done = 8 */
  public static final String TASKSTATUS_80NearlyDone = "8";
  /** 40% Busy = 4 */
  public static final String TASKSTATUS_40Busy = "4";
  /** 60% Good Progress = 6 */
  public static final String TASKSTATUS_60GoodProgress = "6";
  /** 90% Finishing = 9 */
  public static final String TASKSTATUS_90Finishing = "9";
  /** 95% Almost Done = A */
  public static final String TASKSTATUS_95AlmostDone = "A";
  /** 99% Cleaning up = C */
  public static final String TASKSTATUS_99CleaningUp = "C";

    @Override
  public int getTableId() {
    return I_R_RequestAction.Table_ID;
  }
}
