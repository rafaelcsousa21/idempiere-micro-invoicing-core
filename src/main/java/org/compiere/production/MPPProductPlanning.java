package org.compiere.production;

import kotliquery.Row;
import org.compiere.orm.MOrgInfoKt;
import org.compiere.orm.Query;
import org.compiere.product.MResource;
import org.eevolution.model.I_PP_Product_Planning;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.CLogMgt;
import org.idempiere.common.util.CLogger;

import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.getSQLValueEx;

/**
 * Product Data Planning
 *
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
public class MPPProductPlanning extends X_PP_Product_Planning {
    /**
     *
     */
    private static final long serialVersionUID = -3061309620804116277L;

    /**
     * Log
     */
    private static CLogger log = CLogger.getCLogger(MPPProductPlanning.class);
    private int m_C_BPartner_ID = 0;

    /**
     * ************************************************************************ Default Constructor
     *
     * @param ctx                    context
     * @param pp_product_planning_id id
     * @return MPPProductPlanning Data Product Planning
     */
    public MPPProductPlanning(int pp_product_planning_id) {
        super(pp_product_planning_id);
    } //	MPPProductPlanning

    /**
     * Load Constructor
     *
     * @param ctx context
     * @return MPPProductPlanning Data Product Planning
     */
    public MPPProductPlanning(Row row) {
        super(row);
    }

    /**
     * Get Data Product Planning to Organization
     *
     * @param ctx          Context
     * @param ad_org_id    Organization ID
     * @param m_product_id Product ID
     * @return MPPProductPlanning
     */
    public static MPPProductPlanning get(
            int ad_client_id, int ad_org_id, int m_product_id) {
        int M_Warehouse_ID = MOrgInfoKt.getOrganizationInfo(ad_org_id).getWarehouseId();
        if (M_Warehouse_ID <= 0) {
            return null;
        }

        int S_Resource_ID = getPlantForWarehouse(M_Warehouse_ID);
        if (S_Resource_ID <= 0) return null;

        return get(ad_client_id, ad_org_id, M_Warehouse_ID, S_Resource_ID, m_product_id);
    }

    /**
     * Get Data Product Planning
     *
     * @param ctx Context
     *            *
     * @return MPPProductPlanning
     */
    public static MPPProductPlanning get(

            int ad_client_id,
            int ad_org_id,
            int m_warehouse_id,
            int s_resource_id,
            int m_product_id) {
        if (log.isLoggable(Level.INFO))
            log.info(
                    "AD_Client_ID="
                            + ad_client_id
                            + " AD_Org_ID="
                            + ad_org_id
                            + " M_Product_ID="
                            + m_product_id
                            + " M_Warehouse_ID="
                            + m_warehouse_id
                            + " S_Resource_ID="
                            + s_resource_id);
        String sql_warehouse = I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID + "=?";
        if (m_warehouse_id == 0) {
            sql_warehouse += " OR " + I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID + " IS NULL";
        }

        String whereClause =
                " AD_Client_ID=? AND AD_Org_ID=?"
                        + " AND "
                        + I_PP_Product_Planning.COLUMNNAME_M_Product_ID
                        + "=?"
                        + " AND ("
                        + sql_warehouse
                        + ")"
                        + " AND "
                        + I_PP_Product_Planning.COLUMNNAME_S_Resource_ID
                        + "=?";

        return new Query(MPPProductPlanning.Table_Name, whereClause)
                .setParameters(ad_client_id, ad_org_id, m_product_id, m_warehouse_id, s_resource_id)
                .setOnlyActiveRecords(true)
                .firstOnly();
    }

    /**
     * Get plant resource for warehouse. If more than one resource is found, first will be used.
     *
     * @param M_Warehouse_ID
     * @return Plant_ID (S_Resource_ID)
     */
    public static int getPlantForWarehouse(int M_Warehouse_ID) {
        final String sql =
                "SELECT MIN("
                        + MResource.COLUMNNAME_S_Resource_ID
                        + ")"
                        + " FROM "
                        + MResource.Table_Name
                        + " WHERE "
                        + MResource.COLUMNNAME_IsManufacturingResource
                        + "=?"
                        + " AND "
                        + MResource.COLUMNNAME_ManufacturingResourceType
                        + "=?"
                        + " AND "
                        + MResource.COLUMNNAME_M_Warehouse_ID
                        + "=?";
        int plant_id =
                getSQLValueEx(
                        sql, true, MResource.MANUFACTURINGRESOURCETYPE_Plant, M_Warehouse_ID);
        return plant_id;
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {
        //
        // Set default : Order_Policy
        if (getOrderPolicy() == null) {
            setOrderPolicy(X_PP_Product_Planning.ORDER_POLICY_Lot_For_Lot);
        }
        //
        // Check Order_Min < Order_Max
        if (getOrderMin().signum() > 0
                && getOrderMax().signum() > 0
                && getOrderMin().compareTo(getOrderMax()) > 0) {
            throw new AdempiereException("@Order_Min@ > @Order_Max@");
        }
        //
        // Check Order_Period
        if (X_PP_Product_Planning.ORDER_POLICY_PeriodOrderQuantity.equals(getOrderPolicy())
                && getOrderPeriod().signum() <= 0) {
            throw new AdempiereException("@Order_Period@ <= 0");
        }
        //
        // Check Order_Qty
        if (X_PP_Product_Planning.ORDER_POLICY_FixedOrderQuantity.equals(getOrderPolicy())
                && getOrderQty().signum() <= 0) {
            throw new AdempiereException("@Order_Qty@ <= 0");
        }
        //
        return true;
    }

    @Override
    public MPPProductBOM getProductBOM() {
        return MPPProductBOM.get(getProductBOMId());
    }

    @Override
    public MResource getResource() {
        return MResource.get(getResourceID());
    }

    /**
     * @return Supplier
     */
    public int getBusinessPartnerId() {
        return this.m_C_BPartner_ID;
    }

    public void dump() {
        if (!CLogMgt.isLevelInfo()) return;
        log.info("------------ Planning Data --------------");
        log.info("           Create Plan: " + isCreatePlan());
        log.info("              Resource: " + getResourceID());
        log.info("          M_Product_ID: " + getProductId());
        log.info("                   BOM: " + getProductBOMId());
        log.info("              Workflow: " + getWorkflowId());
        log.info("  Network Distribution: " + getNetworkDistributionId());
        log.info("Delivery Time Promised: " + getDeliveryTimePromised());
        log.info("         TransfertTime: " + getTransfertTime());
        log.info("         Order Min/Max: " + getOrderMin() + " / " + getOrderMax());
        log.info("            Order Pack: " + getOrderPack());
        log.info("          Safety Stock: " + getSafetyStock());
        log.info("          Order Period: " + getOrderPeriod());
        log.info("          Order Policy: " + getOrderPolicy());
        log.info("             Warehouse: " + getWarehouseId());
        log.info("               Planner: " + getPlannerId());
        log.info("              Supplier: " + getBusinessPartnerId());
    }
} //	Product Data Planning
