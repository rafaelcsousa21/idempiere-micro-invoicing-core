package org.compiere.server;

import org.compiere.accounting.MClient;
import org.compiere.crm.MUser;
import org.compiere.orm.MAttachment;
import org.compiere.orm.MOrgInfo;
import org.compiere.orm.MRole;
import org.compiere.orm.TimeUtil;
import org.compiere.process.*;
import org.compiere.schedule.MScheduler;
import org.compiere.schedule.MSchedulerLog;
import org.compiere.schedule.MSchedulerPara;
import org.compiere.util.DisplayType;
import org.compiere.wf.MMailText;
import org.compiere.wf.MNote;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.close;
import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Scheduler
 *
 * @author Jorg Janke
 * @version $Id: Scheduler.java,v 1.5 2006/07/30 00:53:33 jjanke Exp $
 *     <p>Contributors: Carlos Ruiz - globalqss - FR [3135351] - Enable Scheduler for buttons
 */
public class Scheduler extends AdempiereServer {

    /** The Concrete Model */
  protected MScheduler m_model = null;
  /** Last Summary */
  protected StringBuffer m_summary = new StringBuffer();

  /** Work */
  protected void doWork() {
    m_summary = new StringBuffer(m_model.toString()).append(" - ");

    // Prepare a ctx for the report/process - BF [1966880]
    MClient schedclient = MClient.get(getCtx(), m_model.getClientId());
    Env.setContext(getCtx(), "#clientId", schedclient.getClientId());
    Env.setContext(getCtx(), "#AD_Language", schedclient.getADLanguage());
    Env.setContext(getCtx(), "#orgId", m_model.getOrgId());
    if (m_model.getOrgId() != 0) {
      MOrgInfo schedorg = MOrgInfo.get(getCtx(), m_model.getOrgId(), null);
      if (schedorg.getM_Warehouse_ID() > 0)
        Env.setContext(getCtx(), "#M_Warehouse_ID", schedorg.getM_Warehouse_ID());
    }
    Env.setContext(getCtx(), "#AD_User_ID", getAD_User_ID());
    Env.setContext(getCtx(), "#SalesRep_ID", getAD_User_ID());
    // TODO: It can be convenient to add  AD_Scheduler.AD_Role_ID
    MUser scheduser = MUser.get(getCtx(), getAD_User_ID());
    MRole[] schedroles = scheduser.getRoles(m_model.getOrgId());
    if (schedroles != null && schedroles.length > 0)
      Env.setContext(
          getCtx(),
          "#AD_Role_ID",
          schedroles[0].getAD_Role_ID()); // first role, ordered by AD_Role_ID
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    SimpleDateFormat dateFormat4Timestamp = new SimpleDateFormat("yyyy-MM-dd");
    Env.setContext(
        getCtx(), "#Date", dateFormat4Timestamp.format(ts) + " 00:00:00"); //  JDBC format

    MProcess process = new MProcess(getCtx(), m_model.getAD_Process_ID(), null);
    try {
      m_summary.append(runProcess(process));
    } catch (Throwable e) {
      log.log(Level.WARNING, process.toString(), e);
      m_summary.append(e.toString());
      throw new Error(e.toString());
    }

    //
    int no = m_model.deleteLog();
    m_summary.append(" Logs deleted=").append(no);
    //
    MSchedulerLog pLog = new MSchedulerLog(m_model, m_summary.toString());
    pLog.setReference(
        "#"
            + String.valueOf(p_runCount)
            + " - "
            + TimeUtil.formatElapsed(new Timestamp(p_startWork)));
    pLog.saveEx();
  } //	doWork

