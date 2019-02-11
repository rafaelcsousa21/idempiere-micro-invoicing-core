package org.compiere.production;

import org.compiere.orm.MOrgInfo;
import org.compiere.orm.Query;
import org.compiere.product.MResource;
import org.compiere.wf.MWorkflow;
import org.eevolution.model.I_PP_Product_Planning;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.CLogMgt;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.getSQLValueEx;

/**
 * Product Data Planning
 *
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
public class MPPProductPlanning extends X_PP_Product_Planning {
  /** */
  private static final long serialVersionUID = -3061309620804116277L;

  /** Log */
  private static CLogger log = CLogger.getCLogger(MPPProductPlanning.class);

  /**
   * ************************************************************************ Default Constructor
   *
   * @param ctx context
   * @param pp_product_planning_id id
   * @param trxName
   * @return MPPProductPlanning Data Product Planning
   */
  public MPPProductPlanning(Properties ctx, int pp_product_planning_id, String trxname) {
    super(ctx, pp_product_planning_id, trxname);
    if (pp_product_planning_id == 0) {}
  } //	MPPProductPlanning

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName Transaction Name
   * @return MPPProductPlanning Data Product Planning
   */
  public MPPProductPlanning(Properties ctx, ResultSet rs, String trxname) {
    super(ctx, rs, trxname);
  }

  /**
   * Get Data Product Planning to Organization
   *
   * @param ctx Context
   * @param ad_org_id Organization ID
   * @param m_product_id Product ID
   * @param trxName Transaction Name
   * @return MPPProductPlanning
   */
  public static MPPProductPlanning get(
      Properties ctx, int ad_client_id, int ad_org_id, int m_product_id, String trxName) {
    int M_Warehouse_ID = MOrgInfo.get(ctx, ad_org_id, trxName).getM_Warehouse_ID();
    if (M_Warehouse_ID <= 0) {
      return null;
    }

    int S_Resource_ID = getPlantForWarehouse(M_Warehouse_ID);
    if (S_Resource_ID <= 0) return null;

    return get(ctx, ad_client_id, ad_org_id, M_Warehouse_ID, S_Resource_ID, m_product_id, trxName);
  }

  /**
   * Get Data Product Planning
   *
   * @param ctx Context
   * @param AD_Client_ID ID Organization
   * @param AD_Org_ID ID Organization
   * @param M_Warehouse_ID Warehouse
   * @param S_Resource_ID Resource type Plant
   * @param M_Product_ID ID Product
   * @param trxname Trx Name
   * @return MPPProductPlanning
   */
  public static MPPProductPlanning get(
      Properties ctx,
      int ad_client_id,
      int ad_org_id,
      int m_warehouse_id,
      int s_resource_id,
      int m_product_id,
      String trxname) {
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

    return new Query(ctx, MPPProductPlanning.Table_Name, whereClause, trxname)
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
    if (getOrder_Policy() == null) {
      setOrder_Policy(X_PP_Product_Planning.ORDER_POLICY_Lot_For_Lot);
    }
    //
    // Check Order_Min < Order_Max
    if (getOrder_Min().signum() > 0
        && getOrder_Max().signum() > 0
        && getOrder_Min().compareTo(getOrder_Max()) > 0) {
      throw new AdempiereException("@Order_Min@ > @Order_Max@");
    }
    //
    // Check Order_Period
    if (X_PP_Product_Planning.ORDER_POLICY_PeriodOrderQuantity.equals(getOrder_Policy())
        && getOrder_Period().signum() <= 0) {
      throw new AdempiereException("@Order_Period@ <= 0");
    }
    //
    // Check Order_Qty
    if (X_PP_Product_Planning.ORDER_POLICY_FixedOrderQuantity.equals(getOrder_Policy())
        && getOrder_Qty().signum() <= 0) {
      throw new AdempiereException("@Order_Qty@ <= 0");
    }
    //
    return true;
  }

  @Override
  public MPPProductBOM getPP_Product_BOM() {
    return MPPProductBOM.get(getCtx(), getPP_Product_BOM_ID());
  }

  @Override
  public MWorkflow getAD_Workflow() {
    return MWorkflow.get(getCtx(), getAD_Workflow_ID());
  }

  @Override
  public MResource getS_Resource() {
    return MResource.get(getCtx(), getS_Resource_ID());
  }

  private int m_C_BPartner_ID = 0;

    /** @return Supplier */
  public int getC_BPartner_ID() {
    return this.m_C_BPartner_ID;
  }

  public void dump() {
    if (!CLogMgt.isLevelInfo()) return;
    log.info("------------ Planning Data --------------");
    log.info("           Create Plan: " + isCreatePlan());
    log.info("              Resource: " + getS_Resource_ID());
    log.info("          M_Product_ID: " + getM_Product_ID());
    log.info("                   BOM: " + getPP_Product_BOM_ID());
    log.info("              Workflow: " + getAD_Workflow_ID());
    log.info("  Network Distribution: " + getDD_NetworkDistribution_ID());
    log.info("Delivery Time Promised: " + getDeliveryTime_Promised());
    log.info("         TransfertTime: " + getTransfertTime());
    log.info("         Order Min/Max: " + getOrder_Min() + " / " + getOrder_Max());
    log.info("            Order Pack: " + getOrder_Pack());
    log.info("          Safety Stock: " + getSafetyStock());
    log.info("          Order Period: " + getOrder_Period());
    log.info("          Order Policy: " + getOrder_Policy());
    log.info("             Warehouse: " + getM_Warehouse_ID());
    log.info("               Planner: " + getPlanner_ID());
    log.info("              Supplier: " + getC_BPartner_ID());
  }
} //	Product Data Planning
