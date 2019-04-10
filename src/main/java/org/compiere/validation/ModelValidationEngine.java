package org.compiere.validation;

import org.compiere.accounting.MClientKt;
import org.compiere.model.ClientWithAccounting;
import org.compiere.model.IFact;
import org.compiere.model.IPODoc;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.compiere.orm.Query;
import org.compiere.process.ImportProcess;
import org.compiere.process.ImportValidator;
import org.compiere.rule.MRule;
import org.idempiere.common.util.CLogger;
import org.idempiere.icommon.model.IPO;

import javax.script.ScriptEngine;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;

/**
 * Model Validation Engine
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>FR [ 1670025 ] ModelValidator.afterLoadPreferences will be useful
 * <li>BF [ 1679692 ] fireDocValidate doesn't treat exceptions as errors
 * <li>FR [ 1724662 ] Support Email should contain model validators info
 * <li>FR [ 2788276 ] Data Import Validator
 * https://sourceforge.net/tracker/?func=detail&aid=2788276&group_id=176962&atid=879335
 * <li>BF [ 2804135 ] Global FactsValidator are not invoked
 * https://sourceforge.net/tracker/?func=detail&aid=2804135&group_id=176962&atid=879332
 * <li>BF [ 2819617 ] NPE if script validator rule returns null
 * https://sourceforge.net/tracker/?func=detail&aid=2819617&group_id=176962&atid=879332
 * @author victor.perez@e-evolution.com, www.e-evolution.com
 * <li>BF [ 2947607 ] Model Validator Engine duplicate listeners
 * @version $Id: ModelValidationEngine.java,v 1.2 2006/07/30 00:58:38 jjanke Exp $
 */
public class ModelValidationEngine {

    /**
     * Engine Singleton
     */
    private static ModelValidationEngine s_engine = null;
    /* flag to indicate a missing model validation class */
    private static String missingModelValidationMessage = "";
    /**
     * Logger
     */
    private static CLogger log = CLogger.getCLogger(ModelValidationEngine.class);
    /**
     * Validators
     */
    private ArrayList<ModelValidator> m_validators = new ArrayList<ModelValidator>();
    /**
     * Model Change Listeners
     */
    private Hashtable<String, ArrayList<ModelValidator>> m_modelChangeListeners =
            new Hashtable<String, ArrayList<ModelValidator>>();
    /**
     * Document Validation Listeners
     */
    private Hashtable<String, ArrayList<ModelValidator>> m_docValidateListeners =
            new Hashtable<String, ArrayList<ModelValidator>>();
    /**
     * Accounting Facts Validation Listeners
     */
    private Hashtable<String, ArrayList<FactsValidator>> m_factsValidateListeners =
            new Hashtable<String, ArrayList<FactsValidator>>();
    /**
     * Data Import Validation Listeners
     */
    private Hashtable<String, ArrayList<ImportValidator>> m_impValidateListeners =
            new Hashtable<String, ArrayList<ImportValidator>>();
    //	/** Change Support			*/
    //	private VetoableChangeSupport m_changeSupport = new VetoableChangeSupport(this);
    private ArrayList<ModelValidator> m_globalValidators = new ArrayList<ModelValidator>();

    /**
     * ************************************************************************ Constructor. Creates
     * Model Validators
     */
    private ModelValidationEngine() {
        super();
        // Load global validators

        MTable table = MTable.get(X_AD_ModelValidator.Table_ID);
        Query query = table.createQuery("IsActive='Y'");
        query.setOrderBy("SeqNo");
        try {
            List<X_AD_ModelValidator> entityTypes = query.list();
            for (X_AD_ModelValidator entityType : entityTypes) {
                String className = entityType.getModelValidationClass();
                if (className == null || className.length() == 0) continue;
                loadValidatorClass(null, className);
            }
        } catch (Exception e) {
            // logging to db will try to init ModelValidationEngine again!
            e.printStackTrace();
            missingModelValidationMessage =
                    missingModelValidationMessage + e.toString() + " global" + '\n';
        }

        // Go through all Clients and start Validators
        List<ClientWithAccounting> clients = MClientKt.getAllClientsWithAccounting();
        for (ClientWithAccounting client : clients) {
            String classNames = client.getModelValidationClasses();
            if (classNames == null || classNames.length() == 0) continue;
            loadValidatorClasses(client, classNames);
        }
    } //	ModelValidatorEngine

    /**
     * Get Singleton
     *
     * @return engine
     */
    public static synchronized ModelValidationEngine get() {
        if (s_engine == null) s_engine = new ModelValidationEngine();
        return s_engine;
    } //	get