  /**
   * Run Process or Report
   *
   * @param process process
   * @return summary
   * @throws Exception
   */
  protected String runProcess(MProcess process) throws Exception {
    if (log.isLoggable(Level.INFO)) log.info(process.toString());

    boolean isReport =
        (process.isReport()
            || process.getAD_ReportView_ID() > 0
            || process.getJasperReport() != null
            || process.getAD_PrintFormat_ID() > 0);
    String schedulerName = Env.parseContext(getCtx(), -1, m_model.getName(), false, true);

    //	Process (see also MWFActivity.performWork
    int AD_Table_ID = m_model.getAD_Table_ID();
    int Record_ID = m_model.getRecord_ID();
    //
    MPInstance pInstance = new MPInstance(process, Record_ID);
    fillParameter(pInstance);
    //
    ProcessInfo pi =
        new ProcessInfo(process.getName(), process.getAD_Process_ID(), AD_Table_ID, Record_ID);
    pi.setAD_User_ID(getAD_User_ID());
    pi.setADClientID(m_model.getClientId());
    pi.setAD_PInstance_ID(pInstance.getAD_PInstance_ID());
    pi.setIsBatch(true);
    pi.setPrintPreview(true);
    MUser from = new MUser(getCtx(), pi.getAD_User_ID(), null);

    pi.setTransactionName(null);
    ServerProcessCtl.process(pi);
    if (pi.isError()) // note, this call close the transaction, don't use m_trx below
    {
      // notify supervisor if error
      int supervisor = m_model.getSupervisor_ID();
      if (supervisor > 0) {
        MUser user = new MUser(getCtx(), supervisor, null);
        boolean email = user.isNotificationEMail();
        boolean notice = user.isNotificationNote();

        if (email || notice) ProcessInfoUtil.setLogFromDB(pi);

        if (email) {
          MClient client = MClient.get(m_model.getCtx(), m_model.getClientId());
          client.sendEMail(
              from, user, schedulerName, pi.getSummary() + " " + pi.getLogInfo(), null);
        }
        if (notice) {
          int AD_Message_ID = 442; // HARDCODED ProcessRunError
          MNote note = new MNote(getCtx(), AD_Message_ID, supervisor, null);
          note.setClientOrg(m_model.getClientId(), m_model.getOrgId());
          note.setTextMsg(schedulerName + "\n" + pi.getSummary());
          note.setRecord(MPInstance.Table_ID, pi.getAD_PInstance_ID());
          note.saveEx();
          String log = pi.getLogInfo(true);
          if (log != null && log.trim().length() > 0) {
            MAttachment attachment =
                new MAttachment(getCtx(), MNote.Table_ID, note.getAD_Note_ID(), null);
            attachment.setClientOrg(m_model.getClientId(), m_model.getOrgId());
            attachment.setTextMsg(schedulerName);
            attachment.addEntry("ProcessLog.html", log.getBytes("UTF-8"));
            attachment.saveEx();
          }
        }
      }
    }

    // always notify recipients
    Integer[] userIDs = m_model.getRecipientAD_User_IDs();
    if (userIDs.length > 0) {
      ProcessInfoUtil.setLogFromDB(pi);
      List<File> fileList = new ArrayList<File>();
      if (isReport) {
        fileList.add(pi.getPDFReport());
      }
      if (pi.isExport() && pi.getExportFile() != null) {
        fileList.add(pi.getExportFile());
      }

      for (int i = 0; i < userIDs.length; i++) {
        MUser user = new MUser(getCtx(), userIDs[i].intValue(), null);
        boolean email = user.isNotificationEMail();
        boolean notice = user.isNotificationNote();

        if (notice) {
          int AD_Message_ID = 441; // ProcessOK
          if (isReport) AD_Message_ID = 884; // 	HARDCODED SchedulerResult
          MNote note = new MNote(getCtx(), AD_Message_ID, userIDs[i].intValue(), null);
          note.setClientOrg(m_model.getClientId(), m_model.getOrgId());
          if (isReport) {
            note.setTextMsg(schedulerName);
            note.setDescription(m_model.getDescription());
            note.setRecord(AD_Table_ID, Record_ID);
          } else {
            note.setTextMsg(schedulerName + "\n" + pi.getSummary());
            note.setRecord(MPInstance.Table_ID, pi.getAD_PInstance_ID());
          }
          if (note.save()) {
            MAttachment attachment = null;
            if (fileList != null && !fileList.isEmpty()) {
              //	Attachment
              attachment = new MAttachment(getCtx(), MNote.Table_ID, note.getAD_Note_ID(), null);
              attachment.setClientOrg(m_model.getClientId(), m_model.getOrgId());
              attachment.setTextMsg(schedulerName);
              for (File entry : fileList) attachment.addEntry(entry);
            }
            String log = pi.getLogInfo(true);
            if (log != null && log.trim().length() > 0) {
              if (attachment == null) {
                attachment = new MAttachment(getCtx(), MNote.Table_ID, note.getAD_Note_ID(), null);
                attachment.setClientOrg(m_model.getClientId(), m_model.getOrgId());
                attachment.setTextMsg(schedulerName);
              }
              attachment.addEntry("ProcessLog.html", log.getBytes("UTF-8"));
              attachment.saveEx();
            }
            if (attachment != null) attachment.saveEx();
          }
        }

        if (email) {
          MMailText mailTemplate = new MMailText(getCtx(), m_model.getR_MailText_ID(), null);
          String mailContent = "";

          if (mailTemplate.is_new()) {
            mailContent = m_model.getDescription();
          } else {
            mailTemplate.setUser(user);
            mailTemplate.setLanguage(Env.getContext(getCtx(), "#AD_Language"));
            // if user has bpartner link. maybe use language depend user
            mailContent = mailTemplate.getMailText(true);
            schedulerName = mailTemplate.getMailHeader();
          }

          MClient client = MClient.get(m_model.getCtx(), m_model.getClientId());
          if (fileList != null && !fileList.isEmpty()) {
            client.sendEMailAttachments(from, user, schedulerName, mailContent, fileList);
          } else {
            client.sendEMail(
                from,
                user,
                schedulerName,
                mailContent + "\n" + pi.getSummary() + " " + pi.getLogInfo(),
                null);
          }
        }
      }

      // IDEMPIERE-2864
      for (File file : fileList) {
        if (file.exists() && !file.delete()) file.deleteOnExit();
      }
    }

    return pi.getSummary();
  } //	runProcess

