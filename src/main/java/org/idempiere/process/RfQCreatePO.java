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
 * Create RfQ PO. Create purchase order(s) for the resonse(s) and lines marked as Selected Winner
 * using the selected Purchase Quantity (in RfQ Line Quantity)
 *
 * @author Jorg Janke
 * @author Teo Sarca, teo.sarca@gmail.com
 * <li>BF [ 2892588 ] Create PO from RfQ is not setting correct the price fields
 * https://sourceforge.net/tracker/?func=detail&aid=2892588&group_id=176962&atid=879332
 * @version $Id: RfQCreatePO.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class RfQCreatePO extends SvrProcess {
    /**
     * RfQ
     */
    private int p_C_RfQ_ID = 0;

    private int p_C_DocType_ID = 0;

    /**
     * Prepare
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("C_DocType_ID")) p_C_DocType_ID = iProcessInfoParameter.getParameterAsInt();
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
        p_C_RfQ_ID = getRecordId();
    } //	prepare

    /**
     * Process. Create purchase order(s) for the resonse(s) and lines marked as Selected Winner using
     * the selected Purchase Quantity (in RfQ Line Quantity) . If a Response is marked as Selected
     * Winner, all lines are created (and Selected Winner of other responses ignored). If there is no
     * response marked as Selected Winner, the lines are used.
     *
     * @return message
     */
    protected String doIt() throws Exception {
        throw new NotImplementedException();
    }
} //	RfQCreatePO
