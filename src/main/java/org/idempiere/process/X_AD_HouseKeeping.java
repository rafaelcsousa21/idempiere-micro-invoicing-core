package org.idempiere.process;

import org.compiere.model.I_AD_HouseKeeping;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

public class X_AD_HouseKeeping extends BasePONameValue implements I_AD_HouseKeeping, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_HouseKeeping(Properties ctx, int AD_HouseKeeping_ID) {
        super(ctx, AD_HouseKeeping_ID);
        /**
         * if (AD_HouseKeeping_ID == 0) { setAD_HouseKeeping_ID (0); setAD_Table_ID (0); setName (null);
         * setValue (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_AD_HouseKeeping(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 4 - System
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    @Override
    public int getTableId() {
        return Table_ID;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_HouseKeeping[").append(getId()).append("]");
        return sb.toString();
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
     * Get Backup Folder.
     *
     * @return Backup Folder
     */
    public String getBackupFolder() {
        return (String) get_Value(COLUMNNAME_BackupFolder);
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
     * Set Last Run.
     *
     * @param LastRun Last Run
     */
    public void setLastRun(Timestamp LastRun) {
        set_Value(COLUMNNAME_LastRun, LastRun);
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
