package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.util.Msg;
import org.idempiere.common.util.CLogger;

import java.util.Properties;


/**
 * Landed Cost Model
 *
 * @author Jorg Janke
 * @version $Id: MLandedCost.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MLandedCost extends X_C_LandedCost {
    /**
     *
     */
    private static final long serialVersionUID = -5645509613930428050L;

    /**
     * ************************************************************************* Standard Constructor
     *
     * @param ctx             context
     * @param C_LandedCost_ID id
     */
    public MLandedCost(Properties ctx, int C_LandedCost_ID) {
        super(ctx, C_LandedCost_ID);
        if (C_LandedCost_ID == 0) {
            //	setInvoiceLineId (0);
            //	setCostElementId (0);
            setLandedCostDistribution(X_C_LandedCost.LANDEDCOSTDISTRIBUTION_Quantity); // Q
        }
    } //	MLandedCost

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MLandedCost(Properties ctx, Row row) {
        super(ctx, row);
    } //	MLandedCost

    /**
     * Get Costs of Invoice Line
     *
     * @param il invoice line
     * @return array of landed cost lines
     */
    public static MLandedCost[] getLandedCosts(MInvoiceLine il) {
        return MBaseLandedCostKt.getInvoiceLineLandedCosts(il);
    } // getLandedCosts

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true if ok
     */
    protected boolean beforeSave(boolean newRecord) {
        //	One Reference
        if (getProductId() == 0 && getInOutId() == 0 && getInOutLineId() == 0) {
            log.saveError(
                    "Error",
                    Msg.parseTranslation(
                            getCtx(), "@NotFound@ @M_Product_ID@ | @M_InOut_ID@ | @M_InOutLine_ID@"));
            return false;
        }
        //	No Product if Line entered
        if (getInOutLineId() != 0 && getProductId() != 0) setProductId(0);

        return true;
    } //	beforeSave

    /**
     * Allocate Costs. Done at Invoice Line Level
     *
     * @return error message or ""
     */
    public String allocateCosts() {
        MInvoiceLine il = new MInvoiceLine(getCtx(), getInvoiceLineId());
        return il.allocateLandedCosts();
    } //	allocateCosts

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MLandedCost[");
        sb.append(getId())
                .append(",CostDistribution=")
                .append(getLandedCostDistribution())
                .append(",M_CostElement_ID=")
                .append(getCostElementId());
        if (getInOutId() != 0) sb.append(",M_InOut_ID=").append(getInOutId());
        if (getInOutLineId() != 0) sb.append(",M_InOutLine_ID=").append(getInOutLineId());
        if (getProductId() != 0) sb.append(",M_Product_ID=").append(getProductId());
        sb.append("]");
        return sb.toString();
    } //	toString
} //	MLandedCost
