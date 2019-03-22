/**
 * **************************************************************************** Product: Adempiere
 * ERP & CRM Smart Business Solution * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it * under the terms version
 * 2 of the GNU General Public License as published * by the Free Software Foundation. This program
 * is distributed in the hope * that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. * See the GNU General
 * Public License for more details. * You should have received a copy of the GNU General Public
 * License along * with this program; if not, write to the Free Software Foundation, Inc., * 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA. * For the text or an alternative of this
 * public license, you may reach us * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA
 * 95054, USA * or via info@compiere.org or http://www.compiere.org/license.html *
 * ***************************************************************************
 */
package org.idempiere.process;

import org.compiere.accounting.MOrder;
import org.compiere.accounting.MProduct;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.logging.Level;

/**
 * Create Distribution List Order
 *
 * @author Jorg Janke
 * @version $Id: DistributionCreate.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class DistributionCreate extends SvrProcess {
    /**
     * Product
     */
    private int p_M_Product_ID = 0;
    /**
     * Quantity
     */
    private BigDecimal p_Qty;
    /**
     * Single Order
     */
    private boolean p_IsCreateSingleOrder;
    /**
     * Single Order BP
     */
    private int p_Bill_BPartner_ID;
    /**
     * SingleOrder Location
     */
    private int p_Bill_Location_ID;
    /**
     * Test Mode
     */
    private boolean p_IsTest;
    /**
     * Distribution List
     */
    private int p_M_DistributionList_ID;

    //	DatePromised
    //	C_DocType_ID

    /** Distribution List */
  /* throw new NotImplementedException();
  private MDistributionList m_dl;*/

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();
            //	log.fine("prepare - " + para[i]);

            switch (name) {
                case "M_Product_ID":
                    p_M_Product_ID = iProcessInfoParameter.getParameterAsInt();
                    break;
                case "Qty":
                    p_Qty = (BigDecimal) iProcessInfoParameter.getParameter();
                    break;
                case "IsCreateSingleOrder":
                    p_IsCreateSingleOrder = "Y".equals(iProcessInfoParameter.getParameter());
                    break;
                case "Bill_BPartner_ID":
                    p_Bill_BPartner_ID = iProcessInfoParameter.getParameterAsInt();
                    break;
                case "p_Bill_Location_ID":
                    p_Bill_Location_ID = iProcessInfoParameter.getParameterAsInt();
                    break;
                case "IsTest":
                    p_IsTest = "Y".equals(iProcessInfoParameter.getParameter());
                    break;
                default:
                    log.log(Level.SEVERE, "Unknown Parameter: " + name);
                    break;
            }
        }
        p_M_DistributionList_ID = getRecordId();
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message (text with variables)
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO))
            log.info(
                    "M_DistributionList_ID="
                            + p_M_DistributionList_ID
                            + ", M_Product_ID="
                            + p_M_Product_ID
                            + ", Qty="
                            + p_Qty
                            + ", Test="
                            + p_IsTest);
        if (p_IsCreateSingleOrder)
            if (log.isLoggable(Level.INFO))
                log.info(
                        "SingleOrder="
                                + p_IsCreateSingleOrder
                                + ", BPartner_ID="
                                + p_Bill_BPartner_ID
                                + ", Location_ID="
                                + p_Bill_Location_ID);
        //
        if (p_M_DistributionList_ID == 0) throw new IllegalArgumentException("No Distribution List ID");
        throw new NotImplementedException();

    /*
    m_dl = new MDistributionList(getCtx(), p_M_DistributionList_ID, null);
    if (m_dl.getId() == 0)
    	throw new Exception ("Distribution List not found -  M_DistributionList_ID=" +  p_M_DistributionList_ID);
    //
    if (p_M_Product_ID == 0)
    	throw new IllegalArgumentException ("No Product");
    m_product = MProduct.get (getCtx(), p_M_Product_ID);
    if (m_product.getId() == 0)
    	throw new Exception ("Product not found -  M_Product_ID=" +  p_M_Product_ID);
    if (p_Qty == null || p_Qty.signum() != 1)
    	throw new IllegalArgumentException ("No Quantity");
    //
    if (p_IsCreateSingleOrder && p_Bill_BPartner_ID == 0)
    	throw new IllegalArgumentException ("Invoice Business Partner required for single Order");
    //	Create Single Order
    if (!p_IsTest && p_IsCreateSingleOrder)
    {
    	MBPartner bp = new MBPartner (getCtx(), p_Bill_BPartner_ID, null);
    	if (bp.getId() == 0)
    		throw new IllegalArgumentException("Single Business Partner not found - C_BPartner_ID=" + p_Bill_BPartner_ID);
    	//
    	m_singleOrder = new MOrder (getCtx(), 0, null);
    	m_singleOrder.setIsSOTrx(true);
    	m_singleOrder.setTargetDocumentTypeId(MOrder.DocSubTypeSO_Standard);
    	m_singleOrder.setBPartner(bp);
    	if (p_Bill_Location_ID != 0)
    		m_singleOrder.setBusinessPartnerLocationId(p_Bill_Location_ID);
    	if (!m_singleOrder.save())
    		throw new IllegalStateException("Single Order not created");
    }

    MDistributionListLine[] lines = m_dl.getLines();
    int counter = 0;
    for (int i = 0; i < lines.length; i++)
    {
    	if (createOrder(lines[i]))
    		counter++;
    }

    //	Update Qty
    if (m_singleOrder != null)
    {
    	StringBuilder msg = new StringBuilder("# ").append(counter).append(" - ").append(m_totalQty);
    	m_singleOrder.setDescription(msg.toString());
    	m_singleOrder.saveEx();
    }

    StringBuilder msgreturn = new StringBuilder("@Created@ #").append(counter).append(" - @Qty@=").append(m_totalQty);
    return msgreturn.toString();
    */
    } //	doIt

    /**
     * Create Order for Distribution Line
     *
     * @param dll Distribution Line
     * @return true if created
     */
  /*
  private boolean createOrder (MDistributionListLine dll)
  {
  	MBPartner bp = new MBPartner (getCtx(), dll.getBusinessPartnerId(), null);
  	if (bp.getId() == 0)
  		throw new IllegalArgumentException("Business Partner not found - C_BPartner_ID=" + dll.getBusinessPartnerId());

  	//	Create Order
  	MOrder order = m_singleOrder;
  	if (!p_IsTest && order == null)
  	{
  		order = new MOrder (getCtx(), 0, null);
  		order.setIsSOTrx(true);
  		order.setTargetDocumentTypeId(MOrder.DocSubTypeSO_Standard);
  		order.setBPartner(bp);
  		if (dll.getBusinessPartnerLocationId() != 0)
  			order.setBusinessPartnerLocationId(dll.getBusinessPartnerLocationId());
  		if (!order.save())
  		{
  			log.log(Level.SEVERE, "Order not saved");
  			return false;
  		}
  	}
  	//	Calculate Qty
  	BigDecimal ratio = dll.getRatio();
  	BigDecimal qty = p_Qty.multiply(ratio);
  	if (qty.compareTo(Env.ZERO) != 0)
  		qty = qty.divide(m_dl.getRatioTotal(), m_product.getUOMPrecision(), BigDecimal.ROUND_HALF_UP);
  	BigDecimal minQty = dll.getMinQty();
  	if (qty.compareTo(minQty) < 0)
  		qty = minQty;
  	m_totalQty = m_totalQty.add(qty);
  	//
  	if (p_IsTest)
  	{
  		addLog(0,null, qty, bp.getName());
  		return false;
  	}

  	//	Create Order Line
  	MOrderLine line = new MOrderLine(order);
  	line.setBusinessPartnerId(dll.getBusinessPartnerId());
  	if (dll.getBusinessPartnerLocationId() != 0)
  		line.setBusinessPartnerLocationId(dll.getBusinessPartnerLocationId());
  	//
  	line.setProductId(p_M_Product_ID, true);
  	line.setQty(qty);
  	line.setPrice();
  	if (!line.save())
  	{
  		log.log(Level.SEVERE, "OrderLine not saved");
  		return false;
  	}

  	addLog(0,null, qty, order.getDocumentNo() + ": " + bp.getName());
  	return true;
  }	//	createOrder
  */

} //	DistributionCreate
