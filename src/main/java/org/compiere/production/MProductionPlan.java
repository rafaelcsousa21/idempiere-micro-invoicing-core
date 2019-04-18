package org.compiere.production;

import kotliquery.Row;
import org.compiere.accounting.MClientKt;
import org.compiere.accounting.MStorageOnHand;
import org.compiere.model.ClientWithAccounting;
import org.compiere.model.I_M_Product;
import org.compiere.product.MAttributeSetInstance;
import org.compiere.product.MProduct;
import org.compiere.product.MProductCategory;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.AdempiereUserError;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * @author hengsin
 */
public class MProductionPlan extends X_M_ProductionPlan {

    /**
     * generated serial id
     */
    private static final long serialVersionUID = -8189507724698695756L;

    /**
     * @param M_ProductionPlan_ID
     */
    public MProductionPlan(int M_ProductionPlan_ID) {
        super(M_ProductionPlan_ID);
    }

    /**
     *
     */
    public MProductionPlan(Row row) {
        super(row);
    }

    public MProductionLine[] getLines() {
        ArrayList<MProductionLine> list = new ArrayList<>();

        String sql =
                "SELECT pl.M_ProductionLine_ID "
                        + "FROM M_ProductionLine pl "
                        + "WHERE pl.M_ProductionPlan_ID = ?";

        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, getId());
            rs = pstmt.executeQuery();
            while (rs.next()) list.add(new MProductionLine(rs.getInt(1)));
        } catch (SQLException ex) {
            throw new AdempiereException("Unable to load production lines", ex);
        }

        MProductionLine[] retValue = new MProductionLine[list.size()];
        list.toArray(retValue);
        return retValue;
    }

    public void deleteLines() {

        for (MProductionLine line : getLines()) {
            line.deleteEx(true);
        }
    } // deleteLines

    public int createLines(boolean mustBeStocked) {

        int lineno = 100;

        int count = 0;

        // product to be produced
        MProduct finishedProduct = new MProduct(getProductId());

        MProductionLine line = new MProductionLine(this);
        line.setLine(lineno);
        line.setProductId(finishedProduct.getId());
        line.setLocatorId(getLocatorId());
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
        int defaultLocator;

        MLocator finishedLocator = MLocator.get(getLocatorId());

        int M_Warehouse_ID = finishedLocator.getWarehouseId();

        int asi = 0;

        // products used in production
        String sql =
                "SELECT M_ProductBom_ID, BOMQty"
                        + " FROM M_Product_BOM"
                        + " WHERE M_Product_ID="
                        + finishedProduct.getProductId()
                        + " ORDER BY Line";

        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {

                lineno = lineno + 10;
                int BOMProduct_ID = rs.getInt(1);
                BigDecimal BOMQty = rs.getBigDecimal(2);
                BigDecimal BOMMovementQty = BOMQty.multiply(requiredQty);

                MProduct bomproduct = new MProduct(BOMProduct_ID);

                if (bomproduct.isBOM() && bomproduct.isPhantom()) {
                    count = count + createLines(mustBeStocked, bomproduct, BOMMovementQty, lineno);
                } else {

                    defaultLocator = bomproduct.getLocatorId();
                    if (defaultLocator == 0) defaultLocator = getLocatorId();

                    if (!bomproduct.isStocked()) {
                        MProductionLine BOMLine;
                        BOMLine = new MProductionLine(this);
                        BOMLine.setLine(lineno);
                        BOMLine.setProductId(BOMProduct_ID);
                        BOMLine.setLocatorId(defaultLocator);
                        BOMLine.setQtyUsed(BOMMovementQty);
                        BOMLine.setPlannedQty(BOMMovementQty);
                        BOMLine.saveEx();

                        lineno = lineno + 10;
                        count++;
                    } else if (BOMMovementQty.signum() == 0) {
                        MProductionLine BOMLine;
                        BOMLine = new MProductionLine(this);
                        BOMLine.setLine(lineno);
                        BOMLine.setProductId(BOMProduct_ID);
                        BOMLine.setLocatorId(defaultLocator);
                        BOMLine.setQtyUsed(BOMMovementQty);
                        BOMLine.setPlannedQty(BOMMovementQty);
                        BOMLine.saveEx();

                        lineno = lineno + 10;
                        count++;
                    } else {

                        // BOM stock info
                        MStorageOnHand[] storages;
                        I_M_Product usedProduct = MProduct.get(BOMProduct_ID);
                        defaultLocator = usedProduct.getLocatorId();
                        if (defaultLocator == 0) defaultLocator = getLocatorId();
                        if (usedProduct == null || usedProduct.getId() == 0) return 0;

                        ClientWithAccounting client = MClientKt.getClientWithAccounting();
                        MProductCategory pc =
                                MProductCategory.get(usedProduct.getProductCategoryId());
                        String MMPolicy = pc.getMMPolicy();
                        if (MMPolicy == null || MMPolicy.length() == 0) {
                            MMPolicy = client.getMMPolicy();
                        }

                        storages =
                                MStorageOnHand.getWarehouse(
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
                        for (MStorageOnHand storage : storages) {

                            BigDecimal lineQty = storage.getQtyOnHand();
                            if (lineQty.signum() != 0) {
                                if (lineQty.compareTo(BOMMovementQty) > 0) lineQty = BOMMovementQty;

                                int loc = storage.getLocatorId();
                                int slASI = storage.getAttributeSetInstanceId();
                                int locAttribSet =
                                        new MAttributeSetInstance(asi).getAttributeSetId();

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
                                    BOMLine.setProductId(BOMProduct_ID);
                                    BOMLine.setLocatorId(loc);
                                    BOMLine.setQtyUsed(lineQty);
                                    BOMLine.setPlannedQty(lineQty);
                                    if (slASI != 0 && locAttribSet != 0) // ie non costing attribute
                                        BOMLine.setAttributeSetInstanceId(slASI);
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
                                    BOMLine.setProductId(BOMProduct_ID);
                                    BOMLine.setLocatorId(defaultLocator);
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
        }

        return count;
    }

    @Override
    protected boolean beforeDelete() {
        deleteLines();
        return true;
    }
}
