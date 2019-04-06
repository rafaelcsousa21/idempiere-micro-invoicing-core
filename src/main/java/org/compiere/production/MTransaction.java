package org.compiere.production;

import kotliquery.Row;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Material Transaction Model
 *
 * @author Jorg Janke
 * @version $Id: MTransaction.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MTransaction extends X_M_Transaction {
    /**
     *
     */
    private static final long serialVersionUID = 3411351000865493212L;

    /**
     * Standard Constructor
     *
     * @param ctx              context
     * @param M_Transaction_ID id
     */
    public MTransaction(int M_Transaction_ID) {
        super(M_Transaction_ID);
        if (M_Transaction_ID == 0) {
            setMovementDate(new Timestamp(System.currentTimeMillis()));
            setMovementQty(Env.ZERO);
        }
    } //	MTransaction

    public MTransaction(Row row) {
        super(row);
    }

    /**
     * Detail Constructor
     *
     * @param ctx                       context
     * @param AD_Org_ID                 org
     * @param MovementType              movement type
     * @param M_Locator_ID              locator
     * @param M_Product_ID              product
     * @param M_AttributeSetInstance_ID attribute
     * @param MovementQty               qty
     * @param MovementDate              optional date
     */
    public MTransaction(

            int AD_Org_ID,
            String MovementType,
            int M_Locator_ID,
            int M_Product_ID,
            int M_AttributeSetInstance_ID,
            BigDecimal MovementQty,
            Timestamp MovementDate) {
        super(0);
        setOrgId(AD_Org_ID);
        setMovementType(MovementType);
        if (M_Locator_ID == 0) throw new IllegalArgumentException("No Locator");
        setLocatorId(M_Locator_ID);
        if (M_Product_ID == 0) throw new IllegalArgumentException("No Product");
        setProductId(M_Product_ID);
        setAttributeSetInstanceId(M_AttributeSetInstance_ID);
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
        return "MTransaction[" + getId() +
                "," +
                getMovementType() +
                ",Qty=" +
                getMovementQty() +
                ",M_Product_ID=" +
                getProductId() +
                ",ASI=" +
                getAttributeSetInstanceId() +
                "]";
    } //	toString
} //	MTransaction
