package org.idempiere.process;

import java.math.BigDecimal;
import java.util.logging.Level;
import org.compiere.accounting.MJournalBatch;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;

/**
 * Copy GL Batch Journal/Lines
 *
 * @author Jorg Janke
 * @version $Id: CopyFromJournal.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class CopyFromJournal extends SvrProcess {
  private int m_GL_JournalBatch_ID = 0;

  /** Prepare - e.g., get Parameters. */
  protected void prepare() {
    IProcessInfoParameter[] para = getParameter();
    for (int i = 0; i < para.length; i++) {
      String name = para[i].getParameterName();
      if (para[i].getParameter() == null) ;
      else if (name.equals("GL_JournalBatch_ID"))
        m_GL_JournalBatch_ID = ((BigDecimal) para[i].getParameter()).intValue();
      else log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
    }
  } //	prepare

  /**
   * Perform process.
   *
   * @return Message (clear text)
   * @throws Exception if not successful
   */
  protected String doIt() throws Exception {
    int To_GL_JournalBatch_ID = getRecord_ID();
    if (log.isLoggable(Level.INFO))
      log.info(
          "doIt - From GL_JournalBatch_ID="
              + m_GL_JournalBatch_ID
              + " to "
              + To_GL_JournalBatch_ID);
    if (To_GL_JournalBatch_ID == 0)
      throw new IllegalArgumentException("Target GL_JournalBatch_ID == 0");
    if (m_GL_JournalBatch_ID == 0)
      throw new IllegalArgumentException("Source GL_JournalBatch_ID == 0");
    MJournalBatch from = new MJournalBatch(getCtx(), m_GL_JournalBatch_ID);
    MJournalBatch to = new MJournalBatch(getCtx(), To_GL_JournalBatch_ID);
    //
    int no = to.copyDetailsFrom(from);
    //
    return "@Copied@=" + no;
  } //	doIt
}
