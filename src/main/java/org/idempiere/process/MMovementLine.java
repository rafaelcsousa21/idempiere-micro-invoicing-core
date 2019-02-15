package org.idempiere.process;

import org.compiere.accounting.MProduct;
import org.compiere.accounting.MWarehouse;
import org.compiere.model.I_M_Product;
import org.compiere.orm.Query;
import org.compiere.production.MLocator;
import org.compiere.util.Msg;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.getSQLValue;

public class MMovementLine extends X_M_MovementLine {
  /** */
  private static final long serialVersionUID = -4078367839033015886L;

  /**
   * Standard Cosntructor
   *
   * @param ctx context
   * @param M_MovementLine_ID id
   * @param trxName transaction
   */
  public MMovementLine(Properties ctx, int M_MovementLine_ID) {
    super(ctx, M_MovementLine_ID);
    if (M_MovementLine_ID == 0) {
      //	setM_LocatorTo_ID (0);	// @M_LocatorTo_ID@
      //	setM_Locator_ID (0);	// @M_Locator_ID@
      //	setM_MovementLine_ID (0);
      //	setLine (0);
      //	setM_Product_ID (0);
      setM_AttributeSetInstance_ID(0); // 	ID
      setMovementQty(Env.ZERO); // 1
      setTargetQty(Env.ZERO); // 0
      setScrappedQty(Env.ZERO);
      setConfirmedQty(Env.ZERO);
      setProcessed(false);
    }
  } //	MMovementLine

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MMovementLine(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MMovementLine

  /**
   * Parent constructor
   *
   * @param parent parent
   */
  public MMovementLine(MMovement parent) {
    this(parent.getCtx(), 0);
    setClientOrg(parent);
    setM_Movement_ID(parent.getM_Movement_ID());
  } //	MMovementLine

  /**
   * Get AttributeSetInstance To
   *
   * @return ASI
   */
  @Override
  public int getMAttributeSetInstanceTo_ID() {
    int M_AttributeSetInstanceTo_ID = super.getMAttributeSetInstanceTo_ID();
    if (M_AttributeSetInstanceTo_ID == 0 && (getM_Locator_ID() == getM_LocatorTo_ID()))
      M_AttributeSetInstanceTo_ID = super.getMAttributeSetInstance_ID();
    return M_AttributeSetInstanceTo_ID;
  } //	getMAttributeSetInstanceTo_ID

  /**
   * Add to Description
   *
   * @param description text
   */
  public void addDescription(String description) {
    String desc = getDescription();
    if (desc == null) setDescription(description);
    else setDescription(desc + " | " + description);
  } //	addDescription

  /**
   * Get Product
   *
   * @return product or null if not defined
   */
  public MProduct getProduct() {
    if (getM_Product_ID() != 0) return MProduct.get(getCtx(), getM_Product_ID());
    return null;
  } //	getProduct

  /**
   * Set Movement Qty - enforce UOM precision
   *
   * @param MovementQty qty
   */
  @Override
  public void setMovementQty(BigDecimal MovementQty) {
    if (MovementQty != null) {
      MProduct product = getProduct();
      if (product != null) {
        int precision = product.getUOMPrecision();
        MovementQty = MovementQty.setScale(precision, BigDecimal.ROUND_HALF_UP);
      }
    }
    super.setMovementQty(MovementQty);
  } //	setMovementQty

  /** Parent */
  private MMovement m_parent = null;

  /**
   * get Parent
   *
   * @return Parent Movement
   */
  public MMovement getParent() {
    if (m_parent == null) m_parent = new MMovement(getCtx(), getM_Movement_ID());
    return m_parent;
  } //	getParent

  /**
   * Before Save
   *
   * @param newRecord new
   * @return true
   */
  @Override
  protected boolean beforeSave(boolean newRecord) {
    if (newRecord && getParent().isComplete()) {
      log.saveError("ParentComplete", Msg.translate(getCtx(), "M_MovementLine"));
      return false;
    }
    //	Set Line No
    if (getLine() == 0) {
      String sql =
          "SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_MovementLine WHERE M_Movement_ID=?";
      int ii = getSQLValue(sql, getM_Movement_ID());
      setLine(ii);
    }

    // either movement between locator or movement between lot
    if (getM_Locator_ID() == getM_LocatorTo_ID()
        && getMAttributeSetInstance_ID() == getMAttributeSetInstanceTo_ID()) {
      log.saveError(
          "Error",
          Msg.parseTranslation(
              getCtx(),
              "@M_Locator_ID@ == @M_LocatorTo_ID@ and @M_AttributeSetInstance_ID@ == @M_AttributeSetInstanceTo_ID@"));
      return false;
    }

    if (getMovementQty().signum() == 0) {
      if (MMovement.DOCACTION_Void.equals(getParent().getDocAction())
          && (MMovement.DOCSTATUS_Drafted.equals(getParent().getDocStatus())
              || MMovement.DOCSTATUS_Invalid.equals(getParent().getDocStatus())
              || MMovement.DOCSTATUS_InProgress.equals(getParent().getDocStatus())
              || MMovement.DOCSTATUS_Approved.equals(getParent().getDocStatus())
              || MMovement.DOCSTATUS_NotApproved.equals(getParent().getDocStatus()))) {
        // [ 2092198 ] Error voiding an Inventory Move - globalqss
        // zero allowed in this case (action Void and status Draft)
      } else {
        log.saveError("FillMandatory", Msg.getElement(getCtx(), "MovementQty"));
        return false;
      }
    }

    //	Qty Precision
    if (newRecord || is_ValueChanged(COLUMNNAME_MovementQty)) setMovementQty(getMovementQty());

    //      Mandatory Instance
    /* IDEMPIERE-1770 - ASI validation must be moved to MMovement.prepareIt, saving a line without ASI is ok on draft
    MProduct product = getProduct();
    if (getMAttributeSetInstance_ID() == 0) {
    	if (product != null && product.isASIMandatory(true)) {
    		if (product.getAttributeSet()==null) {
    			log.saveError("NoAttributeSet", product.getValue());
    			return false;
    		}
    		if (! product.getAttributeSet().excludeTableEntry(MMovementLine.Table_ID, true)) {  // outgoing
    			log.saveError("FillMandatory", Msg.getElement(getCtx(), COLUMNNAME_M_AttributeSetInstance_ID));
    			return false;
    		}
    	}
    }
    */
    if (getMAttributeSetInstanceTo_ID() == 0) {
      // instance id default to same for movement between locator
      if (getM_Locator_ID() != getM_LocatorTo_ID()) {
        if (getMAttributeSetInstance_ID() != 0) // set to from
        setM_AttributeSetInstanceTo_ID(getMAttributeSetInstance_ID());
      }

      /* IDEMPIERE-1770 - ASI validation must be moved to MMovement.prepareIt, saving a line without ASI is ok on draft
      if (product != null && product.isASIMandatory(false) && getMAttributeSetInstanceTo_ID() == 0)
      {
      	if (product.getAttributeSet()==null) {
      		log.saveError("NoAttributeSet", product.getValue());
      		return false;
      	}
      	if (! product.getAttributeSet().excludeTableEntry(MMovementLine.Table_ID, false)) { // incoming
      		log.saveError("FillMandatory", Msg.getElement(getCtx(), COLUMNNAME_M_AttributeSetInstanceTo_ID));
      		return false;
      	}
      }
      */
    } //      ASI

    return true;
  } //	beforeSave

    /**
   * Set M_Locator_ID
   *
   * @param M_Locator_ID id
   */
  @Override
  public void setM_Locator_ID(int M_Locator_ID) {
    if (M_Locator_ID < 0) throw new IllegalArgumentException("M_Locator_ID is mandatory.");
    //      set to 0 explicitly to reset
    set_Value(COLUMNNAME_M_Locator_ID, M_Locator_ID);
  } //      setM_Locator_ID

  /**
   * Set M_LocatorTo_ID
   *
   * @param M_LocatorTo_ID id
   */
  @Override
  public void setM_LocatorTo_ID(int M_LocatorTo_ID) {
    if (M_LocatorTo_ID < 0) throw new IllegalArgumentException("M_LocatorTo_ID is mandatory.");
    //      set to 0 explicitly to reset
    set_Value(COLUMNNAME_M_LocatorTo_ID, M_LocatorTo_ID);
  } //      M_LocatorTo_ID

    public String toString() {
    return Table_Name
        + "["
        + getId()
        + ", M_Product_ID="
        + getM_Product_ID()
        + ", M_ASI_ID="
        + getMAttributeSetInstance_ID()
        + ", M_ASITo_ID="
        + getMAttributeSetInstanceTo_ID()
        + ", M_Locator_ID="
        + getM_Locator_ID()
        + ", M_LocatorTo_ID="
        + getM_LocatorTo_ID()
        + "]";
  }
} //	MMovementLine
