package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.orm.Query;
import org.compiere.product.MProduct;
import org.compiere.production.MLocator;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.*;

/**
 * Inventory Storage Model
 *
 * @author Jorg Janke
 * @version $Id: MStorageOnHand.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MStorageOnHand extends X_M_StorageOnHand {
    /**
     *
     */
    private static final long serialVersionUID = -3820729340100521329L;
    /**
     * Log
     */
    private static CLogger s_log = CLogger.getCLogger(MStorageOnHand.class);
    /**
     * Warehouse
     */
    private int m_M_Warehouse_ID = 0;

    /**
     * ************************************************************************ Persistency
     * Constructor
     *
     * @param ctx     context
     * @param ignored ignored
     * @param trxName transaction
     */
    public MStorageOnHand(Properties ctx, int ignored) {
        super(ctx, 0);
        if (ignored != 0) throw new IllegalArgumentException("Multi-Key");
        //
        setQtyOnHand(Env.ZERO);
    } //	MStorageOnHand

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MStorageOnHand(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MStorageOnHand

    public MStorageOnHand(Properties ctx, Row row) {
        super(ctx, row);
    } //	MStorageOnHand

    /**
     * Full NEW Constructor
     *
     * @param locator                   (parent) locator
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID attribute
     */
    private MStorageOnHand(
            MLocator locator, int M_Product_ID, int M_AttributeSetInstance_ID, Timestamp dateMPolicy) {
        this(locator.getCtx(), 0);
        setClientOrg(locator);
        setM_Locator_ID(locator.getM_Locator_ID());
        setM_Product_ID(M_Product_ID);
        setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
        dateMPolicy = Util.removeTime(dateMPolicy);
        setDateMaterialPolicy(dateMPolicy);
    } //	MStorageOnHand

    /**
     * @param ctx
     * @param M_Locator_ID
     * @param M_Product_ID
     * @param M_AttributeSetInstance_ID
     * @param trxName
     * @return MStorageOnHand
     * @deprecated
     */
    public static MStorageOnHand get(
            Properties ctx,
            int M_Locator_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID) {
        return get(ctx, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID, null);
    }

    /**
     * Get Storage Info
     *
     * @param ctx                       context
     * @param M_Locator_ID              locator
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID instance
     * @param dateMPolicy
     * @param trxName                   transaction
     * @return existing or null
     */
    public static MStorageOnHand get(
            Properties ctx,
            int M_Locator_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            Timestamp dateMPolicy) {
        String sqlWhere = "M_Locator_ID=? AND M_Product_ID=? AND ";
        if (M_AttributeSetInstance_ID == 0)
            sqlWhere += "(M_AttributeSetInstance_ID=? OR M_AttributeSetInstance_ID IS NULL)";
        else sqlWhere += "M_AttributeSetInstance_ID=?";

        if (dateMPolicy != null) sqlWhere += " AND DateMaterialPolicy=trunc(cast(? as date))";

        Query query = new Query(ctx, MStorageOnHand.Table_Name, sqlWhere);
        if (dateMPolicy != null)
            query.setParameters(M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID, dateMPolicy);
        else query.setParameters(M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID);
        MStorageOnHand retValue = query.first();

        if (retValue == null) {
            if (s_log.isLoggable(Level.FINE))
                s_log.fine(
                        "Not Found - M_Locator_ID="
                                + M_Locator_ID
                                + ", M_Product_ID="
                                + M_Product_ID
                                + ", M_AttributeSetInstance_ID="
                                + M_AttributeSetInstance_ID);
        } else {
            if (s_log.isLoggable(Level.FINE))
                s_log.fine(
                        "M_Locator_ID="
                                + M_Locator_ID
                                + ", M_Product_ID="
                                + M_Product_ID
                                + ", M_AttributeSetInstance_ID="
                                + M_AttributeSetInstance_ID);
        }
        return retValue;
    } //	get

    /**
     * Get all Storages for Product where QtyOnHand <> 0
     *
     * @param ctx          context
     * @param M_Product_ID product
     * @param M_Locator_ID locator
     * @param trxName      transaction
     * @return existing or null
     */
    public static MStorageOnHand[] getAll(
            Properties ctx,
            int M_Product_ID,
            int M_Locator_ID,
            String trxName,
            boolean forUpdate,
            int timeout) {
        String sqlWhere = "M_Product_ID=? AND M_Locator_ID=? AND QtyOnHand <> 0";
        Query query =
                new Query(ctx, MStorageOnHand.Table_Name, sqlWhere)
                        .setParameters(M_Product_ID, M_Locator_ID);
        MProduct product = new MProduct(ctx, M_Product_ID);
        if (product.isUseGuaranteeDateForMPolicy()) {
            query
                    .addJoinClause(
                            " LEFT OUTER JOIN M_AttributeSetInstance asi ON (M_StorageOnHand.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID) ")
                    .setOrderBy("asi." + I_M_AttributeSetInstance.COLUMNNAME_GuaranteeDate);
        } else {
            query.setOrderBy(
                    MStorageOnHand.COLUMNNAME_DateMaterialPolicy
                            + ","
                            + MStorageOnHand.COLUMNNAME_M_AttributeSetInstance_ID);
        }
        if (forUpdate) {
            query.setForUpdate(forUpdate);
            if (timeout > 0) {
                query.setQueryTimeout(timeout);
            }
        }
        List<MStorageOnHand> list = query.list();

        MStorageOnHand[] retValue = new MStorageOnHand[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getAll

    /**
     * Get Storage Info for Product across warehouses
     *
     * @param ctx          context
     * @param M_Product_ID product
     * @param trxName      transaction
     * @return existing or null
     */
    public static MStorageOnHand[] getOfProduct(Properties ctx, int M_Product_ID) {
        String sqlWhere = "M_Product_ID=?";

        List<MStorageOnHand> list =
                new Query(ctx, MStorageOnHand.Table_Name, sqlWhere)
                        .setParameters(M_Product_ID)
                        .list();

        MStorageOnHand[] retValue = new MStorageOnHand[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getOfProduct

    /**
     * Get Storage Info for Warehouse
     *
     * @param ctx                       context
     * @param M_Warehouse_ID
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID instance
     * @param M_AttributeSet_ID         attribute set (NOT USED)
     * @param allAttributeInstances     if true, all attribute set instances (NOT USED)
     * @param minGuaranteeDate          optional minimum guarantee date if all attribute instances
     * @param FiFo                      first in-first-out
     * @param trxName                   transaction
     * @return existing - ordered by location priority (desc) and/or guarantee date
     * @deprecated
     */
    public static MStorageOnHand[] getWarehouse(
            Properties ctx,
            int M_Warehouse_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            int M_AttributeSet_ID,
            boolean allAttributeInstances,
            Timestamp minGuaranteeDate,
            boolean FiFo,
            String trxName) {
        return getWarehouse(
                ctx,
                M_Warehouse_ID,
                M_Product_ID,
                M_AttributeSetInstance_ID,
                minGuaranteeDate,
                FiFo,
                false,
                0,
                trxName);
    }

    /**
     * Get Storage Info for Warehouse or locator
     *
     * @param ctx                       context
     * @param M_Warehouse_ID            ignore if M_Locator_ID > 0
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID instance id, 0 to retrieve all instance
     * @param minGuaranteeDate          optional minimum guarantee date if all attribute instances
     * @param FiFo                      first in-first-out
     * @param positiveOnly              if true, only return storage records with qtyOnHand > 0
     * @param M_Locator_ID              optional locator id
     * @param trxName                   transaction
     * @return existing - ordered by location priority (desc) and/or guarantee date
     */
    public static MStorageOnHand[] getWarehouse(
            Properties ctx,
            int M_Warehouse_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            Timestamp minGuaranteeDate,
            boolean FiFo,
            boolean positiveOnly,
            int M_Locator_ID,
            String trxName) {
        return getWarehouse(
                ctx,
                M_Warehouse_ID,
                M_Product_ID,
                M_AttributeSetInstance_ID,
                minGuaranteeDate,
                FiFo,
                positiveOnly,
                M_Locator_ID,
                trxName,
                false);
    }

    /**
     * Get Storage Info for Warehouse or locator
     *
     * @param ctx                       context
     * @param M_Warehouse_ID            ignore if M_Locator_ID > 0
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID instance id, 0 to retrieve all instance
     * @param minGuaranteeDate          optional minimum guarantee date if all attribute instances
     * @param FiFo                      first in-first-out
     * @param positiveOnly              if true, only return storage records with qtyOnHand > 0
     * @param M_Locator_ID              optional locator id
     * @param trxName                   transaction
     * @param forUpdate
     * @return existing - ordered by location priority (desc) and/or guarantee date
     */
    public static MStorageOnHand[] getWarehouse(
            Properties ctx,
            int M_Warehouse_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            Timestamp minGuaranteeDate,
            boolean FiFo,
            boolean positiveOnly,
            int M_Locator_ID,
            String trxName,
            boolean forUpdate) {
        return getWarehouse(
                ctx,
                M_Warehouse_ID,
                M_Product_ID,
                M_AttributeSetInstance_ID,
                minGuaranteeDate,
                FiFo,
                positiveOnly,
                M_Locator_ID,
                trxName,
                forUpdate,
                0);
    }

    /**
     * Get Storage Info for Warehouse or locator
     *
     * @param ctx                       context
     * @param M_Warehouse_ID            ignore if M_Locator_ID > 0
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID instance id, 0 to retrieve all instance
     * @param minGuaranteeDate          optional minimum guarantee date if all attribute instances
     * @param FiFo                      first in-first-out
     * @param positiveOnly              if true, only return storage records with qtyOnHand > 0
     * @param M_Locator_ID              optional locator id
     * @param trxName                   transaction
     * @param forUpdate
     * @return existing - ordered by location priority (desc) and/or guarantee date
     */
    public static MStorageOnHand[] getWarehouse(
            Properties ctx,
            int M_Warehouse_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            Timestamp minGuaranteeDate,
            boolean FiFo,
            boolean positiveOnly,
            int M_Locator_ID,
            String trxName,
            boolean forUpdate,
            int timeout) {
        if ((M_Warehouse_ID == 0 && M_Locator_ID == 0) || M_Product_ID == 0)
            return new MStorageOnHand[0];

        boolean allAttributeInstances = false;
        if (M_AttributeSetInstance_ID == 0) allAttributeInstances = true;

        ArrayList<MStorageOnHand> list = new ArrayList<MStorageOnHand>();
        //	Specific Attribute Set Instance
        String sql =
                "SELECT s.M_Product_ID,s.M_Locator_ID,s.M_AttributeSetInstance_ID,"
                        + "s.AD_Client_ID,s.AD_Org_ID,s.IsActive,s.Created,s.CreatedBy,s.Updated,s.UpdatedBy,"
                        + "s.QtyOnHand,s.DateLastInventory,s.M_StorageOnHand_UU,s.DateMaterialPolicy "
                        + "FROM M_StorageOnHand s"
                        + " INNER JOIN M_Locator l ON (l.M_Locator_ID=s.M_Locator_ID) ";
        if (M_Locator_ID > 0) sql += "WHERE l.M_Locator_ID = ?";
        else sql += "WHERE l.M_Warehouse_ID=?";
        sql += " AND s.M_Product_ID=?" + " AND COALESCE(s.M_AttributeSetInstance_ID,0)=? ";
        if (positiveOnly) {
            sql += " AND s.QtyOnHand > 0 ";
        } else {
            sql += " AND s.QtyOnHand <> 0 ";
        }
        sql += "ORDER BY l.PriorityNo DESC, DateMaterialPolicy ";
        if (!FiFo) sql += " DESC, s.M_AttributeSetInstance_ID DESC ";
        else sql += ", s.M_AttributeSetInstance_ID ";
        //	All Attribute Set Instances
        if (allAttributeInstances) {
            sql =
                    "SELECT s.M_Product_ID,s.M_Locator_ID,s.M_AttributeSetInstance_ID,"
                            + " s.AD_Client_ID,s.AD_Org_ID,s.IsActive,s.Created,s.CreatedBy,s.Updated,s.UpdatedBy,"
                            + " s.QtyOnHand,s.DateLastInventory,s.M_StorageOnHand_UU,s.DateMaterialPolicy "
                            + " FROM M_StorageOnHand s"
                            + " INNER JOIN M_Locator l ON (l.M_Locator_ID=s.M_Locator_ID)"
                            + " LEFT OUTER JOIN M_AttributeSetInstance asi ON (s.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID) ";
            if (M_Locator_ID > 0) sql += "WHERE l.M_Locator_ID = ?";
            else sql += "WHERE l.M_Warehouse_ID=?";
            sql += " AND s.M_Product_ID=? ";
            if (positiveOnly) {
                sql += " AND s.QtyOnHand > 0 ";
            } else {
                sql += " AND s.QtyOnHand <> 0 ";
            }

            if (minGuaranteeDate != null) {
                sql += "AND (asi.GuaranteeDate IS NULL OR asi.GuaranteeDate>?) ";
            }

            MProduct product = MProduct.get(Env.getCtx(), M_Product_ID);

            if (product.isUseGuaranteeDateForMPolicy()) {
                sql += "ORDER BY l.PriorityNo DESC, COALESCE(asi.GuaranteeDate,s.DateMaterialPolicy)";
                if (!FiFo) sql += " DESC, s.M_AttributeSetInstance_ID DESC ";
                else sql += ", s.M_AttributeSetInstance_ID ";
            } else {
                sql += "ORDER BY l.PriorityNo DESC, l.M_Locator_ID, s.DateMaterialPolicy";
                if (!FiFo) sql += " DESC, s.M_AttributeSetInstance_ID DESC ";
                else sql += ", s.M_AttributeSetInstance_ID ";
            }

            sql += ", s.QtyOnHand DESC";
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, M_Locator_ID > 0 ? M_Locator_ID : M_Warehouse_ID);
            pstmt.setInt(2, M_Product_ID);
            if (!allAttributeInstances) {
                pstmt.setInt(3, M_AttributeSetInstance_ID);
            } else if (minGuaranteeDate != null) {
                pstmt.setTimestamp(3, minGuaranteeDate);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getBigDecimal(11).signum() != 0) {
                    MStorageOnHand storage = new MStorageOnHand(ctx, rs);
                    if (!Util.isEmpty(trxName) && forUpdate) {
                        forUpdate(storage);
                    }
                    list.add(storage);
                }
            }
        } catch (Exception e) {
            s_log.log(Level.SEVERE, sql, e);
        } finally {

            rs = null;
            pstmt = null;
        }
        MStorageOnHand[] retValue = new MStorageOnHand[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getWarehouse

    /**
     * Get Storage Info for Warehouse or locator
     *
     * @param ctx                       context
     * @param M_Warehouse_ID            ignore if M_Locator_ID > 0
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID instance id, 0 to retrieve storages that don't have asi, -1 to
     *                                  retrieve all instance
     * @param minGuaranteeDate          optional minimum guarantee date if all attribute instances
     * @param FiFo                      first in-first-out
     * @param M_Locator_ID              optional locator id
     * @param trxName                   transaction
     * @param forUpdate
     * @return existing - ordered by location priority (desc) and/or guarantee date
     */
    public static MStorageOnHand[] getWarehouseNegative(
            Properties ctx,
            int M_Warehouse_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            Timestamp minGuaranteeDate,
            boolean FiFo,
            int M_Locator_ID,
            String trxName,
            boolean forUpdate) {
        return getWarehouseNegative(
                ctx,
                M_Warehouse_ID,
                M_Product_ID,
                M_AttributeSetInstance_ID,
                minGuaranteeDate,
                FiFo,
                M_Locator_ID,
                trxName,
                forUpdate,
                0);
    }

    /**
     * Get Storage Info for Warehouse or locator
     *
     * @param ctx                       context
     * @param M_Warehouse_ID            ignore if M_Locator_ID > 0
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID instance id, 0 to retrieve storages that don't have asi, -1 to
     *                                  retrieve all instance
     * @param minGuaranteeDate          optional minimum guarantee date if all attribute instances
     * @param FiFo                      first in-first-out
     * @param M_Locator_ID              optional locator id
     * @param trxName                   transaction
     * @param forUpdate
     * @param timeout
     * @return existing - ordered by location priority (desc) and/or guarantee date
     */
    public static MStorageOnHand[] getWarehouseNegative(
            Properties ctx,
            int M_Warehouse_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            Timestamp minGuaranteeDate,
            boolean FiFo,
            int M_Locator_ID,
            String trxName,
            boolean forUpdate,
            int timeout) {
        if ((M_Warehouse_ID == 0 && M_Locator_ID == 0) || M_Product_ID == 0)
            return new MStorageOnHand[0];

        ArrayList<MStorageOnHand> list = new ArrayList<MStorageOnHand>();
        String sql =
                "SELECT s.M_Product_ID,s.M_Locator_ID,s.M_AttributeSetInstance_ID,"
                        + "s.AD_Client_ID,s.AD_Org_ID,s.IsActive,s.Created,s.CreatedBy,s.Updated,s.UpdatedBy,"
                        + "s.QtyOnHand,s.DateLastInventory,s.M_StorageOnHand_UU,s.DateMaterialPolicy "
                        + "FROM M_StorageOnHand s"
                        + " INNER JOIN M_Locator l ON (l.M_Locator_ID=s.M_Locator_ID)"
                        + " LEFT OUTER JOIN M_AttributeSetInstance asi ON (s.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID) ";
        if (M_Locator_ID > 0) sql += "WHERE l.M_Locator_ID = ?";
        else sql += "WHERE l.M_Warehouse_ID=?";
        sql += " AND s.M_Product_ID=? " + " AND s.QtyOnHand < 0 ";

        if (minGuaranteeDate != null) {
            sql += "AND (asi.GuaranteeDate IS NULL OR asi.GuaranteeDate>?) ";
        }

        if (M_AttributeSetInstance_ID > 0) {
            sql += "AND s.M_AttributeSetInstance_ID=? ";
        } else if (M_AttributeSetInstance_ID == 0) {
            sql += "AND (s.M_AttributeSetInstance_ID=0 OR s.M_AttributeSetInstance_ID IS NULL) ";
        }

        MProduct product = MProduct.get(Env.getCtx(), M_Product_ID);

        if (product.isUseGuaranteeDateForMPolicy()) {
            sql += "ORDER BY l.PriorityNo DESC, " + "asi.GuaranteeDate";
            if (!FiFo) sql += " DESC";
        } else {
            sql += "ORDER BY l.PriorityNo DESC, l.M_Locator_ID, s.DateMaterialPolicy";
            if (!FiFo) sql += " DESC, s.M_AttributeSetInstance_ID DESC ";
            else sql += ", s.M_AttributeSetInstance_ID ";
        }

        sql += ", s.QtyOnHand DESC";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            int index = 0;
            pstmt = prepareStatement(sql);
            pstmt.setInt(++index, M_Locator_ID > 0 ? M_Locator_ID : M_Warehouse_ID);
            pstmt.setInt(++index, M_Product_ID);
            if (minGuaranteeDate != null) {
                pstmt.setTimestamp(++index, minGuaranteeDate);
            }
            if (M_AttributeSetInstance_ID > 0) {
                pstmt.setInt(++index, M_AttributeSetInstance_ID);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getBigDecimal(11).signum() != 0) {
                    MStorageOnHand storage = new MStorageOnHand(ctx, rs);
                    if (!Util.isEmpty(trxName) && forUpdate) {
                        forUpdate(storage);
                    }
                    list.add(storage);
                }
            }
        } catch (Exception e) {
            s_log.log(Level.SEVERE, sql, e);
        } finally {

            rs = null;
            pstmt = null;
        }
        MStorageOnHand[] retValue = new MStorageOnHand[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getWarehouse

    /**
     * Create or Get Storage Info
     *
     * @param ctx                       context
     * @param M_Locator_ID              locator
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID instance
     * @param trxName                   transaction
     * @return existing/new or null
     */
    public static MStorageOnHand getCreate(
            Properties ctx,
            int M_Locator_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            Timestamp dateMPolicy,
            String trxName) {
        return getCreate(
                ctx, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID, dateMPolicy, trxName, false);
    }

    /**
     * Create or Get Storage Info
     *
     * @param ctx                       context
     * @param M_Locator_ID              locator
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID instance
     * @param trxName                   transaction
     * @param forUpdate
     * @return existing/new or null
     */
    public static MStorageOnHand getCreate(
            Properties ctx,
            int M_Locator_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            Timestamp dateMPolicy,
            String trxName,
            boolean forUpdate) {
        return getCreate(
                ctx,
                M_Locator_ID,
                M_Product_ID,
                M_AttributeSetInstance_ID,
                dateMPolicy,
                trxName,
                forUpdate,
                0);
    }

    /**
     * Create or Get Storage Info
     *
     * @param ctx                       context
     * @param M_Locator_ID              locator
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID instance
     * @param trxName                   transaction
     * @param forUpdate
     * @param timeout
     * @return existing/new or null
     */
    public static MStorageOnHand getCreate(
            Properties ctx,
            int M_Locator_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            Timestamp dateMPolicy,
            String trxName,
            boolean forUpdate,
            int timeout) {
        if (M_Locator_ID == 0) throw new IllegalArgumentException("M_Locator_ID=0");
        if (M_Product_ID == 0) throw new IllegalArgumentException("M_Product_ID=0");
        if (dateMPolicy != null) dateMPolicy = Util.removeTime(dateMPolicy);

        MStorageOnHand retValue =
                get(ctx, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID, dateMPolicy);
        if (retValue != null) {
            if (forUpdate) forUpdate(retValue);
            return retValue;
        }

        //	Insert row based on locator
        MLocator locator = new MLocator(ctx, M_Locator_ID);
        if (locator.getId() != M_Locator_ID)
            throw new IllegalArgumentException("Not found M_Locator_ID=" + M_Locator_ID);
        //
        if (dateMPolicy == null) {
            dateMPolicy = new Timestamp(new Date().getTime());
            dateMPolicy = Util.removeTime(dateMPolicy);
        }
        retValue = new MStorageOnHand(locator, M_Product_ID, M_AttributeSetInstance_ID, dateMPolicy);
        retValue.saveEx();
        if (s_log.isLoggable(Level.FINE)) s_log.fine("New " + retValue);
        return retValue;
    } //	getCreate

    /**
     * Update Storage Info add. Called from MProjectIssue
     *
     * @param ctx                                context
     * @param M_Warehouse_ID                     warehouse
     * @param M_Locator_ID                       locator
     * @param M_Product_ID                       product
     * @param M_AttributeSetInstance_ID          AS Instance
     * @param reservationAttributeSetInstance_ID reservation AS Instance
     * @param diffQtyOnHand                      add on hand
     * @param trxName                            transaction
     * @return true if updated
     * @deprecated
     */
    public static boolean add(
            Properties ctx,
            int M_Warehouse_ID,
            int M_Locator_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            BigDecimal diffQtyOnHand,
            String trxName) {
        return add(
                ctx,
                M_Warehouse_ID,
                M_Locator_ID,
                M_Product_ID,
                M_AttributeSetInstance_ID,
                diffQtyOnHand,
                null,
                trxName);
    }

    /**
     * Update Storage Info add. Called from MProjectIssue
     *
     * @param ctx                                context
     * @param M_Warehouse_ID                     warehouse
     * @param M_Locator_ID                       locator
     * @param M_Product_ID                       product
     * @param M_AttributeSetInstance_ID          AS Instance
     * @param reservationAttributeSetInstance_ID reservation AS Instance
     * @param diffQtyOnHand                      add on hand
     * @param dateMPolicy
     * @param trxName                            transaction
     * @return true if updated
     */
    public static boolean add(
            Properties ctx,
            int M_Warehouse_ID,
            int M_Locator_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            BigDecimal diffQtyOnHand,
            Timestamp dateMPolicy,
            String trxName) {
        if (diffQtyOnHand == null || diffQtyOnHand.signum() == 0) return true;

        if (dateMPolicy != null) dateMPolicy = Util.removeTime(dateMPolicy);

        //	Get Storage
        MStorageOnHand storage =
                getCreate(
                        ctx,
                        M_Locator_ID,
                        M_Product_ID,
                        M_AttributeSetInstance_ID,
                        dateMPolicy,
                        trxName,
                        true,
                        120);
        //	Verify
        if (storage.getM_Locator_ID() != M_Locator_ID
                && storage.getM_Product_ID() != M_Product_ID
                && storage.getMAttributeSetInstance_ID() != M_AttributeSetInstance_ID) {
            s_log.severe(
                    "No Storage found - M_Locator_ID="
                            + M_Locator_ID
                            + ",M_Product_ID="
                            + M_Product_ID
                            + ",ASI="
                            + M_AttributeSetInstance_ID);
            return false;
        }

        storage.addQtyOnHand(diffQtyOnHand);
        if (s_log.isLoggable(Level.FINE)) {
            StringBuilder diffText =
                    new StringBuilder("(OnHand=")
                            .append(diffQtyOnHand)
                            .append(") -> ")
                            .append(storage.toString());
            s_log.fine(diffText.toString());
        }
        return true;
    } //	add

    /**
     * ************************************************************************ Get Location with
     * highest Locator Priority and a sufficient OnHand Qty
     *
     * @param M_Warehouse_ID            warehouse
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID asi
     * @param Qty                       qty
     * @param trxName                   transaction
     * @return id
     */
    public static int getM_Locator_ID(
            int M_Warehouse_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            BigDecimal Qty,
            String trxName) {
        int M_Locator_ID = 0;
        int firstM_Locator_ID = 0;
        String sql =
                "SELECT s.M_Locator_ID, s.QtyOnHand "
                        + "FROM M_StorageOnHand s"
                        + " INNER JOIN M_Locator l ON (s.M_Locator_ID=l.M_Locator_ID)"
                        + " INNER JOIN M_Product p ON (s.M_Product_ID=p.M_Product_ID)"
                        + " LEFT OUTER JOIN M_AttributeSet mas ON (p.M_AttributeSet_ID=mas.M_AttributeSet_ID) "
                        + "WHERE l.M_Warehouse_ID=?"
                        + " AND s.M_Product_ID=?"
                        + " AND (mas.IsInstanceAttribute IS NULL OR mas.IsInstanceAttribute='N' OR s.M_AttributeSetInstance_ID=?)"
                        + " AND l.IsActive='Y' "
                        + "ORDER BY l.PriorityNo DESC, s.QtyOnHand DESC";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, M_Warehouse_ID);
            pstmt.setInt(2, M_Product_ID);
            pstmt.setInt(3, M_AttributeSetInstance_ID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BigDecimal QtyOnHand = rs.getBigDecimal(2);
                if (QtyOnHand != null && Qty.compareTo(QtyOnHand) <= 0) {
                    M_Locator_ID = rs.getInt(1);
                    break;
                }
                if (firstM_Locator_ID == 0) firstM_Locator_ID = rs.getInt(1);
            }
        } catch (SQLException ex) {
            s_log.log(Level.SEVERE, sql, ex);
        } finally {

            rs = null;
            pstmt = null;
        }
        if (M_Locator_ID != 0) return M_Locator_ID;
        return firstM_Locator_ID;
    } //	getM_Locator_ID

    /**
     * Get Quantity On Hand of Warehouse Available for Reservation
     *
     * @param M_Product_ID
     * @param M_Warehouse_ID
     * @param M_AttributeSetInstance_ID
     * @param trxName
     * @return QtyOnHand
     */
    public static BigDecimal getQtyOnHandForReservation(
            int M_Product_ID, int M_Warehouse_ID, int M_AttributeSetInstance_ID) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                " SELECT SUM(QtyOnHand) FROM M_StorageOnHand oh"
                        + " JOIN M_Locator loc ON (oh.M_Locator_ID=loc.M_Locator_ID)"
                        + " LEFT JOIN M_LocatorType lt ON (loc.M_LocatorType_ID=lt.M_LocatorType_ID)")
                .append(" WHERE oh.M_Product_ID=?")
                .append(" AND loc.M_Warehouse_ID=? AND COALESCE(lt.IsAvailableForReservation,'Y')='Y'");

        ArrayList<Object> params = new ArrayList<Object>();
        params.add(M_Product_ID);
        params.add(M_Warehouse_ID);

        // With ASI
        if (M_AttributeSetInstance_ID != 0) {
            sql.append(" AND oh.M_AttributeSetInstance_ID=?");
            params.add(M_AttributeSetInstance_ID);
        }

        BigDecimal qty = getSQLValueBD(sql.toString(), params);
        if (qty == null) qty = Env.ZERO;

        return qty;
    }

    /**
     * @param M_Product_ID
     * @param M_AttributeSetInstance_ID
     * @param trxName
     * @return datempolicy timestamp
     */
    public static Timestamp getDateMaterialPolicy(
            int M_Product_ID, int M_AttributeSetInstance_ID) {

        if (M_Product_ID <= 0 || M_AttributeSetInstance_ID <= 0) return null;

        String sql =
                "SELECT dateMaterialPolicy FROM M_StorageOnHand WHERE M_Product_ID=? and M_AttributeSetInstance_ID=? ORDER BY QtyOnHand DESC";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, M_Product_ID);
            pstmt.setInt(2, M_AttributeSetInstance_ID);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp(1);
            }
        } catch (SQLException ex) {
            s_log.log(Level.SEVERE, sql, ex);

        } finally {

            rs = null;
            pstmt = null;
        }

        return null;
    } // getDateMaterialPolicy

    /**
     * @param M_Product_ID
     * @param M_AttributeSetInstance_ID
     * @param M_Locator_ID
     * @param trxName
     * @return datempolicy timestamp
     */
    public static Timestamp getDateMaterialPolicy(
            int M_Product_ID, int M_AttributeSetInstance_ID, int M_Locator_ID) {

        if (M_Product_ID <= 0 || M_AttributeSetInstance_ID <= 0) return null;

        String sql =
                "SELECT dateMaterialPolicy FROM M_StorageOnHand WHERE M_Product_ID=? and M_AttributeSetInstance_ID=? AND M_Locator_ID=? ORDER BY QtyOnHand DESC";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, M_Product_ID);
            pstmt.setInt(2, M_AttributeSetInstance_ID);
            pstmt.setInt(3, M_Locator_ID);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp(1);
            }
        } catch (SQLException ex) {
            s_log.log(Level.SEVERE, sql, ex);

        } finally {

            rs = null;
            pstmt = null;
        }

        return null;
    } // getDateMaterialPolicy

    /**
     * Add quantity on hand directly - not using cached value - solving IDEMPIERE-2629
     *
     * @param addition
     */
    public void addQtyOnHand(BigDecimal addition) {
        final String sql =
                "UPDATE M_StorageOnHand SET QtyOnHand=QtyOnHand+?, Updated=SYSDATE, UpdatedBy=? "
                        + "WHERE M_Product_ID=? AND M_Locator_ID=? AND M_AttributeSetInstance_ID=? AND DateMaterialPolicy=?";
        executeUpdateEx(
                sql,
                new Object[]{
                        addition,
                        Env.getUserId(Env.getCtx()),
                        getM_Product_ID(),
                        getM_Locator_ID(),
                        getMAttributeSetInstance_ID(),
                        getDateMaterialPolicy()
                }
        );
        load((HashMap) null);
        if (getQtyOnHand().signum() == -1) {
            MWarehouse wh = MWarehouse.get(Env.getCtx(), getWarehouseId());
            if (wh.isDisallowNegativeInv()) {
                throw new NegativeInventoryDisallowedException(
                        getCtx(),
                        getWarehouseId(),
                        getM_Product_ID(),
                        getMAttributeSetInstance_ID(),
                        getM_Locator_ID(),
                        getQtyOnHand().subtract(addition),
                        addition.negate());
            }
        }
    }

    /**
     * Get M_Warehouse_ID of Locator
     *
     * @return warehouse
     */
    public int getWarehouseId() {
        if (m_M_Warehouse_ID == 0) {
            MLocator loc = MLocator.get(getCtx(), getM_Locator_ID());
            m_M_Warehouse_ID = loc.getWarehouseId();
        }
        return m_M_Warehouse_ID;
    } //	getWarehouseId

    /**
     * Before Save
     *
     * @param newRecord new
     * @param success   success
     * @return success
     */
    @Override
    protected boolean beforeSave(boolean newRecord) {
        //	Negative Inventory check
        if (newRecord || is_ValueChanged("QtyOnHand")) {
            MWarehouse wh = new MWarehouse(getCtx(), getWarehouseId());
            if (wh.isDisallowNegativeInv()) {
                String sql =
                        "SELECT SUM(QtyOnHand) "
                                + "FROM M_StorageOnHand s"
                                + " INNER JOIN M_Locator l ON (s.M_Locator_ID=l.M_Locator_ID) "
                                + "WHERE s.M_Product_ID=?" //	#1
                                + " AND l.M_Warehouse_ID=?"
                                + " AND l.M_Locator_ID=?"
                                + " AND s.M_AttributeSetInstance_ID<>?";
                BigDecimal QtyOnHand =
                        getSQLValueBDEx(
                                sql,
                                new Object[]{
                                        getM_Product_ID(),
                                        getWarehouseId(),
                                        getM_Locator_ID(),
                                        getMAttributeSetInstance_ID()
                                });
                if (QtyOnHand == null) QtyOnHand = Env.ZERO;

                // Add qty onhand for current record
                QtyOnHand = QtyOnHand.add(getQtyOnHand());

                if (getQtyOnHand().compareTo(BigDecimal.ZERO) < 0 || QtyOnHand.compareTo(Env.ZERO) < 0) {
                    log.saveError(
                            "Error",
                            new NegativeInventoryDisallowedException(
                                    getCtx(),
                                    getWarehouseId(),
                                    getM_Product_ID(),
                                    getMAttributeSetInstance_ID(),
                                    getM_Locator_ID(),
                                    QtyOnHand.subtract(getQtyOnHand()),
                                    getQtyOnHand().negate()));
                    return false;
                }

                if (getMAttributeSetInstance_ID() > 0 && getQtyOnHand().signum() < 0) {
                    log.saveError(
                            "Error",
                            new NegativeInventoryDisallowedException(
                                    getCtx(),
                                    getWarehouseId(),
                                    getM_Product_ID(),
                                    getMAttributeSetInstance_ID(),
                                    getM_Locator_ID(),
                                    QtyOnHand.subtract(getQtyOnHand()),
                                    getQtyOnHand().negate()));
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuffer sb =
                new StringBuffer("MStorageOnHand[")
                        .append("M_Locator_ID=")
                        .append(getM_Locator_ID())
                        .append(",M_Product_ID=")
                        .append(getM_Product_ID())
                        .append(",M_AttributeSetInstance_ID=")
                        .append(getMAttributeSetInstance_ID())
                        .append(",DateMaterialPolicy=")
                        .append(getDateMaterialPolicy())
                        .append(": OnHand=")
                        .append(getQtyOnHand())
                        /* @win commented out
                        .append(",Reserved=").append(getQtyReserved())
                        .append(",Ordered=").append(getQtyOrdered())
                        */
                        .append("]");
        return sb.toString();
    } //	toString
} //	MStorageOnHand