  protected int getAD_User_ID() {
    int AD_User_ID;
    if (m_model.getSupervisor_ID() > 0) AD_User_ID = m_model.getSupervisor_ID();
    else if (m_model.getCreatedBy() > 0) AD_User_ID = m_model.getCreatedBy();
    else if (m_model.getUpdatedBy() > 0) AD_User_ID = m_model.getUpdatedBy();
    else AD_User_ID = 100; // fall back to SuperUser
    return AD_User_ID;
  }

  /**
   * Fill Parameter
   *
   * @param pInstance process instance
   */
  protected void fillParameter(MPInstance pInstance) {
    MSchedulerPara[] sParams = m_model.getParameters(false);
    MPInstancePara[] iParams = pInstance.getParameters();
    for (int pi = 0; pi < iParams.length; pi++) {
      MPInstancePara iPara = iParams[pi];
      for (int np = 0; np < sParams.length; np++) {
        MSchedulerPara sPara = sParams[np];
        if (iPara.getParameterName().equals(sPara.getColumnName())) {
          String paraDesc = sPara.getDescription();
          if (paraDesc != null && paraDesc.trim().length() > 0)
            iPara.setInfo(sPara.getDescription());
          String variable = sPara.getParameterDefault();
          String toVariable = sPara.getParameterToDefault();
          if (log.isLoggable(Level.FINE)) log.fine(sPara.getColumnName() + " = " + variable);
          //	Value - Constant/Variable
          Object value = parseVariable(sPara, variable);
          Object toValue = toVariable != null ? parseVariable(sPara, toVariable) : null;

          //	No Value
          if (value == null && toValue == null) {
            if (log.isLoggable(Level.FINE)) log.fine(sPara.getColumnName() + " - empty");
            break;
          }

          //	Convert to Type
          try {
            if (DisplayType.isNumeric(sPara.getDisplayType())
                || DisplayType.isID(sPara.getDisplayType())) {
              DecimalFormat decimalFormat = DisplayType.getNumberFormat(sPara.getDisplayType());
              BigDecimal bd = toBigDecimal(value);
              iPara.setP_Number(bd);
              if (toValue != null) {
                bd = toBigDecimal(toValue);
                iPara.setP_Number_To(bd);
              }
              if (Util.isEmpty(paraDesc)) {
                String info = decimalFormat.format(iPara.getP_Number());
                if (iPara.getP_Number_To() != null)
                  info = info + " - " + decimalFormat.format(iPara.getP_Number_To());
                iPara.setInfo(info);
              }
              if (log.isLoggable(Level.FINE))
                log.fine(sPara.getColumnName() + " = " + variable + " (=" + bd + "=)");
            } else if (DisplayType.isDate(sPara.getDisplayType())) {
              SimpleDateFormat dateFormat = DisplayType.getDateFormat(sPara.getDisplayType());
              Timestamp ts = toTimestamp(value);
              iPara.setP_Date(ts);
              if (toValue != null) {
                ts = toTimestamp(toValue);
                iPara.setP_Date_To(ts);
              }
              if (Util.isEmpty(paraDesc)) {
                String info = dateFormat.format(iPara.getP_Date());
                if (iPara.getP_Date_To() != null) {
                  info = info + " - " + dateFormat.format(iPara.getP_Date_To());
                }
                iPara.setInfo(info);
              }
              if (log.isLoggable(Level.FINE))
                log.fine(sPara.getColumnName() + " = " + variable + " (=" + ts + "=)");
            } else {
              iPara.setP_String(value.toString());
              if (toValue != null) {
                iPara.setP_String_To(toValue.toString());
              }
              if (Util.isEmpty(paraDesc)) {
                String info = iPara.getP_String();
                if (iPara.getP_String_To() != null) {
                  info = info + " - " + iPara.getP_String_To();
                }
                iPara.setInfo(info);
              }
              if (log.isLoggable(Level.FINE))
                log.fine(
                    sPara.getColumnName()
                        + " = "
                        + variable
                        + " (="
                        + value
                        + "=) "
                        + value.getClass().getName());
            }
            if (!iPara.save()) log.warning("Not Saved - " + sPara.getColumnName());
          } catch (Exception e) {
            log.warning(
                sPara.getColumnName()
                    + " = "
                    + variable
                    + " ("
                    + value
                    + ") "
                    + value.getClass().getName()
                    + " - "
                    + e.getLocalizedMessage());
          }
          break;
        } //	parameter match
      } //	scheduler parameter loop
    } //	instance parameter loop
  } //	fillParameter

