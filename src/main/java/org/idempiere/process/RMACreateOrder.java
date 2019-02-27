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
import org.compiere.accounting.MOrderLine;
import org.compiere.invoicing.MRMA;
import org.compiere.model.I_C_Invoice;
import org.compiere.order.MInOut;
import org.compiere.order.MRMALine;
import org.compiere.process.SvrProcess;

/**
 * Creates Order from RMA document
 *
 * @author Ashley Ramdass
 */
public class RMACreateOrder extends SvrProcess {

    private int rmaId = 0;

    @Override
    protected void prepare() {
        rmaId = getRecord_ID();
    }

    @Override
    protected String doIt() throws Exception {
        // Load RMA
        MRMA rma = new MRMA(getCtx(), rmaId);

        // Load Original Order
        org.compiere.order.MOrder originalOrder = rma.getOriginalOrder();

        if (rma.getId() == 0) {
            throw new Exception("No RMA defined");
        }

        if (originalOrder == null) {
            throw new Exception("Could not load the original order");
        }

        // Create new order and set the different values based on original order/RMA doc
        MOrder order = new MOrder(getCtx(), 0);
        order.setOrgId(rma.getOrgId());
        order.setBusinessPartnerId(originalOrder.getBusinessPartnerId());
        order.setBusinessPartnerLocationId(originalOrder.getBusinessPartnerLocationId());
        order.setUserId(originalOrder.getUserId());
        order.setBill_BPartner_ID(originalOrder.getBill_BPartner_ID());
        order.setBusinessPartnerInvoicingLocationId(originalOrder.getBusinessPartnerInvoicingLocationId());
        order.setBill_User_ID(originalOrder.getBill_User_ID());
        order.setSalesRepresentativeId(rma.getSalesRepresentativeId());
        order.setPriceListId(originalOrder.getPriceListId());
        order.setIsSOTrx(originalOrder.isSOTrx());
        order.setWarehouseId(originalOrder.getWarehouseId());
        order.setTargetDocumentTypeId(originalOrder.getTargetDocumentTypeId());
        order.setPaymentTermId(originalOrder.getPaymentTermId());
        order.setDeliveryRule(originalOrder.getDeliveryRule());

        if (!order.save()) {
            throw new IllegalStateException("Could not create order");
        }

        MInOut originalShipment = rma.getShipment();
        I_C_Invoice originalInvoice = rma.getOriginalInvoice();

        MRMALine lines[] = rma.getLines(true);
        for (MRMALine line : lines) {
            if (line.getShipLine() != null
                    && line.getShipLine().getC_OrderLine_ID() != 0
                    && line.getM_Product_ID() != 0) {
                // Create order lines if the RMA Doc line has a shipment line
                MOrderLine orderLine = new MOrderLine(order);
                MOrderLine originalOLine =
                        new MOrderLine(getCtx(), line.getShipLine().getC_OrderLine_ID());
                orderLine.setOrgId(line.getOrgId());
                orderLine.setM_Product_ID(originalOLine.getM_Product_ID());
                orderLine.setM_AttributeSetInstance_ID(originalOLine.getMAttributeSetInstance_ID());
                orderLine.setC_UOM_ID(originalOLine.getC_UOM_ID());
                orderLine.setC_Tax_ID(originalOLine.getC_Tax_ID());
                orderLine.setWarehouseId(originalOLine.getWarehouseId());
                orderLine.setCurrencyId(originalOLine.getCurrencyId());
                orderLine.setQty(line.getQty());
                orderLine.setProjectId(originalOLine.getProjectId());
                orderLine.setBusinessActivityId(originalOLine.getBusinessActivityId());
                orderLine.setCampaignId(originalOLine.getCampaignId());
                orderLine.setPrice();
                orderLine.setPrice(line.getAmt());

                if (!orderLine.save()) {
                    throw new IllegalStateException("Could not create Order Line");
                }
            } else if (line.getM_Product_ID() != 0) {
                if (originalInvoice != null) {
                    MOrderLine orderLine = new MOrderLine(order);
                    orderLine.setOrgId(line.getOrgId());
                    orderLine.setM_Product_ID(line.getM_Product_ID());
                    orderLine.setM_AttributeSetInstance_ID(line.getMAttributeSetInstance_ID());
                    orderLine.setC_UOM_ID(line.getC_UOM_ID());
                    orderLine.setC_Tax_ID(line.getC_Tax_ID());
                    orderLine.setWarehouseId(originalShipment.getWarehouseId());
                    orderLine.setCurrencyId(originalInvoice.getCurrencyId());
                    orderLine.setQty(line.getQty());
                    orderLine.setProjectId(line.getProjectId());
                    orderLine.setBusinessActivityId(line.getBusinessActivityId());
                    orderLine.setCampaignId(line.getCampaignId());
                    orderLine.setPrice();
                    orderLine.setPrice(line.getAmt());

                    if (!orderLine.save()) {
                        throw new IllegalStateException("Could not create Order Line");
                    }
                } else if (originalOrder != null) {
                    MOrderLine orderLine = new MOrderLine(order);
                    orderLine.setOrgId(line.getOrgId());
                    orderLine.setM_Product_ID(line.getM_Product_ID());
                    orderLine.setM_AttributeSetInstance_ID(line.getMAttributeSetInstance_ID());
                    orderLine.setC_UOM_ID(line.getC_UOM_ID());
                    orderLine.setC_Tax_ID(line.getC_Tax_ID());
                    orderLine.setWarehouseId(originalOrder.getWarehouseId());
                    orderLine.setCurrencyId(originalOrder.getCurrencyId());
                    orderLine.setQty(line.getQty());
                    orderLine.setProjectId(line.getProjectId());
                    orderLine.setBusinessActivityId(line.getBusinessActivityId());
                    orderLine.setCampaignId(line.getCampaignId());
                    orderLine.setPrice();
                    orderLine.setPrice(line.getAmt());

                    if (!orderLine.save()) {
                        throw new IllegalStateException("Could not create Order Line");
                    }
                }
            }
        }

        rma.setOrderId(order.getOrderId());
        if (!rma.save()) {
            throw new IllegalStateException("Could not update RMA document");
        }

        return "Order Created: " + order.getDocumentNo();
    }
}
