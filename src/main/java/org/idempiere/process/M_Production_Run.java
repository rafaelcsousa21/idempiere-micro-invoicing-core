/**
 * **************************************************************************** Product: Adempiere
 * ERP & CRM Smart Business Solution * This program is free software; you can redistribute it and/or
 * modify it * under the terms version 2 of the GNU General Public License as published * by the
 * Free Software Foundation. This program is distributed in the hope * that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied * warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. * See the GNU General Public License for more details. * You should have
 * received a copy of the GNU General Public License along * with this program; if not, write to the
 * Free Software Foundation, Inc., * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. * For
 * the text or an alternative of this public license, you may reach us * Copyright (C) 2003-2007
 * e-Evolution,SC. All Rights Reserved. * Contributor(s): Victor Perez www.e-evolution.com *
 * ***************************************************************************
 */
package org.idempiere.process;

import org.compiere.accounting.MClientKt;
import org.compiere.accounting.MProduct;
import org.compiere.accounting.MStorageOnHand;
import org.compiere.accounting.MStorageReservation;
import org.compiere.docengine.DocumentEngine;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.orm.Query;
import org.compiere.process.SvrProcess;
import org.compiere.production.MLocator;
import org.compiere.production.MPPProductBOM;
import org.compiere.production.MTransaction;
import org.compiere.production.X_M_Production;
import org.compiere.production.X_M_ProductionLine;
import org.compiere.production.X_M_ProductionPlan;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.idempiere.common.util.AdempiereUserError;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.ValueNamePair;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdateEx;

/**
 * Production of BOMs 1) Creating ProductionLines when IsCreated = 'N' 2) Posting the Lines
 * (optionally only when fully stocked)
 *
 * @author victor.perez@e-evolution.com
 * @contributor: Carlos Ruiz (globalqss) - review backward compatibility - implement mustBeStocked
 * properly
 */
@Deprecated // replaced by ProductionProcess
public class M_Production_Run extends SvrProcess {

    /**
     * The Record
     */
    private int p_Record_ID = 0;

    private boolean mustBeStocked = false;

