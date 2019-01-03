package org.compiere.accounting;

import java.math.BigDecimal;
import java.util.Properties;
import org.compiere.orm.MSysConfig;
import org.compiere.product.MAttributeSetInstance;
import org.compiere.product.MProduct;
import org.compiere.production.MLocator;
import org.compiere.util.Msg;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

/** @author hengsin */
public class NegativeInventoryDisallowedException extends AdempiereException {
  /** */
  private static final long serialVersionUID = 253224414462489886L;

  private int M_Warehouse_ID;
  private int M_Product_ID;
  private int M_AttributeSetInstance_ID;
  private int M_Locator_ID;
  private BigDecimal QtyOnHand;
  private BigDecimal MovementQty;

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
            new Object[] {
              MWarehouse.get(ctx, M_Warehouse_ID).getName(),
              MProduct.get(ctx, M_Product_ID).getValue()
                  + MSysConfig.getValue(
                      MSysConfig.IDENTIFIER_SEPARATOR, "_", Env.getClientId(ctx))
                  + MProduct.get(ctx, M_Product_ID).getName(),
              M_AttributeSetInstance_ID > 0
                  ? MAttributeSetInstance.get(ctx, M_AttributeSetInstance_ID, M_Product_ID)
                      .getDescription()
                  : "0",
              M_Locator_ID > 0 ? MLocator.get(ctx, M_Locator_ID).getValue() : "0",
              MovementQty.subtract(QtyOnHand)
            }));

    this.M_Warehouse_ID = M_Warehouse_ID;
    this.M_Product_ID = M_Product_ID;
    this.M_AttributeSetInstance_ID = M_AttributeSetInstance_ID;
    this.M_Locator_ID = M_Locator_ID;
    this.QtyOnHand = QtyOnHand;
    this.MovementQty = MovementQty;
  }

  public int getM_Warehouse_ID() {
    return M_Warehouse_ID;
  }

  public int getM_Product_ID() {
    return M_Product_ID;
  }

  public int getMAttributeSetInstance_ID() {
    return M_AttributeSetInstance_ID;
  }

  public int getM_Locator_ID() {
    return M_Locator_ID;
  }

  public BigDecimal getQtyOnHand() {
    return QtyOnHand;
  }

  public BigDecimal getMovementQty() {
    return MovementQty;
  }
}
