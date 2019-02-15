package org.compiere.production;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.idempiere.common.util.Env;

/**
 * Material Transaction Model
 *
 * @author Jorg Janke
 * @version $Id: MTransaction.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MTransaction extends X_M_Transaction {
  /** */
  private static final long serialVersionUID = 3411351000865493212L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param M_Transaction_ID id
   * @param trxName transaction
   */
  public MTransaction(Properties ctx, int M_Transaction_ID) {
    super(ctx, M_Transaction_ID);
    if (M_Transaction_ID == 0) {
      //	setM_Transaction_ID (0);		//	PK
      //	setM_Locator_ID (0);
      //	setM_Product_ID (0);
      setMovementDate(new Timestamp(System.currentTimeMillis()));
      setMovementQty(Env.ZERO);
      //	setMovementType (MOVEMENTTYPE_CustomerShipment);
    }
  } //	MTransaction

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MTransaction(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MTransaction

  /**
   * Detail Constructor
   *
   * @param ctx context
   * @param AD_Org_ID org
   * @param MovementType movement type
   * @param M_Locator_ID locator
   * @param M_Product_ID product
   * @param M_AttributeSetInstance_ID attribute
   * @param MovementQty qty
   * @param MovementDate optional date
   * @param trxName transaction
   */
  public MTransaction(
      Properties ctx,
      int AD_Org_ID,
      String MovementType,
      int M_Locator_ID,
      int M_Product_ID,
      int M_AttributeSetInstance_ID,
      BigDecimal MovementQty,
      Timestamp MovementDate,
      String trxName) {
    super(ctx, 0);
    setAD_Org_ID(AD_Org_ID);
    setMovementType(MovementType);
    if (M_Locator_ID == 0) throw new IllegalArgumentException("No Locator");
    setM_Locator_ID(M_Locator_ID);
    if (M_Product_ID == 0) throw new IllegalArgumentException("No Product");
    setM_Product_ID(M_Product_ID);
    setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
    //
    if (MovementQty != null) // 	Can be 0
    setMovementQty(MovementQty);
    if (MovementDate == null) setMovementDate(new Timestamp(System.currentTimeMillis()));
    else setMovementDate(MovementDate);
  } //	MTransaction

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("MTransaction[");
    sb.append(getId())
        .append(",")
        .append(getMovementType())
        .append(",Qty=")
        .append(getMovementQty())
        .append(",M_Product_ID=")
        .append(getM_Product_ID())
        .append(",ASI=")
        .append(getMAttributeSetInstance_ID())
        .append("]");
    return sb.toString();
  } //	toString
} //	MTransaction
