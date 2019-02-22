/**
 *
 */
package org.compiere.production;

import org.compiere.accounting.MClient;
import org.compiere.accounting.MStorageOnHand;
import org.compiere.product.MAttributeSetInstance;
import org.compiere.product.MProduct;
import org.compiere.product.MProductCategory;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.AdempiereUserError;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.prepareStatement;

/** @author hengsin */
public class MProductionPlan extends X_M_ProductionPlan {

    /** generated serial id */
    private static final long serialVersionUID = -8189507724698695756L;

    /**
     * @param ctx
     * @param M_ProductionPlan_ID
     * @param trxName
     */
    public MProductionPlan(Properties ctx, int M_ProductionPlan_ID) {
        super(ctx, M_ProductionPlan_ID);
    }

    /**
     * @param ctx
     * @param rs
     * @param trxName
     */
    public MProductionPlan(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    public MProductionLine[] getLines() {
        ArrayList<MProductionLine> list = new ArrayList<MProductionLine>();

        String sql =
                "SELECT pl.M_ProductionLine_ID "
                        + "FROM M_ProductionLine pl "
                        + "WHERE pl.M_ProductionPlan_ID = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, getId());
            rs = pstmt.executeQuery();
            while (rs.next()) list.add(new MProductionLine(getCtx(), rs.getInt(1)));
        } catch (SQLException ex) {
            throw new AdempiereException("Unable to load production lines", ex);
        } finally {

            rs = null;
            pstmt = null;
        }

        MProductionLine[] retValue = new MProductionLine[list.size()];
        list.toArray(retValue);
        return retValue;
    }

    public void deleteLines(String trxName) {

        for (MProductionLine line : getLines()) {
            line.deleteEx(true);
        }
    } // deleteLines

    public int createLines(boolean mustBeStocked) {

        int lineno = 100;

        int count = 0;

        // product to be produced
        MProduct finishedProduct = new MProduct(getCtx(), getM_Product_ID());

        MProductionLine line = new MProductionLine(this);
        line.setLine(lineno);
        line.setM_Product_ID(finishedProduct.getId());
        line.setM_Locator_ID(getM_Locator_ID());
        line.setMovementQty(getProductionQty());
        line.setPlannedQty(getProductionQty());

        line.saveEx();
        count++;

        count = count + createLines(mustBeStocked, finishedProduct, getProductionQty(), lineno);

        return count;
    }

