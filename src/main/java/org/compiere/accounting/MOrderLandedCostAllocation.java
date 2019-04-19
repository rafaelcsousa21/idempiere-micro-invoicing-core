package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_OrderLandedCostAllocation;
import org.compiere.orm.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hengsin
 */
public class MOrderLandedCostAllocation extends X_C_OrderLandedCostAllocation {

    /**
     * generated serial id
     */
    private static final long serialVersionUID = -3876186097908624583L;

    /**
     * @param C_OrderLandedCostAllocation_ID
     */
    public MOrderLandedCostAllocation(
            int C_OrderLandedCostAllocation_ID) {
        super(C_OrderLandedCostAllocation_ID);
    }

    /**
     */
    public MOrderLandedCostAllocation(Row row) {
        super(row);
    }

    /**
     * Get Lines of allocation
     *
     * @return lines
     */
    public static I_C_OrderLandedCostAllocation[] getOfOrderLine(int C_OrderLine_ID) {
        List<I_C_OrderLandedCostAllocation> list =
                new Query<I_C_OrderLandedCostAllocation>(
                        I_C_OrderLandedCostAllocation.Table_Name,
                        I_C_OrderLandedCostAllocation.COLUMNNAME_C_OrderLine_ID + "=?"
                )
                        .setParameters(C_OrderLine_ID)
                        .list();
        return list.toArray(new I_C_OrderLandedCostAllocation[0]);
    } //	getLines

    /**
     * Set Amt
     *
     * @param Amt       amount
     * @param precision precision
     */
    public void setAmt(double Amt, int precision) {
        BigDecimal bd = BigDecimal.valueOf(Amt);
        if (bd.scale() > precision) bd = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
        super.setAmt(bd);
    } //	setAmt

    public void setClientOrg(int a, int b) {
        super.setClientOrg(a, b);
    }
}
