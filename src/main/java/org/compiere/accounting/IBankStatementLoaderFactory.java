package org.compiere.accounting;

public interface IBankStatementLoaderFactory {

    /**
     * This class will be implemented in OSGi plugins. Every plugin that provides this service may or
     * may not provide an BankStatementLoader depending on the given classname. The classname can be
     * given by the user in the "Bank" window in the "Bank Satement Loader" tab. By convention this
     * classname is the fully qualified classname of the Loader class you want to use.
     *
     * @param className
     * @return BankStatementLoader instance
     */
    public BankStatementLoaderInterface newBankStatementLoaderInstance(String className);
}
