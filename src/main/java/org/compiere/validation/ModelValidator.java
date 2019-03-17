package org.compiere.validation;

import org.compiere.model.I_AD_Client;
import org.idempiere.icommon.model.IPO;

/**
 * Model Validator
 *
 * @author Jorg Janke
 * @version $Id: ModelValidator.java,v 1.2 2006/07/30 00:58:18 jjanke Exp $
 * <p>2007/02/26 laydasalasc - globalqss - Add new timings for all before/after events on
 * documents
 */
public interface ModelValidator {

    // Correlation between constant events and list of event script model validators
    String[] tableEventValidators =
            new String[]{
                    "", // 0
                    X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableBeforeNew, // TYPE_BEFORE_NEW = 1
                    X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableBeforeChange, // TYPE_BEFORE_CHANGE = 2
                    X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableBeforeDelete, // TYPE_BEFORE_DELETE = 3
                    X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableAfterNew, // TYPE_AFTER_NEW = 4
                    X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableAfterChange, // TYPE_AFTER_CHANGE = 5
                    X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_TableAfterDelete, // TYPE_AFTER_DELETE = 6
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_TableAfterNewReplication, // TYPE_AFTER_NEW_REPLICATION = 7
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_TableAfterChangeReplication, // TYPE_AFTER_CHANGE_REPLICATION = 8
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_TableBeforeDeleteReplication // TYPE_BEFORE_DELETE_REPLICATION = 9
            };

    /**
     * Called before document is prepared
     */
    int TIMING_BEFORE_PREPARE = 1;

    /**
     * Called before document is void
     */
    int TIMING_BEFORE_VOID = 2;
    /**
     * Called before document is close
     */
    int TIMING_BEFORE_CLOSE = 3;
    /**
     * Called before document is reactivate
     */
    int TIMING_BEFORE_REACTIVATE = 4;
    /**
     * Called before document is reversecorrect
     */
    int TIMING_BEFORE_REVERSECORRECT = 5;
    /**
     * Called before document is reverseaccrual
     */
    int TIMING_BEFORE_REVERSEACCRUAL = 6;
    /**
     * Called before document is completed
     */
    int TIMING_BEFORE_COMPLETE = 7;
    /**
     * Called after document is prepared
     */
    int TIMING_AFTER_PREPARE = 8;
    /**
     * Called after document is completed
     */
    int TIMING_AFTER_COMPLETE = 9;

    /**
     * Called after document is void
     */
    int TIMING_AFTER_VOID = 10;
    /**
     * Called after document is closed
     */
    int TIMING_AFTER_CLOSE = 11;
    /**
     * Called after document is reactivated
     */
    int TIMING_AFTER_REACTIVATE = 12;
    /**
     * Called after document is reversecorrect
     */
    int TIMING_AFTER_REVERSECORRECT = 13;
    /**
     * Called after document is reverseaccrual
     */
    int TIMING_AFTER_REVERSEACCRUAL = 14;
    /**
     * Called before document is posted
     */
    int TIMING_BEFORE_POST = 15;
    /**
     * Called after document is posted
     */
    int TIMING_AFTER_POST = 16;

    // Correlation between constant events and list of event script model validators
    String[] documentEventValidators =
            new String[]{
                    "", // 0
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentBeforePrepare, // TIMING_BEFORE_PREPARE = 1
                    X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentBeforeVoid, // TIMING_BEFORE_VOID = 2
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentBeforeClose, // TIMING_BEFORE_CLOSE = 3
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentBeforeReactivate, // TIMING_BEFORE_REACTIVATE = 4
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentBeforeReverseCorrect, // TIMING_BEFORE_REVERSECORRECT = 5
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentBeforeReverseAccrual, // TIMING_BEFORE_REVERSEACCRUAL = 6
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentBeforeComplete, // TIMING_BEFORE_COMPLETE = 7
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentAfterPrepare, // TIMING_AFTER_PREPARE = 8
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentAfterComplete, // TIMING_AFTER_COMPLETE = 9
                    X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentAfterVoid, // TIMING_AFTER_VOID = 10
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentAfterClose, // TIMING_AFTER_CLOSE = 11
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentAfterReactivate, // TIMING_AFTER_REACTIVATE = 12
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentAfterReverseCorrect, // TIMING_AFTER_REVERSECORRECT = 13
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentAfterReverseAccrual, // TIMING_AFTER_REVERSEACCRUAL = 14
                    X_AD_Table_ScriptValidator
                            .EVENTMODELVALIDATOR_DocumentBeforePost, // TIMING_BEFORE_POST = 15
                    X_AD_Table_ScriptValidator.EVENTMODELVALIDATOR_DocumentAfterPost // TIMING_AFTER_POST = 16
            };

    /**
     * Initialize Validation
     *
     * @param engine validation engine
     * @param client client
     */
    void initialize(ModelValidationEngine engine, I_AD_Client client);

    /**
     * Get Client to be monitored
     *
     * @return clientId
     */
    int getClientId();

    /**
     * User logged in Called before preferences are set
     *
     * @param AD_Org_ID  org
     * @param AD_Role_ID role
     * @param AD_User_ID user
     * @return error message or null
     */
    String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID);

    /**
     * Model Change of a monitored Table. Called after PO.beforeSave/PO.beforeDelete when you called
     * addModelChange for the table
     *
     * @param po   persistent object
     * @param type TYPE_
     * @return error message or null
     * @throws Exception if the recipient wishes the change to be not accept.
     */
    String modelChange(IPO po, int type) throws Exception;

    /**
     * Validate Document. Called as first step of DocAction.prepareIt or at the end of
     * DocAction.completeIt when you called addDocValidate for the table. Note that totals, etc. may
     * not be correct before the prepare stage.
     *
     * @param po     persistent object
     * @param timing see TIMING_ constants
     * @return error message or null - if not null, the document will be marked as Invalid.
     */
    String docValidate(IPO po, int timing);
} //	ModelValidator
