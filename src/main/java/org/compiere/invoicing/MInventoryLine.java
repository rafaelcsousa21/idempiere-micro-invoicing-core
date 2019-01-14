package org.compiere.invoicing;

import kotliquery.Row;
import org.compiere.accounting.*;
import org.compiere.model.IDocLine;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.orm.MDocType;
import org.compiere.orm.Query;
import org.compiere.process.DocAction;
import org.compiere.util.Msg;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.getSQLValue;

/**
 * Physical Inventory Line Model
 *
 * @author Jorg Janke
 * @version $Id: MInventoryLine.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *     <li>BF [ 1817757 ] Error on saving MInventoryLine in a custom environment
 *     <li>BF [ 1722982 ] Error with inventory when you enter count qty in negative
 */
public class MInventoryLine extends X_M_InventoryLine implements IDocLine {
  /** */
  private static final long serialVersionUID = -3864175464877913555L;

  /**
   * Get Inventory Line with parameters
   *
   * @param inventory inventory
   * @param M_Locator_ID locator
   * @param M_Product_ID product
   * @param M_AttributeSetInstance_ID asi
   * @return line or null
   */
  public static MInventoryLine get(
      MInventory inventory, int M_Locator_ID, int M_Product_ID, int M_AttributeSetInstance_ID) {
    final String whereClause =
        "M_Inventory_ID=? AND M_Locator_ID=?"
            + " AND M_Product_ID=? AND M_AttributeSetInstance_ID=?";
    return new Query(
            inventory.getCtx(), I_M_InventoryLine.Table_Name, whereClause, null)
        .setParameters(inventory.getId(), M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID)
        .firstOnly();
  } //	get

