package org.compiere.invoicing;

import kotliquery.Row;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.compiere.model.I_A_Depreciation_Workfile;
import org.compiere.model.SetGetModel;
import org.compiere.model.UseLife;
import org.compiere.orm.Query;
import org.compiere.orm.SetGetUtil;
import org.compiere.orm.TimeUtil;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdateEx;

/**
 * Depreciation Workfile Model
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class MDepreciationWorkfile extends X_A_Depreciation_Workfile implements UseLife {
    /**
     *
     */
    private static final long serialVersionUID = -3814417671427820714L;
    /**
     * Static cache: Asset/PostingType -> Workfile
     */
    private static CCache<MultiKey, MDepreciationWorkfile> s_cacheAsset =
            new CCache<MultiKey, MDepreciationWorkfile>(
                    I_A_Depreciation_Workfile.Table_Name,
                    I_A_Depreciation_Workfile.Table_Name + "_Asset",
                    10);
    /**
     * Asset (parent)
     */
    private MAsset m_asset = null;
    /**
     * Logger
     */
    private CLogger log = CLogger.getCLogger(getClass());
    /**
     *
     */
    private boolean m_isFiscal = false;
    /**
     * Build depreciation flag - if true, the depreciation should be built after save
     */
    private boolean m_buildDepreciation = false;

    /**
     * Default Constructor
     *
     * @param ctx                context
     * @param M_InventoryLine_ID line
     */
    public MDepreciationWorkfile(Properties ctx, int A_Depreciation_Workfile_ID) {
        super(ctx, A_Depreciation_Workfile_ID);
        if (A_Depreciation_Workfile_ID == 0) {
            setPostingType(X_A_Depreciation_Workfile.POSTINGTYPE_Actual);
            setQtyCurrent(Env.ZERO);
            setAssetCost(Env.ZERO);
            setAccumulatedDepreciation(Env.ZERO);
            setPeriodPosted(0);
            setCurrentPeriod(0);
        }
    } //	MDepreciationWorkfile

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MDepreciationWorkfile(Properties ctx, Row row) {
        super(ctx, row);
    } //	MDepreciationWorkfile

    /**
     *
     */
    public MDepreciationWorkfile(MAsset asset, String postingType, MAssetGroupAcct assetgrpacct) {
        this(asset.getCtx(), 0);
        setAssetId(asset.getAssetId());
        setOrgId(asset.getOrgId()); // @win added
        setAssetCost(asset.getA_Asset_Cost());
        setAccumulatedDepreciation(asset.getA_Accumulated_Depr());
        setAccumulatedDepreciationFiscal(asset.getA_Accumulated_Depr_F());
        setCurrentPeriod(asset.getA_Current_Period());

        setIsDepreciated(asset.isDepreciated());
        setPostingType(postingType);
        //
        // Copy UseLife values from asset group to workfile
        if (assetgrpacct == null) {
            assetgrpacct =
                    MAssetGroupAcct.forA_Asset_Group_ID(
                            asset.getCtx(), asset.getAssetGroupId(), postingType);
        }
        UseLifeImpl.copyValues(this, assetgrpacct);

        //
        // Set Date Acct from Asset
        Timestamp dateAcct = asset.getDateAcct();
        if (dateAcct != null) {
            dateAcct = TimeUtil.addMonths(dateAcct, 1);
            setDateAcct(dateAcct);
        }
        //
        // Set UseLife values from asset (if any)
        if (asset.getUseLifeMonths() > 0) {
            UseLifeImpl.get(this, false).setUseLifeMonths(asset.getUseLifeMonths());
        }
        if (asset.getUseLifeMonths_F() > 0) {
            UseLifeImpl.get(this, true).setUseLifeMonths(asset.getUseLifeMonths_F());
        }
        //
        dump();
    }

    public static Collection<MDepreciationWorkfile> forA_Asset_ID(
            Properties ctx, int asset_id) {
        return new Query(
                ctx,
                I_A_Depreciation_Workfile.Table_Name,
                I_A_Depreciation_Workfile.COLUMNNAME_A_Asset_ID + "=?"
        )
                .setParameters(new Object[]{asset_id})
                .list();
    }

    /**
     * Get/load workfile from cache (if trxName is null)
     *
     * @param ctx
     * @param A_Asset_ID
     * @param postingType
     * @param trxName
     * @return workfile
     */
    public static MDepreciationWorkfile get(
            Properties ctx, int A_Asset_ID, String postingType) {
        if (A_Asset_ID <= 0 || postingType == null) {
            return null;
        }

        final MultiKey key = new MultiKey(A_Asset_ID, postingType);
        MDepreciationWorkfile wk = s_cacheAsset.get(key);
        if (wk != null) return wk;
    /* @win temporary change as this code is causing duplicate create MDepreciationWorkfile on asset addition
    final String whereClause = COLUMNNAME_A_Asset_ID+"=?"
    							+" AND "+COLUMNNAME_PostingType+"=? AND "+COLUMNNAME_A_QTY_Current+">?";
    MDepreciationWorkfile wk = new Query(ctx, MDepreciationWorkfile.Table_Name, whereClause)
    									.setParameters(new Object[]{A_Asset_ID, postingType, 0})
    									.firstOnly();
    */
        final String whereClause =
                I_A_Depreciation_Workfile.COLUMNNAME_A_Asset_ID
                        + "=?"
                        + " AND "
                        + I_A_Depreciation_Workfile.COLUMNNAME_PostingType
                        + "=? ";
        wk =
                new Query(ctx, I_A_Depreciation_Workfile.Table_Name, whereClause)
                        .setParameters(new Object[]{A_Asset_ID, postingType})
                        .firstOnly();

        if (wk != null) {
            s_cacheAsset.put(key, wk);
        }
        return wk;
    }

    /**
     * Update Founding Mode related fields
     *
     * @param m                 model
     * @param changedColumnName column name that has been changed
     */
    public static void updateFinantare(SetGetModel m, String changedColumnName) {
        // Own contribution:
        BigDecimal valCofinantare =
                SetGetUtil.get_AttrValueAsBigDecimal(
                        m, I_A_Depreciation_Workfile.COLUMNNAME_A_Valoare_Cofinantare);
        // Asset Value:
        BigDecimal assetCost =
                SetGetUtil.get_AttrValueAsBigDecimal(m, I_A_Depreciation_Workfile.COLUMNNAME_A_Asset_Cost);
        // Third value:
        BigDecimal valTert =
                SetGetUtil.get_AttrValueAsBigDecimal(
                        m, I_A_Depreciation_Workfile.COLUMNNAME_A_Valoare_Tert);

        // Calculate values
        if (valCofinantare.signum() == 0 && valTert.signum() == 0) {
            // Values ​​have never been set, so put everything on their own financing
            valCofinantare = assetCost;
            valTert = Env.ZERO;
        } else if (I_A_Depreciation_Workfile.COLUMNNAME_A_Asset_Cost.equals(changedColumnName)) {
            valCofinantare = assetCost.subtract(valTert);
        } else if (I_A_Depreciation_Workfile.COLUMNNAME_A_Valoare_Cofinantare.equals(
                changedColumnName)) {
            valTert = assetCost.subtract(valCofinantare);
        } else if (I_A_Depreciation_Workfile.COLUMNNAME_A_Valoare_Tert.equals(changedColumnName)) {
            valCofinantare = assetCost.subtract(valTert);
        } else {
            valTert = assetCost.subtract(valCofinantare);
        }

        // Financing Type
        String tipFinantare = X_A_Depreciation_Workfile.A_TIP_FINANTARE_Cofinantare;
        if (valTert.signum() == 0) {
            tipFinantare = X_A_Depreciation_Workfile.A_TIP_FINANTARE_Proprie;
        } else if (valCofinantare.signum() == 0) {
            tipFinantare = X_A_Depreciation_Workfile.A_TIP_FINANTARE_Terti;
        }
        //
        // Set values
        m.setAttrValue(I_A_Depreciation_Workfile.COLUMNNAME_A_Tip_Finantare, tipFinantare);
        m.setAttrValue(I_A_Depreciation_Workfile.COLUMNNAME_A_Valoare_Cofinantare, valCofinantare);
        m.setAttrValue(I_A_Depreciation_Workfile.COLUMNNAME_A_Valoare_Tert, valTert);
        //
        // If the method is invoked for a persistent object when reset mode of financing
        if (X_A_Depreciation_Workfile.A_TIP_FINANTARE_Proprie.equals(tipFinantare)
                && SetGetUtil.isPersistent(m)) {
            m.setAttrValue(I_A_Depreciation_Workfile.COLUMNNAME_A_FundingMode_ID, null);
        }
    }

    /**
     * Get Asset
     */
    public MAsset getAsset() {
        return getAsset(false);
    }

    /**
     * Get asset using this trxName
     *
     * @param requery requery asset
     * @return parent asset
     */
    public MAsset getAsset(boolean requery) {
        if (m_asset == null || requery) {
            m_asset = MAsset.get(getCtx(), getAssetId());
        }
        if (m_asset.getId() <= 0) {
            m_asset = null;
        }
        return m_asset;
    }

    /**
     * Gets asset's service date (commissioning)
     *
     * @return asset service date
     */
    public Timestamp getAssetServiceDate() {
        MAsset asset = getAsset();
        if (asset == null) {
            return null;
        }
        return asset.getAssetServiceDate();
    }

    /**
     * Gets asset's class
     *
     * @return asset class id
     */
  /* commented out by @win
  public int getA_Asset_Class_ID()
  {
  	MAsset asset = getAsset();
  	if (asset == null) {
  		return 0;
  	}
  	return asset.getA_Asset_Class_ID();
  }
  */
    // end comment by @win
    protected boolean beforeSave(boolean newRecord) {
        if (log.isLoggable(Level.INFO)) log.info("Entering: trxName=" + null);

        // copy UseLife to A_Life
        if (newRecord) { // @win: should only update only if newrecord
            setLifePeriod(getUseLifeMonths());
            setAssetLifeYears(getUseLifeYears());
            setLifePeriodFiscal(getUseLifeMonths_F());
            setAssetLifeYearsFiscal(getUseLifeYearsFiscal());
        }

        // If it is fully amortized, change the state's FA
        MAsset asset = getAsset(true);
        if (MAsset.A_ASSET_STATUS_Activated.equals(asset.getAssetStatus()) && isFullyDepreciated()) {
            asset.changeStatus(MAsset.A_ASSET_STATUS_Depreciated, null);
            asset.saveEx();
        }

        // Fix DateAcct
        if (is_ValueChanged(I_A_Depreciation_Workfile.COLUMNNAME_DateAcct)) {
            setDateAcct(TimeUtil.getMonthLastDay(getDateAcct()));
        }

        //
        BigDecimal cost = getAssetCost();
        BigDecimal accumDep_C = getAccumulatedDepreciation();
        setAssetRemaining(cost.subtract(accumDep_C));
        BigDecimal accumDep_F = getAccumulatedDepreciationFiscal();
        setAssetRemainingFiscal(cost.subtract(accumDep_F));

        // Financing
        {
            String mainColumnName = null;
            if (newRecord || is_ValueChanged(I_A_Depreciation_Workfile.COLUMNNAME_A_Asset_Cost)) {
                mainColumnName = I_A_Depreciation_Workfile.COLUMNNAME_A_Asset_Cost;
            } else if (is_ValueChanged(I_A_Depreciation_Workfile.COLUMNNAME_A_Valoare_Cofinantare)) {
                mainColumnName = I_A_Depreciation_Workfile.COLUMNNAME_A_Valoare_Cofinantare;
            } else if (is_ValueChanged(I_A_Depreciation_Workfile.COLUMNNAME_A_Valoare_Tert)) {
                mainColumnName = I_A_Depreciation_Workfile.COLUMNNAME_A_Valoare_Tert;
            }
            updateFinantare(this, mainColumnName);
        }

        if (log.isLoggable(Level.INFO))
            log.info("Leaving: trxName=" + null + " [RETURN TRUE]");
        return true;
    } //	beforeSave

    /**
     * Asset is fully depreciated
     *
     * <ul>
     * <li>If PostingType != ACTUAL then return false
     * <li>Do not check your current asset
     * </ul>
     *
     * @return true if the asset is fully depreciated, false otherwise
     */
    public boolean isFullyDepreciated() {
        if (!getPostingType().equals(X_A_Depreciation_Workfile.POSTINGTYPE_Actual)) {
            return false;
        }

        // check if is fully depreciated
        BigDecimal remainingAmt_C = getRemainingCost(null, false);
        BigDecimal remainingAmt_F = getRemainingCost(null, true);
        if (remainingAmt_C.signum() == 0 && remainingAmt_F.signum() == 0) {
            // if A_Asset_Cost is 0 have a voided addition, in this case asset is not full depreciated
            if (getAssetCost().signum() == 0) {
                return false;
            }
            //
            return true;
        }

        return false;
    }

    /**
     * Get Asset Accounting for this workfile
     *
     * @return asset accounting model
     */
    public MAssetAcct getA_AssetAcct(Timestamp dateAcct) {
        return MAssetAcct.forA_Asset_ID(getCtx(), getAssetId(), getPostingType(), dateAcct);
    }

    /**
     * Returns the current cost of FAs. It is calculated as the difference between acquisition value
     * and the value that you (A_Salvage_Value)
     *
     * @return the current cost of FAs
     */
    public BigDecimal getActualCost() {
        return getActualCost(getAssetCost());
    }

    /**
     *
     */
    public BigDecimal getActualCost(BigDecimal assetCost) {
        return assetCost.subtract(getSalvageValue());
    }

    /**
     * Adjust Accumulated depreciation
     *
     * @param amt
     * @param amt_F
     * @param reset
     * @return
     */
    public boolean adjustAccumulatedDepr(BigDecimal amt, BigDecimal amt_F, boolean reset) {
        if (amt == null) {
            amt = Env.ZERO;
        }
        if (amt_F == null) {
            amt_F = Env.ZERO;
        }
        setAccumulatedDepreciation(amt.add(reset ? Env.ZERO : getAccumulatedDepreciation()));
        setAccumulatedDepreciationFiscal(amt_F.add(reset ? Env.ZERO : getAccumulatedDepreciationFiscal()));
        return true;
    }

    /**
     *
     */
    public int getUseLifeMonths(boolean fiscal) {
        return fiscal ? getUseLifeMonths_F() : getUseLifeMonths();
    }

    /**
     *
     */
    public BigDecimal getA_Accumulated_Depr(boolean fiscal) {
        return fiscal ? getAccumulatedDepreciationFiscal() : getAccumulatedDepreciation();
    }

    /**
     * Returns the residual (remaining) value
     */
    public BigDecimal getRemainingCost(BigDecimal accumAmt, boolean fiscal) {
        BigDecimal cost = getActualCost();
        if (accumAmt == null) {
            accumAmt = getA_Accumulated_Depr(fiscal);
        }
        return cost.subtract(accumAmt);
    }

    /**
     * Returns the residual (remaining) value
     */
    public BigDecimal getRemainingCost(BigDecimal accumAmt) {
        return getRemainingCost(accumAmt, isFiscal());
    }

    /**
     *
     */
    public int getRemainingPeriods(int A_Current_Period, MDepreciation method) {
        int useLifePeriods = getUseLifeMonths(isFiscal());
        if (method != null) {
            useLifePeriods += method.getFixMonthOffset();
        }
        int currentPeriod = (A_Current_Period >= 0 ? A_Current_Period : getCurrentPeriod());
        return useLifePeriods - currentPeriod;
    }

    /**
     *
     */
    public int getRemainingPeriods(int A_Current_Period) {
        return getRemainingPeriods(A_Current_Period, null);
    }

    /**
     *
     */
    public boolean isFiscal() {
        return m_isFiscal;
    }

    /**
     * Set fiscal flag (temporary - is not modifing the workfile)
     *
     * @param fiscal
     */
    public void setFiscal(boolean fiscal) {
        m_isFiscal = fiscal;
    }

    /**
     * Increment the current period (A_Current_Period) 1, and a month DateAcct
     */
    public void incA_Current_Period() {
        int old_period = getCurrentPeriod();
        Timestamp old_date = getDateAcct();
        int new_period = old_period + 1;
        Timestamp new_date = TimeUtil.addMonths(getDateAcct(), 1);
        setCurrentPeriod(new_period);
        setDateAcct(new_date);
        //
        if (log.isLoggable(Level.FINE))
            log.fine(
                    "(A_Current_Period, DateAcct)=("
                            + old_period
                            + ", "
                            + old_date
                            + ")->("
                            + new_period
                            + ", "
                            + new_date
                            + ")");
    }

    /**
     * Set A Current Period (and Data Act) processed just after the last expense. Do not save.
     */
    public void setA_Current_Period() {
        String whereClause =
                MDepreciationExp.COLUMNNAME_A_Asset_ID
                        + "=?"
                        + " AND "
                        + MDepreciationExp.COLUMNNAME_PostingType
                        + "=?"
                        + " AND "
                        + MDepreciationExp.COLUMNNAME_Processed
                        + "=? AND IsActive=?";
        //
        MDepreciationExp depexp =
                new Query(getCtx(), MDepreciationExp.Table_Name, whereClause)
                        .setParameters(new Object[]{getAssetId(), getPostingType(), true, true})
                        .setOrderBy(
                                MDepreciationExp.COLUMNNAME_A_Period
                                        + " DESC"
                                        + ","
                                        + MDepreciationExp.COLUMNNAME_DateAcct
                                        + " DESC")
                        .first();
        if (depexp != null) {
            setCurrentPeriod(depexp.getPeriod());
            setDateAcct(depexp.getDateAcct());
            incA_Current_Period();
        } else {
            log.info("There are no records from which to infer its");
        }
    }

    /**
     * Truncate not processed depreciation entries. IS NOT modifying workfile.
     */
    public void truncDepreciation() {
        String trxName = null;

        int A_Current_Period = getCurrentPeriod();
        final String sql =
                "DELETE FROM "
                        + MDepreciationExp.Table_Name
                        + " WHERE "
                        + MDepreciationExp.COLUMNNAME_Processed
                        + "=?"
                        + " AND "
                        + MDepreciationExp.COLUMNNAME_A_Period
                        + ">=?"
                        + " AND "
                        + MDepreciationExp.COLUMNNAME_A_Asset_ID
                        + "=?"
                        + " AND "
                        + MDepreciationExp.COLUMNNAME_PostingType
                        + "=?";
        Object[] params = new Object[]{false, A_Current_Period, getAssetId(), getPostingType()};
        int no = executeUpdateEx(sql, params);
        if (log.isLoggable(Level.FINE)) log.fine("sql=" + sql + "\nDeleted #" + no);
    } //	truncDepreciation

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
        return is_ValueChanged(index);
    }

} //	MDepreciationWorkfile
