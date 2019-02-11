package org.idempiere.process;

import org.compiere.accounting.MPeriod;
import org.compiere.accounting.MStorageOnHand;
import org.compiere.accounting.NegativeInventoryDisallowedException;
import org.compiere.crm.MBPartner;
import org.compiere.docengine.DocumentEngine;
import org.compiere.model.*;
import org.compiere.orm.MDocType;
import org.compiere.orm.Query;
import org.compiere.process.CompleteActionResult;
import org.compiere.process.DocAction;
import org.compiere.production.MLocator;
import org.compiere.production.MProject;
import org.compiere.util.Msg;
import org.compiere.validation.ModelValidationEngine;
import org.compiere.validation.ModelValidator;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Util;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.*;

public class MDDOrder extends X_DD_Order implements DocAction, IPODoc {
  /** */
  private static final long serialVersionUID = -5997157712614274458L;

    /**
   * ************************************************************************ Default Constructor
   *
   * @param ctx context
   * @param DD_Order_ID order to load, (0 create new order)
   * @param trxName trx name
   */
  public MDDOrder(Properties ctx, int DD_Order_ID, String trxName) {
    super(ctx, DD_Order_ID, trxName);
    //  New
    if (DD_Order_ID == 0) {
      setDocStatus(DOCSTATUS_Drafted);
      setDocAction(DOCACTION_Prepare);
      //
      setDeliveryRule(DELIVERYRULE_Availability);
      setFreightCostRule(FREIGHTCOSTRULE_FreightIncluded);
      setPriorityRule(PRIORITYRULE_Medium);
      setDeliveryViaRule(DELIVERYVIARULE_Pickup);
      //
      setIsSelected(false);
      setIsSOTrx(true);
      setIsDropShip(false);
      setSendEMail(false);
      //
      setIsApproved(false);
      setIsPrinted(false);
      setIsDelivered(false);
      //
      super.setProcessed(false);
      setProcessing(false);
      setPosted(false);

      setDatePromised(new Timestamp(System.currentTimeMillis()));
      setDateOrdered(new Timestamp(System.currentTimeMillis()));

      setFreightAmt(Env.ZERO);
      setChargeAmt(Env.ZERO);
    }
  } //	MDDOrder

