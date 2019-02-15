package org.idempiere.process;

import java.math.BigDecimal;
import org.compiere.accounting.MAcctSchema;
import org.compiere.accounting.MClient;
import org.compiere.accounting.MCost;
import org.compiere.accounting.MProduct;
import org.compiere.invoicing.MInventoryLine;
import org.compiere.process.SvrProcess;

/** @author hengsin */
public class CostAdjustmentLineRefreshCost extends SvrProcess {

    /* (non-Javadoc)
   * @see org.compiere.process.SvrProcess#prepare()
   */
  @Override
  protected void prepare() {}

  /* (non-Javadoc)
   * @see org.compiere.process.SvrProcess#doIt()
   */
  @Override
  protected String doIt() throws Exception {
    MInventoryLine line = new MInventoryLine(getCtx(), getRecord_ID());
    MProduct product = line.getProduct();
    MClient client = MClient.get(getCtx(), line.getClientId());
    MAcctSchema as = client.getAcctSchema();
    MCost cost =
        product.getCostingRecord(
            as,
            line.getOrgId(),
            line.getMAttributeSetInstance_ID(),
            line.getM_Inventory().getCostingMethod());
    if (cost != null) {
      line.setCurrentCostPrice(cost.getCurrentCostPrice());
      line.setNewCostPrice(cost.getCurrentCostPrice());
      line.saveEx();
    } else {
      line.setCurrentCostPrice(BigDecimal.ZERO);
      line.setNewCostPrice(BigDecimal.ZERO);
      line.saveEx();
    }

    return "@Ok@";
  }
}
