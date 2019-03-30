package org.compiere.accounting;

import org.compiere.orm.MSysConfig;
import org.compiere.product.MAttributeSetInstance;
import org.compiere.product.MProduct;
import org.compiere.production.MLocator;
import org.compiere.util.Msg;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * @author hengsin
 */
public class NegativeInventoryDisallowedException extends AdempiereException {
    /**
     *
     */
    private static final long serialVersionUID = 253224414462489886L;

    public NegativeInventoryDisallowedException(
            Properties ctx,
            int M_Warehouse_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            int M_Locator_ID,
            BigDecimal QtyOnHand,
            BigDecimal MovementQty) {
        super(
                Msg.getMsg(
                        ctx,
                        "NegativeInventoryDisallowedInfo",
                        new Object[]{
                                MWarehouse.get(ctx, M_Warehouse_ID).getName(),
                                MProduct.get(ctx, M_Product_ID).getSearchKey()
                                        + MSysConfig.getConfigValue(
                                        MSysConfig.IDENTIFIER_SEPARATOR, "_", Env.getClientId(ctx))
                                        + MProduct.get(ctx, M_Product_ID).getName(),
                                M_AttributeSetInstance_ID > 0
                                        ? MAttributeSetInstance.get(ctx, M_AttributeSetInstance_ID, M_Product_ID)
                                        .getDescription()
                                        : "0",
                                M_Locator_ID > 0 ? MLocator.get(ctx, M_Locator_ID).getValue() : "0",
                                MovementQty.subtract(QtyOnHand)
                        }));

    }

}