  /**
   * ************************************************************************ Project Constructor
   *
   * @param project Project to create Order from
   * @param IsSOTrx sales order
   * @param DocSubTypeSO if SO DocType Target (default DocSubTypeSO_OnCredit)
   */
  public MDDOrder(MProject project, boolean IsSOTrx, String DocSubTypeSO) {
    this(project.getCtx(), 0, null);
    setADClientID(project.getClientId());
    setAD_Org_ID(project.getOrgId());
    setC_Campaign_ID(project.getC_Campaign_ID());
    setSalesRep_ID(project.getSalesRep_ID());
    //
    setC_Project_ID(project.getC_Project_ID());
    setDescription(project.getName());
    Timestamp ts = project.getDateContract();
    if (ts != null) setDateOrdered(ts);
    ts = project.getDateFinish();
    if (ts != null) setDatePromised(ts);
    //
    setC_BPartner_ID(project.getC_BPartner_ID());
    setC_BPartner_Location_ID(project.getC_BPartner_Location_ID());
    setAD_User_ID(project.getAD_User_ID());
    //
    setM_Warehouse_ID(project.getM_Warehouse_ID());
    //
    setIsSOTrx(IsSOTrx);
  } //	MDDOrder

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set record
   * @param trxName transaction
   */
  public MDDOrder(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MDDOrder

  /** Order Lines */
  private MDDOrderLine[] m_lines = null;

  /** Force Creation of order */
  @SuppressWarnings("unused")
  private boolean m_forceCreation = false;

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
   * Set Business Partner Defaults & Details. SOTrx should be set.
   *
   * @param bp business partner
   */
  public void setBPartner(I_C_BPartner bp) {
    if (bp == null) return;

    setC_BPartner_ID(bp.getC_BPartner_ID());
    //	Defaults Payment Term
    int ii = 0;
    if (isSOTrx()) ii = bp.getC_PaymentTerm_ID();
    else ii = bp.getPO_PaymentTerm_ID();

    //	Default Price List
    if (isSOTrx()) ii = bp.getM_PriceList_ID();
    else ii = bp.getPO_PriceList_ID();
    //	Default Delivery/Via Rule
    String ss = bp.getDeliveryRule();
    if (ss != null) setDeliveryRule(ss);
    ss = bp.getDeliveryViaRule();
    if (ss != null) setDeliveryViaRule(ss);
    //	Default Invoice/Payment Rule
    ss = bp.getInvoiceRule();

    if (getSalesRep_ID() == 0) {
      ii = Env.getAD_User_ID(getCtx());
      if (ii != 0) setSalesRep_ID(ii);
    }

    //	Set Locations
    I_C_BPartner_Location[] locs = bp.getLocations();
    if (locs != null) {
      for (int i = 0; i < locs.length; i++) {
        if (locs[i].isShipTo()) {
          super.setC_BPartner_Location_ID(locs[i].getC_BPartner_Location_ID());
        }
      }
      //	set to first
      if (getC_BPartner_Location_ID() == 0 && locs.length > 0) {
        super.setC_BPartner_Location_ID(locs[0].getC_BPartner_Location_ID());
      }
    }
    if (getC_BPartner_Location_ID() == 0) {
      log.log(Level.SEVERE, "MDDOrder.setBPartner - Has no Ship To Address: " + bp);
    }

    //	Set Contact
    I_AD_User[] contacts = bp.getContacts();
    if (contacts != null && contacts.length == 1) {
      setAD_User_ID(contacts[0].getAD_User_ID());
    }
  } //	setBPartner

    /**
   * ************************************************************************ String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuffer sb =
        new StringBuffer("MDDOrder[")
            .append(getId())
            .append("-")
            .append(getDocumentNo())
            .append(",IsSOTrx=")
            .append(isSOTrx())
            .append(",C_DocType_ID=")
            .append(getC_DocType_ID())
            .append("]");
    return sb.toString();
  } //	toString

  /**
   * Get Document Info
   *
   * @return document info (untranslated)
   */
  public String getDocumentInfo() {
    MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
    return dt.getNameTrl() + " " + getDocumentNo();
  } //	getDocumentInfo

    /**
   * ************************************************************************ Get Lines of Order
   *
   * @param whereClause where clause or null (starting with AND)
   * @param orderClause order clause
   * @return lines
   */
  public MDDOrderLine[] getLines(String whereClause, String orderClause) {
    StringBuffer whereClauseFinal =
        new StringBuffer(MDDOrderLine.COLUMNNAME_DD_Order_ID).append("=?");
    if (!Util.isEmpty(whereClause, true))
      whereClauseFinal.append(" AND (").append(whereClause).append(")");
    //
    List<MDDOrderLine> list =
        new Query(getCtx(), I_DD_OrderLine.Table_Name, whereClauseFinal.toString(), null)
            .setParameters(getDD_Order_ID())
            .setOrderBy(orderClause)
            .list();
    return list.toArray(new MDDOrderLine[list.size()]);
  } //	getLines

  /**
   * Get Lines of Order
   *
   * @param requery requery
   * @param orderBy optional order by column
   * @return lines
   */
  public MDDOrderLine[] getLines(boolean requery, String orderBy) {
    if (m_lines != null && !requery) {
      set_TrxName(m_lines, null);
      return m_lines;
    }
    //
    String orderClause = "";
    if (orderBy != null && orderBy.length() > 0) orderClause += orderBy;
    else orderClause += "Line";
    m_lines = getLines(null, orderClause);
    return m_lines;
  } //	getLines

  /**
   * Get Lines of Order. (useb by web store)
   *
   * @return lines
   */
  public MDDOrderLine[] getLines() {
    return getLines(false, null);
  } //	getLines

    /**
   * Set DocAction
   *
   * @param DocAction doc action
   */
  public void setDocAction(String DocAction) {
    setDocAction(DocAction, false);
  } //	setDocAction

  /**
   * Set Processed. Propergate to Lines/Taxes
   *
   * @param processed processed
   */
  public void setProcessed(boolean processed) {
    super.setProcessed(processed);
    if (getId() == 0) return;
    String set =
        "SET Processed='" + (processed ? "Y" : "N") + "' WHERE DD_Order_ID=" + getDD_Order_ID();
    int noLine = executeUpdate("UPDATE DD_OrderLine " + set);
    m_lines = null;
    if (log.isLoggable(Level.FINE)) log.fine("setProcessed - " + processed + " - Lines=" + noLine);
  } //	setProcessed

  /**
   * ************************************************************************ Before Save
   *
   * @param newRecord new
   * @return save
   */
  protected boolean beforeSave(boolean newRecord) {
    //	Client/Org Check
    if (getOrgId() == 0) {
      int context_AD_Org_ID = Env.getOrgId(getCtx());
      if (context_AD_Org_ID != 0) {
        setAD_Org_ID(context_AD_Org_ID);
        log.warning("Changed Org to Context=" + context_AD_Org_ID);
      }
    }
    if (getClientId() == 0) {
      m_processMsg = "AD_Client_ID = 0";
      return false;
    }

    //	New Record Doc Type - make sure DocType set to 0
    if (newRecord && getC_DocType_ID() == 0) setC_DocType_ID(0);

    //	Default Warehouse
    if (getM_Warehouse_ID() == 0) {
      int ii = Env.getContextAsInt(getCtx(), "#M_Warehouse_ID");
      if (ii != 0) setM_Warehouse_ID(ii);
      else {
        log.saveError("FillMandatory", Msg.getElement(getCtx(), "M_Warehouse_ID"));
        return false;
      }
    }
    //	Reservations in Warehouse
    if (!newRecord && is_ValueChanged("M_Warehouse_ID")) {
      MDDOrderLine[] lines = getLines(false, null);
      for (int i = 0; i < lines.length; i++) {
        if (!lines[i].canChangeWarehouse()) return false;
      }
    }

    //	No Partner Info - set Template
    if (getC_BPartner_ID() == 0) setBPartner(MBPartner.getTemplate(getCtx(), getClientId()));
    if (getC_BPartner_Location_ID() == 0)
      setBPartner(new MBPartner(getCtx(), getC_BPartner_ID(), null));

    //	Default Sales Rep
    if (getSalesRep_ID() == 0) {
      int ii = Env.getContextAsInt(getCtx(), "#AD_User_ID");
      if (ii != 0) setSalesRep_ID(ii);
    }

    return true;
  } //	beforeSave

  /**
   * After Save
   *
   * @param newRecord new
   * @param success success
   * @return true if can be saved
   */
  protected boolean afterSave(boolean newRecord, boolean success) {
    if (!success || newRecord) return success;

    //	Propagate Description changes
    if (is_ValueChanged("Description") || is_ValueChanged("POReference")) {
      String sql =
          "UPDATE M_Movement i"
              + " SET (Description,POReference)="
              + "(SELECT Description,POReference "
              + "FROM DD_Order o WHERE i.DD_Order_ID=o.DD_Order_ID) "
              + "WHERE DocStatus NOT IN ('RE','CL') AND DD_Order_ID="
              + getDD_Order_ID();
      int no = executeUpdate(sql);
      if (log.isLoggable(Level.FINE)) log.fine("Description -> #" + no);
    }

    //	Sync Lines
    afterSaveSync("AD_Org_ID");
    afterSaveSync("C_BPartner_ID");
    afterSaveSync("C_BPartner_Location_ID");
    afterSaveSync("DateOrdered");
    afterSaveSync("DatePromised");
    afterSaveSync("M_Shipper_ID");
    //
    return true;
  } //	afterSave

  private void afterSaveSync(String columnName) {
    if (is_ValueChanged(columnName)) {
      final String whereClause = I_DD_Order.COLUMNNAME_DD_Order_ID + "=?";
      List<MDDOrderLine> lines =
          new Query(getCtx(), I_DD_OrderLine.Table_Name, whereClause, null)
              .setParameters(getDD_Order_ID())
              .list();

      for (MDDOrderLine line : lines) {
        line.set_ValueOfColumn(columnName, get_Value(columnName));
        line.saveEx();
        if (log.isLoggable(Level.FINE))
          log.fine(columnName + " Lines -> #" + get_Value(columnName));
      }
    }
  } //	afterSaveSync

  /**
   * Set DocAction
   *
   * @param DocAction doc oction
   * @param forceCreation force creation
   */
  public void setDocAction(String DocAction, boolean forceCreation) {
    super.setDocAction(DocAction);
    m_forceCreation = forceCreation;
  } //	setDocAction

  /**
   * Before Delete
   *
   * @return true of it can be deleted
   */
  protected boolean beforeDelete() {
    if (isProcessed()) return false;

    getLines();
    for (int i = 0; i < m_lines.length; i++) {
      m_lines[i].delete(true);
    }
    return true;
  } //	beforeDelete

  /**
   * ************************************************************************ Process document
   *
   * @param processAction document action
   * @return true if performed
   */
  public boolean processIt(String processAction) {
    m_processMsg = null;
    DocumentEngine engine = new DocumentEngine(this, getDocStatus());
    return engine.processIt(processAction, getDocAction());
  } //	processIt

  /** Process Message */
  private String m_processMsg = null;
  /** Just Prepared Flag */
  private boolean m_justPrepared = false;

  /**
   * Unlock Document.
   *
   * @return true if success
   */
  public boolean unlockIt() {
    if (log.isLoggable(Level.INFO)) log.info("unlockIt - " + toString());
    setProcessing(false);
    return true;
  } //	unlockIt

  /**
   * Invalidate Document
   *
   * @return true if success
   */
  public boolean invalidateIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    setDocAction(DOCACTION_Prepare);
    return true;
  } //	invalidateIt

