package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.model.AssetAccounting;
import org.compiere.model.AssetGroupAccounting;
import org.compiere.orm.PO;
import org.compiere.orm.Query;
import org.compiere.orm.SetGetUtil;
import org.compiere.orm.TimeUtil;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Asset Acct Model
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class MAssetAcct extends X_A_Asset_Acct {
    /**
     *
     */
    private static final long serialVersionUID = -3919172418904053712L;
    /**
     * Static Cache: A_Asset_Acct_ID -> MAssetAcct
     */
    private static CCache<Integer, AssetAccounting> s_cache =
            new CCache<>(AssetAccounting.Table_Name, 5);

    /**
     * DO NOT USE DIRECTLY
     */
    public MAssetAcct(int X_A_Asset_Acct_ID) {
        super(X_A_Asset_Acct_ID);
        if (X_A_Asset_Acct_ID == 0) {
            setSalvageValue(Env.ZERO);
        }
    }

    public MAssetAcct(Row row) {
        super(row);
    }

    /**
     * Create new asset accounting from asset group accounting
     *
     * @param asset        asset
     * @param assetgrpacct asset group accounting
     */
    public MAssetAcct(MAsset asset, AssetGroupAccounting assetgrpacct) {
        this(0);

        SetGetUtil.copyValues(this, (PO)assetgrpacct, null, null);
        setAssetId(asset.getAssetId());
        if (asset.getA_DepreciationId() > 0) {
            setDepreciationId(asset.getA_DepreciationId());
        }
        if (asset.getA_Depreciation_FId() > 0) {
            setDepreciationFiscalId(asset.getA_Depreciation_FId());
        }
        setPeriodStart(1);
        setPeriodEnd(asset.getUseLifeMonths());
        // ~ setProcessing(false);
        dump();
    }

    /**
     * Get asset accounting.
     *
     * @param A_Asset_ID  asset
     * @param postingType Posting type
     * @param dateAcct    check ValidFrom
     * @return asset accounting for the given asset
     */
    public static AssetAccounting forA_AssetId(
            int A_Asset_ID, String postingType, Timestamp dateAcct) {
        //
        ArrayList<Object> params = new ArrayList<>();
        StringBuilder whereClause =
                new StringBuilder(
                        AssetAccounting.COLUMNNAME_A_Asset_ID
                                + "=? AND "
                                + AssetAccounting.COLUMNNAME_PostingType
                                + "=?");
        params.add(A_Asset_ID);
        params.add(postingType);
        if (dateAcct != null) {
            whereClause.append(" AND " + AssetAccounting.COLUMNNAME_ValidFrom).append("<=?");
            params.add(dateAcct);
        }
        AssetAccounting acct =
                new Query<AssetAccounting>(AssetAccounting.Table_Name, whereClause.toString())
                        .setParameters(params)
                        .setOrderBy(AssetAccounting.COLUMNNAME_ValidFrom + " DESC NULLS LAST")
                        .first();
        addToCache(acct);
        return acct;
    }

    private static void addToCache(AssetAccounting acct) {
        if (acct == null || acct.getId() <= 0) {
            return;
        }
        s_cache.put(acct.getId(), acct);
    }

    /**
     *
     */
    public BigDecimal getA_Depreciation_Variable_Perc(boolean fiscal) {
        return fiscal ? getDepreciationVariablePercentFiscal() : getDepreciationVariablePercent();
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {
        if (getValidFrom() == null && newRecord) {
            setValidFrom(TimeUtil.getDay(1970, 01, 01)); // FIXME
        }
        return true;
    }
} //	class MAssetAcct
