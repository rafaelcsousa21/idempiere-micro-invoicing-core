package org.idempiere.process;

import org.compiere.accounting.MBankStatementLoader;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Process for loading Bank Statements into I_BankStatement
 *
 * @author Maarten Klinker, Eldir Tomassen
 * @version $Id: LoadBankStatement.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class LoadBankStatement extends SvrProcess {

    /** Client to be imported to */
  private int m_AD_Client_ID = 0;

  /** Organization to be imported to */
  private int m_AD_Org_ID = 0;

  /** Ban Statement Loader */
  private int m_C_BankStmtLoader_ID = 0;

  /** File to be imported */
  private String fileName = "";

  /** Current context */
  private Properties m_ctx;

  /** Current context */
  private MBankStatementLoader m_controller = null;

  /** Prepare - e.g., get Parameters. */
  protected void prepare() {
    log.info("");
    m_ctx = Env.getCtx();
    IProcessInfoParameter[] para = getParameter();
    for (int i = 0; i < para.length; i++) {
      String name = para[i].getParameterName();
      if (name.equals("C_BankStatementLoader_ID"))
        m_C_BankStmtLoader_ID = ((BigDecimal) para[i].getParameter()).intValue();
      else if (name.equals("FileName")) fileName = (String) para[i].getParameter();
      else log.log(Level.SEVERE, "Unknown Parameter: " + name);
    }
    m_AD_Client_ID = Env.getClientId(m_ctx);
    if (log.isLoggable(Level.INFO)) log.info("AD_Client_ID=" + m_AD_Client_ID);
    m_AD_Org_ID = Env.getOrgId(m_ctx);
    if (log.isLoggable(Level.INFO)) {
      log.info("AD_Org_ID=" + m_AD_Org_ID);
      log.info("C_BankStatementLoader_ID=" + m_C_BankStmtLoader_ID);
    }
  } //	prepare

  /**
   * Perform process.
   *
   * @return Message
   * @throws Exception
   */
  protected String doIt() throws java.lang.Exception {
    log.info("LoadBankStatement.doIt");
    String message = "@Error@";

    m_controller = new MBankStatementLoader(m_ctx, m_C_BankStmtLoader_ID, fileName);
    if (log.isLoggable(Level.INFO)) log.info(m_controller.toString());

    if (m_controller == null || m_controller.getId() == 0) log.log(Level.SEVERE, "Invalid Loader");

    // Start loading bank statement lines
    else if (!m_controller.loadLines())
      log.log(
          Level.SEVERE,
          m_controller.getErrorMessage() + " - " + m_controller.getErrorDescription());
    else {
      if (log.isLoggable(Level.INFO)) log.info("Imported=" + m_controller.getLoadCount());
      addLog(0, null, new BigDecimal(m_controller.getLoadCount()), "@Loaded@");
      message = "@OK@";
    }

    return message;
  } //	doIt
} //	LoadBankStatement
