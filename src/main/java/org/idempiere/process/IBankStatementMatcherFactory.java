package org.idempiere.process;

public interface IBankStatementMatcherFactory {

  /**
   * This class will be implemented in OSGi plugins. Every plugin that provides this service may or
   * may not provide an BankStatementMatcher depending on the given classname. By convention this
   * classname is the fully qualified classname of the Loader class you want to use.
   *
   * @param className
   * @return BankStatementMatcher instance
   */
  public BankStatementMatcherInterface newBankStatementMatcherInstance(String className);
}