    /**
     * @param validatorId Java class name or equinox extension Id
     * @return ModelValidator instance of null if validatorId not found
     */
    public static ModelValidator getModelValidator(String validatorId) {
        return null;
    }

    private void loadValidatorClasses(ClientWithAccounting client, String classNames) {
        StringTokenizer st = new StringTokenizer(classNames, ";");
        while (st.hasMoreTokens()) {
            String className = null;
            try {
                className = st.nextToken();
                if (className == null) continue;
                className = className.trim();
                if (className.length() == 0) continue;
                //
                loadValidatorClass(client, className);
            } catch (Exception e) {
                // logging to db will try to init ModelValidationEngine again!
                e.printStackTrace();
                missingModelValidationMessage =
                        missingModelValidationMessage + e.toString() + " on client " + client.getName() + '\n';
            }
        }
    }

    private void loadValidatorClass(ClientWithAccounting client, String className) {
        try {
            //
            ModelValidator validator = null;
            validator = getModelValidator(className);

            if (validator == null) {
                missingModelValidationMessage =
                        missingModelValidationMessage
                                + " Missing class "
                                + className
                                + (client != null ? (" on client " + client.getName()) : " global")
                                + '\n';
            } else {
                initialize(validator, client);
            }
        } catch (Exception e) {
            // logging to db will try to init ModelValidationEngine again!
            e.printStackTrace();
            missingModelValidationMessage =
                    missingModelValidationMessage
                            + e.toString()
                            + (client != null ? (" on client " + client.getName()) : " global")
                            + '\n';
        }
    }

    /**
     * Initialize and add validator
     *
     * @param validator
     * @param client
     */
    private void initialize(ModelValidator validator, ClientWithAccounting client) {
        if (client == null) m_globalValidators.add(validator);
        m_validators.add(validator);
        validator.initialize(this, client);
    } //	initialize

