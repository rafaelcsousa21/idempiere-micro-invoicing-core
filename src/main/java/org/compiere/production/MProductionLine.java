package org.compiere.production;

import kotliquery.Row;
import org.compiere.accounting.MAcctSchema;
import org.compiere.accounting.MClientInfo;
import org.compiere.accounting.MCost;
import org.compiere.accounting.MProduct;
import org.compiere.accounting.MStorageOnHand;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_ProductionPlan;
import org.compiere.orm.Query;
import org.compiere.product.MAttributeSetInstance;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdateEx;
import static software.hsharp.core.util.DBKt.forUpdate;
import static software.hsharp.core.util.DBKt.getSQLValueEx;

public class MProductionLine extends X_M_ProductionLine {
    /**
     *
     */
    private static final long serialVersionUID = 5939914729719167512L;

    private MProduction productionParent;

    /**
     * Standard Constructor
     *
     * @param ctx                 ctx
     * @param M_ProductionLine_ID id
     */
    public MProductionLine(Properties ctx, int M_ProductionLine_ID) {
        super(ctx, M_ProductionLine_ID);
        if (M_ProductionLine_ID == 0) {
            setLine(0); // @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM M_ProductionLine WHERE
            setAttributeSetInstanceId(0);
            setProductionLineId(0);
            setProductionId(0);
            setMovementQty(Env.ZERO);
            setProcessed(false);
        }
    } // MProductionLine

    public MProductionLine(Properties ctx, Row row) {
        super(ctx, row);
    } //	MProductionLine

    /**
     * Parent Constructor
     */
    public MProductionLine(MProduction header) {
        super(header.getCtx(), 0);
        setProductionId(header.getId());
        setADClientID(header.getClientId());
        setOrgId(header.getOrgId());
        productionParent = header;
    }

    public MProductionLine(MProductionPlan header) {
        super(header.getCtx(), 0);
        setProductionPlanId(header.getId());
        setADClientID(header.getClientId());
        setOrgId(header.getOrgId());
    }

