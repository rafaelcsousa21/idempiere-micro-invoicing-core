package org.compiere.accounting;

import java.sql.ResultSet;
import java.util.Properties;
import org.idempiere.common.util.Env;

/**
 * Revenue Recognition Plan
 *
 * @author Jorg Janke
 * @version $Id: MRevenueRecognitionPlan.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MRevenueRecognitionPlan extends X_C_RevenueRecognition_Plan {
  /** */
  private static final long serialVersionUID = -8437258098744762898L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param C_RevenueRecognition_Plan_ID id
   */
  public MRevenueRecognitionPlan(Properties ctx, int C_RevenueRecognition_Plan_ID) {
    super(ctx, C_RevenueRecognition_Plan_ID);
    if (C_RevenueRecognition_Plan_ID == 0) {
      //	setC_AcctSchema_ID (0);
      //	setC_Currency_ID (0);
      //	setC_InvoiceLine_ID (0);
      //	setC_RevenueRecognition_ID (0);
      //	setC_RevenueRecognition_Plan_ID (0);
      //	setP_Revenue_Acct (0);
      //	setUnEarnedRevenue_Acct (0);
      setTotalAmt(Env.ZERO);
      setRecognizedAmt(Env.ZERO);
    }
  } //	MRevenueRecognitionPlan

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   */
  public MRevenueRecognitionPlan(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MRevenueRecognitionPlan

  /**
   * After Save
   *
   * @param newRecord new
   * @param success success
   * @return success
   */
  //	protected boolean afterSave (boolean newRecord, boolean success)
  //	{
  //		if (!success)
  //			return success;
  //		if (newRecord)
  //		{
  //			MRevenueRecognition rr = new MRevenueRecognition(getCtx(), getC_RevenueRecognition_ID(),
  // null);
  //			if (rr.isTimeBased())
  //			{
  //				/**	Get InvoiveQty
  //				SELECT	QtyInvoiced, M_Product_ID
  //				  INTO	v_Qty, v_M_Product_ID
  //				FROM	C_InvoiceLine
  //				WHERE 	C_InvoiceLine_ID=:new.C_InvoiceLine_ID;
  //				--	Insert
  //				AD_Sequence_Next ('C_ServiceLevel', :new.AD_Client_ID, v_NextNo);
  //				INSERT INTO C_ServiceLevel
  //					(C_ServiceLevel_ID, C_RevenueRecognition_Plan_ID,
  //					AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
  //					M_Product_ID, Description, ServiceLevelInvoiced, ServiceLevelProvided,
  //					Processing,Processed)
  //				VALUES
  //					(v_NextNo, :new.C_RevenueRecognition_Plan_ID,
  //					:new.AD_Client_ID,:new.AD_Org_ID,'Y',SysDate,:new.CreatedBy,SysDate,:new.UpdatedBy,
  //					v_M_Product_ID, NULL, v_Qty, 0,
  //					'N', 'N');
  //				**/
  //			}
  //		}
  //		return success;
  //	}	//	afterSave
} //	MRevenueRecognitionPlan
