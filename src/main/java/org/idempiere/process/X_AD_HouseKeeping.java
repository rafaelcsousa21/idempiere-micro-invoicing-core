package org.idempiere.process;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_AD_HouseKeeping;
import org.compiere.orm.BasePONameValue;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;
import org.idempiere.orm.POInfo;

public class X_AD_HouseKeeping extends BasePONameValue implements I_AD_HouseKeeping, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_HouseKeeping(Properties ctx, int AD_HouseKeeping_ID, String trxName) {
    super(ctx, AD_HouseKeeping_ID, trxName);
    /**
     * if (AD_HouseKeeping_ID == 0) { setAD_HouseKeeping_ID (0); setAD_Table_ID (0); setName (null);
     * setValue (null); }
     */
  }

  /** Load Constructor */
  public X_AD_HouseKeeping(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 4 - System
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  /** Load Meta Data */
  protected POInfo initPO(Properties ctx) {
    POInfo poi = POInfo.getPOInfo(ctx, Table_ID, get_TrxName());
    return poi;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_AD_HouseKeeping[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set House Keeping Configuration.
   *
   * @param AD_HouseKeeping_ID House Keeping Configuration
   */
  public void setAD_HouseKeeping_ID(int AD_HouseKeeping_ID) {
    if (AD_HouseKeeping_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_HouseKeeping_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_HouseKeeping_ID, Integer.valueOf(AD_HouseKeeping_ID));
  }

  /**
   * Get House Keeping Configuration.
   *
   * @return House Keeping Configuration
   */
  public int getAD_HouseKeeping_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_HouseKeeping_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set AD_HouseKeeping_UU.
   *
   * @param AD_HouseKeeping_UU AD_HouseKeeping_UU
   */
  public void setAD_HouseKeeping_UU(String AD_HouseKeeping_UU) {
    set_Value(COLUMNNAME_AD_HouseKeeping_UU, AD_HouseKeeping_UU);
  }

  /**
   * Get AD_HouseKeeping_UU.
   *
   * @return AD_HouseKeeping_UU
   */
  public String getAD_HouseKeeping_UU() {
    return (String) get_Value(COLUMNNAME_AD_HouseKeeping_UU);
  }

  public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException {
    return (org.compiere.model.I_AD_Table)
        MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
            .getPO(getAD_Table_ID(), get_TrxName());
  }

  /**
   * Set Table.
   *
   * @param AD_Table_ID Database Table information
   */
  public void setAD_Table_ID(int AD_Table_ID) {
    if (AD_Table_ID < 1) set_Value(COLUMNNAME_AD_Table_ID, null);
    else set_Value(COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
  }

  /**
   * Get Table.
   *
   * @return Database Table information
   */
  public int getAD_Table_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Table_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Backup Folder.
   *
   * @param BackupFolder Backup Folder
   */
  public void setBackupFolder(String BackupFolder) {
    set_Value(COLUMNNAME_BackupFolder, BackupFolder);
  }

  /**
   * Get Backup Folder.
   *
   * @return Backup Folder
   */
  public String getBackupFolder() {
    return (String) get_Value(COLUMNNAME_BackupFolder);
  }

  /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(COLUMNNAME_Description, Description);
  }

  /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
  }

  /**
   * Set Comment/Help.
   *
   * @param Help Comment or Hint
   */
  public void setHelp(String Help) {
    set_Value(COLUMNNAME_Help, Help);
  }

  /**
   * Get Comment/Help.
   *
   * @return Comment or Hint
   */
  public String getHelp() {
    return (String) get_Value(COLUMNNAME_Help);
  }

  /**
   * Set Export XML Backup.
   *
   * @param IsExportXMLBackup Export XML Backup
   */
  public void setIsExportXMLBackup(boolean IsExportXMLBackup) {
    set_Value(COLUMNNAME_IsExportXMLBackup, Boolean.valueOf(IsExportXMLBackup));
  }

  /**
   * Get Export XML Backup.
   *
   * @return Export XML Backup
   */
  public boolean isExportXMLBackup() {
    Object oo = get_Value(COLUMNNAME_IsExportXMLBackup);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Save In Historic.
   *
   * @param IsSaveInHistoric Save In Historic
   */
  public void setIsSaveInHistoric(boolean IsSaveInHistoric) {
    set_Value(COLUMNNAME_IsSaveInHistoric, Boolean.valueOf(IsSaveInHistoric));
  }

  /**
   * Get Save In Historic.
   *
   * @return Save In Historic
   */
  public boolean isSaveInHistoric() {
    Object oo = get_Value(COLUMNNAME_IsSaveInHistoric);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Last Deleted.
   *
   * @param LastDeleted Last Deleted
   */
  public void setLastDeleted(int LastDeleted) {
    set_Value(COLUMNNAME_LastDeleted, Integer.valueOf(LastDeleted));
  }

  /**
   * Get Last Deleted.
   *
   * @return Last Deleted
   */
  public int getLastDeleted() {
    Integer ii = (Integer) get_Value(COLUMNNAME_LastDeleted);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Last Run.
   *
   * @param LastRun Last Run
   */
  public void setLastRun(Timestamp LastRun) {
    set_Value(COLUMNNAME_LastRun, LastRun);
  }

  /**
   * Get Last Run.
   *
   * @return Last Run
   */
  public Timestamp getLastRun() {
    return (Timestamp) get_Value(COLUMNNAME_LastRun);
  }

  /**
   * Set Process Now.
   *
   * @param Processing Process Now
   */
  public void setProcessing(boolean Processing) {
    set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
  }

  /**
   * Get Process Now.
   *
   * @return Process Now
   */
  public boolean isProcessing() {
    Object oo = get_Value(COLUMNNAME_Processing);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Sql WHERE.
   *
   * @param WhereClause Fully qualified SQL WHERE clause
   */
  public void setWhereClause(String WhereClause) {
    set_Value(COLUMNNAME_WhereClause, WhereClause);
  }

  /**
   * Get Sql WHERE.
   *
   * @return Fully qualified SQL WHERE clause
   */
  public String getWhereClause() {
    return (String) get_Value(COLUMNNAME_WhereClause);
  }
}