    private int createLines(
            boolean mustBeStocked, MProduct finishedProduct, BigDecimal requiredQty, int lineno) {

        int count = 0;
        int defaultLocator = 0;

        MLocator finishedLocator = MLocator.get(getCtx(), getM_Locator_ID());

        int M_Warehouse_ID = finishedLocator.getM_Warehouse_ID();

        int asi = 0;

        // products used in production
        String sql =
                "SELECT M_ProductBom_ID, BOMQty"
                        + " FROM M_Product_BOM"
                        + " WHERE M_Product_ID="
                        + finishedProduct.getM_Product_ID()
                        + " ORDER BY Line";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {

                lineno = lineno + 10;
                int BOMProduct_ID = rs.getInt(1);
                BigDecimal BOMQty = rs.getBigDecimal(2);
                BigDecimal BOMMovementQty = BOMQty.multiply(requiredQty);

                MProduct bomproduct = new MProduct(Env.getCtx(), BOMProduct_ID);

                if (bomproduct.isBOM() && bomproduct.isPhantom()) {
                    count = count + createLines(mustBeStocked, bomproduct, BOMMovementQty, lineno);
                } else {

                    defaultLocator = bomproduct.getM_Locator_ID();
                    if (defaultLocator == 0) defaultLocator = getM_Locator_ID();

                    if (!bomproduct.isStocked()) {
                        MProductionLine BOMLine = null;
                        BOMLine = new MProductionLine(this);
                        BOMLine.setLine(lineno);
                        BOMLine.setM_Product_ID(BOMProduct_ID);
                        BOMLine.setM_Locator_ID(defaultLocator);
                        BOMLine.setQtyUsed(BOMMovementQty);
                        BOMLine.setPlannedQty(BOMMovementQty);
                        BOMLine.saveEx();

                        lineno = lineno + 10;
                        count++;
                    } else if (BOMMovementQty.signum() == 0) {
                        MProductionLine BOMLine = null;
                        BOMLine = new MProductionLine(this);
                        BOMLine.setLine(lineno);
                        BOMLine.setM_Product_ID(BOMProduct_ID);
                        BOMLine.setM_Locator_ID(defaultLocator);
                        BOMLine.setQtyUsed(BOMMovementQty);
                        BOMLine.setPlannedQty(BOMMovementQty);
                        BOMLine.saveEx();

                        lineno = lineno + 10;
                        count++;
                    } else {

                        // BOM stock info
                        MStorageOnHand[] storages = null;
                        MProduct usedProduct = MProduct.get(getCtx(), BOMProduct_ID);
                        defaultLocator = usedProduct.getM_Locator_ID();
                        if (defaultLocator == 0) defaultLocator = getM_Locator_ID();
                        if (usedProduct == null || usedProduct.getId() == 0) return 0;

                        MClient client = MClient.get(getCtx());
                        MProductCategory pc =
                                MProductCategory.get(getCtx(), usedProduct.getM_Product_Category_ID());
                        String MMPolicy = pc.getMMPolicy();
                        if (MMPolicy == null || MMPolicy.length() == 0) {
                            MMPolicy = client.getMMPolicy();
                        }

                        storages =
                                MStorageOnHand.getWarehouse(
                                        getCtx(),
                                        M_Warehouse_ID,
                                        BOMProduct_ID,
                                        0,
                                        null,
                                        MProductCategory.MMPOLICY_FiFo.equals(MMPolicy),
                                        true,
                                        0,
                                        null);

                        MProductionLine BOMLine = null;
                        int prevLoc = -1;
                        int previousAttribSet = -1;
                        // Create lines from storage until qty is reached
                        for (int sl = 0; sl < storages.length; sl++) {

                            BigDecimal lineQty = storages[sl].getQtyOnHand();
                            if (lineQty.signum() != 0) {
                                if (lineQty.compareTo(BOMMovementQty) > 0) lineQty = BOMMovementQty;

                                int loc = storages[sl].getM_Locator_ID();
                                int slASI = storages[sl].getMAttributeSetInstance_ID();
                                int locAttribSet =
                                        new MAttributeSetInstance(getCtx(), asi).getMAttributeSet_ID();

                                // roll up costing attributes if in the same locator
                                if (locAttribSet == 0 && previousAttribSet == 0 && prevLoc == loc) {
                                    BOMLine.setQtyUsed(BOMLine.getQtyUsed().add(lineQty));
                                    BOMLine.setPlannedQty(BOMLine.getQtyUsed());
                                    BOMLine.saveEx();

                                }
                                // otherwise create new line
                                else {
                                    BOMLine = new MProductionLine(this);
                                    BOMLine.setLine(lineno);
                                    BOMLine.setM_Product_ID(BOMProduct_ID);
                                    BOMLine.setM_Locator_ID(loc);
                                    BOMLine.setQtyUsed(lineQty);
                                    BOMLine.setPlannedQty(lineQty);
                                    if (slASI != 0 && locAttribSet != 0) // ie non costing attribute
                                        BOMLine.setM_AttributeSetInstance_ID(slASI);
                                    BOMLine.saveEx();

                                    lineno = lineno + 10;
                                    count++;
                                }
                                prevLoc = loc;
                                previousAttribSet = locAttribSet;
                                // enough ?
                                BOMMovementQty = BOMMovementQty.subtract(lineQty);
                                if (BOMMovementQty.signum() == 0) break;
                            }
                        } // for available storages

                        // fallback
                        if (BOMMovementQty.signum() != 0) {
                            if (!mustBeStocked) {

                                // roll up costing attributes if in the same locator
                                if (previousAttribSet == 0 && prevLoc == defaultLocator) {
                                    BOMLine.setQtyUsed(BOMLine.getQtyUsed().add(BOMMovementQty));
                                    BOMLine.setPlannedQty(BOMLine.getQtyUsed());
                                    BOMLine.saveEx();

                                }
                                // otherwise create new line
                                else {

                                    BOMLine = new MProductionLine(this);
                                    BOMLine.setLine(lineno);
                                    BOMLine.setM_Product_ID(BOMProduct_ID);
                                    BOMLine.setM_Locator_ID(defaultLocator);
                                    BOMLine.setQtyUsed(BOMMovementQty);
                                    BOMLine.setPlannedQty(BOMMovementQty);
                                    BOMLine.saveEx();

                                    lineno = lineno + 10;
                                    count++;
                                }

                            } else {
                                throw new AdempiereUserError("Not enough stock of " + BOMProduct_ID);
                            }
                        }
                    }
                }
            } // for all bom products
        } catch (Exception e) {
            throw new AdempiereException("Failed to create production lines", e);
        } finally {

        }

        return count;
    }

    @Override
    protected boolean beforeDelete() {
        deleteLines(null);
        return true;
    }
}
