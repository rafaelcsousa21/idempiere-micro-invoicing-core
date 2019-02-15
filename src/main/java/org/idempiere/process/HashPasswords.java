package org.idempiere.process;

import java.util.List;
import org.compiere.crm.MUser;
import org.compiere.orm.MSysConfig;
import org.compiere.orm.MTable;
import org.compiere.process.SvrProcess;
import org.compiere.util.SystemIDs;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.*;

/** Hash existing passwords */
public class HashPasswords extends SvrProcess {
  /** Prepare - e.g., get Parameters. */
  protected void prepare() {} // 	prepare

  /**
   * Perform process.
   *
   * @return Message
   * @throws Exception if not successful
   */
  protected String doIt() throws Exception {
    boolean hash_password = MSysConfig.getBooleanValue(MSysConfig.USER_PASSWORD_HASH, false);
    if (hash_password) throw new AdempiereException("Passwords already hashed");

    String where = " Password IS NOT NULL AND Salt IS NULL ";

    // update the sysconfig key to Y out of trx and reset the cache
    MSysConfig conf = new MSysConfig(getCtx(), SystemIDs.SYSCONFIG_USER_HASH_PASSWORD);
    conf.setValue("Y");
    conf.saveEx();
    CacheMgt.get().reset(MSysConfig.Table_Name);

    int count = 0;
    try {
      List<MUser> users =
          MTable.get(getCtx(), MUser.Table_ID).createQuery(where).list();
      for (MUser user : users) {
        user.setPassword(user.getPassword());
        count++;
        user.saveEx();
      }
    } catch (Exception e) {
      // reset to N on exception
      conf.setValue("N");
      conf.saveEx();
      throw e;
    }

    return "@Updated@ " + count;
  } //	doIt
} //	HashPasswords
