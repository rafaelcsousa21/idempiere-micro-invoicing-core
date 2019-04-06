package org.idempiere.process;

import kotliquery.Row;
import org.idempiere.common.util.CLogger;

import java.util.logging.Level;

public class MBankStatementMatcher extends X_C_BankStatementMatcher {
    /**
     *
     */
    private static final long serialVersionUID = -3756318777177414260L;
    /**
     * Static Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MBankStatementMatcher.class);
    private BankStatementMatcherInterface m_matcher = null;
    private Boolean m_matcherValid = null;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx                       context
     * @param C_BankStatementMatcher_ID id
     */
    public MBankStatementMatcher(int C_BankStatementMatcher_ID) {
        super(C_BankStatementMatcher_ID);
    } //	MBankStatementMatcher

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MBankStatementMatcher(Row row) {
        super(row);
    } //	MBankStatementMatcher

    /**
     * Get Bank Statement Matcher Algorithms
     *
     * @param ctx context
     * @return matchers
     */
    public static MBankStatementMatcher[] getMatchers() {
        return MBaseBankStatementMatcherKt.getBankStatementMatcherAlgorithms();
    } //	getMatchers

    /**
     * get BankStatementMatcher instance
     *
     * @param className
     * @return instance of the BankStatementMatcherInterface or null
     */
    public static BankStatementMatcherInterface getBankStatementMatcher(String className) {
        if (className == null || className.length() == 0) {
            s_log.log(Level.SEVERE, "No BankStatementMatcherInterface class name");
            return null;
        }

        BankStatementMatcherInterface myBankStatementMatcher = null;

        if (myBankStatementMatcher == null) {
            s_log.log(Level.CONFIG, className + " not found in service/extension registry and classpath");
            return null;
        }

        return myBankStatementMatcher;
    }

    /**
     * Is Matcher Valid
     *
     * @return true if valid
     */
    public boolean isMatcherValid() {
        if (m_matcherValid == null) getMatcher();
        return m_matcherValid;
    } //	isMatcherValid

    /**
     * Get Matcher
     *
     * @return Matcher Instance
     */
    public BankStatementMatcherInterface getMatcher() {
        if (m_matcher != null || (m_matcherValid != null && m_matcherValid))
            return m_matcher;

        String className = getClassname();
        if (className == null || className.length() == 0) return null;

        try {
            if (log.isLoggable(Level.INFO)) log.info("MBankStatementMatch Class Name=" + className);
            // load the BankStatementMatcher class via OSGi Service definition from a plugin
            m_matcher = getBankStatementMatcher(className);
            if (m_matcher == null) {
                // if no OSGi plugin is found try the legacy way (in my own classpath)
                Class<?> bsrClass = Class.forName(className);
                m_matcher = (BankStatementMatcherInterface) bsrClass.newInstance();
            }
            m_matcherValid = Boolean.TRUE;
        } catch (Exception e) {
            log.log(Level.SEVERE, className, e);
            m_matcher = null;
            m_matcherValid = Boolean.FALSE;
        }
        return m_matcher;
    } //	getMatcher
} //	MBankStatementMatcher
