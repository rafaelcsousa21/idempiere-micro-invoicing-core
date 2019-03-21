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

import org.compiere.model.IProcessInfoParameter;
import org.compiere.process.SvrProcess;

import java.util.logging.Level;

/**
 * Create RfQ Response from RfQ Topic
 *
 * @author Jorg Janke
 * @version $Id: RfQCreate.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class RfQCreate extends SvrProcess {
    /**
     * Send RfQ
     */
    private boolean p_IsSendRfQ = false;
    /**
     * RfQ
     */
    private int p_C_RfQ_ID = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("IsSendRfQ")) p_IsSendRfQ = "Y".equals(iProcessInfoParameter.getParameter());
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
        p_C_RfQ_ID = getRecordId();
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message (translated text)
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        throw new NotImplementedException();

    /*
    MRfQ rfq = new MRfQ (getCtx(), p_C_RfQ_ID, null);
    if (log.isLoggable(Level.INFO)) log.info("doIt - " + rfq + ", Send=" + p_IsSendRfQ);
    String error = rfq.checkQuoteTotalAmtOnly();
    if (error != null && error.length() > 0)
    	throw new Exception (error);

    int counter = 0;
    int sent = 0;
    int notSent = 0;

    //	Get all existing responses
    MRfQResponse[] responses = rfq.getResponses (false, false);

    //	Topic
    MRfQTopic topic = new MRfQTopic (getCtx(), rfq.getRfQ_TopicId(), null);
    MRfQTopicSubscriber[] subscribers = topic.getSubscribers();
    for (int i = 0; i < subscribers.length; i++)
    {
    	MRfQTopicSubscriber subscriber = subscribers[i];
    	boolean skip = false;
    	//	existing response
    	for (int r = 0; r < responses.length; r++)
    	{
    		if (subscriber.getBusinessPartnerId() == responses[r].getBusinessPartnerId()
    			&& subscriber.getBusinessPartnerLocationId() == responses[r].getBusinessPartnerLocationId())
    		{
    			skip = true;
    			break;
    		}
    	}
    	if (skip)
    		continue;

    	//	Create Response
    	MRfQResponse response = new MRfQResponse (rfq, subscriber);
    	if (response.getId() == 0)	//	no lines
    		continue;

    	counter++;
    	if (p_IsSendRfQ)
    	{
    		if (response.sendRfQ())
    			sent++;
    		else
    			notSent++;
    	}
    }	//	for all subscribers

    StringBuilder retValue = new StringBuilder("@Created@ ").append(counter);
    if (p_IsSendRfQ)
    	retValue.append(" - @IsSendRfQ@=").append(sent).append(" - @Error@=").append(notSent);
    return retValue.toString();
    */
    } //	doIt
} //	RfQCreate
