package org.compiere.production;

import kotliquery.Row;
import org.compiere.accounting.Doc;
import org.compiere.accounting.MAcctSchema;
import org.compiere.accounting.MClientKt;
import org.compiere.accounting.MPeriod;
import org.compiere.accounting.MProduct;
import org.compiere.accounting.MStorageOnHand;
import org.compiere.accounting.MWarehouse;
import org.compiere.docengine.DocumentEngine;
import org.compiere.model.ClientWithAccounting;
import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Production;
import org.compiere.model.I_M_ProductionPlan;
import org.compiere.orm.MDocType;
import org.compiere.orm.MSysConfig;
import org.compiere.orm.PO;
import org.compiere.orm.PeriodClosedException;
import org.compiere.orm.Query;
import org.compiere.process.CompleteActionResult;
import org.compiere.process.DocAction;
import org.compiere.product.MAttributeSetInstance;
import org.compiere.product.MProductCategory;
import org.compiere.validation.ModelValidationEngine;
import org.compiere.validation.ModelValidator;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.AdempiereUserError;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.getSQLValue;
import static software.hsharp.core.util.DBKt.getSQLValueBD;
import static software.hsharp.core.util.DBKt.getSQLValueString;
import static software.hsharp.core.util.DBKt.prepareStatement;

public class MProduction extends X_M_Production implements I_M_Production, DocAction, IPODoc {
    /**
     *
     */
    private static final long serialVersionUID = 8047044372956625013L;

    /**
     * Log
     */
    @SuppressWarnings("unused")
    private static CLogger m_log = CLogger.getCLogger(MProduction.class);

    private int lineno;
    private int count;
    /**
     * Process Message
     */
    private String m_processMsg = null;
    /**
     * Just Prepared Flag
     */
    private boolean m_justPrepared = false;

    public MProduction(int M_Production_ID) {
        super(M_Production_ID);
        if (M_Production_ID == 0) {
            setDocStatus(X_M_Production.DOCSTATUS_Drafted);
            setDocAction(X_M_Production.DOCACTION_Prepare);
        }
    }

    public MProduction(Row row) {
        super(row);
    }

    public MProduction(I_C_OrderLine line) {
        super(0);
        setADClientID(line.getClientId());
        setOrgId(line.getOrgId());
        setMovementDate(line.getDatePromised());
    }

    public MProduction(MProjectLine line) {
        super(0);
        MProject project = new MProject(line.getProjectId());
        MWarehouse wh = new MWarehouse(project.getWarehouseId());

        int M_Locator_ID;

        M_Locator_ID = wh.getDefaultLocator().getLocatorId();
        setADClientID(line.getClientId());
        setOrgId(line.getOrgId());
        setProductId(line.getProductId());
        setProductionQty(line.getPlannedQty());
        setLocatorId(M_Locator_ID);
        setDescription(
                project.getSearchKey() + "_" + project.getName() + " Line: " + line.getLine() + " (project)");
        setProjectId(line.getProjectId());
        setBusinessPartnerId(project.getBusinessPartnerId());
        setCampaignId(project.getCampaignId());
        setTransactionOrganizationId(project.getTransactionOrganizationId());
        setBusinessActivityId(project.getBusinessActivityId());
        setProjectPhaseId(line.getProjectPhaseId());
        setProjectTaskId(line.getProjectTaskId());
        setMovementDate(Env.getContextAsDate());
    }

    @NotNull
    @Override
    public CompleteActionResult completeIt() {
        // Re-Check
        if (!m_justPrepared) {
            String status = prepareIt();
            m_justPrepared = false;
            if (!DocAction.Companion.getSTATUS_InProgress().equals(status))
                return new CompleteActionResult(status);
        }

        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
        if (m_processMsg != null)
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());

        StringBuilder errors = new StringBuilder();
        int processed = 0;

