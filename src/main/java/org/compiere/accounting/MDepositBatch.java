/**
 * ******************************************************************** This file is part of
 * Adempiere ERP Bazaar * http://www.adempiere.org * * Copyright (C) Alejandro Falcone * Copyright
 * (C) Contributors * * This program is free software; you can redistribute it and/or * modify it
 * under the terms of the GNU General Public License * as published by the Free Software Foundation;
 * either version 2 * of the License, or (at your option) any later version. * * This program is
 * distributed in the hope that it will be useful, * but WITHOUT ANY WARRANTY; without even the
 * implied warranty of * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the * GNU General
 * Public License for more details. * * You should have received a copy of the GNU General Public
 * License * along with this program; if not, write to the Free Software * Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, * MA 02110-1301, USA. * * Contributors: * - Alejandro
 * Falcone (afalcone@users.sourceforge.net) * http://www.openbiz.com.ar * * Sponsors: * - Idalica
 * Inc. (http://www.idalica.com) *
 * *********************************************************************
 */
package org.compiere.accounting;

import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.idempiere.common.util.Env;

import java.sql.ResultSet;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.*;

/**
 * Deposit Batch Model
 *
 * @author Alejandro Falcone
 * @version $Id: MDepositBatch.java,v 1.3 2007/06/28 00:51:03 afalcone Exp $
 */
public class MDepositBatch extends X_C_DepositBatch implements IPODoc {
  /** */
  private static final long serialVersionUID = -977397802747749777L;

  /**
   * Create & Load existing Persistent Object
   *
   * @param ctx context
   * @param C_DepositBatch_ID The unique ID of the object
   * @param trxName transaction name
   */
  public MDepositBatch(Properties ctx, int C_DepositBatch_ID, String trxName) {
    super(ctx, C_DepositBatch_ID, trxName);
    if (C_DepositBatch_ID == 0) {
      setDocStatus(X_C_DepositBatch.DOCSTATUS_Drafted);
      setProcessed(false);
      setProcessing(false);
      setDepositAmt(Env.ZERO);
    }
  } //	MDepositBatch

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MDepositBatch(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MDepositBatch

  /**
   * Copy Constructor. Dos not copy: Dates/Period
   *
   * @param original original
   */
  public MDepositBatch(MDepositBatch original) {
    this(original.getCtx(), 0, null);
    setClientOrg(original);
    setC_DepositBatch_ID(original.getC_DepositBatch_ID());

    setDescription(original.getDescription());
    setC_DocType_ID(original.getC_DocType_ID());

    setDateDoc(original.getDateDoc());
    setDateDeposit(original.getDateDeposit());
    setDepositAmt(original.getDepositAmt());
  } //	MDepositBatch

  /**
   * Overwrite Client/Org if required
   *
   * @param AD_Client_ID client
   * @param AD_Org_ID org
   */
  public void setClientOrg(int AD_Client_ID, int AD_Org_ID) {
    super.setClientOrg(AD_Client_ID, AD_Org_ID);
  } //	setClientOrg

    /** Process Message */
  private String m_processMsg = null;
  /** Just Prepared Flag */
  //	private boolean		m_justPrepared = false;

    /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuffer sb = new StringBuffer("MDepositBatch[");
    sb.append(getId())
        .append(",")
        .append(getDescription())
        .append(",Amount=")
        .append(getDepositAmt())
        .append("]");
    return sb.toString();
  } //	toString

    /**
   * After Delete
   *
   * @param success success
   * @return success
   */
  protected boolean afterDelete(boolean success) {
    if (getC_DepositBatch_ID() != 0) {
      String sql = "UPDATE C_Payment p SET C_DepositBatch_ID= 0  WHERE p.C_DepositBatch_ID=?";
      executeUpdateEx(sql, new Object[] {getC_DepositBatch_ID()});
    }

    return success;
  } //	afterDelete

    @Override
  public void setDoc(IDoc doc) {}

  @Override
  public void setProcessedOn(String processed, boolean b, boolean b1) {}
} //	MDepositBatch
