package org.compiere.production;

import kotliquery.Row;
import org.compiere.accounting.MClient;
import org.compiere.accounting.MProduct;
import org.compiere.accounting.MStorageOnHand;
import org.compiere.accounting.NegativeInventoryDisallowedException;
import org.compiere.model.IDocLine;
import org.compiere.util.MsgKt;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.getSQLValue;

/**
 * Project Issue Model
 *
 * @author Jorg Janke
 * @version $Id: MProjectIssue.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MProjectIssue extends X_C_ProjectIssue implements IDocLine {

    /**
     *
     */
    private static final long serialVersionUID = 4714411434615096132L;

    /**
     * Standard Constructor
     *
     * @param ctx               context
     * @param C_ProjectIssue_ID id
     */
    public MProjectIssue(int C_ProjectIssue_ID) {
        super(C_ProjectIssue_ID);
        if (C_ProjectIssue_ID == 0) {
            //	setProjectId (0);
            //	setLine (0);
            //	setLocatorId (0);
            //	setProductId (0);
            //	setMovementDate (new Timestamp(System.currentTimeMillis()));
            setMovementQty(Env.ZERO);
            setPosted(false);
            setProcessed(false);
        }
    } //	MProjectIssue

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MProjectIssue(Row row) {
        super(row);
    } //	MProjectIssue

    /**
     * New Parent Constructor
     *
     * @param project parent
     */
    public MProjectIssue(MProject project) {
        this(0);
        setClientOrg(project.getClientId(), project.getOrgId());
        setProjectId(project.getProjectId()); // 	Parent
        setLine(getNextLine());
        //
        //	setLocatorId (0);
        //	setProductId (0);
        //
        setMovementDate(new Timestamp(System.currentTimeMillis()));
        setMovementQty(Env.ZERO);
        setPosted(false);
        setProcessed(false);
    } //	MProjectIssue

    /**
     * Get the next Line No
     *
     * @return next line no
     */
    private int getNextLine() {
        return getSQLValue(
                "SELECT COALESCE(MAX(Line),0)+10 FROM C_ProjectIssue WHERE C_Project_ID=?",
                getProjectId());
    } //	getLineFromProject

    /**
     * Set Mandatory Values
     *
     * @param M_Locator_ID locator
     * @param M_Product_ID product
     * @param MovementQty  qty
     */
    public void setMandatory(int M_Locator_ID, int M_Product_ID, BigDecimal MovementQty) {
        setLocatorId(M_Locator_ID);
        setProductId(M_Product_ID);
        setMovementQty(MovementQty);
    } //	setMandatory

    /**
     * ************************************************************************ Process Issue
     *
     * @return true if processed
     */
    public boolean process() {
        if (!save()) return false;
        if (getProductId() == 0) {
            log.log(Level.SEVERE, "No Product");
            return false;
        }

        MProduct product = MProduct.get(getProductId());

        //	If not a stocked Item nothing to do
        if (!product.isStocked()) {
            setProcessed(true);
            return save();
        }

        /** @todo Transaction */

        //	**	Create Material Transactions **
        MTransaction mTrx =
                new MTransaction(

                        getOrgId(),
                        MTransaction.MOVEMENTTYPE_WorkOrderPlus,
                        getLocatorId(),
                        getProductId(),
                        getAttributeSetInstanceId(),
                        getMovementQty().negate(),
                        getMovementDate()
                );
        mTrx.setProjectIssueId(getProjectIssueId());
        //
        MLocator loc = MLocator.get(getLocatorId());

        Timestamp dateMPolicy = getMovementDate();

        if (getAttributeSetInstanceId() > 0) {
            Timestamp t =
                    MStorageOnHand.getDateMaterialPolicy(
                            getProductId(), getAttributeSetInstanceId());
            if (t != null) dateMPolicy = t;
        }

        boolean ok = true;
        try {
            if (getMovementQty().negate().signum() < 0) {
                String MMPolicy = product.getMMPolicy();
                Timestamp minGuaranteeDate = getMovementDate();
                int M_Warehouse_ID =
                        getLocatorId() > 0
                                ? getLocator().getWarehouseId()
                                : getProject().getWarehouseId();
                MStorageOnHand[] storages =
                        MStorageOnHand.getWarehouse(

                                M_Warehouse_ID,
                                getProductId(),
                                getAttributeSetInstanceId(),
                                minGuaranteeDate,
                                MClient.MMPOLICY_FiFo.equals(MMPolicy),
                                true,
                                getLocatorId(),
                                null,
                                true);
                BigDecimal qtyToIssue = getMovementQty();
                for (MStorageOnHand storage : storages) {
                    if (storage.getQtyOnHand().compareTo(qtyToIssue) >= 0) {
                        storage.addQtyOnHand(qtyToIssue.negate());
                        qtyToIssue = BigDecimal.ZERO;
                    } else {
                        qtyToIssue = qtyToIssue.subtract(storage.getQtyOnHand());
                        storage.addQtyOnHand(storage.getQtyOnHand().negate());
                    }

                    if (qtyToIssue.signum() == 0) break;
                }
                if (qtyToIssue.signum() > 0) {
                    ok =
                            MStorageOnHand.add(

                                    loc.getWarehouseId(),
                                    getLocatorId(),
                                    getProductId(),
                                    getAttributeSetInstanceId(),
                                    qtyToIssue.negate(),
                                    dateMPolicy,
                                    null);
                }
            } else {
                ok =
                        MStorageOnHand.add(

                                loc.getWarehouseId(),
                                getLocatorId(),
                                getProductId(),
                                getAttributeSetInstanceId(),
                                getMovementQty().negate(),
                                dateMPolicy,
                                null);
            }
        } catch (NegativeInventoryDisallowedException e) {
            log.severe(e.getMessage());
            StringBuilder error = new StringBuilder();
            error.append(MsgKt.getElementTranslation("Line")).append(" ").append(getLine()).append(": ");
            error.append(e.getMessage()).append("\n");
            throw new AdempiereException(error.toString());
        }

        if (ok) {
            if (mTrx.save()) {
                setProcessed(true);
                if (save()) return true;
                else log.log(Level.SEVERE, "Issue not saved"); // 	requires trx !!
            } else log.log(Level.SEVERE, "Transaction not saved"); // 	requires trx !!
        } else log.log(Level.SEVERE, "Storage not updated"); // 	OK
        //
        return false;
    } //	process
} //	MProjectIssue