  /**
   * ************************************************************************ Default Constructor
   *
   * @param ctx context
   * @param M_InventoryLine_ID line
   * @param trxName transaction
   */
  public MInventoryLine(Properties ctx, int M_InventoryLine_ID, String trxName) {
    super(ctx, M_InventoryLine_ID, trxName);
    if (M_InventoryLine_ID == 0) {
      //	setM_Inventory_ID (0);			//	Parent
      //	setM_InventoryLine_ID (0);		//	PK
      //	setM_Locator_ID (0);			//	FK
      setLine(0);
      //	setM_Product_ID (0);			//	FK
      setM_AttributeSetInstance_ID(0); // 	FK
      setInventoryType(X_M_InventoryLine.INVENTORYTYPE_InventoryDifference);
      setQtyBook(Env.ZERO);
      setQtyCount(Env.ZERO);
      setProcessed(false);
    }
  } //	MInventoryLine

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MInventoryLine(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MInventoryLine
  public MInventoryLine(Properties ctx, Row row) {
    super(ctx, row);
  } //	MInventoryLine

  /**
   * Detail Constructor. Locator/Product/AttributeSetInstance must be unique
   *
   * @param inventory parent
   * @param M_Locator_ID locator
   * @param M_Product_ID product
   * @param M_AttributeSetInstance_ID instance
   * @param QtyBook book value
   * @param QtyCount count value
   * @param QtyInternalUse internal use value
   */
  public MInventoryLine(
      MInventory inventory,
      int M_Locator_ID,
      int M_Product_ID,
      int M_AttributeSetInstance_ID,
      BigDecimal QtyBook,
      BigDecimal QtyCount,
      BigDecimal QtyInternalUse) {
    this(inventory.getCtx(), 0, null);
    if (inventory.getId() == 0) throw new IllegalArgumentException("Header not saved");
    m_parent = inventory;
    setM_Inventory_ID(inventory.getM_Inventory_ID()); // 	Parent
    setClientOrg(inventory. getClientId(), inventory. getOrgId());
    setM_Locator_ID(M_Locator_ID); // 	FK
    setM_Product_ID(M_Product_ID); // 	FK
    setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
    //
    if (QtyBook != null) setQtyBook(QtyBook);
    if (QtyCount != null && QtyCount.signum() != 0) setQtyCount(QtyCount);
    if (QtyInternalUse != null && QtyInternalUse.signum() != 0) setQtyInternalUse(QtyInternalUse);
    // m_isManualEntry = false;
  } //	MInventoryLine

  public MInventoryLine(
      MInventory inventory,
      int M_Locator_ID,
      int M_Product_ID,
      int M_AttributeSetInstance_ID,
      BigDecimal QtyBook,
      BigDecimal QtyCount) {
    this(inventory, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID, QtyBook, QtyCount, null);
  }

  /** Manually created */
  // private boolean 	m_isManualEntry = true;
  /** Parent */
  private MInventory m_parent = null;
  /** Product */
  private MProduct m_product = null;

  /**
   * Get Product
   *
   * @return product or null if not defined
   */
  public MProduct getProduct() {
    int M_Product_ID = getM_Product_ID();
    if (M_Product_ID == 0) return null;
    if (m_product != null && m_product.getM_Product_ID() != M_Product_ID)
      m_product = null; // 	reset
    if (m_product == null) m_product = MProduct.get(getCtx(), M_Product_ID);
    return m_product;
  } //	getProduct

  /**
   * Set Count Qty - enforce UOM
   *
   * @param QtyCount qty
   */
  @Override
  public void setQtyCount(BigDecimal QtyCount) {
    if (QtyCount != null) {
      MProduct product = getProduct();
      if (product != null) {
        int precision = product.getUOMPrecision();
        QtyCount = QtyCount.setScale(precision, BigDecimal.ROUND_HALF_UP);
      }
    }
    super.setQtyCount(QtyCount);
  } //	setQtyCount

  /**
   * Set Internal Use Qty - enforce UOM
   *
   * @param QtyInternalUse qty
   */
  @Override
  public void setQtyInternalUse(BigDecimal QtyInternalUse) {
    if (QtyInternalUse != null) {
      MProduct product = getProduct();
      if (product != null) {
        int precision = product.getUOMPrecision();
        QtyInternalUse = QtyInternalUse.setScale(precision, BigDecimal.ROUND_HALF_UP);
      }
    }
    super.setQtyInternalUse(QtyInternalUse);
  } //	setQtyInternalUse

  /**
   * Add to Description
   *
   * @param description text
   */
  public void addDescription(String description) {
    String desc = getDescription();
    if (desc == null) setDescription(description);
    else {
      StringBuilder msgd = new StringBuilder(desc).append(" | ").append(description);
      setDescription(msgd.toString());
    }
  } //	addDescription

  /**
   * Get Parent
   *
   * @param parent parent
   */
  protected void setParent(MInventory parent) {
    m_parent = parent;
  } //	setParent

  /**
   * Get Parent
   *
   * @return parent
   */
  public MInventory getParent() {
    if (m_parent == null) m_parent = new MInventory(getCtx(), getM_Inventory_ID(), null);
    return m_parent;
  } //	getParent

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("MInventoryLine[");
    sb.append(getId())
        .append("-M_Product_ID=")
        .append(getM_Product_ID())
        .append(",QtyCount=")
        .append(getQtyCount())
        .append(",QtyInternalUse=")
        .append(getQtyInternalUse())
        .append(",QtyBook=")
        .append(getQtyBook())
        .append(",M_AttributeSetInstance_ID=")
        .append(getMAttributeSetInstance_ID())
        .append("]");
    return sb.toString();
  } //	toString

