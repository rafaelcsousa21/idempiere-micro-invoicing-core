package org.compiere.wf;

import org.compiere.model.I_AD_Workflow;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for AD_Workflow
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Workflow extends BasePONameValue implements I_AD_Workflow, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_Workflow(Properties ctx, int AD_Workflow_ID, String trxName) {
    super(ctx, AD_Workflow_ID, trxName);
  }

  /** Load Constructor */
  public X_AD_Workflow(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_AD_Workflow[").append(getId()).append("]");
    return sb.toString();
  }

  /** AccessLevel AD_Reference_ID=5 */
  public static final int ACCESSLEVEL_AD_Reference_ID = 5;
  /** Organization = 1 */
  public static final String ACCESSLEVEL_Organization = "1";
  /** Client+Organization = 3 */
  public static final String ACCESSLEVEL_ClientPlusOrganization = "3";
  /** System only = 4 */
  public static final String ACCESSLEVEL_SystemOnly = "4";
  /** All = 7 */
  public static final String ACCESSLEVEL_All = "7";
  /** System+Client = 6 */
  public static final String ACCESSLEVEL_SystemPlusClient = "6";
  /** Client only = 2 */
  public static final String ACCESSLEVEL_ClientOnly = "2";
  /**
   * Set Data Access Level.
   *
   * @param AccessLevel Access Level required
   */
  public void setWFAccessLevel(String AccessLevel) {

    set_Value(COLUMNNAME_AccessLevel, AccessLevel);
  }

    /**
   * Get Context Help.
   *
   * @return Context Help
   */
  public int getAD_CtxHelp_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_CtxHelp_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Table.
   *
   * @param AD_Table_ID Database Table information
   */
  public void setAD_Table_ID(int AD_Table_ID) {
    if (AD_Table_ID < 1) set_Value(COLUMNNAME_AD_Table_ID, null);
    else set_Value(COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
  }

  /**
   * Get Table.
   *
   * @return Database Table information
   */
  public int getAD_Table_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Table_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Node.
   *
   * @return Workflow Node (activity), step or process
   */
  public int getAD_WF_Node_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_Node_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Workflow Responsible.
   *
   * @return Responsible for Workflow Execution
   */
  public int getAD_WF_Responsible_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_Responsible_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Workflow.
   *
   * @return Workflow or combination of tasks
   */
  public int getAD_Workflow_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Workflow_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Workflow Processor.
   *
   * @return Workflow Processor Server
   */
  public int getAD_WorkflowProcessor_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WorkflowProcessor_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Author.
   *
   * @param Author Author/Creator of the Entity
   */
  public void setAuthor(String Author) {
    set_Value(COLUMNNAME_Author, Author);
  }

    /**
   * Set Cost.
   *
   * @param Cost Cost information
   */
  public void setCost(BigDecimal Cost) {
    set_Value(COLUMNNAME_Cost, Cost);
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
   * Get Document Value Logic.
   *
   * @return Logic to determine Workflow Start - If true, a workflow process is started for the
   *     document
   */
  public String getDocValueLogic() {
    return (String) get_Value(COLUMNNAME_DocValueLogic);
  }

  /**
   * Set Duration.
   *
   * @param Duration Normal Duration in Duration Unit
   */
  public void setDuration(int Duration) {
    set_Value(COLUMNNAME_Duration, Integer.valueOf(Duration));
  }

    /** DurationUnit AD_Reference_ID=299 */
  public static final int DURATIONUNIT_AD_Reference_ID = 299;
  /** Year = Y */
  public static final String DURATIONUNIT_Year = "Y";
  /** Month = M */
  public static final String DURATIONUNIT_Month = "M";
  /** Day = D */
  public static final String DURATIONUNIT_Day = "D";
  /** hour = h */
  public static final String DURATIONUNIT_Hour = "h";
  /** minute = m */
  public static final String DURATIONUNIT_Minute = "m";
  /** second = s */
  public static final String DURATIONUNIT_Second = "s";
  /**
   * Set Duration Unit.
   *
   * @param DurationUnit Unit of Duration
   */
  public void setDurationUnit(String DurationUnit) {

    set_Value(COLUMNNAME_DurationUnit, DurationUnit);
  }

  /**
   * Get Duration Unit.
   *
   * @return Unit of Duration
   */
  public String getDurationUnit() {
    return (String) get_Value(COLUMNNAME_DurationUnit);
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
   * Get Comment/Help.
   *
   * @return Comment or Hint
   */
  public String getHelp() {
    return (String) get_Value(COLUMNNAME_Help);
  }

  /**
   * Set Beta Functionality.
   *
   * @param IsBetaFunctionality This functionality is considered Beta
   */
  public void setIsBetaFunctionality(boolean IsBetaFunctionality) {
    set_Value(COLUMNNAME_IsBetaFunctionality, Boolean.valueOf(IsBetaFunctionality));
  }

    /**
   * Set Default.
   *
   * @param IsDefault Default value
   */
  public void setIsDefault(boolean IsDefault) {
    set_Value(COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
  }

    /**
   * Set Valid.
   *
   * @param IsValid Element is valid
   */
  public void setIsValid(boolean IsValid) {
    set_Value(COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
  }

  /**
   * Get Valid.
   *
   * @return Element is valid
   */
  public boolean isValid() {
    Object oo = get_Value(COLUMNNAME_IsValid);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Priority.
   *
   * @return Indicates if this request is of a high, medium or low priority.
   */
  public int getPriority() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Priority);
    if (ii == null) return 0;
    return ii;
  }

  /** ProcessType AD_Reference_ID=53224 */
  public static final int PROCESSTYPE_AD_Reference_ID = 53224;
  /** Batch Flow = BF */
  public static final String PROCESSTYPE_BatchFlow = "BF";
  /** Continuous Flow = CF */
  public static final String PROCESSTYPE_ContinuousFlow = "CF";
  /** Dedicate Repetititive Flow = DR */
  public static final String PROCESSTYPE_DedicateRepetititiveFlow = "DR";
  /** Job Shop = JS */
  public static final String PROCESSTYPE_JobShop = "JS";
  /** Mixed Repetitive Flow = MR */
  public static final String PROCESSTYPE_MixedRepetitiveFlow = "MR";
  /** Plant = PL */
  public static final String PROCESSTYPE_Plant = "PL";

    /** PublishStatus AD_Reference_ID=310 */
  public static final int PUBLISHSTATUS_AD_Reference_ID = 310;
  /** Released = R */
  public static final String PUBLISHSTATUS_Released = "R";
  /** Test = T */
  public static final String PUBLISHSTATUS_Test = "T";
  /** Under Revision = U */
  public static final String PUBLISHSTATUS_UnderRevision = "U";
  /** Void = V */
  public static final String PUBLISHSTATUS_Void = "V";
  /**
   * Set Publication Status.
   *
   * @param PublishStatus Status of Publication
   */
  public void setPublishStatus(String PublishStatus) {

    set_Value(COLUMNNAME_PublishStatus, PublishStatus);
  }

    /**
   * Get Resource.
   *
   * @return Resource
   */
  public int getS_Resource_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_S_Resource_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Valid from.
   *
   * @return Valid from including this date (first day)
   */
  public Timestamp getValidFrom() {
    return (Timestamp) get_Value(COLUMNNAME_ValidFrom);
  }

    /**
   * Get Valid to.
   *
   * @return Valid to including this date (last day)
   */
  public Timestamp getValidTo() {
    return (Timestamp) get_Value(COLUMNNAME_ValidTo);
  }

  /**
   * Set Version.
   *
   * @param Version Version of the table definition
   */
  public void setVersion(int Version) {
    set_Value(COLUMNNAME_Version, Integer.valueOf(Version));
  }

    /**
   * Set Waiting Time.
   *
   * @param WaitingTime Workflow Simulation Waiting time
   */
  public void setWaitingTime(int WaitingTime) {
    set_Value(COLUMNNAME_WaitingTime, Integer.valueOf(WaitingTime));
  }

    /** WorkflowType AD_Reference_ID=328 */
  public static final int WORKFLOWTYPE_AD_Reference_ID = 328;
  /** General = G */
  public static final String WORKFLOWTYPE_General = "G";
  /** Document Process = P */
  public static final String WORKFLOWTYPE_DocumentProcess = "P";
  /** Document Value = V */
  public static final String WORKFLOWTYPE_DocumentValue = "V";
  /** Manufacturing = M */
  public static final String WORKFLOWTYPE_Manufacturing = "M";
  /** Quality = Q */
  public static final String WORKFLOWTYPE_Quality = "Q";
  /** Wizard = W */
  public static final String WORKFLOWTYPE_Wizard = "W";

    /**
   * Get Workflow Type.
   *
   * @return Type of Workflow
   */
  public String getWorkflowType() {
    return (String) get_Value(COLUMNNAME_WorkflowType);
  }

  /**
   * Set Working Time.
   *
   * @param WorkingTime Workflow Simulation Execution Time
   */
  public void setWorkingTime(int WorkingTime) {
    set_Value(COLUMNNAME_WorkingTime, Integer.valueOf(WorkingTime));
  }

    @Override
  public int getTableId() {
    return I_AD_Workflow.Table_ID;
  }
}
