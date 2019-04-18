package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.model.I_A_Asset_Group_Acct;
import org.compiere.model.UseLife;
import org.compiere.orm.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * Asset Group Accounting Model
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class MAssetGroupAcct extends X_A_Asset_Group_Acct implements UseLife {

    /**
     *
     */
    private static final long serialVersionUID = -3458020679308192943L;

    /**
     * Default ConstructorX_A_Asset_Group_Acct
     *
     * @param X_A_Asset_Group_Acct_ID id
     */
    public MAssetGroupAcct(int X_A_Asset_Group_Acct_ID) {
        super(X_A_Asset_Group_Acct_ID);
    } //	MAssetGroupAcct

    /**
     * Load Constructor
     *
     */
    public MAssetGroupAcct(Row row) {
        super(row);
    } //	MAssetGroupAcct

    /**
     * Get Asset Group Accountings for given group
     */
    public static List<I_A_Asset_Group_Acct> forA_Asset_GroupId(int A_Asset_Group_ID) {
        return new Query<I_A_Asset_Group_Acct>(
                I_A_Asset_Group_Acct.Table_Name,
                I_A_Asset_Group_Acct.COLUMNNAME_A_Asset_Group_ID + "=?"
        )
                .setParameters(A_Asset_Group_ID)
                .list();
    }

    /**
     * Get Asset Group Accountings for given group
     */
    public static I_A_Asset_Group_Acct forA_Asset_GroupId(
            int A_Asset_Group_ID, String postingType) {
        final String whereClause =
                I_A_Asset_Group_Acct.COLUMNNAME_A_Asset_Group_ID
                        + "=? AND "
                        + I_A_Asset_Group_Acct.COLUMNNAME_PostingType
                        + "=?";
        return new Query<I_A_Asset_Group_Acct>(I_A_Asset_Group_Acct.Table_Name, whereClause)
                .setParameters(A_Asset_Group_ID, postingType)
                .firstOnly();
    }

    public Timestamp getAssetServiceDate() {
        return null;
    }

    public boolean beforeSave(boolean newRecord) {
        return UseLifeImpl.get(this).validate();
    }

    public boolean setAttrValue(String ColumnName, Object value) {
        int index = getColumnIndex(ColumnName);
        if (index < 0) return false;
        return setValueNoCheck(ColumnName, value);
    }

    public Object getAttrValue(String ColumnName) {
        int index = getColumnIndex(ColumnName);
        if (index < 0) return null;
        return getValue(index);
    }

    public boolean isAttrValueChanged(String ColumnName) {
        int index = getColumnIndex(ColumnName);
        if (index < 0) return false;
        return isValueChanged(index);
    }
} //	MAssetGroupAcct