  /**
   * ************************************************************************ Prepare Document
   *
   * @return new status (In Progress or Invalid)
   */
  public String prepareIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
    if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();
    MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

    //	Std Period open?
    if (!MPeriod.isOpen(getCtx(), getDateOrdered(), dt.getDocBaseType(), getOrgId())) {
      m_processMsg = "@PeriodClosed@";
      return DocAction.Companion.getSTATUS_Invalid();
    }

    //	Lines
    MDDOrderLine[] lines = getLines(true, "M_Product_ID");
    if (lines.length == 0) {
      m_processMsg = "@NoLines@";
      return DocAction.Companion.getSTATUS_Invalid();
    }

    // Bug 1564431
    if (getDeliveryRule() != null && getDeliveryRule().equals(DELIVERYRULE_CompleteOrder)) {
      for (int i = 0; i < lines.length; i++) {
        MDDOrderLine line = lines[i];
        I_M_Product product = line.getProduct();
        if (product != null && product.isExcludeAutoDelivery()) {
          m_processMsg = "@M_Product_ID@ " + product.getValue() + " @IsExcludeAutoDelivery@";
          return DocAction.Companion.getSTATUS_Invalid();
        }
      }
    }

    //	Mandatory Product Attribute Set Instance
    String mandatoryType = "='Y'"; // 	IN ('Y','S')
    String sql =
        "SELECT COUNT(*) "
            + "FROM DD_OrderLine ol"
            + " INNER JOIN M_Product p ON (ol.M_Product_ID=p.M_Product_ID)"
            + " INNER JOIN M_AttributeSet pas ON (p.M_AttributeSet_ID=pas.M_AttributeSet_ID) "
            + "WHERE pas.MandatoryType"
            + mandatoryType
            + " AND ol.M_AttributeSetInstance_ID IS NULL"
            + " AND ol.DD_Order_ID=?";
    int no = getSQLValue(sql, getDD_Order_ID());
    if (no != 0) {
      m_processMsg = "@LinesWithoutProductAttribute@ (" + no + ")";
      return DocAction.Companion.getSTATUS_Invalid();
    }