  private Timestamp toTimestamp(Object value) {
    Timestamp ts = null;
    if (value instanceof Timestamp) ts = (Timestamp) value;
    else ts = Timestamp.valueOf(value.toString());
    return ts;
  }

  private BigDecimal toBigDecimal(Object value) {
    BigDecimal bd = null;
    if (value instanceof BigDecimal) bd = (BigDecimal) value;
    else if (value instanceof Integer) bd = new BigDecimal(((Integer) value).intValue());
    else bd = new BigDecimal(value.toString());
    return bd;
  }

  private Object parseVariable(MSchedulerPara sPara, String variable) {
    Object value = variable;
    if (variable == null || (variable != null && variable.length() == 0)) value = null;
    else if (variable.startsWith("@SQL=")) {
      String defStr = "";
      String sql = variable.substring(5); // 	w/o tag
      // sql = Env.parseContext(m_vo.ctx, m_vo.WindowNo, sql, false, true);	//	replace variables
      // hengsin, capture unparseable error to avoid subsequent sql exception
      sql = Env.parseContext(getCtx(), 0, sql, false, false); // 	replace variables
      if (sql.equals(""))
        log.log(
            Level.WARNING,
            "(" + sPara.getColumnName() + ") - Default SQL variable parse failed: " + variable);
      else {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
          stmt = prepareStatement(sql, null);
          rs = stmt.executeQuery();
          if (rs.next()) defStr = rs.getString(1);
          else {
            if (log.isLoggable(Level.INFO))
              log.log(Level.INFO, "(" + sPara.getColumnName() + ") - no Result: " + sql);
          }
        } catch (SQLException e) {
          log.log(Level.WARNING, "(" + sPara.getColumnName() + ") " + sql, e);
        } finally {
          close(rs, stmt);
          rs = null;
          stmt = null;
        }
      }
      if (!Util.isEmpty(defStr)) value = defStr;
    } //	SQL Statement
    else if (variable.indexOf('@') != -1
        && variable.indexOf('@') != variable.lastIndexOf('@')) // 	we have a variable / BF [1926032]
    {
      //	Strip
      int index = variable.indexOf('@');
      String columnName = variable.substring(index + 1);
      index = columnName.indexOf('@');
      if (index == -1) {
        log.warning(sPara.getColumnName() + " - cannot evaluate=" + variable);
        return null;
      }
      String tail = index < (columnName.length() - 1) ? columnName.substring(index + 1) : null;
      columnName = columnName.substring(0, index);
      //	try Env
      String env = Env.getContext(getCtx(), columnName);
      if (env == null || env.length() == 0) env = Env.getContext(getCtx(), columnName);
      if (env.length() == 0) {
        log.warning(
            sPara.getColumnName() + " - not in environment =" + columnName + "(" + variable + ")");
        return null;
      } else value = env;

      if (tail != null && columnName.equals("#Date")) {
        tail = tail.trim();
        if (tail.startsWith("-") || tail.startsWith("+")) {
          boolean negate = tail.startsWith("-");
          int type = Calendar.DATE;
          tail = tail.substring(1);
          if (tail.endsWith("d")) {
            tail = tail.substring(0, tail.length() - 1);
          } else if (tail.endsWith("m")) {
            type = Calendar.MONTH;
            tail = tail.substring(0, tail.length() - 1);
          } else if (tail.endsWith("y")) {
            type = Calendar.YEAR;
            tail = tail.substring(0, tail.length() - 1);
          }

          int toApply = 0;
          try {
            toApply = Integer.parseInt(tail);
          } catch (Exception e) {
          }
          if (toApply > 0) {
            if (negate) toApply = toApply * -1;
            Timestamp ts = toTimestamp(value);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(ts.getTime());
            cal.add(type, toApply);
            value = new Timestamp(cal.getTimeInMillis());
          }
        }
      }
    } //	@variable@
    return value;
  }

} //	Scheduler
