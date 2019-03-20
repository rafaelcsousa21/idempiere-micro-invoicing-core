package org.idempiere.process;

import org.compiere.accounting.MAcctSchema;
import org.compiere.accounting.MCost;
import org.compiere.accounting.MProduct;
import org.compiere.accounting.X_T_BOM_Indented;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.orm.Query;
import org.compiere.process.SvrProcess;
import org.compiere.product.MProductBOM;
import org.idempiere.common.exceptions.FillMandatoryException;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Cost Multi-Level BOM & Formula Review
 *
 * @author victor.perez@e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 * @author pbowden@adaxa.com modified for manufacturing light
 */
public class IndentedBOM extends SvrProcess {
    //
    private int p_AD_Org_ID = 0;
    private int p_C_AcctSchema_ID = 0;
    private int p_M_Product_ID = 0;
    private int p_M_CostElement_ID = 0;
    //
    private int m_LevelNo = 0;
    private int m_SeqNo = 0;
    private MAcctSchema m_as = null;

    protected void prepare() {
        for (IProcessInfoParameter para : getParameter()) {
            String name = para.getParameterName();
            if (para.getParameter() == null) ;
            else if (name.equals(MCost.COLUMNNAME_C_AcctSchema_ID)) {
                p_C_AcctSchema_ID = para.getParameterAsInt();
                m_as = MAcctSchema.get(getCtx(), p_C_AcctSchema_ID);
            } else if (name.equals(MCost.COLUMNNAME_M_CostElement_ID)) {
                p_M_CostElement_ID = para.getParameterAsInt();
            } else if (name.equals(MCost.COLUMNNAME_M_Product_ID))
                p_M_Product_ID = para.getParameterAsInt();
            else log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    } // prepare

    /**
     * Perform process.
     *
     * @return Message (clear text)
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        if (p_M_Product_ID == 0) {
            throw new FillMandatoryException("M_Product_ID");
        }
        explodeProduct(p_M_Product_ID, Env.ONE, Env.ONE);
        //
        return "";
    } // doIt

    /**
     * Generate an Explosion for this product
     *
     * @param product
     * @param isComponent component / header
     */
    private llCost explodeProduct(int M_Product_ID, BigDecimal qty, BigDecimal accumQty) {
        MProduct product = MProduct.get(getCtx(), M_Product_ID);

        X_T_BOM_Indented tboml = new X_T_BOM_Indented(getCtx(), 0);

        tboml.setOrgId(p_AD_Org_ID);
        tboml.setAcctSchemaId(p_C_AcctSchema_ID);
        tboml.setPInstanceId(getProcessInstanceId());
        tboml.setCostElementId(p_M_CostElement_ID);
        tboml.setSelectedProductId(product.getId());
        tboml.setProductId(p_M_Product_ID);
        tboml.setQtyBOM(qty);
        tboml.setQty(accumQty);
        //
        tboml.setSeqNo(m_SeqNo);
        tboml.setLevelNo(m_LevelNo);
        String pad = "";
        if (m_LevelNo > 0) pad = String.format("%1$" + 4 * m_LevelNo + "s", "");
        tboml.setLevels((m_LevelNo > 0 ? ":" : "") + pad + " " + product.getValue());
        //
        // Set Costs:
        MCost cost = MCost.get(product, 0, m_as, p_AD_Org_ID, p_M_CostElement_ID);
        tboml.setCurrentCostPrice(cost.getCurrentCostPrice());
        tboml.setCost(cost.getCurrentCostPrice().multiply(accumQty));
        tboml.setFutureCostPrice(cost.getFutureCostPrice());
        tboml.setCostFuture(cost.getFutureCostPrice().multiply(accumQty));
        m_SeqNo++;

        BigDecimal llCost = Env.ZERO;
        BigDecimal llFutureCost = Env.ZERO;
        List<MProductBOM> list = getBOMs(product);
        for (MProductBOM bom : list) {
            m_LevelNo++;
            llCost ll =
                    explodeProduct(
                            bom.getBOMProductId(), bom.getBOMQty(), accumQty.multiply(bom.getBOMQty()));
            llCost = llCost.add(ll.currentCost.multiply(accumQty.multiply(bom.getBOMQty())));
            llFutureCost = llFutureCost.add(ll.futureCost.multiply(accumQty.multiply(bom.getBOMQty())));
            m_LevelNo--;
        }

        llCost retVal = new llCost();
        if (list.size() == 0) {
            tboml.setCurrentCostPriceLL(cost.getCurrentCostPrice());
            tboml.setFutureCostPriceLL(cost.getFutureCostPrice());

            //
            retVal.currentCost = cost.getCurrentCostPrice();
            retVal.futureCost = cost.getFutureCostPrice();
        } else {
            tboml.setCurrentCostPriceLL(llCost);
            tboml.setFutureCostPriceLL(llFutureCost);

            //
            retVal.currentCost = llCost;
            retVal.futureCost = llFutureCost;
        }

        tboml.saveEx();
        return retVal;
    }

    /**
     * Get BOMs for given product
     *
     * @param product
     * @param isComponent
     * @return list of MProductBOM
     */
    private List<MProductBOM> getBOMs(MProduct product) {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder whereClause = new StringBuilder();
        whereClause.append(MProductBOM.COLUMNNAME_M_Product_ID).append("=?");
        params.add(product.getId());

        List<MProductBOM> list =
                new Query(getCtx(), MProductBOM.Table_Name, whereClause.toString())
                        .setParameters(params)
                        .setOnlyActiveRecords(true)
                        .setOrderBy(MProductBOM.COLUMNNAME_Line)
                        .list();
        return list;
    }

    private static class llCost {
        BigDecimal currentCost = Env.ZERO;
        BigDecimal futureCost = Env.ZERO;
    }
}
