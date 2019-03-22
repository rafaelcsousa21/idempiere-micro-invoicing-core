package org.idempiere.process;

import org.compiere.accounting.MJournal;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;

import java.math.BigDecimal;
import java.util.logging.Level;

/**
 * Copy GL Journal/Lines
 *
 * @author Carlos Ruiz
 */
public class CopyFromJournalDoc extends SvrProcess {
    private int m_GL_Journal_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("GL_Journal_ID"))
                m_GL_Journal_ID = ((BigDecimal) iProcessInfoParameter.getParameter()).intValue();
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
        int To_GL_Journal_ID = getRecordId();
        if (log.isLoggable(Level.INFO))
            log.info("doIt - From GL_Journal_ID=" + m_GL_Journal_ID + " to " + To_GL_Journal_ID);
        if (To_GL_Journal_ID == 0) throw new IllegalArgumentException("Target GL_Journal_ID == 0");
        if (m_GL_Journal_ID == 0) throw new IllegalArgumentException("Source GL_Journal_ID == 0");
        MJournal from = new MJournal(getCtx(), m_GL_Journal_ID);
        MJournal to = new MJournal(getCtx(), To_GL_Journal_ID);
        //
        int no = to.copyLinesFrom(from, to.getDateAcct(), 'x');
        //
        return "@Copied@=" + no;
    } //	doIt
}