    private int m_level = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("MustBeStocked"))
                mustBeStocked = iProcessInfoParameter.getParameter().equals("Y");
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
        p_Record_ID = getRecordId();
    } // prepare

    /**
     * Process
     *
     * @return message
     * @throws Exception
     */
    protected String doIt() throws Exception {
        log.info("Search fields in M_Production");

        X_M_Production production = new X_M_Production(p_Record_ID);
        /** No Action */
        if (production.isProcessed()) {
            log.info("Already Posted");
            return "@AlreadyPosted@";
        }

        String whereClause = "M_Production_ID=? ";
        List<X_M_ProductionPlan> lines =
                new Query(X_M_ProductionPlan.Table_Name, whereClause)
                        .setParameters(p_Record_ID)
                        .setOrderBy("Line, M_Product_ID")
                        .list();
        for (X_M_ProductionPlan pp : lines) {

            if (!"Y".equals(production.getIsCreated())) {
                int line = 100;
                int no =
                        executeUpdateEx(
                                "DELETE M_ProductionLine WHERE M_ProductionPlan_ID = ?",
                                new Object[]{pp.getProductionPlanId()}
                        );
                if (no == -1)
                    raiseError(
                            "ERROR",
                            "DELETE M_ProductionLine WHERE M_ProductionPlan_ID = " + pp.getProductionPlanId());

                MProduct product = MProduct.get(pp.getProductId());

                X_M_ProductionLine pl = new X_M_ProductionLine(0);
                pl.setOrgId(pp.getOrgId());
                pl.setLine(line);
                pl.setDescription(pp.getDescription());
                pl.setProductId(pp.getProductId());
                pl.setLocatorId(pp.getLocatorId());
                pl.setProductionPlanId(pp.getProductionPlanId());
                pl.setMovementQty(pp.getProductionQty());
                pl.saveEx();
                if (explosion(pp, product, pp.getProductionQty(), line) == 0)
                    raiseError("No BOM Lines", "");

            } else {
                whereClause = "M_ProductionPlan_ID= ? ";
                List<X_M_ProductionLine> production_lines =
                        new Query(X_M_ProductionLine.Table_Name, whereClause)
                                .setParameters(pp.getProductionPlanId())
                                .setOrderBy("Line")
                                .list();

                for (X_M_ProductionLine pline : production_lines) {
                    MLocator locator = MLocator.get(pline.getLocatorId());
                    String MovementType = MTransaction.MOVEMENTTYPE_ProductionPlus;
                    BigDecimal MovementQty = pline.getMovementQty();
                    if (MovementQty.signum() == 0) continue;
                    else if (MovementQty.signum() < 0) {
                        BigDecimal QtyAvailable =
                                MStorageReservation.getQtyAvailable(
                                        locator.getWarehouseId(),
                                        pline.getProductId(),
                                        pline.getAttributeSetInstanceId());

                        if (mustBeStocked && QtyAvailable.add(MovementQty).signum() < 0) {
                            raiseError("@NotEnoughStocked@: " + pline.getProduct().getName(), "");
                        }

                        MovementType = MTransaction.MOVEMENTTYPE_Production_;
                    }

                    Timestamp dateMPolicy = production.getMovementDate();
                    if (pline.getAttributeSetInstanceId() > 0) {
                        Timestamp t =
                                MStorageOnHand.getDateMaterialPolicy(
                                        pline.getProductId(), pline.getAttributeSetInstanceId());
                        if (t != null) dateMPolicy = t;
                    }

                    if (!MStorageOnHand.add(
                            locator.getWarehouseId(),
                            locator.getLocatorId(),
                            pline.getProductId(),
                            pline.getAttributeSetInstanceId(),
                            MovementQty,
                            dateMPolicy
                    )) {
                        raiseError("Cannot correct Inventory", "");
                    }

                    // Create Transaction
                    MTransaction mtrx =
                            new MTransaction(
                                    pline.getOrgId(),
                                    MovementType,
                                    locator.getLocatorId(),
                                    pline.getProductId(),
                                    pline.getAttributeSetInstanceId(),
                                    MovementQty,
                                    production.getMovementDate()
                            );
                    mtrx.setProductionLineId(pline.getProductionLineId());
                    mtrx.saveEx();

                    pline.setProcessed(true);
                    pline.saveEx();
                } // Production Line

                pp.setProcessed(true);
                pp.saveEx();
            }
        } // Production Plan

        if (!"Y".equals(production.getIsCreated())) {
            production.setIsCreated("Y");
            production.saveEx();
        } else {
            production.setProcessed(true);
            production.saveEx();

            /* Immediate accounting */
            if (MClientKt.getClientWithAccounting().isClientAccountingImmediate()) {
                @SuppressWarnings("unused")
                String ignoreError =
                        DocumentEngine.postImmediate(
                                getClientId(),
                                production.getTableId(),
                                production.getId(),
                                true
                        );
            }
        }

        return "@OK@";
    }

    /**
     * Explosion the Production Plan
     *
     * @param pp
     * @param product
     * @param qty
     * @throws Exception
     */
    private int explosion(X_M_ProductionPlan pp, MProduct product, BigDecimal qty, int line)
            throws Exception {
        I_PP_Product_BOM bom = MPPProductBOM.getDefault(product);
        if (bom == null) {
            raiseError(
                    "Do not exist default BOM for this product :"
                            + product.getSearchKey()
                            + "-"
                            + product.getName(),
                    "");
        }
        I_PP_Product_BOMLine[] bom_lines = bom.getLines(new Timestamp(System.currentTimeMillis()));
        m_level += 1;
        int components = 0;
        line = line * m_level;
        for (I_PP_Product_BOMLine bomline : bom_lines) {
            MProduct component = MProduct.get(bomline.getProductId());

            if (component.isBOM() && !component.isStocked()) {
                explosion(pp, component, bomline.getQtyBOM().multiply(qty), line);
            } else {
                line += 1;
                X_M_ProductionLine pl = new X_M_ProductionLine(0);
                pl.setOrgId(pp.getOrgId());
                pl.setLine(line);
                pl.setDescription(bomline.getDescription());
                pl.setProductId(bomline.getProductId());
                pl.setLocatorId(pp.getLocatorId());
                pl.setProductionPlanId(pp.getProductionPlanId());
                pl.setMovementQty(bomline.getQtyBOM().multiply(qty).negate());
                pl.saveEx();
                components += 1;
            }
        }
        return components;
    }

    private void raiseError(String string, String sql) throws Exception {
        StringBuilder msg = new StringBuilder(string);
        ValueNamePair pp = CLogger.retrieveError();
        if (pp != null) msg = new StringBuilder(pp.getName()).append(" - ");
        msg.append(sql);
        throw new AdempiereUserError(msg.toString());
    }
} // M_Production_Run
