package org.idempiere.process;

import org.compiere.orm.MRole;
import org.idempiere.common.base.Service;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;

public class MBankStatementMatcher extends X_C_BankStatementMatcher {
  /** */
  private static final long serialVersionUID = -3756318777177414260L;

  /**
   * Get Bank Statement Matcher Algorithms
   *
   * @param ctx context
   * @param trxName transaction
   * @return matchers
   */
  public static MBankStatementMatcher[] getMatchers(Properties ctx) {
    ArrayList<MBankStatementMatcher> list = new ArrayList<MBankStatementMatcher>();
    String sql =
        MRole.getDefault(ctx, false)
            .addAccessSQL(
                "SELECT * FROM C_BankStatementMatcher ORDER BY SeqNo",
                "C_BankStatementMatcher",
                MRole.SQL_NOTQUALIFIED,
                MRole.SQL_RO);
    @SuppressWarnings("unused")
    int AD_Client_ID = Env.getClientId(ctx);
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(new MBankStatementMatcher(ctx, rs));
    } catch (Exception e) {
      s_log.log(Level.SEVERE, sql, e);
    } finally {

      rs = null;
      pstmt = null;
    }
    //	Convert
    MBankStatementMatcher[] retValue = new MBankStatementMatcher[list.size()];
    list.toArray(retValue);
    return retValue;
  } //	getMatchers

  /** Static Logger */
  private static CLogger s_log = CLogger.getCLogger(MBankStatementMatcher.class);

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param C_BankStatementMatcher_ID id
   * @param trxName transaction
   */
  public MBankStatementMatcher(Properties ctx, int C_BankStatementMatcher_ID) {
    super(ctx, C_BankStatementMatcher_ID);
  } //	MBankStatementMatcher

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MBankStatementMatcher(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MBankStatementMatcher

  private BankStatementMatcherInterface m_matcher = null;
  private Boolean m_matcherValid = null;

  /**
   * Is Matcher Valid
   *
   * @return true if valid
   */
  public boolean isMatcherValid() {
    if (m_matcherValid == null) getMatcher();
    return m_matcherValid.booleanValue();
  } //	isMatcherValid

  /**
   * Get Matcher
   *
   * @return Matcher Instance
   */
  public BankStatementMatcherInterface getMatcher() {
    if (m_matcher != null || (m_matcherValid != null && m_matcherValid.booleanValue()))
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

    List<IBankStatementMatcherFactory> factoryList =
        Service.Companion.locator().list(IBankStatementMatcherFactory.class).getServices();
    if (factoryList != null) {
      for (IBankStatementMatcherFactory factory : factoryList) {
        BankStatementMatcherInterface matcher = factory.newBankStatementMatcherInstance(className);
        if (matcher != null) {
          myBankStatementMatcher = matcher;
          break;
        }
      }
    }

    if (myBankStatementMatcher == null) {
      s_log.log(Level.CONFIG, className + " not found in service/extension registry and classpath");
      return null;
    }

    return myBankStatementMatcher;
  }
} //	MBankStatementMatcher
