package org.compiere.accounting;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.model.I_C_OrderLandedCostAllocation;
import org.compiere.orm.Query;
import org.idempiere.common.util.Env;

/** @author hengsin */
public class MOrderLandedCostAllocation extends X_C_OrderLandedCostAllocation {

  /** generated serial id */
  private static final long serialVersionUID = -3876186097908624583L;

  /**
   * @param ctx
   * @param C_OrderLandedCostAllocation_ID
   * @param trxName
   */
  public MOrderLandedCostAllocation(
      Properties ctx, int C_OrderLandedCostAllocation_ID) {
    super(ctx, C_OrderLandedCostAllocation_ID);
  }

  /**
   * @param ctx
   * @param rs
   * @param trxName
   */
  public MOrderLandedCostAllocation(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  }

  /**
   * Set Amt
   *
   * @param Amt amount
   * @param precision precision
   */
  public void setAmt(double Amt, int precision) {
    BigDecimal bd = BigDecimal.valueOf(Amt);
    if (bd.scale() > precision) bd = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
    super.setAmt(bd);
  } //	setAmt

  /**
   * Get Lines of allocation
   *
   * @param whereClause starting with AND
   * @return lines
   */
  public static MOrderLandedCostAllocation[] getOfOrderLine(int C_OrderLine_ID) {
    StringBuilder whereClause =
        new StringBuilder(I_C_OrderLandedCostAllocation.COLUMNNAME_C_OrderLine_ID).append("=?");
    List<MOrderLandedCostAllocation> list =
        new Query(
                Env.getCtx(),
                I_C_OrderLandedCostAllocation.Table_Name,
                whereClause.toString()
        )
            .setParameters(C_OrderLine_ID)
            .list();
    return list.toArray(new MOrderLandedCostAllocation[list.size()]);
  } //	getLines

  public void setClientOrg(int a, int b) {
    super.setClientOrg(a, b);
  }
}
