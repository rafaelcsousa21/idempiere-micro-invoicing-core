package org.compiere.wf;

import org.compiere.model.I_AD_WF_Node;
import org.compiere.orm.BasePONameValue;
import org.compiere.orm.MTable;
import org.idempiere.common.util.Env;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for AD_WF_Node
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_Node extends BasePONameValue implements I_AD_WF_Node, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_WF_Node(Properties ctx, int AD_WF_Node_ID, String trxName) {
    super(ctx, AD_WF_Node_ID, trxName);
    /**
     * if (AD_WF_Node_ID == 0) { setAction (null); // Z setAD_WF_Node_ID (0); setAD_Workflow_ID (0);
     * setCost (Env.ZERO); setDuration (0); setEntityType (null); // @SQL=select
     * get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual setIsCentrallyMaintained (true); // Y
     * setJoinElement (null); // X setLimit (0); setName (null); setSplitElement (null); // X
     * setValue (null); setWaitingTime (0); setXPosition (0); setYPosition (0); }
     */
  }

  /** Load Constructor */
  public X_AD_WF_Node(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_AD_WF_Node[").append(getId()).append("]");
    return sb.toString();
  }

  /** Action AD_Reference_ID=302 */
  public static final int ACTION_AD_Reference_ID = 302;
  /** Wait (Sleep) = Z */
  public static final String ACTION_WaitSleep = "Z";
  /** User Choice = C */
  public static final String ACTION_UserChoice = "C";
  /** Sub Workflow = F */
  public static final String ACTION_SubWorkflow = "F";
  /** Set Variable = V */
  public static final String ACTION_SetVariable = "V";
  /** User Window = W */
  public static final String ACTION_UserWindow = "W";
  /** User Form = X */
  public static final String ACTION_UserForm = "X";
  /** Apps Task = T */
  public static final String ACTION_AppsTask = "T";
  /** Apps Report = R */
  public static final String ACTION_AppsReport = "R";
  /** Apps Process = P */
  public static final String ACTION_AppsProcess = "P";
  /** Document Action = D */
  public static final String ACTION_DocumentAction = "D";
  /** EMail = M */
  public static final String ACTION_EMail = "M";
  /** User Workbench = B */
  public static final String ACTION_UserWorkbench = "B";
  /** User Info = I */
  public static final String ACTION_UserInfo = "I";
  /**
   * Set Action.
   *
   * @param Action Indicates the Action to be performed
   */
  public void setAction(String Action) {

    set_Value(COLUMNNAME_Action, Action);
  }

  /**
   * Get Action.
   *
   * @return Indicates the Action to be performed
   */
  public String getAction() {
    return (String) get_Value(COLUMNNAME_Action);
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
   * Get Special Form.
   *
   * @return Special Form
   */
  public int getAD_Form_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Form_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Image.
   *
   * @return Image or Icon
   */
  public int getAD_Image_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Image_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Info Window.
   *
   * @return Info and search/select Window
   */
  public int getAD_InfoWindow_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_InfoWindow_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Process.
   *
   * @return Process or Report
   */
  public int getAD_Process_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Process_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get OS Task.
   *
   * @return Operation System Task
   */
  public int getAD_Task_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Task_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Workflow Block.
   *
   * @return Workflow Transaction Execution Block
   */
  public int getAD_WF_Block_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_WF_Block_ID);
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
   * Get Window.
   *
   * @return Data entry or display window
   */
  public int getAD_Window_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Window_ID);
    if (ii == null) return 0;
    return ii;
  }

  public org.compiere.model.I_AD_Workflow getAD_Workflow() throws RuntimeException {
    return (org.compiere.model.I_AD_Workflow)
        MTable.get(getCtx(), org.compiere.model.I_AD_Workflow.Table_Name)
            .getPO(getAD_Workflow_ID(), null);
  }

  /**
   * Set Workflow.
   *
   * @param AD_Workflow_ID Workflow or combination of tasks
   */
  public void setAD_Workflow_ID(int AD_Workflow_ID) {
    if (AD_Workflow_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_Workflow_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
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
   * Set Attribute Name.
   *
   * @param AttributeName Name of the Attribute
   */
  public void setAttributeName(String AttributeName) {
    set_Value(COLUMNNAME_AttributeName, AttributeName);
  }

  /**
   * Get Attribute Name.
   *
   * @return Name of the Attribute
   */
  public String getAttributeName() {
    return (String) get_Value(COLUMNNAME_AttributeName);
  }

    /**
   * Get Attribute Value.
   *
   * @return Value of the Attribute
   */
  public String getAttributeValue() {
    return (String) get_Value(COLUMNNAME_AttributeValue);
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
   * Get Document Action.
   *
   * @return The targeted status of the document
   */
  public String getDocAction() {
    return (String) get_Value(COLUMNNAME_DocAction);
  }

  /**
   * Set Duration.
   *
   * @param Duration Normal Duration in Duration Unit
   */
  public void setDuration(int Duration) {
    set_Value(COLUMNNAME_Duration, Integer.valueOf(Duration));
  }

  /**
   * Get Duration.
   *
   * @return Normal Duration in Duration Unit
   */
  public int getDuration() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Duration);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Dynamic Priority Change.
   *
   * @return Change of priority when Activity is suspended waiting for user
   */
  public BigDecimal getDynPriorityChange() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_DynPriorityChange);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /** DynPriorityUnit AD_Reference_ID=221 */
  public static final int DYNPRIORITYUNIT_AD_Reference_ID = 221;
  /** Minute = M */
  public static final String DYNPRIORITYUNIT_Minute = "M";
  /** Hour = H */
  public static final String DYNPRIORITYUNIT_Hour = "H";
  /** Day = D */
  public static final String DYNPRIORITYUNIT_Day = "D";

    /**
   * Get Dynamic Priority Unit.
   *
   * @return Change of priority when Activity is suspended waiting for user
   */
  public String getDynPriorityUnit() {
    return (String) get_Value(COLUMNNAME_DynPriorityUnit);
  }

    /**
   * Get EMail Address.
   *
   * @return Electronic Mail Address
   */
  public String getEMail() {
    return (String) get_Value(COLUMNNAME_EMail);
  }

  /** EMailRecipient AD_Reference_ID=363 */
  public static final int EMAILRECIPIENT_AD_Reference_ID = 363;
  /** Document Owner = D */
  public static final String EMAILRECIPIENT_DocumentOwner = "D";
  /** Document Business Partner = B */
  public static final String EMAILRECIPIENT_DocumentBusinessPartner = "B";
  /** WF Responsible = R */
  public static final String EMAILRECIPIENT_WFResponsible = "R";

    /**
   * Get EMail Recipient.
   *
   * @return Recipient of the EMail
   */
  public String getEMailRecipient() {
    return (String) get_Value(COLUMNNAME_EMailRecipient);
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

    /** FinishMode AD_Reference_ID=303 */
  public static final int FINISHMODE_AD_Reference_ID = 303;
  /** Automatic = A */
  public static final String FINISHMODE_Automatic = "A";
  /** Manual = M */
  public static final String FINISHMODE_Manual = "M";

    /**
   * Get Comment/Help.
   *
   * @return Comment or Hint
   */
  public String getHelp() {
    return (String) get_Value(COLUMNNAME_Help);
  }

  /**
   * Set Centrally maintained.
   *
   * @param IsCentrallyMaintained Information maintained in System Element table
   */
  public void setIsCentrallyMaintained(boolean IsCentrallyMaintained) {
    set_Value(COLUMNNAME_IsCentrallyMaintained, Boolean.valueOf(IsCentrallyMaintained));
  }

    /** JoinElement AD_Reference_ID=301 */
  public static final int JOINELEMENT_AD_Reference_ID = 301;
  /** AND = A */
  public static final String JOINELEMENT_AND = "A";
  /** XOR = X */
  public static final String JOINELEMENT_XOR = "X";
  /**
   * Set Join Element.
   *
   * @param JoinElement Semantics for multiple incoming Transitions
   */
  public void setJoinElement(String JoinElement) {

    set_Value(COLUMNNAME_JoinElement, JoinElement);
  }

  /**
   * Get Join Element.
   *
   * @return Semantics for multiple incoming Transitions
   */
  public String getJoinElement() {
    return (String) get_Value(COLUMNNAME_JoinElement);
  }

  /**
   * Set Duration Limit.
   *
   * @param Limit Maximum Duration in Duration Unit
   */
  public void setLimit(int Limit) {
    set_Value(COLUMNNAME_Limit, Integer.valueOf(Limit));
  }

  /**
   * Get Duration Limit.
   *
   * @return Maximum Duration in Duration Unit
   */
  public int getLimit() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Limit);
    if (ii == null) return 0;
    return ii;
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

    /**
   * Get Mail Template.
   *
   * @return Text templates for mailings
   */
  public int getR_MailText_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_R_MailText_ID);
    if (ii == null) return 0;
    return ii;
  }

    /** SplitElement AD_Reference_ID=301 */
  public static final int SPLITELEMENT_AD_Reference_ID = 301;
  /** AND = A */
  public static final String SPLITELEMENT_AND = "A";
  /** XOR = X */
  public static final String SPLITELEMENT_XOR = "X";
  /**
   * Set Split Element.
   *
   * @param SplitElement Semantics for multiple outgoing Transitions
   */
  public void setSplitElement(String SplitElement) {

    set_Value(COLUMNNAME_SplitElement, SplitElement);
  }

  /**
   * Get Split Element.
   *
   * @return Semantics for multiple outgoing Transitions
   */
  public String getSplitElement() {
    return (String) get_Value(COLUMNNAME_SplitElement);
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

  /** StartMode AD_Reference_ID=303 */
  public static final int STARTMODE_AD_Reference_ID = 303;
  /** Automatic = A */
  public static final String STARTMODE_Automatic = "A";
  /** Manual = M */
  public static final String STARTMODE_Manual = "M";

    /** SubflowExecution AD_Reference_ID=307 */
  public static final int SUBFLOWEXECUTION_AD_Reference_ID = 307;
  /** Asynchronously = A */
  public static final String SUBFLOWEXECUTION_Asynchronously = "A";
  /** Synchronously = S */
  public static final String SUBFLOWEXECUTION_Synchronously = "S";

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
   * Set Waiting Time.
   *
   * @param WaitingTime Workflow Simulation Waiting time
   */
  public void setWaitingTime(int WaitingTime) {
    set_Value(COLUMNNAME_WaitingTime, Integer.valueOf(WaitingTime));
  }

    /**
   * Get Wait Time.
   *
   * @return Time in minutes to wait (sleep)
   */
  public int getWaitTime() {
    Integer ii = (Integer) get_Value(COLUMNNAME_WaitTime);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Workflow.
   *
   * @return Workflow or tasks
   */
  public int getWorkflow_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Workflow_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set X Position.
   *
   * @param XPosition Absolute X (horizontal) position in 1/72 of an inch
   */
  public void setXPosition(int XPosition) {
    set_Value(COLUMNNAME_XPosition, Integer.valueOf(XPosition));
  }

  /**
   * Get X Position.
   *
   * @return Absolute X (horizontal) position in 1/72 of an inch
   */
  public int getXPosition() {
    Integer ii = (Integer) get_Value(COLUMNNAME_XPosition);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Y Position.
   *
   * @param YPosition Absolute Y (vertical) position in 1/72 of an inch
   */
  public void setYPosition(int YPosition) {
    set_Value(COLUMNNAME_YPosition, Integer.valueOf(YPosition));
  }

  /**
   * Get Y Position.
   *
   * @return Absolute Y (vertical) position in 1/72 of an inch
   */
  public int getYPosition() {
    Integer ii = (Integer) get_Value(COLUMNNAME_YPosition);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_AD_WF_Node.Table_ID;
  }
}