        if (!isUseProductionPlan()) {
            MProductionLine[] lines = getLines();
            // IDEMPIERE-3107 Check if End Product in Production Lines exist
            if (!isHaveEndProduct(lines)) {
                m_processMsg = "Production does not contain End Product";
                return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
            }
            errors.append(processLines(lines));
            if (errors.length() > 0) {
                m_processMsg = errors.toString();
                return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
            }
        } else {
            Query planQuery =
                    new Query(
                            I_M_ProductionPlan.Table_Name,
                            "M_ProductionPlan.M_Production_ID=?"
                    );
            List<MProductionPlan> plans = planQuery.setParameters(getProductionId()).list();
            for (MProductionPlan plan : plans) {
                MProductionLine[] lines = plan.getLines();

                // IDEMPIERE-3107 Check if End Product in Production Lines exist
                if (!isHaveEndProduct(lines)) {
                    m_processMsg =
                            String.format(
                                    "Production plan (line %1$d id %2$d) does not contain End Product",
                                    plan.getLine(), plan.getId());
                    return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
                }

                if (lines.length > 0) {
                    errors.append(processLines(lines));
                    if (errors.length() > 0) {
                        m_processMsg = errors.toString();
                        return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
                    }
                    processed = processed + lines.length;
                }
                plan.setProcessed(true);
                plan.saveEx();
            }
        }

        //		User Validation
        String valid =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
        if (valid != null) {
            m_processMsg = valid;
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
        }