    /**
     * @param date
     * @return "" for success, error string if failed
     */
    public String createTransactions(Timestamp date, boolean mustBeStocked) {
        int reversalId = getProductionReversalId();
        if (reversalId <= 0) {
            // delete existing ASI records
            int deleted = deleteMA();
            if (log.isLoggable(Level.FINE))
                log.log(Level.FINE, "Deleted " + deleted + " attribute records ");
        }
        MProduct prod = new MProduct(getCtx(), getProductId());
        if (log.isLoggable(Level.FINE)) log.log(Level.FINE, "Loaded Product " + prod.toString());

        if (!prod.isStocked() || prod.getProductType().compareTo(MProduct.PRODUCTTYPE_Item) != 0) {
            // no need to do any movements
            if (log.isLoggable(Level.FINE))
                log.log(Level.FINE, "Production Line " + getLine() + " does not require stock movement");
            return "";
        }
        StringBuilder errorString = new StringBuilder();

        MAttributeSetInstance asi =
                new MAttributeSetInstance(getCtx(), getAttributeSetInstanceId());
        I_M_AttributeSet attributeset = prod.getMAttributeSet();
        boolean isAutoGenerateLot = false;
        if (attributeset != null) isAutoGenerateLot = attributeset.isAutoGenerateLot();
        String asiString = asi.getDescription();
        if (asiString == null) asiString = "";

        if (log.isLoggable(Level.FINEST)) log.log(Level.FINEST, "asi Description is: " + asiString);
        // create transactions for finished goods
        if (getProductId() == getEndProductId()) {
            if (reversalId <= 0 && isAutoGenerateLot && getAttributeSetInstanceId() == 0) {
                asi = MAttributeSetInstance.generateLot(getCtx(), (MProduct) getProduct());
                setAttributeSetInstanceId(asi.getAttributeSetInstanceId());
            }
            Timestamp dateMPolicy = date;
            if (getAttributeSetInstanceId() > 0) {
                Timestamp t =
                        MStorageOnHand.getDateMaterialPolicy(
                                getProductId(), getAttributeSetInstanceId(), getLocatorId());
                if (t != null) dateMPolicy = t;
            }

            dateMPolicy = Util.removeTime(dateMPolicy);
            // for reversal, keep the ma copy from original trx
            if (reversalId <= 0) {
                MProductionLineMA lineMA =
                        new MProductionLineMA(this, asi.getId(), getMovementQty(), dateMPolicy);
                if (!lineMA.save()) {
                    log.log(Level.SEVERE, "Could not save MA for " + toString());
                    errorString.append("Could not save MA for " + toString() + "\n");
                }
            }
            MTransaction matTrx =
                    new MTransaction(
                            getCtx(),
                            getOrgId(),
                            "P+",
                            getLocatorId(),
                            getProductId(),
                            asi.getId(),
                            getMovementQty(),
                            date
                    );
            matTrx.setProductionLineId(getId());
            if (!matTrx.save()) {
                log.log(Level.SEVERE, "Could not save transaction for " + toString());
                errorString.append("Could not save transaction for " + toString() + "\n");
            }
            MStorageOnHand storage =
                    MStorageOnHand.getCreate(
                            getCtx(),
                            getLocatorId(),
                            getProductId(),
                            asi.getId(),
                            dateMPolicy,
                            null);
            storage.addQtyOnHand(getMovementQty());
            if (log.isLoggable(Level.FINE))
                log.log(Level.FINE, "Created finished goods line " + getLine());

            return errorString.toString();
        }

        // create transactions and update stock used in production
        MStorageOnHand[] storages =
                MStorageOnHand.getAll(
                        getCtx(), getProductId(), getLocatorId(), null, false, 0);

        MProductionLineMA lineMA = null;
        MTransaction matTrx = null;
        BigDecimal qtyToMove = getMovementQty().negate();

        if (qtyToMove.signum() > 0) {
            for (int sl = 0; sl < storages.length; sl++) {

                BigDecimal lineQty = storages[sl].getQtyOnHand();

                if (log.isLoggable(Level.FINE)) log.log(Level.FINE, "QtyAvailable " + lineQty);
                if (lineQty.signum() > 0) {
                    if (lineQty.compareTo(qtyToMove) > 0) lineQty = qtyToMove;

                    MAttributeSetInstance slASI =
                            new MAttributeSetInstance(
                                    getCtx(), storages[sl].getAttributeSetInstanceId());
                    String slASIString = slASI.getDescription();
                    if (slASIString == null) slASIString = "";

                    if (log.isLoggable(Level.FINEST))
                        log.log(Level.FINEST, "slASI-Description =" + slASIString);

                    if (slASIString.compareTo(asiString) == 0 || asi.getAttributeSetId() == 0)
                    // storage matches specified ASI or is a costing asi (inc. 0)
                    // This process will move negative stock on hand quantities
                    {
                        lineMA =
                                MProductionLineMA.get(
                                        this,
                                        storages[sl].getAttributeSetInstanceId(),
                                        storages[sl].getDateMaterialPolicy());
                        lineMA.setMovementQty(lineMA.getMovementQty().add(lineQty.negate()));
                        if (!lineMA.save()) {
                            log.log(Level.SEVERE, "Could not save MA for " + toString());
                            errorString.append("Could not save MA for " + toString() + "\n");
                        } else {
                            if (log.isLoggable(Level.FINE)) log.log(Level.FINE, "Saved MA for " + toString());
                        }
                        matTrx =
                                new MTransaction(
                                        getCtx(),
                                        getOrgId(),
                                        "P-",
                                        getLocatorId(),
                                        getProductId(),
                                        lineMA.getAttributeSetInstanceId(),
                                        lineQty.negate(),
                                        date
                                );
                        matTrx.setProductionLineId(getId());
                        if (!matTrx.save()) {
                            log.log(Level.SEVERE, "Could not save transaction for " + toString());
                            errorString.append("Could not save transaction for " + toString() + "\n");
                        } else {
                            if (log.isLoggable(Level.FINE))
                                log.log(Level.FINE, "Saved transaction for " + toString());
                        }
                        forUpdate(storages[sl]);
                        storages[sl].addQtyOnHand(lineQty.negate());
                        qtyToMove = qtyToMove.subtract(lineQty);
                        if (log.isLoggable(Level.FINE))
                            log.log(
                                    Level.FINE, getLine() + " Qty moved = " + lineQty + ", Remaining = " + qtyToMove);
                    }
                }

                if (qtyToMove.signum() == 0) break;
            } // for available storages
        } else if (qtyToMove.signum() < 0) {

            MClientInfo m_clientInfo = MClientInfo.get(getCtx(), getClientId());
            MAcctSchema acctSchema =
                    new MAcctSchema(getCtx(), m_clientInfo.getAcctSchema1Id());
            if (asi.getId() == 0
                    && MAcctSchema.COSTINGLEVEL_BatchLot.equals(prod.getCostingLevel(acctSchema))) {
                // add quantity to last attributesetinstance
                String sqlWhere = "M_Product_ID=? AND M_Locator_ID=? AND M_AttributeSetInstance_ID > 0 ";
                MStorageOnHand storage =
                        new Query(getCtx(), MStorageOnHand.Table_Name, sqlWhere)
                                .setParameters(getProductId(), getLocatorId())
                                .setOrderBy(
                                        MStorageOnHand.COLUMNNAME_DateMaterialPolicy
                                                + " DESC,"
                                                + MStorageOnHand.COLUMNNAME_M_AttributeSetInstance_ID
                                                + " DESC")
                                .first();

                if (storage != null) {
                    setAttributeSetInstanceId(storage.getAttributeSetInstanceId());
                    asi =
                            new MAttributeSetInstance(
                                    getCtx(), storage.getAttributeSetInstanceId());
                    asiString = asi.getDescription();
                } else {
                    String costingMethod = prod.getCostingMethod(acctSchema);
                    StringBuilder localWhereClause =
                            new StringBuilder("M_Product_ID =?")
                                    .append(" AND C_AcctSchema_ID=?")
                                    .append(" AND ce.CostingMethod = ? ")
                                    .append(" AND CurrentCostPrice <> 0 ");
                    MCost cost =
                            new Query(getCtx(), I_M_Cost.Table_Name, localWhereClause.toString())
                                    .setParameters(getProductId(), acctSchema.getId(), costingMethod)
                                    .addJoinClause(
                                            " INNER JOIN M_CostElement ce ON (M_Cost.M_CostElement_ID =ce.M_CostElement_ID ) ")
                                    .setOrderBy("Updated DESC")
                                    .first();
                    if (cost != null) {
                        setAttributeSetInstanceId(cost.getAttributeSetInstanceId());
                        asi =
                                new MAttributeSetInstance(
                                        getCtx(), cost.getAttributeSetInstanceId());
                        asiString = asi.getDescription();

                    } else {
                        log.log(Level.SEVERE, "Cannot retrieve cost of Product r " + prod.toString());
                        errorString.append("Cannot retrieve cost of Product " + prod.toString());
                    }
                }
            }
        }

        if (!(qtyToMove.signum() == 0)) {
            if (mustBeStocked && qtyToMove.signum() > 0) {
                MLocator loc = new MLocator(getCtx(), getLocatorId());
                errorString.append(
                        "Insufficient qty on hand of " + prod.toString() + " at " + loc.toString() + "\n");
            } else {
                MStorageOnHand storage =
                        MStorageOnHand.getCreate(
                                Env.getCtx(),
                                getLocatorId(),
                                getProductId(),
                                asi.getId(),
                                date,
                                null,
                                true);

                BigDecimal lineQty = qtyToMove;
                MAttributeSetInstance slASI =
                        new MAttributeSetInstance(
                                getCtx(), storage.getAttributeSetInstanceId());
                String slASIString = slASI.getDescription();
                if (slASIString == null) slASIString = "";

                if (log.isLoggable(Level.FINEST))
                    log.log(Level.FINEST, "slASI-Description =" + slASIString);

                if (slASIString.compareTo(asiString) == 0 || asi.getAttributeSetId() == 0)
                // storage matches specified ASI or is a costing asi (inc. 0)
                // This process will move negative stock on hand quantities
                {
                    lineMA =
                            MProductionLineMA.get(
                                    this, storage.getAttributeSetInstanceId(), storage.getDateMaterialPolicy());
                    lineMA.setMovementQty(lineMA.getMovementQty().add(lineQty.negate()));

                    if (!lineMA.save()) {
                        log.log(Level.SEVERE, "Could not save MA for " + toString());
                        errorString.append("Could not save MA for " + toString() + "\n");
                    } else {
                        if (log.isLoggable(Level.FINE)) log.log(Level.FINE, "Saved MA for " + toString());
                    }
                    matTrx =
                            new MTransaction(
                                    getCtx(),
                                    getOrgId(),
                                    "P-",
                                    getLocatorId(),
                                    getProductId(),
                                    asi.getId(),
                                    lineQty.negate(),
                                    date
                            );
                    matTrx.setProductionLineId(getId());
                    if (!matTrx.save()) {
                        log.log(Level.SEVERE, "Could not save transaction for " + toString());
                        errorString.append("Could not save transaction for " + toString() + "\n");
                    } else {
                        if (log.isLoggable(Level.FINE))
                            log.log(Level.FINE, "Saved transaction for " + toString());
                    }
                    storage.addQtyOnHand(lineQty.negate());
                    qtyToMove = qtyToMove.subtract(lineQty);
                    if (log.isLoggable(Level.FINE))
                        log.log(
                                Level.FINE, getLine() + " Qty moved = " + lineQty + ", Remaining = " + qtyToMove);
                } else {
                    errorString.append(
                            "Storage doesn't match ASI "
                                    + prod.toString()
                                    + " / "
                                    + slASIString
                                    + " vs. "
                                    + asiString
                                    + "\n");
                }
            }
        }

        return errorString.toString();
    }