  /**
   * Before Save
   *
   * @param newRecord new
   * @return true if can be saved
   */
  protected boolean beforeSave(boolean newRecord) {
    if (newRecord && getParent().isComplete()) {
      log.saveError("ParentComplete", Msg.translate(getCtx(), "M_InventoryLine"));
      return false;
    }
    /* IDEMPIERE-1770 - ASI validation must be moved to MInventory.prepareIt, saving a line without ASI is ok on draft
    if (m_isManualEntry)
    {
    	//	Product requires ASI
    	if (getMAttributeSetInstance_ID() == 0)
    	{
    		MProduct product = MProduct.get(getCtx(), getM_Product_ID());
    		if (product != null && product.isASIMandatory(isSOTrx()))
    		{
    			if(product.getAttributeSet()==null){
    				log.saveError("NoAttributeSet", product.getValue());
    				return false;
    			}
    			if (! product.getAttributeSet().excludeTableEntry(MInventoryLine.Table_ID, isSOTrx())) {
    				log.saveError("FillMandatory", Msg.getElement(getCtx(), COLUMNNAME_M_AttributeSetInstance_ID));
    				return false;
    			}
    		}
    	}	//	No ASI
    }	//	manual
    */

    //	Set Line No
    if (getLine() == 0) {
      String sql =
          "SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_InventoryLine WHERE M_Inventory_ID=?";
      int ii = getSQLValue(null, sql, getM_Inventory_ID());
      setLine(ii);
    }

    // Enforce QtyCount >= 0  - teo_sarca BF [ 1722982 ]
    // GlobalQSS -> reverting this change because of Bug 2904321 - Create Inventory Count List not
    // taking negative qty products
    /*
    if ( (!newRecord) && is_ValueChanged("QtyCount") && getQtyCount().signum() < 0)
    {
    	log.saveError("Warning", Msg.getElement(getCtx(), COLUMNNAME_QtyCount)+" < 0");
    	return false;
    }
    */
    //	Enforce Qty UOM
    if (newRecord || is_ValueChanged("QtyCount")) setQtyCount(getQtyCount());
    if (newRecord || is_ValueChanged("QtyInternalUse")) setQtyInternalUse(getQtyInternalUse());

    MDocType dt = MDocType.get(getCtx(), getParent().getC_DocType_ID());
    String docSubTypeInv = dt.getDocSubTypeInv();

    if (MDocType.DOCSUBTYPEINV_InternalUseInventory.equals(docSubTypeInv)) {

      // Internal Use Inventory validations
      if (!X_M_InventoryLine.INVENTORYTYPE_ChargeAccount.equals(getInventoryType()))
        setInventoryType(X_M_InventoryLine.INVENTORYTYPE_ChargeAccount);
      //
      if (getC_Charge_ID() == 0) {
        log.saveError("InternalUseNeedsCharge", "");
        return false;
      }
      // error if book or count are filled on an internal use inventory
      // i.e. coming from import or web services
      if (getQtyBook().signum() != 0) {
        log.saveError("Quantity", Msg.getElement(getCtx(), I_M_InventoryLine.COLUMNNAME_QtyBook));
        return false;
      }
      if (getQtyCount().signum() != 0) {
        log.saveError("Quantity", Msg.getElement(getCtx(), I_M_InventoryLine.COLUMNNAME_QtyCount));
        return false;
      }
      if (getQtyInternalUse().signum() == 0
          && !getParent().getDocAction().equals(DocAction.Companion.getACTION_Void())) {
        log.saveError(
            "FillMandatory", Msg.getElement(getCtx(), I_M_InventoryLine.COLUMNNAME_QtyInternalUse));
        return false;
      }

    } else if (MDocType.DOCSUBTYPEINV_PhysicalInventory.equals(docSubTypeInv)) {

      // Physical Inventory validations
      if (X_M_InventoryLine.INVENTORYTYPE_ChargeAccount.equals(getInventoryType())) {
        if (getC_Charge_ID() == 0) {
          log.saveError("FillMandatory", Msg.getElement(getCtx(), "C_Charge_ID"));
          return false;
        }
      } else if (getC_Charge_ID() != 0) {
        setC_Charge_ID(0);
      }
      if (getQtyInternalUse().signum() != 0) {
        log.saveError(
            "Quantity", Msg.getElement(getCtx(), I_M_InventoryLine.COLUMNNAME_QtyInternalUse));
        return false;
      }
    } else if (MDocType.DOCSUBTYPEINV_CostAdjustment.equals(docSubTypeInv)) {
      int M_ASI_ID = getMAttributeSetInstance_ID();
      MProduct product = new MProduct(getCtx(), getM_Product_ID(), null);
      MClient client = MClient.get(getCtx());
      MAcctSchema as = client.getAcctSchema();
      String costingLevel = product.getCostingLevel(as);
      if (MAcctSchema.COSTINGLEVEL_BatchLot.equals(costingLevel)) {
        if (M_ASI_ID == 0) {
          log.saveError(
              "FillMandatory",
              Msg.getElement(getCtx(), I_M_InventoryLine.COLUMNNAME_M_AttributeSetInstance_ID));
          return false;
        }
      }

      String costingMethod = getParent().getCostingMethod();
      int AD_Org_ID =  getOrgId();
      MCost cost = product.getCostingRecord(as, AD_Org_ID, M_ASI_ID, costingMethod);
      if (cost == null) {
        if (!MCostElement.COSTINGMETHOD_StandardCosting.equals(costingMethod)) {
          log.saveError("NoCostingRecord", "");
          return false;
        }
      }
      setM_Locator_ID(0);
    } else {
      log.saveError("Error", "Document inventory subtype not configured, cannot complete");
      return false;
    }

    //	Set AD_Org to parent if not charge
    if (getC_Charge_ID() == 0) setAD_Org_ID(getParent(). getOrgId());

    return true;
  } //	beforeSave

