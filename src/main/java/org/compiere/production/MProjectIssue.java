package org.compiere.production;

import org.compiere.accounting.MClient;
import org.compiere.accounting.MProduct;
import org.compiere.accounting.MStorageOnHand;
import org.compiere.accounting.NegativeInventoryDisallowedException;
import org.compiere.model.IDocLine;
import org.compiere.util.Msg;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.getSQLValue;

/**
 * Project Issue Model
 *
 * @author Jorg Janke
 * @version $Id: MProjectIssue.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MProjectIssue extends X_C_ProjectIssue implements IDocLine {

  /** */
  private static final long serialVersionUID = 4714411434615096132L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param C_ProjectIssue_ID id
   * @param trxName transaction
   */
  public MProjectIssue(Properties ctx, int C_ProjectIssue_ID, String trxName) {
    super(ctx, C_ProjectIssue_ID, trxName);
    if (C_ProjectIssue_ID == 0) {
      //	setC_Project_ID (0);
      //	setLine (0);
      //	setM_Locator_ID (0);
      //	setM_Product_ID (0);
      //	setMovementDate (new Timestamp(System.currentTimeMillis()));
      setMovementQty(Env.ZERO);
      setPosted(false);
      setProcessed(false);
    }
  } //	MProjectIssue

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MProjectIssue(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MProjectIssue

  /**
   * New Parent Constructor
   *
   * @param project parent
   */
  public MProjectIssue(MProject project) {
    this(project.getCtx(), 0, null);
    setClientOrg(project. getClientId(), project. getOrgId());
    setC_Project_ID(project.getC_Project_ID()); // 	Parent
    setLine(getNextLine());
    m_parent = project;
    //
    //	setM_Locator_ID (0);
    //	setM_Product_ID (0);
    //
    setMovementDate(new Timestamp(System.currentTimeMillis()));
    setMovementQty(Env.ZERO);
    setPosted(false);
    setProcessed(false);
  } //	MProjectIssue

  /** Parent */
  private MProject m_parent = null;

  /**
   * Get the next Line No
   *
   * @return next line no
   */
  private int getNextLine() {
    return getSQLValue(
        null,
        "SELECT COALESCE(MAX(Line),0)+10 FROM C_ProjectIssue WHERE C_Project_ID=?",
        getC_Project_ID());
  } //	getLineFromProject

  /**
   * Set Mandatory Values
   *
   * @param M_Locator_ID locator
   * @param M_Product_ID product
   * @param MovementQty qty
   */
  public void setMandatory(int M_Locator_ID, int M_Product_ID, BigDecimal MovementQty) {
    setM_Locator_ID(M_Locator_ID);
    setM_Product_ID(M_Product_ID);
    setMovementQty(MovementQty);
  } //	setMandatory

  /**
   * Get Parent
   *
   * @return project
   */
  public MProject getParent() {
    if (m_parent == null && getC_Project_ID() != 0)
      m_parent = new MProject(getCtx(), getC_Project_ID(), null);
    return m_parent;
  } //	getParent

  /**
   * ************************************************************************ Process Issue
   *
   * @return true if processed
   */
  public boolean process() {
    if (!save()) return false;
    if (getM_Product_ID() == 0) {
      log.log(Level.SEVERE, "No Product");
      return false;
    }

    MProduct product = MProduct.get(getCtx(), getM_Product_ID());

    //	If not a stocked Item nothing to do
    if (!product.isStocked()) {
      setProcessed(true);
      return save();
    }

    /** @todo Transaction */

    //	**	Create Material Transactions **
    MTransaction mTrx =
        new MTransaction(
            getCtx(),
             getOrgId(),
            MTransaction.MOVEMENTTYPE_WorkOrderPlus,
            getM_Locator_ID(),
            getM_Product_ID(),
            getMAttributeSetInstance_ID(),
            getMovementQty().negate(),
            getMovementDate(),
            null);
    mTrx.setC_ProjectIssue_ID(getC_ProjectIssue_ID());
    //
    MLocator loc = MLocator.get(getCtx(), getM_Locator_ID());

    Timestamp dateMPolicy = getMovementDate();

    if (getMAttributeSetInstance_ID() > 0) {
      Timestamp t =
          MStorageOnHand.getDateMaterialPolicy(
              getM_Product_ID(), getMAttributeSetInstance_ID(), null);
      if (t != null) dateMPolicy = t;
    }

    boolean ok = true;
    try {
      if (getMovementQty().negate().signum() < 0) {
        String MMPolicy = product.getMMPolicy();
        Timestamp minGuaranteeDate = getMovementDate();
        int M_Warehouse_ID =
            getM_Locator_ID() > 0
                ? getM_Locator().getM_Warehouse_ID()
                : getC_Project().getM_Warehouse_ID();
        MStorageOnHand[] storages =
            MStorageOnHand.getWarehouse(
                getCtx(),
                M_Warehouse_ID,
                getM_Product_ID(),
                getMAttributeSetInstance_ID(),
                minGuaranteeDate,
                MClient.MMPOLICY_FiFo.equals(MMPolicy),
                true,
                getM_Locator_ID(),
                null,
                true);
        BigDecimal qtyToIssue = getMovementQty();
        for (MStorageOnHand storage : storages) {
          if (storage.getQtyOnHand().compareTo(qtyToIssue) >= 0) {
            storage.addQtyOnHand(qtyToIssue.negate());
            qtyToIssue = BigDecimal.ZERO;
          } else {
            qtyToIssue = qtyToIssue.subtract(storage.getQtyOnHand());
            storage.addQtyOnHand(storage.getQtyOnHand().negate());
          }

          if (qtyToIssue.signum() == 0) break;
        }
        if (qtyToIssue.signum() > 0) {
          ok =
              MStorageOnHand.add(
                  getCtx(),
                  loc.getM_Warehouse_ID(),
                  getM_Locator_ID(),
                  getM_Product_ID(),
                  getMAttributeSetInstance_ID(),
                  qtyToIssue.negate(),
                  dateMPolicy,
                  null);
        }
      } else {
        ok =
            MStorageOnHand.add(
                getCtx(),
                loc.getM_Warehouse_ID(),
                getM_Locator_ID(),
                getM_Product_ID(),
                getMAttributeSetInstance_ID(),
                getMovementQty().negate(),
                dateMPolicy,
                null);
      }
    } catch (NegativeInventoryDisallowedException e) {
      log.severe(e.getMessage());
      StringBuilder error = new StringBuilder();
      error.append(Msg.getElement(getCtx(), "Line")).append(" ").append(getLine()).append(": ");
      error.append(e.getMessage()).append("\n");
      throw new AdempiereException(error.toString());
    }

    if (ok) {
      if (mTrx.save(null)) {
        setProcessed(true);
        if (save()) return true;
        else log.log(Level.SEVERE, "Issue not saved"); // 	requires trx !!
      } else log.log(Level.SEVERE, "Transaction not saved"); // 	requires trx !!
    } else log.log(Level.SEVERE, "Storage not updated"); // 	OK
    //
    return false;
  } //	process
} //	MProjectIssue