    /**
     * Fire Document Validation. Call docValidate method of added validators
     *
     * @param po persistent objects
     * @return error message or null
     */
    public String fireDocValidate(IPODoc po, int docTiming) {
        if (po == null) return null;

        String propertyName = po.getTableName() + "*";
        ArrayList<ModelValidator> list = m_docValidateListeners.get(propertyName);
        if (list != null) {
            // ad_entitytype.modelvalidationclasses
            String error = fireDocValidate(po, docTiming, list);
            if (error != null && error.length() > 0) return error;
        }

        propertyName = po.getTableName() + po.getClientId();
        list = m_docValidateListeners.get(propertyName);
        if (list != null) {
            // ad_client.modelvalidationclasses
            String error = fireDocValidate(po, docTiming, list);
            if (error != null && error.length() > 0) return error;
        }

        // now process the script model validator for this event
        List<MTableScriptValidator> scriptValidators =
                MTableScriptValidator.getModelValidatorRules(
                        po.getTableId(), ModelValidator.documentEventValidators[docTiming]);
        if (scriptValidators != null) {
            for (MTableScriptValidator scriptValidator : scriptValidators) {
                MRule rule = MRule.get(scriptValidator.getRuleId());
                // currently just JSR 223 supported
                if (rule != null
                        && rule.isActive()
                        && rule.getRuleType().equals(MRule.RULETYPE_JSR223ScriptingAPIs)
                        && rule.getEventType().equals(MRule.EVENTTYPE_ModelValidatorDocumentEvent)) {
                    String error;
                    try {
                        ScriptEngine engine = rule.getScriptEngine();

                        // now add the method arguments to the engine
                        engine.put(MRule.ARGUMENTS_PREFIX + "PO", po);
                        engine.put(MRule.ARGUMENTS_PREFIX + "Type", docTiming);
                        engine.put(
                                MRule.ARGUMENTS_PREFIX + "Event",
                                ModelValidator.documentEventValidators[docTiming]);

                        Object retval = engine.eval(rule.getScript());
                        error = (retval == null ? "" : retval.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        error = e.toString();
                    }
                    if (error != null && error.length() > 0) return error;
                }
            }
        }

    /*
    //now process osgi event handlers
    IEvent event = EventManager.newEvent(ModelValidator.documentEventTopics[docTiming],
    		new EventProperty(EventManager.EVENT_DATA, po), new EventProperty("tableName", po.getTableName()));
    EventManager.getInstance().sendEvent(event);
    @SuppressWarnings("unchecked")
    List<String> errors = (List<String>) event.getProperty(IEventManager.EVENT_ERROR_MESSAGES);
    if (errors != null && !errors.isEmpty())
    	return errors.get(0);*/

        return null;
    } //	fireDocValidate

    private String fireDocValidate(IPODoc po, int docTiming, ArrayList<ModelValidator> list) {
        for (ModelValidator modelValidator : list) {
            ModelValidator validator;
            try {
                validator = modelValidator;
                if (validator.getClientId() == po.getClientId()
                        || m_globalValidators.contains(validator)) {
                    String error = validator.docValidate(po, docTiming);
                    if (error != null && error.length() > 0) {
                        if (log.isLoggable(Level.FINE)) {
                            log.log(Level.FINE, "po=" + po + " validator=" + validator + " timing=" + docTiming);
                        }
                        return error;
                    }
                }
            } catch (Exception e) {
                // log the stack trace
                log.log(Level.SEVERE, e.getLocalizedMessage(), e);
                // Exeptions are errors and should stop the document processing - teo_sarca [ 1679692 ]
                String error = e.getLocalizedMessage();
                if (error == null) error = e.toString();
                return error;
            }
        }
        return null;
    }

    /**
     * Fire Accounting Facts Validation. Call factsValidate method of added validators
     *
     * @param schema
     * @param facts
     * @param po
     * @return error message or null
     */
    public String fireFactsValidate(I_C_AcctSchema schema, List<IFact> facts, IPODoc po) {
        if (schema == null || facts == null || po == null) return null;

        String propertyName = po.getTableName() + "*";
        ArrayList<FactsValidator> list = m_factsValidateListeners.get(propertyName);
        if (list != null) {
            // ad_entitytype.modelvalidationclasses
            String error = fireFactsValidate(schema, facts, po, list);
            if (error != null && error.length() > 0) return error;
        }

        propertyName = po.getTableName() + po.getClientId();
        list = m_factsValidateListeners.get(propertyName);
        if (list != null) {
            // ad_client.modelvalidationclasses
            String error = fireFactsValidate(schema, facts, po, list);
            if (error != null && error.length() > 0) return error;
        }

    /*
    //process osgi event handlers
    FactsEventData eventData = new FactsEventData(schema, facts, po);
    IEvent event = EventManager.newEvent(IEventTopics.ACCT_FACTS_VALIDATE,
    		new EventProperty(EventManager.EVENT_DATA, eventData), new EventProperty("tableName", po.getTableName()));
    EventManager.getInstance().sendEvent(event);
    @SuppressWarnings("unchecked")
    List<String> errors = (List<String>) event.getProperty(IEventManager.EVENT_ERROR_MESSAGES);
    if (errors != null && !errors.isEmpty())
    	return errors.get(0);*/

        return null;
    } //	fireFactsValidate

    private String fireFactsValidate(
            I_C_AcctSchema schema, List<IFact> facts, IPO po, ArrayList<FactsValidator> list) {
        for (int i = 0; i < list.size(); i++) {
            FactsValidator validator = null;
            try {
                validator = list.get(i);
                if (validator.getClientId() == po.getClientId()
                        || (validator instanceof ModelValidator
                        && m_globalValidators.contains(validator))) {
                    String error = validator.factsValidate(schema, facts, po);
                    if (error != null && error.length() > 0) {
                        if (log.isLoggable(Level.FINE)) {
                            log.log(Level.FINE, "po=" + po + " schema=" + schema + " validator=" + validator);
                        }
                        return error;
                    }
                }
            } catch (Exception e) {
                // log the stack trace
                log.log(Level.SEVERE, e.getLocalizedMessage(), e);
                // Exeptions are errors and should stop the document processing - teo_sarca [ 1679692 ]
                String error = e.getLocalizedMessage();
                if (error == null) error = e.toString();
                return error;
            }
        }
        return null;
    }

    /**
     * Fire Import Validation. Call {@link ImportValidator#validate(ImportProcess, Object, Object,
     * int)} or registered validators.
     *
     * @param process     import process
     * @param importModel import record (e.g. X_I_BPartner)
     * @param targetModel target model (e.g. MBPartner, MBPartnerLocation, MUser)
     * @param timing      see ImportValidator.TIMING_* constants
     */
    public void fireImportValidate(
            ImportProcess process, PO importModel, PO targetModel, int timing) {

        String propertyName = process.getImportTableName() + "*";
        ArrayList<ImportValidator> list = m_impValidateListeners.get(propertyName);
        if (list != null) {
            for (ImportValidator validator : list) {
                validator.validate(process, importModel, targetModel, timing);
            }
        }

        // osgi event handler
    }

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("ModelValidationEngine[");
        sb.append("Validators=#")
                .append(m_validators.size())
                .append(", ModelChange=#")
                .append(m_modelChangeListeners.size())
                .append(", DocValidate=#")
                .append(m_docValidateListeners.size())
                .append("]");
        return sb.toString();
    } //	toString

} //	ModelValidatorEngine
