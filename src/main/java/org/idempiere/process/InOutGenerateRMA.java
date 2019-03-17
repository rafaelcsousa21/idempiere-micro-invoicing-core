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

import org.compiere.invoicing.MInOut;
import org.compiere.invoicing.MInOutLine;
import org.compiere.invoicing.MInvoiceLine;
import org.compiere.invoicing.MRMA;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.order.MRMALine;
import org.compiere.orm.Query;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.getSQLValue;
import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Generate shipment for Vendor RMA. Based on {@link org.compiere.process.InOutGenerate}.
 *
 * @author Ashley Ramdass
 * @author Teo Sarca
 * <li>BF [ 2818523 ] Invoice and Shipment are not matched in case of RMA
 * https://sourceforge.net/tracker/?func=detail&aid=2818523&group_id=176962&atid=879332
 */
public class InOutGenerateRMA extends SvrProcess {
    /**
     * Manual Selection
     */
    private boolean p_Selection = false;
    /**
     * Warehouse
     */
    @SuppressWarnings("unused")
    private int p_M_Warehouse_ID = 0;
    /**
     * DocAction
     */
    private String p_docAction = DocAction.Companion.getACTION_Complete();
    /**
     * Number of Shipments
     */
    private int m_created = 0;
    /**
     * Movement Date
     */
    private Timestamp m_movementDate = null;

    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (int i = 0; i < para.length; i++) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) ;
            else if (name.equals("M_Warehouse_ID")) p_M_Warehouse_ID = para[i].getParameterAsInt();
            else if (name.equals("Selection")) p_Selection = "Y".equals(para[i].getParameter());
            else if (name.equals("DocAction")) p_docAction = (String) para[i].getParameter();
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }

        m_movementDate = Env.getContextAsDate(getCtx(), "#Date");
        if (m_movementDate == null) {
            m_movementDate = new Timestamp(System.currentTimeMillis());
        }
    }

    protected String doIt() throws Exception {
        if (!p_Selection) {
            throw new IllegalStateException("Shipments can only be generated from selection");
        }

        String sql =
                "SELECT rma.M_RMA_ID FROM M_RMA rma, T_Selection "
                        + "WHERE rma.DocStatus='CO' AND rma.IsSOTrx='N' AND rma.AD_Client_ID=? "
                        + "AND rma.M_RMA_ID = T_Selection.T_Selection_ID "
                        + "AND T_Selection.AD_PInstance_ID=? ";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, Env.getClientId(getCtx()));
            pstmt.setInt(2, getAD_PInstanceId());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                generateShipment(rs.getInt(1));
            }
        } catch (Exception ex) {
            throw new AdempiereException(ex);
        } finally {

            rs = null;
            pstmt = null;
        }

        StringBuilder msgreturn = new StringBuilder("@Created@ = ").append(m_created);
        return msgreturn.toString();
    }

    private int getShipmentDocTypeId(int M_RMA_ID) {
        String docTypeSQl =
                "SELECT dt.C_DocTypeShipment_ID FROM C_DocType dt "
                        + "INNER JOIN M_RMA rma ON dt.C_DocType_ID=rma.C_DocType_ID "
                        + "WHERE rma.M_RMA_ID=?";

        int docTypeId = getSQLValue(docTypeSQl, M_RMA_ID);

        return docTypeId;
    }

    private MInOut createShipment(MRMA rma) {
        int docTypeId = getShipmentDocTypeId(rma.getId());

        if (docTypeId == -1) {
            throw new IllegalStateException("Could not get invoice document type for Vendor RMA");
        }

        MInOut originalReceipt = rma.getShipment();

        MInOut shipment = new MInOut(getCtx(), 0);
        shipment.setRMAId(rma.getId());
        shipment.setOrgId(rma.getOrgId());
        shipment.setTransactionOrganizationId(originalReceipt.getTransactionOrganizationId());
        shipment.setDescription(rma.getDescription());
        shipment.setBusinessPartnerId(rma.getBusinessPartnerId());
        shipment.setBusinessPartnerLocationId(originalReceipt.getBusinessPartnerLocationId());
        shipment.setIsSOTrx(rma.isSOTrx());
        shipment.setDocumentTypeId(docTypeId);
        shipment.setWarehouseId(originalReceipt.getWarehouseId());
        shipment.setMovementType(MInOut.MOVEMENTTYPE_VendorReturns);
        shipment.setProjectId(originalReceipt.getProjectId());
        shipment.setCampaignId(originalReceipt.getCampaignId());
        shipment.setBusinessActivityId(originalReceipt.getBusinessActivityId());
        shipment.setUser1Id(originalReceipt.getUser1Id());
        shipment.setUser2Id(originalReceipt.getUser2Id());

        if (!shipment.save()) {
            throw new IllegalStateException("Could not create Shipment");
        }

        return shipment;
    }

    private MInOutLine[] createShipmentLines(MRMA rma, MInOut shipment) {
        ArrayList<MInOutLine> shipLineList = new ArrayList<MInOutLine>();

        MRMALine[] rmaLines = rma.getLines(true);
        for (MRMALine rmaLine : rmaLines) {
            if (rmaLine.getInOutLineId() != 0
                    || rmaLine.getChargeId() != 0
                    || rmaLine.getProductId() != 0) {
                MInOutLine shipLine = new MInOutLine(shipment);
                shipLine.setRMALineId(rmaLine.getId());
                shipLine.setLine(rmaLine.getLine());
                shipLine.setDescription(rmaLine.getDescription());

                if (rmaLine.getChargeId() != 0) {
                    shipLine.setChargeId(rmaLine.getChargeId());
                    shipLine.setValueNoCheck(MInOutLine.COLUMNNAME_M_Product_ID, null);
                    shipLine.setValueNoCheck(MInOutLine.COLUMNNAME_M_AttributeSetInstance_ID, null);
                    shipLine.setValueNoCheck(MInOutLine.COLUMNNAME_M_Locator_ID, null);
                } else {
                    shipLine.setProductId(rmaLine.getProductId());
                    shipLine.setAttributeSetInstanceId(rmaLine.getAttributeSetInstanceId());
                    shipLine.setLocatorId(rmaLine.getLocatorId());
                }

                shipLine.setUOMId(rmaLine.getUOMId());
                shipLine.setQty(rmaLine.getQty());
                shipLine.setProjectId(rmaLine.getProjectId());
                shipLine.setCampaignId(rmaLine.getCampaignId());
                shipLine.setBusinessActivityId(rmaLine.getBusinessActivityId());
                shipLine.setProjectPhaseId(rmaLine.getProjectPhaseId());
                shipLine.setProjectTaskId(rmaLine.getProjectTaskId());
                shipLine.setUser1Id(rmaLine.getUser1Id());
                shipLine.setUser2Id(rmaLine.getUser2Id());
                shipLine.saveEx();
                shipLineList.add(shipLine);
                //
                // Link to corresponding Invoice Line (if any) - teo_sarca [ 2818523 ]
                // The MMatchInv records will be automatically generated on MInOut.completeIt()
                MInvoiceLine invoiceLine =
                        new Query(
                                shipment.getCtx(),
                                I_C_InvoiceLine.Table_Name,
                                I_C_InvoiceLine.COLUMNNAME_M_RMALine_ID + "=?"
                        )
                                .setParameters(rmaLine.getRMALineId())
                                .firstOnly();
                if (invoiceLine != null) {
                    invoiceLine.setInOutLineId(shipLine.getInOutLineId());
                    invoiceLine.saveEx();
                }
            }
        }

        MInOutLine[] shipLines = new MInOutLine[shipLineList.size()];
        shipLineList.toArray(shipLines);

        return shipLines;
    }

    private void generateShipment(int M_RMA_ID) {
        MRMA rma = new MRMA(getCtx(), M_RMA_ID);
        statusUpdate(Msg.getMsg(getCtx(), "Processing") + " " + rma.getDocumentInfo());

        MInOut shipment = createShipment(rma);
        MInOutLine[] shipmentLines = createShipmentLines(rma, shipment);

        if (shipmentLines.length == 0) {
            StringBuilder msglog =
                    new StringBuilder("No shipment lines created: M_RMA_ID=")
                            .append(M_RMA_ID)
                            .append(", M_InOut_ID=")
                            .append(shipment.getId());
            log.log(Level.WARNING, msglog.toString());
        }

        StringBuffer processMsg = new StringBuffer().append(shipment.getDocumentNo());

        if (!shipment.processIt(p_docAction)) {
            processMsg.append(" (NOT Processed)");
            StringBuilder msglog =
                    new StringBuilder("Shipment Processing failed: ")
                            .append(shipment)
                            .append(" - ")
                            .append(shipment.getProcessMsg());
            log.warning(msglog.toString());
            throw new IllegalStateException(
                    "Shipment Processing failed: " + shipment + " - " + shipment.getProcessMsg());
        }

        if (!shipment.save()) {
            throw new IllegalStateException("Could not update shipment");
        }

        // Add processing information to process log
        addBufferLog(
                shipment.getInOutId(),
                shipment.getMovementDate(),
                null,
                processMsg.toString(),
                shipment.getTableId(),
                shipment.getInOutId());
        m_created++;
    }
}