    reserveStock(lines);

    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
    if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();

    m_justPrepared = true;
    return DocAction.Companion.getSTATUS_InProgress();
  } //	prepareIt

  /**
   * Reserve Inventory. Counterpart: MMovement.completeIt()
   *
   * @param lines distribution order lines (ordered by M_Product_ID for deadlock prevention)
   * @return true if (un) reserved
   */
  public void reserveStock(MDDOrderLine[] lines) {
    BigDecimal Volume = Env.ZERO;
    BigDecimal Weight = Env.ZERO;

    StringBuilder errors = new StringBuilder();
    //	Always check and (un) Reserve Inventory
    for (MDDOrderLine line : lines) {
      MLocator locator_from = MLocator.get(getCtx(), line.getM_Locator_ID());
      MLocator locator_to = MLocator.get(getCtx(), line.getM_LocatorTo_ID());
      BigDecimal reserved_ordered =
          line.getQtyOrdered().subtract(line.getQtyReserved()).subtract(line.getQtyDelivered());
      if (reserved_ordered.signum() == 0) {
        I_M_Product product = line.getProduct();
        if (product != null) {
          Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
          Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
        }
        continue;
      }

      if (log.isLoggable(Level.FINE))
        log.fine(
            "Line="
                + line.getLine()
                + " - Ordered="
                + line.getQtyOrdered()
                + ",Reserved="
                + line.getQtyReserved()
                + ",Delivered="
                + line.getQtyDelivered());

      //	Check Product - Stocked and Item
      I_M_Product product = line.getProduct();
      if (product != null) {
        try {
          if (product.isStocked()) {
            //	Update Storage
            if (!MStorageOnHand.add(
                getCtx(),
                locator_to.getM_Warehouse_ID(),
                locator_to.getM_Locator_ID(),
                line.getM_Product_ID(),
                line.getMAttributeSetInstance_ID(),
                Env.ZERO,
                null,
                null)) {
              throw new AdempiereException("!MStorageOnHand1");
            }

            if (!MStorageOnHand.add(
                getCtx(),
                locator_from.getM_Warehouse_ID(),
                locator_from.getM_Locator_ID(),
                line.getM_Product_ID(),
                line.getMAttributeSetInstanceTo_ID(),
                Env.ZERO,
                null,
                null)) {
              throw new AdempiereException("!MStorageOnHand2");
            }
          } //	stockec
          //	update line
          line.setQtyReserved(line.getQtyReserved().add(reserved_ordered));
          line.saveEx();
          //
          Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
          Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
        } catch (NegativeInventoryDisallowedException e) {
          log.severe(e.getMessage());
          errors
              .append(Msg.getElement(getCtx(), "Line"))
              .append(" ")
              .append(line.getLine())
              .append(": ");
          errors.append(e.getMessage()).append("\n");
        }
      } //	product
    } //	reverse inventory

    if (errors.toString().length() > 0) throw new AdempiereException(errors.toString());

    setVolume(Volume);
    setWeight(Weight);
  } //	reserveStock

  /**
   * Approve Document
   *
   * @return true if success
   */
  public boolean approveIt() {
    if (log.isLoggable(Level.INFO)) log.info("approveIt - " + toString());
    setIsApproved(true);
    return true;
  } //	approveIt

  /**
   * Reject Approval
   *
   * @return true if success
   */
  public boolean rejectIt() {
    if (log.isLoggable(Level.INFO)) log.info("rejectIt - " + toString());
    setIsApproved(false);
    return true;
  } //	rejectIt

  /**
   * ************************************************************************ Complete Document
   *
   * @return new status (Complete, In Progress, Invalid, Waiting ..)
   */
  public CompleteActionResult completeIt() {
    @SuppressWarnings("unused")
    MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

    //	Just prepare
    if (DOCACTION_Prepare.equals(getDocAction())) {
      setProcessed(false);
      return new CompleteActionResult(DocAction.Companion.getSTATUS_InProgress());
    }

    //	Re-Check
    if (!m_justPrepared) {
      String status = prepareIt();
      m_justPrepared = false;
      if (!DocAction.Companion.getSTATUS_InProgress().equals(status))
        return new CompleteActionResult(status);
    }

    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
    if (m_processMsg != null)
      return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());

    //	Implicit Approval
    if (!isApproved()) approveIt();
    getLines(true, null);
    if (log.isLoggable(Level.INFO)) log.info(toString());
    StringBuilder info = new StringBuilder();
    String valid =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
    if (valid != null) {
      if (info.length() > 0) info.append(" - ");
      info.append(valid);
      m_processMsg = info.toString();
      return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
    }

    setProcessed(true);
    m_processMsg = info.toString();
    //
    setDocAction(DOCACTION_Close);
    return new CompleteActionResult(DocAction.Companion.getSTATUS_Completed());
  } //	completeIt

  /**
   * Void Document. Set Qtys to 0 - Sales: reverse all documents
   *
   * @return true if success
   */
  public boolean voidIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    // Before Void
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
    if (m_processMsg != null) return false;

    MDDOrderLine[] lines = getLines(true, "M_Product_ID");
    for (int i = 0; i < lines.length; i++) {
      MDDOrderLine line = lines[i];
      BigDecimal old = line.getQtyOrdered();
      if (old.signum() != 0) {
        line.addDescription(Msg.getMsg(getCtx(), "Voided") + " (" + old + ")");
        line.saveEx(null);
      }
    }
    addDescription(Msg.getMsg(getCtx(), "Voided"));
    //	Clear Reservations
    reserveStock(lines);
    // After Void
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
    if (m_processMsg != null) return false;

    setProcessed(true);
    setDocAction(DOCACTION_None);
    return true;
  } //	voidIt

  /**
   * Create Shipment/Invoice Reversals
   *
   * @return true if success
   */
  /*
  private boolean createReversals()
  {
  	//	Cancel only Sales
  	if (!isSOTrx())
  		return true;

  	if (log.isLoggable(Level.INFO)) log.info("createReversals");
  	StringBuffer info = new StringBuffer();

  	//	Reverse All *Shipments*
  	info.append("@M_InOut_ID@:");
  	MInOut[] shipments = getShipments();
  	for (int i = 0; i < shipments.length; i++)
  	{
  		MInOut ship = shipments[i];
  		//	if closed - ignore
  		if (MInOut.DOCSTATUS_Closed.equals(ship.getDocStatus())
  			|| MInOut.DOCSTATUS_Reversed.equals(ship.getDocStatus())
  			|| MInOut.DOCSTATUS_Voided.equals(ship.getDocStatus()) )
  			continue;
  		ship.set_TrxName(null);

  		//	If not completed - void - otherwise reverse it
  		if (!MInOut.DOCSTATUS_Completed.equals(ship.getDocStatus()))
  		{
  			if (ship.voidIt())
  				ship.setDocStatus(MInOut.DOCSTATUS_Voided);
  		}
  		else if (ship.reverseCorrectIt())	//	completed shipment
  		{
  			ship.setDocStatus(MInOut.DOCSTATUS_Reversed);
  			info.append(" ").append(ship.getDocumentNo());
  		}
  		else
  		{
  			m_processMsg = "Could not reverse Shipment " + ship;
  			return false;
  		}
  		ship.setDocAction(MInOut.DOCACTION_None);
  		ship.save(null);
  	}	//	for all shipments

  	//	Reverse All *Invoices*
  	info.append(" - @C_Invoice_ID@:");
  	MInvoice[] invoices = getInvoices();
  	for (int i = 0; i < invoices.length; i++)
  	{
  		MInvoice invoice = invoices[i];
  		//	if closed - ignore
  		if (MInvoice.DOCSTATUS_Closed.equals(invoice.getDocStatus())
  			|| MInvoice.DOCSTATUS_Reversed.equals(invoice.getDocStatus())
  			|| MInvoice.DOCSTATUS_Voided.equals(invoice.getDocStatus()) )
  			continue;
  		invoice.set_TrxName(null);

  		//	If not completed - void - otherwise reverse it
  		if (!MInvoice.DOCSTATUS_Completed.equals(invoice.getDocStatus()))
  		{
  			if (invoice.voidIt())
  				invoice.setDocStatus(MInvoice.DOCSTATUS_Voided);
  		}
  		else if (invoice.reverseCorrectIt())	//	completed invoice
  		{
  			invoice.setDocStatus(MInvoice.DOCSTATUS_Reversed);
  			info.append(" ").append(invoice.getDocumentNo());
  		}
  		else
  		{
  			m_processMsg = "Could not reverse Invoice " + invoice;
  			return false;
  		}
  		invoice.setDocAction(MInvoice.DOCACTION_None);
  		invoice.save(null);
  	}	//	for all shipments

  	m_processMsg = info.toString();
  	return true;
  }	//	createReversals
  */

  /**
   * Close Document. Cancel not delivered Qunatities
   *
   * @return true if success
   */
  public boolean closeIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    // Before Close
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
    if (m_processMsg != null) return false;

    //	Close Not delivered Qty - SO/PO
    MDDOrderLine[] lines = getLines(true, "M_Product_ID");
    for (int i = 0; i < lines.length; i++) {
      MDDOrderLine line = lines[i];
      BigDecimal old = line.getQtyOrdered();
      if (old.compareTo(line.getQtyDelivered()) != 0) {
        line.setQtyOrdered(line.getQtyDelivered());
        //	QtyEntered unchanged
        line.addDescription("Close (" + old + ")");
        line.saveEx(null);
      }
    }
    //	Clear Reservations
    reserveStock(lines);

    setProcessed(true);
    setDocAction(DOCACTION_None);
    // After Close
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
    if (m_processMsg != null) return false;
    return true;
  } //	closeIt

  /**
   * Reverse Correction - same void
   *
   * @return true if success
   */
  public boolean reverseCorrectIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    // Before reverseCorrect
    m_processMsg =
        ModelValidationEngine.get()
            .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
    if (m_processMsg != null) return false;

    // After reverseCorrect
    m_processMsg =
        ModelValidationEngine.get()
            .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
    if (m_processMsg != null) return false;

    return voidIt();
  } //	reverseCorrectionIt

  /**
   * Reverse Accrual - none
   *
   * @return false
   */
  public boolean reverseAccrualIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    // Before reverseAccrual
    m_processMsg =
        ModelValidationEngine.get()
            .fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
    if (m_processMsg != null) return false;

    // After reverseAccrual
    m_processMsg =
        ModelValidationEngine.get()
            .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
    if (m_processMsg != null) return false;

    return false;
  } //	reverseAccrualIt

  /**
   * Re-activate.
   *
   * @return true if success
   */
  public boolean reActivateIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    // Before reActivate
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
    if (m_processMsg != null) return false;
    // After reActivate
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
    if (m_processMsg != null) return false;

    setDocAction(DOCACTION_Complete);
    setProcessed(false);
    return true;
  } //	reActivateIt

  /**
   * *********************************************************************** Get Summary
   *
   * @return Summary of Document
   */
  public String getSummary() {
    StringBuilder sb = new StringBuilder();
    sb.append(getDocumentNo());

    if (m_lines != null) sb.append(" (#").append(m_lines.length).append(")");
    //	 - Description
    if (getDescription() != null && getDescription().length() > 0)
      sb.append(" - ").append(getDescription());
    return sb.toString();
  } //	getSummary

  /**
   * Get Process Message
   *
   * @return clear text error message
   */
  public String getProcessMsg() {
    return m_processMsg;
  } //	getProcessMsg

  /**
   * Get Document Owner (Responsible)
   *
   * @return AD_User_ID
   */
  public int getDoc_User_ID() {
    return getSalesRep_ID();
  } //	getDoc_User_ID

  public BigDecimal getApprovalAmt() {
    // TODO Auto-generated method stub
    return null;
  }

  public int getC_Currency_ID() {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * Document Status is Complete or Closed
   *
   * @return true if CO, CL or RE
   */
  public boolean isComplete() {
    String ds = getDocStatus();
    return DOCSTATUS_Completed.equals(ds)
        || DOCSTATUS_Closed.equals(ds)
        || DOCSTATUS_Reversed.equals(ds);
  } //	isComplete

  @Override
  public void setDoc(IDoc doc) {}

  @Override
  public void setProcessedOn(String processed, boolean b, boolean b1) {}
} //	MDDOrder
