package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.bo.X_C_Currency;
import org.compiere.model.I_C_Currency;
import org.compiere.model.TypedQuery;
import org.compiere.orm.MTable;
import software.hsharp.core.orm.MBaseTableKt;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class MBankStatementLoader extends X_C_BankStatementLoader {
    /**
     *
     */
    private static final long serialVersionUID = -4096456424277340847L;

    /**
     * Number of statement lines imported
     */
    private int loadCount = 0;

    /**
     * Message will be handled by Adempiere (e.g. translated)
     */
    private String errorMessage = "";

    /**
     * Additional error description
     */
    private String errorDescription = "";

    /**
     * Loader object to use
     */
    private BankStatementLoaderInterface m_loader = null;

    /**
     * Map of currency ISO-Codes to lookup id
     */
    private HashMap<String, Integer> currencyMap;

    /**
     * Create a Statement Loader Added for compatibility with new PO infrastructure (bug# 968136)
     *
     * @param C_BankStatementLoader_ID loader to use
     */
    public MBankStatementLoader(int C_BankStatementLoader_ID) {
        super(C_BankStatementLoader_ID);
        init(null);
    } //	MBankStatementLoader

    /**
     * Create a Statement Loader
     *
     * @param C_BankStatementLoader_ID loader to use
     * @param fileName                 input file
     */
    public MBankStatementLoader(
            int C_BankStatementLoader_ID, String fileName) {
        super(C_BankStatementLoader_ID);
        init(fileName);
    } //	MBankStatementLoader

    /**
     * Create a Statement Loader
     *
     */
    public MBankStatementLoader(Row row) {
        super(row);
        init(null);
    } //	MBankStatementLoader

    /**
     * get BankStatementLoader instance
     *
     * @param className
     * @return instance of the BankStatementLoaderInterface or null
     */
    public static BankStatementLoaderInterface getBankStatementLoader(String className) {
        if (className == null || className.length() == 0) {
            s_log.log(Level.SEVERE, "No BankStatementLoaderInterface class name");
            return null;
        }

        BankStatementLoaderInterface myBankStatementLoader = null;

        if (myBankStatementLoader == null) {
            s_log.log(Level.CONFIG, className + " not found in service/extension registry and classpath");
            return null;
        }

        return myBankStatementLoader;
    }

    private void init(String fileName) {
        try {
            if (log.isLoggable(Level.INFO))
                log.info("MBankStatementLoader Class Name=" + getStmtLoaderClass());
            // load the BankStatementLoader class via OSGi Service definition from a plugin
            m_loader = getBankStatementLoader(getStmtLoaderClass());
            if (m_loader == null) {
                // if no OSGi plugin is found try the legacy way (in my own classpath)
                Class<?> bsrClass = Class.forName(getStmtLoaderClass());
                m_loader = (BankStatementLoaderInterface) bsrClass.newInstance();
            }
        } catch (Exception e) {
            errorMessage = "ClassNotLoaded";
            errorDescription = e.getMessage();
        }
    }

    /**
     * Return Name
     *
     * @return Name
     */
    public String toString() {
        return "MBankStatementLoader[" +
                getId() +
                "-" +
                getName() +
                "]";
    } //	toString

    /**
     * Start loading Bankstatements
     *
     * @return true if loading completed successfully
     */
    public boolean loadLines() {
        boolean result = false;
        log.info("MBankStatementLoader.loadLines");
        if (m_loader == null) {
            errorMessage = "ClassNotLoaded";
            return result;
        }
        // Initialize lookup lists
        MTable table;
        TypedQuery<I_C_Currency> query;

        table = MBaseTableKt.getTable(X_C_Currency.Table_ID);
        query = table.createQuery("IsActive='Y'");
        List<I_C_Currency> currencyList = query.list();
        currencyMap = new HashMap<>();

        for (I_C_Currency currency : currencyList) {
            currencyMap.put(currency.getISOCode(), currency.getId());
        }
        //	Initialize the Loader
        if (!m_loader.init(this)) {
            errorMessage = m_loader.getLastErrorMessage();
            errorDescription = m_loader.getLastErrorDescription();
            return result;
        }
        //	Verify whether the data structure is valid
        if (!m_loader.isValid()) {
            errorMessage = m_loader.getLastErrorMessage();
            errorDescription = m_loader.getLastErrorDescription();
            return result;
        }
        //	Load statement lines
        if (!m_loader.loadLines()) {
            errorMessage = m_loader.getLastErrorMessage();
            errorDescription = m_loader.getLastErrorDescription();
            return result;
        }
        result = true;
        return result;
    } //	loadLines

    /**
     * Return the most recent error
     *
     * @return Error message This error message will be handled as a Adempiere message, (e.g. it can
     * be translated)
     */
    public String getErrorMessage() {
        return errorMessage;
    } //	getErrorMessage

    /**
     * Return the most recent error description
     *
     * @return Error description This is an additional error description, it can be used to provided
     * descriptive information, such as a file name or SQL error, that can not be translated by
     * the Adempiere message system.
     */
    public String getErrorDescription() {
        return errorDescription;
    } //	getErrorDescription

    /**
     * The total number of statement lines loaded
     *
     * @return Number of imported statement lines
     */
    public int getLoadCount() {
        return loadCount;
    } //	getLoadCount
} // MBankStatementLoader
