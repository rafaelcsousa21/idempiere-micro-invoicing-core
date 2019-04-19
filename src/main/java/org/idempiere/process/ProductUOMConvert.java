package org.idempiere.process;

import org.compiere.accounting.MProduct;
import org.compiere.accounting.MUOMConversion;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.process.SvrProcess;
import org.compiere.product.MUOM;
import org.idempiere.common.util.AdempiereUserError;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.logging.Level;

/**
 * Product UOM Conversion
 *
 * @author Jorg Janke
 * @version $Id: ProductUOMConvert.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ProductUOMConvert extends SvrProcess {
    /**
     * Product From
     */
    private int p_M_Product_ID = 0;
    /**
     * Product To
     */
    private int p_M_Product_To_ID = 0;
    /**
     * Locator
     */
    private int p_M_Locator_ID = 0;
    /**
     * Quantity
     */
    private BigDecimal p_Qty = null;

    /**
     * Prepare
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            switch (name) {
                case "M_Product_ID":
                    p_M_Product_ID = iProcessInfoParameter.getParameterAsInt();
                    break;
                case "M_Product_To_ID":
                    p_M_Product_To_ID = iProcessInfoParameter.getParameterAsInt();
                    break;
                case "M_Locator_ID":
                    p_M_Locator_ID = iProcessInfoParameter.getParameterAsInt();
                    break;
                case "Qty":
                    p_Qty = (BigDecimal) iProcessInfoParameter.getParameter();
                    break;
                default:
                    log.log(Level.SEVERE, "Unknown Parameter: " + name);
                    break;
            }
        }
    } //	prepare

    /**
     * Process
     *
     * @return message
     * @throws Exception
     */
    protected String doIt() throws Exception {
        if (p_M_Product_ID == 0
                || p_M_Product_To_ID == 0
                || p_M_Locator_ID == 0
                || p_Qty == null
                || Env.ZERO.compareTo(p_Qty) == 0) throw new AdempiereUserError("Invalid Parameter");
        //
        MProduct product = MProduct.get(p_M_Product_ID);
        MProduct productTo = MProduct.get(p_M_Product_To_ID);
        if (log.isLoggable(Level.INFO))
            log.info(
                    "Product="
                            + product
                            + ", ProductTo="
                            + productTo
                            + ", M_Locator_ID="
                            + p_M_Locator_ID
                            + ", Qty="
                            + p_Qty);

        I_C_UOM_Conversion[] conversions =
                MUOMConversion.getProductConversions(product.getProductId());
        I_C_UOM_Conversion conversion = null;
        for (I_C_UOM_Conversion i_c_uom_conversion : conversions) {
            if (i_c_uom_conversion.getTargetUOMId() == productTo.getUOMId()) conversion = i_c_uom_conversion;
        }
        if (conversion == null) throw new AdempiereUserError("@NotFound@: @C_UOM_Conversion_ID@");

        MUOM uomTo = MUOM.get(productTo.getUOMId());
        BigDecimal qtyTo =
                p_Qty.divide(conversion.getDivideRate(), uomTo.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
        BigDecimal qtyTo6 = p_Qty.divide(conversion.getDivideRate(), 6, BigDecimal.ROUND_HALF_UP);
        if (qtyTo.compareTo(qtyTo6) != 0)
            throw new AdempiereUserError(
                    "@StdPrecision@: "
                            + qtyTo
                            + " <> "
                            + qtyTo6
                            + " ("
                            + p_Qty
                            + "/"
                            + conversion.getDivideRate()
                            + ")");
        if (log.isLoggable(Level.INFO)) log.info(conversion + " -> " + qtyTo);

        //	Set to Beta
        return "Not completed yet";
    } //	doIt
} //	ProductUOMConvert