  /**
   * After Save
   *
   * @param newRecord new
   * @param success success
   * @return true
   */
  // protected boolean afterSave (boolean newRecord, boolean success)
  // {
  //	if (!success)
  //		return false;
  //
  //	//	Create MA
  //	//if (newRecord && success
  //	//	&& m_isManualEntry && getMAttributeSetInstance_ID() == 0)
  //	//	createMA();
  //	return true;
  // }	//	afterSave

  /** Create Material Allocations for new Instances */
  /*private void createMA()
  {
  	MStorageOnHand[] storages = MStorageOnHand.getAll(getCtx(), getM_Product_ID(),
  		getM_Locator_ID(), null);
  	boolean allZeroASI = true;
  	for (int i = 0; i < storages.length; i++)
  	{
  		if (storages[i].getMAttributeSetInstance_ID() != 0)
  		{
  			allZeroASI = false;
  			break;
  		}
  	}
  	if (allZeroASI)
  		return;

  	MInventoryLineMA ma = null;
  	BigDecimal sum = Env.ZERO;
  	for (int i = 0; i < storages.length; i++)
  	{
  		MStorageOnHand storage = storages[i];
  		if (storage.getQtyOnHand().signum() == 0)
  			continue;
  		if (ma != null
  			&& ma.getMAttributeSetInstance_ID() == storage.getMAttributeSetInstance_ID())
  			ma.setMovementQty(ma.getMovementQty().add(storage.getQtyOnHand()));
  		else
  			ma = new MInventoryLineMA (this,
  				storage.getMAttributeSetInstance_ID(), storage.getQtyOnHand());
  		if (!ma.save())
  			;
  		sum = sum.add(storage.getQtyOnHand());
  	}
  	if (sum.compareTo(getQtyBook()) != 0)
  	{
  		log.warning("QtyBook=" + getQtyBook() + " corrected to Sum of MA=" + sum);
  		setQtyBook(sum);
  	}
  }	//	createMA*/

  /**
   * Is Internal Use Inventory
   *
   * @return true if is internal use inventory
   */
  public boolean isInternalUseInventory() {
    //  IDEMPIERE-675
    MDocType dt = MDocType.get(getCtx(), getParent().getC_DocType_ID());
    String docSubTypeInv = dt.getDocSubTypeInv();
    return (MDocType.DOCSUBTYPEINV_InternalUseInventory.equals(docSubTypeInv));
  }

  /**
   * Get Movement Qty (absolute value)
   * <li>negative value means outgoing trx
   * <li>positive value means incoming trx
   *
   * @return movement qty
   */
  public BigDecimal getMovementQty() {
    if (isInternalUseInventory()) {
      return getQtyInternalUse().negate();
    } else {
      return getQtyCount().subtract(getQtyBook());
    }
  }

  /** @return true if is an outgoing transaction */
  public boolean isSOTrx() {
    return getMovementQty().signum() < 0;
  }
} //	MInventoryLine
