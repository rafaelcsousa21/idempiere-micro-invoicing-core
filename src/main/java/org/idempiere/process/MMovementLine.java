package org.idempiere.process;

import kotliquery.Row;
import org.compiere.accounting.MProduct;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;

import static software.hsharp.core.util.DBKt.getSQLValue;

public class MMovementLine extends X_M_MovementLine {
    /**
     *
     */
    private static final long serialVersionUID = -4078367839033015886L;
    /**
     * Parent
     */
    private MMovement m_parent = null;

    /**
     * Standard Cosntructor
     *
     * @param M_MovementLine_ID id
     */
    public MMovementLine(int M_MovementLine_ID) {
        super(M_MovementLine_ID);
        if (M_MovementLine_ID == 0) {
            setAttributeSetInstanceId(0); // 	ID
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
     */
    public MMovementLine(Row row) {
        super(row);
    } //	MMovementLine

    /**
     * Parent constructor
     *
     * @param parent parent
     */
    public MMovementLine(MMovement parent) {
        this(0);
        setClientOrg(parent);
        setMovementId(parent.getMovementId());
    } //	MMovementLine

    /**
     * Get AttributeSetInstance To
     *
     * @return ASI
     */
    @Override
    public int getMAttributeSetInstanceToId() {
        int M_AttributeSetInstanceTo_ID = super.getMAttributeSetInstanceToId();
        if (M_AttributeSetInstanceTo_ID == 0 && (getLocatorId() == getLocatorToId()))
            M_AttributeSetInstanceTo_ID = super.getAttributeSetInstanceId();
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
        if (getProductId() != 0) return MProduct.get(getProductId());
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

    /**
     * get Parent
     *
     * @return Parent Movement
     */
    public MMovement getParent() {
        if (m_parent == null) m_parent = new MMovement(getMovementId());
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
            log.saveError("ParentComplete", MsgKt.translate("M_MovementLine"));
            return false;
        }
        //	Set Line No
        if (getLine() == 0) {
            String sql =
                    "SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_MovementLine WHERE M_Movement_ID=?";
            int ii = getSQLValue(sql, getMovementId());
            setLine(ii);
        }

        // either movement between locator or movement between lot
        if (getLocatorId() == getLocatorToId()
                && getAttributeSetInstanceId() == getMAttributeSetInstanceToId()) {
            log.saveError(
                    "Error",
                    MsgKt.parseTranslation(
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
                log.saveError("FillMandatory", MsgKt.getElementTranslation("MovementQty"));
                return false;
            }
        }

        //	Qty Precision
        if (newRecord || isValueChanged(COLUMNNAME_MovementQty)) setMovementQty(getMovementQty());

        //      Mandatory Instance
    /* IDEMPIERE-1770 - ASI validation must be moved to MMovement.prepareIt, saving a line without ASI is ok on draft
    MProduct product = getProduct();
    if (getAttributeSetInstanceId() == 0) {
    	if (product != null && product.isASIMandatory(true)) {
    		if (product.getAttributeSet()==null) {
    			log.saveError("NoAttributeSet", product.getValue());
    			return false;
    		}
    		if (! product.getAttributeSet().excludeTableEntry(MMovementLine.Table_ID, true)) {  // outgoing
    			log.saveError("FillMandatory", MsgKt.getElementTranslation(COLUMNNAME_M_AttributeSetInstance_ID));
    			return false;
    		}
    	}
    }
    */
        if (getMAttributeSetInstanceToId() == 0) {
            // instance id default to same for movement between locator
            if (getLocatorId() != getLocatorToId()) {
                if (getAttributeSetInstanceId() != 0) // set to from
                    setAttributeSetInstanceToId(getAttributeSetInstanceId());
            }

      /* IDEMPIERE-1770 - ASI validation must be moved to MMovement.prepareIt, saving a line without ASI is ok on draft
      if (product != null && product.isASIMandatory(false) && getMAttributeSetInstanceToId() == 0)
      {
      	if (product.getAttributeSet()==null) {
      		log.saveError("NoAttributeSet", product.getValue());
      		return false;
      	}
      	if (! product.getAttributeSet().excludeTableEntry(MMovementLine.Table_ID, false)) { // incoming
      		log.saveError("FillMandatory", MsgKt.getElementTranslation(COLUMNNAME_M_AttributeSetInstanceTo_ID));
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
    public void setLocatorId(int M_Locator_ID) {
        if (M_Locator_ID < 0) throw new IllegalArgumentException("M_Locator_ID is mandatory.");
        //      set to 0 explicitly to reset
        setValue(COLUMNNAME_M_Locator_ID, M_Locator_ID);
    } //      setLocatorId

    /**
     * Set M_LocatorTo_ID
     *
     * @param M_LocatorTo_ID id
     */
    @Override
    public void setLocatorToId(int M_LocatorTo_ID) {
        if (M_LocatorTo_ID < 0) throw new IllegalArgumentException("M_LocatorTo_ID is mandatory.");
        //      set to 0 explicitly to reset
        setValue(COLUMNNAME_M_LocatorTo_ID, M_LocatorTo_ID);
    } //      M_LocatorTo_ID

    public String toString() {
        return Table_Name
                + "["
                + getId()
                + ", M_Product_ID="
                + getProductId()
                + ", M_ASI_ID="
                + getAttributeSetInstanceId()
                + ", M_ASITo_ID="
                + getMAttributeSetInstanceToId()
                + ", M_Locator_ID="
                + getLocatorId()
                + ", M_LocatorTo_ID="
                + getLocatorToId()
                + "]";
    }
} //	MMovementLine
