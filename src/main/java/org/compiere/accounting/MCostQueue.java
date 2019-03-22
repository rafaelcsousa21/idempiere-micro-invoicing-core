package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_AcctSchema;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Cost Queue Model
 *
 * @author Jorg Janke
 * @version $Id: MCostQueue.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MCostQueue extends X_M_CostQueue {
    /**
     *
     */
    private static final long serialVersionUID = -1782836708418500130L;
    /**
     * Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MCostQueue.class);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx     context
     * @param ignored multi-key
     */
    public MCostQueue(Properties ctx, int ignored) {
        super(ctx, ignored);
        if (ignored == 0) {
            //	setAccountingSchemaId (0);
            //	setAttributeSetInstanceId (0);
            //	setCostElementId (0);
            //	setCostTypeId (0);
            //	setProductId (0);
            setCurrentCostPrice(Env.ZERO);
            setCurrentQty(Env.ZERO);
        } else throw new IllegalArgumentException("Multi-Key");
    } //	MCostQueue

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MCostQueue(Properties ctx, Row row) {
        super(ctx, row);
    } //	MCostQueue

    /**
     * Parent Constructor
     *
     * @param product                   Product
     * @param M_AttributeSetInstance_ID asi
     * @param as                        Acct Schema
     * @param AD_Org_ID                 org
     * @param M_CostElement_ID          cost element
     */
    public MCostQueue(
            MProduct product,
            int M_AttributeSetInstance_ID,
            MAcctSchema as,
            int AD_Org_ID,
            int M_CostElement_ID) {
        this(product.getCtx(), 0);
        setClientOrg(product.getClientId(), AD_Org_ID);
        setAccountingSchemaId(as.getAccountingSchemaId());
        setCostTypeId(as.getCostTypeId());
        setProductId(product.getProductId());
        setAttributeSetInstanceId(M_AttributeSetInstance_ID);
        setCostElementId(M_CostElement_ID);
    } //	MCostQueue

    /**
     * Get/Create Cost Queue Record. CostingLevel is not validated
     *
     * @param product                   product
     * @param M_AttributeSetInstance_ID real asi
     * @param as                        accounting schema
     * @param AD_Org_ID                 real org
     * @param M_CostElement_ID          element
     * @return cost queue or null
     */
    public static MCostQueue get(
            MProduct product,
            int M_AttributeSetInstance_ID,
            MAcctSchema as,
            int AD_Org_ID,
            int M_CostElement_ID) {
        return MBaseCostQueueKt.getCreateCostQueueRecord(product,
                M_AttributeSetInstance_ID,
                as,
                AD_Org_ID,
                M_CostElement_ID
        );
    } //	get

    /**
     * Get Cost Queue Records in Lifo/Fifo order
     *
     * @param product  product
     * @param M_ASI_ID costing level ASI
     * @param as       accounting schema
     * @param Org_ID   costing level org
     * @param ce       Cost Element
     * @return cost queue or null
     */
    public static MCostQueue[] getQueue(
            MProduct product, int M_ASI_ID, I_C_AcctSchema as, int Org_ID, MCostElement ce) {
        return MBaseCostQueueKt.getCostQueueRecordsInLifoFifoOrder(product, M_ASI_ID, as, Org_ID, ce);
    } //	getQueue

    /**
     * Adjust Qty based on in Lifo/Fifo order
     *
     * @param product  product
     * @param M_ASI_ID costing level ASI
     * @param as       accounting schema
     * @param Org_ID   costing level org
     * @param ce       Cost Element
     * @param Qty      quantity to be reduced
     * @param trxName  transaction
     * @return cost price reduced or null of error
     */
    public static BigDecimal adjustQty(
            MProduct product,
            int M_ASI_ID,
            MAcctSchema as,
            int Org_ID,
            MCostElement ce,
            BigDecimal Qty,
            String trxName) {
        if (Qty.signum() == 0) return Env.ZERO;
        MCostQueue[] costQ = getQueue(product, M_ASI_ID, as, Org_ID, ce);
        BigDecimal remainingQty = Qty;
        for (int i = 0; i < costQ.length; i++) {
            MCostQueue queue = costQ[i];
            //	Negative Qty i.e. add
            if (remainingQty.signum() < 0) {
                BigDecimal oldQty = queue.getCurrentQty();
                BigDecimal newQty = oldQty.subtract(remainingQty);
                queue.setCurrentQty(newQty);
                if (queue.save()) {
                    if (s_log.isLoggable(Level.FINE))
                        s_log.fine(
                                "Qty="
                                        + remainingQty
                                        + "(!), ASI="
                                        + queue.getAttributeSetInstanceId()
                                        + " - "
                                        + oldQty
                                        + " -> "
                                        + newQty);
                    return queue.getCurrentCostPrice();
                } else return null;
            }

            //	Positive queue
            if (queue.getCurrentQty().signum() > 0) {
                BigDecimal reduction = remainingQty;
                if (reduction.compareTo(queue.getCurrentQty()) > 0) reduction = queue.getCurrentQty();
                BigDecimal oldQty = queue.getCurrentQty();
                BigDecimal newQty = oldQty.subtract(reduction);
                queue.setCurrentQty(newQty);
                if (queue.save()) {
                    if (s_log.isLoggable(Level.FINE))
                        s_log.fine(
                                "Qty="
                                        + reduction
                                        + ", ASI="
                                        + queue.getAttributeSetInstanceId()
                                        + " - "
                                        + oldQty
                                        + " -> "
                                        + newQty);
                    remainingQty = remainingQty.subtract(reduction);
                } else return null;
                //
                if (remainingQty.signum() == 0) {
                    return queue.getCurrentCostPrice();
                }
            }
        } //	for queue

        if (s_log.isLoggable(Level.FINE)) s_log.fine("RemainingQty=" + remainingQty);
        return null;
    } //	adjustQty

    /**
     * Calculate Cost based on Qty based on in Lifo/Fifo order
     *
     * @param product  product
     * @param M_ASI_ID costing level ASI
     * @param as       accounting schema
     * @param Org_ID   costing level org
     * @param ce       Cost Element
     * @param Qty      quantity to be reduced
     * @return cost for qty or null of error
     */
    public static BigDecimal getCosts(
            MProduct product,
            int M_ASI_ID,
            I_C_AcctSchema as,
            int Org_ID,
            MCostElement ce,
            BigDecimal Qty) {
        if (Qty.signum() == 0) return Env.ZERO;
        MCostQueue[] costQ = getQueue(product, M_ASI_ID, as, Org_ID, ce);
        //
        BigDecimal cost = Env.ZERO;
        BigDecimal remainingQty = Qty;
        BigDecimal firstPrice = null;
        BigDecimal lastPrice = null;
        //
        for (int i = 0; i < costQ.length; i++) {
            MCostQueue queue = costQ[i];
            //	Negative Qty i.e. add
            if (remainingQty.signum() <= 0) {
                @SuppressWarnings("unused")
                BigDecimal oldQty = queue.getCurrentQty();
                lastPrice = queue.getCurrentCostPrice();
                BigDecimal costBatch = lastPrice.multiply(remainingQty);
                cost = cost.add(costBatch);
                if (s_log.isLoggable(Level.CONFIG))
                    s_log.config(
                            "ASI="
                                    + queue.getAttributeSetInstanceId()
                                    + " - Cost="
                                    + lastPrice
                                    + " * Qty="
                                    + remainingQty
                                    + "(!) = "
                                    + costBatch);
                return cost;
            }

            //	Positive queue
            if (queue.getCurrentQty().signum() > 0) {
                BigDecimal reduction = remainingQty;
                if (reduction.compareTo(queue.getCurrentQty()) > 0) reduction = queue.getCurrentQty();
                @SuppressWarnings("unused")
                BigDecimal oldQty = queue.getCurrentQty();
                lastPrice = queue.getCurrentCostPrice();
                BigDecimal costBatch = lastPrice.multiply(reduction);
                cost = cost.add(costBatch);
                if (s_log.isLoggable(Level.FINE))
                    s_log.fine(
                            "ASI="
                                    + queue.getAttributeSetInstanceId()
                                    + " - Cost="
                                    + lastPrice
                                    + " * Qty="
                                    + reduction
                                    + " = "
                                    + costBatch);
                remainingQty = remainingQty.subtract(reduction);
                //	Done
                if (remainingQty.signum() == 0) {
                    if (s_log.isLoggable(Level.CONFIG)) s_log.config("Cost=" + cost);
                    return cost;
                }
                if (firstPrice == null) firstPrice = lastPrice;
            }
        } //	for queue

        if (lastPrice == null) {
            lastPrice = MCost.getSeedCosts(product, M_ASI_ID, as, Org_ID, ce.getCostingMethod(), 0);
            if (lastPrice == null) {
                s_log.info("No Price found");
                return null;
            }
            s_log.info("No Cost Queue");
        }
        BigDecimal costBatch = lastPrice.multiply(remainingQty);
        s_log.fine("RemainingQty=" + remainingQty + " * LastPrice=" + lastPrice + " = " + costBatch);
        cost = cost.add(costBatch);
        if (s_log.isLoggable(Level.CONFIG)) s_log.config("Cost=" + cost);
        return cost;
    } //	getCosts

    /**
     * Update Record. ((OldAvg*OldQty)+(Price*Qty)) / (OldQty+Qty)
     *
     * @param amt       total Amount
     * @param qty       quantity
     * @param precision costing precision
     */
    public void setCosts(BigDecimal amt, BigDecimal qty, int precision) {
        BigDecimal oldSum = getCurrentCostPrice().multiply(getCurrentQty());
        BigDecimal newSum = amt; // 	is total already
        BigDecimal sumAmt = oldSum.add(newSum);
        BigDecimal sumQty = getCurrentQty().add(qty);
        if (sumQty.signum() != 0) {
            BigDecimal cost = sumAmt.divide(sumQty, precision, BigDecimal.ROUND_HALF_UP);
            setCurrentCostPrice(cost);
        }
        //
        setCurrentQty(getCurrentQty().add(qty));
    } //	update
} //	MCostQueue
