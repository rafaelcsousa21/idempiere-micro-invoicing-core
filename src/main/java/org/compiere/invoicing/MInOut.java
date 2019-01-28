package org.compiere.invoicing;

import org.compiere.accounting.MClient;
import org.compiere.accounting.*;
import org.compiere.crm.MBPartner;
import org.compiere.docengine.DocumentEngine;
import org.compiere.model.*;
import org.compiere.order.MOrder;
import org.compiere.order.MRMALine;
import org.compiere.orm.*;
import org.compiere.process.CompleteActionResult;
import org.compiere.process.DocAction;
import org.compiere.production.MTransaction;
import org.compiere.util.Msg;
import org.compiere.validation.ModelValidationEngine;
import org.compiere.validation.ModelValidator;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.orm.POKt.I_ZERO;

public class MInOut extends org.compiere.order.MInOut implements DocAction, IPODoc {
  public MInOut(Properties ctx, int M_InOut_ID, String trxName) {
    super(ctx, M_InOut_ID, trxName);
  }

  protected MInOutLine[] m_lines = null;

  public MInOut(MOrder order, int C_DocTypeShipment_ID, Timestamp movementDate) {
    super(order, C_DocTypeShipment_ID, movementDate);
  }

  public MInOut(MInOut original, int c_docType_id, Timestamp movementDate) {
    super(original, c_docType_id, movementDate);
  }

  public MInOut(
      I_C_Invoice invoice, int C_DocTypeShipment_ID, Timestamp movementDate, int M_Warehouse_ID) {
    super(invoice, C_DocTypeShipment_ID, movementDate, M_Warehouse_ID);
  }

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set record
   * @param trxName transaction
   */
  public MInOut(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * Get Lines of Shipment
   *
   * @param requery refresh from db
   * @return lines
   */
  public MInOutLine[] getLines(boolean requery) {
    if (m_lines != null && !requery) {
      org.idempiere.orm.PO.set_TrxName(m_lines, null);
      return m_lines;
    }
    List<MInOutLine> list =
        new Query(getCtx(), I_M_InOutLine.Table_Name, "M_InOut_ID=?", null)
            .setParameters(getM_InOut_ID())
            .setOrderBy(MInOutLine.COLUMNNAME_Line)
            .list();
    //
    m_lines = new MInOutLine[list.size()];
    list.toArray(m_lines);
    return m_lines;
  } //	getMInOutLines

  /**
   * Get Lines of Shipment
   *
   * @return lines
   */
  public MInOutLine[] getLines() {
    return getLines(false);
  } //	getLines

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
  } //	process

  /** Process Message */
  protected String m_processMsg = null;
  /** Just Prepared Flag */
  protected boolean m_justPrepared = false;

  /**
   * Unlock Document.
   *
   * @return true if success
   */
  public boolean unlockIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
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
   * Prepare Document
   *
   * @return new status (In Progress or Invalid)
   */
  public String prepareIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
    if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();

    MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