        setProcessed(true);
        setDocAction(X_M_Production.DOCACTION_Close);
        return new CompleteActionResult(DocAction.Companion.getSTATUS_Completed());
    }

    private boolean isHaveEndProduct(MProductionLine[] lines) {

        for (MProductionLine line : lines) {
            if (line.isEndProduct()) return true;
        }
        return false;
    }

    private Object processLines(MProductionLine[] lines) {
        StringBuilder errors = new StringBuilder();
        for (MProductionLine line : lines) {
            String error = line.createTransactions(getMovementDate(), false);
            if (!Util.isEmpty(error)) {
                errors.append(error);
            } else {
                line.setProcessed(true);
                line.saveEx();
            }
        }

        return errors.toString();
    }

    public MProductionLine[] getLines() {
        ArrayList<MProductionLine> list = new ArrayList<>();

        String sql =
                "SELECT pl.M_ProductionLine_ID "
                        + "FROM M_ProductionLine pl "
                        + "WHERE pl.M_Production_ID = ?";

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

        lineno = 100;

        count = 0;

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

        createLines(mustBeStocked, finishedProduct, getProductionQty());

        return count;
    }

    private int createLines(boolean mustBeStocked, MProduct finishedProduct, BigDecimal requiredQty) {

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
                    createLines(mustBeStocked, bomproduct, BOMMovementQty);
                } else {

                    defaultLocator = bomproduct.getLocatorId();
                    if (defaultLocator == 0) defaultLocator = getLocatorId();

                    if (!bomproduct.isStocked()) {
                        MProductionLine BOMLine = null;
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
                        MProductionLine BOMLine = null;
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
                        MStorageOnHand[] storages = null;
                        MProduct usedProduct = MProduct.get(BOMProduct_ID);
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

    @Override
    public boolean processIt(@NotNull String processAction) {
        m_processMsg = null;
        DocumentEngine engine = new DocumentEngine(this, getDocStatus());
        return engine.processIt(processAction, getDocAction());
    }

    @Override
    public boolean unlockIt() {
        if (log.isLoggable(Level.INFO)) log.info("unlockIt - " + toString());
        setProcessing(false);
        return true;
    }

    @Override
    public boolean invalidateIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        setDocAction(X_M_Production.DOCACTION_Prepare);
        return true;
    }

    @NotNull
    @Override
    public String prepareIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
        if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();

        //	Std Period open?
        MPeriod.testPeriodOpen(
                getMovementDate(), MDocType.DOCBASETYPE_MaterialProduction, getOrgId());

        if (getIsCreated().equals("N")) {
            m_processMsg = "Not created";
            return DocAction.Companion.getSTATUS_Invalid();
        }

        if (!isUseProductionPlan()) {
            m_processMsg = validateEndProduct(getProductId());
            if (!Util.isEmpty(m_processMsg)) {
                return DocAction.Companion.getSTATUS_Invalid();
            }
        } else {
            Query planQuery =
                    new Query(
                            I_M_ProductionPlan.Table_Name,
                            "M_ProductionPlan.M_Production_ID=?"
                    );
            List<MProductionPlan> plans = planQuery.setParameters(getProductionId()).list();
            for (MProductionPlan plan : plans) {
                m_processMsg = validateEndProduct(plan.getProductId());
                if (!Util.isEmpty(m_processMsg)) {
                    return DocAction.Companion.getSTATUS_Invalid();
                }
            }
        }

        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
        if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();

        m_justPrepared = true;
        if (!X_M_Production.DOCACTION_Complete.equals(getDocAction()))
            setDocAction(X_M_Production.DOCACTION_Complete);
        return DocAction.Companion.getSTATUS_InProgress();
    }

    protected String validateEndProduct(int M_Product_ID) {
        String msg = isBom(M_Product_ID);
        if (!Util.isEmpty(msg)) return msg;

        if (!costsOK(M_Product_ID)) {
            msg = "Excessive difference in standard costs";
            if (MSysConfig.getBooleanValue(
                    MSysConfig.MFG_ValidateCostsDifferenceOnCreate, false, getClientId())) {
                return msg;
            } else {
                log.warning(msg);
            }
        }

        return null;
    }

    protected String isBom(int M_Product_ID) {
        String bom =
                getSQLValueString(
                        "SELECT isbom FROM M_Product WHERE M_Product_ID = ?", M_Product_ID);
        if ("N".compareTo(bom) == 0) {
            return "Attempt to create product line for Non Bill Of Materials";
        }
        int materials =
                getSQLValue(
                        "SELECT count(M_Product_BOM_ID) FROM M_Product_BOM WHERE M_Product_ID = ?",
                        M_Product_ID);
        if (materials == 0) {
            return "Attempt to create product line for Bill Of Materials with no BOM Products";
        }
        return null;
    }

    protected boolean costsOK(int M_Product_ID) throws AdempiereUserError {
        MProduct product = MProduct.get(M_Product_ID);
        String costingMethod = product.getCostingMethod(MClientKt.getClientWithAccounting().getAcctSchema());
        // will not work if non-standard costing is used
        if (MAcctSchema.COSTINGMETHOD_StandardCosting.equals(costingMethod)) {
            String sql =
                    "SELECT ABS(((cc.currentcostprice-(SELECT SUM(c.currentcostprice*bom.bomqty)"
                            + " FROM m_cost c"
                            + " INNER JOIN m_product_bom bom ON (c.m_product_id=bom.m_productbom_id)"
                            + " INNER JOIN m_costelement ce ON (c.m_costelement_id = ce.m_costelement_id AND ce.costingmethod = 'S')"
                            + " WHERE bom.m_product_id = pp.m_product_id)"
                            + " )/cc.currentcostprice))"
                            + " FROM m_product pp"
                            + " INNER JOIN m_cost cc on (cc.m_product_id=pp.m_product_id)"
                            + " INNER JOIN m_costelement ce ON (cc.m_costelement_id=ce.m_costelement_id)"
                            + " WHERE cc.currentcostprice > 0 AND pp.M_Product_ID = ?"
                            + " AND ce.costingmethod='S'";

            BigDecimal costPercentageDiff = getSQLValueBD(sql, M_Product_ID);

            if (costPercentageDiff == null) {
                costPercentageDiff = Env.ZERO;
                String msg = "Could not retrieve costs";
                if (MSysConfig.getBooleanValue(
                        MSysConfig.MFG_ValidateCostsOnCreate, false, getClientId())) {
                    throw new AdempiereUserError(msg);
                } else {
                    log.warning(msg);
                }
            }

            return (costPercentageDiff.compareTo(new BigDecimal("0.005"))) < 0;

        }
        return true;
    }

    @Override
    public boolean approveIt() {
        return true;
    }

    @Override
    public boolean rejectIt() {
        return true;
    }

    @Override
    public boolean voidIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        // Before Void
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
        if (m_processMsg != null) return false;

        if (X_M_Production.DOCSTATUS_Closed.equals(getDocStatus())
                || X_M_Production.DOCSTATUS_Reversed.equals(getDocStatus())
                || X_M_Production.DOCSTATUS_Voided.equals(getDocStatus())) {
            m_processMsg = "Document Closed: " + getDocStatus();
            setDocAction(X_M_Production.DOCACTION_None);
            return false;
        }

        // Not Processed
        if (X_M_Production.DOCSTATUS_Drafted.equals(getDocStatus())
                || X_M_Production.DOCSTATUS_Invalid.equals(getDocStatus())
                || X_M_Production.DOCSTATUS_InProgress.equals(getDocStatus())
                || X_M_Production.DOCSTATUS_Approved.equals(getDocStatus())
                || X_M_Production.DOCSTATUS_NotApproved.equals(getDocStatus())) {
            setIsCreated("N");
            if (!isUseProductionPlan()) {
                deleteLines();
                setProductionQty(BigDecimal.ZERO);
            } else {
                Query planQuery =
                        new Query(
                                I_M_ProductionPlan.Table_Name,
                                "M_ProductionPlan.M_Production_ID=?"
                        );
                List<MProductionPlan> plans = planQuery.setParameters(getProductionId()).list();
                for (MProductionPlan plan : plans) {
                    plan.deleteLines();
                    plan.setProductionQty(BigDecimal.ZERO);
                    plan.setProcessed(true);
                    plan.saveEx();
                }
            }

        } else {
            boolean accrual = false;
            try {
                MPeriod.testPeriodOpen(
                        getMovementDate(), Doc.DOCTYPE_MatProduction, getOrgId());
            } catch (PeriodClosedException e) {
                accrual = true;
            }

            if (accrual) return reverseAccrualIt();
            else return reverseCorrectIt();
        }

        // After Void
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
        if (m_processMsg != null) return false;

        setProcessed(true);
        setDocAction(X_M_Production.DOCACTION_None);
        return true;
    }

    @Override
    public boolean closeIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        // Before Close
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
        if (m_processMsg != null) return false;

        setProcessed(true);
        setDocAction(X_M_Production.DOCACTION_None);

        // After Close
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
        return m_processMsg == null;
    }

    @Override
    public boolean reverseCorrectIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        // Before reverseCorrect
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
        if (m_processMsg != null) return false;

        MProduction reversal = reverse(false);
        if (reversal == null) return false;

        // After reverseCorrect
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
        if (m_processMsg != null) return false;

        m_processMsg = reversal.getDocumentNo();

        return true;
    }

    private MProduction reverse(boolean accrual) {
        Timestamp reversalDate = accrual ? Env.getContextAsDate() : getMovementDate();
        if (reversalDate == null) {
            reversalDate = new Timestamp(System.currentTimeMillis());
        }

        MPeriod.testPeriodOpen(reversalDate, Doc.DOCTYPE_MatProduction, getOrgId());
        MProduction reversal;
        reversal = copyFrom(reversalDate);

        StringBuilder msgadd = new StringBuilder("{->").append(getDocumentNo()).append(")");
        reversal.addDescription(msgadd.toString());
        reversal.setReversalId(getProductionId());
        reversal.saveEx();

        // Reverse Line Qty
        MProductionLine[] sLines = getLines();
        MProductionLine[] tLines = reversal.getLines();
        for (int i = 0; i < sLines.length; i++) {
            //	We need to copy MA
            if (sLines[i].getAttributeSetInstanceId() == 0) {
                MProductionLineMA[] mas = MProductionLineMA.get(sLines[i].getId());
                for (MProductionLineMA mProductionLineMA : mas) {
                    MProductionLineMA ma =
                            new MProductionLineMA(
                                    tLines[i],
                                    mProductionLineMA.getAttributeSetInstanceId(),
                                    mProductionLineMA.getMovementQty().negate(),
                                    mProductionLineMA.getDateMaterialPolicy());
                    ma.saveEx();
                }
            }
        }

        if (!reversal.processIt(DocAction.Companion.getACTION_Complete())) {
            m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
            return null;
        }

        reversal.closeIt();
        reversal.setProcessing(false);
        reversal.setDocStatus(X_M_Production.DOCSTATUS_Reversed);
        reversal.setDocAction(X_M_Production.DOCACTION_None);
        reversal.saveEx();

        msgadd = new StringBuilder("(").append(reversal.getDocumentNo()).append("<-)");
        addDescription(msgadd.toString());

        setProcessed(true);
        setReversalId(reversal.getProductionId());
        setDocStatus(X_M_Production.DOCSTATUS_Reversed); // 	may come from void
        setDocAction(X_M_Production.DOCACTION_None);

        return reversal;
    }

    private MProduction copyFrom(Timestamp reversalDate) {
        MProduction to = new MProduction(0);
        PO.copyValues(this, to, getClientId(), getOrgId());

        to.setValueNoCheck("DocumentNo", null);
        //
        to.setDocStatus(X_M_Production.DOCSTATUS_Drafted); // 	Draft
        to.setDocAction(X_M_Production.DOCACTION_Complete);
        to.setMovementDate(reversalDate);
        to.setIsComplete(false);
        to.setIsCreated("Y");
        to.setProcessing(false);
        to.setProcessed(false);
        to.setIsUseProductionPlan(isUseProductionPlan());
        if (isUseProductionPlan()) {
            to.saveEx();
            Query planQuery =
                    new Query(
                            I_M_ProductionPlan.Table_Name,
                            "M_ProductionPlan.M_Production_ID=?"
                    );
            List<MProductionPlan> fplans = planQuery.setParameters(getProductionId()).list();
            for (MProductionPlan fplan : fplans) {
                MProductionPlan tplan = new MProductionPlan(0);
                PO.copyValues(fplan, tplan, getClientId(), getOrgId());
                tplan.setProductionId(to.getProductionId());
                tplan.setProductionQty(fplan.getProductionQty().negate());
                tplan.setProcessed(false);
                tplan.saveEx();

                MProductionLine[] flines = fplan.getLines();
                for (MProductionLine fline : flines) {
                    MProductionLine tline = new MProductionLine(tplan);
                    PO.copyValues(fline, tline, getClientId(), getOrgId());
                    tline.setProductionPlanId(tplan.getProductionPlanId());
                    tline.setMovementQty(fline.getMovementQty().negate());
                    tline.setPlannedQty(fline.getPlannedQty().negate());
                    tline.setQtyUsed(fline.getQtyUsed().negate());
                    tline.saveEx();
                }
            }
        } else {
            to.setProductionQty(getProductionQty().negate());
            to.saveEx();
            MProductionLine[] flines = getLines();
            for (MProductionLine fline : flines) {
                MProductionLine tline = new MProductionLine(to);
                PO.copyValues(fline, tline, getClientId(), getOrgId());
                tline.setProductionId(to.getProductionId());
                tline.setMovementQty(fline.getMovementQty().negate());
                tline.setPlannedQty(fline.getPlannedQty().negate());
                tline.setQtyUsed(fline.getQtyUsed().negate());
                tline.saveEx();
            }
        }

        return to;
    }

    /**
     * Add to Description
     *
     * @param description text
     */
    public void addDescription(String description) {
        String desc = getDescription();
        if (desc == null) setDescription(description);
        else {
            setDescription(desc + " | " + description);
        }
    } //	addDescription

    @Override
    public boolean reverseAccrualIt() {
        if (log.isLoggable(Level.INFO)) log.info(toString());
        // Before reverseAccrual
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
        if (m_processMsg != null) return false;

        MProduction reversal = reverse(true);
        if (reversal == null) return false;

        // After reverseAccrual
        m_processMsg =
                ModelValidationEngine.get()
                        .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
        if (m_processMsg != null) return false;

        m_processMsg = reversal.getDocumentNo();

        return true;
    }

    @Override
    public boolean reActivateIt() {
        if (log.isLoggable(Level.INFO)) log.info("reActivateIt - " + toString());
        // Before reActivate
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
        if (m_processMsg != null) return false;

        // After reActivate
        m_processMsg =
                ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
        if (m_processMsg != null) return false;
        return false;
    }

    @NotNull
    @Override
    public String getSummary() {
        return getDocumentNo();
    }

    @NotNull
    @Override
    public String getDocumentInfo() {
        return getDocumentNo();
    }

    @NotNull
    @Override
    public String getProcessMsg() {
        return m_processMsg;
    }

    @Override
    public int getDocumentUserId() {
        return getCreatedBy();
    }

    @Override
    public int getCurrencyId() {
        return MClientKt.getClientWithAccounting().getCurrencyId();
    }

    @NotNull
    @Override
    public BigDecimal getApprovalAmt() {
        return BigDecimal.ZERO;
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {
        if (getProductId() > 0) {
            if (isUseProductionPlan()) {
                setIsUseProductionPlan(false);
            }
        } else {
            if (!isUseProductionPlan()) {
                setIsUseProductionPlan(true);
            }
        }
        return true;
    }

    @Override
    public void setDoc(IDoc doc) {
    }

    @Override
    public void setProcessedOn(String processed, boolean b, boolean b1) {
    }

}