    private int getEndProductId() {
        if (productionParent != null) {
            return productionParent.getProductId();
        } else if (getProductionId() > 0) {
            return getProduction().getProductId();
        } else {
            return getProductionPlan().getProductId();
        }
    }

    private int deleteMA() {
        String sql = "DELETE FROM M_ProductionLineMA WHERE M_ProductionLine_ID = " + getId();
        int count = executeUpdateEx(sql);
        return count;
    }

    public String toString() {
        if (getProductId() == 0) return ("No product defined for production line " + getLine());
        MProduct product = new MProduct(getCtx(), getProductId());
        return ("Production line:"
                + getLine()
                + " -- "
                + getMovementQty()
                + " of "
                + product.getSearchKey());
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {
        if (productionParent == null && getProductionId() > 0)
            productionParent = new MProduction(getCtx(), getProductionId());

        if (getProductionId() > 0) {
            if (productionParent.getProductId() == getProductId()
                    && productionParent.getProductionQty().signum() == getMovementQty().signum())
                setIsEndProduct(true);
            else setIsEndProduct(false);
        } else {
            I_M_ProductionPlan plan = getProductionPlan();
            if (plan.getProductId() == getProductId()
                    && plan.getProductionQty().signum() == getMovementQty().signum()) setIsEndProduct(true);
            else setIsEndProduct(false);
        }

        if (isEndProduct() && getAttributeSetInstanceId() != 0) {
            String where =
                    "M_QualityTest_ID IN (SELECT M_QualityTest_ID "
                            + "FROM M_Product_QualityTest WHERE M_Product_ID=?) "
                            + "AND M_QualityTest_ID NOT IN (SELECT M_QualityTest_ID "
                            + "FROM M_QualityTestResult WHERE M_AttributeSetInstance_ID=?)";

            List<MQualityTest> tests =
                    new Query(getCtx(), MQualityTest.Table_Name, where)
                            .setOnlyActiveRecords(true)
                            .setParameters(getProductId(), getAttributeSetInstanceId())
                            .list();
            // create quality control results
            for (MQualityTest test : tests) {
                test.createResult(getAttributeSetInstanceId());
            }
        }

        if (!isEndProduct()) {
            setMovementQty(getQtyUsed().negate());
        }

        return true;
    }

    @Override
    protected boolean beforeDelete() {

        deleteMA();
        return true;
    }

    /**
     * Get Reversal_ID of parent production
     *
     * @return Reversal_ID
     */
    public int getProductionReversalId() {
        if (getProductionId() > 0)
            return getSQLValueEx(
                    "SELECT Reversal_ID FROM M_Production WHERE M_Production_ID=?",
                    getProductionId());
        else
            return getSQLValueEx(
                    "SELECT p.Reversal_ID FROM M_ProductionPlan pp INNER JOIN M_Production p ON (pp.M_Production_ID = p.M_Production_ID) WHERE pp.M_ProductionPlan_ID=?",
                    getProductionPlanId());
    }

}