    //  Order OR RMA can be processed on a shipment/receipt
    if (getC_Order_ID() != 0 && getM_RMA_ID() != 0) {
      m_processMsg = "@OrderOrRMA@";
      return DocAction.Companion.getSTATUS_Invalid();
    }
    //	Std Period open?
    if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(),  getOrgId())) {
      m_processMsg = "@PeriodClosed@";
      return DocAction.Companion.getSTATUS_Invalid();
    }

    //	Credit Check
    if (isSOTrx() && !isReversal()) {
      I_C_Order order = getC_Order();
      if (order != null
          && MDocType.DOCSUBTYPESO_PrepayOrder.equals(order.getC_DocType().getDocSubTypeSO())
          && !MSysConfig.getBooleanValue(
              MSysConfig.CHECK_CREDIT_ON_PREPAY_ORDER, true,  getClientId(),  getOrgId())) {
        // ignore -- don't validate Prepay Orders depending on sysconfig parameter
      } else {
        MBPartner bp = new MBPartner(getCtx(), getC_BPartner_ID(), null);
        if (MBPartner.SOCREDITSTATUS_CreditStop.equals(bp.getSOCreditStatus())) {
          m_processMsg =
              "@BPartnerCreditStop@ - @TotalOpenBalance@="
                  + bp.getTotalOpenBalance()
                  + ", @SO_CreditLimit@="
                  + bp.getSO_CreditLimit();
          return DocAction.Companion.getSTATUS_Invalid();
        }
        if (MBPartner.SOCREDITSTATUS_CreditHold.equals(bp.getSOCreditStatus())) {
          m_processMsg =
              "@BPartnerCreditHold@ - @TotalOpenBalance@="
                  + bp.getTotalOpenBalance()
                  + ", @SO_CreditLimit@="
                  + bp.getSO_CreditLimit();
          return DocAction.Companion.getSTATUS_Invalid();
        }
        if (!MBPartner.SOCREDITSTATUS_NoCreditCheck.equals(bp.getSOCreditStatus())
            && Env.ZERO.compareTo(bp.getSO_CreditLimit()) != 0) {
          BigDecimal notInvoicedAmt = MBPartner.getNotInvoicedAmt(getC_BPartner_ID());
          if (MBPartner.SOCREDITSTATUS_CreditHold.equals(bp.getSOCreditStatus(notInvoicedAmt))) {
            m_processMsg =
                "@BPartnerOverSCreditHold@ - @TotalOpenBalance@="
                    + bp.getTotalOpenBalance()
                    + ", @NotInvoicedAmt@="
                    + notInvoicedAmt
                    + ", @SO_CreditLimit@="
                    + bp.getSO_CreditLimit();
            return DocAction.Companion.getSTATUS_Invalid();
          }
        }
      }
    }

    //	Lines
    MInOutLine[] lines = getLines(true);
    if (lines == null || lines.length == 0) {
      m_processMsg = "@NoLines@";
      return DocAction.Companion.getSTATUS_Invalid();
    }
    BigDecimal Volume = Env.ZERO;
    BigDecimal Weight = Env.ZERO;

    //	Mandatory Attributes
    for (int i = 0; i < lines.length; i++) {
      MInOutLine line = lines[i];
      MProduct product = line.getProduct();
      if (product != null) {
        Volume = Volume.add(product.getVolume().multiply(line.getMovementQty()));
        Weight = Weight.add(product.getWeight().multiply(line.getMovementQty()));
      }
      //
      if (line.getMAttributeSetInstance_ID() != 0) continue;
      if (product != null && product.isASIMandatory(isSOTrx())) {
        if (product.getAttributeSet() != null
            && !product.getAttributeSet().excludeTableEntry(MInOutLine.Table_ID, isSOTrx())) {
          m_processMsg =
              "@M_AttributeSet_ID@ @IsMandatory@ (@Line@ #"
                  + lines[i].getLine()
                  + ", @M_Product_ID@="
                  + product.getValue()
                  + ")";
          return DocAction.Companion.getSTATUS_Invalid();
        }
      }
    }
    setVolume(Volume);
    setWeight(Weight);

    if (!isReversal()) //	don't change reversal
    {
      createConfirmation();
    }

    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
    if (m_processMsg != null) return DocAction.Companion.getSTATUS_Invalid();

    m_justPrepared = true;
    if (!DOCACTION_Complete.equals(getDocAction())) setDocAction(DOCACTION_Complete);
    return DocAction.Companion.getSTATUS_InProgress();
  } //	prepareIt

  /**
   * Approve Document
   *
   * @return true if success
   */
  public boolean approveIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    setIsApproved(true);
    return true;
  } //	approveIt

  /**
   * Reject Approval
   *
   * @return true if success
   */
  public boolean rejectIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    setIsApproved(false);
    return true;
  } //	rejectIt

  protected MInOutConfirm[] m_confirms = null;

  /**
   * Get Confirmations
   *
   * @param requery requery
   * @return array of Confirmations
   */
  public MInOutConfirm[] getConfirmations(boolean requery) {
    if (m_confirms != null && !requery) {
      org.idempiere.orm.PO.set_TrxName(m_confirms, null);
      return m_confirms;
    }
    List<MInOutConfirm> list =
        new Query(getCtx(), I_M_InOutConfirm.Table_Name, "M_InOut_ID=?", null)
            .setParameters(getM_InOut_ID())
            .list();
    m_confirms = new MInOutConfirm[list.size()];
    list.toArray(m_confirms);
    return m_confirms;
  } //	getConfirmations

  /**
   * Complete Document
   *
   * @return new status (Complete, In Progress, Invalid, Waiting ..)
   */
  public CompleteActionResult completeIt() {
    //	Re-Check
    if (!m_justPrepared) {
      String status = prepareIt();
      m_justPrepared = false;
      if (!DocAction.Companion.getSTATUS_InProgress().equals(status))
        return new CompleteActionResult(status);
    }

    // Set the definite document number after completed (if needed)
    setDefiniteDocumentNo();

    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
    if (m_processMsg != null)
      return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());

    //	Outstanding (not processed) Incoming Confirmations ?
    MInOutConfirm[] confirmations = getConfirmations(true);
    for (int i = 0; i < confirmations.length; i++) {
      MInOutConfirm confirm = confirmations[i];
      if (!confirm.isProcessed()) {
        if (MInOutConfirm.CONFIRMTYPE_CustomerConfirmation.equals(confirm.getConfirmType()))
          continue;
        //
        m_processMsg =
            "Open @M_InOutConfirm_ID@: "
                + confirm.getConfirmTypeName()
                + " - "
                + confirm.getDocumentNo();
        return new CompleteActionResult(DocAction.Companion.getSTATUS_InProgress());
      }
    }

    //	Implicit Approval
    if (!isApproved()) approveIt();
    if (log.isLoggable(Level.INFO)) log.info(toString());
    StringBuilder info = new StringBuilder();

    StringBuilder errors = new StringBuilder();
    //	For all lines
    MInOutLine[] lines = getLines(false);
    for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
      MInOutLine sLine = lines[lineIndex];
      MProduct product = sLine.getProduct();

      try {
        //	Qty & Type
        String MovementType = getMovementType();
        BigDecimal Qty = sLine.getMovementQty();
        if (MovementType.charAt(1) == '-') // 	C- Customer Shipment - V- Vendor Return
        Qty = Qty.negate();

        //	Update Order Line
        MOrderLine oLine = null;
        if (sLine.getC_OrderLine_ID() != 0) {
          oLine = new MOrderLine(getCtx(), sLine.getC_OrderLine_ID(), null);
          if (log.isLoggable(Level.FINE))
            log.fine(
                "OrderLine - Reserved="
                    + oLine.getQtyReserved()
                    + ", Delivered="
                    + oLine.getQtyDelivered());
        }

        // Load RMA Line
        MRMALine rmaLine = null;

        if (sLine.getM_RMALine_ID() != 0) {
          rmaLine = new MRMALine(getCtx(), sLine.getM_RMALine_ID(), null);
        }

        if (log.isLoggable(Level.INFO))
          log.info("Line=" + sLine.getLine() + " - Qty=" + sLine.getMovementQty());

        //	Stock Movement - Counterpart MOrder.reserveStock
        if (product != null && product.isStocked()) {
          // Ignore the Material Policy when is Reverse Correction
          if (!isReversal()) {
            BigDecimal movementQty = sLine.getMovementQty();
            BigDecimal qtyOnLineMA =
                MInOutLineMA.getManualQty(sLine.getM_InOutLine_ID(), null);

            if ((movementQty.signum() != 0
                    && qtyOnLineMA.signum() != 0
                    && movementQty.signum() != qtyOnLineMA.signum()) // must have same sign
                || (qtyOnLineMA.abs().compareTo(movementQty.abs())
                    > 0)) { // compare absolute values
              // More then line qty on attribute tab for line 10
              m_processMsg = "@Over_Qty_On_Attribute_Tab@ " + sLine.getLine();
              return new CompleteActionResult(DOCSTATUS_Invalid);
            }

            checkMaterialPolicy(sLine, movementQty.subtract(qtyOnLineMA));
          }

          log.fine("Material Transaction");
          MTransaction mtrx = null;

          //
          BigDecimal overReceipt = BigDecimal.ZERO;
          if (!isReversal()) {
            if (oLine != null) {
              BigDecimal toDelivered = oLine.getQtyOrdered().subtract(oLine.getQtyDelivered());
              if (toDelivered.signum() < 0) // IDEMPIERE-2889
              toDelivered = Env.ZERO;
              if (sLine.getMovementQty().compareTo(toDelivered) > 0)
                overReceipt = sLine.getMovementQty().subtract(toDelivered);
              if (overReceipt.signum() != 0) {
                sLine.setQtyOverReceipt(overReceipt);
                sLine.saveEx();
              }
            }
          } else {
            overReceipt = sLine.getQtyOverReceipt();
          }
          BigDecimal orderedQtyToUpdate = sLine.getMovementQty().subtract(overReceipt);
          //
          if (sLine.getMAttributeSetInstance_ID() == 0) {
            MInOutLineMA mas[] =
                MInOutLineMA.get(getCtx(), sLine.getM_InOutLine_ID(), null);
            for (int j = 0; j < mas.length; j++) {
              MInOutLineMA ma = mas[j];
              BigDecimal QtyMA = ma.getMovementQty();
              if (MovementType.charAt(1) == '-') // 	C- Customer Shipment - V- Vendor Return
              QtyMA = QtyMA.negate();

              //	Update Storage - see also VMatch.createMatchRecord
              if (!MStorageOnHand.add(
                  getCtx(),
                  getM_Warehouse_ID(),
                  sLine.getM_Locator_ID(),
                  sLine.getM_Product_ID(),
                  ma.getMAttributeSetInstance_ID(),
                  QtyMA,
                  ma.getDateMaterialPolicy(),
                  null)) {
                String lastError = CLogger.retrieveErrorString("");
                m_processMsg =
                    "Cannot correct Inventory OnHand (MA) ["
                        + product.getValue()
                        + "] - "
                        + lastError;
                return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
              }

              //	Create Transaction
              mtrx =
                  new MTransaction(
                      getCtx(),
                      sLine. getOrgId(),
                      MovementType,
                      sLine.getM_Locator_ID(),
                      sLine.getM_Product_ID(),
                      ma.getMAttributeSetInstance_ID(),
                      QtyMA,
                      getMovementDate(),
                      null);
              mtrx.setM_InOutLine_ID(sLine.getM_InOutLine_ID());
              if (!mtrx.save()) {
                m_processMsg =
                    "Could not create Material Transaction (MA) [" + product.getValue() + "]";
                return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
              }
            }

            if (oLine != null && mtrx != null && oLine.getQtyOrdered().signum() > 0) {
              if (sLine.getC_OrderLine_ID() != 0) {
                if (!MStorageReservation.add(
                    getCtx(),
                    oLine.getM_Warehouse_ID(),
                    sLine.getM_Product_ID(),
                    oLine.getMAttributeSetInstance_ID(),
                    orderedQtyToUpdate.negate(),
                    isSOTrx(),
                    null)) {
                  String lastError = CLogger.retrieveErrorString("");
                  m_processMsg =
                      "Cannot correct Inventory "
                          + (isSOTrx() ? "Reserved" : "Ordered")
                          + " (MA) - ["
                          + product.getValue()
                          + "] - "
                          + lastError;
                  return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
                }
              }
            }
          }
          //	sLine.getMAttributeSetInstance_ID() != 0
          if (mtrx == null) {
            Timestamp dateMPolicy = null;
            MStorageOnHand[] storages =
                MStorageOnHand.getWarehouse(
                    getCtx(),
                    0,
                    sLine.getM_Product_ID(),
                    sLine.getMAttributeSetInstance_ID(),
                    null,
                    MClient.MMPOLICY_FiFo.equals(product.getMMPolicy()),
                    false,
                    sLine.getM_Locator_ID(),
                    null);
            for (MStorageOnHand storage : storages) {
              if (storage.getQtyOnHand().compareTo(sLine.getMovementQty()) >= 0) {
                dateMPolicy = storage.getDateMaterialPolicy();
                break;
              }
            }

            if (dateMPolicy == null && storages.length > 0)
              dateMPolicy = storages[0].getDateMaterialPolicy();

            if (dateMPolicy == null) dateMPolicy = getMovementDate();

            //	Fallback: Update Storage - see also VMatch.createMatchRecord
            if (!MStorageOnHand.add(
                getCtx(),
                getM_Warehouse_ID(),
                sLine.getM_Locator_ID(),
                sLine.getM_Product_ID(),
                sLine.getMAttributeSetInstance_ID(),
                Qty,
                dateMPolicy,
                null)) {
              String lastError = CLogger.retrieveErrorString("");
              m_processMsg =
                  "Cannot correct Inventory OnHand [" + product.getValue() + "] - " + lastError;
              return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
            }
            if (oLine != null && oLine.getQtyOrdered().signum() > 0) {
              if (!MStorageReservation.add(
                  getCtx(),
                  oLine.getM_Warehouse_ID(),
                  sLine.getM_Product_ID(),
                  oLine.getMAttributeSetInstance_ID(),
                  orderedQtyToUpdate.negate(),
                  isSOTrx(),
                  null)) {
                m_processMsg =
                    "Cannot correct Inventory Reserved "
                        + (isSOTrx() ? "Reserved [" : "Ordered [")
                        + product.getValue()
                        + "]";
                return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
              }
            }

            //	FallBack: Create Transaction
            mtrx =
                new MTransaction(
                    getCtx(),
                    sLine. getOrgId(),
                    MovementType,
                    sLine.getM_Locator_ID(),
                    sLine.getM_Product_ID(),
                    sLine.getMAttributeSetInstance_ID(),
                    Qty,
                    getMovementDate(),
                    null);
            mtrx.setM_InOutLine_ID(sLine.getM_InOutLine_ID());
            if (!mtrx.save()) {
              m_processMsg =
                  CLogger.retrieveErrorString(
                      "Could not create Material Transaction [" + product.getValue() + "]");
              return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
            }
          }
        } //	stock movement

        //	Correct Order Line
        if (product != null && oLine != null) // 	other in VMatch.createMatchRecord
        {
          oLine.setQtyReserved(
              oLine
                  .getQtyReserved()
                  .subtract(sLine.getMovementQty().subtract(sLine.getQtyOverReceipt())));
        }

        //	Update Sales Order Line
        if (oLine != null) {
          if (isSOTrx() //	PO is done by Matching
              || sLine.getM_Product_ID() == 0) // 	PO Charges, empty lines
          {
            if (isSOTrx()) oLine.setQtyDelivered(oLine.getQtyDelivered().subtract(Qty));
            else oLine.setQtyDelivered(oLine.getQtyDelivered().add(Qty));
            oLine.setDateDelivered(getMovementDate()); // 	overwrite=last
          }
          if (!oLine.save()) {
            m_processMsg = "Could not update Order Line";
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
          } else if (log.isLoggable(Level.FINE))
            log.fine(
                "OrderLine -> Reserved="
                    + oLine.getQtyReserved()
                    + ", Delivered="
                    + oLine.getQtyReserved());
        }
        //  Update RMA Line Qty Delivered
        else if (rmaLine != null) {
          if (isSOTrx()) {
            rmaLine.setQtyDelivered(rmaLine.getQtyDelivered().add(Qty));
          } else {
            rmaLine.setQtyDelivered(rmaLine.getQtyDelivered().subtract(Qty));
          }
          if (!rmaLine.save()) {
            m_processMsg = "Could not update RMA Line";
            return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
          }
        }

        //	Create Asset for SO
        if (product != null
            && isSOTrx()
            && product.isCreateAsset()
            && !product.getM_Product_Category().getA_Asset_Group().isFixedAsset()
            && sLine.getMovementQty().signum() > 0
            && !isReversal()) {
          log.fine("Asset");
          info.append("@A_Asset_ID@: ");
          int noAssets = sLine.getMovementQty().intValue();
          if (!product.isOneAssetPerUOM()) noAssets = 1;
          for (int i = 0; i < noAssets; i++) {
            if (i > 0) info.append(" - ");
            int deliveryCount = i + 1;
            if (!product.isOneAssetPerUOM()) deliveryCount = 0;
            MAsset asset = new MAsset(this, sLine, deliveryCount);
            if (!asset.save(null)) {
              m_processMsg = "Could not create Asset";
              return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
            }
            info.append(asset.getValue());
          }
        } //	Asset

        //	Matching
        if (!isSOTrx() && sLine.getM_Product_ID() != 0 && !isReversal()) {
          BigDecimal matchQty = sLine.getMovementQty();
          //	Invoice - Receipt Match (requires Product)
          MInvoiceLine iLine = MInvoiceLine.getOfInOutLine(sLine);
          if (iLine != null && iLine.getM_Product_ID() != 0) {
            if (matchQty.compareTo(iLine.getQtyInvoiced()) > 0) matchQty = iLine.getQtyInvoiced();

            MMatchInv[] matches =
                MMatchInv.get(
                    getCtx(),
                    sLine.getM_InOutLine_ID(),
                    iLine.getC_InvoiceLine_ID(),
                    null);
            if (matches == null || matches.length == 0) {
              MMatchInv inv = new MMatchInv(iLine, getMovementDate(), matchQty);
              if (sLine.getMAttributeSetInstance_ID() != iLine.getMAttributeSetInstance_ID()) {
                iLine.setM_AttributeSetInstance_ID(sLine.getMAttributeSetInstance_ID());
                iLine.saveEx(); // 	update matched invoice with ASI
                inv.setM_AttributeSetInstance_ID(sLine.getMAttributeSetInstance_ID());
              }
              if (!inv.save(null)) {
                m_processMsg = CLogger.retrieveErrorString("Could not create Inv Matching");
                return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
              }
              addDocsPostProcess(inv);
            }
          }

          //	Link to Order
          if (sLine.getC_OrderLine_ID() != 0) {
            log.fine("PO Matching");
            //	Ship - PO
            MMatchPO po = MMatchPO.create(null, sLine, getMovementDate(), matchQty);
            if (po != null) {
              if (!po.save(null)) {
                m_processMsg = "Could not create PO Matching";
                return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
              }
              if (!po.isPosted()) addDocsPostProcess(po);
              MMatchInv matchInvCreated = po.getMatchInvCreated();
              if (matchInvCreated != null) {
                addDocsPostProcess(matchInvCreated);
              }
            }
            //	Update PO with ASI
            if (oLine != null
                && oLine.getMAttributeSetInstance_ID() == 0
                && sLine.getMovementQty().compareTo(oLine.getQtyOrdered())
                    == 0) //  just if full match [ 1876965 ]
            {
              oLine.setM_AttributeSetInstance_ID(sLine.getMAttributeSetInstance_ID());
              oLine.saveEx(null);
            }
          } else //	No Order - Try finding links via Invoice
          {
            //	Invoice has an Order Link
            if (iLine != null && iLine.getC_OrderLine_ID() != 0) {
              //	Invoice is created before  Shipment
              log.fine("PO(Inv) Matching");
              //	Ship - Invoice
              MMatchPO po = MMatchPO.create(iLine, sLine, getMovementDate(), matchQty);
              if (po != null) {
                if (!po.save(null)) {
                  m_processMsg = "Could not create PO(Inv) Matching";
                  return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
                }
                if (!po.isPosted()) addDocsPostProcess(po);
              }

              //	Update PO with ASI
              oLine = new MOrderLine(getCtx(), iLine.getC_OrderLine_ID(), null);
              if (oLine != null
                  && oLine.getMAttributeSetInstance_ID() == 0
                  && sLine.getMovementQty().compareTo(oLine.getQtyOrdered())
                      == 0) //  just if full match [ 1876965 ]
              {
                oLine.setM_AttributeSetInstance_ID(sLine.getMAttributeSetInstance_ID());
                oLine.saveEx(null);
              }
            }
          } //	No Order
        } //	PO Matching
      } catch (NegativeInventoryDisallowedException e) {
        log.severe(e.getMessage());
        errors
            .append(Msg.getElement(getCtx(), "Line"))
            .append(" ")
            .append(sLine.getLine())
            .append(": ");
        errors.append(e.getMessage()).append("\n");
      }
    } //	for all lines

    if (errors.toString().length() > 0) {
      m_processMsg = errors.toString();
      return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
    }

    //	Counter Documents
    MInOut counter = createCounterDoc();
    if (counter != null)
      info.append(" - @CounterDoc@: @M_InOut_ID@=").append(counter.getDocumentNo());

    //  Drop Shipments
    MInOut dropShipment = createDropShipment();
    if (dropShipment != null)
      info.append(" - @DropShipment@: @M_InOut_ID@=").append(dropShipment.getDocumentNo());
    //	User Validation
    String valid =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
    if (valid != null) {
      m_processMsg = valid;
      return new CompleteActionResult(DocAction.Companion.getSTATUS_Invalid());
    }

    m_processMsg = info.toString();
    setProcessed(true);
    setDocAction(DOCACTION_Close);
    return new CompleteActionResult(DocAction.Companion.getSTATUS_Completed());
  } //	completeIt

  /**
   * Void Document.
   *
   * @return true if success
   */
  public boolean voidIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());

    if (DOCSTATUS_Closed.equals(getDocStatus())
        || DOCSTATUS_Reversed.equals(getDocStatus())
        || DOCSTATUS_Voided.equals(getDocStatus())) {
      m_processMsg = "Document Closed: " + getDocStatus();
      return false;
    }

    //	Not Processed
    if (DOCSTATUS_Drafted.equals(getDocStatus())
        || DOCSTATUS_Invalid.equals(getDocStatus())
        || DOCSTATUS_InProgress.equals(getDocStatus())
        || DOCSTATUS_Approved.equals(getDocStatus())
        || DOCSTATUS_NotApproved.equals(getDocStatus())) {
      // Before Void
      m_processMsg =
          ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
      if (m_processMsg != null) return false;

      //	Set lines to 0
      MInOutLine[] lines = getLines(false);
      for (int i = 0; i < lines.length; i++) {
        MInOutLine line = lines[i];
        BigDecimal old = line.getMovementQty();
        if (old.signum() != 0) {
          line.setQty(Env.ZERO);
          StringBuilder msgadd = new StringBuilder("Void (").append(old).append(")");
          line.addDescription(msgadd.toString());
          line.saveEx(null);
        }
      }
      //
      // Void Confirmations
      setDocStatus(DOCSTATUS_Voided); // need to set & save docstatus to be able to check it in
      // MInOutConfirm.voidIt()
      saveEx();
      voidConfirmations();
    } else {
      boolean accrual = false;
      try {
        MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(),  getOrgId());
      } catch (PeriodClosedException e) {
        accrual = true;
      }

      if (accrual) return reverseAccrualIt();
      else return reverseCorrectIt();
    }

    // After Void
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
    if (m_processMsg != null) return false;

    setProcessed(true);
    setDocAction(DOCACTION_None);
    return true;
  } //	voidIt

  /**
   * Close Document.
   *
   * @return true if success
   */
  public boolean closeIt() {
    if (log.isLoggable(Level.INFO)) log.info(toString());
    // Before Close
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
    if (m_processMsg != null) return false;

    setProcessed(true);
    setDocAction(DOCACTION_None);

    // After Close
    m_processMsg =
        ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
    if (m_processMsg != null) return false;
    return true;
  } //	closeIt

  /**
   * Reverse Correction - same date
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

    MInOut reversal = reverse(false);
    if (reversal == null) return false;

    // After reverseCorrect
    m_processMsg =
        ModelValidationEngine.get()
            .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
    if (m_processMsg != null) return false;

    m_processMsg = reversal.getDocumentNo();
    setProcessed(true);
    setDocStatus(DOCSTATUS_Reversed); // 	 may come from void
    setDocAction(DOCACTION_None);
    return true;
  } //	reverseCorrectionIt

  protected MInOut reverse(boolean accrual) {
    MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
    Timestamp reversalDate = accrual ? Env.getContextAsDate(getCtx(), "#Date") : getDateAcct();
    if (reversalDate == null) {
      reversalDate = new Timestamp(System.currentTimeMillis());
    }
    Timestamp reversalMovementDate = accrual ? reversalDate : getMovementDate();
    if (!MPeriod.isOpen(getCtx(), reversalDate, dt.getDocBaseType(),  getOrgId())) {
      m_processMsg = "@PeriodClosed@";
      return null;
    }

    //	Reverse/Delete Matching
    if (!isSOTrx()) {
      if (!reverseMatching(reversalDate)) return null;
    }

    //	Deep Copy
    MInOut reversal =
        copyFrom(
            this,
            reversalMovementDate,
            reversalDate,
            getC_DocType_ID(),
            isSOTrx(),
            false,
            null,
            true);
    if (reversal == null) {
      m_processMsg = "Could not create Ship Reversal";
      return null;
    }
    reversal.setReversal(true);

    //	Reverse Line Qty
    MInOutLine[] sLines = getLines(false);
    MInOutLine[] rLines = reversal.getLines(false);
    for (int i = 0; i < rLines.length; i++) {
      MInOutLine rLine = rLines[i];
      rLine.setQtyEntered(rLine.getQtyEntered().negate());
      rLine.setMovementQty(rLine.getMovementQty().negate());
      rLine.setQtyOverReceipt(rLine.getQtyOverReceipt().negate());
      rLine.setM_AttributeSetInstance_ID(sLines[i].getMAttributeSetInstance_ID());
      // Goodwill: store original (voided/reversed) document line
      rLine.setReversalLine_ID(sLines[i].getM_InOutLine_ID());
      if (!rLine.save(null)) {
        m_processMsg = "Could not correct Ship Reversal Line";
        return null;
      }
      //	We need to copy MA
      if (rLine.getMAttributeSetInstance_ID() == 0) {
        MInOutLineMA mas[] =
            MInOutLineMA.get(getCtx(), sLines[i].getM_InOutLine_ID(), null);
        for (int j = 0; j < mas.length; j++) {
          MInOutLineMA ma =
              new MInOutLineMA(
                  rLine,
                  mas[j].getMAttributeSetInstance_ID(),
                  mas[j].getMovementQty().negate(),
                  mas[j].getDateMaterialPolicy(),
                  true);
          ma.saveEx();
        }
      }
      //	De-Activate Asset
      MAsset asset = MAsset.getFromShipment(getCtx(), sLines[i].getM_InOutLine_ID(), null);
      if (asset != null) {
        asset.setIsActive(false);
        asset.setDescription(
            asset.getDescription()
                + " ("
                + reversal.getDocumentNo()
                + " #"
                + rLine.getLine()
                + "<-)");
        asset.saveEx();
      }
    }
    reversal.setC_Order_ID(getC_Order_ID());
    // Set M_RMA_ID
    reversal.setM_RMA_ID(getM_RMA_ID());
    StringBuilder msgadd = new StringBuilder("{->").append(getDocumentNo()).append(")");
    reversal.addDescription(msgadd.toString());
    // FR1948157
    reversal.setReversal_ID(getM_InOut_ID());
    reversal.saveEx(null);
    //
    reversal.docsPostProcess = this.docsPostProcess;
    this.docsPostProcess = new ArrayList<IPODoc>();
    //
    if (!reversal.processIt(DocAction.Companion.getACTION_Complete())
        || !reversal.getDocStatus().equals(DocAction.Companion.getSTATUS_Completed())) {
      m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
      return null;
    }
    reversal.closeIt();
    reversal.setProcessing(false);
    reversal.setDocStatus(DOCSTATUS_Reversed);
    reversal.setDocAction(DOCACTION_None);
    reversal.saveEx(null);
    //
    msgadd = new StringBuilder("(").append(reversal.getDocumentNo()).append("<-)");
    addDescription(msgadd.toString());

    //
    // Void Confirmations
    setDocStatus(DOCSTATUS_Reversed); // need to set & save docstatus to be able to check it in
    // MInOutConfirm.voidIt()
    saveEx();
    // FR1948157
    this.setReversal_ID(reversal.getM_InOut_ID());
    voidConfirmations();
    return reversal;
  }

  protected boolean reverseMatching(Timestamp reversalDate) {
    MMatchInv[] mInv = MMatchInv.getInOut(getCtx(), getM_InOut_ID(), null);
    for (MMatchInv mMatchInv : mInv) {
      if (mMatchInv.getReversal_ID() > 0) continue;

      String description = mMatchInv.getDescription();
      if (description == null || !description.endsWith("<-)")) {
        if (!mMatchInv.reverse(reversalDate)) {
          log.log(
              Level.SEVERE,
              "Failed to create reversal for match invoice " + mMatchInv.getDocumentNo());
          return false;
        }
        addDocsPostProcess(new MMatchInv(Env.getCtx(), mMatchInv.getReversal_ID(), null));
      }
    }
    MMatchPO[] mMatchPOList = MMatchPO.getInOut(getCtx(), getM_InOut_ID(), null);
    for (MMatchPO mMatchPO : mMatchPOList) {
      if (mMatchPO.getReversal_ID() > 0) continue;

      String description = mMatchPO.getDescription();
      if (description == null || !description.endsWith("<-)")) {
        if (!mMatchPO.reverse(reversalDate)) {
          log.log(
              Level.SEVERE,
              "Failed to create reversal for match purchase order " + mMatchPO.getDocumentNo());
          return false;
        }
        addDocsPostProcess(new MMatchPO(Env.getCtx(), mMatchPO.getReversal_ID(), null));
      }
    }
    return true;
  }

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

    MInOut reversal = reverse(true);
    if (reversal == null) return false;

    // After reverseAccrual
    m_processMsg =
        ModelValidationEngine.get()
            .fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
    if (m_processMsg != null) return false;

    m_processMsg = reversal.getDocumentNo();
    setProcessed(true);
    setDocStatus(DOCSTATUS_Reversed); // 	 may come from void
    setDocAction(DOCACTION_None);
    return true;
  } //	reverseAccrualIt

  /**
   * Re-activate
   *
   * @return false
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

    return false;
  } //	reActivateIt

  @Override
  public void setDoc(IDoc doc) {}

  @Override
  public void setProcessedOn(String processed, boolean b, boolean b1) {}

  /** Create the missing next Confirmation */
  public void createConfirmation() {
    MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
    boolean pick = dt.isPickQAConfirm();
    boolean ship = dt.isShipConfirm();
    //	Nothing to do
    if (!pick && !ship) {
      log.fine("No need");
      return;
    }

    //	Create Both .. after each other
    if (pick && ship) {
      boolean havePick = false;
      boolean haveShip = false;
      MInOutConfirm[] confirmations = getConfirmations(false);
      for (int i = 0; i < confirmations.length; i++) {
        MInOutConfirm confirm = confirmations[i];
        if (MInOutConfirm.CONFIRMTYPE_PickQAConfirm.equals(confirm.getConfirmType())) {
          if (!confirm.isProcessed()) // 	wait intil done
          {
            if (log.isLoggable(Level.FINE)) log.fine("Unprocessed: " + confirm);
            return;
          }
          havePick = true;
        } else if (MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm.equals(confirm.getConfirmType()))
          haveShip = true;
      }
      //	Create Pick
      if (!havePick) {
        MInOutConfirm.create(this, MInOutConfirm.CONFIRMTYPE_PickQAConfirm, false);
        return;
      }
      //	Create Ship
      if (!haveShip) {
        MInOutConfirm.create(this, MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm, false);
        return;
      }
      return;
    }
    //	Create just one
    if (pick) MInOutConfirm.create(this, MInOutConfirm.CONFIRMTYPE_PickQAConfirm, true);
    else if (ship) MInOutConfirm.create(this, MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm, true);
  } //	createConfirmation

  protected void voidConfirmations() {
    for (MInOutConfirm confirm : getConfirmations(true)) {
      if (!confirm.isProcessed()) {
        if (!confirm.processIt(MInOutConfirm.DOCACTION_Void))
          throw new AdempiereException(confirm.getProcessMsg());
        confirm.saveEx();
      }
    }
  }

  /** Set the definite document number after completed */
  protected void setDefiniteDocumentNo() {
    MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
    if (dt.isOverwriteDateOnComplete()) {
      setMovementDate(TimeUtil.getDay(0));
      if (getDateAcct().before(getMovementDate())) {
        setDateAcct(getMovementDate());
        MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(),  getOrgId());
      }
    }
    if (dt.isOverwriteSeqOnComplete()) {
      String value = MSequence.getDocumentNo(getC_DocType_ID(), null, true, this);
      if (value != null) setDocumentNo(value);
    }
  }

  /** Check Material Policy Sets line ASI */
  protected void checkMaterialPolicy(MInOutLine line, BigDecimal qty) {

    int no = MInOutLineMA.deleteInOutLineMA(line.getM_InOutLine_ID(), null);
    if (no > 0) if (log.isLoggable(Level.CONFIG)) log.config("Delete old #" + no);

    if (Env.ZERO.compareTo(qty) == 0) return;

    //	Incoming Trx
    String MovementType = getMovementType();
    boolean inTrx = MovementType.charAt(1) == '+'; // 	V+ Vendor Receipt

    boolean needSave = false;

    MProduct product = line.getProduct();

    //	Need to have Location
    if (product != null && line.getM_Locator_ID() == 0) {
      // MWarehouse w = MWarehouse.get(getCtx(), getM_Warehouse_ID());
      line.setM_Warehouse_ID(getM_Warehouse_ID());
      line.setM_Locator_ID(inTrx ? Env.ZERO : line.getMovementQty()); // 	default Locator
      needSave = true;
    }

    //	Attribute Set Instance
    //  Create an  Attribute Set Instance to any receipt FIFO/LIFO
    if (product != null && line.getMAttributeSetInstance_ID() == 0) {
      // Validate Transaction
      if (getMovementType().compareTo(MInOut.MOVEMENTTYPE_VendorReceipts) == 0) {
        // auto balance negative on hand
        BigDecimal qtyToReceive = autoBalanceNegative(line, product, qty);

        // Allocate remaining qty.
        if (qtyToReceive.compareTo(Env.ZERO) > 0) {
          MInOutLineMA ma =
              MInOutLineMA.addOrCreate(line, 0, qtyToReceive, getMovementDate(), true);
          ma.saveEx();
        }

      } else if (getMovementType().compareTo(MInOut.MOVEMENTTYPE_CustomerReturns) == 0) {
        BigDecimal qtyToReturn = autoBalanceNegative(line, product, qty);

        if (line.getM_RMALine_ID() != 0 && qtyToReturn.compareTo(Env.ZERO) > 0) {
          // Linking to shipment line
          MRMALine rmaLine = new MRMALine(getCtx(), line.getM_RMALine_ID(), null);
          if (rmaLine.getM_InOutLine_ID() > 0) {
            // retrieving ASI which is not already returned
            MInOutLineMA shipmentMAS[] =
                MInOutLineMA.getNonReturned(getCtx(), rmaLine.getM_InOutLine_ID(), null);

            for (MInOutLineMA sMA : shipmentMAS) {
              BigDecimal lineMAQty = sMA.getMovementQty();
              if (lineMAQty.compareTo(qtyToReturn) > 0) {
                lineMAQty = qtyToReturn;
              }

              MInOutLineMA ma =
                  MInOutLineMA.addOrCreate(
                      line,
                      sMA.getMAttributeSetInstance_ID(),
                      lineMAQty,
                      sMA.getDateMaterialPolicy(),
                      true);
              ma.saveEx();

              qtyToReturn = qtyToReturn.subtract(lineMAQty);
              if (qtyToReturn.compareTo(Env.ZERO) == 0) break;
            }
          }
        }
        if (qtyToReturn.compareTo(Env.ZERO) > 0) {
          // Use movement data for  Material policy if no linkage found to Shipment.
          MInOutLineMA ma = MInOutLineMA.addOrCreate(line, 0, qtyToReturn, getMovementDate(), true);
          ma.saveEx();
        }
      }
      // Create consume the Attribute Set Instance using policy FIFO/LIFO
      else if (getMovementType().compareTo(MInOut.MOVEMENTTYPE_VendorReturns) == 0
          || getMovementType().compareTo(MInOut.MOVEMENTTYPE_CustomerShipment) == 0) {
        String MMPolicy = product.getMMPolicy();
        Timestamp minGuaranteeDate = getMovementDate();
        MStorageOnHand[] storages =
            MStorageOnHand.getWarehouse(
                getCtx(),
                getM_Warehouse_ID(),
                line.getM_Product_ID(),
                line.getMAttributeSetInstance_ID(),
                minGuaranteeDate,
                MClient.MMPOLICY_FiFo.equals(MMPolicy),
                true,
                line.getM_Locator_ID(),
                null,
                false);
        BigDecimal qtyToDeliver = qty;
        for (MStorageOnHand storage : storages) {
          if (storage.getQtyOnHand().compareTo(qtyToDeliver) >= 0) {
            MInOutLineMA ma =
                new MInOutLineMA(
                    line,
                    storage.getMAttributeSetInstance_ID(),
                    qtyToDeliver,
                    storage.getDateMaterialPolicy(),
                    true);
            ma.saveEx();
            qtyToDeliver = Env.ZERO;
          } else {
            MInOutLineMA ma =
                new MInOutLineMA(
                    line,
                    storage.getMAttributeSetInstance_ID(),
                    storage.getQtyOnHand(),
                    storage.getDateMaterialPolicy(),
                    true);
            ma.saveEx();
            qtyToDeliver = qtyToDeliver.subtract(storage.getQtyOnHand());
            if (log.isLoggable(Level.FINE)) log.fine(ma + ", QtyToDeliver=" + qtyToDeliver);
          }

          if (qtyToDeliver.signum() == 0) break;
        }

        if (qtyToDeliver.signum() != 0) {
          // Over Delivery
          MInOutLineMA ma =
              MInOutLineMA.addOrCreate(
                  line, line.getMAttributeSetInstance_ID(), qtyToDeliver, getMovementDate(), true);
          ma.saveEx();
          if (log.isLoggable(Level.FINE)) log.fine("##: " + ma);
        }
      } //	outgoing Trx
    } //	attributeSetInstance

    if (needSave) {
      line.saveEx();
    }
  } //	checkMaterialPolicy

  /**
   * ************************************************************************ Create Counter
   * Document
   *
   * @return InOut
   */
  protected MInOut createCounterDoc() {
    //	Is this a counter doc ?
    if (getRef_InOut_ID() != 0) return null;

    //	Org Must be linked to BPartner
    MOrg org = MOrg.get(getCtx(),  getOrgId());
    int counterC_BPartner_ID = org.getLinkedC_BPartner_ID(null);
    if (counterC_BPartner_ID == 0) return null;
    //	Business Partner needs to be linked to Org
    MBPartner bp = new MBPartner(getCtx(), getC_BPartner_ID(), null);
    int counterAD_Org_ID = bp.getAD_OrgBP_ID_Int();
    if (counterAD_Org_ID == 0) return null;

    MBPartner counterBP = new MBPartner(getCtx(), counterC_BPartner_ID, null);
    MOrgInfo counterOrgInfo = MOrgInfo.get(getCtx(), counterAD_Org_ID, null);
    if (log.isLoggable(Level.INFO)) log.info("Counter BP=" + counterBP.getName());

    //	Document Type
    int C_DocTypeTarget_ID = 0;
    MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(getCtx(), getC_DocType_ID());
    if (counterDT != null) {
      if (log.isLoggable(Level.FINE)) log.fine(counterDT.toString());
      if (!counterDT.isCreateCounter() || !counterDT.isValid()) return null;
      C_DocTypeTarget_ID = counterDT.getCounter_C_DocType_ID();
    } else //	indirect
    {
      C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocType_ID(getCtx(), getC_DocType_ID());
      if (log.isLoggable(Level.FINE)) log.fine("Indirect C_DocTypeTarget_ID=" + C_DocTypeTarget_ID);
      if (C_DocTypeTarget_ID <= 0) return null;
    }

    //	Deep Copy
    MInOut counter =
        copyFrom(
            this,
            getMovementDate(),
            getDateAcct(),
            C_DocTypeTarget_ID,
            !isSOTrx(),
            true,
            null,
            true);

    //
    counter.setAD_Org_ID(counterAD_Org_ID);
    counter.setM_Warehouse_ID(counterOrgInfo.getM_Warehouse_ID());
    //
    counter.setBPartner(counterBP);

    if (isDropShip()) {
      counter.setIsDropShip(true);
      counter.setDropShip_BPartner_ID(getDropShip_BPartner_ID());
      counter.setDropShip_Location_ID(getDropShip_Location_ID());
      counter.setDropShip_User_ID(getDropShip_User_ID());
    }

    //	Refernces (Should not be required
    counter.setSalesRep_ID(getSalesRep_ID());
    counter.saveEx(null);

    String MovementType = counter.getMovementType();
    boolean inTrx = MovementType.charAt(1) == '+'; // 	V+ Vendor Receipt

    //	Update copied lines
    MInOutLine[] counterLines = counter.getLines(true);
    for (int i = 0; i < counterLines.length; i++) {
      MInOutLine counterLine = counterLines[i];
      counterLine.setClientOrg(counter);
      counterLine.setM_Warehouse_ID(counter.getM_Warehouse_ID());
      counterLine.setM_Locator_ID(0);
      counterLine.setM_Locator_ID(inTrx ? Env.ZERO : counterLine.getMovementQty());
      //
      counterLine.saveEx(null);
    }

    if (log.isLoggable(Level.FINE)) log.fine(counter.toString());

    //	Document Action
    if (counterDT != null) {
      if (counterDT.getDocAction() != null) {
        counter.setDocAction(counterDT.getDocAction());
        // added AdempiereException by zuhri
        if (!counter.processIt(counterDT.getDocAction()))
          throw new AdempiereException(
              "Failed when processing document - " + counter.getProcessMsg());
        // end added
        counter.saveEx(null);
      }
    }
    return counter;
  } //	createCounterDoc

  /**
   * Automatically creates a customer shipment for any drop shipment material receipt Based on
   * createCounterDoc() by JJ
   *
   * @return shipment if created else null
   */
  protected MInOut createDropShipment() {

    if (isSOTrx() || !isDropShip() || getC_Order_ID() == 0) return null;

    int linkedOrderID = new MOrder(getCtx(), getC_Order_ID(), null).getLink_Order_ID();
    if (linkedOrderID <= 0) return null;

    //	Document Type
    int C_DocTypeTarget_ID = 0;
    MDocType[] shipmentTypes =
        MDocType.getOfDocBaseType(getCtx(), MDocType.DOCBASETYPE_MaterialDelivery);

    for (int i = 0; i < shipmentTypes.length; i++) {
      if (shipmentTypes[i].isSOTrx() && (C_DocTypeTarget_ID == 0 || shipmentTypes[i].isDefault()))
        C_DocTypeTarget_ID = shipmentTypes[i].getC_DocType_ID();
    }

    //	Deep Copy
    MInOut dropShipment =
        copyFrom(
            this,
            getMovementDate(),
            getDateAcct(),
            C_DocTypeTarget_ID,
            !isSOTrx(),
            false,
            null,
            true);

    dropShipment.setC_Order_ID(linkedOrderID);

    // get invoice id from linked order
    int invID = new MOrder(getCtx(), linkedOrderID, null).getC_Invoice_ID();
    if (invID != 0) dropShipment.setC_Invoice_ID(invID);

    dropShipment.setC_BPartner_ID(getDropShip_BPartner_ID());
    dropShipment.setC_BPartner_Location_ID(getDropShip_Location_ID());
    dropShipment.setAD_User_ID(getDropShip_User_ID());
    dropShipment.setIsDropShip(false);
    dropShipment.setDropShip_BPartner_ID(0);
    dropShipment.setDropShip_Location_ID(0);
    dropShipment.setDropShip_User_ID(0);
    dropShipment.setMovementType(MOVEMENTTYPE_CustomerShipment);

    //	References (Should not be required
    dropShipment.setSalesRep_ID(getSalesRep_ID());
    dropShipment.saveEx(null);

    //		Update line order references to linked sales order lines
    MInOutLine[] lines = dropShipment.getLines(true);
    for (int i = 0; i < lines.length; i++) {
      MInOutLine dropLine = lines[i];
      MOrderLine ol = new MOrderLine(getCtx(), dropLine.getC_OrderLine_ID(), null);
      if (ol.getC_OrderLine_ID() != 0) {
        dropLine.setC_OrderLine_ID(ol.getLink_OrderLine_ID());
        dropLine.saveEx();
      }
    }

    if (log.isLoggable(Level.FINE)) log.fine(dropShipment.toString());

    dropShipment.setDocAction(DocAction.Companion.getACTION_Complete());
    // added AdempiereException by Zuhri
    if (!dropShipment.processIt(DocAction.Companion.getACTION_Complete()))
      throw new AdempiereException(
          "Failed when processing document - " + dropShipment.getProcessMsg());
    // end added
    dropShipment.saveEx();

    return dropShipment;
  }

  /**
   * Create new Shipment by copying
   *
   * @param from shipment
   * @param dateDoc date of the document date
   * @param C_DocType_ID doc type
   * @param isSOTrx sales order
   * @param counter create counter links
   * @param trxName trx
   * @param setOrder set the order link
   * @return Shipment
   */
  public static MInOut copyFrom(
      MInOut from,
      Timestamp dateDoc,
      Timestamp dateAcct,
      int C_DocType_ID,
      boolean isSOTrx,
      boolean counter,
      String trxName,
      boolean setOrder) {
    MInOut to = new MInOut(from.getCtx(), 0, null);
    to.set_TrxName(trxName);
    copyValues(from, to, from. getClientId(), from. getOrgId());
    to.set_ValueNoCheck("M_InOut_ID", I_ZERO);
    to.set_ValueNoCheck("DocumentNo", null);
    //
    to.setDocStatus(DOCSTATUS_Drafted); // 	Draft
    to.setDocAction(DOCACTION_Complete);
    //
    to.setC_DocType_ID(C_DocType_ID);
    to.setIsSOTrx(isSOTrx);
    if (counter) {
      MDocType docType = MDocType.get(from.getCtx(), C_DocType_ID);
      if (MDocType.DOCBASETYPE_MaterialDelivery.equals(docType.getDocBaseType())) {
        to.setMovementType(isSOTrx ? MOVEMENTTYPE_CustomerShipment : MOVEMENTTYPE_VendorReturns);
      } else if (MDocType.DOCBASETYPE_MaterialReceipt.equals(docType.getDocBaseType())) {
        to.setMovementType(isSOTrx ? MOVEMENTTYPE_CustomerReturns : MOVEMENTTYPE_VendorReceipts);
      }
    }

    //
    to.setDateOrdered(dateDoc);
    to.setDateAcct(dateAcct);
    to.setMovementDate(dateDoc);
    to.setDatePrinted(null);
    to.setIsPrinted(false);
    to.setDateReceived(null);
    to.setNoPackages(0);
    to.setShipDate(null);
    to.setPickDate(null);
    to.setIsInTransit(false);
    //
    to.setIsApproved(false);
    to.setC_Invoice_ID(0);
    to.setTrackingNo(null);
    to.setIsInDispute(false);
    //
    to.setPosted(false);
    to.setProcessed(false);
    // [ 1633721 ] Reverse Documents- Processing=Y
    to.setProcessing(false);
    to.setC_Order_ID(0); // 	Overwritten by setOrder
    to.setM_RMA_ID(0); //  Overwritten by setOrder
    if (counter) {
      to.setC_Order_ID(0);
      to.setRef_InOut_ID(from.getM_InOut_ID());
      //	Try to find Order/Invoice link
      if (from.getC_Order_ID() != 0) {
        MOrder peer = new MOrder(from.getCtx(), from.getC_Order_ID(), null);
        if (peer.getRef_Order_ID() != 0) to.setC_Order_ID(peer.getRef_Order_ID());
      }
      if (from.getC_Invoice_ID() != 0) {
        MInvoice peer = new MInvoice(from.getCtx(), from.getC_Invoice_ID(), null);
        if (peer.getRef_Invoice_ID() != 0) to.setC_Invoice_ID(peer.getRef_Invoice_ID());
      }
      // find RMA link
      if (from.getM_RMA_ID() != 0) {
        MRMA peer = new MRMA(from.getCtx(), from.getM_RMA_ID(), null);
        if (peer.getRef_RMA_ID() > 0) to.setM_RMA_ID(peer.getRef_RMA_ID());
      }
    } else {
      to.setRef_InOut_ID(0);
      if (setOrder) {
        to.setC_Order_ID(from.getC_Order_ID());
        to.setM_RMA_ID(from.getM_RMA_ID()); // Copy also RMA
      }
    }
    //
    if (!to.save(trxName)) throw new IllegalStateException("Could not create Shipment");
    if (counter) from.setRef_InOut_ID(to.getM_InOut_ID());

    if (to.copyLinesFrom(from, counter, setOrder) <= 0)
      throw new IllegalStateException("Could not create Shipment Lines");

    return to;
  } //	copyFrom

  /**
   * @deprecated Create new Shipment by copying
   * @param from shipment
   * @param dateDoc date of the document date
   * @param C_DocType_ID doc type
   * @param isSOTrx sales order
   * @param counter create counter links
   * @param trxName trx
   * @param setOrder set the order link
   * @return Shipment
   */
  public static MInOut copyFrom(
      MInOut from,
      Timestamp dateDoc,
      int C_DocType_ID,
      boolean isSOTrx,
      boolean counter,
      String trxName,
      boolean setOrder) {
    MInOut to = copyFrom(from, dateDoc, dateDoc, C_DocType_ID, isSOTrx, counter, trxName, setOrder);
    return to;
  }

  protected BigDecimal autoBalanceNegative(
      org.compiere.order.MInOutLine line, MProduct product, BigDecimal qtyToReceive) {
    MStorageOnHand[] storages =
        MStorageOnHand.getWarehouseNegative(
            getCtx(),
            getM_Warehouse_ID(),
            line.getM_Product_ID(),
            0,
            null,
            org.compiere.orm.MClient.MMPOLICY_FiFo.equals(product.getMMPolicy()),
            line.getM_Locator_ID(),
            null,
            false);

    Timestamp dateMPolicy = null;

    for (MStorageOnHand storage : storages) {
      if (storage.getQtyOnHand().signum() < 0 && qtyToReceive.compareTo(Env.ZERO) > 0) {
        dateMPolicy = storage.getDateMaterialPolicy();
        BigDecimal lineMAQty = qtyToReceive;
        if (lineMAQty.compareTo(storage.getQtyOnHand().negate()) > 0)
          lineMAQty = storage.getQtyOnHand().negate();

        // Using ASI from storage record
        MInOutLineMA ma =
            new MInOutLineMA(
                line, storage.getMAttributeSetInstance_ID(), lineMAQty, dateMPolicy, true);
        ma.saveEx();
        qtyToReceive = qtyToReceive.subtract(lineMAQty);
      }
    }
    return qtyToReceive;
  }


  /**
   * Get Document Approval Amount
   *
   * @return amount
   */
  public BigDecimal getApprovalAmt() {
    return Env.ZERO;
  } //	getApprovalAmt

  /**
   * *********************************************************************** Get Summary
   *
   * @return Summary of Document
   */
  public String getSummary() {
    StringBuilder sb = new StringBuilder();
    sb.append(getDocumentNo());
    //	: Total Lines = 123.00 (#1)
    sb.append(":")
            //	.append(Msg.translate(getCtx(),"TotalLines")).append("=").append(getTotalLines())
            .append(" (#")
            .append(getLines(false).length)
            .append(")");
    //	 - Description
    if (getDescription() != null && getDescription().length() > 0)
      sb.append(" - ").append(getDescription());
    return sb.toString();
  } //	getSummary

  /**
   * Get Document Info
   *
   * @return document info (untranslated)
   */
  public String getDocumentInfo() {
    MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
    StringBuilder msgreturn =
            new StringBuilder().append(dt.getNameTrl()).append(" ").append(getDocumentNo());
    return msgreturn.toString();
  } //	getDocumentInfo

  /**
   * Get Document Owner (Responsible)
   *
   * @return AD_User_ID
   */
  public int getDoc_User_ID() {
    return getSalesRep_ID();
  } //	getDoc_User_ID

  /**
   * Get C_Currency_ID
   *
   * @return Accounting Currency
   */
  public int getC_Currency_ID() {
    return Env.getContextAsInt(getCtx(), "$C_Currency_ID");
  } //	getC_Currency_ID
}
